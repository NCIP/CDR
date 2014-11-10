<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="gtexhome home xtrawide" scope="request"/>

<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>
        <div id="nav" class="clearfix">
            <div id="navlist">
                <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
                <g:link controller="textSearch" class="list" action="searchhome">Search</g:link> 
                <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN')}">
                    <g:link controller="backoffice" class="list" action="index">Back Office</g:link>                   
                </g:if>
                <g:link controller="home"  class="list" action="more" >More...</g:link>
            </div>
        </div>
        <div id="container" class="clearfix">
            <h1>GTEX Home</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <g:render template="/caseRecord/caseRecordTable_tmpl" bean="${caseRecordList}" />
                <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/caseRecord/list">View all Cases >></a></div><br /><div class="left fasttrack querytracker">FT = FastTrack status, QT = Number of active queries</div></div>
                
                <g:render template="/shippingEvent/shippingEventTable_tmpl" bean="${shippingEventList}" />
                <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/shippingEvent/list">View all Shipping Events >></a></div></div>                                        

                <g:render template="/candidateRecord/candidateRecordTable_tmpl" bean="${candidateRecordInstanceList}" />
                <div class="clearfix tablefooter"><div class="left">Most recently created on top. Italic font indicates candidate is linked to a case. *Mouse over <span class="ca-bubble"></span> to view BSS comments.</div><div class="right"><a href="/cahubdataservices/candidateRecord/list">View all Candidates >></a></div></div>     
              
            </div>
        </div>
    </body>
</html>
