<div id="ErrorDiv"></div>
<table>
    <thead>
      <tr><th colspan="9">BMS Case List</th></tr>
      <tr>
        <g:sortableColumn property="caseId" title="Case ID" />
        <th>Parent Case ID</th>
        <g:sortableColumn property="bss" title="BSS" />
        <g:sortableColumn property="caseCollectionType" title="Case Type" />
        <th class="specimencount">Specimens</th>
        <g:sortableColumn property="caseStatus" title="Case Status" />
        <th>PRC Report</th>
        <th>Donor Export</th>
        <g:sortableColumn class="dateentry" property="dateCreated" title="Date Created" />
      </tr>
    </thead>
    <tbody>
          <g:if test="${caseRecordInstanceList}">
            <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>${caseRecordInstance.caseId}</td>
                <td>${caseRecordInstance.parentCase?.caseId}</td>
                <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
                <td>${caseRecordInstance.caseCollectionType}</td>
                <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>
                <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span></td>
                <td><g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate != null && caseRecordInstance.caseStatus.code  != 'WITHDR'}">
                       <span class="yes">Yes</span> <g:link controller="prcReport" action="viewBms" id="${caseRecordInstance.prcReport.id}">(View)</g:link> | <a target="_blank" href="${createLink(uri: '/')}rest/gtexprcreport/${caseRecordInstance.caseId}">(XML)</a>
                    </g:if><g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate == null}"><span class="incomplete">In Progress</span>
                    </g:if><g:if test="${!caseRecordInstance.prcReport ||  caseRecordInstance.caseStatus.code  == 'WITHDR'}"><span class="no">No</span></g:if></td>
                <td><g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'}"><a target="_blank" href="${createLink(uri: '/')}rest/gtexdonorvars/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (XML)</a></g:if><g:else><span class="no">No</span></g:else></td>
                <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
              </tr>
            </g:each>
        </g:if><g:else><tr><td colspan="10">No cases exist</td></tr></g:else>
    </tbody>
</table>
