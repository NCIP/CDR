<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueForm" %>
<g:set var="bodyclass" value="show" scope="request"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bpvTissueForm.label', default: 'BpvTissueForm')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.surgDate.label" default="Surg Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.surgDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.firstIncis.label" default="First Incis" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.firstIncis}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.clamp1Time.label" default="Clamp1 Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.clamp1Time}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.clamp2Time.label" default="Clamp2 Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.clamp2Time}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.resectTime.label" default="Resect Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.resectTime}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.surgComment.label" default="Surg Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "surgComment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.grossTimeIn.label" default="Gross Time In" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.grossTimeIn}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.grossDiagx.label" default="Gross Diagx" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "grossDiagx")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.grossTimeOut.label" default="Gross Time Out" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.grossTimeOut}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.tissSop.label" default="Tiss Sop" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "tissSop")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.tissDissecTime.label" default="Tiss Dissec Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.tissDissecTime}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.tissComment.label" default="Tiss Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "tissComment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.tissGrossId.label" default="Tiss Gross Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "tissGrossId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.dissecStartTime.label" default="Dissec Start Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.dissecStartTime}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.dissecEndTime.label" default="Dissec End Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.dissecEndTime}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.specimenRecord.label" default="Specimen Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="specimenRecord" action="show" id="${bpvTissueFormInstance?.specimenRecord?.id}">${bpvTissueFormInstance?.specimenRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bpvTissueFormInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bpvTissueForm.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bpvTissueFormInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${bpvTissueFormInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
