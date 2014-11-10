
<%@ page import="nci.obbr.cahub.util.UserLogin" %>
<g:set var="bodyclass" value="userlogin wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'userLogin.label', default: 'UserLogin')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>   
        </div>
      </div>
      <div id="container" class="clearfix">
         <g:form controller="userLogin" action="list">
           <table style="border: 0px">
             <tr> 
               <td> Enter User Name to search: <g:textField name="userName" value="${userName}" />&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                    Enter Organization Code to search: <g:textField name="organization" value="${organization}" />&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                    <font color="brown">If both User Name and Organization are input, Organization will be ignored.</font>
               </td>
             </tr>
             <tr><td>Start:<g:jqDateTimePicker name="start" value="${start}" />
                %{--<tr><td>Start:<g:jqDatePicker name="start" value="${start}" /> --}%
                &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;End: %{--<g:jqDatePicker name="end" value="${end}" /> --}% <g:jqDateTimePicker name="end" value="${end}" /> 
                &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;   <g:actionSubmit action="list"   value="Search" /></td>
             </tr>
           </table>
         </g:form>
         <g:if test="${userLoginInstanceList}">
           <h1>User Login History</h1>
           <p>
             Search range: ${search_range}  &nbsp;&nbsp; displaying ${userLoginInstanceList.size()} of ${userLoginInstanceTotal} results
             <g:if test="${userName}"> for user=<b>${userName}</b>
             </g:if>
             <g:elseif test="${organization}"> for organization=<b>${organization}</b>
             </g:elseif>
           </p>
           <br/>
           <g:if test="${flash.message}">
             <div class="message">${flash.message}</div>
           </g:if>
           <div class="list">
             <table>
               <thead>
                 <tr>
                   <g:sortableColumn property="username" title="${message(code: 'userLogin.username.label', default: 'Username')}" params="${params}"/>
                   <g:sortableColumn property="organization" title="Organization" params="${params}"/>
                   <g:sortableColumn property="loginDate" title="${message(code: 'userLogin.loginDate.label', default: 'Login Date')}" params="${params}"/>
                   <g:sortableColumn property="sessionDestroyed" title="${message(code: 'userLogin.sessionDestroyed.label', default: 'Session Destroyed')}" params="${params}"/>
                   <g:sortableColumn property="logout" title="${message(code: 'userLogin.logout.label', default: 'Logout')}" params="${params}"/>
                   <g:sortableColumn property="sessionCreated" title="${message(code: 'userLogin.sessionCreated.label', default: 'Session Created')}" params="${params}"/>
                   <g:sortableColumn property="sessionLastAccessed" title="${message(code: 'userLogin.sessionLastAccessed.label', default: 'Session Last Accessed')}" params="${params}"/>
                   <g:sortableColumn property="sessionId" title="${message(code: 'userLogin.sessionId.label', default: 'Session Id')}" params="${params}"/>
                 </tr>
               </thead>
               <tbody>
                 <g:each in="${userLoginInstanceList}" status="i" var="userLoginInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      <td>${fieldValue(bean: userLoginInstance, field: "username")}</td>
                      <td>${fieldValue(bean: userLoginInstance, field: "organization")}</td>
                      <td><g:formatDate date="${userLoginInstance.loginDate}" /></td>
                      <td><g:formatDate date="${userLoginInstance.sessionDestroyed}" /></td>
                      <td><g:formatBoolean boolean="${userLoginInstance.logout}" /></td>
                      <td><g:formatDate date="${userLoginInstance.sessionCreated}" /></td>
                      <td><g:formatDate date="${userLoginInstance.sessionLastAccessed}" /></td>
                      <td>${fieldValue(bean: userLoginInstance, field: "sessionId")}</td>
                    </tr>
                 </g:each>
               </tbody>
             </table>
           </div>
           <div class="paginateButtons">
             <g:paginate total="${userLoginInstanceTotal}" max="25" params="${params}" />
                %{--<g:paginate total="${userLoginInstanceTotal}" max="25" params="[start:start, end:end, userName:userName, organization:organization]" /> --}%
           </div>
         </g:if>
         <g:elseif test="${errorMessage}">
           <font color="red"><p><b>${errorMessage}</b></p></font>
         </g:elseif>
         <g:else>
           <p>no search results.</p>
         </g:else>
       </div>
    </body>
</html>
