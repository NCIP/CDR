package nci.obbr.cahub.forms.common.withdraw

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.util.FileUpload


class CaseWithdraw {

    CaseRecord caseRecord
    
    //BSS form entries
    Date dateWithdrawalRcvd
   
    String requestInitiator
    
    boolean reasonMedical=false
    boolean reasonPersonal=false
    boolean reasonCultural=false
    boolean reasonReligious=false
    boolean reasonUnknown=false

    boolean reasonUndocumConsent=false
    boolean reasonProcError=false
    boolean reasonInclExcl=false
    boolean reasonOther=false
    String reasonOtherDescrip
      
    String reasonComments
    String withdrawReqType
    String withdrawReqTypeOther
    String bssNameCompletingForm
    String bssSiteComments
    Date dateBSSEntrySubmitted
   
    // final review 
    String elrReviewer
    Date dateELRReviewed
    String  opsReviewer
    Date    dateOPSReviewed
    String   qmReviewer
    Date   dateQMReviewed
    String   directorReviewer
    Date   dateDirectorReviewed
    
   
    // Recall Approve fields
    Date dateAckRcvdByCDR
    String ackRcvdYesNOCDR
    String cahubApproveOrNot
    String apprComments
    String approvedBy
    Date dateApproved
    Boolean assignToCDR=false
    Boolean assignToCBR=false
    Boolean assignToPRC=false
    Boolean assignToBrainbank=false
    Boolean assignToLDACC=false
    
    // FINAL NOTIFICATION BY BSS TO DONOR OR NOK
    
    Boolean finalNotifyviaMail=false
    Boolean finalNotifyviaPhone=false
    Boolean finalNotifyviaEmail=false
    Boolean finalNotifyviaOth=false
    String finalNotifyOthType
    
    String finalAnyOtherNotified
    Date finalCanNotifyDate
    Date finalNotifiedDate
    Date finalprovideProofDate
    Date finalCompleteDate
    String finalCompleteBy
   
    //booleans to determin if form has started or not
    Boolean bssSectionA=false
    Boolean aprSectionB=false
    Boolean elrSectionD=false
    Boolean qmSectionD=false
    Boolean opsSectionD=false
    Boolean drSectionD=false
    
    //pmh 03/28/14
    Date dateCreated
    Date lastUpdated
    
        
    // to determine whether case is active, or withdraw is in progress or withdrawn
    String caseStatus
    
    static belongsTo = CaseRecord
    
    static hasMany = [casewithdrawform:CaseWithdrawForms]
    
    String toString(){"$caseRecord.caseId"}
    
    static auditable = true
    
    static constraints = {
        
        caseRecord(blank:false, nullable:false)
        
       
        dateWithdrawalRcvd(blank:true, nullable:true)
      
        reasonOtherDescrip(blank:true, nullable:true)
       
        reasonComments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
       
        withdrawReqType(blank:true, nullable:true)
        withdrawReqTypeOther(blank:true, nullable:true)
        caseStatus(blank:true, nullable:true)
        
        bssNameCompletingForm(blank:true, nullable:true)
       
        bssSiteComments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
       
        dateBSSEntrySubmitted(blank:true, nullable:true)
       
        // apr fields- form 2
        
        dateAckRcvdByCDR(blank:true, nullable:true)
        ackRcvdYesNOCDR(blank:true, nullable:true)
        cahubApproveOrNot(blank:true, nullable:true)
        apprComments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        approvedBy(blank:true, nullable:true)
        dateApproved(blank:true, nullable:true)
        
        // final reviews
        elrReviewer(blank:true, nullable:true)
        dateELRReviewed(blank:true, nullable:true)
        opsReviewer(blank:true, nullable:true)
        dateOPSReviewed(blank:true, nullable:true)
        qmReviewer(blank:true, nullable:true)
        dateQMReviewed(blank:true, nullable:true)
        directorReviewer(blank:true, nullable:true)
        dateDirectorReviewed(blank:true, nullable:true)
        
        // after recall fulfill & notify
        
        finalNotifyOthType(blank:true, nullable:true)
        finalAnyOtherNotified(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        finalCanNotifyDate(blank:true, nullable:true)
        finalNotifiedDate(blank:true, nullable:true)
        finalprovideProofDate(blank:true, nullable:true)
        finalCompleteDate(blank:true, nullable:true)
        finalCompleteBy(blank:true, nullable:true)
        requestInitiator(blank:true, nullable:true)
        dateCreated(blank:true, nullable:true)
        lastUpdated(blank:true, nullable:true)
        
    }
    
    static mapping = {
        table 'case_withdraw'
        id generator:'sequence', params:[sequence:'case_withdraw_pk']
    }
}
