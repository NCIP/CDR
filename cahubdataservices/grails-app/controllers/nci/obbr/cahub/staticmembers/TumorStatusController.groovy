package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured

class TumorStatusController {

    def scaffold = TumorStatus
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tumorStatusInstanceList: TumorStatus.list(params), tumorStatusInstanceTotal: TumorStatus.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def tumorStatusInstance = new TumorStatus()
        tumorStatusInstance.properties = params
        return [tumorStatusInstance: tumorStatusInstance]
    }

    def save = {
        def tumorStatusInstance = new TumorStatus(params)
        if (tumorStatusInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), tumorStatusInstance.id])}"
            redirect(action: "show", id: tumorStatusInstance.id)
        }
        else {
            render(view: "create", model: [tumorStatusInstance: tumorStatusInstance])
        }
    }

    def show = {
        def tumorStatusInstance = TumorStatus.get(params.id)
        if (!tumorStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tumorStatusInstance: tumorStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def tumorStatusInstance = TumorStatus.get(params.id)
        if (!tumorStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tumorStatusInstance: tumorStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def tumorStatusInstance = TumorStatus.get(params.id)
        if (tumorStatusInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tumorStatusInstance.version > version) {
                    
                    tumorStatusInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tumorStatus.label', default: 'TumorStatus')] as Object[], "Another user has updated this TumorStatus while you were editing")
                    render(view: "edit", model: [tumorStatusInstance: tumorStatusInstance])
                    return
                }
            }
            tumorStatusInstance.properties = params
            if (!tumorStatusInstance.hasErrors() && tumorStatusInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), tumorStatusInstance.id])}"
                redirect(action: "show", id: tumorStatusInstance.id)
            }
            else {
                render(view: "edit", model: [tumorStatusInstance: tumorStatusInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def tumorStatusInstance = TumorStatus.get(params.id)
        if (tumorStatusInstance) {
            try {
                tumorStatusInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tumorStatus.label', default: 'TumorStatus'), params.id])}"
            redirect(action: "list")
        }
    }
}
