package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
  
class FormMetadataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = FormMetadata
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [formMetadataInstanceList: FormMetadata.list(params), formMetadataInstanceTotal: FormMetadata.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def formMetadataInstance = new FormMetadata()
        formMetadataInstance.properties = params
        return [formMetadataInstance: formMetadataInstance]
    }

    def save = {
        def formMetadataInstance = new FormMetadata(params)
        if (formMetadataInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), formMetadataInstance.id])}"
            redirect(action: "show", id: formMetadataInstance.id)
        }
        else {
            render(view: "create", model: [formMetadataInstance: formMetadataInstance])
        }
    }

    def show = {
        def formMetadataInstance = FormMetadata.get(params.id)
        if (!formMetadataInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), params.id])}"
            redirect(action: "list")
        }
        else {
            [formMetadataInstance: formMetadataInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def formMetadataInstance = FormMetadata.get(params.id)
        if (!formMetadataInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [formMetadataInstance: formMetadataInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def formMetadataInstance = FormMetadata.get(params.id)
        if (formMetadataInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (formMetadataInstance.version > version) {
                    
                    formMetadataInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'formMetadata.label', default: 'FormMetadata')] as Object[], "Another user has updated this FormMetadata while you were editing")
                    render(view: "edit", model: [formMetadataInstance: formMetadataInstance])
                    return
                }
            }
            formMetadataInstance.properties = params
            if (!formMetadataInstance.hasErrors() && formMetadataInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), formMetadataInstance.id])}"
                redirect(action: "show", id: formMetadataInstance.id)
            }
            else {
                render(view: "edit", model: [formMetadataInstance: formMetadataInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def formMetadataInstance = FormMetadata.get(params.id)
        if (formMetadataInstance) {
            try {
                formMetadataInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'formMetadata.label', default: 'FormMetadata'), params.id])}"
            redirect(action: "list")
        }
    }
}
