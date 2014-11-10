

<%@ page import="nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'therapyRecord.label', default: 'TherapyRecord')}" />
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
            <g:hasErrors bean="${therapyRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${therapyRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${therapyRecordInstance?.id}" />
                <g:hiddenField name="version" value="${therapyRecordInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="therapyRecord.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${therapyRecordInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicComments"><g:message code="therapyRecord.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${therapyRecordInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="typeOfTherapy"><g:message code="therapyRecord.typeOfTherapy.label" default="Type Of Therapy" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'typeOfTherapy', 'errors')}">
                                    <g:textField name="typeOfTherapy" value="${therapyRecordInstance?.typeOfTherapy}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="descTherapy"><g:message code="therapyRecord.descTherapy.label" default="Desc Therapy" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'descTherapy', 'errors')}">
                                    <g:textArea name="descTherapy" cols="40" rows="5" value="${therapyRecordInstance?.descTherapy}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="therapyDate"><g:message code="therapyRecord.therapyDate.label" default="Therapy Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'therapyDate', 'errors')}">
                                    <g:datePicker name="therapyDate" precision="day" value="${therapyRecordInstance?.therapyDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="howLongAgo"><g:message code="therapyRecord.howLongAgo.label" default="How Long Ago" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'howLongAgo', 'errors')}">
                                    <g:textField name="howLongAgo" value="${therapyRecordInstance?.howLongAgo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hbcForm"><g:message code="therapyRecord.hbcForm.label" default="Hbc Form" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'hbcForm', 'errors')}">
                                    <g:select name="hbcForm" from="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HBCForm?.values()}" keys="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HBCForm?.values()*.name()}" value="${therapyRecordInstance?.hbcForm?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="durationMonths"><g:message code="therapyRecord.durationMonths.label" default="Duration Months" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'durationMonths', 'errors')}">
                                    <g:textField name="durationMonths" value="${fieldValue(bean: therapyRecordInstance, field: 'durationMonths')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="noOfYearsStopped"><g:message code="therapyRecord.noOfYearsStopped.label" default="No Of Years Stopped" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'noOfYearsStopped', 'errors')}">
                                    <g:textField name="noOfYearsStopped" value="${fieldValue(bean: therapyRecordInstance, field: 'noOfYearsStopped')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hrtForm"><g:message code="therapyRecord.hrtForm.label" default="Hrt Form" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'hrtForm', 'errors')}">
                                    <g:select name="hrtForm" from="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTForm?.values()}" keys="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTForm?.values()*.name()}" value="${therapyRecordInstance?.hrtForm?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hrtType"><g:message code="therapyRecord.hrtType.label" default="Hrt Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'hrtType', 'errors')}">
                                    <g:select name="hrtType" from="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTType?.values()}" keys="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTType?.values()*.name()}" value="${therapyRecordInstance?.hrtType?.name()}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bpvClinicalDataEntry"><g:message code="therapyRecord.bpvClinicalDataEntry.label" default="Bpv Clinical Data Entry" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'bpvClinicalDataEntry', 'errors')}">
                                    <g:select name="bpvClinicalDataEntry.id" from="${nci.obbr.cahub.forms.bpv.BpvClinicalDataEntry.list()}" optionKey="id" value="${therapyRecordInstance?.bpvClinicalDataEntry?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalGUID"><g:message code="therapyRecord.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${therapyRecordInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicVersion"><g:message code="therapyRecord.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: therapyRecordInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: therapyRecordInstance, field: 'publicVersion')}" />
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
