package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*

class ShippingEvent extends DataRecordBaseClass {

    String bio4dEventId
    Study study    
    String sender
    String recipient
    String courier
    String trackingNumber
    Date shipDateTime
    String sendingUser
    ShippingContentType shippingContentType
    ShippingEventType shippingEventType
    String caseId
    Date receiptDateTime
    String receivingUser
    String isBatchShipment
    String sourceShipEvt
    String kitType
    String identifier
    
    String toString(){"$bio4dEventId"}

    static hasMany = [kits:KitRecord, slides:SlideRecord, specimens:SpecimenRecord, discreps:ShipDiscrepancy, derivatives:DerivativeRecord]
    static belongsTo = [KitRecord, SlideRecord, SpecimenRecord, DerivativeRecord]

    
  
    static constraints = {
        bio4dEventId(blank:false,nullable:false)
        study(blank:false,nullable:false)                
        sender(blank:false,nullable:false)
        recipient(blank:false,nullable:false)
        courier(blank:false,nullable:false)
        trackingNumber(blank:false,nullable:false)
        shipDateTime(blank:false,nullable:false)
        sendingUser(blank:true,nullable:true)
        shippingContentType(blank:true,nullable:true)
        shippingEventType(blank:true,nullable:true)
        kits(blank:true,nullable:true)
        slides(blank:true,nullable:true)
        specimens(blank:true,nullable:true)
        caseId(blank:true,nullable:true)
        receiptDateTime(blank:true,nullable:true)
        receivingUser(blank:true,nullable:true)
        discreps(blank:true,nullable:true)
        isBatchShipment(blank:true,nullable:true)
        sourceShipEvt(blank:true,nullable:true)
        kitType(blank:true,nullable:true)
        identifier(blank:true,nullable:true)
        derivatives(blank:true,nullable:true)
    }
    
    
    static mapping = {
      table 'dr_shipevt'
      id generator:'sequence', params:[sequence:'dr_shipevt_pk']
      
      sort id:"desc"  
        
    }
}
