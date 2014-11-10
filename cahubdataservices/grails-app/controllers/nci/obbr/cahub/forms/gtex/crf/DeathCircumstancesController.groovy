package nci.obbr.cahub.forms.gtex.crf

import grails.converters.JSON
import nci.obbr.cahub.staticmembers.DeathCause
import nci.obbr.cahub.staticmembers.CVocabType
import nci.obbr.cahub.datarecords.vocab.*
import nci.obbr.cahub.util.ComputeMethods
import java.net.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import grails.plugins.springsecurity.Secured


class DeathCircumstancesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [deathCircumstancesInstanceList: DeathCircumstances.list(params), deathCircumstancesInstanceTotal: DeathCircumstances.count()]
    }

    def create = {
        def deathCircumstancesInstance = new DeathCircumstances()
        deathCircumstancesInstance.properties = params
        return [deathCircumstancesInstance: deathCircumstancesInstance]
    }

    def save = {
        def deathCircumstancesInstance = new DeathCircumstances(params)
        def caseReportForm = CaseReportForm.findById(params.formid)
        
        if (deathCircumstancesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances for '), caseReportForm?.caseRecord?.caseId])}"
            redirect(action: "show", id: deathCircumstancesInstance.id)
        }
        else {
            render(view: "create", model: [deathCircumstancesInstance: deathCircumstancesInstance])
        }
    }

    def show = {
        def deathCircumstancesInstance = DeathCircumstances.get(params.id)
        if (!deathCircumstancesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deathCircumstancesInstance: deathCircumstancesInstance]
        }
    }

    def edit = {
        def deathCircumstancesInstance = DeathCircumstances.get(params.id)
     
        
        if (!deathCircumstancesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), params.id])}"
            redirect(action: "list")
        }
        else {
            
            def caseReportForm = CaseReportForm.findById(params.formid)
            
            def caseRecord = caseReportForm?.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            def cdrVer = caseReportForm.caseRecord.cdrVer
            def  version45 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                version45 = false
            }
            
            def parentBssCode = caseReportForm.caseRecord.bss.parentBss.code
            def caseCollectionTypeCode = caseReportForm.caseRecord.caseCollectionType.code
            def version = deathCircumstancesInstance.version
            if(version == 0){
                return [deathCircumstancesInstance: deathCircumstancesInstance, formid:params.formid, parentBssCode:parentBssCode, caseCollectionTypeCode:caseCollectionTypeCode, cdrVer:cdrVer,version45:version45]
            }else{
                    
                def result= checkError(deathCircumstancesInstance, parentBssCode, caseCollectionTypeCode)
              
                if(result){
                    //result.each{
                    // deathCircumstancesInstance.errors.reject(it, it)
                    //}
                    result.each(){key,value->
                        if(key == 'dateTimeActualDeath2'){
                            deathCircumstancesInstance.errors.reject(key, value)
                        }else{
                            deathCircumstancesInstance.errors.rejectValue(key, value)
                        }
                        // println("it: " + it)
                           
                    }//each
                    return [deathCircumstancesInstance: deathCircumstancesInstance, parentBssCode:parentBssCode, caseCollectionTypeCode:caseCollectionTypeCode, cdrVer:cdrVer,version45:version45]
                
                }else{
              
                    return [deathCircumstancesInstance: deathCircumstancesInstance, formid:params.formid, parentBssCode:parentBssCode, caseCollectionTypeCode:caseCollectionTypeCode, cdrVer:cdrVer,version45:version45]
                }
                    
            }
          
           
            
            //return [deathCircumstancesInstance: deathCircumstancesInstance]
        }
    }

    
    
    /**  static List checkError(deathCircumstancesInstance){
    //println("some body call me...")
    def result = []
    def deathCertificateAvailable = deathCircumstancesInstance.deathCertificateAvailable
        
    if(!deathCertificateAvailable){
    result.add("Please specify if the death certificate is available.")
    }   
        
    def dateTimePronouncedDead = deathCircumstancesInstance.dateTimePronouncedDead
    if(!dateTimePronouncedDead){
    result.add("The date and time pronounced dead is a required field.")
    }
        
    if(dateTimePronouncedDead){
    def now = new Date()
    if(dateTimePronouncedDead > now){
    result.add("The date and time pronounced dead can not be later than the current date and time.")
    }
    }
        
    def dateTimeActualDeath = deathCircumstancesInstance.dateTimeActualDeath
    def dateTimePresumedDeath = deathCircumstancesInstance.dateTimePresumedDeath
    def dateTimeLastSeenAlive = deathCircumstancesInstance.dateTimeLastSeenAlive
    if(!dateTimeActualDeath && !dateTimePresumedDeath && !dateTimeLastSeenAlive){
    result.add("Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
    }else if(dateTimeActualDeath && (dateTimePresumedDeath||dateTimeLastSeenAlive) ){
    result.add("Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
    }else if (!dateTimeActualDeath && dateTimePresumedDeath && !dateTimeLastSeenAlive ){
    result.add("Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
    }else if (!dateTimeActualDeath && !dateTimePresumedDeath && dateTimeLastSeenAlive ){
    result.add("Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
    }else{
    if(dateTimeActualDeath){
    def now = new Date()
    if(dateTimeActualDeath > now){
    result.add("The date and time of actual (witnessed) death can not be later than the current date and time.")
    }
    }
            
    if(dateTimePresumedDeath){
    def now = new Date()
    if(dateTimePresumedDeath > now){
    result.add("The date and time of presumed death can not be later than the current date and time.")
    }
    }
            
    if(dateTimeLastSeenAlive){
    def now = new Date()
    if(dateTimeLastSeenAlive > now){
    result.add("The date and time last seen alive can not  be later than the current date and time.")
    }
    }
            
            
    }
        
        
    def placeOfDeath = deathCircumstancesInstance.placeOfDeath
    if(placeOfDeath && placeOfDeath.toString() == 'Other'){
    def otherPlaceOfDeath = deathCircumstancesInstance.otherPlaceOfDeath
    if(!otherPlaceOfDeath){
    result.add("Other place of death is a required field.")
    }
            
    }
        
    def personDeterminedDeath=deathCircumstancesInstance.personDeterminedDeath
    if(personDeterminedDeath && personDeterminedDeath.toString()=='Other'){
    def otherPersonDeterminedDeath=deathCircumstancesInstance.otherPersonDeterminedDeath
    if(!otherPersonDeterminedDeath){
    result.add("Other person who determined date/time of death is a required field.")
    }
    }
        
    def onVentilator=deathCircumstancesInstance.onVentilator
    if( onVentilator && onVentilator.toString()=='Yes'){
    def ventilatorDuration=deathCircumstancesInstance.ventilatorDuration
    if(!ventilatorDuration){
    result.add("The duration on ventilator is a required field")
    }
    }
        
    def ventilatorDuration=deathCircumstancesInstance.ventilatorDuration
    if(ventilatorDuration && !ventilatorDuration.isNumber()){
    result.add("The duration on ventilator must be a number.")
    }
        
    def immediateCause =deathCircumstancesInstance.immediateCause
    if(!immediateCause){
    result.add("The immediate cause of death is a required field.")
    }
       
    if(immediateCause && immediateCause.toString()=='Other'){
    def otherImmediate =deathCircumstancesInstance.otherImmediate
    if(!otherImmediate){
    result.add("Other immediate cause is a required field.")
    }
    }
        
    def immediateInterval = deathCircumstancesInstance.immediateInterval
    if(immediateInterval && !immediateInterval.isNumber()){
    result.add("The interval of onset to death for immedate cause must be a number.")
    }
        
    def firstCause =deathCircumstancesInstance.firstCause
        
    if(!firstCause){
    result.add("The first underlying cause of death is a required field.")
    }
       
        
    if( firstCause&& firstCause.toString()=='Other'){
    def otherFirstCause =deathCircumstancesInstance.otherFirstCause
    if(!otherFirstCause){
    result.add("Other first cause is a required field.")
    }
    }
        
    def firstCauseInterval = deathCircumstancesInstance.firstCauseInterval
    if(firstCauseInterval && !firstCauseInterval.isNumber()){
    result.add("The interval of onset to death for first underlying cause must be a number.")
    }
        
    def lastCause = deathCircumstancesInstance.lastCause
    if(lastCause && lastCause.toString()=='Other'){
    def otherLastCause =deathCircumstancesInstance.otherLastCause
    if(!otherLastCause){
    result.add("Other last cause is a required field.")
    }
    }
        
        
    def lastCauseInterval = deathCircumstancesInstance.lastCauseInterval
    if(lastCauseInterval && !lastCauseInterval.isNumber()){
    result.add("The interval of onset to death for last underlying cause must be a number.")
    }
        
    def wasRefrigerated = deathCircumstancesInstance.wasRefrigerated
        
    if(!wasRefrigerated){
    result.add("Please specify if the body was refrigerated at any time before procurement.")
    }
        
    if(wasRefrigerated && wasRefrigerated.toString()=='Yes'){
    def estimatedHours =deathCircumstancesInstance.estimatedHours
    if(!estimatedHours){
    result.add("The estimated number of hours in refrigeration is a required field.")
    }
    }
        
    def estimatedHours =deathCircumstancesInstance.estimatedHours
    if(estimatedHours && !estimatedHours.isNumber()){
    result.add("The estimate number of hours in refrigeration must be a number.")
    }
    // println("result size:" + result.size())
       
        
    def hardyScale = deathCircumstancesInstance.hardyScale
        
    if(!hardyScale){
    result.add("Please choose a Hardy Scale rating.")
    }
        
    return result
    }*/
    
    
    
    
    static Map checkError(deathCircumstancesInstance, parentBssCode, caseCollectionTypeCode){
        //println("some body call me...")
        def result = [:]
        def deathCertificateAvailable = deathCircumstancesInstance.deathCertificateAvailable
        
        if(!deathCertificateAvailable){
            result.put("deathCertificateAvailable","Please specify if the death certificate is available.")
        }   
        
        def dateTimePronouncedDead = deathCircumstancesInstance.dateTimePronouncedDead
        if(!dateTimePronouncedDead){
            result.put("dateTimePronouncedDead", "The date and time pronounced dead is a required field.")
        }
        
        if(dateTimePronouncedDead){
            def now = new Date()
            if(dateTimePronouncedDead > now){
                result.put("dateTimePronouncedDead", "The date and time pronounced dead can not be later than the current date and time.")
            }
        }
        
        def dateTimeActualDeath = deathCircumstancesInstance.dateTimeActualDeath
        def dateTimePresumedDeath = deathCircumstancesInstance.dateTimePresumedDeath
        def dateTimeLastSeenAlive = deathCircumstancesInstance.dateTimeLastSeenAlive
        if(!dateTimeActualDeath && !dateTimePresumedDeath && !dateTimeLastSeenAlive){
            result.put("dateTimeActualDeath2", "Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
        }else if(dateTimeActualDeath && (dateTimePresumedDeath||dateTimeLastSeenAlive) ){
            result.put("dateTimeActualDeath2", "Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
        }else if (!dateTimeActualDeath && dateTimePresumedDeath && !dateTimeLastSeenAlive ){
            result.put("dateTimeActualDeath2", "Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
        }else if (!dateTimeActualDeath && !dateTimePresumedDeath && dateTimeLastSeenAlive ){
            result.put("dateTimeActualDeath2", "Please specify date/time of actual death or both date/time of presumed death and date/time last seen alive.")
        }else{
            if(dateTimeActualDeath){
                def now = new Date()
                if(dateTimeActualDeath > now){
                    result.put("dateTimeActualDeath", "The date and time of actual (witnessed) death can not be later than the current date and time.")
                }
            }
            
            if(dateTimePresumedDeath){
                def now = new Date()
                if(dateTimePresumedDeath > now){
                    result.put("dateTimePresumedDeath", "The date and time of presumed death can not be later than the current date and time.")
                }
            }
            
            if(dateTimeLastSeenAlive){
                def now = new Date()
                if(dateTimeLastSeenAlive > now){
                    result.put("dateTimeLastSeenAlive", "The date and time last seen alive can not  be later than the current date and time.")
                }
            }
            
            
        }
        
        
        def placeOfDeath = deathCircumstancesInstance.placeOfDeath
        if(placeOfDeath && placeOfDeath.toString() == 'Other'){
            def otherPlaceOfDeath = deathCircumstancesInstance.otherPlaceOfDeath
            if(!otherPlaceOfDeath){
                result.put("otherPlaceOfDeath", "Other place of death is a required field.")
            }
            
        }
        
        def personDeterminedDeath=deathCircumstancesInstance.personDeterminedDeath
        if(personDeterminedDeath && personDeterminedDeath.toString()=='Other'){
            def otherPersonDeterminedDeath=deathCircumstancesInstance.otherPersonDeterminedDeath
            if(!otherPersonDeterminedDeath){
                result.put("otherPersonDeterminedDeath", "Other person who determined date/time of death is a required field.")
            }
        }
        
        def onVentilator=deathCircumstancesInstance.onVentilator
        if( onVentilator && onVentilator.toString()=='Yes'){
            def ventilatorDuration=deathCircumstancesInstance.ventilatorDuration
            if(!ventilatorDuration){
                result.put("ventilatorDuration", "The duration on ventilator is a required field")
            }
        }
        
        def ventilatorDuration=deathCircumstancesInstance.ventilatorDuration
        if(ventilatorDuration && !ventilatorDuration.isNumber()){
            result.put("ventilatorDuration", "The duration on ventilator must be a number.")
        }


        //def caseReportForm = CaseReportForm.findById(deathCircumstancesInstance.id)
        def caseReportForm = CaseReportForm.findByDeathCircumstances(deathCircumstancesInstance)
        def cdrVer = caseReportForm.caseRecord.cdrVer
        def  version45 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            version45 = false
        }
        
        def immediateCause =deathCircumstancesInstance.immediateCause
        def immediateCauseVocab =deathCircumstancesInstance.immCodCvocab
        if (version45) {
            def deathCertificateCause =deathCircumstancesInstance.deathCertificateCause
            def deathCertificateCauseVocab =deathCircumstancesInstance.deathCertCauseVocab
            if(!deathCertificateCauseVocab && !deathCertificateCause && deathCertificateAvailable.toString().equals('Yes')){
                result.put("deathCertificateCause", "Please specify Death certificate cause of death.")
            }
            if(!immediateCauseVocab){
                result.put("immediateCause", "The immediate cause of death is a required field.")
            }                        
        } else {
            if(!immediateCause){
                result.put("immediateCause", "The immediate cause of death is a required field.")
            }            
        }
        
       
        if(immediateCause && immediateCause.toString()=='Other'){
            def otherImmediate =deathCircumstancesInstance.otherImmediate
            if(!otherImmediate){
                result.put("otherImmediate", "Other immediate cause is a required field.")
            }
        }
        
        def immediateInterval = deathCircumstancesInstance.immediateInterval
        if(immediateInterval && !immediateInterval.isNumber()){
            result.put("immediateInterval", "The interval of onset to death for immedate cause must be a number.")
        }
        
        def firstCause =deathCircumstancesInstance.firstCause
        def firstCauseVocab =deathCircumstancesInstance.firstCodCvocab

        if (version45) {
            if(!firstCauseVocab){
                result.put("firstCause", "The first underlying cause of death is a required field.")
            }                        
        } else {
            if(!firstCause){
                result.put("firstCause", "The first underlying cause of death is a required field.")
            }
        }

        
       
        
        if( firstCause&& firstCause.toString()=='Other'){
            def otherFirstCause =deathCircumstancesInstance.otherFirstCause
            if(!otherFirstCause){
                result.put("otherFirstCause", "Other first cause is a required field.")
            }
        }
        
        def firstCauseInterval = deathCircumstancesInstance.firstCauseInterval
        if(firstCauseInterval && !firstCauseInterval.isNumber()){
            result.put("firstCauseInterval", "The interval of onset to death for first underlying cause must be a number.")
        }
        
        def lastCause = deathCircumstancesInstance.lastCause
        if(lastCause && lastCause.toString()=='Other'){
            def otherLastCause =deathCircumstancesInstance.otherLastCause
            if(!otherLastCause){
                result.put("otherLastCause", "Other last cause is a required field.")
            }
        }
        
        
        def lastCauseInterval = deathCircumstancesInstance.lastCauseInterval
        if(lastCauseInterval && !lastCauseInterval.isNumber()){
            result.put("lastCauseInterval", "The interval of onset to death for last underlying cause must be a number.")
        }
        
        def wasRefrigerated = deathCircumstancesInstance.wasRefrigerated
        
        if(!wasRefrigerated){
            result.put("wasRefrigerated", "Please specify if the body was refrigerated at any time before procurement.")
        }
        
        if(wasRefrigerated && wasRefrigerated.toString()=='Yes'){
            def estimatedHours =deathCircumstancesInstance.estimatedHours
            if(!estimatedHours){
                result.put("estimatedHours", "The estimated number of hours in refrigeration is a required field.")
            }
        }
        
        def estimatedHours =deathCircumstancesInstance.estimatedHours
        if(estimatedHours && !estimatedHours.isNumber()){
            result.put("estimatedHours", "The estimate number of hours in refrigeration must be a number.")
        }
        // println("result size:" + result.size())
       
        
        def hardyScale = deathCircumstancesInstance.hardyScale
        
        if(!hardyScale){
            result.put("hardyScale", "Please choose a Hardy Scale rating.")
        }
        
        def opoType = deathCircumstancesInstance.opoType
        if(!opoType && parentBssCode == 'NDRI' && caseCollectionTypeCode == 'OPO'){
            result.put("opoType", "Please choose an Organ Donor (OPO) Type.")
        }
        
        return result
    }
    
    
    static String decodeURIComponent(String input){
        
        if(input == null || input.length() == 0){
            return "";
        }else{
            input = input.trim();
            println("input: " + input)
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            // input="%22A%22%20B%20%c2%b1%20%22";
            String out = (String)engine.eval("decodeURIComponent('" + input + "')");

            return out;
        }
       
        
    }
    
    def update = {
        //println("in update formid: ${params.formid}")
        def deathCircumstancesInstance = DeathCircumstances.get(params.id)
        if (deathCircumstancesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deathCircumstancesInstance.version > version) {
                    
                    deathCircumstancesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deathCircumstances.label', default: 'DeathCircumstances')] as Object[], "Another user has updated this DeathCircumstances while you were editing")
                    render(view: "edit", model: [deathCircumstancesInstance: deathCircumstancesInstance])
                    return
                }
            }

            def caseReportForm = CaseReportForm.findById(params.formid)    
            def cdrVer = caseReportForm.caseRecord.cdrVer    
            def  version45 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                version45 = false
            }

            //println " Desc : "+params.results-cod-cod            
            deathCircumstancesInstance.properties = params
            CVocabRecord newVocab// = new CVocabRecord()
            CVocabCUIRecord cuiRec
            if (version45) {            
                if (params.deathCertificateCauseR == 'vocab' ) {
                
                    if (params.deathCertificateCauseCodId!="" &&  encode(params.deathCertificateCauseCodCod) != deathCircumstancesInstance.deathCertificateCauseV ) {
                        deathCircumstancesInstance.deathCertificateCauseV=encode(params.deathCertificateCauseCodCod)
                        if (deathCircumstancesInstance.deathCertCauseVocab!=null) {
                            newVocab = deathCircumstancesInstance.deathCertCauseVocab
                            deathCircumstancesInstance.deathCertCauseVocab.cuiList.each() {
                                cuiRec = it
                            }
                            //deathCircumstancesInstance.deathCertCauseVocab.cuiList = null
                        } else {
                            newVocab = new CVocabRecord()
                            cuiRec = new CVocabCUIRecord()
                        }

                        newVocab.cVocabId = params.deathCertificateCauseCodId   
                        if (params.deathCertificateCauseCodTyp.equalsIgnoreCase("COD")) {
                            newVocab.cVocabType = CVocabType.findByCode("COD")
                        } else {
                            newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                        }

                        newVocab.cVocabUserSelection = encode(params.deathCertificateCauseCodCod)
                        def cuiCS = params.deathCertificateCauseCodCui
                        if (cuiCS!="") {
                            if (deathCircumstancesInstance.deathCertCauseVocab!=null) {
                                deathCircumstancesInstance.deathCertCauseVocab.cuiList = null
                            }                        
                            String [] cuis = cuiCS.split(",")
                            cuis.each {
                                cuiRec.cui = it.toString()
                                newVocab.addToCuiList(cuiRec) 
                                cuiRec = new CVocabCUIRecord()
                            }
                        }
                        newVocab.icdCd = params.deathCertificateCauseCodIcdCd
                        newVocab.cVocabVer = params.deathCertificateCauseCodCvocabVer
                        newVocab.srcDef=params.deathCertificateCauseCodSrcDef
                        newVocab.save(failOnError:true, flush: true)
                        deathCircumstancesInstance.deathCertCauseVocab = newVocab
                        deathCircumstancesInstance.deathCertificateCause = ""
                    }
                } else if (params.deathCertificateCauseR == 'manual') {
                    deathCircumstancesInstance.deathCertCauseVocab = null 
                    deathCircumstancesInstance.deathCertificateCauseV=""
                }
            

                if (params.immediateCauseCodId!="" && encode(params.immediateCauseCodCod) != deathCircumstancesInstance.immediateCause ) {
                    deathCircumstancesInstance.immediateCause = encode(params.immediateCauseCodCod)
                    if (deathCircumstancesInstance.immCodCvocab!=null) {
                        newVocab = deathCircumstancesInstance.immCodCvocab
                        deathCircumstancesInstance.immCodCvocab.cuiList.each() {
                            cuiRec = it
                        }
                        //deathCircumstancesInstance.immCodCvocab.cuiList = null
                    } else {
                        newVocab = new CVocabRecord()
                        cuiRec = new CVocabCUIRecord()
                    }

                    newVocab.cVocabId = params.immediateCauseCodId   
                    if (params.immediateCauseCodTyp.equalsIgnoreCase("COD")) {
                        newVocab.cVocabType = CVocabType.findByCode("COD")
                    } else {
                        newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                    }

                    newVocab.cVocabUserSelection = encode(params.immediateCauseCodCod)
                    def cuiCS = params.immediateCauseCodCui
                    if (cuiCS!="") {
                        if (deathCircumstancesInstance.immCodCvocab!=null) {
                            deathCircumstancesInstance.immCodCvocab.cuiList = null
                        }
                        String [] cuis = cuiCS.split(",")
                        cuis.each {
                            cuiRec.cui = it.toString()
                            newVocab.addToCuiList(cuiRec) 
                            cuiRec = new CVocabCUIRecord()
                        }
                    }
                    newVocab.icdCd = params.immediateCauseCodIcdCd
                    newVocab.cVocabVer = params.immediateCauseCodCvocabVer
                    newVocab.srcDef=params.immediateCauseCodSrcDef
                    // newVocab.srcDefDeco=decodeURIComponent(params.immediateCauseCodSrcDef)
                    newVocab.save(failOnError:true, flush: true)
                    deathCircumstancesInstance.immCodCvocab = newVocab
                }
            
                if (params.firstCauseCodId!="" && encode(params.firstCauseCodCod) != deathCircumstancesInstance.firstCause) {
                    deathCircumstancesInstance.firstCause = encode(params.firstCauseCodCod)
                    if (deathCircumstancesInstance.firstCodCvocab!=null) {
                        newVocab = deathCircumstancesInstance.firstCodCvocab
                        deathCircumstancesInstance.firstCodCvocab.cuiList.each() {
                            cuiRec = it
                        }
                        //deathCircumstancesInstance.firstCodCvocab.cuiList = null
                    } else {
                        newVocab = new CVocabRecord()
                        cuiRec = new CVocabCUIRecord()
                    }                
                    newVocab.cVocabId = params.firstCauseCodId   
                    if (params.firstCauseCodTyp.equalsIgnoreCase("COD")) {
                        newVocab.cVocabType = CVocabType.findByCode("COD")
                    } else {
                        newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                    }
            
                    newVocab.cVocabUserSelection = encode(params.firstCauseCodCod)
                    def cuiCS = params.firstCauseCodCui
                    if (cuiCS!="") {
                        if (deathCircumstancesInstance.firstCodCvocab!=null) {
                            deathCircumstancesInstance.firstCodCvocab.cuiList = null
                        }                
                        String [] cuis = cuiCS.split(",")
                        cuis.each {
                            cuiRec.cui = it.toString()
                            newVocab.addToCuiList(cuiRec) 
                            cuiRec = new CVocabCUIRecord()
                        }
                    }
                    newVocab.icdCd = params.firstCauseCodIcdCd
                    newVocab.cVocabVer = params.firstCauseCodCvocabVer
                    newVocab.srcDef=params.firstCauseCodSrcDef
                    newVocab.save(failOnError:true, flush: true)
                    deathCircumstancesInstance.firstCodCvocab = newVocab
                }
             
                if (params.lastCauseCodId!="" && encode(params.lastCauseCodCod) != deathCircumstancesInstance.lastCause) {
                    deathCircumstancesInstance.lastCause =encode(params.lastCauseCodCod)
                    if (deathCircumstancesInstance.lastCodCvocab!=null) {
                        newVocab = deathCircumstancesInstance.lastCodCvocab
                        deathCircumstancesInstance.lastCodCvocab.cuiList.each() {
                            cuiRec = it
                        }
                        //deathCircumstancesInstance.lastCodCvocab.cuiList = null
                    } else {
                        newVocab = new CVocabRecord()
                        cuiRec = new CVocabCUIRecord()
                    }
                    newVocab.cVocabId = params.lastCauseCodId   
                    if (params.lastCauseCodTyp.equalsIgnoreCase("COD")) {
                        newVocab.cVocabType = CVocabType.findByCode("COD")
                    } else {
                        newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                    }
            
                    newVocab.cVocabUserSelection = encode(params.lastCauseCodCod)
                    def cuiCS = params.lastCauseCodCui
                    if (cuiCS!="") {
                        if (deathCircumstancesInstance.lastCodCvocab!=null) {
                            deathCircumstancesInstance.lastCodCvocab.cuiList = null
                        }                                
                        String [] cuis = cuiCS.split(",")
                        cuis.each {
                            cuiRec.cui = it.toString()
                            newVocab.addToCuiList(cuiRec) 
                            cuiRec = new CVocabCUIRecord()
                        }
                    }
                    newVocab.icdCd = params.lastCauseCodIcdCd
                    newVocab.cVocabVer = params.lastCauseCodCvocabVer
                    newVocab.srcDef=params.lastCauseCodSrcDef
                    newVocab.save(failOnError:true, flush: true)
                    deathCircumstancesInstance.lastCodCvocab = newVocab
                }
            }    
            
            /* if (!deathCircumstancesInstance.hasErrors() && deathCircumstancesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), deathCircumstancesInstance.id])}"
            // redirect(action: "show", id: deathCircumstancesInstance.id)
            redirect(action:"edit", params:[id:deathCircumstancesInstance.id, formid:params.formid])
            }
            else {
            flash.message = "data not saved"
            // render(view: "edit", model: [deathCircumstancesInstance: deathCircumstancesInstance])
            redirect(action:"edit", params:[id:deathCircumstancesInstance.id, formid:params.formid])
            }*/
            
            
            try{
                deathCircumstancesInstance.save(failOnError:true, flush: true)
                
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances for '), caseReportForm?.caseRecord?.caseId])}"
                redirect(action:"edit", params:[id:deathCircumstancesInstance.id, formid:params.formid, cdrVer:cdrVer,version45:version45])
                 
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"edit", params:[id:deathCircumstancesInstance.id, formid:params.formid, cdrVer:cdrVer,version45:version45])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), params.id])}"
            redirect(action: "list")
        }
    }

/**   String encode(str){
      
       if(!str)
        retunr str  
        str =str.replaceAll(">", "&gt;")
        str = str.replaceAll("<", "&lt;")
      
        return str;
        
   }**/
   
    
     String encode(str){
      
       if(!str)
          retunr str 
       
         str =str.replaceAll(">", "&gt;")
         str = str.replaceAll("<", "&lt;")
         str=str.replaceAll("\"", "&quot;")
       if(str.indexOf('&amp;')>=0){
            str=str.replace("&amp;", "&")
            encode(str)
       }else{
            return str;
       }
      
    }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])     
    def delete = {
        def deathCircumstancesInstance = DeathCircumstances.get(params.id)
        if (deathCircumstancesInstance) {
            try {
                deathCircumstancesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deathCircumstances.label', default: 'DeathCircumstances'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
     //PMH 06/26/13 display Vocab definitions in jquery dialog CDRQA 665
     def vocabdetails ={
       
        def vocabRec
        def typ = ""
        def cuiList = ""
        if(params.vocid){
            vocabRec = CVocabRecord.findById(params.vocid)
        }
        if(vocabRec){
            typ=CVocabType.findById(vocabRec.cVocabType?.id).code
            if(vocabRec.cuiList){
                cuiList=vocabRec.cuiList?.join(",")
            }
        }
        
        if(params.callback) {
            render "${params.callback.encodeAsURL()}(${[vocabRec:vocabRec,typ:typ,cuiList:cuiList] as JSON})"
        } else {
            render ([vocabRec:vocabRec,typ:typ,cuiList:cuiList] as JSON)
        }
    }
}
