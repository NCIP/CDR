
<%@ page import="nci.obbr.cahub.util.HelpFileUpload" %>
<g:set var="bodyclass" value="help fileupload show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'helpFileUpload.label', default: 'HelpFileUpload')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.opsName.label" default="Ops Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "uploadedBy")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.fileName.label" default="File Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "fileName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.filePath.label" default="File Path" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "filePath")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.comments.label" default="Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "comments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.uploadTime.label" default="Upload Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "dateCreated")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="helpFileUpload.studyCode.label" default="Study Code" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: helpFileUploadInstance, field: "studyCode")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${helpFileUploadInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
