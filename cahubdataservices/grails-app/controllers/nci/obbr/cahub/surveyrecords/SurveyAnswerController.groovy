package nci.obbr.cahub.surveyrecords
import grails.plugins.springsecurity.Secured
    

class SurveyAnswerController {

    def scaffold = SurveyAnswer
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [surveyAnswerInstanceList: SurveyAnswer.list(params), surveyAnswerInstanceTotal: SurveyAnswer.count()]
    }

    def create = {
        def surveyAnswerInstance = new SurveyAnswer()
        surveyAnswerInstance.properties = params
        return [surveyAnswerInstance: surveyAnswerInstance]
    }

    def save = {
        def surveyAnswerInstance = new SurveyAnswer(params)
        if (surveyAnswerInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), surveyAnswerInstance.id])}"
            redirect(action: "show", id: surveyAnswerInstance.id)
        }
        else {
            render(view: "create", model: [surveyAnswerInstance: surveyAnswerInstance])
        }
    }

    def show = {
        def surveyAnswerInstance = SurveyAnswer.get(params.id)
        if (!surveyAnswerInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surveyAnswerInstance: surveyAnswerInstance]
        }
    }

    def edit = {
        def surveyAnswerInstance = SurveyAnswer.get(params.id)
        if (!surveyAnswerInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [surveyAnswerInstance: surveyAnswerInstance]
        }
    }

    def update = {
        def surveyAnswerInstance = SurveyAnswer.get(params.id)
        if (surveyAnswerInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyAnswerInstance.version > version) {
                    
                    surveyAnswerInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyAnswer.label', default: 'surveyAnswer')] as Object[], "Another user has updated this surveyAnswer while you were editing")
                    render(view: "edit", model: [surveyAnswerInstance: surveyAnswerInstance])
                    return
                }
            }
            surveyAnswerInstance.properties = params
            if (!surveyAnswerInstance.hasErrors() && surveyAnswerInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), surveyAnswerInstance.id])}"
                redirect(action: "show", id: surveyAnswerInstance.id)
            }
            else {
                render(view: "edit", model: [surveyAnswerInstance: surveyAnswerInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def surveyAnswerInstance = SurveyAnswer.get(params.id)
        if (surveyAnswerInstance) {
            try {
                surveyAnswerInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAnswer.label', default: 'surveyAnswer'), params.id])}"
            redirect(action: "list")
        }
    }
}
