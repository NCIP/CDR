

<%@ page import="nci.obbr.cahub.util.querytracker.Deviation" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
    
</g:if>
<g:set var="bodyclass" value="deviation edit" scope="request"/>
<g:set var="jira_home" value="${AppSetting.findByCode('JIRA_HOME')}" />
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'deviation.label', default: 'Deviation')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'deviation.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="show" id="${deviationInstance?.id}">Show Deviation</g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviationInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${deviationInstance?.id}" />
                <g:hiddenField name="version" value="${deviationInstance?.version}" />
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/show/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
