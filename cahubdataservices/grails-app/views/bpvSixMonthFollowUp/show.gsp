
<%@ page import="nci.obbr.cahub.forms.bpv.BpvSixMonthFollowUp" %>
<g:set var="bodyclass" value="bpvsixmonthfollowup show" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${bpvSixMonthFollowUpInstance?.formMetadata?.cdrFormName}" />

  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1><g:message code="default.show.label.with.case.id" args="[entityName,bpvSixMonthFollowUpInstance.caseRecord.caseId]"/></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <div id="show">
      <div class="dialog">
        <g:render template="formFieldsInc" />
      </div>
    </div>
    <g:if test="${bpvSixMonthFollowUpInstance?.dateSubmitted  && (session.DM )}">
      <div class="buttons">
        <g:form>
          <g:hiddenField name="id" value="${bpvSixMonthFollowUpInstance?.id}" />
          <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
          <g:if test="${!bpvSixMonthFollowUpInstance.dmReviewDate && session.org.code == 'OBBR'}">

            <span class="button"><g:actionSubmit class="save" action="dmReview" value="DM Review" /></span>
          </g:if>
        </g:form>
      </div>
    </g:if>
  </div>
</body>
</html>
