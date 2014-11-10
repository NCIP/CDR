

<%@ page import="nci.obbr.cahub.surveyrecords.InterviewRecord" %>
<g:set var="bodyclass" value="interviewrecord create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'interviewRecord.label', default: 'InterviewRecord')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'interviewrecord.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script>        
        <title><g:message code="default.create.label" args="[entityName]" /></title>

    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Start New Interview</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${interviewRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${interviewRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                  <g:render template="formFieldsInc" />
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton id="interviewCreate" name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><input class="delete" type="button" id="interviewCancel" value="Cancel"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
