
<%@ page import="nci.obbr.cahub.forms.bpv.BpvBloodForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${bpvBloodFormInstance?.formMetadata?.cdrFormName}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvBloodForm.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'bpvBloodForm.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'bpvBloodForm.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="tissueBankId" title="${message(code: 'bpvBloodForm.tissueBankId.label', default: 'Tissue Bank Id')}" />
                        
                            <g:sortableColumn property="bloodMinimum" title="${message(code: 'bpvBloodForm.bloodMinimum.label', default: 'Blood Minimum')}" />
                        
                            <g:sortableColumn property="bloodDrawType" title="${message(code: 'bpvBloodForm.bloodDrawType.label', default: 'Blood Draw Type')}" />
                        
                            <g:sortableColumn property="bloodDrawTypeOs" title="${message(code: 'bpvBloodForm.bloodDrawTypeOs.label', default: 'Blood Draw Type Os')}" />
                        
                            <g:sortableColumn property="dateTimeBloodDraw" title="${message(code: 'bpvBloodForm.dateTimeBloodDraw.label', default: 'Date Time Blood Draw')}" />
                        
                            <g:sortableColumn property="bloodDrawNurse" title="${message(code: 'bpvBloodForm.bloodDrawNurse.label', default: 'Blood Draw Nurse')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvBloodFormInstanceList}" status="i" var="bpvBloodFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvBloodFormInstance.id}">${fieldValue(bean: bpvBloodFormInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "tissueBankId")}</td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "bloodMinimum")}</td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "bloodDrawType")}</td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "bloodDrawTypeOs")}</td>
                        
                            <td><g:formatDate date="${bpvBloodFormInstance.dateTimeBloodDraw}" /></td>
                        
                            <td>${fieldValue(bean: bpvBloodFormInstance, field: "bloodDrawNurse")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvBloodFormInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
