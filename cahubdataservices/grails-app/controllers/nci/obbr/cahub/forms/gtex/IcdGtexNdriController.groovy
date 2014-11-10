package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.staticmembers.BSS

import grails.plugins.springsecurity.Secured 

class IcdGtexNdriController extends BaseGTEXCRFController{

    def accessPrivilegeService
     
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = IcdGtexNdri 
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [IcdGtexNdriInstanceList: IcdGtexNdri.list(params), IcdGtexNdriInstanceTotal: IcdGtexNdri.count()]
    }

    def create = {
        def IcdGtexNdriInstance = new IcdGtexNdri()
        IcdGtexNdriInstance.properties = params
        
        def bssSubList = bssSubList(IcdGtexNdriInstance.candidateRecord.bss)
        
        return [IcdGtexNdriInstance: IcdGtexNdriInstance, bssSubList: bssSubList]
    }

    def save = {
        def IcdGtexNdriInstance = new IcdGtexNdri(params)
        if (IcdGtexNdriInstance.save(flush: true)) {
            
            setConsentOnCandidateRecord(IcdGtexNdriInstance)            
            
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
            redirect(action: "edit", id: IcdGtexNdriInstance.id)
        }
        else {
            def bssSubList = bssSubList(IcdGtexNdriInstance.candidateRecord.bss)
            render(view: "create", model: [IcdGtexNdriInstance: IcdGtexNdriInstance, bssSubList: bssSubList])
        }
    }

    def show = {
        redirect(controller: "login", action: "denied")
        def IcdGtexNdriInstance = IcdGtexNdri.get(params.id)
        if (!IcdGtexNdriInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            [IcdGtexNdriInstance: IcdGtexNdriInstance]
        }
    }


    def view = {
        def IcdGtexNdriInstance = IcdGtexNdri.get(params.id)
        if (!IcdGtexNdriInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            def candidateRecord = IcdGtexNdriInstance.candidateRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            
//               if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//           }
            
            
            def bssSubList = bssSubList(IcdGtexNdriInstance.candidateRecord.bss)            
            
            return [IcdGtexNdriInstance: IcdGtexNdriInstance, bssSubList: bssSubList]
        }
    }    
    
    def edit = {
        def IcdGtexNdriInstance = IcdGtexNdri.get(params.id)
        
      /**  if(IcdGtexNdriInstance.candidateRecord.caseRecord &&
            IcdGtexNdriInstance.candidateRecord.caseRecord?.caseStatus.code != 'DATA' &&
           IcdGtexNdriInstance.candidateRecord.caseRecord?.caseStatus.code != 'DATACOMP' &&
           IcdGtexNdriInstance.candidateRecord.caseRecord?.caseStatus.code != 'REMED'){
           
            redirect(controller: "login", action: "denied")
           
        }    **/ 
        
        
          def candidateRecord = IcdGtexNdriInstance.candidateRecord
          int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
//               if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//           }
        
        if (!IcdGtexNdriInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            
            def bssSubList = bssSubList(IcdGtexNdriInstance.candidateRecord.bss)            
            
            return [IcdGtexNdriInstance: IcdGtexNdriInstance, bssSubList: bssSubList]
        }
    }

    def update = {
        def IcdGtexNdriInstance = IcdGtexNdri.get(params.id)
        
      
        if (IcdGtexNdriInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (IcdGtexNdriInstance.version > version) {
                    
                    IcdGtexNdriInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form')] as Object[], "Another user has updated this IcdGtexNdri while you were editing")
                    render(view: "edit", model: [IcdGtexNdriInstance: IcdGtexNdriInstance])
                    return
                }
            }
            IcdGtexNdriInstance.properties = params
            if (!IcdGtexNdriInstance.hasErrors() && IcdGtexNdriInstance.save(flush: true)) {
                
                setConsentOnCandidateRecord(IcdGtexNdriInstance)                    
                
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate '), IcdGtexNdriInstance.candidateRecord.candidateId])}"
                redirect(action: "edit", id: IcdGtexNdriInstance.id)
            }
            else {
                render(view: "edit", model: [IcdGtexNdriInstance: IcdGtexNdriInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def IcdGtexNdriInstance = IcdGtexNdri.get(params.id)
        if (IcdGtexNdriInstance) {
            try {
                IcdGtexNdriInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexNdri.label', default: 'NDRI Consent Information Form For Candidate'), IcdGtexNdriInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
}
