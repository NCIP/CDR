package nci.obbr.cahub.forms.gtex
import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.util.FileUpload

import grails.converters.JSON

class BrainBankFeedbackController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def mbbService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [brainBankFeedbackInstanceList: BrainBankFeedback.list(params), brainBankFeedbackInstanceTotal: BrainBankFeedback.count()]
    }

    def create = {
        
        def brainBankFeedbackInstance
        
                
        try{
            brainBankFeedbackInstance=BrainBankFeedback.findByCaseRecord(CaseRecord.get(params.id))
            if(!brainBankFeedbackInstance){
              
                brainBankFeedbackInstance= new BrainBankFeedback(params)
                brainBankFeedbackInstance.save(failOnError:true)
                mbbService.feedbackStructCreate(brainBankFeedbackInstance)
                //mbbService.feedbackDamageCreate(brainBankFeedbackInstance)
            }
           
            
        }
        catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
        
        
        def mslist=  BrainStructures.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        def showmslist=false
        def anyStructselect=false
        mslist.each{
                
            if(it.userInput){
                //if( it.userInput){
                showmslist=true
                anyStructselect=true
            }
            if(it.preSelected){
                anyStructselect=true 
            }
            
        }
        // pslist is used to show only the preselected structures in the checkbox
        def pslist =BrainStructures.findAllByBrainbankfeedbackAndUserInput(brainBankFeedbackInstance,false)
        
        def damlist=  BrainDamage.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        def showgi=false // gross inspection list
        def showhp=false // histopathological eval list
        damlist.each{
            if(it.studyType.equals("histopathological evaluation")){
                showhp=true
            }
            if(it.studyType.equals("gross inspection")){
                showgi=true
            }
            
        }
        
        redirect(action: "edit", params: [id: brainBankFeedbackInstance.id, typ: "cr"])
    }

  

    def showpage = ""
    def show = {
        showpage = "yes"
        edit()
    }


    def edit = {
        def brainBankFeedbackInstance = BrainBankFeedback.get(params.id)
        def canSubmit="no"
        if (!brainBankFeedbackInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'brainBankFeedback.label', default: 'BrainBankFeedback'), params.id])}"
            //redirect(action: "list")
            redirect(controller:"home", action: "mbbhome")
        }
        else {
            
            
            def mslist=  BrainStructures.findAllByBrainbankfeedback(brainBankFeedbackInstance)
            def showmslist=false
            def anyStructselect=false
           
            mslist.each{
                
                if(it.userInput){
                    //if( it.userInput){
                    showmslist=true
                    anyStructselect=true
                }
                if(it.preSelected){
                    anyStructselect=true 
                }
                
            
            }
            def pslist =BrainStructures.findAllByBrainbankfeedbackAndUserInput(brainBankFeedbackInstance,false)
           
            def damlist=  BrainDamage.findAllByBrainbankfeedback(brainBankFeedbackInstance)
            def showgi=false // gross inspection list
            def showhp=false // histopathological eval list
            def hpComments=false
            def damComments=false
            damlist.each{
                if(it.studyType.equals("histopathological evaluation")){
                    showhp=true
                    if(!it.region || !it.observation){
                        hpComments=true
                    }
                }
                if(it.studyType.equals("gross inspection")){
                    showgi=true
                    if(!it.region || !it.observation){
                        damComments=true
                    }
                }
                
            
            }
            if (params.typ != "cr" && showpage != "yes") {
                def errorMap = checkError(brainBankFeedbackInstance,anyStructselect)
                if(hpComments){
                    errorMap.put('hpComments', 'Please enter a valid histopathological observation and region. ')
                }
                if(damComments){
                    errorMap.put('damComments', 'Please enter a valid  gross observation and region. ')
                }
                errorMap.each() {key, value ->

                    brainBankFeedbackInstance.errors.rejectValue(key, value)
                }

                if (errorMap.size() == 0) {
                    canSubmit = 'Yes'
                }
            }
            return [brainBankFeedbackInstance: brainBankFeedbackInstance,mslist:mslist.sort { it.id },damlist:damlist.sort { it.id },pslist:pslist,canSubmit:canSubmit,showmslist:showmslist,showhp:showhp,showgi:showgi]
        }
    }
    
    def resumeEditing = {
       
        def brainBankFeedbackInstance = BrainBankFeedback.get(params.id)
       
        brainBankFeedbackInstance.dateSubmitted = null
        brainBankFeedbackInstance.submittedBy = null
        if (brainBankFeedbackInstance.save(flush: true)) {
            // def mslist=  BrainStructures.findAllByBrainbankfeedback(brainBankFeedbackInstance)
            
            redirect(action: "edit", id: brainBankFeedbackInstance.id)
        }
        else {
            render(view: "show", model: [brainBankFeedbackInstance: brainBankFeedbackInstance])
        }
    }

    def save = {
       
        //  params.each{key,value->
        // println "from save key: ${key}   value:${value}"
        // }
        def brainBankFeedbackInstance = BrainBankFeedback.get(params.id)
        def errorMap
        def canSubmit="no"
        def updateStructStatus
        def updateDamageStatus
        def hpComments=false
        def damComments=false
        //if structure and damage need to be added .. go ahead
        updateStructStatus= mbbService.feedbackStructUpdate(brainBankFeedbackInstance,params)
        updateDamageStatus= mbbService.feedbackDamageUpdate(brainBankFeedbackInstance,params)
       
        def mslist=  BrainStructures.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        def showmslist=false
        def anyStructselect=false
        mslist.each{
                
            if(it.userInput){
                //if( it.userInput){
                showmslist=true
                anyStructselect=true
            }
            if(it.preSelected){
                anyStructselect=true 
            }
            
        }
        
        def pslist =BrainStructures.findAllByBrainbankfeedbackAndUserInput(brainBankFeedbackInstance,false)
        
        def damlist=  BrainDamage.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        def showgi=false // gross inspection list
        def showhp=false // histopathological eval list
        damlist.each{
            if(it.studyType.equals("histopathological evaluation")){
                showhp=true
                if(!it.region || !it.observation){
                    hpComments=true
                }
            }
            if(it.studyType.equals("gross inspection") ){
                showgi=true
                if(!it.region || !it.observation){
                    damComments=true
                }
            }
            
        }
        
        if (brainBankFeedbackInstance) {
           
            if (params.version) {
                def version = params.version.toLong()
                if (brainBankFeedbackInstance.version > version) {
                    
                    brainBankFeedbackInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'brainBankFeedback.label', default: 'BrainBankFeedback')] as Object[], "Another user has updated this BrainBankFeedback while you were editing")
                    render(view: "edit", model: [brainBankFeedbackInstance: brainBankFeedbackInstance,mslist:mslist.sort { it.id },damlist:damlist.sort { it.id },pslist:pslist,showmslist:showmslist,showgi:showgi,showhp:showhp])
                    return
                }
            }
           
            brainBankFeedbackInstance.properties = params
            
            
            if( params.vessel != "json"){
                def uploadedFile = request.getFile("fileName")
                def originalFileName = uploadedFile?.originalFilename?.toLowerCase()
                def fileId

                if(originalFileName){
                    if ( (! originalFileName.toString().endsWith(".pdf")) &&
                        (! originalFileName.toString().endsWith(".zip")) &&
                        (! originalFileName.toString().endsWith(".doc")) &&
                        (! originalFileName.toString().endsWith(".docx"))) {


                        brainBankFeedbackInstance.uploadedFile=originalFileName               
                    } 
                    else{

                        fileId=mbbService.uploadNeuroReport(brainBankFeedbackInstance, request) 

                        brainBankFeedbackInstance.uploadedFile= fileId

                    }
                }
                else{
                    if(brainBankFeedbackInstance.uploadedFile && !brainBankFeedbackInstance.uploadedFile.isInteger()){

                        brainBankFeedbackInstance.uploadedFile=null

                    }
                }
            }
            
           
            if(brainBankFeedbackInstance.acceptForFurtherProc=='Yes'){
                brainBankFeedbackInstance.noLeftHemisProc=false
                brainBankFeedbackInstance.noRightHemisProc=false
                brainBankFeedbackInstance.wholeBrainNoProc=false
            }
            else{
                
                if(brainBankFeedbackInstance.noLeftHemisProcReason){
                    brainBankFeedbackInstance.noLeftHemisProc=true
                }
                else{
                    brainBankFeedbackInstance.noLeftHemisProc=false 
                }
                if(brainBankFeedbackInstance.noRightHemisProcReason){
                    brainBankFeedbackInstance.noRightHemisProc=true
                }
                else{
                    brainBankFeedbackInstance.noRightHemisProc=false
                }
                if(brainBankFeedbackInstance.wholeBrainNoProcReason){
                    brainBankFeedbackInstance.wholeBrainNoProc=true
                }
                else{
                    brainBankFeedbackInstance.wholeBrainNoProc=false
                }
            }
            if (brainBankFeedbackInstance.wasBrainProcImmed.equals("Yes"))  {
                brainBankFeedbackInstance.storedImmed=null
                brainBankFeedbackInstance.storedImmedType=null
                brainBankFeedbackInstance.storedInComments=null
            
            }
            
            
            if (brainBankFeedbackInstance.storedImmed.equals("No"))  {
                               
                if(brainBankFeedbackInstance.storedImmedType.equals("-80")){
                    brainBankFeedbackInstance.storedInComments=null
                }
                brainBankFeedbackInstance.storedImmedType=null
            
            }
            
            
            // do not go to error check for interim saves of structure or damage. Not required
            //feedbackstatus set to true for first time save of form
            if(!updateStructStatus && !updateDamageStatus ){
                brainBankFeedbackInstance.feedbackStarted=true
            }
            
            
            if(brainBankFeedbackInstance.feedbackStarted==true ){  
                errorMap= checkError(brainBankFeedbackInstance,anyStructselect)
                
            }
            
            if(hpComments){
                errorMap.put('hpComments', 'Please enter a valid histopathological observation and region. ')
            }
            if(damComments){
                errorMap.put('damComments', 'Please enter a valid  gross observation and region. ')
            }  
                            
                        
            errorMap?.each() {key, value ->
             
                brainBankFeedbackInstance.errors.rejectValue(key, value)
            }
            if (errorMap?.size() == 0) {
                canSubmit = 'Yes'
            }
                
            brainBankFeedbackInstance.save(flush: true) 
            def resultsMap = [:]
            def latestEntries = []
            if(damlist.size() > 0){
                latestEntries.add(damlist.sort{ it.id }.last())
            } else {
                latestEntries.add(null)
            }
            if(mslist.size() > 0){
                latestEntries.add(mslist.sort{ it.id }.last())
            } else {
                latestEntries.add(null)
            }
            resultsMap.put("success",latestEntries)
            if (!brainBankFeedbackInstance.hasErrors()) {               
             
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'brainBankFeedback.label', default: 'Brain Bank Feedback for '), brainBankFeedbackInstance.caseRecord.caseId])}"
                if( params.vessel != "json"){            
                    redirect(action: "edit", id:brainBankFeedbackInstance.id)
                } else {
                    if(params.callback) {
                        render "${params.callback.encodeAsURL()}(${resultsMap as JSON})"
                    } else {
                        render resultsMap as JSON
                    }  
                }            
            }
            else{
                if( params.vessel != "json"){
                    render(view: "edit", model: [brainBankFeedbackInstance: brainBankFeedbackInstance,mslist:mslist.sort { it.id },damlist:damlist.sort { it.id },pslist:pslist,canSubmit:canSubmit,showmslist:showmslist,showgi:showgi,showhp:showhp])
                } else {
                    if(params.callback) {
                        render "${params.callback.encodeAsURL()}(${resultsMap as JSON})"
                    } else {
                        render resultsMap as JSON
                    }  
                }
            }
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'brainBankFeedback.label', default: 'BrainBankFeedback'), params.id])}"
          
            redirect(controller:"home", action: "mbbhome")
        }
    }
    
    
    def submit = {
        
        def brainBankFeedbackInstance = BrainBankFeedback.get(params.id)
        def mslist=  BrainStructures.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        def showmslist=false
        def anyStructselect=false
        mslist.each{
                
            if(it.userInput){
                //if( it.userInput){
                showmslist=true
                anyStructselect=true
            }
            if(it.preSelected){
                anyStructselect=true 
            }
            
        }
        def pslist =BrainStructures.findAllByBrainbankfeedbackAndUserInput(brainBankFeedbackInstance,false)
        
        def damlist=  BrainDamage.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        def showgi=false // gross inspection list
        def showhp=false // histopathological eval list
        
        def hpComments=false
        def damComments=false
        damlist.each{
            if(it.studyType.equals("histopathological evaluation")){
                showhp=true
                if(!it.region || !it.observation){
                    hpComments=true
                }
            }
            if(it.studyType.equals("gross inspection")){
                showgi=true
                if(!it.region || !it.observation){
                    damComments=true
                }
            }
            
        }                
        
        if (brainBankFeedbackInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (brainBankFeedbackInstance.version > version) {
                    
                    brainBankFeedbackInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'brainBankFeedbackInstance.label', default: 'brainBankFeedbackInstance')] as Object[], "Another user has updated this Patient while you were editing")
                    
                    render(view: "edit", model: [brainBankFeedbackInstance: brainBankFeedbackInstance,mslist:mslist.sort { it.id },damlist:damlist.sort { it.id },pslist:pslist,showmslist:showmslist,showgi:showgi,showhp:showhp])
                    return
                }
            }
            brainBankFeedbackInstance.properties = params
            
            
            def uploadedFile = request.getFile("fileName")
            def originalFileName = uploadedFile?.originalFilename?.toLowerCase()
            def fileId
            
            if(originalFileName){
                if ( (! originalFileName.toString().endsWith(".pdf")) &&
                    (! originalFileName.toString().endsWith(".zip")) &&
                    (! originalFileName.toString().endsWith(".doc")) &&
                    (! originalFileName.toString().endsWith(".docx"))) {
                      
                    
                    brainBankFeedbackInstance.uploadedFile=originalFileName               
                } 
                else{
                 
                    fileId=mbbService.uploadNeuroReport(brainBankFeedbackInstance, request) 
                
                    brainBankFeedbackInstance.uploadedFile= fileId
                
                }
            }
            else{
                 if(brainBankFeedbackInstance.uploadedFile && !brainBankFeedbackInstance.uploadedFile.isInteger()){
                    
                    brainBankFeedbackInstance.uploadedFile=null
                
                }
            }
            
            
            
            if(brainBankFeedbackInstance.acceptForFurtherProc=='Yes'){
                brainBankFeedbackInstance.noLeftHemisProc=false
                brainBankFeedbackInstance.noRightHemisProc=false
                brainBankFeedbackInstance.wholeBrainNoProc=false
            }
            else{
                if(brainBankFeedbackInstance.noLeftHemisProcReason){
                    brainBankFeedbackInstance.noLeftHemisProc=true
                }
                else{
                    brainBankFeedbackInstance.noLeftHemisProc=false 
                }
                if(brainBankFeedbackInstance.noRightHemisProcReason){
                    brainBankFeedbackInstance.noRightHemisProc=true
                }
                else{
                    brainBankFeedbackInstance.noRightHemisProc=false
                }
                if(brainBankFeedbackInstance.wholeBrainNoProcReason){
                    brainBankFeedbackInstance.wholeBrainNoProc=true
                }
                else{
                    brainBankFeedbackInstance.wholeBrainNoProc=false
                }
            }
            
            if (brainBankFeedbackInstance.wasBrainProcImmed.equals("Yes"))  {
                brainBankFeedbackInstance.storedImmed=null
                brainBankFeedbackInstance.storedImmedType=null
                brainBankFeedbackInstance.storedInComments=null
            
            }
            
             
            if (brainBankFeedbackInstance.storedImmed.equals("No"))  {
                               
                if(brainBankFeedbackInstance.storedImmedType.equals("-80")){
                    brainBankFeedbackInstance.storedInComments=null
                }
                brainBankFeedbackInstance.storedImmedType=null
            
            }
             
            brainBankFeedbackInstance.save(flush:true)
            def errorMap = checkError(brainBankFeedbackInstance,anyStructselect)
         
            
            if(hpComments){
                errorMap.put('hpComments', 'Please enter a valid histopathological observation and region. ')
            }
            if(damComments){
                errorMap.put('damComments', 'Please enter a valid  gross observation and region. ')
            }   
            
            
            errorMap.each() {key, value ->              
                brainBankFeedbackInstance.errors.rejectValue(key, value)
            }
            
            if (!brainBankFeedbackInstance.hasErrors() ) {
                                
                brainBankFeedbackInstance.dateSubmitted = new Date()
                brainBankFeedbackInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                
                    
                //redirect(action: "mbbhome", controller:"home")
                if (session.org?.code == 'OBBR') {
                    redirect(controller: 'caseRecord', action: "display", params: [id:brainBankFeedbackInstance.caseRecord.id])
                }
                else{
                    redirect(action: "mbbhome", controller:"home")
                }               
                             
            }
            else {
                
                render(view: "edit", model: [brainBankFeedbackInstance: brainBankFeedbackInstance,mslist:mslist.sort { it.id },damlist:damlist.sort { it.id },pslist:pslist,showmslist:showmslist,showgi:showgi,showhp:showhp])
            }
        }
        else {
            flash.message = "Feedback not found"
            //redirect(action: "list")
            redirect(action: "mbbhome", controller:"home")
        }
    }
    
    def removeFile = {
         
        def resultsMap = [:]
        def brainBankFeedbackInstance = BrainBankFeedback.get(params.id)
        
        def fileUploadInstance = FileUpload.get(brainBankFeedbackInstance.uploadedFile)
        def convertedFilePath = fileUploadInstance.filePath + File.separator + fileUploadInstance.fileName
        def file = new File(convertedFilePath)
        resultsMap.put("success", "fail")
        try {
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the file")
                } else {
                    resultsMap.put("success", "yes")
                }
            }
            brainBankFeedbackInstance.uploadedFile=null
            brainBankFeedbackInstance.save(flush: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        //redirect(controller: 'caseRecord', action: "display", params: [id:fileUploadInstance.caseRecord.id])
        //In case there is no caseRecord for old data
        //redirect(action: "edit", id:brainBankFeedbackInstance.id)

        if(params.callback) {
            render "${params.callback.encodeAsURL()}(${resultsMap as JSON})"
        } else {
            render resultsMap as JSON
        }   
    }
    
    
    def removeStructure = {
        
        def resultsMap = [:]
        resultsMap.put("structDel", "fail")
        //println " trying to remove structure "+params.sid
        def brainBankStructInstance = BrainStructures.get(params.id)
        
        
        try {
            
            brainBankStructInstance?.delete()
            
            resultsMap.put("structDel", "yes")
            resultsMap.put("delid", params.id)
            
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        //redirect(controller: 'caseRecord', action: "display", params: [id:fileUploadInstance.caseRecord.id])
        //In case there is no caseRecord for old data
        // redirect(action: "edit", id:params.id)
       
        if(params.callback) {
            render "${params.callback.encodeAsURL()}(${resultsMap as JSON})"
        } else {
            render resultsMap as JSON
        }   
    }
    
    def removeDamage = {
         
        
        def resultsMap = [:]
        resultsMap.put("damDel", "fail")
        def brainBankDamInstance = BrainDamage.get(params.id)
        
        
        try {
            
            brainBankDamInstance?.delete()
            resultsMap.put("damDel", "yes")
            resultsMap.put("delid", params.id)
            
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        //redirect(controller: 'caseRecord', action: "display", params: [id:fileUploadInstance.caseRecord.id])
        //In case there is no caseRecord for old data
        // redirect(action: "edit", id:params.id)
       
        if(params.callback) {
            render "${params.callback.encodeAsURL()}(${resultsMap as JSON})"
        } else {
            render resultsMap as JSON
        }   
    }

    Map checkError(brainBankFeedbackInstance,mslist) {
        def errorMap = [:]    
       
        if(brainBankFeedbackInstance.uploadedFile && !brainBankFeedbackInstance.uploadedFile.isInteger()){
            
            errorMap.put('uploadedFile', 'You can only upload a pdf, zip, doc or docx file type.  Please choose the right file to upload. ')
                
        }     
                
        if (!brainBankFeedbackInstance.contentTempOnArr ) {
            errorMap.put('contentTempOnArr', 'Please enter temperature of contents upon arrival')
        }
        
        if (brainBankFeedbackInstance.contentTempOnArr.equals("other") && !brainBankFeedbackInstance.comments ) {
            errorMap.put('comments', 'You have mentioned \'Other\' for Temperature of contents upon arrival. Please describe in the comments section')
        }
       
        
        if (!brainBankFeedbackInstance.wholeBrainWt || !brainBankFeedbackInstance.wholeBrainWt.isFloat()) {
            errorMap.put('wholeBrainWt', 'Please enter a valid number as whole brain weight')
            
        }
        
        if (!brainBankFeedbackInstance.acceptForFurtherProc ) {
            errorMap.put('acceptForFurtherProc', 'Please enter reason if partial/whole brain and brain structures are acceptable upon gross inspection')
            
        }
        if (!brainBankFeedbackInstance.missingStructExists ) {
            errorMap.put('missingStructExists', 'Please enter if there are any missing structures')
            
        }
        if (brainBankFeedbackInstance.missingStructExists=='Yes' && mslist==false) {
            errorMap.put('missingStructExists', 'Please select or add missing structures')
            
        }
        if (!brainBankFeedbackInstance.wasBrainProcImmed ) {
            errorMap.put('wasBrainProcImmed', 'Please enter if Brain was processed immediately')
            
        }
        if (brainBankFeedbackInstance.wasBrainProcImmed.equals("No") && !brainBankFeedbackInstance.storedImmed ) {
            errorMap.put('storedImmed', 'Please check if Brain was stored immediately')
            
        }
        
        
        if (brainBankFeedbackInstance.wasBrainProcImmed.equals("Yes") && (!brainBankFeedbackInstance.processDate || !brainBankFeedbackInstance.processedBy)) {
            //println brainBankFeedbackInstance.wasBrainProcImmed+' is brainBankFeedbackInstance.wasBrainProcImmed'
            errorMap.put('wasBrainProcImmed', 'Please enter Brain processing information ')
            
        }
        
        if (brainBankFeedbackInstance.processDate && !brainBankFeedbackInstance.processedBy) {
            //println brainBankFeedbackInstance.wasBrainProcImmed+' is brainBankFeedbackInstance.wasBrainProcImmed'
            errorMap.put('wasBrainProcImmed', 'Please complete Brain processing information ')
            
        }
        
        if (!brainBankFeedbackInstance.processDate && brainBankFeedbackInstance.processedBy) {
            //println brainBankFeedbackInstance.wasBrainProcImmed+' is brainBankFeedbackInstance.wasBrainProcImmed'
            errorMap.put('wasBrainProcImmed', 'Please complete Brain processing information ')
            
        }
        
                
        if (brainBankFeedbackInstance.wasBrainProcImmed.equals("No") && brainBankFeedbackInstance.storedImmed.equals("Yes") && !brainBankFeedbackInstance.storedImmedType ) {
            errorMap.put('storedImmedType', 'Please specify the storage procedure  ')
            
        }
        
        if (brainBankFeedbackInstance.wasBrainProcImmed.equals("No") && brainBankFeedbackInstance.storedImmed.equals("No") && !brainBankFeedbackInstance.storedInComments ) {
            errorMap.put('storedInComments', 'Please specify the storage procedure if not stored immediately ')
            
        }
        if (brainBankFeedbackInstance.wasBrainProcImmed.equals("No") && brainBankFeedbackInstance.storedImmed.equals("Yes") && brainBankFeedbackInstance.storedImmedType =='formalin' && !brainBankFeedbackInstance.storedInComments) {
            errorMap.put('storedInComments', 'Please specify the storage procedure using Formalin in the comments section provided ')
            
        }
        
        if (brainBankFeedbackInstance.wasBrainProcImmed.equals("No") &&  brainBankFeedbackInstance.storedImmed.equals("Yes") && brainBankFeedbackInstance.storedImmedType =='other' && !brainBankFeedbackInstance.storedInComments) {
            errorMap.put('storedInComments', 'Please specify other  storage procedure used in the comments section provided')
            
        }
        
        if ((!brainBankFeedbackInstance.noLeftHemisProcReason && !brainBankFeedbackInstance.wholeBrainNoProcReason && !brainBankFeedbackInstance.noRightHemisProcReason) && brainBankFeedbackInstance.acceptForFurtherProc==false  ) {
            errorMap.put('acceptForFurtherProc', 'Please enter reason if partial/whole brain and brain structures are accepatable upon gross inspection ')
        } 
        
        //if (brainBankFeedbackInstance.storedImmed && !brainBankFeedbackInstance.storedImmedType  ) {
        // errorMap.put('storedImmed', 'Please enter how the Brain was stored ')
        //} 
        
        if (!brainBankFeedbackInstance.qaPerson1 || !brainBankFeedbackInstance.qaDate1) {
            errorMap.put('qaPerson1', 'Please enter name of person completing information and date of completion')
        } 
        
        if (!brainBankFeedbackInstance.qaPerson2 || !brainBankFeedbackInstance.qaDate2 ) {
            errorMap.put('qaPerson2', 'Please enter name of second person verifying information and date of verification')
        }
    
               
        return errorMap
    }
}
    

