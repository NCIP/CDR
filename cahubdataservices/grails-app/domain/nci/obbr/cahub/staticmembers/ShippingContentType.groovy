package nci.obbr.cahub.staticmembers

class ShippingContentType extends StaticMemberBaseClass{

     static mapping = {
      table 'st_shipping_content_type'
      id generator:'sequence', params:[sequence:'st_shipping_content_type_pk']
    }
}
