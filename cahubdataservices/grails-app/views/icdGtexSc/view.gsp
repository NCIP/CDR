

<%@ page import="nci.obbr.cahub.forms.gtex.IcdGtexSc" %>
<g:set var="bodyclass" value="icdgtexsc view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'IcdGtexSc.label', default: 'Consent Verification')}" />
        <g:set var="candidateId" value="${(IcdGtexScInstance?.candidateRecord?.candidateId)}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">         
            <h1>View Consent Verification For Candidate <g:link controller="candidateRecord" action="view" id="${IcdGtexScInstance?.candidateRecord?.id}">${IcdGtexScInstance?.candidateRecord?.candidateId}</g:link></h1>            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${IcdGtexScInstance}">
            <div class="errors">
                <g:renderErrors bean="${IcdGtexScInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${IcdGtexScInstance?.candidateRecord?.caseRecord}" candidaterecord="${IcdGtexScInstance?.candidateRecord}" form="gtexConsent" />
            <div id="view">
            <g:form method="post" autocomplete="off" >
                <g:hiddenField name="id" value="${IcdGtexScInstance?.id}" />
                <g:hiddenField name="version" value="${IcdGtexScInstance?.version}" />
                <div class="dialog">
                  <g:render template="formFieldsInc" />
                </div>
            </g:form>
        </div>
        </div>
      
        <script type="text/javascript">
            $(document).ready(function(){
                $('#view :input').attr('disabled', true);
                $('img[alt=Date]').css("display","none");
            });

        </script>        
    </body>
</html>
