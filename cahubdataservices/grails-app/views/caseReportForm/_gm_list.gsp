<!-- common dialog box div holder -->
<%@page defaultCodec="none" %>
<div id="vocDefDialog1" ></div>


<div class="dialog">
  <h3> General Medical History</h3>
  <table>
    <thead>
        <th class="medical-condition">Medical History</th>
        <th>Choose Option</th>
        <th class="nowrap">Year of Onset<br />(YYYY)</th>
        <th>History of Treatment</th>
    <g:if test="${show45VersionUpdates}">
      <th class="nowrap">History Source</th>
    </g:if>
    <g:else>
      <th class="nowrap">Medical Record<br />Documentation</th>
    </g:else>

    </tr>
    </thead>
    <tbody>
    <g:each in="${generalMedicalHistoryInstanceList}" status="i" var="generalMedicalHistoryInstance">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <g:if test="${show45VersionUpdates && generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabId}">
          
      <td class="value">
            <div class="vocDefDC" data-title="Medical History" data-vocid="${generalMedicalHistoryInstance?.medicalConditionCvocab?.id}" title="${generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabUserSelection}" >
              <span class="asLink">${generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabUserSelection}</span>
            </div>
          </td>
        
        
        </g:if>
      <g:else>
        <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "medicalCondition")}<span id="generalmedicalhistory.${generalMedicalHistoryInstance.code?.toLowerCase()}" class="vocab-tooltip"></span></td>
      </g:else>
         
      <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "chooseOption")}</td>

      <g:if test="${!generalMedicalHistoryInstance.yearOfOnset || generalMedicalHistoryInstance.yearOfOnset == '1900'}">
        <td>Unknown</td>
      </g:if>
      <g:else>
        <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "yearOfOnset")}</td>
      </g:else>


      <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "treatment")}</td>

      <g:if test="${show45VersionUpdates}">

        <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "source")}</td>
      </g:if>
      <g:else>
        <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "medicalRecord")}</td>

      </g:else>




      </tr>
    </g:each>
    </tbody>
  </table>
</div>
