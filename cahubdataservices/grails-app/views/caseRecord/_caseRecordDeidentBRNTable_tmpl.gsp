
                <table>
                    <thead>
                        <tr><th colspan="9">BRN Case List</th></tr>
                        <tr>
                            <g:sortableColumn property="caseId" title="Case ID" />  
                            <g:sortableColumn property="bss" title="BSS" />
                            <g:sortableColumn property="primaryTissueType" title="Primary Organ" />  
                            <th class="specimencount">Specimens</th>       
                            <g:sortableColumn property="caseStatus" title="Case Status" /> 
                            <th>Case Record Export</th>
                            <g:sortableColumn property="dateCreated" class="dateentry" title="Date Created" /> 
                        </tr>
                    </thead>
                    <tbody>
                    <g:if test="${caseRecordInstanceList}">
                    <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                                <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}" action="showbpvdeident" />
                                %{-- <g:link controller="caseRecord" action="showbpvdeident" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
                            </td>
                            <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
                            <td>${caseRecordInstance.primaryTissueType}</td>
                            <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>                                         
                            <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span></td>                    
                            <td><g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'}"><a target="_blank" href="${createLink(uri: '/')}rest/caserecord/${caseRecordInstance.caseId}">View XML</a></g:if><g:else>Not Available</g:else></td>
                            <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
                        </tr>
                    </g:each>
                    </g:if>
                    <g:else>
                      <tr><td colspan="10">No cases exist</td></tr>
                    </g:else>
                    </tbody>
                </table>

