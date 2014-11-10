package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured 

class KitRecordController {

    def scaffold = KitRecord
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [kitRecordInstanceList: KitRecord.list(params), kitRecordInstanceTotal: KitRecord.count()]
    }

    def create = {
        def kitRecordInstance = new KitRecord()
        kitRecordInstance.properties = params
        return [kitRecordInstance: kitRecordInstance]
    }

    def save = {
        def kitRecordInstance = new KitRecord(params)
        if (kitRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), kitRecordInstance.id])}"
            redirect(action: "show", id: kitRecordInstance.id)
        }
        else {
            render(view: "create", model: [kitRecordInstance: kitRecordInstance])
        }
    }

    def show = {
        def kitRecordInstance = KitRecord.get(params.id)
        if (!kitRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [kitRecordInstance: kitRecordInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def kitRecordInstance = KitRecord.get(params.id)
        if (!kitRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [kitRecordInstance: kitRecordInstance]
        }
    }

    def update = {
        def kitRecordInstance = KitRecord.get(params.id)
        if (kitRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (kitRecordInstance.version > version) {
                    
                    kitRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'kitRecord.label', default: 'KitRecord')] as Object[], "Another user has updated this KitRecord while you were editing")
                    render(view: "edit", model: [kitRecordInstance: kitRecordInstance])
                    return
                }
            }
            kitRecordInstance.properties = params
            if (!kitRecordInstance.hasErrors() && kitRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), kitRecordInstance.id])}"
                redirect(action: "show", id: kitRecordInstance.id)
            }
            else {
                render(view: "edit", model: [kitRecordInstance: kitRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def kitRecordInstance = KitRecord.get(params.id)
        if (kitRecordInstance) {
            try {
                kitRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kitRecord.label', default: 'KitRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
