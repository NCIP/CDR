package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.plugins.springsecurity.Secured 

class BpvScreeningEnrollmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvScreeningEnrollmentInstanceList: BpvScreeningEnrollment.list(params), bpvScreeningEnrollmentInstanceTotal: BpvScreeningEnrollment.count()]
    }

    def create = {
        def bpvScreeningEnrollmentInstance = new BpvScreeningEnrollment()
        bpvScreeningEnrollmentInstance.properties = params
        return [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance]
    }

    def save = {
        def bpvScreeningEnrollmentInstance = new BpvScreeningEnrollment(params)
        
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvScreeningEnrollmentInstance.formSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }
        
        if (bpvScreeningEnrollmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "editWithValidation", id: bpvScreeningEnrollmentInstance.id)
        }
        else {
            render(view: "create", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance])
        }
    }

    def show = {
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        if (!bpvScreeningEnrollmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(bpvScreeningEnrollmentInstance.candidateRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
//            if (!accessPrivilegeService.checkAccessPrivilegeCandidate(bpvScreeningEnrollmentInstance.candidateRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            def canResume = (accessPrivilegeService.checkAccessPrivilegeCandidate(bpvScreeningEnrollmentInstance.candidateRecord, session, 'edit') == 0)
            [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance, canResume: canResume]
        }
    }

    def edit = {
        def canSubmit = 'No'
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        if (!bpvScreeningEnrollmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(bpvScreeningEnrollmentInstance.candidateRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            if (bpvScreeningEnrollmentInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvScreeningEnrollmentInstance)
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            return [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance, canSubmit: canSubmit]
        }
    }
    
    def editWithValidation = {
        def canSubmit = 'No'
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        if (!bpvScreeningEnrollmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(bpvScreeningEnrollmentInstance.candidateRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            if (bpvScreeningEnrollmentInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvScreeningEnrollmentInstance)
            errorMap.each() {key, value ->
                bpvScreeningEnrollmentInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            render(view: "edit", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance, canSubmit: canSubmit])
        }
    }

    def update = {
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        if (bpvScreeningEnrollmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvScreeningEnrollmentInstance.version > version) {
                    
                    bpvScreeningEnrollmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvScreeningEnrollment while you were editing")
                    render(view: "edit", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance])
                    return
                }
            }
            bpvScreeningEnrollmentInstance.properties = params
            if (!bpvScreeningEnrollmentInstance.hasErrors() && bpvScreeningEnrollmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
                redirect(action: "editWithValidation", id: bpvScreeningEnrollmentInstance.id)
            }
            else {
                render(view: "edit", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
    
    def submit = {
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        if (bpvScreeningEnrollmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvScreeningEnrollmentInstance.version > version) {
                    
                    bpvScreeningEnrollmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvScreeningEnrollment while you were editing")
                    render(view: "edit", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance])
                    return
                }
            }
            bpvScreeningEnrollmentInstance.properties = params
            bpvScreeningEnrollmentInstance.dateSubmitted = new Date()
            bpvScreeningEnrollmentInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def errorMap = checkError(bpvScreeningEnrollmentInstance)
            errorMap.each() {key, value ->
                bpvScreeningEnrollmentInstance.errors.rejectValue(key, value)
            }
            if (!bpvScreeningEnrollmentInstance.hasErrors() && bpvScreeningEnrollmentInstance.save(flush: true)) {
                setIsEligibleInCandidateRecord(bpvScreeningEnrollmentInstance)
                flash.message = "${message(code: 'default.submitted.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
                //redirect(action: "show", id: bpvScreeningEnrollmentInstance.id)
                redirect(controller: "candidateRecord", action: "view", id: bpvScreeningEnrollmentInstance.candidateRecord.id)
            }
            else {
                bpvScreeningEnrollmentInstance.discard()
                render(view: "edit", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        if (bpvScreeningEnrollmentInstance) {
            try {
                bpvScreeningEnrollmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvScreeningEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
    
    def resumeEditing = {
        def bpvScreeningEnrollmentInstance = BpvScreeningEnrollment.get(params.id)
        bpvScreeningEnrollmentInstance.dateSubmitted = null
        bpvScreeningEnrollmentInstance.submittedBy = null
        if (bpvScreeningEnrollmentInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvScreeningEnrollmentInstance.id)
        }
        else {
            render(view: "show", model: [bpvScreeningEnrollmentInstance: bpvScreeningEnrollmentInstance])
        }
    }
    
    def setIsEligibleInCandidateRecord(bpvScreeningEnrollmentInstance) {
        if (bpvScreeningEnrollmentInstance.consentObtained == 'Yes' && bpvScreeningEnrollmentInstance.meetCriteria == 'Yes') {
            bpvScreeningEnrollmentInstance.candidateRecord.isEligible = true
        } else {
            bpvScreeningEnrollmentInstance.candidateRecord.isEligible = false
        }    
    }
    
    Map checkError(bpvScreeningEnrollmentInstance) {
        def errorMap = [:]
        
        if (!bpvScreeningEnrollmentInstance.nameCreatCandidate) {
            errorMap.put('nameCreatCandidate', 'Question #2 cannot be blank')
        }
        if (bpvScreeningEnrollmentInstance.meetCriteria != 'Yes' && bpvScreeningEnrollmentInstance.meetCriteria != 'No') {
            errorMap.put('meetCriteria', 'Please select an option for question #3')
        }
        if (bpvScreeningEnrollmentInstance.meetCriteria == 'No' && !bpvScreeningEnrollmentInstance.reasonNotMeet) {
            errorMap.put('reasonNotMeet', 'Please select an option for reason eligibility criteria was not met')
        }
        if (bpvScreeningEnrollmentInstance.reasonNotMeet == 'Other, specify' && !bpvScreeningEnrollmentInstance.otherReasonNotMeet) {
            errorMap.put('otherReasonNotMeet', 'Please specify other reason eligibility criteria was not met')
        }
        
        if (bpvScreeningEnrollmentInstance.meetCriteria == 'Yes')
        {
            if (bpvScreeningEnrollmentInstance.consentObtained != 'Yes' && bpvScreeningEnrollmentInstance.consentObtained != 'No') {
                errorMap.put('consentObtained', 'Please select an option for question #4')
            }
            if (bpvScreeningEnrollmentInstance.consentObtained == 'No' && !bpvScreeningEnrollmentInstance.reasonNotConsented) {
                errorMap.put('reasonNotConsented', 'Please select an option for reason Consent was not obtained')
            }
            if (bpvScreeningEnrollmentInstance.reasonNotConsented == 'Other, specify' && !bpvScreeningEnrollmentInstance.otherReason) {
                errorMap.put('otherReason', 'Please specify other reason Consent was not obtained')
            }
            if (!bpvScreeningEnrollmentInstance.nameConsentCandidate) {
                errorMap.put('nameConsentCandidate', 'Question #5 cannot be blank')
            }
        }
        return errorMap
    }
}

