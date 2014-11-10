

<%@ page import="nci.obbr.cahub.forms.gtex.IcdGtexRpci" %>
<g:set var="bodyclass" value="icdgtexrpci edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'IcdGtexRpci.label', default: 'Consent Verification')}" />
        <g:set var="candidateId" value="${(IcdGtexRpciInstance?.candidateRecord?.candidateId)}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">        
            <h1><g:message code="default.edit.label.with.candidate.id" args="[entityName,candidateId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${IcdGtexRpciInstance}">
            <div class="errors">
                <g:renderErrors bean="${IcdGtexRpciInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${IcdGtexRpciInstance?.candidateRecord?.caseRecord}" candidaterecord="${IcdGtexRpciInstance?.candidateRecord}" form="gtexConsent" />
            <g:form method="post" autocomplete="off" >
                <g:hiddenField name="id" value="${IcdGtexRpciInstance?.id}" />
                <g:hiddenField name="version" value="${IcdGtexRpciInstance?.version}" />
                <div class="dialog">
                  <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
