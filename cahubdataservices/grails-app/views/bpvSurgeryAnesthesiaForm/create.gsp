<g:if test="${env != 'production'}">
   <%-- cache buster--%>
   <g:set var="d" value="${new Date()}" />
   <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<%@ page import="nci.obbr.cahub.forms.bpv.BpvSurgeryAnesthesiaForm" %>
<g:set var="bodyclass" value="bpvsurgeryanest create bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName}" />
        <g:set var="caseId" value="${bpvSurgeryAnesthesiaFormInstance?.caseRecord?.caseId}" />
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'surgeryAnesthesiaForm.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script> 
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
            <g:hasErrors bean="${bpvSurgeryAnesthesiaFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${bpvSurgeryAnesthesiaFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                                    <g:hiddenField name="caseRecord.id" value="${params.caseRecord.id}" />
                                    <g:hiddenField name="formMetadata.id" value="${params.formMetadata.id}" />
                                    <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
