package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
// import nci.obbr.cahub.staticmembers.AcquisitionType

class BpvTissueForm extends CDRBaseClass {
    // CaseRecord caseRecord
    SpecimenRecord specimenRecord
    Date     surgDate
    Date     firstIncis
    Date     clamp1Time
    Date     clamp2Time
    Date     resectTime
    String   surgComment
    Date     grossTimeIn
    String   grossDiagx
    Date     grossTimeOut
    String   tissSop
    Date     tissDissecTime
    String   tissComment
    String   tissGrossId
    Date     dissecStartTime
    Date     dissecEndTime

    static belongsTo = SpecimenRecord
    
    String toString(){"$specimenRecord.specimenId"}
    
    static constraints = {

        surgDate(blank:true,nullable:true)
        firstIncis(blank:true,nullable:true)
        clamp1Time(blank:true,nullable:true)
        clamp2Time(blank:true,nullable:true)
        resectTime(blank:true,nullable:true)
        surgComment(blank:true,nullable:true)
        grossTimeIn(blank:true,nullable:true)
        grossDiagx(blank:true,nullable:true)
        grossTimeOut(blank:true,nullable:true)
        tissSop(blank:true,nullable:true)
        tissDissecTime(blank:true,nullable:true)
        tissComment(blank:true,nullable:true)
        tissGrossId(blank:true,nullable:true)
        // acquisType(blank:false,nullable:false) get from specimen record
        // tumorStatus(blank:true,nullable:true) found on and imported with Specimen Record
        dissecStartTime(blank:true,nullable:true)
        dissecEndTime(blank:true,nullable:true)
        // fixative(blank:true,nullable:true) get from specimen record
        
        // protoDelayToFix(blank:false,nullable:false) get from Protocol
        // protoTimeInFix(blank:false,nullable:false) get from Protocol
        
    }
    
    static mapping = {
         table 'bpv_tissue_form'
         id generator:'sequence', params:[sequence:'bpv_tissue_form_pk']
    }
    
}
