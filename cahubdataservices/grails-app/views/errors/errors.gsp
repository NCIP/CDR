
<%@ page import="nci.obbr.cahub.util.AppSetting" %>

<html>
    <head>
        <title><g:message code="default.page.title"/>: Page Not Found</title>
        <meta name="layout" content="main" />
    </head>
    <body>
      <font color="red"> Page Not found! Please go back to previous page and try again.</font> </br>
      <h2> <font color="red"> If you think this is a bug in the CDR application, please report it to the caHUB/CDR team.</font> </h2>
    </body>
</html>
<head>
<meta name='layout' content='main' />
<title>Session expired</title>
</head>
<body>
<div class='body'>
        <g:set var="helpemail" value="${AppSetting.findByCode('HELP_EMAIL')}" />
       <div class="errors"><ul><li>Page not found. <a href="/cahubdataservices/">Please click here to log in again.</a></div>        
       <font color="blue"> If you think this is a bug in the CDR application, please report it to the caHUB/CDR team at:</font>       
       <a href="mailto:${helpemail.value}">${helpemail.value}</a>
        </li></ul></div>
</body>
