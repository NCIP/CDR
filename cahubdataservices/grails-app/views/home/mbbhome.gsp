<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="caserecord list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'Case Record')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
         <div id="nav">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
            <g:link controller="textSearch" class="list" action="searchhome">Search</g:link>
            <g:link controller="query" class="list" action="list">Query Tracker</g:link> 
            <g:link controller="home"  class="list" action="more">More...</g:link>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Case Record List for ${session.study.code}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
              <g:if test="${session.study.code == 'GTEX'}">
                  <g:render template="/caseRecord/caseRecordMBBTable_tmpl" bean="${caseRecordInstanceList}" />              
              </g:if>
                 
            </div>
            <div class="paginateButtons">
                <g:paginate total="${caseRecordInstanceTotal}" /> | Total: ${caseRecordInstanceTotal}
            </div>
        </div>
    </body>
</html>
