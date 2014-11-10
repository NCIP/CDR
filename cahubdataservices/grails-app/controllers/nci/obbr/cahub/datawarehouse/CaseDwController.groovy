package nci.obbr.cahub.datawarehouse

import grails.plugins.springsecurity.Secured

class CaseDwController {

    def scaffold = CaseDw
    def caseDwService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [caseDwInstanceList: CaseDw.list(params), caseDwInstanceTotal: CaseDw.count()]
    }

    def create = {
        def caseDwInstance = new CaseDw()
        caseDwInstance.properties = params
        caseDwInstance = caseDwService.populateCaseDw(caseDwInstance) 
        redirect(action: "list", id: caseDwInstance.id)
//        return [caseDwInstance: caseDwInstance]
    }

    def save = {
        def caseDwInstance = new CaseDw(params)
        if (caseDwInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), caseDwInstance.id])}"
            redirect(action: "show", id: caseDwInstance.id)
        }
        else {
            render(view: "create", model: [caseDwInstance: caseDwInstance])
        }
    }

    def show = {
        def caseDwInstance = CaseDw.get(params.id)
        if (!caseDwInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}"
            redirect(action: "list")
        }
        else {
            [caseDwInstance: caseDwInstance]
        }
    }

    def edit = {
        def caseDwInstance = CaseDw.get(params.id)
        if (!caseDwInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [caseDwInstance: caseDwInstance]
        }
    }

    def update = {
        def caseDwInstance = CaseDw.get(params.id)
        if (caseDwInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (caseDwInstance.version > version) {
                    
                    caseDwInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseDw.label', default: 'CaseDw')] as Object[], "Another user has updated this CaseDw while you were editing")
                    render(view: "edit", model: [caseDwInstance: caseDwInstance])
                    return
                }
            }
            caseDwInstance.properties = params
            if (!caseDwInstance.hasErrors() && caseDwInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), caseDwInstance.id])}"
                redirect(action: "show", id: caseDwInstance.id)
            }
            else {
                render(view: "edit", model: [caseDwInstance: caseDwInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}"
            redirect(action: "list")
        }
    }

    //We don't want anyone else to delete caseDw records
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def caseDwInstance = CaseDw.get(params.id)
        if (caseDwInstance) {
            try {
                caseDwInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}"
            redirect(action: "list")
        }
    }
}
