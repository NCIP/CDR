package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class HistologicTypeController {

    def scaffold = HistologicType
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [histologicTypeInstanceList: HistologicType.list(params), histologicTypeInstanceTotal: HistologicType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def histologicTypeInstance = new HistologicType()
        histologicTypeInstance.properties = params
        return [histologicTypeInstance: histologicTypeInstance]
    }

    def save = {
        def histologicTypeInstance = new HistologicType(params)
        if (histologicTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), histologicTypeInstance.id])}"
            redirect(action: "show", id: histologicTypeInstance.id)
        }
        else {
            render(view: "create", model: [histologicTypeInstance: histologicTypeInstance])
        }
    }

    def show = {
        def histologicTypeInstance = HistologicType.get(params.id)
        if (!histologicTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [histologicTypeInstance: histologicTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])    
    def edit = {
        def histologicTypeInstance = HistologicType.get(params.id)
        if (!histologicTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [histologicTypeInstance: histologicTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def histologicTypeInstance = HistologicType.get(params.id)
        if (histologicTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (histologicTypeInstance.version > version) {
                    
                    histologicTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'histologicType.label', default: 'HistologicType')] as Object[], "Another user has updated this HistologicType while you were editing")
                    render(view: "edit", model: [histologicTypeInstance: histologicTypeInstance])
                    return
                }
            }
            histologicTypeInstance.properties = params
            if (!histologicTypeInstance.hasErrors() && histologicTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), histologicTypeInstance.id])}"
                redirect(action: "show", id: histologicTypeInstance.id)
            }
            else {
                render(view: "edit", model: [histologicTypeInstance: histologicTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])    
    def delete = {
        def histologicTypeInstance = HistologicType.get(params.id)
        if (histologicTypeInstance) {
            try {
                histologicTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'histologicType.label', default: 'HistologicType'), params.id])}"
            redirect(action: "list")
        }
    }
}
