
<%@ page import="nci.obbr.cahub.util.querytracker.Deviation" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %> 
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
      
      
</g:if>
<g:set var="bodyclass" value="deviation list" scope="request"/>
 <g:set var="jira_home" value="${AppSetting.findByCode('JIRA_HOME')}" />
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'deviation.label', default: 'Deviation')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'deviation.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:if test="${(session.org?.code == 'OBBR' && session.DM) || session.org?.code != 'OBBR'}">
                <g:if test="${params.caseRecord?.id}">
                    <a class="create" href="/cahubdataservices/deviation/create?caseRecord.id=${params.caseRecord?.id}"><g:message code="default.new.label" args="[entityName]" /></a>
                </g:if>
            </g:if>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${session.org?.code == 'OBBR' && !params.caseRecord?.id}">
                <g:form name="studyFilter" action="list" method="post">
                    <label for="studyId">Filter by Study:</label>
                    <g:select name="studyId" from="${nci.obbr.cahub.staticmembers.Study.list()}" optionKey="id" noSelection="['': 'All Studies']" value="${params.studyId}" />
                </g:form>
            </g:if>
            <div class="tableheader clearfix">
                <div class="left">NC = Non conformance issued?</div>
            </div>
            <g:if test="${params.caseRecord?.id}">
                <g:render template="/caseRecord/caseDetails" bean="${CaseRecord.get(params.caseRecord?.id)}" var="caseRecord"/>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:if test="${!params.caseRecord?.id}">
                                <g:sortableColumn property="id" title="ID" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="caseRecord.caseId" title="Case Record" params="[studyId: params.studyId]" />
                                <th>Description</th>
                                <g:sortableColumn property="queryStatus.code" title="CDR Status" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="dateCreated" title="Date Opened" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="sop" title="SOP" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="nonConformance" title="NC" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="type" title="Type" params="[studyId: params.studyId]" />
                                  <g:sortableColumn property="jiraId" title="JIRA ID" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="dateDeviation" title="Date of Deviation" params="[studyId: params.studyId]" />
                                <g:sortableColumn property="jiraCloseDate" title="Date Jira Issue(s) Closed " params="[studyId: params.studyId]" />
                                <th>View</th>
                            </g:if>
                            <g:else>
                                <th>ID</th>
                                <th>Description</th>
                                <th>CDR Status</th>
                                <th>Date Opened</th>
                                <th>SOP</th>
                                <th>NC</th>
                                <th>Type</th>
                                <th>JIRA ID</th>
                                <th>Date of Deviation</th>
                                <th>Date Jira Issue(s) Closed </th>
                                <th>View</th>
                            </g:else>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviationInstanceList}" status="i" var="deviationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${deviationInstance.id}</td>
                            <g:if test="${!params.caseRecord?.id}">
                                <td><nobr><g:link controller="caseRecord" action="display" id="${deviationInstance.caseRecord?.id}">${deviationInstance.caseRecord}</g:link></nobr></td>
                            </g:if>
                            <td>
                                <g:if test="${deviationInstance.description?.size() <= 60}">${deviationInstance.description}</g:if>
                                <g:else><span class="ca-tooltip-nobg" data-msg="${deviationInstance.description?.replace('\n', '<br />')?.replace('\"', '\'')}">${deviationInstance.description?.substring(0, 60)}&nbsp;&hellip;</span></g:else>
                            </td>
                            <td>${deviationInstance.queryStatus}</td>
                            <td><g:formatDate format="MM/dd/yyyy" date="${deviationInstance.dateCreated}" /></td>
                            <td>${deviationInstance.sop}</td>
                            <td>${deviationInstance.nonConformance}</td>
                            <td>${deviationInstance.type}</td>
                            <%-- pmh 05/14/14 cdrqa 1153--%>
                             <td>
                                  <ul>
                                  <g:each in="${deviationInstance.jiraId?.split(',')}" var="k">
                                  <li><a target="_blank" href="${jira_home.value}${k.trim()}">${k}</a></li>
                                  </g:each>
                                  </ul>
                                 
                             </td>
                            <td><g:formatDate format="MM/dd/yyyy" date="${deviationInstance.dateDeviation}" /></td>
                             <td><g:formatDate format="MM/dd/yyyy" date="${deviationInstance.jiraCloseDate}" /></td>
                            <td class="textcenter"><g:link action="show" id="${deviationInstance.id}"><img src="/cahubdataservices/images/magGlass16x16.png" alt="View" width="12" height="12" /></g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <g:if test="${!params.caseRecord?.id}">
                <div class="paginateButtons">
                    <g:if test="${params.studyId}">
                        <g:paginate total="${deviationInstanceTotal}" params="[studyId: params.studyId]" />
                    </g:if>
                    <g:else>
                        <g:paginate total="${deviationInstanceTotal}" />
                    </g:else>
                </div>
            </g:if>
        </div>
    </body>
</html>
