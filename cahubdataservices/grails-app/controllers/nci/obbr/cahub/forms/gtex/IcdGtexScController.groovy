package nci.obbr.cahub.forms.gtex

import grails.plugins.springsecurity.Secured 

class IcdGtexScController extends BaseGTEXCRFController{

    def accessPrivilegeService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = IcdGtexSc  
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [IcdGtexScInstanceList: IcdGtexSc.list(params), IcdGtexScInstanceTotal: IcdGtexSc.count()]
    }

    def create = {
        def IcdGtexScInstance = new IcdGtexSc()
        IcdGtexScInstance.properties = params
        
        def bssSubList = bssSubList(IcdGtexScInstance.candidateRecord.bss)
        
        return [IcdGtexScInstance: IcdGtexScInstance, bssSubList: bssSubList]
    }

    def save = {
        //println "inside save"
        def IcdGtexScInstance = new IcdGtexSc(params)
        if (IcdGtexScInstance.save(flush: true)) {

            setConsentOnCandidateRecord(IcdGtexScInstance)
            
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
            redirect(action: "edit", id: IcdGtexScInstance.id)
        }
        else {
            def bssSubList = bssSubList(IcdGtexScInstance.candidateRecord.bss)
            render(view: "create", model: [IcdGtexScInstance: IcdGtexScInstance, bssSubList: bssSubList])
        }
    }

    def show = {
        
         redirect(controller: "login", action: "denied")
        
        def IcdGtexScInstance = IcdGtexSc.get(params.id)
        if (!IcdGtexScInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            [IcdGtexScInstance: IcdGtexScInstance]
        }
    }

    
    def view = {
        def IcdGtexScInstance = IcdGtexSc.get(params.id)
        if (!IcdGtexScInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            
            def candidateRecord = IcdGtexScInstance.candidateRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
//               if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//           }
            
            def bssSubList = bssSubList(IcdGtexScInstance.candidateRecord.bss)
            
            return [IcdGtexScInstance: IcdGtexScInstance, bssSubList: bssSubList]
        }
    }  
    
    def edit = {
        def IcdGtexScInstance = IcdGtexSc.get(params.id)
        
       /** if( IcdGtexScInstance.candidateRecord.caseRecord &&
            IcdGtexScInstance.candidateRecord.caseRecord?.caseStatus.code != 'DATA' &&
           IcdGtexScInstance.candidateRecord.caseRecord?.caseStatus.code != 'DATACOMP' &&
           IcdGtexScInstance.candidateRecord.caseRecord?.caseStatus.code != 'REMED'){
           
            redirect(controller: "login", action: "denied")
           
        }   **/      
        
        if (!IcdGtexScInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            
             def candidateRecord = IcdGtexScInstance.candidateRecord
             int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')
             if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                 return
             }
//               if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//           }
        
            
            def bssSubList = bssSubList(IcdGtexScInstance.candidateRecord.bss)
            
            return [IcdGtexScInstance: IcdGtexScInstance, bssSubList: bssSubList]
        }
    }

    def update = {
        //println "inside update"
        def IcdGtexScInstance = IcdGtexSc.get(params.id)
        
        IcdGtexScInstance.clearErrors()
        
        if (IcdGtexScInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (IcdGtexScInstance.version > version) {
                    
                    IcdGtexScInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form')] as Object[], "Another user has updated this IcdGtexSc while you were editing")
                    render(view: "edit", model: [IcdGtexScInstance: IcdGtexScInstance])
                    return
                }
            }
            IcdGtexScInstance.properties = params
            if (!IcdGtexScInstance.hasErrors() && IcdGtexScInstance.save(flush: true)) {
                
                setConsentOnCandidateRecord(IcdGtexScInstance)
                
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
                redirect(action: "edit", id: IcdGtexScInstance.id)
            }
            else {
                render(view: "edit", model: [IcdGtexScInstance: IcdGtexScInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def IcdGtexScInstance = IcdGtexSc.get(params.id)
        if (IcdGtexScInstance) {
            try {
                IcdGtexScInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexSc.label', default: 'Science Care Consent Information Form For Candidate'), IcdGtexScInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
      
}
