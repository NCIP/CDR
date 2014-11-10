
<%@ page import="nci.obbr.cahub.datarecords.qm.CahubAcceptable" %>
<g:set var="bodyclass" value="cahubAcceptable show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'cahubAcceptable.label', default: 'CahubAcceptable')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div >
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
           
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>View caHUB Acceptable Form</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
               <g:render template="/caseRecord/caseDetails" bean="${cahubAcceptableInstance.caseRecord}" var="caseRecord" /> 
                <g:render template="formFieldsInc" />
            </div>
            
        </div>
    </body>
</html>
