
<%@ page import="nci.obbr.cahub.datarecords.ChpTissueRecord" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'chpTissueRecord.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'chpTissueRecord.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'chpTissueRecord.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="timeInCass" title="${message(code: 'chpTissueRecord.timeInCass.label', default: 'Time In Cass')}" />
                        
                            <g:sortableColumn property="timeInFix" title="${message(code: 'chpTissueRecord.timeInFix.label', default: 'Time In Fix')}" />
                        
                            <g:sortableColumn property="timeInProcessor" title="${message(code: 'chpTissueRecord.timeInProcessor.label', default: 'Time In Processor')}" />
                        
                            <g:sortableColumn property="procTimeEnd" title="${message(code: 'chpTissueRecord.procTimeEnd.label', default: 'Proc Time End')}" />
                        
                            <g:sortableColumn property="procTimeRemov" title="${message(code: 'chpTissueRecord.procTimeRemov.label', default: 'Proc Time Remov')}" />
                        
                            <g:sortableColumn property="timeEmbedded" title="${message(code: 'chpTissueRecord.timeEmbedded.label', default: 'Time Embedded')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'chpTissueRecord.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${chpTissueRecordInstanceList}" status="i" var="chpTissueRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${chpTissueRecordInstance.id}">${fieldValue(bean: chpTissueRecordInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: chpTissueRecordInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: chpTissueRecordInstance, field: "publicComments")}</td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.timeInCass}" /></td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.timeInFix}" /></td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.timeInProcessor}" /></td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.procTimeEnd}" /></td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.procTimeRemov}" /></td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.timeEmbedded}" /></td>
                        
                            <td><g:formatDate date="${chpTissueRecordInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${chpTissueRecordInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
