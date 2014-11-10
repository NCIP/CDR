<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.BrainBankFeedback" %>
<g:set var="bodyclass" value="brainbankfeedback show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'brainBankFeedback.label', default: 'Brain Bank Feedback Form')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>View Brain Bank Feedback Form ${session.org.code}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
             <g:render template="caseDetails" bean="${brainBankFeedbackInstance.caseRecord}" var="caseRecord" />
              <g:form method="post" action="save" class="tdwrap tdtop" enctype="multipart/form-data">
               <g:hiddenField name="id" value="${brainBankFeedbackInstance?.id}" />
                  <div id="show">
                      <g:render template="formFieldsInc"/>
                 </div>
                 <div class="buttons">
                   <g:if test="${brainBankFeedbackInstance.dateSubmitted  && ((session.DM == true && session.org.code == 'OBBR') || session.org?.code == 'MBB')}">
                     <span class="button"><g:actionSubmit class="edit" action="resumeEditing" value="${message(code: 'default.button.resumeEditing.label', default: 'Resume Editing')}" /></span>
                   </g:if>
                </div>
            </g:form>
             
        
    </body>
</html>
