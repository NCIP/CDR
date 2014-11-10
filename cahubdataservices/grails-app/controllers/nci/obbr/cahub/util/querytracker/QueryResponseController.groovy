package nci.obbr.cahub.util.querytracker
import grails.plugins.springsecurity.Secured
    
class QueryResponseController {

    static scaffold = QueryResponse
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [queryResponseInstanceList: QueryResponse.list(params), queryResponseInstanceTotal: QueryResponse.count()]
    }

    def create = {
        def queryResponseInstance = new QueryResponse()
        queryResponseInstance.properties = params
        return [queryResponseInstance: queryResponseInstance]
    }

    def save = {
        def queryResponseInstance = new QueryResponse(params)
        if (queryResponseInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), queryResponseInstance.id])}"
            redirect(action: "show", id: queryResponseInstance.id)
        }
        else {
            render(view: "create", model: [queryResponseInstance: queryResponseInstance])
        }
    }

    def show = {
        def queryResponseInstance = QueryResponse.get(params.id)
        if (!queryResponseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), params.id])}"
            redirect(action: "list")
        }
        else {
            [queryResponseInstance: queryResponseInstance]
        }
    }

    def edit = {
        def queryResponseInstance = QueryResponse.get(params.id)
        if (!queryResponseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [queryResponseInstance: queryResponseInstance]
        }
    }

    def update = {
        def queryResponseInstance = QueryResponse.get(params.id)
        if (queryResponseInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryResponseInstance.version > version) {
                    
                    queryResponseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'queryResponse.label', default: 'QueryResponse')] as Object[], "Another user has updated this QueryResponse while you were editing")
                    render(view: "edit", model: [queryResponseInstance: queryResponseInstance])
                    return
                }
            }
            queryResponseInstance.properties = params
            if (!queryResponseInstance.hasErrors() && queryResponseInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), queryResponseInstance.id])}"
                redirect(action: "show", id: queryResponseInstance.id)
            }
            else {
                render(view: "edit", model: [queryResponseInstance: queryResponseInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def queryResponseInstance = QueryResponse.get(params.id)
        if (queryResponseInstance) {
            try {
                queryResponseInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryResponse.label', default: 'QueryResponse'), params.id])}"
            redirect(action: "list")
        }
    }
}
