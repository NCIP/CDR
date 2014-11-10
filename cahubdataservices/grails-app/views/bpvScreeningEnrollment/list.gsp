<%@ page import="nci.obbr.cahub.forms.bpv.BpvScreeningEnrollment" %>
<g:set var="bodyclass" value="bpvscreeenenroll list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvScreeningEnrollmentInstance?.formMetadata?.cdrFormName}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvScreeningEnrollment.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="protocolSiteNum" title="${message(code: 'bpvScreeningEnrollment.protocolSiteNum.label', default: 'Protocol Site Num')}" />
                        
                            <th><g:message code="bpvScreeningEnrollment.candidateRecord.label" default="Candidate Record" /></th>
                        
                            <g:sortableColumn property="nameCreatCandidate" title="${message(code: 'bpvScreeningEnrollment.nameCreatCandidate.label', default: 'Name Creat Candidate')}" />
                        
                            <g:sortableColumn property="consentObtained" title="${message(code: 'bpvScreeningEnrollment.consentObtained.label', default: 'Consent Obtained')}" />
                        
                            <g:sortableColumn property="reasonNotConsented" title="${message(code: 'bpvScreeningEnrollment.reasonNotConsented.label', default: 'Reason Not Consented')}" />
                            
                            <g:sortableColumn property="otherReason" title="${message(code: 'bpvScreeningEnrollment.otherReason.label', default: 'Other Reason')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvScreeningEnrollmentInstanceList}" status="i" var="bpvScreeningEnrollmentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvScreeningEnrollmentInstance.id}">${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "id")}</g:link></td>
                            
                            <td>${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "protocolSiteNum")}</td>
                        
                            <td>${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "candidateRecord")}</td>
                        
                            <td>${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "nameCreatCandidate")}</td>
                        
                            <td>${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "consentObtained")}</td>
                        
                            <td>${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "reasonNotConsented")}</td>
                            
                            <td>${fieldValue(bean: bpvScreeningEnrollmentInstance, field: "otherReason")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvScreeningEnrollmentInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
