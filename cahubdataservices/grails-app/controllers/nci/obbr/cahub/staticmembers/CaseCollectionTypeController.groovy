package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class CaseCollectionTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = true
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [caseCollectionTypeInstanceList: CaseCollectionType.list(params), caseCollectionTypeInstanceTotal: CaseCollectionType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def caseCollectionTypeInstance = new CaseCollectionType()
        caseCollectionTypeInstance.properties = params
        return [caseCollectionTypeInstance: caseCollectionTypeInstance]
    }

    def save = {
        def caseCollectionTypeInstance = new CaseCollectionType(params)
        if (caseCollectionTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), caseCollectionTypeInstance.id])}"
            redirect(action: "show", id: caseCollectionTypeInstance.id)
        }
        else {
            render(view: "create", model: [caseCollectionTypeInstance: caseCollectionTypeInstance])
        }
    }

    def show = {
        def caseCollectionTypeInstance = CaseCollectionType.get(params.id)
        if (!caseCollectionTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [caseCollectionTypeInstance: caseCollectionTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def caseCollectionTypeInstance = CaseCollectionType.get(params.id)
        if (!caseCollectionTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [caseCollectionTypeInstance: caseCollectionTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def caseCollectionTypeInstance = CaseCollectionType.get(params.id)
        if (caseCollectionTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (caseCollectionTypeInstance.version > version) {
                    
                    caseCollectionTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseCollectionType.label', default: 'CaseCollectionType')] as Object[], "Another user has updated this CaseCollectionType while you were editing")
                    render(view: "edit", model: [caseCollectionTypeInstance: caseCollectionTypeInstance])
                    return
                }
            }
            caseCollectionTypeInstance.properties = params
            if (!caseCollectionTypeInstance.hasErrors() && caseCollectionTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), caseCollectionTypeInstance.id])}"
                redirect(action: "show", id: caseCollectionTypeInstance.id)
            }
            else {
                render(view: "edit", model: [caseCollectionTypeInstance: caseCollectionTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), params.id])}"
            redirect(action: "list")
        }
    }

     //We don't want anyone else to delete
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])       
    def delete = {
        def caseCollectionTypeInstance = CaseCollectionType.get(params.id)
        if (caseCollectionTypeInstance) {
            try {
                caseCollectionTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseCollectionType.label', default: 'CaseCollectionType'), params.id])}"
            redirect(action: "list")
        }
    }
}
