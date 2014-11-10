

<%@ page import="nci.obbr.cahub.datarecords.ChpTissueRecord" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${chpTissueRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${chpTissueRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${chpTissueRecordInstance?.id}" />
                <g:hiddenField name="version" value="${chpTissueRecordInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="chpTissueRecord.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${chpTissueRecordInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicComments"><g:message code="chpTissueRecord.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${chpTissueRecordInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeInCass"><g:message code="chpTissueRecord.timeInCass.label" default="Time In Cass" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'timeInCass', 'errors')}">
                                    <g:datePicker name="timeInCass" precision="day" value="${chpTissueRecordInstance?.timeInCass}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeInFix"><g:message code="chpTissueRecord.timeInFix.label" default="Time In Fix" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'timeInFix', 'errors')}">
                                    <g:datePicker name="timeInFix" precision="day" value="${chpTissueRecordInstance?.timeInFix}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeInProcessor"><g:message code="chpTissueRecord.timeInProcessor.label" default="Time In Processor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'timeInProcessor', 'errors')}">
                                    <g:datePicker name="timeInProcessor" precision="day" value="${chpTissueRecordInstance?.timeInProcessor}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="procTimeEnd"><g:message code="chpTissueRecord.procTimeEnd.label" default="Proc Time End" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'procTimeEnd', 'errors')}">
                                    <g:datePicker name="procTimeEnd" precision="day" value="${chpTissueRecordInstance?.procTimeEnd}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="procTimeRemov"><g:message code="chpTissueRecord.procTimeRemov.label" default="Proc Time Remov" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'procTimeRemov', 'errors')}">
                                    <g:datePicker name="procTimeRemov" precision="day" value="${chpTissueRecordInstance?.procTimeRemov}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeEmbedded"><g:message code="chpTissueRecord.timeEmbedded.label" default="Time Embedded" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'timeEmbedded', 'errors')}">
                                    <g:datePicker name="timeEmbedded" precision="day" value="${chpTissueRecordInstance?.timeEmbedded}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalGUID"><g:message code="chpTissueRecord.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${chpTissueRecordInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicVersion"><g:message code="chpTissueRecord.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: chpTissueRecordInstance, field: 'publicVersion')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specimenRecord"><g:message code="chpTissueRecord.specimenRecord.label" default="Specimen Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpTissueRecordInstance, field: 'specimenRecord', 'errors')}">
                                    <g:select name="specimenRecord.id" from="${nci.obbr.cahub.datarecords.SpecimenRecord.list()}" optionKey="id" value="${chpTissueRecordInstance?.specimenRecord?.id}"  />
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
