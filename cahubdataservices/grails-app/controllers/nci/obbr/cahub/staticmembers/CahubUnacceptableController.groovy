package nci.obbr.cahub.staticmembers

class CahubUnacceptableController {
    
    def scaffold = CahubUnacceptable

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [cahubUnacceptableInstanceList: CahubUnacceptable.list(params), cahubUnacceptableInstanceTotal: CahubUnacceptable.count()]
    }

    def create = {
        def cahubUnacceptableInstance = new CahubUnacceptable()
        cahubUnacceptableInstance.properties = params
        return [cahubUnacceptableInstance: cahubUnacceptableInstance]
    }

    def save = {
        def cahubUnacceptableInstance = new CahubUnacceptable(params)
        if (cahubUnacceptableInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), cahubUnacceptableInstance.id])}"
            redirect(action: "show", id: cahubUnacceptableInstance.id)
        }
        else {
            render(view: "create", model: [cahubUnacceptableInstance: cahubUnacceptableInstance])
        }
    }

    def show = {
        def cahubUnacceptableInstance = CahubUnacceptable.get(params.id)
        if (!cahubUnacceptableInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), params.id])}"
            redirect(action: "list")
        }
        else {
            [cahubUnacceptableInstance: cahubUnacceptableInstance]
        }
    }

    def edit = {
        def cahubUnacceptableInstance = CahubUnacceptable.get(params.id)
        if (!cahubUnacceptableInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [cahubUnacceptableInstance: cahubUnacceptableInstance]
        }
    }

    def update = {
        def cahubUnacceptableInstance = CahubUnacceptable.get(params.id)
        if (cahubUnacceptableInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (cahubUnacceptableInstance.version > version) {
                    
                    cahubUnacceptableInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable')] as Object[], "Another user has updated this CahubUnacceptable while you were editing")
                    render(view: "edit", model: [cahubUnacceptableInstance: cahubUnacceptableInstance])
                    return
                }
            }
            cahubUnacceptableInstance.properties = params
            if (!cahubUnacceptableInstance.hasErrors() && cahubUnacceptableInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), cahubUnacceptableInstance.id])}"
                redirect(action: "show", id: cahubUnacceptableInstance.id)
            }
            else {
                render(view: "edit", model: [cahubUnacceptableInstance: cahubUnacceptableInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def cahubUnacceptableInstance = CahubUnacceptable.get(params.id)
        if (cahubUnacceptableInstance) {
            try {
                cahubUnacceptableInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cahubUnacceptable.label', default: 'CahubUnacceptable'), params.id])}"
            redirect(action: "list")
        }
    }
}
