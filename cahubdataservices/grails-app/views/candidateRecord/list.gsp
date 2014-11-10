<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<g:set var="bodyclass" value="candidaterecord list wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'candidateRecord.label', default: 'Candidate Record')}"/>
        <title><g:message code="default.list.label" args="[entityName]"/></title>
        <g:javascript>
            
    $(document).ready(function(){
        $("#query").focus()
    
    
       });
        </g:javascript>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/textSearch/searchCandiForm"  />
 
            <br/>
            <div class="list">
                <g:if test="${session.study.code == 'BPV'}">
                    <g:render template="/candidateRecord/candidateRecordBpvTable_tmpl" bean="${candidateRecordInstanceList}"/>
                </g:if>
                <g:elseif test="${session.study.code == 'GTEX'}">
                    <g:render template="/candidateRecord/candidateRecordGtexTable_tmpl" bean="${candidateRecordInstanceList}"/>
                </g:elseif>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${candidateRecordInstanceTotal}"/> | Total: ${candidateRecordInstanceTotal}
            </div>
            <div class="clearfix tablefooter"><div class="left">*Mouse over <span class="ca-bubble"></span> to view BSS comments.</div><div class="right"></div></div>     
        </div>     
    </body>
</html>
