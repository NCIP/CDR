package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured 

class MedicalConditionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def scaffold = MedicalCondition

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [medicalConditionInstanceList: MedicalCondition.list(params), medicalConditionInstanceTotal: MedicalCondition.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def medicalConditionInstance = new MedicalCondition()
        medicalConditionInstance.properties = params
        return [medicalConditionInstance: medicalConditionInstance]
    }

    def save = {
        def medicalConditionInstance = new MedicalCondition(params)
        if (medicalConditionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), medicalConditionInstance.id])}"
            redirect(action: "show", id: medicalConditionInstance.id)
        }
        else {
            render(view: "create", model: [medicalConditionInstance: medicalConditionInstance])
        }
    }

    def show = {
        def medicalConditionInstance = MedicalCondition.get(params.id)
        if (!medicalConditionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), params.id])}"
            redirect(action: "list")
        }
        else {
            [medicalConditionInstance: medicalConditionInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def medicalConditionInstance = MedicalCondition.get(params.id)
        if (!medicalConditionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [medicalConditionInstance: medicalConditionInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def medicalConditionInstance = MedicalCondition.get(params.id)
        if (medicalConditionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (medicalConditionInstance.version > version) {
                    
                    medicalConditionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'medicalCondition.label', default: 'MedicalCondition')] as Object[], "Another user has updated this MedicalCondition while you were editing")
                    render(view: "edit", model: [medicalConditionInstance: medicalConditionInstance])
                    return
                }
            }
            medicalConditionInstance.properties = params
            if (!medicalConditionInstance.hasErrors() && medicalConditionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), medicalConditionInstance.id])}"
                redirect(action: "show", id: medicalConditionInstance.id)
            }
            else {
                render(view: "edit", model: [medicalConditionInstance: medicalConditionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def medicalConditionInstance = MedicalCondition.get(params.id)
        if (medicalConditionInstance) {
            try {
                medicalConditionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalCondition.label', default: 'MedicalCondition'), params.id])}"
            redirect(action: "list")
        }
    }
}
