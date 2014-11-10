<%@ page import="nci.obbr.cahub.forms.bpv.BpvConsentEnrollment" %>
<g:set var="bodyclass" value="bpvconsent edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName}" />
        <g:set var="candidateId" value="${bpvConsentEnrollmentInstance?.candidateRecord?.candidateId}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a href="${createLink(uri: '/')}" class="home">Home</a>
          </div>
      </div>
      <div id="container" class="clearfix">    
            <h1><g:message code="default.edit.label.with.candidate.id" args="[entityName,candidateId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvConsentEnrollmentInstance}">
            <div class="errors">
                <g:renderErrors bean="${bpvConsentEnrollmentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${bpvConsentEnrollmentInstance?.candidateRecord?.caseRecord}" candidaterecord="${bpvConsentEnrollmentInstance?.candidateRecord}" form="bpvConsent" />
            <g:form method="post" >
                <g:hiddenField name="id" value="${bpvConsentEnrollmentInstance?.id}" />
                <g:hiddenField name="version" value="${bpvConsentEnrollmentInstance?.version}" />
                <g:hiddenField name="changed" value="N" />
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <g:if test="${canSubmit == 'Yes'}">
                        <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" onclick="return checkModification()" /></span>
                    </g:if>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
                </div>
            </g:form>
     </div><!-- end container --> 
    </body>
</html>
