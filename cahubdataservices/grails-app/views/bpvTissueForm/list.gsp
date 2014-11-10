
<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bpvTissueForm.label', default: 'BpvTissueForm')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvTissueForm.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'bpvTissueForm.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'bpvTissueForm.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="surgDate" title="${message(code: 'bpvTissueForm.surgDate.label', default: 'Surg Date')}" />
                        
                            <g:sortableColumn property="firstIncis" title="${message(code: 'bpvTissueForm.firstIncis.label', default: 'First Incis')}" />
                        
                            <g:sortableColumn property="clamp1Time" title="${message(code: 'bpvTissueForm.clamp1Time.label', default: 'Clamp1 Time')}" />
                        
                            <g:sortableColumn property="clamp2Time" title="${message(code: 'bpvTissueForm.clamp2Time.label', default: 'Clamp2 Time')}" />
                        
                            <g:sortableColumn property="resectTime" title="${message(code: 'bpvTissueForm.resectTime.label', default: 'Resect Time')}" />
                        
                            <g:sortableColumn property="surgComment" title="${message(code: 'bpvTissueForm.surgComment.label', default: 'Surg Comment')}" />
                        
                            <g:sortableColumn property="grossTimeIn" title="${message(code: 'bpvTissueForm.grossTimeIn.label', default: 'Gross Time In')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvTissueFormInstanceList}" status="i" var="bpvTissueFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvTissueFormInstance.id}">${fieldValue(bean: bpvTissueFormInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: bpvTissueFormInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: bpvTissueFormInstance, field: "publicComments")}</td>
                        
                            <td><g:formatDate date="${bpvTissueFormInstance.surgDate}" /></td>
                        
                            <td><g:formatDate date="${bpvTissueFormInstance.firstIncis}" /></td>
                        
                            <td><g:formatDate date="${bpvTissueFormInstance.clamp1Time}" /></td>
                        
                            <td><g:formatDate date="${bpvTissueFormInstance.clamp2Time}" /></td>
                        
                            <td><g:formatDate date="${bpvTissueFormInstance.resectTime}" /></td>
                        
                            <td>${fieldValue(bean: bpvTissueFormInstance, field: "surgComment")}</td>
                        
                            <td><g:formatDate date="${bpvTissueFormInstance.grossTimeIn}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvTissueFormInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
