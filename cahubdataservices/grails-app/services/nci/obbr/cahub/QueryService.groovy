package nci.obbr.cahub

import nci.obbr.cahub.util.querytracker.Query
import nci.obbr.cahub.util.querytracker.QueryAttachment
import nci.obbr.cahub.util.FileUpload
import nci.obbr.cahub.staticmembers.CaseAttachmentType
import nci.obbr.cahub.staticmembers.ActivityType
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.staticmembers.BSS

class QueryService {

    static transactional = true
    
    def activityEventService

    def upload(params, uploadedFile, username, orgCode) {   
        try {
            def queryInstance = Query.get(params.id)
            def fileUploadInstance = new FileUpload(params)
            
            def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            def strippedFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))                    
            def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                         
            def current_time = (new Date()).getTime()           
            def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
            def pathUploads = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + orgCode + File.separator + 'query_tracker' + File.separator + params.id
            File dir = new File(pathUploads)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            uploadedFile.transferTo(new File(pathUploads, newFileName))
            
            fileUploadInstance.fileName = newFileName
            fileUploadInstance.filePath = pathUploads
            fileUploadInstance.uploadTime = new Date().getDateTimeString()
            fileUploadInstance.hideFromBss = params.hideFromBss
            fileUploadInstance.save(flush:true)
            def queryAttachmentInstance = new QueryAttachment(fileUpload:fileUploadInstance, uploadedBy:username, query:queryInstance).save(flush:true)
            queryInstance.addToQueryAttachments(queryAttachmentInstance)
            queryInstance.save(flush:true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def remove(params) {
        try {
            def queryAttachmentInstance = QueryAttachment.get(params.attachmentId)
            def path = queryAttachmentInstance?.fileUpload?.filePath + File.separator + queryAttachmentInstance?.fileUpload?.fileName
            def file = new File(path)
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the query attachment file")
                }
            }
            queryAttachmentInstance?.delete(flush:true)
            queryAttachmentInstance?.fileUpload?.delete(flush:true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def createFznRequest(organization, caseRecord, queryType, description, dueDate, openedBy) {
        def queryInstance = new Query(
            organization: organization,
            caseRecord: caseRecord,
            isDcf: 'No',
            isPr2: 'No',
            queryType: queryType,
            description: description,
            dueDate: dueDate,
            openedBy: openedBy,
            study: BSS.findByCode(organization?.code)?.study,
            queryStatus: QueryStatus.findByCode("ACTIVE"),
            task: 'FZN'
        )
        
        if (queryInstance.save(flush: true)) {
            def activityType = ActivityType.findByCode("QUERY")
            def caseId = queryInstance.caseRecord?.caseId
            def study = queryInstance.caseRecord?.study
            def bssCode = queryInstance.organization?.code
            def username = openedBy
            def orgCode = queryInstance.organization?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, queryInstance.id, orgCode)
        }
    }
    
    def createInfectiousDiseaseEntry(organization, caseRecord, queryType, description, dueDate, task) {
        def queryInstance = new Query(
            organization: organization,
            caseRecord: caseRecord,
            isDcf: 'No',
            isPr2: 'No',
            queryType: queryType,
            description: description,
            bpvClinical: true,
            dueDate: dueDate,
            openedBy: 'auto',
            study: BSS.findByCode(organization?.code)?.study,
            queryStatus: QueryStatus.findByCode("ACTIVE"),
            task: task
        )
        
        if (queryInstance.save(flush: true)) {
            def activityType = ActivityType.findByCode("QUERY")
            def caseId = queryInstance.caseRecord?.caseId
            def study = queryInstance.caseRecord?.study
            def bssCode = queryInstance.caseRecord?.bss?.parentBss?.code
            def username = queryInstance.openedBy
            def orgCode = queryInstance.organization?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, queryInstance.id, orgCode)
        }
    }
}
