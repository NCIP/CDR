package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured 

class ProcessingEventController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = ProcessingEvent

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [processingEventInstanceList: ProcessingEvent.list(params), processingEventInstanceTotal: ProcessingEvent.count()]
    }

    def create = {
        def processingEventInstance = new ProcessingEvent()
        processingEventInstance.properties = params
        return [processingEventInstance: processingEventInstance]
    }

    def save = {
        def processingEventInstance = new ProcessingEvent(params)
        if (processingEventInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), processingEventInstance.id])}"
            redirect(action: "show", id: processingEventInstance.id)
        }
        else {
            render(view: "create", model: [processingEventInstance: processingEventInstance])
        }
    }

    def show = {
        def processingEventInstance = ProcessingEvent.get(params.id)
        if (!processingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            [processingEventInstance: processingEventInstance]
        }
    }
    
    def showByCbrId = {
        def processingEventInstance = ProcessingEvent.findByEventId(params.id)
        if (!processingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            redirect(action: "show", id: processingEventInstance.id)
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])        
    def edit = {
        def processingEventInstance = ProcessingEvent.get(params.id)
        if (!processingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [processingEventInstance: processingEventInstance]
        }
    }

    def update = {
        def processingEventInstance = ProcessingEvent.get(params.id)
        if (processingEventInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (processingEventInstance.version > version) {
                    
                    processingEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'processingEvent.label', default: 'ProcessingEvent')] as Object[], "Another user has updated this ProcessingEvent while you were editing")
                    render(view: "edit", model: [processingEventInstance: processingEventInstance])
                    return
                }
            }
            processingEventInstance.properties = params
            if (!processingEventInstance.hasErrors() && processingEventInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), processingEventInstance.id])}"
                redirect(action: "show", id: processingEventInstance.id)
            }
            else {
                render(view: "edit", model: [processingEventInstance: processingEventInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])    
    def delete = {
        def processingEventInstance = ProcessingEvent.get(params.id)
        if (processingEventInstance) {
            try {
                processingEventInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'processingEvent.label', default: 'ProcessingEvent'), params.id])}"
            redirect(action: "list")
        }
    }
}
