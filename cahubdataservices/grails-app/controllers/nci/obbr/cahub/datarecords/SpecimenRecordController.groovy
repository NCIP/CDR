package nci.obbr.cahub.datarecords

import grails.converters.JSON
import nci.obbr.cahub.staticmembers.*
import grails.plugins.springsecurity.Secured 

class SpecimenRecordController {

    //def accessPrivilegeService
    def scaffold = SpecimenRecord

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [specimenRecordInstanceList: SpecimenRecord.list(params), specimenRecordInstanceTotal: SpecimenRecord.count()]
    }

    def create = {
        def specimenRecordInstance = new SpecimenRecord()
        specimenRecordInstance.properties = params
        return [specimenRecordInstance: specimenRecordInstance]
    }

    def save = {
        def specimenRecordInstance = new SpecimenRecord(params)
        if (specimenRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), specimenRecordInstance.id])}"
            redirect(action: "show", id: specimenRecordInstance.id)
        }
        else {
            render(view: "create", model: [specimenRecordInstance: specimenRecordInstance])
        }
    }

    def show = {
        def specimenRecordInstance
        
        if (params.id)
        {
            boolean isNumber = true
            try
            {
                Integer.parseInt(params.id)
            }
            catch(NumberFormatException ee)
            {
                isNumber = false
            }
            if (isNumber) 
            {
                specimenRecordInstance = SpecimenRecord.get(params.id)
            }
            if (!specimenRecordInstance) 
            {
                specimenRecordInstance = SpecimenRecord.findBySpecimenId(params.id)
            }
        }
       /**  def caseRecord = specimenRecordInstance?.caseRecord
         int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'show')
         if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
         }
        **/ //use config.groovy to control access
        if (!specimenRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [specimenRecordInstance: specimenRecordInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN', 'ROLE_NCI-FREDERICK_CAHUB_DM'])
    def edit = {
        def specimenRecordInstance = SpecimenRecord.get(params.id)
        def maximized = params._action_edit == "Edit" || params._action_edit == null ? false : true
        if (!specimenRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [specimenRecordInstance: specimenRecordInstance, maximized: maximized]
        }
    }

    def update = {
        //println params
        def specimenRecordInstance = SpecimenRecord.get(params.id)
        if (specimenRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (specimenRecordInstance.version > version) {
                    
                    specimenRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'specimenRecord.label', default: 'SpecimenRecord')] as Object[], "Another user has updated this SpecimenRecord while you were editing")
                    render(view: "edit", model: [specimenRecordInstance: specimenRecordInstance])
                    return
                }
            }
            specimenRecordInstance.properties = params
            if (!specimenRecordInstance.hasErrors() && specimenRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), specimenRecordInstance.id])}"
                if(params.viajson == "yes"){
                    def payload = ["results": "success"]
                    render "${params.callback.encodeAsURL()}(${payload as JSON})"
                } else {
                    redirect(action: "show", id: specimenRecordInstance.id)
                }
            }
            else {
                render(view: "edit", model: [specimenRecordInstance: specimenRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def specimenRecordInstance = SpecimenRecord.get(params.id)
        if (specimenRecordInstance) {
            try {
                specimenRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specimenRecord.label', default: 'SpecimenRecord'), params.id])}"
            redirect(action: "list")
        }
    }
}
