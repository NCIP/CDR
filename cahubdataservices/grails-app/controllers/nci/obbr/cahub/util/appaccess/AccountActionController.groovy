package nci.obbr.cahub.util.appaccess
import grails.plugins.springsecurity.Secured
     
class AccountActionController {
    
    static scaffold=AccountAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [accountActionInstanceList: AccountAction.list(params), accountActionInstanceTotal: AccountAction.count()]
    }

    def create = {
        def accountActionInstance = new AccountAction()
        accountActionInstance.properties = params
        return [accountActionInstance: accountActionInstance]
    }

    def save = {
        def accountActionInstance = new AccountAction(params)
        if (accountActionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), accountActionInstance.id])}"
            redirect(action: "show", id: accountActionInstance.id)
        }
        else {
            render(view: "create", model: [accountActionInstance: accountActionInstance])
        }
    }

    def show = {
        def accountActionInstance = AccountAction.get(params.id)
        if (!accountActionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accountActionInstance: accountActionInstance]
        }
    }

    def edit = {
        def accountActionInstance = AccountAction.get(params.id)
        if (!accountActionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accountActionInstance: accountActionInstance]
        }
    }

    def update = {
        def accountActionInstance = AccountAction.get(params.id)
        if (accountActionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (accountActionInstance.version > version) {
                    
                    accountActionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accountAction.label', default: 'AccountAction')] as Object[], "Another user has updated this AccountAction while you were editing")
                    render(view: "edit", model: [accountActionInstance: accountActionInstance])
                    return
                }
            }
            accountActionInstance.properties = params
            if (!accountActionInstance.hasErrors() && accountActionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), accountActionInstance.id])}"
                redirect(action: "show", id: accountActionInstance.id)
            }
            else {
                render(view: "edit", model: [accountActionInstance: accountActionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def accountActionInstance = AccountAction.get(params.id)
        if (accountActionInstance) {
            try {
                accountActionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountAction.label', default: 'AccountAction'), params.id])}"
            redirect(action: "list")
        }
    }
}
