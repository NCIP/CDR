<style>
  
  td,th {white-space:nowrap}
  
</style>

<table>
                    <thead>
                        <tr><th colspan="13">Case Search Result List</th></tr>
                        <tr>
                          <th>Case ID</th>
                          <th>BSS</th>
                          <th>Case Type</th>
                          <th>Candidate ID</th>
                          <th>Case Status</th>
                          <th>Date Created</th>
                        </tr>
                    </thead>
                    <tbody>
                   
                    <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                                  <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}"/>
                                  %{-- <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
                            </td>
                            <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
                            <td>${caseRecordInstance.caseCollectionType}</td>
                            <td>
                              <g:if test="${caseRecordInstance.candidateRecord}">
                                  <a href="/cahubdataservices/candidateRecord/view/${caseRecordInstance?.candidateRecord?.id}">${caseRecordInstance?.candidateRecord?.candidateId}</a>
                              </g:if>
                              <g:else><span class="no">Not Linked</span></g:else>
                            </td>
                            <td>
                              <span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
                            </td>                            
                    
                            <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>                            
                        </tr>
                    </g:each>
                   
                    </tbody>
                </table>

