<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page import="nci.obbr.cahub.util.appaccess.AccountStatus" %>
<g:set var="bodyclass" value="accountstatus" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="appuserEntityName" value="${message(code: 'appUsers.label', default: 'Application Users')}" />
  <g:set var="appListingEntityName" value="${message(code: 'applicationListing.label', default: 'Available Applications')}" />
  <g:set var="entityName" value="${message(code: 'accountStatus.label', default: 'AccountStatus')}" />

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

      <a class="list" href="${createLink(uri: '/accountStatus/acctStatus')}">Users Account Status</a>
      <g:link class="list" controller="appUsers" action="list">Application Users List</g:link>
      <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
      <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <!--  PMH 01/22/13: include search by name and organization-->
    <g:form action="acctStatus">
      <table style="border: 0px">
        <tr>
          <td> Enter Name: <g:textField name="searchname" value="${searchname}" /></td>
        <td>Enter Organization: <g:select name="searchorg" from="${nci.obbr.cahub.staticmembers.Organization.list()}" optionKey="code" value="${searchorg}"  noSelection="${['':'Select One...']}"/>
        &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;   <g:actionSubmit action="acctStatus"   value="Search" /></td>
        </tr>
      </table>
    </g:form>
    <!-- end form-->


    <div class="list">
      <table >
        <thead>
        <tbody>

          <tr class="prop">
            <th valign="top" class="name"><g:message code="appUsers.firstname.label" default="User Name" /> </th>
        <th valign="top" class="name">Aperio </th>
        <th valign="top" class="name">BRIMS </th>
        <th valign="top" class="name">CDR </th>
        <th valign="top" class="name">MasterControl</th>
        </tr>



        <g:each in="${aul}" status="i" var="eachuser"> 
          <tr class="${(i % 2) == 0 ? 'prop even' : ' prop odd'}"> 


            <td> <g:link controller="accountStatus" action="showDetail " params="['id':eachuser.id]">${eachuser.firstname} ${eachuser.lastname} </g:link></td>
          <g:if test="${mmap.get(eachuser.id)}">

            <g:if test="${mmap.get(eachuser.id).get('Aperio')=='YES'}"><td><span class="yes">${mmap.get(eachuser.id).get('Aperio')}</span></td></g:if>
            <g:else><td> <span class="no">NO</span></td></g:else>

            <g:if test="${mmap.get(eachuser.id).get('BRIMS')=='YES'}"><td><span class="yes">${mmap.get(eachuser.id).get('BRIMS')}</span></td></g:if>
            <g:else><td> <span class="no">NO</span></td></g:else>
            <g:if test="${mmap.get(eachuser.id).get('CDR')=='YES'}"><td><span class="yes">${mmap.get(eachuser.id).get('CDR')}</span></td></g:if>
            <g:else><td> <span class="no">NO</span></td></g:else>
            <g:if test="${mmap.get(eachuser.id).get('MasterControl')=='YES'}"><td><span class="yes">${mmap.get(eachuser.id).get('MasterControl')}</span></td></g:if>
            <g:else><td> <span class="no">NO</span></td></g:else>


          </g:if>
          <g:else>
            <td> <span class="no">NO</span></td>
            <td> <span class="no">NO</span></td>
            <td> <span class="no">NO</span></td>
            <td> <span class="no">NO</span></td>

          </g:else>


          </tr>
        </g:each> 


        </tbody>
        </thead>


      </table>

      <div class="paginateButtons">


        <g:paginate params="${params}" total="${total}" />

      </div>


    </div>
    <p> * clicking on the name will take to the acct status detail page</p>
  </div>

</body>
</html>


