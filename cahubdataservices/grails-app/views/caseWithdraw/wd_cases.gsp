
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
            <h1>Cases Recalled</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <br>
              
            <g:if test="${caseList}">
           <table>
                   <thead>
                         <tr>
                          <th>Case ID</th> 
                          <th>Study</th>
                            </tr>
                    </thead>
                    <tbody>
                      <g:each in="${caseList}" status="i" var="c">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>
                             <g:displayCaseRecordLink caseRecord="${c}" session="${session}" />
                             %{-- <g:link controller="caseRecord" action="display" id="${c.id}">${c.caseId}</g:link> --}%
                           </td>
                        <td>${c.study.name}</td>
                      </tr>
                      </g:each>
                    </tbody>
           </table>
            </g:if>
            <g:else><p class="name">No Recalled cases to view</g:else>
              
          
        </div>
    </body>
</html>
