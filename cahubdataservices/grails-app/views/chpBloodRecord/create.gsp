

<%@ page import="nci.obbr.cahub.datarecords.ChpBloodRecord" %>
<g:set var="bodyclass" value="chpblood create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord')}" />
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
            <g:hasErrors bean="${chpBloodRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${chpBloodRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalComments"><g:message code="chpBloodRecord.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${chpBloodRecordInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicComments"><g:message code="chpBloodRecord.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${chpBloodRecordInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hemolysis"><g:message code="chpBloodRecord.hemolysis.label" default="Hemolysis" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'hemolysis', 'errors')}">
                                    <g:textField name="hemolysis" value="${chpBloodRecordInstance?.hemolysis}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bloodProcComment"><g:message code="chpBloodRecord.bloodProcComment.label" default="Blood Proc Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'bloodProcComment', 'errors')}">
                                    <g:textField name="bloodProcComment" value="${chpBloodRecordInstance?.bloodProcComment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bloodStorageComment"><g:message code="chpBloodRecord.bloodStorageComment.label" default="Blood Storage Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'bloodStorageComment', 'errors')}">
                                    <g:textField name="bloodStorageComment" value="${chpBloodRecordInstance?.bloodStorageComment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bloodFrozen"><g:message code="chpBloodRecord.bloodFrozen.label" default="Blood Frozen" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'bloodFrozen', 'errors')}">
                                    <g:datePicker name="bloodFrozen" precision="day" value="${chpBloodRecordInstance?.bloodFrozen}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bloodProcEnd"><g:message code="chpBloodRecord.bloodProcEnd.label" default="Blood Proc End" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'bloodProcEnd', 'errors')}">
                                    <g:datePicker name="bloodProcEnd" precision="day" value="${chpBloodRecordInstance?.bloodProcEnd}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bloodProcStart"><g:message code="chpBloodRecord.bloodProcStart.label" default="Blood Proc Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'bloodProcStart', 'errors')}">
                                    <g:datePicker name="bloodProcStart" precision="day" value="${chpBloodRecordInstance?.bloodProcStart}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bloodStorage"><g:message code="chpBloodRecord.bloodStorage.label" default="Blood Storage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'bloodStorage', 'errors')}">
                                    <g:datePicker name="bloodStorage" precision="day" value="${chpBloodRecordInstance?.bloodStorage}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalGUID"><g:message code="chpBloodRecord.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${chpBloodRecordInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicVersion"><g:message code="chpBloodRecord.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: chpBloodRecordInstance, field: 'publicVersion')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="specimenRecord"><g:message code="chpBloodRecord.specimenRecord.label" default="Specimen Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'specimenRecord', 'errors')}">
                                    <g:select name="specimenRecord.id" from="${nci.obbr.cahub.datarecords.SpecimenRecord.list()}" optionKey="id" value="${chpBloodRecordInstance?.specimenRecord?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="volUnits"><g:message code="chpBloodRecord.volUnits.label" default="Vol Units" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'volUnits', 'errors')}">
                                    <g:textField name="volUnits" value="${chpBloodRecordInstance?.volUnits}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="volume"><g:message code="chpBloodRecord.volume.label" default="Volume" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chpBloodRecordInstance, field: 'volume', 'errors')}">
                                    <g:textField name="volume" value="${fieldValue(bean: chpBloodRecordInstance, field: 'volume')}" />
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
