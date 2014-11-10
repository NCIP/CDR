package nci.obbr.cahub.forms.gtex

import grails.converters.*
import java.text.SimpleDateFormat;
import nci.obbr.cahub.datarecords.*
import grails.plugins.springsecurity.Secured

class TissueRecoveryBrainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def mbbService
    def bpvWorkSheetService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [tissueRecoveryBrainInstanceList: TissueRecoveryBrain.list(params), tissueRecoveryBrainInstanceTotal: TissueRecoveryBrain.count()]
    }

    def upload = {
        def tissueRecoveryBrainInstance = new TissueRecoveryBrain()
        tissueRecoveryBrainInstance.properties = params
        return [tissueRecoveryBrainInstance: tissueRecoveryBrainInstance]
    }

    def save = {
        
      
        def cid = params.get("caseRecord.id")
        /** def tissueRecoveryBrainInstance = new TissueRecoveryBrain(params)
        if (tissueRecoveryBrainInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.created.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), tissueRecoveryBrainInstance.id])}"
        redirect(action: "show", id: tissueRecoveryBrainInstance.id)
        }
        else {
        render(view: "create", model: [tissueRecoveryBrainInstance: tissueRecoveryBrainInstance])
        }**/
        boolean hasError=false
        try{
            mbbService.saveFile(params, request)
        }catch(Exception e){
            hasError=true
            // println e
            //flash.message=e
            // show something more meaningful CDRQA-466 :pmh 04/25/13
            if(e.toString().contains('BrainTissue.sampleId.unique.error')){
                flash.message="A sample ID in the file being uploaded already exists. Please check data and try again"
            }else{
                flash.message="Error in uploading the file. Please check file. Accepted file formats are .xls and .csv"
            }
        }
        
        if(hasError){
            redirect(action: "upload", params:['caseRecord.id':cid])
        }else{
            def caseRecord =CaseRecord.findById(cid)
            def tissueRecoveryBrainInstance = TissueRecoveryBrain.findByCaseRecord(caseRecord)
            redirect(action: "edit", id:tissueRecoveryBrainInstance.id)
        }
        
    }

    def show = {
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        if (!tissueRecoveryBrainInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tissueRecoveryBrainInstance: tissueRecoveryBrainInstance]
        }
    }

    def edit = {
        def errorMap=[:]
        def canSubmit='N'
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        if (!tissueRecoveryBrainInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
            redirect(action: "list")
        }
        else {
            if(tissueRecoveryBrainInstance.dateSubmitted)
                redirect(controller: "login", action: "denied")
                
            if(tissueRecoveryBrainInstance.started){
                def result =checkError(tissueRecoveryBrainInstance)
                if(result){
                    result.each(){key,value->
                        // println("key: " + key + " value: " + value)
                        tissueRecoveryBrainInstance.errors.reject(key, value)
                        errorMap.put(key, "errors")
                    }//each
                }else{
                    canSubmit='Y'
                }
                
            }
            
            def brainTissues=mbbService.getBrainTissues(tissueRecoveryBrainInstance)
           
            return [tissueRecoveryBrainInstance:tissueRecoveryBrainInstance, brainTissues:brainTissues, errorMap:errorMap, canSubmit:canSubmit]
        }
    }
    
    def submit ={
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        
        mbbService.submitForm(tissueRecoveryBrainInstance)
        redirect(action: "view", id:params.id)
         
    }
    
    def resume ={
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        
        mbbService.resumeEditing(tissueRecoveryBrainInstance)
        redirect(action: "edit", id:params.id)
         
    }

    def view = {
        def errorMap=[:]
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        if (!tissueRecoveryBrainInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
            redirect(action: "list")
        }
        else {
           
            
            def brainTissues=mbbService.getBrainTissues(tissueRecoveryBrainInstance)
           
            return [tissueRecoveryBrainInstance:tissueRecoveryBrainInstance, brainTissues:brainTissues]
        }
    }

    
    def update = {
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        if (tissueRecoveryBrainInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tissueRecoveryBrainInstance.version > version) {
                    
                    tissueRecoveryBrainInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain')] as Object[], "Another user has updated this TissueRecoveryBrain while you were editing")
                    render(view: "edit", model: [tissueRecoveryBrainInstance: tissueRecoveryBrainInstance])
                    return
                }
            }
            mbbService.updateForm(tissueRecoveryBrainInstance, params)
            def brainTissues = tissueRecoveryBrainInstance.brainTissues.sort{it.position}
            redirect(action: "edit", id:params.id)
            // render(view: "edit", model: [tissueRecoveryBrainInstance: tissueRecoveryBrainInstance, brainTissues:brainTissues])
            /**tissueRecoveryBrainInstance.properties = params
            if (!tissueRecoveryBrainInstance.hasErrors() && tissueRecoveryBrainInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), tissueRecoveryBrainInstance.id])}"
            redirect(action: "show", id: tissueRecoveryBrainInstance.id)
            }
            else {
            render(view: "edit", model: [tissueRecoveryBrainInstance: tissueRecoveryBrainInstance])
            }**/
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        if (tissueRecoveryBrainInstance) {
            try {
                tissueRecoveryBrainInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain'), params.id])}"
            redirect(action: "list")
        }
    }
    
    Map checkError(tissueRecoveryBrainInstance){
        def result = [:]
        def list=tissueRecoveryBrainInstance.brainTissues
        list.each{
            def mass = it.mass
            def collectionDateStr = it.collectionDateStr
            def collectionTimeStr = it.collectionTimeStr
            def notes = it.notes
            if(it.collected){
                if(!mass ){
                    result.put("mass_" + it.id, "The mass for position " + it.position + " is a required field")
                }
                if(!collectionDateStr){
                    result.put("date_" + it.id, "The date for position " + it.position + " is a required field")
                }
               if(collectionDateStr && !mbbService.isDate(collectionDateStr) ){
                  result.put("date_" + it.id,  "Wrong date format for position " + it.position)
               }
              if(!collectionTimeStr){
                result.put("time_" + it.id, "The time for position " + it.position + " is a required field")
              }
            
           
        }else{
                 if(mass != null){
                     result.put("mass_" + it.id, "Please clear the mass for position " + it.position)
                 }
                 
                 if(collectionDateStr){
                       result.put("date_" + it.id, "Please clear the date for position " + it.position)
                 }
                 
                if(collectionTimeStr){
                     result.put("time_" + it.id, "Please clear the time for position " + it.position)
                }
              
                if(!notes && it.position != 'B08' && it.position != 'B09' ){
                     
                    result.put("notes_" + it.id, "The notes for position " + it.position + " is a required field")
                
                }
            
        }
        
            
        }
        return result
        
    }
    
    
    def get_interval={
        def result=[:]
        def id = params.id
        def tissueRecoveryBrainInstance= TissueRecoveryBrain.findById(id)
        def d = params.d
        def t =params.t
        def i =params.i
        def interval = '&nbsp;'
        if(t &&d && mbbService.isDate(d)){
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            def collectionDate=df.parse(d + " " + t )
            def removedTime=mbbService.brainRemovedDateTime(tissueRecoveryBrainInstance.caseRecord)
            interval = bpvWorkSheetService.calculateInterval(removedTime, collectionDate)
        }
        //  println("i: " + i + "  id: " + params.id + " d: " + params.d + " t: " + params.t + " interval: " + interval)
        result.put("in_id", "in_" +i)
        result.put("interval", interval)
        render result as JSON
    }
      
    
    def export ={
        def tissueRecoveryBrainInstance = TissueRecoveryBrain.get(params.id)
        def case_id = tissueRecoveryBrainInstance?.caseRecord.caseId
        response.setContentType("application/excel")
        response.setHeader("Content-disposition", "attachment;filename=" + case_id + ".xls")
        def file =  mbbService.exportForm(tissueRecoveryBrainInstance, response.outputStream)
         
    
    }
}
