package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured

class CaseAttachmentTypeController {

    def scaffold = CaseAttachmentType
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [caseAttachmentTypeInstanceList: CaseAttachmentType.list(params), caseAttachmentTypeInstanceTotal: CaseAttachmentType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def caseAttachmentTypeInstance = new CaseAttachmentType()
        caseAttachmentTypeInstance.properties = params
        return [caseAttachmentTypeInstance: caseAttachmentTypeInstance]
    }

    def save = {
        def caseAttachmentTypeInstance = new CaseAttachmentType(params)
        if (caseAttachmentTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), caseAttachmentTypeInstance.id])}"
            redirect(action: "show", id: caseAttachmentTypeInstance.id)
        }
        else {
            render(view: "create", model: [caseAttachmentTypeInstance: caseAttachmentTypeInstance])
        }
    }

    def show = {
        def caseAttachmentTypeInstance = CaseAttachmentType.get(params.id)
        if (!caseAttachmentTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [caseAttachmentTypeInstance: caseAttachmentTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def caseAttachmentTypeInstance = CaseAttachmentType.get(params.id)
        if (!caseAttachmentTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [caseAttachmentTypeInstance: caseAttachmentTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def caseAttachmentTypeInstance = CaseAttachmentType.get(params.id)
        if (caseAttachmentTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (caseAttachmentTypeInstance.version > version) {
                    
                    caseAttachmentTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType')] as Object[], "Another user has updated this CaseAttachmentType while you were editing")
                    render(view: "edit", model: [caseAttachmentTypeInstance: caseAttachmentTypeInstance])
                    return
                }
            }
            caseAttachmentTypeInstance.properties = params
            if (!caseAttachmentTypeInstance.hasErrors() && caseAttachmentTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), caseAttachmentTypeInstance.id])}"
                redirect(action: "show", id: caseAttachmentTypeInstance.id)
            }
            else {
                render(view: "edit", model: [caseAttachmentTypeInstance: caseAttachmentTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def caseAttachmentTypeInstance = CaseAttachmentType.get(params.id)
        if (caseAttachmentTypeInstance) {
            try {
                caseAttachmentTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseAttachmentType.label', default: 'CaseAttachmentType'), params.id])}"
            redirect(action: "list")
        }
    }
}
