package nci.obbr.cahub.util

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.datarecords.ctc.*
//import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.gtex.crf.*
import nci.obbr.cahub.forms.gtex.*
import nci.obbr.cahub.util.querytracker.*

import org.apache.commons.logging.LogFactory


class TextSearchController {
     def textSearchService
     def prcReportService
     def caseReportFormService
    
    private static final log = LogFactory.getLog(this)

    
    def searchhome={
        
    }
    
     def searchhomeCTC={
        
    }
    
     def search = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")"
            def q2='';
            def q3=' (studyCode:GTEX AND -studyCode:BMS AND -statusCode:WITHDR)'
           // println("code: " + session.org.code)
            if(session.org.code != 'OBBR'){

              def bss = BSS.findByCode(session.org.code)
                //get all bss, parent and subs
              def bssList = BSS.findAllByParentBss(bss)
              
               
              if(bssList){
                 // println("bss list not null")
                  q2=" +("
                  int i = 0
                  for (b in bssList){
                      if (i == 0){
                          q2 += "BSSCode:" + b.code
                      }else{
                          q2 += " OR BSSCode:"+b.code
                      }
                      i++
                  }
                  q2 += ")"
              }else{
                 // println("bss list is null")
                  //do somthing.....
              }
       
             }


            // println("query string: " + q + q2)
           
              def size=0
              def count = 0
              def caseRecordInstanceList=[]
            
          try{
             def map  = CaseRecord.search(
                  q+q2+q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
          /*    def map  = CaseRecord.search{
                  //must(queryString(q))
                  gt("publicVersion", 2)
                  //sort("CaseRecord.dateCreated", CompassQuery.SortPropertyType.DATE)
               
         
              }*/
               
                count = map.total
                // println "There are ${count} hits for query ${params.query}" 
                 
                
           
              
             caseRecordInstanceList = map.results
          
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
                
             [caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, msg:""]    
          }catch (Exception e){
               //def msg=e.toString()
               def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               [caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, msg:msg]
          }
            
          /*  def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            [caseRecordInstanceList:listDB, total:count, query:query, size:size]*/
            
           
         
        }
    }
    
    
    
    
     def searchCandi = {
      
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")"
            def q2='';
           // def q3=' (studyCode:GTEX AND -studyCode:BMS AND -statusCode:WITHDR)'
           def q3=' (studyCode:GTEX AND -studyCode:BMS )'
           // println("code: " + session.org.code)
            if(session.org.code != 'OBBR'){

              def bss = BSS.findByCode(session.org.code)
                //get all bss, parent and subs
              def bssList = BSS.findAllByParentBss(bss)
              
               
              if(bssList){
                 // println("bss list not null")
                  q2=" +("
                  int i = 0
                  for (b in bssList){
                      if (i == 0){
                          q2 += "BSSCode:" + b.code
                      }else{
                          q2 += " OR BSSCode:"+b.code
                      }
                      i++
                  }
                  q2 += ")"
              }else{
                 // println("bss list is null")
                  //do somthing.....
              }
       
             }


           
              def size=0
              def count = 0
              def candidateRecordInstanceList=[]
               def listDB =[]
            
          try{
             def map  = CandidateRecord.search(
                    q+q2+q3,
                    [offset:offset, max:25, sort:"CandidateRecord.dateCreated", order:"desc"]
         
               )
              
       
                count = map.total
                
 
             candidateRecordInstanceList = map.results
          
            
            if( candidateRecordInstanceList){
                size=candidateRecordInstanceList.size()
            }
            
                
             def list = []
             for(cd in candidateRecordInstanceList){
                 list.add(cd.id)
             }
        
            
           
            if(list){
               listDB=CandidateRecord.getAll(list)
            }
                
                
             [candidateRecordInstanceList:listDB, total:count, query:query, size:size, msg:""]    
           // render ("size: " + size)
          }catch (Exception e){
               //def msg=e.toString(
               def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               [candidateRecordInstanceList:listDB, total:count, query:query, size:size, msg:msg]
               //render ("msg: " + msg)
          }
            
         
           
         
        }
        
       // render ("test")
        
    }
    
   
        
    def searchCandiBPV = {
      
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")"
            def q2='';
           // def q3=' (studyCode:GTEX AND -studyCode:BMS AND -statusCode:WITHDR)'
           def q3=' (studyCode:BPV)'
           // println("code: " + session.org.code)
            if(session.org.code != 'OBBR'){

              def bss = BSS.findByCode(session.org.code)
                //get all bss, parent and subs
              def bssList = BSS.findAllByParentBss(bss)
              
               
              if(bssList){
                 // println("bss list not null")
                  q2=" +("
                  int i = 0
                  for (b in bssList){
                      if (i == 0){
                          q2 += "BSSCode:" + b.code
                      }else{
                          q2 += " OR BSSCode:"+b.code
                      }
                      i++
                  }
                  q2 += ")"
              }else{
                 // println("bss list is null")
                  //do somthing.....
              }
       
             }


           
              def size=0
              def count = 0
              def candidateRecordInstanceList=[]
               def listDB =[]
            
          try{
             def map  = CandidateRecord.search(
                    q+q2+q3,
                    [offset:offset, max:25, sort:"CandidateRecord.dateCreated", order:"desc"]
         
               )
              
       
                count = map.total 
                 
                
           
              
             candidateRecordInstanceList = map.results
          
            
            if( candidateRecordInstanceList){
                size=candidateRecordInstanceList.size()
            }
            
                
             def list = []
             for(cd in candidateRecordInstanceList){
                 list.add(cd.id)
             }
        
            
           
            if(list){
               listDB=CandidateRecord.getAll(list)
            }
                
                
             [candidateRecordInstanceList:listDB, total:count, query:query, size:size, msg:""]    
           // render ("size: " + size)
          }catch (Exception e){
              // def msg=e.toString()
              def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               [candidateRecordInstanceList:listDB, total:count, query:query, size:size, msg:msg]
               //render ("msg: " + msg)
          }
            
         
           
         
        }
        
       // render ("test")
        
    }
    
    
    
    def searchBMS = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")"
            def q2='';
            def q3=' (studyCode:BMS AND -statusCode:WITHDR) '
           // println("code: " + session.org.code)
            if(session.org.code != 'OBBR'){

              def bss = BSS.findByCode(session.org.code)
                //get all bss, parent and subs
              def bssList = BSS.findAllByParentBss(bss)
              
               
              if(bssList){
                 // println("bss list not null")
                  q2=" +("
                  int i = 0
                  for (b in bssList){
                      if (i == 0){
                          q2 += "BSSCode:" + b.code
                      }else{
                          q2 += " OR BSSCode:"+b.code
                      }
                      i++
                  }
                  q2 += ")"
              }else{
                 // println("bss list is null")
                  //do somthing.....
              }
       
             }


            // println("query string: " + q + q2)
            
            def count=0
            def size=0
            def caseRecordInstanceList=[]
           
            try{
            
             def map  = CaseRecord.search(
                  q+q2+q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
          /*    def map  = CaseRecord.search{
                  //must(queryString(q))
                  gt("publicVersion", 2)
                  //sort("CaseRecord.dateCreated", CompassQuery.SortPropertyType.DATE)
               
         
              }*/
               
               count = map.total
                // println "There are ${count} hits for query ${params.query}" 
                 
                
           
              
            caseRecordInstanceList = map.results
            
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
           def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
                    
           def specimenCount=[:]
         if(listDB){
             def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: listDB])

             count_result.each(){
                 specimenCount.put(it[0], it[1])

             }
         }
         
           // [caseRecordInstanceList:listDB, total:count, query:query, size:size]*/
            
            [caseRecordInstanceList:listDB, specimenCount:specimenCount, total:count, query:query, size:size, msg:""]
            
            }catch(Exception e){
                  // def msg=e.toString()
                def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               [caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, msg:msg]
            }
         
        }
    }
    
    
    
    
     def searchBPV = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")"
            def q2='';
            def q3=' (studyCode:BPV  AND -statusCode:WITHDR) '
           // println("code: " + session.org.code)
            if(session.org.code != 'OBBR'){

              def bss = BSS.findByCode(session.org.code)
                //get all bss, parent and subs
              def bssList = BSS.findAllByParentBss(bss)
              
               
              if(bssList){
                 // println("bss list not null")
                  q2=" +("
                  int i = 0
                  for (b in bssList){
                      if (i == 0){
                          q2 += "BSSCode:" + b.code
                      }else{
                          q2 += " OR BSSCode:"+b.code
                      }
                      i++
                  }
                  q2 += ")"
              }else{
                 // println("bss list is null")
                  //do somthing.....
              }
       
             }


            // println("query string: " + q + q2)
            
            def count=0
            def size=0
            def caseRecordInstanceList=[]
              def listDB =[]
               def specimenCount=[:]
           
            try{
             def map  = CaseRecord.search(
                  q+q2+q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
          /*    def map  = CaseRecord.search{
                  //must(queryString(q))
                  gt("publicVersion", 2)
                  //sort("CaseRecord.dateCreated", CompassQuery.SortPropertyType.DATE)
               
         
              }*/
               
             count = map.total
                // println "There are ${count} hits for query ${params.query}" 
                 
                
           
              
            caseRecordInstanceList = map.results
           
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
          /*  def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            [caseRecordInstanceList:listDB, total:count, query:query, size:size]*/
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
                
           
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
                    
          
         if(listDB){
             def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: listDB])

             count_result.each(){
                 specimenCount.put(it[0], it[1])

             }
         }
            
           // println("soecumenCountSize: " + specimenCount.size())
                
               [caseRecordInstanceList:listDB, specimenCount:specimenCount, total:count, query:query, size:size, msg:""]
            }catch(Exception  e){
               //  def msg=e.toString()
               def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, msg:msg]
                [caseRecordInstanceList:listDB, specimenCount:specimenCount, total:count, query:query, size:size, msg:msg]
            }
         
        }
    }
    
    
    
     def searchCTC = {
          def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
       // println("here????")
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "" + params.query + ""
            def q2='';
            def q3=' (studyCode:CTC  AND -statusCode:WITHDR) '
           // println("code: " + session.org.code)
           


            // println("query string: " + q + q2)
            
            def count=0
            def size=0
            def caseRecordInstanceList=[]
              def listDB =[]
               def specimenCount=[:]
                def list = []
           
            try{
             def map  = PatientRecord.search(
                  q,
                  [offset:offset, max:25, sort:"PatientRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               
             count = map.total
               // println "There are ${count} hits for query ${params.query}" 
                 
                
           
              
            caseRecordInstanceList = map.results
            
          
             for(cd in caseRecordInstanceList){
                 list.add(PatientRecord.findById(cd.id))
             }
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
           
              // println ("list size: " + list.size())
            
               
               [patientRecordList:list,  total:count, query:query, size:size, username:username, msg:""]
            }catch(Exception  e){
               //  def msg=e.toString()
               def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, msg:msg]
               [patientRecordList:list,  total:count, query:query, size:size, msg:msg, username:username]
            }
         
        }
    }
    
    
    
     def searchMBB = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")"
            def q2='';
            def q3=' (studyCode:GTEX AND -studyCode:BMS AND tissueCode:BRAIN AND -statusCode:WITHDR) '
           // println("code: " + session.org.code)
           


            // println("query string: " + q + q2)
            
            def count=0
            def size=0
            def caseRecordInstanceList=[]
              def listDB =[]
               def specimenCount=[:]
                def sermap =[:]
                def queryCount=[:]
           
            try{
             def map  = CaseRecord.search(
                  q+q2+q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               
             count = map.total
                // println "There are ${count} hits for query ${params.query}" 
                 
       
           
              
            caseRecordInstanceList = map.results
            
       
             def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
                
           
            if(list){
               listDB=CaseRecord.getAll(list)
            }
                
          
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
           
          
            listDB.each{
            
            def key=it.caseId 
            def li = prcReportService.getSerologyList2(it)
            def val=''
            if(li)
            val=li.join(',')
                          
            sermap.put(key, val)
        }
       
        queryCount = getQueryCountMap(listDB)   
                
              
                          
               return [caseRecordInstanceList: listDB, totoal: count, selectedStudy:session.study.code, sermap:sermap, queryCount:queryCount, total:count, query:query, size:size, msg:""] 
              // [patientRecordList:list,  total:count, query:query, size:size, msg:""]
            }catch(Exception  e){
               //  def msg=e.toString()
               def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, msg:msg]
               //[patientRecordList:list,  total:count, query:query, size:size, msg:msg]
               [caseRecordInstanceList: listDB, totoal: count, selectedStudy:session.study.code, sermap:sermap, queryCount:queryCount, total:count, query:query, size:size, msg:msg] 
            }
         
        }
    }
    
    def getQueryCountMap(caseRecordInstanceList) {
        def queryCount = [:]
        if (caseRecordInstanceList) {
            def activeStatus = QueryStatus.findByCode("ACTIVE")
            def countResult
            if (session.org?.code == 'OBBR') {
                countResult= Query.executeQuery("select c.id, count(*) from Query i inner join i.caseRecord c inner join i.queryStatus s where c in (:list) and s.id = :activeStatus group by c.id",  [list:caseRecordInstanceList, activeStatus:activeStatus.id])
            } else {
                countResult= Query.executeQuery("select c.id, count(*) from Query i inner join i.caseRecord c inner join i.queryStatus s inner join i.organization o where c in (:list) and s.id = :activeStatus and o.code like :org group by c.id",  [list:caseRecordInstanceList, activeStatus:activeStatus.id, org:session.org?.code + "%"])
            }
            countResult.each() {
                queryCount.put(it[0], it[1]) 
            }
        }

        return queryCount
    }
    
     def searchBRN = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"

            def q3=' (studyCode:BRN AND -statusCode:WITHDR)'
         
         
             def count=0
            def size=0
            def caseRecordInstanceList=[]
               def specimenCount=[:] 
           
            try{
            
             def map  = CaseRecord.search(
                  q+q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
        
               
                count = map.total
 
                 
                
           
              
            caseRecordInstanceList = map.results
           
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
           if(caseRecordInstanceList){ 
                  def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseRecordInstanceList])

                 count_result.each(){
                     specimenCount.put(it[0], it[1])

                 }
          }
            
       
            
            [caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, specimenCount:specimenCount, msg:""]
            }catch(Exception e){
                 //def msg=e.toString()
                 def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                 [caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size, specimenCount:specimenCount, msg:msg]
            }
        }
    }
    
    
    
    
    
    def searchPRC = {
        if(params.query){
              //println("form type is: " + params.formtype)
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            def q3=' (studyCode:GTEX AND -studyCode:BMS AND -statusCode:WITHDR)'
           

            // println("query string: " + q + q2)
            
            
            def count=0
            def size=0
            def caseList=[]
          
            
            try{
            
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
              count = map.total
               
                 
                
           
              
            def caseRecordInstanceList = map.results
            
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
           
           /**  listDB.each(){
               def map2=prcReportService.getPrcCaseMap(it)
               caseList.add(map2)
            }*/
            
            caseList = prcReportService.getPrcCaseMaps(listDB)
            [caseList:caseList, total:count, query:query, size:size, msg:"", formtype:params.formtype]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
            }catch(Exception e){
                  //def msg=e.toString()
                  def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                [caseList:caseList, total:count, query:query, size:size, msg:msg, formtype:params.formtype]
            }
         
        }
    }
    
    
    
     def searchPRCBms = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            def q3=' (studyCode:BMS AND -statusCode:WITHDR)'
           

            // println("query string: " + q + q2)
            
                        
              def count=0
            def size=0
            def caseList=[]
            
            try{
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               count = map.total
               
                 
                
           
              
            def caseRecordInstanceList = map.results
            
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
           
             listDB.each(){
               def map2=prcReportService.getPrcCaseMapBms(it)
               caseList.add(map2)
            }
            [caseListBms:caseList, total:count, query:query, size:size, msg:"", formtype:params.formtype]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
            }catch(Exception e){
                 // def msg=e.toString()
                 def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                [caseList:caseList, total:count, query:query, size:size, msg:msg, formtype:params.formtype]
            }
         
        }
    }
    
    
    
     def searchPRCBpv = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            def q3=' (studyCode:BPV  AND -statusCode:WITHDR AND hasLocalPathReview:true) '
           

            // println("query string: " + q + q2)
            
                        
            def count=0
            def size=0
            def caseList=[]
            
            try{
            
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               count = map.total
               
                 
                
           
              
            def caseRecordInstanceList = map.results
            
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
            
            /** listDB.each(){
               def map2=prcReportService.getPrcCaseMapBpv(it)
               caseList.add(map2)
            }**/
            
            caseList = prcReportService.getPrcCaseMapsBpv(listDB)
            [caseListBpv:caseList, total:count, query:query, size:size, msg:"", formtype:params.formtype]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
         
            }catch(Exception e){
                  def msg="Wrong search Criteria"
                  log.error((new Date()).toString() + " " + e.toString())
                [caseList:caseList, total:count, query:query, size:size, msg:msg, formtype:params.formtype]
            }
            
        }
    }
    
     def searchPRCBrn = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            def q3=' (studyCode:BRN AND -statusCode:WITHDR)'
           

            // println("query string: " + q + q2)
            
            def count=0
            def size=0
            def caseList=[]
                        
            try{
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               count = map.total
               
                 
                
           
              
            def caseRecordInstanceList = map.results
          
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
       
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
            def listDB =[]
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
            
             listDB.each(){
               def map2=prcReportService.getPrcCaseMapBrn(it)
               caseList.add(map2)
            }
            [caseListBrn:caseList, total:count, query:query, size:size, msg:"", formtype:params.formtype]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
         
            }catch(Exception e){
                // def msg=e.toString()
                def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                [caseList:caseList, total:count, query:query, size:size, msg:msg, formtype:params.formtype]
            }                
        }
    }
    
    
    def searchVari = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            
            def q3
            
            if(session.org?.code =='VARI'){
                 q3=' (studyCode:GTEX AND -studyCode:BMS)'
            }else{
                  q3=' (studyCode:GTEX AND -studyCode:BMS AND -statusCode:WITHDR)'
            }
            
          
           

            // println("query string: " + q + q2)
            
                        
             def count=0
             def size=0
             def listDB =[]
             def specimenCount=[:]
             
            try{
            
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
              count = map.total
               
            
              
            def caseRecordInstanceList = map.results
           
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
         
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
           
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
            
           if(listDB){ 
                  def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: listDB])

                 count_result.each(){
                     specimenCount.put(it[0], it[1])

                 }
          }
            
            /* def caseList=[]
             listDB.each(){
               def map2=prcReportService.getPrcCaseMap(it)
               caseList.add(map2)
            }*/
            
            [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount,  msg:""]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
            }catch(Exception e){
                 //def msg=e.toString()
                 def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                  [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount,  msg:msg]
            }
        }
    }
       
    
    def searchVariBpv = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            
            def q3
            
            if(session.org?.code =='VARI'){
                 q3=' (studyCode:BPV) '
           
            }else{
                 q3=' (studyCode:BPV  AND -statusCode:WITHDR) '
           
            }
            
           

            // println("query string: " + q + q2)
            
              def count=0
             def size=0
             def listDB =[]
             def specimenCount=[:]
             
            try{
            
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               count = map.total
               
            
              
            def caseRecordInstanceList = map.results
             size=0
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
         
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
          
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
             
           if(listDB){ 
                  def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: listDB])

                 count_result.each(){
                     specimenCount.put(it[0], it[1])

                 }
          }
            
            /* def caseList=[]
             listDB.each(){
               def map2=prcReportService.getPrcCaseMap(it)
               caseList.add(map2)
            }*/
            
            [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount, msg:""]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
            }catch(Exception e){
                 //def msg=e.toString()
                 def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                  [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount,  msg:msg]
            }
        }
    }
       
    
    
    
     def searchVariBrn = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            
              def q3
            
            if(session.org?.code =='VARI'){
              q3=' (studyCode:BRN) '
            }else{
                q3=' (studyCode:BRN  AND -statusCode:WITHDR) '
            }

            // println("query string: " + q + q2)
            
             def count=0
             def size=0
             def listDB =[]
             def specimenCount=[:]
            
            try{            
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
               count = map.total
               
            
              
            def caseRecordInstanceList = map.results
           
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
         
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
           
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
            
           if(listDB){ 
                  def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: listDB])

                 count_result.each(){
                     specimenCount.put(it[0], it[1])

                 }
          }
            
            /* def caseList=[]
             listDB.each(){
               def map2=prcReportService.getPrcCaseMap(it)
               caseList.add(map2)
            }*/
            
            [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount, msg:""]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
            }catch(Exception e){
                 //def msg=e.toString()
                 def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                  [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount,  msg:msg]
            }
        }
    }
       
    
    
       def searchVariBms = {
        if(params.query){
          
              def query = params.query
              def offset = params.offset
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q=  "(" + params.query + ")"
            
            
               def q3
            
            if(session.org?.code =='VARI'){
             q3=' (studyCode:BMS)'
            }else{
              q3=' (studyCode:BMS AND -statusCode:WITHDR)' 
            }

            // println("query string: " + q + q2)
            
              def count=0
             def size=0
             def listDB =[]
             def specimenCount=[:]
            
            try{
             def map  = CaseRecord.search(
                  q +q3,
                  [offset:offset, max:25, sort:"CaseRecord.dateCreated", order:"desc"]
         
               )
              
          
         
             count = map.total
               
            
              
            def caseRecordInstanceList = map.results
            
            
            if( caseRecordInstanceList){
                size=caseRecordInstanceList.size()
            }
            
         
            
            def list = []
             for(cd in caseRecordInstanceList){
                 list.add(cd.id)
             }
        
            
         
            if(list){
               listDB=CaseRecord.getAll(list)
            }
            
          
           if(listDB){ 
                  def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: listDB])

                 count_result.each(){
                     specimenCount.put(it[0], it[1])

                 }
          }
            
            /* def caseList=[]
             listDB.each(){
               def map2=prcReportService.getPrcCaseMap(it)
               caseList.add(map2)
            }*/
            
            [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount, msg:""]
            
            //[caseRecordInstanceList:caseRecordInstanceList, total:count, query:query, size:size]
            }catch(Exception e){
                 //def msg=e.toString()
                 def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
                  [caseRecordInstanceList:listDB, total:count, query:query, size:size, specimenCount:specimenCount,  msg:msg]
            }
        }
    }
       
    
    
    def display={
        
         def caseRecordInstance = CaseRecord.get(params.id)
         if (!caseRecordInstance) {
                flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'CaseRecord'), params.id])}"
                redirect(action: "list")
         }
         else {
                //ensure user is entitled to access this caseRecord instance
                [caseRecordInstance:caseRecordInstance]
         }
        
    }
    
    
     /**
     * Perform a bulk index of every searchable object in the database
     */
    def index_all = {
         textSearchService.index_all()
        render("bulk index started in a background thread.")
    }

     def index_patient = {
         textSearchService.index_patient()
        render("bulk index started in a background thread.")
    }

    
    def index_obj = {
        
        def caseId =params.caseId
        
        if(caseId){
                def caseRecord= CaseRecord.findByCaseId(caseId)
                if(caseRecord){
                     
                     caseRecord.index()
                   
                    render("caseRecord object index started in a background thread.")
                }else{
                    render("invalid caseId.")
                }
        
        } else{
                render("Please specify caseid")
            
        }
        
      
        
        
    }
    
    
    def update_age ={
        
        caseReportFormService.updateAge()
      
        render("age updated")
    }
    
    
    
     def searchQt = {
      
        if(params.query){
          
              def query = params.query
              def offset = params.offset
             // println("offset: " + offset)
              if(!offset)
                 offset=0
                 
             //def q="+" + params.query
            def q= "(" + params.query + ")" 
            def q2=""
            def q3 = ""
            if(session.org.code != 'OBBR'){
                q2= " (orgCode:" + session.org.code + "*)" 
                q3 = " (qtStatusCode:[* TO *])"
            }

           
              def size=0
              def count = 0
              def qtInstanceList=[]
               def listDB =[]
            
          try{
             def map  = Query.search(
                    q + q2 + q3,
                    [offset:offset, max:25, sort:"Query.dateCreated", order:"desc"]
         
               )
              
       
                count = map.total
               // println("count: " + count)
 
             qtInstanceList = map.results
          
            
            if( qtInstanceList){
                size=qtInstanceList.size()
            }
            
                
             def list = []
             for(qt in qtInstanceList){
                 list.add(qt.id)
             }
        
            
           
            if(list){
               listDB=Query.getAll(list)
            }
                
                
             [queryInstanceList:listDB, total:count, query:query, size:size, msg:""]    
          
          }catch (Exception e){
               //def msg=e.toString()
               def msg="Wrong Search Criteria."
               log.error((new Date()).toString() + " " + e.toString())
               [queryInstanceList:listDB, total:count, query:query, size:size, msg:msg]
             
          }
            
         
           
         
        }
        
      
        
    }
    
    
   
}
