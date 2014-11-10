<%@ page import="nci.obbr.cahub.forms.bpv.clinicaldataentry.BpvClinicalDataEntry" %>
<g:set var="bodyclass" value="bpvclinicaldata create bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName}"/>
        <g:set var="caseId" value="${bpvClinicalDataEntryInstance?.caseRecord?.caseId}"/>
        <title><g:message code="default.create.label" args="[entityName]"/></title>
        <g:if test="${env != 'production'}">
          <%-- cache buster--%>
          <g:set var="d" value="${new Date()}" />
          <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
        </g:if>      
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'clinical-data-entry.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script> 
    </head>
    <body>
        <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.create.label.with.case.id" args="[entityName,caseId]"/></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvClinicalDataEntryInstance}">
                <div class="errors">
                    <g:renderErrors bean="${bpvClinicalDataEntryInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form action="save" class="tdtop">
                <div class="dialog">
                    <g:hiddenField name="caseRecord.id" value="${params.caseRecord?.id}"/>
                    <g:hiddenField name="formMetadata.id" value="${params.formMetadata.id}"/>
                    <g:render template="formFieldsInc"/>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}"/></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
