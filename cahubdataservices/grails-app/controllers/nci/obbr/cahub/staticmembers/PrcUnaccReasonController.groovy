package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class PrcUnaccReasonController {

    def scaffold = PrcUnaccReason
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [prcUnaccReasonInstanceList: PrcUnaccReason.list(params), prcUnaccReasonInstanceTotal: PrcUnaccReason.count()]
    }

    def create = {
        def prcUnaccReasonInstance = new PrcUnaccReason()
        prcUnaccReasonInstance.properties = params
        return [prcUnaccReasonInstance: prcUnaccReasonInstance]
    }

    def save = {
        def prcUnaccReasonInstance = new PrcUnaccReason(params)
        if (prcUnaccReasonInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), prcUnaccReasonInstance.id])}"
            redirect(action: "show", id: prcUnaccReasonInstance.id)
        }
        else {
            render(view: "create", model: [prcUnaccReasonInstance: prcUnaccReasonInstance])
        }
    }

    def show = {
        def prcUnaccReasonInstance = PrcUnaccReason.get(params.id)
        if (!prcUnaccReasonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), params.id])}"
            redirect(action: "list")
        }
        else {
            [prcUnaccReasonInstance: prcUnaccReasonInstance]
        }
    }

    def edit = {
        def prcUnaccReasonInstance = PrcUnaccReason.get(params.id)
        if (!prcUnaccReasonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [prcUnaccReasonInstance: prcUnaccReasonInstance]
        }
    }

    def update = {
        def prcUnaccReasonInstance = PrcUnaccReason.get(params.id)
        if (prcUnaccReasonInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prcUnaccReasonInstance.version > version) {
                    
                    prcUnaccReasonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason')] as Object[], "Another user has updated this PrcUnaccReason while you were editing")
                    render(view: "edit", model: [prcUnaccReasonInstance: prcUnaccReasonInstance])
                    return
                }
            }
            prcUnaccReasonInstance.properties = params
            if (!prcUnaccReasonInstance.hasErrors() && prcUnaccReasonInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), prcUnaccReasonInstance.id])}"
                redirect(action: "show", id: prcUnaccReasonInstance.id)
            }
            else {
                render(view: "edit", model: [prcUnaccReasonInstance: prcUnaccReasonInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), params.id])}"
            redirect(action: "list")
        }
    }

     @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def prcUnaccReasonInstance = PrcUnaccReason.get(params.id)
        if (prcUnaccReasonInstance) {
            try {
                prcUnaccReasonInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcUnaccReason.label', default: 'PrcUnaccReason'), params.id])}"
            redirect(action: "list")
        }
    }
}
