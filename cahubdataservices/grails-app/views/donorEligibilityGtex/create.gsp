<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.DonorEligibilityGtex" %>
<g:set var="bodyclass" value="donoreligibility create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form')}" />
        <g:set var="canidateId" value="${(donorEligibilityGtexInstance?.candidateRecord?.candidateId?.toString())}" />
          <script type="text/javascript" src="/cahubdataservices/js/donoreligibility.js?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">         
            <h1><g:message code="default.create.label.with.candidate.id" args="[entityName, canidateId]" /></h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${donorEligibilityGtexInstance}">
            <div class="errors">
                <g:renderErrors bean="${donorEligibilityGtexInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" autocomplete="off" >
                <div class="dialog">
                  <g:hiddenField name="candidateRecord.id" value="${params.candidateRecord?.id}" />                                
                  <g:render template="formFieldsInc" />
                </div>                                                                                                           
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
