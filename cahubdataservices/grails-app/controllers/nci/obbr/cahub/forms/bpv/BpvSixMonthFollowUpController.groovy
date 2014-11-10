package nci.obbr.cahub.forms.bpv
import groovy.sql.Sql
import nci.obbr.cahub.util.AppSetting
import grails.util.GrailsUtil

class BpvSixMonthFollowUpController {

    def sendMailService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    javax.sql.DataSource dataSource

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvSixMonthFollowUpInstanceList: BpvSixMonthFollowUp.list(params), bpvSixMonthFollowUpInstanceTotal: BpvSixMonthFollowUp.count()]
    }

    def create = {
        def bpvSixMonthFollowUpInstance = new BpvSixMonthFollowUp()
        bpvSixMonthFollowUpInstance.properties = params
        def isElig =isCaseEligible(params.caseRecord.id)
        return [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance,isElig:isElig]
    }

    def save = {
        
        def bpvSixMonthFollowUpInstance = new BpvSixMonthFollowUp(params)
        if (bpvSixMonthFollowUpInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvSixMonthFollowUp.label', default: 'Bpv 6 Month Follow up for'), bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
            //redirect(action: "show", id: bpvSixMonthFollowUpInstance.id)
            redirect(action: "editWithValidation", id: bpvSixMonthFollowUpInstance.id)
        }
        else {
            render(view: "create", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
        }
    }

    def show = {
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        
        if (!bpvSixMonthFollowUpInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvSixMonthFollowUp.label', default: 'Bpv Six Month Follow up for'), params.id])}"
            redirect(action: "list")
        }
        else {
            def isElig =isCaseEligible(bpvSixMonthFollowUpInstance.caseRecord.id)
            [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance,isElig:isElig]
        }
    }

        
    def edit = {
        def canSubmit = 'No'
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        if (!bpvSixMonthFollowUpInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
                        
            def errorMap = checkError(bpvSixMonthFollowUpInstance)
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            def isElig =isCaseEligible(bpvSixMonthFollowUpInstance.caseRecord.id)
            return [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance, canSubmit: canSubmit,isElig:isElig]
        }
    }
    
    def editWithValidation = {
        def canSubmit = 'No'
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        if (!bpvSixMonthFollowUpInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
        else {
                        
            def errorMap = checkError(bpvSixMonthFollowUpInstance)
            errorMap.each() {key, value ->
                bpvSixMonthFollowUpInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            def isElig =isCaseEligible(bpvSixMonthFollowUpInstance.caseRecord.id)
            render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance, canSubmit: canSubmit,isElig:isElig])
        }
    }

       
    
    def update = {
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        if (bpvSixMonthFollowUpInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvSixMonthFollowUpInstance.version > version) {
                    
                    bpvSixMonthFollowUpInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvSixMonthFollowUp while you were editing")
                    render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
                    return
                }
            }
            
            bpvSixMonthFollowUpInstance.properties = params
            
            if (!bpvSixMonthFollowUpInstance.hasErrors() && bpvSixMonthFollowUpInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
                redirect(action: "editWithValidation", id: bpvSixMonthFollowUpInstance.id)
            }
            else {
                render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message',  args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    
    def submit = {
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        if (bpvSixMonthFollowUpInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvSixMonthFollowUpInstance.version > version) {
                    
                    bpvSixMonthFollowUpInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvSixMonthFollowUp while you were editing")
                    render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
                    return
                }
            }
            
            bpvSixMonthFollowUpInstance.properties = params
            
            def errorMap = checkError(bpvSixMonthFollowUpInstance)
            errorMap.each() {key, value ->
                bpvSixMonthFollowUpInstance.errors.rejectValue(key, value)
            }
            if (!bpvSixMonthFollowUpInstance.hasErrors() && bpvSixMonthFollowUpInstance.save(flush: true)) {
                
                bpvSixMonthFollowUpInstance.dateSubmitted = new Date()
                bpvSixMonthFollowUpInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                flash.message = "${message(code: 'default.submitted.message', args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
                
                // now email the DM team that form has been submitted
                def recipient = AppSetting.findByCode('DM_FAST_TRACK_DISTRO')?.bigValue
                // def recipient = 'pushpa.hariharan@nih.gov'
                def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"
                def emailSubject = "CDR Alert:$env Six Month follow up form submitted for ${bpvSixMonthFollowUpInstance.caseRecord.caseId} "
                def emailBody = "Six month follow up form has been submitted by ${bpvSixMonthFollowUpInstance.submittedBy} for ${bpvSixMonthFollowUpInstance.caseRecord.caseId} "
                sendMailService.sendServiceEmail(recipient, emailSubject, emailBody, 'body')
                           
                redirect(controller: "caseRecord", action: "view", id: bpvSixMonthFollowUpInstance.caseRecord.id)
                
                
            }
            else {
                bpvSixMonthFollowUpInstance.discard()
                render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    def dmReview = {
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        if (bpvSixMonthFollowUpInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvSixMonthFollowUpInstance.version > version) {
                    
                    bpvSixMonthFollowUpInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvSixMonthFollowUp while you were editing")
                    render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
                    return
                }
            }
            
            bpvSixMonthFollowUpInstance.properties = params
            
            
            def errorMap = checkError(bpvSixMonthFollowUpInstance)
            errorMap.each() {key, value ->
                bpvSixMonthFollowUpInstance.errors.rejectValue(key, value)
            }
            if (!bpvSixMonthFollowUpInstance.hasErrors() && bpvSixMonthFollowUpInstance.save(flush: true)) {
                
                bpvSixMonthFollowUpInstance.dmReviewed = true
                bpvSixMonthFollowUpInstance.dmReviewDate = new Date()
                flash.message = ' DM has reviewed Case '+ bpvSixMonthFollowUpInstance.caseRecord.caseId                
                redirect(action: "show", id: bpvSixMonthFollowUpInstance.id)              
                
            }
            else {
                bpvSixMonthFollowUpInstance.discard()
                render(view: "edit", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName + ' for Case', bpvSixMonthFollowUpInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }
    
    def resumeEditing = {
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        bpvSixMonthFollowUpInstance.dateSubmitted = null
        bpvSixMonthFollowUpInstance.submittedBy = null
        bpvSixMonthFollowUpInstance.dmReviewed = null
        bpvSixMonthFollowUpInstance.dmReviewDate = null
        if (bpvSixMonthFollowUpInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvSixMonthFollowUpInstance.id)
        }
        else {
            render(view: "show", model: [bpvSixMonthFollowUpInstance: bpvSixMonthFollowUpInstance])
        }
    }
    def delete = {
        def bpvSixMonthFollowUpInstance = BpvSixMonthFollowUp.get(params.id)
        if (bpvSixMonthFollowUpInstance) {
            try {
                bpvSixMonthFollowUpInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bpvSixMonthFollowUp.label', default: 'BpvSixMonthFollowUp'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bpvSixMonthFollowUp.label', default: 'BpvSixMonthFollowUp'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvSixMonthFollowUp.label', default: 'BpvSixMonthFollowUp'), params.id])}"
            redirect(action: "list")
        }
    }
    
    boolean isCaseEligible(caseid) {
        def sql = new Sql(dataSource)
        def sqlString=  " select distinct 'Eligible' as Criteria "+
                        " from bpv_case_quality_review b, Bpv_Prc_Path_Review p, dr_case c,st_case_status s "+
                        " where b.case_record_id = c.id  "+
                        " and p.case_record_id = c.id "+
                        " and b.tubes='Yes' "+
                        " and b.tumor_module='Yes' "+
                        " and b.sld_Rev_Confirm_Elig='Yes' "+
                        " and b.date_submitted is not null "+
                        " and b.project_criteria='Yes' "+
                        " and p.consistent_local_prc='Yes' "+
                        " and p.review_date is not null "+
                        " and c.case_status_id=s.id "+
        //" and c.id="+caseid
                         " and s.code='COMP' and c.id="+caseid
        
        def rows = sql.rows(sqlString)
        /**println("row size: " + rows.size())
        rows.each{
        println("sample_qc_id: " + it[0]  + " sample_fr_id: " + it[1] + " sample_fr_weight: " + it[2])
        }**/
        sql.close()
        if(rows){
            // println "pmh cheking if case elig: "+rows[0][0]
            return true
        }
        else{//println " not elig"
            return false}
        
    }
    Map checkError(bpvSixMonthFollowUpInstance) {
        def errorMap = [:]
        
        if (!bpvSixMonthFollowUpInstance.donorStatus) {
            errorMap.put('donorStatus', 'Please enter Donor Status')
        }
        
        
        if (!bpvSixMonthFollowUpInstance.recurrence) {
            errorMap.put('recurrence', 'Please enter a recurrence option')
        }        
        
        return errorMap
    }
    
}
