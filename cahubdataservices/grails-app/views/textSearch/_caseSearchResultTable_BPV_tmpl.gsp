<style>
  
  td,th {white-space:nowrap}
  
</style>
<table>
                    <thead>
                        <tr><th colspan="13">Case Search Result List</th></tr>
                        <tr>
                          <th>Case ID</th>
                          <th>Primary Organ</th>
                          <th>BSS</th>
                          <th>Candidate ID</th>
                          <th>Specimens</th>
                          <th>Case Status</th>
                          <th>Date Created</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
                        
                        <g:if test="${caseRecordInstance}">
                          
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                                <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}"/>
                                %{--
                                <g:if test="${caseRecordInstance.caseStatus?.code == 'COMP' || caseRecordInstance.caseStatus?.code == 'RELE' || session.org.code == caseRecordInstance.bss?.code}">
                                  <span class="${caseRecordInstance?.caseStatus?.code == 'DATA' && caseRecordInstance?.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date()) ? 'redlink' : ''}">
                                    <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link>
                                  </span>
                                </g:if>
                                <g:elseif test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                                  session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                                                  session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") ||
                                                  session.authorities?.contains("ROLE_ADMIN")}'>         
                                  <span class="${caseRecordInstance?.caseStatus?.code == 'DATA' && caseRecordInstance?.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date()) ? 'redlink' : ''}">
                                    <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link>
                                  </span>
                                </g:elseif>
                                <g:else>
                                    ${caseRecordInstance.caseId}                        
                                </g:else>  
                                --}%
                            </td>
                            <td>${caseRecordInstance.primaryTissueType?.name}</td>
                            <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
                            <td>
                              <g:if test="${caseRecordInstance.candidateRecord}">
                                  <a href="/cahubdataservices/candidateRecord/view/${caseRecordInstance?.candidateRecord?.id}">${caseRecordInstance?.candidateRecord?.candidateId}</a>
                              </g:if>
                              <g:else><span class="no">Not Linked</span></g:else>
                            </td>
                            <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>
                            <td>
                              <span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus?.description}">${caseRecordInstance.caseStatus}</span>
                            </td>                            
                    
                            <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>                            
                        </tr>
                        </g:if>
                        <g:else>
                          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><font color="red">NULL</font></td>
                            <td><font color="red">NULL</font></td>
                            <td><font color="red">NULL</font></td>
                            <td><font color="red">NULL</font></td>
                            <td><font color="red">NULL</font></td>
                            <td><font color="red">NULL</font></td>
                            <td><font color="red">NULL</font></td>     
                          </tr>       
                        </g:else> 
                    </g:each>
                   
                    </tbody>
                </table>

