package nci.obbr.cahub.util.appaccess

import nci.obbr.cahub.CDRBaseClass


class AccountStatus extends CDRBaseClass{
    AppUsers  appUser
    ApplicationListing  applicationListing
    Date dateCreated2
    Date dateDeactivated
    String role
    
    //the below line added by me on 07/19

    //static hasMany = [ applisting:ApplicationListing]
    
    
    static mapping = {

        table 'account_status'
        id generator:'sequence', params:[sequence:'account_status_pk']
     }  

    static constraints = {
        dateCreated2(nullable:true, blank:true)
        dateDeactivated(nullable:true, blank:true)
        dateCreated(nullable:true, blank:true)
        role(nullable:true, blank:true)
       
    }
}
