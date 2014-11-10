

<%@ page import="nci.obbr.cahub.util.querytracker.Deviation" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="deviation create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'deviation.label', default: 'Deviation')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'deviation.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.create.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviationInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
