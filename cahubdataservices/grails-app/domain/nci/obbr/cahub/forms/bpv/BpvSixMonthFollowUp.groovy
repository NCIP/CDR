package nci.obbr.cahub.forms.bpv
import nci.obbr.cahub.datarecords.*

class BpvSixMonthFollowUp extends BpvFormBaseClass{
    
    CaseRecord caseRecord
    String donorStatus
    String recurrence
    String comments
    Boolean dmReviewed = false
    Date  dmReviewDate

    static constraints = {
        caseRecord(unique:false, nullable:false, blank:false)
        donorStatus(blank:true, nullable:true)
        recurrence(blank:true, nullable:true)
        dmReviewed(blank:true, nullable:true)
        dmReviewDate(blank:true, nullable:true)
        comments(blank:true, nullable:true, widget:'textarea', maxSize:4000)
    }
    
    static mapping = {
        table 'bpv_sixmonth_followup'
        id generator:'sequence', params:[sequence:'bpv_sixmonth_followup_pk']
    }
}
