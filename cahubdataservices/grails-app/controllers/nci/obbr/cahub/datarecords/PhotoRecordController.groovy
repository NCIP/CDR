package nci.obbr.cahub.datarecords

class PhotoRecordController {

    def scaffold = PhotoRecord
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [photoRecordInstanceList: PhotoRecord.list(params), photoRecordInstanceTotal: PhotoRecord.count()]
    }

    def create = {
        def photoRecordInstance = new PhotoRecord()
        photoRecordInstance.properties = params
        return [photoRecordInstance: photoRecordInstance]
    }

    def save = {
        def photoRecordInstance = new PhotoRecord(params)
        if (photoRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), photoRecordInstance.id])}"
            redirect(action: "show", id: photoRecordInstance.id)
        }
        else {
            render(view: "create", model: [photoRecordInstance: photoRecordInstance])
        }
    }

    def show = {
        def photoRecordInstance = PhotoRecord.get(params.id)
        if (!photoRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [photoRecordInstance: photoRecordInstance]
        }
    }

    def edit = {
        def photoRecordInstance = PhotoRecord.get(params.id)
        if (!photoRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [photoRecordInstance: photoRecordInstance]
        }
    }

    def update = {
        def photoRecordInstance = PhotoRecord.get(params.id)
        if (photoRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (photoRecordInstance.version > version) {
                    
                    photoRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'photoRecord.label', default: 'PhotoRecord')] as Object[], "Another user has updated this PhotoRecord while you were editing")
                    render(view: "edit", model: [photoRecordInstance: photoRecordInstance])
                    return
                }
            }
            photoRecordInstance.properties = params
            if (!photoRecordInstance.hasErrors() && photoRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), photoRecordInstance.id])}"
                redirect(action: "show", id: photoRecordInstance.id)
            }
            else {
                render(view: "edit", model: [photoRecordInstance: photoRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def photoRecordInstance = PhotoRecord.get(params.id)
        if (photoRecordInstance) {
            try {
                photoRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'photoRecord.label', default: 'PhotoRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
