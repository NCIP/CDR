package nci.obbr.cahub

import nci.obbr.cahub.forms.bpv.BpvBloodForm
import nci.obbr.cahub.forms.bpv.BpvTissueForm
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import grails.converters.deep.*
import org.codehaus.groovy.grails.commons.ApplicationHolder 
import java.text.SimpleDateFormat
//import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord

class ChpEventService {
    
    static transactional = true
    
    def sendMailService

    def chpBloodRecordDelete(payload) {
        def errors 
        
        def chpDetailsList = payload.chpBloodObjects.children()
        println "size: " + payload.chpBloodObjects.children().size()
        println "Calling Delete was called"
        chpDetailsList.each() {
            def chpBloodRecord
            
            def specimenRecordInstance = SpecimenRecord.findBySpecimenId(it.specimenId.text())
            
            if ( !specimenRecordInstance ) {
                errors = chpBloodRecord.errors
                log.error("chpBloodRecord FindParent failed on Case ID: " + it.'@id'.text()+" Specimen ID: " + it.specimenId.text())
                log.error(errors)
            
                sendMailService.sendServiceEventEmail('FAIL:', errors.toString() + '\n'+ "Case ID: " + it.'@id'.text()+'\n'+"Specimen ID: " + it.specimenId.text())           
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>" 
                    )
            } else {
                specimenRecordInstance.chpBloodRecord.delete(failonerror:true,flush:true)
            }
        }
        return errors
    }
    
    def chpBloodRecordInsert(payload) {
        def errors 
        
        def chpDetailsList = payload.chpBloodObjects.children()
        println "size: " + payload.chpBloodObjects.children().size()
        println "Calling Insert was called"
        chpDetailsList.each() {
            def chpBloodRecord
            
            def specimenRecordInstance = SpecimenRecord.findBySpecimenId(it.specimenId.text())
            println "it.chpBlood:     " + it.'@id'.text()
            println "it.specimenId:   " + it.specimenId
            
            if ( !specimenRecordInstance ) {
                // errors = chpBloodRecord.errors
                log.error("chpBloodRecord FindParent failed on Case ID: " + it.'@id'.text()+" Specimen ID: " + it.specimenId.text())
                // log.error(errors)
            
                sendMailService.sendServiceEventEmail('FAIL:', errors.toString() + '\n'+ "Case ID: " + it.'@id'.text()+'\n'+"Specimen ID: " + it.specimenId.text())           
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>" 
                    )
            }
            
            
            Date testDate = ocFormatssnn.parse(it.BloodDraw.toString())
            //println "testDate: " + testDate
            //println "parseVolume1: " + parseVolume(it.volume.toString(), "vol")
            //println "parseVolume2: " + parseVolume(it.volume.toString(), "unit")
            if ( !specimenRecordInstance.containerType ) {
               specimenRecordInstance.containerType =  ContainerType.findByCode(it.containerType.text().toUpperCase())
               //println "specimenRecordInstance.protocol 2: " + specimenRecordInstance.protocol.toString()
            }
            //println "containerType: " + it.containerType.text()
            chpBloodRecord = new ChpBloodRecord(
                specimenRecord: specimenRecordInstance,
                // containerType: ContainerType.findByCode(it.containerType.text()),
                // acquisitionType: AcquisitionType.findByCode(terminologyMap(it.sampleType.text(),"ocBlood")),
                volume: it.volume.toFloat(),
                volUnits: it.volUnits.toString(),
                bloodProcStart: ocFormatssnn.parse(it.bloodProcStart.text()),
                bloodProcEnd:   ocFormatssnn.parse(it.bloodProcEnd.text()),
                bloodFrozen:    ocFormatssnn.parse(it.bloodFrozen.text()),
                bloodStorage:   ocFormatssnn.parse(it.bloodStorage.text()),
                hemolysis: it.hemolysis.toString(),
                bloodProcComment: it.bloodProcComments.toString(),
                bloodStorageComment: it.bloodStorageIssues.toString()
            )
            
            createOrReplaceBpvBloodForm(it)
            
            if (chpBloodRecord.validate()) {
                chpBloodRecord.save(failOnError:true)
            } else {
                
                errors = chpBloodRecord.errors
                log.error("chpBloodRecord INSERT failed on "+" Case ID: " + it.'@id'.text()+'\n'+"Specimen ID: " + it.specimenId.text())           
                log.error(errors)
            
                sendMailService.sendServiceEventEmail('INSERT FAIL:', errors.toString() + '\n'+ " Case ID: " + it.id.text()+'\n'+"Specimen ID: " + it.specimenId.text())                     
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>"
                )
            }
        }
        return errors
    }

    def chpBloodRecordUpdate(payload) {
        def errors 
        
        def chpDetailsList = payload.chpBloodObjects.children()
        println "size: " + payload.chpBloodObjects.children().size()
        println "Calling Update was called"
        chpDetailsList.each() {
            def chpBloodRecord
            
            def specimenRecordInstance = SpecimenRecord.findBySpecimenId(it.specimenId.text())
            println "it.chpBlood:     " + it.'@id'.text()
            println "it.specimenId:   " + it.specimenId
            
            if ( !specimenRecordInstance ) {
                errors = chpBloodRecord.errors
                log.error("chpBloodRecord FindParent failed on Case ID: " + it.'@id'.text()+" Specimen ID: " + it.specimenId.text())
                log.error(errors)
            
                sendMailService.sendServiceEventEmail('FAIL:', errors.toString() + '\n'+ "Case ID: " + it.'@id'.text()+'\n'+"Specimen ID: " + it.specimenId.text())           
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>" 
                    )
            }
            
            Date testDate = ocFormatssnn.parse(it.BloodDraw.toString())
            //println "testDate: " + testDate
            //println "parseVolume1: " + parseVolume(it.volume.toString(), "vol")
            //println "parseVolume2: " + parseVolume(it.volume.toString(), "unit")
            if ( !specimenRecordInstance.containerType ) {
               println "it.containerType:                       " + it.containerType.text()
               specimenRecordInstance.containerType =  ContainerType.findByCode(it.containerType.text().toUpperCase())
               println "specimenRecordInstance.containerType:   " + specimenRecordInstance.containerType
            }
            //println "containerType: " + it.containerType.text()
            chpBloodRecord = specimenRecordInstance.chpBloodRecord
            if ( chpBloodRecord ) {
                chpBloodRecord.volume = it.volume.toFloat()
                chpBloodRecord.volUnits = it.volUnits.toString()
                chpBloodRecord.bloodProcStart = ocFormatssnn.parse(it.bloodProcStart.text())
                chpBloodRecord.bloodProcEnd =   ocFormatssnn.parse(it.bloodProcEnd.text())
                chpBloodRecord.bloodFrozen  =   ocFormatssnn.parse(it.bloodFrozen.text())
                chpBloodRecord.bloodStorage =   ocFormatssnn.parse(it.bloodStorage.text())
                chpBloodRecord.hemolysis    =   it.hemolysis.toString()
                chpBloodRecord.bloodProcComment = it.bloodProcComments.toString()
                chpBloodRecord.bloodStorageComment = it.bloodStorageIssues.toString()
            } else {
               errors = chpBloodRecord.errors
               log.error("specimenRecord Find chpBloodRecord failed on UPDATE for Case ID: " + it.'@id'.text()+" Specimen ID: " + it.specimenId.text())
               log.error(errors)
            
               sendMailService.sendServiceEventEmail('FAIL:', errors.toString() + '\n'+ "Case ID: " + it.'@id'.text()+'\n'+"Specimen ID: " + it.specimenId.text())           
            
               //throw runtimeexeption is required to trigger rollback
               throw new RuntimeException(
                 "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                 + errors.toString()
                 +"</message></collectionEvent>" 
                ) 
            }
                        
            createOrReplaceBpvBloodForm(it)
            
            if (chpBloodRecord.validate()) {
                chpBloodRecord.save(failOnError:true)
            } else {
                
                errors = chpBloodRecord.errors
                log.error("chpBloodRecord UPDATE failed on "+" Case ID: " + it.'@id'.text()+'\n'+"Specimen ID: " + it.specimenId.text())           
                log.error(errors)
            
                sendMailService.sendServiceEventEmail('UPDATE FAIL:', errors.toString() + '\n'+ " Case ID: " + it.id.text()+'\n'+"Specimen ID: " + it.specimenId.text())                     
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>"
                )
            }
        }
        return errors
    }
    
    
    def checkNullFloat (nodeChild) {
        println "checkNullFloat: nodeChild is empty: " + nodeChild.isEmpty()
        println "checkNullFloat: nodeChild size :    " + nodeChild.size()
        if (nodeChild.toString() == "") {
            return 0.0
        } else {
            println "checkNullFloat: nodeChild: ==>" + nodeChild + "<=="
            return nodeChild.toFloat()
            /* There simply HAS to be a better way.... */
        }
    }
    def parseVolume (volAndUnit, type) {
        String buf1, buf2 
        // println "volAndUnit: " + volAndUnit
        // println "index: " + volAndUnit.indexOf('ml')
        if (volAndUnit.indexOf('ml') == -1) {
            return volAndUnit
        } else {
            buf1 = volAndUnit.substring(0, volAndUnit.indexOf('ml'))
            buf2 = volAndUnit.substring(volAndUnit.indexOf('ml'))
                //println "buf1: " + buf1
                //println "buf2: " + buf2

            if (type == "vol") {
                return buf1
            } else if (type == "unit") {
                return buf2
            } else {
                return "usage error: volAndUnit(field, type) where type is one of \"vol\" or \"unit\" "
            }
        
        }
    }
    
    def terminologyMap (term, mapType) {
        if (mapType == "ocBlood") {
            switch (term) {
                case "Serum":
                return "BLOODSRM"
                break
                case "Plasma":
                return "BLOODPLAS"
                break
                case "Whole Blood":
                return "BLOODW"
                break
            }
        } else if (mapType == "ocBloodSource") {
            switch (term) {
                case "Yes":
                return "Source of the Venous Blood Draw Was a Fresh Needle Stick"
                break
            }
        } else {
            return "usage error: terminologyMap(term, type) where type is one of \"ocBlood\" or \"ocBloodSource\" "
        }
    }
    
    def createOrReplaceBpvBloodForm(chpBloodRecord) {
        def bpvBloodForm = CaseRecord.findByCaseId(chpBloodRecord.'@id'.text()).bpvBloodForm
            
        //println "chpBloodRecord.dateTimeBloodDraw.text():                " + chpBloodRecord.BloodDraw.text()
        //println "chpBloodRecord.BloodReceipt.text()):                    " + chpBloodRecord.BloodReceipt.text()
        //println "chpBloodRecord.bloodDrawComments.text():                " + chpBloodRecord.bloodDrawComments.text()
        //println "terminologyMap(it.bloodSource.text(),ocBloodSource): " + terminologyMap(chpBloodRecord.bloodSource.text(),"ocBloodSource")

        if ( bpvBloodForm ) {
            bpvBloodForm.dateTimeBloodDraw = ocFormatssnn.parse(chpBloodRecord.BloodDraw.text())
            bpvBloodForm.dateTimeBloodReceived = ocFormatssnn.parse(chpBloodRecord.BloodReceipt.text())
            bpvBloodForm.bloodDrawComments = chpBloodRecord.bloodDrawComments.text()
            bpvBloodForm.bloodSource = terminologyMap(chpBloodRecord.bloodSource.text(),"ocBloodSource")
        } else {
            bpvBloodForm = new BpvBloodForm(
                caseRecord: CaseRecord.findByCaseId(chpBloodRecord.'@id'.text()),
                dateTimeBloodDraw: ocFormatssnn.parse(chpBloodRecord.BloodDraw.text()),
                dateTimeBloodReceived: ocFormatssnn.parse(chpBloodRecord.BloodReceipt.text()),
                bloodDrawComments: chpBloodRecord.bloodDrawComments.text(),
                bloodSource: terminologyMap(chpBloodRecord.bloodSource.text(),"ocBloodSource")
            )
        }

        if (bpvBloodForm.validate()) {
            bpvBloodForm.save(failOnError:true)
        }
    }
    
    def chpTissueRecordInsert(payload) {
        //not sure why this is here anymore, but the call below took a while to figure out
        //so leaving for historians...
        //instantiate new specimen records from a shipping event
        //def specimenIdList = payload.specimens.children().collect{it.'@id'} 
     
        def errors 
        
        def chpDetailsList = payload.chptissueobjects.children()
        
        chpDetailsList.each() {

            def chpTissueRecord 
            def specimenRecordInstance = SpecimenRecord.findBySpecimenId(it.g1_cass_id_1.text())
            
            if ( !specimenRecordInstance ) {
                errors = chpTissueRecord.errors
                log.error("chpTissueRecord FindParent failed on Case ID: " + it.tiss_caseid.text()+" Specimen ID: " + it.g1_cass_id_1.text())
                log.error(errors)
            
                sendMailService.sendServiceEventEmail('FAIL:', errors.toString() + '\n'+ "Case ID: " + it.tiss_caseid.text()+'\n'+"Specimen ID: " + it.g1_cass_id_1.text())           
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>" 
                    )
            } 
            
            String timeInFixbuffer = it.g1_time_in_fix.text()
            int strpos = (timeInFixbuffer.indexOf(':'))
            if (!strpos) {
                println ("Scream Bloody Murder!" + it.tiss_caseid.text() + " g1_time_in_fix    " + it.g1_time_in_fix.text())
            }
            int foo=0, bar=strpos.intValue()
            String proto = timeInFixbuffer.substring(foo ,bar)
            String protoTif = timeInFixbuffer.substring(bar+1)
            // println "proto     =>" + proto + "<="
            // println "protoTif  =>" + protoTif + "<="
            //println "${it.g1_cass_id_1.text()}: surgDate =>" + it.surgDate.text() + "<="
            //println "${it.g1_cass_id_1.text()}: proto  =>" + proto.toUpperCase() + "<="
            //println "specimenRecordInstance.protocol 1: " + specimenRecordInstance.protocol.toString()
            
            
            if ( !specimenRecordInstance.protocol ) {
               specimenRecordInstance.protocol =  Protocol.findByCode(proto.toUpperCase())
               //println "specimenRecordInstance.protocol 2: " + specimenRecordInstance.protocol.toString()
            }
            //println "specimenRecordInstance.protocol 3: " + specimenRecordInstance.protocol.toString()
            if ( specimenRecordInstance.tumorStatus.toString() == "" ) {
                specimenRecordInstance.tumorStatus = TumorStatus.findByName(it.tiss_gross_diag.text())
            }

            chpTissueRecord = new ChpTissueRecord(
                specimenRecord: specimenRecordInstance,
                // surgDate: dFormat.parse(it.surgDate.text()),
                // firstIncis: makeDateTime(it.surgDate, it.surg_first_incis.text() ),
                // clamp1Time: makeDateTime(it.surgDate, it.surg_first_clamp.text() ),
                // clamp2Time: makeDateTime(it.surgDate, it.surg_secnd_clamp.text() ),
                // resectTime: makeDateTime(it.surgDate, it.surg_org_resect.text() ),
                // surgComment: it.surg_comments.text(),
                // grossTimeIn: makeDateTime(it.gross_datein, it.gross_timein.text() ),
                // grossDiagx: it.gross_diagx.text(),
                // grossTimeOut: makeDateTime(it.gross_datein, it.gross_timeout.text() ),
                // tissSop: it.tiss_sop.text(),
                // tissDissecTime: makeDateTime(it.tiss_spec_date, it.tiss_spec_time.text() ),
                // tissComment: it.tiss_comments.text(),
                // tissGrossId: it.tiss_gross_id.text(),
                // dissecStartTime: makeDateTime(it.tiss_spec_date, it.tiss_disect_startime.text() ),
                // dissecEndTime: makeDateTime(it.tiss_spec_date, it.tiss_disect_endtime.text() ),
                // protoDelayToFix: it.g1_delay_to_fix.text(),
                // protoTimeInFix: protoTif,
                /* Sometimes the date / time barcode timestamps come in with 
                 * yyyy-MM-DD'T'HH:mm and sometimes they come in with yyyy-MM-DD'T'HH:mm:ss
                 * The format conditional tests for string length and returns either one or the other.
                 * */
                timeInCass: formatConditional( it.g1_cass_tm_1.text() ),
                timeInFix: formatConditional( it.g1_fix_tm_2.text() ),
                timeInProcessor: formatConditional( it.g2_proc_tm_3.text() ),
                procTimeEnd: formatConditional( it.g2_cyc_end_4.text() ),
                procTimeRemov: formatConditional( it.g2_remov_tm_5.text() ),
                timeEmbedded: formatConditional( it.g2_embed_tm_6.text() ),
                // acquisType: AcquisitionType.findByName(it.tiss_spec_source.text()),
                // tumorStatus: TumorStatus.findByName(it.tiss_gross_diag.text()),
                // fixative: Fixative.findByName(it.tiss_fixative.text()),
                // protocol: Protocol.findByCode(proto.toUpperCase())  
            )
            
            createOrReplaceBpvTissueForm(it)
            
            // chpTissueRecord.surgDate  = it.surgDate  it.surgDate is of type groovy.util.slurpersupport.NodeChild and I see no way other than toString to map it to a Date object.
           
            // println "it.tiss_spec_source.text():  " + it.tiss_spec_source.text()
            // println "${chpTissueRecord.specimenRecord}: surgDate =>" + chpTissueRecord.specimenRecord.caseRecord.bpvTissueForm.surgDate.toString() + "<="
            // println "${chpTissueRecord.specimenRecord}: protocol =>" + chpTissueRecord.specimenRecord.protocol + "<="
            
            if (chpTissueRecord.validate()) {
                chpTissueRecord.save(failOnError:true)
            } else {
                
                errors = chpTissueRecord.errors
                log.error("chpTissueRecord INSERT failed on "+" Case ID: " + it.tiss_caseid.text()+" Specimen ID: " + it.g1_cass_id_1.text())
                log.error(errors)
            
                sendMailService.sendServiceEventEmail('INSERT FAIL:', errors.toString() + '\n'+ "Case ID: " + it.tiss_caseid.text()+'\n'+"Specimen ID: " + it.g1_cass_id_1.text())           
            
                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    + errors.toString()
                    +"</message></collectionEvent>"
                )
            }
        }
       
        return errors
    }
    
    def createOrReplaceBpvTissueForm(it) {
        //def bpvTissueForm = CaseRecord.findByCaseId(it.'@id'.text()).bpvTissueForm
        def bpvTissueForm = SpecimenRecord.findBySpecimenId(it.tiss_gross_id.text()).bpvTissueForm
            
        //println "it.surgDate.text():            " + it.surgDate.text()
        //println "it.surg_first_incis.text()):   " + it.surg_first_incis.text()
        //println "it.tiss_caseid.text():         " + it.tiss_caseid.text()
        //println "it.tiss_gross_id.text():       " + it.tiss_gross_id.text()
        //println "it.'@id'.text():               " + it.'@id'.text()
        //println "bpvTissueForm  :               " + bpvTissueForm

        if ( bpvTissueForm ) {
            println "bpvTissueForm exists! updating.... "
            println "it.tiss_gross_id.text():       " + it.tiss_gross_id.text()
            bpvTissueForm.surgDate = dFormat.parse(it.surgDate.text())
            bpvTissueForm.firstIncis = makeDateTime(it.surgDate, it.surg_first_incis.text() )
            bpvTissueForm.clamp1Time = makeDateTime(it.surgDate, it.surg_first_clamp.text() )
            bpvTissueForm.clamp2Time = makeDateTime(it.surgDate, it.surg_secnd_clamp.text() )
            bpvTissueForm.resectTime = makeDateTime(it.surgDate, it.surg_org_resect.text() )
            bpvTissueForm.surgComment = it.surg_comments.text()
            bpvTissueForm.grossTimeIn = makeDateTime(it.gross_datein, it.gross_timein.text() )
            bpvTissueForm.grossDiagx  = it.gross_diagx.text()
            bpvTissueForm.grossTimeOut   = makeDateTime(it.gross_datein, it.gross_timeout.text() )
            bpvTissueForm.tissSop        = it.tiss_sop.text()
            bpvTissueForm.tissDissecTime = makeDateTime(it.tiss_spec_date, it.tiss_spec_time.text() )
            bpvTissueForm.tissComment    = it.tiss_comments.text()
            bpvTissueForm.tissGrossId    = it.tiss_gross_id.text()
            bpvTissueForm.dissecStartTime = makeDateTime(it.tiss_spec_date, it.tiss_disect_startime.text() )
            bpvTissueForm.dissecEndTime  = makeDateTime(it.tiss_spec_date, it.tiss_disect_endtime.text() )
        } else {
            println "creating new bpvTissueForm!        "
            println "it.tiss_gross_id.text():       " + it.tiss_gross_id.text()
            bpvTissueForm = new BpvTissueForm(
                //caseRecord:    CaseRecord.findByCaseId(it.'@id'.text()),
                specimenRecord: SpecimenRecord.findBySpecimenId(it.tiss_gross_id.text()),
                surgDate:      dFormat.parse(it.surgDate.text()),
                firstIncis:    makeDateTime(it.surgDate, it.surg_first_incis.text() ),
                clamp1Time:    makeDateTime(it.surgDate, it.surg_first_clamp.text() ),
                clamp2Time:    makeDateTime(it.surgDate, it.surg_secnd_clamp.text() ),
                resectTime:    makeDateTime(it.surgDate, it.surg_org_resect.text() ),
                surgComment:    it.surg_comments.text(),
                grossTimeIn:    makeDateTime(it.gross_datein, it.gross_timein.text() ),
                grossDiagx:     it.gross_diagx.text(),
                grossTimeOut:   makeDateTime(it.gross_datein, it.gross_timeout.text() ),
                tissSop:        it.tiss_sop.text(),
                tissDissecTime:   makeDateTime(it.tiss_spec_date, it.tiss_spec_time.text() ),
                tissComment:      it.tiss_comments.text(),
                tissGrossId:      it.tiss_gross_id.text(),
                dissecStartTime:  makeDateTime(it.tiss_spec_date, it.tiss_disect_startime.text() ),
                dissecEndTime:    makeDateTime(it.tiss_spec_date, it.tiss_disect_endtime.text() )
            )
        }
        //println "Calling Validate! "
        if (bpvTissueForm.validate()) {
            //println "Saving BpvTissueForm for case: " + bpvTissueForm.caseRecord.caseId
            bpvTissueForm.save(failOnError:true)
        }
    }
    
    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat ocFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat ocFormatss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat ocFormatssnn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    
    def formatConditional ( dtTmString ){
      if (dtTmString.size() == 19) {
                return ocFormatss.parse(dtTmString)
            } else {
               if (dtTmString.size() == 16)  {
                   return ocFormatss.parse(dtTmString+":00")
               } else {
                    return null
               }
          }
      }

    def makeDateTime (dt, tm) {
        if (!tm ) {
            return null
        }
        Date   dDate = null
        String sDate = dt.text()
        
        sDate = sDate.concat(" ")
        sDate = sDate.concat(tm)
        dDate = dtFormat.parse(sDate.toString() )
        return dDate
    }
   
    def longTimeDiff(startDate, endDate){
        def result
        if (startDate && endDate){
            result = endDate.time - startDate.time // milliseconds
        }else{
            result = null
        }
    return result
    }
    
    def strTimeDiff(millis){
       
        def result
        if (millis){
            int seconds, hours, minutes, days, weeks
            
            println "timeDiff (millis)=" + millis.toString()
            
            minutes = millis/(60*1000)
            
            if (minutes > 60) {
                hours = minutes / 60
                minutes = minutes - (hours * 60)
            }
            
            println "Time Diff(hh:mm) = " + hours + ":" + minutes
            
            /*
            seconds    =  difference % 60
            difference = (difference - seconds) / 60
            minutes    =  difference % 60
            difference = (difference - minutes) / 60
            hours      =  difference % 24
            difference = (difference - hours)   / 24
            days       =  difference % 7
            weeks      = (difference - days)    /  7
            println "($weeks weeks, $days days, $hours:$minutes:$seconds)"
            */
            
            result = hours+ ":" + minutes

        }else{
            result = null
        }
        
    return result
        
    }
    
    def chpTissueRecordDelete(payload) {
        //not sure why this is here anymore, but the call below took a while to figure out
        //so leaving for historians...
        //instantiate new specimen records from a shipping event
        //def specimenIdList = payload.specimens.children().collect{it.'@id'} 
     
        def errors 
        
        def chpDetailsList = payload.chptissueobjects.children()
        def chpTissueRecord 
        def specimenRecord 
        def chpCopy 
        chpDetailsList.each() {
            chpTissueRecord = SpecimenRecord.findBySpecimenId(it.g1_cass_id_1.text()).chpTissueRecord
            specimenRecord = chpTissueRecord.specimenRecord
            
            println "chpTissueRecord:      "+ chpTissueRecord.class.name
            println "specimenRecord: "+ specimenRecord.class.name
            println "chpTissueRecord:      "+ chpTissueRecord
            println "specimenRecord: "+ specimenRecord
            
            chpCopy = chpTissueRecord
            try {
                
                specimenRecord.chpTissueRecord.delete(failOnError:true,flush:true)
                
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                // errors.message
                chpTissueRecord.errors.rejectValue("exception", "DataIntegrityViolationException", [message(code: 'chpTissueRecord.label', default: 'chpTissueRecord')] as Object[], "Delete failure for record: "+it.g1_cass_id_1.text() )
             }
            
        }
    
        return errors
    }
    def chpTissueRecordSelect(payload) {
        //not sure why this is here anymore, but the call below took a while to figure out
        //so leaving for historians...
        //instantiate new specimen records from a shipping event
        //def specimenIdList = payload.specimens.children().collect{it.'@id'} 
     
        def errors 
        
        def chpDetailsList = payload.chptissueobjects.children()
        
        chpDetailsList.each() {
            chpTissueRecord.findBySpecimenId(it.g1_cass_id_1.text())
            println chpTissueRecord.specimenId.text() 
        }
    
        return errors
    }
    
    def chpTissueRecordUpdate(payload) {
        //not sure why this is here anymore, but the call below took a while to figure out
        //so leaving for historians...
        //instantiate new specimen records from a shipping event
        //def specimenIdList = payload.specimens.children().collect{it.'@id'} 
     
        def errors 
        println "payload.chptissueobjects.children(): " + payload.chptissueobjects.children()
        payload.chptissueobjects.children().each() {
            println "it: " + it
            println "it.g1_cass_id_1.text() " + it.g1_cass_id_1.text()
        }
        def chpDetailsList = payload.chptissueobjects.children()
        
        payload.chptissueobjects.children().each() {
            def specimenRecordInstance = SpecimenRecord.findBySpecimenId(it.g1_cass_id_1.text())
            def chpTissueRecord = specimenRecordInstance.chpTissueRecord
            def parentSpecimen = SpecimenRecord.findBySpecimenId(specimenRecordInstance.parentSpecimen.specimenId)
            def bpvTissueForm = parentSpecimen.bpvTissueForm
            
            if (chpTissueRecord) {
                println "Updating: " + specimenRecordInstance.specimenId

                createOrReplaceBpvTissueForm(it)

                String timeInFixbuffer = it.g1_time_in_fix.text()
                int strpos = (timeInFixbuffer.indexOf(':'))
                if (!strpos) {
                    println ("Scream Bloody Murder!" + it.tiss_caseid.text() + " g1_time_in_fix    " + it.g1_time_in_fix.text())
                }
                int foo=0, bar=strpos.intValue()
                String proto = timeInFixbuffer.substring(foo ,bar)
                String protoTif = timeInFixbuffer.substring(bar+1)
                println "proto     =>" + proto + "<="
                println "protoTif  =>" + protoTif + "<="
				
				
                // chpTissueRecord.protoDelayToFix  = it.g1_delay_to_fix.text()
                // chpTissueRecord.protoTimeInFix  = it.g1_time_in_fix.text()
                // chpTissueRecord.protoTimeInFix  = protoTif

                /* Sometimes the date / time barcode timestamps come in with 
                 * yyyy-MM-DD'T'HH:mm and sometimes they come in with yyyy-MM-DD'T'HH:mm:ss
                 * The format conditional tests for string length and returns either one or the other.
                 * */

                chpTissueRecord.timeInCass = formatConditional( it.g1_cass_tm_1.text() )
		chpTissueRecord.timeInFix = formatConditional( it.g1_fix_tm_2.text() )
                
                chpTissueRecord.timeInProcessor = formatConditional( it.g2_proc_tm_3.text() )
		chpTissueRecord.procTimeEnd = formatConditional( it.g2_cyc_end_4.text() )
		chpTissueRecord.procTimeRemov = formatConditional( it.g2_remov_tm_5.text() )
		chpTissueRecord.timeEmbedded = formatConditional( it.g2_embed_tm_6.text() )
            
		// chpTissueRecord.acquisType   = AcquisitionType.findByName(it.tiss_spec_source.text())
		// chpTissueRecord.tumorStatus  = TumorStatus.findByName(it.tiss_gross_diag.text())
		// chpTissueRecord.fixative     = Fixative.findByName(it.tiss_fixative.text())
		specimenRecordInstance.protocol = Protocol.findByName(proto)  // TODO: convert to findByCode with .toUpperCase()
				
                if (chpTissueRecord.validate() && specimenRecordInstance.validate()) {
                    println " Saving! " + it.tiss_caseid.text()
                    println " Saving! " + it.g1_cass_id_1.text()
                    specimenRecordInstance.save(failOnError:true)
                    chpTissueRecord.save(failOnError:true)
                } else {
				
                    errors = chpTissueRecord.errors
                    log.error("chpTissueRecord UPDATE failed on "+" Case ID: " + it.tiss_caseid.text()+" Specimen ID: " + it.g1_cass_id_1.text())
                    log.error(errors)

                    sendMailService.sendServiceEventEmail('UPDATE FAIL:', errors.toString() + '\n'+'\n' + "Case ID " + it.tiss_caseid.text() + ", Specimen ID " + it.g1_cass_id_1.text()+ " Not Found!" )

                    //throw runtimeexeption is required to trigger rollback
                    throw new RuntimeException(
                        "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                        + errors.toString()
                        +"</message></collectionEvent>"
                    )

                }
        
            } else {  // chpInstance NOT found
            
                sendMailService.sendServiceEventEmail('UPDATE FAIL:', errors.toString() + '\n'+'\n' + "Case ID " + it.tiss_caseid.text() + ", Specimen ID " + it.g1_cass_id_1.text()+ " Not Found!" )

                //throw runtimeexeption is required to trigger rollback
                throw new RuntimeException(
                    "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
                    +'UPDATE FAIL:' + '\n'+'\n' + "Case ID " + it.tiss_caseid.text() + ", Specimen ID " + it.g1_cass_id_1.text()+ " Not Found!"+'\n'
                    + errors.toString()+'\n'
                    +"</message></collectionEvent>"
                )
            }
        } 
        return errors
    }
}

