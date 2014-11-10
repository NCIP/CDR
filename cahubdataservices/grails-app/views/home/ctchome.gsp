<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import=" nci.obbr.cahub.datarecords.ctc.PatientRecord" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="ctchome home wide" scope="request"/>

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
            <g:link controller="textSearch" class="list" action="searchhomeCTC">Search</g:link>
              <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)}">
            <g:link controller="patientRecord"  class="create" action="create" >New CTC Patient Record</g:link>
              </g:if>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>CTC Home</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">


              <%--
              <g:render template="/caseRecord/caseRecordCtcTable_tmpl" bean="${caseRecordInstanceList}" />
              <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/caseRecord/list">View all Cases >></a></div><br /><div class="left querytracker">QT = Number of active queries</div></div>
              </table>
              --%>
             
              <g:render template="/patientRecord/patientRecordTable_tmpl" bean="${patientRecordList}" />
              <div class="clearfix tablefooter"><div class="left">Most recently created on top</div><div class="right"><a href="/cahubdataservices/patientRecord/list">View all Patient Records >></a></div></div>
              </table>                                
              
              
                                        
             
              
            </div>
        </div><!-- end container --> 
    </body>
</html>
