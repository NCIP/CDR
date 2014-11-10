package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.util.*;
import nci.obbr.cahub.prc.*;
import nci.obbr.cahub.ldacc.*;
import java.text.SimpleDateFormat
import java.text.DecimalFormat
import nci.obbr.cahub.forms.bpv.BpvLocalPathReview
import nci.obbr.cahub.util.querytracker.*
import nci.obbr.cahub.forms.gtex.*
import nci.obbr.cahub.forms.gtex.crf.*

class PrcReportService {

    static transactional = true

    def ldaccService
    def activityEventService
    def cachedGtexDonorVarsExportService

    def createReport(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
            def prcReportSubmission = new PrcReportSubmission()
            prcReportSubmission.reportVersion=1
            prcReportSubmission.caseRecord = caseRecord
            prcReportSubmission.forFzn = false
            prcReportInstance.currentSubmission = prcReportSubmission
            prcReportInstance.status='Editing'
            
          
            def speciments=[]
            if(caseRecord.study.code =='GTEX'){
                speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=?  and s.fixative.code='XG' and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
            }
            if(caseRecord.study.code =='BMS'){
                speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s  where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) and s.specimenId like '%25' order by s.specimenId", [caseRecord.id])
            }
          
           
            speciments.each(){
                def prcSpecimen = new PrcSpecimen()
                prcSpecimen.specimenRecord = it
                prcSpecimen.inventoryStatus=InventoryStatus.findByCode('ACCP')
                if(caseRecord.study.code =='BMS'){
                    prcSpecimen.comments='piece'
                }
                prcSpecimen.save(failOnError:true)
                it.prcSpecimen = prcSpecimen
                it.save(failOnError:true)
            }

            prcReportSubmission.save(failOnError:true)
            prcReportInstance.save(failOnError:true)
        } catch(Exception e) {
            throw new RuntimeException(e.toString())
        }
    }

    def qaReview(prcReportInstance, username) {
        try {
            prcReportInstance.reviewedBy = username
            prcReportInstance.reviewDate = new Date()
            def caseRecord = prcReportInstance.caseRecord
            if(randomlyPicked(caseRecord)){
                def seqNum = getCaseSeqNumber(caseRecord)
                prcReportInstance.matchedSeq = seqNum
                
            }
            prcReportInstance.save(failOnError:true)
            
            def result = PrcReportSubmission.executeQuery("from  PrcReportSubmission prs where prs.id = (select max(prs2.id) from PrcReportSubmission prs2 where prs2.caseRecord.id=? and (prs2.forFzn is null or prs2.forFzn=false)) ", [caseRecord.id])
            def submission=result.get(0)
            def activityType = ActivityType.findByCode("PRCAVAILABLE")
            def caseId = prcReportInstance.caseRecord.caseId
            def study = prcReportInstance.caseRecord.study
            def bssCode = prcReportInstance.caseRecord.bss?.parentBss?.code

            cachedGtexDonorVarsExportService.cacheReleasedCase(prcReportInstance.caseRecord, new Date(), "PRC report released")
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
            
            if(randomlyPicked(caseRecord)){
                def activityType2 = ActivityType.findByCode("PRCFORQC")
                activityEventService.createEvent(activityType2, caseId, study, bssCode, null, username, submission?.submittedBy, null)
            }
        } catch(Exception e) {
            throw new RuntimeException(e.toString())
        }
    }

    def startNew(prcReportInstance){
        try{
            def caseRecord = prcReportInstance.caseRecord
            prcReportInstance.status = 'Editing'
            prcReportInstance.reviewedBy = null
            prcReportInstance.reviewDate = null
            def currentSub = prcReportInstance.currentSubmission
            def currentVersion = currentSub.reportVersion
            def nextSubmission= new PrcReportSubmission()
            nextSubmission.reportVersion= currentVersion +1
            nextSubmission.caseRecord = caseRecord
            nextSubmission.forFzn = false
            prcReportInstance.currentSubmission = nextSubmission
            prcReportInstance.save(failOnError:true)
            nextSubmission.save(failOnError:true)
        }catch(Exception e){
             
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    
    def getPrcSpeciemenList4Edit(prcReportInstance) { 
        try{
            def result=[]
            def caseRecord = prcReportInstance.caseRecord
           
            
            result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=? and s.fixative.code='XG'  order by s.specimenId", [caseRecord.id])
            // println("case record: " + caseRecord.caseId)
            //def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
           
            
            //add new prcSpecimen in case vari loaded slide ship more than once
            def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) and s.fixative.code='XG'  order by s.specimenId", [caseRecord.id])
            
            speciments.each(){
                def prcSpecimen = new PrcSpecimen()
                prcSpecimen.specimenRecord = it
                prcSpecimen.inventoryStatus=InventoryStatus.findByCode('ACCP')
                prcSpecimen.save(failOnError:true)
                result.add(prcSpecimen)
                it.prcSpecimen = prcSpecimen
                it.save(failOnError:true)
            }
                
            
          
        
            //def result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            def qcResult = QcResult.executeQuery("select distinct s.privateId, r.value  from Donor d inner join d.specimens s inner join s.qcs q inner join q.qcResults r where d.privateId = ? and r.attribute='RIN Number' ", [caseRecord.caseId])
            if(qcResult){
                def rinMap =[:]
                qcResult.each{
                    //println("id: " + it[0].toString() + " rin: " + it[1].toString())
                    // rinMap.put(it[0].toString(), it[1].toString())  
                    
                    def rin= formatRin(it[1].toString())
                    def old = rinMap.get(it[0].toString())
                    if(old)
                    rinMap.put(it[0].toString(), old+"," + rin)
                    else
                    rinMap.put(it[0].toString(), rin)    
                }



                //println("qcResult size: " + qcResult.size())

                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    //   println("specimenId: " + specimenId)
                    def rin = rinMap.get(specimenId)
                    //      println "rin: " + rin
                    if(!rin){
                        // println("get try str....")
                        def tryStr = specimenId.substring(0,specimenId.size()-1)+'6'
                        // println("specimenId: " + specimenId + " tryStr: " + tryStr)
                        rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'6')
                        //  println("rin again: " + rin )
                        if(!rin){
                            // println("try strng2: " +  specimenId.substring(0,specimenId.size()-1)+'5')
                            rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'5')
                        }
                    }
                    /** if(rin)
                    it.rin = formatRin(rin)
                    else 
                    it.rin = rin**/
                        
                    it.rin = rin

                }
            }else{
                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    it.rin='Pending QA'
                }
            }
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    
    def getPrcSpeciemenList(prcReportInstance) { 
        try{
            def result=[]
            def caseRecord = prcReportInstance.caseRecord
           
            
            result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=? and s.fixative.code='XG' order by s.specimenId", [caseRecord.id])
            // println("case record: " + caseRecord.caseId)
            //def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
           
        
          
        
            //def result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            def qcResult = QcResult.executeQuery("select distinct s.privateId, r.value  from Donor d inner join d.specimens s inner join s.qcs q inner join q.qcResults r where d.privateId = ? and r.attribute='RIN Number' ", [caseRecord.caseId])
            if(qcResult){
                def rinMap =[:]
                qcResult.each{
                    //println("id: " + it[0].toString() + " rin: " + it[1].toString())
                    def rin= formatRin(it[1].toString())
                    def old = rinMap.get(it[0].toString())
                    if(old)
                    rinMap.put(it[0].toString(), old+"," + rin)
                    else
                    rinMap.put(it[0].toString(), rin)                           
                }



                //println("qcResult size: " + qcResult.size())

                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    // println("specimenId: " + specimenId)
                    def rin = rinMap.get(specimenId)
                    // println "rin: " + rin
                    if(!rin){
                        // println("get try str....")
                        def tryStr = specimenId.substring(0,specimenId.size()-1)+'6'
                        // println("specimenId: " + specimenId + " tryStr: " + tryStr)
                        rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'6')
                        //  println("rin again: " + rin )
                        if(!rin){
                            //   println("try strng2: " +  specimenId.substring(0,specimenId.size()-1)+'5')
                            rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'5')
                            //   println("rin again2: " + rin )
                        }
                    }
                    /*if(rin)
                    it.rin = formatRin(rin)
                    else 
                    it.rin = rin*/
                        
                    it.rin=rin

                }
            }else{
                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    it.rin='Pending QA'
                }
            }
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getPrcSpeciemenListBmsByTissue(prcReportInstance, tissueCode) { 
        try{
            def result=[]
            def caseRecord = prcReportInstance.caseRecord
           
            
            result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s, AcquisitionType t, Protocol p, Fixative f where s.tissueType = t and t.code=? and s.protocol = p and s.fixative=f and  s.caseRecord.id=?  order by p.displayOrder, f.displayOrder", [tissueCode, caseRecord.id])
            // println("case record: " + caseRecord.caseId)
            //def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
           
         
          
        
            //def result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            def qcResult = QcResult.executeQuery("select s.privateId, r.value  from Donor d inner join d.specimens s inner join s.qcs q inner join q.qcResults r where d.privateId = ? and r.attribute='RIN Number' ", [caseRecord.caseId])
            if(qcResult){
                def rinMap =[:]
                qcResult.each{
                    //println("id: " + it[0].toString() + " rin: " + it[1].toString())
                    rinMap.put(it[0].toString(), it[1].toString())                           
                }



                //println("qcResult size: " + qcResult.size())

                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    //println("specimenId: " + specimenId)
                    def rin = rinMap.get(specimenId)
                    //println "rin: " + rin
                    if(!rin){
                        // println("get try str....")
                        def tryStr = specimenId.substring(0,specimenId.size()-1)+'6'
                        // println("specimenId: " + specimenId + " tryStr: " + tryStr)
                        rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'6')
                        //  println("rin again: " + rin )
                        if(!rin){
                            // println("try strng2: " +  specimenId.substring(0,specimenId.size()-1)+'5')
                            rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'5')
                        }
                    }
                    if(rin)
                    it.rin = formatRin(rin)
                    else 
                    it.rin = rin

                }
            }else{
                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    it.rin='Pending QA'
                }
            }
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    def getPrcSpeciemenListBms(prcReportInstance, orderby) { 
        
        def map = [:]
        map.put("tissue, delay hour, fixative", "t.name, p.displayOrder, f.displayOrder")
        map.put("tissue, fixative, delay hour", "t.name, f.displayOrder, p.displayOrder")
        map.put("delay hour, tissue, fixative", "p.displayOrder, t.name, f.displayOrder")
        map.put("delay hour, fixative, tissue", "p.displayOrder, f.displayOrder, t.name")
        map.put("fixative, tissue, delay hour", "f.displayOrder, t.name, p.displayOrder")
        map.put("fixative, delay hour, tissue", "f.displayOrder, p.displayOrder, t.name")
        
        def clause=''
        
        if(!orderby || !map.get(orderby) )
        clause = "t.name, p.displayOrder, f.displayOrder"
        else
        clause = map.get(orderby)
        
        
        
        try{
            def caseRecord = prcReportInstance.caseRecord
           
            
            def result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s, AcquisitionType t, Protocol p, Fixative f where s.tissueType = t and s.protocol = p and s.fixative=f and  s.caseRecord.id=?  order by " + clause, [caseRecord.id])
            // println("case record: " + caseRecord.caseId)
            //def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
           
         
        
            //def result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            def qcResult = QcResult.executeQuery("select s.privateId, r.value  from Donor d inner join d.specimens s inner join s.qcs q inner join q.qcResults r where d.privateId = ? and r.attribute='RIN Number' ", [caseRecord.parentCase.caseId])
            if(qcResult){
                def rinMap =[:]
                qcResult.each{
                    //println("id: " + it[0].toString() + " rin: " + it[1].toString())
                    rinMap.put(it[0].toString(), it[1].toString())                           
                }



                //println("qcResult size: " + qcResult.size())

                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    //println("specimenId: " + specimenId)
                    def rin = rinMap.get(specimenId)
                    //println "rin: " + rin
                    if(!rin){
                        // println("get try str....")
                        def tryStr = specimenId.substring(0,specimenId.size()-1)+'6'
                        // println("specimenId: " + specimenId + " tryStr: " + tryStr)
                        rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'6')
                        //  println("rin again: " + rin )
                        if(!rin){
                            // println("try strng2: " +  specimenId.substring(0,specimenId.size()-1)+'5')
                            rin = rinMap.get(specimenId.substring(0,specimenId.size()-1)+'5')
                        }
                    }
                    if(rin)
                    it.rin = formatRin(rin)
                    else 
                    it.rin = rin

                }
            }else{
                result.each{
                    def specimenId = it.specimenRecord.specimenId
                    it.rin='Pending QA'
                }
            }
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    
    def getPrcSpeciemenMapBms(prcReportInstance) { 
        def result=[:]
        try{
            
            def caseRecord = prcReportInstance.caseRecord
        
            //println("do not have result...")
            //def tissues= SpecimenRecord.executeQuery("select distinct t from SpecimenRecord s, AcquisitionType t  where s.tissueType=t and s.caseRecord.id=? and s.specimenId like '%26' order by t.name", [caseRecord.id])
            def tissues= SpecimenRecord.executeQuery("select distinct t from SpecimenRecord s, AcquisitionType t  where s.tissueType=t and s.caseRecord.id=? and s.specimenId like '%25' order by t.name", [caseRecord.id])
            tissues.each(){
                def code = it.code
                def specimenList = getPrcSpeciemenListBmsByTissue(prcReportInstance, code)
                //println("tissue: " + it.name + " specimenList size:" + specimenList.size())
                if(!specimenList)
                specimenList = getPrcSpeciemenListBmsByTissue(prcReportInstance, code)
              
                result.put(it.name, specimenList)
            }
            return result
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
    }
    
   
    
    
     
    
    
    def getPrcSpeciemenListByCase(caseRecord) { 
        try{
            def result =[] 
            //def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
            // def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s  where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) and s.specimenId like '%25' order by s.specimenId", [caseRecord.id])
          
           
            //  speciments.each(){
            //   def prcSpecimen = new PrcSpecimen()
            //     prcSpecimen.specimenRecord = it
            //     prcSpecimen.save(failOnError:true)
            //   }
           
            
            def studyCode = caseRecord.study.code
            if(studyCode == 'GTEX'){
                result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=? and s.fixative.code='XG'  order by s.specimenId", [caseRecord.id])
            }
            
            if(studyCode == 'BMS'){
                result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            }
            return result

        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    def getPrcReport(caseRecord) { 
        try{
            PrcReport result = null
            def list= PrcReport.executeQuery("select p from PrcReport p  where p.caseRecord.id=?", [caseRecord.id])
          
            if(list)
            return list.get(0)
            else
            return result
           
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getSerologyList(prcReportInstance){
        try{
            def ser = prcReportInstance.caseRecord.caseReportForm?.serologyResult
            def result=[]
            if(ser?.HIV_I_II_Ab =='Positive' || ser?.HIV_I_II_Ab == 'Indeterminate')
            result.add("HIV I/II Ab=" +ser?.HIV_I_II_Ab)
           
            if(ser?.HIV_I_II_Plus_O_antibody == 'Positive' || ser?.HIV_I_II_Plus_O_antibody == 'Indeterminate')
            result.add("HIV I/II Plus O Antibody=" +ser?.HIV_I_II_Plus_O_antibody)
              
            if(ser?.HBsAg == 'Positive' || ser?.HBsAg == 'Indeterminate' )
            result.add("HBsAg=" + ser?.HBsAg )
             
            if(ser?.HBsAb == 'Positive' || ser?.HBsAb == 'Indeterminate' )
            result.add("HBsAb=" + ser?.HBsAb )
            
            if(ser?.HBcAb == 'Positive' || ser?.HBcAb == 'Indeterminate' )
            result.add("HBcAb (Total; IgG+IgM)=" + ser?.HBcAb )
                
            if(ser?.HBcAb_IgM == 'Positive' || ser?.HBcAb_IgM == 'Indeterminate' )
            result.add("HBcAb-IgM=" + ser?.HBcAb_IgM )
                
            if(ser?.HCV_Ab == 'Positive' || ser?.HCV_Ab == 'Indeterminate' )
            result.add("HCV Ab=" + ser?.HCV_Ab )
                
            if(ser?.EBV_IgG_Ab == 'Positive' || ser?.EBV_IgG_Ab == 'Indeterminate' )
            result.add("EBV IgG Ab=" + ser?.EBV_IgG_Ab )
                
            if(ser?.EBV_IgM_Ab == 'Positive' || ser?.EBV_IgM_Ab == 'Indeterminate' )
            result.add("EBV IgM Ab=" + ser?.EBV_IgM_Ab )
                
            if(ser?.RPR == 'Positive' || ser?.RPR == 'Indeterminate' )
            result.add("RPR=" + ser?.RPR )
                
            if(ser?.CMV_Total_Ab == 'Positive' || ser?.CMV_Total_Ab == 'Indeterminate' )
            result.add("CMV Total Ab=" + ser?.CMV_Total_Ab )
                
            if(ser?.HIV_1_NAT == 'Positive' || ser?.HIV_1_NAT == 'Indeterminate' )
            result.add("HIV-1 NAT=" + ser?.HIV_1_NAT )
                
            if(ser?.HCV_1_NAT == 'Positive' || ser?.HCV_1_NAT == 'Indeterminate' )
            result.add("HCV-1 NAT=" + ser?.HCV_1_NAT )
                
            if(ser?.PRR_VDRL == 'Positive' || ser?.PRR_VDRL == 'Indeterminate' )
            result.add("PRR/VDRL=" + ser?.PRR_VDRL )
          
            return result
       
        }catch(Exception e){
            e.printStackTrace()
             
            throw new RuntimeException(e.toString())
        }
    }
    
    
    def getSerologyList2(caseRecord){
        try{
            def ser = caseRecord.caseReportForm?.serologyResult
            def result=[]
            if(ser?.HIV_I_II_Ab =='Positive' || ser?.HIV_I_II_Ab == 'Indeterminate')
            result.add("HIV I/II Ab=" +ser?.HIV_I_II_Ab)
           
            if(ser?.HIV_I_II_Plus_O_antibody == 'Positive' || ser?.HIV_I_II_Plus_O_antibody == 'Indeterminate')
            result.add("HIV I/II Plus O Antibody=" +ser?.HIV_I_II_Plus_O_antibody)
              
            if(ser?.HBsAg == 'Positive' || ser?.HBsAg == 'Indeterminate' )
            result.add("HBsAg=" + ser?.HBsAg )
             
            if(ser?.HBsAb == 'Positive' || ser?.HBsAb == 'Indeterminate' )
            result.add("HBsAb=" + ser?.HBsAb )
            
            if(ser?.HBcAb == 'Positive' || ser?.HBcAb == 'Indeterminate' )
            result.add("HBcAb (Total; IgG+IgM)=" + ser?.HBcAb )
                
            if(ser?.HBcAb_IgM == 'Positive' || ser?.HBcAb_IgM == 'Indeterminate' )
            result.add("HBcAb-IgM=" + ser?.HBcAb_IgM )
                
            if(ser?.HCV_Ab == 'Positive' || ser?.HCV_Ab == 'Indeterminate' )
            result.add("HCV Ab=" + ser?.HCV_Ab )
                
            if(ser?.EBV_IgG_Ab == 'Positive' || ser?.EBV_IgG_Ab == 'Indeterminate' )
            result.add("EBV IgG Ab=" + ser?.EBV_IgG_Ab )
                
            if(ser?.EBV_IgM_Ab == 'Positive' || ser?.EBV_IgM_Ab == 'Indeterminate' )
            result.add("EBV IgM Ab=" + ser?.EBV_IgM_Ab )
                
            if(ser?.RPR == 'Positive' || ser?.RPR == 'Indeterminate' )
            result.add("RPR=" + ser?.RPR )
                
            if(ser?.CMV_Total_Ab == 'Positive' || ser?.CMV_Total_Ab == 'Indeterminate' )
            result.add("CMV Total Ab=" + ser?.CMV_Total_Ab )
                
            if(ser?.HIV_1_NAT == 'Positive' || ser?.HIV_1_NAT == 'Indeterminate' )
            result.add("HIV-1 NAT=" + ser?.HIV_1_NAT )
                
            if(ser?.HCV_1_NAT == 'Positive' || ser?.HCV_1_NAT == 'Indeterminate' )
            result.add("HCV-1 NAT=" + ser?.HCV_1_NAT )
                
            if(ser?.PRR_VDRL == 'Positive' || ser?.PRR_VDRL == 'Indeterminate' )
            result.add("PRR/VDRL=" + ser?.PRR_VDRL )
          
            return result
       
        }catch(Exception e){
            e.printStackTrace()
             
            throw new RuntimeException(e.toString())
        }
    }
    
    
    def getPrcIssueList(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            //  def result = PrcIssue.executeQuery("select pi from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
            def result = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and (forQc is null or forQc = false) and (forFzn is null or forFzn = false)", [caseRecord.id])
         
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getPrcIssueList4Qc(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            //  def result = PrcIssue.executeQuery("select pi from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
            def result = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and forQc = true", [caseRecord.id])
         
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getPrcIssueListByCase(caseRecord) { 
        try{
     
         
            // def result = PrcIssue.executeQuery("select pi from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
            result = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and (forQc is null or forQc = false)  and (forFzn is null or forFzn = false)", [caseRecord.id])
         
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    
    def getPrcIssueList4QcByCase(caseRecord) { 
        try{
     
         
            // def result = PrcIssue.executeQuery("select pi from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
            result = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and forQc =true", [caseRecord.id])
         
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getPrcIssueTotal(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            // def result = PrcIssue.executeQuery("select count(*) from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
            def result = PrcIssue.executeQuery("select count(*) from PrcIssue  where caseRecord.id=? and (forQc is null or forQc = false)", [caseRecord.id])
           
            return result.get(0)
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        

    }
    
            
            
    def getPrcUnresolvedIssueCount(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            //def result = PrcIssue.executeQuery("select count(*) from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=? and (pi.resolved is null or pi.resolved='P' or  pi.resolved='N')", [caseRecord.id])
            def result = PrcIssue.executeQuery("select count(*) from PrcIssue pi where caseRecord.id=? and (forQc is null or forQc = false) and (forFzn is null or forFzn = false) and (pi.resolved is null or pi.resolved='P' or  pi.resolved='N')", [caseRecord.id])
           
            return result.get(0)
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getPrcUnresolvedIssueCount4Qc(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            //def result = PrcIssue.executeQuery("select count(*) from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=? and (pi.resolved is null or pi.resolved='P' or  pi.resolved='N')", [caseRecord.id])
            def result = PrcIssue.executeQuery("select count(*) from PrcIssue pi where caseRecord.id=? and forQc = true and (pi.resolved is null or pi.resolved='P' or  pi.resolved='N')", [caseRecord.id])
           
            return result.get(0)
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        

    }
    
  
    
  
    
    
    def getPrcIssueResolutionDisplayList(prcReportInstance) { 
        def result=[]
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            def resolution  = PrcIssueResolution.executeQuery("select pir from PrcIssueResolution  pir  inner join pir.prcIssue pi  where pi.caseRecord.id=?  and (pi.forQc is null or pi.forQc = false) and (pi.forFzn is null or pi.forFzn = false) order by pir.id", [caseRecord.id])
         
            resolution.each(){
              
                def map = [:]
                def tissue = it.prcIssue.specimenRecord?.tissueType?.name
                def specumenId = it.prcIssue.specimenRecord?.specimenId
                map.put("specimenId", specumenId )
                map.put("tissue", tissue)
         
                map.put("issueDescription", it.issueDescription)
                map.put("resolutionComments", it.resolutionComments)
                String pattern = "MM/dd/yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                def dateString = format.format(it.prcReportSubmission.dateSubmitted)

                map.put("dateSubmitted", dateString)
                result.add(map)
            }
            def issue = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and (forQc is null or forQc = false) and (forFzn is null or forFzn = false)", [caseRecord.id])
            issue.each(){
                
                  
                def prcIssueResolutionList = PrcIssueResolution.executeQuery("select pir from PrcIssueResolution pir inner join pir.prcIssue pi where (pi.forQc is null or pi.forQc = false) and (pi.forFzn is null or pi.forFzn = false) and pi.id=? and pir.prcReportSubmission.id = (select max(pir2.prcReportSubmission.id) from PrcIssueResolution pir2 inner join pir2.prcIssue pi2 where pi2.id=?)", [it.id, it.id])
                  
                if(prcIssueResolutionList){
                    def prcIssueResolution = prcIssueResolutionList.get(0)
                    
                    if(prcIssueResolution.issueDescription != it.issueDescription || prcIssueResolution.resolutionComments != it.resolutionComments){
                          
                        def map = [:]
                        def tissue = it.specimenRecord?.tissueType?.name
                        map.put("tissue", tissue)
                        map.put("issueDescription", it.issueDescription)
                        map.put("resolutionComments", it.resolutionComments)
                       
                        result.add(map)
                    }
                }else{
                          
                        
                    def map = [:]
                    def tissue = it.specimenRecord?.tissueType?.name
                    map.put("tissue", tissue)
                    map.put("issueDescription", it.issueDescription)
                    map.put("resolutionComments", it.resolutionComments)
                         
                    result.add(map)
                }
                    
               
            }
           
            return result
        }catch(Exception e){
            e.printStackTrace()
             
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    
    
    def getPrcReportSubList(prcReportInstance){
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            def result = PrcReportSubmission.executeQuery("select prs from PrcReportSubmission prs inner join prs.caseRecord c where c.id=? and (prs.forFzn=null or prs.forFzn=false) order by prs.id", [caseRecord.id])
         
            return result
       
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        
    }
    
    def submitReport(prcReportInstance, username, prcIssueList) {   
        try {
            def submission = prcReportInstance.currentSubmission
            submission.dateSubmitted = new Date()
            submission.submittedBy = username
            submission.save(failOnError:true)
            prcReportInstance.status = "Submitted"
            prcReportInstance.save(failOnError:true)
           
            prcIssueList.each() {
                def prcIssueResolutionList = PrcIssueResolution.executeQuery("select pir from PrcIssueResolution pir inner join pir.prcIssue pi where (pi.forQc is null or pi.forQc = false)  and (pi.forFzn is null or pi.forFzn = false) and pi.id=? and pir.prcReportSubmission.id = (select max(pir2.prcReportSubmission.id) from PrcIssueResolution pir2 inner join pir2.prcIssue pi2 where (pi2.forQc is null or pi2.forQc = false)  and (pi2.forFzn is null or pi2.forFzn = false) and pi2.id=?)", [it.id, it.id])

                if (prcIssueResolutionList) {
                    def prcIssueResolution = prcIssueResolutionList.get(0)
                    if (prcIssueResolution.issueDescription != it.issueDescription || prcIssueResolution.resolutionComments != it.resolutionComments) {
                        def pir = new PrcIssueResolution()
                        pir.issueDescription = it.issueDescription
                        pir.resolutionComments = it.resolutionComments
                        pir.prcReportSubmission = submission
                        pir.prcIssue = it
                        pir.save(failOnError:true)
                    }
                } else {
                    def pir = new PrcIssueResolution()
                    pir.issueDescription = it.issueDescription
                    pir.resolutionComments = it.resolutionComments
                    pir.prcReportSubmission = submission
                    pir.prcIssue = it
                    pir.save(failOnError:true)
                }
            }
            
            def studyCode = prcReportInstance.caseRecord.study.code
            if(studyCode == 'GTEX' && submission.reportVersion == 1){
                
                def caseRecord = prcReportInstance.caseRecord
                def feedbackInstance = new Feedback()
                  
                def feedbackSubmission = new FeedbackSubmission()
                feedbackSubmission.feedbackVersion=1
                feedbackSubmission.caseRecord = caseRecord
                feedbackSubmission.forFzn = false
                feedbackInstance.currentSubmission = feedbackSubmission
                feedbackInstance.status='Editing'
                feedbackInstance.caseRecord = caseRecord
                feedbackInstance.comments=prcReportInstance.comments
                feedbackSubmission.save(failOnError:true)
                feedbackInstance.save(failOnError:true)
                prcIssueList.each() {
                    def feedbackIssue = new FeedbackIssue()
                    feedbackIssue.specimenRecord = it.specimenRecord
                    feedbackIssue.caseRecord = it.caseRecord
                    feedbackIssue.issueDescription=it.issueDescription
                    feedbackIssue.resolutionComments = it.resolutionComments
                    feedbackIssue.forFzn = false
                    feedbackIssue.submissionCreated=feedbackSubmission
                    feedbackIssue.save(failOnError:true)
                }
                
            }
            def activityType = ActivityType.findByCode("PRCCOMP")
            def actTypeCore  = ActivityType.findByCode("COREUNACC")
            def caseId = prcReportInstance.caseRecord.caseId
            def study = prcReportInstance.caseRecord.study
            def bssCode = prcReportInstance.caseRecord.bss?.parentBss?.code
            def gtexCoreTissues = AppSetting.findByCode("GTEX_CORE_TISSUE_LIST").value
            def unaccList = []
            def emailBody = ""
            prcReportInstance.caseRecord.specimens.each(){
                if (gtexCoreTissues.contains(it.tissueType?.code)){
                    if (it.prcSpecimen?.inventoryStatus?.code == "UNACC") {
                        unaccList.add( it.tissueType.name + ": " + it.prcSpecimen?.inventoryStatus.name )
                    }
                }
            }
            if (unaccList.size() > 0) {
                unaccList.each() {
                    emailBody += it + "\n"
                }
                activityEventService.sendEmail(actTypeCore, caseId, study, bssCode, null, username, emailBody, null)
//                activityEventService.createEvent(actTypeCore, caseId, study, bssCode, null, username, 
//                    emailBody, null)
            }
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
        } catch (Exception e) {
            e.printStackTrace()    
            throw new RuntimeException(e.toString())
        }
    }
    
    
    def completeQc(prcReportInstance, username) throws Exception{
        def currentSubmission = prcReportInstance.currentSubmission
       
        prcReportInstance.qcVersion = prcReportInstance.currentSubmission
        prcReportInstance.qcReviewedBy = username
        prcReportInstance.qcReviewDate = new Date()
        prcReportInstance.status='Submitted'
        prcReportInstance.save(failOnError:true)
        
    }
    
    def prcQaReview(prcReportInstance, username){
        
        try{
            def submission = prcReportInstance.currentSubmission
            submission.dateReviewed= new Date()
            submission.reviewedBy=username
            submission.save(failOnError:true)
          
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        
    }
    
    def saveReport(params, request){
        try{
          
            
            def prcReportInstance = PrcReport.get(params.id)
            def caseRecord = prcReportInstance.caseRecord
            prcReportInstance.properties = params
            
            /** def prcCaseStatusId =params.prcCaseStatusId
            if(prcCaseStatusId){
            def prcCaseStatus = PrcCaseStatus.findById(prcCaseStatusId)
            prcReportInstance.prcCaseStatus = prcCaseStatus
            }**/
            
          
            prcReportInstance.save(failOnError:true)
            
          
            def current_time = (new Date()).getTime()
            int i = 0
            params.each(){key,value->
               // println("key: " + key + " value: " + value)
                i++;
                def ps_id
                if(key.startsWith('is_ps_id')){
                   
                    def prcSpecimen = PrcSpecimen.get(value)
                    prcSpecimen.autolysis=params["${value}_autolysis"]
                    prcSpecimen.comments=params["${value}_comments"]
                    // prcSpecimen.releaseToInventory=params["${value}_releaseToInventory"]
                    //  prcSpecimen.projectManagerFU=params["${value}_projectManagerFU"]
                    def inventoryStatusName =params["${value}_inventoryStatus"]
                    //  println("inventoryStatusName: " + inventoryStatusName)
                    def inventoryStatus = InventoryStatus.findByName(inventoryStatusName)
                    prcSpecimen.inventoryStatus = inventoryStatus
                    
                    if(inventoryStatusName=='Unacceptable'){
                         def reasons = prcSpecimen.unaccReasons
                         def reasonMap=[:]
                         reasons.each(){
                            // println("it.reason.code: " + it.reason.code)
                            reasonMap.put(it.reason.id, it)
                                 
                         }
                         def list = PrcUnaccReason.findAll()
                         list.each(){
                             def id = it.id
                            // println("code??? " +code)
                             def unaccReason = reasonMap.get(id)
                             if(!unaccReason){
                                 unaccReason = new UnaccReasonSelection()
                             }
                             unaccReason.reason=it
                             unaccReason.prcSpecimen=prcSpecimen
                             if(params.get(prcSpecimen.id + "_unacc_reason_" + id ) == 'on'){
                                 //println("find on???????" + prcSpecimen.id)
                                 unaccReason.selected = true
                             }else{
                                unaccReason.selected = false 
                             }
                            unaccReason.save(failOnError:true) 
                         }
                    }else{
                        def reasons = prcSpecimen.unaccReasons
                        reasons.each(){
                            it.selected = false
                            it.save(failOnError:true) 
                        }
                    }
                    
                    def load = params["${value}_load"]
              
                    if(load == 'Y'){
                        prcSpecimen.caption=params["${value}_caption"]
                        def uploadedFile = request.getFile("${value}_file")
                        if(uploadedFile){
                            def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            
                            def strippedFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.'))                    
                            
            
                            def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                    
          
                        
                       
                            def unique_time = current_time + i
                            
                            def newFileName = strippedFileName + "-" + unique_time + "." + fileExtension
                            
                            def pathUploads = AppSetting.findByCode("PRC_FILE").value +File.separator + caseRecord.caseId + File.separator + "summary_report_attachments" + File.separator + "images"
                            File dir = new File(pathUploads)
                            if(!dir.exists()){
                                dir.mkdirs()
                            }
                            uploadedFile.transferTo( new File(pathUploads, newFileName) )
                            prcSpecimen.filePath = pathUploads + File.separator + newFileName
                            
                    
                        }
                    }
                    
                  
                    
                  
                    prcSpecimen.save(failOnError:true)
            
                }
            }
            
            def fileRemoveId = params.paid_remove
            if(fileRemoveId){
                def prcAttachment = PrcAttachment.get(fileRemoveId)
                 
                prcAttachment.delete(failOnError:true)
            }
            
            params.each(){key,value->
               
                 
                if(key.startsWith('is_pi_id')){
               
                    def prcIssue = PrcIssue.get(value)
                    prcIssue.issueDescription =params["${value}_issueDescription"]
                    // SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy")
                    // def issueRequestDate = params["${value}_issueRequestDate_month"]+"/" + params["${value}_issueRequestDate_day"] +"/" + params["${value}_issueRequestDate_year"]
                    // prcIssue.issueRequestDate = df.parse(issueRequestDate)
                    // prcIssue.releaseToInventory=params["${value}_releaseToInventory2"]
                    prcIssue.pendingFurtherFollowUp=params["${value}_pendingFurtherFollowUp"]
                    prcIssue.resolved=params["${value}_resolved"]
                    prcIssue.resolutionComments=params["${value}_resolutionComments"]
                    
                    prcIssue.save(failOnError:true)
            
                }
            }
            
            def new_pi_specimen_id = params.new_pi_specimen_id
            def new_pi_issue_description = params.new_pi_issue_description
            if(new_pi_specimen_id  || new_pi_issue_description ){
                def prcIssue = new PrcIssue()
                prcIssue.specimenRecord = SpecimenRecord.findBySpecimenId(new_pi_specimen_id)
                
               
                /* SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy")
                def issueRequestDate =params.new_pid_issue_request_date_month+"/"+ params.new_pid_issue_request_date_day +"/" + params.new_pid_issue_request_date_year
                println("suueRequestDate: ${issueRequestDate}")*/
                
                prcIssue.issueDescription = params.new_pi_issue_description
                // prcIssue.issueRequestDate = df.parse(issueRequestDate)
        
        
                // prcIssue.releaseToInventory = params.new_pi_issue_release_to_inventory
                prcIssue.pendingFurtherFollowUp=params.new_pi_issue_pending_further_follow_up
                prcIssue.resolved =params.new_pi_issue_resolved
                prcIssue.resolutionComments = params.new_pi_issue_resolution_comments
                prcIssue.submissionCreated=prcReportInstance.currentSubmission
                prcIssue.caseRecord = caseRecord
                prcIssue.forQc = false
                prcIssue.forFzn = false
                prcIssue.save(failOnError:true)
            }
            
            def deletePi = params.delete_pi
            if(deletePi){
                def prcIssue = PrcIssue.get(deletePi)
                prcIssue.delete(failOnError:true)
            }
            
            
            params.each(){key,value->
               
               
                if(key.startsWith('is_prs_id')){
                    
                    def prcReportSubmission = PrcReportSubmission.get(value)
                    prcReportSubmission.pathologist = params["${value}_pathologist"]
                    
                    prcReportSubmission.save(failOnError:true)
            
                }
            }
            
            
            
           
            
            /*def deletePrs = params.delete_prs
            if(deletePrs){
            def prcReportSubmission = PrcReportSubmission.get(deletePrs)
            prcReportSubmission.delete(failOnError:true)
            }*/
            
            
             
          
            def new_pi_specimen_id_att = params.new_pi_specimen_id_att
            if(new_pi_specimen_id_att){
                def uploadedFile = request.getFile("new_file")
                def prcAttachment = new PrcAttachment();
                def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            
                def strippedFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.'))                    
                            
            
                def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                    
          
                        
                       
                def unique_time = current_time + i
                            
                def newFileName = strippedFileName + "-" + unique_time + "." + fileExtension
                            
                def pathUploads = AppSetting.findByCode("PRC_FILE").value +File.separator + caseRecord.caseId + File.separator + "summary_report_attachments" + File.separator + "images"
                File dir = new File(pathUploads)
                if(!dir.exists()){
                    dir.mkdirs()
                }
                uploadedFile.transferTo( new File(pathUploads, newFileName) )
                prcAttachment.filePath = pathUploads + File.separator + newFileName
                prcAttachment.caption = params.new_caption
                prcAttachment.specimenRecord = SpecimenRecord.findBySpecimenId(new_pi_specimen_id_att)
                prcAttachment.save(failOnError:true)
                 
            }
            
            
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
    }
    
    
    
    def saveReport4Qc(params, request){
        try{
          
            
            def prcReportInstance = PrcReport.get(params.id)
            def caseRecord = prcReportInstance.caseRecord
            prcReportInstance.properties = params
            
            prcReportInstance.status='QCEditing'
            
          
            prcReportInstance.save(failOnError:true)
            
          
          
            
           
            params.each(){key,value->
               
                 
                if(key.startsWith('is_pi_id')){
                    //println("in is pi id????????? value: " + value)
                    
                    def prcIssue = PrcIssue.get(value)
                    prcIssue.issueDescription =params["${value}_issueDescription"]
                    // SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy")
                    // def issueRequestDate = params["${value}_issueRequestDate_month"]+"/" + params["${value}_issueRequestDate_day"] +"/" + params["${value}_issueRequestDate_year"]
                    // prcIssue.issueRequestDate = df.parse(issueRequestDate)
                    // prcIssue.releaseToInventory=params["${value}_releaseToInventory2"]
                    prcIssue.pendingFurtherFollowUp=params["${value}_pendingFurtherFollowUp"]
                    prcIssue.resolved=params["${value}_resolved"]
                    prcIssue.resolutionComments=params["${value}_resolutionComments"]
                    
                    prcIssue.save(failOnError:true)
            
                }
            }
            
            def new_pi_specimen_id = params.new_pi_specimen_id
            def new_pi_issue_description = params.new_pi_issue_description
            if(new_pi_specimen_id  || new_pi_issue_description ){
                def prcIssue = new PrcIssue()
                prcIssue.specimenRecord = SpecimenRecord.findBySpecimenId(new_pi_specimen_id)
                
               
                /* SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy")
                def issueRequestDate =params.new_pid_issue_request_date_month+"/"+ params.new_pid_issue_request_date_day +"/" + params.new_pid_issue_request_date_year
                println("suueRequestDate: ${issueRequestDate}")*/
                
                prcIssue.issueDescription = params.new_pi_issue_description
                // prcIssue.issueRequestDate = df.parse(issueRequestDate)
        
        
                // prcIssue.releaseToInventory = params.new_pi_issue_release_to_inventory
                prcIssue.pendingFurtherFollowUp=params.new_pi_issue_pending_further_follow_up
                prcIssue.resolved =params.new_pi_issue_resolved
                prcIssue.resolutionComments = params.new_pi_issue_resolution_comments
                prcIssue.submissionCreated=prcReportInstance.currentSubmission
                prcIssue.caseRecord = caseRecord
                prcIssue.forQc = true
                prcIssue.forFzn = false
                prcIssue.save(failOnError:true)
            }
            
            def deletePi = params.delete_pi
            if(deletePi){
                def prcIssue = PrcIssue.get(deletePi)
                prcIssue.delete(failOnError:true)
            }
            
            
       
            
            
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
    }
    
    
    
    
    
    def getSpecimenList(prcReportInstance){
        try{
            def result=[]
            def caseRecord = prcReportInstance.caseRecord
         
            //def result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%25' and s not in (select s2 from PrcIssue pi inner join pi.specimenRecord s2 where s2.caseRecord.id=?) order by s.specimenId", [caseRecord.id, caseRecord.id])
            // def result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%25' order by s.specimenId", [caseRecord.id])
            if(caseRecord.study.code =='GTEX'){
                 
                //result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.prcSpecimen ps where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
                result= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s.fixative.code='XG'  order by s.specimenId", [caseRecord.id])
                // println( "result size: " + result.size())
            }
           
            if(caseRecord.study.code =='BMS'){
                result = SpecimenRecord.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%25' order by s.specimenId", [caseRecord.id])
            }
            
            return result
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    /* def getSpecimenList26(prcReportInstance){
    try{
    def caseRecord = prcReportInstance.caseRecord
         
    //def result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%25' and s not in (select s2 from PrcIssue pi inner join pi.specimenRecord s2 where s2.caseRecord.id=?) order by s.specimenId", [caseRecord.id, caseRecord.id])
    def result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%26' order by s.specimenId", [caseRecord.id])
       
    return result
       
    }catch(Exception e){
    e.printStackTrace()
            
    throw new RuntimeException(e.toString())
    }
        
    }*/
    
    def getSpecimenCount(caseRecord){
        try{
         
            def result=[]
            
            if(caseRecord.study.code =='GTEX'){
                result =SpecimenRecord.executeQuery("select count(distinct s) from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s.fixative.code='XG' order by s.specimenId", [caseRecord.id])
            }
            
            if(caseRecord.study.code =='BMS'){
                result = PrcReportSubmission.executeQuery("select count(*) from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%25' order by s.specimenId", [caseRecord.id])
                 
            }
            
            
            if(result.get(0) == 0){
                return '--'
            }
           
            return result.get(0)
       
        }catch(Exception e){
            e.printStackTrace()
            // println("catch Exception??? " + e.toString())
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    def getSpecimenCountFzn(caseRecord){
        try{
         
            def result=[]
            
            if(caseRecord.study.code =='GTEX'){
                result =SpecimenRecord.executeQuery("select count(distinct s) from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s.fixative.code='DICE' order by s.specimenId", [caseRecord.id])
            }
            
             
            
            if(result.get(0) == 0){
                return '--'
            }
           
            return result.get(0)
       
        }catch(Exception e){
            e.printStackTrace()
            // println("catch Exception??? " + e.toString())
            throw new RuntimeException(e.toString())
        }
        
    }
    
    /*  def getSpecimenCount26(caseRecord){
    try{
         
    def result = PrcReportSubmission.executeQuery("select count(*) from SpecimenRecord s inner join s.caseRecord c where c.id=?  and s.specimenId like '%26' order by s.specimenId", [caseRecord.id])
           
    return result.get(0)
       
    }catch(Exception e){
    e.printStackTrace()
    // println("catch Exception??? " + e.toString())
    throw new RuntimeException(e.toString())
    }
        
    }*/
    
    
    Map getPrcCaseMap(caseRecord){
        def map = [:]
        def study = caseRecord.study.code
        if(study=='BMS'){
            map.put("gCaseId", caseRecord?.parentCase?.caseId)
            map.put("gId", caseRecord?.parentCase?.id)
        }
        map.put("id", caseRecord.id)
        map.put("caseId", caseRecord.caseId)
            
        map.put("status", caseRecord.caseStatus)
        map.put("statusdesc", caseRecord.caseStatus.description)
        def caseReportForm
        if(study=='GTEX')
        caseReportForm=caseRecord.caseReportForm
        else
        caseReportForm=caseRecord?.parentCase?.caseReportForm
        def demographics=caseReportForm?.demographics
        def gender="&nbsp;"
        if(demographics?.gender?.toString() == 'Other'){
            gender=demographics?.otherGender
        } else{
            gender=demographics?.gender
        }
            
        map.put("gender", gender?gender:'&nbsp;')
            
        def collectionDate = caseRecord.tissueRecoveryGtex?.collectionDate
        def startTime = caseRecord.tissueRecoveryGtex?.collectionStartTime
        def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
        def cross_clamp_time = caseRecord.tissueRecoveryGtex?.crossClampTime
        def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)
                               
        def deathDate
        if(caseRecord.caseCollectionType?.code == 'POSTM' || caseRecord.caseCollectionType?.code == 'OPO' ){
            if(caseReportForm?.deathCircumstances?.dateTimeActualDeath){
                                      
                deathDate = caseReportForm?.deathCircumstances?.dateTimeActualDeath
            }else{
                                      
                deathDate = caseReportForm?.deathCircumstances?.dateTimePresumedDeath
            }
                                   
                                                                    
        }else{
                                
            deathDate = crossClampDateTime
                              
        }
                               
             
        def birthDate = demographics?.dateOfBirth
        def age =ldaccService.calculateAge(deathDate, birthDate)
        map.put("age", age?age:'&nbsp;') 
              
        def deathCircumstances = caseReportForm?.deathCircumstances
        def causeOfDeath="nbsp;"
        if(deathCircumstances?.immediateCause=='Other'){
            causeOfDeath =deathCircumstances?.otherImmediate
        }else{
            causeOfDeath =deathCircumstances?.immediateCause
        }
        map.put("causeOfDeath", causeOfDeath)
              
        
        def specimenCount= getSpecimenCount(caseRecord)
        /* if(study=='GTEX')      
        specimenCount = getSpecimenCount(caseRecord)
        else
        specimenCount = getSpecimenCount26(caseRecord)*/
        
        map.put("specimenCount", specimenCount)
              
        def specimenCountFzn= getSpecimenCountFzn(caseRecord)
        map.put("specimenCountFzn", specimenCountFzn)
              
        def prcReport = caseRecord.prcReport
        def issueTotal="&nbsp;"
        int unresolvedCount
        int unresolvedCount4Qc=0
        String qcFlag = 'white'
        if(prcReport){
            issueTotal=getPrcIssueTotal(prcReport)
            unresolvedCount=getPrcUnresolvedIssueCount(prcReport)
            unresolvedCount4Qc=getPrcUnresolvedIssueCount4Qc(prcReport)
            if(prcReport.hasQcIssue=='Yes'){
                if(unresolvedCount4Qc > 0){
                    qcFlag='red'
                }else{
                    qcFlag='green'
                }
            }else if(prcReport.matchedSeq !=null  && !prcReport.hasQcIssue){
                qcFlag='black'
            }else if(prcReport.hasQcIssue=='No'){
                qcFlag='green'
            }else{
                         
            }
        }
              
        def status="&nbsp;"
        if(unresolvedCount == 1){
            status = '1 issue'
        }
        if(unresolvedCount > 1){
            status = Integer.toString(unresolvedCount) + " issues"
        }
        map.put("issueTotal", issueTotal)
        map.put("unresolvedCount", status)
        map.put("prcReport", prcReport)
        map.put("qcFlag", qcFlag)
             
        def prcReportFzn = caseRecord.prcReportFzn
        map.put("prcReportFzn", prcReportFzn)
        def hasFr=hasFrozenSample(caseRecord)
        map.put("hasFr", hasFr) 
        def requestMade = requestMade(caseRecord)
        map.put("requestMade", requestMade)
              
        def feedback = caseRecord.feedback
        def feedbackFzn=caseRecord.feedbackFzn
        map.put("feedback", feedback)
        map.put("feedbackFzn", feedbackFzn)
             
        if(hasFr && !requestMade){
            map.put("received", sampleReceivedAtVari(caseRecord))
        }
        return map
        
    }

    
    
    
    List getPrcCaseMaps(caseList){
         
        def result=[]
        def status_map=[:]
        def gender_map=[:]
        def bod_map=[:]
        def cross_clamp_map=[:]
        def death_map=[:]
        def specimen_count_map=[:]
        def specimen_fzn_count_map=[:]
        def issue_count_map=[:]
        def unr_issue_count_map=[:]
        def unr_issue_qc_count_map=[:]
        def matched_seq_map=[:]
        def hasqc_map=[:]
        def prc_report_map=[:]
        def prc_report_fzn_map=[:]
        def cod_map=[:]
        def has_fzn_map=[:]
        def req_map=[:]
        def feedback_map=[:]
        def feedback_fzn_map=[:]
        def to_be_shipped_map=[:]
        def received_map=[:]
           
        def status_result=CaseRecord.executeQuery("select c.id, caseStatus from CaseRecord c where c in (:list)", [list: caseList])
        status_result.each(){
            status_map.put(it[0], it[1])             
        }
           
        def gender_result=CaseReportForm.executeQuery("select crf.caseRecord.id, crf.demographics.gender, crf.demographics.otherGender, crf.demographics.dateOfBirth from CaseReportForm crf  where  crf.caseRecord in (:list)", [list: caseList])
           
           
        gender_result.each(){
            if(it[1]== 'Other'){
                gender_map.put(it[0], it[2])
            }else{
                gender_map.put(it[0], it[1]) 
            }
            bod_map.put(it[0], it[3])
  
        }
           
        def  cross_clamp_result=TissueRecoveryGtex.executeQuery("select trf.caseRecord.id, trf.collectionDate, trf.collectionStartTime, trf.crossClampTime from TissueRecoveryGtex trf  where trf.caseRecord in (:list)", [list: caseList])
        cross_clamp_result.each(){
            def collectionDate = it[1]
            def startTime=it[2]
            def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
            def cross_clamp_time = it[3]
            def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)
            cross_clamp_map.put(it[0], crossClampDateTime)              
        }
           
        def death_result=  CaseReportForm.executeQuery("select crf.caseRecord.id, crf.deathCircumstances.dateTimeActualDeath, crf.deathCircumstances.dateTimePresumedDeath, crf.deathCircumstances.immediateCause, crf.deathCircumstances.otherImmediate from CaseReportForm crf  where crf.caseRecord.caseCollectionType.code in ('POSTM', 'OPO') and crf.caseRecord in (:list)", [list: caseList])
          
        death_result.each(){
            if(it[1]){
                death_map.put(it[0], it[1])
            }else{
                death_map.put(it[0], it[2])
            }
               
            if(it[3]=='Other'){
                cod_map.put(it[0], it[4])
            }else{
                cod_map.put(it[0], it[3])
            }
        }
 
        def surgi_result= CaseRecord.executeQuery("select c.id  from CaseRecord c where c.caseCollectionType.code= 'SURGI' and c in (:list)", [list: caseList])  
        surgi_result.each(){
            def cc = cross_clamp_map.get(it)
            if(cc){
                death_map.put(it, cc)
            }
        }
    
          
        
        def specimen_count_result = SpecimenRecord.executeQuery("select s.caseRecord.id, count(distinct s) from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where  s.caseRecord in (:list) and s.fixative.code='XG' group by s.caseRecord.id", [list: caseList])
        specimen_count_result.each{
            specimen_count_map.put(it[0], it[1])
        } 
           
        def specimen_fzn_count_result = SpecimenRecord.executeQuery("select s.caseRecord.id, count(distinct s) from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord in (:list) and s.fixative.code='DICE' group by s.caseRecord.id", [list: caseList])
        specimen_fzn_count_result.each{
            specimen_fzn_count_map.put(it[0], it[1])
        }      
        
        
        def issue_count_result= PrcIssue.executeQuery("select caseRecord.id, count(*) from PrcIssue  where caseRecord  in (:list) and (forQc is null or forQc = false) and (forFzn is null or forFzn = false) group by caseRecord.id", [list: caseList])
        issue_count_result.each{
            issue_count_map.put(it[0], it[1])
        }
        
        def unr_issue_count_result=PrcIssue.executeQuery("select caseRecord.id, count(*) from PrcIssue  where caseRecord  in (:list) and (forQc is null or forQc = false)  and (forFzn is null or forFzn = false)  and (resolved is null or resolved='P' or  resolved='N') group by caseRecord.id", [list: caseList])
        unr_issue_count_result.each(){
            unr_issue_count_map.put(it[0], it[1])
        }
           
        def unr_issue_qc_count_result=PrcIssue.executeQuery("select caseRecord.id, count(*) from PrcIssue pi where caseRecord in (:list) and forQc = true and (pi.resolved is null or pi.resolved='P' or  pi.resolved='N') group by caseRecord.id", [list: caseList])
        unr_issue_qc_count_result.each(){
            unr_issue_qc_count_map.put(it[0], it[1])
        }
        
        def prc_report_result = PrcReport.executeQuery("select p.caseRecord.id, p from PrcReport p where p.caseRecord in (:list)", [list: caseList])
        prc_report_result.each(){
            prc_report_map.put(it[0], it[1])
        }
           
        def prc_report_fzn_result = PrcReport.executeQuery("select p.caseRecord.id, p from PrcReportFzn p where p.caseRecord in (:list)", [list: caseList])
        prc_report_fzn_result.each(){
            prc_report_fzn_map.put(it[0], it[1])
        }
           
        def has_fzn_result= SpecimenRecord.executeQuery("select s.caseRecord.id, count(*) from SpecimenRecord s  where s.caseRecord in (:list)  and s.fixative.code='DICE' group by s.caseRecord.id ", [list: caseList])
        has_fzn_result.each(){
            has_fzn_map.put(it[0], it[1])
        }
          
        def req_result= Query.executeQuery("select q.caseRecord.id, count(*) from Query q  where q.caseRecord  in (:list) and task = 'FZN' group by q.caseRecord.id", [list: caseList])
        req_result.each(){
            req_map.put(it[0], it[1])
        }
          
        def feedback_reuslt = Feedback.executeQuery("select f.caseRecord.id, f from Feedback f where f.caseRecord in (:list)", [list: caseList])
        feedback_reuslt.each(){
            feedback_map.put(it[0], it[1])
        }
        def feedback_fzn_reuslt = Feedback.executeQuery("select f.caseRecord.id, f from FeedbackFzn f where f.caseRecord in (:list)", [list: caseList])
        feedback_fzn_reuslt.each(){
            feedback_fzn_map.put(it[0], it[1])
        }
        
        def to_be_shipped_result= SpecimenRecord.executeQuery("select s.caseRecord.id,  count(distinct s.id) from SpecimenRecord s  where s.caseRecord in (:list) and s.fixative.code='DICE'  group by s.caseRecord.id", [list: caseList])
        to_be_shipped_result.each(){
            to_be_shipped_map.put(it[0], it[1])
        }
        def received_result = ShippingEvent.executeQuery("select s.caseRecord.id, count(distinct s.id) from ShippingEvent sh inner join sh.specimens s where s.caseRecord in (:list) and s.fixative.code='DICE' and sh.recipient='VARI' and sh.shippingContentType.code='TISSUE' and sh.shippingEventType.code='USED' and sh.receiptDateTime is not null and sh.receivingUser is not null group by s.caseRecord.id", [list: caseList])
        received_result.each(){
            received_map.put(it[0], it[1])
        }  
        
        caseList.each{
            def map = [:]
            
            def id = it.id
            map.put("id", id)
            map.put("caseId", it.caseId)
            def caseStatus =  status_map.get(id)
            
            map.put("status", caseStatus)
            map.put("statusdesc", caseStatus.description)
            def gender=gender_map.get(id) 
            map.put("gender", gender?gender:'&nbsp;')
           
            
          
                               
            def deathDate = death_map.get(id)
            def birthDate = bod_map.get(id)
            def age =ldaccService.calculateAge(deathDate, birthDate)
            map.put("age", age?age:'&nbsp;') 
            def causeOfDeath= cod_map.get(id)
            map.put("causeOfDeath", causeOfDeath?causeOfDeath:'&nbsp;')
            def specimenCount =   specimen_count_map.get(id)
            if(!specimenCount)
            map.put("specimenCount", "--")
            else
            map.put("specimenCount", specimenCount)
            def specimenCountFzn =  specimen_fzn_count_map.get(id)
            if(!specimenCountFzn)
            map.put("specimenCountFzn", "--")
            else
            map.put("specimenCountFzn", specimenCountFzn)
            def prcReport = prc_report_map.get(id) 
            def issueTotal="&nbsp;"
            int unresolvedCount
            int unresolvedCount4Qc=0
            String qcFlag = 'white'
            if(prcReport){
                if (issue_count_map?.get(id)) issueTotal = issue_count_map.get(id)
                else issueTotal = 0
                 
                if (unr_issue_count_map?.get(id)) unresolvedCount=unr_issue_count_map.get(id) 
                else unresolvedCount = 0
                 
                if (unr_issue_qc_count_map?.get(id)) unresolvedCount4Qc = unr_issue_qc_count_map.get(id)
                else unresolvedCount4Qc = 0
                 
                if(prcReport.hasQcIssue=='Yes'){
                    if(unresolvedCount4Qc > 0){
                        qcFlag='red'
                    }else{
                        qcFlag='green'
                    }
                }else if(prcReport.matchedSeq !=null  && !prcReport.hasQcIssue){
                    qcFlag='black'
                }else if(prcReport.hasQcIssue=='No'){
                    qcFlag='green'
                }else{
                         
                }
            }
               
             
              
            def status="&nbsp;"
            if(unresolvedCount == 1){
                status = '1 issue'
            }
            if(unresolvedCount > 1){
                status = Integer.toString(unresolvedCount) + " issues"
            }
            map.put("issueTotal", issueTotal)
            map.put("unresolvedCount", status)
            map.put("prcReport", prcReport)
            map.put("qcFlag", qcFlag)
             
            def prcReportFzn = prc_report_fzn_map.get(id) 
            map.put("prcReportFzn", prcReportFzn)
            def hasFr = false
            def fzn_count = has_fzn_map.get(id)
            if(fzn_count){
                hasFr = true
            }
             
            map.put("hasFr", hasFr) 
            def requestMade = req_map.get(id)
            map.put("requestMade", requestMade)
              
            def feedback = feedback_map.get(id)
            def feedbackFzn=feedback_fzn_map.get(id)
            map.put("feedback", feedback)
            map.put("feedbackFzn", feedbackFzn)
             
            if(hasFr){
                boolean received = false
                def to_be = to_be_shipped_map.get(id)
                def rev=received_map.get(id)
                if(to_be > 0 && rev > 0 && to_be == rev)
                received = true
              
                  
                map.put("received", received )
            }
            return result.add(map)
        
        }
         
        return result
    }

    
    Map getPrcCaseMapBms(caseRecord){
       
        def map = [:]
        map.put("id", caseRecord.id)
        map.put("caseId", caseRecord.caseId)
        map.put("gCaseId", caseRecord?.parentCase?.caseId)
        map.put("gId", caseRecord?.parentCase?.id)
            
        map.put("status", caseRecord.caseStatus)
        map.put("statusdesc", caseRecord.caseStatus.description)
        def caseReportForm=caseRecord?.parentCase?.caseReportForm
        def demographics=caseReportForm?.demographics
        def gender="&nbsp;"
        if(demographics?.gender?.toString() == 'Other'){
            gender=demographics?.otherGender
        } else{
            gender=demographics?.gender
        }
            
        map.put("gender", gender?gender:'&nbsp;')
            
        def collectionDate = caseRecord?.parentCase?.tissueRecoveryGtex?.collectionDate
        def startTime = caseRecord?.parentCase?.tissueRecoveryGtex?.collectionStartTime
        def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
        def cross_clamp_time = caseRecord.tissueRecoveryGtex?.crossClampTime
        def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)
                               
        def deathDate
        if(caseRecord?.parentCase?.caseCollectionType?.code == 'POSTM' || caseRecord.caseCollectionType?.code == 'OPO' ){
            if(caseReportForm?.deathCircumstances?.dateTimeActualDeath){
                                      
                deathDate = caseReportForm?.deathCircumstances?.dateTimeActualDeath
            }else{
                                      
                deathDate = caseReportForm?.deathCircumstances?.dateTimePresumedDeath
            }
                                   
                                                                    
        }else{
                                
            deathDate = crossClampDateTime
                              
        }
                               
             
        def birthDate = demographics?.dateOfBirth
        def age =ldaccService.calculateAge(deathDate, birthDate)
        map.put("age", age?age:'&nbsp;') 
              
        def deathCircumstances = caseReportForm?.deathCircumstances
        def causeOfDeath="nbsp;"
        if(deathCircumstances?.immediateCause=='Other'){
            causeOfDeath =deathCircumstances?.otherImmediate
        }else{
            causeOfDeath =deathCircumstances?.immediateCause
        }
        map.put("causeOfDeath", causeOfDeath?causeOfDeath:'&nbsp;')
              
        //def specimenCount = getSpecimenCount26(caseRecord)
        def specimenCount = getSpecimenCount(caseRecord)
              
        map.put("specimenCount", specimenCount)
              
        def prcReport = caseRecord.prcReport
        def issueTotal="&nbsp;"
        int unresolvedCount
        if(prcReport){
            issueTotal=getPrcIssueTotal(prcReport)
            unresolvedCount=getPrcUnresolvedIssueCount(prcReport)
        }
              
        def status="&nbsp;"
        if(unresolvedCount == 1){
            status = '1 issue'
        }
        if(unresolvedCount > 1){
            status = Integer.toString(unresolvedCount) + " issues"
        }
        map.put("issueTotal", issueTotal)
        map.put("unresolvedCount", status)
        map.put("prcReport", prcReport)
           
              
           
        return map
        
    }

    
    Map getPrcCaseMapBpv(caseRecord){
        def map = [:]
        map.put("id", caseRecord.id)
        map.put("caseId", caseRecord.caseId)
        //map.put("bss", caseRecord.bss?.name)
        map.put("primaryOrgan", caseRecord.primaryTissueType?.name )
        map.put("status", caseRecord.caseStatus)
        def slides= SlideRecord.executeQuery("select sl from SlideRecord sl inner join sl.specimenRecord sp inner join sp.caseRecord c where c.id=? and sl.createdBy is not null and sl.createdBy != 'VARI' and sl.bpvLocalPathReview is not null  order by sl.id", [caseRecord.id])
        // println("slides size " + slides.size())
        def slides2=[]
        slides.each{
            //if(BpvLocalPathReview.findBySlideRecord(it)){
            //    println("found!!!")
            // }
               
                   
            if(it.bpvLocalPathReview){
                //println("it id: " + it.bpvLocalPathReview.id)
                slides2.add(it)
            }
        }
        map.put("slides", slides2)
        map.put("finalSurgicalPath", caseRecord.finalSurgicalPath)
        map.put("prcSignOff", caseRecord.prcSignOff)
           
        return map
          
    }
     
    
    
    List getPrcCaseMapsBpv(caseList){
        def result=[]
        def slide_map = [:]
        def slide_result= SlideRecord.executeQuery("select c.id, sl from SlideRecord sl inner join sl.specimenRecord sp inner join sp.caseRecord c where c in (:list) and sl.createdBy is not null and sl.createdBy != 'VARI' and sl.bpvLocalPathReview is not null", [list: caseList])
        slide_result.each(){
            def s_list = slide_map.get(it[0])
            if(!s_list){
                s_list = []
                slide_map.put(it[0], s_list)
            }
            if(it[1].bpvLocalPathReview){
                s_list.add(it[1])
            }
            
        }
         
        caseList.each(){
            def map = [:]
            map.put("id", it.id)
            map.put("caseId", it.caseId)
            //map.put("bss", caseRecord.bss?.name)
            map.put("primaryOrgan", it.primaryTissueType?.name )
            map.put("status", it.caseStatus)
            def slides = slide_map.get(it.id)
            map.put("slides", slides)
            map.put("finalSurgicalPath", it.finalSurgicalPath)
            map.put("prcSignOff", it.prcSignOff)
           
            result.add(map)
        }
          
        return result
    }
     
    Map getPrcCaseMapBrn(caseRecord){
        def map = [:]
        map.put("id", caseRecord.id)
        map.put("caseId", caseRecord.caseId)
        //map.put("bss", caseRecord.bss?.name)
        map.put("primaryOrgan", caseRecord.primaryTissueType?.name )
        map.put("status", caseRecord.caseStatus)
        def slides = SlideRecord.executeQuery("select sl from SlideRecord sl inner join sl.specimenRecord sp inner join sp.caseRecord c where c.id=? and sl.createdBy is not null and sl.createdBy != 'VARI' and sl.localPathologyReview is not null order by sl.id", [caseRecord.id])
        //println("slides size: " + slides.size())
        map.put("slides", slides)
        map.put("finalSurgicalPath", caseRecord.finalSurgicalPath)
          
           
        return map
          
    }
                                                                                            
    def getPrcAttachmentList(prcReportInstance) { 
        try{
            def caseRecord = prcReportInstance.caseRecord
         
            def result = PrcAttachment.executeQuery("select pa from PrcAttachment pa inner join pa.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
         
            return result
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        

    }
    
    
    def getRins(tissue_display, tissue_cdr){
        try{
            def tissueM=['Prostate', 'Testis']
            def tissueF=["Uterus","Cervix - Endocervix","Cervix - Ectocervix","Vagina","Fallopian Tube","Ovary"]
            def tissueBrain=['Frontal Cortex (BA9)','Cerebellar Hemisphere','Putamen (basal ganglia)',
                            'Hypothalamus', 'Hippocampus', 'Substantia nigra', 'Anterior cingulate cortex (BA24)',
                           'Amygdala', 'Caudate (basal ganglia)', 'Nucleus accumbens (basal ganglia)',
                            'Spinal cord (cervical c-1)']
            
            def hasBrain = []
         
            
            def genderResult= CaseRecord.executeQuery("select c.caseId, d.gender from CaseRecord c inner join c.caseReportForm f inner join f.demographics d")
            def genderMap=[:]
            genderResult.each{
                def caseId = it[0]
                def gender = it[1]
                genderMap.put(caseId, gender)
            }
         
            def rinResult = QcResult.executeQuery("select  d.privateId, s.privateId, s.tissueSiteDetail, r.value  from Donor d inner join d.specimens s inner join s.qcs q inner join q.qcResults r, CaseRecord cd where d.caseRecord =cd and r.attribute='RIN Number' order by cd.dateCreated desc")
            def rinMap = [:]
           
            rinResult.each{
                def caseId = it[0]
                def specimenId = it[1]
                def tissueSiteDetail = it[2]
                if(!tissueSiteDetail){
                  
                    def tissueName = (SpecimenRecord.findBySpecimenId(specimenId))?.tissueType?.name
                    tissueSiteDetail = tissue_cdr.get(tissueName)
                    //println("specimenId: " + specimenId + " tissueSiteDetail: " + tissueSiteDetail + " tissueName: " + tissueName )
                }
                def rin = formatRin(it[3])
                def caseMap = rinMap.get(caseId)
                if(!caseMap){
                    caseMap = [:]
                    rinMap.put(caseId, caseMap)
                }
                def old_value = caseMap.get(tissueSiteDetail)
                if(old_value && !strContains(old_value, rin)){
                    rin = old_value + ", " + rin  
                }else if(old_value && strContains(old_value, rin) ){
                    rin = old_value
                }else{
                   
                }
                caseMap.put(tissueSiteDetail, rin)
            }
           
          
            def tissueResult = CaseRecord.executeQuery("select c.caseId, s.specimenId, t.name from CaseRecord c inner join c.specimens s inner join s.tissueType t ")
            def tissueMap = [:]   
            
            tissueResult.each{
                def caseId = it[0]
                def specimenId = it[1]
                if(specimenId == caseId + "-0011")
                hasBrain.add(caseId)
                def tissueName = it[2]
                def caseMap = tissueMap.get(caseId)
                if(!caseMap){
                    caseMap = [:]
                    tissueMap.put(caseId, caseMap)
                }
                def tissueSiteDetail = tissue_cdr.get(tissueName)
                caseMap.put(tissueSiteDetail, "Yes")
            }
         
            def result = [:]
            rinMap.each{key, value->
                if(value.size() > 5){
                    def caseId = key
            
                
               
                    def valueMap = value
                    def gender =genderMap.get(caseId)
            
                
                    if(gender){ 
                        def collectMap = tissueMap.get(caseId)
                        def caseMap = result.get(caseId)
                        if(!caseMap){
                            caseMap = [:]
                            result.put(caseId, caseMap)
                        }
               
                
                        tissue_display.each{key2, value2->
                            def tissueSiteDetail = key2
                  
                            def displayValue = value2
                   
                            def contents = []
                    
                            def rin = valueMap.get(tissueSiteDetail)
                            if(!rin){
                       
                                if(gender.toString() == 'Male' && tissueF.contains(tissueSiteDetail)){
                                    rin='sex restricted'
                                }else if(gender.toString() == 'Female' && tissueM.contains(tissueSiteDetail)){
                                    rin='sex restricted'
                                }else if(hasBrain.contains(caseId) && tissueBrain.contains(tissueSiteDetail)){
                                    rin = 'N/A'
                                }else{
                            
                                    def collected =  collectMap.get(tissueSiteDetail)
                                    if(collected)
                                    rin = 'N/A'
                                    else
                                    rin = 'not collected'
                                }
                            }
                    
                            contents.add(rin)
                    
                            if(rin == 'N/A'){
                                contents.add('white')
                            }else if(rin == 'not collected'){
                                contents.add('black')
                            }else if (rin =='sex restricted' ){
                                contents.add('gray')
                            }else{
                                def style = getStyle(rin)
                                contents.add(style)
                       
                            }
                            caseMap.put(displayValue, contents)
                    
                   
                        }
                    } //if gender
               
                }
           
            }//rinMap
           
     
            return result
           
       
        }catch(Exception e){
            e.printStackTrace()
           
            throw new RuntimeException(e.toString())
        }
        
        
    }
                                                                                               
    
    def getStyle(rin){
        def result = ''
        def arr = rin.split(",")
        def max = 0;
        arr.each{
            def is = 0
            if ( it.indexOf('-') > 0){            
                def s = it.replace('-', '.')
                is = Float.parseFloat(s)                            
            }else{
                is = Float.parseFloat(it)
            }
            if(is > max)
            max = is
                        
        }                      
        if(max < 4.5){
            result = 'pink'
        }else if( max >= 4.5 && max < 6){
            result = 'yellow'
        }else{
            result = 'light_green' 
        }
        
        return result
    }
   
    
    def strContains(old_value, new_value){
        //println("somebody call me?????")
        
        def result = false
        def arr = old_value.split(",")
        arr.each{
            
             
            if(it.trim() == new_value.trim()){
                result = true
            }
            
            // if(it.trim() == '8.967177' || it.trim() == '8.436086')
            //    println("it : " + it + " new_value: " + new_value + " result:" + result)
            
        }
        return result 
    }
    
    
    def formatRin(rin){
        def result=''
        if(rin.indexOf('-') > 0){
            result = rin
        }else{
            def num = Float.parseFloat(rin)
            DecimalFormat df = new DecimalFormat("#0.0")
            result = (df.format(num)).toString()

        }
        
        return result
    }
    
    
    def signOff(params, username){
        
        try{ 
            def c = CaseRecord.findById(params.id)
            if(params.checked == 'true'){
                c.prcSignOff = true
            }else{
                c.prcSignOff = false
            }
        
            c.prcSignOffBy=username
            c.prcSignOffDate=new Date()
            c.save(failOnError:true)
        
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
    }
    
    
    def getCaseSeqNumber(caseRecord){
        if(caseRecord.study.code != 'GTEX')
        return -1
        if(caseRecord.caseStatus.code == 'INVA' || caseRecord.caseStatus.code=='WITHDR')
        return -1
        
        def count_result = CaseRecord.executeQuery("select count(*) from CaseRecord c where c.study.code='GTEX' and c.caseStatus.code  != 'WITHDR' and c.caseStatus.code  != 'INVA' and c.id <= ?",  [caseRecord.id])
        return count_result.get(0)
    }
    
    def randomlyPicked(caseRecord){
        def seqNum = getCaseSeqNumber(caseRecord)
        def result =RandomCaseSequence.findBySeqNumber(seqNum)
        if(result){
            return true;
        }else{
            return false;
        }
    }
    
    
    def hasFrozenSample(caseRecord){
        try{
            def hasFr=false
            def result=[]
        
         
     
            //result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.prcSpecimen ps where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            result= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s  where s.caseRecord.id=? and s.fixative.code='DICE' ", [caseRecord.id])
            // println( "result size: " + result.size())
            if(result)
            hasFr=true;
            
            return hasFr
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        
    }
    
    def requestMade(caseRecord){
        try{
            def made=false
            def result=[]
        
         
     
            //result = PrcReportSubmission.executeQuery("select s from SpecimenRecord s inner join s.prcSpecimen ps where s.caseRecord.id=?  order by s.specimenId", [caseRecord.id])
            result= Query.executeQuery("select distinct q from Query q  where q.caseRecord.id=? and task = 'FZN' ", [caseRecord.id])
            // println( "result size: " + result.size())
            if(result)
            made=true;
            
            return made
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    
    def sampleReceivedAtVari(caseRecord){
        try{
            def result=true
           
                 
            def samples= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s  where s.caseRecord.id=? and s.fixative.code='DICE'  order by s.specimenId", [caseRecord.id])
            def received = ShippingEvent.executeQuery("select distinct s from ShippingEvent sh inner join sh.specimens s where s.caseRecord.id=? and s.fixative.code='DICE' and sh.recipient='VARI' and sh.shippingContentType.code='TISSUE' and sh.shippingEventType.code='USED' and sh.receiptDateTime is not null and sh.receivingUser is not null", [caseRecord.id])
            
            //  println("sample size: " + samples.size() + "  received size: " + received.size())
            
            if(!samples || !received){
                return false
            }
               
            samples.each{
                if(!received.contains(it)){
                    result = false
                }
            }
         
            
            return result
       
        }catch(Exception e){
            e.printStackTrace()
            
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    def paxgReportSubmittedBy(caseRecord){
        def submitted_by = ''
     
        if(caseRecord){
            def result = PrcReportSubmission.executeQuery("select p.submittedBy from PrcReportSubmission p where p.caseRecord.id=? and (p.forFzn = false or p.forFzn is null) and p.reportVersion=1", [caseRecord.id])
            if(result)
            submitted_by = result[0]
        }
        return submitted_by
    }
    
    
    //pmh 03/20/14 cdrqa:1079
    def getEligQ15Response(prcReportInstance){
       
        try{
            def donorEligGtex = prcReportInstance?.caseRecord?.candidateRecord?.donorEligibilityGtex
            def comment=donorEligGtex?.tissueTransplantComments?.trim()
            def result=[]
            
            if(donorEligGtex?.humAnimTissueTransplant =='Yes' && comment){          
                result= ['yes',comment]                
            }
            else{
                result= ['no', comment] 
            }
           
            return result
       
        }catch(Exception e){
            e.printStackTrace()
             
            throw new RuntimeException(e.toString())
        }
    }
    
    
}
