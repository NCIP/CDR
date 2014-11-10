
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="search" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title>Search Home</title> 
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
      <g:if test="${session.getAttribute('chosenHome') == 'BRN' || session.getAttribute('chosenHome') == 'PRC' || session.getAttribute('chosenHome') == 'BMS' || session.getAttribute('chosenHome') == 'BPV' || session.getAttribute('chosenHome') == 'CTC' || session.getAttribute('chosenHome') == 'MBB' }">
      <g:if test="${ session.getAttribute('chosenHome') == 'BRN'}">
         <h1>Search BRN Data</h1> 
      </g:if>  
       <g:if test="${session.getAttribute('chosenHome') == 'PRC'}">
         <h1>Search PRC Data</h1>
      </g:if>  
         <g:if test="${session.getAttribute('chosenHome') == 'BMS'}">
         <h1>Search BMS Data</h1>
      </g:if>  
           <g:if test="${session.getAttribute('chosenHome') == 'BPV'}">
         <h1>Search BPV Data</h1>
      </g:if>  
        <g:if test="${session.getAttribute('chosenHome') == 'CTC'}">
         <h1>Search CTC Data</h1>
      </g:if>  
    </g:if>
     <g:elseif test="${session.org?.code == 'VARI' || session.org?.code == 'BROAD' }">
       <g:if test="${session.study.code=='GTEX'}">
          <h1>Search GTEX Data</h1> 
       </g:if>
        <g:if test="${session.study.code=='BRN'}">
          <h1>Search BRN Data</h1> 
       </g:if>
        <g:if test="${session.study.code=='BPV'}">
          <h1>Search BPV Data</h1> 
       </g:if>
       <g:if test="${session.study.code=='BMS'}">
          <h1>Search BMS Data</h1> 
       </g:if>
     </g:elseif>
      <g:elseif test="${session.org?.code == 'NDRI'}">
          <h1>Search GTEX/BMS Data</h1> 
      </g:elseif>
      <g:elseif test="${session.org?.code == 'MBB'}">
        <h1>Search MBB Data</h1>
      </g:elseif>
    <g:else>
        <h1>Search GTEX Data</h1>
    </g:else>

       <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
       </g:if>
       <g:render template="searchForm"  /> 
       <g:if test="${session.getAttribute('chosenHome') == 'CTC'}"><g:render template="textSearchHints_CTC" /></g:if>
       <g:else>  <g:render template="textSearchHintsInc" />  </g:else>
     
      </div>
    </body>
</html>
