<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>

<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link controller="candidateRecord" class="create" action="create">Add New Candidate</g:link></span>   
            <span class="menuButton"><g:link controller="fileUpload" class="create" action="create">New File Upload</g:link></span> 
            <span class="menuButton"><g:link controller="fileUpload" class="list" action="list">Uploaded File List</g:link></span> 
            <span class="menuButton"><g:link controller="textSearch" class="list" action="searchhome">Search</g:link></span> 
           <!-- <g:link controller="caseWithdraw"  class="list" action="listAllRecallCases" >Recall cases list</g:link>-->
        </div>
        <div class="body">
            <h1>${session.org.name} Home</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
              <g:render template="/candidateRecord/candidateRecordTable_tmpl" bean="${candidateRecordInstanceList}" />
              <div class="clearfix tablefooter"><div class="left">Most recently created on top. Italic font indicates candidate is linked to a case. *Mouse over <span class="ca-bubble"></span> to view BSS comments.</div><div class="right"><a href="/cahubdataservices/candidateRecord/list">View all Candidates >></a></div></div>

              <g:render template="/caseRecord/caseRecordTable_tmpl" bean="${caseRecordList}" />
              <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/caseRecord/list">View all Cases >></a></div></div>
            </div>

        </div>
    </div>
    </body>
</html>
