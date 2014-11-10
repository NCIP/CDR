

<%@ page import="nci.obbr.cahub.datawarehouse.CaseDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseDw.label', default: 'CaseDw')}" />
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
            <g:hasErrors bean="${caseDwInstance}">
            <div class="errors">
                <g:renderErrors bean="${caseDwInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalComments"><g:message code="caseDw.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${caseDwInstance?.internalComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicComments"><g:message code="caseDw.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${caseDwInstance?.publicComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="caseRecord"><g:message code="caseDw.caseRecord.label" default="Case Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'caseRecord', 'errors')}">
                                    <g:select name="caseRecord.id" from="${nci.obbr.cahub.datarecords.CaseRecord.list()}" optionKey="id" value="${caseDwInstance?.caseRecord?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="caseId"><g:message code="caseDw.caseId.label" default="Case Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'caseId', 'errors')}">
                                    <g:textField name="caseId" value="${caseDwInstance?.caseId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="PMI"><g:message code="caseDw.PMI.label" default="PMI" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'PMI', 'errors')}">
                                    <g:textField name="PMI" value="${fieldValue(bean: caseDwInstance, field: 'PMI')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="avgFixTime"><g:message code="caseDw.avgFixTime.label" default="Avg Fix Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'avgFixTime', 'errors')}">
                                    <g:textField name="avgFixTime" value="${fieldValue(bean: caseDwInstance, field: 'avgFixTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="brain"><g:message code="caseDw.brain.label" default="Brain" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'brain', 'errors')}">
                                    <g:textField name="brain" value="${caseDwInstance?.brain}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="caseCollectionType"><g:message code="caseDw.caseCollectionType.label" default="Case Collection Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'caseCollectionType', 'errors')}">
                                    <g:select name="caseCollectionType.id" from="${nci.obbr.cahub.staticmembers.CaseCollectionType.list()}" optionKey="id" value="${caseDwInstance?.caseCollectionType?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalGUID"><g:message code="caseDw.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${caseDwInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maxFixTime"><g:message code="caseDw.maxFixTime.label" default="Max Fix Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'maxFixTime', 'errors')}">
                                    <g:textField name="maxFixTime" value="${fieldValue(bean: caseDwInstance, field: 'maxFixTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="minFixTime"><g:message code="caseDw.minFixTime.label" default="Min Fix Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'minFixTime', 'errors')}">
                                    <g:textField name="minFixTime" value="${fieldValue(bean: caseDwInstance, field: 'minFixTime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="procedureDuration"><g:message code="caseDw.procedureDuration.label" default="Procedure Duration" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'procedureDuration', 'errors')}">
                                    <g:textField name="procedureDuration" value="${fieldValue(bean: caseDwInstance, field: 'procedureDuration')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publicVersion"><g:message code="caseDw.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseDwInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: caseDwInstance, field: 'publicVersion')}" />
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
