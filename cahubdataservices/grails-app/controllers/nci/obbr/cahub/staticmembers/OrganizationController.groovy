package nci.obbr.cahub.staticmembers

import grails.plugins.springsecurity.Secured 

class OrganizationController {

    def scaffold = Organization
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [organizationInstanceList: Organization.list(params), organizationInstanceTotal: Organization.count()]
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def create = {
        def organizationInstance = new Organization()
        organizationInstance.properties = params
        return [organizationInstance: organizationInstance]
    }

    def save = {
        def organizationInstance = new Organization(params)
        if (organizationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])}"
            redirect(action: "show", id: organizationInstance.id)
        }
        else {
            render(view: "create", model: [organizationInstance: organizationInstance])
        }
    }

    def show = {
        def organizationInstance = Organization.get(params.id)
        if (!organizationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
        else {
            [organizationInstance: organizationInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def organizationInstance = Organization.get(params.id)
        if (!organizationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [organizationInstance: organizationInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def update = {
        def organizationInstance = Organization.get(params.id)
        if (organizationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (organizationInstance.version > version) {
                    
                    organizationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'organization.label', default: 'Organization')] as Object[], "Another user has updated this Organization while you were editing")
                    render(view: "edit", model: [organizationInstance: organizationInstance])
                    return
                }
            }
            organizationInstance.properties = params
            if (!organizationInstance.hasErrors() && organizationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])}"
                redirect(action: "show", id: organizationInstance.id)
            }
            else {
                render(view: "edit", model: [organizationInstance: organizationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
    }

       //We don't want anyone else to delete
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def organizationInstance = Organization.get(params.id)
        if (organizationInstance) {
            try {
                organizationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
    }
}
