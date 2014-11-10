package nci.obbr.cahub.security.cas.userdetails

import nci.obbr.cahub.authservice.User
import org.jasig.cas.client.validation.Assertion

import org.springframework.dao.DataAccessException
import org.springframework.security.web.WebAttributes

import org.codehaus.groovy.grails.web.util.WebUtils
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UsernameNotFoundException

import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService
import org.springframework.security.cas.userdetails.GrantedAuthorityFromAssertionAttributesUserDetailsService


class CDRAbstractCasAssertionUserDetailsService extends AbstractCasAssertionUserDetailsService {
    private static final String PROTECTED_PASSWORD = "[PROTECTED]";
    private static final String[] authorityAttribNamesFromCas = ["authorities"]
    private static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]

    private GrantedAuthorityFromAssertionAttributesUserDetailsService grantedAuthoritiesService =
        new GrantedAuthorityFromAssertionAttributesUserDetailsService(authorityAttribNamesFromCas)

    @Override
    protected UserDetails loadUserDetails(Assertion casAssert)
        throws UsernameNotFoundException, DataAccessException {

        def grailsUser
        User.withTransaction { status ->
            Set<GrantedAuthority> authorities
            def username = casAssert.principal.name
            def userDetails = User.findByUsername(username)

            if(userDetails) {
                authorities = userDetails.authorities.collect{new GrantedAuthorityImpl(it.authority)}
                grailsUser = new GrailsUser(userDetails.username,
                    PROTECTED_PASSWORD, userDetails.enabled, !userDetails.accountExpired,
                        !userDetails.passwordExpired, !userDetails.accountLocked, authorities ?: NO_ROLES, userDetails.id)
            } else {
                authorities = new HashSet<GrantedAuthority>(10)
                userDetails = grantedAuthoritiesService.loadUserDetails(casAssert)

                for(GrantedAuthority authority : userDetails.authorities) {
                    authority.authority.replace("[", "").replace("]", "").split("\\s*,\\s*").each {
                        authorities.add(new GrantedAuthorityImpl(it))
                    }
                }

                grailsUser = new GrailsUser(userDetails.username,
                    PROTECTED_PASSWORD, userDetails.enabled, userDetails.accountNonExpired,
                        userDetails.credentialsNonExpired, userDetails.accountNonLocked, authorities ?: NO_ROLES, -1)
            }
        }

        def session = WebUtils.retrieveGrailsWebRequest().session
        session[WebAttributes.LAST_USERNAME] = grailsUser?.username

        return grailsUser
    }
}
