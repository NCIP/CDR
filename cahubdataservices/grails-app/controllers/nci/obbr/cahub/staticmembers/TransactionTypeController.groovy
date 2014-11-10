package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
    
class TransactionTypeController {

    def scaffold = TransactionType
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [transactionTypeInstanceList: TransactionType.list(params), transactionTypeInstanceTotal: TransactionType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def transactionTypeInstance = new TransactionType()
        transactionTypeInstance.properties = params
        return [transactionTypeInstance: transactionTypeInstance]
    }

    def save = {
        def transactionTypeInstance = new TransactionType(params)
        if (transactionTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), transactionTypeInstance.id])}"
            redirect(action: "show", id: transactionTypeInstance.id)
        }
        else {
            render(view: "create", model: [transactionTypeInstance: transactionTypeInstance])
        }
    }

    def show = {
        def transactionTypeInstance = TransactionType.get(params.id)
        if (!transactionTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [transactionTypeInstance: transactionTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def transactionTypeInstance = TransactionType.get(params.id)
        if (!transactionTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [transactionTypeInstance: transactionTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def transactionTypeInstance = TransactionType.get(params.id)
        if (transactionTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (transactionTypeInstance.version > version) {
                    
                    transactionTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'transactionType.label', default: 'TransactionType')] as Object[], "Another user has updated this TransactionType while you were editing")
                    render(view: "edit", model: [transactionTypeInstance: transactionTypeInstance])
                    return
                }
            }
            transactionTypeInstance.properties = params
            if (!transactionTypeInstance.hasErrors() && transactionTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), transactionTypeInstance.id])}"
                redirect(action: "show", id: transactionTypeInstance.id)
            }
            else {
                render(view: "edit", model: [transactionTypeInstance: transactionTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def transactionTypeInstance = TransactionType.get(params.id)
        if (transactionTypeInstance) {
            try {
                transactionTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), params.id])}"
            redirect(action: "list")
        }
    }
}
