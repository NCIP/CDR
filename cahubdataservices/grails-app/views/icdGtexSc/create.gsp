<%@ page import="nci.obbr.cahub.forms.gtex.IcdGtexSc" %>
<g:set var="bodyclass" value="icdgtexsc create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'IcdGtexSc.label', default: 'Consent Verification')}" />
        <g:set var="candidateId" value="${(IcdGtexScInstance?.candidateRecord?.candidateId)}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">   
            <h1><g:message code="default.create.label.with.candidate.id" args="[entityName,candidateId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${IcdGtexScInstance}">
            <div class="errors">
                <g:renderErrors bean="${IcdGtexScInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" autocomplete="off" >
                <g:hiddenField name="candidateRecord.id" value="${params.candidateRecord.id}" />              
                <div class="dialog">
                  <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
