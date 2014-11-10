

<%@ page import="nci.obbr.cahub.util.querytracker.Query" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="query create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'query.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.create.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${queryInstance}">
            <div class="errors">
                <g:renderErrors bean="${queryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="save" value="Save" /></span>
                    <span class="button"><g:actionSubmit class="save" action="saveActivate" value="Activate" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
