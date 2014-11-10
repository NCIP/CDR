package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class KitTypeController {

    def scaffold = KitType
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [kitTypeInstanceList: KitType.list(params), kitTypeInstanceTotal: KitType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def kitTypeInstance = new KitType()
        kitTypeInstance.properties = params
        return [kitTypeInstance: kitTypeInstance]
    }

    def save = {
        def kitTypeInstance = new KitType(params)
        if (kitTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'kitType.label', default: 'KitType'), kitTypeInstance.id])}"
            redirect(action: "show", id: kitTypeInstance.id)
        }
        else {
            render(view: "create", model: [kitTypeInstance: kitTypeInstance])
        }
    }

    def show = {
        def kitTypeInstance = KitType.get(params.id)
        if (!kitTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitType.label', default: 'KitType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [kitTypeInstance: kitTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def kitTypeInstance = KitType.get(params.id)
        if (!kitTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitType.label', default: 'KitType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [kitTypeInstance: kitTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def kitTypeInstance = KitType.get(params.id)
        if (kitTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (kitTypeInstance.version > version) {
                    
                    kitTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'kitType.label', default: 'KitType')] as Object[], "Another user has updated this KitType while you were editing")
                    render(view: "edit", model: [kitTypeInstance: kitTypeInstance])
                    return
                }
            }
            kitTypeInstance.properties = params
            if (!kitTypeInstance.hasErrors() && kitTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'kitType.label', default: 'KitType'), kitTypeInstance.id])}"
                redirect(action: "show", id: kitTypeInstance.id)
            }
            else {
                render(view: "edit", model: [kitTypeInstance: kitTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitType.label', default: 'KitType'), params.id])}"
            redirect(action: "list")
        }
    }

       //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def kitTypeInstance = KitType.get(params.id)
        if (kitTypeInstance) {
            try {
                kitTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'kitType.label', default: 'KitType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'kitType.label', default: 'KitType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitType.label', default: 'KitType'), params.id])}"
            redirect(action: "list")
        }
    }
}
