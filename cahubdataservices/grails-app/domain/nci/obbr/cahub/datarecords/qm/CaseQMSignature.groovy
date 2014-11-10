package nci.obbr.cahub.datarecords.qm

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.CDRBaseClass

class CaseQMSignature {
    
    CaseRecord caseRecord
    Date signedDate
    String userId
    String comments
    
    static auditable = true
    
    static constraints = {
        caseRecord(blank:false,nullable:false)
        signedDate(blank:true,nullable:true)
        userId(blank:true,nullable:true)
        comments(blank:true,nullable:true, maxSize:4000)
    }
    
    static mapping = {
      table 'dr_case_qm_signature'
      id generator:'sequence', params:[sequence:'dr_case_qm_signature_pk']
      
    }    
}
