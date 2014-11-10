package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured

class SOPRecordController {

    def scaffold = SOPRecord
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [SOPRecordInstanceList: SOPRecord.list(params), SOPRecordInstanceTotal: SOPRecord.count()]
    }

    def create = {
        def SOPRecordInstance = new SOPRecord()
        SOPRecordInstance.properties = params
        return [SOPRecordInstance: SOPRecordInstance]
    }

    def save = {
        def SOPRecordInstance = new SOPRecord(params)
        if (SOPRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), SOPRecordInstance.id])}"
            redirect(action: "show", id: SOPRecordInstance.id)
        }
        else {
            render(view: "create", model: [SOPRecordInstance: SOPRecordInstance])
        }
    }

    def show = {
        def SOPRecordInstance = SOPRecord.get(params.id)
        if (!SOPRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [SOPRecordInstance: SOPRecordInstance]
        }
    }

    def edit = {
        def SOPRecordInstance = SOPRecord.get(params.id)
        if (!SOPRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [SOPRecordInstance: SOPRecordInstance]
        }
    }

    def update = {
        def SOPRecordInstance = SOPRecord.get(params.id)
        if (SOPRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (SOPRecordInstance.version > version) {
                    
                    SOPRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'SOPRecord.label', default: 'SOPRecord')] as Object[], "Another user has updated this SOPRecord while you were editing")
                    render(view: "edit", model: [SOPRecordInstance: SOPRecordInstance])
                    return
                }
            }
            SOPRecordInstance.properties = params
            if (!SOPRecordInstance.hasErrors() && SOPRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), SOPRecordInstance.id])}"
                redirect(action: "show", id: SOPRecordInstance.id)
            }
            else {
                render(view: "edit", model: [SOPRecordInstance: SOPRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), params.id])}"
            redirect(action: "list")
        }
    }


    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def SOPRecordInstance = SOPRecord.get(params.id)
        if (SOPRecordInstance) {
            try {
                SOPRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOPRecord.label', default: 'SOPRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
