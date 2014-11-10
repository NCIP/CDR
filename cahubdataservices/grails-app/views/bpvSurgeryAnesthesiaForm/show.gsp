<g:if test="${env != 'production'}">
   <%-- cache buster--%>
   <g:set var="d" value="${new Date()}" />
   <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<%@ page import="nci.obbr.cahub.forms.bpv.BpvSurgeryAnesthesiaForm" %>
<g:set var="bodyclass" value="bpvsurgeryanest show bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName}" />
        <g:set var="caseId" value="${bpvSurgeryAnesthesiaFormInstance?.caseRecord?.caseId}" />
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'surgeryAnesthesiaForm.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="nav" class="clearfix">
            <div id="navlist">
                <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            </div>
        </div>
        <div id="container" class="clearfix">
            <h1><g:message code="default.show.label.with.case.id" args="[entityName,caseId]"/></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:queryDesc caserecord="${bpvSurgeryAnesthesiaFormInstance?.caseRecord}" form="bpvSurgery" />
            <div id="show">
                <div class="dialog tdwrap tdtop">
                    <g:render template="formFieldsInc" />
                </div>
            </div>
            <g:if test="${bpvSurgeryAnesthesiaFormInstance?.dateSubmitted &&
                bpvSurgeryAnesthesiaFormInstance?.caseRecord?.candidateRecord?.isEligible &&
                    bpvSurgeryAnesthesiaFormInstance?.caseRecord?.candidateRecord?.isConsented &&
                        canResume}">
                <div class="buttons">
                    <g:form class="tdwrap tdtop">
                        <g:hiddenField name="id" value="${bpvSurgeryAnesthesiaFormInstance?.id}" />
                        <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                    </g:form>
                </div>
            </g:if>
        </div>
    </body>
</html>
