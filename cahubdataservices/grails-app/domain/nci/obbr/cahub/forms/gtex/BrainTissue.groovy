package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.SpecimenRecord

class BrainTissue extends CDRBaseClass{

    TissueRecoveryBrain tissueRecoveryBrain
    String position
    String sampleId
    String collSampleId
    String collSampleId2
    String collectionDateStr
    String collectionTimeStr
    Date collectionDate
    AcquisitionType tissueType 
    Float mass
    String notes
    String interval
    String rin
    Boolean collected=true
    
    SpecimenRecord specimenRecord
      static transients = ['interval', 'rin']
    
    
     static mapping = {
                table 'brain_tissue'
                id generator:'sequence', params:[sequence:'brain_tissue_pk']
     }           

    
    static constraints = {
         collSampleId(nullable:true, blank:true)
         collSampleId2(nullable:true, blank:true)
         mass(nullable:true, blank:true)
         collectionDateStr(nullable:true, blank:true)
         collectionTimeStr(nullable:true, blank:true)
         collectionDate(nullable:true, blank:true)
         mass(nullable:true, blank:true)
         notes(nullable:true, blank:true, maxSize:4000)
         specimenRecord(nullable:true, blank:true)
         sampleId(nullable:true, blank:true, unique:true)
         collected(nullable:true, blank:true)
         
    }
}
