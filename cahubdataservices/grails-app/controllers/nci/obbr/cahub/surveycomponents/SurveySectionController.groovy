package nci.obbr.cahub.surveycomponents
import grails.plugins.springsecurity.Secured
    
class SurveySectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = SurveySection

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [surveySectionInstanceList: SurveySection.list(params), surveySectionInstanceTotal: SurveySection.count()]
    }

    def create = {
        def surveySectionInstance = new SurveySection()
        surveySectionInstance.properties = params
        return [surveySectionInstance: surveySectionInstance]
    }

    def save = {
        def surveySectionInstance = new SurveySection(params)
        if (surveySectionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), surveySectionInstance.id])}"
            redirect(action: "show", id: surveySectionInstance.id)
        }
        else {
            render(view: "create", model: [surveySectionInstance: surveySectionInstance])
        }
    }

    def show = {
        def surveySectionInstance = SurveySection.get(params.id)
        if (!surveySectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surveySectionInstance: surveySectionInstance]
        }
    }

    def edit = {
        def surveySectionInstance = SurveySection.get(params.id)
        if (!surveySectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [surveySectionInstance: surveySectionInstance]
        }
    }

    def update = {
        def surveySectionInstance = SurveySection.get(params.id)
        if (surveySectionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveySectionInstance.version > version) {
                    
                    surveySectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveySection.label', default: 'SurveySection')] as Object[], "Another user has updated this SurveySection while you were editing")
                    render(view: "edit", model: [surveySectionInstance: surveySectionInstance])
                    return
                }
            }
            surveySectionInstance.properties = params
            if (!surveySectionInstance.hasErrors() && surveySectionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), surveySectionInstance.id])}"
                redirect(action: "show", id: surveySectionInstance.id)
            }
            else {
                render(view: "edit", model: [surveySectionInstance: surveySectionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def surveySectionInstance = SurveySection.get(params.id)
        if (surveySectionInstance) {
            try {
                surveySectionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveySection.label', default: 'SurveySection'), params.id])}"
            redirect(action: "list")
        }
    }
}
