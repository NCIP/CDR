package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*

class PrcIssueResolution extends CDRBaseClass {
    PrcIssue prcIssue
    String resolutionComments
    String issueDescription
    PrcReportSubmission prcReportSubmission
    
    static mapping = {
      table 'prc_issue_resolution'
      id generator:'sequence', params:[sequence:'prc_issue_resolution_pk']
    }
    
    static constraints = {
         prcIssue(unique:false, nullable:false, blank:false)
         resolutionComments(nullable:true, blank:true, maxSize:4000)
         issueDescription(nullable:true, blank:true, maxSize:4000)
         prcReportSubmission(unique:false, nullable:false, blank:false)
    }

    
}
