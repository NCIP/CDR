<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="bpvbsshome home wide" scope="request"/>

<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>          
       <div id="nav" class="clearfix">
          <div id="navlist">
                <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
                <g:link controller="candidateRecord" class="create" action="create">Add New Candidate</g:link>    
                <g:link controller="query" class="list" action="list">Query Tracker</g:link>
                <g:link controller="textSearch" class="list" action="searchhome">Search</g:link> 
                <g:link controller="home"  class="list" action="more" >More...</g:link>
                <a class="ar" href="${nci.obbr.cahub.util.AppSetting.findByCode('CDRAR_HOSTNAME')?.value}/cahubanalytics/report/bpvrpthome">CDR-AR</a>
                <g:if test='${session.authorities?.contains("ROLE_BPV_ELSI")}'>  
                  <g:link controller="home" class="list" action="bpvelsihome" >ELSI Home</g:link>
                </g:if>                
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>${session.org.name} Home</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">

              <g:render template="/caseRecord/caseRecordBpvTable_tmpl" bean="${caseRecordList}" />

              <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/caseRecord/list?s=bpv">View all Cases >></a></div><br /><div class="left querytracker">QT = Number of active queries</div></div>
              </table>

              
              <g:render template="/candidateRecord/candidateRecordBpvTable_tmpl" bean="${candidateRecordList}" />


                <div class="clearfix tablefooter"><div class="left">Most recently created on top. Italic font indicates candidate is linked to a case. *Mouse over <span class="ca-bubble"></span> to view BSS comments.</div><div class="right"><a href="/cahubdataservices/candidateRecord/list">View all Candidates >></a></div></div>
                     
              
            </div>
            
        </div><!-- end container --> 
    </body>
</html>
