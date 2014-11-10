package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.staticmembers.BSS

import grails.plugins.springsecurity.Secured 

class IcdGtexRpciController extends BaseGTEXCRFController{

    def accessPrivilegeService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = IcdGtexRpci
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [IcdGtexRpciInstanceList: IcdGtexRpci.list(params), IcdGtexRpciInstanceTotal: IcdGtexRpci.count()]
    }

    def create = {
        def IcdGtexRpciInstance = new IcdGtexRpci()
        IcdGtexRpciInstance.properties = params

        def bssSubList = bssSubList(IcdGtexRpciInstance.candidateRecord.bss)
        
        return [IcdGtexRpciInstance: IcdGtexRpciInstance, bssSubList: bssSubList]
    }

    def save = {
        def IcdGtexRpciInstance = new IcdGtexRpci(params)
        
        // Setting dummy values to these two variables because these two were non nullable fields in the
        // previous release.
        IcdGtexRpciInstance.otherOrganTissue = "No"
        IcdGtexRpciInstance.specifyOtherOrganTissue = "None"
        
        if (IcdGtexRpciInstance.save(flush: true)) {
            
            setConsentOnCandidateRecord(IcdGtexRpciInstance)
            
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form'), IcdGtexRpciInstance.id])}"
            redirect(action: "edit", id: IcdGtexRpciInstance.id)
        }
        else {
            
            def bssSubList = bssSubList(IcdGtexRpciInstance.candidateRecord.bss)
            
            render(view: "create", model: [IcdGtexRpciInstance: IcdGtexRpciInstance, bssSubList: bssSubList])
        }
    }

    def show = {
          redirect(controller: "login", action: "denied")
        def IcdGtexRpciInstance = IcdGtexRpci.get(params.id)
        if (!IcdGtexRpciInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            [IcdGtexRpciInstance: IcdGtexRpciInstance]
        }
    }

    def view = {
        def IcdGtexRpciInstance = IcdGtexRpci.get(params.id)
        if (!IcdGtexRpciInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            
             def candidateRecord = IcdGtexRpciInstance.candidateRecord
             int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')
             if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                 return
             }
//               if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//           }
            
            def bssSubList = bssSubList(IcdGtexRpciInstance.candidateRecord.bss)
            return [IcdGtexRpciInstance: IcdGtexRpciInstance, bssSubList: bssSubList]
        }
    }   
    
    def edit = {
        def IcdGtexRpciInstance = IcdGtexRpci.get(params.id)
        
       /** if(IcdGtexRpciInstance.candidateRecord.caseRecord &&
            IcdGtexRpciInstance.candidateRecord.caseRecord?.caseStatus.code != 'DATA' &&
           IcdGtexRpciInstance.candidateRecord.caseRecord?.caseStatus.code != 'DATACOMP' &&
           IcdGtexRpciInstance.candidateRecord.caseRecord?.caseStatus.code != 'REMED'){
           
            redirect(controller: "login", action: "denied")
           
        }    **/     
        
          def candidateRecord = IcdGtexRpciInstance.candidateRecord
          int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')
          if (accessPrivilege > 0) {
              redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
              return
          }
//               if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//           }
        
        
        if (!IcdGtexRpciInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            def bssSubList = bssSubList(IcdGtexRpciInstance.candidateRecord.bss)
            return [IcdGtexRpciInstance: IcdGtexRpciInstance, bssSubList: bssSubList]
        }
    }

    def update = {
        def IcdGtexRpciInstance = IcdGtexRpci.get(params.id)
        if (IcdGtexRpciInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (IcdGtexRpciInstance.version > version) {
                    
                    IcdGtexRpciInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form')] as Object[], "Another user has updated this IcdGtexRpci while you were editing")
                    render(view: "edit", model: [IcdGtexRpciInstance: IcdGtexRpciInstance])
                    return
                }
            }
            IcdGtexRpciInstance.properties = params
            if (!IcdGtexRpciInstance.hasErrors() && IcdGtexRpciInstance.save(flush: true)) {
                
                setConsentOnCandidateRecord(IcdGtexRpciInstance)                
                
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
                redirect(action: "edit", id: IcdGtexRpciInstance.id)
            }
            else {
                render(view: "edit", model: [IcdGtexRpciInstance: IcdGtexRpciInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }

    	//We don't want anyone else to delete 
	@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def IcdGtexRpciInstance = IcdGtexRpci.get(params.id)
        if (IcdGtexRpciInstance) {
            try {
                IcdGtexRpciInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'IcdGtexRpci.label', default: 'RPCI Consent Information Form For Candidate'), IcdGtexRpciInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
    
    def bssSubList(){
        def bssSubList = []
        def bss = BSS.findByCode("RPCI")
        //get all bss, parent and subs
        if(bss){
            def tmpList = BSS.findAllByParentBss(bss)
     
            tmpList.each{
                //strip BSSs that don't have a protocolSiteNum
                if(it.protocolSiteNum){
                    bssSubList.add(it)
                }
            }
        }
        return bssSubList
    }    
    
}
