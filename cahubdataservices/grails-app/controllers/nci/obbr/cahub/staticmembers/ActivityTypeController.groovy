package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured

class ActivityTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = ActivityType

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [activityTypeInstanceList: ActivityType.list(params), activityTypeInstanceTotal: ActivityType.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def activityTypeInstance = new ActivityType()
        activityTypeInstance.properties = params
        return [activityTypeInstance: activityTypeInstance]
    }

    def save = {
        def activityTypeInstance = new ActivityType(params)
        if (activityTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'activityType.label', default: 'ActivityType'), activityTypeInstance.id])}"
            redirect(action: "show", id: activityTypeInstance.id)
        }
        else {
            render(view: "create", model: [activityTypeInstance: activityTypeInstance])
        }
    }

    def show = {
        def activityTypeInstance = ActivityType.get(params.id)
        if (!activityTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityType.label', default: 'ActivityType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [activityTypeInstance: activityTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def activityTypeInstance = ActivityType.get(params.id)
        if (!activityTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityType.label', default: 'ActivityType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [activityTypeInstance: activityTypeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def activityTypeInstance = ActivityType.get(params.id)
        if (activityTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (activityTypeInstance.version > version) {
                    
                    activityTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'activityType.label', default: 'ActivityType')] as Object[], "Another user has updated this ActivityType while you were editing")
                    render(view: "edit", model: [activityTypeInstance: activityTypeInstance])
                    return
                }
            }
            activityTypeInstance.properties = params
            if (!activityTypeInstance.hasErrors() && activityTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'activityType.label', default: 'ActivityType'), activityTypeInstance.id])}"
                redirect(action: "show", id: activityTypeInstance.id)
            }
            else {
                render(view: "edit", model: [activityTypeInstance: activityTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityType.label', default: 'ActivityType'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def activityTypeInstance = ActivityType.get(params.id)
        if (activityTypeInstance) {
            try {
                activityTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'activityType.label', default: 'ActivityType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'activityType.label', default: 'ActivityType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityType.label', default: 'ActivityType'), params.id])}"
            redirect(action: "list")
        }
    }
}
