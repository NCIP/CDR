package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class Feedback extends CDRBaseClass {
     CaseRecord caseRecord
     String comments
     Boolean hasIssue
     FeedbackSubmission currentSubmission
     String status
     String reviewedBy
    Date reviewDate 
    Boolean started = false
     
    
    static mapping = {
      table 'feedback'
      id generator:'sequence', params:[sequence:'feedback_pk']
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
