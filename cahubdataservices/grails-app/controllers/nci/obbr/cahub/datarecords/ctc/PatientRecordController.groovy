package nci.obbr.cahub.datarecords.ctc
import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.CaseStatus
import nci.obbr.cahub.staticmembers.CaseCollectionType
import org.codehaus.groovy.grails.commons.ApplicationHolder
import nci.obbr.cahub.util.*
import nci.obbr.cahub.forms.ctc.*

class PatientRecordController {
    def ctcService 
    static allowedMethods = [save: "POST", update: "POST", delete: "POST", submit:"POST", resumeEditing:"POST" ]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase()
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        
        if(!params.sort){
            params.sort ="dateCreated"
            params.order = "desc"
        }
        [patientRecordInstanceList: PatientRecord.list(params), patientRecordInstanceTotal: PatientRecord.count(),username:username]
    }

    def create = {
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
        if (!session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') && !session.authorities.contains('ROLE_ADMIN') &&  !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) {
            redirect(controller: "login", action: "denied")
            return
        }
        def patientRecordInstance = new PatientRecord()
        patientRecordInstance.properties = params
        
        def ctcBssList= BSS.findAllByStudy(Study.findByCode('CTC'))
        // SPRI is the research inst. SPC is the clinic. Blood is sent from SPC to SPRI... so do not inlcude SPRI in the bss list
        def filterCTCList=ctcBssList.findAll { !(it.code?.contains ('SPRI')) }
        //println filterCTCList
        
        return [patientRecordInstance: patientRecordInstance,ctcBssList:filterCTCList]
    }

    def save = {
                
        def patientRecordInstance = new PatientRecord(params)
        
        if (!patientRecordInstance.disease || patientRecordInstance.disease?.trim().length()  == 0 ) {
            patientRecordInstance.disease="Breast Cancer"
        }
        // isDuplicate is needed to foolproof the entry of patientID. 'abc123' and '   abc123' should be considered duplicates
        // patient ID is case sensitive. currently 'abc' and 'ABC' are different entries
        def isDuplicate =false
        def objExists =PatientRecord.findByPatientId(params.patientId?.trim())
        if(objExists) isDuplicate =true
        
        if ( !isDuplicate && patientRecordInstance.save(flush: true) ) {
            patientRecordInstance.patientId= patientRecordInstance.patientId?.trim()          
            //flash.message =" Saved Patient Record "
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'Patient.label', default: 'Patient ID'), patientRecordInstance.patientId])}"
            //flash.args =[patientRecordInstance.id]
            flash.default ="Saved Patient Record"
            redirect(action: "edit", id: patientRecordInstance.id)
            //redirect(action: "ctchome", controller:"home")
        }
        else {
            if(isDuplicate){
                flash.message =" Patient ID already exists. Please enter a valid ID"
                flash.default =" Patient ID already exists. Please enter a valid ID" 
            }else{
                flash.message =" Failed creating Patient Record"
                flash.default =" Failed creating Patient Record" 
            }
            //flash.args =[patientRecordInstance.id]
            patientRecordInstance.errors.rejectValue('patientId', 'Failed creating Patient Record')
            def ctcBssList= BSS.findAllByStudy(Study.findByCode('CTC'))
            // SPRI is the research inst. SPC is the clinic. Blood is sent from SPC to SPRI... so do not inlcude SPRI in the bss list
            def filterCTCList=ctcBssList.findAll { !(it.code?.contains ('SPRI')) }
            //println filterCTCList
            render(view: "create", model: [patientRecordInstance: patientRecordInstance,ctcBssList:filterCTCList])
        }
        
       
        
    }

    def show = {
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
        if (!session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB') && !session.authorities.contains('ROLE_ADMIN') &&  !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) {
            redirect(controller: "login", action: "denied")
            return
        }
        def patientRecordInstance = PatientRecord.get(params.id)
        
        def ctcBssList= BSS.findAllByStudy(Study.findByCode('CTC'))
        // SPRI is the research inst. SPC is the clinic. Blood is sent from SPC to SPRI... so do not inlcude SPRI in the bss list
        def filterCTCList=ctcBssList.findAll { !(it.code?.contains ('SPRI')) }
        
        if (!patientRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'Patient.label', default: 'Patient'), params.id])}"
            redirect(action: "list")
        }
        else {
            // def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
            [patientRecordInstance: patientRecordInstance,ctcBssList:filterCTCList, username:username]
        }
    }
    
    def submit = {
        
        
      
        def patientRecordInstance = PatientRecord.get(params.id)
        def newcaseId
        if (patientRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (patientRecordInstance.version > version) {
                    
                    patientRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'Patient.label', default: 'Patient')] as Object[], "Another user has updated this Patient while you were editing")
                    render(view: "edit", model: [patientRecordInstance: patientRecordInstance])
                    return
                }
            }
            patientRecordInstance.properties = params
            
            if (!patientRecordInstance.disease || patientRecordInstance.disease?.trim().length()  == 0 ) {
                patientRecordInstance.disease="Breast Cancer"
            }
            def errorMap = checkError(patientRecordInstance)
            errorMap.each() {key, value ->
                patientRecordInstance.errors.rejectValue(key, value)
            }
            
            if (!patientRecordInstance.hasErrors() ) {
                if(!patientRecordInstance.caseRecord){
                    newcaseId=createNewCaseRecord(patientRecordInstance)
                
                    if(newcaseId){
                       
                        //patientRecordInstance.dateCreated = new Date()
                        patientRecordInstance.caseRecord= CaseRecord.findByCaseId(patientRecordInstance.patientId)
                        //patientRecordInstance.lastUpdated = new Date()
                        patientRecordInstance.dateSubmitted = new Date()
                        patientRecordInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                        patientRecordInstance.save(flush: true)
                        
                        def caseRecord = patientRecordInstance.caseRecord
                        caseRecord.index()
                        
                        flash.message = "${message(code: 'default.submitted.message', args: [message(code: 'Patient.label', default: 'Patient ID'), patientRecordInstance.patientId])}"
                        //redirect(action: "ctchome", controller:"home")
                        redirect(action:"accessCtc", controller:"caseRecord",id:patientRecordInstance.caseRecord?.id)
                        
                    }
                    else{
                        flash.message =" Failed creating Case Record"
                        flash.args =[patientRecordInstance.id]
                        flash.default =" Failed creating Case Record" 
                   
                        //redirect(action: "list")
                        patientRecordInstance.discard()
                        render(view: "edit", model: [patientRecordInstance: patientRecordInstance])
                    }
                }
                else{
                    patientRecordInstance.dateSubmitted = new Date()
                    patientRecordInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    
                    // coming here means that the case record of the patient needs to be synced with any changes to the the patient record
                    def existingCaseInstance = CaseRecord.findById(patientRecordInstance?.caseRecord.id)
                    def oldPatientId
                    if(existingCaseInstance){
                        oldPatientId =existingCaseInstance.caseId                    
                        existingCaseInstance.caseId=patientRecordInstance.patientId
                        existingCaseInstance.bss=patientRecordInstance.collectionSite
                        existingCaseInstance.save(flush:true)
                        existingCaseInstance.index()
                        // if patient id has changed and not the same as in the originally created case record, then change the crf specimens too 
                        if(oldPatientId != patientRecordInstance.patientId){
                            //println("i AM HERE using liquns  code")
                            for (int i= 1; i <=5; i++){
                                def crf = CtcCrf.findByCaseRecordAndWhichVisit(existingCaseInstance, i)
                                if(crf && crf.dateSubmitted !=null){
                                    def ctcSamples = crf.ctcSamples
                                    ctcSamples.each{
                                        def sample = it.sample
                                        if(sample){
                                            sample.specimenId=existingCaseInstance.caseId + "_" + crf.whichVisit + "_" +it.tubeId 
                                            sample.save(flush:true)
                                        }
                                    }
                                }else {
                                    //do nothing here :cdrqa 1396 check for crf date submitted
                                }
                            }
                        }

                        
                    }
                    flash.message = "${message(code: 'default.submitted.message', args: [message(code: 'Patient.label', default: 'Patient'), patientRecordInstance.patientId])}"
                    
                    //redirect(action: "ctchome", controller:"home")
                    redirect(action:"accessCtc", controller:"caseRecord",id:patientRecordInstance.caseRecord?.id)
                    
                }
                 
                
            }
            else {
                patientRecordInstance.discard()
                render(view: "edit", model: [patientRecordInstance: patientRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [patientRecordInstance?.formMetadata?.cdrFormName + ' for Case', patientRecordInstance.caseRecord.caseId])}"
            //redirect(action: "list")
            redirect(action: "ctchome", controller:"home")
        }
    }


    def edit = {
        
        def patientRecordInstance = PatientRecord.get(params.id)
        if(params.pid){
            println "got this from update Action "+params.pid
            patientRecordInstance.patientId=params.pid
        }
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
        if ((!session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') && !session.authorities.contains('ROLE_ADMIN') &&  !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) ||patientRecordInstance.dateSubmitted) {
            redirect(controller: "login", action: "denied")
            return
        }
        def canSubmit = 'No'
        def ctcBssList= BSS.findAllByStudy(Study.findByCode('CTC'))
        // SPRI is the research inst. SPC is the clinic. Blood is sent from SPC to SPRI... so do not inlcude SPRI in the bss list
        def filterCTCList=ctcBssList.findAll { !(it.code?.contains ('SPRI')) }
        //println filterCTCList
        
        if (!patientRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'Patient.label', default: 'Patient ID'), params.id])}"
            //redirect(action: "list")
            redirect(action: "ctchome", controller:"home")
        }
        else {
            
            def errorMap = checkError(patientRecordInstance)
            errorMap.each() {key, value ->
                patientRecordInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            else{flash.message = ""}
            
            def submittedVisit = ctcService.getSubmittedVisit(patientRecordInstance)
            //println("submittedVist: " + submittedVisit)
           
            return [patientRecordInstance: patientRecordInstance,canSubmit: canSubmit,ctcBssList:filterCTCList, submittedVisit:submittedVisit]
        }
    }
    
    
    def update = {
       
        def patientRecordInstance = PatientRecord.get(params.id?.trim())
        //println params.patientId + " is patient id in update action"
        //println patientRecordInstance.patientId + " is patient id in update action from patientRecordInstance"
        
        if (patientRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (patientRecordInstance.version > version) {
                    
                    patientRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'Patient.label', default: 'Patient')] as Object[], "Another user has updated this Patient while you were editing")
                    render(view: "edit", model: [patientRecordInstance: patientRecordInstance])
                    return
                }
            }
            patientRecordInstance.properties = params            
            if (!patientRecordInstance.disease || patientRecordInstance.disease?.trim().length()  == 0 ) {
                patientRecordInstance.disease="Breast Cancer"
            }
            if (patientRecordInstance.patientId ) {
                patientRecordInstance.patientId=patientRecordInstance.patientId.trim()
                // println patientRecordInstance.patientId+" in line 286"
            }
            
            def errorMap = checkError(patientRecordInstance)
            errorMap.each() {key, value ->
                patientRecordInstance.errors.rejectValue(key, value)
            }
            
            if ( !patientRecordInstance.hasErrors() && patientRecordInstance.save(flush: true)) {
                patientRecordInstance.patientId= patientRecordInstance.patientId.trim()  
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'Patient.label', default: 'Patient ID'), patientRecordInstance.patientId])}"
                
            }
            
               // passing pid to check if pid is duplicate
            redirect(action: "edit", id: patientRecordInstance.id,params: [pid: patientRecordInstance.patientId])
           
        }
        else {
            
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'Patient.label', default: 'Patient'), params.id])}"
            //redirect(action: "list")
            redirect(action: "ctchome", controller:"home")
        }
    }

    
    
    def remove = {
        
        def patientRecordInstance = PatientRecord.get(params.id)
        def caseRecord = CaseRecord.findByCaseId(patientRecordInstance?.patientId)
        // println caseRecord.id+ " is cse id to be deleted"
        if(patientRecordInstance &&  !(session?.org?.code.equals('OBBR') && session.DM==true)){
            redirect(controller: "login", action: "denied")
            return
        }
        else{
            // println("caseRecord.id: " + caseRecord.id)
            
            try {
                
                patientRecordInstance.delete(flush: true)
                //We don't want anyone else to delete case records
                if (caseRecord && (session.org?.code == 'OBBR' || session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER'))) {
                    caseRecord.delete(flush: true)
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException(e.toString())
            }
        
            //redirect(action: "list")
            redirect(action: "ctchome", controller:"home")
            
        }
    }
    
    def resumeEditing = {
        def patientRecordInstance = PatientRecord.get(params.id)
       
        patientRecordInstance.dateSubmitted = null
        patientRecordInstance.submittedBy = null
        if (patientRecordInstance.save(flush: true)) {
            redirect(action: "edit", id: patientRecordInstance.id)
            
        }
        else {
            render(view: "show", model: [patientRecordInstance: patientRecordInstance])
        }
    }
    
    String createNewCaseRecord(patientRecordInstance) {
        def canCreateCase = true
        def newcaseId = null
        if (CaseRecord.findByCaseId(patientRecordInstance?.patientId) != null) {
            
            canCreateCase = false
        }
       
        
        if (canCreateCase) {
            def caseRecordInstance = new CaseRecord(
                caseId: patientRecordInstance.patientId,
                caseStatus: CaseStatus.findByCode("DATA"),
                caseCollectionType: CaseCollectionType.findByCode("SURGI"),
                bss: patientRecordInstance.collectionSite,
                study: Study.findByCode("CTC"),
                cdrVer: ApplicationHolder.application.metadata['app.version']
                    
            )
       
            
            try
            {
                caseRecordInstance.save(flush:true)
                
                newcaseId =caseRecordInstance.id
            }
            catch(Exception ee)
            {
                //flash.message =" Failed creating Case Record"
                //flash.args =[patientRecordInstance.id]
                //flash.default =" Failed creating Case Record" 
                //flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'caseRecord.label', default: 'Case Record ID or Tissue Bank ID for Case'), caseRecordInstance.caseId])}"
                
            }
        }
        return newcaseId 
    }
    
    Map checkError(patientRecordInstance) {
        def errorMap = [:]
       
               
        if (!patientRecordInstance.patientId ) {
            errorMap.put('patientId', 'Please enter Patient ID')
        }else{
            if(patientRecordInstance.patientId.length() > 50 ){
                errorMap.put('patientId', "The length of patient ID can't be greater than 50") 
            }
        }
        if(patientRecordInstance.patientId && CaseRecord.findByCaseId(patientRecordInstance?.patientId.trim())){
            def this_caseId = patientRecordInstance.caseRecord?.id
            def find_caseId = CaseRecord.findByCaseId(patientRecordInstance?.patientId)?.id
            if(this_caseId && find_caseId && this_caseId == find_caseId){
              
            }else if(!this_caseId && find_caseId){
                errorMap.put('patientId', 'Patient ID already exists. Please enter a valid ID ')
            }else if(this_caseId && find_caseId && this_caseId != find_caseId){
                errorMap.put('patientId', 'Patient ID already exists. Please enter a valid ID ')
            }else{
                
            }
            
            
        }
        if (!patientRecordInstance.experiment ) {
            
            errorMap.put('experiment', 'Please select type of Experiment ')
        }
        if (!patientRecordInstance.gender ) {
            errorMap.put('gender', 'Please select Patient Gender')
            
        }
        //if (!patientRecordInstance.disease ) {
        //   errorMap.put('disease', 'Please enter type of disease')
        //}
        
        if (!patientRecordInstance.cancerStage ) {
            
            errorMap.put('cancerStage', 'Please select Cancer stage')
        }
        //if (!patientRecordInstance.consentDate ) {
            
        // errorMap.put('consentDate', 'Please enter patient consent date')
        //  }
                
        if (!patientRecordInstance.collectionSite ) {
            errorMap.put('collectionSite', 'Please select Collection site')
        }
        if (!patientRecordInstance.visit ) {
            
            errorMap.put('visit', 'Please select Visit Number')
        }
        
       
        
        
        return errorMap
    }
}
