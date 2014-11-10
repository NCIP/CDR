package nci.obbr.cahub.forms.bpv.clinicaldataentry

import grails.plugins.springsecurity.Secured
import nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord
import nci.obbr.cahub.util.AppSetting
import grails.util.GrailsUtil
import nci.obbr.cahub.SendMailService
import nci.obbr.cahub.util.querytracker.Query
import nci.obbr.cahub.staticmembers.Organization
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.QueryType
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BpvClinicalDataEntryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService    
    def sendMailService
    def recipient
    def subject
    def emailBody
    def queryService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvClinicalDataEntryInstanceList: BpvClinicalDataEntry.list(params), bpvClinicalDataEntryInstanceTotal: BpvClinicalDataEntry.count()]
    }

    def create = {
        def bpvClinicalDataEntryInstance = new BpvClinicalDataEntry()
        bpvClinicalDataEntryInstance.properties = params
        return [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance]
    }

    def save = {
        def bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
        
        def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
        if (caseRecord)
        {
            def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
            if (bpvClinicalDataEntryInstance2)
            {
                bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your creating")
                
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                return
            }
        }
        
        boolean successSaved = false
        try
        {
            successSaved = bpvClinicalDataEntryInstance.save(flush: true)
        }
        catch(java.sql.BatchUpdateException se)
        {
            bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName] as Object[], "33Another user has updated this Clinical Data Entry form while you were editing")
            render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            return
        }
        
        if(successSaved) {
            flash.message = "${message(code: 'default.created.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "editWithValidation", id: bpvClinicalDataEntryInstance.id)
        } else {
            render(view: "create", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        }
    }

    def show = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        //println 'CCC-- currentV=' + ApplicationHolder.application.metadata['app.version'] + ', caseVer=' + bpvClinicalDataEntryInstance.caseRecord.cdrVer
        
        if(!bpvClinicalDataEntryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvClinicalDataEntryInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(bpvClinicalDataEntryInstance.caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }            
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvClinicalDataEntryInstance.caseRecord, session, 'edit') == 0)
            [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance, canResume: canResume]
        }
    }

    def edit = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if(!bpvClinicalDataEntryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvClinicalDataEntryInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvClinicalDataEntryInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            validateFields(bpvClinicalDataEntryInstance)
            def canSubmit = !bpvClinicalDataEntryInstance.errors.hasErrors()
            bpvClinicalDataEntryInstance.clearErrors()
            return [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance, canSubmit: canSubmit]
        }
    }

    def editWithValidation = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if(!bpvClinicalDataEntryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvClinicalDataEntryInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvClinicalDataEntryInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            validateFields(bpvClinicalDataEntryInstance)
            render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance, canSubmit: !bpvClinicalDataEntryInstance.errors.hasErrors()])
        }
    }

    def update = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            if (params.prevCancerDiagDt.equalsIgnoreCase("Select Date")) {
                params.prevCancerDiagDt=""
            }
            if (params.chemoTherb4SurgDt.equalsIgnoreCase("Select Date")) {
                params.chemoTherb4SurgDt=""
            }
            if (params.irradTherb4SurgDt.equalsIgnoreCase("Select Date")) {
                params.irradTherb4SurgDt=""
            }
            if (params.immTherb4SurgDt.equalsIgnoreCase("Select Date")) {
                params.immTherb4SurgDt=""
            }
            if (params.hormTherb4SurgDt.equalsIgnoreCase("Select Date")) {
                params.hormTherb4SurgDt=""
            }
            bpvClinicalDataEntryInstance.properties = params
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "editWithValidation", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }
    
def savePrevCancerDiag = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Previous Cancer Diagnosis)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "prevcancer"
            therapyRecord.descTherapy = params.prevCancerDiagDesc

            if (params.prevCancerDiagDt!="Select Date") {
                if ((new Date(params.prevCancerDiagDt)) > new Date()) {
                    redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                    flash.message = "Previous malignancy diagnosis date cannot be in the future"
                    return
                } else {
                    therapyRecord.therapyDate = new Date(params.prevCancerDiagDt)
                }
                
            } else {
                params.prevCancerDiagDt=""
            }
            if (params.prevCancerDiagEst!='') {
             therapyRecord.howLongAgo = Double.parseDouble(params.prevCancerDiagEst)   
            }
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }                   
    
    def saveChemo = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Chemotheraphy)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "chemo"
            therapyRecord.descTherapy = params.chemoTherb4SurgDesc
            if (params.chemoTherb4SurgDt!="Select Date") {
                if ((new Date(params.chemoTherb4SurgDt)) > new Date()) {
                    redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                    flash.message = "Chemotherapy date cannot be in the future"
                    return
                } else {
                    therapyRecord.therapyDate = new Date(params.chemoTherb4SurgDt)
                }
            } else {
                params.chemoTherb4SurgDt=""
            }
            if (params.chemoTherb4SurgEst!='') {
                therapyRecord.howLongAgo = Double.parseDouble(params.chemoTherb4SurgEst)  
        }
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }    
    
    def saveIrradTher = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Radidation Therathy)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "radiation"
            therapyRecord.descTherapy = params.irradTherb4SurgDesc
            if (params.irradTherb4SurgDt!="Select Date") {
                if ((new Date(params.irradTherb4SurgDt)) > new Date()) {
                    redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                    flash.message = "Radiation therapy date cannot be in the future"
                    return
                } else {                
                    therapyRecord.therapyDate = new Date(params.irradTherb4SurgDt)
            }
            } else {
                params.irradTherb4SurgDt=""                
            }
            if (params.irradTherb4SurgEst!='') {
            therapyRecord.howLongAgo = Double.parseDouble(params.irradTherb4SurgEst)
        }
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//                println "In render"
//                render(view: "_IrradTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                //render(view: "_IrradTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }    
    

    def saveImmunoTher = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Immunotheraphy)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "immuno"
            therapyRecord.descTherapy = params.immTherb4SurgDesc
            if (params.immTherb4SurgDt!="Select Date") {
                if ((new Date(params.immTherb4SurgDt)) > new Date()) {
                    redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                    flash.message = "Immunotherapy date cannot be in the future"
                    return
                } else {                
                    therapyRecord.therapyDate = new Date(params.immTherb4SurgDt)
            }
            } else {
                params.immTherb4SurgDt=""                
            }
            if (params.immTherb4SurgEst!='') {
            therapyRecord.howLongAgo = Double.parseDouble(params.immTherb4SurgEst)
        }
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }        
    
    def saveHormonalTher = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Hormonal Theraphy)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "hormonal"
            therapyRecord.descTherapy = params.hormTherb4SurgDesc
            if (params.hormTherb4SurgDt!="Select Date") {
                if ((new Date(params.hormTherb4SurgDt)) > new Date()) {
                    redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                    flash.message = "Hormonal therapy date cannot be in the future"
                    return
                } else {
                    therapyRecord.therapyDate = new Date(params.hormTherb4SurgDt)
            }
            } else {
                params.hormTherb4SurgDt=""                
            }
            if (params.hormTherb4SurgEst!='') {
            therapyRecord.howLongAgo = Double.parseDouble(params.hormTherb4SurgEst)
        }
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }        
    
def saveHormonalBC = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Hormonal Birth Control)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "HBC"
            if ((params.formHBCOtherSpecification == null)||(params.formHBCOtherSpecification == '')||(params.formHBCOtherSpecification == 'null')) therapyRecord.specOtherHBCForm = ""
            else therapyRecord.specOtherHBCForm = params.formHBCOtherSpecification
            therapyRecord.hbcForm = params.formHorBirthControl
            if (params.hormBCDur!='') {
                therapyRecord.durationMonths = Double.parseDouble(params.hormBCDur)
            }
            if (params.hormBCLast!='') {
                therapyRecord.noOfYearsStopped = Double.parseDouble(params.hormBCLast)
            }
            
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    def saveHormonalRT = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if (!bpvClinicalDataEntryInstance) {
            bpvClinicalDataEntryInstance = new BpvClinicalDataEntry(params)
            
            def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        
            if (caseRecord)
            {
                def bpvClinicalDataEntryInstance2 = BpvClinicalDataEntry.findByCaseRecord(caseRecord)
                if (bpvClinicalDataEntryInstance2)
                {
                    bpvClinicalDataEntryInstance2.errors.rejectValue("version", "Another user has created this Clinical Data Entry form before your saving (Hormonal Replacement Theraphy)")

                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance2])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"            
        }
        
//        if(bpvClinicalDataEntryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
//            //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
//        }        
        
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bpvClinicalDataEntry.label', default: 'BpvClinicalDataEntry')] as Object[], "Another user has updated this Clinical Data Entry form while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
            
            bpvClinicalDataEntryInstance.properties = params
            TherapyRecord therapyRecord = new TherapyRecord()
            therapyRecord.typeOfTherapy = "HRT"
            
            if ((params.formHRTOtherSpecification == null)||(params.formHRTOtherSpecification == '')||(params.formHRTOtherSpecification == 'null')) therapyRecord.specOtherHRTForm = ""
            else therapyRecord.specOtherHRTForm = params.formHRTOtherSpecification
            therapyRecord.hrtForm = params.formHorReplaceTher
            therapyRecord.hrtType = params.typeHorReplaceTher
            if (params.hormRTDur!='') {
                therapyRecord.durationMonths = Double.parseDouble(params.hormRTDur)
            }
            if (params.hormRTLast!='') {
                therapyRecord.noOfYearsStopped = Double.parseDouble(params.hormRTLast)
            }
            
            therapyRecord.bpvClinicalDataEntry = bpvClinicalDataEntryInstance
            therapyRecord.save(flush: true)
            bpvClinicalDataEntryInstance.addToTherapyRecords(therapyRecord)
            
            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
                redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
            } else {
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bpvClinicalDataEntry.label', default: 'BPV Clinical Data Entry form for Case '), bpvClinicalDataEntryInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    
    def submit = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if(bpvClinicalDataEntryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvClinicalDataEntryInstance.version > version) {
                    bpvClinicalDataEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvClinicalDataEntry while you were editing")
                    render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
                    return
                }
            }
                
            bpvClinicalDataEntryInstance.properties = params
            validateFields(bpvClinicalDataEntryInstance)

            if(!bpvClinicalDataEntryInstance.hasErrors() && bpvClinicalDataEntryInstance.save(flush: true)) {
                bpvClinicalDataEntryInstance.dateSubmitted = new Date()
                bpvClinicalDataEntryInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                checkInfectiousDisease(bpvClinicalDataEntryInstance)
                
                flash.message = "${message(code: 'default.submitted.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvClinicalDataEntryInstance.caseRecord.id)
            } else {
                bpvClinicalDataEntryInstance.discard()
                render(view: "edit", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        if(bpvClinicalDataEntryInstance) {
            try {
                bpvClinicalDataEntryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
                redirect(action: "list")
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def resumeEditing = {
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        bpvClinicalDataEntryInstance.dateSubmitted = null
        bpvClinicalDataEntryInstance.submittedBy = null

        if(bpvClinicalDataEntryInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvClinicalDataEntryInstance.id)
        } else {
            render(view: "show", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        }
    }

    def deleteTherapy = {
        //println "params.id inside deleteTherapy "+params.id
        //println "params :: "+params
        //println "therapyId :: "+params.therapyId
        //println "therType :: "+params.therType
        
        def therapyRecordInstance = TherapyRecord.get(params.id)
        //def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        def bpvClinicalDataEntryInstance = therapyRecordInstance.bpvClinicalDataEntry
        //println "bpvClinicalDataEntryInstance ID "+bpvClinicalDataEntryInstance.id
        therapyRecordInstance.delete(flush: true)
        if (params.therType.equalsIgnoreCase("Irrad")) {
            render(view: "_IrradTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        } else if (params.therType.equalsIgnoreCase("Chemo")) {
            render(view: "_ChemoTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        } else if (params.therType.equalsIgnoreCase("Immuno")) {
            render(view: "_ImmunoTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        } else if (params.therType.equalsIgnoreCase("Hormonal")) {
            render(view: "_HormTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        } else if (params.therType.equalsIgnoreCase("PrevCancer")) {
            render(view: "_PrevCancerTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        } else if (params.therType.equalsIgnoreCase("HBC")) {
            render(view: "_HBCTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])
        } else if (params.therType.equalsIgnoreCase("HRT")) {
            render(view: "_HRTTabContent", model: [bpvClinicalDataEntryInstance: bpvClinicalDataEntryInstance])            
        } 
        //render(view: "_IrradTabContent")
        //println "Deleted therapy record - "+params.id
    }
    
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_NCI-FREDERICK_CAHUB_DM','ROLE_ADMIN']) 
    def forceSubmit = {
        def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        def bpvClinicalDataEntryInstance = BpvClinicalDataEntry.get(params.id)
        bpvClinicalDataEntryInstance.submittedBy = "forcedby:" + username
        bpvClinicalDataEntryInstance.dateSubmitted = new Date()

            if (bpvClinicalDataEntryInstance.save(flush: true)) {
                checkInfectiousDisease(bpvClinicalDataEntryInstance)
                flash.message = "${message(code: 'default.forcesubmissionsuccess.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"
                //redirect(action: "show", id: bpvClinicalDataEntryInstance.id)
                redirect(controller: "caseRecord", action: "display", id: bpvClinicalDataEntryInstance.caseRecord.id)
            }        
            else {
                bpvClinicalDataEntryInstance.discard()
                flash.message = "${message(code: 'default.forcesubmissionfailed.message', args: [bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName + ' for Case', bpvClinicalDataEntryInstance.caseRecord.caseId])}"                
                redirect(controller: "caseRecord", action: "display", id: bpvClinicalDataEntryInstance.caseRecord.id)
            }            
    }       
    
    
    def checkInfectiousDisease(bpvClinicalDataEntryInstance) {
        def result
        def organization = Organization.findByCode(bpvClinicalDataEntryInstance.caseRecord?.bss?.parentBss?.code)
        def caseRecord = bpvClinicalDataEntryInstance.caseRecord
        def queryType = QueryType.findByCode("VERIFY")
        def description
        def dueDate = (new Date()).plus(10)
        
        if (bpvClinicalDataEntryInstance.hepB == 'Yes') {
            result = Query.executeQuery("select q from Query q where q.caseRecord.id = ? and task = 'HEPB'", [bpvClinicalDataEntryInstance.caseRecord?.id])
            if (result.size() == 0) {
                description = "Infectious disease Hepatitis B data was entered and submitted for Case " + bpvClinicalDataEntryInstance.caseRecord?.caseId + ". Please re-visit the Clinical Data Entry Form and verify all data related to Infectious disease Hepatitis B."
                queryService.createInfectiousDiseaseEntry(organization, caseRecord, queryType, description, dueDate, "HEPB")
            }
        }
        
        if (bpvClinicalDataEntryInstance.hepC == 'Yes') {
            result = Query.executeQuery("select q from Query q where q.caseRecord.id = ? and task = 'HEPC'", [bpvClinicalDataEntryInstance.caseRecord?.id])
            if (result.size() == 0) {
                description = "Infectious disease Hepatitis C data was entered and submitted for Case " + bpvClinicalDataEntryInstance.caseRecord?.caseId + ". Please re-visit the Clinical Data Entry Form and verify all data related to Infectious disease Hepatitis C."
                queryService.createInfectiousDiseaseEntry(organization, caseRecord, queryType, description, dueDate, "HEPC")
            }
        }
        
        if (bpvClinicalDataEntryInstance.hiv == 'Yes') {
            result = Query.executeQuery("select q from Query q where q.caseRecord.id = ? and task = 'HIV'", [bpvClinicalDataEntryInstance.caseRecord?.id])
            if (result.size() == 0) {
                description = "Infectious disease HIV data was entered and submitted for Case " + bpvClinicalDataEntryInstance.caseRecord?.caseId + ". Please re-visit the Clinical Data Entry Form and verify all data related to Infectious disease HIV."
                queryService.createInfectiousDiseaseEntry(organization, caseRecord, queryType, description, dueDate, "HIV")
            }
        }
        
        if (bpvClinicalDataEntryInstance.scrAssay == 'Yes') {
            result = Query.executeQuery("select q from Query q where q.caseRecord.id = ? and task = 'HIVASSAY'", [bpvClinicalDataEntryInstance.caseRecord?.id])
            if (result.size() == 0) {
                description = "Infectious disease HIV assay data was entered and submitted for Case " + bpvClinicalDataEntryInstance.caseRecord?.caseId + ". Please re-visit the Clinical Data Entry Form and verify all data related to Infectious disease HIV assay."
                queryService.createInfectiousDiseaseEntry(organization, caseRecord, queryType, description, dueDate, "HIVASSAY")
            }
        }
        
        if (bpvClinicalDataEntryInstance.otherInfect != null && bpvClinicalDataEntryInstance.otherInfect?.trim() != "") {
            result = Query.executeQuery("select q from Query q where q.caseRecord.id = ? and task = 'OTHERINFECT'", [bpvClinicalDataEntryInstance.caseRecord?.id])
            if (result.size() == 0) {
                description = "Other infectious disease data was entered and submitted for Case " + bpvClinicalDataEntryInstance.caseRecord?.caseId + ". Please re-visit the Clinical Data Entry Form and verify all data related to Other Infectious Disease."
                queryService.createInfectiousDiseaseEntry(organization, caseRecord, queryType, description, dueDate, "OTHERINFECT")
            }
        }
    }
    
    def validateFields(bpvClinicalDataEntryInstance) {
        def recsToDelList = []
        def therapyRecordsSize = 0
        def therapyRecordsPrevCancer = "No"
        def therapyRecordsIrrad = "No"
        def therapyRecordsChemo = "No"
        def therapyRecordsImmuno = "No"
        def therapyRecordsHorm = "No"
        def therapyRecordsHBC = "No"
        def therapyRecordsHRT = "No"
        
        
        if ((bpvClinicalDataEntryInstance.therapyRecords!=null) || (bpvClinicalDataEntryInstance.therapyRecords.size() != 0)) {        
                bpvClinicalDataEntryInstance.therapyRecords.each() {
                    if(it.typeOfTherapy == "prevcancer") {
                        therapyRecordsPrevCancer = "Yes"
                    }
                    if(it.typeOfTherapy == "radiation") {
                        therapyRecordsIrrad = "Yes"
                    }
                    if(it.typeOfTherapy == "chemo") {
                        therapyRecordsChemo = "Yes"
                    }
                    if(it.typeOfTherapy == "immuno") {
                        therapyRecordsImmuno = "Yes"
                    }
                    if(it.typeOfTherapy == "hormonal") {
                        therapyRecordsHorm = "Yes"
                    }
                    if(it.typeOfTherapy == "HBC") {
                        therapyRecordsHBC = "Yes"
                    }
                    if(it.typeOfTherapy == "HRT") {
                        therapyRecordsHRT = "Yes"
                    }            
                }
        }
        
           
        //if (bpvClinicalDataEntryInstance.prevMalignancy != 'Yes' || bpvClinicalDataEntryInstance.prevMalignancy != 'No' || bpvClinicalDataEntryInstance.prevMalignancy != 'Unknown' ) {
        if (bpvClinicalDataEntryInstance.prevMalignancy == null ) {
            bpvClinicalDataEntryInstance.errors.rejectValue('prevMalignancy', 'Please select an option for Participant\'s prior malignancy')
        } else {        
            if (bpvClinicalDataEntryInstance.prevMalignancy == 'Yes' && therapyRecordsPrevCancer.equals("No")) {
                bpvClinicalDataEntryInstance.errors.rejectValue('prevMalignancy', 'Please enter Participant\'s previous malignancy since you have chosen Yes for the question')
            }
                if ((bpvClinicalDataEntryInstance.prevMalignancy == 'No' || bpvClinicalDataEntryInstance.prevMalignancy == 'Unknown')  && therapyRecordsPrevCancer.equals("Yes")) {
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "prevcancer") {
                            recsToDelList.add(it.id)
                        }
                    }
                }
//            if (bpvClinicalDataEntryInstance.prevMalignancy == 'Yes' && bpvClinicalDataEntryInstance.prevCancerDiagDt == null && bpvClinicalDataEntryInstance.prevCancerDiagEst == null ) {
//                bpvClinicalDataEntryInstance.errors.rejectValue('prevCancerDiagDt', 'Please enter a date when cancer was diagnosed before or how long ago it was diagnosed')
//            }            
        }

        int n = 0
        boolean checkRel = false
        while(true)
        {
            n++
            def bloodRelCancer
            def relCancerType
            try
            {
                bloodRelCancer = bpvClinicalDataEntryInstance."${'bloodRelCancer' + n}"
            }
            catch(Exception ee)
            {
                break
            }
            
            try
            {
                relCancerType = bpvClinicalDataEntryInstance."${'relCancerType' + n}"
            }
            catch(Exception ee)
            {
                //relCancerType = 'X'
            }
            //println 'CCCC success cancer relation # =' + n + ', bloodRelCancer=' + bloodRelCancer + ', relCancerType=' + relCancerType
            if ((bloodRelCancer != null)&&(!bloodRelCancer.trim().equals('')))
            {
                checkRel = true
                if (bloodRelCancer.toLowerCase().startsWith('other')) {}
                else if (bloodRelCancer.toLowerCase().startsWith('none')) {}
                else if ((relCancerType == null)||(relCancerType.trim().equals('')))
                {
                    bpvClinicalDataEntryInstance.errors.rejectValue('relCancerType'+n, 'Please enter the type of cancer for the specified relative - '+bloodRelCancer)
                }
            }
        }
        
        Double versionValue = 0.0
        if (bpvClinicalDataEntryInstance.caseRecord?.cdrVer)
        {
            try { versionValue = Double.parseDouble(bpvClinicalDataEntryInstance.caseRecord.cdrVer.substring(0, (bpvClinicalDataEntryInstance.caseRecord.cdrVer.indexOf('.') + 2 ))) }
            catch(Exception ee) { versionValue = 0.0 }
        }
        
        if ((versionValue >= 6.0)&&(!checkRel))
        {
            bpvClinicalDataEntryInstance.errors.rejectValue('bloodRelCancer1', 'Please check \'None\' before proceeding if participant\'s relatives have no cancer history.')
        }
        
        if (bpvClinicalDataEntryInstance.bloodRelCancer13 != null) { 
            if (bpvClinicalDataEntryInstance.othBloodRelCancer == null) { 
                bpvClinicalDataEntryInstance.errors.rejectValue('othBloodRelCancer', 'Please enter the type of relative in the other box')
            } else {
                if (bpvClinicalDataEntryInstance.relCancerType13 == null) {
                  bpvClinicalDataEntryInstance.errors.rejectValue('relCancerType13', 'Please enter the type of cancer for the specified relative - '+bpvClinicalDataEntryInstance.othBloodRelCancer)
                }
            }
        }       
        
        if (bpvClinicalDataEntryInstance.isImmunoSupp == null){
            bpvClinicalDataEntryInstance.errors.rejectValue('isImmunoSupp', 'Please choose between Yes/No/Unknown options to answer Participant\'s immuno suppressive status')
        } else {
            if (bpvClinicalDataEntryInstance.isImmunoSupp == 'Yes' && bpvClinicalDataEntryInstance.immunoSuppStatus1 == null && bpvClinicalDataEntryInstance.immunoSuppStatus2 == null && bpvClinicalDataEntryInstance.immunoSuppStatus3 == null && bpvClinicalDataEntryInstance.immunoSuppStatus4 == null) {
                bpvClinicalDataEntryInstance.errors.rejectValue('immunoSuppStatus1', 'Participant\'s Immunosuppressive status is required since you answered \'Yes\'')
            } else {
                if (bpvClinicalDataEntryInstance.immunoSuppStatus4 != null && bpvClinicalDataEntryInstance.otherImmunoSuppStatus == null) { 
                    bpvClinicalDataEntryInstance.errors.rejectValue('otherImmunoSuppStatus', 'Please enter the Other immunosuppressive status since you have selected it')
                } 
            }
        }
        

        if (bpvClinicalDataEntryInstance.irradTherb4Surg == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('irradTherb4Surg', 'Please choose between Yes/No/Unknown options to answer Participant\'s radiation therapy history')
        } else {
            if (bpvClinicalDataEntryInstance.irradTherb4Surg == 'Yes' && therapyRecordsIrrad.equals("No")) {
                bpvClinicalDataEntryInstance.errors.rejectValue('irradTherb4Surg', 'Please enter any previous history of Radiation therapies that Participant has undergone, since you have chosen Yes for the question')
            }
                if ((bpvClinicalDataEntryInstance.irradTherb4Surg == 'No' || bpvClinicalDataEntryInstance.irradTherb4Surg == 'Unknown')  && therapyRecordsIrrad.equals("Yes")) {
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "radiation") {
                            recsToDelList.add(it.id)
                        }
                    }
                }
            
            
//                if (bpvClinicalDataEntryInstance.irradTherb4SurgDesc == null) { 
//                    bpvClinicalDataEntryInstance.errors.rejectValue('irradTherb4SurgDesc', 'Description of Radiation therapy before surgery cannot be blank')
//                }
//                if (bpvClinicalDataEntryInstance.irradTherb4SurgDt == null) { 
//                    bpvClinicalDataEntryInstance.errors.rejectValue('irradTherb4SurgDt', 'Date when Radiation therapy before surgery was done cannot be blank')
//                }            
//            }
        }
        
        if (bpvClinicalDataEntryInstance.chemoTherb4Surg == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('chemoTherb4Surg', 'Please choose between Yes/No/Unknown options to answer Participant\'s chemotherapy history')
        } else {        
            if (bpvClinicalDataEntryInstance.chemoTherb4Surg == 'Yes' && therapyRecordsChemo.equals("No")) {
                bpvClinicalDataEntryInstance.errors.rejectValue('chemoTherb4Surg', 'Please enter any previous history of Chemotherapies that Participant has undergone, since you have chosen Yes for the question')
            }        
                if ((bpvClinicalDataEntryInstance.chemoTherb4Surg == 'No' || bpvClinicalDataEntryInstance.chemoTherb4Surg == 'Unknown')  && therapyRecordsChemo.equals("Yes")) {
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "chemo") {
                            recsToDelList.add(it.id)
                        }
                    }
                }            
//            if (bpvClinicalDataEntryInstance.chemoTherb4Surg == 'Yes') { 
//                    if (bpvClinicalDataEntryInstance.chemoTherb4SurgDesc == null) { 
//                        bpvClinicalDataEntryInstance.errors.rejectValue('chemoTherb4SurgDesc', 'Description of Chemotherapy before surgery cannot be blank')
//                    }
//                    if (bpvClinicalDataEntryInstance.chemoTherb4SurgDt == null) { 
//                        bpvClinicalDataEntryInstance.errors.rejectValue('chemoTherb4SurgDt', 'Date when Chemotherapy before surgery was done cannot be blank')
//                    }            
//                }        
        }

        if (bpvClinicalDataEntryInstance.immTherb4Surg == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('immTherb4Surg', 'Please choose between Yes/No/Unknown options to answer Participant\'s immunotherapy history')
        } else {        
            if (bpvClinicalDataEntryInstance.immTherb4Surg == 'Yes' && therapyRecordsImmuno.equals("No")) {
                bpvClinicalDataEntryInstance.errors.rejectValue('immTherb4Surg', 'Please enter any previous history of Immunotherapies that Participant has undergone, since you have chosen Yes for the question')
            }            
                if ((bpvClinicalDataEntryInstance.immTherb4Surg == 'No' || bpvClinicalDataEntryInstance.immTherb4Surg == 'Unknown')  && therapyRecordsImmuno.equals("Yes")) {
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "immuno") {
                            recsToDelList.add(it.id)
                        }
                    }
                }            
//            if (bpvClinicalDataEntryInstance.immTherb4Surg == 'Yes') { 
//                if (bpvClinicalDataEntryInstance.immTherb4SurgDesc == null) { 
//                    bpvClinicalDataEntryInstance.errors.rejectValue('immTherb4SurgDesc', 'Description of Immunotherapy before surgery cannot be blank')
//                }
//                if (bpvClinicalDataEntryInstance.immTherb4SurgDt == null) { 
//                    bpvClinicalDataEntryInstance.errors.rejectValue('immTherb4SurgDt', 'Date when Immunotherapy before surgery was done cannot be blank')
//                }            
//            }       
        }

        if (bpvClinicalDataEntryInstance.hormTherb4Surg == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('hormTherb4Surg', 'Please choose between Yes/No/Unknown options to answer Participant\'s hormonal therapy history')
        } else {        
            if (bpvClinicalDataEntryInstance.hormTherb4Surg == 'Yes' && therapyRecordsHorm.equals("No")) {
                bpvClinicalDataEntryInstance.errors.rejectValue('hormTherb4Surg', 'Please enter any previous history of Hormonal therapies that Participant has undergone, since you have chosen Yes for the question')
            }            
                if ((bpvClinicalDataEntryInstance.hormTherb4Surg == 'No' || bpvClinicalDataEntryInstance.hormTherb4Surg == 'Unknown')  && therapyRecordsHorm.equals("Yes")) {
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "hormonal") {
                            recsToDelList.add(it.id)
                        }
                    }
                }            
            
//            if (bpvClinicalDataEntryInstance.hormTherb4Surg == 'Yes') { 
//                if (bpvClinicalDataEntryInstance.hormTherb4SurgDesc == null) { 
//                    bpvClinicalDataEntryInstance.errors.rejectValue('hormTherb4SurgDesc', 'Description of hormonal therapy before surgery cannot be blank')
//                }
//                if (bpvClinicalDataEntryInstance.hormTherb4SurgDt == null) { 
//                    bpvClinicalDataEntryInstance.errors.rejectValue('hormTherb4SurgDt', 'Date when hormonal therapy before surgery was done cannot be blank')
//                }            
//            }
        }        
           
        if (bpvClinicalDataEntryInstance.hepB == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('hepB', 'Please choose between Yes/No/Unknown options to answer the question about presence of Hepatitis B')
        }         
        
        if (bpvClinicalDataEntryInstance.hepC == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('hepC', 'Please choose between Yes/No/Unknown options to answer the question about presence of Hepatitis C')
        }         

        if (bpvClinicalDataEntryInstance.hiv == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('hiv', 'Please choose between Yes/No/Unknown options to answer the question about presence of HIV')
        }         

        if (bpvClinicalDataEntryInstance.scrAssay == null) {
            bpvClinicalDataEntryInstance.errors.rejectValue('scrAssay', 'Please choose between Yes/No/Unknown options to answer the question about screening assays')
        }                 
        

        if (bpvClinicalDataEntryInstance.tobaccoSmHist.equals(null)) {
            bpvClinicalDataEntryInstance.errors.rejectValue('tobaccoSmHist', 'Please choose one of the options given for Participant\'s Tobacco smoking history')
//        } else if (bpvClinicalDataEntryInstance.tobaccoSmHist.equals("Includes daily and non-daily smokers") || bpvClinicalDataEntryInstance.tobaccoSmHist.equals("Current reformed smoker for more than 15 years") || bpvClinicalDataEntryInstance.tobaccoSmHist.equals("Current reformed smoker for less than 15 years")) {
//            if (bpvClinicalDataEntryInstance.smokeAgeStart == null || bpvClinicalDataEntryInstance.smokeAgeStart.equals("")) {
//                bpvClinicalDataEntryInstance.errors.rejectValue('smokeAgeStart', 'The age at which the Participant started smoking is required')
//            }
//            if (!bpvClinicalDataEntryInstance.tobaccoSmHist.equals("Includes daily and non-daily smokers")) {
//                if (bpvClinicalDataEntryInstance.smokeAgeStop == null || bpvClinicalDataEntryInstance.smokeAgeStop.equals("")) {
//                    bpvClinicalDataEntryInstance.errors.rejectValue('smokeAgeStop', 'The age at which the Participant stopped smoking is required')
//                }                    
//            }
//            if (bpvClinicalDataEntryInstance.cigsPerDay == null || bpvClinicalDataEntryInstance.cigsPerDay.equals("")) {
//                bpvClinicalDataEntryInstance.errors.rejectValue('cigsPerDay', 'Number of cigarettes per day the Participant smoked on the days s/he smoked is required')
//            }
//            if (bpvClinicalDataEntryInstance.numPackYearsSm == null || bpvClinicalDataEntryInstance.numPackYearsSm.equals("")) {
//                bpvClinicalDataEntryInstance.errors.rejectValue('numPackYearsSm', 'Number of pack years smoked is required')
//            }            
        }       
        
        if (bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString()=='Ovary') {
            
            if (bpvClinicalDataEntryInstance.wasPregnant == null) {
                bpvClinicalDataEntryInstance.errors.rejectValue('wasPregnant', 'Please choose between Yes/No/Unknown options to answer Participant\'s pregnancy')
            } else {
                if (bpvClinicalDataEntryInstance.wasPregnant == 'Yes') { 
                    if (bpvClinicalDataEntryInstance.totPregnancies == null) { 
                        bpvClinicalDataEntryInstance.errors.rejectValue('totPregnancies', 'Total number of pregancies cannot be blank')
                    }
                    if (bpvClinicalDataEntryInstance.totLiveBirths == null) { 
                        bpvClinicalDataEntryInstance.errors.rejectValue('totLiveBirths', 'Total number of live births cannot be blank')
                    }            
                    if (bpvClinicalDataEntryInstance.ageAt1stChild == null) { 
                        bpvClinicalDataEntryInstance.errors.rejectValue('ageAt1stChild', 'Participant\'s Age when the first child was born cannot be blank')
                    }            
                }
            }
            
            if (bpvClinicalDataEntryInstance.gynSurg == null) {
                bpvClinicalDataEntryInstance.errors.rejectValue('gynSurg', 'Participant\'s gynecological surgery history is a required field')
            }

            if (bpvClinicalDataEntryInstance.hormBirthControl.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('hormBirthControl', 'Please choose one of the options given for Participant\'s hormonally based birth control usage')
            } else {
                if ((bpvClinicalDataEntryInstance.hormBirthControl == 'Current user' || bpvClinicalDataEntryInstance.hormBirthControl == 'Former user') && therapyRecordsHBC.equals("No")) {
                    bpvClinicalDataEntryInstance.errors.rejectValue('hormBirthControl', 'Please enter any previous history of Hormonal birth control that Participant has taken, if the Participant was a Current/Former user')
                }
                
                if ((bpvClinicalDataEntryInstance.hormBirthControl == 'Never used' || bpvClinicalDataEntryInstance.hormBirthControl == 'Unknown')  && therapyRecordsHBC.equals("Yes")) {
                    bpvClinicalDataEntryInstance.othHorBC = ""
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "HBC") {
                            recsToDelList.add(it.id)
                        }
                    }
                }                
            }

            if (bpvClinicalDataEntryInstance.usedHorReplaceTher.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('usedHorReplaceTher', 'Please choose one of the options given for Participant\'s hormone replacement therapy history')
            } else {
                if (bpvClinicalDataEntryInstance.usedHorReplaceTher == 'Yes' && therapyRecordsHRT.equals("No")) {
                    bpvClinicalDataEntryInstance.errors.rejectValue('usedHorReplaceTher', 'Please enter any previous history of Hormone Replacement therapies that Participant has undergone, since you have chosen Yes for the question')
                }
                if ((bpvClinicalDataEntryInstance.usedHorReplaceTher == 'No' || bpvClinicalDataEntryInstance.usedHorReplaceTher == 'Unknown')  && therapyRecordsHRT.equals("Yes")) {
                    bpvClinicalDataEntryInstance.othHorRT = ""
                   bpvClinicalDataEntryInstance.therapyRecords.each() {
                        if(it.typeOfTherapy == "HRT") {
                            recsToDelList.add(it.id)
                        }
                    }
                }                
            }

            if (bpvClinicalDataEntryInstance.menoStatus.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('menoStatus', 'Please choose one of the options given for Participant\'s menopausal status')
            }
            /*
            else
            {
               if (bpvClinicalDataEntryInstance.gynSurg != null)
               {
                   boolean tag = false
                   while(true)
                   {
                       String gynSrg = bpvClinicalDataEntryInstance.gynSurg
                       if (gynSrg.toLowerCase().indexOf('neither hysterectomy') < 0) break
                       if (gynSrg.toLowerCase().indexOf('nor oophorectomy') < 0) break
                       String menoSt = bpvClinicalDataEntryInstance.menoStatus
                       if (menoSt.toLowerCase().indexOf('no prior hysterectomy') < 0) break
                       if (menoSt.toLowerCase().indexOf('prior bilateral oophorectomy') < 0) break
                       tag = true
                       break
                   }
                   if (tag)
                   {
                       bpvClinicalDataEntryInstance.errors.rejectValue('menoStatus', 'This menopausal status value(#12) contradicts the questionair of gynecological surgeries(#9)')
                   }
               }
            }
            println 'CCCC gynSurg=' + bpvClinicalDataEntryInstance.gynSurg + ', menoStatus=' + bpvClinicalDataEntryInstance.menoStatus
            */
            
            if (bpvClinicalDataEntryInstance.clinicalFIGOStg.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('clinicalFIGOStg', 'Clinical FIGO Stage is a required field')
            }        
        } else {
            
            if (bpvClinicalDataEntryInstance.clinicalTumStgGrp.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('clinicalTumStgGrp', 'Clinical tumor stage group is a required field')
            }                                
        }
        
           if (bpvClinicalDataEntryInstance.secHandSmHist1 == null && bpvClinicalDataEntryInstance.secHandSmHist2 == null && bpvClinicalDataEntryInstance.secHandSmHist3 == null && bpvClinicalDataEntryInstance.secHandSmHist4 == null) {
                bpvClinicalDataEntryInstance.errors.rejectValue('secHandSmHist1', 'Participant\'s Exposure to second hand smoke is a required field')
            }

            if (bpvClinicalDataEntryInstance.alcoholConsum.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('alcoholConsum', 'Please choose one of the options given for Participant\'s alcohol consumption')
            } else {
                if (bpvClinicalDataEntryInstance.alcoholConsum.equals("Alcohol consumption more than 2 drinks per day for men and more than 1 drink per day for women")){
                    if (bpvClinicalDataEntryInstance.numYrsAlcCon.equals(null) || bpvClinicalDataEntryInstance.numYrsAlcCon.trim().equals("") || bpvClinicalDataEntryInstance.numYrsAlcCon.trim().toFloat()==0.0) {
                        bpvClinicalDataEntryInstance.errors.rejectValue('numYrsAlcCon', 'Provide the Number of years Participant has consumed more than 2 drinks per day for men and more than 1 drink per day for women')
                    }                    
                }

            }
        
        if (bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString()=='Lung') {
            if (bpvClinicalDataEntryInstance.isEnvCarc == null){
                bpvClinicalDataEntryInstance.errors.rejectValue('isEnvCarc', 'Please choose between Yes/No/Unknown options to answer Participant\'s environmental carcinogens exposure')
            } else {
                if (bpvClinicalDataEntryInstance.isEnvCarc == 'Yes' && bpvClinicalDataEntryInstance.envCarc1 == null && bpvClinicalDataEntryInstance.envCarc2 == null && bpvClinicalDataEntryInstance.envCarc3 == null && bpvClinicalDataEntryInstance.envCarc4 == null && bpvClinicalDataEntryInstance.envCarc5 == null) {
                        bpvClinicalDataEntryInstance.errors.rejectValue('envCarc1', 'Participant\'s Exposure to environmental/workplace carcinogens is required since you answered \'Yes\'')
                    }
            }
        }
        
        if (bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString()=='Colon') {
            
            if (bpvClinicalDataEntryInstance.isAddtlColoRisk == null) {
                bpvClinicalDataEntryInstance.errors.rejectValue('isAddtlColoRisk', 'Please choose between Yes/No/Unknown options for Participant\'s additional colorectal cancer risk factors')
            } else {
            if (bpvClinicalDataEntryInstance.isAddtlColoRisk == 'Yes' && bpvClinicalDataEntryInstance.addtlColoRisk1 == null && bpvClinicalDataEntryInstance.addtlColoRisk2 == null && bpvClinicalDataEntryInstance.addtlColoRisk3 == null && bpvClinicalDataEntryInstance.addtlColoRisk4 == null && bpvClinicalDataEntryInstance.addtlColoRisk5 == null && bpvClinicalDataEntryInstance.addtlColoRisk6 == null) {
                    bpvClinicalDataEntryInstance.errors.rejectValue('addtlColoRisk1', 'Additional colorectal cancer risk factors is required since you answered \'Yes\'')
                } else if (bpvClinicalDataEntryInstance.addtlColoRisk6 != null && bpvClinicalDataEntryInstance.otherAddColRisk == null) {
                    bpvClinicalDataEntryInstance.errors.rejectValue('otherAddColRisk', 'Please specify the other colorectal cancer risk factor')
                }
            }
        }
        
        
        
        //if (bpvClinicalDataEntryInstance.perfStatusScale.equals(null)) {
        if ((bpvClinicalDataEntryInstance.perfStatusScale.equals(null))||(bpvClinicalDataEntryInstance.perfStatusScale.trim().equals(''))) {
            bpvClinicalDataEntryInstance.errors.rejectValue('perfStatusScale', 'Performance status scale is a required field')
        } else {
            if (bpvClinicalDataEntryInstance.perfStatusScale.equals("Karnofsky Score") && bpvClinicalDataEntryInstance.karnofskyScore.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('karnofskyScore', 'Please choose one of the options given for the Karnofsky Score')
            }
            if (bpvClinicalDataEntryInstance.perfStatusScale.equals("Eastern Cancer Oncology Group") && bpvClinicalDataEntryInstance.ecogStatus.equals(null)) {
                bpvClinicalDataEntryInstance.errors.rejectValue('ecogStatus', 'Please choose one of the options given for the ECOG Functional Performance Status ')
            }            
        }               
        
        if ((versionValue >= 6.0) && ((!bpvClinicalDataEntryInstance.perfStatusScale)||(bpvClinicalDataEntryInstance.perfStatusScale == 'Not Recorded')||(bpvClinicalDataEntryInstance.perfStatusScale.trim().equals('')))) {}
        else
        {
            if ((bpvClinicalDataEntryInstance.timingOfScore.equals(null))||(bpvClinicalDataEntryInstance.timingOfScore.trim().equals(''))) {
                bpvClinicalDataEntryInstance.errors.rejectValue('timingOfScore', 'Timing of score is a required field')
            } else if (bpvClinicalDataEntryInstance.timingOfScore.equals('Other, Specify') && ((bpvClinicalDataEntryInstance.timingOfScoreOs == null)||bpvClinicalDataEntryInstance.timingOfScoreOs.trim().equals(''))) {
                bpvClinicalDataEntryInstance.errors.rejectValue('timingOfScoreOs', 'Please specify the answer in the Other box for the Timing of score question')
            }
        }

        recsToDelList.each() {
            def therapyRecordInstance = TherapyRecord.get(it)
            bpvClinicalDataEntryInstance.removeFromTherapyRecords(therapyRecordInstance)
            therapyRecordInstance.delete(flush: true)
        }
    }

}
