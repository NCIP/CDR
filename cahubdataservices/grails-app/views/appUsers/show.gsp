
<%@ page import="nci.obbr.cahub.util.appaccess.AppUsers" %>
<g:set var="bodyclass" value="appusers show" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'appUsers.label', default: 'AppUsers')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
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
      <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
      <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
      <a class="list" href="${createLink(uri: '/appUsers/reports.gsp')}">Create Report</a>
    </div>
  </div>
  <div id="container" class="clearfix">    
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

          <tr class="prop">
            <td valign="top" class="name"><g:message code="appUsers.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.internalComments.label" default="Internal Comments" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "internalComments")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.publicComments.label" default="Public Comments" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "publicComments")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.organization.label" default="Organization" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "organization")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.organizationDetail.label" default="Organization Detail" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "organizationDetail")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.title.label" default="Title" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "title")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.email.label" default="Email" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "email")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.phone.label" default="Phone" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "phone")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.dateCreated.label" default="Date Created" /></td>

        <td valign="top" class="value"><g:formatDate date="${appUsersInstance?.dateCreated}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.firstname.label" default="Firstname" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "firstname")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.internalGUID.label" default="Internal GUID" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "internalGUID")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.lastUpdated.label" default="Last Updated" /></td>

        <td valign="top" class="value"><g:formatDate date="${appUsersInstance?.lastUpdated}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.lastname.label" default="Lastname" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "lastname")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.publicVersion.label" default="Public Version" /></td>

        <td valign="top" class="value">${fieldValue(bean: appUsersInstance, field: "publicVersion")}</td>

        </tr>

        
        <tr class="prop">
          <td valign="top" class="name"><g:message code="appUsers.receiveAlerts.label" default="Receive Alerts" /></td>

        <td valign="top" class="value"><g:formatBoolean boolean="${appUsersInstance?.receiveAlerts}" /></td>

        </tr>

        </tr>

        </tbody>
      </table>
    </div>
    <div class="buttons">
      <g:form>
        <g:hiddenField name="id" value="${appUsersInstance?.id}" />
        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
      </g:form>
    </div>
  </div>
</body>
</html>
