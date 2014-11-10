package nci.obbr.cahub.prc.bpv

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.FormMetadata
import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.util.ComputeMethods

class BpvPrcPathReviewController {
    
    def bpvPrcPathReviewService
    
    static allowedMethods = [update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bpvPrcPathReviewInstanceList: BpvPrcPathReview.list(params), bpvPrcPathReviewInstanceTotal: BpvPrcPathReview.count()]
    }

    def create = {
        def bpvPrcPathReviewInstance = new BpvPrcPathReview()
        bpvPrcPathReviewInstance.properties = params
        return [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance]
    }

    def save = {
        def bpvPrcPathReviewInstance = new BpvPrcPathReview(params) 
        try{   
            bpvPrcPathReviewService.createForm(bpvPrcPathReviewInstance) 
            flash.message = "${message(code: 'default.created.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "edit", id: bpvPrcPathReviewInstance.id)
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
            redirect(controller: "home", action: "prchome") 
        } 
    }
    
    def show = {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        if (!bpvPrcPathReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
        else {
            [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance]
        }
    }

    def edit = {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        def errorMap = [:]
        def canReview = false
        if (!bpvPrcPathReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
        else {
            if (bpvPrcPathReviewInstance.status == 'Reviewed') {
                redirect(controller: "login", action: "denied")
                return
            }
            // Add review for the new experimental samples
            bpvPrcPathReviewService.addNewReview(bpvPrcPathReviewInstance)
            
            if (isStarted(bpvPrcPathReviewInstance)) {
                def result = checkError(bpvPrcPathReviewInstance)
                if (result) {
                    result.each() { key, value ->
                        bpvPrcPathReviewInstance.errors.reject(value, value)
                        errorMap.put(key, "errors")
                    } //each
                } else {
                    canReview = true
                }
            }
            def hisList = bpvPrcPathReviewService.retrieveHisList(bpvPrcPathReviewInstance) 
            return [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance, errorMap:errorMap, canReview:canReview, hisList:hisList]
        }
    }

    def update = {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        if (bpvPrcPathReviewInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvPrcPathReviewInstance.version > version) {    
                    bpvPrcPathReviewInstance.errors.rejectValue("version", "default.optimistic.locking.failure", ['Pathology Review Form'] as Object[], "Another user has updated this BpvPrcPathReview while you were editing")
                    def hisList = bpvPrcPathReviewService.retrieveHisList(bpvPrcPathReviewInstance)
                    render(view: "edit", model: [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance, hisList:hisList] )
                    return
                }
            }
            bpvPrcPathReviewService.saveForm(params)
            flash.message = "${message(code: 'default.updated.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance.slideRecord.slideId])}"
            redirect(action: "edit", params: [id: bpvPrcPathReviewInstance.id])
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "edit", params: [id:bpvPrcPathReviewInstance.id])
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        if (bpvPrcPathReviewInstance) {
            try {
                bpvPrcPathReviewInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
    }
                                    
    static Map checkError(bpvPrcPathReviewInstance) {
        
        
        def result = [:]
 
        if (!bpvPrcPathReviewInstance?.organOrigin) {
            result.put("organOrigin", "The organ of origin is a required field")
        }
         
        if (bpvPrcPathReviewInstance?.organOrigin == 'Other' && !bpvPrcPathReviewInstance.otherOrganOrigin) {
            result.put("otherOrganOrigin", "The other organ of origin is a required field")
        }
        
        if (!bpvPrcPathReviewInstance?.histologicType)
            result.put("needHis", "Please select histologic type")
            
        if (bpvPrcPathReviewInstance?.histologicType?.code == 'C7' && !bpvPrcPathReviewInstance?.otherHistologicType)
            result.put("detail_C7", "Please specify histologic types and %")
            
        if (bpvPrcPathReviewInstance?.histologicType?.code == 'C8' && !bpvPrcPathReviewInstance?.otherHistologicType)
            result.put("detail_C8", "Please specify histologic types and %")
            
        if (bpvPrcPathReviewInstance?.histologicType?.code == 'C9' && !bpvPrcPathReviewInstance?.otherHistologicType)
            result.put("detail_C9", "Please specify histologic types and %")
            
        if (bpvPrcPathReviewInstance?.histologicType?.code == 'C20' && !bpvPrcPathReviewInstance?.otherHistologicType)
            result.put("detail_C20", "Please specify histologic type")
            
        if (bpvPrcPathReviewInstance?.histologicType?.code == 'OTHER' && !bpvPrcPathReviewInstance?.otherHistologicType)
            result.put("detail_other", "Please specify histologic type")
            
        if (bpvPrcPathReviewInstance?.histologicType?.code == 'C78' && !bpvPrcPathReviewInstance?.otherHistologicType)
            result.put("detail_C78", "Please specify histologic types and %")
            
        if (bpvPrcPathReviewInstance?.category=='Kidney'){
            if(!bpvPrcPathReviewInstance?.sarcomatoid){
                result.put("sarcomatoid", "The presence of sarcomatoid features is a required field") 
            }
        }
        
        if (bpvPrcPathReviewInstance?.sarcomatoid == 'Present' && !bpvPrcPathReviewInstance?.sarcomatoidDesc)
            result.put("sarcomatoidDesc", "Describe sarcomatoid features")
         
        if (bpvPrcPathReviewInstance?.tumorDimension == null)
            result.put("tumorDimension", "The greatest tumor dimension on slide is a required field")
             
        if (bpvPrcPathReviewInstance?.pctTumorArea == null)
            result.put("pctTumorArea", "The percent of cross-sectional surface area composed of tumor focus is a required field")
         
        if (bpvPrcPathReviewInstance?.pctTumorCellularity == null)
            result.put("pctTumorCellularity", "The percent of tumor cellularity by cell count of the entire slide is a required field")

        
        if(ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance.caseRecord.cdrVer, '5.3') >= 0)
        {
            if (bpvPrcPathReviewInstance?.pctNecroticTissue == null)
                result.put("pctNecroticTissue", "The percent of necrotic tissue by surface area of the entire slide is a required field")
        }
        
        if (bpvPrcPathReviewInstance?.pctViablTumor == null)
            result.put("pctViablTumor", "The percent viable tumor by surface area is a required field")
          
        if (bpvPrcPathReviewInstance?.pctNecroticTumor == null)
            result.put("pctNecroticTumor", "The percent necrotic tumor by surface area is a required field")
             
        //if (bpvPrcPathReviewInstance?.pctViableNonTumor == null)
        //    result.put("pctViableNonTumor", "The percent viable non-tumor tissue by surface area is a required field")
        if (bpvPrcPathReviewInstance?.pctViableNonTumor == null)
        {
            if (ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance.caseRecord.cdrVer, '5.3') >= 0)
                result.put("pctViableNonTumor", "The percent tumor stroma by surface area is a required field")
            else 
                result.put("pctViableNonTumor", "The percent viable non-tumor tissue by surface area is a required field")
        }
             
        if (bpvPrcPathReviewInstance?.pctNonCellular == null)
            result.put("pctNonCellular", "The percent non-cellular component by surface area is a required field")
            
        if (bpvPrcPathReviewInstance?.hisTotal != 100.0)
            result.put("hisTotal", "Histologic Profile Total value should be 100%")
            
        if (!bpvPrcPathReviewInstance?.gradingSystem)
            result.put("gradingSystem", "The histologic grading system is a required field")

        if (bpvPrcPathReviewInstance?.gradingSystem){
            if(!bpvPrcPathReviewInstance?.grade)
                result.put("grade", "The histologic grade is a required field")
        }
        
         if (!bpvPrcPathReviewInstance?.histoEligible)
            result.put("histoEligible", "Please specify if pathology review of the H&E slide derived from QC FFPE tumor tissue confirmed the histological type to be eligible for BPV study") 
            

        if (!bpvPrcPathReviewInstance?.meetsCriteria)
            result.put("meetsCriteria", "Please specify if this slide meets the microscopic analysis criteria") 

        if (bpvPrcPathReviewInstance?.meetsCriteria == 'No' && !bpvPrcPathReviewInstance?.reasonNotMeet)
            result.put("reasonNotMeet", "Please specify what findings do not meet the microscopic analysis criteria of the BPV project")
           
        if (!bpvPrcPathReviewInstance?.consistentLocalPrc) {
            if (ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.0') >= 0) {
                result.put("consistentLocalPrc", "Please specify if this slide is consistent with the findings of the local BSS pathologist")
            } else {
                result.put("consistentLocalPrc", "Please specify if this slide is consistent with the findings of the Diagnostic Pathology Report for this case")
            }
        }
        
        if (bpvPrcPathReviewInstance?.consistentLocalPrc == 'No' && !bpvPrcPathReviewInstance?.reasonNotCons) {
            if (ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.0') >= 0) {
                result.put("reasonNotCons", "Please specify what findings are not consistent with those of the local BSS pathologist")
            } else {
                result.put("reasonNotCons", "Please specify what findings are not consistent with those of the Diagnostic Pathology Report for this case")
            }
        }

        for (i in bpvPrcPathReviewInstance?.bpvPrcExpSampleReviews) {
            if (!i.sameOriginType)
                result.put("sameOriginType_" + i.id, "Please specify if the organ of origin and histologic type of the slide " + i.slideRecord?.slideId + " are the same as the QC section")
                
            if (!i.meetsCriteria)
                result.put("meetsCriteria_" + i.id, "Please specify if the slide " + i.slideRecord?.slideId + " meets the microscopic analysis criteria")
                
            if ((i.sameOriginType == 'No' || i.meetsCriteria == 'No') && !i.reasonNotMeet)
                result.put("reasonNotMeet_" + i.id, "Please provide comments for the slide " + i.slideRecord?.slideId + " if No is selected for either of the two questions") 
                
            if(ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance.caseRecord.cdrVer, '5.4.1') >= 0){
                if (!i.percTumor)
                result.put("percTumor_" + i.id, "Please specify the percent tumor nuclei for slide " + i.slideRecord?.slideId)
                
                
            }
        }

        return result
    }
     
    static boolean isStarted(bpvPrcPathReviewInstance) {
        def result = false
        
        if(bpvPrcPathReviewInstance.version > 0)
            return true 
        
        return result                                  
    }
      
    
    def view = {
        //params.each() {key, value ->
        //    println("in view, key: " + key + "value: " + value)
        //}
        
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        
        if (!bpvPrcPathReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance?.slideRecord?.slideId])}"
            redirect(action: "list")
        }
        
        def hisList = bpvPrcPathReviewService.retrieveHisList(bpvPrcPathReviewInstance) 
        return [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance, hisList:hisList]
    }
    
    
    def review = {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        def errorMap = [:]
        def canReview = false
       
        try {
            def result = checkError(bpvPrcPathReviewInstance)
            if (result) {
                result.each() {key, value ->   
                    bpvPrcPathReviewInstance.errors.reject(value, value)
                    errorMap.put(key, "errors")
                } //each
                flash.message="failed to submit"
                def hisList = bpvPrcPathReviewService.retrieveHisList(bpvPrcPathReviewInstance)
                render(view: "edit", model: [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance,  errorMap:errorMap, canReview:canReview, hisList:hisList] )
            } else {
                def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                bpvPrcPathReviewService.reviewForm(bpvPrcPathReviewInstance, username)
                flash.message = "${message(code: 'default.submitted.message', args: ['Pathology Review Form' + ' for Slide', bpvPrcPathReviewInstance.slideRecord.slideId])}"
                redirect(controller: "home", action: "prchome")  
            }
        } catch (Exception e) {
            flash.message = "Failed: " + e.toString()  
            redirect(action: "edit", params: [id: bpvPrcPathReviewInstance.id])     
        }
    }
    
    
    def reedit = {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        def errorMap = [:]
        def canReview = false
       
        try {
            //prcReportService.submitReport(prcReportInstance)
            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            bpvPrcPathReviewService.reEdit(bpvPrcPathReviewInstance)
            def hisList = bpvPrcPathReviewService.retrieveHisList(bpvPrcPathReviewInstance)
            //render(view: "edit", model: [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance,  errorMap:errorMap, canReview:canReview, hisList:hisList] )
            redirect(action: "edit", params: [id: bpvPrcPathReviewInstance.id])
        } catch (Exception e) {
            flash.message = "Failed: " + e.toString()  
            //redirect(action: "show", params: [id: bpvPrcPathReviewInstance.id])
            render(view: "show", model: [bpvPrcPathReviewInstance: bpvPrcPathReviewInstance])
        }        
    }    
}

