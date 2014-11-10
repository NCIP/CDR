
<g:set var="bodyclass" value="query report" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
        <title>Query Tracker Report</title>
    </head>
    <body>
        <div id="nav" class="clearfix">
            <div id="navlist"></div>
        </div>
        <div id="container" class="clearfix">
            <h1>Query Tracker Report</h1>
            <div class="div-t-r clearfix remove-border noshade">
                <div class="left row">Total: ${queryInstanceList.size()}</div>
                <div class="right row"><g:formatDate format="MM/dd/yyyy HH:mm" date="${new Date()}" /></div>
            </div>
            <div class="div-t-r clearfix remove-border header">
                <div class="left cell-narrow row">Query ID</div>
                <div class="left cell-narrow row">Organization</div>
                <div class="left cell-wide row">Associated with</div>
                <div class="left cell-narrow row">Date opened</div>
                <div class="left cell-narrow row">Status</div>
                <div class="left cell-wide row">Query type</div>
                <div class="left cell-narrow row">Due date</div>
            </div>
            <g:each in="${queryInstanceList}" var="queryInstance" status="i">
                <div class="div-t-r clearfix remove-border ${(i % 2) == 1 ? 'shade' : 'noshade'}">
                    <div class="left cell-narrow row">${queryInstance.id}</div>
                    <div class="left cell-narrow row">
                        <g:if test="${queryInstance.organization}">
                            ${queryInstance.organization?.code}
                        </g:if>
                        <g:else>&nbsp;</g:else>
                    </div>
                    <div class="left cell-wide row">
                        <g:if test="${queryInstance.caseRecord}">
                            ${queryInstance.caseRecord?.caseId}
                        </g:if>
                        <g:elseif test="${queryInstance.candidateRecord}">
                            ${queryInstance.candidateRecord?.candidateId}
                        </g:elseif>
                        <g:elseif test="${queryInstance.other}">
                            ${queryInstance.other}
                        </g:elseif>
                        <g:else>&nbsp;</g:else>
                    </div>
                    <div class="left cell-narrow row"><g:formatDate format="MM/dd/yyyy" date="${queryInstance.dateCreated}" /></div>
                    <div class="left cell-narrow row">${queryInstance.queryStatus}</div>
                    <div class="left cell-wide row">${queryInstance.queryType}</div>
                    <div class="left cell-narrow row">
                        <g:if test="${queryInstance.dueDate}">
                            <g:formatDate format="MM/dd/yyyy" date="${queryInstance.dueDate}" />
                        </g:if>
                        <g:else>&nbsp;</g:else>
                    </div>
                </div>
                <div class="div-t-r clearfix ${queryInstance.queryResponses ? 'remove-border' : 'blue-border'} ${(i % 2) == 1 ? 'shade' : 'noshade'}">
                    <div class="left row"><span class="purple">Description:</span><br /><br />${queryInstance.description?.replace('\n', '<br />')}</div>
                </div>
                <g:if test="${queryInstance.queryResponses}">
                    <div class="div-t-r clearfix blue-border ${(i % 2) == 1 ? 'shade' : 'noshade'}">
                        <div class="left row">
                            <span class="purple">Responses:</span><br /><br />
                            <g:each in="${queryInstance.queryResponses}">
                                <i>${it.responder}</i>&nbsp;<span class="responseTime"><g:formatDate format="MM/dd/yyyy HH:mm" date="${it.dateCreated}" /></span><br /><br />
                                ${it.response?.replace('\n', '<br />')}<br /><br />
                            </g:each>
                        </div>
                    </div>
                </g:if>
            </g:each>
        </div>
    </body>
</html>
