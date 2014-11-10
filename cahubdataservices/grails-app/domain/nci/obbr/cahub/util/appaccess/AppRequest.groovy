package nci.obbr.cahub.util.appaccess

import nci.obbr.cahub.CDRBaseClass

class AppRequest extends CDRBaseClass{
    
    AppUsers appUser
    String approvedBy
    //AccountAction[]  accountActions
    String status
    Date dateSubmitted
    Date dateCompleted
    Date dateAssigned
    String action 
    String role
    ApplicationListing applicationListing
    
    String performedBy
    Date datePerformed
    String otherDescription
    
   
    //static hasMany = [ accountActions:AccountAction]
    
    
    static mapping = {

        table 'app_request'
        id generator:'sequence', params:[sequence:'app_request_pk']
      
         
     }  

    static constraints = {
        
        approvedBy(nullable:true,blank:true)
        //status (inList:["created", "submitted", "completed"])
        dateSubmitted(nullable:true,blank:true)
        dateCompleted(nullable:true,blank:true)
        status(nullable:true,blank:true)
        dateAssigned(nullable:true,blank:true)
        // new fields from old account action class
        role(nullable:true, blank:true)
        performedBy(nullable:true, blank:true)
        action(nullable:true, blank:true)
        datePerformed(nullable:true, blank:true)
        applicationListing(nullable:true, blank:true)
        //otherDescription(nullable:true, blank:true)
        otherDescription(blank:true, nullable:true,widget:'textarea',maxSize:4000)
    }
    
    String toString(){
        appUser.id
    }
}
