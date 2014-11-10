package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
   
class SopVersionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = SopVersion

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [sopVersionInstanceList: SopVersion.list(params), sopVersionInstanceTotal: SopVersion.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM'])
    def create = {
        def sopVersionInstance = new SopVersion()
        sopVersionInstance.properties = params
        return [sopVersionInstance: sopVersionInstance]
    }

    def save = {
        def sopVersionInstance = new SopVersion(params)
        if (sopVersionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), sopVersionInstance.id])}"
            redirect(action: "show", id: sopVersionInstance.id)
        }
        else {
            render(view: "create", model: [sopVersionInstance: sopVersionInstance])
        }
    }

    def show = {
        def sopVersionInstance = SopVersion.get(params.id)
        if (!sopVersionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sopVersionInstance: sopVersionInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM'])
    def edit = {
        def sopVersionInstance = SopVersion.get(params.id)
        if (!sopVersionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [sopVersionInstance: sopVersionInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM'])
    def update = {
        def sopVersionInstance = SopVersion.get(params.id)
        if (sopVersionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sopVersionInstance.version > version) {
                    
                    sopVersionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'sopVersion.label', default: 'SopVersion')] as Object[], "Another user has updated this SopVersion while you were editing")
                    render(view: "edit", model: [sopVersionInstance: sopVersionInstance])
                    return
                }
            }
            sopVersionInstance.properties = params
            if (!sopVersionInstance.hasErrors() && sopVersionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), sopVersionInstance.id])}"
                redirect(action: "show", id: sopVersionInstance.id)
            }
            else {
                render(view: "edit", model: [sopVersionInstance: sopVersionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM']) 
    def delete = {
        def sopVersionInstance = SopVersion.get(params.id)
        if (sopVersionInstance) {
            try {
                sopVersionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sopVersion.label', default: 'SopVersion'), params.id])}"
            redirect(action: "list")
        }
    }
}
