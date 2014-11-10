
<%@ page import="nci.obbr.cahub.forms.bpv.BpvConsentEnrollment" %>
<g:set var="bodyclass" value="bpvconsentenroll" scope="request"/>
<g:if test="${session.org?.code == 'OBBR'}">
   <g:set var="homeLink" value="/cahubdataservices/home/choosehome" scope="request"/>
</g:if>
<g:else>
   <g:set var="homeLink" value="/cahubdataservices/home" scope="request"/>
</g:else>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'bpvConsentEnrollment.label', default: 'BPV Subject Consent Form')}" />
        <g:set var="candidateId" value="${bpvConsentEnrollmentInstance?.candidateRecord?.candidateId}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <content tag="breadcrumbs"><g:render template="/includes/breadcrumbs" model="[location:'bpvconsentenroll.edit']"/></content>
      <content tag="vers"><g:render template="/includes/vers" model="[location:'cahubdataservices']"/></content>
      <g:if test='${flash.message}'><div id="message" class="redtext">${flash.message}</div></g:if>
      <g:hasErrors bean="${bpvConsentEnrollmentInstance}">
      <div class="errors">
        <g:renderErrors bean="${bpvConsentEnrollmentInstance}" as="list" />
      </div>
      </g:hasErrors>
      <g:form method="post" class="cahubForm">
        <h1><g:message code="default.edit.label.with.candidate.id" args="[entityName,candidateId]" /></h1>
        <g:hiddenField name="id" value="${bpvConsentEnrollmentInstance?.id}" />
        <g:hiddenField name="version" value="${bpvConsentEnrollmentInstance?.version}" />
        <div class="dialog">
          <g:render template="formFieldsInc-noTables" />
        </div>
        <div class="buttons">
          <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
          <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" /></span>
          <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
        </div>
       </g:form>
    </body>
</html>
