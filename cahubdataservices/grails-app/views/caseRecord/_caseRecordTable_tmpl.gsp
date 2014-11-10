<div id="ErrorDiv"></div>
<table>
  <thead>
    <tr><th colspan="13">GTEX Case List</th></tr>
    <tr>
      <g:if test="${session.org?.code == 'BROAD' || (session.org?.code == 'OBBR' && session.DM == true)}">
        <g:sortableColumn property="dmFastTrack" title="FT" />
      </g:if>    
      <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
        <g:if test="${params.controller != 'textSearch'}">
          <th>QT</th>
        </g:if>
      </g:if>
      <g:if test="${session.org?.code == 'OBBR' && session.DM == true}">
        <g:if test="${params.controller != 'textSearch'}">
          <th>DV</th>
        </g:if>
      </g:if>
      <g:sortableColumn property="caseId" title="${message(code: 'caseRecord.caseId.label', default: 'Case ID')}" />                          
      <g:sortableColumn property="bss.code" title="${message(code: 'caseRecord.BSS.label', default: 'BSS')}" />
      <g:sortableColumn property="caseCollectionType.name" title="${message(code: 'candidateRecord.caseType.label', default: 'Case Type')}" />
      <th>Candidate ID</th>
      <th>TRF</th>
      <th>CRF</th>                            
      <th class="specimencount">Specimens</th>
        <g:if test ="${session.org?.code == 'OBBR'}">
      <th>PRC Report</th>
        </g:if>
      <g:else>
        <th>PF Form</th>
      </g:else>
      <g:sortableColumn property="caseStatus.name" title="${message(code: 'caseRecord.dateCreated.label', default: 'Case Status')}" />                            
      <g:sortableColumn property="dateCreated" class="dateentry" title="${message(code: 'caseRecord.dateCreated.label', default: 'Date Created')}" />                
    </tr>
  </thead><tbody>
    <g:if test="${caseRecordInstanceList}">
       <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
       <g:if test="${session.org?.code == 'BROAD' || (session.org?.code == 'OBBR' && session.DM == true)}">
      <td><g:if test ="${caseRecordInstance.caseStatus?.code=='COLL' || caseRecordInstance.caseStatus?.code=='DATA' || caseRecordInstance.caseStatus?.code=='DATACOMP' || caseRecordInstance.caseStatus?.code=='QA' || caseRecordInstance.caseStatus?.code=='PRC' || caseRecordInstance.caseStatus?.code=='BSSQACOMP'}"> 
             <g:if test="${session.org?.code == 'BROAD' || (session.org?.code == 'OBBR' && session.DM == true)}">
               <g:remoteLink controller="caseRecord" action="changeCaseFastTrackStatus" update='[success:"imgFT_${caseRecordInstance.id}",failure:"ErrorDiv"]' id="${caseRecordInstance.id}">
                 <div id='imgFT_${caseRecordInstance.id}' onclick="clearErrorDiv()">
                    <g:if test ="${caseRecordInstance.dmFastTrack==0 || caseRecordInstance.dmFastTrack==null}"><img src="/cahubdataservices/images/normalStatus.gif" onmouseover="tooltip.show('Click to add this case into the FastTrack queue');" onmouseout="tooltip.hide();"></g:if>
                    <g:else><img src="/cahubdataservices/images/fastTrack.gif" onmouseover="tooltip.show('Click to remove this case from the FastTrack queue');" onmouseout="tooltip.hide();"></g:else>
                 </div>
               </g:remoteLink>
             </g:if><g:else>
               <g:if test ="${caseRecordInstance.dmFastTrack==0 || caseRecordInstance.dmFastTrack==null}"><img src="/cahubdataservices/images/normalStatus.gif" onmouseover="tooltip.show('Click to add this case into the FastTrack queue');" onmouseout="tooltip.hide();"></g:if>
               <g:else><img src="/cahubdataservices/images/fastTrack.gif" onmouseover="tooltip.show('Click to remove this case from the FastTrack queue');" onmouseout="tooltip.hide();"></g:else>                            
            </g:else>
         </g:if><g:else><img src="/cahubdataservices/images/nonMod.gif" onmouseover="tooltip.show('Case passed QA review.');" onmouseout="tooltip.hide();"></g:else></td>
      </g:if>  
      <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
        <g:if test="${params.controller != 'textSearch'}">
          <td><g:if test="${queryCount.get(caseRecordInstance.id)}"><a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}"><span class="no">${queryCount.get(caseRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
        </g:if>
      </g:if>
     <g:if test="${session.org?.code == 'OBBR' && session.DM == true}">
        <g:if test="${params.controller != 'textSearch'}">
          <td><g:if test="${deviationCount.get(caseRecordInstance.id)}"><a href="/cahubdataservices/deviation/listByCase?caseRecord.id=${caseRecordInstance.id}"><span class="no">${deviationCount.get(caseRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
        </g:if>
      </g:if>
      <td class="nowrap">
          <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}"/>
          %{-- <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
      </td>
      <td class="nowrap"><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
      <td>${caseRecordInstance.caseCollectionType}</td>
      <td class="nowrap"><g:if test="${caseRecordInstance.candidateRecord}"><a href="/cahubdataservices/candidateRecord/view/${caseRecordInstance?.candidateRecord?.id}">${caseRecordInstance?.candidateRecord?.candidateId}</a></g:if><g:else><span class="no">Not Linked</span></g:else></td>
      <td><g:if test="${caseRecordInstance.candidateRecord}">
            <g:if test="${caseRecordInstance.tissueRecoveryGtex}"><span class="yes">Yes</span> 
                 <g:if test="${session.DM == true && session.org.code != 'OBBR'}">
                     <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}"><g:link controller="tissueRecoveryGtex" action="edit" id="${caseRecordInstance.tissueRecoveryGtex.id}">(Edit)</g:link></g:if>
                     <g:else><g:link controller="tissueRecoveryGtex" action="view" id="${caseRecordInstance.tissueRecoveryGtex.id}">(View)</g:link></g:else>
                 </g:if>     
                 <g:else><g:link controller="tissueRecoveryGtex" action="view" id="${caseRecordInstance.tissueRecoveryGtex.id}">(View)</g:link></g:else>                                  
            </g:if><g:else><span class="no">No</span> 
                 <g:if test="${session.DM == true && session.org.code != 'OBBR'}"><a href="/cahubdataservices/tissueRecoveryGtex/create?caseRecord.id=${caseRecordInstance.id}">(Add)</a></g:if>
            </g:else>
          </g:if><g:else><span class="no">No</span></g:else></td>
      <td><g:if test="${caseRecordInstance.candidateRecord}">
           <g:if test="${caseRecordInstance.caseReportForm?.status?.value == 1}"><span class="yes">Yes</span> 
                <g:if test="${session.DM == true && session.org.code != 'OBBR'}">
                    <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}"><g:link controller="caseReportForm" action="show" id="${caseRecordInstance.caseReportForm.id}">(Edit)</g:link></g:if>
                    <g:else><g:link controller="caseReportForm" action="display" id="${caseRecordInstance.caseReportForm.id}">(View)</g:link></g:else>                                    
                </g:if> 
                <g:else><g:link controller="caseReportForm" action="display" id="${caseRecordInstance.caseReportForm.id}">(View)</g:link></g:else>                                  
           </g:if>
           <g:if test="${caseRecordInstance.caseReportForm?.status?.value == 0}"><span class="incomplete">In Progress</span> 
                <g:if test="${session.DM == true && session.org.code != 'OBBR'}">
                    <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}"><g:link controller="caseReportForm" action="show" id="${caseRecordInstance.caseReportForm.id}">(Edit)</g:link></g:if>
                    <g:else><g:link controller="caseReportForm" action="display" id="${caseRecordInstance.caseReportForm.id}">(View)</g:link></g:else>                                     
                    </g:if>
                    <g:else><g:link controller="caseReportForm" action="display" id="${caseRecordInstance.caseReportForm.id}">(View)</g:link></g:else>   
           </g:if>                
           <g:if test="${!caseRecordInstance.caseReportForm}"><span class="no">No</span><g:if test="${session.DM == true && session.org.code != 'OBBR'}"> <a href="/cahubdataservices/caseReportForm/save?caseRecord.id=${caseRecordInstance.id}">(Add)</a></g:if></g:if>
         </g:if><g:else><span class="no">No</span></g:else></td>
      
      <td>
          <g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else>
          <g:if test="${frozenList?.contains(caseRecordInstance.id)}"><span class="ca-icicle ui-ca-tooltip" data-msg="Case has frozen specimens."></span></g:if>
          <g:if test="${brainList?.contains(caseRecordInstance.id)}"><span class="ca-brain ui-ca-tooltip" data-msg="Case has brain specimens."></span></g:if>          
       </td>
        <g:if test ="${session.org?.code == 'OBBR'}">
      <td><g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate != null}"><span class="yes">Yes</span> <g:link controller="prcReport" action="view" id="${caseRecordInstance.prcReport.id}">(View)</g:link></g:if>
          <g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate == null}"><span class="incomplete">In Progress</span></g:if>                        
          <g:if test="${!caseRecordInstance.prcReport}"><span class="no">No</span></g:if></td>   
       </g:if>
       <g:else>
         <td><g:if test="${caseRecordInstance.feedback && caseRecordInstance.feedback?.reviewDate != null}"><span class="yes">Yes</span> <g:link controller="feedback" action="view" id="${caseRecordInstance.feedback.id}">(View)</g:link></g:if>
          <g:if test="${caseRecordInstance.feedback && caseRecordInstance.feedback?.reviewDate == null}"><span class="incomplete">In Progress</span></g:if>                        
          <g:if test="${!caseRecordInstance.feedback}"><span class="no">No</span></g:if></td>   
       </g:else>
      <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
          <g:if test="${caseRecordInstance.candidateRecord?.isConsented == true && caseRecordInstance.candidateRecord?.isEligible == true && caseRecordInstance.tissueRecoveryGtex && caseRecordInstance.caseReportForm?.status?.value == 1 && session.DM == true && session.org.code != 'OBBR' && (caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP')}">
            <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
          </g:if>
          <g:if test="${session.org.code == 'OBBR' && session.DM == true}"><a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a></g:if></td>                    
      <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.phase?.code}</b> (${caseRecordInstance.phase?.name})"><g:formatDate date="${caseRecordInstance.dateCreated}" /></span></td>
    </tr>
     </g:each>
   </g:if><g:else><tr><td colspan="10">No cases exist</td></tr></g:else>
  </tbody>
</table>

