

<%@ page import="nci.obbr.cahub.prc.PrcSpecimenReport" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'PrcSpecimenReport.label', default: 'GTEX Pathology Review Form')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            
        </div>
        <div class="body">
            <h1>${entityName}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prcSpecimenReportInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcSpecimenReportInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prcSpecimenReportInstance?.id}" />
                <g:hiddenField name="version" value="${prcSpecimenReportInstance?.version}" />
                <div class="dialog">
                <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
