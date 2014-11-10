<%@ page import="nci.obbr.cahub.forms.bpv.BpvConsentEnrollment" %>
<g:set var="bodyclass" value="bpvconsent list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvConsentEnrollmentInstance?.formMetadata?.cdrFormName}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvConsentEnrollment.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="inputtedCaseId" title="${message(code: 'bpvConsentEnrollment.inputtedCaseId.label', default: 'Inputted Case Id')}" />
                        
                            <g:sortableColumn property="bssIrbProtocol" title="${message(code: 'bpvConsentEnrollment.bssIrbProtocol.label', default: 'Bss Irb Protocol')}" />
                            
                            <th><g:message code="bpvConsentEnrollment.candidateRecord.label" default="Candidate Record" /></th>
                        
                            <g:sortableColumn property="tissueBankId" title="${message(code: 'bpvConsentEnrollment.tissueBankId.label', default: 'Tissue Bank Id')}" />
                        
                            <g:sortableColumn property="submittedBy" title="${message(code: 'bpvConsentEnrollment.submittedBy.label', default: 'Submitted By')}" />
                        
                            <g:sortableColumn property="dateSubmitted" title="${message(code: 'bpvConsentEnrollment.dateSubmitted.label', default: 'Date Submitted')}" />
                            
                            <g:sortableColumn property="primaryTissueType" title="${message(code: 'bpvConsentEnrollment.primaryTissueType.label', default: 'Primary Tissue Type')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvConsentEnrollmentInstanceList}" status="i" var="bpvConsentEnrollmentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvConsentEnrollmentInstance.id}">${fieldValue(bean: bpvConsentEnrollmentInstance, field: "id")}</g:link></td>
                    
                            <td>${fieldValue(bean: bpvConsentEnrollmentInstance, field: "inputtedCaseId")}</td>
                        
                            <td>${fieldValue(bean: bpvConsentEnrollmentInstance, field: "bssIrbProtocol")}</td>
                        
                            <td>${fieldValue(bean: bpvConsentEnrollmentInstance, field: "candidateRecord")}</td>
                        
                            <td>${fieldValue(bean: bpvConsentEnrollmentInstance, field: "tissueBankId")}</td>
                        
                            <td>${fieldValue(bean: bpvConsentEnrollmentInstance, field: "submittedBy")}</td>
                        
                            <td><g:formatDate date="${bpvConsentEnrollmentInstance.dateSubmitted}" /></td>
                            
                            <td>${fieldValue(bean: bpvConsentEnrollmentInstance, field: "primaryTissueType")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvConsentEnrollmentInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
