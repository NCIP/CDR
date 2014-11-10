<%@ page import="nci.obbr.cahub.datarecords.SlideRecord" %>
<g:set var="bodyclass" value="sliderecord show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'slideRecord.label', default: 'SlideRecord')}" />
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
                            <td valign="top" class="name"><g:message code="slideRecord.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.specimenRecord.label" default="Specimen Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="specimenRecord" action="show" id="${slideRecordInstance?.specimenRecord?.id}">${slideRecordInstance?.specimenRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.imageRecord.label" default="Image Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="imageRecord" action="show" id="${slideRecordInstance?.imageRecord?.id}">${slideRecordInstance?.imageRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.slideId.label" default="Slide Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "slideId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.requestReorder.label" default="Request Reorder" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${slideRecordInstance?.requestReorder}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.reorderType.label" default="Reorder Type" /></td>
                            
                            <td valign="top" class="value">${slideRecordInstance?.reorderType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.reorderReason.label" default="Reorder Reason" /></td>
                            
                            <td valign="top" class="value">${slideRecordInstance?.reorderReason?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.reorderOtherComment.label" default="Reorder Other Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "reorderOtherComment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.reorderRequestDate.label" default="Reorder Request Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${slideRecordInstance?.reorderRequestDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.slideLocation.label" default="Slide Location" /></td>
                            
                            <td valign="top" class="value"><g:link controller="organization" action="show" id="${slideRecordInstance?.slideLocation?.id}">${slideRecordInstance?.slideLocation?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.shipEvents.label" default="Ship Events" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${slideRecordInstance.shipEvents}" var="s">
                                    <li><g:link controller="shippingEvent" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.prcForm.label" default="Prc Form" /></td>
                            
                            <td valign="top" class="value"><g:link controller="prcForm" action="show" id="${slideRecordInstance?.prcForm?.id}">${slideRecordInstance?.prcForm?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.finalSurgicalReview.label" default="Final Surgical Review" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "finalSurgicalReview")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.localPathologyReview.label" default="Local Pathology Review" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "localPathologyReview")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${slideRecordInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${slideRecordInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="slideRecord.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: slideRecordInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${slideRecordInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
