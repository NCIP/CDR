<%@ page import="nci.obbr.cahub.forms.gtex.IcdGtexNdri" %>
<g:set var="bodyclass" value="icdgtexndri view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'IcdGtexNdri.label', default: 'Consent Verification')}" />
        <g:set var="candidateId" value="${(IcdGtexNdriInstance?.candidateRecord?.candidateId)}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">           
            <h1>View Consent Verification For Candidate <g:link controller="candidateRecord" action="view" id="${IcdGtexNdriInstance?.candidateRecord?.id}">${IcdGtexNdriInstance?.candidateRecord?.candidateId}</g:link></h1>            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${IcdGtexNdriInstance}">
            <div class="errors">
                <g:renderErrors bean="${IcdGtexNdriInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${IcdGtexNdriInstance?.candidateRecord?.caseRecord}" candidaterecord="${IcdGtexNdriInstance?.candidateRecord}" form="gtexConsent" />
            <div id="view">
            <g:form method="post" autocomplete="off" >
                <g:hiddenField name="id" value="${IcdGtexNdriInstance?.id}" />
                <g:hiddenField name="version" value="${IcdGtexNdriInstance?.version}" />
                <div class="dialog">
                  <g:render template="formFieldsInc" />
                </div>
            </g:form>
            </div>
        </div>
    </body>
</html>
