package nci.obbr.cahub.forms.bpv

import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.util.ComputeMethods

class BpvCaseQualityReviewController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvCaseQualityReviewInstanceList: BpvCaseQualityReview.list(params), bpvCaseQualityReviewInstanceTotal: BpvCaseQualityReview.count()]
    }

    def create = {
        def bpvCaseQualityReviewInstance = new BpvCaseQualityReview()
        bpvCaseQualityReviewInstance.properties = params
        
        //pmh 04/24/14 cdrqa:1128
        def  version53 = false
        if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '5.3') >= 0){
            version53 = true
        }            
        return [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance,version53:version53]
    }

    def save = {
        def bpvCaseQualityReviewInstance = new BpvCaseQualityReview(params)
        if (bpvCaseQualityReviewInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "editWithValidation", id: bpvCaseQualityReviewInstance.id)
        }
        else {
            render(view: "create", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance])
        }
    }

    def show = {
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        if (!bpvCaseQualityReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvCaseQualityReviewInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
            }
            //            if (!accessPrivilegeService.checkAccessPrivilege(bpvCaseQualityReviewInstance.caseRecord, session, 'view')) {
            //                redirect(controller: "login", action: "denied")
            //                return
            //            }
            
            //pmh 04/24/14 cdrqa:1128
            def  version53 = false
            if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '5.3') >= 0){
                version53 = true
            }           
            
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvCaseQualityReviewInstance.caseRecord, session, 'edit') == 0)
            [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance, canResume: canResume,version53:version53]
        }
    }

    def edit = {
        def canSubmit = 'No'
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        if (!bpvCaseQualityReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvCaseQualityReviewInstance.caseRecord, session, 'edit') 
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
            }
            if (bpvCaseQualityReviewInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvCaseQualityReviewInstance)
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            
            //pmh 04/24/14 cdrqa:1128
            def  version53 = false
            if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '5.3') >= 0){
                version53 = true
            }            
            return [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance, canSubmit: canSubmit,version53:version53]
        }
    }
    
    def editWithValidation = {
        def canSubmit = 'No'
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        if (!bpvCaseQualityReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvCaseQualityReviewInstance.caseRecord, session, 'edit') 
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
            }
            if (bpvCaseQualityReviewInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvCaseQualityReviewInstance)
            errorMap.each() {key, value ->
                bpvCaseQualityReviewInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            
            //pmh 04/24/14 cdrqa:1128
            def  version53 = false
            if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '5.3') >= 0){
                version53 = true
            }            
            render(view: "edit", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance, canSubmit: canSubmit,version53:version53])
        }
    }

    def update = {
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        if (bpvCaseQualityReviewInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvCaseQualityReviewInstance.version > version) {
                    
                    bpvCaseQualityReviewInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvCaseQualityReview while you were editing")
                    render(view: "edit", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance])
                    return
                }
            }
            bpvCaseQualityReviewInstance.properties = params
            if (!bpvCaseQualityReviewInstance.hasErrors() && bpvCaseQualityReviewInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
                redirect(action: "editWithValidation", id: bpvCaseQualityReviewInstance.id)
            }
            else {
                render(view: "edit", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    
    def submit = {
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        if (bpvCaseQualityReviewInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvCaseQualityReviewInstance.version > version) {
                    
                    bpvCaseQualityReviewInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvCaseQualityReview while you were editing")
                    render(view: "edit", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance])
                    return
                }
            }
            bpvCaseQualityReviewInstance.properties = params
            def errorMap = checkError(bpvCaseQualityReviewInstance)
            errorMap.each() {key, value ->
                bpvCaseQualityReviewInstance.errors.rejectValue(key, value)
            }
            if (!bpvCaseQualityReviewInstance.hasErrors() && bpvCaseQualityReviewInstance.save(flush: true)) {
                bpvCaseQualityReviewInstance.dateSubmitted = new Date()
                bpvCaseQualityReviewInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                flash.message = "${message(code: 'default.submitted.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
                //redirect(action: "show", id: bpvCaseQualityReviewInstance.id)
                redirect(controller: "caseRecord", action: "display", id: bpvCaseQualityReviewInstance.caseRecord.id)
            }
            else {
                bpvCaseQualityReviewInstance.discard()
                render(view: "edit", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        if (bpvCaseQualityReviewInstance) {
            try {
                bpvCaseQualityReviewInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName + ' for Case', bpvCaseQualityReviewInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    def resumeEditing = {
        def bpvCaseQualityReviewInstance = BpvCaseQualityReview.get(params.id)
        bpvCaseQualityReviewInstance.dateSubmitted = null
        bpvCaseQualityReviewInstance.submittedBy = null
        if (bpvCaseQualityReviewInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvCaseQualityReviewInstance.id)
        }
        else {
            render(view: "show", model: [bpvCaseQualityReviewInstance: bpvCaseQualityReviewInstance])
        }
    }
    
    Map checkError(bpvCaseQualityReviewInstance) {
        def errorMap = [:]
        def chkVer=false 
        // println "bpvCaseQualityReviewInstance.caseRecord.cdrVer "+bpvCaseQualityReviewInstance.caseRecord.cdrVer
        if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '4.6') >= 0){
            chkVer=true
        }
        
        def chkVer6=false
        if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '6.0') >= 0){
            chkVer6=true
        }
        
        def chkVer61=false
        if(ComputeMethods.compareCDRVersion(bpvCaseQualityReviewInstance.caseRecord.cdrVer, '6.1') >= 0){
            chkVer61=true
        }
        
        if (bpvCaseQualityReviewInstance.consent != 'Yes' && bpvCaseQualityReviewInstance.consent != 'No') {
            errorMap.put('consent', 'Please select an option for question #1')
        }
        if (bpvCaseQualityReviewInstance.tubes != 'Yes' && bpvCaseQualityReviewInstance.tubes != 'No') {
            errorMap.put('tubes', 'Please select an option for question #2')
        }
        if (bpvCaseQualityReviewInstance.plasmaAliquots != 'Yes' && bpvCaseQualityReviewInstance.plasmaAliquots != 'No') {
            errorMap.put('plasmaAliquots', 'Please select an option for question #3')
        }
        if (bpvCaseQualityReviewInstance.tumorModule != 'Yes' && bpvCaseQualityReviewInstance.tumorModule != 'No') {
            errorMap.put('tumorModule', 'Please select an option for question #4')
        }
        if (bpvCaseQualityReviewInstance.additionalModule != 'Yes' && bpvCaseQualityReviewInstance.additionalModule != 'No') {
            errorMap.put('additionalModule', 'Please select an option for question #5')
        }
        if (bpvCaseQualityReviewInstance.slideReview != 'Yes' && bpvCaseQualityReviewInstance.slideReview != 'No') {
            errorMap.put('slideReview', 'Please select an option for question #6a')
        }
        if(bpvCaseQualityReviewInstance.slideReview == 'Yes'){
            
            if (chkVer && bpvCaseQualityReviewInstance.sldRevConfirmElig != 'Yes' && bpvCaseQualityReviewInstance.sldRevConfirmElig != 'No') {
                errorMap.put('sldRevConfirmElig', 'Please select an option for question #6b')
            }
            if (bpvCaseQualityReviewInstance.reviewConsistent != 'Yes' && bpvCaseQualityReviewInstance.reviewConsistent != 'No') {
                errorMap.put('reviewConsistent', 'Please select an option for question #7')
            }
            if (bpvCaseQualityReviewInstance.projectCriteria != 'Yes' && bpvCaseQualityReviewInstance.projectCriteria != 'No') {
                errorMap.put('projectCriteria', 'Please select an option for question #9')
            }
        }
        
        if (bpvCaseQualityReviewInstance.clinicalData != 'Yes' && bpvCaseQualityReviewInstance.clinicalData != 'No') {
            errorMap.put('clinicalData', 'Please select an option for question #8')
        }
        
        if (bpvCaseQualityReviewInstance.requirements != 'Yes' && bpvCaseQualityReviewInstance.requirements != 'No') {
            errorMap.put('requirements', 'Please select an option for question #10')
        }
        if (bpvCaseQualityReviewInstance.wasStopped != 'Yes' && bpvCaseQualityReviewInstance.wasStopped != 'No') {
            errorMap.put('wasStopped', 'Please select an option for question #11')
        }
        if (bpvCaseQualityReviewInstance.wasStopped == 'Yes' && !bpvCaseQualityReviewInstance.reasonStopped) {
            errorMap.put('reasonStopped', 'Please select an option for reason case was stopped')
        }
        if (bpvCaseQualityReviewInstance.reasonStopped == 'Other, specify' && !bpvCaseQualityReviewInstance.otherReason) {
            errorMap.put('otherReason', 'Please specify other reason')
        }
        
        if (chkVer6 && bpvCaseQualityReviewInstance.normalAdjCollected != 'Yes' && bpvCaseQualityReviewInstance.normalAdjCollected != 'No') {
            errorMap.put('normalAdjCollected', 'Please select an option for question #5a')
        }
        if (chkVer6 && bpvCaseQualityReviewInstance.addlTumorCollected != 'Yes' && bpvCaseQualityReviewInstance.addlTumorCollected != 'No') {
            errorMap.put('addlTumorCollected', 'Please select an option for question #5b')
        }
        
        if (bpvCaseQualityReviewInstance.requirements == 'Yes' && chkVer61 && !bpvCaseQualityReviewInstance.whatWasShipped ) {
            errorMap.put('whatWasShipped', 'Please select what was shipped ')
        }
        
        return errorMap
    }
}
