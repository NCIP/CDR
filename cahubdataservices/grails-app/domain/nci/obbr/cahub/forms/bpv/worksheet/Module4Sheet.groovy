package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class Module4Sheet extends CDRBaseClass{
   BpvWorkSheet bpvWorkSheet
   Module module 
   
  
   String ttDisecPerformedBy
   String ttComment
   
   String ttFfpeId1
   Float ttFfpeWeight1
   SpecimenRecord ttFfpeSample1
   String ttFfpeTimeStr1
   Date ttFfpeTime1
   String ttFfpeId2
   Float ttFfpeWeight2
   SpecimenRecord ttFfpeSample2
   String ttFfpeTimeStr2
   Date ttFfpeTime2
   String ttFfpeId3
   Float ttFfpeWeight3
   SpecimenRecord ttFfpeSample3
   String ttFfpeTimeStr3
   Date ttFfpeTime3
   
   String ttFrozenId1
   Float ttFrozenWeight1
   SpecimenRecord ttFrozenSample1
   String ttFrozenTimeStr1
   Date ttFrozenTime1
   String ttFrozenId2
   Float ttFrozenWeight2
   SpecimenRecord ttFrozenSample2
   String ttFrozenTimeStr2
   Date ttFrozenTime2
   String ttFrozenId3
   Float ttFrozenWeight3
   SpecimenRecord ttFrozenSample3
   String ttFrozenTimeStr3
   Date ttFrozenTime3
   
    String ttFixativeTimeInRange
    String ttReasonNotInRange
   
    static mapping = {
      table 'bpv_module4_sheet'
      id generator:'sequence', params:[sequence:'bpv_module4_sheet_pk']
   }

    static constraints = {
     
       ttDisecPerformedBy(blank:true,nullable:true)
       ttComment(blank:true,nullable:true, maxSize:4000)
   
       ttFfpeId1(blank:true,nullable:true)
       ttFfpeWeight1(blank:true,nullable:true)
       ttFfpeId2(blank:true,nullable:true)
       ttFfpeWeight2(blank:true,nullable:true)
       ttFfpeId3(blank:true,nullable:true)
       ttFfpeWeight3(blank:true,nullable:true)
   
       ttFrozenId1(blank:true,nullable:true)
       ttFrozenWeight1(blank:true,nullable:true)
       ttFrozenId2(blank:true,nullable:true)
       ttFrozenWeight2(blank:true,nullable:true)
       ttFrozenId3(blank:true,nullable:true)
       ttFrozenWeight3(blank:true,nullable:true)
       
        ttFfpeSample1(blank:true,nullable:true)
        ttFfpeSample2(blank:true,nullable:true)
        ttFfpeSample3(blank:true,nullable:true)
        ttFrozenSample1(blank:true,nullable:true)
        ttFrozenSample2(blank:true,nullable:true)
        ttFrozenSample3(blank:true,nullable:true)
        
        
        ttFfpeTimeStr1(blank:true,nullable:true)
        ttFfpeTime1(blank:true,nullable:true)
        ttFfpeTimeStr2(blank:true,nullable:true)
        ttFfpeTime2(blank:true,nullable:true)
        ttFfpeTimeStr3(blank:true,nullable:true)
        ttFfpeTime3(blank:true,nullable:true)
        
        ttFrozenTimeStr1(blank:true,nullable:true)
        ttFrozenTime1(blank:true,nullable:true)
        ttFrozenTimeStr2(blank:true,nullable:true)
        ttFrozenTime2(blank:true,nullable:true)
        ttFrozenTimeStr3(blank:true,nullable:true)
        ttFrozenTime3(blank:true,nullable:true)
        
        ttFixativeTimeInRange(blank:true,nullable:true)
        ttReasonNotInRange(blank:true,nullable:true, maxSize:4000)
    }
}
