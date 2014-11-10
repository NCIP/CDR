package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.util.AppSetting
import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.util.ComputeMethods


class BpvConsentEnrollmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService
    def activityEventService
    def bpvCaseStatusService
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvConsentEnrollmentInstanceList: BpvConsentEnrollment.list(params), bpvConsentEnrollmentInstanceTotal: BpvConsentEnrollment.count()]
    }

    def create = {
        def bpvConsentEnrollmentInstance = new BpvConsentEnrollment()
        bpvConsentEnrollmentInstance.properties = params
        return [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance]
    }

    def save = {
        def bpvConsentEnrollmentInstance = new BpvConsentEnrollment(params)
        bpvConsentEnrollmentInstance.ethnicBackground = bpvConsentEnrollmentInstance.calculateEthnicBackground()
        
        boolean r = true;
	if ((bpvConsentEnrollmentInstance?.inputtedCaseId)&&(CaseRecord.findByCaseId(bpvConsentEnrollmentInstance?.inputtedCaseId?.toUpperCase()) != null)) {
            bpvConsentEnrollmentInstance.errors.rejectValue('inputtedCaseId', 'This Case ID is already used. Input another value')
            r = false
        }
        if ((bpvConsentEnrollmentInstance?.tissueBankId)&&(CaseRecord.findByTissueBankId(bpvConsentEnrollmentInstance?.tissueBankId) != null)) {
            bpvConsentEnrollmentInstance.errors.rejectValue('tissueBankId', 'This Tissue Bank ID is already used. Input another value')
            r = false
        }
        if (!r)
        {
            render(view: "create", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
            return
        }
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvConsentEnrollmentInstance.formSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }
        
        if (bpvConsentEnrollmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "editWithValidation", id: bpvConsentEnrollmentInstance.id)
        }
        else {
            render(view: "create", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
        }
    }

    def show = {
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        if (!bpvConsentEnrollmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(bpvConsentEnrollmentInstance.candidateRecord, session, 'view')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
//            if (!accessPrivilegeService.checkAccessPrivilegeCandidate(bpvConsentEnrollmentInstance.candidateRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            def canResume = (accessPrivilegeService.checkAccessPrivilegeCandidate(bpvConsentEnrollmentInstance.candidateRecord, session, 'edit') == 0)
            [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance, canResume: canResume]
        }
    }

    def edit = {
        def canSubmit = 'No'
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        if (!bpvConsentEnrollmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(bpvConsentEnrollmentInstance.candidateRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            if (bpvConsentEnrollmentInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvConsentEnrollmentInstance)
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            return [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance, canSubmit: canSubmit]
        }
    }
    
    def editWithValidation = {
        def canSubmit = 'No'
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        if (!bpvConsentEnrollmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
        else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(bpvConsentEnrollmentInstance.candidateRecord, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            if (bpvConsentEnrollmentInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            def errorMap = checkError(bpvConsentEnrollmentInstance)
            errorMap.each() {key, value ->
                bpvConsentEnrollmentInstance.errors.rejectValue(key, value)
            }
            if (errorMap.size() == 0) {
                canSubmit = 'Yes'
            }
            render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance, canSubmit: canSubmit])
        }
    }

    def update = {
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        if (bpvConsentEnrollmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvConsentEnrollmentInstance.version > version) {
                    
                    bpvConsentEnrollmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvConsentEnrollment while you were editing")
                    render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
                    return
                }
            }
            bpvConsentEnrollmentInstance.primaryTissueType = null
            bpvConsentEnrollmentInstance.properties = params
            bpvConsentEnrollmentInstance.ethnicBackground = bpvConsentEnrollmentInstance.calculateEthnicBackground()
            if (!bpvConsentEnrollmentInstance.hasErrors() && bpvConsentEnrollmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
                redirect(action: "editWithValidation", id: bpvConsentEnrollmentInstance.id)
            }
            else {
                render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
    
    def submit = {
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        if (bpvConsentEnrollmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvConsentEnrollmentInstance.version > version) {
                    
                    bpvConsentEnrollmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvConsentEnrollment while you were editing")
                    render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
                    return
                }
            }
         //   bpvConsentEnrollmentInstance.primaryTissueType = null
          //  bpvConsentEnrollmentInstance.properties = params
         //   bpvConsentEnrollmentInstance.ethnicBackground = bpvConsentEnrollmentInstance.calculateEthnicBackground()
            def errorMap = checkError(bpvConsentEnrollmentInstance)
            errorMap.each() {key, value ->
                bpvConsentEnrollmentInstance.errors.rejectValue(key, value)
            }
            
            if(!errorMap){
                try{
                    def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    bpvCaseStatusService.submitConsentForm(params, username)
                    bpvConsentEnrollmentInstance = BpvConsentEnrollment.findById(params.id)
                    def caseRecord= bpvConsentEnrollmentInstance.candidateRecord.caseRecord
                   
                    caseRecord.index()
                    redirect(controller: "caseRecord", action: "display", id: caseRecord.id)
                }catch(Exception e){
                      flash.message="Failed: " + e.toString()  
          
                   redirect(action:"edit", params:[id:bpvConsentEnrollmentInstance.id])
                }
            }else{
                redirect(action:"edit", params:[id:bpvConsentEnrollmentInstance.id])
            }
            
            
          /**  if (!bpvConsentEnrollmentInstance.hasErrors() && bpvConsentEnrollmentInstance.save(flush: true)) {
                setIsConsentedInCandidateRecord(bpvConsentEnrollmentInstance)
                if (bpvConsentEnrollmentInstance.candidateRecord.isEligible && bpvConsentEnrollmentInstance.candidateRecord.isConsented) {
                    if (!bpvConsentEnrollmentInstance.candidateRecord.caseRecord) {
                        linkToNewCaseRecord(bpvConsentEnrollmentInstance)
                    } else {
                        updateOldCaseRecord(bpvConsentEnrollmentInstance) 
                    }
                } else {
                    bpvConsentEnrollmentInstance.dateSubmitted = new Date()
                    bpvConsentEnrollmentInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    flash.message = "${message(code: 'default.submitted.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
                    redirect(controller: "candidateRecord", action: "view", id: bpvConsentEnrollmentInstance.candidateRecord.id)
                }
            }
            else {
                bpvConsentEnrollmentInstance.discard()
                render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
            }**/
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        if (bpvConsentEnrollmentInstance) {
            try {
                bpvConsentEnrollmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
            redirect(action: "list")
        }
    }
    
    def resumeEditing = {
        def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id)
        bpvConsentEnrollmentInstance.dateSubmitted = null
        bpvConsentEnrollmentInstance.submittedBy = null
        if (bpvConsentEnrollmentInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvConsentEnrollmentInstance.id)
        }
        else {
            render(view: "show", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
        }
    }
    
    def linkToNewCaseRecord(bpvConsentEnrollmentInstance) {
        def canCreateCase = true
        
        if (CaseRecord.findByCaseId(bpvConsentEnrollmentInstance.inputtedCaseId.toUpperCase()) != null) {
            bpvConsentEnrollmentInstance.errors.rejectValue('inputtedCaseId', 'This Case ID is already taken')
            canCreateCase = false
        }
        if (CaseRecord.findByTissueBankId(bpvConsentEnrollmentInstance.tissueBankId) != null) {
            bpvConsentEnrollmentInstance.errors.rejectValue('tissueBankId', 'This Tissue Bank ID is already taken')
            canCreateCase = false
        }
        
        if (canCreateCase) {
            def caseRecordInstance = new CaseRecord(
                    caseId: bpvConsentEnrollmentInstance.inputtedCaseId.toUpperCase(),
                    kitList: bpvConsentEnrollmentInstance.inputtedKitList,
                    candidateRecord: bpvConsentEnrollmentInstance.candidateRecord,
                    caseStatus: CaseStatus.findByCode("INIT"),
                    caseCollectionType: bpvConsentEnrollmentInstance.candidateRecord.caseCollectionType,
                    bss: bpvConsentEnrollmentInstance.candidateRecord.bss,
                    tissueBankId: bpvConsentEnrollmentInstance.tissueBankId,
                    study: Study.findByCode("BPV"),
                    cdrVer: ApplicationHolder.application.metadata['app.version'],
                    primaryTissueType: bpvConsentEnrollmentInstance.primaryTissueType
            )
            boolean r = false
            try
            {
                r = caseRecordInstance.save(flush: true)
            }
            catch(Exception ee)
            {
                println "Exception on submitting BpvConsentEnrollment form for caseID=" + bpvConsentEnrollmentInstance.inputtedCaseId.toUpperCase() + " , Error=" + ee.getMessage()
                flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'caseRecord.label', default: 'Case Record ID or Tissue Bank ID for Case'), caseRecordInstance.caseId])}"
                redirect(action: "edit", id: bpvConsentEnrollmentInstance.id)
                return
            }
            if (r) {
                bpvConsentEnrollmentInstance.dateSubmitted = new Date()
                bpvConsentEnrollmentInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                bpvConsentEnrollmentInstance.inputtedCaseId = bpvConsentEnrollmentInstance.inputtedCaseId.toUpperCase()
                bpvConsentEnrollmentInstance.save(flush: true)
                
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseRecord.label', default: 'Case Record for Case'), caseRecordInstance.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: caseRecordInstance.id)
                
                def activityType = ActivityType.findByCode("BPVCASE")
                def caseId = caseRecordInstance.caseId
                def study = caseRecordInstance.study
                def bssCode = caseRecordInstance.bss?.code
                def initiator = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                activityEventService.createEvent(activityType, caseId, study, bssCode, null, initiator, null, null)
            } else {
                // Display the error message of caseRecordInstance in edit view of BpvConsentEnrollment
                bpvConsentEnrollmentInstance.errors = caseRecordInstance.errors
                bpvConsentEnrollmentInstance.candidateRecord.caseRecord = null
                render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
            }    
        } else {
            render(view: "edit", model: [bpvConsentEnrollmentInstance: bpvConsentEnrollmentInstance])
        }
    }
    
    def updateOldCaseRecord(bpvConsentEnrollmentInstance) {
        def modifying = false

        if (bpvConsentEnrollmentInstance.inputtedCaseId != bpvConsentEnrollmentInstance.candidateRecord.caseRecord.caseId) {
          //  bpvConsentEnrollmentInstance.inputtedCaseId = bpvConsentEnrollmentInstance.candidateRecord.caseRecord.caseId
            //pmh cdrqa 1333 09/11/14
            def cc1 = ShippingEvent.createCriteria()
            def shippingEventInstanceList = cc1.list {
                eq("caseId", bpvConsentEnrollmentInstance.candidateRecord.caseRecord.caseId)
                order("dateCreated", "desc")
            }
            shippingEventInstanceList.each{
                
                it.caseId=bpvConsentEnrollmentInstance.inputtedCaseId
                it.save()
                
            }
            
            bpvConsentEnrollmentInstance.candidateRecord.caseRecord.caseId=bpvConsentEnrollmentInstance.inputtedCaseId
            bpvConsentEnrollmentInstance.candidateRecord.caseRecord.index()
            modifying = true
        }
       
        if (bpvConsentEnrollmentInstance.inputtedKitList != bpvConsentEnrollmentInstance.candidateRecord.caseRecord.kitList) {
            bpvConsentEnrollmentInstance.inputtedKitList = bpvConsentEnrollmentInstance.candidateRecord.caseRecord.kitList
            modifying = true
        }
        
        
        if (bpvConsentEnrollmentInstance.tissueBankId != bpvConsentEnrollmentInstance.candidateRecord.caseRecord.tissueBankId) {
           // bpvConsentEnrollmentInstance.tissueBankId = bpvConsentEnrollmentInstance.candidateRecord.caseRecord.tissueBankId
             bpvConsentEnrollmentInstance.candidateRecord.caseRecord.tissueBankId=bpvConsentEnrollmentInstance.tissueBankId
            modifying = true
        }
        
        if (bpvConsentEnrollmentInstance.primaryTissueType != bpvConsentEnrollmentInstance.candidateRecord.caseRecord.primaryTissueType) {
            bpvConsentEnrollmentInstance.primaryTissueType = bpvConsentEnrollmentInstance.candidateRecord.caseRecord.primaryTissueType
            modifying = true
        }

        bpvConsentEnrollmentInstance.dateSubmitted = new Date()
        bpvConsentEnrollmentInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        bpvConsentEnrollmentInstance.save(flush: true)
        if (modifying) {
            flash.message = "Case Record information cannot be updated after the case is created"
        } else {
            flash.message = "${message(code: 'default.submitted.message', args: [bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName + ' for Candidate', bpvConsentEnrollmentInstance.candidateRecord.candidateId])}"
        }
        redirect(controller: "caseRecord", action: "display", id: bpvConsentEnrollmentInstance.candidateRecord.caseRecord.id)
    }
    
    def setIsConsentedInCandidateRecord(bpvConsentEnrollmentInstance) {
        if (bpvConsentEnrollmentInstance.signedDated == 'Yes') {
            bpvConsentEnrollmentInstance.candidateRecord.isConsented = true
        } else {
            bpvConsentEnrollmentInstance.candidateRecord.isConsented = false
        } 
    }
    
def updatePackYearsSmoked = {
    def bpvConsentEnrollmentInstance = BpvConsentEnrollment.get(params.id) 
    def ageAsOfConsentedDate = params.age
    if (ageAsOfConsentedDate != null && ageAsOfConsentedDate.indexOf(" ")!=-1) {
            ageAsOfConsentedDate = ageAsOfConsentedDate.substring(0,ageAsOfConsentedDate.indexOf(" "))
        }

        if (bpvConsentEnrollmentInstance?.candidateRecord?.caseRecord?.bpvClinicalDataEntry!=null) {
            def bpvClinicalDataEntry = bpvConsentEnrollmentInstance.candidateRecord.caseRecord.bpvClinicalDataEntry
            if (bpvClinicalDataEntry.tobaccoSmHist.equals("Includes daily and non-daily smokers") && bpvClinicalDataEntry.smokeAgeStart!=null && bpvClinicalDataEntry.cigsPerDay!=null) {
                def startVal = Double.parseDouble(bpvClinicalDataEntry.smokeAgeStart)
                def stopVal = Double.parseDouble(ageAsOfConsentedDate)
                def cigsPerDay = Double.parseDouble(bpvClinicalDataEntry.cigsPerDay)
                def packYrsSmoked = ((((stopVal - startVal)*cigsPerDay)/20)).round(1)
                bpvClinicalDataEntry.numPackYearsSm = packYrsSmoked
                bpvClinicalDataEntry.save(flush: true)
            }            
        }    
    }    
    
    Map checkError(bpvConsentEnrollmentInstance) {
        def errorMap = [:]
        
        if (!bpvConsentEnrollmentInstance.inputtedCaseId) {
            errorMap.put('inputtedCaseId', 'Question #1 cannot be blank')
        }
        
        
        if(bpvConsentEnrollmentInstance.inputtedCaseId){
            def caseRecord = bpvConsentEnrollmentInstance.candidateRecord.caseRecord
            if(caseRecord){
                def caseId = caseRecord.caseId
                if(bpvConsentEnrollmentInstance.inputtedCaseId != caseId){
                    def search = CaseRecord.findByCaseId(bpvConsentEnrollmentInstance.inputtedCaseId)
                    if(search){
                        errorMap.put('inputtedCaseId', 'The case id ' + bpvConsentEnrollmentInstance.inputtedCaseId + " already exists in CDR database.")
                    }
                }
            }
        }
        //UPMC only and candidates created with CDR Ver 5.3 and later
        if(bpvConsentEnrollmentInstance?.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(bpvConsentEnrollmentInstance?.candidateRecord?.cdrVer, '5.3') >= 0){        
            if (!bpvConsentEnrollmentInstance.localFacility && bpvConsentEnrollmentInstance.candidateRecord.bss.code == 'UPMC') {
                errorMap.put('localFacility', 'Please select the local hospital where the surgery occurred.')
            }        
        }
        
        if (!bpvConsentEnrollmentInstance.inputtedKitList) {
            errorMap.put('inputtedKitList', 'Question #2 cannot be blank')
        }        
        if (!bpvConsentEnrollmentInstance.tissueBankId) {
            errorMap.put('tissueBankId', 'Question #4 cannot be blank')
        }
        
        if (bpvConsentEnrollmentInstance.tissueBankId) {
             def caseRecord = bpvConsentEnrollmentInstance.candidateRecord.caseRecord
            if(caseRecord){
                def tissueBankId = caseRecord.tissueBankId
                if(bpvConsentEnrollmentInstance.tissueBankId != tissueBankId){
                    def search = CaseRecord.findByTissueBankId(bpvConsentEnrollmentInstance.tissueBankId)
                    if(search){
                        errorMap.put('tissueBankId', 'The tissue bank id ' + bpvConsentEnrollmentInstance.tissueBankId + " already exists in CDR database.")
                    }
                }
            }
        }
        
        if (!bpvConsentEnrollmentInstance.primaryTissueType) {
            errorMap.put('primaryTissueType', 'Please select an option for question #5')
        }
        if (!bpvConsentEnrollmentInstance.dob) {
            errorMap.put('dob', 'Question #6 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.gender) {
            errorMap.put('gender', 'Please select an option for question #7')
        }
        if (bpvConsentEnrollmentInstance.gender == 'Other, specify' && !bpvConsentEnrollmentInstance.otherGender) {
            errorMap.put('otherGender', 'Please specify other gender')
        }
        if (bpvConsentEnrollmentInstance.primaryTissueType?.name == 'Ovary' && bpvConsentEnrollmentInstance.gender == 'Male') {
            errorMap.put('gender', '\"Participant\'s gender\" cannot be \"Male\" when \"Primary Tissue Type\" is \"Ovary\"')
        }
        if (!bpvConsentEnrollmentInstance.race) {
            errorMap.put('race', 'Please select an option for question #8')
        }
        if (!bpvConsentEnrollmentInstance.ethnicity) {
            errorMap.put('ethnicity', 'Please select an option for question #9')
        }
        if (!bpvConsentEnrollmentInstance.jewish) {
            errorMap.put('jewish', 'Please select an option for question #10')
        }
        if (bpvConsentEnrollmentInstance.otherEthnic && !bpvConsentEnrollmentInstance.otherEthnicStr) {
            errorMap.put('otherEthnicStr', 'Please specify other ethnic background')
        }
        
        if (bpvConsentEnrollmentInstance.signedDated == 'Yes') {
            if (!bpvConsentEnrollmentInstance.dateConsented) {
                errorMap.put('dateConsented', 'Date of Participant\'s Consent cannot be blank')
            }
            if (!bpvConsentEnrollmentInstance.age) {
                errorMap.put('age', 'Age of Participant as of Consent date cannot be blank')
            }
            if (bpvConsentEnrollmentInstance.meetAge != 'Yes' && bpvConsentEnrollmentInstance.meetAge != 'No') {
                errorMap.put('meetAge', 'Please select an option for does the Participant meet the Age of Majority for your State')
            }
            if (!bpvConsentEnrollmentInstance.dateWitnessed) {
                errorMap.put('dateWitnessed', 'Date of witness of Consent cannot be blank')
            }
            if (!bpvConsentEnrollmentInstance.dateVerified) {
                errorMap.put('dateVerified', 'Date of Consent verification cannot be blank')
            }
            if (!bpvConsentEnrollmentInstance.consentFormVer) {
                errorMap.put('consentFormVer', 'Version of Consent form cannot be blank')
            }
            if (!bpvConsentEnrollmentInstance.nameObtainedConsent) {
                errorMap.put('nameObtainedConsent', 'Consent obtained by cannot be blank')
            }
            if (!bpvConsentEnrollmentInstance.consentorRelationship) {
                errorMap.put('consentorRelationship', 'Please select an option for relationship of Consent signer to donor')
            }
            if (bpvConsentEnrollmentInstance.consentorRelationship == 'Other, specify' && !bpvConsentEnrollmentInstance.otherConsentRelation) {
                errorMap.put('otherConsentRelation', 'Please specify other relationship of Consent signer to donor')
            }
        } else if (bpvConsentEnrollmentInstance.signedDated == 'No') {    
        } else {
            errorMap.put('signedDated', 'Please select an option for Consent form was signed and dated')
        }
        
        if (!bpvConsentEnrollmentInstance.dateIRBApproved) {
            errorMap.put('dateIRBApproved', 'IRB approval date cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.dateIRBExpires) {
            errorMap.put('dateIRBExpires', 'IRB expiration date cannot be blank')
        }
        /*
        if (!bpvConsentEnrollmentInstance.dateConsented) {
            errorMap.put('dateConsented', 'Question #12 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.consentFormVer) {
            errorMap.put('consentFormVer', 'Question #13 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.nameObtainedConsent) {
            errorMap.put('nameObtainedConsent', 'Question #14 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.consentorRelationship) {
            errorMap.put('consentorRelationship', 'Please select an option for question #15')
        }
        if (bpvConsentEnrollmentInstance.consentorRelationship == 'Other, specify' && !bpvConsentEnrollmentInstance.otherConsentRelation) {
            errorMap.put('otherConsentRelation', 'Please specify other relationship of consent signer to donor')
        }
        if (!bpvConsentEnrollmentInstance.dateIRBApproved) {
            errorMap.put('dateIRBApproved', 'Question #16 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.dateIRBExpires) {
            errorMap.put('dateIRBExpires', 'Question #17 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.dateWitnessed) {
            errorMap.put('dateWitnessed', 'Question #19 cannot be blank')
        }
        if (!bpvConsentEnrollmentInstance.dateVerified) {
            errorMap.put('dateVerified', 'Question #20 cannot be blank')
        }
        if (bpvConsentEnrollmentInstance.signedDated != 'Yes' && bpvConsentEnrollmentInstance.signedDated != 'No') {
            errorMap.put('signedDated', 'Please select an option for question #21')
        }
        */
        return errorMap
    }
}
