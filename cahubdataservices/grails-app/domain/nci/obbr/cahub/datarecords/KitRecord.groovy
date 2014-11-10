package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.*

class KitRecord extends DataRecordBaseClass{
    
    String kitId
    CaseRecord caseRecord
    Study study
    BSS bss
    KitType kitType
    Date expirationDate
    String identifier
    
    static hasMany = [shipEvents:ShippingEvent]
    
    
    String toString(){"$kitId"}
    
    static constraints = {
        caseRecord(blank:true,nullable:true)
        kitId(blank:false,nullable:false)
        shipEvents(blank:true,nullable:true)
        study(blank:false,nullable:false)
        bss(blank:false,nullable:false)
        kitType(blank:false,nullable:false)   
        expirationDate(blank:true,nullable:true) //not all kits have an expiration date
        identifier(blank:true,nullable:true)
    }
    
   static mapping = {
      table 'dr_kit'
      id generator:'sequence', params:[sequence:'dr_kit_pk']
      
      sort id:"desc"  
    }
}
