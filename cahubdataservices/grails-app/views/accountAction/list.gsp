
<%@ page import="nci.obbr.cahub.util.appaccess.AccountAction" %>
<g:set var="bodyclass" value="accountaction list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'accountAction.label', default: 'AccountAction')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accountAction.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'accountAction.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'accountAction.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="role" title="${message(code: 'accountAction.role.label', default: 'Role')}" />
                        
                            <g:sortableColumn property="action" title="${message(code: 'accountAction.action.label', default: 'Action')}" />
                        
                            <th><g:message code="accountAction.appRequest.label" default="App Request" /></th>
                        
                            <th><g:message code="accountAction.applicationListing.label" default="Application Listing" /></th>
                        
                            <g:sortableColumn property="dateCreated" class="dateentry" title="${message(code: 'accountAction.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="internalGUID" title="${message(code: 'accountAction.internalGUID.label', default: 'Internal GUID')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'accountAction.lastUpdated.label', default: 'Last Updated')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountActionInstanceList}" status="i" var="accountActionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accountActionInstance.id}">${fieldValue(bean: accountActionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "role")}</td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "action")}</td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "appRequest")}</td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "applicationListing")}</td>
                        
                            <td><g:formatDate date="${accountActionInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: accountActionInstance, field: "internalGUID")}</td>
                        
                            <td><g:formatDate date="${accountActionInstance.lastUpdated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accountActionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
