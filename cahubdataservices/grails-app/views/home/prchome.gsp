<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prchome home wide" scope="request"/>
<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>
       <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
           
            <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN')}">
                <g:link controller="backoffice" class="list" action="index">Back Office</g:link>
            </g:if>
             <g:link controller="prcReport" class="list" action="caselist" params="[study: 'BMS']">BMS Case List</g:link> 
              <g:link controller="prcReport" class="list" action="caselist" params="[study: 'BRN']">BRN Case List</g:link>
              <a href="${AppSetting.findByCode('CDRAR_HOSTNAME').value}/cahubanalytics/prcReport/rin" target="new" class="list">RIN Value Chart</a>
              <a href="${AppSetting.findByCode('CDRAR_HOSTNAME').value}/cahubanalytics/prcReport/index" class="list">AR Report</a>
               <g:link controller="prcUnaccReason" class="list" action="list" >Reason For Unacceptable Tissues</g:link>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>PRC Home</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
        
             <g:render template="/textSearch/searchForm"  />

            <div class="list">

              <g:render template="/caseRecord/caseRecordPrcTable_tmpl"  />
              <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/prcReport/caselist?study=GTEX">View all Cases &gt;&gt;</a></div></div>
              <br></br>
              
              
              <g:render template="/caseRecord/caseRecordPrcBpvTable_tmpl" />
              <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/prcReport/caselist?study=BPV">View all Cases &gt;&gt;</a></div></div>
                      
            </div>

        </div>
    </div>
    </body>
</html>
