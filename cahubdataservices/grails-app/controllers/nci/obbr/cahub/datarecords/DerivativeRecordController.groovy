package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured 

class DerivativeRecordController {

    def scaffold = DerivativeRecord

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [derivativeRecordInstanceList: DerivativeRecord.list(params), derivativeRecordInstanceTotal: DerivativeRecord.count()]
    }

    def create = {
        def derivativeRecordInstance = new DerivativeRecord()
        derivativeRecordInstance.properties = params
        return [derivativeRecordInstance: derivativeRecordInstance]
    }

    def save = {
        def derivativeRecordInstance = new DerivativeRecord(params)
        if (derivativeRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), derivativeRecordInstance.id])}"
            redirect(action: "show", id: derivativeRecordInstance.id)
        }
        else {
            render(view: "create", model: [derivativeRecordInstance: derivativeRecordInstance])
        }
    }

    def show = {
        def derivativeRecordInstance = DerivativeRecord.get(params.id)
        if (!derivativeRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [derivativeRecordInstance: derivativeRecordInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])    
    def edit = {
        def derivativeRecordInstance = DerivativeRecord.get(params.id)
        if (!derivativeRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [derivativeRecordInstance: derivativeRecordInstance]
        }
    }

    def update = {
        def derivativeRecordInstance = DerivativeRecord.get(params.id)
        if (derivativeRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (derivativeRecordInstance.version > version) {
                    
                    derivativeRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'derivativeRecord.label', default: 'DerivativeRecord')] as Object[], "Another user has updated this DerivativeRecord while you were editing")
                    render(view: "edit", model: [derivativeRecordInstance: derivativeRecordInstance])
                    return
                }
            }
            derivativeRecordInstance.properties = params
            if (!derivativeRecordInstance.hasErrors() && derivativeRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), derivativeRecordInstance.id])}"
                redirect(action: "show", id: derivativeRecordInstance.id)
            }
            else {
                render(view: "edit", model: [derivativeRecordInstance: derivativeRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def derivativeRecordInstance = DerivativeRecord.get(params.id)
        if (derivativeRecordInstance) {
            try {
                derivativeRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'derivativeRecord.label', default: 'DerivativeRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
