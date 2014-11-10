package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.gtex.crf.*
import nci.obbr.cahub.forms.bms.*
import nci.obbr.cahub.forms.bms.*
import nci.obbr.cahub.forms.gtex.*
import nci.obbr.cahub.forms.bpv.*
import nci.obbr.cahub.forms.ctc.*
import nci.obbr.cahub.prc.*
import nci.obbr.cahub.forms.bpv.worksheet.*
import nci.obbr.cahub.forms.bpv.clinicaldataentry.*
import nci.obbr.cahub.util.FileUpload
import nci.obbr.cahub.forms.common.withdraw.*
import nci.obbr.cahub.forms.ctc.*
import nci.obbr.cahub.datarecords.ctc.*
import nci.obbr.cahub.util.bpv.BatchProcessing

class CaseRecord extends DataRecordBaseClass {
    
    String caseId //cbr case id
    CandidateRecord candidateRecord
    PatientRecord patientRecord
    CaseStatus caseStatus
    CaseCollectionType caseCollectionType
    BSS bss
    String tissueBankId
    Study study
    String kitList
    String cdrVer
    AcquisitionType primaryTissueType //subject or study tissue/organ
    CaseRecord parentCase
    
    String finalSurgicalPath
    Date dateFspUploaded
    String fspUploadedBy
    
    Boolean prcSignOff=false
    String prcSignOffBy
    Date   prcSignOffDate
    Integer dmFastTrack=0
    
    Boolean hasLocalPathReview=false
     
    StudyPhase phase
    BatchProcessing batchProcessing
    Boolean mtExcluded
    
    //int age
    //float weight
    
    static belongsTo = [CandidateRecord, PatientRecord]
    
    static hasOne = [tissueRecoveryGtex:TissueRecoveryGtex, tissueRecoveryBms:TissueRecoveryBms, caseReportForm:CaseReportForm,prcReport:PrcReport, prcReportFzn:PrcReportFzn, bpvBloodForm:BpvBloodForm, bpvCaseQualityReview:BpvCaseQualityReview, bpvTissueProcessEmbed:BpvTissueProcessEmbed, bpvTissueGrossEvaluation:BpvTissueGrossEvaluation, bpvTissueReceiptDissection:BpvTissueReceiptDissection, bpvSurgeryAnesthesiaForm:BpvSurgeryAnesthesiaForm, bpvClinicalDataEntry:BpvClinicalDataEntry, bpvWorkSheet:BpvWorkSheet, bpvSlidePrep:BpvSlidePrep,caseWithdraw:CaseWithdraw, tissueRecoveryBrain:TissueRecoveryBrain, feedback:Feedback, feedbackFzn:FeedbackFzn,brainBankFeedback:BrainBankFeedback,bpvSixMonthFollowUp:BpvSixMonthFollowUp]
    
    static hasMany = [specimens:SpecimenRecord, files:FileUpload, ctcCrfs:CtcCrf]

    String toString(){"$caseId"}    
    
    static searchable = {
      // age index:"not_analyzed", format: "000000"
      // weight index:"not_analyzed", format: "000000.00"
      // publicVersion index:"not_analyzed", format: "000000.00"
      // dateCreated format: "yyyy-MM-dd HH:mm"

        'dateCreated'  name:'caseDateCreated', format: "yyyy-MM-dd HH:mm"
        
        caseStatus component: true
        bss component: true
        specimens component: true
        files component: true
        //tissueRecoveryGtex component: true
        caseReportForm component: true
        candidateRecord component: true
        patientRecord component: true
        caseCollectionType component: true
        study component: true
        parentCase component: true
        primaryTissueType component: true
        phase component: true
        
        
        
    }
    
    static mapping = {
        table 'dr_case'
        id generator:'sequence', params:[sequence:'dr_case_pk']
        specimens sort:"specimenId" 
        files sort:"uploadTime"
        sort dateCreated:"desc"  
    }
    
    static constraints = {
        caseId(unique:true, blank:false, nullable:false)
        candidateRecord(blank:true, nullable:true)
        caseStatus(blank:false, nullable:false)
        caseCollectionType(blank:false, nullable:false)
        bss(nullable:false, blank:false)
        tissueBankId(unique:true, nullable:true, blank:true)
        study(blank:false, nullable:false)
        kitList(nullable:true, blank:true)
        cdrVer(nullable:true, blank:true)
        primaryTissueType(nullable:true, blank:true)
        parentCase(nullable:true, blank:true)
        finalSurgicalPath(nullable:true, blank:true)
        dateFspUploaded(nullable:true, blank:true)
        fspUploadedBy(nullable:true, blank:true)
        tissueRecoveryGtex(nullable:true, blank:true)        
        tissueRecoveryBms(nullable:true, blank:true)
        tissueRecoveryBrain(nullable:true, blank:true)
        caseReportForm(nullable:true, blank:true)
        prcReport(nullable:true, blank:true)
        prcReportFzn(nullable:true, blank:true)
        bpvBloodForm(nullable:true, blank:true)
        bpvCaseQualityReview(nullable:true, blank:true)
        bpvTissueProcessEmbed(nullable:true, blank:true)
        bpvTissueGrossEvaluation(nullable:true, blank:true)
        bpvTissueReceiptDissection(nullable:true, blank:true)
        bpvClinicalDataEntry(nullable:true, blank:true)
        bpvSurgeryAnesthesiaForm(nullable:true, blank:true)
        bpvWorkSheet(nullable:true, blank:true)
        prcSignOff(nullable:true, blank:true)
        prcSignOffBy(nullable:true, blank:true)
        prcSignOffDate(nullable:true, blank:true)
        bpvSlidePrep(nullable:true, blank:true)
        dmFastTrack(nullable:true, blank:true)
        caseWithdraw(nullable:true, blank:true)
        hasLocalPathReview(nullable:true, blank:true)
        phase(nullable:true, blank:true)
        feedback(nullable:true, blank:true)
        feedbackFzn(nullable:true, blank:true)
        patientRecord(nullable:true, blank:true)
        brainBankFeedback(nullable:true, blank:true)
        bpvSixMonthFollowUp(nullable:true, blank:true)
        batchProcessing(nullable:true, blank:true)
        mtExcluded(nullable:true, blank:true)
    }
}
