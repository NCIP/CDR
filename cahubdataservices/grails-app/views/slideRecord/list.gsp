<%@ page import="nci.obbr.cahub.datarecords.SlideRecord" %>
<g:set var="bodyclass" value="sliderecord list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'slideRecord.label', default: 'SlideRecord')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'slideRecord.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'slideRecord.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'slideRecord.publicComments.label', default: 'Public Comments')}" />
                        
                            <th><g:message code="slideRecord.specimenRecord.label" default="Specimen Record" /></th>
                        
                            <th><g:message code="slideRecord.imageRecord.label" default="Image Record" /></th>
                        
                            <g:sortableColumn property="slideId" title="${message(code: 'slideRecord.slideId.label', default: 'Slide Id')}" />
                        
                            <g:sortableColumn property="requestReorder" title="${message(code: 'slideRecord.requestReorder.label', default: 'Request Reorder')}" />
                        
                            <g:sortableColumn property="reorderType" title="${message(code: 'slideRecord.reorderType.label', default: 'Reorder Type')}" />
                        
                            <g:sortableColumn property="reorderReason" title="${message(code: 'slideRecord.reorderReason.label', default: 'Reorder Reason')}" />
                        
                            <g:sortableColumn property="reorderOtherComment" title="${message(code: 'slideRecord.reorderOtherComment.label', default: 'Reorder Other Comment')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${slideRecordInstanceList}" status="i" var="slideRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${slideRecordInstance.id}">${fieldValue(bean: slideRecordInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "specimenRecord")}</td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "imageRecord")}</td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "slideId")}</td>
                        
                            <td><g:formatBoolean boolean="${slideRecordInstance.requestReorder}" /></td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "reorderType")}</td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "reorderReason")}</td>
                        
                            <td>${fieldValue(bean: slideRecordInstance, field: "reorderOtherComment")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${slideRecordInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
