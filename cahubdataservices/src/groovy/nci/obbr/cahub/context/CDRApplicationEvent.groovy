package nci.obbr.cahub.context

import nci.obbr.cahub.util.UserLogin
import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.Organization

import org.codehaus.groovy.grails.web.util.WebUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityEventListener

import org.springframework.context.ApplicationEvent
import org.springframework.security.web.WebAttributes
import org.springframework.security.authentication.InsufficientAuthenticationException

import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent
import nci.obbr.cahub.util.*


class CDRApplicationEvent extends SecurityEventListener {
    def grailsApplication

    @Override
    public void onApplicationEvent(final ApplicationEvent e) {
        if(e instanceof AuthenticationSuccessEvent) {
            logUser(e.source)
            loadUserSession(e.source)
        } else if(e instanceof AuthenticationFailureCredentialsExpiredEvent) {
            def session = WebUtils.retrieveGrailsWebRequest().session
            String username = session[WebAttributes.LAST_USERNAME]

            if(username) {
                RegistrationCode.withTransaction { status ->
                    def registrationCode = RegistrationCode.findByUsername(username)
                    if(!registrationCode) {
                        registrationCode = new RegistrationCode(username: username)
                        registrationCode.save(flush: true)
                    }
                    session['EXPIRED_PASSWORD_TOKEN'] = registrationCode?.token
                }
            }
        }
    }

    protected void logUser(source) {
        try {
            def username = source.principal.username
            def sessionId = source.details.sessionId

            if(sessionId) {
                UserLogin.withTransaction { status ->
                    //if (username != 'ldaccservice')
                    //{
                        def userLogin = new UserLogin()
                        userLogin.username = username
                        userLogin.loginDate = new Date()
                        userLogin.sessionId = sessionId
                        userLogin.application = grailsApplication.metadata.'app.name'
                        userLogin.save(failOnError:true, flush:true)
                    //}
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace()
            throw ex
        }
    }

    protected void loadUserSession(source) {
        try {
            def session = WebUtils.retrieveGrailsWebRequest().session
            session.appVer = grailsApplication.metadata.'app.version'

            //Get roles from Spring Security
            def authorities =  source.authorities*.authority
            session.authorities = authorities
            
            def bss
            def organization
            def role

            //This would be better if it wasn't hardcoded.  TODO: make a role list from a constants file
            //These are set from the original bootstrap.groovy and maintained in the Spring Security UI plugin
            //Test if incoming role is an outside organization or BSS:
            if(authorities.contains("ROLE_BSS_NDRI") ||
                authorities.contains("ROLE_BSS_RPCI") ||
                authorities.contains("ROLE_ORG_VARI") ||
                authorities.contains("ROLE_ORG_BROAD") ||
                authorities.contains("ROLE_BSS_UNM") ||
                authorities.contains("ROLE_BSS_BMC") ||
                authorities.contains("ROLE_BSS_UPMC") ||
                authorities.contains("ROLE_BSS_EU") ||
                authorities.contains("ROLE_ORG_MBB") ||
                authorities.contains("ROLE_BSS_SC") ||
                authorities.contains("ROLE_BSS_VUMC")) {

                //Get the current role.  

                
                authorities.each{

                    if(it.toString() != "ROLE_SERVICE"){ //exclude service accounts
                    
                        role = it.toString()
                        role = role.substring(9).toString()

                        //Try for a BSS
                        bss = BSS.findByCode(role)
                        //Try for an Organization
                        organization = Organization.findByCode(role)

                        if(bss || organization){ 
                            return true  //break
                        }
                    }
                }
       
                //Polymorphically assign either a BSS or Organization (Gosh I love Groovy)
                def org = bss ?: organization

                //Put a seperate user and org object into session.
                //session.user = user -- taking out because getting unexpected results with session timeout
                session.org = org
                if(role in ['VARI','BROAD','MBB']) {
                    //set session privilege flags
                    session.DM = false
                    session.PRC = false
                    session.LDS = false
                } else if(role in ['NDRI','RPCI','UNM','BMC','UPMC','EU','SC','VUMC',]) {
                    //set session privilege flags
                    session.DM = true
                    session.PRC = false
                    session.LDS = true
                }
                
               if(role in ['NDRI','RPCI','SC']) {
                    def study = Study.findByCode("GTEX")
                    session.study = study
                }

                else if(role in ['UNM','BMC','UPMC','EU','VUMC']) {

                    if(authorities.contains("ROLE_BPV_MAIN") && !authorities.contains("ROLE_BPV_ELSI")) {
                        //if user has ELSI role for their BSS, set study and send to bpv elsi home
                        def study = Study.findByCode("BPV")                        
                        session.study = study
                    }        
                    else if(authorities.contains("ROLE_BPV_MAIN") && authorities.contains("ROLE_BPV_ELSI")) {
                        //if user has ELSI role for their BSS, set study and send to bpv elsi home
                        def study = Study.findByCode("BPV")                        
                        session.study = study
                    }               
                    else if(!authorities.contains("ROLE_BPV_MAIN") && authorities.contains("ROLE_BPV_ELSI")) {
                        //if user has ELSI role for their BSS, set study and send to bpv elsi home
                        def study = Study.findByCode("BPVELSI")                        
                        session.study = study
                    }                     
                    else{
                        def study = Study.findByCode("BPV")
                        session.study = study
                    }
                    
                }
            }

            if(authorities.contains("ROLE_SERVICE")) {
                session.serviceAccount = true
            }
           

            if(authorities.contains('ROLE_ADMIN') ||
                authorities.contains('ROLE_NCI-FREDERICK_CAHUB') ||
                authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') ||
                authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') ||
                authorities.contains('ROLE_ORG_OBBR')) {

                def org = Organization.findByCode("OBBR")
                session.org = org

                if(authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC')) {
                    session.PRC = true
                }
            }
            
            if(!session.org){
                if(AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(source.principal.username.toLowerCase())){
                   // println("in CTC")
                     def org = Organization.findByCode("CTC")
                     session.org=org
                     session.chosenHome="CTC"
                    // println("org: " + org)
                }
            }
            
            if(session.org){
                def sessionId = source.details.sessionId

                if(sessionId) {
                    UserLogin.withTransaction { status ->
                        def userLogin =  UserLogin.findBySessionId(sessionId)
                        if(userLogin){
                            userLogin.organization = session.org
                            userLogin.save(failOnError:true, flush:true)
                        }
                    }
                }
                
            }else {
                throw new InsufficientAuthenticationException("User [${source.principal.username}] has no associated organization.")
            }
        } catch(InsufficientAuthenticationException iae) {
            iae.printStackTrace()
            throw iae
        } catch(Exception ex) {
            ex.printStackTrace()
            throw ex
        }
    }
}
