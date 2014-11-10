package nci.obbr.cahub.datarecords

import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.staticmembers.*

class ShippingEventController {

    def scaffold = ShippingEvent
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        def max = params.max
        def offset = params.offset
        if(!offset)
            offset = 0
            
        def count=0
       
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        def withdrCaseId = []
        withdrCase.each {
            withdrCaseId.add(it.caseId) 
        }
        
        def result_count
        if (withdrCaseId) {
            def c = ShippingEvent.createCriteria()
            count = c.count {
                or {
                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                order("dateCreated", "desc")
            }
        } else {
            def c = ShippingEvent.createCriteria()
            count = c.count {      
                order("dateCreated", "desc")
            } 
        }
        
        def shippingEventInstanceList 
        if (withdrCaseId) {
            def c2 = ShippingEvent.createCriteria()
            shippingEventInstanceList = c2.list(max:max, offset:offset) {
                or {
                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                order("dateCreated", "desc")
            }
        } else {
            def c2 = ShippingEvent.createCriteria()
            shippingEventInstanceList = c2.list(max:max, offset:offset) {  
                order("dateCreated", "desc")
            } 
        }
        
        // [shippingEventInstanceList: ShippingEvent.list(params), shippingEventInstanceTotal: ShippingEvent.count()]
        [shippingEventInstanceList: shippingEventInstanceList, shippingEventInstanceTotal:count]
    }
    
    def listByCase = { 
        def count=0
       
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        def withdrCaseId = []
        withdrCase.each {
            withdrCaseId.add(it.caseId) 
        }
        
        def shippingEventInstanceList 
        if (withdrCaseId) {
            def c2 = ShippingEvent.createCriteria()
            shippingEventInstanceList = c2.list {
                eq("caseId", params.caseId)
                or {
                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                order("dateCreated", "desc")
            }
        } else {
            def c2 = ShippingEvent.createCriteria()
            shippingEventInstanceList = c2.list {
                eq("caseId", params.caseId)
                order("dateCreated", "desc")
            }
        }
        
        def caseRecord = CaseRecord.findByCaseId(params.caseId)
        
        [shippingEventInstanceList: shippingEventInstanceList, caseRecord: caseRecord]
    }

    def create = {
        def shippingEventInstance = new ShippingEvent()
        shippingEventInstance.properties = params
        return [shippingEventInstance: shippingEventInstance]
    }

    def save = {
        def shippingEventInstance = new ShippingEvent(params)
        if (shippingEventInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), shippingEventInstance.id])}"
            redirect(action: "show", id: shippingEventInstance.id)
        }
        else {
            render(view: "create", model: [shippingEventInstance: shippingEventInstance])
        }
    }

    def show = {
        def shippingEventInstance = ShippingEvent.get(params.id)
        if (!shippingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            [shippingEventInstance: shippingEventInstance]
        }
    }
    
    def showByCbrId = {
        def shippingEventInstance = ShippingEvent.findByBio4dEventId(params.id)
        if (!shippingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            redirect(action: "show", id: shippingEventInstance.id)
        }
    }
    
    def showByCbrIdAndCase = {
        def shipEvtList = ShippingEvent.findAllByBio4dEventId(params.id)
        def shippingEventInstance
        if (shipEvtList.size()>1) {
            shippingEventInstance = ShippingEvent.findByBio4dEventIdAndCaseId(params.id, params.caseId)
        } else {
            shippingEventInstance = ShippingEvent.findByBio4dEventId(params.id)
        }        
        if (!shippingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            redirect(action: "show", id: shippingEventInstance.id)
        }
    }    

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def shippingEventInstance = ShippingEvent.get(params.id)
        if (!shippingEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [shippingEventInstance: shippingEventInstance]
        }
    }

    def update = {
        def shippingEventInstance = ShippingEvent.get(params.id)
        if (shippingEventInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (shippingEventInstance.version > version) {
                    
                    shippingEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'shippingEvent.label', default: 'ShippingEvent')] as Object[], "Another user has updated this ShippingEvent while you were editing")
                    render(view: "edit", model: [shippingEventInstance: shippingEventInstance])
                    return
                }
            }
            shippingEventInstance.properties = params
            if (!shippingEventInstance.hasErrors() && shippingEventInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), shippingEventInstance.id])}"
                redirect(action: "show", id: shippingEventInstance.id)
            }
            else {
                render(view: "edit", model: [shippingEventInstance: shippingEventInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def shippingEventInstance = ShippingEvent.get(params.id)
        if (shippingEventInstance) {
            try {
                shippingEventInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shippingEvent.label', default: 'ShippingEvent'), params.id])}"
            redirect(action: "list")
        }
    }
}
