

<%@ page import="nci.obbr.cahub.forms.gtex.IcdGtexRpci" %>
<g:set var="bodyclass" value="icdgtexrpci view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'IcdGtexRpci.label', default: 'Consent Verification')}" />
        <g:set var="candidateId" value="${(IcdGtexRpciInstance?.candidateRecord?.candidateId)}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">          
            <h1>View Consent Verification For Candidate <g:link controller="candidateRecord" action="view" id="${IcdGtexRpciInstance?.candidateRecord?.id}">${IcdGtexRpciInstance?.candidateRecord?.candidateId}</g:link></h1>            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${IcdGtexRpciInstance}">
            <div class="errors">
                <g:renderErrors bean="${IcdGtexRpciInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${IcdGtexRpciInstance?.candidateRecord?.caseRecord}" candidaterecord="${IcdGtexRpciInstance?.candidateRecord}" form="gtexConsent" />
            <div id="view">
            <g:form method="post" autocomplete="off" >
                <g:hiddenField name="id" value="${IcdGtexRpciInstance?.id}" />
                <g:hiddenField name="version" value="${IcdGtexRpciInstance?.version}" />
                <div class="dialog">
<%--            <g:if test="${IcdGtexRpciInstance.candidateRecord.caseRecord?.cdrVer != session.appVer}">
--%>                    

                  <g:if test="${params.v1 == 'true'}">                    
                      <g:render template="formFieldsInc_v1x" />
                  </g:if>
                  <g:else>
                      <g:render template="formFieldsInc" />
                  </g:else>
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
