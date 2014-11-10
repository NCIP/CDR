package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*

import grails.plugins.springsecurity.Secured 

class ImageRecordController {

    def scaffold = ImageRecord

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [imageRecordInstanceList: ImageRecord.list(params), imageRecordInstanceTotal: ImageRecord.count()]
    }

    def create = {
        def imageRecordInstance = new ImageRecord()
        imageRecordInstance.properties = params
        return [imageRecordInstance: imageRecordInstance]
    }

    def save = {
        def imageRecordInstance = new ImageRecord(params)
        if (imageRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), imageRecordInstance.id])}"
            redirect(action: "show", id: imageRecordInstance.id)
        }
        else {
            render(view: "create", model: [imageRecordInstance: imageRecordInstance])
        }
    }

    def show = {
        def imageRecordInstance = ImageRecord.get(params.id)
        if (!imageRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [imageRecordInstance: imageRecordInstance]
        }
    }
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def imageRecordInstance = ImageRecord.get(params.id)
        if (!imageRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [imageRecordInstance: imageRecordInstance]
        }
    }

    def update = {
        def imageRecordInstance = ImageRecord.get(params.id)
        if (imageRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (imageRecordInstance.version > version) {
                    
                    imageRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'imageRecord.label', default: 'ImageRecord')] as Object[], "Another user has updated this ImageRecord while you were editing")
                    render(view: "edit", model: [imageRecordInstance: imageRecordInstance])
                    return
                }
            }
            imageRecordInstance.properties = params
            if (!imageRecordInstance.hasErrors() && imageRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), imageRecordInstance.id])}"
                redirect(action: "show", id: imageRecordInstance.id)
            }
            else {
                render(view: "edit", model: [imageRecordInstance: imageRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def imageRecordInstance = ImageRecord.get(params.id)
        if (imageRecordInstance) {
            try {
                imageRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageRecord.label', default: 'ImageRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
