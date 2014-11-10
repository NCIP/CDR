package nci.obbr.cahub.forms.gtex.crf
import grails.plugins.springsecurity.Secured

class SurgicalProceduresController {

    def caseReportFormService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [surgicalProceduresInstanceList: SurgicalProcedures.list(params), surgicalProceduresInstanceTotal: SurgicalProcedures.count()]
    }

    def create = {
        def surgicalProceduresInstance = new SurgicalProcedures()
        surgicalProceduresInstance.properties = params
        return [surgicalProceduresInstance: surgicalProceduresInstance]
    }

    def save = {
        def surgicalProceduresInstance = new SurgicalProcedures(params)
        if (surgicalProceduresInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), surgicalProceduresInstance.id])}"
            redirect(action: "show", id: surgicalProceduresInstance.id)
        }
        else {
            render(view: "create", model: [surgicalProceduresInstance: surgicalProceduresInstance])
        }
    }

    def show = {
        def surgicalProceduresInstance = SurgicalProcedures.get(params.id)
        if (!surgicalProceduresInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surgicalProceduresInstance: surgicalProceduresInstance]
        }
    }

    def edit = {
        def errorMap=[:]
        def surgicalProceduresInstance = SurgicalProcedures.get(params.id)
        def caseReportForm=CaseReportForm.findBySurgicalProcedures(surgicalProceduresInstance)
        def caseRecord = caseReportForm?.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//             if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
        
        
        def surgicalMedications = surgicalProceduresInstance.surgicalMedications
        
        List list =surgicalMedications.sort{it.id}
        
        def list1=[]
        def list2=[]
        def list3=[]
        def list4=[]
        def list5=[]
        def list6=[]
        def list7=[]
        def list8=[]
        def list9=[]
        def list10=[]
        def list11=[]
        
        def list12=[]
        def list13=[]
        def list14=[]
          
        //def s = surgicalProceduresInstance.get(1)
        
     /*  for(i in 0..surgicalMedications.size()) {
            def s = surgicalMedications.get(i)
            if(s.catgory == 'Pre-Operative Medications' && s.subCatgory=='intravenous (IV) sedation administered' ){
                
                list1.add(s)
            }
             if(s.catgory == 'Pre-Operative Medications' && s.subCatgory=='IV Opiate administered' ){
                
                list2.add(s)
            }
            
            if(s.catgory == 'Pre-Operative Medications' && s.subCatgory=='IV Antiemetic administered' ){
                
                list3.add(s)
            }
            
             if(s.catgory == 'Pre-Operative Medications' && s.subCatgory=='IV Antacid administered' ){
                
                list4.add(s)
            }
            
            if(s.catgory == 'Pre-Operative Anesthesia' && s.subCatgory=='local anesthesia agents administered' ){
                
                list5.add(s)
            }
            
              if(s.catgory == 'Pre-Operative Anesthesia' && s.subCatgory=='regional (spinal/epidural) anesthesia agents  administered' ){
                
                list6.add(s)
            }
            
              if(s.catgory == 'Pre-Operative Anesthesia' && s.subCatgory=='IV anesthetic administered' ){
                
                list7.add(s)
            }
            
              if(s.catgory == 'Pre-Operative Anesthesia' && s.subCatgory=='IV narcotic / opiate anesthetic administered' ){
                
                list8.add(s)
            }
            
              if(s.catgory == 'Pre-Operative Anesthesia' && s.subCatgory=='IV muscle relaxant administered' ){
                
                list9.add(s)
            }
            
             if(s.catgory == 'Pre-Operative Anesthesia' && s.subCatgory=='Inhalation anesthetic administered' ){
                
                list10.add(s)
            }
            
              if(s.catgory == 'during surgery' && s.subCatgory ){
                
                list11.add(s)
            }
            
              if(s.catgory == 'Pre-Operative Medications' && !s.subCatgory ){
                
                list12.add(s)
            }
            
            
               if(s.catgory == 'Pre-Operative Anesthesia' && !s.subCatgory ){
                
                list13.add(s)
            }
            
              if(s.catgory == 'during surgery' && !s.subCatgory ){
                
                list14.add(s)
            }
        }*/
        
       list.each() {
             //println("id: ${it.id}")
            if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='intravenous (IV) sedation administered' ){
                
                list1.add(it)
            }
             if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='IV Opiate administered' ){
                
                list2.add(it)
            }
            
            if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='IV Antiemetic administered' ){
                
                list3.add(it)
            }
            
             if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='IV Antacid administered' ){
                
                list4.add(it)
            }
            
            if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='local anesthesia agents administered' ){
                
                list5.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='regional (spinal/epidural) anesthesia agents  administered' ){
                
                list6.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='IV anesthetic administered' ){
                
                list7.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='IV narcotic / opiate anesthetic administered' ){
                
                list8.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='IV muscle relaxant administered' ){
                
                list9.add(it)
            }
            
             if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='Inhalation anesthetic administered' ){
                
                list10.add(it)
            }
            
              if(it.catgory == 'during surgery' && it.subCatgory ){
                
                list11.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Medications' && !it.subCatgory ){
                
                list12.add(it)
            }
            
            
               if(it.catgory == 'Pre-Operative Anesthesia' && !it.subCatgory ){
                
                list13.add(it)
            }
            
              if(it.catgory == 'during surgery' && !it.subCatgory ){
                
                list14.add(it)
            }
        }
        /*list1.sort{it.rown}
        list2.sort{it.rown}
        list3.sort{it.rown}
        list4.sort{it.rown}
        list5.sort{it.rown}
        list6.sort{it.rown}
        list7.sort{it.rown}
        list8.sort{it.rown}
        list9.sort{it.rown}
        list10.sort{it.rown}
        list11.sort{it.rown}
        list12.sort{it.id}
        list13.sort{it.id}
        list14.sort{it.id}*/
        
         int rowid = 1
         list1.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
          list2.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
          list3.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
          list4.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
          list12.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
         
         def durationId2=++rowid;
           list5.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
           list6.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
         
          list7.each() {
             it.rowid = ++rowid
            //println("rowid ${it.rowid}")
         }
         
          list8.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
         list9.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
         list10.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
         
         list13.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
         
         list11.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
         
          list14.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
        
        //println("list14 size: ${list14.size()}")
        if (!surgicalProceduresInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), params.id])}"
            redirect(action: "list")
        }
        else {
            if(surgicalProceduresInstance.version > 0){
              def result= checkError(surgicalProceduresInstance, 1, list1, list2, list3, list4, list12,durationId2, list5, list6, list7, list8, list9, list10, list13, list11,list14 )
                      if(result){
                          result.each(){key, value->
                             // println("it: " + it)
                            surgicalProceduresInstance.errors.reject(key, value)
                            errorMap.put(key, "errors")
                         }//each
                          return [surgicalProceduresInstance: surgicalProceduresInstance, list1:list1, list2:list2, list3:list3, list4:list4, list5:list5, list6:list6, list7:list7, list8:list8, list9:list9, list10:list10, list11:list11, list12:list12, list13:list13, list14:list14, durationId1:1, durationId2:durationId2, formid:params.formid, errorMap:errorMap]
                      }else{
                          return [surgicalProceduresInstance: surgicalProceduresInstance, list1:list1, list2:list2, list3:list3, list4:list4, list5:list5, list6:list6, list7:list7, list8:list8, list9:list9, list10:list10, list11:list11, list12:list12, list13:list13, list14:list14, durationId1:1, durationId2:durationId2, formid:params.formid, errorMap:errorMap] 
                      }
            }else{
                return [surgicalProceduresInstance: surgicalProceduresInstance, list1:list1, list2:list2, list3:list3, list4:list4, list5:list5, list6:list6, list7:list7, list8:list8, list9:list9, list10:list10, list11:list11, list12:list12, list13:list13, list14:list14, durationId1:1, durationId2:durationId2, formid:params.formid, errorMap:errorMap] 
            }
            
        }
    }

    def update = {
        def surgicalProceduresInstance = SurgicalProcedures.get(params.id)
        if (surgicalProceduresInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surgicalProceduresInstance.version > version) {
                    
                    surgicalProceduresInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures')] as Object[], "Another user has updated this SurgicalProcedures while you were editing")
                    render(view: "edit", model: [surgicalProceduresInstance: surgicalProceduresInstance])
                    return
                }
            }
            
           // println("before save....")
             caseReportFormService.saveSurgicalProcedures(params)
       
              redirect(action:"edit", params:[id:surgicalProceduresInstance.id, formid:params.formid])
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def surgicalProceduresInstance = SurgicalProcedures.get(params.id)
        if (surgicalProceduresInstance) {
            try {
                surgicalProceduresInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
     static Map checkError(surgicalProceduresInstance, durationId1, list1, list2, list3, list4, list12, durationId2, list5,list6,list7,list8,list9,list10,list13, list11, list14 ){
        def result = [:]
        def durationPreOpeMed = surgicalProceduresInstance.durationPreOpeMed
        if(!durationPreOpeMed)
          result.put("durationPreOpeMed", "The duration of administration of pre-operative medications is a required field. (rowid:${durationId1})")
        
        if(durationPreOpeMed){
            def pattern = ~/\d+:[0,1,2,3,4,5]\d/
            if(!pattern.matcher(durationPreOpeMed))
              result.put("durationPreOpeMed", "Wrong duration format. (rowid:${durationId1})")
              
            if(durationPreOpeMed == "00:00")
                 result.put("durationPreOpeMed", "Duration can not be 00:00. (rowid:${durationId1})")
        }
        
        boolean hasDosage1=false;
        list1.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
             if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
             
            if(dosage){
                hasDosage1 =true;
            }
         }
         
         list2.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id,"Please specify medication name. (rowid:${it.rowid}) ")
             }
            
              if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
            
             if(dosage){
                hasDosage1 =true;
            }
         }
         
         list3.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
              if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
             
              if(dosage){
                hasDosage1 =true;
            }
         }
         
         list4.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id,"Please specify medication name. (rowid:${it.rowid}) ")
             }
             
             if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
             
            if(dosage){
                hasDosage1 =true;
            }
         }
         
         if(!hasDosage1){
             result.put("hasDosage1", "Please specify at least one item in Pre-Operative Medications ")
         }
         
        
         
        
         list12.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }else if(!medicationName && !dosage){
                 result.put("medicationName" + it.id, "Please delete the record or enter the fields. (rowid:${it.rowid}) ")
             }else if(medicationName && !dosage){
                 result.put("dosage" + it.id, "Please specify the dosage. (rowid:${it.rowid}) ")
                 
             }
         }
        
        def durationAnesthesia = surgicalProceduresInstance.durationAnesthesia
         if(!durationAnesthesia)
          result.put("durationAnesthesia", "The duration of anesthesia induction  is a required field. (rowid:${durationId2})")
        
        if(durationAnesthesia){
            def pattern = ~/\d+:[0,1,2,3,4,5]\d/
            if(!pattern.matcher(durationAnesthesia))
              result.put("durationAnesthesia","Wrong duration format. (rowid:${durationId2})")
              
            if(durationAnesthesia == "00:00")
                   result.put("durationAnesthesia","Duration can not be 00:00. (rowid:${durationId2})")
        }
        
         boolean hasDosage2=false;
          list5.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
             if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
             
             if(dosage){
                hasDosage2 =true;
            }
         }
         
          list6.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
             if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
              if(dosage){
                hasDosage2 =true;
            }
         }
         
          list7.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
             if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
              if(dosage){
                hasDosage2 =true;
            }
         }
        
          list8.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
              if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
              if(dosage){
                hasDosage2 =true;
            }
         }
          list9.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
              if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
              if(dosage){
                hasDosage2 =true;
            }
         }
         
          list10.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
              if(medicationName && !dosage && (it.isOther1 || it.isOther2)){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
             if(dosage){
                hasDosage2 =true;
            }
         }
        
         if(!hasDosage2){
             result.put("hasDosage2", "Please specify at least one item in Pre-Operative Anesthesia")
         }
        
         list13.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }else if(!medicationName && !dosage){
                 result.put("medicationName" + it.id, "Please delete the record or enter the fields. (rowid:${it.rowid}) ")
             } else if(medicationName && !dosage){
                 result.put("dosage" + it.id, "Please specify the dosage. (rowid:${it.rowid}) ")
                 
             }else{
                 
             }
         }
         
        
        list11.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }
             
             if(medicationName && !dosage ){
                 result.put("dosage" + it.id, "Please specify dosage. (rowid:${it.rowid}) ")
             }
         }
         
        
         list14.each() {
             def medicationName = it.medicationName
             def dosage = it.dosage
             if(!medicationName && dosage){
                 result.put("medicationName" + it.id, "Please specify medication name. (rowid:${it.rowid}) ")
             }else if(!medicationName && !dosage){
                 result.put("medicationName" + it.id, "Please delete the record or enter the fields. (rowid:${it.rowid}) ")
             }else if(medicationName && !dosage){
                 result.put("dosage" + it.id, "Please specify the dosage. (rowid:${it.rowid}) ")
                 
             }else{
                 
             }
         }
        return result
    }
    
    
     static Map checkError(surgicalProceduresInstance){

        def surgicalMedications = surgicalProceduresInstance.surgicalMedications
        
        List list =surgicalMedications.sort{it.id}
        
        def list1=[]
        def list2=[]
        def list3=[]
        def list4=[]
        def list5=[]
        def list6=[]
        def list7=[]
        def list8=[]
        def list9=[]
        def list10=[]
        def list11=[]
        
        def list12=[]
        def list13=[]
        def list14=[]
          
       
          
         
        
       list.each() {
             //println("id: ${it.id}")
            if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='intravenous (IV) sedation administered' ){
                
                list1.add(it)
            }
             if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='IV Opiate administered' ){
                
                list2.add(it)
            }
            
            if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='IV Antiemetic administered' ){
                
                list3.add(it)
            }
            
             if(it.catgory == 'Pre-Operative Medications' && it.subCatgory=='IV Antacid administered' ){
                
                list4.add(it)
            }
            
            if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='local anesthesia agents administered' ){
                
                list5.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='regional (spinal/epidural) anesthesia agents  administered' ){
                
                list6.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='IV anesthetic administered' ){
                
                list7.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='IV narcotic / opiate anesthetic administered' ){
                
                list8.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='IV muscle relaxant administered' ){
                
                list9.add(it)
            }
            
             if(it.catgory == 'Pre-Operative Anesthesia' && it.subCatgory=='Inhalation anesthetic administered' ){
                
                list10.add(it)
            }
            
              if(it.catgory == 'during surgery' && it.subCatgory ){
                
                list11.add(it)
            }
            
              if(it.catgory == 'Pre-Operative Medications' && !it.subCatgory ){
                
                list12.add(it)
            }
            
            
               if(it.catgory == 'Pre-Operative Anesthesia' && !it.subCatgory ){
                
                list13.add(it)
            }
            
              if(it.catgory == 'during surgery' && !it.subCatgory ){
                
                list14.add(it)
            }
        }
       
        
         int rowid = 1
         list1.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
          list2.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
          list3.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
          list4.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
          list12.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
         
         def durationId2=++rowid;
           list5.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
           list6.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
         
          list7.each() {
             it.rowid = ++rowid
            //println("rowid ${it.rowid}")
         }
         
          list8.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
         list9.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
         list10.each() {
             it.rowid = ++rowid
             //println("rowid ${it.rowid}")
         }
         
         list13.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
         
         list11.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
         
          list14.each() {
             it.rowid = ++rowid
            // println("rowid ${it.rowid}")
         }
        
        
        def result= checkError(surgicalProceduresInstance, 1, list1, list2, list3, list4, list12,durationId2, list5, list6, list7, list8, list9, list10, list13, list11,list14 )
 
        return result
      
         
     }

    
}
