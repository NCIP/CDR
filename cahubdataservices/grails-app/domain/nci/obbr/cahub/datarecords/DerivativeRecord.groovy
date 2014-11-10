package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*

class DerivativeRecord extends DataRecordBaseClass{

    SpecimenRecord parentSpecimen
    String derivativeId    
    Fixative fixative
    ContainerType containerType
    String derivativeType //CBR terminology used to track derivative types
    String eventId
    DerivativeRecord parentDerivative
    
    static hasMany = [shippingEvents:ShippingEvent, processEvents:ProcessingEvent]

    String toString(){"$derivativeId"}

    static mapping = {
        table 'dr_derivative'
        id generator:'sequence', params:[sequence:'dr_derivative_pk']
    }     
    
    
    static constraints = {
        parentSpecimen(blank:true,nullable:true)
        derivativeId(blank:false,nullable:false,unique:true)      
        fixative(blank:true,nullable:true)  
        containerType(blank:true,nullable:true)          
        derivativeType(blank:true,nullable:true)   
        eventId(blank:true,nullable:true)
        shippingEvents(blank:true,nullable:true)
        processEvents(blank:true,nullable:true)
        parentDerivative(blank:true,nullable:true)
    }
}
