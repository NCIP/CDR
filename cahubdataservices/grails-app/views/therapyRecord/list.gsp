
<%@ page import="nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'therapyRecord.label', default: 'TherapyRecord')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'therapyRecord.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'therapyRecord.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'therapyRecord.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="typeOfTherapy" title="${message(code: 'therapyRecord.typeOfTherapy.label', default: 'Type Of Therapy')}" />
                        
                            <g:sortableColumn property="descTherapy" title="${message(code: 'therapyRecord.descTherapy.label', default: 'Desc Therapy')}" />
                        
                            <g:sortableColumn property="therapyDate" title="${message(code: 'therapyRecord.therapyDate.label', default: 'Therapy Date')}" />
                        
                            <g:sortableColumn property="howLongAgo" title="${message(code: 'therapyRecord.howLongAgo.label', default: 'How Long Ago')}" />
                        
                            <g:sortableColumn property="hbcForm" title="${message(code: 'therapyRecord.hbcForm.label', default: 'Hbc Form')}" />
                        
                            <g:sortableColumn property="durationMonths" title="${message(code: 'therapyRecord.durationMonths.label', default: 'Duration Months')}" />
                        
                            <g:sortableColumn property="noOfYearsStopped" title="${message(code: 'therapyRecord.noOfYearsStopped.label', default: 'No Of Years Stopped')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${therapyRecordInstanceList}" status="i" var="therapyRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${therapyRecordInstance.id}">${fieldValue(bean: therapyRecordInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "typeOfTherapy")}</td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "descTherapy")}</td>
                        
                            <td><g:formatDate date="${therapyRecordInstance.therapyDate}" /></td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "howLongAgo")}</td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "hbcForm")}</td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "durationMonths")}</td>
                        
                            <td>${fieldValue(bean: therapyRecordInstance, field: "noOfYearsStopped")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${therapyRecordInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
