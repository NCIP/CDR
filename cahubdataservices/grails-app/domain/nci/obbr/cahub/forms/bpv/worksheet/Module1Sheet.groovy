package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class Module1Sheet extends CDRBaseClass {
   BpvWorkSheet bpvWorkSheet
   Module module
   
    //Frozon sample
   Timepoint sampleFr
   
   Timepoint sampleQc
   Timepoint sampleA
   Timepoint sampleB
   Timepoint sampleC
   Timepoint sampleD
    
   String whichOvary
   Integer priority
         
    
    static mapping = {
      table 'bpv_module1_sheet'
      id generator:'sequence', params:[sequence:'bpv_module1_sheet_pk']
    }
    
      static constraints = {
    
       whichOvary(blank:true,nullable:true)
       sampleFr(blank:true,nullable:true)
       sampleQc(blank:true,nullable:true)
       priority(blank:true,nullable:true)
       
  
    }
	
}

