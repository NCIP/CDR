<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>

<g:set var="canModify" value="${session.DM && (!candidateRecordInstance?.caseRecord || candidateRecordInstance?.caseRecord?.caseStatus?.code == 'INIT' || candidateRecordInstance?.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance?.caseRecord?.caseStatus?.code == 'REMED')}" />

<table>
  <tbody>
    <tr class="prop">
      <td valign="top" class="name" style="width:250px;">
        <label for="candidateId">Candidate ID:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'candidateId', 'errors')}">
${candidateRecordInstance?.candidateId}
      </td>
    </tr>                          

    <tr class="prop">
      <td valign="top" class="name">
        <label for="bss">BSS:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'bss', 'errors')}">
${candidateRecordInstance?.bss.name} (${candidateRecordInstance?.bss?.code})
<%--
<g:select name="bss.id" from="${bssSubList}" optionValue="name" optionKey="id" value="${candidateRecordInstance?.bss?.id}"  />
--%>
      </td>
    </tr>                        

    <tr class="prop">
      <td valign="top" class="name">
        <label for="caseCollectionType">Case Collection Type:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'caseCollectionType', 'errors')}">
  <g:select name="caseCollectionType.id" from="${filteredCaseCollectionTypeList}" optionKey="id" value="${candidateRecordInstance?.caseCollectionType?.id}"  />
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="candidateId">Date Created:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'dateCreated', 'errors')}">
<g:formatDate date="${candidateRecordInstance?.dateCreated}" />
</td>
</tr>                           

<tr class="prop">
  <td valign="top" class="name">
    <label for="caseRecord">Linked Case ID:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'caseRecord', 'errors')}">
<g:if test="${!candidateRecordInstance?.caseRecord}">
  <span class="no">Not Linked</span>
  %{--
  <g:if test="${candidateRecordInstance.isConsented == true && candidateRecordInstance.bpvConsentEnrollment && session.DM == true}">
      <g:link controller="candidateRecord" action="linkCandidateToCase" id="${candidateRecordInstance.id}">(Link)</g:link></span>
  </g:if>
  --}%
</g:if>
<g:else>
  %{-- <g:link controller="caseRecord" action="display" id="${candidateRecordInstance?.caseRecord?.id}">${candidateRecordInstance?.caseRecord?.caseId}</g:link> --}%
  <g:displayCaseRecordLink caseRecord="${candidateRecordInstance?.caseRecord}" session="${session}"/>
  %{--
  <g:if test="${!candidateRecordInstance.caseRecord?.tissueRecoveryGtex && !candidateRecordInstance.caseRecord?.caseReportForm}">
      <a href="/cahubdataservices/candidateRecord/linkCandidateToCase/${candidateRecordInstance.id}">(Unlink)</a>
  </g:if>
  --}%
</g:else>
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label>Subject Screening Form:</label>
  </td>
  <td>
<g:if test="${!candidateRecordInstance.bpvScreeningEnrollment}">
  <span class="no">Not Started</span>
  <g:if test="${canModify}">
    <a href="/cahubdataservices/bpvScreeningEnrollment/create?candidateRecord.id=${candidateRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SCREEN')?.id}">(Start)</a>
  </g:if>
</g:if>
<g:elseif test="${!candidateRecordInstance.bpvScreeningEnrollment.dateSubmitted}">
  <span class="incomplete">In Progress</span>
  <g:if test="${canModify}">
    <g:link controller="bpvScreeningEnrollment" action="edit" id="${candidateRecordInstance.bpvScreeningEnrollment.id}">(Edit)</g:link>
  </g:if>
  <g:else>
    <g:link controller="bpvScreeningEnrollment" action="show" id="${candidateRecordInstance.bpvScreeningEnrollment.id}">(View)</g:link>
  </g:else>
</g:elseif>
<g:else>
  <span class="yes">Completed</span>
  <g:link controller="bpvScreeningEnrollment" action="show" id="${candidateRecordInstance.bpvScreeningEnrollment.id}">(View)</g:link>
</g:else>
</td>                                                                      
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label>Subject Consent Form:</label>
  </td>
  <td>
<g:if test="${!candidateRecordInstance.bpvConsentEnrollment}">
  <span class="no">Not Started</span>
  <g:if test="${canModify && candidateRecordInstance?.isEligible}">
    <a href="/cahubdataservices/bpvConsentEnrollment/create?candidateRecord.id=${candidateRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('CONSENT')?.id}">(Start)</a>
  </g:if>
</g:if>
<g:elseif test="${!candidateRecordInstance.bpvConsentEnrollment.dateSubmitted}">
  <span class="incomplete">In Progress</span>
  <g:if test="${canModify}">
    <g:link controller="bpvConsentEnrollment" action="edit" id="${candidateRecordInstance.bpvConsentEnrollment.id}">(Edit)</g:link>
  </g:if>
  <g:else>
    <g:link controller="bpvConsentEnrollment" action="show" id="${candidateRecordInstance.bpvConsentEnrollment.id}">(View)</g:link>
  </g:else>
</g:elseif>
<g:else>
  <span class="yes">Completed</span>
  <g:link controller="bpvConsentEnrollment" action="show" id="${candidateRecordInstance.bpvConsentEnrollment.id}">(View)</g:link>
</g:else>
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="internalComments">Comments:*</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'internalComments', 'errors')}">
<g:textArea name="internalComments" cols="40" rows="5" value="${candidateRecordInstance?.internalComments}" /><br />
<span class="no-phi-note">*No PHI allowed in this field</span>
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="study">Study:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'study', 'errors')}">
${candidateRecordInstance?.study.code}
  </td>
</tr>

<g:if test="${session.DM == true}">
  <tr class="prop">
    <td valign="top" class="name">Query Tracker:</td>
    <td valign="top" class="value">
      <a href="/cahubdataservices/query/listByCandidate?candidateRecord.id=${candidateRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
    </td>
  </tr>
</g:if>

<g:if test="${session.org?.code == 'OBBR'}">
  <tr class="prop">
    <td valign="top" class="name">
      <label for="study">CDR Version When Created:</label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'cdrVer', 'errors')}">
${candidateRecordInstance?.cdrVer}
    </td>
  </tr>
  </g:if>

    </tbody>
    </table>
