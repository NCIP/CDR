package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured

class DosageUnitController {

    def scaffold = DosageUnit
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [dosageUnitInstanceList: DosageUnit.list(params), dosageUnitInstanceTotal: DosageUnit.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def dosageUnitInstance = new DosageUnit()
        dosageUnitInstance.properties = params
        return [dosageUnitInstance: dosageUnitInstance]
    }

    def save = {
        def dosageUnitInstance = new DosageUnit(params)
        if (dosageUnitInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), dosageUnitInstance.id])}"
            redirect(action: "show", id: dosageUnitInstance.id)
        }
        else {
            render(view: "create", model: [dosageUnitInstance: dosageUnitInstance])
        }
    }

    def show = {
        def dosageUnitInstance = DosageUnit.get(params.id)
        if (!dosageUnitInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), params.id])}"
            redirect(action: "list")
        }
        else {
            [dosageUnitInstance: dosageUnitInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def dosageUnitInstance = DosageUnit.get(params.id)
        if (!dosageUnitInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [dosageUnitInstance: dosageUnitInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def dosageUnitInstance = DosageUnit.get(params.id)
        if (dosageUnitInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (dosageUnitInstance.version > version) {
                    
                    dosageUnitInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'dosageUnit.label', default: 'DosageUnit')] as Object[], "Another user has updated this DosageUnit while you were editing")
                    render(view: "edit", model: [dosageUnitInstance: dosageUnitInstance])
                    return
                }
            }
            dosageUnitInstance.properties = params
            if (!dosageUnitInstance.hasErrors() && dosageUnitInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), dosageUnitInstance.id])}"
                redirect(action: "show", id: dosageUnitInstance.id)
            }
            else {
                render(view: "edit", model: [dosageUnitInstance: dosageUnitInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def dosageUnitInstance = DosageUnit.get(params.id)
        if (dosageUnitInstance) {
            try {
                dosageUnitInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dosageUnit.label', default: 'DosageUnit'), params.id])}"
            redirect(action: "list")
        }
    }
}
