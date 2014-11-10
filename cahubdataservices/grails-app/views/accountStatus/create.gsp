

<%@ page import="nci.obbr.cahub.util.appaccess.AccountStatus" %>
<g:set var="bodyclass" value="accountstatus create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'accountStatus.label', default: 'AccountStatus')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
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
        </div>
      </div>
      <div id="container" class="clearfix">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${accountStatusInstance}">
      <div class="errors">
        <g:renderErrors bean="${accountStatusInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form action="save" >
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="internalComments"><g:message code="accountStatus.internalComments.label" default="Internal Comments" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'internalComments', 'errors')}">
          <g:textArea name="internalComments" cols="40" rows="5" value="${accountStatusInstance?.internalComments}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="publicComments"><g:message code="accountStatus.publicComments.label" default="Public Comments" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'publicComments', 'errors')}">
          <g:textArea name="publicComments" cols="40" rows="5" value="${accountStatusInstance?.publicComments}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="dateCreated2"><g:message code="accountStatus.dateCreated2.label" default="Date Created2" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'dateCreated2', 'errors')}">
          <g:datePicker name="dateCreated2" precision="day" value="${accountStatusInstance?.dateCreated2}" default="none" noSelection="['': '']" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="dateDeactivated"><g:message code="accountStatus.dateDeactivated.label" default="Date Deactivated" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'dateDeactivated', 'errors')}">
          <g:datePicker name="dateDeactivated" precision="day" value="${accountStatusInstance?.dateDeactivated}" default="none" noSelection="['': '']" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="role"><g:message code="accountStatus.role.label" default="Role" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'role', 'errors')}">
          <g:textField name="role" value="${accountStatusInstance?.role}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="appUser"><g:message code="accountStatus.appUser.label" default="App User" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'appUser', 'errors')}">
          <g:select name="appUser.id" from="${nci.obbr.cahub.util.appaccess.AppUsers.list()}" optionKey="id" value="${accountStatusInstance?.appUser?.id}"  />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="applicationListing"><g:message code="accountStatus.applicationListing.label" default="Application Listing" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'applicationListing', 'errors')}">
          <g:select name="applicationListing.id" from="${nci.obbr.cahub.util.appaccess.ApplicationListing.list()}" optionKey="id" value="${accountStatusInstance?.applicationListing?.id}"  />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="internalGUID"><g:message code="accountStatus.internalGUID.label" default="Internal GUID" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'internalGUID', 'errors')}">
          <g:textField name="internalGUID" value="${accountStatusInstance?.internalGUID}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="publicVersion"><g:message code="accountStatus.publicVersion.label" default="Public Version" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: accountStatusInstance, field: 'publicVersion', 'errors')}">
          <g:textField name="publicVersion" value="${fieldValue(bean: accountStatusInstance, field: 'publicVersion')}" />
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
