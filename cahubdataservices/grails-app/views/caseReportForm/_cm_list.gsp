<%@ page import="java.text.SimpleDateFormat" %>
<%@page defaultCodec="none" %>
<!-- common dialog box div holder -->
<div id="vocDefDialog1" ></div>

<div class="list">
  <h3>Section C.  Current Medications</h3>
  <g:if test="${concomitantMedicationInstanceList}">
    <table >
      <thead>
        <tr>
          <th>Medication Name/Vitamins/Supplements</th>
      <g:if test="${!show45VersionUpdates}">
        <th>Amount</th>
        <th>Dosage Form/Unit</th>
        <th>Frequency</th>
        <th>Route</th>
      </g:if>
      <g:if test="${show45VersionUpdates}">
        <th>History Source</th>
      </g:if>
      <th>Date of Last Administration</th>


      </tr>
      </thead>
      <tbody>
      <g:each in="${concomitantMedicationInstanceList}" status="i" var="concomitantMedicationInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

        <g:if test="${show45VersionUpdates && concomitantMedicationInstance?.medicationNameCvocab?.cVocabId}">
        <!--  <td>${concomitantMedicationInstance?.medicationNameCvocab?.cVocabUserSelection}  </td>-->
          <td class="value">
            <div class="vocDefDC" data-title="Medication Name/Vitamins/Supplements" data-vocid="${concomitantMedicationInstance.medicationNameCvocab?.id}" title="${concomitantMedicationInstance?.medicationNameCvocab?.cVocabUserSelection}" >
              <span class="asLink">${concomitantMedicationInstance?.medicationNameCvocab?.cVocabUserSelection}</span>
            </div>
          </td>
        </g:if>
        <g:else>
          <td>${fieldValue(bean: concomitantMedicationInstance, field: "medicationName")}</td>
        </g:else>
        <g:if test="${!show45VersionUpdates}">

          <g:if test="${!concomitantMedicationInstance.amount || concomitantMedicationInstance.amount == -1}">
            <td>&nbsp;</td>
          </g:if>
          <g:else>
            <td>${fieldValue(bean: concomitantMedicationInstance, field: "amount")}</td>
          </g:else>
          <td>${fieldValue(bean: concomitantMedicationInstance, field: "dosageUnit")}</td>
          <td>${fieldValue(bean: concomitantMedicationInstance, field: "frequency")}</td>

          <td>${fieldValue(bean: concomitantMedicationInstance, field: "route")}</td>

        </g:if>


        <g:if test="${show45VersionUpdates}"><td>${fieldValue(bean: concomitantMedicationInstance, field: "source")}</td> </g:if>
        <g:if test="${!concomitantMedicationInstance.dateofLastAdministration || (new SimpleDateFormat('MM/dd/yyyy')).format(concomitantMedicationInstance.dateofLastAdministration)=='01/01/1900'}">
          <td>Unknown</td>
        </g:if>
        <g:else>
          <td><g:formatDate format="MM/dd/yyyy" date="${concomitantMedicationInstance.dateofLastAdministration}" /></td>
        </g:else>




        </tr>
      </g:each>
      </tbody>
    </table>
  </g:if>
  <g:else>
    <p>No record.</p>
  </g:else>
</div>
