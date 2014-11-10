package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
   
class QueryStatusController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = QueryStatus

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [queryStatusInstanceList: QueryStatus.list(params), queryStatusInstanceTotal: QueryStatus.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def queryStatusInstance = new QueryStatus()
        queryStatusInstance.properties = params
        return [queryStatusInstance: queryStatusInstance]
    }

    def save = {
        def queryStatusInstance = new QueryStatus(params)
        if (queryStatusInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), queryStatusInstance.id])}"
            redirect(action: "show", id: queryStatusInstance.id)
        }
        else {
            render(view: "create", model: [queryStatusInstance: queryStatusInstance])
        }
    }

    def show = {
        def queryStatusInstance = QueryStatus.get(params.id)
        if (!queryStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            [queryStatusInstance: queryStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def queryStatusInstance = QueryStatus.get(params.id)
        if (!queryStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [queryStatusInstance: queryStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def queryStatusInstance = QueryStatus.get(params.id)
        if (queryStatusInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryStatusInstance.version > version) {
                    
                    queryStatusInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'queryStatus.label', default: 'QueryStatus')] as Object[], "Another user has updated this QueryStatus while you were editing")
                    render(view: "edit", model: [queryStatusInstance: queryStatusInstance])
                    return
                }
            }
            queryStatusInstance.properties = params
            if (!queryStatusInstance.hasErrors() && queryStatusInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), queryStatusInstance.id])}"
                redirect(action: "show", id: queryStatusInstance.id)
            }
            else {
                render(view: "edit", model: [queryStatusInstance: queryStatusInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def queryStatusInstance = QueryStatus.get(params.id)
        if (queryStatusInstance) {
            try {
                queryStatusInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryStatus.label', default: 'QueryStatus'), params.id])}"
            redirect(action: "list")
        }
    }
}
