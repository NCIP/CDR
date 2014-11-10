<%@ page import="nci.obbr.cahub.datarecords.ctc.PatientRecord" %>
<g:set var="bodyclass" value="patientrecord create " scope="request"/>

<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="CTC Patient Record" />
     <%--   <g:set var="caseId" value="${patientRecordInstance?.caseRecord?.caseId}" /> --%>
        <title>Create CTC Record</title>
    </head>
    <body>
        <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
         <%--    <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link> --%>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Create CTC Patient Profile </h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}"
           default="${flash.default}"/></div>
            </g:if>
            <g:hasErrors bean="${patientRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${patientRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" class="tdwrap tdtop">
                <div class="dialog">
                             
                    <g:render template="form"/>
                </div>
                 <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save saveAction" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
