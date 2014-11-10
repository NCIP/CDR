<g:if test="${env != 'production'}">
   <%-- cache buster--%>
   <g:set var="d" value="${new Date()}" />
   <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<%@ page import="nci.obbr.cahub.forms.bpv.BpvSurgeryAnesthesiaForm" %>
<g:set var="bodyclass" value="bpvsurgeryanest edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName}" />
        <g:set var="caseId" value="${bpvSurgeryAnesthesiaFormInstance?.caseRecord?.caseId}" />
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'surgeryAnesthesiaForm.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script> 
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
          <g:hasErrors bean="${bpvSurgeryAnesthesiaFormInstance}">
              <div class="errors">
                  <g:renderErrors bean="${bpvSurgeryAnesthesiaFormInstance}" as="list"/>
              </div>
          </g:hasErrors>
          <g:queryDesc caserecord="${bpvSurgeryAnesthesiaFormInstance?.caseRecord}" form="bpvSurgery" />
          <g:form method="post" class="tdwrap tdtop">
                <g:hiddenField name="id" value="${bpvSurgeryAnesthesiaFormInstance?.id}"/>
                <g:hiddenField name="version" value="${bpvSurgeryAnesthesiaFormInstance?.version}"/>
                <g:hiddenField name="changed" value="N"/>
                <div class="dialog">
                    <g:render template="formFieldsInc"/>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}"/></span>
                    <g:if test="${canSubmit}">
                        <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}" onclick="return checkModification()"/></span>
                    </g:if>
                    <g:if test="${bpvSurgeryAnesthesiaFormInstance.caseRecord.caseStatus.code == 'QA' && session.DM == true && session.authorities.contains("ROLE_NCI-FREDERICK_CAHUB_DM") && !canSubmit}">
                        <span class="button"><g:actionSubmit class="save saveAction" action="forceSubmit" value="${message(code: 'default.button.submit.label', default: 'Force Submit')}" onclick="return checkModification()" /></span>
                    </g:if>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
