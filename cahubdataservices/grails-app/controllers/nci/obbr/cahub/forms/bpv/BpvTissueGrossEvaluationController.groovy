package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.PhotoRecord
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import nci.obbr.cahub.util.ComputeMethods
import grails.plugins.springsecurity.Secured

class BpvTissueGrossEvaluationController {
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def bpvTissueGrossEvaluationService
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvTissueGrossEvaluationInstanceList: BpvTissueGrossEvaluation.list(params), bpvTissueGrossEvaluationInstanceTotal: BpvTissueGrossEvaluation.count()]
    }

    def create = {
        def bpvTissueGrossEvaluationInstance = new BpvTissueGrossEvaluation()
        bpvTissueGrossEvaluationInstance.properties = params
        
        return [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance]
    }

    def save = {
        def bpvTissueGrossEvaluationInstance = new BpvTissueGrossEvaluation(params)
        //params.each() {key, value ->
        //   println("in save, key: " + key + "value: " + value)
        //}
        
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvTissueGrossEvaluationInstance.transportSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }

        if (bpvTissueGrossEvaluationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            
            redirect(action: "edit", id: bpvTissueGrossEvaluationInstance.id)
        }
        else {
            //def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
            render(view: "create", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance])
        }
    }

    def show = {
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        if (!bpvTissueGrossEvaluationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueGrossEvaluationInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
            }
            //            if (!accessPrivilegeService.checkAccessPrivilege(bpvTissueGrossEvaluationInstance.caseRecord, session, 'view')) {
            //                redirect(controller: "login", action: "denied")
            //                return
            //            }
            def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
            def cdrVer = bpvTissueGrossEvaluationInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }            
            def canResume = ( accessPrivilegeService.checkAccessPrivilege(bpvTissueGrossEvaluationInstance.caseRecord, session, 'edit') == 0)
            [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, warningMap:warningMap, version53:version53, canResume: canResume]
        }
    }

    def edit = {
        def canSubmit = 'No'
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        if (!bpvTissueGrossEvaluationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueGrossEvaluationInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
            }
            if (bpvTissueGrossEvaluationInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvTissueGrossEvaluationInstance)
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            def cdrVer = bpvTissueGrossEvaluationInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }            
            
            def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
            return [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, canSubmit: canSubmit, warningMap:warningMap, version53:version53]
        }
    }
    
    def editWithValidation = {
        def canSubmit = 'No'
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        if (!bpvTissueGrossEvaluationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueGrossEvaluationInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
            }
            if (bpvTissueGrossEvaluationInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvTissueGrossEvaluationInstance)
            errorMap.each() {key, value ->
                bpvTissueGrossEvaluationInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            def cdrVer = bpvTissueGrossEvaluationInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }                        
            def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
            render(view: "edit", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, canSubmit: canSubmit, warningMap:warningMap, version53:version53])
        }
    }

    def update = {
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        // params.each() {key, value ->
        //   println("in update, key: " + key + "value: " + value)
        //}
        if (bpvTissueGrossEvaluationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvTissueGrossEvaluationInstance.version > version) {
                    
                    bpvTissueGrossEvaluationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvTissueGrossEvaluation while you were editing")
                    def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
                    render(view: "edit", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, warningMap:warningMap])
                    return
                }
            }
            bpvTissueGrossEvaluationInstance.properties = params
            if (!bpvTissueGrossEvaluationInstance.hasErrors() && bpvTissueGrossEvaluationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
                redirect(action: "editWithValidation", id: bpvTissueGrossEvaluationInstance.id)
            }
            else {
                def cdrVer = bpvTissueGrossEvaluationInstance.caseRecord.cdrVer
                def  version53 = true
                if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                    version53 = false
                }
                def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
                render(view: "edit", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, warningMap:warningMap, version53:version53])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    def submit = {
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        if (bpvTissueGrossEvaluationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvTissueGrossEvaluationInstance.version > version) {
                    
                    bpvTissueGrossEvaluationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvTissueGrossEvaluation while you were editing")
                    def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
                    render(view: "edit", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, warningMap: warningMap])
                    return
                }
            }
            bpvTissueGrossEvaluationInstance.properties = params
            bpvTissueGrossEvaluationInstance.dateSubmitted = new Date()
            bpvTissueGrossEvaluationInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def errorMap = checkError(bpvTissueGrossEvaluationInstance)
            errorMap.each() {key, value ->
                bpvTissueGrossEvaluationInstance.errors.rejectValue(key, value)
            }
            if (!bpvTissueGrossEvaluationInstance.hasErrors() && bpvTissueGrossEvaluationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.submitted.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
                //redirect(action: "show", id: bpvTissueGrossEvaluationInstance.id)
                redirect(controller: "caseRecord", action: "display", id: bpvTissueGrossEvaluationInstance.caseRecord.id)
            }
            else {
                bpvTissueGrossEvaluationInstance.discard()
                def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
                def  version53 = true
                if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                    version53 = false
                }                
                render(view: "edit", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, warningMap: warningMap, version53:version53])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        if (bpvTissueGrossEvaluationInstance) {
            try {
                bpvTissueGrossEvaluationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    def resumeEditing = {
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        bpvTissueGrossEvaluationInstance.dateSubmitted = null
        bpvTissueGrossEvaluationInstance.submittedBy = null
        if (bpvTissueGrossEvaluationInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvTissueGrossEvaluationInstance.id)
        }
        else {
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }            
            def warningMap = getWarningMap(bpvTissueGrossEvaluationInstance)
            render(view: "show", model: [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance, warningMap:warningMap, version53:version53])
        }
    }
    
    def upload = {
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        return [bpvTissueGrossEvaluationInstance: bpvTissueGrossEvaluationInstance]
    }
    
    def upload_save ={
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        def uploadedFile = request.getFile("filepath")
        if (!uploadedFile.empty) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvTissueGrossEvaluationInstance.version > version) {    
                    bpvTissueGrossEvaluationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvTissueGrossEvaluation while you were editing")
                    render(view: "upload", model: [bpvTissueGrossEvaluationInstance:bpvTissueGrossEvaluationInstance])
                    return
                }
            }
            bpvTissueGrossEvaluationService.upload(params, uploadedFile)
            flash.message = "${message(code: 'default.uploaded.message', args: [message(code: 'bpvTissueGrossEvaluationInstance.label', default: 'Tissue photo for Case'), bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            redirect(controller: 'bpvTissueGrossEvaluation', action: "edit", params: [id:bpvTissueGrossEvaluationInstance.id])
        } else {
            bpvTissueGrossEvaluationInstance.errors.reject("error", "Please choose a file")
            render(view: "upload", model: [bpvTissueGrossEvaluationInstance:bpvTissueGrossEvaluationInstance])
        }
    }
   
    def download = {
        def photoRecordInstance = PhotoRecord.get(params.photoId)
        def path = photoRecordInstance.filePath + File.separator + photoRecordInstance.fileName
        def file = new File(path)
        if (file.exists()) {
            def inputStream = new FileInputStream(file)
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
            response.outputStream << inputStream //Performing a binary stream copy
            inputStream.close()
            response.outputStream.close()
        } else {
            flash.message = "File not found, please remove: " + file.getName()
            redirect(controller: 'bpvTissueGrossEvaluation', action: "edit", params: [id:params.id])
        }
    }

    def remove = {
        bpvTissueGrossEvaluationService.remove(params)
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        render(template: "photoTable", bean: bpvTissueGrossEvaluationInstance, var: "bpvTissueGrossEvaluationInstance") 
    } 
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_NCI-FREDERICK_CAHUB_DM','ROLE_ADMIN']) 
    def forceSubmit = {
        def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
        bpvTissueGrossEvaluationInstance.submittedBy = "forcedby:" + username
        bpvTissueGrossEvaluationInstance.dateSubmitted = new Date()

        if (bpvTissueGrossEvaluationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.forcesubmissionsuccess.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"
            //redirect(action: "show", id: bpvTissueGrossEvaluationInstance.id)
            redirect(controller: "caseRecord", action: "display", id: bpvTissueGrossEvaluationInstance.caseRecord.id)
        }        
        else {
            bpvTissueGrossEvaluationInstance.discard()
            flash.message = "${message(code: 'default.forcesubmissionfailed.message', args: [bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueGrossEvaluationInstance.caseRecord.caseId])}"                
            redirect(controller: "caseRecord", action: "display", id: bpvTissueGrossEvaluationInstance.caseRecord.id)
        }            
    }
    
    Map checkError(bpvTissueGrossEvaluationInstance) {
        def errorMap = [:]
        boolean m1ok=true
        boolean m2ok=true
        boolean m3ok=true
        boolean m4ok=true
        boolean m5ok=true
        
        if (bpvTissueGrossEvaluationInstance.tissueReceived != null) {
            if (bpvTissueGrossEvaluationInstance.tissueReceived == 'Yes') {
                if (!bpvTissueGrossEvaluationInstance.dateTimeArrived) {
                    errorMap.put('dateTimeArrived', 'Question #1 cannot be blank')
                }
                if (!bpvTissueGrossEvaluationInstance.nameReceived) {
                    errorMap.put('nameReceived', 'Question #2 cannot be blank')
                }
                if (bpvTissueGrossEvaluationInstance.transportPerformed != 'Yes' && bpvTissueGrossEvaluationInstance.transportPerformed != 'No') {
                    errorMap.put('transportPerformed', 'Please select an option for question #4')
                }
                if (bpvTissueGrossEvaluationInstance.transportPerformed == 'No' && !bpvTissueGrossEvaluationInstance.transportComments) {
                    errorMap.put('transportComments', 'Question #4, tissue transport comments cannot be blank')
                }   
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes' && bpvTissueGrossEvaluationInstance.roomTemperature == null) {
                    errorMap.put('roomTemperature', 'Question #5 cannot be blank')
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes' && bpvTissueGrossEvaluationInstance.roomHumidity == null) {
                    errorMap.put('roomHumidity', 'Question #6 cannot be blank')
                }
                if (!bpvTissueGrossEvaluationInstance.nameEvaluated) {
                    errorMap.put('nameEvaluated', 'Question #7 cannot be blank')
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes') {
                    if (bpvTissueGrossEvaluationInstance.resectionH <= 0) {
                        errorMap.put('resectionH', 'Please specify the height of resected tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.resectionW <= 0) {
                        errorMap.put('resectionW', 'Please specify the width of resected tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.resectionD <= 0) {
                        errorMap.put('resectionD', 'Please specify the depth of resected tissue')
                    } 
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes' && bpvTissueGrossEvaluationInstance.resectionWeight <= 0) {
                    errorMap.put('resectionWeight', 'Please specify the weight of resection')
                }
                if (bpvTissueGrossEvaluationInstance.diseaseObserved != 'Yes' && bpvTissueGrossEvaluationInstance.diseaseObserved != 'No') {
                    errorMap.put('diseaseObserved', 'Please select an option for question #10')
                }
                if (!bpvTissueGrossEvaluationInstance.diagnosis) {
                    errorMap.put('diagnosis', 'Question #12 cannot be blank')
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes' && bpvTissueGrossEvaluationInstance.photoTaken != 'Yes' && bpvTissueGrossEvaluationInstance.photoTaken != 'No') {
                    errorMap.put('photoTaken', 'Please select an option for question #13')
                }
                if (bpvTissueGrossEvaluationInstance.photoTaken == 'Yes' && !bpvTissueGrossEvaluationInstance.photos) {
                    errorMap.put('photos', 'Please upload the tissue photograph(s)')
                }
                if (bpvTissueGrossEvaluationInstance.photoTaken == 'No' && !bpvTissueGrossEvaluationInstance.reasonNoPhoto) {
                    errorMap.put('reasonNoPhoto', 'Please explain why no photograph was taken')
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes' && bpvTissueGrossEvaluationInstance.inkUsed != 'Yes' && bpvTissueGrossEvaluationInstance.inkUsed != 'No') {
                    errorMap.put('inkUsed', 'Please select an option for question #14')
                }
                if (bpvTissueGrossEvaluationInstance.inkUsed == 'Yes' && !bpvTissueGrossEvaluationInstance.inkType) {
                    errorMap.put('inkType', 'Please specify the type of ink')
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased != 'Yes' && bpvTissueGrossEvaluationInstance.excessReleased != 'No') {
                    errorMap.put('excessReleased', 'Please select an option for question #15')
                }
                if (bpvTissueGrossEvaluationInstance.excessReleased == 'No' && !bpvTissueGrossEvaluationInstance.noReleaseReason) {
                    errorMap.put('noReleaseReason', 'Please specify reason why no tumor tissue was released')
                }
                
                    
                    if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes' && !bpvTissueGrossEvaluationInstance.secTissueCollect && bpvTissueGrossEvaluationInstance?.formVersion >=2) {
                        errorMap.put('secTissueCollect', 'Please specify if second piece of tissue was collected')
                    }
                    
                   if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes'  && !bpvTissueGrossEvaluationInstance.dimenMeetCriteria && bpvTissueGrossEvaluationInstance?.formVersion >=2) {
                        errorMap.put('dimenMeetCriteria', 'Please specify if dimension of each experimental piece meet criteria')
                    }

                if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes') {
                    if (bpvTissueGrossEvaluationInstance.excessH <= 0) {
                        errorMap.put('excessH', 'Please specify the height of tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.excessW <= 0) {
                        errorMap.put('excessW', 'Please specify the width of tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.excessD <= 0) {
                        errorMap.put('excessD', 'Please specify the depth of tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.areaPercentage == null) {
                        errorMap.put('areaPercentage', 'Question #18 cannot be blank')
                    }
                    if (bpvTissueGrossEvaluationInstance.contentPercentage == null) {
                        errorMap.put('contentPercentage', 'Question #19 cannot be blank')
                    }
                    if (!bpvTissueGrossEvaluationInstance.appearance) {
                        errorMap.put('appearance', 'Please select an option for question #20')
                    }
                    if (!bpvTissueGrossEvaluationInstance.timeTransferred) {
                        errorMap.put('timeTransferred', 'Question #29 cannot be blank')
                    }
                    // both first and second tissue cannot have the same modules selected
                    if(bpvTissueGrossEvaluationInstance.ftm1 && bpvTissueGrossEvaluationInstance.stm1 && bpvTissueGrossEvaluationInstance?.formVersion >=2){
                        m1ok=false
                        errorMap.put('ftm1', ' Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                        errorMap.put('stm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                    }
                    if(bpvTissueGrossEvaluationInstance.ftm2 && bpvTissueGrossEvaluationInstance.stm2){
                        m2ok=false
                        errorMap.put('ftm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                        errorMap.put('stm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                    }
                    if(bpvTissueGrossEvaluationInstance.ftm3 && bpvTissueGrossEvaluationInstance.stm3){
                        m3ok=false
                        errorMap.put('ftm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                        errorMap.put('stm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                    }
                    if(bpvTissueGrossEvaluationInstance.ftm4 && bpvTissueGrossEvaluationInstance.stm4){
                        m4ok=false
                        errorMap.put('ftm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                        errorMap.put('stm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                    }
                     if(bpvTissueGrossEvaluationInstance.ftm5 && bpvTissueGrossEvaluationInstance.stm5){
                        m5ok=false
                        errorMap.put('ftm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                        errorMap.put('stm1', 'Please select module allocation  M1, M2, M3, M4, M5 in either of the two tissues only')
                    }
                    
                    // if worksheet is submitted then modules selected here should match what is in the worksheet
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.dateSubmitted && bpvTissueGrossEvaluationInstance?.formVersion >=2){
                        if(m1ok && bpvTissueGrossEvaluationInstance.ftm1 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm1){
                            //bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module I. But module I does not exists in worksheet")
                            errorMap.put('ftm1', 'First Tissue has been allocated as Module I. But module I does not exists in worksheet')
                   
                        }
                        if(m2ok && bpvTissueGrossEvaluationInstance.ftm2 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm2){
                            //  bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module II. But module II does not exists in worksheet")
                            errorMap.put('ftm1', 'First Tissue has been allocated as Module II. But module II does not exists in worksheet')
                   
                        }
                        if(m3ok && bpvTissueGrossEvaluationInstance.ftm3 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm3){
                            //  bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module III. But Module III does not exists in worksheet")
                            errorMap.put('ftm1', 'First Tissue has been allocated as Module III. But Module III does not exists in worksheet')
                   
                        }
                        if(m4ok && bpvTissueGrossEvaluationInstance.ftm4 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm4){
                            //   bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module IV. But Module IV does not exists in worksheet")
                            errorMap.put('ftm1', 'First Tissue has been allocated as Module IV. But Module IV does not exists in worksheet')
                   
                        }
                         if(m5ok && bpvTissueGrossEvaluationInstance.ftm5 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm5){
                            //   bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module IV. But Module IV does not exists in worksheet")
                            errorMap.put('ftm1', 'First Tissue has been allocated as Module V. But Module V does not exists in worksheet')
                   
                        }
                        //if(bpvTissueGrossEvaluationInstance.ftAddlTumor && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sett){
                            //  bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated for Additionaal Tumor. But Additional Tumor does not exists in worksheet")
                          //  errorMap.put('ftm1', 'First Tissue has been allocated for Additional Tumor. But Additional Tumor does not exists in worksheet')
                   
                       // }
                    }
                    
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet && bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.formVersion==1){
                        if( bpvTissueGrossEvaluationInstance?.formVersion >=2 
                            && !bpvTissueGrossEvaluationInstance.ftm1 
                            && !bpvTissueGrossEvaluationInstance.ftm2 
                            && !bpvTissueGrossEvaluationInstance.ftm3 
                            && !bpvTissueGrossEvaluationInstance.ftm4 
                            && !bpvTissueGrossEvaluationInstance.ftAddlTumor
                            )
                        {                    

                         errorMap.put('ftm1', 'Atleast one allocation for processing is to be selected in first tissue')
                        }
                    
                    }else{
                        
                           if( bpvTissueGrossEvaluationInstance?.formVersion >=2 
                            && !bpvTissueGrossEvaluationInstance.ftm1 
                            && !bpvTissueGrossEvaluationInstance.ftm2 
                            && !bpvTissueGrossEvaluationInstance.ftm5
                            && !bpvTissueGrossEvaluationInstance.ftAddlTumor
                            )
                        {                    

                         errorMap.put('ftm1', 'Atleast one allocation for processing is to be selected in first tissue')
                        }
                    
                        
                    }
            
                }
                    
                   
               
                
                if (bpvTissueGrossEvaluationInstance.secTissueCollect == 'Yes' && bpvTissueGrossEvaluationInstance?.formVersion >=2) {
                    if (bpvTissueGrossEvaluationInstance.secTisH <= 0) {
                        errorMap.put('secTisH', 'Please specify the height of second tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.secTisW <= 0) {
                        errorMap.put('secTisW', 'Please specify the width of second tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.secTisD <= 0) {
                        errorMap.put('secTisD', 'Please specify the depth of second tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.stAreaPercentage == null) {
                        errorMap.put('stAreaPercentage', 'Question #23 cannot be blank')
                    }
                    if (bpvTissueGrossEvaluationInstance.stContentPercentage == null) {
                        errorMap.put('stContentPercentage', 'Question #24 cannot be blank')
                    }
                    if (!bpvTissueGrossEvaluationInstance.stAppearance) {
                        errorMap.put('stAppearance', 'Please select an option for question #25')
                    }
                    if (!bpvTissueGrossEvaluationInstance.timeTransferred) {
                        errorMap.put('timeTransferred', 'Question #29 cannot be blank')
                    }
                    
                    
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.dateSubmitted && bpvTissueGrossEvaluationInstance?.formVersion >=2){                     
             
                        if(m1ok && bpvTissueGrossEvaluationInstance.stm1 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm1){
                            //bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module I. But module I does not exists in worksheet")
                            errorMap.put('stm1', 'Second Tissue has been allocated as Module I. But module I does not exists in worksheet')
                   
                        }
                        if(m2ok && bpvTissueGrossEvaluationInstance.stm2 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm2){
                            //  bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module II. But module II does not exists in worksheet")
                            errorMap.put('stm1', 'stm2 Second Tissue has been allocated as Module II. But module II does not exists in worksheet')
                   
                        }
                        if(m3ok && bpvTissueGrossEvaluationInstance.stm3 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm3){
                            //  bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module III. But Module III does not exists in worksheet")
                            errorMap.put('stm1', 'Second Tissue has been allocated as Module III. But Module III does not exists in worksheet')
                   
                        }
                        if(m4ok && bpvTissueGrossEvaluationInstance.stm4 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm4){
                            //   bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module IV. But Module IV does not exists in worksheet")
                            errorMap.put('stm1', 'Second Tissue has been allocated as Module IV. But Module IV does not exists in worksheet')
                   
                        }
                         if(m5ok && bpvTissueGrossEvaluationInstance.stm5 && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sm5){
                            //   bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated as Module IV. But Module IV does not exists in worksheet")
                            errorMap.put('stm1', 'Second Tissue has been allocated as Module V. But Module V does not exists in worksheet')
                   
                        }
                        //if(bpvTissueGrossEvaluationInstance.stAddlTumor && !bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.sett){
                            //  bpvTissueGrossEvaluationInstance.errors.reject("Second Tissue has been allocated for Additionaal Tumor. But Additional Tumor does not exists in worksheet")
                          //  errorMap.put('stm1', 'Second Tissue has been allocated for Additional Tumor. But Additional Tumor does not exists in worksheet')
                   
                        //}                                 
            
                    }
                    
                    
                     if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet && bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.formVersion==1){
                            if( bpvTissueGrossEvaluationInstance?.formVersion >=2 && 
                            !bpvTissueGrossEvaluationInstance.stm1 
                            && !bpvTissueGrossEvaluationInstance.stm2 
                            && !bpvTissueGrossEvaluationInstance.stm3 
                            && !bpvTissueGrossEvaluationInstance.stm4 
                            && !bpvTissueGrossEvaluationInstance.stAddlTumor
                            )
                            {                    

                             errorMap.put('stm1', 'Atleast one allocation for processing is to be selected in second tissue ')
                            }
                    
                     }else{
                           if( bpvTissueGrossEvaluationInstance?.formVersion >=2 && 
                            !bpvTissueGrossEvaluationInstance.stm1 
                            && !bpvTissueGrossEvaluationInstance.stm2 
                            && !bpvTissueGrossEvaluationInstance.stm5  
                            && !bpvTissueGrossEvaluationInstance.stAddlTumor
                            )
                            {                    

                             errorMap.put('stm1', 'Atleast one allocation for processing is to be selected in second tissue ')
                            }
                        
                     }
                    
                    
                    
                }
                
                // checking the reverse way ( if in worksheet and not in tissue gross eval form)
                if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet?.dateSubmitted && bpvTissueGrossEvaluationInstance?.formVersion >=2){
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.m1 && !(bpvTissueGrossEvaluationInstance.ftm1 || bpvTissueGrossEvaluationInstance.stm1) ){                                               
                        bpvTissueGrossEvaluationInstance.errors.reject("errormsg", "Module I was selected on Worksheet Form, but not on Tissue Gross Evaluation Form")
                    }
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.m2 && !(bpvTissueGrossEvaluationInstance.ftm2 || bpvTissueGrossEvaluationInstance.stm2) ){                       
                        bpvTissueGrossEvaluationInstance.errors.reject("errormsg", "Module II was selected on Worksheet Form, but not on Tissue Gross Evaluation Form")
                    }
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.m3 && !(bpvTissueGrossEvaluationInstance.ftm3 || bpvTissueGrossEvaluationInstance.stm3) ){                        
                        bpvTissueGrossEvaluationInstance.errors.reject("errormsg", "Module III was selected on Worksheet Form, but not on Tissue Gross Evaluation Form")
                    }
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.m4 && !(bpvTissueGrossEvaluationInstance.ftm4 || bpvTissueGrossEvaluationInstance.stm4) ){                      
                        bpvTissueGrossEvaluationInstance.errors.reject("errormsg", "Module IV was selected on Worksheet Form, but not on Tissue Gross Evaluation Form")
                    }
                    if(bpvTissueGrossEvaluationInstance.caseRecord.bpvWorkSheet.sett && !(bpvTissueGrossEvaluationInstance.ftAddlTumor || bpvTissueGrossEvaluationInstance.stAddlTumor) ){                      
                        bpvTissueGrossEvaluationInstance.errors.reject("errormsg", "Additional Tumor was selected on Worksheet Form, but not on Tissue Gross Evaluation Form")
                    }
                
                }
                
            
                

                if (bpvTissueGrossEvaluationInstance.normalAdjReleased == 'Yes') {
                    if (bpvTissueGrossEvaluationInstance.normalAdjH <= 0) {
                        errorMap.put('normalAdjH', 'Please specify the height of Normal adjacent tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.normalAdjW <= 0) {
                        errorMap.put('normalAdjW', 'Please specify the width of Normal adjacent tumor tissue')
                    }
                    if (bpvTissueGrossEvaluationInstance.normalAdjD <= 0) {
                        errorMap.put('normalAdjD', 'Please specify the depth of Normal adjacent tumor tissue')
                    }
                }
            } else {
                if (!bpvTissueGrossEvaluationInstance.tissueNotReceivedReason) {
                    errorMap.put('tissueNotReceivedReason', 'Please explain why tissue was not received in Gross Room from OR')
                }         
            }
        } else {
            errorMap.put('tissueReceived', 'Please choose an option to state whether Tissue was received in Gross Room from OR or not')
        }    
        return errorMap
    }
    
    Map getWarningMap(bpvTissueGrossEvaluationInstance) {
        Map warningMap = [:]
        if (bpvTissueGrossEvaluationInstance.tissueReceived == 'Yes') {        
            if (bpvTissueGrossEvaluationInstance.excessReleased == 'Yes') {
                
                if ((bpvTissueGrossEvaluationInstance.areaPercentage != null)&&(bpvTissueGrossEvaluationInstance.areaPercentage > 20))
                {   
                    warningMap.put('areaPercentage', 'The value of Question #18 is greater than 20 %.')
                }
                if ((bpvTissueGrossEvaluationInstance.contentPercentage != null)&&(bpvTissueGrossEvaluationInstance.contentPercentage < 50))
                {   
                    warningMap.put('contentPercentage', 'The value of Question #19 is less than 50 %.')
                }
            }
            if (bpvTissueGrossEvaluationInstance.secTissueCollect == 'Yes') {
                
                if ((bpvTissueGrossEvaluationInstance.stAreaPercentage != null)&&(bpvTissueGrossEvaluationInstance.stAreaPercentage > 20))
                {   
                    warningMap.put('stAreaPercentage', 'The value of Question #23 is greater than 20 %.')
                }
                if ((bpvTissueGrossEvaluationInstance.stContentPercentage != null)&&(bpvTissueGrossEvaluationInstance.stContentPercentage < 50))
                {   
                    warningMap.put('stContentPercentage', 'The value of Question #24 is less than 50 %.')
                }
            }
            
            if (bpvTissueGrossEvaluationInstance.normalAdjReleased == 'Yes')
            {
                if ((bpvTissueGrossEvaluationInstance.normalAdjH > 0)&&(bpvTissueGrossEvaluationInstance.normalAdjH < 1)) 
                {
                    warningMap.put('normalAdjH', 'The value of the height of Normal adjacent tumor tissue is less than 1 cm.')
                }
                if ((bpvTissueGrossEvaluationInstance.normalAdjW > 0)&&(bpvTissueGrossEvaluationInstance.normalAdjW < 1))
                {   
                    warningMap.put('normalAdjW', 'The value of the width of Normal adjacent tumor tissue is less than 1 cm.')
                }
                if ((bpvTissueGrossEvaluationInstance.normalAdjD > 0)&&(bpvTissueGrossEvaluationInstance.normalAdjD < 1))
                {   
                    warningMap.put('normalAdjD', 'The value of the depth of Normal adjacent tumor tissue is less than 1 cm.')
                }
            }
        }   
        return warningMap
    }
    
    
    
    
}
