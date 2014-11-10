package nci.obbr.cahub.util.bpv

class BatchProcessingController {
    def batchProcessingService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [batchProcessingInstanceList: BatchProcessing.list(params), batchProcessingInstanceTotal: BatchProcessing.count()]
    }

    def create = {
        def batchProcessingInstance = new BatchProcessing()
        batchProcessingInstance.properties = params
        return [batchProcessingInstance: batchProcessingInstance]
    }

    def save = {
        def batchProcessingInstance = new BatchProcessing(params)
        if (batchProcessingInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), batchProcessingInstance.id])}"
            redirect(action: "show", id: batchProcessingInstance.id)
        }
        else {
            render(view: "create", model: [batchProcessingInstance: batchProcessingInstance])
        }
    }

    def show = {
        def batchProcessingInstance = BatchProcessing.get(params.id)
        if (!batchProcessingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), params.id])}"
            redirect(action: "list")
        }
        else {
            [batchProcessingInstance: batchProcessingInstance]
        }
    }

    def edit = {
        def batchProcessingInstance = BatchProcessing.get(params.id)
        if (!batchProcessingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [batchProcessingInstance: batchProcessingInstance]
        }
    }

    def update = {
        def batchProcessingInstance = BatchProcessing.get(params.id)
        if (batchProcessingInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (batchProcessingInstance.version > version) {
                    
                    batchProcessingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'batchProcessing.label', default: 'BatchProcessing')] as Object[], "Another user has updated this BatchProcessing while you were editing")
                    render(view: "edit", model: [batchProcessingInstance: batchProcessingInstance])
                    return
                }
            }
            batchProcessingInstance.properties = params
            if (!batchProcessingInstance.hasErrors() && batchProcessingInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), batchProcessingInstance.id])}"
                redirect(action: "show", id: batchProcessingInstance.id)
            }
            else {
                render(view: "edit", model: [batchProcessingInstance: batchProcessingInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def batchProcessingInstance = BatchProcessing.get(params.id)
        if (batchProcessingInstance) {
            try {
                batchProcessingInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batchProcessing.label', default: 'BatchProcessing'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def trackModule = {
        batchProcessingService.trackModule()
        render "done"
    }
}
