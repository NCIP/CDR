package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class FeedbackFzn extends CDRBaseClass {
     CaseRecord caseRecord
     String comments
     Boolean hasIssue
     String reviewedBy
     Date reviewDate 
     FeedbackSubmission currentSubmission
     String status
     Boolean started = false
    
    static mapping = {
      table 'feedback_fzn'
      id generator:'sequence', params:[sequence:'feedback_fzn_pk']
    }

    static constraints = {
         caseRecord(unique:true, nullable:false, blank:false)
         comments(nullable:true, blank:true, maxSize:4000)
         hasIssue(nullable:true, blank:true)
          reviewDate(nullable:true, blank:true)
         reviewedBy (nullable:true, blank:true)
         currentSubmission(nullable:true, blank:true)
         status(nullable:true, blank:true)
         started(nullable:true, blank:true)
    }
}
