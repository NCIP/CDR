package nci.obbr.cahub.util.querytracker

import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.staticmembers.CaseAttachmentType
import groovy.sql.Sql
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.surveyrecords.InterviewRecord
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.Organization
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.staticmembers.QueryType
import nci.obbr.cahub.util.FileUpload
import nci.obbr.cahub.staticmembers.CaseAttachmentType
import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.staticmembers.ActivityType
import nci.obbr.cahub.util.ActivityEvent
import grails.plugins.springsecurity.Secured 

class QueryController {

    static scaffold = Query
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def queryService
    def activityEventService
    javax.sql.DataSource dataSource
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def queryInstanceList
        def queryInstanceTotal
        
        if (session.org?.code == 'OBBR') {
            queryInstanceList = Query.list(params)
            queryInstanceTotal = Query.count()
        } else {
            def queryInstanceTotalList = Query.createCriteria().list() {
                createAlias("organization", "o")
                like("o.code", session.org?.code + "%")
                isNotNull("queryStatus")
            }
            queryInstanceList = Query.createCriteria().list(params) {
                createAlias("organization", "o")
                like("o.code", session.org?.code + "%")
                isNotNull("queryStatus")
            }
            queryInstanceTotal = queryInstanceTotalList.size()
        }
        
        [queryInstanceList: queryInstanceList, queryInstanceTotal: queryInstanceTotal]
    }
    
    def listByCase = {
        if (!params.caseRecord?.id) {
            redirect(action: "list", params: params)
        } else {
            def queryInstanceList
            if (session.org?.code == 'OBBR') {
                queryInstanceList = Query.createCriteria().list {
                    createAlias("caseRecord", "c")
                    eq("c.id", Long.parseLong(params.caseRecord?.id))
                    order("dateCreated", "desc")
                } 
            } else {
                queryInstanceList = Query.createCriteria().list {
                    createAlias("caseRecord", "c")
                    eq("c.id", Long.parseLong(params.caseRecord?.id))
                    createAlias("organization", "o")
                    like("o.code", session.org?.code + "%")
                    isNotNull("queryStatus")
                    order("dateCreated", "desc")
                }
            }
            render(view: "list", model: [queryInstanceList: queryInstanceList])
        }
    }
    
    def listByCandidate = {
        if (!params.candidateRecord?.id) {
            redirect(action: "list", params: params)
        } else {
            def queryInstanceList
            if (session.org?.code == 'OBBR') {
                queryInstanceList = Query.createCriteria().list {
                    createAlias("candidateRecord", "c")
                    eq("c.id", Long.parseLong(params.candidateRecord?.id))
                    order("dateCreated", "desc")
                } 
            } else {
                queryInstanceList = Query.createCriteria().list {
                    createAlias("candidateRecord", "c")
                    eq("c.id", Long.parseLong(params.candidateRecord?.id))
                    createAlias("organization", "o")
                    like("o.code", session.org?.code + "%")
                    isNotNull("queryStatus")
                    order("dateCreated", "desc")
                }
            }
            render(view: "list", model: [queryInstanceList: queryInstanceList])
        }
    }

    def listByInterview = {
        if (!params.interviewRecord?.id) {
            redirect(action: "list", params: params)
        } else {
            def queryInstanceList
            if (session.org?.code == 'OBBR') {
                queryInstanceList = Query.createCriteria().list {
                    createAlias("interviewRecord", "i")
                    eq("i.id", Long.parseLong(params.interviewRecord?.id))
                    order("dateCreated", "desc")
                } 
            } else {
                queryInstanceList = Query.createCriteria().list {
                    createAlias("interviewRecord", "i")
                    eq("i.id", Long.parseLong(params.interviewRecord?.id))
                    createAlias("organization", "o")
                    like("o.code", session.org?.code + "%")
                    isNotNull("queryStatus")
                    order("dateCreated", "desc")
                }
            }
            render(view: "list", model: [queryInstanceList: queryInstanceList])
        }
    }
    
    def create = {
        def queryInstance = new Query()
        queryInstance.properties = params
        return [queryInstance: queryInstance]
    }

    def save = {
        def queryInstance = new Query(params)
        queryInstance.caseRecord = CaseRecord.findByCaseId(params.caseId)
        queryInstance.candidateRecord = CandidateRecord.findByCandidateId(params.candidateId)
        queryInstance.interviewRecord = InterviewRecord.findByInterviewId(params.interviewId)
        
        def errorMap = checkError(queryInstance)
        errorMap.each() {key, value ->
            queryInstance.errors.rejectValue(key, value)
        }
        
        if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
            if (queryInstance.caseRecord) {
                queryInstance.study = queryInstance.caseRecord.study
            } else if (queryInstance.candidateRecord) {
                queryInstance.study = queryInstance.candidateRecord.study
            } else if (queryInstance.interviewRecord) {
                queryInstance.study = Study.findByCode('BPVELSI')
            } else {
                queryInstance.study = BSS.findByCode(queryInstance.organization?.code)?.study
            }
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'query.label', default: 'Query'), queryInstance.id])}"
            redirect(action: "show", id: queryInstance.id)
        }
        else {
            render(view: "create", model: [queryInstance: queryInstance])
        }
    }

    def saveActivate = {
        def queryInstance = new Query(params)
        queryInstance.caseRecord = CaseRecord.findByCaseId(params.caseId)
        queryInstance.candidateRecord = CandidateRecord.findByCandidateId(params.candidateId)
        queryInstance.interviewRecord = InterviewRecord.findByInterviewId(params.interviewId)
        
        def errorMap = checkError(queryInstance)
        errorMap.each() {key, value ->
            queryInstance.errors.rejectValue(key, value)
        }
        
        if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
            if (queryInstance.caseRecord) {
                queryInstance.study = queryInstance.caseRecord.study
            } else if (queryInstance.candidateRecord) {
                queryInstance.study = queryInstance.candidateRecord.study
            } else if (queryInstance.interviewRecord) {
                queryInstance.study = Study.findByCode('BPVELSI')
            } else {
                queryInstance.study = BSS.findByCode(queryInstance.organization?.code)?.study
            }
            queryInstance.queryStatus = QueryStatus.findByCode("ACTIVE")
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'query.label', default: 'Query'), queryInstance.id])}"
            redirect(action: "show", id: queryInstance.id)
            
            def activityType = ActivityType.findByCode("QUERY")
            def caseId = queryInstance.caseRecord?.caseId
            def study = queryInstance.study
            def bssCode = queryInstance.organization?.code
            def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def orgCode = queryInstance.organization?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, queryInstance.id, orgCode)
        }
        else {
            render(view: "create", model: [queryInstance: queryInstance])
        }
    }
    
    def show = {
        def queryInstance = Query.get(params.id)
        if (!queryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
        else {
            if (!accessPrivilegeService.checkAccessPrivilegeQuery(queryInstance, session)) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            [queryInstance: queryInstance]
        }
    }

    def edit = {
        def queryInstance = Query.get(params.id)
        if (!queryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [queryInstance: queryInstance]
        }
    }

    def update = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "edit", model: [queryInstance: queryInstance])
                    return
                }
            }
            queryInstance.properties = params
            queryInstance.caseRecord = CaseRecord.findByCaseId(params.caseId)
            queryInstance.candidateRecord = CandidateRecord.findByCandidateId(params.candidateId)
            queryInstance.interviewRecord = InterviewRecord.findByInterviewId(params.interviewId)
            if (queryInstance.caseRecord) {
                queryInstance.study = queryInstance.caseRecord.study
            } else if (queryInstance.candidateRecord) {
                queryInstance.study = queryInstance.candidateRecord.study
            } else if (queryInstance.interviewRecord) {
                queryInstance.study = Study.findByCode('BPVELSI')
            } else {
                queryInstance.study = BSS.findByCode(queryInstance.organization?.code)?.study
            }
            
            def errorMap = checkError(queryInstance)
            errorMap.each() {key, value ->
                queryInstance.errors.rejectValue(key, value)
            }
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'query.label', default: 'Query'), queryInstance.id])}"
                redirect(action: "show", id: queryInstance.id)
            }
            else {
                queryInstance.discard()
                render(view: "edit", model: [queryInstance: queryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            try {
                queryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def upload = {
        def queryInstance = Query.get(params.id)
        
        // hide the case recall categories for general file uploads
        def gencatList = CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
        gencatList.add(CaseAttachmentType.findByCode('OTHER'))
        
        return [queryInstance: queryInstance, gencatList: gencatList]
    }
    
    def upload_save = {
        def queryInstance = Query.get(params.id)
        def uploadedFile = request.getFile("filepath")
        if (!uploadedFile.empty) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "upload", model: [queryInstance:queryInstance])
                    return
                }
            }
            def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def orgCode = session.org?.code
            queryService.upload(params, uploadedFile, username, orgCode)
            flash.message = "${message(code: 'default.uploaded.message', args: [message(code: 'queryInstance.label', default: 'Attachment for Query'), queryInstance.id])}"
            redirect(controller: 'query', action: "show", params: [id:queryInstance.id])
        } else {
            queryInstance.errors.reject("error", "Please choose a file")
            
            // hide the case recall categories for general file uploads
            def gencatList = CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
            gencatList.add(CaseAttachmentType.findByCode('OTHER'))
            
            render(view: "upload", model: [queryInstance:queryInstance, gencatList: gencatList])
        }
    }
    
    def download = {
        def queryAttachmentInstance = QueryAttachment.get(params.attachmentId)
        def path = queryAttachmentInstance?.fileUpload?.filePath + File.separator + queryAttachmentInstance?.fileUpload?.fileName
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
            redirect(controller: 'query', action: "show", params: [id:params.id])
        }
    }
    
    def remove = {
        queryService.remove(params)
        def queryInstance = Query.get(params.id)
        render(template: "attachmentTable", bean: queryInstance, var: "queryInstance") 
    } 
    
    def addResponse = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "show", model: [queryInstance: queryInstance])
                    return
                }
            }
            
            def queryResponseInstance = new QueryResponse(params)
            queryResponseInstance.query = queryInstance
            queryResponseInstance.responder = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()            

            def errorMap = checkResponseError(queryResponseInstance)
            errorMap.each() {key, value ->
                queryInstance.errors.reject("", value)
            }
            
            if (!queryInstance.hasErrors()) {
                queryResponseInstance.save(flush: true)
            }

            render(template: "responseTable", bean: queryInstance, var: "queryInstance")
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def address = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "edit", model: [queryInstance: queryInstance])
                    return
                }
            }
            
            queryInstance.queryStatus = QueryStatus.findByCode("ADDRESSED")
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                flash.message = "Query ${queryInstance.id} marked as Addressed"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: queryInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def resolve = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "edit", model: [queryInstance: queryInstance])
                    return
                }
            }
            
            queryInstance.queryStatus = QueryStatus.findByCode("RESOLVED")
            if (params.closed == "true") {
                queryInstance.closedBy = null
                queryInstance.dateClosed = null
            }
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                flash.message = "Query ${queryInstance.id} marked as Resolved"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: queryInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
     def unresolve = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "edit", model: [queryInstance: queryInstance])
                    return
                }
            }
            
            queryInstance.queryStatus = QueryStatus.findByCode("UNRESOLVED")
            if (params.closed == "true") {
                queryInstance.closedBy = null
                queryInstance.dateClosed = null
            }
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                flash.message = "Query ${queryInstance.id} marked as Unresolved"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: queryInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    def reactivate = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "edit", model: [queryInstance: queryInstance])
                    return
                }
            }
            
            queryInstance.queryStatus = QueryStatus.findByCode("ACTIVE")
            if (params.closed == "true") {
                queryInstance.closedBy = null
                queryInstance.dateClosed = null
            } else if (params.newQuery == "true") {
                def activityType = ActivityType.findByCode("QUERY")
                def caseId = queryInstance.caseRecord?.caseId
                def study = queryInstance.study
                def bssCode = queryInstance.organization?.code
                def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                def orgCode = queryInstance.organization?.code
                activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, queryInstance.id, orgCode)
            }
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                flash.message = "Query ${queryInstance.id} marked as Active"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: queryInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def close = {
        def queryInstance = Query.get(params.id)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    
                    queryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'query.label', default: 'Query')] as Object[], "Another user has updated this Query while you were editing")
                    render(view: "edit", model: [queryInstance: queryInstance])
                    return
                }
            }
            
            queryInstance.queryStatus = QueryStatus.findByCode("CLOSED")
            queryInstance.closedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            queryInstance.dateClosed = new Date()
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                flash.message = "Query ${queryInstance.id} marked as Closed"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: queryInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def getStudyCodeForFormList = {
        def studyCode
        def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
        
        if (caseRecordInstance) {
            if (params.organization) {
                def orgCode = Organization.get(params.organization?.id)?.code
                if (orgCode != 'VARI' && orgCode != 'BROAD') {
                    studyCode = caseRecordInstance.study?.code
                } else {
                    studyCode = "HIDE"
                }
            } else {
                studyCode = caseRecordInstance.study?.code
            }
        } else {
            studyCode = "NOCASE"
        }
        
        render studyCode
    }
    
    def getStudyCodeForCandidateFormList = {
        def studyCode
        def candidateRecordInstance = CandidateRecord.findByCandidateId(params.candidateId)
        
        if (candidateRecordInstance) {
            if (params.organization) {
                def orgCode = Organization.get(params.organization?.id)?.code
                if (orgCode != 'VARI' && orgCode != 'BROAD') {
                    studyCode = candidateRecordInstance.study?.code
                } else {
                    studyCode = "HIDE"
                }
            } else {
                studyCode = candidateRecordInstance.study?.code
            }
        } else {
            studyCode = "NOCANDIDATE"
        }
        
        render studyCode
    }
    
    def getStudyCodeForInterviewFormList = {
        def studyCode
        def interviewRecordInstance = InterviewRecord.findByInterviewId(params.interviewId)
        
        if (interviewRecordInstance) {
            studyCode = "BPVELSI"
        } else {
            studyCode = "NOCANDIDATE"
        }
        
        render studyCode
    }
    
    def updateDueDate = {
        String result
        Calendar cal = Calendar.getInstance()
        cal.set(params.newYear.toInteger(), params.newMonth.toInteger() - 1, params.newDay.toInteger())
        Date newDate = cal.getTime()
        String newDateString = newDate.format("MM/dd/yyyy")
        
        def queryInstance = Query.get(params.queryId)
        if (queryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryInstance.version > version) {
                    result = "ERROR"
                }
            }
            queryInstance.dueDate = newDate
            
            if (!queryInstance.hasErrors() && queryInstance.save(flush: true)) {
                result = newDateString
            }
            else {
                result = "ERROR"
            }
        }
        else {
            result = "ERROR"
        }
        
        render result
    }
    
    def reportFilter = {
        
    }
    
    def report = {
        def queryInstanceList = Query.createCriteria().list {
            if (params.overDue) {
                le("dueDate", (new Date()).minus(params.overDue.toInteger()))
            }
            if (params.orgFilter?.id) {
                createAlias("organization", "o")
                like("o.code", Organization.get(params.orgFilter?.id)?.code + "%")
            }
            if (params.status) {
                createAlias("queryStatus", "q")
                eq("q.name", params.status)
            }
            order("dateCreated", "desc")
        }
        return [queryInstanceList: queryInstanceList]
    }
    
    def migrate = {
        CaseRecord caseRecord
        Study study
        Organization organization
        String openedBy
        QueryStatus queryStatus
        String isDcf
        QueryType queryType
        String description
        Date dueDate
        Integer accessId
        
        def queryInstance
        def fileUploadInstance
        def queryAttachmentInstance
        
        def sql = new Sql(dataSource)
        def rows = sql.rows('select * from issue2 order by id')
        int count = 0
        
        for (row in rows) {
            if (row[1]?.endsWith('X') || row[1]?.endsWith('000')) {
                caseRecord = null
            } else {
                caseRecord = CaseRecord.findByCaseId(row[1])
            }
            
            if (caseRecord) {
                study = caseRecord.study
            } else {
                study = null
            }
            
            if (row[11] == 'UNYTS') {
                organization = Organization.findByCode('RPCI')
            } else if (row[11] && row[11] != 'DCF') {
                organization = Organization.findByCode(row[11].split('-')[0])
            } else {
                organization = null
            }
            
            switch (row[3]) {
                case 1: openedBy = 'Angela Zimmerman'
                        break
                case 2: openedBy = 'Charles Shive'
                        break
                case 3: openedBy = 'Karna Robinson'
                        break
                case 4: openedBy = 'Robin Burges'
                        break
                case 5: openedBy = 'Debra Bradbury'
                        break
                case 6: openedBy = 'Meryl Poland'
                        break
                default: openedBy = null
                         break
            }
            
            if (row[5]) {
                queryStatus = QueryStatus.findByName(row[5])
            } else {
                queryStatus = null
            }
            
            if (row[11] == 'DCF') {
                isDcf = 'Yes'
            } else {
                isDcf = 'No'
            }
            
            switch (row[6]?.charAt(1)) {
                case '1': queryType = QueryType.findByCode('MISS')
                          break
                case '2': queryType = QueryType.findByCode('DISCREP')
                          break
                case '3': queryType = QueryType.findByCode('VERIFY')
                          break
                default: queryType = null
                         break
            }
            
            description = row[8]
            
            if (row[9]) {
                dueDate = new Date(row[9].getTime())
            } else {
                dueDate = null
            }
            
            accessId = row[0]
            
            queryInstance = new Query(
                                    caseRecord:caseRecord,
                                    study:study,
                                    organization:organization,
                                    openedBy:openedBy,
                                    queryStatus:queryStatus,
                                    isDcf:isDcf,
                                    queryType:queryType,
                                    description:description,
                                    dueDate:dueDate,
                                    gtexCrf:false,
                                    gtexTrf:false,
                                    gtexConsent:false,
                                    gtexDonorEligi:false,
                                    bmsTrf:false,
                                    bpvScreening:false,
                                    bpvConsent:false,
                                    bpvBlood:false,
                                    bpvGross:false,
                                    bpvSurgery:false,
                                    bpvDissection:false,
                                    bpvWorksheet:false,
                                    bpvProcessing:false,
                                    bpvStain:false,
                                    bpvClinical:false,
                                    bpvQuality:false,
                                    accessId:accessId
                                )
            
            if (queryInstance.save(flush:true)) {
                if (row[4]) {
                    queryInstance.dateCreated = new Date(row[4].getTime())
                }
                
                if (row[12]) {
                    fileUploadInstance = new FileUpload()
                    fileUploadInstance.fileName = row[12]
                    fileUploadInstance.filePath = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + Organization.findByCode('OBBR').code + File.separator + 'query_tracker' + File.separator + queryInstance.id
                    if (row[11] == 'DCF') {
                        fileUploadInstance.category = CaseAttachmentType.findByCode('DCF')
                    } 
                    fileUploadInstance.save(flush:true)
                    queryAttachmentInstance = new QueryAttachment(fileUpload:fileUploadInstance, uploadedBy:null, query:queryInstance)
                    
                    if (queryAttachmentInstance.save(flush:true)) {
                        if (row[4]) {
                            queryAttachmentInstance.dateCreated = new Date(row[4].getTime())
                        }
                    }
                }
                
                count++
            }
        }
        
        render(count + " rows created")
    }
    
    def createDir = {
        def dirPath = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + Organization.findByCode('OBBR').code + File.separator + 'query_tracker'
        File dir = new File(dirPath)
        
        if (!dir.exists()) {
            boolean dirCreated = dir.mkdirs()
            if (dirCreated) {
                render("Target directory created.")
            } else {
                render("Failed to create the target directory.")
            }
        } else {
            render("Target directory already exists.")
        }
    }
    
    def migrateFiles = {
        def filePath
        def fileName
        File file
        File dir
        boolean fileMoved
        int count = 0
        
        Query.list().each { queryInstance ->
            queryInstance.queryAttachments.each {
                filePath = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + Organization.findByCode('OBBR').code + File.separator + 'query_tracker'
                fileName = it.fileUpload.fileName
                file = new File(filePath + File.separator + fileName)
                dir = new File(filePath + File.separator + queryInstance.id)
                dir.mkdir()
                fileMoved = file.renameTo(new File(dir, file.getName()))
                if (fileMoved) {
                    count++
                }
            }
        }
        
        render(count + " files moved")
    }
    
    Map checkError(queryInstance) {
        def errorMap = [:]
        
        if (!queryInstance.organization) {
            errorMap.put('organization', 'Please select an Organization')
        }
        if (queryInstance.organization && queryInstance.caseRecord) {
            if (queryInstance.organization?.code == 'BROAD') {
                if (queryInstance.caseRecord?.study?.code != 'GTEX' && queryInstance.caseRecord?.study?.code != 'BMS') {
                    errorMap.put('organization', 'You cannot select BROAD for Organization if the case is not a GTEX or BMS case')
                }
            } else if (queryInstance.organization?.code == 'MBB') {
                def isBrain = false
                queryInstance.caseRecord?.specimens?.each() {
                    if (it.tissueType?.code == 'BRAIN') {
                        isBrain = true
                    }
                }
                if (queryInstance.caseRecord?.study?.code != 'GTEX' || !isBrain) {
                    errorMap.put('organization', 'You cannot select MBB for Organization if the case is not a GTEX case with brain tissue')
                }
            } else if (BSS.findByCode(queryInstance.organization?.code)) {
                if (queryInstance.organization?.code?.split('-')[0] != queryInstance.caseRecord?.bss?.parentBss?.code) {
                    errorMap.put('organization', 'The case you entered does not belong to this Organization')
                }
            }
        }
        if (queryInstance.organization && queryInstance.candidateRecord) {
            if (queryInstance.organization?.code == 'VARI' || queryInstance.organization?.code == 'BROAD' || queryInstance.organization?.code == 'MBB') {
                errorMap.put('organization', 'You cannot select VARI, BROAD or MBB for Organization if this Query is for candidate')
            } else if (BSS.findByCode(queryInstance.organization?.code)) {
                if (queryInstance.organization?.code?.split('-')[0] != queryInstance.candidateRecord?.bss?.parentBss?.code) {
                    errorMap.put('organization', 'The candidate you entered does not belong to this Organization')
                }
            }
        }
        if (queryInstance.organization && queryInstance.interviewRecord) {
            if (queryInstance.organization?.code == 'VARI' || queryInstance.organization?.code == 'BROAD' || queryInstance.organization?.code == 'MBB') {
                errorMap.put('organization', 'You cannot select VARI, BROAD or MBB for Organization if this Query is for ELSI interview')
            } else if (BSS.findByCode(queryInstance.organization?.code)) {
                if (queryInstance.organization?.code?.split('-')[0] != queryInstance.interviewRecord?.orgCode) {
                    errorMap.put('organization', 'The interview you entered does not belong to this Organization')
                }
            }
        }
        if (queryInstance.isDcf != 'Yes' && queryInstance.isDcf != 'No') {
            errorMap.put('isDcf', 'Please select an option for \"Is this a DCF?\"')
        }
        if (queryInstance.isDcf == 'Yes' && !queryInstance.dcfId) {
            errorMap.put('dcfId', 'Please enter the DCF ID')
        }
        if (queryInstance.isPr2 != 'Yes' && queryInstance.isPr2 != 'No') {
            errorMap.put('isPr2', 'Please select an option for \"Is this a PR2?\"')
        }
        if (queryInstance.isPr2 == 'Yes' && !queryInstance.pr2Id) {
            errorMap.put('pr2Id', 'Please enter the PR2 ID')
        }
        if (queryInstance.organization?.code != 'VARI' && queryInstance.isPr2 == 'Yes') {
            errorMap.put('organization', 'Organization must be VARI if this is a PR2')
        }
        if (!queryInstance.queryType) {
            errorMap.put('queryType', 'Please select a Query type')
        }
        if (!queryInstance.description) {
            errorMap.put('description', 'Please enter the description')
        }
        if (!queryInstance.openedBy) {
            errorMap.put('openedBy', 'Please enter the name of the person who opened this Query')
        }
        
        return errorMap
    }
    
    Map checkResponseError(queryResponseInstance) {
        def errorMap = [:]
        
        if (!queryResponseInstance.response || !queryResponseInstance.response?.trim()) {
            errorMap.put('response', 'Response cannot be blank')
        }
        
        return errorMap
    }
}
