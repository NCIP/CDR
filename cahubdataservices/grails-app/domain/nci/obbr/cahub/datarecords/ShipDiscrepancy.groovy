package nci.obbr.cahub.datarecords

import nci.obbr.cahub.CDRBaseClass

class ShipDiscrepancy extends CDRBaseClass{
    
    String discrepancyId
    String shippingContainerId
    String name
    String value
    
    static hasMany = [specimens:SpecimenRecord]
    static hasOne  = [shipEvent:ShippingEvent] 
    static belongsTo = [SpecimenRecord]

    static constraints = {
        
        discrepancyId(nullable:true,blank:true)
        shippingContainerId(nullable:true,blank:true)
        name(nullable:true, blank:true )
        value(nullable:true, blank:true , widget:'textarea',maxSize:4000)
        specimens(nullable:true, blank:true)
        shipEvent(nullable:true, blank:true)
    }
    
     static mapping = {
      table 'dr_ship_discrep'
      id generator:'sequence', params:[sequence:'dr_ship_discrep_pk']
      sort dateCreated:"desc"
    }
}
