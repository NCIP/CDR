<%@ page import="nci.obbr.cahub.forms.bpv.BpvBloodForm" %>
<g:set var="bodyclass" value="bpvbloodform create bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvBloodFormInstance?.formMetadata?.cdrFormName}" />
        <g:set var="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.create.label.with.case.id" args="[entityName,caseId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvBloodFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${bpvBloodFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" action="save" >
                <div class="dialog">
                <g:hiddenField name="id" value="${bpvBloodFormInstance.id}" />
                <g:hiddenField name="version" value="${bpvBloodFormInstance?.version}" />
                <g:hiddenField name="caseId" value="${bpvBloodFormInstance.caseRecord?.caseId}" />
                <g:hiddenField name="caseRecord.id" value="${params.caseRecord.id}" />
                  <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>      
      <g:render template="floatingFormsInc" />
    </body>
</html>
