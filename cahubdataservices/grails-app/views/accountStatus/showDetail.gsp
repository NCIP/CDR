<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page import="nci.obbr.cahub.util.appaccess.AccountStatus" %>
<g:set var="bodyclass" value="accountstatus show" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'accountStatus.label', default: 'AccountStatus')}" />
  <g:set var="appRequestEntityName" value="${message(code: 'appRequest.label', default: 'View/Edit Application Request ')}" />
  <g:set var="counter" value="${1}" />

  <title><g:message code="default.list.label" args="[entityName]" /></title>

  <style type="text/css">
    table,  td,  th{border:1px solid #ddd;border-collapse: collapse}
  </style>
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
      <g:link class="list" controller="appUsers" action="list">Application Users List</g:link>
      <!--<span class="menuButton"><g:link class="list"  controller="applicationListing" action="list">Available Applications List</g:link></span>-->
      <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
      <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">

    <h1>Account Status Details</h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
      <table >
        <thead>
        <tbody>
          <tr  class='prop even' > 
            <td colspan="2" class="name ">
              <span >  <g:link  controller="appUsers" action="show" id="${aui?.id}">User profile</g:link></span>
            </td>
          </tr>
          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
            <td class="name "><g:message code="appUsers.firstname.label" default="Firstname" /></td>

        <td class="value ">${aui.firstname}</td>

        </tr>



        <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
          <td class="name"><g:message code="appUsers.lastname.label" default="Lastname" /></td>

        <td class="value">${aui.lastname}</td>

        </tr>



        <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
          <td valign="top" class="name"><g:message code="appUsers.organization.label" default="Organization" /></td>

        <td valign="top" class="value">${aui.organization}</td>

        </tr>


        <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
          <td valign="top" class="name"><g:message code="appUsers.title.label" default="Title" /></td>

        <td valign="top" class="value">${aui.title}</td>

        </tr>
        <!-- deprecated as of CDR relese 5.0 07/2013
        <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
          <td valign="top" class="name"><g:message code="appUsers.hipaaTraining.label" default="HIPAA Training" /></td>

        <td valign="top" class="value">${aui.hipaaTraining}</td>

        </tr>
        
        <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
          <td valign="top" class="name"><g:message code="appUsers.nihSecurityTraining.label" default="NIH Security Training" /></td>

        <td valign="top" class="value">${aui.nihSecurityTraining}</td>

        </tr>
        -->
        
        <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
          <td valign="top" class="name"><g:message code="appUsers.email.label" default="Email" /></td>

        <td valign="top" class="value">${aui.email}</td>

        </tr>
        <g:set var="counter" value="${counter + 1}" />
        <g:each in="${asi}" status="i" var="s">

          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
            <td  style="text-align:left" colspan="2">

              <a class="home" href="${createLink(uri: '/')}accountStatus/edit/${s.id}">${s?.applicationListing?.name}</a>
            </td>

          </tr>


          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}">            
            <td class="name ">  Date Created</td>

            <td class="value">
          <g:formatDate format="MM/dd/yyyy" date="${s?.dateCreated2}"/>

          </td>
          </tr>                  


          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}">            
            <td class="name ">   Date Deactivated </td>
            <td class="value">
          <g:formatDate format="MM/dd/yyyy" date="${s?.dateDeactivated }"/>
          </td>
          </tr>


          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}">   
            <td class="name"> Role</td>
            <td class="value"> ${s?.role}

            </td>
          </tr>  
          <g:set var="counter" value="${counter + 1}" />
        </g:each>


        <g:set var="counter" value="${counter + 2}" />
        <g:each in="${adv}" status="i" var="s">
          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}"> 
            <td  class="name" style="text-align:left;color:gray " >

${s}</a>
            </td>
            <td class="name">
              &nbsp;

            </td>
          </tr>

          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}">
            <td class="name ">  Date Created</td>

            <td class="name">
              ---

            </td>
          </tr>                     


          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}">           
            <td class="name ">   Date Deactivated </td>
            <td class=" name">
              ---
            </td>
          </tr>

          <tr class="${(counter % 2) == 0 ? 'prop even' : ' prop odd'}">

            <td class="name"> Role</td>

            <td class=" name">
              ---
            </td>
          </tr>                             
          <g:set var="counter" value="${counter + 1}" />
        </g:each>

        </thead>
        </tbody>

      </table>
      <div class="buttons">
        <table border="0">
          <tr>
            <TD >
          <g:form>
            <g:hiddenField name="id" value="${aui?.id}" />

          <!--  <span class="button"><g:actionSubmit class="save" action="createApprequest" value="Create new request" onclick="return confirm('Are you sure to create a request for user ${aui.firstname} ${aui.lastname}?') "/></span> -->
            <span class="button"><g:actionSubmit class="save" action="createApprequest" value="Create new request" /></span> 
          </g:form>
          </td>

          </tr>
        </table>
      </div>
    </div>

  </div>
</body>
</html>

