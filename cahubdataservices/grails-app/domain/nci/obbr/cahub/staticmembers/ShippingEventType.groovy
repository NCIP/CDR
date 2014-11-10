package nci.obbr.cahub.staticmembers

class ShippingEventType extends StaticMemberBaseClass{
    
     static mapping = {
      table 'st_shipping_event_type'
      id generator:'sequence', params:[sequence:'st_shipping_event_type_pk']
    }

}
