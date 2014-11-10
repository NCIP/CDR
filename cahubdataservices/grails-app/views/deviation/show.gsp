<%@ page import="nci.obbr.cahub.util.AppSetting" %> 
<%@ page import="nci.obbr.cahub.util.querytracker.Deviation" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
    
</g:if>
<g:set var="bodyclass" value="deviation show" scope="request"/>
 <g:set var="jira_home" value="${AppSetting.findByCode('JIRA_HOME')}" />
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'deviation.label', default: 'Deviation')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'deviation.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:if test="${deviationInstance?.caseRecord}">
                <a class="list" href="/cahubdataservices/deviation/listByCase?caseRecord.id=${deviationInstance?.caseRecord?.id}">Deviation List for ${deviationInstance?.caseRecord?.caseId}</a>
            </g:if>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div id="show">
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
            </div>
            <div>
                <table class="tdwrap">
                    <tbody>
                        <tr class="prop">
                            <td class="formheader">Responses</td>
                        </tr>
                        <tr class="prop">
                            <td class="name">
                                <g:render template="responseTable" bean="${deviationInstance}" var="deviationInstance" />
                                <g:if test="${(session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code != 'CLOSED' && deviationInstance?.queryStatus?.code != 'INVALID') || (session.org?.code != 'OBBR' && (deviationInstance?.queryStatus?.code == 'OPEN' || deviationInstance?.queryStatus?.code == 'PROGRESS'))}">
                                    <div>
                                        <button id="addResponseBtn">Add</button>
                                    </div>
                                </g:if>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td class="formheader">Documentation of Deviation</td>
                        </tr>
                        <tr class="prop">
                            <td class="name">
                                <g:render template="attachmentTable" bean="${deviationInstance}" var="deviationInstance" />
                                <g:if test="${(session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code != 'CLOSED' && deviationInstance?.queryStatus?.code != 'INVALID') || (session.org?.code != 'OBBR' && (deviationInstance?.queryStatus?.code == 'OPEN' || deviationInstance?.queryStatus?.code == 'PROGRESS'))}">
                                    <div>
                                        <g:link class="uibutton" controller="deviation" action="upload" id="${deviationInstance?.id}">
                                            <span class="ui-icon ui-icon-circle-arrow-n"></span>Upload
                                        </g:link>
                                    </div>
                                </g:if>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <g:render template="responseDialog"/>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${deviationInstance?.id}" />
                    <g:if test="${(session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code != 'CLOSED' && deviationInstance?.queryStatus?.code != 'INVALID') || (session.org?.code != 'OBBR' && (deviationInstance?.queryStatus?.code == 'OPEN' || deviationInstance?.queryStatus?.code == 'PROGRESS'))}">
                        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code == 'OPEN'}">
                        <span class="button"><g:actionSubmit id="progressBtn" class="save" action="progress" value="Mark as In Progress" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code == 'PROGRESS'}">
                        <span class="button"><g:actionSubmit id="reopenBtn" class="save" action="reopen" value="Reopen" /></span>
                        <span class="button"><g:actionSubmit id="resolveBtn" class="save" action="resolve" value="Resolve" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code == 'RESOLVED'}">
                        <span class="button"><g:actionSubmit id="reopenBtn" class="save" action="reopen" value="Reopen" /></span>
                        <span class="button"><g:actionSubmit id="closeBtn" class="save" action="close" value="Close" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code == 'CLOSED'}">
                        <g:hiddenField name="closed" value="true" />
                        <span class="button"><g:actionSubmit id="reopenBtn" class="save" action="reopen" value="Reopen" /></span>
                        <span class="button"><g:actionSubmit id="resolveBtn" class="save" action="resolve" value="Mark as Resolved" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code != 'INVALID'}">
                        <span class="button right"><g:actionSubmit id="invalidateBtn" class="save" action="invalidate" value="Invalidate" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code == 'INVALID'}">
                        <span class="button"><g:actionSubmit id="reopenBtn" class="save" action="reopen" value="Reopen" /></span>
                    </g:if>
                </g:form>
            </div>
        </div>
    </body>
</html>
