<table>
  <thead>
    <tr><th colspan="10">BPV Case List</th></tr>
    <tr>
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
  <g:sortableColumn property="primaryTissueType" title="${message(code: 'caseRecord.caseId.label', default: 'Primary Organ')}" />                                      
  <g:sortableColumn property="bss" title="${message(code: 'caseRecord.BSS.label', default: 'BSS')}" />
  <th>Candidate ID</th>
  <th class="specimencount">Specimens</th>                            
  <g:sortableColumn property="caseStatus" title="${message(code: 'caseRecord.dateCreated.label', default: 'Case Status')}" /> 
<th >Follow-up Status</th>
  <g:sortableColumn property="dateCreated" class="dateentry" title="${message(code: 'caseRecord.dateCreated.label', default: 'Date Created')}" />
   
</tr>
</thead>
<tbody>
<g:if test="${caseRecordInstanceList}">
  <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
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
    <td>
    <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}"/>
%{--
<g:if test="${caseRecordInstance.caseStatus.code == 'COMP' || caseRecordInstance.caseStatus.code == 'RELE' || session.org.code == caseRecordInstance.bss.code}">
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
    <td>${caseRecordInstance.primaryTissueType}</td>                    
    <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
    <td><a href="/cahubdataservices/candidateRecord/view/${caseRecordInstance.candidateRecord?.id}">${caseRecordInstance.candidateRecord?.candidateId}</a></td>
    <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>
    <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
    <g:if test="${caseRecordInstance.candidateRecord?.isConsented == true && caseRecordInstance.candidateRecord?.isEligible == true && caseRecordInstance.tissueRecoveryGtex && caseRecordInstance.caseReportForm?.status?.value == 1 && session.DM == true && session.org.code != 'OBBR' && (caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP')}">
      <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
    </g:if><g:if test="${session.org.code == 'OBBR' && session.DM == true}">
      <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
    </g:if></td>
    
    <td> 
    <g:if test="${sixMonthFollowUpStat.get(caseRecordInstance.id)}">
      <g:if test="${sixMonthFollowUpStat.get(caseRecordInstance.id) =='In Progress'}"> <span class='incomplete'> ${sixMonthFollowUpStat.get(caseRecordInstance.id)}</span></g:if>
      <g:if test="${sixMonthFollowUpStat.get(caseRecordInstance.id) =='Not Started'}"> ${sixMonthFollowUpStat.get(caseRecordInstance.id)}</g:if>
      <g:if test="${sixMonthFollowUpStat.get(caseRecordInstance.id) =='Completed'}"> <span class='yes'> ${sixMonthFollowUpStat.get(caseRecordInstance.id)}</span></g:if>

    </g:if>
    <g:else>&nbsp;</g:else></td>
    <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
    </tr>
  </g:each></g:if>
<g:else><tr><td colspan="7">No cases exist</td></tr></g:else>
</tbody>
</table>

