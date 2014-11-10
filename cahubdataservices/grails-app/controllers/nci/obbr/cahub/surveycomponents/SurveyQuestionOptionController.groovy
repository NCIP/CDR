package nci.obbr.cahub.surveycomponents
import grails.plugins.springsecurity.Secured
     
class SurveyQuestionOptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = SurveyQuestionOption
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [surveyQuestionOptionInstanceList: SurveyQuestionOption.list(params), surveyQuestionOptionInstanceTotal: SurveyQuestionOption.count()]
    }

    def create = {
        def surveyQuestionOptionInstance = new SurveyQuestionOption()
        surveyQuestionOptionInstance.properties = params
        return [surveyQuestionOptionInstance: surveyQuestionOptionInstance]
    }

    def save = {
        def surveyQuestionOptionInstance = new SurveyQuestionOption(params)
        if (surveyQuestionOptionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), surveyQuestionOptionInstance.id])}"
            redirect(action: "show", id: surveyQuestionOptionInstance.id)
        }
        else {
            render(view: "create", model: [surveyQuestionOptionInstance: surveyQuestionOptionInstance])
        }
    }

    def show = {
        def surveyQuestionOptionInstance = SurveyQuestionOption.get(params.id)
        if (!surveyQuestionOptionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surveyQuestionOptionInstance: surveyQuestionOptionInstance]
        }
    }

    def edit = {
        def surveyQuestionOptionInstance = SurveyQuestionOption.get(params.id)
        if (!surveyQuestionOptionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [surveyQuestionOptionInstance: surveyQuestionOptionInstance]
        }
    }

    def update = {
        def surveyQuestionOptionInstance = SurveyQuestionOption.get(params.id)
        if (surveyQuestionOptionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyQuestionOptionInstance.version > version) {
                    
                    surveyQuestionOptionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption')] as Object[], "Another user has updated this SurveyQuestionOption while you were editing")
                    render(view: "edit", model: [surveyQuestionOptionInstance: surveyQuestionOptionInstance])
                    return
                }
            }
            surveyQuestionOptionInstance.properties = params
            if (!surveyQuestionOptionInstance.hasErrors() && surveyQuestionOptionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), surveyQuestionOptionInstance.id])}"
                redirect(action: "show", id: surveyQuestionOptionInstance.id)
            }
            else {
                render(view: "edit", model: [surveyQuestionOptionInstance: surveyQuestionOptionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def surveyQuestionOptionInstance = SurveyQuestionOption.get(params.id)
        if (surveyQuestionOptionInstance) {
            try {
                surveyQuestionOptionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestionOption.label', default: 'SurveyQuestionOption'), params.id])}"
            redirect(action: "list")
        }
    }
}
