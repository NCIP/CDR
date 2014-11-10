package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*

import grails.plugins.springsecurity.Secured 

import grails.converters.JSON

class SlideRecordController {

    def scaffold = SlideRecord

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [slideRecordInstanceList: SlideRecord.list(params), slideRecordInstanceTotal: SlideRecord.count()]
    }

    def create = {
        def slideRecordInstance = new SlideRecord()
        slideRecordInstance.properties = params
        return [slideRecordInstance: slideRecordInstance]
    }

    def save = {
        def slideRecordInstance = new SlideRecord(params)
        if (slideRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), slideRecordInstance.id])}"
            redirect(action: "show", id: slideRecordInstance.id)
        }
        else {
            render(view: "create", model: [slideRecordInstance: slideRecordInstance])
        }
    }

    def show = {
        def slideRecordInstance = SlideRecord.get(params.id)
        if (!slideRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [slideRecordInstance: slideRecordInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def slideRecordInstance = SlideRecord.get(params.id)
        if (!slideRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [slideRecordInstance: slideRecordInstance]
        }
    }

    def update = {
        def slideRecordInstance = SlideRecord.get(params.id)
        if (slideRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (slideRecordInstance.version > version) {
                    
                    slideRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'slideRecord.label', default: 'SlideRecord')] as Object[], "Another user has updated this SlideRecord while you were editing")
                    render(view: "edit", model: [slideRecordInstance: slideRecordInstance])
                    return
                }
            }
            slideRecordInstance.properties = params
            
            if(slideRecordInstance.requestReorder == true){
                slideRecordInstance.reorderRequestDate = new Date()
            }
            
            if (!slideRecordInstance.hasErrors() && slideRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), slideRecordInstance.id])}"
                redirect(action: "edit", id: slideRecordInstance.id)
            }
            else {
                render(view: "edit", model: [slideRecordInstance: slideRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def slideRecordInstance = SlideRecord.get(params.id)
        if (slideRecordInstance) {
            try {
                slideRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'slideRecord.label', default: 'SlideRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
