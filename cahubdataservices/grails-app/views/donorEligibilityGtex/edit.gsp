<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<%@ page import="nci.obbr.cahub.forms.gtex.DonorEligibilityGtex" %>
<g:set var="bodyclass" value="donoreligibility edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form')}" />
        <g:set var="canidateId" value="${(donorEligibilityGtexInstance?.candidateRecord?.candidateId?.toString())}" />
         <script type="text/javascript" src="/cahubdataservices/js/donoreligibility.js?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">        
             <h1><g:message code="default.edit.label.with.candidate.id" args="[entityName, canidateId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${donorEligibilityGtexInstance}">
            <div class="errors">
                <g:renderErrors bean="${donorEligibilityGtexInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${donorEligibilityGtexInstance?.candidateRecord?.caseRecord}" candidaterecord="${donorEligibilityGtexInstance?.candidateRecord}" form="gtexDonorEligi" />
            <g:form method="post" autocomplete="off" >
                <g:hiddenField name="id" value="${donorEligibilityGtexInstance?.id}" />
                <g:hiddenField name="version" value="${donorEligibilityGtexInstance?.version}" />
                <div class="dialog">
                <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
