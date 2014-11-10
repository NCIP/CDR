
<%@ page import="nci.obbr.cahub.datarecords.ctc.PatientRecord" %>
<g:set var="bodyclass" value="patientrecord edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="CTC Patient Profile" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
<%--   <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link> --%>
      
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${patientRecordInstance}">
      <div class="errors">
        <g:renderErrors bean="${patientRecordInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" >
      <g:hiddenField name="id" value="${patientRecordInstance?.id}" />
      <g:hiddenField name="version" value="${patientRecordInstance?.version}" />
      <g:hiddenField name="changed" value="N" />
      <div class="dialog">
        <g:render template="form"/>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
        <g:if test="${canSubmit == 'Yes'}">
          <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" onclick="return checkModification()" /></span>
        </g:if>
        <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
      </div>
    </g:form>
  </div>
</body>
</html>
