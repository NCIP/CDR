<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="textsearch list xtrawide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'CaseRecord')}" />
  <script type="text/javascript" src="${resource(dir:'js',file:'query.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title>Search Results</title>
</head>
<body >
      <div id="nav" class="clearfix">
          <div id="navlist">
            <g:if test="${session.org?.code == 'OBBR'}">
            <a class="home" href="${createLink(uri: '/home/opshome')}"><g:message code="default.home.label"/></a>
            </g:if>
            <g:else>
               <a class="home" href="${createLink(uri: '/home/')}"><g:message code="default.home.label"/></a>
            </g:else>
          </div>
      </div>
      <div id="container" class="clearfix">
    <h1>Search Results</h1>    
    <g:render template="searchQtForm"  /> 
    <g:if test="${(total == 0 || !total) && !msg}">
      <div class="errors"><ul><li>No query trackers match your search criteria.  Please try different search terms.</li></ul></div>
    </g:if>
    <g:elseif test="${(total == 0 || !total) && msg}">
      <div class="errors"><ul><li>${msg}</li></ul></div>
    </g:elseif>
    <g:else>
      <p><i>Displaying ${size} of ${total} results. Click on a Case ID or Candidate ID to see form entry status and data.</i></p>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      
         <div class="list">
                <table >
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Associated with</th>
                            <th>Case status</th>
                            <th>Issue type</th>
                            <th>Description</th>
                            <th>Organization</th>
                            <th>IS</th>
                            <th>Query type</th>
                            <th>Date opened</th>
                            <th>Opened by</th>
                            <th>Due Date</th>
                            <th>Date closed</th>
                            <th>Closed by</th>
                            <th>View</th>
                           
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${queryInstanceList}" status="i" var="queryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${queryInstance.id}</td>
                                 <g:if test="${queryInstance.caseRecord}">
                                    <td>
                                        <nobr>
                                            <g:displayCaseRecordLink caseRecord="${queryInstance.caseRecord}" session="${session}" />
                                            %{-- <g:link controller="caseRecord" action="display" id="${queryInstance.caseRecord?.id}">${queryInstance.caseRecord}</g:link> --}%
                                        </nobr>
                                     </td>
                                </g:if>
                                <g:elseif test="${queryInstance.candidateRecord}">
                                    <td><nobr><g:link controller="candidateRecord" action="view" id="${queryInstance.candidateRecord?.id}">${queryInstance.candidateRecord}</g:link></nobr></td>
                                </g:elseif>
                                <g:elseif test="${queryInstance.other}">
                                    <td><nobr>${queryInstance.other}</nobr></td>
                                </g:elseif>
                                <g:else>
                                    <td></td>
                                </g:else>
                                <td>${queryInstance.caseRecord?.caseStatus}</td>
                            <td>
                                <g:if test="${queryInstance.isDcf == 'Yes'}">DCF</g:if>
                                <g:elseif test="${queryInstance.queryType?.code == 'ACTION'}">Other</g:elseif>
                                <g:else>DM Query</g:else>
                            </td>
                            <td>
                                <g:if test="${queryInstance.description?.size() <= 10}">${queryInstance.description}</g:if>
                                <g:else><span class="ca-tooltip-nobg" data-msg="${queryInstance.description?.replace('\n', '<br />')?.replace('\"', '\'')}">${queryInstance.description?.substring(0, 10)}&nbsp;&hellip;</span></g:else>
                            </td>
                                <td>${queryInstance.organization}</td>
                            <td>${queryInstance.queryStatus}</td>
                            <td>${queryInstance.queryType}</td>
                            <td><g:formatDate format="MM/dd/yyyy" date="${queryInstance.dateCreated}" /></td>
                            <td>${queryInstance.openedBy}</td>
                            <td id="dueDateTD_${queryInstance.id}" class="dueDateTD">
                                <div id="dueDateHoverWrap_${queryInstance.id}" class="${session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code != 'CLOSED' ? 'dueDateHoverWrap' : ''} clearfix">
                                    <span id="dueDateDisplay_${queryInstance.id}" class="dueDateDisplay left"><g:formatDate format="MM/dd/yyyy" date="${queryInstance.dueDate}" /></span>
                                    <span id="dueDateEdit_${queryInstance.id}" class="dueDateEdit ui-icon ui-icon-pencil right hide" title="Click to edit"></span>
                                </div>
                                <span id="dueDatePicker_${queryInstance.id}" class="dueDatePicker hide"><g:jqDatePicker class="dueDate" name="dueDate_${queryInstance.id}" value="${queryInstance.dueDate}" /></span>
                            </td>
                            <td><g:formatDate format="MM/dd/yyyy" date="${queryInstance.dateClosed}" /></td>
                            <td>${queryInstance.closedBy}</td>
                            <td class="textcenter"><g:link controller="query" action="show" id="${queryInstance.id}"><img src="/cahubdataservices/images/magGlass16x16.png" alt="View" width="12" height="12" /></g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
      <g:if test="${total > size}">
        <div class="paginateButtons">
          <g:paginate total="${total}" params="[query:query]" max="25" /> | Total: ${total}
        </div>
      </g:if>
    </g:else>
  </div>
</body>
</html>
