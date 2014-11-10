package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*

class QcAndFrozenSample extends CDRBaseClass{

    BpvWorkSheet bpvWorkSheet
    
   Timepoint sampleFr
   Float sampleFrWeight
    
   Timepoint sampleQc
   
    Module moduleAssigned
    Module moduleInput
    
    Boolean started
    
    
    static mapping = {
      table 'bpv_qc_and_frozen_sample'
      id generator:'sequence', params:[sequence:'bpv_qc_and_frozen_sample_pk']
    }
    
    
    static constraints = {
           sampleFrWeight(blank:true,nullable:true)
           moduleAssigned(blank:true,nullable:true)
           moduleInput(blank:true,nullable:true)
           started(blank:true,nullable:true)
    }
}
