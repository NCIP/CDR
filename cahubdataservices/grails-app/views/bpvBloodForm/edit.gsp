<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.bpv.BpvBloodForm" %>
<g:set var="entityName" value="${bpvBloodFormInstance?.formMetadata?.cdrFormName}" />
<g:set var="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
<g:set var="bloodFormVersion" value="${bpvBloodFormInstance?.bloodFormVersion == '' || bpvBloodFormInstance?.bloodFormVersion == null?1:bpvBloodFormInstance?.bloodFormVersion}" scope="request" />
<g:set var="bodyclass" value="bpvbloodform edit bpv-study bloodformversion${bloodFormVersion>1?2:''}" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <script type="text/javascript">var previousLoc = "${params.loc ?: ''}"</script> 
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'bloodform' + (bloodFormVersion>1?2:'') + '.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script> 
    </head>
    <body>
        <div id="nav" class="clearfix">
            <div id="navlist">
                <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            </div>
        </div>
        <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label.with.case.id" args="[entityName,caseId]"/></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvBloodFormInstance}">
                <div id="pageErrs" class="errors hide">
                    <g:renderErrors bean="${bpvBloodFormInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:if test="${bpvBloodFormInstance?.bloodMinimum == 'Yes'}">
                <g:warnings warningmap="${warningMap}" />
            </g:if>
            <g:queryDesc caserecord="${bpvBloodFormInstance?.caseRecord}" form="bpvBlood" />
            <g:form method="post" action="save">
                <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}"/>
                <g:hiddenField name="version" value="${bpvBloodFormInstance?.version}"/>
                <g:hiddenField name="caseId" value="${bpvBloodFormInstance.caseRecord?.caseId}"/>
                <g:hiddenField name="changed" value="N"/>
                <g:hiddenField name="loc" value=""/>
                <div class="dialog">
                    <g:render template="formFieldsInc"/>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" id="formsave" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}"/></span>
                    <g:if test="${canSubmit}">
                        <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" onclick="return checkModification()"/></span>
                    </g:if>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${bpvBloodFormInstance?.id}';"></input></span>
                </div>
            </g:form>
        </div>
        <g:render template="floatingFormsInc"/>
    </body>
</html>
