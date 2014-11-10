

<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bpvTissueForm.label', default: 'BpvTissueForm')}" />
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
            <g:hasErrors bean="${bpvTissueFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${bpvTissueFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${bpvTissueFormInstance?.id}" />
                <g:hiddenField name="version" value="${bpvTissueFormInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="bpvTissueForm.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${bpvTissueFormInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicComments"><g:message code="bpvTissueForm.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${bpvTissueFormInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgDate"><g:message code="bpvTissueForm.surgDate.label" default="Surg Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'surgDate', 'errors')}">
                                    <g:datePicker name="surgDate" precision="day" value="${bpvTissueFormInstance?.surgDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="firstIncis"><g:message code="bpvTissueForm.firstIncis.label" default="First Incis" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'firstIncis', 'errors')}">
                                    <g:datePicker name="firstIncis" precision="day" value="${bpvTissueFormInstance?.firstIncis}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clamp1Time"><g:message code="bpvTissueForm.clamp1Time.label" default="Clamp1 Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'clamp1Time', 'errors')}">
                                    <g:datePicker name="clamp1Time" precision="day" value="${bpvTissueFormInstance?.clamp1Time}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clamp2Time"><g:message code="bpvTissueForm.clamp2Time.label" default="Clamp2 Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'clamp2Time', 'errors')}">
                                    <g:datePicker name="clamp2Time" precision="day" value="${bpvTissueFormInstance?.clamp2Time}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="resectTime"><g:message code="bpvTissueForm.resectTime.label" default="Resect Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'resectTime', 'errors')}">
                                    <g:datePicker name="resectTime" precision="day" value="${bpvTissueFormInstance?.resectTime}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgComment"><g:message code="bpvTissueForm.surgComment.label" default="Surg Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'surgComment', 'errors')}">
                                    <g:textField name="surgComment" value="${bpvTissueFormInstance?.surgComment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="grossTimeIn"><g:message code="bpvTissueForm.grossTimeIn.label" default="Gross Time In" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'grossTimeIn', 'errors')}">
                                    <g:datePicker name="grossTimeIn" precision="day" value="${bpvTissueFormInstance?.grossTimeIn}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="grossDiagx"><g:message code="bpvTissueForm.grossDiagx.label" default="Gross Diagx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'grossDiagx', 'errors')}">
                                    <g:textField name="grossDiagx" value="${bpvTissueFormInstance?.grossDiagx}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="grossTimeOut"><g:message code="bpvTissueForm.grossTimeOut.label" default="Gross Time Out" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'grossTimeOut', 'errors')}">
                                    <g:datePicker name="grossTimeOut" precision="day" value="${bpvTissueFormInstance?.grossTimeOut}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissSop"><g:message code="bpvTissueForm.tissSop.label" default="Tiss Sop" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'tissSop', 'errors')}">
                                    <g:textField name="tissSop" value="${bpvTissueFormInstance?.tissSop}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissDissecTime"><g:message code="bpvTissueForm.tissDissecTime.label" default="Tiss Dissec Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'tissDissecTime', 'errors')}">
                                    <g:datePicker name="tissDissecTime" precision="day" value="${bpvTissueFormInstance?.tissDissecTime}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissComment"><g:message code="bpvTissueForm.tissComment.label" default="Tiss Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'tissComment', 'errors')}">
                                    <g:textField name="tissComment" value="${bpvTissueFormInstance?.tissComment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissGrossId"><g:message code="bpvTissueForm.tissGrossId.label" default="Tiss Gross Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'tissGrossId', 'errors')}">
                                    <g:textField name="tissGrossId" value="${bpvTissueFormInstance?.tissGrossId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dissecStartTime"><g:message code="bpvTissueForm.dissecStartTime.label" default="Dissec Start Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'dissecStartTime', 'errors')}">
                                    <g:datePicker name="dissecStartTime" precision="day" value="${bpvTissueFormInstance?.dissecStartTime}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dissecEndTime"><g:message code="bpvTissueForm.dissecEndTime.label" default="Dissec End Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'dissecEndTime', 'errors')}">
                                    <g:datePicker name="dissecEndTime" precision="day" value="${bpvTissueFormInstance?.dissecEndTime}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseRecord"><g:message code="bpvTissueForm.caseRecord.label" default="Case Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'caseRecord', 'errors')}">
                                    <g:select name="caseRecord.id" from="${nci.obbr.cahub.datarecords.CaseRecord.list()}" optionKey="id" value="${bpvTissueFormInstance?.caseRecord?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalGUID"><g:message code="bpvTissueForm.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${bpvTissueFormInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicVersion"><g:message code="bpvTissueForm.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bpvTissueFormInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: bpvTissueFormInstance, field: 'publicVersion')}" />
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
