

<%@ page import="nci.obbr.cahub.forms.gtex.DonorEligibilityGtex" %>
<g:set var="bodyclass" value="donoreligibility view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'DonorEligibilityGtex.label', default: 'Donor Eligibility Criteria Form')}" />
        <g:set var="canidateId" value="${(donorEligibilityGtexInstance?.candidateRecord?.candidateId?.toString())}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">     
            <h1>View Donor Eligibility Criteria Form For Candidate <g:link controller="candidateRecord" action="view" id="${donorEligibilityGtexInstance?.candidateRecord?.id}">${donorEligibilityGtexInstance?.candidateRecord?.candidateId}</g:link></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${donorEligibilityGtexInstance}">
            <div class="errors">
                <g:renderErrors bean="${donorEligibilityGtexInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${donorEligibilityGtexInstance?.candidateRecord?.caseRecord}" candidaterecord="${donorEligibilityGtexInstance?.candidateRecord}" form="gtexDonorEligi" />
            <div id="view">
            <g:form method="post" autocomplete="off" >
                <g:hiddenField name="id" value="${donorEligibilityGtexInstance?.id}" />
                <g:hiddenField name="version" value="${donorEligibilityGtexInstance?.version}" />
                <div class="dialog">
                <g:render template="formFieldsInc" />
                </div>
            </g:form>
            </div>
        </div>

        <script type="text/javascript">
            $(document).ready(function(){
                $('#view :input').attr('disabled', true);
            });

        </script>
      
    </body>
</html>
