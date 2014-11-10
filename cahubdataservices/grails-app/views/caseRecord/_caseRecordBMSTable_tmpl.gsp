<table>
    <thead>
        <tr><th colspan="10">BMS Case List</th></tr>
        <tr>
            <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
              <g:if test="${params.controller != 'textSearch'}">
                <th>QT</th>
              </g:if>
            </g:if>
            <g:sortableColumn property="caseId" params="[s:'bms']" title="${message(code: 'caseRecord.caseId.label', default: 'Case ID')}" />
            <g:sortableColumn property="parentCase" params="[s:'bms']" title="${message(code: 'caseRecord.caseId.label', default: 'Parent Case ID')}" />
            <g:sortableColumn property="bss.code" params="[s:'bms']" title="${message(code: 'caseRecord.BSS.label', default: 'BSS')}" />
            <g:sortableColumn property="caseCollectionType.name" params="[s:'bms']" title="${message(code: 'candidateRecord.caseType.label', default: 'Case Type')}" />
            <th>Candidate ID</th>
            <th>TRF</th>
            <th class="specimencount">Specimens</th>
            <g:sortableColumn property="caseStatus.name" params="[s:'bms']" title="${message(code: 'caseRecord.dateCreated.label', default: 'Case Status')}" />
            <g:sortableColumn class="dateentry" property="dateCreated" params="[s:'bms']" title="${message(code: 'caseRecord.dateCreated.label', default: 'Date Created')}" />
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
                    <td>
                        <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}" />
                        %{-- <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
                    </td>
                    <td>
                        <g:displayCaseRecordLink caseRecord="${caseRecordInstance.parentCase}" session="${session}" nullFlavor="nothing"/>
                        %{-- <g:link controller="caseRecord" action="display" id="${caseRecordInstance.parentCase?.id}">${caseRecordInstance.parentCase?.caseId}</g:link> --}%
                    </td>
                    <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
                    <td>${caseRecordInstance.caseCollectionType}</td>
                    <td><g:if test="${caseRecordInstance.parentCase?.candidateRecord}"><a href="/cahubdataservices/candidateRecord/view/${caseRecordInstance?.parentCase?.candidateRecord?.id}">${caseRecordInstance?.parentCase?.candidateRecord?.candidateId}</a></g:if><g:else><span class="no">Parent Not Linked</span></g:else></td>
                    <td><g:if test="${caseRecordInstance.tissueRecoveryBms}">
                          <g:if test="${caseRecordInstance.tissueRecoveryBms.dateSubmitted}"><span class="yes">Yes</span></g:if>
                          <g:else><span class="incomplete">In Progress</span></g:else> 
                          <g:if test="${session.DM == true}">
                                <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}">
                                    <g:if test="${caseRecordInstance.tissueRecoveryBms.dateSubmitted}">
                                        <g:link controller="tissueRecoveryBms" action="restart" id="${caseRecordInstance.tissueRecoveryBms.id}" onclick="return confirm('The form has beem submitted, are you sure to resume editing?')" >(Edit)</g:link>|<g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link>
                                    </g:if><g:else>
                                        <g:link controller="tissueRecoveryBms" action="edit" id="${caseRecordInstance.tissueRecoveryBms.id}" >(Edit)</g:link>|<g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link>
                                    </g:else>
                                </g:if><g:else><g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link></g:else>
                            </g:if><g:else><g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link></g:else>
                        </g:if><g:else>
                            <span class="no">No</span><g:if test="${session.DM == true && caseRecordInstance?.parentCase?.candidateRecord && caseRecordInstance?.parentCase?.tissueRecoveryGtex?.collectionDate}"><a href="/cahubdataservices/tissueRecoveryBms/save?caseRecord.id=${caseRecordInstance.id}">(Add)</a>
                        </g:if></g:else></td>
                    <td><g:if test="${specimenCountBMS.get(caseRecordInstance.id)}">${specimenCountBMS.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>
                    <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
                        <g:if test="${caseRecordInstance.candidateRecord?.isConsented == true && caseRecordInstance.candidateRecord?.isEligible == true && caseRecordInstance.tissueRecoveryGtex && caseRecordInstance.caseReportForm?.status?.value == 1 && session.DM == true && session.org.code != 'OBBR' && (caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP')}">
                            <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
                        </g:if><g:if test="${session.org.code == 'OBBR' && session.DM == true}">
                            <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a></g:if></td>
                    <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
                </tr></g:each></g:if>
        <g:else><tr><td colspan="10">No cases exist</td></tr></g:else>
    </tbody>
</table>
