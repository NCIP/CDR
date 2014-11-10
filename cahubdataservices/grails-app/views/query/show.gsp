

<%@ page import="nci.obbr.cahub.util.querytracker.Query" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="query show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'query.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:if test="${queryInstance?.caseRecord}">
                <a class="list" href="/cahubdataservices/query/listByCase?caseRecord.id=${queryInstance?.caseRecord?.id}">Query List for ${queryInstance?.caseRecord?.caseId}</a>
            </g:if>
            <g:elseif test="${queryInstance?.candidateRecord}">
                <a class="list" href="/cahubdataservices/query/listByCandidate?candidateRecord.id=${queryInstance?.candidateRecord?.id}">Query List for ${queryInstance?.candidateRecord?.candidateId}</a>
            </g:elseif>
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
                                <g:render template="responseTable" bean="${queryInstance}" var="queryInstance" />
                                <g:if test="${(session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code != 'CLOSED') || (session.org?.code != 'OBBR' && queryInstance?.queryStatus?.code == 'ACTIVE')}">
                                    <div>
                                        <button id="addResponseBtn">Add</button>
                                    </div>
                                </g:if>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td class="formheader">Attachments</td>
                        </tr>     
                        <tr class="prop">
                            <td class="name">
                                <g:render template="attachmentTable" bean="${queryInstance}" var="queryInstance" />
                                <g:if test="${(session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code != 'CLOSED') || (session.org?.code != 'OBBR' && queryInstance?.queryStatus?.code == 'ACTIVE')}">
                                    <div>
                                        <g:link class="uibutton" controller="query" action="upload" id="${queryInstance?.id}">
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
                    <g:hiddenField name="id" value="${queryInstance?.id}" />
                    <g:if test="${session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code != 'CLOSED'}">
                        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && session.DM && !queryInstance?.queryStatus}">
                        <g:hiddenField name="newQuery" value="true" />
                        <span class="button"><g:actionSubmit id="activateBtn" class="save" action="reactivate" value="Activate" /></span>
                    </g:if>
                    <g:if test="${(session.org?.code != 'OBBR' && queryInstance?.queryStatus?.code == 'ACTIVE') || (session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code == 'ACTIVE')}">
                        <span class="button"><g:actionSubmit id="addressBtn" class="save" action="address" value="Mark as Addressed" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code == 'ADDRESSED'}">
                        <span class="button"><g:actionSubmit id="reactivateBtn" class="save" action="reactivate" value="Reactivate" /></span>
                        <span class="button"><g:actionSubmit id="resolveBtn" class="save" action="resolve" value="Resolve" /></span>
                        <span class="button"><g:actionSubmit id="unresolveBtn" class="save" action="unresolve" value="Unresolve" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code == 'RESOLVED'}">
                        <span class="button"><g:actionSubmit id="reactivateBtn" class="save" action="reactivate" value="Reactivate" /></span>
                        <span class="button"><g:actionSubmit id="closeBtn" class="save" action="close" value="Close" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code == 'UNRESOLVED'}">
                        <span class="button"><g:actionSubmit id="reactivateBtn" class="save" action="reactivate" value="Reactivate" /></span>
                        <span class="button"><g:actionSubmit id="closeBtn" class="save" action="close" value="Close" /></span>
                    </g:if>
                    <g:if test="${session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code == 'CLOSED'}">
                        <g:hiddenField name="closed" value="true" />
                        <span class="button"><g:actionSubmit id="reactivateBtn" class="save" action="reactivate" value="Reactivate" /></span>
                        <span class="button"><g:actionSubmit id="resolveBtn" class="save" action="resolve" value="Mark as Resolved" /></span>
                        <span class="button"><g:actionSubmit id="unresolveBtn" class="save" action="unresolve" value="Mark as Unresolved" /></span>
                    </g:if>
                </g:form>
            </div>
        </div>
    </body>
</html>
