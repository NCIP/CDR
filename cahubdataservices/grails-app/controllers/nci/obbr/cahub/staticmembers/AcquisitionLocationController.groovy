package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 


class AcquisitionLocationController {

    def scaffold = AcquisitionLocation
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [acquisitionLocationInstanceList: AcquisitionLocation.list(params), acquisitionLocationInstanceTotal: AcquisitionLocation.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def acquisitionLocationInstance = new AcquisitionLocation()
        acquisitionLocationInstance.properties = params
        return [acquisitionLocationInstance: acquisitionLocationInstance]
    }

    def save = {
        def acquisitionLocationInstance = new AcquisitionLocation(params)
        if (acquisitionLocationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), acquisitionLocationInstance.id])}"
            redirect(action: "show", id: acquisitionLocationInstance.id)
        }
        else {
            render(view: "create", model: [acquisitionLocationInstance: acquisitionLocationInstance])
        }
    }

    def show = {
        def acquisitionLocationInstance = AcquisitionLocation.get(params.id)
        if (!acquisitionLocationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), params.id])}"
            redirect(action: "list")
        }
        else {
            [acquisitionLocationInstance: acquisitionLocationInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def acquisitionLocationInstance = AcquisitionLocation.get(params.id)
        if (!acquisitionLocationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [acquisitionLocationInstance: acquisitionLocationInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def acquisitionLocationInstance = AcquisitionLocation.get(params.id)
        if (acquisitionLocationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (acquisitionLocationInstance.version > version) {
                    
                    acquisitionLocationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation')] as Object[], "Another user has updated this AcquisitionLocation while you were editing")
                    render(view: "edit", model: [acquisitionLocationInstance: acquisitionLocationInstance])
                    return
                }
            }
            acquisitionLocationInstance.properties = params
            if (!acquisitionLocationInstance.hasErrors() && acquisitionLocationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), acquisitionLocationInstance.id])}"
                redirect(action: "show", id: acquisitionLocationInstance.id)
            }
            else {
                render(view: "edit", model: [acquisitionLocationInstance: acquisitionLocationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), params.id])}"
            redirect(action: "list")
        }
    }

    //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def acquisitionLocationInstance = AcquisitionLocation.get(params.id)
        if (acquisitionLocationInstance) {
            try {
                acquisitionLocationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'acquisitionLocation.label', default: 'AcquisitionLocation'), params.id])}"
            redirect(action: "list")
        }
    }
}
