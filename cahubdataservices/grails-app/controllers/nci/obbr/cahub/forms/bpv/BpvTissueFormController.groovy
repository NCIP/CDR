package nci.obbr.cahub.forms.bpv

import grails.plugins.springsecurity.Secured

class BpvTissueFormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvTissueFormInstanceList: BpvTissueForm.list(params), bpvTissueFormInstanceTotal: BpvTissueForm.count()]
    }

    def create = {
        def bpvTissueFormInstance = new BpvTissueForm()
        bpvTissueFormInstance.properties = params
        return [bpvTissueFormInstance: bpvTissueFormInstance]
    }

    def save = {
        def bpvTissueFormInstance = new BpvTissueForm(params)
        if (bpvTissueFormInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), bpvTissueFormInstance.id])}"
            redirect(action: "show", id: bpvTissueFormInstance.id)
        }
        else {
            render(view: "create", model: [bpvTissueFormInstance: bpvTissueFormInstance])
        }
    }

    def show = {
        def bpvTissueFormInstance = BpvTissueForm.get(params.id)
        if (!bpvTissueFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), params.id])}"
            redirect(action: "list")
        }
        else {
            [bpvTissueFormInstance: bpvTissueFormInstance]
        }
    }

    def edit = {
        def bpvTissueFormInstance = BpvTissueForm.get(params.id)
        if (!bpvTissueFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [bpvTissueFormInstance: bpvTissueFormInstance]
        }
    }

    def update = {
        def bpvTissueFormInstance = BpvTissueForm.get(params.id)
        if (bpvTissueFormInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvTissueFormInstance.version > version) {
                    
                    bpvTissueFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm')] as Object[], "Another user has updated this BpvTissueForm while you were editing")
                    render(view: "edit", model: [bpvTissueFormInstance: bpvTissueFormInstance])
                    return
                }
            }
            bpvTissueFormInstance.properties = params
            if (!bpvTissueFormInstance.hasErrors() && bpvTissueFormInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), bpvTissueFormInstance.id])}"
                redirect(action: "show", id: bpvTissueFormInstance.id)
            }
            else {
                render(view: "edit", model: [bpvTissueFormInstance: bpvTissueFormInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvTissueFormInstance = BpvTissueForm.get(params.id)
        if (bpvTissueFormInstance) {
            try {
                bpvTissueFormInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvTissueForm.label', default: 'BpvTissueForm'), params.id])}"
            redirect(action: "list")
        }
    }
}
