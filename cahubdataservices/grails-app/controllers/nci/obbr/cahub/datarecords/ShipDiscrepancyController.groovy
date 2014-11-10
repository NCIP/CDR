package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured 

class ShipDiscrepancyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = ShipDiscrepancy

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [shipDiscrepancyInstanceList: ShipDiscrepancy.list(params), shipDiscrepancyInstanceTotal: ShipDiscrepancy.count()]
    }

    def create = {
        def shipDiscrepancyInstance = new ShipDiscrepancy()
        shipDiscrepancyInstance.properties = params
        return [shipDiscrepancyInstance: shipDiscrepancyInstance]
    }

    def save = {
        def shipDiscrepancyInstance = new ShipDiscrepancy(params)
        if (shipDiscrepancyInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), shipDiscrepancyInstance.id])}"
            redirect(action: "show", id: shipDiscrepancyInstance.id)
        }
        else {
            render(view: "create", model: [shipDiscrepancyInstance: shipDiscrepancyInstance])
        }
    }

    def show = {
        def shipDiscrepancyInstance = ShipDiscrepancy.get(params.id)
        if (!shipDiscrepancyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), params.id])}"
            redirect(action: "list")
        }
        else {
            [shipDiscrepancyInstance: shipDiscrepancyInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def shipDiscrepancyInstance = ShipDiscrepancy.get(params.id)
        if (!shipDiscrepancyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [shipDiscrepancyInstance: shipDiscrepancyInstance]
        }
    }

    def update = {
        def shipDiscrepancyInstance = ShipDiscrepancy.get(params.id)
        if (shipDiscrepancyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (shipDiscrepancyInstance.version > version) {
                    
                    shipDiscrepancyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy')] as Object[], "Another user has updated this ShipDiscrepancy while you were editing")
                    render(view: "edit", model: [shipDiscrepancyInstance: shipDiscrepancyInstance])
                    return
                }
            }
            shipDiscrepancyInstance.properties = params
            if (!shipDiscrepancyInstance.hasErrors() && shipDiscrepancyInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), shipDiscrepancyInstance.id])}"
                redirect(action: "show", id: shipDiscrepancyInstance.id)
            }
            else {
                render(view: "edit", model: [shipDiscrepancyInstance: shipDiscrepancyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def shipDiscrepancyInstance = ShipDiscrepancy.get(params.id)
        if (shipDiscrepancyInstance) {
            try {
                shipDiscrepancyInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy'), params.id])}"
            redirect(action: "list")
        }
    }
}
