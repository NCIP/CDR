package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*

class FeedbackIssueResolution extends CDRBaseClass {
    FeedbackIssue feedbackIssue
    String resolutionComments
    String issueDescription
    FeedbackSubmission feedbackSubmission
    
    static mapping = {
      table 'feedback_issue_resolution'
      id generator:'sequence', params:[sequence:'feedback_issue_resolution_pk']
    }
    
    static constraints = {
         feedbackIssue(unique:false, nullable:false, blank:false)
         resolutionComments(nullable:true, blank:true, maxSize:4000)
         issueDescription(nullable:true, blank:true, maxSize:4000)
         feedbackSubmission(unique:false, nullable:false, blank:false)
    }

    
}

