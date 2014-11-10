package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured

class ContainerTypeController {

    def scaffold = ContainerType
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [containerTypeInstanceList: ContainerType.list(params), containerTypeInstanceTotal: ContainerType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def containerTypeInstance = new ContainerType()
        containerTypeInstance.properties = params
        return [containerTypeInstance: containerTypeInstance]
    }

    def save = {
        def containerTypeInstance = new ContainerType(params)
        if (containerTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'containerType.label', default: 'ContainerType'), containerTypeInstance.id])}"
            redirect(action: "show", id: containerTypeInstance.id)
        }
        else {
            render(view: "create", model: [containerTypeInstance: containerTypeInstance])
        }
    }

    def show = {
        def containerTypeInstance = ContainerType.get(params.id)
        if (!containerTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'containerType.label', default: 'ContainerType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [containerTypeInstance: containerTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def containerTypeInstance = ContainerType.get(params.id)
        if (!containerTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'containerType.label', default: 'ContainerType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [containerTypeInstance: containerTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def containerTypeInstance = ContainerType.get(params.id)
        if (containerTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (containerTypeInstance.version > version) {
                    
                    containerTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'containerType.label', default: 'ContainerType')] as Object[], "Another user has updated this ContainerType while you were editing")
                    render(view: "edit", model: [containerTypeInstance: containerTypeInstance])
                    return
                }
            }
            containerTypeInstance.properties = params
            if (!containerTypeInstance.hasErrors() && containerTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'containerType.label', default: 'ContainerType'), containerTypeInstance.id])}"
                redirect(action: "show", id: containerTypeInstance.id)
            }
            else {
                render(view: "edit", model: [containerTypeInstance: containerTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'containerType.label', default: 'ContainerType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def containerTypeInstance = ContainerType.get(params.id)
        if (containerTypeInstance) {
            try {
                containerTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'containerType.label', default: 'ContainerType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'containerType.label', default: 'ContainerType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'containerType.label', default: 'ContainerType'), params.id])}"
            redirect(action: "list")
        }
    }
}
