
<%@ page import="nci.obbr.cahub.forms.gtex.crf.GeneralMedicalHistory" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="generalmedhistory create" scope="request"/>

<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory')}" />
  <script type="text/javascript" src="${resource(dir:'js',file:'generalmedhistory.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link class="list" controller="caseReportForm" action="show" id="${params.formid}">Case Report Form Menu</g:link>
      <g:link class="list" controller="medicalHistory" action="edit" id="${params.formid}" params="[formid:params.formid]">Medical History</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1>General Medical History</h1>

    <div class="message">Instruction: If "Choose Option" is "Yes" and "Year of Onset" is unknown, select "Unknown."</div>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:each in="${generalMedicalHistoryInstanceList}" status="i" var="generalMedicalHistoryInstance">
      <g:hasErrors bean="${generalMedicalHistoryInstance}">
        <div class="errors">
          <g:renderErrors bean="${generalMedicalHistoryInstance}" as="list" />
        </div>
      </g:hasErrors>
    </g:each>
    <div class="list">
      <table>
        <thead>
          <tr>
            <th>Row</th>
            <th>Medical Condition</th>
            <th>Choose Option</th>
            <th>Year of Onset (YYYY)</th>
            <th>History of Treatment</th>
        <g:if test="${show45VersionUpdates}">
          <th>History Source</th>
        </g:if>
        <g:else>
          <th>Medical Record Documentation</th>
        </g:else>
        </tr>
        </thead>
        <tbody>
        <g:form method="post" autocomplete="off" >
          <input type="hidden" name="mid" value="${params.mid}"/>
          <input type="hidden" name="formid" value="${params.formid}"/>
          <g:each in="${generalMedicalHistoryInstanceList}" status="i" var="generalMedicalHistoryInstance">
            <input type="hidden"  name="id${i}" value="${generalMedicalHistoryInstance.id}"/>
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "rown")}</td>
              <td>${fieldValue(bean: generalMedicalHistoryInstance, field: "medicalCondition")}<span id="generalmedicalhistory.${generalMedicalHistoryInstance.code?.toLowerCase()}" class="vocab-tooltip"></span></td> 
              <td class="value ${errorMap.get('option'+ generalMedicalHistoryInstance.id)}">
                <div><g:radio name="chooseOption${i}" id="chooseOption${i}_1" value="Yes" checked="${generalMedicalHistoryInstance.chooseOption?.toString() =='Yes'}"/>&nbsp;<label for="chooseOption${i}_1">Yes</label> <br />
                  <g:radio name="chooseOption${i}" id="chooseOption${i}_2" value="No" checked="${generalMedicalHistoryInstance.chooseOption?.toString() =='No'}"/>&nbsp;<label for="chooseOption${i}_2">No</label> <br />
                  <nobr><g:radio name="chooseOption${i}" id="chooseOption${i}_3" value="Unknown" checked="${generalMedicalHistoryInstance.chooseOption?.toString() =='Unknown'}"/>&nbsp;<label for="chooseOption${i}_3">Unknown</label></nobr>
                </div>
              </td>
              <td class="value ${errorMap.get('year'+ generalMedicalHistoryInstance.id)}">
                <input type="text" id="yearOfOnset${i}" name="yearOfOnset${i}" class="yearOfOnset" value="${fieldValue(bean: generalMedicalHistoryInstance, field: "yearOfOnset")}" maxlength="4" size="4" /><br />
                <nobr><g:radio name="yearOfOnsetUnknown${i}" class="yearOfOnsetUnknown" value="Unknown" checked="${generalMedicalHistoryInstance?.yearOfOnset == '1900'}"/>&nbsp;<label for="yearOfOnsetUnknown${i}">Unknown</label></nobr>
              </td>
              <td class="value ${errorMap.get('treatment'+ generalMedicalHistoryInstance.id)}">
                <div>
                  <g:radio name="treatment${i}" id="treatment${i}_1" value="Yes" checked="${generalMedicalHistoryInstance.treatment?.toString() =='Yes'}"/>&nbsp;<label for="treatment${i}_1">Yes</label> <br />
                  <g:radio name="treatment${i}" id="treatment${i}_2"  value="No" checked="${generalMedicalHistoryInstance.treatment?.toString() =='No'}"/>&nbsp;<label for="treatment${i}_2">No</label> <br />
                  <nobr><g:radio name="treatment${i}" id="treatment${i}_3"   value="Unknown" checked="${generalMedicalHistoryInstance.treatment?.toString() =='Unknown'}"/>&nbsp;<label for="treatment${i}_3">Unknown</label></nobr>
                </div>
              </td>
            <g:if test="${show45VersionUpdates}">
              <td class="value ${errorMap.get('source'+ generalMedicalHistoryInstance.id)}">
                <div>
                  <g:select name="source${i}" from="${['Medical Record', 'Family Report', 'Medical Record and Family Report']}" value="${generalMedicalHistoryInstance.source ?:generalMedicalHistoryInstance.medicalHistory.source}" noSelection="['': 'Select One']"/>
                </div>
              </td>
            </g:if>
            <g:else>
              <td class="value ${errorMap.get('medicalRecord'+ generalMedicalHistoryInstance.id)}">
                <div>
                  <g:radio name="medicalRecord${i}" id="medicalRecord${i}_1" value="Yes" checked="${generalMedicalHistoryInstance.medicalRecord?.toString() =='Yes'}"/>&nbsp;<label for="medicalRecord${i}_1">Yes</label> <br />
                  <g:radio name="medicalRecord${i}" id="medicalRecord${i}_2" value="No" checked="${generalMedicalHistoryInstance.medicalRecord?.toString() =='No'}"/>&nbsp;<label for="medicalRecord${i}_2">No</label> <br />
                  <nobr><g:radio name="medicalRecord${i}" id="medicalRecord${i}_3" value="Unknown" checked="${generalMedicalHistoryInstance.medicalRecord?.toString() =='Unknown'}"/>&nbsp;<label for="medicalRecord${i}_3">Unknown </label></nobr>   
                </div>
              </td>
            </g:else>
            </tr>
          </g:each>
          </tbody>
      </table>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update_g" value="Save" /></span>
      </div>
      </g:form>
    </div>
  </div>
</body>
</html>
