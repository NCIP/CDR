
package nci.obbr.cahub.util

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import java.util.*;
import nci.obbr.cahub.util.UserLogin
import org.apache.commons.logging.LogFactory

class CDRSessionListener implements HttpSessionListener{
    private static final log = LogFactory.getLog(this)
    
     @Override
    public void sessionCreated(HttpSessionEvent e) {
        
    }
    
    
    
    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
       def appName = grails.util.Metadata.current.'app.name'
      //  println("in listner: " + appName)
       try{
          HttpSession s = e.getSession();
          def createdTime = s.getCreationTime();
          def lastAccessedTime = s.getLastAccessedTime();
          def sessionCreated = new Date(createdTime)
          def sessionLastAccessed = new Date(lastAccessedTime)
          def logout = s.getAttribute("logout")
          def org = s.org
          if(!logout){
              logout = new Boolean(false)
          }
          def sessionDestroyed = new Date()
             UserLogin.withTransaction { status ->
          def userLogin = UserLogin.findBySessionId(s.getId())
          if(userLogin){
             
              userLogin.sessionCreated = sessionCreated
              userLogin.sessionLastAccessed = sessionLastAccessed
              userLogin.sessionDestroyed = sessionDestroyed
              userLogin.logout = logout
              userLogin.organization =org
              userLogin.save(failOnError:true,flush:true)
          }else{
              def username = s.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
              if(username){
                
                 //if (username != 'ldaccservice')
                 //{
                    userLogin   = new UserLogin()
                    userLogin.username = username
                    userLogin.loginDate = sessionCreated
                    userLogin.sessionId = s.getId()
                    userLogin.sessionCreated = sessionCreated
                    userLogin.sessionLastAccessed = sessionLastAccessed
                    userLogin.sessionDestroyed = sessionDestroyed
                    userLogin.logout = logout
                    userLogin.organization =org
                    userLogin.application=appName
                    userLogin.save(failOnError:true,flush:true)
                 //}
                  
                // UserLoginMap.getInstance().deleteElement(s.getId())
              }
          }
          
           
             }
          
       }catch(Exception exc){
          
            log.error((new Date()).toString() + " failed in session destroy: " + exc)
       }  
         
          
    }	
	
}

