<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<g:set var="bodyclass" value="tissuerecoverygtex view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'TissueRecoveryGtex.label', default: 'Tissue Recovery Form')}" />
        <g:set var="caseId" value="${(tissueRecoveryGtexInstance?.caseRecord?.caseId)}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
        <script type="text/javascript" src="${resource(dir:'js',file:'tissuerecoverygtex.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
     </div>
    <div id="container" class="clearfix">
    <h1>View Tissue Recovery Form for ${tissueRecoveryGtexInstance?.caseRecord?.encodeAsHTML()}</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${tissueRecoveryGtexInstance}">
      <div class="errors">
        <g:renderErrors bean="${tissueRecoveryGtexInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:queryDesc caserecord="${tissueRecoveryGtexInstance?.caseRecord}" form="gtexTrf" />
    <div id="view">
      <g:form method="post" autocomplete="off">
        <g:hiddenField name="id" value="${tissueRecoveryGtexInstance?.id}" />
        <g:hiddenField name="version" value="${tissueRecoveryGtexInstance?.version}" />
        <g:render template="formFieldsInc" model="[isView:'Yes']" />
      </g:form>   
      <div class="list"> 
      <g:render template="lowerFormFieldsInc" />
      </div>
    </div>
</body>
</html>
