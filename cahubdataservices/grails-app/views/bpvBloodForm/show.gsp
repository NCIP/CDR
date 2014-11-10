<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.bpv.BpvBloodForm" %>
<g:set var="bloodFormVersion" value="${bpvBloodFormInstance?.bloodFormVersion == '' || bpvBloodFormInstance?.bloodFormVersion == null?1:bpvBloodFormInstance?.bloodFormVersion}" scope="request" />
<g:set var="bodyclass" value="bpvbloodform show bpv-study bloodformversion${bloodFormVersion>1?2:''}" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvBloodFormInstance?.formMetadata?.cdrFormName}" />
        <g:set var="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>   
        <script type="text/javascript">var previousLoc = ""</script>
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'bloodform' + (bloodFormVersion>1?2:'') + '.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script>   
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
            <g:if test="${bpvBloodFormInstance?.bloodMinimum == 'Yes'}">
                <g:warnings warningmap="${warningMap}" />
            </g:if>
            <g:queryDesc caserecord="${bpvBloodFormInstance?.caseRecord}" form="bpvBlood" />
            <div id="show">
                <div class="dialog tdwrap tdtop">
                    <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}"/>
                    <g:hiddenField name="version" value="${bpvBloodFormInstance?.version}"/>
                    <g:hiddenField name="caseId" value="${bpvBloodFormInstance.caseRecord?.caseId}"/>
                    <g:render template="formFieldsInc"/>
                </div>
            </div>
            <g:if test="${bpvBloodFormInstance?.dateSubmitted &&
                bpvBloodFormInstance?.caseRecord?.candidateRecord?.isEligible &&
                    bpvBloodFormInstance?.caseRecord?.candidateRecord?.isConsented &&
                        canResume}">
                <div class="buttons">
                    <g:form class="tdwrap tdtop">
                        <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}"/>
                        <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                    </g:form>
                </div>
            </g:if>
        </div>
    </body>
</html>
