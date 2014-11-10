package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord

class TissueRecoveryBrain  extends CDRBaseClass{
     CaseRecord caseRecord
     Date dateSubmitted
     boolean started=false
     String uploadedFilename_bt
     
    
    
      static hasMany = [brainTissues:BrainTissue]
      
     static mapping = {
                table 'brain_tissue_recovery'
                id generator:'sequence', params:[sequence:'brain_tissue_recovery_pk']
     }           

    
    static constraints = {
        dateSubmitted(nullable:true, blank:true)
        uploadedFilename_bt(nullable:true, blank:true)
    }
}
