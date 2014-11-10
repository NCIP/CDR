<table>
  <thead>
    <tr><th colspan="9"><g:if test="${showBRN=='Y'}">BRN Case List</g:if><g:else>Case List</g:else></th></tr>
    <tr>
      <g:sortableColumn property="caseId" title="${message(code: 'caseRecord.caseId.label', default: 'Case ID')}" />                          
      <g:sortableColumn property="bss" title="${message(code: 'caseRecord.BSS.label', default: 'BSS')}" />
      <th>Primary Organ</th>    
      <th class="specimencount">Specimens</th>                            
      <g:sortableColumn property="caseStatus" title="${message(code: 'caseRecord.dateCreated.label', default: 'Case Status')}" />   
      <th>Case Record Export</th>
      <g:sortableColumn property="dateCreated" class="dateentry" title="${message(code: 'caseRecord.dateCreated.label', default: 'Date Created')}" />                
    </tr>
  </thead>
  <tbody>
      <g:if test="${caseRecordInstanceList}"><g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
      <td>
          <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}" />
          %{-- <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
      </td>
      <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
      <td>${caseRecordInstance.primaryTissueType}</td>                            
      <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>                                         
      <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
        <g:if test="${caseRecordInstance.candidateRecord?.isConsented == true && caseRecordInstance.candidateRecord?.isEligible == true && caseRecordInstance.tissueRecoveryGtex && caseRecordInstance.caseReportForm?.status?.value == 1 && session.DM == true && session.org.code != 'OBBR' && (caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP')}">
          <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
        </g:if><g:if test="${session.org.code == 'OBBR' && session.DM == true}">
          <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
        </g:if></td>                    
      <td><g:if test ="${caseRecordInstance.caseStatus?.code=='RELE' || caseRecordInstance.caseStatus?.code=='COMP'}">
          <a target="_blank" href="${createLink(uri: '/')}rest/caserecord/${caseRecordInstance.caseId}">View XML</a>
          </g:if><g:else>Not Available</g:else></td>                            
      <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
    </tr>
      </g:each></g:if><g:else>
    <tr><td colspan="10">No cases exist</td></tr></g:else>
  </tbody>
</table>

