
<%@ page import="nci.obbr.cahub.util.appaccess.AccountStatus" %>
<g:set var="bodyclass" value="accountstatus list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'accountStatus.label', default: 'AccountStatus')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
     <div id="nav" class="clearfix">
        <div id="navlist">
            <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
            session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
              session.authorities?.contains("ROLE_ADMIN")}'>

      <a class="home" href="${createLink(uri: '/home/opshome')}"><g:message code="default.home.label"/></a>
    </g:if>
    
    <g:else>
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </g:else>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accountStatus.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'accountStatus.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'accountStatus.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="dateCreated2" title="${message(code: 'accountStatus.dateCreated2.label', default: 'Date Created2')}" />
                        
                            <g:sortableColumn property="dateDeactivated" title="${message(code: 'accountStatus.dateDeactivated.label', default: 'Date Deactivated')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'accountStatus.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="role" title="${message(code: 'accountStatus.role.label', default: 'Role')}" />
                        
                            <th><g:message code="accountStatus.appUser.label" default="App User" /></th>
                        
                            <th><g:message code="accountStatus.applicationListing.label" default="Application Listing" /></th>
                        
                            <g:sortableColumn property="internalGUID" title="${message(code: 'accountStatus.internalGUID.label', default: 'Internal GUID')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountStatusInstanceList}" status="i" var="accountStatusInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accountStatusInstance.id}">${fieldValue(bean: accountStatusInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accountStatusInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: accountStatusInstance, field: "publicComments")}</td>
                        
                            <td><g:formatDate date="${accountStatusInstance.dateCreated2}" /></td>
                        
                            <td><g:formatDate date="${accountStatusInstance.dateDeactivated}" /></td>
                        
                            <td><g:formatDate date="${accountStatusInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: accountStatusInstance, field: "role")}</td>
                        
                            <td>${fieldValue(bean: accountStatusInstance, field: "appUser")}</td>
                        
                            <td>${fieldValue(bean: accountStatusInstance, field: "applicationListing")}</td>
                        
                            <td>${fieldValue(bean: accountStatusInstance, field: "internalGUID")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accountStatusInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
