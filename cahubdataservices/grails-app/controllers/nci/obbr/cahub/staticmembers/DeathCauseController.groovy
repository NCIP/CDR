package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class DeathCauseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = DeathCause
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [deathCauseInstanceList: DeathCause.list(params), deathCauseInstanceTotal: DeathCause.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def deathCauseInstance = new DeathCause()
        deathCauseInstance.properties = params
        return [deathCauseInstance: deathCauseInstance]
    }

    def save = {
        def deathCauseInstance = new DeathCause(params)
        if (deathCauseInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), deathCauseInstance.id])}"
            redirect(action: "show", id: deathCauseInstance.id)
        }
        else {
            render(view: "create", model: [deathCauseInstance: deathCauseInstance])
        }
    }

    def show = {
        def deathCauseInstance = DeathCause.get(params.id)
        if (!deathCauseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deathCauseInstance: deathCauseInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def deathCauseInstance = DeathCause.get(params.id)
        if (!deathCauseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [deathCauseInstance: deathCauseInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def deathCauseInstance = DeathCause.get(params.id)
        if (deathCauseInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deathCauseInstance.version > version) {
                    
                    deathCauseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deathCause.label', default: 'DeathCause')] as Object[], "Another user has updated this DeathCause while you were editing")
                    render(view: "edit", model: [deathCauseInstance: deathCauseInstance])
                    return
                }
            }
            deathCauseInstance.properties = params
            if (!deathCauseInstance.hasErrors() && deathCauseInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), deathCauseInstance.id])}"
                redirect(action: "show", id: deathCauseInstance.id)
            }
            else {
                render(view: "edit", model: [deathCauseInstance: deathCauseInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), params.id])}"
            redirect(action: "list")
        }
    }

       //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def deathCauseInstance = DeathCause.get(params.id)
        if (deathCauseInstance) {
            try {
                deathCauseInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCause.label', default: 'DeathCause'), params.id])}"
            redirect(action: "list")
        }
    }
}
