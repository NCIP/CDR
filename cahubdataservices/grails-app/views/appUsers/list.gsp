
<%@ page import="nci.obbr.cahub.util.appaccess.AppUsers" %>
<g:set var="bodyclass" value="appusers list" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'appUsers.label', default: 'AppUsers')}" />
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
      <a class="list" href="${createLink(uri: '/accountStatus/acctStatus')}">Users Account Status</a>
      <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
      <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
      <a class="list" href="${createLink(uri: '/appUsers/reports.gsp')}">Create Report</a>
    </div>
  </div>
  <div id="container" class="clearfix">    
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    
     <!--  PMH 01/22/13: include search by name and organization-->
    <g:form action="list">
             <table style="border: 0px">
               <tr>
                 <td> Enter Name: <g:textField name="searchname" value="${searchname}" /></td>
                 <td>Enter Organization: <g:select name="searchorg" from="${nci.obbr.cahub.staticmembers.Organization.list()}" optionKey="code" value="${searchorg}"  noSelection="${['':'Select One...']}"/>
                 &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;   <g:actionSubmit action="list"   value="Search" /></td>
                </tr>
              </table>
     </g:form>
    <!-- end form-->
    
    <div class="list">
      <table>
        <thead>
          <tr>

      
        <g:sortableColumn  style="white-space:nowrap" property="firstname" title="${message(code: 'appUsers.firstname.label', default: 'Firstname (click to edit info)')}" />
        <g:sortableColumn style="white-space:nowrap" property="lastname" title="${message(code: 'appUsers.lastname.label', default: 'Lastname')}" />   
   
        <g:sortableColumn style="white-space:nowrap" property="organization" title="${message(code: 'appUsers.organization.label', default: 'Organization')}" />

        <g:sortableColumn style="white-space:nowrap" property="title" title="${message(code: 'appUsers.title.label', default: 'Title')}" />

        <g:sortableColumn style="white-space:nowrap" property="email" title="${message(code: 'appUsers.email.label', default: 'Email')}" />

        <g:sortableColumn style="white-space:nowrap" property="phone" title="${message(code: 'appUsers.phone.label', default: 'Phone')}" />

        <g:sortableColumn style="white-space:nowrap" property="dateCreated" title="${message(code: 'appUsers.dateCreated.label', default: 'Date Created')}" />
        
        <g:sortableColumn style="white-space:nowrap" property="receiveAlerts" title="${message(code: 'appUsers.receiveAlerts.label', default: 'Receive Alerts')}" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${appUsersInstanceList}" status="i" var="appUsersInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

              <td><g:link action="show" id="${appUsersInstance.id}">${fieldValue(bean: appUsersInstance, field: "firstname")}</g:link></td>

          <td>${fieldValue(bean: appUsersInstance, field: "lastname")}</td>
  
          <td>${fieldValue(bean: appUsersInstance, field: "organization")}</td>

          <td>${fieldValue(bean: appUsersInstance, field: "title")}</td>

          <td>${fieldValue(bean: appUsersInstance, field: "email")}</td>

          <td>${fieldValue(bean: appUsersInstance, field: "phone")}</td>

          <td><g:formatDate date="${appUsersInstance.dateCreated}" /></td>

          <td><g:formatBoolean boolean="${appUsersInstance.receiveAlerts}" /></td>

 
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      
      <g:paginate params="${params}" total="${appUsersInstanceTotal}" />   &nbsp;&nbsp; Total: ${appUsersInstanceTotal}
     
    </div>


  </div>
</body>
</html>
