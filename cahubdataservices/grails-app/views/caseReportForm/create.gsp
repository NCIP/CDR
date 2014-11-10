

<%@ page import="nci.obbr.cahub.forms.gtex.crf.CaseReportForm" %>
<g:set var="bodyclass" value="casereportform create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseReportForm.label', default: 'Case Report Form')}" />
        <g:set var="caseId" value="${(caseReportFormInstance?.caseRecord?.caseId)}" />
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
            <h1><g:message code="default.create.label.with.case.id" args="[entityName,caseId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${caseReportFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${caseReportFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="caseRecord"><g:message code="caseReportForm.caseRecord.label" default="Case Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseReportFormInstance, field: 'caseRecord', 'errors')}">
                                    <g:select name="caseRecord.id" from="${nci.obbr.cahub.datarecords.CaseRecord.list()}" optionKey="id" value="${caseReportFormInstance?.caseRecord?.id}"  />
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
