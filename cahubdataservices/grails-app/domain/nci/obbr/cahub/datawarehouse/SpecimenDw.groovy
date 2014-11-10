package nci.obbr.cahub.datawarehouse

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.DataRecordBaseClass
import nci.obbr.cahub.datarecords.SpecimenRecord

class SpecimenDw extends DataRecordBaseClass {

    SpecimenRecord specimenRecord
    CaseDw caseDw
    String specimenId
    AcquisitionType tissueType //final tissue type
    AcquisitionLocation tissueLocation
    Fixative fixative
    Double ischemicTime
    Double latestRin
    
    static auditable = false
    
    String toString(){"$specimenId"}
    
    static belongsTo = [caseDw: CaseDw]
    
    static mapping = {
        table 'dw_specimen'
        id generator:'sequence', params:[sequence:'dw_specimen_pk']
        specimenId column:'specimen_id', index:'dw_specId_idx'
    }
    static constraints = {
        specimenRecord(blank:false,nullable:false)
        specimenId(blank:false,nullable:false,unique:true)
        tissueType(blank:false,nullable:false)
        tissueLocation(blank:true,nullable:true)
        fixative(blank:true,nullable:true)
        ischemicTime(blank:true,nullable:true)
        latestRin(blank:true,nullable:true)
    }
}
