package nci.obbr.cahub.util.querytracker
import grails.plugins.springsecurity.Secured
    
class QueryAttachmentController {

    static scaffold = QueryAttachment
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [queryAttachmentInstanceList: QueryAttachment.list(params), queryAttachmentInstanceTotal: QueryAttachment.count()]
    }

    def create = {
        def queryAttachmentInstance = new QueryAttachment()
        queryAttachmentInstance.properties = params
        return [queryAttachmentInstance: queryAttachmentInstance]
    }

    def save = {
        def queryAttachmentInstance = new QueryAttachment(params)
        if (queryAttachmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), queryAttachmentInstance.id])}"
            redirect(action: "show", id: queryAttachmentInstance.id)
        }
        else {
            render(view: "create", model: [queryAttachmentInstance: queryAttachmentInstance])
        }
    }

    def show = {
        def queryAttachmentInstance = QueryAttachment.get(params.id)
        if (!queryAttachmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [queryAttachmentInstance: queryAttachmentInstance]
        }
    }

    def edit = {
        def queryAttachmentInstance = QueryAttachment.get(params.id)
        if (!queryAttachmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [queryAttachmentInstance: queryAttachmentInstance]
        }
    }

    def update = {
        def queryAttachmentInstance = QueryAttachment.get(params.id)
        if (queryAttachmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (queryAttachmentInstance.version > version) {
                    
                    queryAttachmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'queryAttachment.label', default: 'QueryAttachment')] as Object[], "Another user has updated this QueryAttachment while you were editing")
                    render(view: "edit", model: [queryAttachmentInstance: queryAttachmentInstance])
                    return
                }
            }
            queryAttachmentInstance.properties = params
            if (!queryAttachmentInstance.hasErrors() && queryAttachmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), queryAttachmentInstance.id])}"
                redirect(action: "show", id: queryAttachmentInstance.id)
            }
            else {
                render(view: "edit", model: [queryAttachmentInstance: queryAttachmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def queryAttachmentInstance = QueryAttachment.get(params.id)
        if (queryAttachmentInstance) {
            try {
                queryAttachmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'queryAttachment.label', default: 'QueryAttachment'), params.id])}"
            redirect(action: "list")
        }
    }
}
