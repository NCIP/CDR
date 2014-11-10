<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="varihome home" scope="request"/>

<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>
       <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link controller="shippingEvent" class="list" action="index">Shipping Events</g:link>
            <g:link controller="query" class="list" action="list">Query Tracker</g:link> 
            <g:link controller="textSearch" class="list" action="searchhome">Search</g:link>
            <g:link controller="home" class="list" action="cbrinvfeed">Query CBR Inventory</g:link>
            <g:link controller="home"  class="list" action="more">More...</g:link>
          </div>
       </div>
       <div id="container" class="clearfix">
            <h1>Welcome ${session.org.name}</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <div class="tableheader clearfix">
                  <div class="left fasttrack">FT = FastTrack status</div>
                  <div class="right">
                    <b>Filter by Study:</b>
                    <%-- to add, remove or make changes to studies, simply modify the array --%>
                    <g:each var="entry" status="i" in="['GTEX','BPV','BMS','BRN']"> 
                      <%=("<a class=\"filtermenu filteritem" + i + "\" href=\"?studyCode=" + entry + "\">" + entry + "</a>").replace("<a class=\"filtermenu filteritem" + i + "\" href=\"?studyCode=" +selectedStudy + "\">" + selectedStudy + "</a>", "<span class=\"filtermenu filteritem" + i + "\">" + selectedStudy + "</span>")%>
                    </g:each> 
                  </div>
                </div>
                <g:if test="${selectedStudy == 'GTEX'}">
                  <g:render template="/caseRecord/caseRecordDeidentGTEXTable_tmpl" bean="${caseRecordInstanceList}" />
                </g:if>
                <g:elseif test="${selectedStudy == 'BMS'}">
                    <g:render template="/caseRecord/caseRecordDeidentBMSTable_tmpl" bean="${caseRecordInstanceList}" />
                </g:elseif>
                <g:elseif test="${selectedStudy == 'BPV'}">
                    <g:render template="/caseRecord/caseRecordDeidentBPVTable_tmpl" bean="${caseRecordInstanceList}" />
                </g:elseif>
                <g:elseif test="${selectedStudy == 'BRN'}">
                    <g:render template="/caseRecord/caseRecordDeidentBRNTable_tmpl" bean="${caseRecordInstanceList}" />
                </g:elseif>
                <div class="paginateButtons">
                    <g:paginate controller="home" action="varihome" total="${caseRecordInstanceTotal}" /> | Total: ${caseRecordInstanceTotal}
                </div>
                <div class="clearfix tablefooter"><div class="left">Most recently created on top</div></div>
            </div>
        </div>
    </body>
</html>
