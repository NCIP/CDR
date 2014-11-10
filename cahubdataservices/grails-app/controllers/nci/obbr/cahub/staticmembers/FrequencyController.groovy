package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
  
class FrequencyController {

    def scaffold = Frequency
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [frequencyInstanceList: Frequency.list(params), frequencyInstanceTotal: Frequency.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def frequencyInstance = new Frequency()
        frequencyInstance.properties = params
        return [frequencyInstance: frequencyInstance]
    }

    def save = {
        def frequencyInstance = new Frequency(params)
        if (frequencyInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'frequency.label', default: 'Frequency'), frequencyInstance.id])}"
            redirect(action: "show", id: frequencyInstance.id)
        }
        else {
            render(view: "create", model: [frequencyInstance: frequencyInstance])
        }
    }

    def show = {
        def frequencyInstance = Frequency.get(params.id)
        if (!frequencyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), params.id])}"
            redirect(action: "list")
        }
        else {
            [frequencyInstance: frequencyInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def frequencyInstance = Frequency.get(params.id)
        if (!frequencyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [frequencyInstance: frequencyInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def frequencyInstance = Frequency.get(params.id)
        if (frequencyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (frequencyInstance.version > version) {
                    
                    frequencyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'frequency.label', default: 'Frequency')] as Object[], "Another user has updated this Frequency while you were editing")
                    render(view: "edit", model: [frequencyInstance: frequencyInstance])
                    return
                }
            }
            frequencyInstance.properties = params
            if (!frequencyInstance.hasErrors() && frequencyInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'frequency.label', default: 'Frequency'), frequencyInstance.id])}"
                redirect(action: "show", id: frequencyInstance.id)
            }
            else {
                render(view: "edit", model: [frequencyInstance: frequencyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def frequencyInstance = Frequency.get(params.id)
        if (frequencyInstance) {
            try {
                frequencyInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'frequency.label', default: 'Frequency'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'frequency.label', default: 'Frequency'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), params.id])}"
            redirect(action: "list")
        }
    }
}
