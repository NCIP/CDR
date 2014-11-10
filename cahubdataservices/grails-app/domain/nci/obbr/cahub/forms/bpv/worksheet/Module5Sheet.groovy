package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class Module5Sheet extends CDRBaseClass {
BpvWorkSheet bpvWorkSheet
   Module module
   
    //Frozon sample
   Timepoint sampleFr
  
   
   Timepoint sampleQc
   Timepoint sampleU
   Timepoint sampleV
   Timepoint sampleW
   Timepoint sampleX
  
   
  
   String whichOvary 
   Integer priority
   
    
   static mapping = {
      table 'bpv_module5_sheet'
      id generator:'sequence', params:[sequence:'bpv_module5_sheet_pk']
    }

    static constraints = {
       whichOvary(blank:true,nullable:true)
       priority(blank:true,nullable:true)
      
     
    }
}
