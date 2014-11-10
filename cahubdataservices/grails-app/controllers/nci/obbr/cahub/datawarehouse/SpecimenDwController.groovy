package nci.obbr.cahub.datawarehouse

import grails.plugins.springsecurity.Secured

class SpecimenDwController {

    def scaffold = SpecimenDw
    def specimenDwService
    
    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [specimenDwInstanceList: SpecimenDw.list(params), specimenDwInstanceTotal: SpecimenDw.count()]
    }

    def create = {
        def specimenDwInstance = new SpecimenDw()
        specimenDwInstance.properties = params
        specimenDwInstance = specimenDwService.populateSpecimenDw(specimenDwInstance) 
        return [specimenDwInstance: specimenDwInstance]
    }

    def save = {
        def specimenDwInstance = new SpecimenDw(params)
        if (specimenDwInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'specimenDw.label', default: 'SpecimenDw'), specimenDwInstance.id])}"
            redirect(action: "show", id: specimenDwInstance.id)
        }
        else {
            render(view: "create", model: [specimenDwInstance: specimenDwInstance])
        }
    }

    def show = {
        def specimenDwInstance = SpecimenDw.get(params.id)
        if (!specimenDwInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenDw.label', default: 'SpecimenDw'), params.id])}"
            redirect(action: "list")
        }
        else {
            [specimenDwInstance: specimenDwInstance]
        }
    }

    def edit = {
        def specimenDwInstance = SpecimenDw.get(params.id)
        if (!specimenDwInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenDw.label', default: 'SpecimenDw'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [specimenDwInstance: specimenDwInstance]
        }
    }

    def update = {
        def specimenDwInstance = SpecimenDw.get(params.id)
        if (specimenDwInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (specimenDwInstance.version > version) {
                    
                    specimenDwInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'specimenDw.label', default: 'SpecimenDw')] as Object[], "Another user has updated this SpecimenDw while you were editing")
                    render(view: "edit", model: [specimenDwInstance: specimenDwInstance])
                    return
                }
            }
            specimenDwInstance.properties = params
            if (!specimenDwInstance.hasErrors() && specimenDwInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'specimenDw.label', default: 'SpecimenDw'), specimenDwInstance.id])}"
                redirect(action: "show", id: specimenDwInstance.id)
            }
            else {
                render(view: "edit", model: [specimenDwInstance: specimenDwInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenDw.label', default: 'SpecimenDw'), params.id])}"
            redirect(action: "list")
        }
    }

    //We don't want anyone else to edit case records
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        /* from http://grails.org/doc/latest/guide/GORM.html : */
        /* Note that Grails does not supply a deleteAll method as deleting data is discouraged and can often be avoided through boolean flags/logic. */
        specimenDwService.deleteSpecimenDw() 
        redirect(action: "create")
    }
}
