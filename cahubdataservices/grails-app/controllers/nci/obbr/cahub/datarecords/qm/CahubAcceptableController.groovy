package nci.obbr.cahub.datarecords.qm

import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.datarecords.CaseRecord

class CahubAcceptableController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index={
        
    }
       

    def show = {
        def cahubAcceptableInstance = CahubAcceptable.get(params.id)
        if (!cahubAcceptableInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cahubAcceptable.label', default: 'CahubAcceptable'), params.id])}"
            redirect(action: "list")
        }
        else {
            [cahubAcceptableInstance: cahubAcceptableInstance]
        }
    }
    
    
    
    def accept ={
        
        def cahubAcceptableInstance
        try{
            
            def caseRecordInstance = CaseRecord.get(params.id)
            
            cahubAcceptableInstance= CahubAcceptable.findByCaseRecord(caseRecordInstance)
            
            if (!cahubAcceptableInstance) {
                cahubAcceptableInstance = new CahubAcceptable()
                cahubAcceptableInstance.caseRecord = caseRecordInstance
                cahubAcceptableInstance.save(failOnError:true)
                
            }
            
        }
        catch(Exception e){
          
            throw new RuntimeException(e.toString())
        }
        
        redirect(action: "edit", id: cahubAcceptableInstance.id)
        //return [cahubAcceptableInstance: cahubAcceptableInstance,caseRecordInstance:caseRecordInstance]
        
    }

    def edit = {
        def errMap
        def cahubAcceptableInstance = CahubAcceptable.get(params.id)
        def caseRecordInstance = CaseRecord.get(cahubAcceptableInstance.caseRecord.id)
        if (!caseRecordInstance) {
            redirect(controller: 'caseRecord', action: "display", params: [id:cahubAcceptableInstance.caseRecord.id])
        }
        
        if (!cahubAcceptableInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'CahubAcceptable.label', default: 'Cahub Analysis Acceptance form'), params.id])}"
            
        }
        else {
            errMap= checkError(cahubAcceptableInstance)
            
        } 
        if(errMap){
            errMap.each(){key,value->
                cahubAcceptableInstance.errors.rejectValue(key, value)
            }
            //render(view: "edit", model: [cahubAcceptableInstance: cahubAcceptableInstance])
        }
            return [cahubAcceptableInstance: cahubAcceptableInstance,caseRecordInstance:caseRecordInstance]
      
       
    }
    
    
    

    def save = {
        
        def cahubAcceptableInstance = CahubAcceptable.get(params.id)
        if (cahubAcceptableInstance) {
            def caseRecordInstance = CaseRecord.get(cahubAcceptableInstance.caseRecord.id)
            if (params.version) {
                def version = params.version.toLong()
                if (cahubAcceptableInstance.version > version) {
                    
                    cahubAcceptableInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'cahubAcceptable.label', default: 'CahubAcceptable')] as Object[], "Another user has updated this CahubAcceptable while you were editing")
                    //render(view: "edit", model: [cahubAcceptableInstance: cahubAcceptableInstance])
                    redirect(action: "edit", id: cahubAcceptableInstance.id)
                    return
                }
            }
            cahubAcceptableInstance.properties = params
            
            
                        
            def errorMap = checkError(cahubAcceptableInstance)
            errorMap.each() {key, value ->
                cahubAcceptableInstance.errors.rejectValue(key, value)
            }
            
            
            
            if (!cahubAcceptableInstance.hasErrors() && cahubAcceptableInstance.save(flush: true)) {
                
                if(cahubAcceptableInstance.acceptable =='true'){
                    cahubAcceptableInstance.cahubUnacceptable=null
                    cahubAcceptableInstance.otherReason=null
                    cahubAcceptableInstance.notAcceptableDate=null
                }
                if(cahubAcceptableInstance.acceptable =='false'){
                    cahubAcceptableInstance.notAcceptableDate=new Date()
                }
                
                cahubAcceptableInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
               // cahubAcceptableInstance.updateDate = new Date()
                flash.message = " Response for acceptance to caHUB Analysis has beed updated for "+caseRecordInstance.caseId
                redirect(controller: "caseRecord", action: "display", id: cahubAcceptableInstance.caseRecord.id)
                       
            }
            else {   
                cahubAcceptableInstance.submittedBy = null
                //cahubAcceptableInstance.updateDate = null
                //render(view: "edit", model: [cahubAcceptableInstance: cahubAcceptableInstance])
                //redirect(action: "edit", model: [cahubAcceptableInstance: cahubAcceptableInstance])
                redirect(action: "edit", id: cahubAcceptableInstance.id)
            }
        }
        else {
            redirect(controller: "caseRecord", action: "display")
            //redirect(action: "list")
        }
    }

    Map checkError(cahubAcceptableInstance) {
        def errorMap = [:]
        //println cahubAcceptableInstance.cahubUnacceptable?.id
        //println cahubAcceptableInstance.acceptable
        if (cahubAcceptableInstance.acceptable != 'true' && cahubAcceptableInstance.acceptable != 'false') {
            errorMap.put('acceptable', 'Please select if acceptable or not')
        }
        if (cahubAcceptableInstance.acceptable == 'false' && cahubAcceptableInstance.cahubUnacceptable?.id.equals(null)) {
           
            errorMap.put('cahubUnacceptable', 'Please select reason why case is not acceptable for analysis ')
        }
        
        if (cahubAcceptableInstance.acceptable.equals('false') && cahubAcceptableInstance.cahubUnacceptable?.code == 'OTHER' && !cahubAcceptableInstance.otherReason) {
            
            errorMap.put('otherReason', 'Please specify other reason ')
        }
        
        
        return errorMap
    }
   
}
