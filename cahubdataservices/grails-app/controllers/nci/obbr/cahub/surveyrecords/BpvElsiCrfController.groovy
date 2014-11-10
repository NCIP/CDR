package nci.obbr.cahub.surveyrecords

import nci.obbr.cahub.surveycomponents.*
import grails.plugins.springsecurity.Secured

class BpvElsiCrfController {

    def scaffold = BpvElsiCrf
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def accessPrivilegeService    
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvElsiCrfInstanceList: BpvElsiCrf.list(params), bpvElsiCrfInstanceTotal: BpvElsiCrf.count()]
    }

    def create = {
        def ir = InterviewRecord.findById(params.id)
        def bpvElsiCrfInstance = new BpvElsiCrf()
        bpvElsiCrfInstance.interviewRecord = ir
        bpvElsiCrfInstance.properties = params
        def surveyTemplate
        ir.surveys.each{
            surveyTemplate = it.surveyTemplate
        }
        if (surveyTemplate.equals(SurveyTemplate.findBySurveyCode("DONOR"))) {
            bpvElsiCrfInstance.bpvStudyConsent = "Consented"
        } else if (surveyTemplate.equals(SurveyTemplate.findBySurveyCode("NONDONOR"))) {
            bpvElsiCrfInstance.bpvStudyConsent = "Declined"
        }
        return [bpvElsiCrfInstance: bpvElsiCrfInstance, ir:ir]
    }

    def save = {
        def bpvElsiCrfInstance = new BpvElsiCrf(params)
        if (bpvElsiCrfInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey for '), bpvElsiCrfInstance.interviewRecord.interviewId])}"
            redirect(action: "editWithValidation", id: bpvElsiCrfInstance.id)
        }
        else {
            render(view: "create", model: [bpvElsiCrfInstance: bpvElsiCrfInstance])
        }
    }

    def show = {
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
        if (!bpvElsiCrfInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey '), params.id])}"
            redirect(action: "list")
        }
        else {
            if (!accessPrivilegeService.checkSurveyAccessPrivilege(bpvElsiCrfInstance.interviewRecord, session, 'show')) {
                redirect(controller: "login", action: "denied")
                return
            }            
            [bpvElsiCrfInstance: bpvElsiCrfInstance]
        }
    }

    def edit = {
        
        def canSubmit = 'No'        
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
        if (!bpvElsiCrfInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey '), params.id])}"
            redirect(action: "list")
        }
        else {
            //println 'CCC A2 submittedBy=' + bpvElsiCrfInstance.submittedBy
            def surveyAccessPrivilege_denied = (!accessPrivilegeService.checkSurveyAccessPrivilege(bpvElsiCrfInstance.interviewRecord, session, 'edit') )
            // This if-block is for JIRA CDRQA-1263 (HUB-REQ-870)
            if (surveyAccessPrivilege_denied)
            {
                if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (bpvElsiCrfInstance.interviewRecord.interviewStatus?.key in ['QA']))
                surveyAccessPrivilege_denied = false
            }
            if (surveyAccessPrivilege_denied || bpvElsiCrfInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            def errorMap = checkError(bpvElsiCrfInstance)
            errorMap.each() {key, value ->
                bpvElsiCrfInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }            
            return [bpvElsiCrfInstance: bpvElsiCrfInstance]
        }
    }
    
    def editWithValidation = {
        def canSubmit = 'No'
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
        if (!bpvElsiCrfInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey '), params.id])}"
            redirect(action: "list")
        }
        else {
            def surveyAccessPrivilege_denied = !accessPrivilegeService.checkSurveyAccessPrivilege(bpvElsiCrfInstance.interviewRecord, session, 'edit') 
            
            if (surveyAccessPrivilege_denied)
            {
                if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (bpvElsiCrfInstance.interviewRecord.interviewStatus?.key in ['QA']))
                surveyAccessPrivilege_denied = false
            }
            
            if (surveyAccessPrivilege_denied || bpvElsiCrfInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            def errorMap = checkError(bpvElsiCrfInstance)
            errorMap.each() {key, value ->
                bpvElsiCrfInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            render(view: "edit", model: [bpvElsiCrfInstance: bpvElsiCrfInstance, canSubmit: canSubmit])
        }
    }    

    def update = {
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
        if (bpvElsiCrfInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvElsiCrfInstance.version > version) {
                    
                    bpvElsiCrfInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey for ')] as Object[], "Another user has updated this BPV ELSI Case Report Form survey for  while you were editing")
                    render(view: "edit", model: [bpvElsiCrfInstance: bpvElsiCrfInstance])
                    return
                }
            }
            bpvElsiCrfInstance.properties = params
            
            if (!bpvElsiCrfInstance.referringStudy.equals('External Study') ) {
                bpvElsiCrfInstance.externalStudySpecify =null
            }
            if (!bpvElsiCrfInstance.hasErrors() && bpvElsiCrfInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey for '), bpvElsiCrfInstance.interviewRecord.interviewId])}"
                //redirect(action: "show", id: bpvElsiCrfInstance.id)
                redirect(action: "editWithValidation", id: bpvElsiCrfInstance.id)
            }
            else {
                render(view: "edit", model: [bpvElsiCrfInstance: bpvElsiCrfInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey '), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])    
    def delete = {
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
        if (bpvElsiCrfInstance) {
            try {
                bpvElsiCrfInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey for '), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey for '), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BpvElsiCrf.label', default: 'BPV ELSI Case Report Form survey for '), params.id])}"
            redirect(action: "list")
        }
    }
    
    def resumeEditing = {
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
 
        def accessPrivilegeResult
        
        //check interview status before resuming CDRQA-1006
        accessPrivilegeResult = accessPrivilegeService.checkSurveyAccessPrivilege(bpvElsiCrfInstance.interviewRecord, session, 'edit')

        if (!accessPrivilegeResult)
        {
            if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (bpvElsiCrfInstance.interviewRecord.interviewStatus?.key in ['QA']))
            accessPrivilegeResult = true
        }
        
        if (!bpvElsiCrfInstance && !accessPrivilegeResult) {        
            redirect(controller: "login", action: "denied")
            return
        }         
        
        else{ //resume editing 
            
            bpvElsiCrfInstance.dateSubmitted = null
            bpvElsiCrfInstance.submittedBy = null            
            if (bpvElsiCrfInstance.save(flush: true)) {

                redirect(action: "edit", id: bpvElsiCrfInstance.id)
            }
            else {

                render(view: "show", model: [bpvElsiCrfInstance: bpvElsiCrfInstance])
            }
        }
    }    
    

    
    def submit = {
        def bpvElsiCrfInstance = BpvElsiCrf.get(params.id)
        if(bpvElsiCrfInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvElsiCrfInstance.version > version) {

                    bpvElsiCrfInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvElsiCrfInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvTissueProcessEmbed while you were editing")
                    render(view: "edit", model: [bpvElsiCrfInstance: bpvElsiCrfInstance])
                    return
                }
            }

            bpvElsiCrfInstance.properties = params
            if (!bpvElsiCrfInstance.referringStudy.equals('External Study') ) {
                bpvElsiCrfInstance.externalStudySpecify =null
            }
            def errorMap = checkError(bpvElsiCrfInstance)
            errorMap.each() {key, value ->
                bpvElsiCrfInstance.errors.rejectValue(key, value)
            }            

            if(!bpvElsiCrfInstance.hasErrors() && bpvElsiCrfInstance.save(flush: true)) {
                bpvElsiCrfInstance.dateSubmitted = new Date()
                bpvElsiCrfInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                flash.message = "BPV ELSI CRF for interview " + bpvElsiCrfInstance.interviewRecord.interviewId + " submitted successfully"
                redirect(controller: "interviewRecord", action: "show", id: bpvElsiCrfInstance.interviewRecord.id)
            } else {
                bpvElsiCrfInstance.discard()
                render(view: "edit", model: [bpvElsiCrfInstance: bpvElsiCrfInstance])
            }
        } else {
            flash.message = "BPV ELSI CRF for interview " + bpvElsiCrfInstance.interviewRecord.interviewId + " not found"
            redirect(action: "list")
        }
    }    
    
    
    Map checkError(bpvElsiCrfInstance) {
        def errorMap = [:]

        def surveyRecordType = ""
        bpvElsiCrfInstance.interviewRecord.surveys.each {
            surveyRecordType = it.surveyTemplate?.surveyCode
        }
        
        /*if (bpvElsiCrfInstance.initialReferralDate.equals(null)) {
        errorMap.put('initialReferralDate', 'Initial referral date is required.')
        } else {
        if (bpvElsiCrfInstance.initialReferralDate > new Date()) {
        errorMap.put('initialReferralDate', 'Initial referral date cannot be in the future.')
        }            
        }*/
        if (bpvElsiCrfInstance.bpvStudyConsent.equals(null)) {
            errorMap.put('bpvStudyConsent', 'Study consent is required.')
        }        
        if (bpvElsiCrfInstance.daysConsentToInterview == null || bpvElsiCrfInstance.daysConsentToInterview == '') {
            errorMap.put('daysConsentToInterview', 'Days from informed consent to ELSI completed interview is required.')
        }         
        if (bpvElsiCrfInstance.daysInterviewToSurgery == null || bpvElsiCrfInstance.daysInterviewToSurgery == '') {
            errorMap.put('daysInterviewToSurgery', 'Days between ELSI completed interview and surgery is required.')
        } else {
            if (bpvElsiCrfInstance.daysInterviewToSurgery < -30) {
                errorMap.put('daysInterviewToSurgery', 'ELSI Interview cannot be more than 30 days after surgery.')
            }
            if (bpvElsiCrfInstance.daysInterviewToSurgery == 0) {
                errorMap.put('daysInterviewToSurgery', 'Date ELSI completed interview cannot be same as the date of surgery.')
            }
        }
        
        if (bpvElsiCrfInstance.tumorLocation.equals(null)) {
            errorMap.put('tumorLocation', 'Patient tumor location is required.')
        }        
        
        /*if (bpvElsiCrfInstance.initialContactDate.equals(null)) {
        errorMap.put('initialContactDate', 'Date of initial contact is required.')
        } else {
        if (bpvElsiCrfInstance.initialContactDate > new Date()) {
        errorMap.put('initialContactDate', 'Date of initial contact cannot be in the future.')
        }                        
        }*/
        if (bpvElsiCrfInstance.contactResultA.equals(null) && bpvElsiCrfInstance.contactResultB.equals(null) && bpvElsiCrfInstance.contactResultC.equals(null)) {
            errorMap.put('contactResultA', 'Result of contact with potential interview respondent is required.')
        } else {
            if (bpvElsiCrfInstance.interviewRecord.studyConsent.equals("Consented") && (bpvElsiCrfInstance.contactResultA.equals("on") || bpvElsiCrfInstance.contactResultB2.equals("on") 
                    || bpvElsiCrfInstance.contactResultC1.equals("on") || bpvElsiCrfInstance.contactResultC2C.equals("on"))) {
                errorMap.put('contactResultA', 'Result of contact with potential interview respondent has one or more options that cannot be true as the interview study consent status is Consented.')
            } else if ((bpvElsiCrfInstance.interviewRecord.studyConsent.equals("Declined") || bpvElsiCrfInstance.interviewRecord.studyConsent.equals("None")) && 
                (bpvElsiCrfInstance.contactResultB1.equals("on") || bpvElsiCrfInstance.contactResultC2A.equals("on") || bpvElsiCrfInstance.contactResultC2B.equals("on"))) {
                errorMap.put('contactResultA', 'Result of contact with potential interview respondent has one or more options that cannot be true as the interview study consent status is Declined/None.')
            }
        }

        if (bpvElsiCrfInstance.interviewRecord.studyConsent.equals("Consented") || bpvElsiCrfInstance.interviewRecord.studyConsent.equals("Partial")) {
            /*        if (bpvElsiCrfInstance.interviewStartDate.equals(null)) {
            errorMap.put('interviewStartDate', 'Date patient commenced interview is required.')
            } else {
            if (bpvElsiCrfInstance.interviewStartDate > new Date()) {
            errorMap.put('interviewStartDate', 'Date patient commenced interview cannot be in the future.')
            }
            }
            if (bpvElsiCrfInstance.interviewEndDate.equals(null)) {
            errorMap.put('interviewEndDate', 'Date patient completed interview is required.')
            } else {
            if (bpvElsiCrfInstance.interviewEndDate > new Date()) {
            errorMap.put('interviewEndDate', 'Date patient completed interview cannot be in the future.')
            }
            }*/
            if (bpvElsiCrfInstance.interviewStartTime.equals(null) || bpvElsiCrfInstance.interviewStartTime == "") {
                errorMap.put('interviewStartTime', 'Time survey interview commenced is required.')
            }        
            if (bpvElsiCrfInstance.interviewEndTime.equals(null) || bpvElsiCrfInstance.interviewEndTime == "") {
                errorMap.put('interviewEndTime', 'Time survey interview completed is required.')
            }
            
            /*        if (bpvElsiCrfInstance.interviewRestartDate != null && bpvElsiCrfInstance.interviewRestartDate > new Date()) {
            errorMap.put('interviewRestartDate', 'Date/Time survey recommenced cannot be in the future.')
            }                   */
            
            if (bpvElsiCrfInstance.surveyAdministrationMode.equals(null) || bpvElsiCrfInstance.surveyAdministrationMode == "") {
                errorMap.put('surveyAdministrationMode', 'Mode of survey administration is required.')
            }       
            if (bpvElsiCrfInstance.dataCollectionMethod.equals(null) || bpvElsiCrfInstance.dataCollectionMethod == "") {
                errorMap.put('dataCollectionMethod', 'Data collection method is required.')
            }               
            //        if (bpvElsiCrfInstance.CIFShredDate.equals(null) || ((bpvElsiCrfInstance.CIFShredTime.equals(null) || bpvElsiCrfInstance.CIFShredTime == ""))) {
            //            errorMap.put('CIFShredDate', 'Date & time patient Contact Information Form (CIF) was shredded is required')
            //        }

            if (bpvElsiCrfInstance.CIFShredDate != null && bpvElsiCrfInstance.CIFShredDate > new Date()) {
                errorMap.put('CIFShredDate', 'Date & time patient Contact Information Form (CIF) was shredded cannot be in the future.')
            }                   
            
            if (bpvElsiCrfInstance.responsesTranscriptionDate.equals(null)) {
                errorMap.put('responsesTranscriptionDate', 'Date patient open-ended interview responses transcription when completed is required.')
            } else {
                if (bpvElsiCrfInstance.responsesTranscriptionDate > new Date()) {
                    errorMap.put('responsesTranscriptionDate', 'Date patient open-ended interview responses transcription cannot be in the future.')
                }
            }
            if (bpvElsiCrfInstance.transcriptionVerifiedDate.equals(null)) {
                errorMap.put('transcriptionVerifiedDate', 'Date transcription is verified for accuracy is required.')
            } else {
                if (bpvElsiCrfInstance.transcriptionVerifiedDate > new Date()) {
                    errorMap.put('transcriptionVerifiedDate', 'Date transcription is verified for accuracy cannot be in the future.')
                }            
            }        
            if (bpvElsiCrfInstance.cdrEntryDate.equals(null)) {
                errorMap.put('cdrEntryDate', 'Date patient survey interview data entered into CDR is required.')
            } else {
                if (bpvElsiCrfInstance.cdrEntryDate > new Date()) {
                    errorMap.put('cdrEntryDate', 'Date patient survey interview data entered into CDR cannot be in the future.')
                }            
            }
            if (bpvElsiCrfInstance.cdrEntryVerifiedDate.equals(null)) {
                errorMap.put('cdrEntryVerifiedDate', 'Date data entry in CDR is verified for accuracy is required.')
            } else {
                if (bpvElsiCrfInstance.cdrEntryVerifiedDate > new Date()) {
                    errorMap.put('cdrEntryVerifiedDate', 'Date data entry in CDR is verified for accuracy cannot be in the future.')
                }                        
            }                

            if (!bpvElsiCrfInstance.audioRecordEraseDate.equals(null)) {
                if (bpvElsiCrfInstance.audioRecordEraseDate > new Date()) {
                    errorMap.put('audioRecordEraseDate', 'Date audio-recording of interview is erased cannot be in the future.')
                }                                    
            }

            if (!bpvElsiCrfInstance.audioEraseVerifiedDate.equals(null)) {
                if (bpvElsiCrfInstance.audioEraseVerifiedDate > new Date()) {
                    errorMap.put('audioEraseVerifiedDate', 'Date recording erasure is verified cannot be in the future.')
                }            
            }

            //        if (bpvElsiCrfInstance.protocolDeviations.equals(null) || bpvElsiCrfInstance.protocolDeviations == "") {
            //            errorMap.put('protocolDeviations', 'Possible adverse events or protocol deviations reported or noted is required')
            //        }
            //        if (bpvElsiCrfInstance.comments.equals(null) || bpvElsiCrfInstance.comments == "") {
            //            errorMap.put('comments', 'Comments and notes are required')
            //        }  

            if (bpvElsiCrfInstance?.formVersion >=2 && bpvElsiCrfInstance.referringStudy && bpvElsiCrfInstance.referringStudy == 'External Study' && !bpvElsiCrfInstance?.externalStudySpecify) {
                errorMap.put('externalStudySpecify', 'Specify External Study in Question 12')
            }
        }

                            
        return errorMap
    }
    
    def searchHome = {
        
    }
    
    def search = {
        
        def bpvElsiCrfInstance = new BpvElsiCrf()
        def interviewRecordInstance = new InterviewRecord()
        if (params.interviewId) {
            
            interviewRecordInstance = InterviewRecord.findByInterviewId(params.interviewId)
            
            if (interviewRecordInstance) {
                redirect(controller: "home", action: "bpvelsihome", id: interviewRecordInstance.id)
                /*bpvElsiCrfInstanceList: bpvElsiCrfInstance, bpvElsiCrfInstanceTotal: bpvElsiCrfInstance.count())*/
            } else {
                flash.message = "BPV ELSI Interview not found: "+ params.interviewId
                redirect(controller: "home", action: "bpvelsihome", id: null)
            }
        } else {
           if (params.bssId) { 
               redirect(controller: "home", action: "bpvelsihome", params: [bssId: params.bssId])
           } else {
               if (params.intrStatus) {
                   redirect(controller: "home", action: "bpvelsihome", params: [intrStatus: params.intrStatus])
               } else {
                   if (params.survStatus) {
                       redirect(controller: "home", action: "bpvelsihome", params: [survStatus: params.survStatus])
                   } else {
                       if (params.crfStatus) {
                           redirect(controller: "home", action: "bpvelsihome", params: [crfStatus: params.crfStatus])
                       } else {
                           if (params.searchFromDate) {
                               redirect(controller: "home", action: "bpvelsihome", params: [searchFromDate: params.searchFromDate, searchToDate: params.searchToDate])
                           } else {
                              redirect(controller: "home", action: "bpvelsihome") 
                           }
                       }
                   }
               }
           }
        }
    }
}
