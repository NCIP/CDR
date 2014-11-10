<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueReceiptDissection" %>
<g:set var="bodyclass" value="bpvtissuereceipt show bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName}"/>
        <g:set var="caseId" value="${bpvTissueReceiptDissectionInstance?.caseRecord?.caseId}"/>
        <title><g:message code="default.show.label" args="[entityName]"/></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
          <h1><g:message code="default.show.label.with.case.id" args="[entityName,caseId]"/></h1>
          <g:if test="${flash.message}">
              <div class="message">${flash.message}</div>
          </g:if>
          <g:warnings warningmap="${warningMap}" />
          <g:queryDesc caserecord="${bpvTissueReceiptDissectionInstance?.caseRecord}" form="bpvDissection" />
          <div id="show">
              <div class="dialog tdwrap tdtop">
                  <g:render template="formFieldsInc" />
              </div>
          </div>
          <g:if test="${bpvTissueReceiptDissectionInstance?.dateSubmitted &&
              bpvTissueReceiptDissectionInstance?.caseRecord?.candidateRecord?.isEligible &&
                  bpvTissueReceiptDissectionInstance?.caseRecord?.candidateRecord?.isConsented &&
                      canResume}">
              <div class="buttons">
                  <g:form class="tdwrap tdtop">
                      <g:hiddenField name="id" value="${bpvTissueReceiptDissectionInstance?.id}" />
                      <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                  </g:form>
              </div>
          </g:if>
        </div>
    </body>
</html>
