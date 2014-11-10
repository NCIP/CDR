package nci.obbr.cahub.prc

import grails.plugins.springsecurity.Secured 

class PrcSpecimenReportController {

    def scaffold = PrcSpecimenReport
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [prcSpecimenReportInstanceList: PrcSpecimenReport.list(params), prcSpecimenReportInstanceTotal: PrcSpecimenReport.count()]
    }

    def create = {
        def prcSpecimenReportInstance = new PrcSpecimenReport()
        prcSpecimenReportInstance.properties = params
        return [prcSpecimenReportInstance: prcSpecimenReportInstance]
    }

    def save = {
        def prcSpecimenReportInstance = new PrcSpecimenReport(params)
        if (prcSpecimenReportInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), prcSpecimenReportInstance.id])}"
            redirect(action: "edit", id: prcSpecimenReportInstance.id)
        }
        else {
            render(view: "create", model: [prcSpecimenReportInstance: prcSpecimenReportInstance])
        }
    }

    def show = {
        def prcSpecimenReportInstance = PrcSpecimenReport.get(params.id)
        if (!prcSpecimenReportInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), params.id])}"
            redirect(action: "list")
        }
        else {
            [prcSpecimenReportInstance: prcSpecimenReportInstance]
        }
    }

    def edit = {
        def prcSpecimenReportInstance = PrcSpecimenReport.get(params.id)
        if (!prcSpecimenReportInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [prcSpecimenReportInstance: prcSpecimenReportInstance]
        }
    }

    def update = {
        def prcSpecimenReportInstance = PrcSpecimenReport.get(params.id)
        if (prcSpecimenReportInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prcSpecimenReportInstance.version > version) {
                    
                    prcSpecimenReportInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport')] as Object[], "Another user has updated this PrcSpecimenReport while you were editing")
                    render(view: "edit", model: [prcSpecimenReportInstance: prcSpecimenReportInstance])
                    return
                }
            }
            prcSpecimenReportInstance.properties = params
            if (!prcSpecimenReportInstance.hasErrors() && prcSpecimenReportInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), prcSpecimenReportInstance.id])}"
                redirect(action: "edit", id: prcSpecimenReportInstance.id)
            }
            else {
                render(view: "edit", model: [prcSpecimenReportInstance: prcSpecimenReportInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), params.id])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB','ROLE_ADMIN']) 
    def delete = {
        def prcSpecimenReportInstance = PrcSpecimenReport.get(params.id)
        if (prcSpecimenReportInstance) {
            try {
                prcSpecimenReportInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'PrcSpecimenReport.label', default: 'PrcSpecimenReport'), params.id])}"
            redirect(action: "list")
        }
    }
}
