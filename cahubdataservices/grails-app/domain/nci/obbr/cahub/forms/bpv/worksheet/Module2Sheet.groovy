package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class Module2Sheet extends CDRBaseClass {
   BpvWorkSheet bpvWorkSheet
   Module module
   
   Timepoint sampleFr
   
   Timepoint sampleQc
    
   Timepoint sampleE
   Timepoint sampleF
   Timepoint sampleG
   Timepoint sampleH
  
   String whichOvary 
   Integer priority
         
    static mapping = {
      table 'bpv_module2_sheet'
      id generator:'sequence', params:[sequence:'bpv_module2_sheet_pk']
    }
    
      static constraints = {
          
      whichOvary(blank:true,nullable:true)
       sampleFr(blank:true,nullable:true)
       sampleQc(blank:true,nullable:true)
       priority(blank:true,nullable:true)
    }
	
}

