<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="textsearch list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'CaseRecord')}" />
  <title>Search Results</title>
</head>
<body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
    <h1>Search Results</h1>    
    <g:render template="searchCandiForm"  />   
    <g:if test="${(total == 0 || !total) && !msg}">
      <div class="errors"><ul><li>No candidates match your search criteria.  Please try different search terms.</li></ul></div>
      <g:render template="textSearchHintsInc" />  
    </g:if>
    <g:elseif test="${(total == 0 || !total) && msg}">
      <div class="errors"><ul><li>${msg}</li></ul></div>
      <g:render template="textSearchHintsInc" />  
    </g:elseif>
    <g:else>
      <p><i>Displaying ${size} of ${total} results. Click on a Case ID or Candidate ID to see form entry status and data.</i></p>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="list">
        <g:render template="/candidateRecord/candidateRecordGtexTable_tmpl" bean="${candidateRecordInstanceList}" />              
      </div>
      <g:if test="${total > size}">
        <div class="paginateButtons">
          <g:paginate total="${total}" params="[query:query]" max="25" /> | Total: ${total}
        </div>
      </g:if>
    </g:else>
  </div>
</body>
</html>
