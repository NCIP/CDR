<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>caHUB Analytics and Reporting - CDR-AR</title>
    </head>
    
    <body>
            <g:set var="env" value="${grails.util.GrailsUtil.environment}" />             
            <g:if test="${env == 'production'}">
              <script>
                //set to get around cross domain security in production
                document.domain = "ncifcrf.gov"
              </script>
            </g:if>      
        <g:set var="cdrar_hostname" value="${AppSetting.findByCode('CDRAR_HOSTNAME')}" />
        <g:set var="cdrar_home" value="${AppSetting.findByCode('CDRAR_HOME')}" />
        <iframe id="cdrar_iframe" src="${cdrar_hostname.value}${cdrar_home.value}" frameborder="0" scrolling="auto" height="900" width="100%"></iframe>

    </body>
</html>
