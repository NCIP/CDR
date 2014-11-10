<%@ page import="nci.obbr.cahub.util.appaccess.AccountAction" %>
<g:set var="bodyclass" value="accountaction edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'accountAction.label', default: 'AccountAction')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${accountActionInstance}">
            <div class="errors">
                <g:renderErrors bean="${accountActionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${accountActionInstance?.id}" />
                <g:hiddenField name="version" value="${accountActionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="accountAction.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${accountActionInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicComments"><g:message code="accountAction.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${accountActionInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="role"><g:message code="accountAction.role.label" default="Role" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'role', 'errors')}">
                                    <g:textField name="role" value="${accountActionInstance?.role}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="action"><g:message code="accountAction.action.label" default="Action" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'action', 'errors')}">
                                    <g:textField name="action" value="${accountActionInstance?.action}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="appRequest"><g:message code="accountAction.appRequest.label" default="App Request" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'appRequest', 'errors')}">
                                    <g:select name="appRequest.id" from="${nci.obbr.cahub.util.appaccess.AppRequest.list()}" optionKey="id" value="${accountActionInstance?.appRequest?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="applicationListing"><g:message code="accountAction.applicationListing.label" default="Application Listing" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'applicationListing', 'errors')}">
                                    <g:select name="applicationListing.id" from="${nci.obbr.cahub.util.appaccess.ApplicationListing.list()}" optionKey="id" value="${accountActionInstance?.applicationListing?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalGUID"><g:message code="accountAction.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${accountActionInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicVersion"><g:message code="accountAction.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accountActionInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: accountActionInstance, field: 'publicVersion')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
