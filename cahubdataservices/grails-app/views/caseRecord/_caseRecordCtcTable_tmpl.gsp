<%@ page import=" nci.obbr.cahub.datarecords.ctc.PatientRecord" %>
<table>
  <thead>
    <tr ><th colspan="10">CTC Case List</th></tr>
    <tr>
  <g:sortableColumn property="caseId" params="[s:'ctc']" title="${message(code: 'caseRecord.caseId.label', default: 'Case ID')}" />
 <!-- <th>Patient Visits</th> -->
  <g:sortableColumn property="bss.code" params="[s:'ctc']" title="${message(code: 'caseRecord.BSS.label', default: 'BSS')}" />

<%--   <th>CRF</th> --%>
  <th class="specimencount">Specimens</th>
  <g:sortableColumn property="caseStatus.name" params="[s:'ctc']" title="${message(code: 'caseRecord.dateCreated.label', default: 'Case Status')}" />
  <g:sortableColumn class="dateentry" property="dateCreated" params="[s:'bms']" title="${message(code: 'caseRecord.dateCreated.label', default: 'Date Created')}" />
</tr>
</thead>
<tbody>
<g:if test="${caseRecordInstanceList}">
  <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
  
      <td>
          <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}" />
          %{-- <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
      </td>
<%--
    <td><b>${PatientRecord.findByPatientId(caseRecordInstance.caseId)?.visit}</b> 
    <g:if test="${!PatientRecord.findByPatientId(caseRecordInstance.caseId)?.visit.equals("Two")}">
      (<g:link controller="patientRecord" action="edit" id="${PatientRecord.findByPatientId(caseRecordInstance.caseId)?.id}">Edit</g:link>)
    </g:if>
    </td>
--%>
    <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>


<%-- <td valign="top" class="value"><span class="no">No</span> (<a href="">Add</a>)</td> --%>
    <td></td>
    <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
    <g:if test="${session.org.code == 'OBBR' && session.DM == true}">
      <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a></g:if></td>
    <td><g:formatDate date="${caseRecordInstance.dateCreated}" /></td>
    </tr></g:each></g:if>
<g:else><tr><td colspan="10">No cases exist</td></tr></g:else>
</tbody>
</table>
