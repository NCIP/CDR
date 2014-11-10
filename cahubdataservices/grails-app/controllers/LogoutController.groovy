import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class LogoutController {
    /**
      * Index action. Redirects to the Spring security logout uri.
    */
    def index = {
        // TODO  put any pre-logout code here
        session.setAttribute("logout", new Boolean (true))
        session.invalidate()

        if(SpringSecurityUtils.securityConfig.cas.active) {
            redirect url: "${SpringSecurityUtils.securityConfig.logout.afterLogoutUrl}&cas_logged_out=1"
        } else {
            redirect(controller:"login",action:"auth", params:[logged_out:1])
        }
    }
}
