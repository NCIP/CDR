 <%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="caserecord view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'CaseRecord')}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
         <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
        
            <h1>View Case Record Details for ${caseRecordInstance.caseId}</h1>
            <div class="message">${flash.message}</div>
            <g:hasErrors bean="${caseRecordInstance}">
              <div class="errors">
                <g:renderErrors bean="${caseRecordInstance}" as="list" />
              </div>
            </g:hasErrors>
            <g:if test="${session.study.code == 'GTEX'}">
                <g:if test="${session.org.code == 'VARI' || session.org.code == 'BROAD'}">
                  <g:render template="/caseRecord/viewgtexlite" bean="${caseRecordInstance}" />              
                </g:if>
                <g:else>
                  <g:render template="/caseRecord/viewgtex" bean="${caseRecordInstance}" />              
                </g:else>
            </g:if>
            <g:if test="${session.study.code == 'BRN'}">
                <g:render template="/caseRecord/viewbrn" bean="${caseRecordInstance}" />              
            </g:if> 
            <g:if test="${session.study.code == 'BMS'}">
                <g:render template="/caseRecord/viewbms" bean="${caseRecordInstance}" />              
            </g:if> 
            <g:if test="${session.study.code == 'BPV'}">
                <g:render template="/caseRecord/viewbpv" bean="${caseRecordInstance}" />              
            </g:if> 
        </div>
    </body>
</html>
