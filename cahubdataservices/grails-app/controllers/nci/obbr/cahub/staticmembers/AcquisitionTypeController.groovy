package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured

class AcquisitionTypeController {

    def scaffold = AcquisitionType

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [acquisitionTypeInstanceList: AcquisitionType.list(params), acquisitionTypeInstanceTotal: AcquisitionType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def acquisitionTypeInstance = new AcquisitionType()
        acquisitionTypeInstance.properties = params
        return [acquisitionTypeInstance: acquisitionTypeInstance]
    }

    def save = {
        def acquisitionTypeInstance = new AcquisitionType(params)
        if (acquisitionTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), acquisitionTypeInstance.id])}"
            redirect(action: "show", id: acquisitionTypeInstance.id)
        }
        else {
            render(view: "create", model: [acquisitionTypeInstance: acquisitionTypeInstance])
        }
    }

    def show = {
        def acquisitionTypeInstance = AcquisitionType.get(params.id)
        if (!acquisitionTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [acquisitionTypeInstance: acquisitionTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def acquisitionTypeInstance = AcquisitionType.get(params.id)
        if (!acquisitionTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [acquisitionTypeInstance: acquisitionTypeInstance]
        }
    }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def acquisitionTypeInstance = AcquisitionType.get(params.id)
        if (acquisitionTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (acquisitionTypeInstance.version > version) {
                    
                    acquisitionTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'acquisitionType.label', default: 'AcquisitionType')] as Object[], "Another user has updated this AcquisitionType while you were editing")
                    render(view: "edit", model: [acquisitionTypeInstance: acquisitionTypeInstance])
                    return
                }
            }
            acquisitionTypeInstance.properties = params
            if (!acquisitionTypeInstance.hasErrors() && acquisitionTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), acquisitionTypeInstance.id])}"
                redirect(action: "show", id: acquisitionTypeInstance.id)
            }
            else {
                render(view: "edit", model: [acquisitionTypeInstance: acquisitionTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def acquisitionTypeInstance = AcquisitionType.get(params.id)
        if (acquisitionTypeInstance) {
            try {
                acquisitionTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionType.label', default: 'AcquisitionType'), params.id])}"
            redirect(action: "list")
        }
    }
}
