
<%@ page import="nci.obbr.cahub.util.appaccess.ApplicationListing" %>
<g:set var="bodyclass" value="applicationlisting list" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'applicationListing.label', default: 'ApplicationListing')}" />
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
      <g:link class="list" controller="appUsers" action="list">Application Users list</g:link>
      <a class="list" href="${createLink(uri: '/accountStatus/acctStatus')}">Users Account Status</a>
      <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
      <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
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

        <g:sortableColumn property="id" title="${message(code: 'applicationListing.id.label', default: 'Id')}" />

        <g:sortableColumn property="name" title="${message(code: 'applicationListing.name.label', default: 'Name')}" />

        <g:sortableColumn property="code" title="${message(code: 'applicationListing.code.label', default: 'Code')}" />

        <g:sortableColumn property="description" title="${message(code: 'applicationListing.description.label', default: 'Description')}" />

        <g:sortableColumn property="opEmail" title="${message(code: 'applicationListing.opEmail.label', default: 'Op Email')}" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${applicationListingInstanceList}" status="i" var="applicationListingInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show" id="${applicationListingInstance.id}">${fieldValue(bean: applicationListingInstance, field: "id")}</g:link></td>

          <td>${fieldValue(bean: applicationListingInstance, field: "name")}</td>

          <td>${fieldValue(bean: applicationListingInstance, field: "code")}</td>

          <td>${fieldValue(bean: applicationListingInstance, field: "description")}</td>

          <td>${fieldValue(bean: applicationListingInstance, field: "opEmail")}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${applicationListingInstanceTotal}" />
    </div>
  </div>
</body>
</html>
