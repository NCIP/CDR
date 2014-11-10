<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="gtexbsshome home wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title><g:message code="default.page.title"/></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link controller="candidateRecord" class="create" action="create">Add New Candidate</g:link>
            <g:link controller="query" class="list" action="list">Query Tracker</g:link>
            <g:link controller="textSearch" class="list" action="searchhome">Search</g:link>
            <g:link controller="home"  class="list" action="more">More...</g:link>
            <a class="ar" href="${nci.obbr.cahub.util.AppSetting.findByCode('CDRAR_HOSTNAME')?.value}/cahubanalytics/report/gtexrpthome">CDR-AR</a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>${session.org.name} Home</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <g:render template="/candidateRecord/candidateRecordTable_tmpl" bean="${candidateRecordInstanceList}" />
                <div class="clearfix tablefooter"><div class="left">Most recently created on top. Italic font indicates candidate is linked to a case. *Mouse over <span class="ca-bubble"></span> to view BSS comments.</div><div class="right"><a href="/cahubdataservices/candidateRecord/list">View all Candidates >></a></div></div>
                <g:render template="/caseRecord/caseRecordTable_tmpl" bean="${gtexCaseList}" var="caseRecordInstanceList" />
                <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/caseRecord/list?s=gtex">View all Cases >></a></div><br /><div class="left querytracker">QT = Number of active queries</div></div>
            </div>
            <g:if test="${session.org.code == 'NDRI'}">
                <g:render template="/caseRecord/caseRecordBMSTable_tmpl" bean="${bmsCaseList}" var="caseRecordInstanceList" />
                <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/caseRecord/list?s=bms">View all BMS Cases >></a></div></div>
                </table>
            </g:if>
        </div>
    </body>
</html>
