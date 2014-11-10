
<table>
                    <thead>
                      <tr><th colspan="10">Candidate List</th></tr>
                        <tr>
                        
                            <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
                              <g:if test="${params.controller != 'textSearch'}">
                                <th>QT</th>
                              </g:if>
                            </g:if>  
                          
                            <g:sortableColumn property="candidateId" title="${message(code: 'candidateRecord.candidateId.label', default: 'Candidate ID')}" />                          
                            
                            <g:sortableColumn property="bss.code" title="${message(code: 'candidateRecord.BSS.label', default: 'BSS')}" />
                            
                            <g:sortableColumn property="caseCollectionType.name" title="${message(code: 'candidateRecord.caseType.label', default: 'Case Type')}" />
                       
                            <th>Consent Verified</th>
                
                            <th>Donor Eligibility</th>                                                                        
                
                            <th>Consented?</th>

                            <th>Eligible?</th>                                                        
                            
                            <th>Case Linked?</th>        
                            
                            <g:sortableColumn property="dateCreated" class="dateentry" title="${message(code: 'candidateRecord.lastUpdated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                      
                    <g:each in="${candidateRecordInstanceList}" status="i" var="candidateRecordInstance">
                      <g:if test="${candidateRecordInstance.caseRecord}"><tr class="${(i % 2) == 0 ? 'odd' : 'even'} linkedRow"></g:if>
                      <g:else><tr class="${(i % 2) == 0 ? 'odd' : 'even'}"></g:else>
                            <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
                              <g:if test="${params.controller != 'textSearch'}">
                                <td><g:if test="${queryCountCandidate.get(candidateRecordInstance.id)}"><a href="/cahubdataservices/query/listByCandidate?candidateRecord.id=${candidateRecordInstance.id}"><span class="no">${queryCountCandidate.get(candidateRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
                              </g:if>
                            </g:if>
                            
                            <td class="candidate-id">
                                <g:if test="${candidateRecordInstance.caseRecord || session.org.code == 'OBBR'}">
                                  <a href="/cahubdataservices/candidateRecord/view/${candidateRecordInstance.id}">${candidateRecordInstance.candidateId}</a> <g:if test="${candidateRecordInstance?.internalComments?.length() > 0}"><span class="ca-bubble" data-msg="${candidateRecordInstance?.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}"></span></g:if>
                                </g:if>                        
                                <g:elseif test="${!candidateRecordInstance.caseRecord && session.DM == true}">
                                  <a href="/cahubdataservices/candidateRecord/modify/${candidateRecordInstance.id}">${candidateRecordInstance.candidateId}</a> <g:if test="${candidateRecordInstance?.internalComments?.length() > 0}"><span class="ca-bubble" data-msg="${candidateRecordInstance?.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}"></span></g:if>
                                </g:elseif>
                            </td>

                            <td><span class="ca-tooltip-nobg" data-msg="<b>${candidateRecordInstance.bss.name}</b>">${candidateRecordInstance.bss}</span></td>
                
                            <td>${candidateRecordInstance.caseCollectionType}</td>

                         <g:if test="${candidateRecordInstance.bss.parentBss.code == 'SC'}">
                            <td>
                                <g:if test="${candidateRecordInstance.icdGtexSc}"><span class="yes">Yes</span>
                                  <g:if test="${session.DM == true}">     
                                  <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP' || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
                                    <g:link controller="icdGtexSc" action="edit" id="${candidateRecordInstance.icdGtexSc.id}">(Edit)</g:link>
                                  </g:if>
                                  <g:else>
                                    <g:link controller="icdGtexSc" action="view" id="${candidateRecordInstance.icdGtexSc.id}">(View)</g:link>                                    
                                  </g:else>         
                                    </g:if>   
                                  <g:else>
                                    <g:link controller="icdGtexSc" action="view" id="${candidateRecordInstance.icdGtexSc.id}">(View)</g:link>                                    
                                  </g:else>                                      
                                  </g:if>
                                <g:else><span class="no">No 
                                    <g:if test="${session.DM == true}">     
                                      <a href="/cahubdataservices/icdGtexSc/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a></span>
                                    </g:if>  

                                </g:else>
                            </td>               
                         </g:if>                            
                            
                            
                         <g:if test="${candidateRecordInstance.bss.parentBss.code == 'RPCI'}">
                            <td>
                                <g:if test="${candidateRecordInstance.icdGtexRpci}"><span class="yes">Yes</span>
                                  <g:if test="${session.DM == true}">     
                                  <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP' || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
                                      <g:link controller="icdGtexRpci" action="edit" id="${candidateRecordInstance.icdGtexRpci.id}">(Edit)</g:link>
                                  </g:if>
                                    <g:else>
                                      <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}">(View)</g:link>                                    
                                    </g:else>                                    
                                    </g:if>  
                                    <g:else>
                                      <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}">(View)</g:link>                                    
                                    </g:else>                                  
                                  </g:if>
                                <g:else><span class="no">No 
                                    <g:if test="${session.DM == true}">     
                                      <a href="/cahubdataservices/icdGtexRpci/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a></span>
                                    </g:if>

                                </g:else>
                            </td>               
                         </g:if>                                                        
                            
                            
                         <g:if test="${candidateRecordInstance.bss.parentBss.code == 'NDRI'}">
                            <td>
                                <g:if test="${candidateRecordInstance.icdGtexNdri}"><span class="yes">Yes</span>
                                  <g:if test="${session.DM == true}">     
                                  <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP' || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
                                    <g:link controller="icdGtexNdri" action="edit" id="${candidateRecordInstance.icdGtexNdri.id}">(Edit)</g:link>
                                  </g:if>
                                    <g:else>
                                      <g:link controller="icdGtexNdri" action="view" id="${candidateRecordInstance.icdGtexNdri.id}">(View)</g:link>                                    
                                    </g:else>   
                                    </g:if>      
                                    <g:else>
                                      <g:link controller="icdGtexNdri" action="view" id="${candidateRecordInstance.icdGtexNdri.id}">(View)</g:link>                                    
                                    </g:else>                                   
                                  </g:if>                                  
                                <g:else><span class="no">No 
                                    <g:if test="${session.DM == true}">     
                                      <a href="/cahubdataservices/icdGtexNdri/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a></span>
                                  </g:if>
                                </g:else>
                                    
                            </td>               
                         </g:if>                                                        

                            <td>
                                <g:if test="${candidateRecordInstance.donorEligibilityGtex}"><span class="yes">Yes</span> 
                                  <g:if test="${session.DM == true}">     
                                  <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP' || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
                                    <g:link controller="donorEligibilityGtex" action="edit" id="${candidateRecordInstance.donorEligibilityGtex.id}">(Edit)</g:link>
                                   </g:if>
                                  <g:else>
                                    <g:link controller="donorEligibilityGtex" action="view" id="${candidateRecordInstance.donorEligibilityGtex.id}">(View)</g:link>                                    
                                  </g:else>
                                  </g:if>
                                  <g:else>
                                    <g:link controller="donorEligibilityGtex" action="view" id="${candidateRecordInstance?.donorEligibilityGtex.id}">(View)</g:link>                                    
                                  </g:else>                                                               
                                  </g:if>                                                                    
                              <g:else><span class="no">No 
                                  <g:if test="${session.DM == true}">     
                                    <a href="/cahubdataservices/donorEligibilityGtex/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a></span>
                                  </g:if>
                              </g:else>
                                  
                            </td>                    
                
                            <td>
                              <g:if test="${candidateRecordInstance.isConsented}"><span class="yes">Yes</span></g:if>
                              <g:else><span class="no">No</span></g:else>
                            </td>                  
                
                            <td>
                              <g:if test="${candidateRecordInstance.isEligible}"><span class="yes">Yes</span></g:if>
                              <g:else><span class="no">No</span></g:else>
                            </td>                  

                            <td>
                                <g:if test="${candidateRecordInstance.caseRecord}">
                                  <span class="ca-tooltip-nobg yes" data-msg="<b>Case ID: ${candidateRecordInstance.caseRecord}</b>">Yes</span>
                                  <g:if test="${session.DM == true}">     
                                  <g:if test="${!candidateRecordInstance.caseRecord?.tissueRecoveryGtex && !candidateRecordInstance.caseRecord?.caseReportForm}">
                                   <a href="/cahubdataservices/candidateRecord/linkCandidateToCase/${candidateRecordInstance.id}">(Edit)</a>
                                  </g:if>                                
                                  </g:if>
                                </g:if>
                
                                <g:else><span class="no">No</span> 
                                  <g:if test="${candidateRecordInstance.isConsented == true && candidateRecordInstance.donorEligibilityGtex && session.DM == true}">
                                    <g:link controller="candidateRecord" action="linkCandidateToCase" id="${candidateRecordInstance.id}">(Link)</g:link></span>
                                  </g:if>
                                </g:else>
                              </td>

               
                            <td><g:formatDate date="${candidateRecordInstance.dateCreated}" /></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
