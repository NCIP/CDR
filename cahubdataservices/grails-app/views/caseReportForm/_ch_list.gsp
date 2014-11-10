
<%@ page import="java.text.SimpleDateFormat" %>
<div class="list">
  <h3>Cancer History</h3>
  <g:if test="${cancerHistoryInstanceList}">
    <table>
      <thead>
        <tr>

          <th class="nowrap">Primary<br />Cancer Type</th>
          <th class="nowrap">Month/Year of<br />first diagnosis</th>
      <g:if test="${show45VersionUpdates}">
        <th class="nowrap">History Source</th>
      </g:if>
      <th class="nowrap">History of any<br />treatments</th>
      <th>Date of last radiation or chemotherapy treatment if applicable</th>
      <th>Is there medical record documentation of this history of cancer and treatment</th>

<%@page defaultCodec="none" %>
      </tr>
      </thead>
      <tbody>
      <g:each in="${cancerHistoryInstanceList}" status="i" var="cancerHistoryInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          
        <g:if test="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabUserSelection}">
          <td class="value">
            <div class="vocDefDC" data-title="Cancer History" data-vocid="${cancerHistoryInstance?.primaryTumorSiteCvocab?.id}" title="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabUserSelection}" >
              <span class="asLink">${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabUserSelection}</span>
            </div>
          </td>
        </g:if>
        <g:else>

          <td>${fieldValue(bean: cancerHistoryInstance, field: "primaryTumorSite")}</td>
        </g:else>

        <g:if test="${!cancerHistoryInstance.monthYearOfFirstDiagnosis || (new SimpleDateFormat('MM/yyyy')).format(cancerHistoryInstance.monthYearOfFirstDiagnosis)=='01/1900'}">
          <td>&nbsp;</td>
        </g:if>
        <g:else>
          <td><g:formatDate format="MM/yyyy" date="${cancerHistoryInstance.monthYearOfFirstDiagnosis}" /></td>
        </g:else>

        <g:if test="${show45VersionUpdates}">

          <td>${fieldValue(bean: cancerHistoryInstance, field: "source")}</td>
        </g:if>

        <g:if test="${cancerHistoryInstance.treatments == 'null'}">
          <td>&nbsp;</td>
        </g:if>
        <g:else>
          <td>${fieldValue(bean: cancerHistoryInstance, field: "treatments")}</td>
        </g:else>

        <g:if test="${!cancerHistoryInstance.monthYearOfLastTreatment || (new SimpleDateFormat('MM/yyyy')).format(cancerHistoryInstance.monthYearOfLastTreatment)=='01/1900'}">
          <td>&nbsp;</td>
        </g:if>
        <g:else>
          <td><g:formatDate format="MM/yyyy" date="${cancerHistoryInstance.monthYearOfLastTreatment}" /></td>
        </g:else>
        <td>${fieldValue(bean: cancerHistoryInstance, field: "medicalRecordExist")}</td>

        </tr>
      </g:each>
      </tbody>
    </table>
  </g:if>
  <g:else><p> No record.</g:else>
</div>
