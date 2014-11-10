<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="error" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title><g:message code="default.page.title"/>: Page Not Found</title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
        <g:set var="helpemail" value="${AppSetting.findByCode('HELP_EMAIL')}" />
        <div class="errors"><ul><li><b>Page not found.</b> Please go back to the previous page and try again.
        <br /><br />
          If you think this is a bug in the CDR application, please report it to the caHUB/CDR team at:       
            <a href="mailto:${helpemail.value}">${helpemail.value}</a>        
        </li></ul></div>   
      </div>
</body>
