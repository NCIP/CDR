package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.CDRBaseClass

class Module3NSheet extends CDRBaseClass{
   BpvWorkSheet bpvWorkSheet
   Module module
   
    //Frozon sample
   Timepoint sampleFr
  
   
   Timepoint sampleQc
   Timepoint sampleI
   Timepoint sampleJ
   Timepoint sampleK
   Timepoint sampleL
   Timepoint sampleM
   Timepoint sampleN
   
  
   String whichOvary 
   Integer priority
   
    
   static mapping = {
      table 'bpv_module3n_sheet'
      id generator:'sequence', params:[sequence:'bpv_module3n_sheet_pk']
    }

    static constraints = {
       whichOvary(blank:true,nullable:true)
       priority(blank:true,nullable:true)
      
     
    }
}
