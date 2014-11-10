package nci.obbr.cahub.forms.bpv.clinicaldataentry

class TherapyRecordController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [therapyRecordInstanceList: TherapyRecord.list(params), therapyRecordInstanceTotal: TherapyRecord.count()]
    }

    def create = {
        def therapyRecordInstance = new TherapyRecord()
        therapyRecordInstance.properties = params
        return [therapyRecordInstance: therapyRecordInstance]
    }

    def save = {
        def therapyRecordInstance = new TherapyRecord(params)
        if (therapyRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), therapyRecordInstance.id])}"
            redirect(action: "show", id: therapyRecordInstance.id)
        }
        else {
            render(view: "create", model: [therapyRecordInstance: therapyRecordInstance])
        }
    }

    def show = {
        def therapyRecordInstance = TherapyRecord.get(params.id)
        if (!therapyRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [therapyRecordInstance: therapyRecordInstance]
        }
    }

    def edit = {
        def therapyRecordInstance = TherapyRecord.get(params.id)
        if (!therapyRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [therapyRecordInstance: therapyRecordInstance]
        }
    }

    def update = {
        def therapyRecordInstance = TherapyRecord.get(params.id)
        if (therapyRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (therapyRecordInstance.version > version) {
                    
                    therapyRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'therapyRecord.label', default: 'TherapyRecord')] as Object[], "Another user has updated this TherapyRecord while you were editing")
                    render(view: "edit", model: [therapyRecordInstance: therapyRecordInstance])
                    return
                }
            }
            therapyRecordInstance.properties = params
            if (!therapyRecordInstance.hasErrors() && therapyRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), therapyRecordInstance.id])}"
                redirect(action: "show", id: therapyRecordInstance.id)
            }
            else {
                render(view: "edit", model: [therapyRecordInstance: therapyRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def therapyRecordInstance = TherapyRecord.get(params.id)
        if (therapyRecordInstance) {
            try {
                therapyRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'therapyRecord.label', default: 'TherapyRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
