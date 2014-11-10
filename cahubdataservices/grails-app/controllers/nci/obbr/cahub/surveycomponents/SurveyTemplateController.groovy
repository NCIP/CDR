package nci.obbr.cahub.surveycomponents
import grails.plugins.springsecurity.Secured
    
class SurveyTemplateController {

    def scaffold = SurveyTemplate
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [surveyTemplateInstanceList: SurveyTemplate.list(params), surveyTemplateInstanceTotal: SurveyTemplate.count()]
    }

    def create = {
        def surveyTemplateInstance = new SurveyTemplate()
        surveyTemplateInstance.properties = params
        return [surveyTemplateInstance: surveyTemplateInstance]
    }

    def save = {
        def surveyTemplateInstance = new SurveyTemplate(params)
        if (surveyTemplateInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), surveyTemplateInstance.id])}"
            redirect(action: "show", id: surveyTemplateInstance.id)
        }
        else {
            render(view: "create", model: [surveyTemplateInstance: surveyTemplateInstance])
        }
    }

    def show = {
        def surveyTemplateInstance = SurveyTemplate.get(params.id)
        if (!surveyTemplateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surveyTemplateInstance: surveyTemplateInstance]
        }
    }

    def edit = {
        def surveyTemplateInstance = SurveyTemplate.get(params.id)
        if (!surveyTemplateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [surveyTemplateInstance: surveyTemplateInstance]
        }
    }

    def update = {
        def surveyTemplateInstance = SurveyTemplate.get(params.id)
        if (surveyTemplateInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyTemplateInstance.version > version) {
                    
                    surveyTemplateInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyTemplate.label', default: 'SurveyTemplate')] as Object[], "Another user has updated this SurveyTemplate while you were editing")
                    render(view: "edit", model: [surveyTemplateInstance: surveyTemplateInstance])
                    return
                }
            }
            surveyTemplateInstance.properties = params
            if (!surveyTemplateInstance.hasErrors() && surveyTemplateInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), surveyTemplateInstance.id])}"
                redirect(action: "show", id: surveyTemplateInstance.id)
            }
            else {
                render(view: "edit", model: [surveyTemplateInstance: surveyTemplateInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def surveyTemplateInstance = SurveyTemplate.get(params.id)
        if (surveyTemplateInstance) {
            try {
                surveyTemplateInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyTemplate.label', default: 'SurveyTemplate'), params.id])}"
            redirect(action: "list")
        }
    }
}
