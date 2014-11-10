package nci.obbr.cahub.util

class UserLogin {
     String sessionId
     String username
     Date    loginDate
     Date sessionCreated
     Date sessionLastAccessed
     Date sessionDestroyed
     Boolean logout
     String organization
     String application
     
    
     static mapping = {
      table 'user_login'
      id generator:'sequence', params:[sequence:'user_login_pk']
       sort loginDate : 'desc'

     }
    
    static constraints = {
         sessionId(nullable:true, blank:true) 
         sessionCreated(nullable:true, blank:true) 
         sessionLastAccessed(nullable:true, blank:true) 
         sessionDestroyed(nullable:true, blank:true) 
         logout(nullable:true, blank:true)
         organization(nullable:true, blank:true)
         application(nullable:true, blank:true)
    }
}
