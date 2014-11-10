package nci.obbr.cahub.forms.bms

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.*
import java.text.SimpleDateFormat
import grails.plugins.springsecurity.Secured

class TissueRecoveryBmsController {
    def bmsService
    def accessPrivilegeService
    
    static allowedMethods = [ update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [tissueRecoveryBmsInstanceList: TissueRecoveryBms.list(params), tissueRecoveryBmsInstanceTotal: TissueRecoveryBms.count()]
    }

    def create = {
        def tissueRecoveryBmsInstance = new TissueRecoveryBms()
        tissueRecoveryBmsInstance.properties = params
        return [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance]
    }

    def save = {
        def tissueRecoveryBmsInstance = new TissueRecoveryBms(params)
        
          try{
               bmsService.saveTrf(tissueRecoveryBmsInstance)
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms'), tissueRecoveryBmsInstance.id])}"
                redirect(action: "edit", id: tissueRecoveryBmsInstance.id)
              
          }catch(Exception e){
            flash.message="Failed: " + e.toString() 
             render(view: "create", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance])
       
          }
        
      
    }

    def show = {
        def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
        if (!tissueRecoveryBmsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance]
        }
    }
    
     def view = {
       
        def tissueRecoveryBmsInstance = TissueRecoveryBms.findById(params.id)
        
        def caseRecord = tissueRecoveryBmsInstance?.caseRecord
           
           int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')
           if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
           }
        
        
        def ccDateTime = bmsService.getCardiatCessTime(tissueRecoveryBmsInstance)
        def specimenMap2 =new TreeMap(bmsService.getSpecimensFull(tissueRecoveryBmsInstance, '1'))
        def specimenMap4 =new TreeMap(bmsService.getSpecimensFull(tissueRecoveryBmsInstance, '4'))
        def specimenMap6 =new TreeMap(bmsService.getSpecimensFull(tissueRecoveryBmsInstance, '6'))
        def specimenMap15 =new TreeMap(bmsService.getSpecimensFull(tissueRecoveryBmsInstance, '15'))
        
        
        [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance, specimens2:specimenMap2.values(), specimens4:specimenMap4.values(), specimens6:specimenMap6.values(), specimens15:specimenMap15.values(), ccDateTime:ccDateTime ]
        
    }

    def edit = {
               def canSubmit='No'
               def needWarning4=false
               def needWarning6=false
               def needWarning15=false
         
            
               def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
               
               def caseRecord = tissueRecoveryBmsInstance?.caseRecord
               int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
                if (accessPrivilege > 0) {
                     redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                     return
                }
//              if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//             }
        
               def tissueList =bmsService.getTissueList(tissueRecoveryBmsInstance)
              
        
               def ccDateTime = bmsService.getCardiatCessTime(tissueRecoveryBmsInstance)
               /*if(tissueRecoveryBmsInstance){
                   def caseId = tissueRecoveryBmsInstance.caseRecord.caseId
                   println("not null: " + caseId)
               }else{
                   println("null")
               }*/
               
               def version = tissueRecoveryBmsInstance.version
                if(version == 0){
                     //println("version is 0")
                     //bmsService.updateTrf(tissueRecoveryBmsInstance, params)
                     return [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance, canSubmit:canSubmit, ccDateTime:ccDateTime, tissueList:tissueList]
                
               }else{
                   
                      def result= checkError(tissueRecoveryBmsInstance)
                      if(result){
                         // println("has result....")
                           result.each(){key,value->
                             // println("it: " + it)
                            tissueRecoveryBmsInstance.errors.rejectValue(key, value)
                            if(key=='protocol1hStarted'){
                                needWarning4=true
                                needWarning6=true
                                needWarning15=true
                            }
                            
                            if(key=='protocol4hStarted'){
                                needWarning6=true
                                needWarning15=true
                            }
                            
                             if(key=='protocol6hStarted'){
                                needWarning15=true
                            }
                    
                            
                            
                         }//each
                          return [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance, canSubmit:canSubmit, ccDateTime:ccDateTime, tissueList:tissueList,needWarning4:needWarning4, needWarning6:needWarning6, needWarning15:needWarning15 ]
                      }else{
                         // println("do not have result....")
                         canSubmit="Yes"
                       return [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance, canSubmit:canSubmit, ccDateTime:ccDateTime, tissueList:tissueList, needWarning4:needWarning4, needWarning6:needWarning6, needWarning15:needWarning15] 
                      }
                  
                }
               
              
        
     
    }
   
    def restart ={
        def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
        tissueRecoveryBmsInstance.dateSubmitted= null
        tissueRecoveryBmsInstance.save(failOnError:true)
        redirect(action: "edit", id:params.id)
    }
    
   def edit2 = {
         def timedelay = params.time_delay
      
         def started
         def errorMap=[:]
         
      def tissueRecoveryBmsInstance = TissueRecoveryBms.findById(params.id)
      
       def caseRecord = tissueRecoveryBmsInstance?.caseRecord
       
           int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
           if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                return
           }
//              if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//       }
        
        
        try{
            bmsService.updateTrf2(params)
            
            
              if(params.time_delay == '1')
                   started=tissueRecoveryBmsInstance.protocol1hStarted
                if(params.time_delay == '4')
                   started=tissueRecoveryBmsInstance.protocol4hStarted
                
                if(params.time_delay == '6')
                   started=tissueRecoveryBmsInstance.protocol6hStarted
                
                if(params.time_delay == '15')
                   started=tissueRecoveryBmsInstance.protocol15hStarted
                   
            if(started){
                def result2 =checkErrorTime(tissueRecoveryBmsInstance, params.time_delay)
                if(result2){
                     result2.each(){key,value->
                             // println("it: " + it)
                            tissueRecoveryBmsInstance.errors.reject(key, value)
                            errorMap.put(key, "errors")
                     }//each
                }
                
            }
          
          
            def specimenMap =new TreeMap(bmsService.getSpecimens(tissueRecoveryBmsInstance, timedelay))
            //def specimenMap =bmsService.getSpecimens(tissueRecoveryBmsInstance, timedelay)
            def dateList = bmsService.getDateList(tissueRecoveryBmsInstance)
            def cc=bmsService.getCardiatCessTime(tissueRecoveryBmsInstance)
           
            def ccTime
            if(cc)
               ccTime=cc.getTime()
             
            def ccString
             SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
             if(cc)
               ccString=df.format(cc)
               
           // println("ccString: " + ccString)
             
            render(view: "edit_time", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance, specimens:specimenMap.values(), dateList:dateList, timedelay:timedelay, ccTime:ccTime, errorMap:errorMap, ccDateTime:cc, ccString:ccString  ])
         // render "testin....."
         }catch(Exception e){
             e.printStackTrace()
           // def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
            def specimenMap =new TreeMap(bmsService.getSpecimens(tissueRecoveryBmsInstance, timedelay))
            def dateList = bmsService.getDateList(tissueRecoveryBmsInstance)
            def cc=bmsService.getCardiatCessTime(tissueRecoveryBmsInstance)
            def ccTime
            if(cc)
               ccTime=cc.getTime()
            flash.message="Failed: " + e.toString()  
         
             def ccString
             SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
             if(cc)
               ccString=df.format(cc)
               
              render(view: "edit_time", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance, specimens:specimenMap.values(), dateList:dateList, timedelay:timedelay, ccTime:ccTime, errorMap:errorMap, ccDateTime:cc, ccString:ccString  ])
        
           
          }
       
      
    }

    
    
    def update = {
        def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
        if (tissueRecoveryBmsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tissueRecoveryBmsInstance.version > version) {
                    
                    tissueRecoveryBmsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms')] as Object[], "Another user has updated this TissueRecoveryBms while you were editing")
                    render(view: "edit", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance])
                    return
                }
            }
            tissueRecoveryBmsInstance.properties = params
            bmsService.updateTrf(tissueRecoveryBmsInstance, params)
           
           //render(view: "edit", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance])
           redirect(action: "edit", id:params.id)
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms'), params.id])}"
            redirect(action: "list")
        }
    }

    
    def submit = {
        def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
       
         tissueRecoveryBmsInstance.dateSubmitted = new Date()
         tissueRecoveryBmsInstance.save(failOnError:true)
           
           //render(view: "edit", model: [tissueRecoveryBmsInstance: tissueRecoveryBmsInstance])
        redirect(action: "view", id:params.id)
            
      
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])     
    def delete = {
        def tissueRecoveryBmsInstance = TissueRecoveryBms.get(params.id)
        if (tissueRecoveryBmsInstance) {
            try {
                tissueRecoveryBmsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
     Map checkError(tissueRecoveryBmsInstance){
        def result = [:]
        
        def dateSampleLeave=tissueRecoveryBmsInstance.dateSampleLeave
        if(!dateSampleLeave){
            result.put("dateSampleLeave", "The date tissue samples leave collection site is a required field.")
        }
        
        def timeSampleLeave=tissueRecoveryBmsInstance.timeSampleLeave
        if(!timeSampleLeave){
            result.put("timeSampleLeave", "The time tissue samples leave collection site is a required field.")
        }
        
        def dateSampleArrive=tissueRecoveryBmsInstance.dateSampleArrive
        if(!dateSampleArrive){
            result.put("dateSampleArrive", "The Date tissue samples arrive at processing site is a required field.")
        }
        
        def timeSampleArrive=tissueRecoveryBmsInstance.timeSampleArrive
        if(!timeSampleArrive){
            result.put("timeSampleArrive", "The time tissue samples arrive at processing site is a required field.")
        }
        
        def envTemperature=tissueRecoveryBmsInstance.envTemperature
        if(!envTemperature){
             result.put("envTemperature", "The environment temperature  is a required field.")
        }
        
      
          def skinContiguous =tissueRecoveryBmsInstance.skinContiguous
        if(!skinContiguous){
             result.put("skinContiguous", "Please specify if Skin is contiguous to the GTEx aliquot")
        }
        
         def muscleContiguous =tissueRecoveryBmsInstance.muscleContiguous
        if(!muscleContiguous){
             result.put("muscleContiguous", "Please specify if Muscle, Skeletal is contiguous to the GTEx aliquot")
        }
        
         def aortaContiguous=tissueRecoveryBmsInstance.aortaContiguous
        if(!aortaContiguous){
             result.put("aortaContiguous", "Please specify if Aorta is contiguous to the GTEx aliquot")
        }
        
         def pancreasContiguous=tissueRecoveryBmsInstance.pancreasContiguous
        if(!pancreasContiguous){
             result.put("pancreasContiguous", "Please specify if Pancreas is contiguous to the GTEx aliquot")
        }
        
        
         def adrenalContiguous =tissueRecoveryBmsInstance.adrenalContiguous
        if(!adrenalContiguous){
             result.put("adrenalContiguous", "Please specify if Adrenal Glands is contiguous to the GTEx aliquot")
        }
        
        
         def thyroidContiguous =tissueRecoveryBmsInstance.thyroidContiguous
        if(!thyroidContiguous){
             result.put("thyroidContiguous", "Please specify if Thyroid Gland is contiguous to the GTEx aliquot")
        }
        
        
        
        def protocol1hStarted = tissueRecoveryBmsInstance.protocol1hStarted
        if(!protocol1hStarted){
            result.put("protocol1hStarted", "The 1 hour delay timepoint record has not been entered")
        }else{
            def result2 =checkErrorTime(tissueRecoveryBmsInstance, '1')
            if(result2){
                result.put("protocol1hStarted", "Data errors in 1 hour delay timepoint records")
           }
            
            
        }
        
        def protocol4hStarted = tissueRecoveryBmsInstance.protocol4hStarted
        
         if(!protocol4hStarted){
            result.put("protocol4hStarted", "The 4 hour delay timepoint record has not been entered")
        }else{
             def result2 =checkErrorTime(tissueRecoveryBmsInstance, '4')
            if(result2){
                result.put("protocol4hStarted", "Data errors in 4 hour delay timepoint records")
           }
        }
        
        
        
         def protocol6hStarted = tissueRecoveryBmsInstance.protocol6hStarted
        
         if(!protocol6hStarted){
            result.put("protocol6hStarted", "The 6 hour delay timepoint record has not been entered")
        }else{
            def result2 =checkErrorTime(tissueRecoveryBmsInstance, '6')
            if(result2){
                result.put("protocol6hStarted", "Data errors in 6 hour delay timepoint records")
           }
        }
        
        
         def protocol15hStarted = tissueRecoveryBmsInstance.protocol15hStarted
        
         if(!protocol15hStarted){
            result.put("protocol15hStarted", "The 15 hour delay timepoint record has not been entered")
        }else{
            def result2 =checkErrorTime(tissueRecoveryBmsInstance, '15')
            if(result2){
                result.put("protocol15hStarted", "Data errors in 15 hour delay timepoint records")
           }
        }
        
        
          def dateStabilized=tissueRecoveryBmsInstance.dateStabilized
        if(!dateStabilized){
             result.put("dateStabilized", "The date placed in stablilizer is a required field.")
        }
        
        def timeStabilized=tissueRecoveryBmsInstance.timeStabilized
        if(!timeStabilized){
             result.put("timeStabilized", "The time placed in stablilizer is a required field.")
        }
        
        
         def dateInDewarLn2=tissueRecoveryBmsInstance.dateInDewarLn2
        if(!dateInDewarLn2){
             result.put("dateInDewarLn2", "The date aliquot held in dewar for LN2 is a required field.")
        }
        
        def timeInDewarLn2=tissueRecoveryBmsInstance.timeInDewarLn2
        if(!timeInDewarLn2){
             result.put("timeInDewarLn2", "The time aliquot held in dewar for LN2 is a required field.")
        }
        
          def dateInDewarDice=tissueRecoveryBmsInstance.dateInDewarDice
        if(!dateInDewarDice){
             result.put("dateInDewarDice", "The date aliquot held in dewar for Dry Ice is a required field.")
        }
        
        def timeInDewarDice=tissueRecoveryBmsInstance.timeInDewarDice
        if(!timeInDewarDice){
             result.put("timeInDewarDice", "The time aliquot held in dewar for Dry Ice is a required field.")
        }
        
       /* def timeInDewar=tissueRecoveryBmsInstance.timeInDewar  
        if(timeInDewar){
            def pattern = ~/\d+:[0,1,2,3,4,5]\d/
            if(!pattern.matcher(timeInDewar))
              result.put("timeInDewar", "Wrong format for time aliquot held in dewar.")
        }*/
        
        return result
        
     }
    
    
     Map checkErrorTime(tissueRecoveryBmsInstance, hour){
        
       def result=[:] 
       def map = bmsService.getSpecimens(tissueRecoveryBmsInstance, hour)
       
       map.each(){key,value->
          def ids = value.ids
          def idList = ids.split("_")
          def id = idList[0]
          def specimenRecord = SpecimenRecord.findById(id)
         
          def specimenIds = (value.specimenIds).replace('<br/>', ',')
          
           
          // if(specimenRecord.parentSpecimen?.tissueType.name=='Aorta' && !value.tissueLoc){
        //  if(specimenRecord.tissueType.name=='Aorta' && !value.tissueLoc){
        if( !value.tissueLoc){
              result.put("tissueLocation_" + ids, "The tissue location  for " + specimenIds + " is a required field")
              
          }
          
          //  if(specimenRecord.parentSpecimen?.tissueType.name=='Aorta' && value.tissueLoc==28){
         // if(specimenRecord.tissueType.name=='Aorta' && value.tissueLoc==28){
         if(value.tissueLoc==28){
                def otherLoc =value.otherTissueLoc
                if(!otherLoc)
                    result.put("tissueLocation_" + ids, "The other tissue location  for " + specimenIds + " is a required field if the 'Other, specify' is selected")
              
          }
          
          // if(specimenRecord.parentSpecimen?.tissueType.name=='Aorta' && value.tissueLoc){
             if(specimenRecord.tissueType.name=='Aorta' && value.tissueLoc){  
               def locationCode=AcquisitionLocation.findById(value.tissueLoc)?.code
               if(locationCode == 'COMBAORT' && !value.comments){
                   result.put("comments_" + ids, "The comments  for " + specimenIds + " is a required field if the location type is combination")
               }
           }
          
          def date1 = value.date1
          def time1 = value.time1
          def date3 = value.date3
          def time3 = value.time3
          def date2 = value.date2
          def time2 = value.time2
          def tissue_cons=value.tissue_cons
          if(!date1){
              result.put("date1_" + ids, "The date removed 2 for " + specimenIds + " is a required field")
          }
            if(!time1){
              result.put("time1_" + ids, "The time removed 2 for " + specimenIds + " is a required field")
          }
          
           if(!date3){
              result.put("date3_" + ids, "The date removed 1 for " + specimenIds + " is a required field")
          }
            if(!time3){
              result.put("time3_" + ids, "The time removed 1 for " + specimenIds + " is a required field")
          }
          
           if(!date2){
              result.put("date2_" + ids, "The fixation start date for " + specimenIds + " is a required field")
          }
          
          if(!time2){
              result.put("time2_" + ids, "The fixation start time for " + specimenIds + " is a required field")
          }
          
         if(!tissue_cons){
             result.put("tissue_cons_" + ids, "The tissue consistency for " + specimenIds + " is a required field")
         }
       }
       
            return result
        
    }
}
