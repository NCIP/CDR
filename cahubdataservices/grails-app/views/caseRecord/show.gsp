<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.ChpTissueRecord" %>
<g:set var="bodyclass" value="caserecord show" scope="request"/>
<g:if test="${session.study.code == 'BPV' || session.study.code == 'BRN'}">
  <g:set var="bodyclass" value="caserecord show wide" scope="request"/>
</g:if>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'Case Record')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
          <div class="message">${flash.message}</div>
          <g:hasErrors bean="${caseRecordInstance}">
            <div class="errors">
              <g:renderErrors bean="${caseRecordInstance}" as="list" />
            </div>
          </g:hasErrors>
          <h1>Show Case Record Details for ${caseRecordInstance.caseId}</h1>

          <g:if test="${session.study.code == 'GTEX'}">
              <g:render template="/caseRecord/showgtex" bean="${caseRecordInstance}" />
          </g:if>
          <g:if test="${session.study.code == 'BPV'}">
              <g:render template="/caseRecord/showbpv" bean="${caseRecordInstance}" />
          </g:if>          
          <g:if test="${session.study.code == 'CTC'}">
              <g:render template="/caseRecord/showctc" bean="${caseRecordInstance}" />
          </g:if>                    
          <g:if test="${session.study.code == 'BRN'}">
              <g:render template="/caseRecord/showbrn" bean="${caseRecordInstance}" />
          </g:if>
          <g:if test="${session.study.code == 'BMS'}">
              <g:render template="/caseRecord/showbms" bean="${caseRecordInstance}" />
          </g:if>
      </div>
    </body>
</html>
