package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class InventoryStatusController {
   def scaffold = InventoryStatus
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [inventoryStatusInstanceList: InventoryStatus.list(params), inventoryStatusInstanceTotal: InventoryStatus.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def inventoryStatusInstance = new InventoryStatus()
        inventoryStatusInstance.properties = params
        return [inventoryStatusInstance: inventoryStatusInstance]
    }

    def save = {
        def inventoryStatusInstance = new InventoryStatus(params)
        if (inventoryStatusInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), inventoryStatusInstance.id])}"
            redirect(action: "show", id: inventoryStatusInstance.id)
        }
        else {
            render(view: "create", model: [inventoryStatusInstance: inventoryStatusInstance])
        }
    }

    def show = {
        def inventoryStatusInstance = InventoryStatus.get(params.id)
        if (!inventoryStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            [inventoryStatusInstance: inventoryStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def inventoryStatusInstance = InventoryStatus.get(params.id)
        if (!inventoryStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [inventoryStatusInstance: inventoryStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def inventoryStatusInstance = InventoryStatus.get(params.id)
        if (inventoryStatusInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (inventoryStatusInstance.version > version) {
                    
                    inventoryStatusInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'inventoryStatus.label', default: 'InventoryStatus')] as Object[], "Another user has updated this InventoryStatus while you were editing")
                    render(view: "edit", model: [inventoryStatusInstance: inventoryStatusInstance])
                    return
                }
            }
            inventoryStatusInstance.properties = params
            if (!inventoryStatusInstance.hasErrors() && inventoryStatusInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), inventoryStatusInstance.id])}"
                redirect(action: "show", id: inventoryStatusInstance.id)
            }
            else {
                render(view: "edit", model: [inventoryStatusInstance: inventoryStatusInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def inventoryStatusInstance = InventoryStatus.get(params.id)
        if (inventoryStatusInstance) {
            try {
                inventoryStatusInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'inventoryStatus.label', default: 'InventoryStatus'), params.id])}"
            redirect(action: "list")
        }
    }
}
