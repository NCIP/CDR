<table>
   <thead>
      <tr><th colspan="9">BPV Case List</th></tr>
      <tr>
         <g:if test="${params.controller != 'textSearch'}">
             <th>QT</th>
         </g:if>
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
                    <g:if test="${params.controller != 'textSearch'}">
                        <td><g:if test="${queryCount.get(caseRecordInstance.id)}"><a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}"><span class="no">${queryCount.get(caseRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
                    </g:if>
                    <td>
                        <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}" action="showbpvdeident" />
                        %{-- <g:link controller="caseRecord" action="showbpvdeident" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
                    </td>                    
                    <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
                    <td>${caseRecordInstance.primaryTissueType}</td>                                        
                    <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>                                         
                    <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span></td>                    
                    <td><g:if test ="${caseRecordInstance.caseStatus?.code in ['BSSQACOMP', 'QA', 'COMP', 'RELE', 'REMED']}"><a target="_blank" href="${createLink(uri: '/')}rest/caserecord/${caseRecordInstance.caseId}">View XML</a>
                        </g:if><g:else>Not Available</g:else>                              
                    </td>                    
                    <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
                </tr>
            </g:each></g:if><g:else><tr><td colspan="7">No cases exist</td></tr></g:else>
    </tbody>
</table>

