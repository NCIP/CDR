package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
    
class SOPController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = SOP

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [SOPInstanceList: SOP.list(params), SOPInstanceTotal: SOP.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def SOPInstance = new SOP()
        SOPInstance.properties = params
        return [SOPInstance: SOPInstance]
    }

    def save = {
        def SOPInstance = new SOP(params)
        if (SOPInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'SOP.label', default: 'SOP'), SOPInstance.id])}"
            redirect(action: "show", id: SOPInstance.id)
        }
        else {
            render(view: "create", model: [SOPInstance: SOPInstance])
        }
    }

    def show = {
        def SOPInstance = SOP.get(params.id)
        if (!SOPInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOP.label', default: 'SOP'), params.id])}"
            redirect(action: "list")
        }
        else {
            [SOPInstance: SOPInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def SOPInstance = SOP.get(params.id)
        if (!SOPInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOP.label', default: 'SOP'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [SOPInstance: SOPInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def SOPInstance = SOP.get(params.id)
        if (SOPInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (SOPInstance.version > version) {
                    
                    SOPInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'SOP.label', default: 'SOP')] as Object[], "Another user has updated this SOP while you were editing")
                    render(view: "edit", model: [SOPInstance: SOPInstance])
                    return
                }
            }
            SOPInstance.properties = params
            if (!SOPInstance.hasErrors() && SOPInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'SOP.label', default: 'SOP'), SOPInstance.id])}"
                redirect(action: "show", id: SOPInstance.id)
            }
            else {
                render(view: "edit", model: [SOPInstance: SOPInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOP.label', default: 'SOP'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def SOPInstance = SOP.get(params.id)
        if (SOPInstance) {
            try {
                SOPInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'SOP.label', default: 'SOP'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'SOP.label', default: 'SOP'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SOP.label', default: 'SOP'), params.id])}"
            redirect(action: "list")
        }
    }
}
