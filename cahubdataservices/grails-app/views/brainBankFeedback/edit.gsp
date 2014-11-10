<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.BrainBankFeedback" %>
<g:set var="bodyclass" value="brainbankfeedback edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'brainBankFeedback.label', default: 'Brain Bank Feedback Form')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
         
         <script type="text/javascript" src="${resource(dir:'js',file:'brainbankfeedback.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${brainBankFeedbackInstance}">
            <div class="errors">
              <ul><g:eachError><li><a href="#${it.field}" title="Go to this question">${it.code}</a></li></g:eachError></ul>
            </div>
            </g:hasErrors>
             <g:render template="caseDetails" bean="${brainBankFeedbackInstance.caseRecord}" var="caseRecord" />
            <%--<g:form action="save"  > --%>
              <g:form method="post" class="tdwrap tdtop bbfform" enctype="multipart/form-data">
               <g:hiddenField name="id" value="${brainBankFeedbackInstance?.id}" />
               <g:render template="formFieldsInc"/>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                 <%--   <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--%>
                <g:if test="${canSubmit == 'Yes'}">
                      <span class="button"><g:actionSubmit class="save" action="submit" value="${message(code: 'default.button.submit.label', default: 'Submit')}"  /></span>
                </g:if>
                </div>
            </g:form>
        </div>
    </body>
</html>
