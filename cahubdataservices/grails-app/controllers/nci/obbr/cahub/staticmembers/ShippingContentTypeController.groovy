package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class ShippingContentTypeController {

    def scaffold = ShippingContentType
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [shippingContentTypeInstanceList: ShippingContentType.list(params), shippingContentTypeInstanceTotal: ShippingContentType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def shippingContentTypeInstance = new ShippingContentType()
        shippingContentTypeInstance.properties = params
        return [shippingContentTypeInstance: shippingContentTypeInstance]
    }

    def save = {
        def shippingContentTypeInstance = new ShippingContentType(params)
        if (shippingContentTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), shippingContentTypeInstance.id])}"
            redirect(action: "show", id: shippingContentTypeInstance.id)
        }
        else {
            render(view: "create", model: [shippingContentTypeInstance: shippingContentTypeInstance])
        }
    }

    def show = {
        def shippingContentTypeInstance = ShippingContentType.get(params.id)
        if (!shippingContentTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [shippingContentTypeInstance: shippingContentTypeInstance]
        }
    }
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def shippingContentTypeInstance = ShippingContentType.get(params.id)
        if (!shippingContentTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [shippingContentTypeInstance: shippingContentTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def shippingContentTypeInstance = ShippingContentType.get(params.id)
        if (shippingContentTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (shippingContentTypeInstance.version > version) {
                    
                    shippingContentTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'shippingContentType.label', default: 'ShippingContentType')] as Object[], "Another user has updated this ShippingContentType while you were editing")
                    render(view: "edit", model: [shippingContentTypeInstance: shippingContentTypeInstance])
                    return
                }
            }
            shippingContentTypeInstance.properties = params
            if (!shippingContentTypeInstance.hasErrors() && shippingContentTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), shippingContentTypeInstance.id])}"
                redirect(action: "show", id: shippingContentTypeInstance.id)
            }
            else {
                render(view: "edit", model: [shippingContentTypeInstance: shippingContentTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), params.id])}"
            redirect(action: "list")
        }
    }

       //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def shippingContentTypeInstance = ShippingContentType.get(params.id)
        if (shippingContentTypeInstance) {
            try {
                shippingContentTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingContentType.label', default: 'ShippingContentType'), params.id])}"
            redirect(action: "list")
        }
    }
}
