package nci.obbr.cahub.forms.gtex.crf

import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.staticmembers.MedicalCondition
import nci.obbr.cahub.util.ComputeMethods
import nci.obbr.cahub.datarecords.vocab.CVocabRecord



class CaseReportFormController {

    def caseReportFormService
    def ldaccService
    def accessPrivilegeService
    
    static allowedMethods = [update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [caseReportFormInstanceList: CaseReportForm.list(params), caseReportFormInstanceTotal: CaseReportForm.count()]
    }

    def create = {
        def caseReportFormInstance = new CaseReportForm()
        caseReportFormInstance.properties = params
        return [caseReportFormInstance: caseReportFormInstance]
    }

 

    
    def save = {
        def caseReportFormInstance = new CaseReportForm(params)
        try{
            caseReportFormService.saveForm(caseReportFormInstance)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action: "show", id: caseReportFormInstance.id)
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
            render(view: "create", model: [caseReportFormInstance: caseReportFormInstance])
        }
    }
    
    def show = {
        def canSubmit=true
        def caseReportFormInstance = CaseReportForm.get(params.id)
        if (!caseReportFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
        else {
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
            def collectionType = caseReportFormInstance.caseRecord.caseCollectionType
            def cdrVer = caseReportFormInstance.caseRecord.cdrVer
            def parentBssCode = caseReportFormInstance.caseRecord.bss.parentBss.code
            def caseCollectionTypeCode = caseReportFormInstance.caseRecord.caseCollectionType.code

            def sectionA
            def sectionAStr=''
            def demographics=caseReportFormInstance.demographics
            def  demoVersion = demographics.version
            if(demoVersion == 0 ){
                sectionA='blue'
                canSubmit=false
                sectionAStr='Not started'
            }else{
                def result= DemographicsController.checkError(demographics)
                if(result){
                    sectionA='red'
                    canSubmit=false
                    sectionAStr='Has validation error'
                }else{
                    sectionA='green'
                    sectionAStr='No validation error'
                }
            }
            
            //println("sectionA: $sectionA")
            
            
            def sectionB
            def sectionBStr
            def medicalHistory=caseReportFormInstance.medicalHistory
            //pmh new 04/19/13-- lines 89 thru 127
            def hasGenMedicalHistoryStarted=false
            def list1 = GeneralMedicalHistory.findAllByMedicalHistory(medicalHistory)
            def totalMC= MedicalCondition.countByCodeIsNotNull()
            
            if(list1.size() > totalMC){
                hasGenMedicalHistoryStarted=true
               
            }
            else{
                for(g in list1){
                    if(g.version > 1){
                        hasGenMedicalHistoryStarted = true
                    }
                }
            }
            def  medVersion = medicalHistory.version
            if(medVersion == 0 ){
                if(hasGenMedicalHistoryStarted == false){
                    sectionB='blue'
                    canSubmit=false
                    sectionBStr='Not started'
                    
                }
                else{
                    
                    def result= MedicalHistoryController.checkError(medicalHistory, cdrVer)
                    result.each(){key,value->
                        if(key.startsWith('generalMedicalHistories') || key.startsWith('s_generalMedicalHistories') || key.startsWith('cancerHistories') ){
                
                            sectionB='purple'
                            canSubmit=false
                            sectionBStr='Please check for validation errors in : <br> Cancer History, <br> General Medical History <br> General Medical History (not specified in the form)'
                           
                        }else{
                            sectionB='green'
                            sectionBStr='Has Started'
                        }
                    }
                }
            }else{
                def result= MedicalHistoryController.checkError(medicalHistory, cdrVer)
                if(result){
                    sectionB='red'
                    canSubmit=false
                    sectionBStr='Has validation error'
                }else{
                    sectionB='green'
                    sectionBStr='No validation error'
                }
            }
            
            //println("sectionB: $sectionB")
            
            def sectionC
            def sectionCStr
            def concomitantMedications = caseReportFormInstance.concomitantMedications
            if(concomitantMedications){
               
                def result = ConcomitantMedicationController.checkError(concomitantMedications, cdrVer)
               
                //println("result size:" + result.size())
                if(result){
                    // println("catch error.....")
                    canSubmit=false
                    sectionC='red'
                    sectionCStr='Has validation error'
                   
                }else{
                    sectionC='green'
                    sectionCStr='No validation error'
                }
            }else{
                sectionC='blue'
                sectionCStr='Not started'
            }
            
            //println("sectionC: $sectionC")
             
            
              
            def sectionD
            def sectionDStr
            
            def deathCircumstances =caseReportFormInstance.deathCircumstances
            def surgicalProcedures =caseReportFormInstance.surgicalProcedures
            
            if(collectionType?.code == 'SURGI'){
                   
                def  deaVersion = surgicalProcedures.version
                if(deaVersion == 0 ){
                    sectionD='blue'
                    canSubmit=false
                    sectionDStr='Not started'
                }else{
                    def result= SurgicalProceduresController.checkError(surgicalProcedures)
                    if(result){
                        sectionD='red'
                        canSubmit=false
                        sectionDStr='Has validation error'
                        //println("result is not null")
                    }else{
                        //println("result is null???")
                        sectionD='green'
                        sectionDStr='No validation error'
                    }
                }
              
                  
            }else{
           
                
                def  deaVersion = deathCircumstances.version
                if(deaVersion == 0 ){
                    sectionD='blue'
                    canSubmit=false
                    sectionDStr='Not started'
                }else{
                    def result= DeathCircumstancesController.checkError(deathCircumstances, parentBssCode, caseCollectionTypeCode)
                    if(result){
                        sectionD='red'
                        canSubmit=false
                        sectionDStr='Has validation error'
                        //println("result is not null")
                    }else{
                        //println("result is null???")
                        sectionD='green'
                        sectionDStr='No validation error'
                    }
                }
            }

            
            
            //println("sectionD: $sectionD")
            
                        
            def sectionE
            def sectionEStr
            def serologyResult =caseReportFormInstance.serologyResult
            def  serVersion = serologyResult.version
            if(serVersion == 0 ){
                sectionE='blue'
                //canSubmit=false
                sectionEStr='Not started'
            }else{
                def result= SerologyResultController.checkError(serologyResult)
                if(result){
                    sectionE='red'
                    canSubmit=false
                    sectionEStr='Has validation error'
                }else{
                    sectionE='green'
                    sectionEStr='No validation error'
                }
            }
            
            // println("sectionE: $sectionE")
            
       
           
            
            [caseReportFormInstance: caseReportFormInstance, sectionA:sectionA, sectionB:sectionB, sectionC:sectionC, sectionD:sectionD, sectionE:sectionE, canSubmit:canSubmit, 
                sectionAStr:sectionAStr, sectionBStr:sectionBStr, sectionCStr:sectionCStr, sectionDStr:sectionDStr, sectionEStr:sectionEStr ]
        }
    }

    def edit = {
        def caseReportFormInstance = CaseReportForm.get(params.id)
        if (!caseReportFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
        else {
            
            return [caseReportFormInstance: caseReportFormInstance]
        }
    }

    def update = {
        def caseReportFormInstance = CaseReportForm.get(params.id)
        if (caseReportFormInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (caseReportFormInstance.version > version) {
                    
                    caseReportFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseReportForm.label', default: 'Case Report Form ')] as Object[], "Another user has updated this CaseReportForm while you were editing")
                    render(view: "edit", model: [caseReportFormInstance: caseReportFormInstance])
                    return
                }
            }
            caseReportFormInstance.properties = params
            if (!caseReportFormInstance.hasErrors() && caseReportFormInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
                redirect(action: "show", id: caseReportFormInstance.id)
            }
            else {
                render(view: "edit", model: [caseReportFormInstance: caseReportFormInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    //We don't want anyone else to delete 
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def caseReportFormInstance = CaseReportForm.get(params.id)
        if (caseReportFormInstance) {
            try {
                caseReportFormInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseReportForm.label', default: 'Case Report Form For'), caseReportFormInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }
    
    def display2={
        redirect(action:'display', id:params.id)
    }
    
    def display={
        def caseReportFormInstance = CaseReportForm.get(params.id)
        def caseRecord = caseReportFormInstance?.caseRecord
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
        def collectionType = caseReportFormInstance.caseRecord.caseCollectionType
        def demographicsInstance = caseReportFormInstance.demographics
        boolean raceWhite=demographicsInstance.raceWhite
        boolean raceAsian=demographicsInstance.raceAsian
        boolean raceBlackAmerican=demographicsInstance.raceBlackAmerican
        boolean raceIndian=demographicsInstance.raceIndian
        boolean raceHawaiian=demographicsInstance.raceHawaiian
        boolean raceUnknown=demographicsInstance.raceUnknown
        String race=''
        if(raceWhite){
            race = race+ ",White"
        }
        if(raceAsian){
            race = race+ ",Asian"
        }
        if(raceBlackAmerican){
            race= race+ ",Black or African American"
        }
        if(raceIndian){
            race= race +",American Indian or Alaska Native"
        }
        if(raceHawaiian){
            race= race +",Native Hawaiian or other Pacific Islander"
        }
        if(raceUnknown){
            race= race +",Unknown"
        }

        if(race && race.length() > 0){
            race=race.substring(1)
        }
        
        
        //pmh CDRQA:560 05/13/13
        //demographicsInstance.race=race
        if(demographicsInstance.version > 0){
            demographicsInstance.race=race
        }
        
        def medicalHistoryInstance=caseReportFormInstance.medicalHistory
        def cancerHistoryInstanceList=medicalHistoryInstance?.cancerHistories
         
        // def generalMedicalHistoryInstanceList=GeneralMedicalHistory.findAllByMedicalHistory(medicalHistoryInstance, [sort:'id'])
        def gmhList=GeneralMedicalHistory.findAllByMedicalHistory(medicalHistoryInstance, [sort:'id'])
        //pmh vocab: 04/30/13 update the code for each medical condition to display the info icons for each med. condition
        
        def generalMedicalHistoryInstanceList
        if(gmhList.size() > 0){
            generalMedicalHistoryInstanceList=caseReportFormService.updateMedicalConditionCodeVal(gmhList)
        }
         
        //pmh HUB-CR-31 03/06/13 print medications list exactly as entered during entry
        //def concomitantMedicationInstanceList=caseReportFormInstance.concomitantMedications.sort
        def concomitantMedicationInstanceList=ConcomitantMedication.findAllByCaseReportForm(caseReportFormInstance, [sort:'dateCreated',order:'asc'])
        
        // this is used to toggle display of 'approximate interval onset to death ' based on cdr version: hub-cr-34
        def  showDeathInterval = true
        // change in the field title from medical record documentation to History source : hub-cr-37
        def show45VersionUpdates=false
        //def computeMethods = new ComputeMethods()
        if(ComputeMethods.compareCDRVersion(caseReportFormInstance.caseRecord.cdrVer, '4.5') >= 0){
            showDeathInterval = false
            show45VersionUpdates=true
        }
        
        
        // end PMH
        
        def deathCircumstancesInstance=caseReportFormInstance.deathCircumstances
        def surgicalProceduresInstance = caseReportFormInstance.surgicalProcedures
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
        def isdeath=false
        if(collectionType?.code == 'SURGI'){
            def surgicalMedications = surgicalProceduresInstance.surgicalMedications
            List list =surgicalMedications.sort{it.id}
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
        
        }else{
            isdeath=true
        }
        
        def deathDate
        if(isdeath){
            
            if(deathCircumstancesInstance?.dateTimeActualDeath){
              
                deathDate = deathCircumstancesInstance?.dateTimeActualDeath
            }else if(deathCircumstancesInstance?.dateTimePresumedDeath){
                deathDate = deathCircumstancesInstance?.dateTimePresumedDeath
            }else{
                 
            }    
        
            
        }else{
            def collectionDate = caseReportFormInstance.caseRecord.tissueRecoveryGtex?.collectionDate
            def startTime = caseReportFormInstance.caseRecord.tissueRecoveryGtex?.collectionStartTime
            def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
            def cross_clamp_time = caseReportFormInstance.caseRecord.tissueRecoveryGtex?.crossClampTime
            def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)                      
            deathDate=crossClampDateTime                                           
        }
        
        def birthDate = demographicsInstance?.dateOfBirth
        if(deathDate && birthDate ){
            def age =ldaccService.calculateAge(deathDate, birthDate)
            demographicsInstance.age=age
        }
        
        def serologyResultInstance=caseReportFormInstance.serologyResult
        return [caseReportFormInstance:caseReportFormInstance, demographicsInstance:demographicsInstance,
            medicalHistoryInstance:medicalHistoryInstance, cancerHistoryInstanceList:cancerHistoryInstanceList,
            generalMedicalHistoryInstanceList:generalMedicalHistoryInstanceList,
            concomitantMedicationInstanceList:concomitantMedicationInstanceList,
            deathCircumstancesInstance:deathCircumstancesInstance,
            serologyResultInstance:serologyResultInstance, isdeath:isdeath,
            surgicalProceduresInstance:surgicalProceduresInstance,list1:list1, list2:list2, list3:list3, list4:list4, list5:list5, list6:list6, list7:list7, list8:list8, list9:list9, list10:list10, list11:list11, list12:list12, list13:list13, list14:list14,showDeathInterval:showDeathInterval,show45VersionUpdates:show45VersionUpdates ]
    }
    
    
    def submit={
        
        def caseReportFormInstance = CaseReportForm.get(params.id)
        caseReportFormInstance.status=CaseReportForm.Status.Submitted
        try{
            caseReportFormInstance.save(failOnError:true,flush: true)
            redirect(action: "show", id: params.id)
        }catch(Exception e){
            flash.message = "Failed: " + e.toString()
            redirect(action: "show", id: params.id)
            
        }
        
    }
    
    
    def editing={
        
        def caseReportFormInstance = CaseReportForm.get(params.id)
        caseReportFormInstance.status=CaseReportForm.Status.Editing
        try{
            caseReportFormInstance.save(failOnError:true,flush: true)
            redirect(action: "show", id: params.id)
        }catch(Exception e){
            flash.message = "Failed: " + e.toString()
            redirect(action: "show", id: params.id)
            
        }
        
    }
    
   
    def addVocabDef={
        println("--------------------------------------------")
      // def http = new HTTPBuilder( 'http://fr-s-hub-solr-p.ncifcrf.gov:8080' ) 
       def vocabList = CVocabRecord.findAll()
       //  def vocabList=[]
        // def vocab2 = CVocabRecord.findById(141)
        // vocabList.add(vocab2)
        int found_num=0;
        int not_found_num = 0;
        vocabList.each{
            if(it.srcDef == null && it.genName == null){
              def map = caseReportFormService.findVocab(it)
              if(map.found){
                  println("found")
                  def str = map.data
                  if(str){
                      caseReportFormService.updateVocab(it, str)
                  }
                  found_num++
              }else{
                  println("big problem!!!!!")
                  not_found_num++;
              }
            }
        }
      
      /** def vocab1 = CVocabRecord.findById(154)
       def map = caseReportFormService.findVocab(vocab1)
       if(map.found){
           println("srcDef: " + map.srcDef)
       }
       
        
          def vocab2 = CVocabRecord.findById(143)
        def map2=caseReportFormService.findVocab(vocab2)
        
        if(map2.found){
            println("genName: " + map2.genName) 
        }**/
        
        println("found_num: " + found_num)
        println("not_found_num: " + not_found_num)
        render("done")
    }    
}
