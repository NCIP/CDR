<%@ page import="nci.obbr.cahub.forms.bpv.BpvScreeningEnrollment" %>
<g:set var="bodyclass" value="bpvscreeenenroll show bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName}" />
        <g:set var="candidateId" value="${bpvScreeningEnrollmentInstance?.candidateRecord?.candidateId}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.show.label.with.candidate.id" args="[entityName,candidateId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:queryDesc caserecord="${bpvScreeningEnrollmentInstance?.candidateRecord?.caseRecord}" candidaterecord="${bpvScreeningEnrollmentInstance?.candidateRecord}" form="bpvScreening" />
            <div id="show">
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
            </div>
            <g:if test="${bpvScreeningEnrollmentInstance?.dateSubmitted && (!bpvScreeningEnrollmentInstance?.candidateRecord?.caseRecord || canResume)}">
                <div class="buttons">
                    <g:form>
                        <g:hiddenField name="id" value="${bpvScreeningEnrollmentInstance?.id}" />
                        <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                    </g:form>
                </div>
            </g:if>
        </div>
    </body>
</html>
