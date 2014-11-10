
<%@ page import="nci.obbr.cahub.datarecords.ChpBloodRecord" %>
<g:set var="bodyclass" value="chpblood list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'chpBloodRecord.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'chpBloodRecord.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'chpBloodRecord.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="hemolysis" title="${message(code: 'chpBloodRecord.hemolysis.label', default: 'Hemolysis')}" />
                        
                            <g:sortableColumn property="bloodProcComment" title="${message(code: 'chpBloodRecord.bloodProcComment.label', default: 'Blood Proc Comment')}" />
                        
                            <g:sortableColumn property="bloodStorageComment" title="${message(code: 'chpBloodRecord.bloodStorageComment.label', default: 'Blood Storage Comment')}" />
                        
                            <g:sortableColumn property="bloodFrozen" title="${message(code: 'chpBloodRecord.bloodFrozen.label', default: 'Blood Frozen')}" />
                        
                            <g:sortableColumn property="bloodProcEnd" title="${message(code: 'chpBloodRecord.bloodProcEnd.label', default: 'Blood Proc End')}" />
                        
                            <g:sortableColumn property="bloodProcStart" title="${message(code: 'chpBloodRecord.bloodProcStart.label', default: 'Blood Proc Start')}" />
                        
                            <g:sortableColumn property="bloodStorage" title="${message(code: 'chpBloodRecord.bloodStorage.label', default: 'Blood Storage')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${chpBloodRecordInstanceList}" status="i" var="chpBloodRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${chpBloodRecordInstance.id}">${fieldValue(bean: chpBloodRecordInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "hemolysis")}</td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "bloodProcComment")}</td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "bloodStorageComment")}</td>
                        
                            <td><g:formatDate date="${chpBloodRecordInstance.bloodFrozen}" /></td>
                        
                            <td><g:formatDate date="${chpBloodRecordInstance.bloodProcEnd}" /></td>
                        
                            <td><g:formatDate date="${chpBloodRecordInstance.bloodProcStart}" /></td>
                        
                            <td><g:formatDate date="${chpBloodRecordInstance.bloodStorage}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${chpBloodRecordInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
