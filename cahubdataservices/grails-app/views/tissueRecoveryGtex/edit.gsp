<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<g:set var="bodyclass" value="tissuerecoverygtex edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'TissueRecoveryGtex.label', default: 'Tissue Recovery Form')}" />
        <g:set var="caseId" value="${(tissueRecoveryGtexInstance?.caseRecord?.caseId)}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <script type="text/javascript" src="${resource(dir:'js',file:'tissuerecoverygtex.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
     </div>
    <div id="container" class="clearfix">
    
    <h1><g:message code="default.edit.label.with.case.id" args="[entityName,caseId]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${tissueRecoveryGtexInstance}">
      <div class="errors">
        <g:renderErrors bean="${tissueRecoveryGtexInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:queryDesc caserecord="${tissueRecoveryGtexInstance?.caseRecord}" form="gtexTrf" />
    <g:form method="post" autocomplete="off">
      <g:hiddenField name="id" value="${tissueRecoveryGtexInstance?.id}" />
      <g:hiddenField name="version" value="${tissueRecoveryGtexInstance?.version}" />
      <g:render template="formFieldsInc" />
      <div class="buttons">
          <span class="button"><g:actionSubmit class="save saveAction" action="update" value="Save Tissue Recovery Collection Data" /></span>
      </div>
      <div class="list">
        <g:render template="lowerFormFieldsInc" />
      </div>
      <%--simulated save button for user convenience.  refreshes page and scrolls top.  all fields in lower section save via ajax.  --%>
      <div class="buttons">
          <span class="button"><g:actionSubmit class="save saveAction" action="update" value="Save Tissue Recovery Collection Data" /></span>
      </div>
    </g:form>  
</div>
</body>
</html>
