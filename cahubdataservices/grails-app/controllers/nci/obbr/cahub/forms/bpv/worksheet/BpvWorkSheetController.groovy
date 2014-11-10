package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.plugins.springsecurity.Secured
import java.text.SimpleDateFormat
import grails.converters.*
import nci.obbr.cahub.staticmembers.Module
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.TumorStatus
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SlideRecord
import groovy.sql.Sql
import nci.obbr.cahub.util.ComputeMethods

class BpvWorkSheetController {
    javax.sql.DataSource dataSource 
    def bpvWorkSheetService
    def accessPrivilegeService
    
    static allowedMethods = [update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvWorkSheetInstanceList: BpvWorkSheet.list(params), bpvWorkSheetInstanceTotal: BpvWorkSheet.count()]
    }

    def create = {
        def bpvWorkSheetInstance = new BpvWorkSheet()
        bpvWorkSheetInstance.properties = params
        return [bpvWorkSheetInstance: bpvWorkSheetInstance]
    }

    def save = {
        def bpvWorkSheetInstance = new BpvWorkSheet(params) 
        try{
            // Populate SOP Record
            def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
            def sopInstance
            if (formMetadataInstance?.sops?.size() > 0) {
                sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
                bpvWorkSheetInstance.formSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
            }
        
            bpvWorkSheetService.saveWorkSeet(bpvWorkSheetInstance)
            flash.message = "${message(code: 'default.created.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, bpvWorkSheetInstance.id])}"
            //redirect(action: "edit", id: tissueRecoveryBmsInstance.id)
            redirect(action: "edit", id: bpvWorkSheetInstance.id)
              
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
            render(view: "create", model: [bpvWorkSheetInstance: bpvWorkSheetInstance])
       
        }
    }

    def show = {
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
        def caseId = caseRecord.caseId

         int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')
           if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
           }        


       // if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')) {
       //     redirect(controller: "login", action: "denied")
        //    return
      //  }
        if (!bpvWorkSheetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, params.id])}"
            redirect(action: "list")
        }
        
        else {
            
         
            def errorMap=[:]
           
            def module1SheetInstance= bpvWorkSheetInstance.module1Sheet
            def intervalMap1 = bpvWorkSheetService.getIntervalMap1(module1SheetInstance)
            
            def module2SheetInstance= bpvWorkSheetInstance.module2Sheet
            def intervalMap2 = bpvWorkSheetService.getIntervalMap2(module2SheetInstance)
            
             def module3NSheetInstance
            def intervalMap3n
            
            def module4NSheetInstance
            def intervalMap4n
            
            
            if(bpvWorkSheetInstance.formVersion == 1){
                module3NSheetInstance= bpvWorkSheetInstance.module3NSheet
                intervalMap3n = bpvWorkSheetService.getIntervalMap3N(module3NSheetInstance)
            
                module4NSheetInstance= bpvWorkSheetInstance.module4NSheet
                 intervalMap4n = bpvWorkSheetService.getIntervalMap4N(module4NSheetInstance)
            }
            
            
            def module5SheetInstance
            def intervalMap5
          
             if(bpvWorkSheetInstance.formVersion == 2){
             module5SheetInstance= bpvWorkSheetInstance.module5Sheet
             intervalMap5 = bpvWorkSheetService.getIntervalMap5(module5SheetInstance)
             }
          
            
            def module3SheetInstance= bpvWorkSheetInstance.module3Sheet
            def module4SheetInstance= bpvWorkSheetInstance.module4Sheet
            
            //def canResume = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            def canResume = (accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit') == 0)
              def warningMap = getWarningMap(intervalMap1, intervalMap2) + getWarningMap2(module1SheetInstance,caseId)
              warningMap += getWarningMap2(module2SheetInstance,caseId)
                if(bpvWorkSheetInstance.formVersion == 1){
                  warningMap += getWarningMap2(module3NSheetInstance,caseId) 
                  warningMap += getWarningMap2(module4NSheetInstance,caseId)
                }
               
             if(bpvWorkSheetInstance.formVersion == 2){
                  warningMap += getWarningMap2(module5SheetInstance,caseId) 
             }
              warningMap += getWarningMap2(module3SheetInstance,caseId)  
              warningMap += getWarningMap2(module4SheetInstance,caseId)
              

           
            [bpvWorkSheetInstance:bpvWorkSheetInstance, module1SheetInstance:module1SheetInstance, module2SheetInstance:module2SheetInstance, module3NSheetInstance:module3NSheetInstance, module4NSheetInstance:module4NSheetInstance, module5SheetInstance:module5SheetInstance, module3SheetInstance:module3SheetInstance, module4SheetInstance:module4SheetInstance, intervalMap1:intervalMap1, intervalMap2:intervalMap2, intervalMap3n:intervalMap3n, intervalMap4n:intervalMap4n, intervalMap5:intervalMap5, warningMap:warningMap, errorMap:errorMap, canResume: canResume]
        }
    
    }
   
    
     def view = {
        render "a view page?"
        
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
        def errorMap=[:]
        //def ms1Instance= bpvWorkSheetInstance.module1Sheet
        //def ms2Instance= bpvWorkSheetInstance.module2Sheet
        //redirect(action:show, ms1:ms1Instance, ms2:ms2Instance)
        
        redirect(action: "show", id:params.id, bpvWorkSheetInstance: bpvWorkSheetInstance, errorMap:errorMap)
        
      
    }

    
    def edit = {
        def errorMap=[:]
        def canSubmit= 'No'
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
        if (!bpvWorkSheetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, params.id])}"
            redirect(action: "list")
        }
        else {
            def caseRecord = bpvWorkSheetInstance?.caseRecord
            //if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit') || bpvWorkSheetInstance.submittedBy != null) {
              //  redirect(controller: "login", action: "denied")
               // return
            //}
             
           int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvWorkSheetInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            

            if(workSheetStarted(bpvWorkSheetInstance)){
                def result= checkError(bpvWorkSheetInstance)
                // println("result size...." + result.size())

                if(result){
                    // println("has result....")
                    result.each(){key,value->
                        // println("key: " + key + "  value: " + value);
                        if(key=="qcAndFrozenSample" || key=="module1Sheet" || key=='module2Sheet' || key=="module3Sheet" || key=="module4Sheet" || key=="module3NSheet" || key=="module4NSheet" || key=="module5Sheet" || key=="priority" || key=="slide_m1" || key=="slide_m2" || key=="slide_m3" || key=="slide_m4" || key=="slide_m5"  || key=="tge_m1" || key=="tge_m2" || key=="tge_m3" ||  key=="tge_m4" || key=="tge_m5" || key=="tge_ett"){
                            errorMap.put(key, "errors")
                            bpvWorkSheetInstance.errors.reject(value, value)
                        }else{
                            bpvWorkSheetInstance.errors.rejectValue(key, value)     
                        }

                    }//each
                }
                  
                if(result.size()==0){
                    canSubmit='Yes'
                }
                  
            }
            return [bpvWorkSheetInstance: bpvWorkSheetInstance, errorMap:errorMap, canSubmit:canSubmit]
        }
    }
    
    def editm = {
        def bpvWorkSheetInstance = BpvWorkSheet.findById(params.id)  
        bpvWorkSheetService.updateWorkSheet(bpvWorkSheetInstance, params)
        def bwiID =bpvWorkSheetInstance.id
        
       
       if(params.editing_module=='MODULE1'){
            redirect(action: "editm1", id: bpvWorkSheetInstance.module1Sheet.id, params:[bwiID:bwiID])
            
        }else if(params.editing_module=='MODULE2'){
            redirect(action: "editm2", id: bpvWorkSheetInstance.module2Sheet.id, params:[bwiID:bwiID])
        }else if(params.editing_module=='MODULE3N'){
            redirect(action: "editm3n", id: bpvWorkSheetInstance.module3NSheet.id, params:[bwiID:bwiID])
        }else if(params.editing_module=='MODULE4N'){
            redirect(action: "editm4n", id: bpvWorkSheetInstance.module4NSheet.id, params:[bwiID:bwiID])
        }else if(params.editing_module=='MODULE3'){
            redirect(action: "editm3", id: bpvWorkSheetInstance.module3Sheet.id, params:[bwiID:bwiID])
        }else if(params.editing_module=='MODULE4'){
            redirect(action: "editm4", id: bpvWorkSheetInstance.module4Sheet.id, params:[bwiID:bwiID])
        }else{
           redirect(action: "editm5", id: bpvWorkSheetInstance.module5Sheet.id, params:[bwiID:bwiID]) 
        }
        
        
    }  
     
        
    def editm1 = {
        
        def module1SheetInstance = Module1Sheet.get(params.id)
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        //def bwi = params.bwiID
        //def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        def bpvWorkSheetInstance=module1SheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
       // if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
       //     redirect(controller: "login", action: "denied")
        //    return
        //}
        
         int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
        
        // end of changes PM 08/02
       
        def intervalMap = bpvWorkSheetService.getIntervalMap1(module1SheetInstance)
        def started = module1Started(module1SheetInstance)
        def errorMap=[:]
         
        if(started){
            def result =checkError12(module1SheetInstance)
            // println("result size: " + result.size())
            if(result){
                result.each(){key,value->
                    // println("it: " + it)
                    if(key == 'whichOvary'  || key=='priority'){
                        module1SheetInstance.errors.rejectValue(key, value)
                    }else{
                        module1SheetInstance.errors.reject(key, value)
                    }
                    
                    errorMap.put(key, "errors")
                }//each
            }
        }
        def warningMap = getWarningMap(intervalMap, [:])
        def warningMap2=getWarningMap2(module1SheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module1SheetInstance: module1SheetInstance, errorMap:errorMap, intervalMap:intervalMap, bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap2]
        
    }
    
    
    def editm2 = {
        def module2SheetInstance = Module2Sheet.get(params.id)
        
        def bpvWorkSheetInstance=module2SheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
       // if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
        //    redirect(controller: "login", action: "denied")
        //    return
      //  }
        
       int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
        
        
        def intervalMap = bpvWorkSheetService.getIntervalMap2(module2SheetInstance)
        def started = module2Started(module2SheetInstance)
        def errorMap=[:]
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        // def bwi = params.bwiID
        // def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        // end of changes PM 08/02
       
        
        
        if(started){
            def result =checkError12(module2SheetInstance)
            //println("result size: " + result.size())
            if(result){
                result.each(){key,value->
                    
                    if(key == 'whichOvary'  || key =='priority'){
                        module2SheetInstance.errors.rejectValue(key, value)
                    }else{
                        module2SheetInstance.errors.reject(key, value)
                    }
                    
                    // println("it: " + it)
                    //  module2SheetInstance.errors.reject(key, value)
                    errorMap.put(key, "errors")
                }//each
            }
        }
        
        //  return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap ]
      
        def warningMap = getWarningMap([:], intervalMap)
         def warningMap2=getWarningMap2(module2SheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap, bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap + warningMap2]
        
    }
    
    
    def editm3n = {
        def module3NSheetInstance = Module3NSheet.get(params.id)
        
        def bpvWorkSheetInstance=module3NSheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
      //  if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
       //     redirect(controller: "login", action: "denied")
       //     return
       // }
       
        
         int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
        
        
        
        
        def intervalMap = bpvWorkSheetService.getIntervalMap3N(module3NSheetInstance)
        def started = module3NStarted(module3NSheetInstance)
        def errorMap=[:]
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        // def bwi = params.bwiID
        // def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        // end of changes PM 08/02
       
        
        
        if(started){
            def result =checkError345(module3NSheetInstance)
            //println("result size: " + result.size())
            if(result){
                result.each(){key,value->
                    
                    if(key == 'whichOvary'  || key =='priority'){
                        module3NSheetInstance.errors.rejectValue(key, value)
                    }else{
                        module3NSheetInstance.errors.reject(key, value)
                    }
                    
                    // println("it: " + it)
                    //  module2SheetInstance.errors.reject(key, value)
                    errorMap.put(key, "errors")
                }//each
            }
        }
        
        //  return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap ]
      
        //def warningMap = getWarningMap([:], intervalMap)
   
         def warningMap2=getWarningMap2(module3NSheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module3NSheetInstance: module3NSheetInstance, errorMap:errorMap, intervalMap:intervalMap, bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap2]
        
    }
    
    
    
    def editm4n = {
        def module4NSheetInstance = Module4NSheet.get(params.id)
        
        def bpvWorkSheetInstance=module4NSheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
        //if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
         //   redirect(controller: "login", action: "denied")
         //   return
        //}
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
        
        
        def intervalMap = bpvWorkSheetService.getIntervalMap4N(module4NSheetInstance)
        def started = module4NStarted(module4NSheetInstance)
        def errorMap=[:]
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        // def bwi = params.bwiID
        // def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        // end of changes PM 08/02
       
        
        
        if(started){
            def result =checkError345(module4NSheetInstance)
            //println("result size: " + result.size())
            if(result){
                result.each(){key,value->
                    
                    if(key == 'whichOvary'  || key =='priority'){
                        module4NSheetInstance.errors.rejectValue(key, value)
                    }else{
                        module4NSheetInstance.errors.reject(key, value)
                    }
                    
                    // println("it: " + it)
                    //  module2SheetInstance.errors.reject(key, value)
                    errorMap.put(key, "errors")
                }//each
            }
        }
        
        //  return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap ]
      
        //def warningMap = getWarningMap([:], intervalMap)
    
          def warningMap2=getWarningMap2(module4NSheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module4NSheetInstance: module4NSheetInstance, errorMap:errorMap, intervalMap:intervalMap, bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap2]
        
    }
    
    def editm3 = {
        def module3SheetInstance = Module3Sheet.get(params.id)
        def errorMap=[:]
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        // def bwi = params.bwiID
        // def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        // end of changes PM 08/02
        def bpvWorkSheetInstance=module3SheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
       // if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
         //   redirect(controller: "login", action: "denied")
         //   return
       // }
       
       int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
       
       
        if(module3SheetInstance.version > 0){
            def result =checkError3(module3SheetInstance)
            if(result){
                result.each(){key,value->
                    // println("it: " + it)
                    if(key == 'oneSample'){
                        module3SheetInstance.errors.reject(key, value)
                    }else{
                        module3SheetInstance.errors.rejectValue(key, value)
                    }
                    //errorMap.put(key, "errors")
                }//each
            }
        }
        
        //  return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap ]
      
          def warningMap2=getWarningMap2(module3SheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module3SheetInstance:module3SheetInstance, errorMap:errorMap,  bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap2 ]
        
    }
    
    def editm4 = {
        def module4SheetInstance = Module4Sheet.get(params.id)
        def errorMap=[:]
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        // def bwi = params.bwiID
        // def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        // end of changes PM 08/02
        def bpvWorkSheetInstance=module4SheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
        //if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
         //   redirect(controller: "login", action: "denied")
         //   return
        //}
        
          int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
       
        if(module4SheetInstance.version > 0){
            def result =checkError4(module4SheetInstance)
            if(result){
                result.each(){key,value->
                    // println("it: " + it)
                    if(key == 'oneSample'){
                        module4SheetInstance.errors.reject(key, value)
                    }else{
                        module4SheetInstance.errors.rejectValue(key, value)
                    }
                    //errorMap.put(key, "errors")
                }//each
            }
        }
        
        //  return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap ]
      
      
          def warningMap2=getWarningMap2(module4SheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module4SheetInstance:module4SheetInstance, errorMap:errorMap,  bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap2]
        
    }
    

    def update = {
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
        if (bpvWorkSheetInstance) {
            if(bpvWorkSheetInstance.dateSubmitted){
                redirect(action: "show", id:params.id)
            }else{
            bpvWorkSheetInstance.properties = params
            bpvWorkSheetService.updateWorkSheet(bpvWorkSheetInstance, params)
            
            //pmh new 06/17/13
            bpvWorkSheetService.updateSpecimenTumorStatus(bpvWorkSheetInstance.caseRecord)
            //pmh
            
            flash.message ='Worksheet data saved.'
            redirect(action: "edit", id:params.id)
            }
           
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, params.id])}"
            redirect(action: "list")
        }
    }

    
    def editm5 = {
        def module5SheetInstance = Module5Sheet.get(params.id)
        
        def bpvWorkSheetInstance=module5SheetInstance.bpvWorkSheet
        
        def caseRecord = bpvWorkSheetInstance?.caseRecord
        //if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
         //   redirect(controller: "login", action: "denied")
         //   return
        //}
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
        
        
        def intervalMap = bpvWorkSheetService.getIntervalMap5(module5SheetInstance)
        def started = module5Started(module5SheetInstance)
        def errorMap=[:]
        
        //PMH 08/02 this new param bwi is for passing on the instance to the module page in order to show the caserecord include 
        // def bwi = params.bwiID
        // def bpvWorkSheetInstance = BpvWorkSheet.findById(bwi)
        // end of changes PM 08/02
       
        
        
        if(started){
            def result =checkError345(module5SheetInstance)
            //println("result size: " + result.size())
            if(result){
                result.each(){key,value->
                    
                    if(key == 'whichOvary'  || key =='priority'){
                        module5SheetInstance.errors.rejectValue(key, value)
                    }else{
                        module5SheetInstance.errors.reject(key, value)
                    }
                    
                    // println("it: " + it)
                    //  module2SheetInstance.errors.reject(key, value)
                    errorMap.put(key, "errors")
                }//each
            }
        }
        
        //  return [module2SheetInstance: module2SheetInstance, errorMap:errorMap, intervalMap:intervalMap ]
      
        //def warningMap = getWarningMap([:], intervalMap)
    
          def warningMap2=getWarningMap2(module5SheetInstance, caseRecord.caseId)
        // PMH 08/02 :passing on the  bpvWorkSheetInstance as well for the caserecord include
        return [module5SheetInstance: module5SheetInstance, errorMap:errorMap, intervalMap:intervalMap, bpvWorkSheetInstance:bpvWorkSheetInstance, warningMap:warningMap2]
        
    }
    
    
    
    def update_time={
        def side = params.side
        if(side != 'L' && side != 'R')
        side = null
        
        def result=[:]
        
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        def now = df.parse(params.now)
        def thedate=df.format(now)
        def fieldid=''
        def field=''
       
        
        def index = params.field.indexOf('_')
        // println("params.field:" + params.field + "  index: " + index)
        def id
        if(index > 0){
            id = params.field.substring(index+1)
            field=params.field.substring(0, index)
        }else{
            field = params.field
        }
       
       // println("field: " + field)
        
        if(field=='sampleId4Record'){
            def timepoint = Timepoint.findById(id)
            def old_date= timepoint.dateSampleRecordedStr
            timepoint.sampleId4Record=params.value
            if(!old_date){
                timepoint.dateSampleRecorded=now
                timepoint.dateSampleRecordedStr=thedate
                fieldid='dateSampleRecordedStr_'+id
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
        }else if(field=='sampleId4Fixative'){
            def module=params.module
            def mid = params.mid
            def t0
            
            def moduleSheet = null
            if(module=='1'){
                moduleSheet = Module1Sheet.findById(mid)
            }else if (module=='2'){
                moduleSheet=Module2Sheet.findById(mid)
            }else if (module=='3n'){
                moduleSheet= Module3NSheet.findById(mid)
            }else if(module=='4n'){
                moduleSheet= Module4NSheet.findById(mid)
            }else{
                 moduleSheet= Module5Sheet.findById(mid)
            }
            
            if(moduleSheet){
                def bpvWorkSheet=moduleSheet.bpvWorkSheet
                if(!side)
                    side = bpvWorkSheetService.getSideFromModule(moduleSheet)
                if(!side){
                    t0=null
                }else{
                    t0=bpvWorkSheetService.getT0(bpvWorkSheet, side)
                }
            }
            
            
            def timepoint = Timepoint.findById(id) 
            def old_date= timepoint.dateSampleInFixativeStr
            timepoint.sampleId4Fixative=params.value
            if(!old_date){
                timepoint.dateSampleInFixative=now
                timepoint.dateSampleInFixativeStr=thedate
                fieldid='dateSampleInFixativeStr_'+id
                def interval1=bpvWorkSheetService.calculateInterval(t0, now)
                //  println("interval1: " + interval1)
                result.put("interval1", interval1)
                result.put("in1id", "in1_"+id)
               
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
        }else if(field=='sampleId4Proc'){
            
            def timepoint = Timepoint.findById(id) 
            def old_date= timepoint.dateSampleInProcStr
            timepoint.sampleId4Proc=params.value
            if(!old_date){
                timepoint.dateSampleInProc=now
                timepoint.dateSampleInProcStr=thedate
                fieldid='dateSampleInProcStr_'+id
                def t2=params.t2
                def interval2 =bpvWorkSheetService.getInterval7H(thedate, t2)
                result.put("interval2", interval2)
                result.put("in2id", "in2_"+id)
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
            
        }else if(field=='sampleId4Removal'){
            
            
            def timepoint = Timepoint.findById(id) 
            def old_date= timepoint.dateSampleRemovedStr
            timepoint.sampleId4Removal=params.value
            if(!old_date){
                timepoint.dateSampleRemoved=now
                timepoint.dateSampleRemovedStr=thedate
                fieldid='dateSampleRemovedStr_'+id
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
            
            
            
        }else if(field=='sampleId4Embedding'){
            
             
            def timepoint = Timepoint.findById(id) 
            def old_date= timepoint.dateSampleEmbStartedStr
            timepoint.sampleId4Embedding=params.value
            if(!old_date){
                timepoint.dateSampleEmbStarted=now
                timepoint.dateSampleEmbStartedStr=thedate
                fieldid='dateSampleEmbStartedStr_'+id
                def t2=params.t2
                def interval3 =bpvWorkSheetService.getInterval(t2, thedate)
                result.put("interval3", interval3)
                result.put("in3id", "in3_"+id)
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
            
        
        
        }else if(field=='sampleId4Frozen'){
            //println("sampleIdFrozen: " + id)
            def timepoint = Timepoint.findById(id) 
            def old_date= timepoint.dateSampleFrozenStr
            timepoint.sampleId4Frozen=params.value
            if(!old_date){
                timepoint.dateSampleFrozen=now
                timepoint.dateSampleFrozenStr=thedate
                fieldid='dateSampleFrozenStr_'+id
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
     
            
        }else if(field=='sampleId4Trans'){
           // println("sampleId4Trans: " + id)
            def timepoint = Timepoint.findById(id) 
            def old_date= timepoint.dateSampleTransStr
            timepoint.sampleId4Trans=params.value
            if(!old_date){
                timepoint.dateSampleTrans=now
                timepoint.dateSampleTransStr=thedate
                fieldid='dateSampleTransStr_'+id
                
            }else{
                thedate = old_date
            }
            timepoint.save(failOnError:true)
            
     
            
        }else if (field=='experimentId'){
       
            def bpvWorkSheet = BpvWorkSheet.findById(id) 
            def old_date= bpvWorkSheet.dateEidRecordedStr
            bpvWorkSheet.experimentId=params.value
            if(!old_date){
                bpvWorkSheet.dateEidRecorded=now
                bpvWorkSheet.dateEidRecordedStr=thedate
                fieldid='dateEidRecordedStr'
                
            }else{
                thedate = old_date
            }
            bpvWorkSheet.save(failOnError:true)
            
        
        
        }else if(field=='ntFfpeId1'){
            def module3Sheet = Module3Sheet.findById(params.mid)
            def old_date = module3Sheet.ntFfpeTimeStr1
            module3Sheet.ntFfpeId1=params.value
            if(!old_date){
                module3Sheet.ntFfpeTime1=now
                module3Sheet.ntFfpeTimeStr1 = thedate
            }else{
                thedate = old_date
            }
            module3Sheet.save(failOnError:true)
            fieldid='ntFfpeTimeStr1'
        }else if(field=='ntFfpeId2'){
            def module3Sheet = Module3Sheet.findById(params.mid)
            def old_date = module3Sheet.ntFfpeTimeStr2
            module3Sheet.ntFfpeId2=params.value
            if(!old_date){
                module3Sheet.ntFfpeTime2=now
                module3Sheet.ntFfpeTimeStr2 = thedate
            }else{
                thedate = old_date
            }
            module3Sheet.save(failOnError:true)
            fieldid='ntFfpeTimeStr2'
        }else if(field=='ntFfpeId3'){
            def module3Sheet = Module3Sheet.findById(params.mid)
            def old_date = module3Sheet.ntFfpeTimeStr3
            module3Sheet.ntFfpeId3=params.value
            if(!old_date){
                module3Sheet.ntFfpeTime3=now
                module3Sheet.ntFfpeTimeStr3 = thedate
            }else{
                thedate = old_date
            }
            module3Sheet.save(failOnError:true)
            fieldid='ntFfpeTimeStr3'
        }else if(field=='ntFrozenId1'){
            def module3Sheet = Module3Sheet.findById(params.mid)
            def old_date = module3Sheet.ntFrozenTimeStr1
            module3Sheet.ntFrozenId1=params.value
            if(!old_date){
                module3Sheet.ntFrozenTime1=now
                module3Sheet.ntFrozenTimeStr1= thedate
            }else{
                thedate = old_date
            }
            module3Sheet.save(failOnError:true)
            fieldid='ntFrozenTimeStr1'
        }else if(field=='ntFrozenId2'){
            def module3Sheet = Module3Sheet.findById(params.mid)
            def old_date = module3Sheet.ntFrozenTimeStr2
            module3Sheet.ntFrozenId2=params.value
            if(!old_date){
                module3Sheet.ntFrozenTime2=now
                module3Sheet.ntFrozenTimeStr2= thedate
            }else{
                thedate = old_date
            }
            module3Sheet.save(failOnError:true)
            fieldid='ntFrozenTimeStr2'
        }else if(field=='ntFrozenId3'){
            def module3Sheet = Module3Sheet.findById(params.mid)
            def old_date = module3Sheet.ntFrozenTimeStr3
            module3Sheet.ntFrozenId3=params.value
            if(!old_date){
                module3Sheet.ntFrozenTime3=now
                module3Sheet.ntFrozenTimeStr3= thedate
            }else{
                thedate = old_date
            }
            module3Sheet.save(failOnError:true)
            fieldid='ntFrozenTimeStr3'
        }else if(field=='ttFfpeId1'){
            def module4Sheet = Module4Sheet.findById(params.mid)
            def old_date = module4Sheet.ttFfpeTimeStr1
            module4Sheet.ttFfpeId1=params.value
            if(!old_date){
                module4Sheet.ttFfpeTime1=now
                module4Sheet.ttFfpeTimeStr1 = thedate
            }else{
                thedate = old_date
            }
            module4Sheet.save(failOnError:true)
            fieldid='ttFfpeTimeStr1'
        }else if(field=='ttFfpeId2'){
            def module4Sheet = Module4Sheet.findById(params.mid)
            def old_date = module4Sheet.ttFfpeTimeStr2
            module4Sheet.ttFfpeId2=params.value
            if(!old_date){
                module4Sheet.ttFfpeTime2=now
                module4Sheet.ttFfpeTimeStr2 = thedate
            }else{
                thedate = old_date
            }
            module4Sheet.save(failOnError:true)
            fieldid='ttFfpeTimeStr2'
        }else if(field=='ttFfpeId3'){
            def module4Sheet = Module4Sheet.findById(params.mid)
            def old_date = module4Sheet.ttFfpeTimeStr3
            module4Sheet.ttFfpeId3=params.value
            if(!old_date){
                module4Sheet.ttFfpeTime3=now
                module4Sheet.ttFfpeTimeStr3 = thedate
            }else{
                thedate = old_date
            }
            module4Sheet.save(failOnError:true)
            fieldid='ttFfpeTimeStr3'
        }else if(field=='ttFrozenId1'){
            def module4Sheet = Module4Sheet.findById(params.mid)
            def old_date = module4Sheet.ttFrozenTimeStr1
            module4Sheet.ttFrozenId1=params.value
            if(!old_date){
                module4Sheet.ttFrozenTime1=now
                module4Sheet.ttFrozenTimeStr1= thedate
            }else{
                thedate = old_date
            }
            module4Sheet.save(failOnError:true)
            fieldid='ttFrozenTimeStr1'
        }else if(field=='ttFrozenId2'){
            def module4Sheet = Module4Sheet.findById(params.mid)
            def old_date = module4Sheet.ttFrozenTimeStr2
            module4Sheet.ttFrozenId2=params.value
            if(!old_date){
                module4Sheet.ttFrozenTime2=now
                module4Sheet.ttFrozenTimeStr2= thedate
            }else{
                thedate = old_date
            }
            module4Sheet.save(failOnError:true)
            fieldid='ttFrozenTimeStr2'
        }else if(field=='ttFrozenId3'){
            def module4Sheet = Module4Sheet.findById(params.mid)
            def old_date = module4Sheet.ttFrozenTimeStr3
            module4Sheet.ttFrozenId3=params.value
            if(!old_date){
                module4Sheet.ttFrozenTime3=now
                module4Sheet.ttFrozenTimeStr3= thedate
            }else{
                thedate = old_date
            }
            module4Sheet.save(failOnError:true)
            fieldid='ttFrozenTimeStr3'
        }else{
            
        }
         
        result.put("thedate", thedate)
        result.put("fieldid", fieldid)
        render result as JSON
        
    }
    
    
    
     
  
    
    
    def get_interval={
        // println("params.side:" + params.side)
        def side = params.side
        if(side != 'L' && side != 'R')
         side = null
        def result=[:]
        
        def index = params.field.indexOf('_')
       
        def id = params.field.substring(index+1)
        def field=params.field.substring(0, index)
        //println("field: " + field)
        if(field=='dateSampleInFixativeStr'){
            def module=params.module
            def mid = params.mid
            def t0
            
              def moduleSheet = null
            if(module=='1'){
                moduleSheet = Module1Sheet.findById(mid)
            }else if (module=='2'){
                moduleSheet=Module2Sheet.findById(mid)
            }else if (module=='3n'){
                moduleSheet= Module3NSheet.findById(mid)
            }else if(module=='4n'){
                moduleSheet= Module4NSheet.findById(mid)
            }else{
                
            }
            
            if(moduleSheet){
               // println("module not null")
                def bpvWorkSheet=moduleSheet.bpvWorkSheet
                if(!side)
                   side = bpvWorkSheetService.getSideFromModule(moduleSheet)
                if(!side){
                    t0=""
                }else{
                    t0=bpvWorkSheetService.getT0Str(bpvWorkSheet, side)
                }
            }
            
           
            // println("t0 from db?????: " + t0)
            def interval1=bpvWorkSheetService.getInterval(t0, params.value)
            // println("interval1: " + interval1)
            result.put("interval1", interval1)
            result.put("in1id", "in1_"+id)
            
            def t2=params.t2
            def interval2 =bpvWorkSheetService.getInterval7H(params.value, t2)
            result.put("interval2", interval2)
            result.put("in2id", "in2_"+id) 
          
                
        }
        
                                                                        
           
        if(field=='dateSampleProcEndStr'){
            // def t4=params.t4
            def t1=params.t1
            //def interval2 =bpvWorkSheetService.getInterval7H(t4, params.value)
            def interval2 =bpvWorkSheetService.getInterval7H(t1, params.value)
            // println("interval2: " + interval2)
            result.put("interval2", interval2)
            result.put("in2id", "in2_"+id) 
            def t3=params.t3
            def interval3 =bpvWorkSheetService.getInterval(params.value, t3)
            result.put("interval3", interval3)
            result.put("in3id", "in3_"+id)
               
        }
           
        if(field=='dateSampleEmbStartedStr'){
            def t2=params.t2
            def interval3 =bpvWorkSheetService.getInterval(t2, params.value)
            result.put("interval3", interval3)
            result.put("in3id", "in3_"+id)
               
               
        }
          
        render result as JSON
    }
            
            
    
    def updatem1 = {
        def module1SheetInstance = Module1Sheet.get(params.id)
        
        module1SheetInstance.properties = params
        bpvWorkSheetService.updateModule1(module1SheetInstance, params)
        flash.message ='Module I data saved.'
        redirect(action: "editm1", id:params.id)
           
      
    }
    
    def updatem2 = {
        def module2SheetInstance = Module2Sheet.get(params.id)
        
        module2SheetInstance.properties = params
        bpvWorkSheetService.updateModule2(module2SheetInstance, params)
        flash.message ='Module II data saved.'
        redirect(action: "editm2", id:params.id)
           
      
    }
    
    
     def updatem3n = {
        def module3NSheetInstance = Module3NSheet.get(params.id)
        
        module3NSheetInstance.properties = params
        bpvWorkSheetService.updateModule3N(module3NSheetInstance, params)
        flash.message ='Module III data saved.'
        redirect(action: "editm3n", id:params.id)
           
        
    }
    
    
    
     def updatem4n = {
        def module4NSheetInstance = Module4NSheet.get(params.id)
        
        module4NSheetInstance.properties = params
        bpvWorkSheetService.updateModule4N(module4NSheetInstance, params)
        flash.message ='Module IV data saved.'
        redirect(action: "editm4n", id:params.id)
           
        
    }
    
    def updatem3 = {
        def module3SheetInstance = Module3Sheet.get(params.id)
        
        module3SheetInstance.properties = params
        bpvWorkSheetService.updateModule3(module3SheetInstance, params)
        flash.message ='Priority IIIA data saved.'
        redirect(action: "editm3", id:params.id)
           
      
    }
    
    
    def updatem4 = {
        def module4SheetInstance = Module4Sheet.get(params.id)
        
        module4SheetInstance.properties = params
        bpvWorkSheetService.updateModule4(module4SheetInstance, params)
        flash.message ='Priority IIIB data saved.'
        redirect(action: "editm4", id:params.id)
           
      
    }

    
    def updatem5 = {
        def module5SheetInstance = Module5Sheet.get(params.id)
        
        module5SheetInstance.properties = params
        bpvWorkSheetService.updateModule5(module5SheetInstance, params)
        flash.message ='Module V data saved.'
        redirect(action: "editm5", id:params.id)
           
        
    }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
        if (bpvWorkSheetInstance) {
            try {
                bpvWorkSheetInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvWorkSheetInstance?.formMetadata?.cdrFormName, params.id])}"
            redirect(action: "list")
        }
    }
    
    static boolean isDate(dateStr){
        boolean result=false
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        df.setLenient(false);
        try{
        
            def date = df.parse(dateStr)
            result=true
        }catch (Exception e){
             
        }
          
        return result
        
        
    }
    
    
    Map checkError(bpvWorkSheetInstance){
        def result = [:]
        
        def parentSampleId=bpvWorkSheetInstance.parentSampleId
        if(!parentSampleId){
            result.put("parentSampleId", "The BPV parent sample barcode ID is a required field.")
        }else{
            def caseId=bpvWorkSheetService.getOtherCaseId(parentSampleId, bpvWorkSheetInstance.caseRecord)
            if(caseId){
                result.put("parentSampleId", "The BPV parent sample barcode ID " + parentSampleId + " exists in case " + caseId)
            }
        }
        
        def experimentId=bpvWorkSheetInstance.experimentId
        if(!experimentId){
            result.put("experimentId", "The experiment key barcode ID is a required field.")
        }
        
        def dateEidRecordedStr=bpvWorkSheetInstance.dateEidRecordedStr
        if(!dateEidRecordedStr){
            // println("catch.....")
            result.put("dateEidRecordedStr", "The date & time experimental key barcode ID was recorded is a required field.")
        }
        
        if(dateEidRecordedStr && !isDate(dateEidRecordedStr)){
            result.put("dateEidRecordedStr", "Wrong date format for the date & time experimental key barcode ID was recorded")
        }
        
            if(bpvWorkSheetInstance.m1){
                def module1SheetInstance=bpvWorkSheetInstance.module1Sheet
                if(module1Started(module1SheetInstance)){
                    def result1 = checkError12(module1SheetInstance)
                    if(result1 && result1.size() > 0){
                        result.put("module1Sheet", "Data error in module I sheet.")
                    }
                }else{
                    result.put("module1Sheet", "Please enter data for module I sheet")
                }
            }


            if(bpvWorkSheetInstance.m2){
                def module2SheetInstance=bpvWorkSheetInstance.module2Sheet
                if(module2Started(module2SheetInstance)){
                    def result2 = checkError12(module2SheetInstance)
                    if(result2 && result2.size() > 0){
                        result.put("module2Sheet", "Data error in module II sheet.")
                    }
                }else{
                    result.put("module2Sheet", "Please enter data for module II sheet.")
                }
            }
            
        
          if(bpvWorkSheetInstance.m3){
                def module3NSheetInstance=bpvWorkSheetInstance.module3NSheet
                if(module3NStarted(module3NSheetInstance)){
                    def result2 = checkError345(module3NSheetInstance)
                    if(result2 && result2.size() > 0){
                        result.put("module3NSheet", "Data error in module III sheet.")
                    }
                }else{
                    result.put("module3NSheet", "Please enter data for module III sheet.")
                }
            }
            
           if(bpvWorkSheetInstance.m4){
                def module4NSheetInstance=bpvWorkSheetInstance.module4NSheet
                if(module4NStarted(module4NSheetInstance)){
                    def result2 = checkError345(module4NSheetInstance)
                    if(result2 && result2.size() > 0){
                        result.put("module4NSheet", "Data error in module IV sheet.")
                    }
                }else{
                    result.put("module4NSheet", "Please enter data for module IV sheet.")
                }
            }
            
            
             if(bpvWorkSheetInstance.m5){
                def module5SheetInstance=bpvWorkSheetInstance.module5Sheet
                if(module5Started(module5SheetInstance)){
                    def result2 = checkError345(module5SheetInstance)
                    if(result2 && result2.size() > 0){
                        result.put("module5Sheet", "Data error in module V sheet.")
                    }
                }else{
                    result.put("module5Sheet", "Please enter data for module V sheet.")
                }
            }
        
    
            Integer firstP=null
            boolean hasP1 = false
            if(bpvWorkSheetInstance.m1 && bpvWorkSheetInstance.module1Sheet.priority){
                if(!firstP){
                  firstP=bpvWorkSheetInstance.module1Sheet.priority
                }else{
                  if(bpvWorkSheetInstance.module1Sheet.priority == firstP){
                      result.put("priority", "Each module must be in different priority.");
                  }
                }
               if(bpvWorkSheetInstance.module1Sheet.priority==1){
                      hasP1=true
               }
            }
            
             if(bpvWorkSheetInstance.m2 && bpvWorkSheetInstance.module2Sheet.priority){
                if(!firstP){
                  firstP=bpvWorkSheetInstance.module2Sheet.priority
                }else{
                  if(bpvWorkSheetInstance.module2Sheet.priority == firstP){
                      result.put("priority", "Each module must be in different priority.");
                  }
                }
                if(bpvWorkSheetInstance.module2Sheet.priority==1){
                      hasP1=true
                  }
            }
            
           if(bpvWorkSheetInstance.m3 && bpvWorkSheetInstance.module3NSheet.priority){
                if(!firstP){
                  firstP=bpvWorkSheetInstance.module3NSheet.priority
                }else{
                  if(bpvWorkSheetInstance.module3NSheet.priority == firstP){
                      result.put("priority", "Each module must be in different priority.");
                  }
                }
                if(bpvWorkSheetInstance.module3NSheet.priority==1){
                      hasP1=true
                  }
            }
        
             if(bpvWorkSheetInstance.m4 && bpvWorkSheetInstance.module4NSheet.priority){
                if(!firstP){
                  firstP=bpvWorkSheetInstance.module4NSheet.priority
                }else{
                  if(bpvWorkSheetInstance.module4NSheet.priority == firstP){
                      result.put("priority", "Each module must be in different priority.");
                  }
                }
                if(bpvWorkSheetInstance.module4NSheet.priority==1){
                      hasP1=true
                  }
            }
            
        
           if(bpvWorkSheetInstance.m5 && bpvWorkSheetInstance.module5Sheet.priority){
                if(!firstP){
                  firstP=bpvWorkSheetInstance.module5Sheet.priority
                }else{
                  if(bpvWorkSheetInstance.module5Sheet.priority == firstP){
                      result.put("priority", "Each module must be in different priority.");
                  }
                }
                if(bpvWorkSheetInstance.module5Sheet.priority==1){
                      hasP1=true
                  }
            }
        
            if(!hasP1){
                result.put("priority", "One module must be in priority I")
            }   
            
         
        
            if(bpvWorkSheetInstance.nat){
                def module3SheetInstance=bpvWorkSheetInstance.module3Sheet
                if(module3SheetInstance.version > 0){
                    def result3 = checkError3(module3SheetInstance)
                    if(result3 && result3.size() > 0){
                        result.put("module3Sheet", "Data error in normal adjacent tissue sheet.")
                    }
                }else{
                    result.put("module3Sheet", "Please enter data for normal adjacent tissue sheet")
                }
            }
            if(bpvWorkSheetInstance.ett){
                def module4SheetInstance=bpvWorkSheetInstance.module4Sheet
                if(module4SheetInstance.version > 0){
                    def result4 = checkError4(module4SheetInstance)
                    if(result4 && result4.size() > 0){
                        result.put("module4Sheet", "Data error in additional tumor tissue sheet.")
                    }
                }else{
                    result.put("module4Sheet", "Please enter data for additional tumor tissue sheet")
                }
            }
            
       // }
           def caseRecord = bpvWorkSheetInstance.caseRecord
           if(caseRecord.bpvSlidePrep?.dateSubmitted){
               def slides = SlideRecord.findAllByCaseId(caseRecord.id.toString())
               slides.each(){
                   if(it?.module?.code == 'MODULE1' && !bpvWorkSheetInstance.m1){
                       result.put("slide_m1", "Module I is not selected, but the slide " + it.slideId +" is in Module I")
                   }
                   
                   if(it?.module?.code == 'MODULE2' && !bpvWorkSheetInstance.m2){
                       result.put("slide_m2", "Module II is not selected, but the slide " + it.slideId +" is in Module II")
                   }
                   
                 if(it?.module?.code == 'MODULE3N' && !bpvWorkSheetInstance.m3){
                       result.put("slide_m3", "Module III is not selected, but the slide " + it.slideId +" is in Module III")
                   }
                   
                   if(it?.module?.code == 'MODULE4N' && !bpvWorkSheetInstance.m4){
                       result.put("slide_m4", "Module IV is not selected, but the slide " + it.slideId +" is in Module IV")
                   }
                   
                
                   if(it?.module?.code == 'MODULE5' && !bpvWorkSheetInstance.m5){
                       result.put("slide_m5", "Module V is not selected, but the slide " + it.slideId +" is in Module V")
                   }
                
               }
           }
           def bpvTissueGrossEvaluation=caseRecord.bpvTissueGrossEvaluation
         if(bpvTissueGrossEvaluation?.formVersion >=2 && bpvTissueGrossEvaluation?.dateSubmitted){
             if((caseRecord.bpvTissueGrossEvaluation.ftm1 || caseRecord.bpvTissueGrossEvaluation.stm1) && !bpvWorkSheetInstance.m1 ){
                 result.put("tge_m1", "M1 was selected on Tissue Gross Evaluation Form, but not on Worksheet" )
             }
             
             if((!caseRecord.bpvTissueGrossEvaluation.ftm1 && !caseRecord.bpvTissueGrossEvaluation.stm1) && bpvWorkSheetInstance.m1 ){
                 result.put("tge_m1", "M1 was not selected on Tissue Gross Evaluation Form, but on Worksheet" )
             }
             
              if((caseRecord.bpvTissueGrossEvaluation.ftm2 || caseRecord.bpvTissueGrossEvaluation.stm2) && !bpvWorkSheetInstance.m2 ){
                 result.put("tge_m2", "M2 was selected on Tissue Gross Evaluation Form, but not on Worksheet" )
             }
              if((!caseRecord.bpvTissueGrossEvaluation.ftm2 && !caseRecord.bpvTissueGrossEvaluation.stm2) && bpvWorkSheetInstance.m2 ){
                 result.put("tge_m2", "M2 was not selected on Tissue Gross Evaluation Form, but on Worksheet" )
             }
             
             if((caseRecord.bpvTissueGrossEvaluation.ftm3 || caseRecord.bpvTissueGrossEvaluation.stm3) && !bpvWorkSheetInstance.m3 ){
                 result.put("tge_m3", "M3 was selected on Tissue Gross Evaluation Form, but not on Worksheet" )
             }
             
               if((!caseRecord.bpvTissueGrossEvaluation.ftm3 && !caseRecord.bpvTissueGrossEvaluation.stm3) && bpvWorkSheetInstance.m3 ){
                 result.put("tge_m3", "M3 was not selected on Tissue Gross Evaluation Form, but on Worksheet" )
             }
             
              if((caseRecord.bpvTissueGrossEvaluation.ftm4 || caseRecord.bpvTissueGrossEvaluation.stm4) && !bpvWorkSheetInstance.m4 ){
                 result.put("tge_m4", "M4 was selected on Tissue Gross Evaluation Form, but not on Worksheet" )
             }
             
             if((!caseRecord.bpvTissueGrossEvaluation.ftm4 && !caseRecord.bpvTissueGrossEvaluation.stm4) && bpvWorkSheetInstance.m4 ){
                 result.put("tge_m4", "M4 was not selected on Tissue Gross Evaluation Form, but on Worksheet" )
             }
            
            
             if((caseRecord.bpvTissueGrossEvaluation.ftm5 || caseRecord.bpvTissueGrossEvaluation.stm5) && !bpvWorkSheetInstance.m5 ){
                 result.put("tge_m5", "M5 was selected on Tissue Gross Evaluation Form, but not on Worksheet" )
             }
            
              if((!caseRecord.bpvTissueGrossEvaluation.ftm5 && !caseRecord.bpvTissueGrossEvaluation.stm5) && bpvWorkSheetInstance.m5 ){
                 result.put("tge_m5", "M5 was not selected on Tissue Gross Evaluation Form, but on Worksheet" )
             }
            
            
              if((caseRecord.bpvTissueGrossEvaluation.ftAddlTumor || caseRecord.bpvTissueGrossEvaluation.stAddlTumor) && !bpvWorkSheetInstance.ett ){
                 result.put("tge_ett", "Additional tumor tissue was selected on Tissue Gross Evaluation Form, but not on Worksheet" )
             }
             
             if((!caseRecord.bpvTissueGrossEvaluation.ftAddlTumor && !caseRecord.bpvTissueGrossEvaluation.stAddlTumor) && bpvWorkSheetInstance.ett ){
                 result.put("tge_ett", "Additional tumor tissue was not selected on Tissue Gross Evaluation Form, but on Worksheet" )
             }
             
         }
        return result
    }

    
    
    Map checkError12(moduleSheetInstance){
       
        def bpvWorkSheet =  moduleSheetInstance.bpvWorkSheet
        def parentSampleId=bpvWorkSheet.parentSampleId
     
         def sampleMap=[:]
         if(moduleSheetInstance instanceof Module1Sheet){
           sampleMap += getSampleListModule2(bpvWorkSheet.module2Sheet)
         }else{
            sampleMap += getSampleListModule1(bpvWorkSheet.module1Sheet) 
         }
        sampleMap += getSampleListModule3N(bpvWorkSheet.module3NSheet)
        sampleMap +=  getSampleListModule4N(bpvWorkSheet.module4NSheet)
        sampleMap +=  getSampleListModule3(bpvWorkSheet.module3Sheet)
        sampleMap += getSampleListModule4(bpvWorkSheet.module4Sheet)
        sampleMap += getSampleListModule5(bpvWorkSheet.module5Sheet)
        
        
       
        def result = [:]
        
        def sampleIdList=[]
        
        def caseRecord = moduleSheetInstance.bpvWorkSheet.caseRecord
        
        def cdrVer = caseRecord.cdrVer    
        def  version54 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '5.4.1') < 0){
            version54 = false
        }
        
        def sampleFr=moduleSheetInstance.sampleFr
        def sampleId4Frozen=sampleFr.sampleId4Frozen
        
        def organ = caseRecord.primaryTissueType.code
        
        if(organ =='OVARY' && !moduleSheetInstance.whichOvary && ComputeMethods.compareCDRVersion(caseRecord.cdrVer, '4.1.1') == 1){
            result.put("whichOvary", "Module I tissue was collected from is a required field." ) 
                
        }
              
        def list=[]
         if(moduleSheetInstance instanceof Module1Sheet){
         list=[moduleSheetInstance.sampleA, moduleSheetInstance.sampleB, moduleSheetInstance.sampleC, moduleSheetInstance.sampleD]
         }else{
            list=[moduleSheetInstance.sampleE, moduleSheetInstance.sampleF, moduleSheetInstance.sampleG, moduleSheetInstance.sampleH] 
         }
        if(version54){
            list.add(moduleSheetInstance.sampleQc);
            if(!sampleId4Frozen){
              result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is a required field.")
            }
        }
          if(sampleId4Frozen){
            
             if(sampleId4Frozen == parentSampleId){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is same as parent sample id")  
                }
                
                if(sampleIdList.contains(sampleId4Frozen)){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is same as other sample id in this sheet.")  
                }else{
                    sampleIdList.add(sampleId4Frozen)
                }
                 
            
               sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(sampleId4Frozen)){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is same as one sample id in " + module + " sheet.")
                        
                }
                   
               }
               
              
                
                 def caseId=bpvWorkSheetService.getOtherCaseId(sampleId4Frozen, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette exists in case " + caseId)
                }
                 
                if(bpvWorkSheetService.isBloodSample(sampleId4Frozen, bpvWorkSheet.caseRecord)){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette exists with tissue type blood.")
                }
            
            
            
                 def dateSampleFrozenStr=sampleFr.dateSampleFrozenStr
                    if(!dateSampleFrozenStr){
                        result.put("dateSampleFrozenStr_" + sampleFr.id, "The Date/Time that tissue sample was frozen in liquid nitrogen is a required field.")
                    }

                    if(dateSampleFrozenStr && !isDate(dateSampleFrozenStr)){
                        result.put("dateSampleFrozenStr_" + sampleFr.id, "Wrong date format for the date/time that tissue sample was frozen in liquid nitrogen")
                    }

                    def sampleFrWeight=sampleFr.weight
                    if(!sampleFrWeight){
                        result.put("weight_" + sampleFr.id, "Weight of Frozen tumor block is a required field")
                    }
           }
            
        
        
        list.each{
            def protocol = it.protocol
            protocol = protocol.replace('<br/>', ' ')
            
            def sampleId4Record=it.sampleId4Record
            if(!sampleId4Record){
                result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is a required field.") 
            }else{
                 
                if(sampleId4Record == parentSampleId){
                    result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is same as parent sample id")  
                }
                
                if(sampleIdList.contains(sampleId4Record)){
                    result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is same as  other sample id in this sheet.")  
                }else{
                    sampleIdList.add(sampleId4Record)
                }
                 
              
                 sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(sampleId4Record)){
                    result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is same as one sample id in " + module + " sheet.")
                   
                        
                }
                   
               }
               
                        
           
                
                def caseId=bpvWorkSheetService.getOtherCaseId(sampleId4Record, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put("sampleId4Record_" + it.id, "the barcodeId " + sampleId4Record+ " for (" + protocol + ") exists in case " + caseId)
                }
                 
                if(bpvWorkSheetService.isBloodSample(sampleId4Record, bpvWorkSheet.caseRecord)){
                    result.put("sampleId4Record_" + it.id, "the barcodeId " + sampleId4Record+ " for (" + protocol + ") exists with tissue type blood")
                }
                
                sampleIdList.add(sampleId4Record)
            }
            
            def dateSampleRecordedStr=it.dateSampleRecordedStr
            if(!dateSampleRecordedStr){
                result.put("dateSampleRecordedStr_" + it.id, "Date/time that cassette was first scanned or recorded (" + protocol + ") is a required field.")
            }
            if(dateSampleRecordedStr && !isDate(dateSampleRecordedStr)){
                result.put("dateSampleRecordedStr_" + it.id, "Wrong date format for date/time that cassette was first scanned or recorded (" + protocol + ").")
            }
             
            def sampleId4Fixative=it.sampleId4Fixative
            if(sampleId4Fixative && sampleId4Record ){
                if(sampleId4Fixative != sampleId4Record)
                result.put("sampleId4Fixative_" + it.id, "Scanned ID of cassette: Record time placed in fixative (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleInFixativeStr=it.dateSampleInFixativeStr
            if(!dateSampleInFixativeStr){
                result.put("dateSampleInFixativeStr_" + it.id, "Date/time that cassette was placed in fixative (" + protocol + ") is a required field.")
            }
            if(dateSampleInFixativeStr && !isDate(dateSampleInFixativeStr)){
                result.put("dateSampleInFixativeStr_" + it.id, "Wrong date format for date/time that cassette was placed in fixative (" + protocol + ").")
            }
             
            
            def sampleId4Proc=it.sampleId4Proc
            if(sampleId4Proc && sampleId4Record ){
                if(sampleId4Proc != sampleId4Record)
                result.put("sampleId4Proc_" + it.id, "Scanned ID of cassette: Record time placed in tissue processor (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleInProcStr=it.dateSampleInProcStr
            if(!dateSampleInProcStr){
                result.put("dateSampleInProcStr_" + it.id, "Date/time cassette was placed in processor (" + protocol + ") is a required field.")
            }
            if(dateSampleInProcStr && !isDate(dateSampleInProcStr)){
                result.put("dateSampleInProcStr_" + it.id, "Wrong date format for date/time cassette was placed in processor (" + protocol + ").")
            }
             
            def dateSampleProcEndStr=it.dateSampleProcEndStr
            if(!dateSampleProcEndStr){
                result.put("dateSampleProcEndStr_" + it.id, "Date/time tissue processor cycle ended (" + protocol + ") is a required field.")
            }
            if(dateSampleProcEndStr && !isDate(dateSampleProcEndStr)){
                result.put("dateSampleProcEndStr_" + it.id, "Wrong date format for date/time tissue processor cycle ended(" + protocol + ").")
            }
             
            
            def sampleId4Removal = it.sampleId4Removal
            if(sampleId4Removal && sampleId4Record ){
                if(sampleId4Removal != sampleId4Record)
                result.put("sampleId4Removal_" + it.id, "Scanned ID of cassette: Record time removed from tissue processor (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleRemovedStr=it.dateSampleRemovedStr
            if(!dateSampleRemovedStr){
                result.put("dateSampleRemovedStr_" + it.id, "Date/time tissue processor cycle ended (" + protocol + ") is a required field.")
            }
            if(dateSampleRemovedStr && !isDate(dateSampleRemovedStr)){
                result.put("dateSampleRemovedStr_" + it.id, "Wrong date format for date/time tissue processor cycle ended(" + protocol + ").")
            }
             
            
            def sampleId4Embedding = it.sampleId4Embedding
            if(sampleId4Embedding && sampleId4Record ){
                if(sampleId4Embedding != sampleId4Record)
                result.put("sampleId4Embedding_" + it.id, "Scanned ID of cassette: Record time tissue embedding was started (" + protocol + ") is different from scanned id of first record ")
            }
            
             
            def dateSampleEmbStartedStr=it.dateSampleEmbStartedStr
            if(!dateSampleEmbStartedStr){
                result.put("dateSampleEmbStartedStr_" + it.id, "Date/time tissue embedding was started (" + protocol + ") is a required field.")
            }
            if(dateSampleEmbStartedStr && !isDate(dateSampleEmbStartedStr)){
                result.put("dateSampleEmbStartedStr_" + it.id, "Wrong date format date/time tissue embedding was started (" + protocol + ").")
            }
            
        }
        
        if(!moduleSheetInstance.priority){
             result.put("priority", "The priority is a required field.")
        }
        return result
    }
 
    
   

    
   Map checkError345(moduleSheetInstance){
       def result=[:]
       def sampleIdList=[]
       
         def bpvWorkSheet =  moduleSheetInstance.bpvWorkSheet
        def parentSampleId=bpvWorkSheet.parentSampleId
     
        
        
         def sampleMap=[:]
        
        sampleMap += getSampleListModule1(bpvWorkSheet.module1Sheet)
        sampleMap += getSampleListModule2(bpvWorkSheet.module2Sheet)
         if(moduleSheetInstance instanceof Module3NSheet){
             sampleMap += getSampleListModule4N(bpvWorkSheet.module4NSheet)
             sampleMap += getSampleListModule5(bpvWorkSheet.module5Sheet)
         }else if(moduleSheetInstance instanceof Module4NSheet){
              sampleMap += getSampleListModule3N(bpvWorkSheet.module3NSheet)
             sampleMap += getSampleListModule5(bpvWorkSheet.module5Sheet)
         }else{
              sampleMap += getSampleListModule3N(bpvWorkSheet.module3NSheet)
               sampleMap += getSampleListModule4N(bpvWorkSheet.module4NSheet)
         }
        
       
        
        sampleMap +=  getSampleListModule3(bpvWorkSheet.module3Sheet)
        sampleMap += getSampleListModule4(bpvWorkSheet.module4Sheet)
        
         def sampleFr=moduleSheetInstance.sampleFr
        def sampleId4Frozen=sampleFr.sampleId4Frozen
        def dateSampleFrozenStr = sampleFr.dateSampleFrozenStr
        def sampleFrWeight=sampleFr?.weight
        
       
         def caseRecord = bpvWorkSheet.caseRecord
        def organ = caseRecord.primaryTissueType.code
        
       
      
        
         if(organ =='OVARY' && !moduleSheetInstance.whichOvary){
            result.put("whichOvary", "Module III tissue was collected from is a required field" ) 
                
        }
        
         if(!sampleId4Frozen){
              result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is a required field.")
            }
            
         if(sampleId4Frozen){
             if(sampleId4Frozen == parentSampleId){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is same as parent sample id")  
                }
                
                if(sampleIdList.contains(sampleId4Frozen)){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is same as other sample id in this sheet.")  
                }else{
                    sampleIdList.add(sampleId4Frozen)
                }
                 
               
                sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(sampleId4Frozen)){
                  
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette is same as one sample id in " + module + " sheet.")
                        
                }
                   
               }
               
            
             
                
            
               def caseId=bpvWorkSheetService.getOtherCaseId(sampleId4Frozen, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette exists in case " + caseId)
                }
                 
                if(bpvWorkSheetService.isBloodSample(sampleId4Frozen, bpvWorkSheet.caseRecord)){
                    result.put("sampleId4Frozen_" + sampleFr.id, "Barcode ID of Frozen tumor tissue Cryosette exists with tissue type blood")
                }
            
             
         }
         if(!dateSampleFrozenStr){
                result.put("dateSampleFrozenStr_" + sampleFr.id, "The Date/Time that tissue sample was frozen in liquid nitrogen is a required field.")
         }

         if(dateSampleFrozenStr && !isDate(dateSampleFrozenStr)){
                result.put("dateSampleFrozenStr_" + sampleFr.id, "Wrong date format for the date/time that tissue sample was frozen in liquid nitrogen")
         }
        
         if(!sampleFrWeight){
              result.put("weight_" + sampleFr.id, "Weight of Frozen tumor block is a required field")
        }
        
        
         def list=[moduleSheetInstance.sampleQc]
         
        list.each{
            
            def protocol = it.protocol
            protocol = protocol.replace('<br/>', ' ')
             
            def sampleId4Record=it.sampleId4Record
            if(!sampleId4Record){
                result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is a required field.") 
            }else{
                 if(sampleId4Record == parentSampleId){
                    result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is same as parent sample id")
                }
                 
                if(sampleIdList.contains(sampleId4Record)){
                    result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is same as  other sample id in this sheet.")  
                }else{
                    sampleIdList.add(sampleId4Record)
                }
                 
               
                 sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(sampleId4Record)){
                  
                result.put("sampleId4Record_" + it.id, "Scanned ID of cassette: record first scan (" + protocol + ") is same as  one sample id in " + module + " sheet")
                  
                        
                }
                   
               }
                
                
             
                
                
                def caseId=bpvWorkSheetService.getOtherCaseId(sampleId4Record, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put("sampleId4Record_" + it.id, "the barcodeId " + sampleId4Record+ " for (" + protocol + ") exists in case " + caseId)
                }
                 
                if(bpvWorkSheetService.isBloodSample(sampleId4Record, bpvWorkSheet.caseRecord)){
                    result.put("sampleId4Record_" + it.id, "the barcodeId " + sampleId4Record+ " for (" + protocol + ") exists with tissue type blood")
                }
                
            }
            
            
            def dateSampleRecordedStr=it.dateSampleRecordedStr
            if(!dateSampleRecordedStr){
                result.put("dateSampleRecordedStr_" + it.id, "Date/time that cassette was first scanned or recorded (" + protocol + ") is a required field.")
            }
            if(dateSampleRecordedStr && !isDate(dateSampleRecordedStr)){
                result.put("dateSampleRecordedStr_" + it.id, "Wrong date format for date/time that cassette was first scanned or recorded (" + protocol + ").")
            }
            
            
             def sampleId4Fixative=it.sampleId4Fixative
            if(sampleId4Fixative && sampleId4Record ){
                if(sampleId4Fixative != sampleId4Record)
                result.put("sampleId4Fixative_" + it.id, "Scanned ID of cassette: Record time placed in fixative (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleInFixativeStr=it.dateSampleInFixativeStr
            if(!dateSampleInFixativeStr){
                result.put("dateSampleInFixativeStr_" + it.id, "Date/time that cassette was placed in fixative (" + protocol + ") is a required field.")
            }
            if(dateSampleInFixativeStr && !isDate(dateSampleInFixativeStr)){
                result.put("dateSampleInFixativeStr_" + it.id, "Wrong date format for date/time that cassette was placed in fixative (" + protocol + ").")
            }
             
            def sampleId4Proc=it.sampleId4Proc
            if(sampleId4Proc && sampleId4Record ){
                if(sampleId4Proc != sampleId4Record)
                result.put("sampleId4Proc_" + it.id, "Scanned ID of cassette: Record time placed in tissue processor (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleInProcStr=it.dateSampleInProcStr
            if(!dateSampleInProcStr){
                result.put("dateSampleInProcStr_" + it.id, "Date/time cassette was placed in processor (" + protocol + ") is a required field.")
            }
            if(dateSampleInProcStr && !isDate(dateSampleInProcStr)){
                result.put("dateSampleInProcStr_" + it.id, "Wrong date format for date/time cassette was placed in processor (" + protocol + ").")
            }
             
            def dateSampleProcEndStr=it.dateSampleProcEndStr
            if(!dateSampleProcEndStr){
                result.put("dateSampleProcEndStr_" + it.id, "Date/time tissue processor cycle ended (" + protocol + ") is a required field.")
            }
            if(dateSampleProcEndStr && !isDate(dateSampleProcEndStr)){
                result.put("dateSampleProcEndStr_" + it.id, "Wrong date format for date/time tissue processor cycle ended(" + protocol + ").")
            }
             
            def sampleId4Removal = it.sampleId4Removal
            if(sampleId4Removal && sampleId4Record ){
                if(sampleId4Removal != sampleId4Record)
                result.put("sampleId4Removal_" + it.id, "Scanned ID of cassette: Record time removed from tissue processor (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleRemovedStr=it.dateSampleRemovedStr
            if(!dateSampleRemovedStr){
                result.put("dateSampleRemovedStr_" + it.id, "Date/time tissue processor cycle ended (" + protocol + ") is a required field.")
            }
            if(dateSampleRemovedStr && !isDate(dateSampleRemovedStr)){
                result.put("dateSampleRemovedStr_" + it.id, "Wrong date format for date/time tissue processor cycle ended(" + protocol + ").")
            }
             
            def sampleId4Embedding = it.sampleId4Embedding
            if(sampleId4Embedding && sampleId4Record ){
                if(sampleId4Embedding != sampleId4Record)
                result.put("sampleId4Embedding_" + it.id, "Scanned ID of cassette: Record time tissue embedding was started (" + protocol + ") is different from scanned id of first record ")
            }
            
            def dateSampleEmbStartedStr=it.dateSampleEmbStartedStr
            if(!dateSampleEmbStartedStr){
                result.put("dateSampleEmbStartedStr_" + it.id, "Date/time tissue embedding was started (" + protocol + ") is a required field.")
            }
            if(dateSampleEmbStartedStr && !isDate(dateSampleEmbStartedStr)){
                result.put("dateSampleEmbStartedStr_" + it.id, "Wrong date format date/time tissue embedding was started (" + protocol + ").")
            }
            
        }
         
        def list2=[]
           if(moduleSheetInstance instanceof Module3NSheet){
               list2=[moduleSheetInstance.sampleI, moduleSheetInstance.sampleJ, moduleSheetInstance.sampleK, moduleSheetInstance.sampleL, moduleSheetInstance.sampleM, moduleSheetInstance.sampleN]
           }else if (moduleSheetInstance instanceof Module4NSheet){
              list2=[moduleSheetInstance.sampleO, moduleSheetInstance.sampleP, moduleSheetInstance.sampleQ, moduleSheetInstance.sampleR, moduleSheetInstance.sampleS, moduleSheetInstance.sampleT] 
           }else{
              list2=[moduleSheetInstance.sampleU, moduleSheetInstance.sampleV, moduleSheetInstance.sampleW, moduleSheetInstance.sampleX]  
           }
          list2.each{
               def protocol = it.protocol
            protocol = protocol.replace('<br/>', ' ')
            
             def sampleId4Frozen2=it.sampleId4Frozen
            if(!sampleId4Frozen2){
                result.put("sampleId4Frozen_" + it.id, "Scanned ID of Cryosette: (" + protocol + ") is a required field.") 
            }else{
                 if(sampleId4Frozen2 == parentSampleId){
                    result.put("sampleId4Frozen_" + it.id, "Scanned ID of cassette: record when frozen (" + protocol + ") is same as parent sample id")
                }
                 
                if(sampleIdList.contains(sampleId4Frozen2)){
                    result.put("sampleId4Frozen_" + it.id, "Scanned ID of cassette: record when frozen (" + protocol + ") is same as  other sample id in this sheet.")  
                }else{
                    sampleIdList.add(sampleId4Frozen2)
                }
                 
                  sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(sampleId4Frozen2)){
                  
                   result.put("sampleId4Frozen_" + it.id, "Scanned ID of cassette: record when frozen (" + protocol + ") is the same as a sample id in " + module +" sheet")
                  
                        
                }
                   
               }
                
               
                
                def caseId=bpvWorkSheetService.getOtherCaseId(sampleId4Frozen2, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put("sampleId4Frozen_" + it.id, "the barcodeId " + sampleId4Frozen2+ " for (" + protocol + ") exists in case " + caseId)
                }
                 
                if(bpvWorkSheetService.isBloodSample(sampleId4Frozen2, bpvWorkSheet.caseRecord)){
                    result.put("sampleId4Frozen_" + it.id, "the barcodeId " + sampleId4Frozen2+ " for (" + protocol + ") exists with tissue type blood")
                }
                
            }
            
             def dateSampleFrozenStr2=it.dateSampleFrozenStr
            if(!dateSampleFrozenStr2){
                result.put("dateSampleFrozenStr_" + it.id, "Date/time frozen (" + protocol + ") is a required field.")
            }
            if(dateSampleFrozenStr2 && !isDate(dateSampleFrozenStr2)){
                result.put("dateSampleFrozenStr_" + it.id, "Wrong date format for date/time frozen (" + protocol + ").")
            }
            
            def sampleId4Trans=it.sampleId4Trans
            if(sampleId4Trans && sampleId4Frozen2 ){
                if(sampleId4Trans != sampleId4Frozen2)
                result.put("sampleId4Trans_" + it.id, "Scanned ID of Cryosette: Record time transferred (" + protocol + ") is different from scanned id of time frozen ")
            }
            
            def dateSampleTransStr=it.dateSampleTransStr
            if(!dateSampleTransStr){
                result.put("dateSampleTransStr_" + it.id, "Date/time transferred (" + protocol + ") is a required field.")
            }
            if(dateSampleTransStr && !isDate(dateSampleTransStr)){
                result.put("dateSampleTransStr_" + it.id, "Wrong date format for date/time transferred (" + protocol + ").")
            } 
            
            
            def weight = it.weight
            if(!weight){
               result.put("weight_" + it.id, "Weight (" + protocol + ") is a required field.") 
            }
          }
        
        if(!moduleSheetInstance.priority){
             result.put("priority", "The priority is a required field.")
        }
       return result
   }
    
    
    
    
    
  
    
    Map checkError3(module3SheetInstance){
          
       
        def bpvWorkSheet =  module3SheetInstance.bpvWorkSheet
        def cdrVer = bpvWorkSheet.caseRecord.cdrVer
        def parentSampleId=bpvWorkSheet.parentSampleId
        def qcAndFrozenSampleInstance=bpvWorkSheet.qcAndFrozenSample
        
        
        
         def sampleMap=[:]
        
        sampleMap += getSampleListModule1(bpvWorkSheet.module1Sheet)
        sampleMap += getSampleListModule2(bpvWorkSheet.module2Sheet)
        sampleMap += getSampleListModule4(bpvWorkSheet.module4Sheet)
        sampleMap += getSampleListModule3N(bpvWorkSheet.module3NSheet)
        sampleMap += getSampleListModule4N(bpvWorkSheet.module4NSheet)
        sampleMap += getSampleListModule5(bpvWorkSheet.module5Sheet)
            
        def result = [:]
        def sampleIdList=[]
        
        boolean hasNormal=false
        
        
        def ids=[]
        def samples=[]
        def weights=[]
        def fieldIds=[]
        def fieldWeights=[]
        def descs=[]
        def times=[]
        def fieldTimes=[]
        
        
        
        ids[0]=module3SheetInstance.ntFfpeId1
        ids[1]=module3SheetInstance.ntFfpeId2
        ids[2]=module3SheetInstance.ntFfpeId3
        ids[3]=module3SheetInstance.ntFrozenId1
        ids[4]=module3SheetInstance.ntFrozenId2
        ids[5]=module3SheetInstance.ntFrozenId3
        
        samples[0]=module3SheetInstance.ntFfpeSample1
        samples[1]=module3SheetInstance.ntFfpeSample2
        samples[2]=module3SheetInstance.ntFfpeSample3
        samples[3]=module3SheetInstance.ntFrozenSample1
        samples[4]=module3SheetInstance.ntFrozenSample2
        samples[5]=module3SheetInstance.ntFrozenSample3
        
        weights[0]=module3SheetInstance.ntFfpeWeight1
        weights[1]=module3SheetInstance.ntFfpeWeight2
        weights[2]=module3SheetInstance.ntFfpeWeight3
        weights[3]=module3SheetInstance.ntFrozenWeight1
        weights[4]=module3SheetInstance.ntFrozenWeight2
        weights[5]=module3SheetInstance.ntFrozenWeight3
        
        descs[0]="FFPE normal adjacent tissue 1"
        descs[1]="FFPE normal adjacent tissue 2"
        descs[2]="FFPE normal adjacent tissue 3"
        descs[3]="Frozen normal adjacent tissue 1"
        descs[4]="Frozen normal adjacent tissue 2"
        descs[5]="Frozen normal adjacent tissue 3"
          
        fieldIds[0]="ntFfpeId1"
        fieldIds[1]="ntFfpeId2"
        fieldIds[2]="ntFfpeId3"
        fieldIds[3]="ntFrozenId1"
        fieldIds[4]="ntFrozenId2"
        fieldIds[5]="ntFrozenId3"
        
       
        fieldWeights[0]="ntFfpeWeight1"
        fieldWeights[1]="ntFfpeWeight2"
        fieldWeights[2]="ntFfpeWeight3"
        fieldWeights[3]="ntFrozenWeight1"
        fieldWeights[4]="ntFrozenWeight2"
        fieldWeights[5]="ntFrozenWeight3"
        
        times[0]=module3SheetInstance.ntFfpeTimeStr1
        times[1]=module3SheetInstance.ntFfpeTimeStr2
        times[2]=module3SheetInstance.ntFfpeTimeStr3
        times[3]=module3SheetInstance.ntFrozenTimeStr1
        times[4]=module3SheetInstance.ntFrozenTimeStr2
        times[5]=module3SheetInstance.ntFrozenTimeStr3
        fieldTimes[0]='ntFfpeTimeStr1'
        fieldTimes[1]='ntFfpeTimeStr2'
        fieldTimes[2]='ntFfpeTimeStr3'
        fieldTimes[3]='ntFrozenTimeStr1'
        fieldTimes[4]='ntFrozenTimeStr2'
        fieldTimes[5]='ntFrozenTimeStr3'
        
        
        for(int i = 0; i < 6; i++){
            def id = ids[i]
            def sample = samples[i]
            def weight = weights[i]
            if(!id && samples[i]){
                result.put(fieldIds[i], "Barcode Id is a required field for " + descs[i])
            }
            if(id){
                hasNormal=true
              
                if(id == parentSampleId){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + " is same as parent sample id")
                }
                   
                
                if(sampleIdList.contains(id)){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + "is same as other sample id in this sheet.") 
                }else{
                    sampleIdList.add(id) 
                }
            
               
                 sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(id)){
                  
                     result.put(fieldIds[i], "Barcode Id for " + descs[i] + " is same as one sample id in " + module + " sheet")
                   
                }
                   
               }
                
                
            
                
                def caseId=bpvWorkSheetService.getOtherCaseId(id, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + " exists in other case" + caseId)
                }
                 
                if(bpvWorkSheetService.isBloodSample(id, bpvWorkSheet.caseRecord)){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + " exists with tissue type blood")
                }
                
                if(!weights[i]){
                    result.put(fieldWeights[i], "The weight for " + descs[i] + " is a required field")
                }
             
                if(!times[i] && ComputeMethods.compareCDRVersion(cdrVer, '4.1.1') == 1){
                    result.put(fieldTimes[i], "The time for " + descs[i] + " is a required field")
                }   
              
                if(times[i] && !isDate(times[i])){
                    result.put(fieldTimes[i], "Wrong date format of date/time for " + descs[i] )
                }
                
                
           
            }
        }
        
       
        
      
            
        if(!module3SheetInstance.ntDisecPerformedBy){
            result.put("ntDisecPerformedBy", "Tissue dissection performed by for normal adjacent tissue is a required field")
        }
        
        
        if(!module3SheetInstance.ntFixativeTimeInRange && ComputeMethods.compareCDRVersion(cdrVer, '4.1.1') == 1){
            result.put("ntFixativeTimeInRange", "Please specify the Priority III FFPE specimens were processed with <1 hour delay to fixation and 23 hour time in fixative")
        }
       
        if(module3SheetInstance.ntFixativeTimeInRange=='No' && !module3SheetInstance.ntReasonNotInRange){
            result.put('ntReasonNotInRange', 'Please specify the reason');
        }
        
        if( !hasNormal){
            result.put("oneSample", "Please select at least one sample")
        }
        
        return result
    }
 
    
    
    Map checkError4(module4SheetInstance){
          
        def bpvWorkSheet =  module4SheetInstance.bpvWorkSheet
        def cdrVer = bpvWorkSheet.caseRecord.cdrVer
        def parentSampleId=bpvWorkSheet.parentSampleId
       
        
           def sampleMap=[:]
        
        sampleMap += getSampleListModule1(bpvWorkSheet.module1Sheet)
        sampleMap += getSampleListModule2(bpvWorkSheet.module2Sheet)
        sampleMap += getSampleListModule3(bpvWorkSheet.module3Sheet)
        sampleMap += getSampleListModule3N(bpvWorkSheet.module3NSheet)
        sampleMap += getSampleListModule4N(bpvWorkSheet.module4NSheet)
        sampleMap += getSampleListModule5(bpvWorkSheet.module5Sheet)
            
        def result = [:]
        def sampleIdList=[]
        
        boolean hasTumor = false;
        boolean hasNormal=false
        
        
        def ids=[]
        def samples=[]
        def weights=[]
        def fieldIds=[]
        def fieldWeights=[]
        def descs=[]
        def times=[]
        def fieldTimes=[]
        
        ids[0]=module4SheetInstance.ttFfpeId1
        ids[1]=module4SheetInstance.ttFfpeId2
        ids[2]=module4SheetInstance.ttFfpeId3
        ids[3]=module4SheetInstance.ttFrozenId1
        ids[4]=module4SheetInstance.ttFrozenId2
        ids[5]=module4SheetInstance.ttFrozenId3
      
        
        samples[0]=module4SheetInstance.ttFfpeSample1
        samples[1]=module4SheetInstance.ttFfpeSample2
        samples[2]=module4SheetInstance.ttFfpeSample3
        samples[3]=module4SheetInstance.ttFrozenSample1
        samples[4]=module4SheetInstance.ttFrozenSample2
        samples[5]=module4SheetInstance.ttFrozenSample3
      
        weights[0]=module4SheetInstance.ttFfpeWeight1
        weights[1]=module4SheetInstance.ttFfpeWeight2
        weights[2]=module4SheetInstance.ttFfpeWeight3
        weights[3]=module4SheetInstance.ttFrozenWeight1
        weights[4]=module4SheetInstance.ttFrozenWeight2
        weights[5]=module4SheetInstance.ttFrozenWeight3
      
        
        descs[0]="FFPE tumor tissue 1"
        descs[1]="FFPE tumor tissue 2"
        descs[2]="FFPE tumor tissue 3"
        descs[3]="Frozen tumor tissue 1"
        descs[4]="Frozen tumor tissue 2"
        descs[5]="Frozen tumor tissue 3"
       
        
        
        fieldIds[0]="ttFfpeId1"
        fieldIds[1]="ttFfpeId2"
        fieldIds[2]="ttFfpeId3"
        fieldIds[3]="ttFrozenId1"
        fieldIds[4]="ttFrozenId2"
        fieldIds[5]="ttFrozenId3"
       
        
        fieldWeights[0]="ttFfpeWeight1"
        fieldWeights[1]="ttFfpeWeight2"
        fieldWeights[2]="ttFfpeWeight3"
        fieldWeights[3]="ttFrozenWeight1"
        fieldWeights[4]="ttFrozenWeight2"
        fieldWeights[5]="ttFrozenWeight3"
        
        times[0]=module4SheetInstance.ttFfpeTimeStr1
        times[1]=module4SheetInstance.ttFfpeTimeStr2
        times[2]=module4SheetInstance.ttFfpeTimeStr3
        times[3]=module4SheetInstance.ttFrozenTimeStr1
        times[4]=module4SheetInstance.ttFrozenTimeStr2
        times[5]=module4SheetInstance.ttFrozenTimeStr3
        fieldTimes[0]='ttFfpeTimeStr1'
        fieldTimes[1]='ttFfpeTimeStr2'
        fieldTimes[2]='ttFfpeTimeStr3'
        fieldTimes[3]='ttFrozenTimeStr1'
        fieldTimes[4]='ttFrozenTimeStr2'
        fieldTimes[5]='ttFrozenTimeStr3'
       
        
        for(int i = 0; i < 6; i++){
            def id = ids[i]
            def sample = samples[i]
            def weight = weights[i]
            if(!id && samples[i]){
                result.put(fieldIds[i], "Barcode Id is a required field for " + descs[i])
            }
            if(id){
              
                hasTumor=true
              
              
           
                if(id == parentSampleId){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + " is same as parent sample id")
                }
                   
                
                if(sampleIdList.contains(id)){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + "is same as other sample id in this sheet.") 
                }else{
                    sampleIdList.add(id) 
                }
            
                   sampleMap.each(){module, sampleList->
                  
                if(sampleList.contains(id)){
                  
                     result.put(fieldIds[i], "Barcode Id for " + descs[i] + " is same as one sample id in " + module + " sheet")
                   
                }
                   
               }
                
             
            
              
                
                def caseId=bpvWorkSheetService.getOtherCaseId(id, bpvWorkSheet.caseRecord)
                if(caseId){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + " exists in other case" + caseId)
                }
             
                 if(bpvWorkSheetService.isBloodSample(id, bpvWorkSheet.caseRecord)){
                    result.put(fieldIds[i], "Barcode Id for " + descs[i] + " exists with tissue type blood")
                }
                
                if(!weights[i]){
                    result.put(fieldWeights[i], "The weight for " + descs[i] + " is a required field")
                }
             
                if(!times[i] && ComputeMethods.compareCDRVersion(cdrVer, '4.1.1') == 1){
                    result.put(fieldTimes[i], "The time for " + descs[i] + " is a required field")
                }   
              
                if(times[i] && !isDate(times[i])){
                    result.put(fieldTimes[i], "Wrong date format of date/time for " + descs[i] )
                }
             
           
            }
        }
        
        
       
        if(!module4SheetInstance.ttDisecPerformedBy){
            result.put("ttDisecPerformedBy", "Tissue dissection performed by for tumor tissue is a required field")
        }
        
        
        if(!module4SheetInstance.ttFixativeTimeInRange && ComputeMethods.compareCDRVersion(cdrVer, '4.1.1') == 1){
            result.put("ttFixativeTimeInRange", "Please specify the Priority III FFPE specimens were processed with <1 hour delay to fixation and 23 hour time in fixative")
        }
       
        if(module4SheetInstance.ttFixativeTimeInRange=='No' && !module4SheetInstance.ttReasonNotInRange){
            result.put('ttReasonNotInRange', 'Please specify the reason');
        }
        
        
       
        if(!hasTumor){
            result.put("oneSample", "Please select at least one sample")
        }
        
        return result
    }
 
   
     Map getSampleListModule1(module1SheetInstance){
         
        def result=[:]
          
        def sampleIdList=[]
        
      
      
        def list=[module1SheetInstance.sampleQc, module1SheetInstance.sampleA, module1SheetInstance.sampleB, module1SheetInstance.sampleC, module1SheetInstance.sampleD]
        list.each{         
            def sampleId4Record=it.sampleId4Record
            if(sampleId4Record){
                sampleIdList.add(sampleId4Record)
            }
            
        }
        
         sampleIdList.add(module1SheetInstance.sampleFr.sampleId4Frozen)
         result.put("module1", sampleIdList)
        return result
    }
    
        
    Map getSampleListModule2(module2SheetInstance){
          def result=[:] 
        def sampleIdList=[]
      
        def list=[module2SheetInstance.sampleQc, module2SheetInstance.sampleE, module2SheetInstance.sampleF, module2SheetInstance.sampleG, module2SheetInstance.sampleH]
        list.each{         
            def sampleId4Record=it.sampleId4Record
            if(sampleId4Record){
                sampleIdList.add(sampleId4Record)
            }
        }
         
        sampleIdList.add(module2SheetInstance.sampleFr.sampleId4Frozen)
        
         result.put("module2", sampleIdList)
        return result
    }
    
    
     Map getSampleListModule3N(module3NSheetInstance){
        def result=[:]
        def sampleIdList=[]
      
         
        if(module3NSheetInstance){
            def sampleId4Record=module3NSheetInstance.sampleQc.sampleId4Record

           if(sampleId4Record){
                sampleIdList.add(sampleId4Record)
           }


           def list=[module3NSheetInstance.sampleFr, module3NSheetInstance.sampleI, module3NSheetInstance.sampleJ, module3NSheetInstance.sampleK, module3NSheetInstance.sampleL, module3NSheetInstance.sampleM, module3NSheetInstance.sampleN]
           list.each{         
               def sampleId4Frozen=it.sampleId4Frozen
               if(sampleId4Frozen){
                   sampleIdList.add(sampleId4Frozen)
               }
           }

           result.put("module3", sampleIdList)
        }
        return result
    }
    
    Map getSampleListModule4N(module4NSheetInstance){
        def result=[:]
        def sampleIdList=[]
        
        if(module4NSheetInstance){
          def sampleId4Record=module4NSheetInstance.sampleQc.sampleId4Record
          
        if(sampleId4Record){
             sampleIdList.add(sampleId4Record)
        }
        
         def list=[module4NSheetInstance.sampleFr, module4NSheetInstance.sampleO, module4NSheetInstance.sampleP, module4NSheetInstance.sampleQ, module4NSheetInstance.sampleR, module4NSheetInstance.sampleS, module4NSheetInstance.sampleT]
        
        list.each{         
            def sampleId4Frozen=it.sampleId4Frozen
            if(sampleId4Frozen){
                sampleIdList.add(sampleId4Frozen)
            }
        }
        
        result.put("module4", sampleIdList)
        }
        
        return result
      
    }
    
    
    Map getSampleListModule5(module5SheetInstance){
        def result=[:]
        def sampleIdList=[]
        
        if(module5SheetInstance){
          def sampleId4Record=module5SheetInstance.sampleQc.sampleId4Record
          
        if(sampleId4Record){
             sampleIdList.add(sampleId4Record)
        }
        
         def list=[module5SheetInstance.sampleFr, module5SheetInstance.sampleU, module5SheetInstance.sampleV, module5SheetInstance.sampleW, module5SheetInstance.sampleX]
        
        list.each{         
            def sampleId4Frozen=it.sampleId4Frozen
            if(sampleId4Frozen){
                sampleIdList.add(sampleId4Frozen)
            }
        }
        
        result.put("module5", sampleIdList)
        }
        
        return result
      
    }
    
    Map getSampleListModule3(module3SheetInstance){
        def result=[:]
        def sampleIdList=[]
      
           
        def ntFfpeId1 = module3SheetInstance.ntFfpeId1
        if(ntFfpeId1)
        sampleIdList.add(ntFfpeId1)
           
        def ntFfpeId2 = module3SheetInstance.ntFfpeId2
        if(ntFfpeId2)
        sampleIdList.add(ntFfpeId2)
           
        def ntFfpeId3 = module3SheetInstance.ntFfpeId3
        if(ntFfpeId3)
        sampleIdList.add(ntFfpeId3)
           
        def ntFrozenId1 = module3SheetInstance.ntFrozenId1
        if(ntFrozenId1)
        sampleIdList.add(ntFrozenId1)
           
        def ntFrozenId2 = module3SheetInstance.ntFrozenId2
        if(ntFrozenId2)
        sampleIdList.add(ntFrozenId2)
           
        def ntFrozenId3 = module3SheetInstance.ntFrozenId3
        if(ntFrozenId3)
        sampleIdList.add(ntFrozenId3)
       
         result.put("normal adjacent tissue", sampleIdList)   
        return result
    }
  
    
    Map getSampleListModule4(module4SheetInstance){
          def result=[:]
        def sampleIdList=[]
      
        def ttFfpeId1 = module4SheetInstance.ttFfpeId1
        if(ttFfpeId1)
        sampleIdList.add(ttFfpeId1)
           
        def ttFfpeId2 = module4SheetInstance.ttFfpeId2
        if(ttFfpeId2)
        sampleIdList.add(ttFfpeId2)
           
        def ttFfpeId3 = module4SheetInstance.ttFfpeId3
        if(ttFfpeId3)
        sampleIdList.add(ttFfpeId3)
           
        def ttFrozenId1 = module4SheetInstance.ttFrozenId1
        if(ttFrozenId1)
        sampleIdList.add(ttFrozenId1)
           
        def ttFrozenId2 = module4SheetInstance.ttFrozenId2
        if(ttFrozenId2)
        sampleIdList.add(ttFrozenId2)
           
        def ttFrozenId3 = module4SheetInstance.ttFrozenId3
        if(ttFrozenId3)
        sampleIdList.add(ttFrozenId3)
        
       // println("additional tumor tissue size: " + sampleIdList.size())
           
        result.put("additional tumor tissue", sampleIdList)
            
        return result
    }
     
    boolean workSheetStarted(bpvWorkSheetInstance){
        def module1Sheet = bpvWorkSheetInstance.module1Sheet
        def module2Sheet = bpvWorkSheetInstance.module2Sheet
        def module3NSheet= bpvWorkSheetInstance.module3NSheet
        def module4NSheet= bpvWorkSheetInstance.module4NSheet
        def module3sheet = bpvWorkSheetInstance.module3Sheet
        def module4sheet = bpvWorkSheetInstance.module4Sheet
        def module5Sheet = bpvWorkSheetInstance.module5Sheet
        if(!bpvWorkSheetInstance.formVersion || bpvWorkSheetInstance.formVersion < 2){
            if(bpvWorkSheetInstance.version > 0 || module1Started(module1Sheet) || module2Started(module2Sheet) || module3NStarted(module3NSheet) || module4NStarted(module4NSheet)  || module3sheet.version > 0 || module4sheet.version > 0 )
            return true
            else
            return false
        }else{
                 if(bpvWorkSheetInstance.version > 0 || module1Started(module1Sheet) || module2Started(module2Sheet)  || module5Started(module5Sheet)  || module3sheet.version > 0 || module4sheet.version > 0 )
            return true
            else
            return false
        }
    }
    
    
  
    
    boolean module1Started(module1SheetInstance){
        if(module1SheetInstance.version > 0 || module1SheetInstance.sampleA.version > 0 || module1SheetInstance.sampleB.version > 0 || module1SheetInstance.sampleC.version > 0 || module1SheetInstance.sampleD.version > 0 || module1SheetInstance.sampleFr.version > 0 || module1SheetInstance.sampleQc.version > 0){
            return true
        }else {
            return false
        }
    }
    
    
    boolean module2Started(module2SheetInstance){
        if(module2SheetInstance.version > 0  || module2SheetInstance.sampleE.version > 0 || module2SheetInstance.sampleF.version > 0 || module2SheetInstance.sampleG.version > 0 || module2SheetInstance.sampleH.version > 0 || module2SheetInstance.sampleFr.version > 0 || module2SheetInstance.sampleQc.version > 0){
            return true
        }else {
            return false
        }
    }
    
    boolean module3NStarted(module3NSheetInstance){
        if(module3NSheetInstance.version > 0  || module3NSheetInstance.sampleI.version > 0 || module3NSheetInstance.sampleJ.version > 0 || module3NSheetInstance.sampleK.version > 0 || module3NSheetInstance.sampleL.version  > 0 ||  module3NSheetInstance.sampleM.version > 0 || module3NSheetInstance.sampleN.version > 0 || module3NSheetInstance.sampleFr.version > 0 || module3NSheetInstance.sampleQc.version > 0){
            return true
        }else {
            return false
        }
    }
    
    
     boolean module4NStarted(module4NSheetInstance){
        if(module4NSheetInstance.version > 0  || module4NSheetInstance.sampleO.version > 0 || module4NSheetInstance.sampleP.version > 0 || module4NSheetInstance.sampleQ.version > 0 || module4NSheetInstance.sampleR.version  > 0 ||  module4NSheetInstance.sampleS.version > 0 || module4NSheetInstance.sampleT.version > 0 || module4NSheetInstance.sampleFr.version > 0 || module4NSheetInstance.sampleQc.version > 0){
            return true
        }else {
            return false
        }
    }
    
    boolean module5Started(module5SheetInstance){
        if(module5SheetInstance.version > 0  || module5SheetInstance.sampleU.version > 0 || module5SheetInstance.sampleV.version > 0 || module5SheetInstance.sampleW.version > 0 || module5SheetInstance.sampleX.version  > 0 || module5SheetInstance.sampleFr.version > 0 || module5SheetInstance.sampleQc.version > 0){
            return true
        }else {
            return false
        }
    }
    
    def submit = {
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
       
        bpvWorkSheetService.submitForm(bpvWorkSheetInstance)
        
        //pmh new 06/17/13
        bpvWorkSheetService.updateSpecimenTumorStatus(bpvWorkSheetInstance.caseRecord)
        //pmh
        
        bpvWorkSheetInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        // tissueRecoveryBmsInstance.dateSubmitted = new Date()
        // tissueRecoveryBmsInstance.save(failOnError:true)
           
        //render(view: "edit", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance])
         redirect(action: "show", id:params.id)
       // redirect(controller:"caseRecord", action: "display", id:bpvWorkSheetInstance.caseRecord.id)
            
      
    }
    
  
    
    def restart ={
       // println("restart : " + params.id)
        def bpvWorkSheetInstance = BpvWorkSheet.get(params.id)
        bpvWorkSheetInstance.dateSubmitted = null
        bpvWorkSheetInstance.submittedBy = null
        bpvWorkSheetInstance.save(failOnError:true)
        redirect(action: "edit", id:params.id)
    }
    
    
    
    def migrate = {
        println("started...")
        def caseList = CaseRecord.findAllByStudy(Study.findByCode('BPV'))
        
        println("size: " + caseList.size())
        
       // def caseList = CaseRecord.findAllByCaseId('BPV-00001')
        
        
        caseList.each(){
            def bpvWorkSheetInstance = it.bpvWorkSheet
            if(bpvWorkSheetInstance){
                println("processing " + it.caseId)
                def m5s = bpvWorkSheetInstance.module5Sheet
                if(!m5s){
                      m5s = new Module5Sheet()
                 m5s.bpvWorkSheet = bpvWorkSheetInstance
                 m5s.module = Module.findByCode('MODULE5')
            
            
                def  sampleFr5 = new Timepoint()
                sampleFr5.save(failOnError:true)
                m5s.sampleFr = sampleFr5

                def sampleQc5 = new Timepoint()

                sampleQc5.protocol='QC FFPE:<br/>23 hours in fixative'
                sampleQc5.plannedDelay='<1 hour'

                sampleQc5.protocolId="protocol_qc"
                sampleQc5.save(failOnError:true)
                m5s.sampleQc = sampleQc5


                def sampleU = new Timepoint()
                sampleU.protocol='Protocol U'
                sampleU.freezeMethod='Dry Ice'
                sampleU.transTo='-80&deg;C Freezer'
                sampleU.save(failOnError:true)
                m5s.sampleU = sampleU

                def sampleV = new Timepoint()
                sampleV.protocol='Protocol V'
                sampleV.freezeMethod='Dry Ice'
                sampleV.transTo='LN Freezer'
                sampleV.save(failOnError:true)
                m5s.sampleV = sampleV

                def sampleW = new Timepoint()
                sampleW.protocol='Protocol W'
                sampleW.freezeMethod='LN2 Vapor Phase'
                sampleW.transTo='-80&deg;C Freezer'
                sampleW.save(failOnError:true)
                m5s.sampleW = sampleW

                def sampleX = new Timepoint()
                sampleX.protocol='Protocol X'
                sampleX.freezeMethod='LN2 Vapor Phase'
                sampleX.transTo='LN Freezer'
                sampleX.save(failOnError:true)
                m5s.sampleX = sampleX


             
           
                m5s.save(failOnError:true)
            
                }
            }
            }
               
        
        println("data migration is done.")
        render("done.")
    }
    
    
    
    Map getWarningMap(intervalMap1, intervalMap2) {
        Map warningMap = [:]
        def intervalKey
        def intervalVal
        def type
        def timepointId
        def h
        def m
        def specimenId
        def protocolId
        
        for (interval in intervalMap1) {
            if (interval.value != '&nbsp;') {
                intervalVal = interval.value?.split(':')
                h = intervalVal[0]?.toInteger()
                m = intervalVal[1]?.toInteger()
                intervalKey = interval.key?.split('_')
                type = intervalKey[0]
                timepointId = intervalKey[1]
                specimenId = Timepoint.get(timepointId)?.sampleId4Record
                protocolId = Timepoint.get(timepointId)?.protocolId

                if (type == 'interval1') {
                    if (h > 0) {
                       // println("specimenId: " + specimenId )
                        warningMap.put(interval.key, specimenId + ': Delay to fixation time is greater than 59 minutes')
                    }
                } else if (type == 'interval2') {
                    if (protocolId == 'protocol_a') {
                        if (!((h == 5 && m >= 50) || (h == 6 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol A is greater than &plusmn;10 minutes')
                        }
                    } else if (protocolId == 'protocol_b') {
                        if (!((h == 11 && m >= 50) || (h == 12 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol B is greater than &plusmn;10 minutes')
                        }
                    } else if (protocolId == 'protocol_c') {
                        if (!((h == 22 && m >= 50) || (h == 23 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol C is greater than &plusmn;10 minutes')
                        }
                    } else if (protocolId == 'protocol_d') {
                        if (!((h == 71 && m >= 50) || (h == 72 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol D is greater than &plusmn;10 minutes')
                        }
                    }
                }
            }
        }
        
        for (interval in intervalMap2) {
            if (interval.value != '&nbsp;') {
                intervalVal = interval.value?.split(':')
                h = intervalVal[0]?.toInteger()
                m = intervalVal[1]?.toInteger()
                intervalKey = interval.key?.split('_')
                type = intervalKey[0]
                timepointId = intervalKey[1]
                specimenId = Timepoint.get(timepointId)?.sampleId4Record
                protocolId = Timepoint.get(timepointId)?.protocolId

                if (type == 'interval1') {
                    if (protocolId == 'protocol_e') {
                        if (!((h == 0 && m >= 50) || (h == 1 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between delay to fixation time and planned delay to fixation time is greater than &plusmn;10 minutes')
                        }
                    } else if (protocolId == 'protocol_f') {
                        if (!((h == 1 && m >= 50) || (h == 2 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between delay to fixation time and planned delay to fixation time is greater than &plusmn;10 minutes')
                        }
                    } else if (protocolId == 'protocol_g') {
                        if (!((h == 2 && m >= 50) || (h == 3 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between delay to fixation time and planned delay to fixation time is greater than &plusmn;10 minutes')
                        }
                    } else if (protocolId == 'protocol_h') {
                        if (!((h == 11 && m >= 50) || (h == 12 && m <= 10))) {
                            warningMap.put(interval.key, specimenId + ': Difference between delay to fixation time and planned delay to fixation time is greater than &plusmn;10 minutes')
                        }
                    }
                } else if (type == 'interval2') {
                    if (protocolId == 'protocol_e') {
                        if (!((h == 11 && m >= 30) || (h == 12 && m <= 30))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol E is greater than &plusmn;30 minutes')
                        }
                    } else if (protocolId == 'protocol_f') {
                        if (!((h == 10 && m >= 30) || (h == 11 && m <= 30))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol F is greater than &plusmn;30 minutes')
                        }
                    } else if (protocolId == 'protocol_g') {
                        if (!((h == 9 && m >= 30) || (h == 10 && m <= 30))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol G is greater than &plusmn;30 minutes')
                        }
                    } else if (protocolId == 'protocol_h') {
                        if (!((h == 11 && m >= 30) || (h == 12 && m <= 30))) {
                            warningMap.put(interval.key, specimenId + ': Difference between actual time in fixative and Protocol H is greater than &plusmn;30 minutes')
                        }
                    }
                }
            }
        }
        
        return warningMap
    }
   
    
    Map getWarningMap2(sheet, caseId) {
        def map=[:]
        def list=[]
        def list2=[]
        def map3=[:]
      if(sheet instanceof Module1Sheet){
           list = [sheet.sampleQc,sheet.sampleA, sheet.sampleB, sheet.sampleC,sheet.sampleD] 
           list2=[sheet.sampleFr]
      } else if  (sheet instanceof Module2Sheet){
          list = [sheet.sampleQc,sheet.sampleE, sheet.sampleF, sheet.sampleG,sheet.sampleH] 
           list2=[sheet.sampleFr]
      }else if(sheet instanceof Module3NSheet){
          list = [sheet.sampleQc]
          list2 = [sheet.sampleFr, sheet.sampleI, sheet.sampleJ, sheet.sampleK, sheet.sampleL, sheet.sampleM, sheet.sampleN]
      }else if(sheet instanceof Module4NSheet){
          list = [sheet.sampleQc]
          list2 = [sheet.sampleFr, sheet.sampleO, sheet.sampleP, sheet.sampleQ, sheet.sampleR, sheet.sampleS, sheet.sampleT]
      }else if(sheet instanceof Module3Sheet){
          map3.put("ntFfpeId1", sheet.ntFfpeId1)
          map3.put("ntFfpeId2", sheet.ntFfpeId2)
          map3.put("ntFfpeId3", sheet.ntFfpeId3)
          map3.put("ntFrozenId1", sheet.ntFrozenId1)
          map3.put("ntFrozenId2", sheet.ntFrozenId2)
          map3.put("ntFrozenId3", sheet.ntFrozenId3)
        
      }else if(sheet instanceof Module4Sheet){
          map3.put("ttFfpeId1", sheet.ttFfpeId1)
          map3.put("ttFfpeId2", sheet.ttFfpeId2)
          map3.put("ttFfpeId3", sheet.ttFfpeId3)
          map3.put("ttFrozenId1", sheet.ttFrozenId1)
          map3.put("ttFrozenId2", sheet.ttFrozenId2)
          map3.put("ttFrozenId3", sheet.ttFrozenId3)
        
      }else{
         list = [sheet.sampleQc]
          list2 = [sheet.sampleFr, sheet.sampleU, sheet.sampleV, sheet.sampleW, sheet.sampleX]  
      }

      list.each(){
          def id = it?.sampleId4Record
          if(id ){
              if(id =~   "^" +caseId + "-[0-9][0-9]\$" ){
                  
              }else{
                  map.put("sampleId_"+ it.id, id + " not in format [case id]-XX")
              }
              
          }
      }
      
        
     list2.each(){
         def id=it.sampleId4Frozen
         if(id){
              if(id =~   "^" +caseId + "-[0-9][0-9]\$" ){
                  
              }else{
                  map.put("sampleId_"+ it.id, id + " not in format [case id]-XX")
              }
         }
     }
     
        
     map3.each(){key, value->
         if(value){
             if(value =~   "^" +caseId + "-[0-9][0-9]\$" ){
                  
              }else{
                  map.put(key, value + " not in format [case id]-XX")
              }
         }
     }
     
      return map
    }
 
}
