<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueReceiptDissection" %>
<g:set var="bodyclass" value="bpvtissuereceipt edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName}"/>
        <g:set var="caseId" value="${bpvTissueReceiptDissectionInstance?.caseRecord?.caseId}"/>
        <title><g:message code="default.edit.label" args="[entityName]"/></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
          <h1><g:message code="default.edit.label.with.case.id" args="[entityName,caseId]"/></h1>
          <g:if test="${flash.message}">
              <div class="message">${flash.message}</div>
          </g:if>
          <g:hasErrors bean="${bpvTissueReceiptDissectionInstance}">
              <div class="errors">
                  <g:renderErrors bean="${bpvTissueReceiptDissectionInstance}" as="list"/>
              </div>
          </g:hasErrors>
          <g:warnings warningmap="${warningMap}" />
          <g:queryDesc caserecord="${bpvTissueReceiptDissectionInstance?.caseRecord}" form="bpvDissection" />
          <g:form method="post" class="tdwrap tdtop">
                <g:hiddenField name="id" value="${bpvTissueReceiptDissectionInstance?.id}"/>
                <g:hiddenField name="version" value="${bpvTissueReceiptDissectionInstance?.version}"/>
                <g:hiddenField name="changed" value="N"/>
                <div class="dialog">
                    <g:render template="formFieldsInc"/>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}"/></span>
                    <g:if test="${canSubmit}">
                        <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" onclick="return checkModification()"/></span>
                    </g:if>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
