package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class PrcReportFzn extends CDRBaseClass {

  CaseRecord caseRecord
    String stainingOfSlides
    String stainingOfImages
    String processing
    String comments
    String amputationType1
    String amputationType2
    PrcReportSubmission currentSubmission
    String status
    String reviewedBy
    Date reviewDate 
   
    
    
     static mapping = {
      table 'prc_report_fzn'
      id generator:'sequence', params:[sequence:'prc_report_fzn_pk']
    }
    
    static constraints = {
        caseRecord(unique:true, nullable:false, blank:false)
        stainingOfSlides(nullable:true, blank:true)
        stainingOfImages(nullable:true, blank:true)
        processing(nullable:true, blank:true)
        comments(nullable:true, blank:true, maxSize:4000)
        amputationType1(nullable:true, blank:true, maxSize:30)
        amputationType2(nullable:true, blank:true, maxSize:30)
        reviewedBy(nullable:true, blank:true)
        reviewDate(nullable:true, blank:true)
      
    }
}
