package nci.obbr.cahub.util.appaccess

import nci.obbr.cahub.CDRBaseClass

class AccountAction extends CDRBaseClass{
    
    String action 
    String role
    ApplicationListing applicationListing
    AppRequest appRequest
    String performedBy
    Date datePerformed
    Boolean notified=false
    
    
    

    
    
    static mapping = {

        table 'account_action'
        id generator:'sequence', params:[sequence:'account_action_pk']
     }  
     
    static constraints = {
        //appname(blank:false)
        //action (inList:["create", "deactivate", "changeRole","none"])
        role(nullable:true, blank:true)
        performedBy(nullable:true, blank:true)
        action(nullable:true, blank:true)
        datePerformed(nullable:true, blank:true)
        notified(nullable:true, blank:true)
    }
}
