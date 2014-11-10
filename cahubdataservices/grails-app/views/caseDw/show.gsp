
<%@ page import="nci.obbr.cahub.datawarehouse.CaseDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseDw.label', default: 'CaseDw')}" />
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
                            <td valign="top" class="name"><g:message code="caseDw.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.caseRecord.label" default="Case Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="caseRecord" action="show" id="${caseDwInstance?.caseRecord?.id}">${caseDwInstance?.caseRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.caseId.label" default="Case Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "caseId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.PMI.label" default="PMI" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "PMI")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.avgFixTime.label" default="Avg Fix Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "avgFixTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.brain.label" default="Brain" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "brain")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.caseCollectionType.label" default="Case Collection Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="caseCollectionType" action="show" id="${caseDwInstance?.caseCollectionType?.id}">${caseDwInstance?.caseCollectionType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${caseDwInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.maxFixTime.label" default="Max Fix Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "maxFixTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.minFixTime.label" default="Min Fix Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "minFixTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.procedureDuration.label" default="Procedure Duration" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: caseDwInstance, field: "procedureDuration")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseDw.specimens.label" default="Specimens" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${caseDwInstance.specimens}" var="s">
                                    <li><g:link controller="specimenDw" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${caseDwInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
