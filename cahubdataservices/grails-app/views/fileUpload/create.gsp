<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="fileupload create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'Case Attachments')}" />
  <title><g:message code="default.upload.label" args="[entityName]" /></title>
  
  <script type="text/javascript">
   $(document).ready(function() {
     
     $("#fileid").focus(function(){
          $("#fileid").removeClass("errors");
     });
     
      $("#categid").focus(function(){
           $("#categid").removeClass("errors");
     });
    $('#btnSubmit').click(function() { 
      if(!$("#fileid").val().length > 0 && !$("#categid").val().length > 0){
          alert ("Choose File is a required field\nCategory is a required field")
          $("#fileid").addClass("errors");
          $("#categid").addClass("errors");
          return false;
        }      
        else if(!$("#fileid").val().length > 0){
          alert ("Choose File is a required field")
          $("#fileid").addClass("errors");
          return false;
        } 
        else if(!$("#categid").val().length > 0){
          alert ("Category is a required field")
          $("#categid").addClass("errors");
          return false;
        } 
        else {
          $(this).closest("form").submit();
          $("#fileid").removeClass("errors");
          $("#categid").removeClass("errors");
        }
    });
});
</script>
  
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>

      <g:link class="list" action="list">General Uploaded File List</g:link>

    </div>
  </div>
  <div id="container" class="clearfix">
    <g:if test="${fileUploadInstance.caseRecord}">
      <h1><g:message code="default.upload.label" args="[entityName]" /></h1>
    </g:if>
    <g:else>
      <h1>Upload General Attachments</h1>
    </g:else>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${fileUploadInstance}">
      <div class="errors">
        <g:renderErrors bean="${fileUploadInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form action="save" method="post" enctype="multipart/form-data" >
      <div class="dialog">
        <table>
          <tbody>    
            <!-- PMH added on 02/04/2013 :show the case id only for case records. Not for NON CASE related general uploads -->
          <g:if test="${fileUploadInstance.caseRecord}">
            <tr class="prop">
              <td valign="top" class="name">
                <label for="caseId"><g:message code="fileUpload.caseId.label" default="Case Id:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: fileUploadInstance, field: 'caseId', 'errors')}">
            <g:link controller="caseRecord" action="display" id="${fileUploadInstance?.caseRecord?.id}">${fileUploadInstance?.caseRecord?.caseId}</g:link>
            <g:hiddenField name="caseRecord.id" value="${fileUploadInstance?.caseRecord?.id}" />
            <g:hiddenField name="caseId" value="${fileUploadInstance?.caseRecord?.caseId}" />
            <g:hiddenField name="id" value="${fileUploadInstance?.id}" />
            </td>
            </tr>
          </g:if>
          <!-- end PMH -->
          <tr class="prop">
            <td valign="top" class="name">
              <label for="myFile"><g:message code="fileUpload.myFile.label" default="Choose File:" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: fileUploadInstance, field: 'fileName', 'errors')}">
             <input type ="file" id="fileid" name="myFile" size="25"  value=""/>
${fileUploadInstance.fileName}
            </td>
          </tr>

          <!-- PMH added on 02/04/2013-->

          <g:if test="${!fileUploadInstance.caseRecord}">
            <tr class="prop">
              <td valign="top" class="name">
                <label for="study">Study:</label>
              </td>
              <td valign="top" >
            <g:hiddenField name="study.id" value="${session?.study?.id}" />
               <!--        <g:select name="study.id" from="${nci.obbr.cahub.staticmembers.Study.list()}" optionKey="id" value="${session?.study?.id}" noSelection="['null': 'Select one']" /> -->
            ${session?.study?.code}
            </td>
            </tr>
          </g:if>
          
          <!-- PMH added on 12/16/2013-->
          
          <tr class="prop">
            <td valign="top" class="name">
              <label for="category">Organization/BSS:</label>
            </td>
            <td valign="top" >
              ${session?.org?.code}
          
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="category">Category:</label>
            </td>
            <td valign="top" class="required">
            <g:select name="category.id" id="categid" from="${gencatList}" optionKey="id" value="${fileUploadInstance?.category?.id}" noSelection="['': 'Select one']" />
  
          </td>
          </tr>
          <!-- end PMH -->
          
          <g:if test="${(session.org.code == 'OBBR' || session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities?.contains('ROLE_ADMIN')) && session.DM == true }">
            
             <tr class="prop">
              <td valign="top" class="name">
                <label for="hideFromBss">Hide from BSS: </label>
              </td>
              <td valign="top" >
            <g:checkBox name="hideFromBss"  />
            </td>
            </tr>
          </g:if>
         

          <tr class="prop">
            <td valign="top" class="name">
              <label for="comments"><g:message code="fileUpload.comments.label" default="Comments:" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: fileUploadInstance, field: 'comments', 'errors')}">
          <g:textArea class="textmedium" name="comments" cols="40" rows="5" value="${fileUploadInstance?.comments}" />

          </td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        
        <g:if test="${fileUploadInstance.caseRecord}">
          <span class="button"><input type="button" id="btnSubmit" name="create" class="save" value="${message(code: 'default.button.upload.label', default: 'Upload')}" /></span>
          <span class="button"><input class="delete" type="button" value="Cancel" onclick="window.location.href='${createLink(uri: '/')}caseRecord/display/${params.caseRecord?.id}';"></input></span>
        </g:if>
        <g:else>
          <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.upload.label', default: 'Upload')}" /></span>
          <span class="button"><input class="delete" type="button" value="Cancel" onclick="window.location.href='${createLink(uri: '/fileUpload/list')}';"></input></span>
        </g:else>
      </div>
    </g:form>
  </div>
</body>
</html>
