<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueForm" %>
<%@ page import="nci.obbr.cahub.datarecords.ChpTissueRecord" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
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
                            <td valign="top" class="name"><g:message code="chpTissueRecord.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpTissueRecordInstance, field: "id")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecordInstance.specimenRecord.parentSpecimen.bpvTissueForm" default="BPV Tissue Form" /></td>
                            
                            <td valign="top" class="value">${chpTissueRecordInstance?.specimenRecord?.parentSpecimen?.bpvTissueForm?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpTissueRecordInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpTissueRecordInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.timeInCass.label" default="Time In Cass" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.timeInCass}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.timeInFix.label" default="Time In Fix" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.timeInFix}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.timeInProcessor.label" default="Time In Processor" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.timeInProcessor}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.procTimeEnd.label" default="Proc Time End" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.procTimeEnd}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.procTimeRemov.label" default="Proc Time Remov" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.procTimeRemov}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.timeEmbedded.label" default="Time Embedded" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.timeEmbedded}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpTissueRecordInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpTissueRecordInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpTissueRecordInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpTissueRecord.specimenRecord.label" default="Specimen Record" /></td>
                            
                            <td valign="top" class="value">${chpTissueRecordInstance?.specimenRecord?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${chpTissueRecordInstance?.id}" />
                    <!-- span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span -->
                    <!-- span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span -->
                </g:form>
            </div>
        </div>
    </body>
</html>
