package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.util.ComputeMethods

class BpvLocalPathReviewController {
    
    static allowedMethods = [update: "POST", delete: "POST"]
    
    def bpvLocalPathReviewService
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bpvLocalPathReviewInstanceList: BpvLocalPathReview.list(params), bpvLocalPathReviewInstanceTotal: BpvLocalPathReview.count()]
    }

    def create = {
        def bpvLocalPathReviewInstance = new BpvLocalPathReview()
        bpvLocalPathReviewInstance.properties = params
        params.later53 = 'yes'
        return [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance]
    }

    def save = {
        def bpvLocalPathReviewInstance = new BpvLocalPathReview(params) 
        try{
            // Populate SOP Record
            def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
            def sopInstance
            if (formMetadataInstance?.sops?.size() > 0) {
                sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
                bpvLocalPathReviewInstance.formSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
            }
            
            bpvLocalPathReviewService.createForm(bpvLocalPathReviewInstance) 
            flash.message = "${message(code: 'default.created.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "edit", id: bpvLocalPathReviewInstance.id)
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
            redirect(controller: "caseRecord", action: "display", params: [id: bpvLocalPathReviewInstance.caseRecord.id])
        } 
    }
    
    def upload = {
        def caseRecordInstance = CaseRecord.get(params.id)
        return [caseRecordInstance: caseRecordInstance]
    }
    
    def upload_save ={
        def caseRecordInstance = CaseRecord.get(params.id)
        def uploadedFile = request.getFile("filepath")
        def originalFilename = uploadedFile.originalFilename.toLowerCase()
        if (!uploadedFile.empty) {
            if (!originalFilename.endsWith('.pdf') &&
                !originalFilename.endsWith('.doc') &&
                !originalFilename.endsWith('.docx')) {
                
                caseRecordInstance.errors.reject("error", "You can only upload a pdf, doc or docx file type. Please upload the right file.")
                render(view: "upload", model: [caseRecordInstance:caseRecordInstance])
            } else {
                if (params.version) {
                    def version = params.version.toLong()
                    if (caseRecordInstance.version > version) {    
                        caseRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseRecord.label', default: 'CaseRecord')] as Object[], "Another user has updated this CaseRecord while you were editing")
                        render(view: "upload", model: [caseRecordInstance:caseRecordInstance])
                        return
                    }
                }
                def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                bpvLocalPathReviewService.upload(params, uploadedFile, username)
                flash.message = "${message(code: 'default.uploaded.message', args: [FormMetadata.findByCode('FINAL')?.cdrFormName + ' for Case', caseRecordInstance.caseId])}"
                redirect(controller: 'caseRecord', action: "display", params: [id:caseRecordInstance.id])
            }
        } else {
            caseRecordInstance.errors.reject("error", "Please choose a file")
            render(view: "upload", model: [caseRecordInstance:caseRecordInstance])
        }
    }
   
    def download = {
        def caseRecordInstance = CaseRecord.get(params.id)
        def convertedFilePath = caseRecordInstance.finalSurgicalPath
        def file = new File(convertedFilePath)
        if (file.exists()) {
            def inputStream = new FileInputStream(file)
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
            response.outputStream << inputStream //Performing a binary stream copy
            inputStream.close()
            response.outputStream.close()
        } else {
            flash.message = "File not found, please remove Final Surgical Pathology Report"
            redirect(controller: 'caseRecord', action: "display", params: [id:params.id])
        }
    }

    def remove = {
        bpvLocalPathReviewService.remove(params)
        redirect(controller: 'caseRecord', action: "display", params: [id: params.id])
    }  
    
    def show = {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        if (!bpvLocalPathReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
        else {
            [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance]
        }
    }

    def edit = {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        
        def errorMap = [:]
        def canReview = false
        if (!bpvLocalPathReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvLocalPathReviewInstance.caseRecord, session, 'edit') 
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvLocalPathReviewInstance.status == 'Reviewed') {
                redirect(controller: "login", action: "denied")
                return
            }
            
            if (isStarted(bpvLocalPathReviewInstance)) {
                def result = checkError(bpvLocalPathReviewInstance)
                if (result) {
                    result.each() { key, value ->
                        bpvLocalPathReviewInstance.errors.reject(value, value)
                        errorMap.put(key, "errors")
                    } //each
                } else {
                    canReview = true
                }
            }
            def hisList = bpvLocalPathReviewService.retrieveHisList(bpvLocalPathReviewInstance) 
            return [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance, errorMap:errorMap, canReview:canReview, hisList:hisList]
        }
    }

    def update = {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        
        if (bpvLocalPathReviewInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvLocalPathReviewInstance.version > version) {    
                    bpvLocalPathReviewInstance.errors.rejectValue("version", "default.optimistic.locking.failure", ['Pathology Review Form'] as Object[], "Another user has updated this BpvLocalPathReview while you were editing")
                    def hisList = bpvLocalPathReviewService.retrieveHisList(bpvLocalPathReviewInstance) 
                    render(view: "edit", model: [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance, hisList:hisList] )
                    return
                }
            }
            bpvLocalPathReviewService.saveForm(params)
            flash.message = "${message(code: 'default.updated.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance.slideRecord.slideId])}"
            redirect(action: "edit", params: [id: bpvLocalPathReviewInstance.id])
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "edit", params: [id:bpvLocalPathReviewInstance.id])
        }
    }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        if (bpvLocalPathReviewInstance) {
            try {
                bpvLocalPathReviewInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
    }
//    static Map checkError(bpvLocalPathReviewInstance) {
//         return checkError(bpvLocalPathReviewInstance, true)
//     }                                
    static Map checkError(bpvLocalPathReviewInstance) {
        
        
        def result = [:]

        if (!bpvLocalPathReviewInstance?.organOrigin) {
            result.put("organOrigin", "The organ of origin is a required field")
        }
         
        if (bpvLocalPathReviewInstance?.organOrigin == 'Other' && !bpvLocalPathReviewInstance.otherOrganOrigin) {
            result.put("otherOrganOrigin", "The other organ of origin is a required field")
        }
        
        if (!bpvLocalPathReviewInstance?.histologicType)
            result.put("needHis", "Please select histologic type")
            
        if (bpvLocalPathReviewInstance?.histologicType?.code == 'C7' && !bpvLocalPathReviewInstance?.otherHistologicType)
            result.put("detail_C7", "Please specify histologic types and %")
            
        if (bpvLocalPathReviewInstance?.histologicType?.code == 'C8' && !bpvLocalPathReviewInstance?.otherHistologicType)
            result.put("detail_C8", "Please specify histologic types and %")
            
        if (bpvLocalPathReviewInstance?.histologicType?.code == 'C9' && !bpvLocalPathReviewInstance?.otherHistologicType)
            result.put("detail_C9", "Please specify histologic types and %")
            
        if (bpvLocalPathReviewInstance?.histologicType?.code == 'C20' && !bpvLocalPathReviewInstance?.otherHistologicType)
            result.put("detail_C20", "Please specify histologic type")
            
        if (bpvLocalPathReviewInstance?.histologicType?.code == 'OTHER' && !bpvLocalPathReviewInstance?.otherHistologicType)
            result.put("detail_other", "Please specify histologic type")
            
        if (bpvLocalPathReviewInstance?.histologicType?.code == 'C78' && !bpvLocalPathReviewInstance?.otherHistologicType)
            result.put("detail_C78", "Please specify histologic types and %")
            
        if (bpvLocalPathReviewInstance?.category=='Kidney'){
            if(!bpvLocalPathReviewInstance?.sarcomatoid){
                result.put("sarcomatoid", "The presence of sarcomatoid features is a required field") 
            }
        }
        
        if(bpvLocalPathReviewInstance?.sarcomatoid == 'Present' && !bpvLocalPathReviewInstance?.sarcomatoidDesc)
            result.put("sarcomatoidDesc", "Describe sarcomatoid features")

        if (bpvLocalPathReviewInstance?.tumorDimension == null)
            result.put("tumorDimension", "The greatest tumor dimension on slide is a required field")
             
        if (bpvLocalPathReviewInstance?.pctTumorArea == null)
            result.put("pctTumorArea", "The percent of cross-sectional surface area composed of tumor focus is a required field")
         
        if (bpvLocalPathReviewInstance?.pctTumorCellularity == null)
            result.put("pctTumorCellularity", "The percent of tumor cellularity by cell count of the entire slide is a required field")

        if (ComputeMethods.compareCDRVersion(bpvLocalPathReviewInstance.caseRecord.cdrVer, '5.3') >= 0)
        {
            if (bpvLocalPathReviewInstance?.pctNecroticTissue == null)
                result.put("pctNecroticTissue", "The percent of necrotic tissue by surface area of the entire slide is a required field")
        }
           
        if (bpvLocalPathReviewInstance?.pctViablTumor == null)
            result.put("pctViablTumor", "The percent viable tumor by surface area is a required field")
          
        if (bpvLocalPathReviewInstance?.pctNecroticTumor == null)
            result.put("pctNecroticTumor", "The percent necrotic tumor by surface area is a required field")
             
        if (bpvLocalPathReviewInstance?.pctViableNonTumor == null)
        {
            if (ComputeMethods.compareCDRVersion(bpvLocalPathReviewInstance.caseRecord.cdrVer, '5.3') >= 0)
                result.put("pctViableNonTumor", "The percent tumor stroma by surface area is a required field")
            else 
                result.put("pctViableNonTumor", "The percent viable non-tumor tissue by surface area is a required field")
        }
                    
        if (bpvLocalPathReviewInstance?.pctNonCellular == null)
            result.put("pctNonCellular", "The percent non-cellular component by surface area is a required field")
            
        if (bpvLocalPathReviewInstance?.pctNonCellular > 0 && !bpvLocalPathReviewInstance.nonCellularDesc)
            result.put("nonCellularDesc", "Please describe non-cellular component")
            
        if (bpvLocalPathReviewInstance?.hisTotal != 100.0)
            result.put("hisTotal", "Histologic Profile Total value should be 100%")

        if (!bpvLocalPathReviewInstance?.gradingSystem)
            result.put("gradingSystem", "The histologic grading system is a required field")

        if (bpvLocalPathReviewInstance?.gradingSystem){
            if(!bpvLocalPathReviewInstance?.grade)
                result.put("grade", "The histologic grade is a required field")
        }

        if (!bpvLocalPathReviewInstance?.pathTumor)
            result.put("pathTumor", "The pathologic spread primary tumor is a required field")

        if (!bpvLocalPathReviewInstance?.pathNodes)
            result.put("pathNodes", "The pathologic spread lymph nodes is a required field")

        if (!bpvLocalPathReviewInstance?.pathMetastases)
        {
            if (ComputeMethods.compareCDRVersion(bpvLocalPathReviewInstance.caseRecord.cdrVer, '5.3') >= 0)
                result.put("pathMetastases", "The distant metastases is a required field")
            else
                result.put("pathMetastases", "The pathologic spread distant metastases is a required field")
        }
        
        if (!bpvLocalPathReviewInstance?.pathStage)
            result.put("pathStage", "The pathologic tumor stage grouping is a required field")
            
         if (ComputeMethods.compareCDRVersion(bpvLocalPathReviewInstance.caseRecord.cdrVer, '5.5') >= 0){
               if (!bpvLocalPathReviewInstance?.histoEligible){
                         result.put("histoEligible", "Please specify if pathology review of the H&E slide derived from QC FFPE tumor tissue confirms the histological type to be eligible for BPV study") 
               }
         }
        if (!bpvLocalPathReviewInstance?.meetsCriteria)
            result.put("meetsCriteria", "Please specify if this slide meets the microscopic analysis criteria") 

        if (bpvLocalPathReviewInstance?.meetsCriteria == 'No' && !bpvLocalPathReviewInstance?.reasonNotMeet)
            result.put("reasonNotMeet", "Specify what findings do not meet the microscopic analysis criteria of the BPV project")
           
        if (!bpvLocalPathReviewInstance?.consistentLocalPrc)
            result.put("consistentLocalPrc", "Please specify if this slide is consistent with the findings of the Diagnostic Pathology Report for this case") 
        
        if (bpvLocalPathReviewInstance?.consistentLocalPrc == 'No' && !bpvLocalPathReviewInstance?.reasonNotCons)
            result.put("reasonNotCons", "Specify what findings are not consistent with those of the Diagnostic Pathology Report for this case")
            
        if (!bpvLocalPathReviewInstance?.pathologistName)
            result.put("pathologistName", "Name of local BSS reviewing pathologist is a required field")
            
        if (!bpvLocalPathReviewInstance?.dateSlideReview)
            result.put("dateSlideReview", "Date of Slide review by the pathologist is a required field")
        
        if (!bpvLocalPathReviewInstance?.dataEnteredBy)
            result.put("dataEnteredBy", "Data entry in the local pathology review form was performed by is a required field")

        return result
    }
     
    static boolean isStarted(bpvLocalPathReviewInstance) {
        def result = false
        
        if(bpvLocalPathReviewInstance.version > 0)
            return true

        return result                                  
    } 
    
    def view = {
        
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvLocalPathReviewInstance.caseRecord, session, 'view') 
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(bpvLocalPathReviewInstance.caseRecord, session, 'view')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
        
        def hisList = bpvLocalPathReviewService.retrieveHisList(bpvLocalPathReviewInstance) 
        def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvLocalPathReviewInstance.caseRecord, session, 'edit') == 0)
        return [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance, hisList:hisList, canResume: canResume]
    }
    
    def review = {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        
        def errorMap = [:]
        def canReview = false
       
        try {
            def result = checkError(bpvLocalPathReviewInstance)
            if (result) {
                result.each() {key, value ->   
                    bpvLocalPathReviewInstance.errors.reject(value, value)
                    errorMap.put(key, "errors")
                } //each
                flash.message="failed to submit"
                def hisList = bpvLocalPathReviewService.retrieveHisList(bpvLocalPathReviewInstance) 
                render(view: "edit", model: [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance,  errorMap:errorMap, canReview:canReview, hisList:hisList] )
            } else {
                def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                bpvLocalPathReviewService.reviewForm(bpvLocalPathReviewInstance, username)
                flash.message = "${message(code: 'default.submitted.message', args: ['Pathology Review Form' + ' for Slide', bpvLocalPathReviewInstance.slideRecord.slideId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvLocalPathReviewInstance.caseRecord.id)   
            }
        } catch (Exception e) {
            flash.message = "Failed: " + e.toString()  
            redirect(action: "edit", params: [id: bpvLocalPathReviewInstance.id])     
        }
    }
    
    def reedit = {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        def errorMap = [:]
        def canReview = false
       
        try {
            //prcReportService.submitReport(prcReportInstance)
            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            bpvLocalPathReviewService.reEdit(bpvLocalPathReviewInstance)
            def hisList = bpvLocalPathReviewService.retrieveHisList(bpvLocalPathReviewInstance) 
            render(view: "edit", model: [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance,  errorMap:errorMap, canReview:canReview, hisList:hisList] )
        } catch (Exception e) {
            flash.message = "Failed: " + e.toString()  
            //redirect(action: "edit", params: [id: bpvLocalPathReviewInstance.id])
            render(view: "show", model: [bpvLocalPathReviewInstance: bpvLocalPathReviewInstance])
        }        
    }    
}
