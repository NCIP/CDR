package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class CaseStatusController {

    def scaffold = CaseStatus

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [caseStatusInstanceList: CaseStatus.list(params), caseStatusInstanceTotal: CaseStatus.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def caseStatusInstance = new CaseStatus()
        caseStatusInstance.properties = params
        return [caseStatusInstance: caseStatusInstance]
    }

    def save = {
        def caseStatusInstance = new CaseStatus(params)
        if (caseStatusInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), caseStatusInstance.id])}"
            redirect(action: "show", id: caseStatusInstance.id)
        }
        else {
            render(view: "create", model: [caseStatusInstance: caseStatusInstance])
        }
    }

    def show = {
        def caseStatusInstance = CaseStatus.get(params.id)
        if (!caseStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            [caseStatusInstance: caseStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def caseStatusInstance = CaseStatus.get(params.id)
        if (!caseStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [caseStatusInstance: caseStatusInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def caseStatusInstance = CaseStatus.get(params.id)
        if (caseStatusInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (caseStatusInstance.version > version) {
                    
                    caseStatusInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseStatus.label', default: 'CaseStatus')] as Object[], "Another user has updated this CaseStatus while you were editing")
                    render(view: "edit", model: [caseStatusInstance: caseStatusInstance])
                    return
                }
            }
            caseStatusInstance.properties = params
            if (!caseStatusInstance.hasErrors() && caseStatusInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), caseStatusInstance.id])}"
                redirect(action: "show", id: caseStatusInstance.id)
            }
            else {
                render(view: "edit", model: [caseStatusInstance: caseStatusInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), params.id])}"
            redirect(action: "list")
        }
    }

     //We don't want anyone else to delete
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])   
    def delete = {
        def caseStatusInstance = CaseStatus.get(params.id)
        if (caseStatusInstance) {
            try {
                caseStatusInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStatus.label', default: 'CaseStatus'), params.id])}"
            redirect(action: "list")
        }
    }
}
