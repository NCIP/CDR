<%@page defaultCodec="none" %>
<%@ page import="nci.obbr.cahub.forms.gtex.crf.CancerHistory" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="cancerhistory edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'cancerHistory.label', default: 'CancerHistory')}" />
  <script type="text/javascript" src="${resource(dir:'js',file:'cancerhistory.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  <script>
    $(document).ready(function(){
        $("#sub").click(function(){
           <g:if test="${!show50VersionUpdates}">
            var site = document.getElementById("primaryTumorSite").value;
            if(site==null ||site.length==0){
                alert("The primary Cancer Type is required");
                document.getElementById("primaryTumorSite").focus();
                return false;
            }
            </g:if>
        });    
        $("#t4").change(function(){ 
            if(document.getElementById("t4").checked){
                $("#ot").show();
            } else {
                document.getElementById("ot").style.display = "none";
                document.getElementById("otherTreatment").value = "";
            }
         });
     });
     
     var pct_srcDef_str=""
     <g:if test="${cancerHistoryInstance?.primaryTumorSiteCvocab?.srcDef}">
      pct_srcDef_str ="${cancerHistoryInstance?.primaryTumorSiteCvocab?.srcDef.replaceAll("\"", "'")}"
    </g:if>
  </script>
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
    <h1>Cancer History</h1>
    <div class="message">
      Instruction:
      <ul>
        <li>If "Month/Year of first diagnosis" is unknown, enter 01/1900</li>
        <li>If "Date of last radiation or chemotherapy treatment if applicable" is unknown, enter 01/1900</li>
      </ul>
    </div>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>


    <g:hasErrors bean="${cancerHistoryInstance}">
      <div class="errors">
        <g:renderErrors bean="${cancerHistoryInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" autocomplete="off">
      <input type='hidden' name="formid" value ="${params.formid}"/>
      <input type='hidden' name="medicalHistory.id" value="${cancerHistoryInstance?.medicalHistory?.id}"  />
      <g:hiddenField name="id" value="${cancerHistoryInstance?.id}" />
      <g:hiddenField name="version" value="${cancerHistoryInstance?.version}" />
      <div class="dialog">
        <table >
          <tbody>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="primaryTumorSite"><g:message code="cancerHistory.primaryTumorSite.label" default="Primary Cancer Type" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'primaryTumorSite', 'errors')}">
                 <g:if test="${!show50VersionUpdates}">
          <g:textField name="primaryTumorSite" value="${cancerHistoryInstance?.primaryTumorSite}" /> 
                 </g:if>
          <g:else>
           <div id="primaryTumorSite-dialog" class="cod-dialog hide" title="Primary Tumor Site">
           <p>Type in at least three characters to filter through results.</p>
           <input id="primaryTumorSite-pts" class="cod-user-input-srch"  />
           <div id="primaryTumorSite-pts-results-container" class="vocab-cod-results-container">
              <h3>Selected Result:</h3>
              <div id="primaryTumorSite-pts-results" class="allow-exceed-limit cod-user-input-srch" type="text"></div>
              <div class="button ui-corner-all clearfix">
                 <input type="submit" id="primaryTumorSite-pts-submit" value="Confirm" class="left button ui-button ui-widget ui-state-default ui-corner-all" />
                <input type="submit" id="primaryTumorSite-pts-cancel" value="Cancel" class="left button ui-button ui-widget ui-state-default ui-corner-all"/>
              </div>
           </div>
           </div>
           <a title="Select from list in Vocab" id="primaryTumorSite-zoomin" class="ca-ui-zoomin ca-tooltip">
              <span class="ui-icon-zoomin" id="pts-label">
                <g:if test="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabUserSelection}">${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabUserSelection}</g:if>
                <g:else>Choose...</g:else>
              </span>
           </a> 
              <input type="hidden" value="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabId}" id="pts-id" name="ptsId"/>
              <input type="hidden" value="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabType}" id="pts-typ" name="ptsTyp"/>
              <input type="hidden" value="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabUserSelection}" id="pts-pct" name="ptsPct"/>
              <input type="hidden" value='${cancerHistoryInstance?.primaryTumorSiteCvocab?.cuiList?.join(",")}' id="pts-cui" name="ptsCui"/>
              <input type="hidden" value="${cancerHistoryInstance?.primaryTumorSiteCvocab?.icdCd}" id="pts-ICDcd" name="ptsIcdCd"/>
              <input type="hidden" value="${cancerHistoryInstance?.primaryTumorSiteCvocab?.cVocabVer}" id="pts-cvocabVer" name="ptsVer"/>
              <input type="hidden" value="${cancerHistoryInstance?.primaryTumorSiteCvocab?.pdqCd}" id="pts-PDQcd" name="ptsPdqcd"/>
              <input type="hidden" value="" id="pts-srcDef" name="ptsSrcDef"/>
          </g:else>
              
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="monthYearOfFirstDiagnosis"><g:message code="cancerHistory.monthYearOfFirstDiagnosis.label" default="Month/Year of first diagnosis" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'monthYearOfFirstDiagnosis', 'errors')}">
          <g:datePicker name="monthYearOfFirstDiagnosis" precision="month" value="${cancerHistoryInstance?.monthYearOfFirstDiagnosis}" default="none" noSelection="['': '']" years="${1900..2099}" />
          </td>
          </tr>

          <g:if test="${show45VersionUpdates}">
            <tr class="prop">
              <td valign="top" class="name">
                <label for="source"><g:message code="cancerHistory.source.label" default="History Source" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'source', 'errors')}">
            <g:select name="source" from="${['Medical Record', 'Family Report', 'Medical Record and Family Report']}" value="${cancerHistoryInstance?.source?:defaultSource}" noSelection="['': 'Select One']"/>
            </td>
            </tr>
          </g:if>


          <tr class="prop">
            <td valign="top" class="name">
              <label for="treatments"><g:message code="cancerHistory.treatments.label" default="History of any treatments" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'treatments', 'errors')}">
              <div>
                <g:checkBox name="treatmentSurgery" id="t1" value="${cancerHistoryInstance?.treatmentSurgery}" />&nbsp;<label for="t1">Surgery</label><br/>
                <g:checkBox name="treatmentRadiation" id="t2" value="${cancerHistoryInstance?.treatmentRadiation}" />&nbsp;<label for="t2">Radiation</label><br/>
                <g:checkBox name="treatmentChemotherapy" id="t3" value="${cancerHistoryInstance?.treatmentChemotherapy}" />&nbsp;<label for="t3">Chemotherapy</label><br/>
                <g:checkBox name="treatmentOther" id="t4" value="${cancerHistoryInstance?.treatmentOther}" />&nbsp;<label for="t4">Other</label><br/>
                <g:checkBox name="treatmentNo" id="t5" value="${cancerHistoryInstance?.treatmentNo}" />&nbsp;<label for="t5">None</label><br/>
                <g:checkBox name="treatmentUnknown" id="t6" value="${cancerHistoryInstance?.treatmentUnknown}" />&nbsp;<label for="t6">Unknown</label><br/>
              </div>
            </td>
          </tr>
          <g:if test="${cancerHistoryInstance?.treatmentOther}">
            <tr class="prop"  id="ot" >
              <td valign="top" class="name">
                <label for="otherTreatment"><g:message code="cancerHistory.otherTreatment.label" default="If Other, specify:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'otherTreatment', 'errors')}">
            <g:textField name="otherTreatment" value="${cancerHistoryInstance?.otherTreatment}" />
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop"  id="ot" style="display:none">
              <td valign="top" class="name">
                <label for="otherTreatment"><g:message code="cancerHistory.otherTreatment.label" default="If Other, specify:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'otherTreatment', 'errors')}">
            <g:textField name="otherTreatment" value="${cancerHistoryInstance?.otherTreatment}" />
            </td>
            </tr>
          </g:else>
          <tr class="prop">
            <td valign="top" class="name">
              <label for="monthYearOfLastTreatment"><g:message code="cancerHistory.monthYearOfLastTreatment.label" default="Date of last radiation or chemotherapy treatment" /></label>
            </td>
            <td valign="top"  class="value ${hasErrors(bean: cancerHistoryInstance, field: 'monthYearOfLastTreatment', 'errors')}">
          <g:datePicker name="monthYearOfLastTreatment" precision="month" value="${cancerHistoryInstance?.monthYearOfLastTreatment}" default="none" noSelection="['': '']" years="${1900..2099}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="medicalRecordExist"><g:message code="cancerHistory.medicalRecordExist.label" default="" /></label>
            </td>
            <td valign="top"   class="value ${hasErrors(bean: cancerHistoryInstance, field: 'medicalRecordExist', 'errors')}">

              <div>
                <g:radio name="medicalRecordExist" id="r1" value="Yes" checked="${cancerHistoryInstance?.medicalRecordExist?.toString() =='Yes'}"/>&nbsp;<label for="r1">Yes</label>&nbsp;&nbsp;&nbsp;
                <g:radio name="medicalRecordExist" id="r2" value="No" checked="${cancerHistoryInstance?.medicalRecordExist?.toString() =='No'}"/>&nbsp;<label for="r2">No</label>&nbsp;&nbsp;&nbsp;
                <g:radio name="medicalRecordExist" id="r3" value="Unknown" checked="${cancerHistoryInstance?.medicalRecordExist?.toString() =='Unknown'}"/>&nbsp;<label for="r3">Unknown</label>
              </div>
            </td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="Save" id="sub" /></span>
        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
      </div>
    </g:form>


  </div>
</body>
</html>


