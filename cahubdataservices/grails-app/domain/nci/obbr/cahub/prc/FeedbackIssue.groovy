package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class FeedbackIssue extends CDRBaseClass {
    
    SpecimenRecord specimenRecord
   
   
    //String comments
    String resolutionComments
    String issueDescription
    CaseRecord caseRecord
    Boolean forFzn
    FeedbackSubmission submissionCreated
    
     static mapping = {
      table 'feedback_issue'
      id generator:'sequence', params:[sequence:'feedback_issue_pk']
    }

    static constraints = {
         caseRecord(unique:false, nullable:true, blank:true)
         specimenRecord(unique:false, nullable:true, blank:true)
         resolutionComments(nullable:true, blank:true, maxSize:4000)
         issueDescription(nullable:true, blank:true, maxSize:4000)
         forFzn(nullable:true, blank:true)
         submissionCreated(nullable:true, blank:true)
    }
}
