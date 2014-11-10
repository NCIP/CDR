package nci.obbr.cahub.datarecords.qm

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.CahubUnacceptable

/* pmh 08/13/2014 for GTEx cases only */

class CahubAcceptable extends CDRBaseClass{

    CaseRecord caseRecord
    Date notAcceptableDate
    String comments
    String otherReason
    String acceptable='true' 
    String submittedBy
    CahubUnacceptable cahubUnacceptable   // reasons why a case is not acceptable
    
  
   
    static constraints = {
        caseRecord(blank:false,nullable:false)
        notAcceptableDate(blank:true,nullable:true)
        comments(blank:true,nullable:true, maxSize:4000)
        acceptable(blank:true,nullable:true)
        submittedBy(blank:true,nullable:true)
        otherReason(blank:true,nullable:true)
        cahubUnacceptable(blank:true,nullable:true)
        
    }
    
    static mapping = {
      table 'dr_cahub_acceptable'
      id generator:'sequence', params:[sequence:'dr_cahub_acceptable_pk']
      
    }    
}
