

<%@ page import="nci.obbr.cahub.datawarehouse.SpecimenDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'specimenDw.label', default: 'SpecimenDw')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${specimenDwInstance}">
            <div class="errors">
                <g:renderErrors bean="${specimenDwInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${specimenDwInstance?.id}" />
                <g:hiddenField name="version" value="${specimenDwInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="specimenDw.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${specimenDwInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicComments"><g:message code="specimenDw.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${specimenDwInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specimenId"><g:message code="specimenDw.specimenId.label" default="Specimen Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'specimenId', 'errors')}">
                                    <g:textField name="specimenId" value="${specimenDwInstance?.specimenId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissueType"><g:message code="specimenDw.tissueType.label" default="Tissue Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'tissueType', 'errors')}">
                                    <g:select name="tissueType.id" from="${nci.obbr.cahub.staticmembers.AcquisitionType.list()}" optionKey="id" value="${specimenDwInstance?.tissueType?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissueLocation"><g:message code="specimenDw.tissueLocation.label" default="Tissue Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'tissueLocation', 'errors')}">
                                    <g:select name="tissueLocation.id" from="${nci.obbr.cahub.staticmembers.AcquisitionLocation.list()}" optionKey="id" value="${specimenDwInstance?.tissueLocation?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ischemicTime"><g:message code="specimenDw.ischemicTime.label" default="Ischemic Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'ischemicTime', 'errors')}">
                                    <g:textField name="ischemicTime" value="${fieldValue(bean: specimenDwInstance, field: 'ischemicTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="latestRin"><g:message code="specimenDw.latestRin.label" default="Latest Rin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'latestRin', 'errors')}">
                                    <g:textField name="latestRin" value="${fieldValue(bean: specimenDwInstance, field: 'latestRin')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="minFixTime"><g:message code="specimenDw.minFixTime.label" default="Min Fix Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'minFixTime', 'errors')}">
                                    <g:textField name="minFixTime" value="${fieldValue(bean: specimenDwInstance, field: 'minFixTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="avgFixTime"><g:message code="specimenDw.avgFixTime.label" default="Avg Fix Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'avgFixTime', 'errors')}">
                                    <g:textField name="avgFixTime" value="${fieldValue(bean: specimenDwInstance, field: 'avgFixTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="maxFixTime"><g:message code="specimenDw.maxFixTime.label" default="Max Fix Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'maxFixTime', 'errors')}">
                                    <g:textField name="maxFixTime" value="${fieldValue(bean: specimenDwInstance, field: 'maxFixTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="procedureDuration"><g:message code="specimenDw.procedureDuration.label" default="Procedure Duration" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'procedureDuration', 'errors')}">
                                    <g:textField name="procedureDuration" value="${fieldValue(bean: specimenDwInstance, field: 'procedureDuration')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="procedureType"><g:message code="specimenDw.procedureType.label" default="Procedure Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'procedureType', 'errors')}">
                                    <g:select name="procedureType.id" from="${nci.obbr.cahub.staticmembers.CaseCollectionType.list()}" optionKey="id" value="${specimenDwInstance?.procedureType?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalGUID"><g:message code="specimenDw.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${specimenDwInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicVersion"><g:message code="specimenDw.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: specimenDwInstance, field: 'publicVersion')}" />
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
