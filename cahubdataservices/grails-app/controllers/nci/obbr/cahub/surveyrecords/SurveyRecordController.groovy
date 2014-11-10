package nci.obbr.cahub.surveyrecords

import nci.obbr.cahub.surveyrecords.*
import nci.obbr.cahub.surveycomponents.*
import grails.converters.*
import nci.obbr.cahub.staticmembers.*
import grails.plugins.springsecurity.Secured 

class SurveyRecordController {

    def scaffold = SurveyRecord
    def accessPrivilegeService
    def bpvElsiService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [surveyRecordInstanceList: SurveyRecord.list(params), surveyRecordInstanceTotal: SurveyRecord.count()]
    }

    def create = {
        def surveyRecordInstance = new SurveyRecord()
        surveyRecordInstance.properties = params
        return [surveyRecordInstance: surveyRecordInstance]
    }

    def save = {
        def surveyRecordInstance = new SurveyRecord(params)
        if (surveyRecordInstance.save(flush: true)) {
            if (surveyRecordInstance.surveyTemplate.surveyCode.equals("DONOR")) {
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Donor Survey Form for '), surveyRecordInstance.interviewRecord.interviewId])}"
            } else {
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Non Donor Survey Form for '), surveyRecordInstance.interviewRecord.interviewId])}"
            }
            redirect(action: "show", id: surveyRecordInstance.id)
        }
        else {
            render(view: "create", model: [surveyRecordInstance: surveyRecordInstance])
        }
    }

    def show = {
        println 'CCC A2 show'
        def surveyRecordInstance = SurveyRecord.get(params.id)
        if (!surveyRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyRecord.label', default: 'Survey Record'), params.id])}"
            redirect(action: "list")
        }
        else {
            if (!accessPrivilegeService.checkSurveyAccessPrivilege(surveyRecordInstance.interviewRecord, session, 'show')) {
                redirect(controller: "login", action: "denied")
                return
            }            
            [surveyRecordInstance: surveyRecordInstance]
        }
    }

    def edit = {
        //println 'CCC A1 edit'
        def surveyRecordInstance = SurveyRecord.get(params.id)
        if (!surveyRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyRecord.label', default: 'Survey Record'), params.id])}"
            redirect(action: "list")
        }
        else {
            def surveyAccessPrivilege_denied = !accessPrivilegeService.checkSurveyAccessPrivilege(surveyRecordInstance.interviewRecord, session, 'edit')
            if (surveyAccessPrivilege_denied)
            {
                if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (surveyRecordInstance.interviewRecord.interviewStatus?.key in ['QA']))
                surveyAccessPrivilege_denied = false
            }
            if (surveyAccessPrivilege_denied || surveyRecordInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            render(view: "edit", model: [surveyRecordInstance: surveyRecordInstance])
        }
    }
        
    def resumeEditing = {
        def surveyRecordInstance = SurveyRecord.get(params.id)

        def surveyAccessPrivilege_denied = !accessPrivilegeService.checkSurveyAccessPrivilege(surveyRecordInstance.interviewRecord, session, 'edit')
        if (surveyAccessPrivilege_denied)
        {
            if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (surveyRecordInstance.interviewRecord.interviewRecord.interviewStatus?.key in ['QA']))
            surveyAccessPrivilege_denied = false
        }
        if (surveyAccessPrivilege_denied || surveyRecordInstance.submittedBy != null) {
            //check interview status before resuming CDRQA-1006
      
            redirect(controller: "login", action: "denied")
            return
        }         
        else{

        
        surveyRecordInstance.dateSubmitted = null
        surveyRecordInstance.submittedBy = null
        if (surveyRecordInstance.save(flush: true)) {

                redirect(action: "edit", id: surveyRecordInstance.id)
        }
        else {

            render(view: "show", model: [surveyRecordInstance: surveyRecordInstance])
        }
    }        
    }
    
    def resumeEditingJSON = {

        def resumeStatus = [:]
        def accessPrivilegeResult
        def surveyRecordInstance = SurveyRecord.get(params.id)

        //check interview status before resuming CDRQA-1006
        accessPrivilegeResult = accessPrivilegeService.checkSurveyAccessPrivilege(surveyRecordInstance.interviewRecord, session, 'edit')
        
        if (!accessPrivilegeResult)
        {
            if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (surveyRecordInstance.interviewRecord.interviewStatus?.key in ['QA']))
            accessPrivilegeResult = true
        }

        if (!surveyRecordInstance && !accessPrivilegeResult) {

            resumeStatus.put("status","failure")
        }         
        else{        
        
            surveyRecordInstance.dateSubmitted = null
            surveyRecordInstance.submittedBy = null

            if (surveyRecordInstance.save(flush: true)) {
                resumeStatus.put("status","success")
            }
            else {
                resumeStatus.put("status","failure")
            }
            JSON.use("deep") {
                if (params.callback) {
                   render "${params.callback.encodeAsURL()}(${resumeStatus as JSON})"
                } else {
                   render resumeStatus as JSON
                }
           }        
        }
    }            
    
    def getSurvey = {
        //println 'CCC A4 params=' + params
        def surveyRecordInstance = SurveyRecord.get(params.id)
        def accessPrivilegeResult
        if (params.pagetype == "edit") {
            accessPrivilegeResult = accessPrivilegeService.checkSurveyAccessPrivilege(surveyRecordInstance.interviewRecord, session, 'edit')
            // This if-block is for JIRA CDRQA-1263 (HUB-REQ-870)
            if (!accessPrivilegeResult)
            {
                if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (surveyRecordInstance.interviewRecord.interviewStatus?.key in ['QA']))
                  accessPrivilegeResult = true
            }
        } else if (params.pagetype == "show") {
            accessPrivilegeResult = accessPrivilegeService.checkSurveyAccessPrivilege(surveyRecordInstance.interviewRecord, session, 'show')
        }
        
        if (surveyRecordInstance && !accessPrivilegeResult) {
            def survey = ["status":"denied"]
            JSON.use("deep") {
                if (params.callback) {
                    render "${params.callback.encodeAsURL()}(${survey as JSON})"
                } else {
                    render survey as JSON
                }
            }
        } else if (!surveyRecordInstance) {
            def survey = ["status":"not found"]
            JSON.use("deep") {
                if (params.callback) {
                    render "${params.callback.encodeAsURL()}(${survey as JSON})"
                } else {
                    render survey as JSON
                }
            }
        }
        else {
            def payload = [:]
            payload.put("survey",bpvElsiService.getSurvey(surveyRecordInstance, session))
            
            JSON.use("deep") {
                if (params.callback) {
                    render "${params.callback.encodeAsURL()}(${payload as JSON})"
                } else {
                    render payload as JSON
                }
            }
        }        
        
    }    
    

    def update = {
        def surveyRecordInstance = SurveyRecord.get(params.id)
        
        if (surveyRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyRecordInstance.version > version) {
                    
                    surveyRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyRecord.label', default: 'Survey Record')] as Object[], "Another user has updated this SurveyRecord while you were editing")
                    render(view: "edit", model: [surveyRecordInstance: surveyRecordInstance])
                    return
                }
            }
            surveyRecordInstance.properties = params
            if (!surveyRecordInstance.hasErrors() && surveyRecordInstance.save(flush: true)) {
                if (surveyRecordInstance.surveyTemplate.surveyCode.equals("DONOR")) {
                    flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Donor Survey Form for '), surveyRecordInstance.interviewRecord.interviewId])}"
                } else {
                    flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Non Donor Survey Form for '), surveyRecordInstance.interviewRecord.interviewId])}"
                }
                redirect(action: "show", id: surveyRecordInstance.id)
            }
            else {
                render(view: "edit", model: [surveyRecordInstance: surveyRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyRecord.label', default: 'Survey Record'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def updateSurveyJSON = {
        def surveyRecordInstance = SurveyRecord.get(params.id)
        
        surveyRecordInstance.dateSubmitted = null
        surveyRecordInstance.submittedBy = null        
        
        if (surveyRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyRecordInstance.version > version) {
                    
                    surveyRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyRecord.label', default: 'Survey Record')] as Object[], "Another user has updated this SurveyRecord while you were editing")
                    render(view: "edit", model: [surveyRecordInstance: surveyRecordInstance])
                    return
                }
            }
            surveyRecordInstance.properties = params
            SurveyAnswer surveyAnswer
            SurveyQuestion surveyQuestion

                surveyQuestion = SurveyQuestion.findById(params.question)
                surveyRecordInstance.answers.each () {
                    if (it.question==surveyQuestion) {
                        surveyAnswer = it
                    }
                }
                if (!surveyAnswer) {
                    surveyAnswer = new SurveyAnswer()
                }
                
                if (params.answer!=null && params.answer!="") {
                    if (surveyQuestion.answerType=="radioYesNo" || (surveyQuestion.parentQuestion!=null && surveyQuestion.parentQuestion.answerType=="tabled radio")) {
                        if (params.answer=="-1" || params.answer=="IDK" || params.answer=="idk") {
                            surveyAnswer.response = params.answer
                        } else {
                            surveyAnswer.response = (nci.obbr.cahub.surveycomponents.SurveyQuestionOption.findByOptionText(params.answer)).optionLabel
                        }
                        
                    } else {
                        surveyAnswer.response = params.answer
                    }
                
                surveyAnswer.question = surveyQuestion
                surveyAnswer.surveyRecord = surveyRecordInstance
                surveyAnswer.save(failOnError:true, flush: true)
                surveyRecordInstance.addToAnswers(surveyAnswer)
                } else {
                    surveyAnswer.delete()
                    surveyRecordInstance.removeFromAnswers(surveyAnswer)
                }
                def questionCount = 0
                surveyRecordInstance.surveyTemplate.sections.each {
                    it.questions.each   {
                        if (it.answerType!="tabled scale" && it.answerType!="tabled radio" && it.answerType!="empty") {
                            questionCount++
                        }
                    }
                }
                def surveyComplete = false
                if (surveyRecordInstance.answers.size()==questionCount) {
                    surveyComplete = true
                }
                surveyComplete = true
            def updateStatus = [:]
            if (!surveyRecordInstance.hasErrors() && surveyRecordInstance.save(flush: true)) {
                if (surveyRecordInstance.surveyTemplate.surveyCode.equals("DONOR")) {
                    flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Donor Survey Form for '), surveyRecordInstance.interviewRecord.interviewId])}"
                } else {
                    flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Non Donor Survey Form for '), surveyRecordInstance.interviewRecord.interviewId])}"
                }
                updateStatus.put("status","success")
                updateStatus.put("surveyComplete",surveyComplete)
            }
            else {
                updateStatus.put("status","failure")
                updateStatus.put("surveyComplete",surveyComplete)
            }
            JSON.use("deep") {
                if (params.callback) {
                   render "${params.callback.encodeAsURL()}(${updateStatus as JSON})"
                } else {
                   render updateStatus as JSON
                }
           }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyRecord.label', default: 'Survey Record'), params.id])}"
            redirect(action: "list")
        }
    }


    def submitSurveyJSON = {
        def surveyRecordInstance = SurveyRecord.get(params.id)
        if(surveyRecordInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(surveyRecordInstance.version > version) {

                    surveyRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [surveyRecordInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvTissueProcessEmbed while you were editing")
                    render(view: "edit", model: [surveyRecordInstance: surveyRecordInstance])
                    return
                }
            }

            surveyRecordInstance.properties = params

            def submitStatus = [:]
            if(!surveyRecordInstance.hasErrors() && surveyRecordInstance.save(flush: true)) {
                surveyRecordInstance.dateSubmitted = new Date()
                surveyRecordInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                submitStatus.put("status","success")
//                render submitStatus as JSON

                if (surveyRecordInstance.surveyTemplate.surveyCode.equals("DONOR")) {
                    flash.message = "BPV ELSI Donor Survey Form for  " + surveyRecordInstance.interviewRecord.interviewId + " submitted successfully"
                } else {
                    flash.message = "BPV ELSI Non Donor Survey Form for  " + surveyRecordInstance.interviewRecord.interviewId + " submitted successfully"
                }
                redirect(controller: "interviewRecord", action: "show", id: surveyRecordInstance.interviewRecord.id)
            } else {
                surveyRecordInstance.discard()
                submitStatus.put("status","failure")
//                render submitStatus as JSON         
                render(view: "edit", model: [surveyRecordInstance: surveyRecordInstance])
            }
        } else {
            flash.message = "Survey " + surveyRecordInstance.id + " not found"
            redirect(action: "list")
        }
    }
    
@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def surveyRecordInstance = SurveyRecord.get(params.id)
        def interviewId = surveyRecordInstance.interviewRecord.interviewId
        if (surveyRecordInstance) {
            try {
                surveyRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveyRecord.label', default: 'BPV ELSI Survey Form for '), interviewId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveyRecord.label', default: 'Survey Record'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyRecord.label', default: 'Survey Record'), params.id])}"
            redirect(action: "list")
        }
    }
}
