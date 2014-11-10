

<%@ page import="nci.obbr.cahub.datawarehouse.SpecimenDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'specimenDw.label', default: 'SpecimenDw')}" />
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
            <g:hasErrors bean="${specimenDwInstance}">
            <div class="errors">
                <g:renderErrors bean="${specimenDwInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="specimenRecord"><g:message code="specimenDw.specimenRecord.label" default="Specimen Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'specimenRecord', 'errors')}">
                                    <g:select name="specimenRecord.id" from="${nci.obbr.cahub.datarecords.SpecimenRecord.list()}" optionKey="id" value="${specimenDwInstance?.specimenRecord?.id}"  />
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
                                    <label for="caseDw"><g:message code="specimenDw.caseDw.label" default="Case Dw" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenDwInstance, field: 'caseDw', 'errors')}">
                                    <g:select name="caseDw.id" from="${nci.obbr.cahub.datawarehouse.CaseDw.list()}" optionKey="id" value="${specimenDwInstance?.caseDw?.id}"  />
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
