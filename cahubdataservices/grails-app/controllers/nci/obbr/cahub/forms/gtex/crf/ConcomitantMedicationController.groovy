package nci.obbr.cahub.forms.gtex.crf


import nci.obbr.cahub.staticmembers.CVocabType
import nci.obbr.cahub.datarecords.vocab.*
import nci.obbr.cahub.util.ComputeMethods

class ConcomitantMedicationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def accessPrivilegeService
    

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [concomitantMedicationInstanceList: ConcomitantMedication.list(params), concomitantMedicationInstanceTotal: ConcomitantMedication.count()]
    }

    def list_cm = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
      
        
        CaseReportForm caseReportForm = CaseReportForm.findById(params.formid) 
        
        def caseRecord = caseReportForm?.caseRecord
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
            
        //PMH 03/08/13: hub-cr-31
        //def list1=caseReportForm.concomitantMedications
        def list1=ConcomitantMedication.findAllByCaseReportForm(caseReportForm, [sort:'dateCreated',order:'asc'])
        def cdrVer = caseReportForm.caseRecord.cdrVer
        
        //pmh hub-cr-44
        def defaultSource=caseReportForm.medicalHistory.source
        def  show45VersionUpdates = true
        def show25VersionUpdates =true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            show45VersionUpdates = false
        }
        if(ComputeMethods.compareCDRVersion(cdrVer, '2.5') < 0){
            show25VersionUpdates = false
            
        }
        //end pmh
        
        def  errorMap=[:]
        for(m in list1){
            def id = m.id
            // println("id is: " + id)
            def medicationName= m.medicationName
            //def dosage = m.dosage
            def amount = m.amount
            def dosageUnit = m.dosageUnit
            def frequency = m.frequency
            def route = m.route
            def dateofLastAdministration = m.dateofLastAdministration
            def source = m.source?.trim()

            if(show25VersionUpdates && !show45VersionUpdates) {
                if(!medicationName){
                    def str = "The Medication Name/Vitamins/Supplements is a required field."
                    m.errors.reject(str, str)
                    errorMap.put("medicationName" + id, "errors")
                }                
            } 

            
            /* if(!dosage){
               
            def str= "The Dosage is a required field for " + medicationName + "." 
            m.errors.reject(str, str)
            println("add to errorMap")
            errorMap.put("medicationName" + id, "errors")
            }*/
            
            if(!amount && show25VersionUpdates && !show45VersionUpdates){
               
                def str= "The Amount is a required field for " + medicationName + "." 
                m.errors.reject(str, str)
                //println("add to errorMap")
                errorMap.put("medicationName" + id, "errors")
            }
            
            if(!dosageUnit && show25VersionUpdates && !show45VersionUpdates){
               
                def str= "The Dosage Form/Unit is a required field for " + medicationName + "." 
                m.errors.reject(str, str)
                //println("add to errorMap")
                errorMap.put("medicationName" + id, "errors")
            }
            
            
            if(!frequency  && show25VersionUpdates && !show45VersionUpdates){
               
                def str= "The Frequency is a required field for " + medicationName + "." 
                m.errors.reject(str, str)
                //println("add to errorMap")
                errorMap.put("medicationName" + id, "errors")
            }
            
            
            if(!route && show25VersionUpdates && !show45VersionUpdates){
                def str = "The Route is a required field for " + medicationName + "." 
                m.errors.reject(str, str)
                errorMap.put("medicationName" + id, "errors")
            }
            
            if(!dateofLastAdministration &&( show25VersionUpdates || show45VersionUpdates)){
                
                def str = "The Date of Last Administration is a required field for " + medicationName + "."
                m.errors.reject(str, str)
                errorMap.put("medicationName" + id, "errors")
                
            }
            
            if(!source && show45VersionUpdates){
                
                def str = "The History Source is a required field for  " + medicationName + "."
                m.errors.reject(str, str)
                errorMap.put("medicationName" + id, "errors")
                
            }
            
            // println("what is the value? " +errorMap.get("medicationName21"))
        }
        
        //def list1 = CaseReportForm.findAllByCaseReportForm(caseReportForm)
        // [concomitantMedicationInstanceList: list1, concomitantMedicationInstanceTotal: list1.size(), formid:params.formid, errorMap:errorMap, cdrVer:cdrVer]
        [concomitantMedicationInstanceList: list1, concomitantMedicationInstanceTotal: list1.size(), formid:params.formid, errorMap:errorMap, show25VersionUpdates:show25VersionUpdates,show45VersionUpdates:show45VersionUpdates,defaultSource:defaultSource]
    }
    def create = {
        def concomitantMedicationInstance = new ConcomitantMedication()
        concomitantMedicationInstance.properties = params
        return [concomitantMedicationInstance: concomitantMedicationInstance]
    }

    def save = {
        def concomitantMedicationInstance = new ConcomitantMedication(params)
        CaseReportForm caseReportForm = CaseReportForm.findById(params.formid) 
        if (concomitantMedicationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'concomitantMedication.label', default: 'Current Medication for '), caseReportForm?.caseRecord?.caseId])}"
            redirect(action: "show", id: concomitantMedicationInstance.id)
        }
        else {
            render(view: "create", model: [concomitantMedicationInstance: concomitantMedicationInstance])
        }
    }

    def save_cm = {
        def concomitantMedicationInstance = new ConcomitantMedication(params)
        /* if (concomitantMedicationInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.created.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), concomitantMedicationInstance.id])}"
        //redirect(action: "show", id: concomitantMedicationInstance.id)
        redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
        }
        else {
        redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
        // render(view: "create", model: [concomitantMedicationInstance: concomitantMedicationInstance])
        }*/
        def caseReportForm = CaseReportForm.findById(params.formid)    
        def cdrVer = caseReportForm.caseRecord.cdrVer    
        def  version45 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            version45 = false
        }
        CVocabRecord newVocab
        
       if (version45) {
            if (params.medicationNameRxId!=""  ) {
                concomitantMedicationInstance.medicationName = params.medicationNameRxRx
                if (concomitantMedicationInstance.medicationNameCvocab!=null) {
                    newVocab = concomitantMedicationInstance.medicationNameCvocab
                } else {
                    newVocab = new CVocabRecord()
                }

                newVocab.cVocabId = params.medicationNameRxId
                if (params.medicationNameRxTyp.equalsIgnoreCase("RX")) {
                    newVocab.cVocabType = CVocabType.findByCode("RX")
                } else {
                    newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                }

                newVocab.cVocabUserSelection = params.medicationNameRxRx
                newVocab.cVocabVer = params.medicationNameRxCvocabVer
                    newVocab.genName = params.medicationNameRxGenName
                newVocab.save(failOnError:true, flush: true)
                concomitantMedicationInstance.medicationNameCvocab = newVocab
            }            
       }             
        
        try{
            concomitantMedicationInstance.save(failOnError:true, flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'concomitantMedication.label', default: 'Current Medication for '), caseReportForm?.caseRecord?.caseId])}"
            redirect(action:"list_cm", params:[formid:params.caseReportForm.id])    
        }catch(Exception e){
            flash.message = "Failed: " + e.toString()
            redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
        }
        
    }
    
    def show = {
        def concomitantMedicationInstance = ConcomitantMedication.get(params.id)
        if (!concomitantMedicationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), params.id])}"
            redirect(action: "list")
        }
        else {
            [concomitantMedicationInstance: concomitantMedicationInstance]
        }
    }

    def edit = {
        def concomitantMedicationInstance = ConcomitantMedication.get(params.id)
        if (!concomitantMedicationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), params.id])}"
            redirect(action: "list")
        }
        else {
            
            def caseRecord = concomitantMedicationInstance?.caseReportForm?.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            } 
            
            def medicationName= concomitantMedicationInstance.medicationName
            // def dosage = concomitantMedicationInstance.dosage
            def amount = concomitantMedicationInstance.amount
            def dosageUnit = concomitantMedicationInstance.dosageUnit
            def frequency = concomitantMedicationInstance.frequency
            def route = concomitantMedicationInstance.route
            def dateofLastAdministration = concomitantMedicationInstance.dateofLastAdministration
            def caseReportForm = CaseReportForm.findById(params.formid)
            def cdrVer = caseReportForm.caseRecord.cdrVer
            def source=concomitantMedicationInstance.source?.trim()
            //pmh hub-cr-712
            def  show45VersionUpdates = true
            def show25VersionUpdates =true
            if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                show45VersionUpdates = false
            }
            if(ComputeMethods.compareCDRVersion(cdrVer, '2.5') < 0){
                show25VersionUpdates = false
            
            }
               
            if(!medicationName && show25VersionUpdates && !show45VersionUpdates){
                def str = "The Medication Name/Vitamins/Supplements is a required field."
                concomitantMedicationInstance.errors.rejectValue("medicationName", str)
               
            }
            
            /* if(!dosage){
            def str= "The Dosage is a required field for " + medicationName + "." 
            concomitantMedicationInstance.errors.rejectValue("dosage", str)
            }*/
            
            if(!amount && show25VersionUpdates && !show45VersionUpdates){
                def str= "The Amount is a required field."
                concomitantMedicationInstance.errors.rejectValue("amount", str)
            }
            
            if(!dosageUnit && show25VersionUpdates && !show45VersionUpdates){
                def str= "The Dosage Form/Unit is a required field."
                concomitantMedicationInstance.errors.rejectValue("dosageUnit", str)
            }
            
            
            if(!frequency && show25VersionUpdates && !show45VersionUpdates){
                def str= "The Frequency is a required field."
                concomitantMedicationInstance.errors.rejectValue("frequency", str)
            }
            
            
            
            if(!route && show25VersionUpdates && !show45VersionUpdates){
                def str = "The Route is a required field." 
                concomitantMedicationInstance.errors.rejectValue("route", str)
            }
            
            if(!dateofLastAdministration ){
                def str = "The Date of Last Administration is a required field."
                concomitantMedicationInstance.errors.rejectValue("dateofLastAdministration", str)
                
            }
            if(!source && show45VersionUpdates){
                
                def str = "The History Source is a required field "
                concomitantMedicationInstance.errors.rejectValue("source", str)
                
                
            }
            
          //  return [concomitantMedicationInstance: concomitantMedicationInstance, cdrVer:cdrVer]
            return [concomitantMedicationInstance: concomitantMedicationInstance, show25VersionUpdates:show25VersionUpdates,show45VersionUpdates:show45VersionUpdates]
        }
    }

    def update = {
        def concomitantMedicationInstance = ConcomitantMedication.get(params.id)
        if (concomitantMedicationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (concomitantMedicationInstance.version > version) {
                    
                    concomitantMedicationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication')] as Object[], "Another user has updated this ConcomitantMedication while you were editing")
                    render(view: "edit", model: [concomitantMedicationInstance: concomitantMedicationInstance])
                    return
                }
            }
        def caseReportForm = CaseReportForm.findById(params.formid)    
        def cdrVer = caseReportForm.caseRecord.cdrVer    
        def  version45 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            version45 = false
        }            
            
            concomitantMedicationInstance.properties = params
            CVocabRecord newVocab
            
       if (version45) {
            if (params.medicationNameRxId!="" && params.medicationNameRxRx != concomitantMedicationInstance.medicationName) {
                concomitantMedicationInstance.medicationName=params.medicationNameRxRx
                if (concomitantMedicationInstance.medicationNameCvocab!=null) {
                    newVocab = concomitantMedicationInstance.medicationNameCvocab
                } else {
                    newVocab = new CVocabRecord()
                }

                newVocab.cVocabId = params.medicationNameRxId
                if (params.medicationNameRxTyp.equalsIgnoreCase("RX")) {
                    newVocab.cVocabType = CVocabType.findByCode("RX")
                } else {
                    newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                }

                newVocab.cVocabUserSelection = params.medicationNameRxRx
                newVocab.cVocabVer = params.medicationNameRxCvocabVer
                    newVocab.genName = params.medicationNameRxGenName
                newVocab.save(failOnError:true, flush: true)
                concomitantMedicationInstance.medicationNameCvocab = newVocab
            }            
       }     
            
            
            
            /* if (!concomitantMedicationInstance.hasErrors() && concomitantMedicationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), concomitantMedicationInstance.id])}"
            // redirect(action: "show", id: concomitantMedicationInstance.id)
               
            redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
            }
            else {
            flash.message ="data not saved"
            //render(view: "edit", model: [concomitantMedicationInstance: concomitantMedicationInstance])
            redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
            }*/
            
            try{
                concomitantMedicationInstance.save(failOnError:true, flush: true)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'concomitantMedication.label', default: 'Current Medication for '), caseReportForm?.caseRecord?.caseId])}"
                redirect(action:"list_cm", params:[formid:params.caseReportForm.id])    
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def concomitantMedicationInstance = ConcomitantMedication.get(params.id)
        if (concomitantMedicationInstance) {
            try {
                concomitantMedicationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), params.id])}"
                //redirect(action: "list")
                redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Failed: " + e.toString()
                //redirect(action: "show", id: params.id)
                redirect(action:"list_cm", params:[formid:params.caseReportForm.id])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'concomitantMedication.label', default: 'ConcomitantMedication'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    static Map checkError(concomitantMedication,cdrVer){
        
        def ComputeMethods = new ComputeMethods()
        
        def result = [:]
        for(m in concomitantMedication){
            def id = m.id
            def medicationName= m.medicationName
            //def dosage = m.dosage
            def amount = m.amount
            def dosageUnit = m.dosageUnit
            def frequency = m.frequency
            def route = m.route
            def dateofLastAdministration = m.dateofLastAdministration
            def source = m.source
               
            if(!medicationName && ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                result.put("medicationName" + id, "The Medication Name/Vitamins/Supplements is a required field." )
            }
            
            /*if(!dosage){
            //println("catch error in checkError method....")
            result.put("dosage" + id, "The Dosage is a required field for " + medicationName + "." )
            }*/
            
            if(!amount && ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0 && ComputeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                result.put("amount" + id, "The Amount is a required field for " + medicationName + "." )
                
            }
            
            if(!dosageUnit && ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0 && ComputeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                result.put("dosageUnit" + id, "The Dosage Form/Unit is a required field for " + medicationName + "." )
                
            }
            
            if(!frequency && ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0 && ComputeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                result.put("frequency" + id, "The Frequency is a required field for " + medicationName + "." )
                
            }
            
            if(!route && ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0 && ComputeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                result.put("route" + id, "The Route is a required field for " + medicationName + "." )
            }
            
            if(!dateofLastAdministration ){
                result.put("dateofLastAdministration" + id, "The Date of Last Administration is a required field for " + medicationName + "." )
            }
            if(!source && ComputeMethods.compareCDRVersion(cdrVer, '4.5') >= 0 ){
                
                result.put("source" + id, "The History Source is a required field " + medicationName + "." )
            }
               
        }
           
        return result
          
        
    }
}
