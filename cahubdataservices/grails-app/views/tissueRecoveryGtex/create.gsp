<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<g:set var="bodyclass" value="tissuerecoverygtex create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'TissueRecoveryGtex.label', default: 'Tissue Recovery Form')}" />
        <g:set var="caseId" value="${(tissueRecoveryGtexInstance?.caseRecord?.caseId)}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <script type="text/javascript" src="${resource(dir:'js',file:'tissuerecoverygtex.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
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
            <g:hasErrors bean="${tissueRecoveryGtexInstance}">
            <div class="errors">
                <g:renderErrors bean="${tissueRecoveryGtexInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" autocomplete="off">
                <div class="dialog">
                <g:hiddenField name="caseRecord.id" value="${params.caseRecord.id}" />                  
                <g:render template="formFieldsInc" />
                *Enter fields above, and click Save below to load Specimen data from CBR.
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save saveAction" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>         
    </div>
    </body>
</html>
