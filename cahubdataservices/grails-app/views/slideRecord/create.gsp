<%@ page import="nci.obbr.cahub.datarecords.SlideRecord" %>
<g:set var="bodyclass" value="sliderecord create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'slideRecord.label', default: 'SlideRecord')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${slideRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${slideRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalComments"><g:message code="slideRecord.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${slideRecordInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicComments"><g:message code="slideRecord.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${slideRecordInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="specimenRecord"><g:message code="slideRecord.specimenRecord.label" default="Specimen Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'specimenRecord', 'errors')}">
                                    <g:select name="specimenRecord.id" from="${nci.obbr.cahub.datarecords.SpecimenRecord.list()}" optionKey="id" value="${slideRecordInstance?.specimenRecord?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageRecord"><g:message code="slideRecord.imageRecord.label" default="Image Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'imageRecord', 'errors')}">
                                    <g:select name="imageRecord.id" from="${nci.obbr.cahub.datarecords.ImageRecord.list()}" optionKey="id" value="${slideRecordInstance?.imageRecord?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="slideId"><g:message code="slideRecord.slideId.label" default="Slide Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'slideId', 'errors')}">
                                    <g:textField name="slideId" value="${slideRecordInstance?.slideId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="requestReorder"><g:message code="slideRecord.requestReorder.label" default="Request Reorder" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'requestReorder', 'errors')}">
                                    <g:checkBox name="requestReorder" value="${slideRecordInstance?.requestReorder}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="reorderType"><g:message code="slideRecord.reorderType.label" default="Reorder Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderType', 'errors')}">
                                    <g:select name="reorderType" from="${nci.obbr.cahub.datarecords.SlideRecord$ReorderType?.values()}" keys="${nci.obbr.cahub.datarecords.SlideRecord$ReorderType?.values()*.name()}" value="${slideRecordInstance?.reorderType?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="reorderReason"><g:message code="slideRecord.reorderReason.label" default="Reorder Reason" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderReason', 'errors')}">
                                    <g:select name="reorderReason" from="${nci.obbr.cahub.datarecords.SlideRecord$ReorderReason?.values()}" keys="${nci.obbr.cahub.datarecords.SlideRecord$ReorderReason?.values()*.name()}" value="${slideRecordInstance?.reorderReason?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="reorderOtherComment"><g:message code="slideRecord.reorderOtherComment.label" default="Reorder Other Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderOtherComment', 'errors')}">
                                    <g:textArea name="reorderOtherComment" cols="40" rows="5" value="${slideRecordInstance?.reorderOtherComment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="reorderRequestDate"><g:message code="slideRecord.reorderRequestDate.label" default="Reorder Request Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderRequestDate', 'errors')}">
                                    <g:datePicker name="reorderRequestDate" precision="day" value="${slideRecordInstance?.reorderRequestDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="slideLocation"><g:message code="slideRecord.slideLocation.label" default="Slide Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'slideLocation', 'errors')}">
                                    <g:select name="slideLocation.id" from="${nci.obbr.cahub.staticmembers.Organization.list()}" optionKey="id" value="${slideRecordInstance?.slideLocation?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="prcForm"><g:message code="slideRecord.prcForm.label" default="Prc Form" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'prcForm', 'errors')}">
                                    <g:select name="prcForm.id" from="${nci.obbr.cahub.prctumor.PrcForm.list()}" optionKey="id" value="${slideRecordInstance?.prcForm?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="finalSurgicalReview"><g:message code="slideRecord.finalSurgicalReview.label" default="Final Surgical Review" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'finalSurgicalReview', 'errors')}">
                                    <g:textField name="finalSurgicalReview" value="${slideRecordInstance?.finalSurgicalReview}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="localPathologyReview"><g:message code="slideRecord.localPathologyReview.label" default="Local Pathology Review" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'localPathologyReview', 'errors')}">
                                    <g:textField name="localPathologyReview" value="${slideRecordInstance?.localPathologyReview}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalGUID"><g:message code="slideRecord.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${slideRecordInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicVersion"><g:message code="slideRecord.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: slideRecordInstance, field: 'publicVersion')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
