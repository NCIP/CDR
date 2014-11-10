package nci.obbr.cahub.security.cas.web

import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.ServletException

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.cas.web.CasAuthenticationFilter
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder


class CDRCasAuthenticationFilter extends CasAuthenticationFilter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
        throws IOException, ServletException {
        SecurityRequestHolder.set((HttpServletRequest)request, (HttpServletResponse)response);
        try {
            super.doFilter(request, response, chain);
        } finally {
            SecurityRequestHolder.reset();
        }
    }
}
