package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class FixativeController {

    def scaffold = Fixative
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [fixativeInstanceList: Fixative.list(params), fixativeInstanceTotal: Fixative.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def fixativeInstance = new Fixative()
        fixativeInstance.properties = params
        return [fixativeInstance: fixativeInstance]
    }

    def save = {
        def fixativeInstance = new Fixative(params)
        if (fixativeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'fixative.label', default: 'Fixative'), fixativeInstance.id])}"
            redirect(action: "show", id: fixativeInstance.id)
        }
        else {
            render(view: "create", model: [fixativeInstance: fixativeInstance])
        }
    }

    def show = {
        def fixativeInstance = Fixative.get(params.id)
        if (!fixativeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fixative.label', default: 'Fixative'), params.id])}"
            redirect(action: "list")
        }
        else {
            [fixativeInstance: fixativeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def fixativeInstance = Fixative.get(params.id)
        if (!fixativeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fixative.label', default: 'Fixative'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [fixativeInstance: fixativeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def fixativeInstance = Fixative.get(params.id)
        if (fixativeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (fixativeInstance.version > version) {
                    
                    fixativeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'fixative.label', default: 'Fixative')] as Object[], "Another user has updated this Fixative while you were editing")
                    render(view: "edit", model: [fixativeInstance: fixativeInstance])
                    return
                }
            }
            fixativeInstance.properties = params
            if (!fixativeInstance.hasErrors() && fixativeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'fixative.label', default: 'Fixative'), fixativeInstance.id])}"
                redirect(action: "show", id: fixativeInstance.id)
            }
            else {
                render(view: "edit", model: [fixativeInstance: fixativeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fixative.label', default: 'Fixative'), params.id])}"
            redirect(action: "list")
        }
    }

       //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def fixativeInstance = Fixative.get(params.id)
        if (fixativeInstance) {
            try {
                fixativeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'fixative.label', default: 'Fixative'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'fixative.label', default: 'Fixative'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fixative.label', default: 'Fixative'), params.id])}"
            redirect(action: "list")
        }
    }
}
