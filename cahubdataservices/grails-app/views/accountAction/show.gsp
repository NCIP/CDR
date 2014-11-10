
<%@ page import="nci.obbr.cahub.util.appaccess.AccountAction" %>
<g:set var="bodyclass" value="accountaction show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'accountAction.label', default: 'AccountAction')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div  class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.role.label" default="Role" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "role")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.action.label" default="Action" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "action")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.appRequest.label" default="App Request" /></td>
                            
                            <td valign="top" class="value"><g:link controller="appRequest" action="show" id="${accountActionInstance?.appRequest?.id}">${accountActionInstance?.appRequest?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.applicationListing.label" default="Application Listing" /></td>
                            
                            <td valign="top" class="value"><g:link controller="applicationListing" action="show" id="${accountActionInstance?.applicationListing?.id}">${accountActionInstance?.applicationListing?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${accountActionInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${accountActionInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountAction.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountActionInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${accountActionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
