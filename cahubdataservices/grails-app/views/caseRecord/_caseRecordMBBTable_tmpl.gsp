<table>
  <thead>
    <tr><th colspan="9">MBB Case List</th></tr>
    <tr>
          <th>QT</th>
      <th>Case ID</th>
      <th>Ventilator<br />(yes or no)</th> 
      <th>Time on Ventilator<br />(hours)</th>                              
      <th class="serology-alert-entry">Serology Alert</th> 
      <th class="dateentry">Date Created</th>
      <th class="trf-entry">TRF</th>
       <th class="trf-entry">Feedback</th>
    </tr>
  </thead><tbody>
  <g:if test="${caseRecordInstanceList}">
    <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:if test="${queryCount.get(caseRecordInstance.id)}"><a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}"><span class="no">${queryCount.get(caseRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
      <td nowrap="nowrap">${caseRecordInstance.caseId}</td>
      <td>${caseRecordInstance.caseReportForm?.deathCircumstances?.onVentilator}</td>
      <td>${caseRecordInstance.caseReportForm?.deathCircumstances?.ventilatorDuration}</td>
      <td>${sermap.get(caseRecordInstance.caseId)}</td>
      <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
      <td><g:if test="${caseRecordInstance.tissueRecoveryBrain && caseRecordInstance.tissueRecoveryBrain.dateSubmitted }"><g:link controller="tissueRecoveryBrain" action="view" id="${caseRecordInstance.tissueRecoveryBrain.id}">(View)</g:link></g:if>
      <g:elseif test="${caseRecordInstance.tissueRecoveryBrain && !caseRecordInstance.tissueRecoveryBrain.dateSubmitted }"><g:link controller="tissueRecoveryBrain" action="edit" id="${caseRecordInstance.tissueRecoveryBrain.id}" >(Edit)</g:link>|<g:link controller="tissueRecoveryBrain" action="view" id="${caseRecordInstance.tissueRecoveryBrain.id}">(View)</g:link></g:elseif>
      <g:else><a href="/cahubdataservices/tissueRecoveryBrain/upload?caseRecord.id=${caseRecordInstance.id}">(Add)</a></g:else>
      </td>
      
      <td><g:if test="${caseRecordInstance.brainBankFeedback && caseRecordInstance.brainBankFeedback.dateSubmitted }"><g:link controller="brainBankFeedback" action="show" id="${caseRecordInstance.brainBankFeedback.id}">(View)</g:link></g:if>
      <g:elseif test="${caseRecordInstance.brainBankFeedback && !caseRecordInstance.brainBankFeedback.dateSubmitted }"><g:link controller="brainBankFeedback" action="edit" id="${caseRecordInstance.brainBankFeedback.id}" >(Edit)</g:link>|<g:link controller="brainBankFeedback" action="show" id="${caseRecordInstance.brainBankFeedback.id}">(View)</g:link></g:elseif>
      <g:else><a href="/cahubdataservices/brainBankFeedback/create?caseRecord.id=${caseRecordInstance.id}">(Add)</a></g:else>
      </td>
      </tr>
    </g:each>
  </g:if><g:else><tr><td colspan="10">No cases exist</td></tr></g:else>
</tbody>
</table>

