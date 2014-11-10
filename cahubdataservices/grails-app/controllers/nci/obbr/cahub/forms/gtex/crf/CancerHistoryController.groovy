package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.util.ComputeMethods
import nci.obbr.cahub.staticmembers.CVocabType
import nci.obbr.cahub.datarecords.vocab.*

class CancerHistoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [cancerHistoryInstanceList: CancerHistory.list(params), cancerHistoryInstanceTotal: CancerHistory.count()]
    }
    
    def list_ch = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        
        //println("mid: ${params.mid}")
        def medicalHistory = MedicalHistory.findById(params.mid)
        def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
        def caseRecord = caseReportFormInstance?.caseRecord
        
        
        
        //pmh hub-cr-44
        def defaultSource=caseReportFormInstance.medicalHistory.source
        def show45VersionUpdates = true
        if(ComputeMethods.compareCDRVersion(caseReportFormInstance.caseRecord.cdrVer, '4.5') < 0){
            show45VersionUpdates = false
        }
        
        
   
        def show50VersionUpdates = true
        if(ComputeMethods.compareCDRVersion(caseReportFormInstance.caseRecord.cdrVer, '5.0') < 0){
            show50VersionUpdates = false
            
        }
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
       
        def list1 = CancerHistory.findAllByMedicalHistory(medicalHistory)
        def errorMap=[:]
         
        for(ca in list1){
            def primaryTumorSite = ca.primaryTumorSite
            def monthYearOfFirstDiagnosis = ca.monthYearOfFirstDiagnosis
            def treatments = ca.treatments
            def monthYearOfLastTreatment = ca.monthYearOfLastTreatment
            def medicalRecordExist = ca.medicalRecordExist
            def treatmentOther = ca.treatmentOther
            def otherTreatment = ca.otherTreatment
            def caseReportForm = CaseReportForm.findById(params.formid)
            def cdrVer = caseReportForm.caseRecord.cdrVer
                     
            // if (!monthYearOfFirstDiagnosis && cdrVer?.compareTo("2.5") >= 0){
                        
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') < 0){
                if (!monthYearOfFirstDiagnosis)
                {
                    def str = "The month/year of first diagnosis is a required field for " + primaryTumorSite
                    ca.errors.rejectValue("monthYearOfFirstDiagnosis", str)
                    errorMap.put(ca.id, "errors")
                } 
                
            }
            if(treatments=='null' &&  !treatmentOther ){
                def str = "The history of any treatments is a required field for " + primaryTumorSite
                ca.errors.rejectValue("treatments", str)
                errorMap.put(ca.id, "errors")
                       
            }
                     
            if(treatmentOther && !otherTreatment){
                def str = "The other treatment is a required field for " + primaryTumorSite
                ca.errors.rejectValue("otherTreatment", str)
                errorMap.put(ca.id, "errors")
                       
            }
            
            //  PMH HUB-CR-31 03/07/13
           
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') < 0){
              
                if(!monthYearOfLastTreatment ){
                    
                    def str = "The date of last radiation or chemotherapy treatment is a required field for " + primaryTumorSite
                    ca.errors.rejectValue("monthYearOfLastTreatment", str)
                    errorMap.put(ca.id, "errors")
                }
                
            }
            
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') >= 0){
                def treatmentRadiation = ca.treatmentRadiation
                def treatmentChemotherapy = ca.treatmentChemotherapy
            
                if (treatmentRadiation || treatmentChemotherapy){
                    if (!monthYearOfLastTreatment)
                    {
                        def str = "The date of last radiation or chemotherapy treatment is a required field for " + primaryTumorSite
                        ca.errors.rejectValue("monthYearOfLastTreatment", str)
                        errorMap.put(ca.id, "errors")
                    }
                    
                }
                
                if(ca.source?.trim() == null){
                    def str = "The History Source is a required field for " + primaryTumorSite
                    ca.errors.rejectValue("source", str)
                    errorMap.put(ca.id, "errors")
                }
            }
            
            if (monthYearOfFirstDiagnosis && monthYearOfLastTreatment)
            {
                if (!monthYearOfFirstDiagnosis.toString().startsWith("1900-01") && !monthYearOfLastTreatment.toString().startsWith("1900-01"))
                {
                    if (monthYearOfFirstDiagnosis.toString().compareTo(monthYearOfLastTreatment.toString()) > 0)
                    {
                        def str = "The first diagnosis cannot be later than the date of last radiation or chemotherapy treatment."
                        ca.errors.rejectValue("monthYearOfLastTreatment", str)
                        errorMap.put(ca.id, "errors")
                    }
                }
            }
            
            // end PMH
                    
            if(!medicalRecordExist){
                def str = "Please specify if there is medical record documentation of this history of cancer and treatment for " + primaryTumorSite
                ca.errors.rejectValue("medicalRecordExist", str)
                errorMap.put(ca.id, "errors")
                       
            }
                 
        }
      
       
        
        [cancerHistoryInstanceList:list1, cancerHistoryInstanceTotal: list1.size(), mid:params.mid, errorMap:errorMap,defaultSource:defaultSource,show45VersionUpdates:show45VersionUpdates, show50VersionUpdates:show50VersionUpdates]
        
        
    }

    def create = {
        def cancerHistoryInstance = new CancerHistory()
        cancerHistoryInstance.properties = params
        return [cancerHistoryInstance: cancerHistoryInstance]
    }

    def save = {
        def cancerHistoryInstance = new CancerHistory(params)
        if (cancerHistoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'cancerHistory.label', default: 'Cancer History for'), cancerHistoryInstance.primaryTumorSite])}"
            redirect(action: "show", id: cancerHistoryInstance.id)
        }
        else {
            render(view: "create", model: [cancerHistoryInstance: cancerHistoryInstance])
        }
    }

                        
                        
    def save_ch = {
        // println("in save_ch formid: ${params.formid}")
        def cancerHistoryInstance = new CancerHistory(params)
        /* if (cancerHistoryInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.created.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), cancerHistoryInstance.id])}"
        // redirect(action: "show", id: cancerHistoryInstance.id)
           
        redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])

        }
        else {
        //render(view: "create", model: [cancerHistoryInstance: cancerHistoryInstance])
        flash.message ="data not saved"
        redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
        }*/
         def medicalHistory = MedicalHistory.findById(params.medicalHistory.id)
        def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
        
           
        try{
            if(params.ptsPct){
                def newVocab = new CVocabRecord()
                newVocab.cVocabUserSelection = encode(params.ptsPct)
                newVocab.cVocabVer = params.ptsVer
                newVocab.icdCd = params.ptsIcdCd
                newVocab.srcDef = params.ptsSrcDef
                newVocab.cVocabType = CVocabType.findByCode("PCT")
                newVocab.cVocabId=params.ptsId
                newVocab.pdqCd = params.ptsPdqcd
                def cuiCS = params.ptsCui
                if (cuiCS!="") {             
                        String [] cuis = cuiCS.split(",")
                        cuis.each {
                            def  cuiRec = new CVocabCUIRecord()
                            cuiRec.cui = it.toString()
                            newVocab.addToCuiList(cuiRec) 
                           
                        }
                    }
                newVocab.save(failOnError:true, flush: true)
                cancerHistoryInstance.primaryTumorSiteCvocab=newVocab
                cancerHistoryInstance.primaryTumorSite = encode(params.ptsPct)
                
            }
            
            cancerHistoryInstance.save(failOnError:true, flush: true)
            
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'cancerHistory.label', default: 'Cancer History for '), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
        }catch(Exception e){
            flash.message = "Failed: " + e.toString()
            redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
        }
        
    }

    def show = {
        def cancerHistoryInstance = CancerHistory.get(params.id)
        if (!cancerHistoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), params.id])}"
            redirect(action: "list")
        }
        else {
            [cancerHistoryInstance: cancerHistoryInstance]
        }
    }

    def edit = {
        def cancerHistoryInstance = CancerHistory.get(params.id)
        def  show45VersionUpdates
        def show50VersionUpdates
        
        if (!cancerHistoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), params.id])}"
            redirect(action: "list")
        }
        else {
              
            def medicalHistory = cancerHistoryInstance.medicalHistory
        
            def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
            def caseRecord = caseReportFormInstance?.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            def primaryTumorSite = cancerHistoryInstance.primaryTumorSite
            def monthYearOfFirstDiagnosis = cancerHistoryInstance.monthYearOfFirstDiagnosis
            def treatments = cancerHistoryInstance.treatments
            def monthYearOfLastTreatment = cancerHistoryInstance.monthYearOfLastTreatment
            def medicalRecordExist = cancerHistoryInstance.medicalRecordExist
            def treatmentOther = cancerHistoryInstance.treatmentOther
            def otherTreatment = cancerHistoryInstance.otherTreatment
            def caseReportForm = CaseReportForm.findById(params.formid)
            def cdrVer = caseReportForm.caseRecord.cdrVer
             
            //if (!monthYearOfFirstDiagnosis && cdrVer?.compareTo("2.5") >= 0){
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') < 0){            
                if (!monthYearOfFirstDiagnosis)
                {
                    def str = "The month/year of first diagnosis is a required field"
                    cancerHistoryInstance.errors.rejectValue("monthYearOfFirstDiagnosis", str)
                }
                
            }
            if(treatments=='null' && !treatmentOther){
                //println("error is here!!!")
                def str = "The history of any treatments is a required field"
                cancerHistoryInstance.errors.rejectValue("treatments", str)
            }
                     
            if(treatmentOther && !otherTreatment){
                def str = "The other treatment is a required field";
                cancerHistoryInstance.errors.rejectValue("otherTreatment", str)
                    
                         
                        
            }
            
            // PMH HUB-CR-31 03/07/13
            
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') < 0){
                    
                if(!monthYearOfLastTreatment ){
                    
                    def str = "The date of last radiation or chemotherapy treatment is a required field"
                    cancerHistoryInstance.errors.rejectValue("monthYearOfLastTreatment", str)
                }
            }
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') >= 0){
            
                def treatmentRadiation = cancerHistoryInstance.treatmentRadiation
                def treatmentChemotherapy = cancerHistoryInstance.treatmentChemotherapy
                
                if((treatmentRadiation || treatmentChemotherapy) && !monthYearOfLastTreatment ){
                    
                    if (!monthYearOfLastTreatment)
                    {
                        def str = " The date of last radiation or chemotherapy treatment is a required field"
                        cancerHistoryInstance.errors.rejectValue("monthYearOfLastTreatment", str)
                    }
                }
                
                if(cancerHistoryInstance.source?.trim() == null){
                    def str = "The History Source is a required field" 
                    cancerHistoryInstance.errors.rejectValue("source", str)
                }
                
            }
            
            if (monthYearOfFirstDiagnosis && monthYearOfLastTreatment)
            {
                if (!monthYearOfFirstDiagnosis.toString().startsWith("1900-01") && !monthYearOfLastTreatment.toString().startsWith("1900-01"))
                {
                    if (monthYearOfFirstDiagnosis.toString().compareTo(monthYearOfLastTreatment.toString()) > 0)
                    {
                        def str = "The first diagnosis cannot be later than the date of last radiation or chemotherapy treatment."
                        cancerHistoryInstance.errors.rejectValue("monthYearOfLastTreatment", str)
                    }
                }
            }
            
            
            // end PMH
                    
            if(!medicalRecordExist){
                def str = "Please specify if there is medical record documentation of this history of cancer and treatment"
                cancerHistoryInstance.errors.rejectValue("medicalRecordExist", str)
                    
                       
            }
            
            //pmh hub-cr-42
            
            show45VersionUpdates = true
            
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '4.5') < 0){
                show45VersionUpdates = false
            }
            
            
            show50VersionUpdates = true
            if(ComputeMethods.compareCDRVersion(caseReportForm.caseRecord.cdrVer, '5.0') < 0){
            show50VersionUpdates = false
             
            }
                 
        }
        
            
        
        
        // return [cancerHistoryInstance: cancerHistoryInstance]
        return [cancerHistoryInstance: cancerHistoryInstance,show45VersionUpdates:show45VersionUpdates, show50VersionUpdates:show50VersionUpdates]
        
    }

    def update = {
        def cancerHistoryInstance = CancerHistory.get(params.id)
         def medicalHistory = MedicalHistory.findById(params.medicalHistory.id)
        def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
           
        if (cancerHistoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (cancerHistoryInstance.version > version) {
                    
                    cancerHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'cancerHistory.label', default: 'CancerHistory')] as Object[], "Another user has updated this CancerHistory while you were editing")
                    render(view: "edit", model: [cancerHistoryInstance: cancerHistoryInstance])
                    return
                }
            }
            cancerHistoryInstance.properties = params
            /*if (!cancerHistoryInstance.hasErrors() && cancerHistoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), cancerHistoryInstance.id])}"
            // redirect(action: "show", id: cancerHistoryInstance.id)
            redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
            else {
            flash.message = 'data not saved'
            // render(view: "edit", model: [cancerHistoryInstance: cancerHistoryInstance])
            redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
            }*/
            
            try{
               
                if(params.ptsPct){
                    
                    def newVocab = cancerHistoryInstance.primaryTumorSiteCvocab
                    if(newVocab && newVocab.cVocabUserSelection != encode(params.ptsPct)){
                        if(!newVocab){
                            newVocab =  new CVocabRecord();
                            cancerHistoryInstance.primaryTumorSiteCvocab=newVocab
                        }
                         newVocab.cuiList = null


                        newVocab.cVocabUserSelection =encode(params.ptsPct)
                        newVocab.cVocabVer = params.ptsVer
                        newVocab.icdCd = params.ptsIcdCd
                        newVocab.srcDef = params.ptsSrcDef
                        newVocab.cVocabType = CVocabType.findByCode("PCT")
                        newVocab.cVocabId=params.ptsId
                        newVocab.pdqCd = params.ptsPdqcd
                        def cuiCS = params.ptsCui
                        if (cuiCS!="") {             
                                String [] cuis = cuiCS.split(",")
                                cuis.each {
                                    def  cuiRec = new CVocabCUIRecord()
                                    cuiRec.cui = it.toString()
                                    newVocab.addToCuiList(cuiRec) 

                                }
                            }

                         newVocab.save(failOnError:true, flush: true)

                        cancerHistoryInstance.primaryTumorSite = encode(params.ptsPct)
                    }
                
            }
                
                
                
                cancerHistoryInstance.save(failOnError:true, flush: true)
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'cancerHistory.label', default: 'Cancer History for '), caseReportFormInstance?.caseRecord?.caseId])}"
                redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), params.id])}"
            redirect(action: "list")
        }
    }

    
     String encode(str){
      
       if(!str)
          retunr str 
       
         str =str.replaceAll(">", "&gt;")
         str = str.replaceAll("<", "&lt;")
       if(str.indexOf('&amp;')>=0){
            str=str.replace("&amp;", "&")
            encode(str)
       }else{
            return str;
       }
      
       
        
   }
    
    
    def delete = {
        def cancerHistoryInstance = CancerHistory.get(params.id)
        if (cancerHistoryInstance) {
            try {
                cancerHistoryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), params.id])}"
                //redirect(action: "list")
                redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Failed: " + e.toString()
                // redirect(action: "show", id: params.id)
                redirect(action:"list_ch", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cancerHistory.label', default: 'CancerHistory'), params.id])}"
            redirect(action: "list")
        }
    }
}
