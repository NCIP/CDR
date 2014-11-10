package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
 
class QueryTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = QueryType

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [queryTypeInstanceList: QueryType.list(params), queryTypeInstanceTotal: QueryType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def queryTypeInstance = new QueryType()
        queryTypeInstance.properties = params
        return [queryTypeInstance: queryTypeInstance]
    }

    def save = {
        def queryTypeInstance = new QueryType(params)
        if (queryTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'queryType.label', default: 'QueryType'), queryTypeInstance.id])}"
            redirect(action: "show", id: queryTypeInstance.id)
        }
        else {
            render(view: "create", model: [queryTypeInstance: queryTypeInstance])
        }
    }

    def show = {
        def queryTypeInstance = QueryType.get(params.id)
        if (!queryTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryType.label', default: 'QueryType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [queryTypeInstance: queryTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def queryTypeInstance = QueryType.get(params.id)
        if (!queryTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryType.label', default: 'QueryType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [queryTypeInstance: queryTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def queryTypeInstance = QueryType.get(params.id)
        if (queryTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryTypeInstance.version > version) {
                    
                    queryTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'queryType.label', default: 'QueryType')] as Object[], "Another user has updated this QueryType while you were editing")
                    render(view: "edit", model: [queryTypeInstance: queryTypeInstance])
                    return
                }
            }
            queryTypeInstance.properties = params
            if (!queryTypeInstance.hasErrors() && queryTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'queryType.label', default: 'QueryType'), queryTypeInstance.id])}"
                redirect(action: "show", id: queryTypeInstance.id)
            }
            else {
                render(view: "edit", model: [queryTypeInstance: queryTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryType.label', default: 'QueryType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def queryTypeInstance = QueryType.get(params.id)
        if (queryTypeInstance) {
            try {
                queryTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'queryType.label', default: 'QueryType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'queryType.label', default: 'QueryType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryType.label', default: 'QueryType'), params.id])}"
            redirect(action: "list")
        }
    }
}
