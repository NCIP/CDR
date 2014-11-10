package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*

class FeedbackSubmission  extends CDRBaseClass{
    CaseRecord caseRecord
    //String pathologist
    Date dateSubmitted
    String submittedBy
    Integer feedbackVersion
    Date dateReviewed
    String reviewedBy
    Boolean forFzn
    
    
    static mapping = {
      table 'feedback_submission'
      id generator:'sequence', params:[sequence:'feedback_submission_pk']
    }
    
    
    static constraints = {
        caseRecord(unique:false, nullable:false, blank:false)
       // pathologist(nullable:true, blank:true)
        dateSubmitted(nullable:true, blank:true)
        submittedBy(nullable:true, blank:true)
        feedbackVersion(nullable:true, blank:true)
        dateReviewed(nullable:true, blank:true)
        reviewedBy(nullable:true, blank:true)
        forFzn(nullable:true, blank:true)
    }
}
