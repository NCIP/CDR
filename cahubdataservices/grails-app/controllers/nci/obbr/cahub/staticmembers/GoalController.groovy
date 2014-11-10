package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
  
class GoalController {

    def scaffold = Goal
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [goalInstanceList: Goal.list(params), goalInstanceTotal: Goal.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def goalInstance = new Goal()
        goalInstance.properties = params
        return [goalInstance: goalInstance]
    }

    def save = {
        def goalInstance = new Goal(params)
        if (goalInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'goal.label', default: 'Goal'), goalInstance.id])}"
            redirect(action: "show", id: goalInstance.id)
        }
        else {
            render(view: "create", model: [goalInstance: goalInstance])
        }
    }

    def show = {
        def goalInstance = Goal.get(params.id)
        if (!goalInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'goal.label', default: 'Goal'), params.id])}"
            redirect(action: "list")
        }
        else {
            [goalInstance: goalInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def goalInstance = Goal.get(params.id)
        if (!goalInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'goal.label', default: 'Goal'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [goalInstance: goalInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def goalInstance = Goal.get(params.id)
        if (goalInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (goalInstance.version > version) {
                    
                    goalInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'goal.label', default: 'Goal')] as Object[], "Another user has updated this Goal while you were editing")
                    render(view: "edit", model: [goalInstance: goalInstance])
                    return
                }
            }
            goalInstance.properties = params
            if (!goalInstance.hasErrors() && goalInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'goal.label', default: 'Goal'), goalInstance.id])}"
                redirect(action: "show", id: goalInstance.id)
            }
            else {
                render(view: "edit", model: [goalInstance: goalInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'goal.label', default: 'Goal'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def goalInstance = Goal.get(params.id)
        if (goalInstance) {
            try {
                goalInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'goal.label', default: 'Goal'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'goal.label', default: 'Goal'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'goal.label', default: 'Goal'), params.id])}"
            redirect(action: "list")
        }
    }
}
