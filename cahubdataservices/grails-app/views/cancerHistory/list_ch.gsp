<%@page defaultCodec="none" %>
<%@ page import="nci.obbr.cahub.forms.gtex.crf.CancerHistory" %>
<g:set var="bodyclass" value="cancerhistory list create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'cancerHistory.label', default: 'CancerHistory')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
  <script type="text/javascript" src="${resource(dir:'js',file:'cancerhistory.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  <g:javascript>
    $(document).ready(function(){

    $("#sub").click(function(){
  
    <g:if test="${!show50VersionUpdates}">
    var site= document.getElementById("primaryTumorSite").value
     if(site==null ||site.length==0){
     alert("The primary cancer type is required")
     document.getElementById("primaryTumorSite").focus();
     return false;
     }
    </g:if>
    <g:else>
      var pct = document.getElementById("pts-pct").value
      if(pct==null || pct.length==0){
      
       alert("The primary cancer type is required")
       return false;
     }
    </g:else>
   
    

    
    });    

    $("#sub2").click(function(){

    document.getElementById("f").style.display = 'none';
    $("#a").show()

    return false;



    });    



    $("#a").click(function(){

    //document.getElementById("f").style.display = 'table-row';
    $("#f").show()
    $(this).hide()

    });


    $("#t4").change(function(){ 
    //alert("hiii")
    if(document.getElementById("t4").checked){
    //alert("check???")
    //document.getElementById("ot").style.display = 'table-row';
    $("#ot").show()
    //document.getElementById("c").style.display = 'none';
    }else{
    //  alert("not checked???")
    document.getElementById("ot").style.display = 'none';
    document.getElementById("otherTreatment").value = '';
    }

    });







    });

     var pct_srcDef_str=""
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
    <h1>Cancer History List</h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:each in="${cancerHistoryInstanceList}" status="i" var="cancerHistoryInstance">

      <g:hasErrors bean="${cancerHistoryInstance}">
        <div class="errors">
          <g:renderErrors bean="${cancerHistoryInstance}" as="list" />
        </div>
      </g:hasErrors>
    </g:each>

    <div class="list">
      <table >
        <thead>
          <tr>
            <th>Primary Cancer Type</th>
            <th>Month/Year of first diagnosis</th>
            
        <g:if test="${show45VersionUpdates}">
          <th>History Source</th>
        </g:if>
        
        <th>History of any treatments</th>
        <th>Date of last radiation or chemotherapy treatment if applicable</th>
        <th>Is there medical record documentation of this history of cancer and treatment</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${cancerHistoryInstanceList}" status="i" var="cancerHistoryInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">


            <td  class="value ${errorMap.get(cancerHistoryInstance.id)}"><div><g:link action="edit" id="${cancerHistoryInstance.id}" params="['id': cancerHistoryInstance.id, 'formid':params.formid]">${cancerHistoryInstance?.primaryTumorSite}</g:link></div></td>

            <td><g:formatDate format="MM/yyyy" date="${cancerHistoryInstance.monthYearOfFirstDiagnosis}" /></td>

          <g:if test="${show45VersionUpdates}"><td>${fieldValue(bean:cancerHistoryInstance, field: "source")}</td> </g:if>

          <g:if test="${cancerHistoryInstance.treatments == 'null'}">
            <td>&nbsp;</td>
          </g:if>
          <g:else>
            <td>${fieldValue(bean: cancerHistoryInstance, field: "treatments")}</td>
          </g:else>

          <td><g:formatDate format="MM/yyyy" date="${cancerHistoryInstance.monthYearOfLastTreatment}" /></td>

          <td>${fieldValue(bean: cancerHistoryInstance, field: "medicalRecordExist")}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <!--<div class="paginateButtons">
       <g:paginate total="${cancerHistoryInstanceTotal}" />
    </div>-->
    <div class="buttons">
      <span class="button"><input type="button" value="Add" id ="a" /></span>
    </div>

    <div id="f"  style="display:none">

      <div class="message">
        Instruction:
        <ul>
          <li>If "Month/Year of first diagnosis" is unknown, enter 01/1900</li>
          <li>If "Date of last radiation or chemotherapy treatment if applicable" is unknown, enter 01/1900</li>
        </ul>
      </div>
      <g:form action="save_ch" autocomplete="off">
        <input type='hidden' name="medicalHistory.id" value="${mid}"/>
        <input type='hidden' name="formid" value ="${params.formid}"/>
        <div class="dialog">
          <table >
            <tbody style="width:800px">

              <tr class="prop">
                <td valign="top" class="name">
                  <label for="primaryTumorSite"><g:message code="cancerHistory.primaryTumorSite.label" default="Primary Cancer Type" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'primaryTumorSite', 'errors')}">
                  <g:if test="${!show50VersionUpdates}">
            <g:textField name="primaryTumorSite" value="${cancerHistoryInstance?.primaryTumorSite}" />
                  </g:if>
            <g:else>
              <div id="primaryTumorSite-dialog" class="pts-dialog hide" title="Primary Tumor Site">
           <p>Type in at least three characters to filter through results.</p>
           <input id="primaryTumorSite-pts" class="cod-user-input-srch" />
           <div id="primaryTumorSite-pts-results-container" class="vocab-pts-results-container">
              <h3>Selected Result:</h3>
              <div id="primaryTumorSite-pts-results" class="vocab-cod-results"></div>
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
              <input type="hidden" value="" id="pts-id" name="ptsId"/>
              <input type="hidden" value="" id="pts-typ" name="ptsTyp"/>
              <input type="hidden" value="" id="pts-pct" name="ptsPct"/>
              <input type="hidden" value="" id="pts-cui" name="ptsCui"/>
              <input type="hidden" value="" id="pts-ICDcd" name="ptsIcdCd"/>
              <input type="hidden" value="" id="pts-cvocabVer" name="ptsVer"/>
              <input type="hidden" value="" id="pts-PDQcd" name="ptsPdqcd"/>
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
                <td valign="top" class="value ${hasErrors(bean: ccancerHistoryInstance, field: 'source', 'errors')}">
              <g:select name="source" from="${['Medical Record', 'Family Report', 'Medical Record and Family Report']}" value="${cancerHistoryInstance?.source?:defaultSource}" noSelection="['': 'Select One']"/>
              </td>
              </tr>
            </g:if>
            
            
            
            

            <tr class="prop">
              <td valign="top" class="name">
                <label for="treatments"><g:message code="cancerHistory.treatments.label" default="History of any treatments" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'treadtments', 'errors')}">


            <g:checkBox name="treatmentSurgery" id="t1" value="${cancerHistoryInstance?.treatmentSurgery}" />&nbsp;<label for="t1">Surgery</label><br/>
            <g:checkBox name="treatmentRadiation" id="t2" value="${cancerHistoryInstance?.treatmentRadiation}" />&nbsp;<label for="t2">Radiation</label><br/>
            <g:checkBox name="treatmentChemotherapy" id="t3" value="${cancerHistoryInstance?.treatmentChemotherapy}" />&nbsp;<label for="t3">Chemotherapy</label><br/>
            <g:checkBox name="treatmentOther" id="t4" value="${cancerHistoryInstance?.treatmentOther}" />&nbsp;<label for="t4">Other</label><br/>
            <g:checkBox name="treatmentNo" id="t5" value="${cancerHistoryInstance?.treatmentNo}" />&nbsp;<label for="t5">None</label><br/>
            <g:checkBox name="treatmentUnknown" id="t6" value="${cancerHistoryInstance?.treatmentUnknown}" />&nbsp;<label for="t6">Unknown:</label>

            </td>
            </tr>

            <tr class="prop"  id="ot" style="display:none">
              <td valign="top" class="name">
                <label for="otherTreatment"><g:message code="cancerHistory.otherTreatment.label" default="If Other, specify:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'otherTreatment', 'errors')}">
            <g:textField name="otherTreatment" value="${cancerHistoryInstance?.otherTreatment}" />
            </td>
            </tr>


            <tr class="prop">
              <td valign="top" class="name">
                <label for="monthYearOfLastTreatment"><g:message code="cancerHistory.monthYearOfLastTreatment.label" default="Date of last radiation or chemotherapy treatment" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'monthYearOfLastTreatment', 'errors')}">
            <g:datePicker name="monthYearOfLastTreatment" precision="month" value="${cancerHistoryInstance?.monthYearOfLastTreatment}" default="none" noSelection="['': '']" years="${1900..2099}"/>
            </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="medicalRecordExist"><g:message code="cancerHistory.medicalRecordExist.label" default="Is there medical record documentation of this history of cancer and treatment" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: cancerHistoryInstance, field: 'medicalRecordExist', 'errors')}">

            <g:radio name="medicalRecordExist" id="r1" value="Yes" checked="${cancerHistoryInstance?.medicalRecordExist?.toString() =='Yes'}"/>&nbsp;<label for="r1">Yes</label>&nbsp;&nbsp;&nbsp;
            <g:radio name="medicalRecordExist" id="r2" value="No" checked="${cancerHistoryInstance?.medicalRecordExist?.toString() =='No'}"/>&nbsp;<label for="r2">No</label>&nbsp;&nbsp;&nbsp;
            <g:radio name="medicalRecordExist" id="r3" value="Unknown" checked="${cancerHistoryInstance?.medicalRecordExist?.toString() =='Unknown'}"/>&nbsp;<label for="r3">Unknown</label>

            </td>
            </tr>



            </tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button"><g:submitButton name="create" class="save" value="Save" id="sub" /></span>
          <span class="button"><g:submitButton name="cancel" class="delete" value="Cancel" id="sub2" /></span>
        </div>
      </g:form>



    </div>

  </div>
</body>
</html>
