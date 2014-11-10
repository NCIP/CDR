<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.ctc.PatientRecord" %>
<g:set var="bodyclass" value="patientrecord show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
         <g:set var="entityName" value="CTC Patient Profile" />
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
                        
                    <g:render template="formViewOnly"/>
        </div>
            <div class="buttons">
              <%--
              <g:if test="${!patientRecordInstance?.dateSubmitted}">
                <g:form>
                    <g:hiddenField name="id" value="${patientRecordInstance?.id}" />
                   
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                  
                </g:form>
              </g:if>
              --%>
              <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) && patientRecordInstance?.dateSubmitted}">
                <div class="buttons">
                    <g:form>
                    <g:hiddenField name="id" value="${patientRecordInstance?.id}" />
                        <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                    </g:form>
                </div>
              </g:if>
            </div>
    </body>
</html>


