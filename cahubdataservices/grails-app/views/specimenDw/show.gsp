
<%@ page import="nci.obbr.cahub.datawarehouse.SpecimenDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'specimenDw.label', default: 'SpecimenDw')}" />
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
                            <td valign="top" class="name"><g:message code="specimenDw.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.specimenId.label" default="Specimen Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "specimenId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.tissueType.label" default="Tissue Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="acquisitionType" action="show" id="${specimenDwInstance?.tissueType?.id}">${specimenDwInstance?.tissueType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.tissueLocation.label" default="Tissue Location" /></td>
                            
                            <td valign="top" class="value"><g:link controller="acquisitionLocation" action="show" id="${specimenDwInstance?.tissueLocation?.id}">${specimenDwInstance?.tissueLocation?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.ischemicTime.label" default="Ischemic Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "ischemicTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.latestRin.label" default="Latest Rin" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "latestRin")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.minFixTime.label" default="Min Fix Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "minFixTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.avgFixTime.label" default="Avg Fix Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "avgFixTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.maxFixTime.label" default="Max Fix Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "maxFixTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.procedureDuration.label" default="Procedure Duration" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "procedureDuration")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.procedureType.label" default="Procedure Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="caseCollectionType" action="show" id="${specimenDwInstance?.procedureType?.id}">${specimenDwInstance?.procedureType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${specimenDwInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${specimenDwInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenDw.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenDwInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${specimenDwInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
