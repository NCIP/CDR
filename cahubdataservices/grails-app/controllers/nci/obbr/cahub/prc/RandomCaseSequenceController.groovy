package nci.obbr.cahub.prc
import grails.plugins.springsecurity.Secured
class RandomCaseSequenceController {

    def scaffold = RandomCaseSequence
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [randomCaseSequenceInstanceList: RandomCaseSequence.list(params), randomCaseSequenceInstanceTotal: RandomCaseSequence.count()]
    }

    def create = {
        def randomCaseSequenceInstance = new RandomCaseSequence()
        randomCaseSequenceInstance.properties = params
        return [randomCaseSequenceInstance: randomCaseSequenceInstance]
    }

    def save = {
        def randomCaseSequenceInstance = new RandomCaseSequence(params)
        if (randomCaseSequenceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), randomCaseSequenceInstance.id])}"
            redirect(action: "show", id: randomCaseSequenceInstance.id)
        }
        else {
            render(view: "create", model: [randomCaseSequenceInstance: randomCaseSequenceInstance])
        }
    }

    def show = {
        def randomCaseSequenceInstance = RandomCaseSequence.get(params.id)
        if (!randomCaseSequenceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), params.id])}"
            redirect(action: "list")
        }
        else {
            [randomCaseSequenceInstance: randomCaseSequenceInstance]
        }
    }

    def edit = {
        def randomCaseSequenceInstance = RandomCaseSequence.get(params.id)
        if (!randomCaseSequenceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [randomCaseSequenceInstance: randomCaseSequenceInstance]
        }
    }

    def update = {
        def randomCaseSequenceInstance = RandomCaseSequence.get(params.id)
        if (randomCaseSequenceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (randomCaseSequenceInstance.version > version) {
                    
                    randomCaseSequenceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence')] as Object[], "Another user has updated this RandomCaseSequence while you were editing")
                    render(view: "edit", model: [randomCaseSequenceInstance: randomCaseSequenceInstance])
                    return
                }
            }
            randomCaseSequenceInstance.properties = params
            if (!randomCaseSequenceInstance.hasErrors() && randomCaseSequenceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), randomCaseSequenceInstance.id])}"
                redirect(action: "show", id: randomCaseSequenceInstance.id)
            }
            else {
                render(view: "edit", model: [randomCaseSequenceInstance: randomCaseSequenceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def randomCaseSequenceInstance = RandomCaseSequence.get(params.id)
        if (randomCaseSequenceInstance) {
            try {
                randomCaseSequenceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'randomCaseSequence.label', default: 'RandomCaseSequence'), params.id])}"
            redirect(action: "list")
        }
    }
}
