package nci.obbr.cahub.util.querytracker

import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.staticmembers.CaseAttachmentType
import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.util.FileUpload
import grails.plugins.springsecurity.Secured 

class DeviationController {

    static scaffold = Deviation
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def deviationService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def deviationInstanceList
        def deviationInstanceTotal = 0
        
        if (session.org?.code == 'OBBR') {
            if (params.studyId) {
                def deviationInstanceTotalList = Deviation.createCriteria().list() {
                    createAlias("caseRecord", "c")
                    eq("c.study", Study.get(params.studyId))
                }
                if (params.sort == "caseRecord.caseId") {
                    /* To avoid "duplicate association path" error, remove "sort" and "order" from params first,
                    explicitly add "order" in criteria, then add "sort" and "order" back */
                    params.remove("sort")
                    def caseOrder = params.order
                    params.remove("order")
                    deviationInstanceList = Deviation.createCriteria().list(params) {
                        createAlias("caseRecord", "c")
                        eq("c.study", Study.get(params.studyId))
                        order("c.caseId", caseOrder)
                    }
                    params.put("sort", "caseRecord.caseId")
                    params.put("order", caseOrder)
                } else {
                    deviationInstanceList = Deviation.createCriteria().list(params) {
                        createAlias("caseRecord", "c")
                        eq("c.study", Study.get(params.studyId))
                    }
                }                
                deviationInstanceTotal = deviationInstanceTotalList.size()
            } else {
                deviationInstanceList = Deviation.list(params)
                deviationInstanceTotal = Deviation.count() 
            }
        } else if (BSS.findByCode(session.org?.code)) {
            def deviationInstanceTotalList = Deviation.createCriteria().list() {
                eq("bss", session.org)
            }
            deviationInstanceList = Deviation.createCriteria().list(params) {
                eq("bss", session.org)
            }
            deviationInstanceTotal = deviationInstanceTotalList.size()
        }
        
        [deviationInstanceList: deviationInstanceList, deviationInstanceTotal: deviationInstanceTotal]
    }
    
    def listByCase = {
        if (!params.caseRecord?.id) {
            redirect(action: "list", params: params)
        } else {
            def deviationInstanceList
            deviationInstanceList = Deviation.createCriteria().list {
                createAlias("caseRecord", "c")
                eq("c.id", Long.parseLong(params.caseRecord?.id))
                order("dateCreated", "desc")
            } 
            render(view: "list", model: [deviationInstanceList: deviationInstanceList])
        }
    }
    
    def create = {
        def deviationInstance = new Deviation()
        deviationInstance.properties = params
        def queryList = generateQueryList(deviationInstance.caseRecord)
        return [deviationInstance: deviationInstance, queryList: queryList]
    }

    def save = {
        def deviationInstance = new Deviation(params)
        
        def errorMap = checkError(deviationInstance)
        errorMap.each() {key, value ->
            deviationInstance.errors.rejectValue(key, value)
        }
        
        if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
            if (deviationInstance.caseRecord) {
                deviationInstance.bss = deviationInstance.caseRecord?.bss?.parentBss
            }
            deviationService.saveQueries(deviationInstance, params)
            deviationInstance.queryStatus = QueryStatus.findByCode("OPEN")
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviation.label', default: 'Deviation'), deviationInstance.id])}"
            redirect(action: "show", id: deviationInstance.id)
        }
        else {
            def queryList = generateQueryList(deviationInstance.caseRecord)
            render(view: "create", model: [deviationInstance: deviationInstance, queryList: queryList])
        }
    }

    def show = {
        def deviationInstance = Deviation.get(params.id)
        if (!deviationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
        else {
            def queryList = generateQueryList(deviationInstance.caseRecord)
            [deviationInstance: deviationInstance, queryList: queryList]
        }
    }

    def edit = {
        def deviationInstance = Deviation.get(params.id)
        if (!deviationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
        else {
            def queryList = generateQueryList(deviationInstance.caseRecord)
            return [deviationInstance: deviationInstance, queryList: queryList]
        }
    }

    def update = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "edit", model: [deviationInstance: deviationInstance])
                    return
                }
            }
            deviationInstance.properties = params
           // params.each{key,value->
             //   println "deviation  key: ${key}   value:${value}"
            //}
            if(params.planned == 'No'){
                deviationInstance?.memoCiNum=null
                deviationInstance?.memoExpiration=null
            }
            if(params.nonConformance== 'No'){
                deviationInstance?.nonConformCapaNum=null
                deviationInstance?.capaCloseDate=null
            }
            deviationService.saveQueries(deviationInstance, params)
            
            def errorMap = checkError(deviationInstance)
            errorMap.each() {key, value ->
                deviationInstance.errors.rejectValue(key, value)
            }
            
            if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviation.label', default: 'Deviation'), deviationInstance.id])}"
                redirect(action: "show", id: deviationInstance.id)
            }
            else {
                deviationInstance.discard()
                def queryList = generateQueryList(deviationInstance.caseRecord)
                render(view: "edit", model: [deviationInstance: deviationInstance, queryList: queryList])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            try {
                deviationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def upload = {
        def deviationInstance = Deviation.get(params.id)
        
        // hide the case recall categories for general file uploads
        def gencatList = CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
        gencatList.add(CaseAttachmentType.findByCode('OTHER'))
        
        return [deviationInstance: deviationInstance, gencatList: gencatList]
    }
    
    def upload_save = {
        def deviationInstance = Deviation.get(params.id)
        def uploadedFile = request.getFile("filepath")
        if (!uploadedFile.empty) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "upload", model: [deviationInstance:deviationInstance])
                    return
                }
            }
            def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def orgCode = session.org?.code
            deviationService.upload(params, uploadedFile, username, orgCode)
            flash.message = "${message(code: 'default.uploaded.message', args: [message(code: 'deviationInstance.label', default: 'Attachment for Deviation'), deviationInstance.id])}"
            redirect(controller: 'deviation', action: "show", params: [id:deviationInstance.id])
        } else {
            deviationInstance.errors.reject("error", "Please choose a file")
            
            // hide the case recall categories for general file uploads
            def gencatList = CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
            gencatList.add(CaseAttachmentType.findByCode('OTHER'))
            
            render(view: "upload", model: [deviationInstance:deviationInstance, gencatList: gencatList])
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
            redirect(controller: 'deviation', action: "show", params: [id:params.id])
        }
    }
    
    def remove = {
        deviationService.remove(params)
        def deviationInstance = Deviation.get(params.id)
        render(template: "attachmentTable", bean: deviationInstance, var: "deviationInstance") 
    }
    
    def addResponse = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    def queryList = generateQueryList(deviationInstance.caseRecord)
                    render(view: "show", model: [deviationInstance: deviationInstance, queryList: queryList])
                    return
                }
            }
            
            def queryResponseInstance = new QueryResponse(params)
            queryResponseInstance.deviation = deviationInstance
            queryResponseInstance.responder = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()            

            def errorMap = checkResponseError(queryResponseInstance)
            errorMap.each() {key, value ->
                deviationInstance.errors.reject("", value)
            }
            
            if (!deviationInstance.hasErrors()) {
                queryResponseInstance.save(flush: true)
            }

            render(template: "responseTable", bean: deviationInstance, var: "deviationInstance")
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def progress = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "edit", model: [deviationInstance: deviationInstance])
                    return
                }
            }
            
            deviationInstance.queryStatus = QueryStatus.findByCode("PROGRESS")
            
            if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
                flash.message = "Deviation ${deviationInstance.id} marked as In Progress"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: deviationInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def resolve = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "edit", model: [deviationInstance: deviationInstance])
                    return
                }
            }
            
            deviationInstance.queryStatus = QueryStatus.findByCode("RESOLVED")
            
            if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
                flash.message = "Deviation ${deviationInstance.id} marked as Resolved"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: deviationInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def reopen = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "edit", model: [deviationInstance: deviationInstance])
                    return
                }
            }
            
            deviationInstance.queryStatus = QueryStatus.findByCode("OPEN")
            
            if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
                flash.message = "Deviation ${deviationInstance.id} marked as Open"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: deviationInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def close = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "edit", model: [deviationInstance: deviationInstance])
                    return
                }
            }
            
            deviationInstance.queryStatus = QueryStatus.findByCode("CLOSED")
            
            if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
                flash.message = "Deviation ${deviationInstance.id} marked as Closed"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: deviationInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def invalidate = {
        def deviationInstance = Deviation.get(params.id)
        if (deviationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviationInstance.version > version) {
                    
                    deviationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviation.label', default: 'Deviation')] as Object[], "Another user has updated this Deviation while you were editing")
                    render(view: "edit", model: [deviationInstance: deviationInstance])
                    return
                }
            }
            
            deviationInstance.queryStatus = QueryStatus.findByCode("INVALID")
            
            if (!deviationInstance.hasErrors() && deviationInstance.save(flush: true)) {
                flash.message = "Deviation ${deviationInstance.id} marked as Invalidated"
            }
            else {
                flash.message = "error"
            }
            
            redirect(action: "show", id: deviationInstance.id)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviation.label', default: 'Deviation'), params.id])}"
            redirect(action: "list")
        }
    }
    
    List generateQueryList(caseRecordInstance) {
        def queryList = []
        if (caseRecordInstance) {
            queryList = Query.createCriteria().list {
                createAlias("caseRecord", "c")
                eq("c.id", caseRecordInstance.id)
                isNotNull("queryStatus")
                order("dateCreated", "desc")
            }
        }
        
        return queryList
    }
    
    Map checkError(deviationInstance) {
        def errorMap = [:]
        
        if (!deviationInstance.caseRecord) {
            errorMap.put('caseRecord', '\"Case Record\" is required')
        }
        if (!deviationInstance.description) {
            errorMap.put('description', '\"Description\" is required')
        }
        if (deviationInstance.planned == 'No' && (deviationInstance.nonConformance != 'Yes' && deviationInstance.nonConformance != 'No')) {
            errorMap.put('nonConformance', 'Please select an option for \"Non conformance issued?\"')
        }
        
        if (deviationInstance.planned == 'No' && !deviationInstance.jiraId ) {
            errorMap.put('jiraId', 'Please enter JIRA ID(s)\"')
        }
        // if (deviationInstance.planned == 'No' && !deviationInstance.jiraCloseDate) {
        //   errorMap.put('jiraCloseDate', 'Please enter JIRA close date\"')
        // }
        if (deviationInstance.nonConformance == 'Yes' && !deviationInstance.nonConformCapaNum) {
            errorMap.put('nonConformCapaNum', 'Please enter Non Conformance CAPPA Number')
        }
        //if (deviationInstance.nonConformance == 'Yes' && !deviationInstance.capaCloseDate ) {
        //     errorMap.put('capaCloseDate', 'Please enter Non Conformance CAPPA close date')
        // }
        if (deviationInstance.type != 'Minor' && deviationInstance.type != 'Major' && deviationInstance.type != 'Critical') {
            errorMap.put('type', 'Please select an option for \"Type\"')
        }
        if (!deviationInstance.dateDeviation) {
            errorMap.put('dateDeviation', 'Please select the \"Date of Deviation\"')
        }
        //if (deviationInstance.jiraId && !deviationInstance.jiraCloseDate) {
        //   errorMap.put('jiraCloseDate', 'Please enter date all JIRA Issue(s) Closed')
        // }
        
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
