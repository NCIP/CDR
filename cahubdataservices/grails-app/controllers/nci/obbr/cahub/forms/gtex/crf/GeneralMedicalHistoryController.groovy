package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.staticmembers.CVocabType
import nci.obbr.cahub.datarecords.vocab.*
import nci.obbr.cahub.util.ComputeMethods

class GeneralMedicalHistoryController  {

    def caseReportFormService
    def accessPrivilegeService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
   

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        [generalMedicalHistoryInstanceList: GeneralMedicalHistory.list(params), generalMedicalHistoryInstanceTotal: GeneralMedicalHistory.count()]
         
    }
    
    
    def create_sg = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        //println("mid: ${params.mid}")
        // [generalMedicalHistoryInstanceList: GeneralMedicalHistory.list(params), generalMedicalHistoryInstanceTotal: GeneralMedicalHistory.count()]
        // def list1 = GeneralMedicalHistory.findAllByDisplayOrderIsNull()
      
        def medicalHistory = MedicalHistory.findById(params.mid)
        def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
        def caseRecord = caseReportFormInstance?.caseRecord
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
        // def list1 = GeneralMedicalHistory.findByMedicalCondition('Ischemic Heart Disease')
        def list1 = GeneralMedicalHistory.findAllByDisplayOrderIsNullAndMedicalHistory(medicalHistory)
        
        def caseReportForm = CaseReportForm.findById(params.formid)
        def cdrVer = caseReportForm.caseRecord.cdrVer
        def defaultSource=caseReportFormInstance.medicalHistory.source
        //pmh
        def  show45VersionUpdates = true
        def  show25VersionUpdates = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            show45VersionUpdates = false
        }
        if(ComputeMethods.compareCDRVersion(cdrVer, '2.5') < 0){
            show25VersionUpdates = false
        }
        //end pmh
        
        def errorMap=[:]
       
        for(g in list1){
            def medicalCondition =  g.medicalCondition
              
               
            def yearOfOnset = g.yearOfOnset
            def treatment = g.treatment
            def medicalRecord = g.medicalRecord
            def source = g.source?.trim()
            
            
            def cal = Calendar.instance
            def curYear = cal.get(Calendar.YEAR)
               
            if(yearOfOnset && !yearOfOnset.isInteger()){
                def str = "The year of onset must be an integer for " + medicalCondition
                g.errors.reject(str, str)
                errorMap.put(g.id, "errors")
            }
            if( yearOfOnset && yearOfOnset.isInteger() && ( Integer.parseInt(yearOfOnset) < 1900 ||Integer.parseInt(yearOfOnset) > curYear)){
                def str = "The year of onset must be a valid entry between 1900 - "+curYear+" for " + medicalCondition 
                g.errors.reject(str, str)
                errorMap.put(g.id, "errors")
            }
                
            if(yearOfOnset == null && show25VersionUpdates){
                // println("caught.....")
                def str = "The year of onset field is required for " + medicalCondition
                g.errors.reject(str, str)
                errorMap.put(g.id, "errors")
            }
                    
            if(!treatment && show25VersionUpdates){
                def str="The history of treatment field is required for " + medicalCondition
                g.errors.reject(str, str)
                errorMap.put(g.id, "errors")
            }
                    
            if(!medicalRecord && show25VersionUpdates && !show45VersionUpdates){
                def str="The medical record documentation field is required for " + medicalCondition
                g.errors.reject(str, str)
                errorMap.put(g.id, "errors")
                 
            }
            if(!source && show45VersionUpdates){
                def str="The History Source field is required for " + medicalCondition
                g.errors.reject(str, str)
                errorMap.put(g.id, "errors")
                 
            }
                

        }//for g in list
        
        // println(" in create_sg list1 size: ${list1.size()}")
        [generalMedicalHistoryInstanceList: list1, generalMedicalHistoryInstanceTotal: list1.size(), mid:params.mid, errorMap:errorMap,show45VersionUpdates:show45VersionUpdates,defaultSource:defaultSource]
    }
    
    def create_g = {
        def errorMap = [:]
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        //  println("in crate_g mid: ${params.mid}")
        def medicalHistory = MedicalHistory.findById(params.mid)
        
        def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
        def caseRecord = caseReportFormInstance?.caseRecord
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
        
        
        def caseReportForm = CaseReportForm.findById(params.formid)
        def cdrVer = caseReportForm.caseRecord.cdrVer
                
        //pmh hub-cr-37
        def  show45VersionUpdates = true
        def  show25VersionUpdates = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            show45VersionUpdates = false
        }
        if(ComputeMethods.compareCDRVersion(cdrVer, '2.5') < 0){
            show25VersionUpdates = false
        }
        //end pmh
         
        def list0 = GeneralMedicalHistory.findAllByDisplayOrderIsNotNullAndMedicalHistory(medicalHistory).sort{it.id}
        
        //pmh vocab: 04/03/13 update the code for each medical condition
        def list1=caseReportFormService.updateMedicalConditionCodeVal(list0)
        
        boolean needCheck = false
        for(g in list1){
            def version = g.version
            //if(version != 0){
            if(version > 1 ){
                needCheck = true
            }
           
        }
        if(needCheck){
            for(g in list1){
               
                def medicalCondition =  g.medicalCondition
                def option = g.chooseOption
                if(!option){
                    def str = "The choose option field is required for " + medicalCondition + "(row: " + g.rown +")."
                    g.errors.reject(str, str)
                    errorMap.put("option" + g.id, "errors")
                }
               
                def yearOfOnset = g.yearOfOnset
                def treatment = g.treatment
                def medicalRecord = g.medicalRecord
                def source= g.source?.trim()
               
                if(yearOfOnset && !yearOfOnset.isInteger()){
                    def str = "The year of onset must be an integer for " + medicalCondition + "(row: " + g.rown +")."
                   
                    g.errors.reject(str, str)
                    errorMap.put("year" + g.id, "errors")
                }
                
                //PH 08/06/12 CDRQA-14
                def cal = Calendar.instance
                def curYear = cal.get(Calendar.YEAR)
                
                if( yearOfOnset && yearOfOnset.isInteger() &&( Integer.parseInt(yearOfOnset) < 1900 ||Integer.parseInt(yearOfOnset) > curYear)){
                    // if( yearOfOnset &&( Integer.parseInt(yearOfOnset) < 1900 ||Integer.parseInt(yearOfOnset) > curYear)){
                    def str = "The year of onset must be a valid entry between 1900 - "+curYear+" for " + medicalCondition + "(row: " + g.rown +")."
                   
                    g.errors.reject(str, str)
                    errorMap.put("year" + g.id, "errors")
                    
                }
                //END PH 08/06/12 CDRQA-14
               
                
                if(option?.toString() =='Yes'){
                    // println("first caught....")
                    if(yearOfOnset == null && show25VersionUpdates){
                        // println("caught.....")
                        def str = "The year of onset field is required for " + medicalCondition + "(row: " + g.rown +")."
                        g.errors.reject(str, str)
                        errorMap.put("year" + g.id, "errors")
                    }
                    
                    if(!treatment && show25VersionUpdates){
                        def str="The history of treatment field is required for " + medicalCondition + "(row: " + g.rown +")."
                        g.errors.reject(str, str)
                        errorMap.put("treatment" + g.id, "errors")
                    }
                    
                    if(!medicalRecord && show25VersionUpdates && !show45VersionUpdates){
                        def str="The medical record documentation field is required for " + medicalCondition + "(row: " + g.rown +")."
                        g.errors.reject(str, str)
                        errorMap.put("medicalRecord" + g.id, "errors")
                 
                    }
                    
                    if(!source && show45VersionUpdates){
                        def str="The History Source field is required for " + medicalCondition + "(row: " + g.rown +")."
                        g.errors.reject(str, str)
                        errorMap.put("source" + g.id, "errors")
                 
                    }
                }

            }//for g in list
        }
        // println("so far so good list.cout ${list1.size()}")
        // [generalMedicalHistoryInstanceList: GeneralMedicalHistory.list(), generalMedicalHistoryInstanceTotal: GeneralMedicalHistory.count()]
        [generalMedicalHistoryInstanceList: list1, generalMedicalHistoryInstanceTotal: list1.size(), formid:params.formid, mid:params.mid, errorMap:errorMap,show45VersionUpdates:show45VersionUpdates]
        // [myList: list1]
    }

    def create = {
        def generalMedicalHistoryInstance = new GeneralMedicalHistory()
        generalMedicalHistoryInstance.properties = params
        return [generalMedicalHistoryInstance: generalMedicalHistoryInstance]
    }

    def save = {
        def generalMedicalHistoryInstance = new GeneralMedicalHistory(params)
        if (generalMedicalHistoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), generalMedicalHistoryInstance.id])}"
            redirect(action: "show", id: generalMedicalHistoryInstance.id)
        }
        else {
            render(view: "create", model: [generalMedicalHistoryInstance: generalMedicalHistoryInstance])
        }
    }

    
    def save_sg = {
        // println("before save... ${params.medicalHistory.id}")
        def generalMedicalHistoryInstance = new GeneralMedicalHistory(params)
        
        def caseReportForm = CaseReportForm.findById(params.formid)    
        def cdrVer = caseReportForm.caseRecord.cdrVer    
        def  version45 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            version45 = false
        }
//        CVocabRecord newVocab
//       if (version45) {
//            if (params.medicalConditionCdeId!="") {
//                if (generalMedicalHistoryInstance.medicalConditionCvocab!=null) {
//                    newVocab = generalMedicalHistoryInstance.medicalConditionCvocab
//                } else {
//                    newVocab = new CVocabRecord()
//                }
//
//                newVocab.cVocabId = params.medicalConditionCdeId
//                if (params.medicalConditionCdeTyp.equalsIgnoreCase("CDE")) {
//                    newVocab.cVocabType = CVocabType.findByCode("MEDCON")
//                }
//
//                newVocab.cVocabUserSelection = params.medicalConditionCdeCde
//                generalMedicalHistoryInstance.medicalCondition = params.medicalConditionCdeCde
//                newVocab.cVocabVer = params.medicalConditionCdeCvocabVer
//                newVocab.save(failOnError:true, flush: true)
//                generalMedicalHistoryInstance.medicalConditionCvocab = newVocab
//            }            
//       }


            CVocabRecord newVocab// = new CVocabRecord()
            CVocabCUIRecord cuiRec
            
       if (version45) {            
                if (params.medicalConditionCodId!="") {
                    if (generalMedicalHistoryInstance.medicalConditionCvocab!=null) {
                        newVocab = generalMedicalHistoryInstance.medicalConditionCvocab
                        generalMedicalHistoryInstance.medicalConditionCvocab.cuiList.each() {
                            cuiRec = it
                        }
                        //generalMedicalHistoryInstance.medicalConditionCvocab.cuiList = null
                    } else {
                        newVocab = new CVocabRecord()
                        cuiRec = new CVocabCUIRecord()
                    }

                    newVocab.cVocabId = params.medicalConditionCodId   
                   // if (params.medicalConditionCodTyp.equalsIgnoreCase("COD")) {
                        newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                   // }

                    newVocab.cVocabUserSelection = encode(params.medicalConditionCodCod)
                    //generalMedicalHistoryInstance.medicalCondition = params.medicalConditionCodCod
                    def cuiCS = params.medicalConditionCodCui
                    if (cuiCS!="") {
                        if (generalMedicalHistoryInstance.medicalConditionCvocab!=null) {
                            generalMedicalHistoryInstance.medicalConditionCvocab.cuiList = null
                        }                        
                        String [] cuis = cuiCS.split(",")
                        cuis.each {
                            cuiRec.cui = it.toString()
                            newVocab.addToCuiList(cuiRec) 
                            cuiRec = new CVocabCUIRecord()
                        }
                    }
                    newVocab.icdCd = params.medicalConditionCodIcdCd
                    newVocab.cVocabVer = params.medicalConditionCodCvocabVer
                          newVocab.srcDef=params.medicalConditionCodSrcDef
                    newVocab.save(failOnError:true, flush: true)
                    generalMedicalHistoryInstance.medicalConditionCvocab = newVocab
                    generalMedicalHistoryInstance.medicalCondition=encode(params.medicalConditionCodCod)
                }
            }


        
        // println("after object...")
        /*if (generalMedicalHistoryInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.created.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), generalMedicalHistoryInstance.id])}"
           
        //redirect(action: "show", id: generalMedicalHistoryInstance.id)
        redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])

            
        }
        else {
        flash.message="data not saved"
        redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
        // render(view: "create", model: [generalMedicalHistoryInstance: generalMedicalHistoryInstance])
          
        }*/
        try{
            generalMedicalHistoryInstance.save(failOnError:true, flush: true)
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory for '), caseReportForm.caseRecord.caseId])}"
            redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
        }catch(Exception e){
            flash.message = "Failed: " + e.toString()
            redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
        }
        
    }
    
    
  /**  String encode(str){
      
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
    def show = {
        def generalMedicalHistoryInstance = GeneralMedicalHistory.get(params.id)
        if (!generalMedicalHistoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), params.id])}"
            redirect(action: "list")
        }
        else {
            [generalMedicalHistoryInstance: generalMedicalHistoryInstance]
        }
    }

    def edit = {
        def generalMedicalHistoryInstance = GeneralMedicalHistory.get(params.id)
        if (!generalMedicalHistoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), params.id])}"
            redirect(action: "list")
        }
        else {
            
            def medicalHistory = generalMedicalHistoryInstance.medicalHistory
        
            def caseReportFormInstance=CaseReportForm.findByMedicalHistory(medicalHistory)
            def caseRecord = caseReportFormInstance?.caseRecord
            //pmh hub-cr-42
            def  show45VersionUpdates = true
            def defaultSource=caseReportFormInstance.medicalHistory.source
            if(ComputeMethods.compareCDRVersion(caseReportFormInstance.caseRecord.cdrVer, '4.5') < 0){
                show45VersionUpdates = false
            }
       
            
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            def medicalCondition =  generalMedicalHistoryInstance.medicalCondition
              
               
            def yearOfOnset = generalMedicalHistoryInstance.yearOfOnset
            def treatment = generalMedicalHistoryInstance.treatment
            def medicalRecord = generalMedicalHistoryInstance.medicalRecord
            
            def cal = Calendar.instance
            def curYear = cal.get(Calendar.YEAR)
            
            if(yearOfOnset && !yearOfOnset.isInteger()){
                def str = "The year of onset must be an integer"
                generalMedicalHistoryInstance.errors.rejectValue("yearOfOnset", str)
                    
            }
            
            if( yearOfOnset && yearOfOnset.isInteger() && ( Integer.parseInt(yearOfOnset) < 1900 ||Integer.parseInt(yearOfOnset) > curYear)){
                def str = "The year of onset must be a valid entry between 1900 - "+curYear 
                generalMedicalHistoryInstance.errors.rejectValue("yearOfOnset", str)
            }
                
            if(yearOfOnset == null){
                // println("caught.....")
                def str = "The year of onset field is required"
                generalMedicalHistoryInstance.errors.rejectValue("yearOfOnset", str)
                      
            }
                    
            if(!treatment){
                def str="The history of treatment field is required"
                generalMedicalHistoryInstance.errors.rejectValue("treatment", str)
                        
            }
                    
            if(!medicalRecord  && !show45VersionUpdates){
                def str="The medical record documentation field is required"
                generalMedicalHistoryInstance.errors.rejectValue("medicalRecord", str)
                 
            }
                
            
            
            return [generalMedicalHistoryInstance: generalMedicalHistoryInstance,show45VersionUpdates:show45VersionUpdates,defaultSource:defaultSource]
        }
    }

    def update = {
        def generalMedicalHistoryInstance = GeneralMedicalHistory.get(params.id)
        if (generalMedicalHistoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (generalMedicalHistoryInstance.version > version) {
                    
                    generalMedicalHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory')] as Object[], "Another user has updated this GeneralMedicalHistory while you were editing")
                    render(view: "edit", model: [generalMedicalHistoryInstance: generalMedicalHistoryInstance])
                    return
                }
            }
            generalMedicalHistoryInstance.properties = params
            
        def caseReportForm = CaseReportForm.findById(params.formid)    
        def cdrVer = caseReportForm.caseRecord.cdrVer    
        def  version45 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '4.5') < 0){
            version45 = false
        }
//        CVocabRecord newVocab
//       if (version45) {
//            if (params.medicalConditionCdeId!="") {
//                if (generalMedicalHistoryInstance.medicalConditionCvocab!=null) {
//                    newVocab = generalMedicalHistoryInstance.medicalConditionCvocab
//                } else {
//                    newVocab = new CVocabRecord()
//                }
//
//                newVocab.cVocabId = params.medicalConditionCdeId
//                if (params.medicalConditionCdeTyp.equalsIgnoreCase("CDE")) {
//                    newVocab.cVocabType = CVocabType.findByCode("MEDCON")
//                }
//
//                newVocab.cVocabUserSelection = params.medicalConditionCdeCde
//                generalMedicalHistoryInstance.medicalCondition = params.medicalConditionCdeCde
//                newVocab.cVocabVer = params.medicalConditionCdeCvocabVer
//                newVocab.save(failOnError:true, flush: true)
//                generalMedicalHistoryInstance.medicalConditionCvocab = newVocab
//            }            
//       }

            CVocabRecord newVocab// = new CVocabRecord()
            CVocabCUIRecord cuiRec
            
       if (version45) {            
                if (params.medicalConditionCodId!="" && encode(params.medicalConditionCodCod) !=generalMedicalHistoryInstance.medicalCondition) {
                    generalMedicalHistoryInstance.medicalCondition=params.medicalConditionCodCod
                    if (generalMedicalHistoryInstance.medicalConditionCvocab!=null) {
                        newVocab = generalMedicalHistoryInstance.medicalConditionCvocab
                        generalMedicalHistoryInstance.medicalConditionCvocab.cuiList.each() {
                            cuiRec = it
                        }
                        //generalMedicalHistoryInstance.medicalConditionCvocab.cuiList = null
                    } else {
                        newVocab = new CVocabRecord()
                        cuiRec = new CVocabCUIRecord()
                    }

                    newVocab.cVocabId = params.medicalConditionCodId   
                   // if (params.medicalConditionCodTyp.equalsIgnoreCase("COD")) {
                        newVocab.cVocabType = CVocabType.findByCode("MEDCON")
                   // }

                    newVocab.cVocabUserSelection = params.medicalConditionCodCod
                    generalMedicalHistoryInstance.medicalCondition = params.medicalConditionCodCod
                    def cuiCS = params.medicalConditionCodCui
                    if (cuiCS!="") {
                        if (generalMedicalHistoryInstance.medicalConditionCvocab!=null) {
                            generalMedicalHistoryInstance.medicalConditionCvocab.cuiList = null
                        }                        
                        String [] cuis = cuiCS.split(",")
                        cuis.each {
                            cuiRec.cui = it.toString()
                            newVocab.addToCuiList(cuiRec) 
                            cuiRec = new CVocabCUIRecord()
                        }
                    }
                    newVocab.icdCd = params.medicalConditionCodIcdCd
                    newVocab.cVocabVer = params.medicalConditionCodCvocabVer
                    newVocab.srcDef=params.medicalConditionCodSrcDef
                    newVocab.save(failOnError:true, flush: true)
                    generalMedicalHistoryInstance.medicalConditionCvocab = newVocab
                }
            }            
            
            /*  if (!generalMedicalHistoryInstance.hasErrors() && generalMedicalHistoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), generalMedicalHistoryInstance.id])}"
            // redirect(action: "show", id: generalMedicalHistoryInstance.id)
            // redirect(action:"edit", params:[id: generalMedicalHistoryInstance.id, formid:params.formid])
            redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
            else {
            flash.message ="data saved"
            // render(view: "edit", model: [generalMedicalHistoryInstance: generalMedicalHistoryInstance])
            // redirect(action:"edit", params:[id: generalMedicalHistoryInstance.id, formid:params.formid])
            redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
            }*/
            
            
            try{
                generalMedicalHistoryInstance.save(failOnError:true, flush: true)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory for '), caseReportForm.caseRecord.caseId])}"
                redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), params.id])}"
            redirect(action: "list")
        }
    }

 
    
 
    def update_g = {
        try{
            caseReportFormService.saveGeneralMedicalRecord(params)
             def caseReportForm = CaseReportForm.findById(params.formid) 
            flash.message = 'Data saved for '+caseReportForm.caseRecord.caseId
            redirect(action:"create_g", params:['mid':params.mid, formid:params.formid])
        }catch(Exception e){
            flash.message = "Failed: " + e.toString()
            redirect(action:"create_g", params:['mid':params.mid, formid:params.formid])
        }
    }
    
    def delete = {
        def generalMedicalHistoryInstance = GeneralMedicalHistory.get(params.id)
        if (generalMedicalHistoryInstance) {
            try {
                generalMedicalHistoryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), params.id])}"
                // redirect(action: "list")
                redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Failed: " + e.toString()
                
                //redirect(action: "show", id: params.id)
                redirect(action:"create_sg", params:[mid:params.medicalHistory.id, formid:params.formid])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory'), params.id])}"
            redirect(action: "list")
        }
    }
}
