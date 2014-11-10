package nci.obbr.cahub.surveycomponents
import grails.plugins.springsecurity.Secured
    
class SurveyQuestionController {

    def scaffold = SurveyQuestion
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [surveyQuestionInstanceList: SurveyQuestion.list(params), surveyQuestionInstanceTotal: SurveyQuestion.count()]
    }

    def create = {
        def surveyQuestionInstance = new SurveyQuestion()
        surveyQuestionInstance.properties = params
        return [surveyQuestionInstance: surveyQuestionInstance]
    }

    def save = {
        def surveyQuestionInstance = new SurveyQuestion(params)
        if (surveyQuestionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), surveyQuestionInstance.id])}"
            redirect(action: "show", id: surveyQuestionInstance.id)
        }
        else {
            render(view: "create", model: [surveyQuestionInstance: surveyQuestionInstance])
        }
    }

    def show = {
        def surveyQuestionInstance = SurveyQuestion.get(params.id)
        if (!surveyQuestionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surveyQuestionInstance: surveyQuestionInstance]
        }
    }

    def edit = {
        def surveyQuestionInstance = SurveyQuestion.get(params.id)
        if (!surveyQuestionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [surveyQuestionInstance: surveyQuestionInstance]
        }
    }

    def update = {
        def surveyQuestionInstance = SurveyQuestion.get(params.id)
        if (surveyQuestionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyQuestionInstance.version > version) {
                    
                    surveyQuestionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyQuestion.label', default: 'SurveyQuestion')] as Object[], "Another user has updated this SurveyQuestion while you were editing")
                    render(view: "edit", model: [surveyQuestionInstance: surveyQuestionInstance])
                    return
                }
            }
            surveyQuestionInstance.properties = params
            if (!surveyQuestionInstance.hasErrors() && surveyQuestionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), surveyQuestionInstance.id])}"
                redirect(action: "show", id: surveyQuestionInstance.id)
            }
            else {
                render(view: "edit", model: [surveyQuestionInstance: surveyQuestionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def surveyQuestionInstance = SurveyQuestion.get(params.id)
        if (surveyQuestionInstance) {
            try {
                surveyQuestionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyQuestion.label', default: 'SurveyQuestion'), params.id])}"
            redirect(action: "list")
        }
    }
}
