package nci.obbr.cahub

import nci.obbr.cahub.forms.bpv.worksheet.*
import nci.obbr.cahub.staticmembers.*
import java.text.SimpleDateFormat
import nci.obbr.cahub.forms.bpv.*
import nci.obbr.cahub.datarecords.*

class BpvWorkSheetService {
    def bmsService
    static transactional = true
    
    def getT0(bpvWorkSheetInstance, side){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        def caseRecord = bpvWorkSheetInstance.caseRecord
        def surgeryForm = BpvSurgeryAnesthesiaForm.findByCaseRecord(caseRecord)
        def  surgeryDate=surgeryForm?.surgeryDate
        def organResecTimeLeft=surgeryForm?.organResecTimeLeft
        def organResecTimeRight=surgeryForm?.organResecTimeRight
        
        if(surgeryDate && organResecTimeLeft && side=='L'){
            def date_str = df.format(surgeryDate)
            def date = df2.parse(date_str + " " + organResecTimeLeft)
            return date
        }else if(surgeryDate && organResecTimeRight && side=='R'){
            def date_str = df.format(surgeryDate)
            def date = df2.parse(date_str + " " + organResecTimeRight)
            return date
        }else{
            return null
        }
    }
    
    def getT0Str(bpvWorkSheetInstance, side){
        def t0= getT0(bpvWorkSheetInstance, side)
        if(t0){
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            return df.format(t0)
        }else{
            return ""
        }
    }
    
    
    def getIntervalMap0(qcAndFrozenSampleInstance){
        def result=[:]
        def t0
        def side = getSideFromM0(qcAndFrozenSampleInstance)
        if(!side){
            t0=null
        }else{
            t0=getT0(qcAndFrozenSampleInstance.bpvWorkSheet, side)
        }
        //def t0=getT0(qcAndFrozenSampleInstance.bpvWorkSheet)
        //println("t0: " + t0)
        def list=[qcAndFrozenSampleInstance.sampleQc]
        list.each{
            def dateSampleInFixative=it.dateSampleInFixative
            def interval1=calculateInterval(t0, dateSampleInFixative)
            //println("interval1: " + interval1)
            result.put("interval1_" +it.id, interval1)
            def dateSampleInProc=it.dateSampleInProc
            def dateSampleProcEnd=it.dateSampleProcEnd
          
            // def interval2=calculateInterval7H(dateSampleInProc, dateSampleProcEnd)
            def interval2=calculateInterval7H(dateSampleInFixative, dateSampleProcEnd)
            //println("interval2_:" + interval2)
            result.put("interval2_" +it.id, interval2)
            def dateSampleEmbStarted=it.dateSampleEmbStarted
            def interval3=calculateInterval(dateSampleProcEnd, dateSampleEmbStarted,)
            result.put("interval3_" + it.id, interval3)
        } 
         
        return result   
    }
    
    
    
    def getIntervalMap1(module1SheetInstance){
        def result=[:]
        def t0
        def side = getSideFromModule(module1SheetInstance)
        if(!side){
            t0=null
        }else{
            t0=getT0(module1SheetInstance.bpvWorkSheet, side)
        }
        //def t0=getT0(module1SheetInstance.bpvWorkSheet)
        //println("t0: " + t0)
        def list=[module1SheetInstance.sampleQc, module1SheetInstance.sampleA, module1SheetInstance.sampleB, module1SheetInstance.sampleC, module1SheetInstance.sampleD]
        list.each{
            def dateSampleInFixative=it.dateSampleInFixative
            def interval1=calculateInterval(t0, dateSampleInFixative)
            //println("interval1: " + interval1)
            result.put("interval1_" +it.id, interval1)
            def dateSampleInProc=it.dateSampleInProc
            def dateSampleProcEnd=it.dateSampleProcEnd
          
            // def interval2=calculateInterval7H(dateSampleInProc, dateSampleProcEnd)
            def interval2=calculateInterval7H(dateSampleInFixative, dateSampleProcEnd)
            //println("interval2_:" + interval2)
            result.put("interval2_" +it.id, interval2)
            def dateSampleEmbStarted=it.dateSampleEmbStarted
            def interval3=calculateInterval(dateSampleProcEnd, dateSampleEmbStarted,)
            result.put("interval3_" + it.id, interval3)
        } 
         
        return result   
    }
    
    
    def getIntervalMap2(module2SheetInstance){
        def result=[:]
        def t0
        def side = getSideFromModule(module2SheetInstance)
        if(!side){
            t0=null
        }else{
            t0=getT0(module2SheetInstance.bpvWorkSheet, side)
        }
        // def t0=getT0(module2SheetInstance.bpvWorkSheet)
        // println("t0: " + t0)
        def list=[module2SheetInstance.sampleQc, module2SheetInstance.sampleE, module2SheetInstance.sampleF, module2SheetInstance.sampleG, module2SheetInstance.sampleH]
        list.each{
            def dateSampleInFixative=it.dateSampleInFixative
            def interval1=calculateInterval(t0, dateSampleInFixative)
            //println("interval1: " + interval1)
            result.put("interval1_" +it.id, interval1)
            def dateSampleInProc=it.dateSampleInProc
            def dateSampleProcEnd=it.dateSampleProcEnd
            //def interval2=calculateInterval7H(dateSampleInProc, dateSampleProcEnd)
            def interval2=calculateInterval7H(dateSampleInFixative, dateSampleProcEnd)
            //println("interval2_:" + interval2)
            result.put("interval2_" +it.id, interval2)
            def dateSampleEmbStarted=it.dateSampleEmbStarted
            def interval3=calculateInterval(dateSampleProcEnd, dateSampleEmbStarted,)
            result.put("interval3_" + it.id, interval3)
        } 
         
        return result   
    }
    
    
    def getIntervalMap3N(module3NSheetInstance){
        
        def result=[:]
        def t0
        def side = getSideFromModule(module3NSheetInstance)
        if(!side){
            t0=null
        }else{
            t0=getT0(module3NSheetInstance.bpvWorkSheet, side)
        }
        // def t0=getT0(module2SheetInstance.bpvWorkSheet)
        // println("t0: " + t0)
        def list=[module3NSheetInstance.sampleQc]
        list.each{
            def dateSampleInFixative=it.dateSampleInFixative
            def interval1=calculateInterval(t0, dateSampleInFixative)
            //println("interval1: " + interval1)
            result.put("interval1_" +it.id, interval1)
            def dateSampleInProc=it.dateSampleInProc
            def dateSampleProcEnd=it.dateSampleProcEnd
            //def interval2=calculateInterval7H(dateSampleInProc, dateSampleProcEnd)
            def interval2=calculateInterval7H(dateSampleInFixative, dateSampleProcEnd)
            //println("interval2_:" + interval2)
            result.put("interval2_" +it.id, interval2)
            def dateSampleEmbStarted=it.dateSampleEmbStarted
            def interval3=calculateInterval(dateSampleProcEnd, dateSampleEmbStarted,)
            result.put("interval3_" + it.id, interval3)
        } 
         
        return result   
        
        
        
    }
    
    
    
    def getIntervalMap4N(module4NSheetInstance){
        
        def result=[:]
        def t0
        def side = getSideFromModule(module4NSheetInstance)
        if(!side){
            t0=null
        }else{
            t0=getT0(module4NSheetInstance.bpvWorkSheet, side)
        }
        // def t0=getT0(module2SheetInstance.bpvWorkSheet)
        // println("t0: " + t0)
        def list=[module4NSheetInstance.sampleQc]
        list.each{
            def dateSampleInFixative=it.dateSampleInFixative
            def interval1=calculateInterval(t0, dateSampleInFixative)
            //println("interval1: " + interval1)
            result.put("interval1_" +it.id, interval1)
            def dateSampleInProc=it.dateSampleInProc
            def dateSampleProcEnd=it.dateSampleProcEnd
            //def interval2=calculateInterval7H(dateSampleInProc, dateSampleProcEnd)
            def interval2=calculateInterval7H(dateSampleInFixative, dateSampleProcEnd)
            //println("interval2_:" + interval2)
            result.put("interval2_" +it.id, interval2)
            def dateSampleEmbStarted=it.dateSampleEmbStarted
            def interval3=calculateInterval(dateSampleProcEnd, dateSampleEmbStarted,)
            result.put("interval3_" + it.id, interval3)
        } 
         
        return result   
        
        
        
    }
    
    
    def getIntervalMap5(module5SheetInstance){
        
        def result=[:]
        def t0
        def side = getSideFromModule(module5SheetInstance)
        if(!side){
            t0=null
        }else{
            t0=getT0(module5SheetInstance.bpvWorkSheet, side)
        }
        // def t0=getT0(module2SheetInstance.bpvWorkSheet)
        // println("t0: " + t0)
        def list=[module5SheetInstance.sampleQc]
        list.each{
            def dateSampleInFixative=it.dateSampleInFixative
            def interval1=calculateInterval(t0, dateSampleInFixative)
            //println("interval1: " + interval1)
            result.put("interval1_" +it.id, interval1)
            def dateSampleInProc=it.dateSampleInProc
            def dateSampleProcEnd=it.dateSampleProcEnd
            //def interval2=calculateInterval7H(dateSampleInProc, dateSampleProcEnd)
            def interval2=calculateInterval7H(dateSampleInFixative, dateSampleProcEnd)
            //println("interval2_:" + interval2)
            result.put("interval2_" +it.id, interval2)
            def dateSampleEmbStarted=it.dateSampleEmbStarted
            def interval3=calculateInterval(dateSampleProcEnd, dateSampleEmbStarted,)
            result.put("interval3_" + it.id, interval3)
        } 
         
        return result   
        
        
        
    }
    
    def saveWorkSeet(bpvWorkSheetInstance) {
        // println("start save???")
        try{
            def caseId =bpvWorkSheetInstance.caseRecord.caseId
            def parentSampleId = caseId + "-00"
            bpvWorkSheetInstance.parentSampleId=parentSampleId
            bpvWorkSheetInstance.formVersion=2
            bpvWorkSheetInstance.save(failOnError:true)
           
            
            
            def m1s = new Module1Sheet()
            m1s.bpvWorkSheet = bpvWorkSheetInstance
            m1s.module = Module.findByCode('MODULE1')
            def  sampleFr1 = new Timepoint()
            sampleFr1.save(failOnError:true)
            m1s.sampleFr = sampleFr1
            
              def sampleQc1 = new Timepoint()
            
             sampleQc1.protocol='QC FFPE (Middle Left):<br/>23 hours in fixative'
              sampleQc1.plannedDelay='<1 hour'
         
              sampleQc1.protocolId="protocol_qc"
               sampleQc1.save(failOnError:true)
               m1s.sampleQc = sampleQc1
            
            def sampleA = new Timepoint()
            //sampleA.displayOrder = 2
            sampleA.protocol='Protocol A:<br/>6 hours in fixative'
            sampleA.plannedDelay='<1 hour'
            // sampleA.rowColor='CCFFFF'
            sampleA.protocolId="protocol_a"
            sampleA.save(failOnError:true)
            m1s.sampleA = sampleA
            
            def sampleB = new Timepoint()
            //sampleB.displayOrder = 3
            sampleB.protocol='Protocol B:<br/>12 hours in fixative'
            sampleB.plannedDelay='<1 hour'
            // sampleB.rowColor='FFCCCC'
            sampleB.protocolId="protocol_b"
            sampleB.save(failOnError:true)
            m1s.sampleB = sampleB
            
            def sampleC = new Timepoint()
            // sampleC.displayOrder = 4
            sampleC.protocol='Protocol C:<br/>23 hours in fixative'
            sampleC.plannedDelay='<1 hour'
            sampleC.protocolId="protocol_c"
            // sampleC.rowColor='CCFFCC'
            sampleC.save(failOnError:true)
            m1s.sampleC = sampleC
            
            
            def sampleD = new Timepoint()
            //sampleD.displayOrder = 5
            sampleD.protocol='Protocol D:<br/>72 hours in fixative'
            sampleD.plannedDelay='<1 hour'
            // sampleD.rowColor='FFCCFF'
            sampleD.protocolId="protocol_d"
            sampleD.save(failOnError:true)
            m1s.sampleD = sampleD
            
            m1s.save(failOnError:true)
           
            
            def m2s = new Module2Sheet()
            m2s.bpvWorkSheet = bpvWorkSheetInstance
            m2s.module = Module.findByCode('MODULE2')
            
            
            def  sampleFr2 = new Timepoint()
            sampleFr2.save(failOnError:true)
            m2s.sampleFr = sampleFr2
            
            def sampleQc2 = new Timepoint()
            
            sampleQc2.protocol='QC FFPE (Middle Left):<br/>23 hours in fixative'
            sampleQc2.plannedDelay='<1 hour'
         
            sampleQc2.protocolId="protocol_qc"
            sampleQc2.save(failOnError:true)
            m2s.sampleQc = sampleQc2
            
            
            def sampleE = new Timepoint()
            //sampleE.displayOrder = 1
            sampleE.protocol='Protocol E:<br/>12 hours in fixative'
            sampleE.plannedDelay='1 hour'
            // sampleE.rowColor='FFFFCC'
            sampleE.protocolId="protocol_e"
            sampleE.save(failOnError:true)
            m2s.sampleE = sampleE
             
              
            def sampleF = new Timepoint()
            //sampleF.displayOrder = 2
            sampleF.protocol='Protocol F:<br/>11 hours in fixative'
            sampleF.plannedDelay='2 hour'
            sampleF.protocolId="protocol_f"
            // sampleF.rowColor='CCFFFF'
            sampleF.save(failOnError:true)
            m2s.sampleF = sampleF
            
            
            def sampleG = new Timepoint()
            //sampleG.displayOrder = 3
            sampleG.protocol='Protocol G:<br/>10 hours in fixative'
            sampleG.plannedDelay='3 hour'
            sampleG.protocolId="protocol_g"
            // sampleG.rowColor='FFCCCC'
            sampleG.save(failOnError:true)
            m2s.sampleG = sampleG
            
            def sampleH = new Timepoint()
            //sampleH.displayOrder = 4
            sampleH.protocol='Protocol H:<br/>12 hours in fixative'
            sampleH.plannedDelay='12 hour'
            // sampleH.rowColor='CCFFCC'
            sampleH.protocolId="protocol_h"
            sampleH.save(failOnError:true)
            m2s.sampleH = sampleH
             
            m2s.save(failOnError:true)
             
            def m3s = new Module3Sheet()
            m3s.bpvWorkSheet = bpvWorkSheetInstance
            m3s.module = Module.findByCode('MODULE3')
            m3s.save(failOnError:true)
             
            def m4s = new Module4Sheet()
            m4s.bpvWorkSheet = bpvWorkSheetInstance
            m4s.module = Module.findByCode('MODULE4')
            m4s.save(failOnError:true)
            
            
           
             def m3ns = new Module3NSheet()
            m3ns.bpvWorkSheet = bpvWorkSheetInstance
            m3ns.module = Module.findByCode('MODULE3N')
            
            
            def  sampleFr3 = new Timepoint()
            sampleFr3.save(failOnError:true)
            m3ns.sampleFr = sampleFr3
            
            def sampleQc3 = new Timepoint()
            
            sampleQc3.protocol='QC FFPE:<br/>23 hours in fixative'
            sampleQc3.plannedDelay='<1 hour'
         
            sampleQc3.protocolId="protocol_qc"
            sampleQc3.save(failOnError:true)
            m3ns.sampleQc = sampleQc3
            
            
            def sampleI = new Timepoint()
            sampleI.protocol='Protocol I'
            sampleI.freezeMethod='Dry Ice'
            sampleI.transTo='-80&deg;C Freezer'
            sampleI.save(failOnError:true)
            m3ns.sampleI = sampleI
            
            def sampleJ = new Timepoint()
            sampleJ.protocol='Protocol J'
            sampleJ.freezeMethod='Dry Ice'
            sampleJ.transTo='-80&deg;C Freezer'
            sampleJ.save(failOnError:true)
            m3ns.sampleJ = sampleJ
            
            def sampleK = new Timepoint()
            sampleK.protocol='Protocol K'
            sampleK.freezeMethod='Dry Ice'
            sampleK.transTo='-80&deg;C Freezer'
            sampleK.save(failOnError:true)
            m3ns.sampleK = sampleK
            
            def sampleL = new Timepoint()
            sampleL.protocol='Protocol L'
            sampleL.freezeMethod='Dry Ice'
            sampleL.transTo='LN Freezer'
            sampleL.save(failOnError:true)
            m3ns.sampleL = sampleL
            
            def sampleM = new Timepoint()
            sampleM.protocol='Protocol M'
            sampleM.freezeMethod='Dry Ice'
            sampleM.transTo='LN Freezer'
            sampleM.save(failOnError:true)
            m3ns.sampleM = sampleM
            
            def sampleN = new Timepoint()
            sampleN.protocol='Protocol N'
            sampleN.freezeMethod='Dry Ice'
            sampleN.transTo='LN Freezer'
            sampleN.save(failOnError:true)
            m3ns.sampleN = sampleN
             
           
            m3ns.save(failOnError:true)
            
            
            
             
             def m4ns = new Module4NSheet()
            m4ns.bpvWorkSheet = bpvWorkSheetInstance
            m4ns.module = Module.findByCode('MODULE4N')
            
            
            def  sampleFr4 = new Timepoint()
            sampleFr4.save(failOnError:true)
            m4ns.sampleFr = sampleFr4
            
            def sampleQc4 = new Timepoint()
            
            sampleQc4.protocol='QC FFPE:<br/>23 hours in fixative'
            sampleQc4.plannedDelay='<1 hour'
         
            sampleQc4.protocolId="protocol_qc"
            sampleQc4.save(failOnError:true)
            m4ns.sampleQc = sampleQc4
            
            
            def sampleO = new Timepoint()
            sampleO.protocol='Protocol O'
            sampleO.freezeMethod='LN2 Vapor Phase'
            sampleO.transTo='-80&deg;C Freezer'
            sampleO.save(failOnError:true)
            m4ns.sampleO = sampleO
            
            def sampleP = new Timepoint()
            sampleP.protocol='Protocol P'
            sampleP.freezeMethod='LN2 Vapor Phase'
            sampleP.transTo='-80&deg;C Freezer'
            sampleP.save(failOnError:true)
            m4ns.sampleP = sampleP
            
            def sampleQ = new Timepoint()
            sampleQ.protocol='Protocol Q'
            sampleQ.freezeMethod='LN2 Vapor Phase'
            sampleQ.transTo='-80&deg;C Freezer'
            sampleQ.save(failOnError:true)
            m4ns.sampleQ = sampleQ
            
            def sampleR = new Timepoint()
            sampleR.protocol='Protocol R'
            sampleR.freezeMethod='LN2 Vapor Phase'
            sampleR.transTo='LN Freezer'
            sampleR.save(failOnError:true)
            m4ns.sampleR = sampleR
            
            def sampleS = new Timepoint()
            sampleS.protocol='Protocol S'
            sampleS.freezeMethod='LN2 Vapor Phase'
            sampleS.transTo='LN Freezer'
            sampleS.save(failOnError:true)
            m4ns.sampleS = sampleS
            
            def sampleT = new Timepoint()
            sampleT.protocol='Protocol T'
            sampleT.freezeMethod='LN2 Vapor Phase'
            sampleT.transTo='LN Freezer'
            sampleT.save(failOnError:true)
            m4ns.sampleT = sampleT
             
           
            m4ns.save(failOnError:true)
            
            
            
           
                 def m5s = new Module5Sheet()
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
            
                  
              
            
          
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }

    }

   
    def updateWorkSheet(bpvWorkSheetInstance, params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
        try{
            bpvWorkSheetInstance.properties = params
            bpvWorkSheetInstance.experimentId=params.get('experimentId_' + params.id)
            def dateEidRecordedStr=params.dateEidRecordedStr
          
            if(isDate(dateEidRecordedStr)){
                bpvWorkSheetInstance.dateEidRecorded=df.parse(dateEidRecordedStr)
            }else{
                bpvWorkSheetInstance.dateEidRecorded=null
            }
            if(bpvWorkSheetInstance.sm1){
                bpvWorkSheetInstance.m1=true
            }
            
            if(bpvWorkSheetInstance.sm2){
                bpvWorkSheetInstance.m2=true
            }
          
             if(bpvWorkSheetInstance.sm3){
                bpvWorkSheetInstance.m3=true
            }
          
             if(bpvWorkSheetInstance.sm4){
                bpvWorkSheetInstance.m4=true
            }
            
            if(bpvWorkSheetInstance.sm5){
                bpvWorkSheetInstance.m5=true
            }
             if(bpvWorkSheetInstance.snat){
                bpvWorkSheetInstance.nat=true
            }
            
             if(bpvWorkSheetInstance.sett){
                bpvWorkSheetInstance.ett=true
            }
            bpvWorkSheetInstance.save(failOnError:true)
        
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
   
    
    
    def updateModule1(module1SheetInstance, params){
        //          params.each{key,value->
        //              println("key: " + key  + " value: " + value)
        //              
        //          }
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try{
            module1SheetInstance.properties = params
            module1SheetInstance.save(failOnError:true)
         
            //println("?????????")
            
            
            def sampleFr=module1SheetInstance.sampleFr
            sampleFr.sampleId4Frozen=params.get('sampleId4Frozen_' +sampleFr.id)
            def dateSampleFrozenStr=params.get("dateSampleFrozenStr_" + sampleFr.id)
            sampleFr.dateSampleFrozenStr=dateSampleFrozenStr
            def sampleFrWeight = params.get("weight_" + sampleFr.id)
            
             if(sampleFrWeight)
               sampleFr.weight = new Float(sampleFrWeight)
             else
               sampleFr.weight = null 
          
          
            if(isDate(dateSampleFrozenStr)){
                sampleFr.dateSampleFrozen=df.parse(dateSampleFrozenStr)
            }else{
                sampleFr.dateSampleFrozen=null
            }
            sampleFr.save(failOnError:true)
          
            def list=[module1SheetInstance.sampleQc, module1SheetInstance.sampleA, module1SheetInstance.sampleB, module1SheetInstance.sampleC, module1SheetInstance.sampleD]
            list.each{
                it.sampleId4Record=params.get("sampleId4Record_" + it.id)
                def dateSampleRecordedStr=params.get("dateSampleRecordedStr_" + it.id)
                it.dateSampleRecordedStr=dateSampleRecordedStr
                //println("I am here???")
              
                if(isDate(dateSampleRecordedStr)){
                    it.dateSampleRecorded=df.parse(dateSampleRecordedStr)
                }else{
                    it.dateSampleRecorded=null
                }
                 
             
                it.sampleId4Fixative=params.get("sampleId4Fixative_" +it.id)
                def dateSampleInFixativeStr=params.get("dateSampleInFixativeStr_" + it.id)
                it.dateSampleInFixativeStr=dateSampleInFixativeStr
              
                if(isDate(dateSampleInFixativeStr)){
                    it.dateSampleInFixative=df.parse(dateSampleInFixativeStr)
                }else{
                    it.dateSampleInFixative=null
                }
                 
                
                
                it.sampleId4Proc=params.get("sampleId4Proc_" + it.id)
                def dateSampleInProcStr=params.get("dateSampleInProcStr_" + it.id)
                it.dateSampleInProcStr=dateSampleInProcStr
              
                if(isDate(dateSampleInProcStr)){
                    it.dateSampleInProc=df.parse(dateSampleInProcStr)
                }else{
                    it.dateSampleInProc=null
                }
              
            
                def dateSampleProcEndStr=params.get("dateSampleProcEndStr_" + it.id)
                it.dateSampleProcEndStr=dateSampleProcEndStr
              
                if(isDate(dateSampleProcEndStr)){
                    it.dateSampleProcEnd=df.parse(dateSampleProcEndStr)
                }else{
                    it.dateSampleProcEnd=null
                }
                
              
                it.sampleId4Removal=params.get("sampleId4Removal_" + it.id)
                def dateSampleRemovedStr=params.get("dateSampleRemovedStr_" + it.id)
                it.dateSampleRemovedStr=dateSampleRemovedStr
              
                if(isDate(dateSampleRemovedStr)){
                    it.dateSampleRemoved=df.parse(dateSampleRemovedStr)
                }else{
                    it.dateSampleRemoved=null
                }
              
               
                it.sampleId4Embedding=params.get("sampleId4Embedding_" + it.id)
          
                def dateSampleEmbStartedStr=params.get("dateSampleEmbStartedStr_" + it.id)
                it.dateSampleEmbStartedStr=dateSampleEmbStartedStr
              
                if(isDate(dateSampleEmbStartedStr)){
                    it.dateSampleEmbStarted=df.parse(dateSampleEmbStartedStr)
                }else{
                    it.dateSampleEmbStarted=null
                }
                
                
             
              
            
            }
         
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    def updateModule2(module2SheetInstance, params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try{
            module2SheetInstance.properties = params
            module2SheetInstance.save(failOnError:true)
        
             def sampleFr=module2SheetInstance.sampleFr
            sampleFr.sampleId4Frozen=params.get('sampleId4Frozen_' +sampleFr.id)
            def dateSampleFrozenStr=params.get("dateSampleFrozenStr_" + sampleFr.id)
            sampleFr.dateSampleFrozenStr=dateSampleFrozenStr
            
            def sampleFrWeight = params.get("weight_" + sampleFr.id)
            if(sampleFrWeight)
               sampleFr.weight = new Float(sampleFrWeight)
          
            if(isDate(dateSampleFrozenStr)){
                sampleFr.dateSampleFrozen=df.parse(dateSampleFrozenStr)
            }else{
                sampleFr.dateSampleFrozen=null
            }
            sampleFr.save(failOnError:true)
            
            
            def list=[module2SheetInstance.sampleQc, module2SheetInstance.sampleE, module2SheetInstance.sampleF, module2SheetInstance.sampleG, module2SheetInstance.sampleH]
            list.each{
                it.sampleId4Record=params.get("sampleId4Record_" + it.id)
                def dateSampleRecordedStr=params.get("dateSampleRecordedStr_" + it.id)
                it.dateSampleRecordedStr=dateSampleRecordedStr
                //println("I am here???")
              
                if(isDate(dateSampleRecordedStr)){
                    it.dateSampleRecorded=df.parse(dateSampleRecordedStr)
                }else{
                    it.dateSampleRecorded=null
                }
                 
             
                it.sampleId4Fixative=params.get("sampleId4Fixative_" +it.id)
                def dateSampleInFixativeStr=params.get("dateSampleInFixativeStr_" + it.id)
                it.dateSampleInFixativeStr=dateSampleInFixativeStr
              
                if(isDate(dateSampleInFixativeStr)){
                    it.dateSampleInFixative=df.parse(dateSampleInFixativeStr)
                }else{
                    it.dateSampleInFixative=null
                }
                 
                
                
                it.sampleId4Proc=params.get("sampleId4Proc_" + it.id)
                def dateSampleInProcStr=params.get("dateSampleInProcStr_" + it.id)
                it.dateSampleInProcStr=dateSampleInProcStr
              
                if(isDate(dateSampleInProcStr)){
                    it.dateSampleInProc=df.parse(dateSampleInProcStr)
                }else{
                    it.dateSampleInProc=null
                }
              
            
                def dateSampleProcEndStr=params.get("dateSampleProcEndStr_" + it.id)
                it.dateSampleProcEndStr=dateSampleProcEndStr
              
                if(isDate(dateSampleProcEndStr)){
                    it.dateSampleProcEnd=df.parse(dateSampleProcEndStr)
                }else{
                    it.dateSampleProcEnd=null
                }
                
              
                it.sampleId4Removal=params.get("sampleId4Removal_" + it.id)
                def dateSampleRemovedStr=params.get("dateSampleRemovedStr_" + it.id)
                it.dateSampleRemovedStr=dateSampleRemovedStr
              
                if(isDate(dateSampleRemovedStr)){
                    it.dateSampleRemoved=df.parse(dateSampleRemovedStr)
                }else{
                    it.dateSampleRemoved=null
                }
              
               
                it.sampleId4Embedding=params.get("sampleId4Embedding_" + it.id)
          
                def dateSampleEmbStartedStr=params.get("dateSampleEmbStartedStr_" + it.id)
                it.dateSampleEmbStartedStr=dateSampleEmbStartedStr
              
                if(isDate(dateSampleEmbStartedStr)){
                    it.dateSampleEmbStarted=df.parse(dateSampleEmbStartedStr)
                }else{
                    it.dateSampleEmbStarted=null
                }
                
                
             
              
             
            }
         
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    
    
     def updateModule3N(module3NSheetInstance, params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try{
            module3NSheetInstance.properties = params
            module3NSheetInstance.save(failOnError:true)
        
             def sampleFr=module3NSheetInstance.sampleFr
            sampleFr.sampleId4Frozen=params.get('sampleId4Frozen_' +sampleFr.id)
            def dateSampleFrozenStr=params.get("dateSampleFrozenStr_" + sampleFr.id)
            sampleFr.dateSampleFrozenStr=dateSampleFrozenStr
            
            def sampleFrWeight = params.get("weight_" + sampleFr.id)
            if(sampleFrWeight)
               sampleFr.weight = new Float(sampleFrWeight)
            else
               sampleFr.weight = null
          
           // println("here????")
            if(isDate(dateSampleFrozenStr)){
                sampleFr.dateSampleFrozen=df.parse(dateSampleFrozenStr)
            }else{
                sampleFr.dateSampleFrozen=null
            }
            sampleFr.save(failOnError:true)
            
            
            def list=[module3NSheetInstance.sampleQc]
            list.each{
                it.sampleId4Record=params.get("sampleId4Record_" + it.id)
                def dateSampleRecordedStr=params.get("dateSampleRecordedStr_" + it.id)
                it.dateSampleRecordedStr=dateSampleRecordedStr
                //println("I am here???")
              
                if(isDate(dateSampleRecordedStr)){
                    it.dateSampleRecorded=df.parse(dateSampleRecordedStr)
                }else{
                    it.dateSampleRecorded=null
                }
                 
             
                it.sampleId4Fixative=params.get("sampleId4Fixative_" +it.id)
                def dateSampleInFixativeStr=params.get("dateSampleInFixativeStr_" + it.id)
                it.dateSampleInFixativeStr=dateSampleInFixativeStr
              
                if(isDate(dateSampleInFixativeStr)){
                    it.dateSampleInFixative=df.parse(dateSampleInFixativeStr)
                }else{
                    it.dateSampleInFixative=null
                }
                 
                
                
                it.sampleId4Proc=params.get("sampleId4Proc_" + it.id)
                def dateSampleInProcStr=params.get("dateSampleInProcStr_" + it.id)
                it.dateSampleInProcStr=dateSampleInProcStr
              
                if(isDate(dateSampleInProcStr)){
                    it.dateSampleInProc=df.parse(dateSampleInProcStr)
                }else{
                    it.dateSampleInProc=null
                }
              
            
                def dateSampleProcEndStr=params.get("dateSampleProcEndStr_" + it.id)
                it.dateSampleProcEndStr=dateSampleProcEndStr
              
                if(isDate(dateSampleProcEndStr)){
                    it.dateSampleProcEnd=df.parse(dateSampleProcEndStr)
                }else{
                    it.dateSampleProcEnd=null
                }
                
              
                it.sampleId4Removal=params.get("sampleId4Removal_" + it.id)
                def dateSampleRemovedStr=params.get("dateSampleRemovedStr_" + it.id)
                it.dateSampleRemovedStr=dateSampleRemovedStr
              
                if(isDate(dateSampleRemovedStr)){
                    it.dateSampleRemoved=df.parse(dateSampleRemovedStr)
                }else{
                    it.dateSampleRemoved=null
                }
              
               
                it.sampleId4Embedding=params.get("sampleId4Embedding_" + it.id)
          
                def dateSampleEmbStartedStr=params.get("dateSampleEmbStartedStr_" + it.id)
                it.dateSampleEmbStartedStr=dateSampleEmbStartedStr
              
                if(isDate(dateSampleEmbStartedStr)){
                    it.dateSampleEmbStarted=df.parse(dateSampleEmbStartedStr)
                }else{
                    it.dateSampleEmbStarted=null
                }
                
                
             
              
             
            }
            
             def list2=[module3NSheetInstance.sampleI, module3NSheetInstance.sampleJ, module3NSheetInstance.sampleK, module3NSheetInstance.sampleL, module3NSheetInstance.sampleM, module3NSheetInstance.sampleN ]
            list2.each{
                 it.sampleId4Frozen=params.get("sampleId4Frozen_" + it.id)
                def dateSampleFrozenStr2=params.get("dateSampleFrozenStr_" + it.id)
                it.dateSampleFrozenStr=dateSampleFrozenStr2
                //println("I am here???")
              
                if(dateSampleFrozenStr2){
                    it.dateSampleFrozen=df.parse(dateSampleFrozenStr2)
                }else{
                    it.dateSampleFrozen=null
                }
                 
                
                
                  it.sampleId4Trans=params.get("sampleId4Trans_" + it.id)
                def dateSampleTransStr=params.get("dateSampleTransStr_" + it.id)
                it.dateSampleTransStr=dateSampleTransStr
                //println("I am here???")
              
                if(isDate(dateSampleTransStr)){
                    it.dateSampleTrans=df.parse(dateSampleTransStr)
                }else{
                    it.dateSampleTrans=null
                }
                
                 def weight = params.get("weight_" + it.id)
                if(weight)
                    it.weight = new Float(weight)
                else
                    it.weight = null
                
             
            }
            
         
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    
    def updateModule4N(module4NSheetInstance, params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try{
            module4NSheetInstance.properties = params
            module4NSheetInstance.save(failOnError:true)
        
             def sampleFr=module4NSheetInstance.sampleFr
            sampleFr.sampleId4Frozen=params.get('sampleId4Frozen_' +sampleFr.id)
            def dateSampleFrozenStr=params.get("dateSampleFrozenStr_" + sampleFr.id)
            sampleFr.dateSampleFrozenStr=dateSampleFrozenStr
            
            def sampleFrWeight = params.get("weight_" + sampleFr.id)
            if(sampleFrWeight)
               sampleFr.weight = new Float(sampleFrWeight)
            else
               sampleFr.weight = null
          
           // println("here????")
            if(isDate(dateSampleFrozenStr)){
                sampleFr.dateSampleFrozen=df.parse(dateSampleFrozenStr)
            }else{
                sampleFr.dateSampleFrozen=null
            }
            sampleFr.save(failOnError:true)
            
            
            def list=[module4NSheetInstance.sampleQc]
            list.each{
                it.sampleId4Record=params.get("sampleId4Record_" + it.id)
                def dateSampleRecordedStr=params.get("dateSampleRecordedStr_" + it.id)
                it.dateSampleRecordedStr=dateSampleRecordedStr
                //println("I am here???")
              
                if(isDate(dateSampleRecordedStr)){
                    it.dateSampleRecorded=df.parse(dateSampleRecordedStr)
                }else{
                    it.dateSampleRecorded=null
                }
                 
             
                it.sampleId4Fixative=params.get("sampleId4Fixative_" +it.id)
                def dateSampleInFixativeStr=params.get("dateSampleInFixativeStr_" + it.id)
                it.dateSampleInFixativeStr=dateSampleInFixativeStr
              
                if(isDate(dateSampleInFixativeStr)){
                    it.dateSampleInFixative=df.parse(dateSampleInFixativeStr)
                }else{
                    it.dateSampleInFixative=null
                }
                 
                
                
                it.sampleId4Proc=params.get("sampleId4Proc_" + it.id)
                def dateSampleInProcStr=params.get("dateSampleInProcStr_" + it.id)
                it.dateSampleInProcStr=dateSampleInProcStr
              
                if(isDate(dateSampleInProcStr)){
                    it.dateSampleInProc=df.parse(dateSampleInProcStr)
                }else{
                    it.dateSampleInProc=null
                }
              
            
                def dateSampleProcEndStr=params.get("dateSampleProcEndStr_" + it.id)
                it.dateSampleProcEndStr=dateSampleProcEndStr
              
                if(isDate(dateSampleProcEndStr)){
                    it.dateSampleProcEnd=df.parse(dateSampleProcEndStr)
                }else{
                    it.dateSampleProcEnd=null
                }
                
              
                it.sampleId4Removal=params.get("sampleId4Removal_" + it.id)
                def dateSampleRemovedStr=params.get("dateSampleRemovedStr_" + it.id)
                it.dateSampleRemovedStr=dateSampleRemovedStr
              
                if(isDate(dateSampleRemovedStr)){
                    it.dateSampleRemoved=df.parse(dateSampleRemovedStr)
                }else{
                    it.dateSampleRemoved=null
                }
              
               
                it.sampleId4Embedding=params.get("sampleId4Embedding_" + it.id)
          
                def dateSampleEmbStartedStr=params.get("dateSampleEmbStartedStr_" + it.id)
                it.dateSampleEmbStartedStr=dateSampleEmbStartedStr
              
                if(isDate(dateSampleEmbStartedStr)){
                    it.dateSampleEmbStarted=df.parse(dateSampleEmbStartedStr)
                }else{
                    it.dateSampleEmbStarted=null
                }
                
                
             
              
             
            }
            
             def list2=[module4NSheetInstance.sampleO, module4NSheetInstance.sampleP, module4NSheetInstance.sampleQ, module4NSheetInstance.sampleR, module4NSheetInstance.sampleS, module4NSheetInstance.sampleT ]
            list2.each{
                 it.sampleId4Frozen=params.get("sampleId4Frozen_" + it.id)
                def dateSampleFrozenStr2=params.get("dateSampleFrozenStr_" + it.id)
                it.dateSampleFrozenStr=dateSampleFrozenStr2
                //println("I am here???")
              
                if(dateSampleFrozenStr2){
                    it.dateSampleFrozen=df.parse(dateSampleFrozenStr2)
                }else{
                    it.dateSampleFrozen=null
                }
                 
                
                
                  it.sampleId4Trans=params.get("sampleId4Trans_" + it.id)
                def dateSampleTransStr=params.get("dateSampleTransStr_" + it.id)
                it.dateSampleTransStr=dateSampleTransStr
                //println("I am here???")
              
                if(isDate(dateSampleTransStr)){
                    it.dateSampleTrans=df.parse(dateSampleTransStr)
                }else{
                    it.dateSampleTrans=null
                }
                
                 def weight = params.get("weight_" + it.id)
                if(weight)
                    it.weight = new Float(weight)
                else
                    it.weight = null
                
             
            }
            
         
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    def updateModule3(module3SheetInstance, params){
          
        try{
            
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            if(isDate(module3SheetInstance.ntFfpeTimeStr1)){
                module3SheetInstance.ntFfpeTime1=df.parse(module3SheetInstance.ntFfpeTimeStr1)
            }else{
                module3SheetInstance.ntFfpeTime1=null
            }
                
            if(isDate(module3SheetInstance.ntFfpeTimeStr2)){
                module3SheetInstance.ntFfpeTime2=df.parse(module3SheetInstance.ntFfpeTimeStr2)
            }else{
                module3SheetInstance.ntFfpeTime2=null
            }
                
            if(isDate(module3SheetInstance.ntFfpeTimeStr3)){
                module3SheetInstance.ntFfpeTime3=df.parse(module3SheetInstance.ntFfpeTimeStr3)
            }else{
                module3SheetInstance.ntFfpeTime3=null
            }
            
            if(isDate(module3SheetInstance.ntFrozenTimeStr1)){
                module3SheetInstance.ntFrozenTime1=df.parse(module3SheetInstance.ntFrozenTimeStr1)
            }else{
                module3SheetInstance.ntFrozenTime1=null
            }
                
            if(isDate(module3SheetInstance.ntFrozenTimeStr2)){
                module3SheetInstance.ntFrozenTime2=df.parse(module3SheetInstance.ntFrozenTimeStr2)
            }else{
                module3SheetInstance.ntFrozenTime2=null
            }
                
            
            if(isDate(module3SheetInstance.ntFrozenTimeStr3)){
                module3SheetInstance.ntFrozenTime3=df.parse(module3SheetInstance.ntFrozenTimeStr3)
            }else{
                module3SheetInstance.ntFrozenTime3=null
            }
            if(module3SheetInstance.ntFixativeTimeInRange=='Yes'){
                module3SheetInstance.ntReasonNotInRange=null
            }
                 
            //module3SheetInstance.properties = params
            module3SheetInstance.save(failOnError:true)
        
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    
    def updateModule4(module4SheetInstance, params){
          
        try{
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            if(isDate(module4SheetInstance.ttFfpeTimeStr1)){
                module4SheetInstance.ttFfpeTime1=df.parse(module4SheetInstance.ttFfpeTimeStr1)
            }else{
                module4SheetInstance.ttFfpeTime1=null
            }
                
            if(isDate(module4SheetInstance.ttFfpeTimeStr2)){
                module4SheetInstance.ttFfpeTime2=df.parse(module4SheetInstance.ttFfpeTimeStr2)
            }else{
                module4SheetInstance.ttFfpeTime2=null
            }
                
            if(isDate(module4SheetInstance.ttFfpeTimeStr3)){
                module4SheetInstance.ttFfpeTime3=df.parse(module4SheetInstance.ttFfpeTimeStr3)
            }else{
                module4SheetInstance.ttFfpeTime3=null
            }
            
            if(isDate(module4SheetInstance.ttFrozenTimeStr1)){
                module4SheetInstance.ttFrozenTime1=df.parse(module4SheetInstance.ttFrozenTimeStr1)
            }else{
                module4SheetInstance.ttFrozenTime1=null
            }
                
            if(isDate(module4SheetInstance.ttFrozenTimeStr2)){
                module4SheetInstance.ttFrozenTime2=df.parse(module4SheetInstance.ttFrozenTimeStr2)
            }else{
                module4SheetInstance.ttFrozenTime2=null
            }
                
            
            if(isDate(module4SheetInstance.ttFrozenTimeStr3)){
                module4SheetInstance.ttFrozenTime3=df.parse(module4SheetInstance.ttFrozenTimeStr3)
            }else{
                module4SheetInstance.ttFrozenTime3=null
            }
                
            if(module4SheetInstance.ttFixativeTimeInRange=='Yes'){
                module4SheetInstance.ttReasonNotInRange=null
            }
            
            // module4SheetInstance.properties = params
            module4SheetInstance.save(failOnError:true)
        
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
     def updateModule5(module5SheetInstance, params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try{
            module5SheetInstance.properties = params
            module5SheetInstance.save(failOnError:true)
        
             def sampleFr=module5SheetInstance.sampleFr
            sampleFr.sampleId4Frozen=params.get('sampleId4Frozen_' +sampleFr.id)
            def dateSampleFrozenStr=params.get("dateSampleFrozenStr_" + sampleFr.id)
            sampleFr.dateSampleFrozenStr=dateSampleFrozenStr
            
            def sampleFrWeight = params.get("weight_" + sampleFr.id)
            if(sampleFrWeight)
               sampleFr.weight = new Float(sampleFrWeight)
            else
               sampleFr.weight = null
          
           // println("here????")
            if(isDate(dateSampleFrozenStr)){
                sampleFr.dateSampleFrozen=df.parse(dateSampleFrozenStr)
            }else{
                sampleFr.dateSampleFrozen=null
            }
            sampleFr.save(failOnError:true)
            
            
            def list=[module5SheetInstance.sampleQc]
            list.each{
                it.sampleId4Record=params.get("sampleId4Record_" + it.id)
                def dateSampleRecordedStr=params.get("dateSampleRecordedStr_" + it.id)
                it.dateSampleRecordedStr=dateSampleRecordedStr
                //println("I am here???")
              
                if(isDate(dateSampleRecordedStr)){
                    it.dateSampleRecorded=df.parse(dateSampleRecordedStr)
                }else{
                    it.dateSampleRecorded=null
                }
                 
             
                it.sampleId4Fixative=params.get("sampleId4Fixative_" +it.id)
                def dateSampleInFixativeStr=params.get("dateSampleInFixativeStr_" + it.id)
                it.dateSampleInFixativeStr=dateSampleInFixativeStr
              
                if(isDate(dateSampleInFixativeStr)){
                    it.dateSampleInFixative=df.parse(dateSampleInFixativeStr)
                }else{
                    it.dateSampleInFixative=null
                }
                 
                
                
                it.sampleId4Proc=params.get("sampleId4Proc_" + it.id)
                def dateSampleInProcStr=params.get("dateSampleInProcStr_" + it.id)
                it.dateSampleInProcStr=dateSampleInProcStr
              
                if(isDate(dateSampleInProcStr)){
                    it.dateSampleInProc=df.parse(dateSampleInProcStr)
                }else{
                    it.dateSampleInProc=null
                }
              
            
                def dateSampleProcEndStr=params.get("dateSampleProcEndStr_" + it.id)
                it.dateSampleProcEndStr=dateSampleProcEndStr
              
                if(isDate(dateSampleProcEndStr)){
                    it.dateSampleProcEnd=df.parse(dateSampleProcEndStr)
                }else{
                    it.dateSampleProcEnd=null
                }
                
              
                it.sampleId4Removal=params.get("sampleId4Removal_" + it.id)
                def dateSampleRemovedStr=params.get("dateSampleRemovedStr_" + it.id)
                it.dateSampleRemovedStr=dateSampleRemovedStr
              
                if(isDate(dateSampleRemovedStr)){
                    it.dateSampleRemoved=df.parse(dateSampleRemovedStr)
                }else{
                    it.dateSampleRemoved=null
                }
              
               
                it.sampleId4Embedding=params.get("sampleId4Embedding_" + it.id)
          
                def dateSampleEmbStartedStr=params.get("dateSampleEmbStartedStr_" + it.id)
                it.dateSampleEmbStartedStr=dateSampleEmbStartedStr
              
                if(isDate(dateSampleEmbStartedStr)){
                    it.dateSampleEmbStarted=df.parse(dateSampleEmbStartedStr)
                }else{
                    it.dateSampleEmbStarted=null
                }
                
                
             
              
             
            }
            
             def list2=[module5SheetInstance.sampleU, module5SheetInstance.sampleV, module5SheetInstance.sampleW, module5SheetInstance.sampleX]
            list2.each{
                 it.sampleId4Frozen=params.get("sampleId4Frozen_" + it.id)
                def dateSampleFrozenStr2=params.get("dateSampleFrozenStr_" + it.id)
                it.dateSampleFrozenStr=dateSampleFrozenStr2
                //println("I am here???")
              
                if(dateSampleFrozenStr2){
                    it.dateSampleFrozen=df.parse(dateSampleFrozenStr2)
                }else{
                    it.dateSampleFrozen=null
                }
                 
                
                
                  it.sampleId4Trans=params.get("sampleId4Trans_" + it.id)
                def dateSampleTransStr=params.get("dateSampleTransStr_" + it.id)
                it.dateSampleTransStr=dateSampleTransStr
                //println("I am here???")
              
                if(isDate(dateSampleTransStr)){
                    it.dateSampleTrans=df.parse(dateSampleTransStr)
                }else{
                    it.dateSampleTrans=null
                }
                
                 def weight = params.get("weight_" + it.id)
                if(weight)
                    it.weight = new Float(weight)
                else
                    it.weight = null
                
             
            }
            
         
            
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    
    static boolean isDate(dateStr){
        boolean result=false
        if(!dateStr)
        return false
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        df.setLenient(false);
        try{
        
            def date = df.parse(dateStr)
            result=true
        }catch (Exception e){
             
        }
          
        return result
        
        
    }
    
    def getInterval(String startDate, String endDate){
        if(isDate(startDate) && isDate(endDate)){
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            return calculateInterval(df.parse(startDate), df.parse(endDate))
        }else{
            return "&nbsp;"
        }
    }
    
    def getInterval7H(String startDate, String endDate){
        if(isDate(startDate) && isDate(endDate)){
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            return calculateInterval7H(df.parse(startDate), df.parse(endDate))
        }else{
            return "&nbsp;"
        }
    }
    
    
    def calculateInterval(Date startDate, Date endDate){
        if(startDate && endDate){
            
            //int min = (endDate.time - startDate.time)/(60*1000)
            
            //return Integer.toString(min)
            return bmsService.convertToHHMM(endDate.time - startDate.time)

                
        }else{
            return "&nbsp;"
        }
        
    }
    
    
    def calculateInterval7H(Date startDate, Date endDate){
        if(startDate && endDate){
            
            //int min = (endDate.time - startDate.time)/(60*1000)
            //int min2 = min - 7*60 -22
            
            // return Integer.toString(min2)
           
            return bmsService.convertToHHMM(endDate.time - startDate.time - (7*60 + 22)*60*1000)

                
        }else{
            return "&nbsp;"
        }
        
    }
    
    
    
    def submitForm(bpvWorkSheetInstance){
        //println("start????")
       
        try{
            def caseRecord = bpvWorkSheetInstance.caseRecord
            bpvWorkSheetInstance.dateSubmitted = new Date()
           // bpvWorkSheetInstance.submittedModule=bpvWorkSheetInstance.chooseModule
            // bpvWorkSheetInstance.save(failOnError:true)  
               
            def parent= bpvWorkSheetInstance.sample
            if(!parent){
                parent = SpecimenRecord.findBySpecimenId(bpvWorkSheetInstance.parentSampleId)
            }
            if(!parent){
                parent= new SpecimenRecord()  
            }
               
               
            parent.specimenId=bpvWorkSheetInstance.parentSampleId
            parent.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
            parent.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
            parent.caseRecord=bpvWorkSheetInstance.caseRecord
            parent.fixative=Fixative.findByCode('FRESH')
            parent.wasConsumed=true
            parent.containerType=ContainerType.findByCode('TRAY')
            parent.save(failOnError:true)
            //println("after save parent.....")
            bpvWorkSheetInstance.sample = parent
            bpvWorkSheetInstance.sm1 = false
            bpvWorkSheetInstance.sm2 = false
            bpvWorkSheetInstance.sm3 = false
            bpvWorkSheetInstance.sm4 = false
            bpvWorkSheetInstance.snat = false
            bpvWorkSheetInstance.sett = false
            
            if(bpvWorkSheetInstance.m1){
                bpvWorkSheetInstance.sm1=true
            }
            
            if(bpvWorkSheetInstance.m2){
                bpvWorkSheetInstance.sm2=true
            }
            
            if(bpvWorkSheetInstance.m3){
                bpvWorkSheetInstance.sm3=true
            }
            
            if(bpvWorkSheetInstance.m4){
                bpvWorkSheetInstance.sm4=true
            }
            
             if(bpvWorkSheetInstance.m5){
                bpvWorkSheetInstance.sm5=true
            }
            
            if(bpvWorkSheetInstance.nat){
                bpvWorkSheetInstance.snat=true
            }
            
            if(bpvWorkSheetInstance.ett){
                bpvWorkSheetInstance.sett=true
            }
            
            
            
            
            
            bpvWorkSheetInstance.save(failOnError:true)
                
            // println("after save worksheet...")      
                
            if(bpvWorkSheetInstance.m1){
                 
                def sampleFr =  bpvWorkSheetInstance.module1Sheet.sampleFr
                def sampleQc = bpvWorkSheetInstance.module1Sheet.sampleQc
                def sampleA = bpvWorkSheetInstance.module1Sheet.sampleA
                def sampleB = bpvWorkSheetInstance.module1Sheet.sampleB
                def sampleC = bpvWorkSheetInstance.module1Sheet.sampleC
                def sampleD = bpvWorkSheetInstance.module1Sheet.sampleD
                   
                def protocolMap = [:]
                   
               // protocolMap.put(sampleFr,  Protocol.findByCode('FROZEN'))
                 protocolMap.put(sampleQc,  Protocol.findByCode('BPV_QCFFPE'))
                protocolMap.put(sampleA,  Protocol.findByCode('BPV_PROTOCOL_A'))
                protocolMap.put(sampleB,  Protocol.findByCode('BPV_PROTOCOL_B'))
                protocolMap.put(sampleC,  Protocol.findByCode('BPV_PROTOCOL_C'))
                protocolMap.put(sampleD,  Protocol.findByCode('BPV_PROTOCOL_D'))
                   
                def list = [sampleFr, sampleQc, sampleA, sampleB, sampleC, sampleD]
                list.each{
                     if(it.sampleId4Record || it.sampleId4Frozen){
                     def protocol = protocolMap.get(it)
                    
                    def s = it.sample
                    //println("specimenid: " + it.sampleId4Record)
                    if(!s && !protocol){
                        s=SpecimenRecord.findBySpecimenId(it.sampleId4Frozen)
                    }
                    if(!s && protocol){
                        s =SpecimenRecord.findBySpecimenId(it.sampleId4Record) 
                    }
                    if(!s){
                        s = new SpecimenRecord()
                    }
                        
                    if(!protocol){
                        s.specimenId=it.sampleId4Frozen
                    }else{
                        s.specimenId=it.sampleId4Record
                    }
                    s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.caseRecord=bpvWorkSheetInstance.caseRecord
                   
                   
                    // if(protocol){
                    //   println("protocol:" + protocol.name)
                    //  }else{
                    //   println("protocol is null")
                    //  }
                    s.protocol=protocolMap.get(it)
                    s.parentSpecimen=parent
                    
                    if(!protocol){
                         s.fixative=Fixative.findByCode('FROZEN')
                         s.containerType=ContainerType.findByCode('CRYOSETTE')
                    }else{
                         s.fixative=Fixative.findByCode('FFPE')
                         s.containerType=ContainerType.findByCode('CASSETTE')
                    }
                    
                    s.save(failOnError:true)
                        
                    if(protocol?.code == 'BPV_QCFFPE' && caseRecord?.bpvSlidePrep?.dateSubmitted){
                        // def slides = SlideRecord.findAllByCaseId(caseRecord.caseId)
                        def slides = SlideRecord.findAllByCaseId(caseRecord.id.toString())
                        slides.each{it2->
                            if(it2.module.code == 'MODULE1' && !it2.specimenRecord){
                                it2.specimenRecord=s
                                it2.save(failOnError:true)
                            }
                        }
                    }
                    
                   if(protocol){
                        def chpTissueRecord = s.chpTissueRecord
                        if(!chpTissueRecord){
                            chpTissueRecord= new ChpTissueRecord()
                        }

                        chpTissueRecord.timeInCass=it.dateSampleRecorded
                        chpTissueRecord.timeInFix=it.dateSampleInFixative
                        chpTissueRecord.timeInProcessor=it.dateSampleInProc
                        chpTissueRecord.procTimeEnd=it.dateSampleProcEnd
                        chpTissueRecord.procTimeRemov=it.dateSampleRemoved
                        chpTissueRecord.timeEmbedded=it.dateSampleEmbStarted
                        chpTissueRecord.specimenRecord=s
                        chpTissueRecord.save(failOnError:true)

                        s.chpTissueRecord=chpTissueRecord
                        s.save(failOnError:true)
                        it.sample=s
                        it.save(failOnError:true)

                     } 
                   }
                       
                }
                
                   
            }
              
            
            
             if(bpvWorkSheetInstance.m2){
                 
                def sampleFr =  bpvWorkSheetInstance.module2Sheet.sampleFr
                def sampleQc = bpvWorkSheetInstance.module2Sheet.sampleQc
                def sampleE = bpvWorkSheetInstance.module2Sheet.sampleE
                def sampleF = bpvWorkSheetInstance.module2Sheet.sampleF
                def sampleG = bpvWorkSheetInstance.module2Sheet.sampleG
                def sampleH= bpvWorkSheetInstance.module2Sheet.sampleH
                   
                def protocolMap = [:]
                   
                //protocolMap.put(sampleFr,  Protocol.findByCode('FROZEN'))
                protocolMap.put(sampleQc,  Protocol.findByCode('BPV_QCFFPE'))
                protocolMap.put(sampleE,  Protocol.findByCode('BPV_PROTOCOL_E'))
                protocolMap.put(sampleF,  Protocol.findByCode('BPV_PROTOCOL_F'))
                protocolMap.put(sampleG,  Protocol.findByCode('BPV_PROTOCOL_G'))
                protocolMap.put(sampleH,  Protocol.findByCode('BPV_PROTOCOL_H'))
                   
                def list = [sampleFr, sampleQc, sampleE, sampleF, sampleG, sampleH]
                list.each{
                    if(it.sampleId4Record || it.sampleId4Frozen){
                    def protocol = protocolMap.get(it)
                    def s = it.sample
                    //println("specimenid: " + it.sampleId4Record)
                     if(!s && !protocol){
                        s= SpecimenRecord.findBySpecimenId(it.sampleId4Frozen)
                    }
                    if(!s && protocol){
                        s =SpecimenRecord.findBySpecimenId(it.sampleId4Record) 
                    }
                    if(!s){
                        s = new SpecimenRecord()
                    }
                     
                    if(!protocol){
                        s.specimenId=it.sampleId4Frozen
                    }else{
                        s.specimenId=it.sampleId4Record
                    }
                    s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.caseRecord=bpvWorkSheetInstance.caseRecord
                   
                    
                    // if(protocol){
                    //   println("protocol:" + protocol.name)
                    //  }else{
                    //   println("protocol is null")
                    //  }
                    s.protocol=protocolMap.get(it)
                    s.parentSpecimen=parent
                    
                    if(!protocol){
                         s.fixative=Fixative.findByCode('FROZEN')
                         s.containerType=ContainerType.findByCode('CRYOSETTE')
                    }else{
                         s.fixative=Fixative.findByCode('FFPE')
                         s.containerType=ContainerType.findByCode('CASSETTE')
                    }
                    
                    s.save(failOnError:true)
                        
                    if(protocol?.code == 'BPV_QCFFPE' && caseRecord?.bpvSlidePrep?.dateSubmitted){
                        // def slides = SlideRecord.findAllByCaseId(caseRecord.caseId)
                        def slides = SlideRecord.findAllByCaseId(caseRecord.id.toString())
                        slides.each{it2->
                            if(it2.module.code == 'MODULE2' && !it2.specimenRecord){
                                it2.specimenRecord=s
                                it2.save(failOnError:true)
                            }
                        }
                    }
                    
                   if(protocol){
                        def chpTissueRecord = s.chpTissueRecord
                        if(!chpTissueRecord){
                            chpTissueRecord= new ChpTissueRecord()
                        }

                        chpTissueRecord.timeInCass=it.dateSampleRecorded
                        chpTissueRecord.timeInFix=it.dateSampleInFixative
                        chpTissueRecord.timeInProcessor=it.dateSampleInProc
                        chpTissueRecord.procTimeEnd=it.dateSampleProcEnd
                        chpTissueRecord.procTimeRemov=it.dateSampleRemoved
                        chpTissueRecord.timeEmbedded=it.dateSampleEmbStarted
                        chpTissueRecord.specimenRecord=s
                        chpTissueRecord.save(failOnError:true)

                        s.chpTissueRecord=chpTissueRecord
                        s.save(failOnError:true)
                        it.sample=s
                        it.save(failOnError:true)

                     } 
                       
                }
                
                }   
            }
               
           //  println("before m3....")
            
              if(bpvWorkSheetInstance.m3){
                 
                def sampleFr =  bpvWorkSheetInstance.module3NSheet.sampleFr
                def sampleQc = bpvWorkSheetInstance.module3NSheet.sampleQc
                def sampleI = bpvWorkSheetInstance.module3NSheet.sampleI
                def sampleJ = bpvWorkSheetInstance.module3NSheet.sampleJ
                def sampleK = bpvWorkSheetInstance.module3NSheet.sampleK
                def sampleL= bpvWorkSheetInstance.module3NSheet.sampleL
                def sampleM= bpvWorkSheetInstance.module3NSheet.sampleM
                def sampleN= bpvWorkSheetInstance.module3NSheet.sampleN
                   
                def protocolMap = [:]
                   
                //protocolMap.put(sampleFr,  Protocol.findByCode('FROZEN'))
                protocolMap.put(sampleQc,  Protocol.findByCode('BPV_QCFFPE'))
                protocolMap.put(sampleI,  Protocol.findByCode('BPV_PROTOCOL_I'))
                protocolMap.put(sampleJ,  Protocol.findByCode('BPV_PROTOCOL_J'))
                protocolMap.put(sampleK,  Protocol.findByCode('BPV_PROTOCOL_K'))
                protocolMap.put(sampleL,  Protocol.findByCode('BPV_PROTOCOL_L'))
                protocolMap.put(sampleM,  Protocol.findByCode('BPV_PROTOCOL_M'))
                protocolMap.put(sampleN,  Protocol.findByCode('BPV_PROTOCOL_N'))
                
                
              
                   
                def list = [ sampleQc]
                list.each{
                    def s = it.sample
                    //println("specimenid: " + it.sampleId4Record)
                    if(!s){
                        s =SpecimenRecord.findBySpecimenId(it.sampleId4Record) 
                    }
                    
                    if(!s){
                        s = new SpecimenRecord()
                    }
                     
                  
                    s.specimenId=it.sampleId4Record
                    
                    s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.caseRecord=bpvWorkSheetInstance.caseRecord
                   
                    def protocol = protocolMap.get(it)
                   
                    s.protocol=protocolMap.get(it)
                    s.parentSpecimen=parent
                    
                   
                    s.fixative=Fixative.findByCode('FFPE')
                    s.containerType=ContainerType.findByCode('CASSETTE')
                    
                    
                    s.save(failOnError:true)
                    //println("after qc sample")
                        
                   if(protocol.code == 'BPV_QCFFPE' && caseRecord?.bpvSlidePrep?.dateSubmitted){
                        // def slides = SlideRecord.findAllByCaseId(caseRecord.caseId)
                        def slides = SlideRecord.findAllByCaseId(caseRecord.id.toString())
                        slides.each{it2->
                            if(it2.module.code == 'MODULE3N' && !it2.specimenRecord){
                                it2.specimenRecord=s
                                it2.save(failOnError:true)
                            }
                        }
                    }
                    
                
                        def chpTissueRecord = s.chpTissueRecord
                        if(!chpTissueRecord){
                            chpTissueRecord= new ChpTissueRecord()
                        }

                        chpTissueRecord.timeInCass=it.dateSampleRecorded
                        chpTissueRecord.timeInFix=it.dateSampleInFixative
                        chpTissueRecord.timeInProcessor=it.dateSampleInProc
                        chpTissueRecord.procTimeEnd=it.dateSampleProcEnd
                        chpTissueRecord.procTimeRemov=it.dateSampleRemoved
                        chpTissueRecord.timeEmbedded=it.dateSampleEmbStarted
                        chpTissueRecord.specimenRecord=s
                        chpTissueRecord.save(failOnError:true)
                        s.chpTissueRecord=chpTissueRecord
                        s.save(failOnError:true)
                        it.sample=s
                        it.save(failOnError:true)
              
                     
                       
                }
                
             
                
                def list2 = [ sampleFr,  sampleI, sampleJ, sampleK, sampleL, sampleM, sampleN]
                list2.each(){
                    
                    def protocol = protocolMap.get(it)
                   def s = it.sample
                   if(!s){
                        s =SpecimenRecord.findBySpecimenId(it.sampleId4Frozen)
                   }
                   if(!s){
                     s = new SpecimenRecord() 
                   }
                   
                   s.specimenId=it.sampleId4Frozen
                   s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                   s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                   s.caseRecord=bpvWorkSheetInstance.caseRecord
                   if(!protocol){
                     s.fixative=Fixative.findByCode('FROZEN')
                   }else{
                       s.fixative=Fixative.findByCode('DICE')
                   }
                   s.containerType=ContainerType.findByCode('CRYOSETTE')
                   s.parentSpecimen=parent
                   s.protocol = protocol
                   s.save(failOnError:true)
                    
                   it.sample=s
                   it.save(failOnError:true)
                    
                    
                }
                
                
                
                
            }
               
            if(bpvWorkSheetInstance.m4){
                 
                def sampleFr =  bpvWorkSheetInstance.module4NSheet.sampleFr
                def sampleQc = bpvWorkSheetInstance.module4NSheet.sampleQc
                def sampleO = bpvWorkSheetInstance.module4NSheet.sampleO
                def sampleP = bpvWorkSheetInstance.module4NSheet.sampleP
                def sampleQ = bpvWorkSheetInstance.module4NSheet.sampleQ
                def sampleR= bpvWorkSheetInstance.module4NSheet.sampleR
                def sampleS= bpvWorkSheetInstance.module4NSheet.sampleS
                def sampleT= bpvWorkSheetInstance.module4NSheet.sampleT
                   
                def protocolMap = [:]
                   
                //protocolMap.put(sampleFr,  Protocol.findByCode('FROZEN'))
                protocolMap.put(sampleQc,  Protocol.findByCode('BPV_QCFFPE'))
                protocolMap.put(sampleO,  Protocol.findByCode('BPV_PROTOCOL_O'))
                protocolMap.put(sampleP,  Protocol.findByCode('BPV_PROTOCOL_P'))
                protocolMap.put(sampleQ,  Protocol.findByCode('BPV_PROTOCOL_Q'))
                protocolMap.put(sampleR,  Protocol.findByCode('BPV_PROTOCOL_R'))
                protocolMap.put(sampleS,  Protocol.findByCode('BPV_PROTOCOL_S'))
                protocolMap.put(sampleT,  Protocol.findByCode('BPV_PROTOCOL_T'))
                
                
              
                   
                def list = [ sampleQc]
                list.each{
                    def s = it.sample
                    //println("specimenid: " + it.sampleId4Record)
                   
                    s =SpecimenRecord.findBySpecimenId(it.sampleId4Record) 
                    
                    if(!s){
                        s = new SpecimenRecord()
                    }
                     
                  
                    s.specimenId=it.sampleId4Record
                    
                    s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.caseRecord=bpvWorkSheetInstance.caseRecord
                   
                    def protocol = protocolMap.get(it)
                   
                    s.protocol=protocolMap.get(it)
                    s.parentSpecimen=parent
                    
                   
                    s.fixative=Fixative.findByCode('FFPE')
                    s.containerType=ContainerType.findByCode('CASSETTE')
                    
                    
                    s.save(failOnError:true)
                        
                    if(protocol.code == 'BPV_QCFFPE' && caseRecord?.bpvSlidePrep?.dateSubmitted){
                        // def slides = SlideRecord.findAllByCaseId(caseRecord.caseId)
                        def slides = SlideRecord.findAllByCaseId(caseRecord.id.toString())
                        slides.each{it2->
                            if(it2.module.code == 'MODULE4N' && !it2.specimenRecord){
                                it2.specimenRecord=s
                                it2.save(failOnError:true)
                            }
                        }
                    }
                    
                 
                        def chpTissueRecord = s.chpTissueRecord
                        if(!chpTissueRecord){
                            chpTissueRecord= new ChpTissueRecord()
                        }

                        chpTissueRecord.timeInCass=it.dateSampleRecorded
                        chpTissueRecord.timeInFix=it.dateSampleInFixative
                        chpTissueRecord.timeInProcessor=it.dateSampleInProc
                        chpTissueRecord.procTimeEnd=it.dateSampleProcEnd
                        chpTissueRecord.procTimeRemov=it.dateSampleRemoved
                        chpTissueRecord.timeEmbedded=it.dateSampleEmbStarted
                        chpTissueRecord.specimenRecord=s
                        chpTissueRecord.save(failOnError:true)

                        s.chpTissueRecord=chpTissueRecord
                        s.save(failOnError:true)
                        it.sample=s
                        it.save(failOnError:true)

                     
                       
                }
                
                
                def list2 = [ sampleFr, sampleO, sampleP, sampleQ, sampleR, sampleS, sampleT]
                list2.each(){
                    def protocol = protocolMap.get(it)
                   def s = it.sample
                   if(!s){
                        s =SpecimenRecord.findBySpecimenId(it.sampleId4Frozen) 
                   }
                   if(!s){
                     s = new SpecimenRecord() 
                   }
                   s.specimenId=it.sampleId4Frozen
                   s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                   s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                   s.caseRecord=bpvWorkSheetInstance.caseRecord
                   if(!protocol){
                     s.fixative=Fixative.findByCode('FROZEN')
                   }else{
                       s.fixative=Fixative.findByCode('LN2VAP')
                   }
                   s.containerType=ContainerType.findByCode('CRYOSETTE')
                   s.parentSpecimen=parent
                   s.protocol = protocol
                   s.save(failOnError:true)
                   it.sample=s
                   it.save(failOnError:true)
                    
                    
                }
                
                
                
                
            }
               
            
            
             if(bpvWorkSheetInstance.m5){
                 
                def sampleFr =  bpvWorkSheetInstance.module5Sheet.sampleFr
                def sampleQc = bpvWorkSheetInstance.module5Sheet.sampleQc
                def sampleU = bpvWorkSheetInstance.module5Sheet.sampleU
                def sampleV = bpvWorkSheetInstance.module5Sheet.sampleV
                def sampleW = bpvWorkSheetInstance.module5Sheet.sampleW
                def sampleX = bpvWorkSheetInstance.module5Sheet.sampleX
              
                   
                def protocolMap = [:]
                   
                //protocolMap.put(sampleFr,  Protocol.findByCode('FROZEN'))
                protocolMap.put(sampleQc,  Protocol.findByCode('BPV_QCFFPE'))
                protocolMap.put(sampleU,  Protocol.findByCode('BPV_PROTOCOL_U'))
                protocolMap.put(sampleV,  Protocol.findByCode('BPV_PROTOCOL_V'))
                protocolMap.put(sampleW,  Protocol.findByCode('BPV_PROTOCOL_W'))
                protocolMap.put(sampleX,  Protocol.findByCode('BPV_PROTOCOL_X'))
               
                
                
              
                   
                def list = [ sampleQc]
                list.each{
                    def s = it.sample
                    //println("specimenid: " + it.sampleId4Record)
                   
                    s =SpecimenRecord.findBySpecimenId(it.sampleId4Record) 
                    
                    if(!s){
                        s = new SpecimenRecord()
                    }
                     
                  
                    s.specimenId=it.sampleId4Record
                    
                    s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                    s.caseRecord=bpvWorkSheetInstance.caseRecord
                   
                    def protocol = protocolMap.get(it)
                   
                    s.protocol=protocolMap.get(it)
                    s.parentSpecimen=parent
                    
                   
                    s.fixative=Fixative.findByCode('FFPE')
                    s.containerType=ContainerType.findByCode('CASSETTE')
                    
                    
                    s.save(failOnError:true)
                        
                    if(protocol.code == 'BPV_QCFFPE' && caseRecord?.bpvSlidePrep?.dateSubmitted){
                        // def slides = SlideRecord.findAllByCaseId(caseRecord.caseId)
                        def slides = SlideRecord.findAllByCaseId(caseRecord.id.toString())
                        slides.each{it2->
                            if(it2.module.code == 'MODULE5' && !it2.specimenRecord){
                                it2.specimenRecord=s
                                it2.save(failOnError:true)
                            }
                        }
                    }
                    
                 
                        def chpTissueRecord = s.chpTissueRecord
                        if(!chpTissueRecord){
                            chpTissueRecord= new ChpTissueRecord()
                        }

                        chpTissueRecord.timeInCass=it.dateSampleRecorded
                        chpTissueRecord.timeInFix=it.dateSampleInFixative
                        chpTissueRecord.timeInProcessor=it.dateSampleInProc
                        chpTissueRecord.procTimeEnd=it.dateSampleProcEnd
                        chpTissueRecord.procTimeRemov=it.dateSampleRemoved
                        chpTissueRecord.timeEmbedded=it.dateSampleEmbStarted
                        chpTissueRecord.specimenRecord=s
                        chpTissueRecord.save(failOnError:true)

                        s.chpTissueRecord=chpTissueRecord
                        s.save(failOnError:true)
                        it.sample=s
                        it.save(failOnError:true)

                     
                       
                }
                
                
                def list2 = [ sampleFr, sampleU, sampleV, sampleW, sampleX]
                list2.each(){
                    def protocol = protocolMap.get(it)
                   def s = it.sample
                   if(!s){
                        s =SpecimenRecord.findBySpecimenId(it.sampleId4Frozen) 
                   }
                   if(!s){
                     s = new SpecimenRecord() 
                   }
                   s.specimenId=it.sampleId4Frozen
                   s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                   s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                   s.caseRecord=bpvWorkSheetInstance.caseRecord
                   if(!protocol){
                     s.fixative=Fixative.findByCode('FROZEN')
                   }else{
                       s.fixative=Fixative.findByCode('LN2VAP')
                   }
                   s.containerType=ContainerType.findByCode('CRYOSETTE')
                   s.parentSpecimen=parent
                   s.protocol = protocol
                   s.save(failOnError:true)
                   it.sample=s
                   it.save(failOnError:true)
                    
                    
                }
                
                
                
                
            }
   
            if(bpvWorkSheetInstance.nat){
                def samples =[]
                def module3Sheet=bpvWorkSheetInstance.module3Sheet
                   
                     
                   
                samples[0]=bpvWorkSheetInstance.module3Sheet.ntFfpeId1
                samples[1]=bpvWorkSheetInstance.module3Sheet.ntFfpeSample1
                samples[2]=bpvWorkSheetInstance.module3Sheet.ntFfpeId2
                samples[3]=bpvWorkSheetInstance.module3Sheet.ntFfpeSample2
                samples[4]=bpvWorkSheetInstance.module3Sheet.ntFfpeId3
                samples[5]=bpvWorkSheetInstance.module3Sheet.ntFfpeSample3
                samples[6]=bpvWorkSheetInstance.module3Sheet.ntFrozenId1
                samples[7]=bpvWorkSheetInstance.module3Sheet.ntFrozenSample1
                samples[8]=bpvWorkSheetInstance.module3Sheet.ntFrozenId2
                samples[9]=bpvWorkSheetInstance.module3Sheet.ntFrozenSample2
                samples[10]=bpvWorkSheetInstance.module3Sheet.ntFrozenId3
                samples[11]=bpvWorkSheetInstance.module3Sheet.ntFrozenSample3
                   
                for(int i = 0; i < 11; i=i+2){
                    def id = samples[i]
                    if(id){
                        def s = samples[i+1]
                        if(!s){
                            s = SpecimenRecord.findBySpecimenId(id)
                        }
                        if(!s){
                            s = new SpecimenRecord()
                        }
                        s.specimenId=id
                        s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                        s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                        s.caseRecord=bpvWorkSheetInstance.caseRecord
                        s.parentSpecimen=parent
                        if(i==0 || i==2 || i==4 ){
                            s.fixative=Fixative.findByCode('FFPE')  
                            s.containerType=ContainerType.findByCode('CASSETTE')
                        }

                        if(i==6 || i==8 || i==10){
                            s.fixative=Fixative.findByCode('FROZEN')
                            s.containerType=ContainerType.findByCode('CRYOSETTE')
                        }

                        s.save(failOnError:true)
                           
                          
                        if(i==0){
                            module3Sheet.ntFfpeSample1 = s
                        }else if(i==2){
                            module3Sheet.ntFfpeSample2 = s
                        }else if(i==4){
                            module3Sheet.ntFfpeSample3 = s
                        }else if(i==6){
                            module3Sheet.ntFrozenSample1=s
                        }else if(i==8){
                            module3Sheet.ntFrozenSample2=s
                        }else if (i==10){
                            module3Sheet.ntFrozenSample3=s
                        }else{
                               
                        }
                        module3Sheet.save(failOnError:true)
                    }
                    
                }
            }
          
            
            if(bpvWorkSheetInstance.ett){
                def samples =[]
                def module4Sheet=bpvWorkSheetInstance.module4Sheet
                samples[0]=bpvWorkSheetInstance.module4Sheet.ttFfpeId1
                samples[1]=bpvWorkSheetInstance.module4Sheet.ttFfpeSample1
                samples[2]=bpvWorkSheetInstance.module4Sheet.ttFfpeId2
                samples[3]=bpvWorkSheetInstance.module4Sheet.ttFfpeSample2
                samples[4]=bpvWorkSheetInstance.module4Sheet.ttFfpeId3
                samples[5]=bpvWorkSheetInstance.module4Sheet.ttFfpeSample3
                samples[6]=bpvWorkSheetInstance.module4Sheet.ttFrozenId1
                samples[7]=bpvWorkSheetInstance.module4Sheet.ttFrozenSample1
                samples[8]=bpvWorkSheetInstance.module4Sheet.ttFrozenId2
                samples[9]=bpvWorkSheetInstance.module4Sheet.ttFrozenSample2
                samples[10]=bpvWorkSheetInstance.module4Sheet.ttFrozenId3
                samples[11]=bpvWorkSheetInstance.module4Sheet.ttFrozenSample3
                   
                   
                for(int i = 0; i < 11; i=i+2){
                    def id = samples[i]
                    if(id){
                        def s = samples[i+1]
                        if(!s){
                            s = SpecimenRecord.findBySpecimenId(id)
                        }
                        if(!s){
                            s = new SpecimenRecord()
                        }
                        s.specimenId=id
                        s.tissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                        s.provisionalTissueType=bpvWorkSheetInstance.caseRecord.primaryTissueType
                        s.caseRecord=bpvWorkSheetInstance.caseRecord
                        s.parentSpecimen=parent
                        if(i==0 || i==2 || i==4){
                            s.fixative=Fixative.findByCode('FFPE')  
                            s.containerType=ContainerType.findByCode('CASSETTE')
                        }

                        if(i==6 || i==8 || i==10){
                            s.fixative=Fixative.findByCode('FROZEN')
                            s.containerType=ContainerType.findByCode('CRYOSETTE')
                        }

                        s.save(failOnError:true)
                           
                        if(i==0){
                            module4Sheet.ttFfpeSample1 = s
                        }else if(i==2){
                            module4Sheet.ttFfpeSample2 = s
                        }else if(i==4){
                            module4Sheet.ttFfpeSample3 = s
                        }else if(i==6){
                            module4Sheet.ttFrozenSample1=s
                        }else if(i==8){
                            module4Sheet.ttFrozenSample2=s
                        }else if (i==10){
                            module4Sheet.ttFrozenSample3=s
                        }else{
                               
                        }
                        module4Sheet.save(failOnError:true)
                    }
                    
                }
            }
          
            
              
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }
    }
    
    
    def getOtherCaseId(specimenId, caseRecord){
        def caseId=''
        def s = SpecimenRecord.findBySpecimenId(specimenId)
        if(s){
            def c = s.caseRecord
            if(c.id != caseRecord.id){
                caseId = c.caseId
            }
        }
        
        return caseId
    }
    
    def isBloodSample(specimenId, caseRecord){
       
        def s = SpecimenRecord.findBySpecimenId(specimenId)
        if(s){
            def c = s.caseRecord
            if(c.id == caseRecord.id){
                if(s.tissueType?.code == 'BLOODPLAS' || s.tissueType?.code== 'BLOODSRM' || s.tissueType?.code=='BLOODRNA' || s.tissueType?.code=='BLOODDNA') {
                    return true
                }
            }
        }
        
        return false
    }
    
    
    
    def  getPriority(caseRecord){
        def altMap=[:]
        if ( caseRecord.study.code == 'BPV'){
            def bpvWorkSheet = caseRecord.bpvWorkSheet
            if(bpvWorkSheet?.dateSubmitted){
                if(bpvWorkSheet.m1){
                    def p = ''
                    if(bpvWorkSheet.module1Sheet?.priority==1){
                        p='PRIORITY1'
                    }
                    if(bpvWorkSheet.module1Sheet?.priority==2){
                        p='PRIORITY2'
                    }
                    
                    altMap.put(bpvWorkSheet.module1Sheet?.sampleFr?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module1Sheet?.sampleQc?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module1Sheet?.sampleA?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module1Sheet?.sampleB?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module1Sheet?.sampleC?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module1Sheet?.sampleD?.sampleId4Record, p)
                }
                
                if(bpvWorkSheet.m2){
                    def p = ''
                    if(bpvWorkSheet.module2Sheet?.priority==1){
                        p='PRIORITY1'
                    }
                    if(bpvWorkSheet.module2Sheet?.priority==2){
                        p='PRIORITY2'
                    }
                    
                    altMap.put(bpvWorkSheet.module2Sheet?.sampleFr?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module2Sheet?.sampleQc?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module2Sheet?.sampleE?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module2Sheet?.sampleF?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module2Sheet?.sampleG?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module2Sheet?.sampleH?.sampleId4Record, p)
                }
                
                
                 if(bpvWorkSheet.m3){
                    def p = ''
                    if(bpvWorkSheet.module3NSheet?.priority==1){
                        p='PRIORITY1'
                    }
                    if(bpvWorkSheet.module3NSheet?.priority==2){
                        p='PRIORITY2'
                    }
                    
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleFr?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleQc?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleI?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleJ?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleK?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleL?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleM?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module3NSheet?.sampleN?.sampleId4Frozen, p)
                }
                
                if(bpvWorkSheet.m4){
                    def p = ''
                    if(bpvWorkSheet.module4NSheet?.priority==1){
                        p='PRIORITY1'
                    }
                    if(bpvWorkSheet.module4NSheet?.priority==2){
                        p='PRIORITY2'
                    }
                    
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleFr?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleQc?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleO?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleP?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleQ?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleR?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleS?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module4NSheet?.sampleT?.sampleId4Frozen, p)
                }
                
                
                
                 if(bpvWorkSheet.m5){
                    def p = ''
                    if(bpvWorkSheet.module5Sheet?.priority==1){
                        p='PRIORITY1'
                    }
                    if(bpvWorkSheet.module5Sheet?.priority==2){
                        p='PRIORITY2'
                    }
                    
                    altMap.put(bpvWorkSheet.module5Sheet?.sampleFr?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module5Sheet?.sampleQc?.sampleId4Record, p)
                    altMap.put(bpvWorkSheet.module5Sheet?.sampleU?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module5Sheet?.sampleV?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module5Sheet?.sampleW?.sampleId4Frozen, p)
                    altMap.put(bpvWorkSheet.module5Sheet?.sampleX?.sampleId4Frozen, p)
                   
                }
                
                if(bpvWorkSheet.nat){
                 def module3Sheet = bpvWorkSheet.module3Sheet
                    def m3List = [module3Sheet.ntFfpeSample1, module3Sheet.ntFfpeSample2, module3Sheet.ntFfpeSample3, module3Sheet.ntFrozenSample1, module3Sheet.ntFrozenSample2, module3Sheet.ntFrozenSample3]
                    m3List.each{
                        if(it?.specimenId){
                            altMap.put(it.specimenId, 'PRIORITY3a') 
                        }
                    }
                }
            
                if(bpvWorkSheet.ett){
                    def module4Sheet = bpvWorkSheet.module4Sheet
                    def m4List = [module4Sheet.ttFfpeSample1, module4Sheet.ttFfpeSample2, module4Sheet.ttFfpeSample3, module4Sheet.ttFrozenSample1, module4Sheet.ttFrozenSample2, module4Sheet.ttFrozenSample3]
                    m4List.each{
                        if(it?.specimenId){
                            altMap.put(it.specimenId, 'PRIORITY3b') 
                        }
                    }
                }
               
            }
           
            
           
        }
        return altMap
    }
    
    
    int compareVersion(String v1, String v2) throws Exception{
        if(!v1 || ! v2)
        throw new Exception("Wrong CDR version format")
        int result=100
        def a1 = v1.split("\\.")
        def a2 = v2.split("\\.")
       
        
        def len1 = a1.size()
        def len2 = a2.size()
        
        def len
        if(len1 <= len2)
        len = len1
        else
        len = len2
       
        
        try{
            for(int i = 0; i < len; i++){
                int i1 = Integer.parseInt(a1[i])
                int i2 = Integer.parseInt(a2[i])
                if(i1 > i2){
                    result = 1
                }else if (i1 == i2){
                    result = 0
                }else{
                    result = -1
                }  
                if(result == 1 || result == -1)
                break
            }
        }catch(Exception e){
            throw new Exception("Wrong CDR version format")
        } 
       
        if(result == 0){
            if(len1 < len2)
            result = -1
            else if(len1 > len2)
            result = 1
            else
            result = 0
        }
       
        return result
        
    }
    
     String getSideFromModule(moduleSheet){
        def caseRecord = moduleSheet.bpvWorkSheet.caseRecord
        def organ = caseRecord.primaryTissueType.code
        if(organ != 'OVARY')
        return 'L'
                     
     
        return moduleSheet.whichOvary
        
     }
    
  
    
    //pmh: new 06/17/13 : updating the tumorstatus field so it can be used in the CDR-AR reports 
    def updateSpecimenTumorStatus(bpvcaseRec){
       
        if(bpvcaseRec) {               
            def  altMap = getPriority(bpvcaseRec) 
            //println bpvcaseRec.id
            try{
                bpvcaseRec.specimens?.each{
               
                    if (altMap.get(it.specimenId).toString()=='PRIORITY1'){                     
                        
                        it.tumorStatus=TumorStatus.findByCode('TUMOR')
                        it.save(failOnError:true)
                       
                    } 
                    else if (altMap.get(it.specimenId).toString()=='PRIORITY2' ){                    
                       
                        it.tumorStatus=TumorStatus.findByCode('TUMOR')
                        it.save(failOnError:true)
                       
                    } 
                    else if (altMap.get(it.specimenId).toString().toUpperCase()=='PRIORITY3A'){                    
                          
                        it.tumorStatus=TumorStatus.findByCode('NORMAL ADJACENT')
                        it.save(failOnError:true)
                       
                    } 
                    else if (altMap.get(it.specimenId).toString().toUpperCase()=='PRIORITY3B'){                    
                                                             
                        it.tumorStatus=TumorStatus.findByCode('TUMOR')
                        it.save(failOnError:true)
                        
                    }
                    else{
                    
                        // do nothing
                    }
               
                }
            
            }catch(Exception e){
                e.printStackTrace()
                throw new RuntimeException(e.toString())
            }
        }
      
    }
}

