package nci.obbr.cahub.prc
import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*

class PrcIssue extends CDRBaseClass {
    SpecimenRecord specimenRecord
    //Date issueRequestDate
   // String releaseToInventory
    String pendingFurtherFollowUp
    String resolved
    //String comments
    String resolutionComments
    String issueDescription
    PrcReportSubmission submissionCreated
    CaseRecord caseRecord
    Boolean forQc
    Boolean forFzn
    
    static mapping = {
      table 'prc_issue'
      id generator:'sequence', params:[sequence:'prc_issue_pk']
    }
    
    static constraints = {
         specimenRecord(unique:false, nullable:true, blank:true)
         caseRecord(unique:false, nullable:true, blank:true)
         //issueRequestDate(nullable:true, blank:true, maxSize:10)
         //comments(nullable:true, blank:true)
       //  releaseToInventory(nullable:true, blank:true, maxSize:10)
         pendingFurtherFollowUp(nullable:true, blank:true, maxSize:10)
         resolutionComments(nullable:true, blank:true, maxSize:4000)
         issueDescription(nullable:true, blank:true, maxSize:4000)
         resolved(nullable:true, blank:true)
         forQc(nullable:true, blank:true)
         forFzn(nullable:true, blank:true)
    }
}
