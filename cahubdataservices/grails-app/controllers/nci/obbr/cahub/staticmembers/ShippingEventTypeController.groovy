package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class ShippingEventTypeController {

    def scaffold = ShippingEventType
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [shippingEventTypeInstanceList: ShippingEventType.list(params), shippingEventTypeInstanceTotal: ShippingEventType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def shippingEventTypeInstance = new ShippingEventType()
        shippingEventTypeInstance.properties = params
        return [shippingEventTypeInstance: shippingEventTypeInstance]
    }

    def save = {
        def shippingEventTypeInstance = new ShippingEventType(params)
        if (shippingEventTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), shippingEventTypeInstance.id])}"
            redirect(action: "show", id: shippingEventTypeInstance.id)
        }
        else {
            render(view: "create", model: [shippingEventTypeInstance: shippingEventTypeInstance])
        }
    }

    def show = {
        def shippingEventTypeInstance = ShippingEventType.get(params.id)
        if (!shippingEventTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [shippingEventTypeInstance: shippingEventTypeInstance]
        }
    }
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def shippingEventTypeInstance = ShippingEventType.get(params.id)
        if (!shippingEventTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [shippingEventTypeInstance: shippingEventTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def shippingEventTypeInstance = ShippingEventType.get(params.id)
        if (shippingEventTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (shippingEventTypeInstance.version > version) {
                    
                    shippingEventTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'shippingEventType.label', default: 'ShippingEventType')] as Object[], "Another user has updated this ShippingEventType while you were editing")
                    render(view: "edit", model: [shippingEventTypeInstance: shippingEventTypeInstance])
                    return
                }
            }
            shippingEventTypeInstance.properties = params
            if (!shippingEventTypeInstance.hasErrors() && shippingEventTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), shippingEventTypeInstance.id])}"
                redirect(action: "show", id: shippingEventTypeInstance.id)
            }
            else {
                render(view: "edit", model: [shippingEventTypeInstance: shippingEventTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), params.id])}"
            redirect(action: "list")
        }
    }

       //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def shippingEventTypeInstance = ShippingEventType.get(params.id)
        if (shippingEventTypeInstance) {
            try {
                shippingEventTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEventType.label', default: 'ShippingEventType'), params.id])}"
            redirect(action: "list")
        }
    }
}
