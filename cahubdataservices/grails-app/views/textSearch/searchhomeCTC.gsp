
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
      
        <g:if test="${session.getAttribute('chosenHome') == 'CTC'}">
         <h1>Search CTC Data</h1>
     
    </g:if>
     
       <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
       </g:if>
       <g:render template="searchForm"  /> 
      <g:render template="textSearchHints_CTC" />  
      </div>
    </body>
</html>
