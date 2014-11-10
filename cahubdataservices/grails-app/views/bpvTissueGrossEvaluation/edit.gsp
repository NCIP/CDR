<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueGrossEvaluation" %>
<g:set var="bodyclass" value="bpvtissuegrosseval edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvTissueGrossEvaluationInstance?.formMetadata?.cdrFormName}" />
        <g:set var="caseId" value="${bpvTissueGrossEvaluationInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label.with.case.id" args="[entityName,caseId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvTissueGrossEvaluationInstance}">
            <div class="errors">
                <g:renderErrors bean="${bpvTissueGrossEvaluationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:warnings warningmap="${warningMap}" />
            <g:queryDesc caserecord="${bpvTissueGrossEvaluationInstance?.caseRecord}" form="bpvGross" />
            <g:form method="post" >
                <g:hiddenField name="id" value="${bpvTissueGrossEvaluationInstance?.id}" />
                <g:hiddenField name="version" value="${bpvTissueGrossEvaluationInstance?.version}" />
                <g:hiddenField name="changed" value="N" />
                <div class="dialog">
                    <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save saveAction" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <g:if test="${canSubmit == 'Yes'}">
                        <span class="button"><g:actionSubmit class="save saveAction" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" onclick="return checkModification()" /></span>
                    </g:if>
                    <g:if test="${bpvTissueGrossEvaluationInstance.caseRecord.caseStatus.code == 'QA' && session.DM == true && session.authorities.contains("ROLE_NCI-FREDERICK_CAHUB_DM") && canSubmit == 'No'}">
                        <span class="button"><g:actionSubmit class="save saveAction" action="forceSubmit" value="${message(code: 'default.button.submit.label', default: 'Force Submit')}" onclick="return checkModification()" /></span>
                    </g:if>                    
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
