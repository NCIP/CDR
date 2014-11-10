package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*

class PrcReportSubmission  extends CDRBaseClass{
    CaseRecord caseRecord
    //String pathologist
    Date dateSubmitted
    String submittedBy
    Integer reportVersion
    Date dateReviewed
    String reviewedBy
    Boolean forFzn
    
    
    static mapping = {
      table 'prc_report_submission'
      id generator:'sequence', params:[sequence:'prc_report_submission_pk']
    }
    
    
    static constraints = {
        caseRecord(unique:false, nullable:false, blank:false)
       // pathologist(nullable:true, blank:true)
        dateSubmitted(nullable:true, blank:true)
        submittedBy(nullable:true, blank:true)
        reportVersion(nullable:true, blank:true)
        dateReviewed(nullable:true, blank:true)
        reviewedBy(nullable:true, blank:true)
        forFzn(nullable:true, blank:true)
    }
}
