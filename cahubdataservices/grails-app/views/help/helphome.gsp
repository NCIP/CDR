<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="help" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
    <title><g:message code="default.page.title"/></title>
  </head>
  <body>
    <div id="nav" class="clearfix">
      <div id="navlist">
        <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      </div>
    </div>
    <div id="container" class="clearfix">

      <g:if test="${session?.study?.code == 'GTEX'}">
        <g:render template="/help/showgtexhelp"  />              
      </g:if>
      <g:if test="${session?.study?.code == 'BPV'}">
        <g:render template="/help/showbpvhelp"  />              
      </g:if> 
      <g:if test="${!session?.study?.code ||(!session?.study?.code =='BPV') && !(session?.study?.code =='GTEX')}">
        <g:render template="/help/showobbrhelp"  />              
      </g:if>   
  <h3>Important Links</h3>

    <g:set var="helpemail" value="${AppSetting?.findByCode('HELP_EMAIL')}" />
    <ul>
      <li>Request help via email: <a href="mailto:${helpemail?.value}">${helpemail?.value}</a> <i>(A support response will be provided by the end of the next business day, 8am-5pm Eastern)</i></li>
      <li><g:link controller="register" action="forgotPassword">Reset password</g:link> <i>(for non-NIH logins only)</i></li>
    </ul>      
    </div>
  </body>
</html>
