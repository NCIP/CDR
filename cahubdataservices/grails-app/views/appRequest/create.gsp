

<%@ page import="nci.obbr.cahub.util.appaccess.AppRequest" %>
<g:set var="bodyclass" value="apprequest create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'appRequest.label', default: 'AppRequest')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>

      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix"> 
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${appRequestInstance}">
            <div class="errors">
                <g:renderErrors bean="${appRequestInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalComments"><g:message code="appRequest.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${appRequestInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicComments"><g:message code="appRequest.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${appRequestInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="approvedBy"><g:message code="appRequest.approvedBy.label" default="Approved By" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'approvedBy', 'errors')}">
                                    <g:textField name="approvedBy" value="${appRequestInstance?.approvedBy}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateSubmitted"><g:message code="appRequest.dateSubmitted.label" default="Date Submitted" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'dateSubmitted', 'errors')}">
                                    <g:datePicker name="dateSubmitted" precision="day" value="${appRequestInstance?.dateSubmitted}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCompleted"><g:message code="appRequest.dateCompleted.label" default="Date Completed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'dateCompleted', 'errors')}">
                                    <g:datePicker name="dateCompleted" precision="day" value="${appRequestInstance?.dateCompleted}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="appUser"><g:message code="appRequest.appUser.label" default="App User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'appUser', 'errors')}">
                                    <g:select name="appUser.id" from="${nci.obbr.cahub.util.appaccess.AppUsers.list()}" optionKey="id" value="${appRequestInstance?.appUser?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalGUID"><g:message code="appRequest.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${appRequestInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicVersion"><g:message code="appRequest.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: appRequestInstance, field: 'publicVersion')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status"><g:message code="appRequest.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: appRequestInstance, field: 'status', 'errors')}">
                                    <g:textField name="status" value="${appRequestInstance?.status}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span> 
                  
                </div>
            </g:form>
        </div>
    </body>
</html>
