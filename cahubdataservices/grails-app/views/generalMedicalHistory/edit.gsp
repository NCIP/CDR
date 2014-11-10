<%@page defaultCodec="none" %>
<%@ page import="nci.obbr.cahub.forms.gtex.crf.GeneralMedicalHistory" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="generalmedhistory edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'generalMedicalHistory.label', default: 'GeneralMedicalHistory')}" />
  <script type="text/javascript">var show45VersionUpdates = ${show45VersionUpdates};</script>
  <script type="text/javascript" src="${resource(dir:'js',file:'generalmedhistory.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  <g:javascript>
    var immediateCause_cod_srcDef_str =""
    var firstCause_cod_srcDef_str = ""
    var lastCause_cod_srcDef_str=""
    var deathCertificateCause_cod_srcDef_str=""
    var medicalCondition_cod_srcDef_str=""
    <g:if test="${generalMedicalHistoryInstance?.medicalConditionCvocab?.srcDef}">
      medicalCondition_cod_srcDef_str ="${generalMedicalHistoryInstance?.medicalConditionCvocab?.srcDef.replaceAll("\"", "'")}"
    </g:if>
    var medicationName_rx_genName_str=""
  </g:javascript>
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
    <div class="message">Instruction: If "Year of Onset" is unknown, select "Unknown."</div>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${generalMedicalHistoryInstance}">
      <div class="errors">
        <g:renderErrors bean="${generalMedicalHistoryInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" autocomplete="off">
      <input type="hidden" name="medicalHistory.id"  value="${generalMedicalHistoryInstance?.medicalHistory?.id}"  />
      <input type='hidden' name="formid" value ="${params.formid}"/>
      <g:hiddenField name="id" value="${generalMedicalHistoryInstance?.id}" />
      <g:hiddenField name="version" value="${generalMedicalHistoryInstance?.version}" />
      <g:hiddenField name="chooseOption" value="${generalMedicalHistoryInstance?.chooseOption}" />
      <g:hiddenField name="rown" value="${generalMedicalHistoryInstance?.rown}" />
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="medicalCondition"><g:message code="generalMedicalHistory.medicalCondition.label" default="Medical History" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: generalMedicalHistoryInstance, field: 'medicalCondition', 'errors')}">
              <g:if test="${show45VersionUpdates}">
                  <div id="medicalCondition" class="select-cod-by-vocab"></div>
                    <input type="hidden" value="${generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabId}" id="medicalCondition-cod-id" name="medicalConditionCodId"/>
                    <input type="hidden" value="${generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabType}" id="medicalCondition-cod-typ" name="medicalConditionCodTyp"/>
                    <input type="hidden" value="${generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabUserSelection}" id="medicalCondition-cod-cod" name="medicalConditionCodCod"/>
                    <input type="hidden" value='${generalMedicalHistoryInstance?.medicalConditionCvocab?.cuiList?.join(",")}' id="medicalCondition-cod-cui" name="medicalConditionCodCui"/>
                    <input type="hidden" value="${generalMedicalHistoryInstance?.medicalConditionCvocab?.icdCd}" id="medicalCondition-cod-ICDcd" name="medicalConditionCodIcdCd"/>
                    <input type="hidden" value="${generalMedicalHistoryInstance?.medicalConditionCvocab?.cVocabVer}" id="medicalCondition-cod-cvocabVer" name="medicalConditionCodCvocabVer"/>
                     <input type="hidden" value="" id="medicalCondition-cod-srcDef" name="medicalConditionCodSrcDef"/>
              </g:if>
              <g:else>
                <g:textField name="medicalCondition" value="${generalMedicalHistoryInstance?.medicalCondition}" />
              </g:else>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="yearOfOnset"><g:message code="generalMedicalHistory.yearOfOnset.label" default="Year of Onset (YYYY)" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: generalMedicalHistoryInstance, field: 'yearOfOnset', 'errors')}">
          <g:textField name="yearOfOnset" maxlength="4" value="${generalMedicalHistoryInstance?.yearOfOnset}" size="4"/>
          <g:radio name="yearOfOnsetUnknown" value="Unknown" checked="${generalMedicalHistoryInstance?.yearOfOnset == '1900'}"/>&nbsp;<label for="yearOfOnsetUnknown">Unknown</label>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="treatment"><g:message code="generalMedicalHistory.treatment.label" default="History of Treatment" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: generalMedicalHistoryInstance, field: 'treatment', 'errors')}">
              <div>
                <g:radio name="treatment" id="t1" value="Yes" checked="${generalMedicalHistoryInstance?.treatment?.toString() =='Yes'}"/>&nbsp;<label for="t1">Yes</label> <br />
                <g:radio name="treatment" id="t2" value="No" checked="${generalMedicalHistoryInstance?.treatment?.toString() =='No'}"/>&nbsp;<label for="t2">No</label><br />
                <g:radio name="treatment" id="t3" value="Unknown" checked="${generalMedicalHistoryInstance?.treatment?.toString() =='Unknown'}"/>&nbsp;<label for="t3">Unknown</label>   
              </div>
            </td>
          </tr>


          <g:if test="${show45VersionUpdates}">
            <tr class="prop">
              <td valign="top" class="name">
                <label for="source"><g:message code="generalMedicalHistory.source.label" default="History Source" /></label>
              <td valign="top" class="value ${hasErrors(bean: generalMedicalHistoryInstance, field: 'source', 'errors')}">
            <g:select name="source" from="${['Medical Record', 'Family Report', 'Medical Record and Family Report']}" value="${generalMedicalHistoryInstance?.source ?:defaultSource}" noSelection="['': 'Select One']"/>
            </td>
            </tr>
          </g:if>
          <g:else>
          <tr class="prop">
            <td valign="top" class="name">
              <label for="medicalRecord"><g:message code="generalMedicalHistory.medicalRecord.label" default="Medical Record Documentation" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: generalMedicalHistoryInstance, field: 'medicalRecord', 'errors')}">
              <div>
                <g:radio name="medicalRecord" id="r1"  value="Yes" checked="${generalMedicalHistoryInstance?.medicalRecord?.toString() =='Yes'}"/>&nbsp;<label for="r1">Yes</label><br />
                <g:radio name="medicalRecord" id="r2" value="No" checked="${generalMedicalHistoryInstance?.medicalRecord?.toString() =='No'}"/>&nbsp;<label for="r2">No</label> <br />
                <g:radio name="medicalRecord" id="r3" value="Unknown" checked="${generalMedicalHistoryInstance?.medicalRecord?.toString() =='Unknown'}"/>&nbsp;<label for="r3">Unknown</label>   
              </div>
            </td>
          </tr>
          </g:else>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="Save" id="sub"/></span>
        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
      </div>
    </g:form>

  </div>
</body>
</html>
