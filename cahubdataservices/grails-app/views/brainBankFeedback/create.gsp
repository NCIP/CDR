<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<%@ page import="nci.obbr.cahub.forms.gtex.BrainBankFeedback" %>
<g:set var="bodyclass" value="brainbankfeedback create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'brainBankFeedback.label', default: 'Brain Bank Feedback Form')}" />
        
               <title><g:message code="default.create.label" args="[entityName]" /></title>
         
         <script type="text/javascript" src="${resource(dir:'js',file:'brainbankfeedback.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
         
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Create Brain Bank Feedback Form</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${brainBankFeedbackInstance}">
            <div class="errors">
                <g:renderErrors bean="${brainBankFeedbackInstance}" as="list" />
            </div>
            </g:hasErrors>
             <g:render template="caseDetails" bean="${brainBankFeedbackInstance.caseRecord}" var="caseRecord" />
             <%--<g:form action="save"  > --%>
              <g:form method="post" action="save" class="tdwrap tdtop bbfform" enctype="multipart/form-data">
               <g:hiddenField name="id" value="${brainBankFeedbackInstance?.id}" />
                
                   

                    <div >
                        <g:render template="formFieldsInc"/> 
                         
                    </div>


                    
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="update" action="save" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
