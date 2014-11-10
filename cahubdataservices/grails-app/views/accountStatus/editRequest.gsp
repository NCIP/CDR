
<%@ page import="nci.obbr.cahub.util.appaccess.AccountStatus" %>
<g:set var="bodyclass" value="accountstatus editrequest" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'accountStatus.label', default: 'AccountStatus')}" />
  <g:set var="appRequestEntityName" value="${message(code: 'appRequest.label', default: 'AppRequest')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>

  <g:javascript>
    $(document).ready(function(){
    $(":input").change(function(){
    document.getElementById("changed").value = "Y"
    // alert("Changed!")
    });

    });

    function sub(){
    //alert("i am here in sub")
    var changed = document.getElementById("changed").value
    if(changed == "Y"){
    alert("Please save the change!")
    return false
    }

    }
    function setNotifyId(id){
    alert("id: " + id)
    document.getElementById("notify_id").value = id

    }

  </g:javascript>


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
            <!--  <span class="menuButton"><g:link class="list"  controller="applicationListing" action="list">Available Applications List</g:link></span>-->
            <g:link class="list" controller="appRequest" action="aclist" >Open Requests</g:link>
            <g:link class="list" controller="appRequest" action="inaclist">Completed/Canceled Requests</g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${apr}">
      <div class="errors">
        <g:renderErrors bean="${apr}" as="list" />
      </div>
    </g:hasErrors>



    <g:form method="post" >
      <input type="hidden" name="changed" value="N" id="changed"/>
      <input type="hidden" name="notify_id" id ="notify_id" />
      <div class="dialog">



        <!-- <h2><g:message code="default.create.label" args="[entityName]" /></h2>-->
        <dl class="formdetails clearfix">
          <dt>Application Request ID:</dt><dd>${apr?.id}</dd>
          <g:hiddenField name="id" value="${apr?.id}" />
          <dt>Status:</dt><dd>${apr?.status}</dd>
          <g:hiddenField name="status" value="${apr?.status?.toUpperCase()}" />

        </dl>




        <table>
          <tbody>
            <tr><td colspan="4" style="background-color:#ececec;font-weight:bold;">User</td></tr>
            <tr class="prop">
              <td class="name">
                <label for="First Name">First Name:</label></td>
          <g:hiddenField name="firstname" value="${apr?.appUser?.firstname?.toUpperCase()}" />
          <td  align="left" class="value ">
${apr?.appUser?.firstname}
          </td>
          <td class="name">
            <label for="Last Name">Last Name:</label></td>
          <g:hiddenField name="lastname" value="${apr?.appUser?.lastname?.toUpperCase()}" />
          <td  align="left" class="value ">
${apr?.appUser?.lastname}
          </td>
          </tr>


          <tr class="prop">
            <td class="name">
              <label for="Organization">Organization:</label></td>
          <g:hiddenField name="organization" value="${apr?.appUser?.organization}" />
          <td  align="left" class="value ">
${apr?.appUser?.organization}
          </td>
          <td class="name">
            <label for="Title">Title:</label></td>
          <g:hiddenField name="title" value="${apr?.appUser?.title?.toUpperCase()}" />
          <td  align="left" class="value ">
${apr?.appUser?.title}
          </td>
          </tr>

           <tr class="prop">
             <td class="name">
            <label for="Title">Email:</label></td>
          <g:hiddenField name="email" value="${apr?.appUser?.email}" />
          <td  align="left" class="value ">
           ${apr?.appUser?.email}
          </td>
            <td valign="top" class="name">
              <label for="approvedBy"><g:message code="approvedBy" default="Approved By:" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: apr, field: 'approvedBy', 'errors')}">


          <g:if test="${apr?.status == null || apr?.status?.toUpperCase().equalsIgnoreCase('CREATED')}">

            <g:textField name="approvedBy" value="${apr?.approvedBy}" />
          </g:if>
          <g:else>
            <g:textField name="approvedBy" value="${apr?.approvedBy}" DISABLED='true'/>

          </g:else>
          </td>
          </tr> 
          </tbody>
        </table>
        <table >
          <tbody>
            <tr><td colspan="4" style="background-color:#ececec;font-weight:bold;">Enter Request  </td></tr>
            <tr class="prop" >
              <td valign="top" class="name">Application: </td> 


              <td valign="top" class="${errorMap.get('appid')}">
          <g:if test="${apr?.status == null || apr?.status?.toUpperCase().equalsIgnoreCase('CREATED')}">
            <g:select name="appid" from="${allowedAppsList}"   optionKey="name" value="${apr?.applicationListing?.name}"  noSelection="['': 'Select Application']" />
          </g:if>
          <g:else>
            <g:select name="appid" from="${allowedAppsList}"  optionKey="name" value="${apr?.applicationListing?.name}"  noSelection="['': 'Select Application']" DISABLED="true"/>
          </g:else>
          </td>

          </tr>


          <tr class="prop" >

            <td valign="top" class="name">Action: </td> 

          <g:if test="${apr?.status == null || apr?.status?.toUpperCase().equalsIgnoreCase('CREATED')}"> 

            <td valign="top" class="" id="update">

              <table>
                <tr>
                  <td valign="top" class="value ${hasErrors(bean: apr, field: 'action_apr.id', 'errors')}" NOWRAP> 
                <g:radio name="action_${apr.id}"  id="r1" value="create" checked="${apr.action =='create'}"/>&nbsp;<label for="r1">Create</label>
                
                &nbsp;&nbsp;&nbsp; <g:radio name="action_${apr.id}" id="r2"  value="deactivate" checked="${apr.action =='deactivate'}"/>&nbsp;<label for="r2">Deactivate</label>
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}" id="r3"  value="reactivate" checked="${apr.action =='reactivate'}"/>&nbsp;<label for="r3">Reactivate</label>
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}"  id="r4" value="change_role" checked="${apr.action =='change_role'}"/>&nbsp;<label for="r4">Change Role</label> 
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}"  id="r5" value="other" checked="${apr.action =='other'}"/>&nbsp;<label for="r5">Other</label>  


            </td>
            </tr>
            <tr><td  class="${errorMap.get('role_' +apr.id)}">Role:<BR>
            <g:textField name="role" value="${apr?.role}" />

            </td>
            <td  class="${errorMap.get('otherDescription')}">Provide justification for this request:
               <g:textArea name="otherDescription" cols="40" rows="5" value="${apr?.otherDescription}" />
            </td>
            </tr>
            </table> 
            </td>
          </g:if>

          <g:else>

            <td valign="top" class="value" id="update">

              <table>
                <tr>
                  <td valign="top" class="value ${hasErrors(bean: apr, field: 'action_apr.id', 'errors')}" NOWRAP> 
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}" id="r1" value="create" checked="${apr.action =='create'}" DISABLED='true'/>&nbsp;<label for="r1">Create</label>
                &nbsp;&nbsp;&nbsp;   <g:radio name="action_${apr.id}"  id="r2"  value="deactivate" checked="${apr.action =='deactivate'}" DISABLED='true'/>&nbsp;<label for="r2">Deactivate</label>
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}"  id="r3"  value="reactivate" checked="${apr.action =='reactivate'}" DISABLED='true'/>&nbsp;<label for="r3">Reactivate</label>
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}"  id="r4" value="change_role" checked="${apr.action =='change_role'}" DISABLED='true'/>&nbsp;<label for="r4">Change Role</label> 
               &nbsp;&nbsp;&nbsp;  <g:radio name="action_${apr.id}"  id="r5" value="other" checked="${apr.action =='other'}" DISABLED='true'/>&nbsp;<label for="r5">Other</label> 

            </td>
            </tr>

            <tr class="prop" >

              <td  class="${errorMap.get('role_' +apr.id)}">Role:<BR>
            <g:textField name="role" value="${apr?.role}"  DISABLED='true'/>
            </td>
             <td  class="${errorMap.get('otherDescription')}">Provide justification for this request:
                <g:textArea name="otherDescription" cols="40" rows="5" value="${apr?.otherDescription}" DISABLED='true'/>
            </td>
            
            </tr>
            </table> 
            </td>

          </g:else>


          </tr>


          <tr class="prop" >
            <td valign="top" class="name">Performed By: </td> 

          <g:if test= "${apr?.status?.toUpperCase().equals('ASSIGNED') && apr?.action !=null}"> 
            <td  class="${errorMap.get('performedBy_' +apr.id)}">
            <g:textField name="performedBy" value="${apr?.performedBy}" />
            </td>
          </g:if>
          <g:else>
            <td valign="top" class="name">
            <g:textField name="performedBy" value="${apr?.performedBy}" disabled="true"/> 
            </td>

          </g:else> 

          </tr>


          <tr class="prop" >
            <td valign="top" class="name">Date performed: </td> 
          <g:if test= "${apr?.status?.toUpperCase().equals('ASSIGNED') && apr?.action !=null}"> 

            <td  class="${errorMap.get('datePerformed_' +apr.id)}">
            <g:jqDatePicker name="datePerformed" value="${apr?.datePerformed}" />
            </td>
          </g:if>

          <g:else>
            <td valign="top" class="name"> 
            <g:textField name="datePerformed" value="${apr?.datePerformed}" disabled="true" />
            </td>
          </g:else>

          </tr>











          </tbody>
        </table>






      </div>
      <div class="buttons">






        <g:if test= "${!apr?.status || apr?.status?.equalsIgnoreCase('CREATED')}"> 

          <span class="button"><g:actionSubmit class="save" value="Save" action="updateRequest" style="display:visible" /></span>
          <g:if test="${canSubmit=='Yes'}">
            <span class="button"><g:actionSubmit  class="save" value="Submit"  action="submitRequest" style="display:visible" onclick="return sub()"/></span>
          </g:if>
          <span class="button"><g:actionSubmit class="show" value="Cancel" action="cancelRequest" style="display:visible" onclick="return confirm('Do you really want to cancel the request?')"/></span>
        </g:if>




        <g:if test= "${apr?.status?.equalsIgnoreCase('SUBMITTED')}">
          <span class="button"><g:actionSubmit class="show"  value="Assign"  action="assignRequest" style="display:visible" /></span>
          <span class="button"><g:actionSubmit class="show" value="Cancel" action="cancelRequest" style="display:visible" onclick="return confirm('Do you really want to cancel the request?')"/></span>

        </g:if>

        <g:if test= "${apr?.status?.equalsIgnoreCase('ASSIGNED')}">
          <span class="button"><g:actionSubmit class="save" value="Save" action="updateRequest" style="display:visible" /></span>
          <span class="button"><g:actionSubmit class="show" value="Cancel" action="cancelRequest" style="display:visible" onclick="return confirm('Do you really want to cancel the request?')"/></span>
          <g:if test="${apr?.performedBy && apr?.datePerformed}">
            <span class="button"><g:actionSubmit class="show" value="Notify" action="notifyAdmin" style="display:visible" /></span>
          </g:if>
        </g:if>
        <g:if test= "${apr?.status?.equalsIgnoreCase('TO_BE_COMPLETED')}">
          <span class="button"><g:actionSubmit class="show"  value="Complete"  action="completeRequest" style="display:visible" onclick="return sub()"/></span>
        </g:if>
      </div>
    </g:form>
  </div>
</body>
</html>

