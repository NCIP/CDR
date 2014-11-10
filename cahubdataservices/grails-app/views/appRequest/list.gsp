
<%@ page import="nci.obbr.cahub.util.appaccess.AppRequest" %>
<g:set var="bodyclass" value="apprequest list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'appRequest.label', default: 'AppRequest')}" />

  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div id="nav" class="clearfix">
      <div id="navlist">
        <a class="home" href="${createLink(uri: '/accountStatus/acctStatus')}"><g:message code="default.home.label"/></a>
        <g:link action="list" params="[stat: 'complete']">Completed Requests Listing</g:link>
        <g:link action="list" params="[stat: 'submit']">Active Requests Listing</g:link>
      </div>
    </div>
    <div id="container" class="clearfix">
    <h1><g:message code="default.list.label" args="[entityName]" /> </h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
      <table>
        <thead>
          <tr>
          
        <g:sortableColumn property="id" title="${message(code: 'appRequest.id.label', default: 'Request Id')}" />

        <th><g:message code="appRequest.appUser.label" default="App User" /></th> 

        <g:sortableColumn property="approvedBy" title="${message(code: 'appRequest.approvedBy.label', default: 'Approved By')}" />

        <g:sortableColumn property="status" title="${message(code: 'appRequest.status.label', default: 'Status')}" />
        <g:sortableColumn property="dateSubmitted" title="${message(code: 'appRequest.dateCreated.label', default: 'Date Created')}" />
        <g:sortableColumn property="dateSubmitted" title="${message(code: 'appRequest.dateSubmitted.label', default: 'Date Submitted')}" />

        <g:sortableColumn property="dateCompleted" title="${message(code: 'appRequest.dateCompleted.label', default: 'Date Completed')}" />
        

        </tr>
        </thead>
        <tbody>

        <g:each in="${appRequestInstanceList}" status="i" var="appRequestInstance">
       <g:if test="${params.stat == null || params.stat.equals('submit')}" > 

            <g:if test="${appRequestInstance.status?.equalsIgnoreCase('SUBMITTED')|| appRequestInstance.status?.equalsIgnoreCase('CREATED') || appRequestInstance.status?.equals('submit') }">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

             <!--  <td>${fieldValue(bean: appRequestInstance, field: "id")}</td>-->
                 <td><g:link controller="accountStatus" action="editRequest" id="${appRequestInstance.id}">${fieldValue(bean: appRequestInstance, field: "id")}</g:link> </td>
                <td><g:link controller="accountStatus" action="showDetail" id="${appRequestInstance.appUser.id}">${fieldValue(bean: appRequestInstance, field: "appUser")}</g:link></td>
                <!--  <td>${fieldValue(bean: appRequestInstance, field: "appUser")}</td>-->

              <td>${fieldValue(bean: appRequestInstance, field: "approvedBy").toUpperCase()}</td>
              <td>${fieldValue(bean: appRequestInstance, field: "status").toUpperCase()}</td>
              <td><g:formatDate date="${appRequestInstance.dateCreated}" /></td>

              <td><g:formatDate date="${appRequestInstance.dateSubmitted}" /></td>


              <td><g:formatDate date="${appRequestInstance.dateCompleted}" /></td>
              <!-- <td> ${params.stat}</td>-->

              </tr>
              </g:if>
            </g:if>
     
          <g:else>

            <g:if test="${params.stat.equals('complete') && appRequestInstance.status?.equalsIgnoreCase('completed')|| appRequestInstance.status?.equalsIgnoreCase('cancelled') }">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">



                <!--<td>${fieldValue(bean: appRequestInstance, field: "id")}</td>-->
                 <td><g:link controller="accountStatus" action="editRequest" id="${appRequestInstance.id}">${fieldValue(bean: appRequestInstance, field: "id")}</g:link></td>
                <td><g:link controller="accountStatus" action="showDetail" id="${appRequestInstance.appUser.id}">${fieldValue(bean: appRequestInstance, field: "appUser")}</g:link></td>
                <!--  <td>${fieldValue(bean: appRequestInstance, field: "appUser")}</td>-->

              <td>${fieldValue(bean: appRequestInstance, field: "approvedBy").toUpperCase()}</td>
              <td>${fieldValue(bean: appRequestInstance, field: "status").toUpperCase()}</td>
              <td><g:formatDate date="${appRequestInstance.dateCreated}" /></td>

              <td><g:formatDate date="${appRequestInstance.dateSubmitted}" /></td>


              <td><g:formatDate date="${appRequestInstance.dateCompleted}" /></td>
            </g:if>
          </g:else>

        </g:each>
        </tbody>



      </table>
      
    </div>
    <div class="paginateButtons">
      <g:paginate total="${appRequestInstanceTotal}" />
    </div>
  </div>
</body>
</html>
