
<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<g:set var="bodyclass" value="fileUpload show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'fileUpload')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list">General Uploaded File List</g:link>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Show File Upload Details</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.caseId.label" default="Case ID:" /></td>
                            
                            <td valign="top" class="value">
                            <g:if test="${fileUploadInstance?.caseRecord}">
                              <g:displayCaseRecordLink caseRecord="${fileUploadInstance?.caseRecord}" session="${session}"/>
                              %{-- <g:link controller="caseRecord" action="display" id="${fileUploadInstance?.caseRecord?.id}">${fileUploadInstance?.caseRecord?.caseId}</g:link> --}%                             
                            </g:if>
                            <g:else>
                              N/A
                            </g:else>

                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.fileName.label" default="File Name:" /></td>
                            
                            <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                            
                        </tr>
                    <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") || session.authorities?.contains("ROLE_ADMIN")}'>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.filePath.label" default="File Path:" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: fileUploadInstance, field: "filePath")}</td>
                            
                        </tr>
                    </g:if>                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.comments.label" default="Comments:" /></td>
                            
                            <td valign="top" class="value"><%=fieldValue(bean: fileUploadInstance, field: "comments").replace("\n","<br />")%></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.uploadTime.label" default="Upload Date/Time:" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: fileUploadInstance, field: "uploadTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.bssCode.label" default="Organization/BSS:" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: fileUploadInstance, field: "bssCode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.category.label" default="Category:" /></td>
                            
                            <td valign="top" class="value">${fileUploadInstance?.category}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.study.label" default="Study:" /></td>
                            
                            <td valign="top" class="value">${fileUploadInstance?.study}</td>
                            
                        </tr>
                        
                          <g:if test="${(session.org.code == 'OBBR' || session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities?.contains('ROLE_ADMIN')) && session.DM == true }">
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="fileUpload.hideFromBss.label" default="Hide from BSS:" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${fileUploadInstance?.hideFromBss}" /></td>
                            
                        </tr>
                            </g:if>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${fileUploadInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                  
                </g:form>
            </div>
        </div>
    </body>
</html>
