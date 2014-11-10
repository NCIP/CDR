<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="caserecord list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'Case Record')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
         <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Case Record List for ${session.study.code}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
              <g:if test="${session.study.code == 'GTEX'}">
                  <div class="tableheader clearfix">
                    <div class="left fasttrack">FT = FastTrack status</div><br />
                    <div class="left querytracker">QT = Number of active queries</div>
                  </div>
                  <g:render template="/caseRecord/caseRecordTable_tmpl" bean="${caseRecordInstanceList}" />              
              </g:if>
              <g:if test="${session.study.code == 'BPV'}">
                  <div class="tableheader clearfix">
                    <div class="left fasttrack">FT = FastTrack status</div><br />
                    <div class="left querytracker">QT = Number of active queries</div>
                  </div>
                  <g:render template="/caseRecord/caseRecordBpvTable_tmpl" bean="${caseRecordInstanceList}" />              
              </g:if>              
              <g:if test="${session.study.code == 'CTC'}">
                  <g:render template="/caseRecord/caseRecordCtcTable_tmpl" bean="${caseRecordInstanceList}" />              
              </g:if>                 
              <g:if test="${session.study.code == 'BRN'}">
                  <g:render template="/caseRecord/caseRecordBrnTable_tmpl" bean="${caseRecordInstanceList}" />              
              </g:if>              
              <g:if test="${session.study.code == 'BMS'}">
                  <div class="tableheader clearfix">
                    <div class="left querytracker">QT = Number of active queries</div>
                  </div>
                  <g:render template="/caseRecord/caseRecordBMSTable_tmpl" bean="${caseRecordInstanceList}" />              
              </g:if>                  
            </div>
            <div class="paginateButtons">
                <g:paginate total="${caseRecordInstanceTotal}" /> | Total: ${caseRecordInstanceTotal}
            </div>
        </div>
    </body>
</html>
