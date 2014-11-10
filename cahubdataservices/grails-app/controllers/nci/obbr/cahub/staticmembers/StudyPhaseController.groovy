package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
    
class StudyPhaseController {

    def scaffold = StudyPhase
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [studyPhaseInstanceList: StudyPhase.list(params), studyPhaseInstanceTotal: StudyPhase.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def studyPhaseInstance = new StudyPhase()
        studyPhaseInstance.properties = params
        return [studyPhaseInstance: studyPhaseInstance]
    }

    def save = {
        def studyPhaseInstance = new StudyPhase(params)
        if (studyPhaseInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), studyPhaseInstance.id])}"
            redirect(action: "show", id: studyPhaseInstance.id)
        }
        else {
            render(view: "create", model: [studyPhaseInstance: studyPhaseInstance])
        }
    }

    def show = {
        def studyPhaseInstance = StudyPhase.get(params.id)
        if (!studyPhaseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studyPhaseInstance: studyPhaseInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def studyPhaseInstance = StudyPhase.get(params.id)
        if (!studyPhaseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [studyPhaseInstance: studyPhaseInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def studyPhaseInstance = StudyPhase.get(params.id)
        if (studyPhaseInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (studyPhaseInstance.version > version) {
                    
                    studyPhaseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyPhase.label', default: 'StudyPhase')] as Object[], "Another user has updated this StudyPhase while you were editing")
                    render(view: "edit", model: [studyPhaseInstance: studyPhaseInstance])
                    return
                }
            }
            studyPhaseInstance.properties = params
            if (!studyPhaseInstance.hasErrors() && studyPhaseInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), studyPhaseInstance.id])}"
                redirect(action: "show", id: studyPhaseInstance.id)
            }
            else {
                render(view: "edit", model: [studyPhaseInstance: studyPhaseInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def studyPhaseInstance = StudyPhase.get(params.id)
        if (studyPhaseInstance) {
            try {
                studyPhaseInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyPhase.label', default: 'StudyPhase'), params.id])}"
            redirect(action: "list")
        }
    }
}
