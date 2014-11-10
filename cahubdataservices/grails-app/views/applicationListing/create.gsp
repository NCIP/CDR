

<%@ page import="nci.obbr.cahub.util.appaccess.ApplicationListing" %>
<g:set var="bodyclass" value="applicationlisting create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'applicationListing.label', default: 'ApplicationListing')}" />
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
     <a class="list" href="${createLink(uri: '/accountStatus/acctStatus')}">Users Account Status</a>
     <g:link class="list"  controller="applicationListing" action="list">Available Applications List</g:link>
     <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
     <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">  
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${applicationListingInstance}">
      <div class="errors">
        <g:renderErrors bean="${applicationListingInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form action="save" >
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="name"><g:message code="applicationListing.name.label" default="Name" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: applicationListingInstance, field: 'name', 'errors')}">
          <g:textField name="name" value="${applicationListingInstance?.name}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="code"><g:message code="applicationListing.code.label" default="Code" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: applicationListingInstance, field: 'code', 'errors')}">
          <g:textField name="code" value="${applicationListingInstance?.code}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="description"><g:message code="applicationListing.description.label" default="Description" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: applicationListingInstance, field: 'description', 'errors')}">
          <g:textArea name="description" cols="40" rows="5" value="${applicationListingInstance?.description}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="opEmail"><g:message code="applicationListing.opEmail.label" default="Op Email" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: applicationListingInstance, field: 'opEmail', 'errors')}">
          <g:textField name="opEmail" value="${applicationListingInstance?.opEmail}" />
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
