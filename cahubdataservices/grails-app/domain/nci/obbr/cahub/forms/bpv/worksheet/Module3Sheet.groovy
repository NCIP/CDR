package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class Module3Sheet extends CDRBaseClass {
   BpvWorkSheet bpvWorkSheet
   Module module 
   
   
   
  // Date ntDisecBegan
  // Date ntDisecEnded
   String ntDisecPerformedBy
   String ntComment
   
   String ntFfpeId1
   Float ntFfpeWeight1
   SpecimenRecord ntFfpeSample1
   String ntFfpeTimeStr1
   Date ntFfpeTime1
   String ntFfpeId2
   Float ntFfpeWeight2
   SpecimenRecord ntFfpeSample2
   String ntFfpeTimeStr2
   Date ntFfpeTime2
   String ntFfpeId3
   Float ntFfpeWeight3
   SpecimenRecord ntFfpeSample3
    String ntFfpeTimeStr3
   Date ntFfpeTime3
   
   String ntFrozenId1
   Float ntFrozenWeight1
   SpecimenRecord ntFrozenSample1
   String ntFrozenTimeStr1
   Date ntFrozenTime1
   String ntFrozenId2
   Float ntFrozenWeight2
   SpecimenRecord ntFrozenSample2
   String ntFrozenTimeStr2
   Date ntFrozenTime2
   String ntFrozenId3
   Float ntFrozenWeight3
   SpecimenRecord ntFrozenSample3
   String ntFrozenTimeStr3
   Date ntFrozenTime3
   
    String ntFixativeTimeInRange
    String ntReasonNotInRange
   
    
   
   
   
   static mapping = {
      table 'bpv_module3_sheet'
      id generator:'sequence', params:[sequence:'bpv_module3_sheet_pk']
   }
    
    static constraints = {
      
   
      // ntDisecBegan(blank:true,nullable:true)
      // ntDisecEnded(blank:true,nullable:true)
       ntDisecPerformedBy(blank:true,nullable:true)
       ntComment(blank:true,nullable:true, maxSize:4000)
   
       ntFfpeId1(blank:true,nullable:true)
       ntFfpeWeight1(blank:true,nullable:true)
       ntFfpeId2(blank:true,nullable:true)
       ntFfpeWeight2(blank:true,nullable:true)
       ntFfpeId3(blank:true,nullable:true)
       ntFfpeWeight3(blank:true,nullable:true)
   
       ntFrozenId1(blank:true,nullable:true)
       ntFrozenWeight1(blank:true,nullable:true)
       ntFrozenId2(blank:true,nullable:true)
       ntFrozenWeight2(blank:true,nullable:true)
       ntFrozenId3(blank:true,nullable:true)
       ntFrozenWeight3(blank:true,nullable:true)
       
        
        ntFfpeSample1(blank:true,nullable:true)
        ntFfpeSample2(blank:true,nullable:true)
        ntFfpeSample3(blank:true,nullable:true)
        ntFrozenSample1(blank:true,nullable:true)
        ntFrozenSample2(blank:true,nullable:true)
        ntFrozenSample3(blank:true,nullable:true)
        
        
        ntFfpeTimeStr1(blank:true,nullable:true)
        ntFfpeTime1(blank:true,nullable:true)
        ntFfpeTimeStr2(blank:true,nullable:true)
        ntFfpeTime2(blank:true,nullable:true)
        ntFfpeTimeStr3(blank:true,nullable:true)
        ntFfpeTime3(blank:true,nullable:true)
        
        ntFrozenTimeStr1(blank:true,nullable:true)
        ntFrozenTime1(blank:true,nullable:true)
        ntFrozenTimeStr2(blank:true,nullable:true)
        ntFrozenTime2(blank:true,nullable:true)
        ntFrozenTimeStr3(blank:true,nullable:true)
        ntFrozenTime3(blank:true,nullable:true)
        
        
        ntFixativeTimeInRange(blank:true,nullable:true)
        ntReasonNotInRange(blank:true,nullable:true, maxSize:4000)
        
    }
}
