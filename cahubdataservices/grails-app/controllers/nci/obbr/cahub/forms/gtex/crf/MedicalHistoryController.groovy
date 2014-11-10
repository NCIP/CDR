package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.util.ComputeMethods

class MedicalHistoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [medicalHistoryInstanceList: MedicalHistory.list(params), medicalHistoryInstanceTotal: MedicalHistory.count()]
    }

    def create = {
        def medicalHistoryInstance = new MedicalHistory()
        medicalHistoryInstance.properties = params
        return [medicalHistoryInstance: medicalHistoryInstance]
    }

    def save = {
        def medicalHistoryInstance = new MedicalHistory(params)
        if (medicalHistoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), medicalHistoryInstance.id])}"
            redirect(action: "show", id: medicalHistoryInstance.id)
        }
        else {
            render(view: "create", model: [medicalHistoryInstance: medicalHistoryInstance])
        }
    }

    def show = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (!medicalHistoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
            redirect(action: "list")
        }
        else {
            [medicalHistoryInstance: medicalHistoryInstance]
        }
    }

    def edit = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (!medicalHistoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
            redirect(action: "list")
        }
        else {
            def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistoryInstance)
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
            
            def version = medicalHistoryInstance.version
            if(version == 0){
                def errorMap=[:]
                return [medicalHistoryInstance: medicalHistoryInstance, errorMap:errorMap]
            }else{
                def errorMap=[:]
                def caseReportForm = CaseReportForm.findById(params.formid)
                def cdrVer = caseReportForm.caseRecord.cdrVer
                //def result= checkError(medicalHistoryInstance, cdrVer)
                def result= checkError(medicalHistoryInstance, cdrVer)
                if(result){
                    int numg = 0;
                    int numg_s = 0;
                    int numc=0;
                    result.each(){key,value->
                        if(!key.startsWith('generalMedicalHistories') && !key.startsWith('s_generalMedicalHistories') && !key.startsWith('cancerHistories') ){
                            medicalHistoryInstance.errors.rejectValue(key, value)
                        }else if(key.startsWith('generalMedicalHistories') && numg==0){
                            // println("I am here...")
                            medicalHistoryInstance.errors.rejectValue('generalMedicalHistories', value)
                            // medicalHistoryInstance.errors.reject(key, value);
                            numg++;
                        }else if(key.startsWith('generalMedicalHistories') && numg > 0){
                            // println("something here??????")
                            medicalHistoryInstance.errors.reject(key, value);
                            numg++;
                        }else if(key.startsWith('s_generalMedicalHistories') && numg_s==0){
                            //  println("put_errors......")
                            errorMap.put("s_generalMedicalHistories", "errors")
                            medicalHistoryInstance.errors.reject(key, value);
                            numg_s++;
                        }else if(key.startsWith('s_generalMedicalHistories') && numg_s >0){
                            medicalHistoryInstance.errors.reject(key, value);
                            numg_s++;
                        }else if (key.startsWith('cancerHistories') && numc==0){
                            medicalHistoryInstance.errors.rejectValue('cancerHistories', value)
                            //  medicalHistoryInstance.errors.reject(key, value);
                            numc++;
                        }else if(key.startsWith('cancerHistories') && numc >0){
                            medicalHistoryInstance.errors.reject(key, value);
                            numc++;
                        }else{
                                   
                        }
                    }
                    return [medicalHistoryInstance: medicalHistoryInstance, errorMap:errorMap]
                }else{
                    return [medicalHistoryInstance: medicalHistoryInstance, errorMap:errorMap]
                }
           
            }
           
        }
    }

    def update = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (medicalHistoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (medicalHistoryInstance.version > version) {
                    
                    medicalHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'medicalHistory.label', default: 'MedicalHistory')] as Object[], "Another user has updated this MedicalHistory while you were editing")
                    render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                    return
                }
            }
            medicalHistoryInstance.properties = params
            def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistoryInstance)
           
            
            /* if (!medicalHistoryInstance.hasErrors() && medicalHistoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), medicalHistoryInstance.id])}"
            //redirect(action: "show", id: medicalHistoryInstance.id)
                 
                
            redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
            }
            else {
            flash.message="data not saved"
            if(result){
            result.each{
            demographicsInstance.errors.reject(it, it)
            }
            }
            //render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
            redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
            }*/
            
            try{
                medicalHistoryInstance.save(failOnError:true,flush: true)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory for '), caseReportFormInstance?.caseRecord?.caseId])}"
                redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
                    
            }
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory for '), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    
    
    def update_ch = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (medicalHistoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (medicalHistoryInstance.version > version) {
                    
                    medicalHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'medicalHistory.label', default: 'MedicalHistory')] as Object[], "Another user has updated this MedicalHistory while you were editing")
                    render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                    return
                }
            }
            medicalHistoryInstance.properties = params
            def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistoryInstance)
            //caseReportFormInstance?.caseRecord?.caseId
            
            if (!medicalHistoryInstance.hasErrors() && medicalHistoryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory for '), caseReportFormInstance?.caseRecord?.caseId])}"
                //redirect(action: "show", id: medicalHistoryInstance.id)
                 
                
                redirect(controller:"cancerHistory", action:"list_ch", params:[mid: medicalHistoryInstance.id, formid:params.formid])
            }
            else {
                flash.message="Data not saved"
                
                //render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                // redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
                redirect(controller:"cancerHistory",action:"list_ch", params:[mid: medicalHistoryInstance.id, formid:params.formid])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    def update_g = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (medicalHistoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (medicalHistoryInstance.version > version) {
                    
                    medicalHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'medicalHistory.label', default: 'MedicalHistory')] as Object[], "Another user has updated this MedicalHistory while you were editing")
                    render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                    return
                }
            }
            medicalHistoryInstance.properties = params
             def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistoryInstance)
            
            if (!medicalHistoryInstance.hasErrors() && medicalHistoryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory for '), caseReportFormInstance?.caseRecord?.caseId])}"
                //redirect(action: "show", id: medicalHistoryInstance.id)
                 
                
                redirect(controller:"generalMedicalHistory", action:"create_g", params:[mid: medicalHistoryInstance.id, formid:params.formid])
            }
            else {
                flash.message="Data not saved"
               
                //render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                // redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
                redirect(controller:"generalMedicalHistory",action:"create_g", params:[mid: medicalHistoryInstance.id, formid:params.formid])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    def update_sg = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (medicalHistoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (medicalHistoryInstance.version > version) {
                    
                    medicalHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'medicalHistory.label', default: 'MedicalHistory')] as Object[], "Another user has updated this MedicalHistory while you were editing")
                    render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                    return
                }
            }
            medicalHistoryInstance.properties = params
             def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistoryInstance)
             
            if (!medicalHistoryInstance.hasErrors() && medicalHistoryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory for '), caseReportFormInstance?.caseRecord?.caseId])}"
                //redirect(action: "show", id: medicalHistoryInstance.id)
                 
                
                redirect(controller:"generalMedicalHistory", action:"create_sg", params:[mid: medicalHistoryInstance.id, formid:params.formid])
            }
            else {
                flash.message="Data not saved."
                
                //render(view: "edit", model: [medicalHistoryInstance: medicalHistoryInstance])
                // redirect(action:"edit", params:[id: medicalHistoryInstance.id, formid:params.formid])
                redirect(controller:"generalMedicalHistory",action:"create_sg", params:[mid: medicalHistoryInstance.id, formid:params.formid])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    static Map checkError(medicalHistoryInstance, cdrVer){
        
        // println("somebody call me....")
        def computeMethods = new ComputeMethods()
        def result = [:]
      
          
        def source = medicalHistoryInstance.source
        if(!source){
             
            result.put("source", "The primary history source is a required field.")
        }
         
        
        if(source && source.toString() == 'Family Report'){
            def primary = medicalHistoryInstance.primary
            if(!primary){
                
                result.put("primary", "The primary individual is a required field.")
            }
        }
        
        def primary = medicalHistoryInstance.primary
        if(primary && primary.toString() == 'Other'){
            def otherPrimary = medicalHistoryInstance.otherPrimary
            if(!otherPrimary){
                
                result.put("otherPrimary", "Other primary is a required field.")
            }
        }
          
        def nonMetastaticCancer = medicalHistoryInstance.nonMetastaticCancer
        if(!nonMetastaticCancer){
             
            result.put("nonMetastaticCancer", "Please specify if there is a history of non-metastatic cancer.")
        }
         
      
        
        if(nonMetastaticCancer && nonMetastaticCancer.toString() == 'Yes'){
            //println("here???")
            def cancerHistories = medicalHistoryInstance.cancerHistories
            if(cancerHistories.size() == 0){
                
                result.put("cancerHistories","Please fill in the cancer history form.")
            }else{
                
                for(c in cancerHistories){
                    def primaryTumorSite = c.primaryTumorSite
                    def monthYearOfFirstDiagnosis = c.monthYearOfFirstDiagnosis
                    def treatments = c.treatments
                    def monthYearOfLastTreatment = c.monthYearOfLastTreatment
                    def medicalRecordExist = c.medicalRecordExist
                    def treatmentOther = c.treatmentOther
                    def otherTreatment = c.otherTreatment
                    def treatmentRadiation = c.treatmentRadiation
                    def treatmentChemotherapy = c.treatmentChemotherapy
                   
                    if (!monthYearOfFirstDiagnosis && computeMethods.compareCDRVersion(cdrVer, '4.5') < 0 && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                        result.put("cancerHistories" + c.id + "monthYearOfFirstDiagnosis", "In Cancer History form the month/year of first diagnosis is a required field for " + primaryTumorSite )
                    }
                    if(treatments=='null' && !treatmentOther){
                        result.put("cancerHistories" + c.id + "treatments", "In Cancer History form the history of any treatments is a required field for " + primaryTumorSite )
                    }
                     
                    if(treatmentOther && !otherTreatment){
                        result.put("cancerHistories" + c.id + "otherTreatment", "In Cancer History form the other treatment is a required field for " + primaryTumorSite )
                    }
                    
                    if(!monthYearOfLastTreatment && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0 && computeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                        result.put("cancerHistories" + c.id + "monthYearOfLastTreatment", "In Cancer History form the date of last radiation or chemotherapy treatment is a required field for " + primaryTumorSite )
                    }
                    if((treatmentRadiation || treatmentChemotherapy) && !monthYearOfLastTreatment &&  computeMethods.compareCDRVersion(cdrVer, '4.5') >= 0  ){
                        result.put("cancerHistories" + c.id + "monthYearOfLastTreatment", "In Cancer History form the date of last radiation or chemotherapy treatment is a required field for " + primaryTumorSite )
                    }
                    
                    if(monthYearOfFirstDiagnosis && monthYearOfLastTreatment) {
                        if (!monthYearOfFirstDiagnosis.toString().startsWith("1900-01") && !monthYearOfLastTreatment.toString().startsWith("1900-01"))
                        {
                            if (monthYearOfFirstDiagnosis.toString().compareTo(monthYearOfLastTreatment.toString()) > 0)
                            {
                                result.put("cancerHistories" + c.id + "monthYearOfLastTreatment", "The first diagnosis cannot be later than the date of last radiation or chemotherapy treatment." )
                            }
                        }
                    }
                    
                    if(!medicalRecordExist){
                        result.put("cancerHistories" + c.id + "medicalRecordExist", "In Cancer History form please specify if there is medical record documentation of this history of cancer and treatment for " + primaryTumorSite )
                    }
                 
                }
                
            }
        }
        
        
        def list1 = GeneralMedicalHistory.findAllByDisplayOrderIsNotNullAndMedicalHistory(medicalHistoryInstance)
        boolean needCheck = false
        for(g in list1){
            def version = g.version
            if(version != 0){
                needCheck = true
            }
           
        }
        if(needCheck){
            for(g in list1){
                def medicalCondition =  g.medicalCondition
                def option = g.chooseOption
                if(!option){
                    result.put("generalMedicalHistories" + g.rown, "In general medical history form the choose option field is required for " + medicalCondition + "(row: " + g.rown +").")
                  
                }
               
                def yearOfOnset = g.yearOfOnset
                def treatment = g.treatment
                def medicalRecord = g.medicalRecord
               
                if(yearOfOnset && !yearOfOnset.isInteger()){
                    result.put("generalMedicalHistories" + g.rown + "year", "The year of onset must be an integer for " + medicalCondition + "(row: " + g.rown +").")
                  
                   
                }
                
                def cal = Calendar.instance
                def curYear = cal.get(Calendar.YEAR)
                
                if( yearOfOnset && yearOfOnset.isInteger() && ( Integer.parseInt(yearOfOnset) < 1900 ||Integer.parseInt(yearOfOnset) > curYear)){
                    def str = "The year of onset must be a valid entry between 1900 - "+curYear+" for " + medicalCondition + "(row: " + g.rown +")."
                    result.put("generalMedicalHistories" + g.rown + "year", str) 
                }
                  
                // println("option: " + option + " yearOfOnset: " + yearOfOnset)
                if(option?.toString() =='Yes'){
                    // println("first caught....")
                    
                    if(yearOfOnset == null && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0 ){
                        //println("caught.....")
                        result.put("generalMedicalHistories" + g.rown + "year", "The year of onset field is required for " + medicalCondition + "(row: " + g.rown +").")
                    }
                    
                    if(!treatment && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0 ){
                        result.put("generalMedicalHistories" + g.rown + "treatment", "The history of treatment field is required for " + medicalCondition + "(row: " + g.rown +").")
                    }
                    
                    if(!medicalRecord && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0 && computeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                        result.put("generalMedicalHistories" + g.rown + "medicalRecord", "The medical record documentation field is required for " + medicalCondition + "(row: " + g.rown +").")
                    }
                }

            }
              
         
                
            
            
        }else{
            result.put("generalMedicalHistories", "Please fill in the general medical history form")
        }
        
        
        def list2 = GeneralMedicalHistory.findAllByDisplayOrderIsNullAndMedicalHistory(medicalHistoryInstance)
        for(g in list2){
            def medicalCondition =  g.medicalCondition
            def yearOfOnset = g.yearOfOnset
            def treatment = g.treatment
            def medicalRecord = g.medicalRecord
            if(yearOfOnset && !yearOfOnset.isInteger()){
                result.put("s_generalMedicalHistories" + g.rown + "year", "The year of onset must be an integer for " + medicalCondition)
                       
            }
                    
            def cal = Calendar.instance
            def curYear = cal.get(Calendar.YEAR)
                
            if( yearOfOnset && yearOfOnset.isInteger() && ( Integer.parseInt(yearOfOnset) < 1900 ||Integer.parseInt(yearOfOnset) > curYear)){
                def str = "The year of onset must be a valid entry between 1900 - "+curYear+" for " + medicalCondition + "(row: " + g.rown +")."
                //println("something ,,,,,,")
                result.put("s_generalMedicalHistories" + g.rown + "year", str) 
            }
                  
            if(yearOfOnset == null && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                //println("caught.....")
                result.put("s_generalMedicalHistories" + g.rown + "year", "The year of onset field is required for " + medicalCondition )
            }
                    
            if(!treatment && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0){
                result.put("s_generalMedicalHistories" + g.rown + "treatment", "The history of treatment field is required for " + medicalCondition )
            }
                    
            if(!medicalRecord && computeMethods.compareCDRVersion(cdrVer, '2.5') >= 0 && computeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
                result.put("s_generalMedicalHistories" + g.rown + "medicalRecord", "The medical record documentation field is required for " + medicalCondition)
            }
                   
        }
                     
        
        return result
    }
    
    def delete = {
        def medicalHistoryInstance = MedicalHistory.get(params.id)
        if (medicalHistoryInstance) {
            try {
                medicalHistoryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'medicalHistory.label', default: 'MedicalHistory'), params.id])}"
            redirect(action: "list")
        }
    }
}
