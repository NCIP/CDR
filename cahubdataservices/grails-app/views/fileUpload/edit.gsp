<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<g:set var="bodyclass" value="fileupload edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'Case Attachments')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  
  <script type="text/javascript">
   $(document).ready(function() {
     
           $("#categid").focus(function(){
           $("#categid").removeClass("errors");
     });
    $('#btnSubmit').click(function() { 
      if($("#categid").val()== 'null'){
          alert ("Category is a required field")
          $("#categid").addClass("errors");
          return false;
        } 
        else {
          $(this).closest("form").submit();
         
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
      <h1>Edit General File Uploads</h1>
    </g:else>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${fileUploadInstance}">
      <div class="errors">
        <g:renderErrors bean="${fileUploadInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" >
      <g:hiddenField name="id" value="${fileUploadInstance?.id}" />
      <g:hiddenField name="version" value="${fileUploadInstance?.version}" />
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="caseId"><g:message code="fileUpload.caseId.label" default="Case ID:" /></label>
              </td>
              <td>
                <g:if test="${fileUploadInstance?.caseRecord}">
                  <g:link controller="caseRecord" action="display" id="${fileUploadInstance?.caseRecord?.id}">${fileUploadInstance?.caseRecord?.caseId}</g:link>                              
                </g:if>
                <g:else>
                 N/A
                </g:else>
              </td>
           </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="fileName"><g:message code="fileUpload.fileName.label" default="File Name:" /></label>
              </td>
               <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
            </tr>
<g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") || session.authorities?.contains("ROLE_ADMIN")}'>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="filePath"><g:message code="fileUpload.filePath.label" default="File Path:" /></label>
              </td>
              <td valign="top" class="value">
${fileUploadInstance?.filePath}
              </td>
            </tr>
</g:if>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="comments"><g:message code="fileUpload.comments.label" default="Comments:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: fileUploadInstance, field: 'comments', 'errors')}">
          <g:textArea name="comments" cols="40" rows="5" value="${fileUploadInstance?.comments}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="uploadTime"><g:message code="fileUpload.uploadTime.label" default="Upload Date/Time:" /></label>
            </td>
            <td valign="top" class="value">
${fileUploadInstance?.uploadTime}
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="bssCode"><g:message code="fileUpload.bssCode.label" default="Organization/BSS:" /></label>
            </td>
            <td valign="top" class="value">
${fileUploadInstance?.bssCode}
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="category"><g:message code="fileUpload.category.label" default="Category:" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: fileUploadInstance, field: 'category', 'errors')}">
          <g:select name="category.id" id="categid" from="${gencatList}" optionKey="id" value="${fileUploadInstance?.category?.id}" noSelection="['null': 'Select one']" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="study"><g:message code="fileUpload.study.label" default="Study" /></label>
            </td>
            <td valign="top" class="value">
            <!--    <g:select name="study.id" from="${nci.obbr.cahub.staticmembers.Study.list()}" optionKey="id" value="${fileUploadInstance?.study?.id}" noSelection="['null': '']" /> -->
${fileUploadInstance?.study?.code}
            </td>
          </tr>
          <g:if test="${(session.org.code == 'OBBR' || session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities?.contains('ROLE_ADMIN')) && session.DM == true }">
            <tr class="prop">
              <td valign="top" class="name">
                <label for="hideFromBss"><g:message code="fileUpload.hideFromBss.label" default="Hide from BSS:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: fileUploadInstance, field: 'hideFromBss', 'errors')}">
            <g:checkBox name="hideFromBss" value="${fileUploadInstance?.hideFromBss}" />
            </td>
            </tr>
          </g:if>
          </tbody>
        </table>
      </div>
      <div class="buttons">
        <g:if test="${fileUploadInstance?.caseRecord}">
        <span class="button"><g:actionSubmit class="save" id="btnSubmit" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
        </g:if>
        <g:else>
          <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
        </g:else>
       
      </div>
    </g:form>
  </div>
</body>
</html>
