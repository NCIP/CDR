package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class BSSController {

    def scaffold = BSS

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [BSSInstanceList: BSS.list(params), BSSInstanceTotal: BSS.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def BSSInstance = new BSS()
        BSSInstance.properties = params
        return [BSSInstance: BSSInstance]
    }

    def save = {
        def BSSInstance = new BSS(params)
        if (BSSInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'BSS.label', default: 'BSS'), BSSInstance.id])}"
            redirect(action: "show", id: BSSInstance.id)
        }
        else {
            render(view: "create", model: [BSSInstance: BSSInstance])
        }
    }

    def show = {
        def BSSInstance = BSS.get(params.id)
        if (!BSSInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BSS.label', default: 'BSS'), params.id])}"
            redirect(action: "list")
        }
        else {
            [BSSInstance: BSSInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def BSSInstance = BSS.get(params.id)
        if (!BSSInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BSS.label', default: 'BSS'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [BSSInstance: BSSInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def BSSInstance = BSS.get(params.id)
        if (BSSInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (BSSInstance.version > version) {
                    
                    BSSInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'BSS.label', default: 'BSS')] as Object[], "Another user has updated this BSS while you were editing")
                    render(view: "edit", model: [BSSInstance: BSSInstance])
                    return
                }
            }
            BSSInstance.properties = params
            if (!BSSInstance.hasErrors() && BSSInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'BSS.label', default: 'BSS'), BSSInstance.id])}"
                redirect(action: "show", id: BSSInstance.id)
            }
            else {
                render(view: "edit", model: [BSSInstance: BSSInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BSS.label', default: 'BSS'), params.id])}"
            redirect(action: "list")
        }
    }

    //We don't want anyone else to delete bss
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
                
        def BSSInstance = BSS.get(params.id)
        if (BSSInstance) {
            try {
                BSSInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'BSS.label', default: 'BSS'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'BSS.label', default: 'BSS'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'BSS.label', default: 'BSS'), params.id])}"
            redirect(action: "list")
        }
    }
}
