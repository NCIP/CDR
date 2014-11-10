package nci.obbr.cahub.util.appaccess

import nci.obbr.cahub.staticmembers.StaticMemberBaseClass

class ApplicationListing extends StaticMemberBaseClass {

    
    String opEmail
    String name
    
    static mapping = {

        table 'app_listing'
        id generator:'sequence', params:[sequence:'app_listing_pk']
     }  
    

    static constraints = {
        
         opEmail (nullable:true, blank:true)
    }
    
    String toString() {
        name
    }
}
