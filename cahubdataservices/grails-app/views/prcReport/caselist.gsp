
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="prcreport list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'CaseRecord')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Case List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <br>
              <g:if test="${studyCode=='GTEX'}">
              <g:form controller="textSearch" action="searchPRC">
                <input name="formtype" value="GTEX" type="hidden"></input>

                 <lable><b>Enter Search Criteria:</b></lable>
                  <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
              <g:actionSubmit action="searchPRC" value="Go" />
         
              </g:form>
              </g:if>
    
             <g:if test="${studyCode=='BMS'}">
              <g:form controller="textSearch" action="searchPRCBms">
                  <input name="formtype" value="BMS" type="hidden"></input>
                 <lable><b>Enter Search Criteria:</b></lable>
                  <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
              <g:actionSubmit action="searchPRCBms" value="Go" />
         
              </g:form>
              </g:if>
            
            <g:if test="${studyCode=='BRN'}">
              <g:form controller="textSearch" action="searchPRCBrn">
                 <input name="formtype" value="BRN" type="hidden"></input>
                 <lable><b>Enter Search Criteria:</b></lable>
                  <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
              <g:actionSubmit action="searchPRCBrn" value="Go" />
                 </g:form>
               </g:if>
              
               <g:if test="${studyCode=='BPV'}">
              <g:form controller="textSearch" action="searchPRCBpv">
                 <input name="formtype" value="BPV" type="hidden"></input>
                 <lable><b>Enter Search Criteria:</b></lable>
                  <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
              <g:actionSubmit action="searchPRCBpv" value="Go" />
         
              </g:form>
              </g:if>
    
    
            </br>
          
            <div class="list">
                <g:if test="${studyCode=='GTEX'}">
                <g:render template="/caseRecord/caseRecordPrcTable_tmpl" bean="${caseList}" />  
                </g:if>
                 <g:if test="${studyCode=='BMS'}">
                <g:render template="/caseRecord/caseRecordPrcBmsTable_tmpl" bean="${caseListBms}" />  
                </g:if>
                  <g:if test="${studyCode=='BPV'}">
                <g:render template="/caseRecord/caseRecordPrcBpvTable_tmpl" bean="${caseListBpv}" />  
                </g:if>
               <g:if test="${studyCode=='BRN'}">
                <g:render template="/caseRecord/caseRecordPrcBrnTable_tmpl" bean="${caseListBrn}" />  
                </g:if>
            </div>
            <div class="paginateButtons">
                <g:paginate params="[study:studyCode]" total="${caseRecordInstanceTotal}" /> | Total: ${caseRecordInstanceTotal}
            </div>
        </div>
    </body>
</html>
