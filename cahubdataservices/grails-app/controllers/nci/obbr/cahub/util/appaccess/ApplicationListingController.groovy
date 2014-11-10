package nci.obbr.cahub.util.appaccess
import grails.plugins.springsecurity.Secured
    
class ApplicationListingController {
    
    static scaffold=ApplicationListing

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [applicationListingInstanceList: ApplicationListing.list(params), applicationListingInstanceTotal: ApplicationListing.count()]
    }

    def create = {
        def applicationListingInstance = new ApplicationListing()
        applicationListingInstance.properties = params
        return [applicationListingInstance: applicationListingInstance]
    }

    def save = {
        def applicationListingInstance = new ApplicationListing(params)
        if (applicationListingInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), applicationListingInstance.id])}"
            redirect(action: "show", id: applicationListingInstance.id)
        }
        else {
            render(view: "create", model: [applicationListingInstance: applicationListingInstance])
        }
    }

    def show = {
        def applicationListingInstance = ApplicationListing.get(params.id)
        if (!applicationListingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), params.id])}"
            redirect(action: "list")
        }
        else {
            [applicationListingInstance: applicationListingInstance]
        }
    }

    def edit = {
        def applicationListingInstance = ApplicationListing.get(params.id)
        if (!applicationListingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [applicationListingInstance: applicationListingInstance]
        }
    }

    def update = {
        def applicationListingInstance = ApplicationListing.get(params.id)
        if (applicationListingInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (applicationListingInstance.version > version) {
                    
                    applicationListingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'applicationListing.label', default: 'ApplicationListing')] as Object[], "Another user has updated this ApplicationListing while you were editing")
                    render(view: "edit", model: [applicationListingInstance: applicationListingInstance])
                    return
                }
            }
            applicationListingInstance.properties = params
            if (!applicationListingInstance.hasErrors() && applicationListingInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), applicationListingInstance.id])}"
                redirect(action: "show", id: applicationListingInstance.id)
            }
            else {
                render(view: "edit", model: [applicationListingInstance: applicationListingInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def applicationListingInstance = ApplicationListing.get(params.id)
        if (applicationListingInstance) {
            try {
                applicationListingInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'applicationListing.label', default: 'ApplicationListing'), params.id])}"
            redirect(action: "list")
        }
    }
}
