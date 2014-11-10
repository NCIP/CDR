package nci.obbr.cahub.util

import nci.obbr.cahub.datarecords.CaseRecord
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
    

class ActivityEventController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = ActivityEvent

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [activityEventInstanceList: ActivityEvent.list(params), activityEventInstanceTotal: ActivityEvent.count()]
    }

    def create = {
        def activityEventInstance = new ActivityEvent()
        activityEventInstance.properties = params
        return [activityEventInstance: activityEventInstance]
    }

    def save = {
        def activityEventInstance = new ActivityEvent(params)
        if (activityEventInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), activityEventInstance.id])}"
            redirect(action: "show", id: activityEventInstance.id)
        }
        else {
            render(view: "create", model: [activityEventInstance: activityEventInstance])
        }
    }

    def show = {
        def activityEventInstance = ActivityEvent.get(params.id)
        if (!activityEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            [activityEventInstance: activityEventInstance]
        }
    }

    def edit = {
        def activityEventInstance = ActivityEvent.get(params.id)
        if (!activityEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [activityEventInstance: activityEventInstance]
        }
    }

    def update = {
        def activityEventInstance = ActivityEvent.get(params.id)
        if (activityEventInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (activityEventInstance.version > version) {
                    
                    activityEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'activityEvent.label', default: 'ActivityEvent')] as Object[], "Another user has updated this ActivityEvent while you were editing")
                    render(view: "edit", model: [activityEventInstance: activityEventInstance])
                    return
                }
            }
            activityEventInstance.properties = params
            if (!activityEventInstance.hasErrors() && activityEventInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), activityEventInstance.id])}"
                redirect(action: "show", id: activityEventInstance.id)
            }
            else {
                render(view: "edit", model: [activityEventInstance: activityEventInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def activityEventInstance = ActivityEvent.get(params.id)
        if (activityEventInstance) {
            try {
                activityEventInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activityEvent.label', default: 'ActivityEvent'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def retrieveEvent = {
        def c = ActivityEvent.createCriteria()
        def study = session.study?.code
        def org = session.org?.code
        def eventList = []
        def numResults = 250
        if (params.max) {
            numResults = params.max as int
        }
        
        eventList = c.list {
            if (study) {
                createAlias("study", "s")
                eq("s.code", study) 
            }
            if (org != 'VARI' && org != 'OBBR' && !session.authorities?.contains('ROLE_BPV_ELSI_DA')) {
                like("bssCode", org + "%")
            } else if (org == 'VARI') {
                createAlias("activityType", "a")
                or {
                    'in'("a.code", ["SHIP", "IMAGEREADY", "COLLECT", "UNUSEDKIT", "BPVCASE", "PRCAVAILABLE", "EMAIL", "SHIPPINGRECEIPT", "SHIPRECPTDISC", "SHIPINSP", "PROCESSEVT"])
                    and {
                        eq('a.code', 'QUERY')
                        eq('bssCode', 'VARI')
                    }
                }
            }
            order("dateCreated", "desc")
            maxResults(numResults)
        } 
        
        JSON.use("deep") {
            if (params.callback) {
                render "${params.callback.encodeAsURL()}(${eventList as JSON})"
            } else {
                render eventList as JSON
            }
        }
    }
}
