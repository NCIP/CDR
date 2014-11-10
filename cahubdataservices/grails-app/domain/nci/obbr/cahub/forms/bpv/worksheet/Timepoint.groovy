package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class Timepoint extends CDRBaseClass{
    
   SpecimenRecord sample
   String sampleId4Record
   Date  dateSampleRecorded
   String dateSampleRecordedStr
   String sampleId4Fixative
   Date   dateSampleInFixative
   String dateSampleInFixativeStr
  
   String sampleId4Proc
   Date   dateSampleInProc
   String dateSampleInProcStr
   String sampleId4ProcEnd
   Date   dateSampleProcEnd
   String dateSampleProcEndStr
   String sampleId4Removal 
   Date   dateSampleRemoved   
   String dateSampleRemovedStr
   String sampleId4Embedding
   Date   dateSampleEmbStarted
   String dateSampleEmbStartedStr
   String sampleId4Frozen
   Date   dateSampleFrozen
   String dateSampleFrozenStr
   String sampleId4Trans
   Date   dateSampleTrans
   String dateSampleTransStr
  // Integer displayOrder
   String protocol
   String plannedDelay
   //String rowColor
   String protocolId
   String freezeMethod
   String transTo
   Float weight
   
    
    static mapping = {
      table 'bpv_timepoint'
      id generator:'sequence', params:[sequence:'bpv_timepoint_pk']
    }
    
    static constraints = {
         sample(blank:true,nullable:true)
         sampleId4Record(blank:true,nullable:true)
         dateSampleRecorded(blank:true,nullable:true)
         dateSampleRecordedStr(blank:true,nullable:true)
         sampleId4Fixative(blank:true,nullable:true)
         dateSampleInFixative(blank:true,nullable:true)
         dateSampleInFixativeStr(blank:true,nullable:true)
         sampleId4Proc(blank:true,nullable:true)
         dateSampleInProc(blank:true,nullable:true)
         dateSampleInProcStr(blank:true,nullable:true)
         sampleId4ProcEnd(blank:true,nullable:true)
         dateSampleProcEnd(blank:true,nullable:true)
         dateSampleProcEndStr(blank:true,nullable:true)
         sampleId4Removal(blank:true,nullable:true) 
         dateSampleRemoved(blank:true,nullable:true)
         dateSampleRemovedStr(blank:true,nullable:true)
         sampleId4Embedding(blank:true,nullable:true)
         dateSampleEmbStarted(blank:true,nullable:true)
         dateSampleEmbStartedStr(blank:true,nullable:true)
         sampleId4Frozen(blank:true,nullable:true)
         dateSampleFrozen(blank:true,nullable:true)
         dateSampleFrozenStr(blank:true,nullable:true)
         sampleId4Trans(blank:true,nullable:true)
         dateSampleTrans(blank:true,nullable:true)
         dateSampleTransStr(blank:true,nullable:true)
         // displayOrder(blank:true,nullable:true)
         protocol(blank:true,nullable:true)
         plannedDelay(blank:true,nullable:true)
         //rowColor(blank:true,nullable:true)
         protocolId(blank:true,nullable:true)
         freezeMethod(blank:true,nullable:true)
         transTo(blank:true,nullable:true)
         weight(blank:true,nullable:true)
    }
}
