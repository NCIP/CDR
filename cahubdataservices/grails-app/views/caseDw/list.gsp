
<%@ page import="nci.obbr.cahub.datawarehouse.CaseDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseDw.label', default: 'CaseDw')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'caseDw.id.label', default: 'Id')}" />
                        
                            <th><g:message code="caseDw.caseRecord.label" default="Case Record" /></th>
                        
                            <g:sortableColumn property="caseId" title="${message(code: 'caseDw.caseId.label', default: 'Case Id')}" />
                        
                            <g:sortableColumn property="PMI" title="${message(code: 'caseDw.PMI.label', default: 'PMI')}" />
                            <g:sortableColumn property="minFixTime" title="${message(code: 'caseDw.minFixTime.label', default: 'Min Fix Time')}" />
                            <g:sortableColumn property="avgFixTime" title="${message(code: 'caseDw.avgFixTime.label', default: 'Avg Fix Time')}" />
                            <g:sortableColumn property="maxFixTime" title="${message(code: 'caseDw.maxFixTime.label', default: 'Max Fix Time')}" />
                            <g:sortableColumn property="brain" title="${message(code: 'caseDw.brain.label', default: 'Brain')}" />
                        
                            <th><g:message code="caseDw.caseCollectionType.label" default="Case Collection Type" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${caseDwInstanceList}" status="i" var="caseDwInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${caseDwInstance.id}">${fieldValue(bean: caseDwInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: caseDwInstance, field: "caseRecord")}</td>
                        
                            <td>${fieldValue(bean: caseDwInstance, field: "caseId")}</td>
                        
                            <td>${fieldValue(bean: caseDwInstance, field: "PMI")}</td>
                            <td>${fieldValue(bean: caseDwInstance, field: "minFixTime")}</td>
                            <td>${fieldValue(bean: caseDwInstance, field: "avgFixTime")}</td>
                            <td>${fieldValue(bean: caseDwInstance, field: "maxFixTime")}</td>
                            <td>${fieldValue(bean: caseDwInstance, field: "brain")}</td>
                        
                            <td>${fieldValue(bean: caseDwInstance, field: "caseCollectionType")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${caseDwInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
