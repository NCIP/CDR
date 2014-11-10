package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured

class ChpBloodRecordController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [chpBloodRecordInstanceList: ChpBloodRecord.list(params), chpBloodRecordInstanceTotal: ChpBloodRecord.count()]
    }
    
    def listcase = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        //println params
        //println CaseRecord.findById(params.id)
        def chpService = new nci.obbr.cahub.ChpService()
        def chpBloodList = CaseRecord.findById(params.id).specimens.chpBloodRecord
        // println "chpBloodList.size(): " + chpBloodList.size()
        def surgDate
        def parentSpecimen
        def parentCase = CaseRecord.findById(params.id)
        def grossDiagnosis
        
        if (parentCase) {
            parentCase.specimens.each() {
                // @TODO: when Module moves out of fixative into another category, this will need to be changed
                if (it.fixative.code == "MODULE") {
                    surgDate = it.bpvTissueForm.surgDate
                    grossDiagnosis = it.bpvTissueForm.grossDiagx
                    // @TODO: surgDate is an attribute of Case.  There can be multiple modules per surgDate.
                }
            }
        }
        def chpBloodKeep = []
        chpBloodList.each() {
            if (it) {
                
                def chpBloodMashup = new ChpBloodMashup (
                    id:             it.id,
                    specimenRecord: it.specimenRecord,
                    generation:     0,
                    // surgDate:       it.specimenRecord.caseRecord.bpvTissueForm.surgDate,
                    surgDate:       surgDate,
                    bloodSource:    it.specimenRecord.caseRecord.bpvBloodForm.bloodSource,
                    dateTimeBloodDraw: it.specimenRecord.caseRecord.bpvBloodForm.dateTimeBloodDraw,
                    volume:         it.volume,
                    volUnits:       it.volUnits,
                    containerType:  it.specimenRecord.containerType,
                    fixative:       it.specimenRecord.fixative,
                    acquisType:     it.specimenRecord.tissueType,
                    dateTimeBloodReceived: it.specimenRecord.caseRecord.bpvBloodForm.dateTimeBloodReceived,
                    bloodProcStart: it.bloodProcStart,
                    bloodProcEnd:   it.bloodProcEnd,
                    hemolysis:      it.hemolysis,
                    bloodFrozen:    it.bloodFrozen,
                    bloodStorage:   it.bloodStorage,
                    bloodProcComment: it.bloodProcComment,
                    bloodStorageComment: it.bloodStorageComment
                )
            chpBloodKeep.add(chpBloodMashup)
            }
        }
        chpBloodKeep = chpBloodKeep.sort{ it.bloodProcStart }
        
        def chpBloodChildren = []
        chpBloodKeep.each() {
            if ( it.specimenRecord.parentSpecimen == null ) { // in other words, if the parent of this specimen is the CaseRecord, it has no specimen parents, it is a top-level specimen
                //println "Calling getChildren from controller: " 
                //println "it.specimenRecord: " +it.specimenRecord
                //println "it.parentSpecimen: " 
                it.generation = 1
                //println "it.generation:     "  + it.generation.toString()
                
                chpBloodChildren.add(it)
                chpService.getChildren(chpBloodKeep, chpBloodChildren, it)
            }
        }
        [chpBloodRecordInstanceList: chpBloodChildren, chpBloodRecordInstanceTotal: chpBloodChildren.count(), grossDiagnosis:grossDiagnosis]    
        // [chpBloodRecordInstanceList: chpBloodKeep, chpBloodRecordInstanceTotal: chpBloodKeep.count()]
    }

    def create = {
        def chpBloodRecordInstance = new ChpBloodRecord()
        chpBloodRecordInstance.properties = params
        return [chpBloodRecordInstance: chpBloodRecordInstance]
    }

    def save = {
        def chpBloodRecordInstance = new ChpBloodRecord(params)
        if (chpBloodRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), chpBloodRecordInstance.id])}"
            redirect(action: "show", id: chpBloodRecordInstance.id)
        }
        else {
            render(view: "create", model: [chpBloodRecordInstance: chpBloodRecordInstance])
        }
    }

    def show = {
        def chpBloodRecordInstance = ChpBloodRecord.get(params.id)
        if (!chpBloodRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [chpBloodRecordInstance: chpBloodRecordInstance]
        }
    }

    def edit = {
        def chpBloodRecordInstance = ChpBloodRecord.get(params.id)
        if (!chpBloodRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [chpBloodRecordInstance: chpBloodRecordInstance]
        }
    }

    def update = {
        def chpBloodRecordInstance = ChpBloodRecord.get(params.id)
        if (chpBloodRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (chpBloodRecordInstance.version > version) {
                    
                    chpBloodRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord')] as Object[], "Another user has updated this ChpBloodRecord while you were editing")
                    render(view: "edit", model: [chpBloodRecordInstance: chpBloodRecordInstance])
                    return
                }
            }
            chpBloodRecordInstance.properties = params
            if (!chpBloodRecordInstance.hasErrors() && chpBloodRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), chpBloodRecordInstance.id])}"
                redirect(action: "show", id: chpBloodRecordInstance.id)
            }
            else {
                render(view: "edit", model: [chpBloodRecordInstance: chpBloodRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def chpBloodRecordInstance = ChpBloodRecord.get(params.id)
        if (chpBloodRecordInstance) {
            try {
                chpBloodRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
