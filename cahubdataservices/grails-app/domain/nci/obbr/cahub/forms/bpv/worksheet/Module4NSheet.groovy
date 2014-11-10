package nci.obbr.cahub.forms.bpv.worksheet
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.CDRBaseClass
class Module4NSheet extends CDRBaseClass{
   BpvWorkSheet bpvWorkSheet
   Module module
   
    //Frozon sample
   Timepoint sampleFr
   
   Timepoint sampleQc
   Timepoint sampleO
   Timepoint sampleP
   Timepoint sampleQ
   Timepoint sampleR
   Timepoint sampleS
   Timepoint sampleT
   
 
   String whichOvary
   Integer priority
   
    
   static mapping = {
      table 'bpv_module4n_sheet'
      id generator:'sequence', params:[sequence:'bpv_module4n_sheet_pk']
    }

    static constraints = {
       whichOvary(blank:true,nullable:true)
       priority(blank:true,nullable:true)
      
     
    }
}
