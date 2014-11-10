package nci.obbr.cahub.forms.gtex

import grails.plugins.springsecurity.Secured 

import nci.obbr.cahub.util.ComputeMethods
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.util.AppSetting
import grails.util.GrailsUtil

class DonorEligibilityGtexController extends BaseGTEXCRFController{
    
    def accessPrivilegeService
    def sendMailService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def scaffold = DonorEligibilityGtex

    def index = {
        redirect(action: "create", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [donorEligibilityGtexInstanceList: DonorEligibilityGtex.list(params), donorEligibilityGtexInstanceTotal: DonorEligibilityGtex.count()]
    }

    def create = {
        def donorEligibilityGtexInstance = new DonorEligibilityGtex()
        donorEligibilityGtexInstance.properties = params
        
       
        //PMH 03/20/13: hub-cr-35 from 4.5 onwards..label change for Q.12
        def  changeText
        // if candidate record is not linked to a case yet, then show the new text
        if(donorEligibilityGtexInstance.candidateRecord.caseRecord == null){
            changeText=true
        }
        else if(ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord.caseRecord.cdrVer, '4.5') >= 0){
            changeText=true
        }
        else{
            changeText=false
        }
        
        //pmh 08/27/13 new for 5.2
        def  ver52Updates = false
       
        if(donorEligibilityGtexInstance.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord?.cdrVer, '5.2') >= 0){
            ver52Updates=true
        }
        return [donorEligibilityGtexInstance: donorEligibilityGtexInstance,changeText:changeText,ver52Updates:ver52Updates]
    }

    def save = {
        def donorEligibilityGtexInstance = new DonorEligibilityGtex(params)
        donorEligibilityGtexInstance.properties = params
        if(params.collectIn8afterDeath){
            donorEligibilityGtexInstance.collectAllIn24afterDeath=null
        }
        else if(params.collectAllIn24afterDeath){
            donorEligibilityGtexInstance.collectIn8afterDeath=null 
        }
        //pmh 08/27/13 new for 5.2
        def  ver52Updates = false
       
        if(donorEligibilityGtexInstance.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord?.cdrVer, '5.2') >= 0){
            ver52Updates=true
        }
        def result=[:]
        
        result= checkError(donorEligibilityGtexInstance,ver52Updates)
        if(result){
            result.each(){key,value->
                donorEligibilityGtexInstance.errors.rejectValue(key, value)
                
                //render(view: "create", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance,ver52Updates:ver52Updates])
            }
            render(view: "create", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance,ver52Updates:ver52Updates])
        }
        else{
        
            if (donorEligibilityGtexInstance.save(flush: true)) {
            
                calculateAndSetEligibility(donorEligibilityGtexInstance,ver52Updates)
            
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
                redirect(action: "edit", id: donorEligibilityGtexInstance.id)
            }
            else {
                render(view: "create", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance,ver52Updates:ver52Updates])
            }
        }
    }

    def show = {
        def donorEligibilityGtexInstance = DonorEligibilityGtex.get(params.id)
        if (!donorEligibilityGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
            redirect(action: "edit")
        }
        else {
            [donorEligibilityGtexInstance: donorEligibilityGtexInstance]
        }
    }

    def view = {
        def donorEligibilityGtexInstance = DonorEligibilityGtex.get(params.id)
        //pmh 08/27/13 new for 5.2
        def  ver52Updates = false
        
        if(donorEligibilityGtexInstance.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord?.cdrVer, '5.2') >= 0){
            ver52Updates=true
        }
        
        if (!donorEligibilityGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
            redirect(action: "edit")
        }
        else {
            
            def candidateRecord = donorEligibilityGtexInstance.candidateRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            //            if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'view')) {
            //                redirect(controller: "login", action: "denied")
            //                return
            //            }
           
            //PMH 03/20/13: hub-cr-35 from 4.5 onwards..label change for Q.12
            def  changeText
            // if candidate record is not linked to a case yet, then show the new text
            if(donorEligibilityGtexInstance.candidateRecord.caseRecord == null){
                changeText=true
            }
            else if(ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord.caseRecord.cdrVer, '4.5') >= 0){
                changeText=true
            }
            else{
                changeText=false
            }
            
            
            return [donorEligibilityGtexInstance: donorEligibilityGtexInstance,changeText:changeText,ver52Updates:ver52Updates]
        }
    }   
    
    def edit = {
        def donorEligibilityGtexInstance = DonorEligibilityGtex.get(params.id)
        def ver52Updates
         def  changeText
        def result=[:]
                
        def candidateRecord = donorEligibilityGtexInstance.candidateRecord
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')
        if (accessPrivilege > 0) {
            redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
            return
        }
        //        if (!accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecord, session, 'edit')) {
        //            redirect(controller: "login", action: "denied")
        //            return
        //        }
        
        
        if (!donorEligibilityGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
            redirect(action: "edit")
        }
        else {
            
            //PMH 03/20/13: hub-cr-35 from 4.5 onwards..label change for Q.12
           
            // if candidate record is not linked to a case yet, then show the new text
            if(donorEligibilityGtexInstance.candidateRecord.caseRecord == null){
                changeText=true
            }
            else if(ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord.caseRecord.cdrVer, '4.5') >= 0){
                changeText=true
            }
            else{
                changeText=false
            }
            
            if(donorEligibilityGtexInstance.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord?.cdrVer, '5.2') >= 0){
                ver52Updates=true
            }
            else{
                ver52Updates=false
            }
            result= checkError(donorEligibilityGtexInstance,ver52Updates)
            if(result){
                result.each(){key,value->
                    donorEligibilityGtexInstance.errors.rejectValue(key, value)
                }
               render(view: "edit", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance,changeText:changeText,ver52Updates:ver52Updates])         
            }
          
        } 
            
        return [donorEligibilityGtexInstance: donorEligibilityGtexInstance,changeText:changeText,ver52Updates:ver52Updates]
    }


def update = {
        
    def donorEligibilityGtexInstance = DonorEligibilityGtex.get(params.id)
    if (donorEligibilityGtexInstance) {
            
             
        //pmh cdrqa 1079 03/20/14
        boolean emailq15response =false
        if((!donorEligibilityGtexInstance.humAnimTissueTransplant || donorEligibilityGtexInstance.humAnimTissueTransplant =="No") && params.humAnimTissueTransplant == 'Yes' && donorEligibilityGtexInstance.candidateRecord.caseRecord?.caseId){
            emailq15response=true
        }
                
        if (params.version) {
            def version = params.version.toLong()
            if (donorEligibilityGtexInstance.version > version) {
                donorEligibilityGtexInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form')] as Object[], "Another user has updated this DonorEligibilityGtex while you were editing")
                render(view: "edit", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance])
                return
            }
        }
        donorEligibilityGtexInstance.properties = params
        if(params.collectIn8afterDeath){
            donorEligibilityGtexInstance.collectAllIn24afterDeath=null
        }
        else if(params.collectAllIn24afterDeath){
            donorEligibilityGtexInstance.collectIn8afterDeath=null 
        }
        //pmh new for 5.2
            
        def result=[:]
        def ver52Updates=false
        if(donorEligibilityGtexInstance.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(donorEligibilityGtexInstance.candidateRecord?.cdrVer, '5.2') >= 0){
            ver52Updates=true
            result= checkError(donorEligibilityGtexInstance,true)
        }
        else{
            ver52Updates=false
            result= checkError(donorEligibilityGtexInstance,false)
        }
            
        if(result){
            result.each(){key,value->
                //println key+ ": "+value
                donorEligibilityGtexInstance.errors.rejectValue(key, value)
            }
            //render(view: "edit", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance,ver52Updates:ver52Updates])
            redirect(action: "edit", id: donorEligibilityGtexInstance.id)
                
        }
        else{
               
            if ( donorEligibilityGtexInstance.save(flush: true)) {

                calculateAndSetEligibility(donorEligibilityGtexInstance,ver52Updates)
                if(emailq15response){
                        
                    def caseId = donorEligibilityGtexInstance.candidateRecord.caseRecord.caseId
                    def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    def recipient = AppSetting.findByCode('GTEX_DONELIGQ15YES_DISTRO')?.bigValue
                    def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"
                    def emailSubject = "CDR Alert:$env Response for Donor Eligibility form  Q15 for ${caseId} is Yes"
                    //def emailBody = "Donor Eligibility form  Q15 for  ${caseId} was submitted by ${username} as YES.\n(Comments: "+donorEligibilityGtexInstance.tissueTransplantComments+")"
                    def emailBody = "Donor Eligibility form  Q15 for  ${caseId} was submitted by ${username}.\n\nReceived a human and/or animal tissue and/or organ transplant or xenotransplant:\t YES\nComments: "+donorEligibilityGtexInstance.tissueTransplantComments
                    sendMailService.sendServiceEmail(recipient, emailSubject, emailBody, 'body')
                    // activityEventService.createEvent(activityType, caseId, study, bssCode, null, username,donorEligibilityGtexInstance.tissueTransplantComments, null)
                }
                                    
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
                redirect(action: "edit", id: donorEligibilityGtexInstance.id)
            }
            else {
                render(view: "edit", model: [donorEligibilityGtexInstance: donorEligibilityGtexInstance,ver52Updates:ver52Updates])
            }
        }
    }
    else {
        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
        redirect(action: "list")
    }
}

//We don't want anyone else to delete 
@Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
def delete = {
    def donorEligibilityGtexInstance = DonorEligibilityGtex.get(params.id)
    if (donorEligibilityGtexInstance) {
        try {
            donorEligibilityGtexInstance.delete(flush: true)
            flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
            redirect(action: "show", id: params.id)
        }
    }
    else {
        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form For Candidate'), donorEligibilityGtexInstance.candidateRecord.candidateId])}"
        redirect(action: "list")
    }
}
    
static Map checkError(donorEligibilityGtexInstance,chkforthisVersion){      
    def result = [:]
    
    if(chkforthisVersion){
        if(!donorEligibilityGtexInstance.collectIn8afterDeath &&  !donorEligibilityGtexInstance.collectAllIn24afterDeath){
                
            result.put("collectIn8afterDeath", " Question 4A: Please select an option for either question #4a or #4b")
            result.put("collectAllIn24afterDeath", " Question 4B: Please select an option for either question #4a or #4b")
        }
    }
    else{
            
        if(!donorEligibilityGtexInstance.collectIn24hDeath || donorEligibilityGtexInstance.collectIn24hDeath.equals("Undef") ){
                
            result.put("collectIn24hDeath", " Please select an option for question #4 ")
               
        }
    }
   
    if(!donorEligibilityGtexInstance.allowedMinOrganType || donorEligibilityGtexInstance.allowedMinOrganType.equals("Undef") ){
                
        result.put("allowedMinOrganType", " Please select an option for question #1 ")
               
    }
    if(!donorEligibilityGtexInstance.age || donorEligibilityGtexInstance.age.equals("Undef")){
                
        result.put("age", " Please select an option for question #2 ")
               
    }
    if(!donorEligibilityGtexInstance.BMI || donorEligibilityGtexInstance.BMI.equals("Undef")){
                
        result.put("BMI", " Please select an option for question #3 ")
               
    }
    if(!donorEligibilityGtexInstance.receiveTransfusionIn48h || donorEligibilityGtexInstance.receiveTransfusionIn48h.equals("Undef")){
                
        result.put("receiveTransfusionIn48h", " Please select an option for question #5 ")
               
    }
    if(!donorEligibilityGtexInstance.diagnosedMetastatis || donorEligibilityGtexInstance.diagnosedMetastatis.equals("Undef")){
                
        result.put("diagnosedMetastatis", " Please select an option for question #6 ")
               
    }
    if(!donorEligibilityGtexInstance.receivedChemoIn2y || donorEligibilityGtexInstance.receivedChemoIn2y.equals("Undef")){
                
        result.put("receivedChemoIn2y", " Please select an option for question #7 ")
               
    }
    if(!donorEligibilityGtexInstance.drugAbuse || donorEligibilityGtexInstance.drugAbuse.equals("Undef")){
                
        result.put("drugAbuse", " Please select an option for question #8 ")
               
    }
        
    if(!donorEligibilityGtexInstance.histOfSexWithHIV || donorEligibilityGtexInstance.histOfSexWithHIV.equals("Undef")){
                
        result.put("histOfSexWithHIV", " Please select an option for question #9 ")
               
    }
    if(!donorEligibilityGtexInstance.contactHIV || donorEligibilityGtexInstance.contactHIV.equals("Undef")){
                
        result.put("contactHIV", " Please select an option for question #10 ")
               
    }
    if(!donorEligibilityGtexInstance.histOfReactiveAssays || donorEligibilityGtexInstance.histOfReactiveAssays.equals("Undef")){
                
        result.put("histOfReactiveAssays", " Please select an option for question #11 ")
               
    }
    if(donorEligibilityGtexInstance.pastBloodDonations.equals('Yes') && !donorEligibilityGtexInstance.bloodDonDenialReason){
                
        result.put("pastBloodDonations", " Please enter a reason for question #13 since you selected Yes ")
               
    }           
    if(donorEligibilityGtexInstance.humAnimTissueTransplant.equals('Yes') && !donorEligibilityGtexInstance.tissueTransplantComments){
        
        result.put("humAnimTissueTransplant", " Please enter comments for question #15 since you selected Yes ")
               
    }
        
    return result
}
}
