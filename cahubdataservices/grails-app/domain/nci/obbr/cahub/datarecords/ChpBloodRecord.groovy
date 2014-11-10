package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*

class ChpBloodRecord extends DataRecordBaseClass {

    SpecimenRecord specimenRecord
    float  volume
    String volUnits
    Date   bloodProcStart  // TODO: Move to BpvBloodForm
    Date   bloodProcEnd    // TODO: Move to BpvBloodForm
    Date   bloodFrozen
    Date   bloodStorage
    String hemolysis
    String bloodProcComment
    String bloodStorageComment
    String freezerType


    static constraints = {
        volume(blank:true,nullable:true)
        volUnits(blank:true,nullable:true)
        bloodProcStart(blank:true,nullable:true)
        bloodProcEnd(blank:true,nullable:true)
        bloodFrozen(blank:true,nullable:true)
        bloodStorage(blank:true,nullable:true)
        hemolysis(blank:true,nullable:true)
        bloodProcComment(blank:true,nullable:true)
        bloodStorageComment(blank:true,nullable:true)
        freezerType(blank:true,nullable:true)
    }
    
    // static belongsTo = SpecimenRecord
    
    String toString(){"$specimenRecord.specimenId"}
    
    static mapping = {
      table 'dr_chp_blood'
      id generator:'sequence', params:[sequence:'dr_chp_blood_pk']
    }
}
 
