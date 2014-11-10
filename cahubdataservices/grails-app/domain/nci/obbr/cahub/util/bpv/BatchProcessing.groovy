package nci.obbr.cahub.util.bpv

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.CDRBaseClass

class BatchProcessing extends CDRBaseClass {
    int processingId
    Date emailDate2M
    Date emailDate2M2
    Date emailDate6M
    Date emailDate6M2
    Date emailDate18M
    Date emailDate18M2
    CaseRecord firstCase
    static hasMany = [members:CaseRecord]
    
     static mapping = {
        table 'batch_processing'
        id generator:'sequence', params:[sequence:'batch_processing_pk']
        sort dateCreated:"desc"  
    }
    
    static constraints = {
        processingId(unique:true, blank:false, nullable:false)
        emailDate2M(blank:true, nullable:true)
        emailDate2M2(blank:true, nullable:true)
        emailDate6M(blank:true, nullable:true)
        emailDate6M2(blank:true, nullable:true)
        emailDate18M(blank:true, nullable:true)
        emailDate18M2(blank:true, nullable:true)
        
    }
}
