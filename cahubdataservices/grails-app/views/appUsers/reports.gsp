<%@ page import="nci.obbr.cahub.util.appaccess.AppUsers" %>
<g:set var="bodyclass" value="appusers create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'appUsers.label', default: 'AppUsers')}" />
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
      <a class="list" href="${createLink(uri: '/accountStatus/acctStatus')}">Users Account Status</a>
      <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
      <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
      <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
    </div>
  </div>
  <div id="container" class="clearfix"> 
    <h1>Export Application User Information</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${appUsersInstance}">
      <div class="errors">
        <g:renderErrors bean="${appUsersInstance}" as="list" />
      </div>
    </g:hasErrors>

    <g:form method="post" controller="reports">
      <div class="dialog">
        <table>
          <tbody>



            <tr class="prop">
              <td valign="top" class="name">
                <label for="organization">Select Organization:</label>
              </td>
              <td valign="top" >
          <g:select name="orgid" from="${nci.obbr.cahub.staticmembers.Organization.list()}" optionKey="id" value="${appUsersInstance?.organization?.id}" noSelection="['ALL': 'ALL ORGANIZATIONS']" />
          </td>
          </tr>


          <tr class="prop">
            <td valign="top" class="name">
              <label for="receiveAlerts">View Receive Alerts status: </label>
            </td>
            <td valign="top" >
          <g:checkBox name="receiveAlerts"  />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="receiveAlerts">Export Format:</label>
            </td>
            <td valign="top" >

          <g:select name="format" from="${['CSV', 'EXCEL','PDF']}" />
          </td>
          </tr>

          </tbody>
        </table>
        <div class="buttons">
          <g:actionSubmit  class="save" action="appUser" value="Export"  />
        </div>
    </g:form>
  </div>


</body>
</html>
