
<%@ page import="nci.obbr.cahub.forms.gtex.crf.CaseReportForm" %>
<g:set var="bodyclass" value="casereportform list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseReportForm.label', default: 'CaseReportForm')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'caseReportForm.id.label', default: 'Id')}" />
                        
                            <th><g:message code="caseReportForm.caseRecord.label" default="Case Record" /></th>
                        
                         
                        
                            <th><g:message code="caseReportForm.status.label" default="Status" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${caseReportFormInstanceList}" status="i" var="caseReportFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${caseReportFormInstance.id}">${fieldValue(bean: caseReportFormInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: caseReportFormInstance, field: "caseRecord")}</td>
                        
                        
                            <td>${fieldValue(bean: caseReportFormInstance, field: "status")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${caseReportFormInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
