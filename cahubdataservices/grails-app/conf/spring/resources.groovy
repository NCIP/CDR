import nci.obbr.cahub.context.CDRApplicationEvent
import nci.obbr.cahub.security.cas.userdetails.CDRAbstractCasAssertionUserDetailsService

beans = {
    securityEventListener(CDRApplicationEvent) {
        grailsApplication = ref('grailsApplication')
    }
    authenticationUserDetailsService(CDRAbstractCasAssertionUserDetailsService)
}
