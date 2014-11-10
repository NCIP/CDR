package nci.obbr.cahub.util.appaccess

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*

class AppUsers extends CDRBaseClass {
    
    String firstname
    String lastname
    String organizationDetail
    String title
    String email
    String phone
    Organization organization
    
    //added on 11/21/12 : PMH
    // the following 4 are deprecated as of CDR release 5.0 07/2013
    Boolean nihSecurityTraining
    Date nihTrainingDt
    Boolean hipaaTraining
    Date hipaaTrainingDt
    // deprecated as of CDR release 5.0 07/2013
    
    // added on 01/14/13:PMH
    Boolean receiveAlerts
    
    static mapping = {

        table 'app_users'
        id generator:'sequence', params:[sequence:'app_users_pk']
    }    

    static constraints = {
        firstname (nullable:false, blank:false)
        lastname(nullable:false, blank:false)
        organization (nullable:false, blank:false)
        organizationDetail (nullable:false, blank:false)
        title (nullable:false, blank:false)
        email(nullable:false, blank:false, unique:true)
        phone(nullable:false, blank:false)  
        hipaaTraining(nullable:true, blank:true)
        hipaaTrainingDt(nullable:true, blank:true)
        nihSecurityTraining(nullable:true, blank:true)
        nihTrainingDt(nullable:true, blank:true)
        receiveAlerts(nullable:true, blank:true)
    }
    
    String toString(){
        "$firstname $lastname"
    }
    
    
}
