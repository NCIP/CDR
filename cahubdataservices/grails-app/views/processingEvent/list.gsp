
<%@ page import="nci.obbr.cahub.datarecords.ProcessingEvent" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'processingEvent.label', default: 'ProcessingEvent')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'processingEvent.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'processingEvent.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'processingEvent.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="eventId" title="${message(code: 'processingEvent.eventId.label', default: 'Event Id')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'processingEvent.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="internalGUID" title="${message(code: 'processingEvent.internalGUID.label', default: 'Internal GUID')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'processingEvent.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="publicVersion" title="${message(code: 'processingEvent.publicVersion.label', default: 'Public Version')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${processingEventInstanceList}" status="i" var="processingEventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${processingEventInstance.id}">${fieldValue(bean: processingEventInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: processingEventInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: processingEventInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: processingEventInstance, field: "eventId")}</td>
                        
                            <td><g:formatDate date="${processingEventInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: processingEventInstance, field: "internalGUID")}</td>
                        
                            <td><g:formatDate date="${processingEventInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: processingEventInstance, field: "publicVersion")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${processingEventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
