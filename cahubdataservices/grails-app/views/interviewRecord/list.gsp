<%@ page import="nci.obbr.cahub.surveyrecords.InterviewRecord" %>
<g:set var="bodyclass" value="interviewrecord list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'interviewRecord.label', default: 'Interview Record')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
         <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Interview Record List for ${session.study?.name}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">

            <g:render template="/interviewRecord/interviewRecordTable_tmpl" bean="${interviewRecordInstanceList}" />              

            </div>
            <div class="paginateButtons">
                <g:paginate total="${interviewRecordInstanceTotal}" /> | Total: ${interviewRecordInstanceTotal}
            </div>
        </div>
    </body>
</html>
