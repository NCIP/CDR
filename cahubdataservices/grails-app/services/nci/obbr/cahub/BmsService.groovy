package nci.obbr.cahub

import nci.obbr.cahub.forms.bms.*
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import java.text.SimpleDateFormat

class BmsService {
    def ldaccService
    static transactional = true
    
    //need implement later
    def getCardiatCessTime(tissueRecoveryBmsInstance){
           def c = tissueRecoveryBmsInstance.caseRecord.parentCase
           def opoType=c.caseReportForm?.deathCircumstances?.opoType
          // println("opoType: " + opoType)
               if(opoType){
                   if(opoType=='BD'){
                       def collectionDate = c.tissueRecoveryGtex?.collectionDate
                       def startTime = c.tissueRecoveryGtex?.collectionStartTime
                       def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
                       def cross_clamp_time = c.tissueRecoveryGtex?.crossClampTime
                       def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)
                       return crossClampDateTime
                   }
               if(opoType=='DCD'){
                 return c.caseReportForm?.deathCircumstances?.dateTimePronouncedDead
               }
        
           }
           
           return null
           
           
    }

     def convertToHHMM(millsec){
         def posMillsec
         if(millsec < 0)
            posMillsec = (-1)*millsec
         else
           posMillsec =millsec
            
         int hour = posMillsec/(60*60*1000)
         int min = posMillsec/(60*1000) -hour*60
         
            def str=''
            if(hour < 10 && hour >= 0)
              str='0' + hour
            else
              str = str + hour
              
           
            
            def str2=''
             if(min < 10 && min >=0)
              str2='0' + min
            else
              str2 = str2 + min
          
        if(millsec < 0)
          return "-" +str+":"+str2
        else
          return str+":"+str2
        
    }
    
    def saveTrf(tissueRecoveryBmsInstance) {
        // println("start save???")
        try{
            tissueRecoveryBmsInstance.save(failOnError:true)
            def list = SpecimenRecord.findAllByCaseRecord(tissueRecoveryBmsInstance.caseRecord)
            list.each{
                def chpTissueRecord = new ChpTissueRecord()
                chpTissueRecord.specimenRecord = it
                chpTissueRecord.save(failOnError:true)
            }
            def c = tissueRecoveryBmsInstance.caseRecord
            c.caseStatus = CaseStatus.findByCode('DATA')
            c.save(failOnError:true)
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }

    }
    
    def getSpecimens(tissueRecoveryBmsInstance, hour){
       def cc=getCardiatCessTime(tissueRecoveryBmsInstance) 
      try{
        def protocol
        if(hour == '1'){
            protocol = Protocol.findByCode("A")
        }else if(hour == '4'){
            protocol = Protocol.findByCode("B")
        }else if (hour == '6'){
            protocol = Protocol.findByCode("C")
        }else{
            protocol = Protocol.findByCode("D")
        }
        
        def caseRecord = tissueRecoveryBmsInstance.caseRecord
        def list = SpecimenRecord.findAllByCaseRecordAndProtocol(caseRecord, protocol)
        //println("list size: " + list.size())
        def parentCase =caseRecord.parentCase
       
        def firstTissueRemovedDate = parentCase?.tissueRecoveryGtex?.firstTissueRemovedDate
        def firstTissueRemovedTime = parentCase?.tissueRecoveryGtex?.firstTissueRemovedTime
        def firstTissueRemovedDateTime = ldaccService.calculateDateWithTime(firstTissueRemovedDate,firstTissueRemovedTime)
        
            
            
            
        def parentList = SpecimenRecord.findAllByCaseRecord(parentCase)
        def parentMap=[:]
        parentList.each{
            def tissueCode = it.tissueType.code
            def aliquotTimeRemoved=it.aliquotTimeRemoved
            def removedDateTime = ldaccService.getDateTimeAfter(firstTissueRemovedDateTime, aliquotTimeRemoved)
            
            if(removedDateTime  &&!parentMap.get(tissueCode))
               parentMap.put(tissueCode, ldaccService.getDateTimeString(removedDateTime))
            
        }
        def result=[:]
        list.each{
           // println("in loop....")
            def specimenId = it.specimenId
           // def tissueCode = it.parentSpecimen?.tissueType.code
            def tissueCode = it.tissueType.code
            def fixOrder = it.fixative.displayOrder
            def key = tissueCode + "_" + fixOrder + "_" + specimenId.substring(0, specimenId.length()-1)
            
            def map = result.get(key)
            if(!map){
                map=[:]
                result.put(key, map)
                map.put("ids", it.id)
                map.put("specimenIds", it.specimenId)
                //map.put("tissue", it.parentSpecimen?.tissueType.name)
                map.put("tissue", it.tissueType.name)
                map.put("tissueCode", it.tissueType.code)
               // if(it.parentSpecimen?.tissueType.name == 'Aorta'){
              /** if(it.tissueType.name == 'Aorta'){
                   // println("it.tissueLocation: " + it.tissueLocation)
                     map.put("tissueLoc", it.tissueLocation?.id)
                     map.put("otherTissueLoc", it.otherTissueLocation)
                }else{
                    if(it.parentSpecimen?.tissueLocation?.code=='OTHER')
                       map.put("tissueLoc", it.parentSpecimen?.otherTissueLocation)
                     else
                       map.put("tissueLoc", it.parentSpecimen?.tissueLocation?.name)
                }**/
                
                 map.put("tissueLoc", it.tissueLocation?.id)
                 map.put("otherTissueLoc", it.otherTissueLocation)
                 map.put("parentTissueLoc", it.parentSpecimen?.tissueLocation?.id)
               // def tissueCode = it.tissueType.code
                //map.put("timeRemoved1",parentMap.get(tissueCode))
               // map.put("timeRemoved1",it.parentSpecimen?.aliquotTimeRemoved)
               def parantDateTime=parentMap.get(tissueCode)
               if(parantDateTime){
                    map.put("pdate3", parantDateTime[0])
                    map.put("ptime3", parantDateTime[1])
                    
                      //  println("ptme3: " + parantDateTime[0])
                   
                   
               }
                map.put("size", it.sizeDiffThanSOP)
                map.put("comments", it.prosectorComments)
                map.put("date1", it.chpTissueRecord.procTimeRemovDate)
               // println("date1: " + it.chpTissueRecord.procTimeRemovDate )
                map.put("time1", it.chpTissueRecord.procTimeRemovTime)
                map.put("date3", it.chpTissueRecord.timeRemovFromBodyDate)
                map.put("time3", it.chpTissueRecord.timeRemovFromBodyTime)
                map.put("date2", it.chpTissueRecord.timeInFixDate)
                map.put("time2", it.chpTissueRecord.timeInFixTime)
                map.put("procTimeRemov", it.chpTissueRecord.procTimeRemov)
                map.put("timeInFix", it.chpTissueRecord.timeInFix)
                def timeInFix=it.chpTissueRecord.timeInFix
                if(timeInFix && cc){
                    def ftime = timeInFix.getTime()
                    def ccTime = cc.getTime()
                    def delay = convertToHHMM(ftime - ccTime)
                    map.put("delay", delay)
                }else{
                    map.put("delay", "")
                }
                map.put("tissue_cons", it.chpTissueRecord.tissueConsistency)
                map.put("fixative", it.fixative?.name)
               def locationList=(AcquisitionType.findByName(it.tissueType.name)).acqLocs
               def locationMap=[:]
               locationList.each{
                   locationMap.put(it.name, it)
               }
                if(it.tissueLocation && !locationMap.get(it.tissueLocation.name))
                  locationMap.put(it.tissueLocation.name, it.tissueLocation)
                  
                if(it.parentSpecimen?.tissueLocation && !locationMap.get(it.parentSpecimen?.tissueLocation.name) )   
                   locationMap.put(it.parentSpecimen?.tissueLocation.name, it.parentSpecimen?.tissueLocation)
                   
               def sortedMap = new TreeMap(locationMap)
               //println("good??")
               def sortedList=sortedMap.values()
               def finalList=[]
               sortedList.each{
                   finalList.add(it)
               }
              // def sortedList= sortedMap.values()
              // println("not good???")
              
               def other = AcquisitionLocation.findByCode('OTHER')
               if(!locationMap.get(other.name)){
                    finalList.add(other)
               }
                    
              
               //println("sortedList size: " + sortedList.size())
                    
                 map.put("locationList", finalList)
                 
                    
            }else{
                def ids = map.get("ids") + "_" + it.id
               // println("ids:" + ids)
                map.put("ids", ids)
                def specimenIds = map.get("specimenIds") + "<br/>" + it.specimenId
                //println("specimenIds: " + specimenIds)
                map.put("specimenIds", specimenIds)
                  
            }
            
            
          
                
            
           
            
        }
        
             return result 
        
       }catch(Exception e){
            throw new RuntimeException(e.toString())
      }
    }
    
    
    
      def getSpecimensFull(tissueRecoveryBmsInstance, hour){
       def cc=getCardiatCessTime(tissueRecoveryBmsInstance) 
      try{
        def protocol
        if(hour == '1'){
            protocol = Protocol.findByCode("A")
        }else if(hour == '4'){
            protocol = Protocol.findByCode("B")
        }else if (hour == '6'){
            protocol = Protocol.findByCode("C")
        }else{
            protocol = Protocol.findByCode("D")
        }
        
        def caseRecord = tissueRecoveryBmsInstance.caseRecord
        def list = SpecimenRecord.findAllByCaseRecordAndProtocol(caseRecord, protocol)
       // println("list size: " + list.size())
        def parentCase =caseRecord.parentCase
       
        def result=[:]
        list.each{
            def specimenId = it.specimenId
            //def tissueCode = it.parentSpecimen?.tissueType.code
            def tissueCode = it.tissueType.code
            def fixOrder = it.fixative.displayOrder
            
            def key = tissueCode + "_" + fixOrder + "_" + it.specimenId
          
            
                def map=[:]
                result.put(key, map)
                map.put("id", it.id)
                map.put("specimenId", specimenId)
               // map.put("tissue", it.parentSpecimen?.tissueType.name)
                map.put("tissue", it.tissueType.name)
                 //if(it.parentSpecimen?.tissueType.name == 'Aorta'){
               /**  if(it.tissueType.name == 'Aorta'){
                      map.put("tissueLoc", it.tissueLocation?.name)  
                      if(it.tissueLocation?.code=='OTHER'){
                          map.put("tissueLoc", it.otherTissueLocation)
                      }
                 }else{
                     
                     if(it.parentSpecimen?.tissueLocation?.code=='OTHER')
                       map.put("tissueLoc", it.parentSpecimen?.otherTissueLocation)
                     else
                       map.put("tissueLoc", it.parentSpecimen?.tissueLocation?.name)
                  // map.put("tissueLoc", it.parentSpecimen?.tissueLocation?.name)  
                 }**/
                
                  map.put("tissueLoc", it.tissueLocation?.name)  
                  if(it.tissueLocation?.code=='OTHER'){
                          map.put("tissueLoc", it.otherTissueLocation)
                 }
                //def tissueCode = it.tissueType.code
                //map.put("timeRemoved1",parentMap.get(tissueCode))
                //map.put("timeRemoved1",it.parentSpecimen?.aliquotTimeRemoved)
                map.put("size", it.sizeDiffThanSOP)
                map.put("comments", it.prosectorComments)
                map.put("date1", it.chpTissueRecord.procTimeRemovDate)
                map.put("time1", it.chpTissueRecord.procTimeRemovTime)
                map.put("date2", it.chpTissueRecord.timeInFixDate)
                map.put("time2", it.chpTissueRecord.timeInFixTime)
                map.put("procTimeRemov", it.chpTissueRecord.procTimeRemov)
                map.put("timeRemovFromBody", it.chpTissueRecord.timeRemovFromBody)
                map.put("timeInFix", it.chpTissueRecord.timeInFix)
                def timeInFix=it.chpTissueRecord.timeInFix
                if(timeInFix && cc){
                    def ftime = timeInFix.getTime()
                    def ccTime = cc.getTime()
                    def delay = convertToHHMM(ftime - ccTime)
                    map.put("delay", delay)
                }else{
                    map.put("delay", "")
                }
                map.put("tissue_cons", it.chpTissueRecord.tissueConsistency)
                map.put("fixative", it.fixative?.name)
                map.put("protocol", protocol.name)
           
       
            
        }
        
             return result 
        
       }catch(Exception e){
            throw new RuntimeException(e.toString())
      }
    }
    
    
    
     def getDateList(tissueRecoveryBmsInstance){
           try{
               SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
              def collectionDate = tissueRecoveryBmsInstance.caseRecord.parentCase?.tissueRecoveryGtex?.collectionDate
              def nextDay = collectionDate + 1
              def result = []
              result.add(df.format(collectionDate))
              result.add(df.format(nextDay))
              
              return result  
            
              
           }catch(Exception e){
            throw new RuntimeException(e.toString())
          }
     }
    
    
    
    def updateTrf(tissueRecoveryBmsInstance, params){
       
        try{
          tissueRecoveryBmsInstance.properties = params
          tissueRecoveryBmsInstance.save(failOnError:true)
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
     def updateTrf2(params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
       // println("timedelay???"  + params.time_delay)
        try{
          def tissueRecoveryBmsInstance=TissueRecoveryBms.findById(params.id)
          tissueRecoveryBmsInstance.properties = params
          tissueRecoveryBmsInstance.save(failOnError:true)
          /*def temperature1h = tissueRecoveryBmsInstance.temperature1h
          def temperature1hUnit = tissueRecoveryBmsInstance.temperature1hUnit
          def temperature4h = tissueRecoveryBmsInstance.temperature4h
          def temperature4hUnit = tissueRecoveryBmsInstance.temperature4hUnit
          def temperature6h = tissueRecoveryBmsInstance.temperature6h
          def temperature6hUnit = tissueRecoveryBmsInstance.temperature6hUnit
           def temperature15h = tissueRecoveryBmsInstance.temperature15h
          def temperature15hUnit = tissueRecoveryBmsInstance.temperature15hUnit*/
          
            def easeOfUseLn21h = tissueRecoveryBmsInstance.easeOfUseLn21h
            def easeOfUseDice1h = tissueRecoveryBmsInstance.easeOfUseDice1h
            
              def easeOfUseLn24h = tissueRecoveryBmsInstance.easeOfUseLn24h
            def easeOfUseDice4h = tissueRecoveryBmsInstance.easeOfUseDice4h
            
              def easeOfUseLn26h = tissueRecoveryBmsInstance.easeOfUseLn26h
            def easeOfUseDice6h = tissueRecoveryBmsInstance.easeOfUseDice6h
            
                 def easeOfUseLn215h = tissueRecoveryBmsInstance.easeOfUseLn215h
            def easeOfUseDice15h = tissueRecoveryBmsInstance.easeOfUseDice15h

          def originalStarted
          if(params.time_delay == '1')
                   originalStarted=tissueRecoveryBmsInstance.protocol1hStarted
                if(params.time_delay == '4')
                   originalStarted=tissueRecoveryBmsInstance.protocol4hStarted
                
                if(params.time_delay == '6')
                   originalStarted=tissueRecoveryBmsInstance.protocol6hStarted
                
                if(params.time_delay == '15')
                   originalStarted=tissueRecoveryBmsInstance.protocol15hStarted
          
          
          boolean started = new Boolean(false)
          
        if(params.time_delay == '1' && (easeOfUseLn21h || easeOfUseDice1h))
          started = new Boolean(true)
          
        if(params.time_delay == '4' && (easeOfUseLn24h || easeOfUseDice4h))
          started = new Boolean(true)
          
         if(params.time_delay == '6' && (easeOfUseLn26h || easeOfUseDice6h))
          started = new Boolean(true)
          
          if(params.time_delay == '15' && (easeOfUseLn215h || easeOfUseDice15h))
          started = new Boolean(true)
        
        
          params.each(){key,value->
              if(value=='isid'){
                 
                  def date1=params.get("date1_"+key)
                 // println("date1: " + date1)
                 def time1=params.get("time1_" + key)
                 def date2=params.get("date2_"+key)
                 def time2=params.get("time2_"+key)
                 def date3=params.get("date3_"+key)
                 def time3=params.get("time3_"+key)
                 def tissue_cons=params.get("tissue_cons_" +key)
                 def size = params.get("size_" + key)
                 def comments = params.get("comments_" + key)
                 def otherLoc=params.get("otherTissueLocation_" + key)
                 if(date1 || time1 || date2 || time2 || tissue_cons || size || comments)
                     started = new Boolean(true)
                 def ids = key.split("_")
                 
                 def tissueLocation = params.get("tissueLocation_" + key)
                 def location_id = -1
                 if(tissueLocation)
                    location_id =Integer.parseInt(tissueLocation)
                 
                  //println("location_id: " + location_id)  
                 //if(tissueLocation)
                    //println("tissueLocation:" + tissueLocation)
                 
                 ids.each{
                     //println("id: " + it)
                     def specimenRecord = SpecimenRecord.findById(it)
                     
                     
                     //if(specimenRecord.tissueType.name=='Aorta' && location_id != -1){
                     if( location_id != -1){
                         //println("save location????")
                         def location=AcquisitionLocation.findById(location_id)
                         //println("location name: " + location.name)
                         specimenRecord.tissueLocation=location
                         specimenRecord.otherTissueLocation=otherLoc
                        
                          started = new Boolean(true)
                       // println("sid: " + specimenRecord.specimenId + " location id: " + location_id )
                     }
                     
                     // if(specimenRecord.tissueType.name=='Aorta' && location_id == -1){
                     if( location_id == -1){
                           specimenRecord.tissueLocation=null
                           specimenRecord.otherTissueLocation=null
                      }
                     //println("s.specimenid:" + s.specimenId)
                     specimenRecord.sizeDiffThanSOP=size
                     specimenRecord.prosectorComments = comments
                     
                        
                        
                     /*def ptype= specimenRecord.provisionalTissueType
                     if(!ptype){
                         println("ptype is null")
                     }*/
                     specimenRecord.save(failOnError:true)
                     def chpTissueRecord = specimenRecord.chpTissueRecord
                     
                     //println("date1:" + date1)
                     chpTissueRecord.procTimeRemovDate=date1
                     chpTissueRecord.procTimeRemovTime=time1
                     chpTissueRecord.timeRemovFromBodyDate=date3
                     chpTissueRecord.timeRemovFromBodyTime=time3
                     chpTissueRecord.timeInFixDate=date2
                     chpTissueRecord.timeInFixTime=time2
                     chpTissueRecord.tissueConsistency =tissue_cons
                     if(date1 && time1){
                         def procTimeRemov=df.parse(date1 + " "+ time1)
                         chpTissueRecord.procTimeRemov=procTimeRemov

                     }else{
                         chpTissueRecord.procTimeRemov=null
                     }
                     
                        
                      if(date3 && time3){
                         def timeRemovFromBody=df.parse(date3 + " "+ time3)
                         chpTissueRecord.timeRemovFromBody=timeRemovFromBody

                     }else{
                         chpTissueRecord.timeRemovFromBody=null
                     }
                        
                        
                      if(date2 && time2){
                            def timeInFix = df.parse(date2 + " " + time2)
                            chpTissueRecord.timeInFix=timeInFix
           
                      }else{
                          chpTissueRecord.timeInFix=null
                      }
                     chpTissueRecord.save(failOnError:true)
                     
                 } //each
                  
               
                    
              }//isid
                
          
          }//params
          
            if(!originalStarted && started){
                if(params.time_delay == '1')
                   tissueRecoveryBmsInstance.protocol1hStarted=started
                if(params.time_delay == '4')
                   tissueRecoveryBmsInstance.protocol4hStarted=started
                
                if(params.time_delay == '6')
                   tissueRecoveryBmsInstance.protocol6hStarted=started
                
                if(params.time_delay == '15')
                   tissueRecoveryBmsInstance.protocol15hStarted=started
           
                tissueRecoveryBmsInstance.save(failOnError:true)
            }
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    def getTissueList(tissueRecoveryBmsInstance){
       
      //  def tissueList = tissueRecoveryBmsInstance.caseRecord?.specimens?.parentSpecimen?.tissueType.code
        def tissueList = tissueRecoveryBmsInstance.caseRecord?.specimens?.tissueType.code
        //println("tissueList size: " + tissueList.size() )
        def uniqueList=[]
        tissueList.each{
            if(!uniqueList.contains(it)){
                uniqueList.add(it)
            }
        }
        
       // println("uniqureList size: " + uniqueList)
        
        return uniqueList
        
    }
    
    
}
