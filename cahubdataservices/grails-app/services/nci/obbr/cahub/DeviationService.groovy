package nci.obbr.cahub

import nci.obbr.cahub.util.querytracker.Deviation
import nci.obbr.cahub.util.querytracker.Query
import nci.obbr.cahub.util.querytracker.QueryAttachment
import nci.obbr.cahub.util.FileUpload
import nci.obbr.cahub.staticmembers.CaseAttachmentType

class DeviationService {

    static transactional = true

    def upload(params, uploadedFile, username, orgCode) {   
        try {
            def deviationInstance = Deviation.get(params.id)
            def fileUploadInstance = new FileUpload(params)
            
            def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            def strippedFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))                    
            def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                         
            def current_time = (new Date()).getTime()           
            def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
            def pathUploads = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + orgCode + File.separator + 'deviation_list' + File.separator + params.id
            File dir = new File(pathUploads)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            uploadedFile.transferTo(new File(pathUploads, newFileName))
            
            fileUploadInstance.fileName = newFileName
            fileUploadInstance.filePath = pathUploads
            fileUploadInstance.uploadTime = new Date().getDateTimeString()
            fileUploadInstance.save(flush:true)
            def queryAttachmentInstance = new QueryAttachment(fileUpload:fileUploadInstance, uploadedBy:username, deviation:deviationInstance).save(flush:true)
            deviationInstance.addToQueryAttachments(queryAttachmentInstance)
            deviationInstance.save(flush:true)
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
                    throw new IOException("Failed to delete the documentation file")
                }
            }
            queryAttachmentInstance?.delete(flush:true)
            queryAttachmentInstance?.fileUpload?.delete(flush:true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def saveQueries(deviationInstance, params) {
        try {
            // clear the hasMany queries collection
            for (query in deviationInstance.queries) {
                query.deviation = null
            }
            
            def splits
            for (i in params) {
                splits = i.key.split("_")
                if (splits[0] == "query") {
                    deviationInstance.addToQueries(Query.get(splits[1])).save(flush:true)
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
}
