
<%@ page import="nci.obbr.cahub.surveyrecords.BpvElsiCrf" %>
<g:set var="bodyclass" value="bpvElsiCrf show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="BPV ELSI Case Report Form" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog" id="show">
                  <g:render template="/interviewRecord/interviewDetails" bean="${bpvElsiCrfInstance.interviewRecord}" var="ir" />
                  <g:render template="formFieldsInc" />
            </div>
            <g:if test="${bpvElsiCrfInstance?.dateSubmitted && (((session.authorities?.contains('ROLE_BPV_ELSI_DA') && bpvElsiCrfInstance.interviewRecord.orgCode==session.org?.code) 
          || session.org?.code == bpvElsiCrfInstance.interviewRecord.orgCode) && bpvElsiCrfInstance.interviewRecord.interviewStatus?.key in ['DATA', 'REMED']) 
          || (session.org?.code == 'OBBR' && ((session.DM == true)||(session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_DM'))) && bpvElsiCrfInstance.interviewRecord.interviewStatus?.key in ['REMED', 'QA'])}">
                <div class="buttons">
                    <g:form class="tdwrap tdtop">
                        <g:hiddenField name="id" value="${bpvElsiCrfInstance?.id}" />
                        <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                    </g:form>
                </div>
            </g:if>            
        </div>
    </body>
</html>
