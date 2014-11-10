<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueGrossEvaluation" %>
<g:set var="bodyclass" value="bpvtissuegrosseval list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvTissueGrossEvaluation.id.label', default: 'Id')}" />

                            <th><g:message code="bpvTissueGrossEvaluation.caseRecord.label" default="Case Record" /></th>
                        
                            <g:sortableColumn property="submittedBy" title="${message(code: 'bpvTissueGrossEvaluation.submittedBy.label', default: 'Submitted By')}" />
                        
                            <g:sortableColumn property="dateSubmitted" title="${message(code: 'bpvTissueGrossEvaluation.dateSubmitted.label', default: 'Date Submitted')}" />
                        
                            <g:sortableColumn property="dateTimeArrived" title="${message(code: 'bpvTissueGrossEvaluation.dateTimeArrived.label', default: 'Date Time Arrived')}" />
                        
                            <g:sortableColumn property="nameReceived" title="${message(code: 'bpvTissueGrossEvaluation.nameReceived.label', default: 'Name Received')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvTissueGrossEvaluationInstanceList}" status="i" var="bpvTissueGrossEvaluationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvTissueGrossEvaluationInstance.id}">${fieldValue(bean: bpvTissueGrossEvaluationInstance, field: "id")}</g:link></td>
                
                            <td>${fieldValue(bean: bpvTissueGrossEvaluationInstance, field: "caseRecord")}</td>
                        
                            <td>${fieldValue(bean: bpvTissueGrossEvaluationInstance, field: "submittedBy")}</td>
                        
                            <td><g:formatDate date="${bpvTissueGrossEvaluationInstance.dateSubmitted}" /></td>
                        
                            <td><g:formatDate date="${bpvTissueGrossEvaluationInstance.dateTimeArrived}" /></td>
                        
                            <td>${fieldValue(bean: bpvTissueGrossEvaluationInstance, field: "nameReceived")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvTissueGrossEvaluationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
