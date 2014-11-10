<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<table>
  <thead>
    <tr ><th class="ctcpatient-thtag" colspan="10">CTC Patient List</th></tr>
    <tr>


      <th>Patient ID</th>
      <th>Case Status</th>

      <th>Sex</th>

      <th>Visit Count</th>

      <th>Disease</th>
      <th>Cancer Stage</th>
      <th>Experiment</th>
      <th>Collection Site</th>
      <th>Date Created</th>
      <th> Action </th>




    </tr>
  </thead>
  <tbody>
  <g:each in="${patientRecordList}" status="i" var="patientRecordInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

     
      
    <g:if test="${patientRecordInstance.caseRecord?.id}">
       <td> <g:link controller="caseRecord" action="accessCtc" id="${patientRecordInstance.caseRecord?.id}">${patientRecordInstance.patientId}</g:link></td>
      <td><b>${patientRecordInstance.caseRecord?.caseStatus}</b></td>
    </g:if>
    <g:else> 
       <td> ${patientRecordInstance.patientId}</td>
      <td>  &nbsp; </td> </g:else>
    
    <td>${fieldValue(bean: patientRecordInstance, field: "gender")}</td>

    <td>${fieldValue(bean: patientRecordInstance, field: "visit")}</td>

    <g:if test="${patientRecordInstance.disease}">
            <td>${patientRecordInstance.disease}</td>
    </g:if>
    <g:else> 
            <td>Breast Cancer </td> 
    </g:else>

    <td>${fieldValue(bean: patientRecordInstance, field: "cancerStage")}</td>
    
    <td>${fieldValue(bean: patientRecordInstance, field: "experiment")}</td>
 
    <td>${fieldValue(bean: patientRecordInstance, field: "collectionSite")}</td>
    <td>${patientRecordInstance.dateCreated?.format('MM-dd-yyyy HH:mm')}</td>

    <td nowrap>
    <g:link class="ui-button ui-state-default ui-corner-all removepadding borderless" title="view" controller="patientRecord" action="show" id="${patientRecordInstance.id}"><span class="ui-icon ui-icon-table">View</span></g:link>
    <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) &&!patientRecordInstance.dateSubmitted}">
      <g:link class="ui-button ui-state-default ui-corner-all removepadding borderless" title="edit" controller="patientRecord" action="edit" id="${patientRecordInstance?.id}" ><span class="ui-icon ui-icon-pencil">Edit</span></g:link>
    </g:if>

   

    </td>       
    </tr>
  </g:each>
</tbody>
</table>

