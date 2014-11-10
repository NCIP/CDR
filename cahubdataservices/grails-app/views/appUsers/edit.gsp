

<%@ page import="nci.obbr.cahub.util.appaccess.AppUsers" %>
<g:set var="bodyclass" value="appusers edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'appUsers.label', default: 'AppUsers')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
session.authorities?.contains("ROLE_ADMIN")}'>
        <a class="home" href="${createLink(uri: '/home/opshome')}"><g:message code="default.home.label"/></a></span>
      </g:if>
      <g:else>
        <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
      </g:else>
      <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
      <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
      <a class="list" href="${createLink(uri: '/accountStatus/acctStatus')}">Users Account Status</a>
      <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
      <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
      <a class="list" href="${createLink(uri: '/appUsers/reports.gsp')}">Create Report</a>
    </div>
  </div>
  <div id="container" class="clearfix">    
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${appUsersInstance}">
      <div class="errors">
        <g:renderErrors bean="${appUsersInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" >
      <g:hiddenField name="id" value="${appUsersInstance?.id}" />
      <g:hiddenField name="version" value="${appUsersInstance?.version}" />
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="internalComments"><g:message code="appUsers.internalComments.label" default="Internal Comments" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'internalComments', 'errors')}">
          <g:textArea name="internalComments" cols="40" rows="5" value="${appUsersInstance?.internalComments}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="publicComments"><g:message code="appUsers.publicComments.label" default="Public Comments" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'publicComments', 'errors')}">
          <g:textArea name="publicComments" cols="40" rows="5" value="${appUsersInstance?.publicComments}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="organization"><g:message code="appUsers.organization.label" default="Organization" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'organization', 'errors')}">
          <g:select name="organization.id" from="${nci.obbr.cahub.staticmembers.Organization.list()}" optionKey="id" value="${appUsersInstance?.organization?.id}" noSelection="['null': '']" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="organizationDetail"><g:message code="appUsers.organizationDetail.label" default="Organization Detail" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'organizationDetail', 'errors')}">
          <g:textField name="organizationDetail" value="${appUsersInstance?.organizationDetail}" />
          </td>
          </tr>


          <tr class="prop">
            <td valign="top" class="name">
              <label for="title"><g:message code="appUsers.title.label" default="Title" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'title', 'errors')}">
          <g:textField name="title" value="${appUsersInstance?.title}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="email"><g:message code="appUsers.email.label" default="Email" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'email', 'errors')}">
          <g:textField name="email" value="${appUsersInstance?.email}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="phone"><g:message code="appUsers.phone.label" default="Phone" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'phone', 'errors')}">
          <g:textField name="phone" value="${appUsersInstance?.phone}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="firstname"><g:message code="appUsers.firstname.label" default="Firstname" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'firstname', 'errors')}">
          <g:textField name="firstname" value="${appUsersInstance?.firstname}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="internalGUID"><g:message code="appUsers.internalGUID.label" default="Internal GUID" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'internalGUID', 'errors')}">
          <g:textField name="internalGUID" value="${appUsersInstance?.internalGUID}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="lastname"><g:message code="appUsers.lastname.label" default="Lastname" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'lastname', 'errors')}">
          <g:textField name="lastname" value="${appUsersInstance?.lastname}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="publicVersion"><g:message code="appUsers.publicVersion.label" default="Public Version" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'publicVersion', 'errors')}">
          <g:textField name="publicVersion" value="${fieldValue(bean: appUsersInstance, field: 'publicVersion')}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="receiveAlerts"><g:message code="appUsers.receiveAlerts.label" default="Receive Alerts" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: appUsersInstance, field: 'receiveAlerts', 'errors')}">
          <g:checkBox name="receiveAlerts" value="${appUsersInstance?.receiveAlerts}" />
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
