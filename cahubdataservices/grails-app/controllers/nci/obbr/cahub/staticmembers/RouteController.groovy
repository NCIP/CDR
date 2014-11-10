package nci.obbr.cahub.staticmembers
import grails.plugins.springsecurity.Secured
    
class RouteController {
    def scaffold = Route
     
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [routeInstanceList: Route.list(params), routeInstanceTotal: Route.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def routeInstance = new Route()
        routeInstance.properties = params
        return [routeInstance: routeInstance]
    }

    def save = {
        def routeInstance = new Route(params)
        if (routeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'route.label', default: 'Route'), routeInstance.id])}"
            redirect(action: "show", id: routeInstance.id)
        }
        else {
            render(view: "create", model: [routeInstance: routeInstance])
        }
    }

    def show = {
        def routeInstance = Route.get(params.id)
        if (!routeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'route.label', default: 'Route'), params.id])}"
            redirect(action: "list")
        }
        else {
            [routeInstance: routeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def routeInstance = Route.get(params.id)
        if (!routeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'route.label', default: 'Route'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [routeInstance: routeInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def routeInstance = Route.get(params.id)
        if (routeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (routeInstance.version > version) {
                    
                    routeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'route.label', default: 'Route')] as Object[], "Another user has updated this Route while you were editing")
                    render(view: "edit", model: [routeInstance: routeInstance])
                    return
                }
            }
            routeInstance.properties = params
            if (!routeInstance.hasErrors() && routeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'route.label', default: 'Route'), routeInstance.id])}"
                redirect(action: "show", id: routeInstance.id)
            }
            else {
                render(view: "edit", model: [routeInstance: routeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'route.label', default: 'Route'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def routeInstance = Route.get(params.id)
        if (routeInstance) {
            try {
                routeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'route.label', default: 'Route'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'route.label', default: 'Route'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'route.label', default: 'Route'), params.id])}"
            redirect(action: "list")
        }
    }
}
