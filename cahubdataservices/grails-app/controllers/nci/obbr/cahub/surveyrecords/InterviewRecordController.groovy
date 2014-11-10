package nci.obbr.cahub.surveyrecords

import nci.obbr.cahub.surveyrecords.*
import nci.obbr.cahub.surveycomponents.*
import grails.plugins.springsecurity.Secured
import nci.obbr.cahub.staticmembers.ActivityType
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.util.querytracker.Query

class InterviewRecordController {
    
    def scaffold = InterviewRecord

    def hubIdGenService
    def activityEventService

    def accessPrivilegeService
    
    def org
    
    def beforeInterceptor = {
        org = session.org
    }        
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
    
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        def interviewList
        def interviewTotal
        
        if (org?.code == 'OBBR' || session.authorities?.contains('ROLE_BPV_ELSI_DA')) {
            interviewList = InterviewRecord.list(params)
            interviewTotal = InterviewRecord.count()
        } else {
            interviewList = InterviewRecord.createCriteria().list(params) {
                eq("orgCode", org?.code)
            }
            interviewTotal = InterviewRecord.findAllByOrgCode(org?.code)?.size()
        }
        
        [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
    }

    def create = {
        def interviewRecordInstance = new InterviewRecord()
        interviewRecordInstance.properties = params
        return [interviewRecordInstance: interviewRecordInstance]
    }

    def save = {
        def interviewRecordInstance = new InterviewRecord(params)
        
        interviewRecordInstance.interviewId = hubIdGenService.genInterviewId(interviewRecordInstance.orgCode)
        
        interviewRecordInstance.interviewStatus = "DATA"        
        
        SurveyRecord surveyRecord = new SurveyRecord(params);

        if (params.surveyChoice != "No-Survey"){
            if (params.surveyChoice=="Donor"){
                surveyRecord.surveyTemplate = SurveyTemplate.findBySurveyCode("DONOR")
            } else {
                surveyRecord.surveyTemplate = SurveyTemplate.findBySurveyCode("NONDONOR")
            }
            interviewRecordInstance.addToSurveys(surveyRecord)
        }
        if (interviewRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), interviewRecordInstance.interviewId])}"
            surveyRecord.interviewRecord = interviewRecordInstance

            redirect(action: "show", id: interviewRecordInstance.id)
        }
        else {
            render(view: "create", model: [interviewRecordInstance: interviewRecordInstance])
        }
        
        def activityType = ActivityType.findByCode("INTERVIEW")
        def study = Study.findByCode("BPVELSI")
        def bssCode = interviewRecordInstance.orgCode
        def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        def interviewId = interviewRecordInstance.interviewId
        def identifier = interviewRecordInstance.id
        activityEventService.createEvent(activityType, "N/A", study, bssCode, null, username, interviewId, identifier)
    }

    def show = {
        def interviewRecordInstance = InterviewRecord.get(params.id)
        if (!interviewRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), params.id])}"
            redirect(action: "list")
        }
        else {
            if (!accessPrivilegeService.checkSurveyAccessPrivilege(interviewRecordInstance, session, 'show')) {
                redirect(controller: "login", action: "denied")
                return
            }            
            def queryCount = getQueryCount(interviewRecordInstance)
            [interviewRecordInstance: interviewRecordInstance, queryCount: queryCount]
        }
    }

    def edit = {
        def interviewRecordInstance = InterviewRecord.get(params.id)
        if (!interviewRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [interviewRecordInstance: interviewRecordInstance]
        }
    }

    def update = {
        def interviewRecordInstance = InterviewRecord.get(params.id)
        boolean statusChanged = false
        def oldStatus
        def newStatus
        if (interviewRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (interviewRecordInstance.version > version) {
                    
                    interviewRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'interviewRecord.label', default: 'Interview Record')] as Object[], "Another user has updated this InterviewRecord while you were editing")
                    render(view: "edit", model: [interviewRecordInstance: interviewRecordInstance])
                    return
                }
            }
            oldStatus = interviewRecordInstance.interviewStatus?.value
            interviewRecordInstance.properties = params
            newStatus = interviewRecordInstance.interviewStatus?.value
            if (oldStatus != newStatus) {
                statusChanged = true
            }
            if (!interviewRecordInstance.hasErrors() && interviewRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), interviewRecordInstance.interviewId])}"
                redirect(action: "show", id: interviewRecordInstance.id)
                
                if(statusChanged) {
                    def activityType = ActivityType.findByCode("INTERVIEWSTATUS")
                    def study = Study.findByCode("BPVELSI")
                    def bssCode = interviewRecordInstance.orgCode
                    def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    def interviewId = interviewRecordInstance.interviewId
                    def identifier = interviewRecordInstance.id
                    activityEventService.createEvent(activityType, "N/A", study, bssCode, null, username, interviewId + ',' + identifier, oldStatus + ',' + newStatus)
                }
            }
            else {
                render(view: "edit", model: [interviewRecordInstance: interviewRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), params.id])}"
            redirect(action: "list")
        }
    }

@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])    
    def delete = {
        def interviewRecordInstance = InterviewRecord.get(params.id)
        if (interviewRecordInstance) {
            try {
                interviewRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'interviewRecord.label', default: 'Interview Record'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def changeInterviewStatus = {

        def interviewRecordInstance = InterviewRecord.get(params.id)
        
        if (!interviewRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'interviewRecord.label', default: 'Interview Status For Interview record'), interviewRecordInstance.interviewId])}"
            redirect(action: "list")
        }
        //block BSS from changing interview status
        else if(session.org?.code == interviewRecordInstance.orgCode &&
                interviewRecordInstance.interviewStatus?.key in ['SITEQACOMP', 'QA', 'COMP', 'RELE']){
                
                 redirect(controller: "login", action: "denied")
                 return                
        }
        //block BSS from changing another BSS interview status
        else if(session.org?.code != interviewRecordInstance.orgCode && (session.DM == true && session.org.code != 'OBBR')){
                
                 redirect(controller: "login", action: "denied")
                 return                
        }        
        
        else if(interviewRecordInstance){ //check if surveys or CRF are not submitted. bug CDRQA-1006
            
            def srSubmitted = false
            
            interviewRecordInstance.surveys.each{
            if(it.dateSubmitted && it.submittedBy != null){
                srSubmitted = true
                }
            }            
            
            if(!interviewRecordInstance.bpvElsiCrf?.dateSubmitted || (interviewRecordInstance.surveys && srSubmitted == false)){            
                    flash.message = "Status cannot be changed. Please submit the Survey and/or the CRF to change status."
                    redirect(action: "show", id: params.id)
            }
            
            
        
        else{
            return [interviewRecordInstance: interviewRecordInstance]
        }        
                                                                                                                            
    }        
    
    
}

    def getQueryCount(interviewRecordInstance) {
        def activeStatus = QueryStatus.findByCode('ACTIVE')
        def queryCount
        if (session.org?.code == 'OBBR') {
            queryCount = Query.executeQuery("select count(*) from Query i inner join i.interviewRecord c inner join i.queryStatus s where c.id = ? and s.id = ?", [interviewRecordInstance.id, activeStatus.id])
        } else {
            queryCount = Query.executeQuery("select count(*) from Query i inner join i.interviewRecord c inner join i.queryStatus s inner join i.organization o where c.id = ? and s.id = ? and o.code like ?", [interviewRecordInstance.id, activeStatus.id, session.org?.code + "%"])
        }
            
        return queryCount[0]
    }
}
