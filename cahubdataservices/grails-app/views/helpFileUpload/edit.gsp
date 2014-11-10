

<%@ page import="nci.obbr.cahub.util.HelpFileUpload" %>
<g:set var="bodyclass" value="help fileupload edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'helpFileUpload.label', default: 'HelpFileUpload')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${helpFileUploadInstance}">
            <div class="errors">
                <g:renderErrors bean="${helpFileUploadInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${helpFileUploadInstance?.id}" />
                <g:hiddenField name="version" value="${helpFileUploadInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="opsName"><g:message code="helpFileUpload.opsName.label" default="Ops Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'uploadedBy', 'errors')}">
                           <!--        <g:textField name="opsName" value="${helpFileUploadInstance?.uploadedBy}" /> -->
                                    ${helpFileUploadInstance?.uploadedBy}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileName"><g:message code="helpFileUpload.fileName.label" default="File Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'fileName', 'errors')}">
                                 <!--   <g:textField name="fileName" value="${helpFileUploadInstance?.fileName}" />-->
                                    ${helpFileUploadInstance?.fileName}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="filePath"><g:message code="helpFileUpload.filePath.label" default="File Path" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'filePath', 'errors')}">
                                 <!--   <g:textField name="filePath" value="${helpFileUploadInstance?.filePath}" /> -->
                                    ${helpFileUploadInstance?.filePath}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comments"><g:message code="helpFileUpload.comments.label" default="Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'comments', 'errors')}">
                                   <g:textArea class="textwide" cols="40" rows="5" name="comments" value="${helpFileUploadInstance?.comments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="uploadTime"><g:message code="helpFileUpload.uploadTime.label" default="Upload Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'dateCreated', 'errors')}">
                                 <!--   <g:textField name="uploadTime" value="${helpFileUploadInstance?.dateCreated}" />-->
                                    ${helpFileUploadInstance?.dateCreated}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="studyCode"><g:message code="helpFileUpload.studyCode.label" default="Study Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'studyCode', 'errors')}">
                                    <g:textField name="studyCode" value="${helpFileUploadInstance?.studyCode}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
