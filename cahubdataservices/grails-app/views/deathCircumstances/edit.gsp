<%@page defaultCodec="none" %>
<%@ page import="nci.obbr.cahub.forms.gtex.crf.DeathCircumstances" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="deathcircums edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'deathCircumstances.label', default: 'DeathCircumstances')}" />
  <script type="text/javascript" src="${resource(dir:'js',file:'deathcircumstances.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  <g:javascript>
    var immediateCause_cod_srcDef_str =""
    <g:if test="${deathCircumstancesInstance?.immCodCvocab?.srcDef}">
      immediateCause_cod_srcDef_str ="${deathCircumstancesInstance?.immCodCvocab?.srcDef.replaceAll("\"", "'")}"
    </g:if>
    
    var firstCause_cod_srcDef_str = ""
     <g:if test="${deathCircumstancesInstance?.firstCodCvocab?.srcDef}">
      firstCause_cod_srcDef_str ="${deathCircumstancesInstance?.firstCodCvocab?.srcDef.replaceAll("\"", "'")}"
    </g:if>
    
    var lastCause_cod_srcDef_str=""
    <g:if test="${deathCircumstancesInstance?.lastCodCvocab?.srcDef}">
      lastCause_cod_srcDef_str ="${deathCircumstancesInstance?.lastCodCvocab?.srcDef.replaceAll("\"", "'")}"
    </g:if>
    
    var deathCertificateCause_cod_srcDef_str=""
    <g:if test="${deathCircumstancesInstance?.deathCertCauseVocab?.srcDef}">
      deathCertificateCause_cod_srcDef_str ="${deathCircumstancesInstance?.deathCertCauseVocab?.srcDef.replaceAll("\"", "'")}"
    </g:if>
    
    var medicalCondition_cod_srcDef_str=""
    var medicationName_rx_genName_str=""
  </g:javascript>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link class="list" controller="caseReportForm" action="show" id="${params.formid}">Case Report Form Menu</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1>Death Circumstances</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${deathCircumstancesInstance}">
      <div class="errors">
        <g:renderErrors bean="${deathCircumstancesInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" autocomplete="off">
      <input type="hidden" name="formid" value="${params.formid}"/>
      <g:hiddenField name="id" value="${deathCircumstancesInstance?.id}" />
      <g:hiddenField name="version" value="${deathCircumstancesInstance?.version}" />
      <div class="dialog">
        <table>
          <tbody class="tdwrap">
            <tr class="prop" >
              <td class="name" >
                <label for="deathCertificateAvailable"><g:message code="deathCircumstances.deathCertificateAvailable.label" default="Is death certificate available" /></label>
              </td>
              <td  class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'deathCertificateAvailable', 'errors')}">
                <div>
          <g:radio name="deathCertificateAvailable" id="d1" value="Yes" checked="${deathCircumstancesInstance?.deathCertificateAvailable?.toString() =='Yes'}"/>&nbsp;<label for="d1">Yes</label>&nbsp;&nbsp;&nbsp;
          <g:radio name="deathCertificateAvailable" id="d2" value="No" checked="${deathCircumstancesInstance?.deathCertificateAvailable?.toString() =='No'}"/>&nbsp;<label for="d2">No</label>&nbsp;&nbsp;&nbsp;
          <g:radio name="deathCertificateAvailable" id="d3" value="Unknown" checked="${deathCircumstancesInstance?.deathCertificateAvailable?.toString() =='Unknown'}"/>&nbsp;<label for="d3">Unknown</label>
                </div>
          </td>
          </tr>

          <tr class="prop">
            <td class="name">
              <label for="dateTimePronouncedDead"><g:message code="deathCircumstances.dateTimePronouncedDead.label" default="Date and time pronounced dead" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'dateTimePronouncedDead', 'errors')}">
          <g:jqDateTimePicker name="dateTimePronouncedDead" value="${deathCircumstancesInstance?.dateTimePronouncedDead}" />
          </td>
          </tr>
          <tr class="prop">
            <td class="name" colspan='2'>Capture one of the next two:</td>
          </tr>
          <tr class="prop" bgcolor="#FFFFCC" >
            <td class="name" style="border-top: 1px solid #ddd;border-left: 1px solid #ddd;border-bottom: 1px solid #ddd;">
              <label for="dateTimeActualDeath"><g:message code="deathCircumstances.dateTimeActualDeath.label" default="Date and time of actual (witnessed) death " /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'dateTimeActualDeath', 'errors')}" style="border-top: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom: 1px solid #ddd;">
          <g:jqDateTimePicker name="dateTimeActualDeath" value="${deathCircumstancesInstance?.dateTimeActualDeath}" />
          </td>
          </tr>
          <tr class="prop">
            <td class="name" colspan='2'>Or</td>
          </tr>
          <tr class="prop" bgcolor="#FFFFCC">
            <td class="name" style="border-top: 1px solid #ddd;border-left: 1px solid #ddd;">
              <label for="dateTimePresumedDeath"><g:message code="deathCircumstances.dateTimePresumedDeath.label" default="Or Date and time of presumed death" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'dateTimePresumedDeath', 'errors')}" style="border-top: 1px solid #ddd;border-right: 1px solid #ddd;">
          <g:jqDateTimePicker name="dateTimePresumedDeath" value="${deathCircumstancesInstance?.dateTimePresumedDeath}" />
          </td>
          </tr>
          <tr class="prop" bgcolor="#FFFFCC">
            <td class="name" colspan='2' style="border-right: 1px solid #ddd;border-left: 1px solid #ddd;">And</td>
          </tr>

          <tr class="prop" bgcolor="#FFFFCC">
            <td class="name" style="border-bottom: 1px solid #ddd;border-left: 1px solid #ddd;">
              <label for="dateTimeLastSeenAlive"><g:message code="deathCircumstances.dateTimeLastSeenAlive.label" default="Date and time last seen alive" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'dateTimeLastSeenAlive', 'errors')}" style="border-bottom: 1px solid #ddd;border-right: 1px solid #ddd;">
          <g:jqDateTimePicker name="dateTimeLastSeenAlive" value="${deathCircumstancesInstance?.dateTimeLastSeenAlive}" />
          </td>
          </tr>
          <tr class="prop">
            <td class="name">
              <label for="placeOfDeath"><g:message code="deathCircumstances.placeOfDeath.label" default="Place of death" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'placeOfDeath', 'errors')}">
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl1" value="Hospital inpatient" checked="${deathCircumstancesInstance?.placeOfDeath =='Hospital inpatient'}"/>&nbsp;<label for="pl1">Hospital inpatient</label><br />
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl2" value="Emergency room" checked="${deathCircumstancesInstance?.placeOfDeath =='Emergency room'}"/>&nbsp;<label for="pl2">Emergency room</label> <br />
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl3" value="Outpatient" checked="${deathCircumstancesInstance?.placeOfDeath =='Outpatient'}"/>&nbsp;<label for="pl3">Outpatient</label> <br />
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl4" value="Hospice" checked="${deathCircumstancesInstance?.placeOfDeath =='Hospice'}"/>&nbsp;<label for="pl4">Hospice</label> <br />
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl5" value="Nursing home/Long-term care facility" checked="${deathCircumstancesInstance?.placeOfDeath =='Nursing home/Long-term care facility'}"/>&nbsp;<label for="pl5">Nursing home/Long-term care facility</label> <br />                              
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl6" value="Decedent's home" checked='${deathCircumstancesInstance?.placeOfDeath =="Decedent's home"}'/>&nbsp;<label for="pl6">Decedent's home</label> <br />
          <g:radio name="placeOfDeath"  class="placeOfDeath"  id="pl7" value="Dead on arrival at hospital" checked="${deathCircumstancesInstance?.placeOfDeath =='Dead on arrival at hospital'}"/>&nbsp;<label for="pl7">Dead on arrival at hospital</label> <br />
          <g:radio name="placeOfDeath"  id="pl8" value="Other" checked="${deathCircumstancesInstance?.placeOfDeath =='Other'}"/>&nbsp;<label for="pl8">Other</label> <br />
          </td>
          </tr>
          <g:if test="${deathCircumstancesInstance?.placeOfDeath=='Other'}">
            <tr class="prop" id="opod" >
              <td class="name">
                <label for="otherPlaceOfDeath"><g:message code="deathCircumstances.otherPlaceOfDeath.label" default="If other, specify:" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherPlaceOfDeath', 'errors')}">
            <g:textField name="otherPlaceOfDeath" value="${deathCircumstancesInstance?.otherPlaceOfDeath}" />
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="opod" style="display:none">
              <td class="name">
                <label for="otherPlaceOfDeath"><g:message code="deathCircumstances.otherPlaceOfDeath.label" default="If other, specify:" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherPlaceOfDeath', 'errors')}">
            <g:textField name="otherPlaceOfDeath" value="${deathCircumstancesInstance?.otherPlaceOfDeath}" />
            </td>
            </tr>
          </g:else>
          <tr class="prop">
            <td class="name">
              <label for="personDeterminedDeath"><g:message code="deathCircumstances.personDeterminedDeath.label" default="" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'personDeterminedDeath', 'errors')}">  
          <g:radio name="personDeterminedDeath"  class="personDeterminedDeath"  id="pe1" value="Physician" checked="${deathCircumstancesInstance?.personDeterminedDeath =='Physician'}"/>&nbsp;<label for="pe1">Physician</label><br />
          <g:radio name="personDeterminedDeath"  class="personDeterminedDeath"  id="pe2" value="Coroner/Medical Examiner (ME)" checked="${deathCircumstancesInstance?.personDeterminedDeath =='Coroner/Medical Examiner (ME)'}"/>&nbsp;<label for="pe2">Coroner/Medical Examiner (ME)</label><br />
          <g:radio name="personDeterminedDeath"  id="pe3" value="Other" checked="${deathCircumstancesInstance?.personDeterminedDeath =='Other'}"/>&nbsp;<label for="pe3">Other</label><br />
          </td>
          </tr>
          <g:if test="${deathCircumstancesInstance?.personDeterminedDeath=='Other'}">
            <tr class="prop" id="op" >
              <td class="name">
                <label for="otherPersonDeterminedDeath"><g:message code="deathCircumstances.otherPersonDeterminedDeath.label" default="If other, specify:" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherPersonDeterminedDeath', 'errors')}">
            <g:textField name="otherPersonDeterminedDeath" value="${deathCircumstancesInstance?.otherPersonDeterminedDeath}" />
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="op" style="display:none">
              <td class="name">
                <label for="otherPersonDeterminedDeath"><g:message code="deathCircumstances.otherPersonDeterminedDeath.label" default="If other, specify:" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherPersonDeterminedDeath', 'errors')}">
            <g:textField name="otherPersonDeterminedDeath" value="${deathCircumstancesInstance?.otherPersonDeterminedDeath}" />
            </td>
            </tr>
          </g:else>

          <tr class="prop">
            <td class="name">
              <label for="mannerOfDeath"><g:message code="deathCircumstances.mannerOfDeath.label" default="Manner of death" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'mannerOfDeath', 'errors')}">
          <g:radio name="mannerOfDeath" id="m1"   value="Natural" checked="${deathCircumstancesInstance?.mannerOfDeath =='Natural'}"/>&nbsp;<label for="m1">Natural</label><br />
          <g:radio name="mannerOfDeath" id="m2"  value="Accident" checked="${deathCircumstancesInstance?.mannerOfDeath =='Accident'}"/>&nbsp;<label for="m2">Accident</label><br />
          <g:radio name="mannerOfDeath" id="m3"  value="Suicide" checked="${deathCircumstancesInstance?.mannerOfDeath =='Suicide'}"/>&nbsp;<label for="m3">Suicide</label><br />
          <g:radio name="mannerOfDeath"  id="m4" value="Homicide" checked="${deathCircumstancesInstance?.mannerOfDeath =='Homicide'}"/>&nbsp;<label for="m4">Homicide</label><br />
          <g:radio name="mannerOfDeath"  id="m5" value="Pending" checked="${deathCircumstancesInstance?.mannerOfDeath =='Pending'}"/>&nbsp;<label for="m5">Pending</label><br />
          <g:radio name="mannerOfDeath"  id="m6" value="Undetermined" checked="${deathCircumstancesInstance?.mannerOfDeath =='Undetermined'}"/>&nbsp;<label for="m6">Undetermined</label><br />
          </td>
          </tr>                        
          <tr class="prop">
            <td class="name">
              <label for="hardyScale"><g:message code="deathCircumstances.hardyScale.label" default="Death Classification: 4-point Hardy Scale" /></label>
            </td>

            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'hardyScale', 'errors')}">
              <div>
                <g:radio name="hardyScale" id="hs1" value="1" checked="${deathCircumstancesInstance?.hardyScale =='1'}"/>
                <label for="hs1">1) Violent and fast death</label><br />
                <g:radio name="hardyScale" id="hs2" value="2" checked="${deathCircumstancesInstance?.hardyScale =='2'}"/>
                <label for="hs2">2) Fast death of natural causes</label><br />
                <g:radio name="hardyScale"  id="hs3" value="3" checked="${deathCircumstancesInstance?.hardyScale =='3'}"/>
                <label for="hs3">3) Intermediate death</label><br />
                <g:radio name="hardyScale"  id="hs4" value="4" checked="${deathCircumstancesInstance?.hardyScale =='4'}"/>
                <label for="hs4">4) Slow death</label><br />
                <g:radio name="hardyScale" id="hs0" value="0" checked="${deathCircumstancesInstance?.hardyScale =='0'}"/>
                <label for="hs0">0) Ventilator case</label><br />  
              </div>
            </td>
          </tr>  
          <tr class="prop">
            <td class="name">
              <label for="autopsyPerformed"><g:message code="deathCircumstances.autopsyPerformed.label" default="Did Coroner / ME Perform an Autopsy?" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'autopsyPerformed', 'errors')}">

          <g:radio name="autopsyPerformed" id="a1" value="Yes" checked="${deathCircumstancesInstance?.autopsyPerformed?.toString() =='Yes'}"/>&nbsp;<label for="a1">Yes</label>&nbsp;&nbsp;&nbsp;
          <g:radio name="autopsyPerformed" id="a2" value="No" checked="${deathCircumstancesInstance?.autopsyPerformed?.toString() =='No'}"/>&nbsp;<label for="a2">No</label>&nbsp;&nbsp;&nbsp;
          <g:radio name="autopsyPerformed" id="a3" value="Unknown" checked="${deathCircumstancesInstance?.autopsyPerformed?.toString() =='Unknown'}"/>&nbsp;<label for="a3">Unknown</label>
          </td>
          </tr>
          <tr class="prop">
            <td class="name">
              <label for="onVentilator"><g:message code="deathCircumstances.onVentilator.label" default="" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'onVentilator', 'errors')}">      
          <g:radio name="onVentilator" id="v1" value="Yes" checked="${deathCircumstancesInstance?.onVentilator?.toString() =='Yes'}"/>&nbsp;<label for="v1">Yes</label>&nbsp;&nbsp;&nbsp;
          <g:radio name="onVentilator" class="onVentilator" id="v2" value="No" checked="${deathCircumstancesInstance?.onVentilator?.toString() =='No'}"/>&nbsp;<label for="v2">No</label>&nbsp;&nbsp;&nbsp;
          <g:radio name="onVentilator" class="onVentilator" id="v3" value="Unknown" checked="${deathCircumstancesInstance?.onVentilator?.toString() =='Unknown'}"/>&nbsp;<label for="v3">Unknown</label>
          </td>
          </tr>

          <g:if test="${deathCircumstancesInstance?.onVentilator?.name()=='Yes'}">
            <tr class="prop" id="du" >
              <td class="name">
                <label for="ventilatorDuration"><g:message code="deathCircumstances.ventilatorDuration.label" default="If yes, specify duration" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'ventilatorDuration', 'errors')}">
            <g:textField name="ventilatorDuration" value="${deathCircumstancesInstance?.ventilatorDuration}" /> (hours)
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="du" style="display:none">
              <td class="name">
                <label for="ventilatorDuration"><g:message code="deathCircumstances.ventilatorDuration.label" default="If yes, specify duration" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'ventilatorDuration', 'errors')}">
            <g:textField name="ventilatorDuration" value="${deathCircumstancesInstance?.ventilatorDuration}" /> (hours)
            </td>
            </tr>

          </g:else>
          <g:if test="${version45 == true}">
            <tr class="prop">
              <td class="name">
                <label for="deathCertificateCause"><g:message code="deathCircumstances.deathCertificateCause.label" default="Death Certificate Cause of Death" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'deathCertificateCause', 'errors')}">
                <div id="deathCertificateCause" class="select-cod-by-vocab with-manual-override"></div>
                <input type="hidden" value="${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabId}" id="deathCertificateCause-cod-id" name="deathCertificateCauseCodId"/>
                <input type="hidden" value="${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabType}" id="deathCertificateCause-cod-typ" name="deathCertificateCauseCodTyp"/>
                <input type="hidden" value="${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabUserSelection}" id="deathCertificateCause-cod-cod" name="deathCertificateCauseCodCod"/>
                <input type="hidden" value='${deathCircumstancesInstance?.deathCertCauseVocab?.cuiList?.join(",")}' id="deathCertificateCause-cod-cui" name="deathCertificateCauseCodCui"/>
                <input type="hidden" value="${deathCircumstancesInstance?.deathCertCauseVocab?.icdCd}" id="deathCertificateCause-cod-ICDcd" name="deathCertificateCauseCodIcdCd"/>
                <input type="hidden" value="${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabVer}" id="deathCertificateCause-cod-cvocabVer" name="deathCertificateCauseCodCvocabVer"/>
                <input type="hidden" value="" id="deathCertificateCause-cod-srcDef" name="deathCertificateCauseCodSrcDef"/>
                <input type="text" id="deathCertificateCause_manual" name="deathCertificateCause" value="${deathCircumstancesInstance?.deathCertificateCause}" class="depends-on cod_manual_override" data-id="deathCertificateCause_manual_radio"/>
              </td>
            </tr>
          </g:if> 
          <tr class="prop">
            <td class="name">
              <label for="immediateCause"><g:message code="deathCircumstances.immediateCause.label" default="Immediate Cause of Death" /></label>
            </td> 
          <g:if test="${version45 == true}">
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'immediateCause', 'errors')}">
              <div id="immediateCause" class="select-cod-by-vocab"></div>
              <input type="hidden" value="${deathCircumstancesInstance?.immCodCvocab?.cVocabId}" id="immediateCause-cod-id" name="immediateCauseCodId"/>
              <input type="hidden" value="${deathCircumstancesInstance?.immCodCvocab?.cVocabType}" id="immediateCause-cod-typ" name="immediateCauseCodTyp"/>
              <input type="hidden" value="${deathCircumstancesInstance?.immCodCvocab?.cVocabUserSelection}" id="immediateCause-cod-cod" name="immediateCauseCodCod"/>
              <input type="hidden" value='${deathCircumstancesInstance?.immCodCvocab?.cuiList?.join(",")}' id="immediateCause-cod-cui" name="immediateCauseCodCui"/>
              <input type="hidden" value="${deathCircumstancesInstance?.immCodCvocab?.icdCd}" id="immediateCause-cod-ICDcd" name="immediateCauseCodIcdCd"/>
              <input type="hidden" value="${deathCircumstancesInstance?.immCodCvocab?.cVocabVer}" id="immediateCause-cod-cvocabVer" name="immediateCauseCodCvocabVer"/>
              <input type="hidden" value="" id="immediateCause-cod-srcDef" name="immediateCauseCodSrcDef"/>
            </td>
          </g:if> 
          <g:else>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'immediateCause', 'errors')}">
            <g:select name="immediateCause" from="${nci.obbr.cahub.staticmembers.DeathCause.list(sort:'name')}" optionKey="name" value="${deathCircumstancesInstance?.immediateCause}"  noSelection="['': '']" />
            </td>
          </g:else>
          </tr>
          <g:if test="${deathCircumstancesInstance?.immediateCause =='Other'}">
            <tr class="prop" id="oi" >
              <td class="name">
                <label for="otherImmediate"><g:message code="deathCircumstances.otherImmediate.label" default="if other specify" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherImmediate', 'errors')}">
            <g:textField name="otherImmediate" value="${deathCircumstancesInstance?.otherImmediate}" />
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="oi" style="display:none">
              <td class="name">
                <label for="otherImmediate"><g:message code="deathCircumstances.otherImmediate.label" default="if other specify" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherImmediate', 'errors')}">
            <g:textField name="otherImmediate" value="${deathCircumstancesInstance?.otherImmediate}" />
            </td>
            </tr>
          </g:else>

          <tr class="prop">
            <td class="name">
              <label for="immediateInterval"><g:message code="deathCircumstances.immediateInterval.label" default="Approximate Interval: Onset to Death" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'immediateInterval', 'errors')}">
          <g:textField name="immediateInterval" value="${deathCircumstancesInstance?.immediateInterval}" /> (hours)
          </td>
          </tr>

          <tr class="prop">
            <td class="name">
              <label for="firstCause"><g:message code="deathCircumstances.firstCause.label" default="First underlying Cause of death" /></label>
            </td>
          <g:if test="${version45 == true}">
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'firstCause', 'errors')}">
              <div id="firstCause" class="select-cod-by-vocab"></div>
              <input type="hidden" value="${deathCircumstancesInstance?.firstCodCvocab?.cVocabId}" id="firstCause-cod-id" name="firstCauseCodId"/>
              <input type="hidden" value="${deathCircumstancesInstance?.firstCodCvocab?.cVocabType}" id="firstCause-cod-typ" name="firstCauseCodTyp"/>
              <input type="hidden" value="${deathCircumstancesInstance?.firstCodCvocab?.cVocabUserSelection}" id="firstCause-cod-cod" name="firstCauseCodCod"/>
              <input type="hidden" value='${deathCircumstancesInstance?.firstCodCvocab?.cuiList?.join(",")}' id="firstCause-cod-cui" name="firstCauseCodCui"/>
              <input type="hidden" value="${deathCircumstancesInstance?.firstCodCvocab?.icdCd}" id="firstCause-cod-ICDcd" name="firstCauseCodIcdCd"/>
              <input type="hidden" value="${deathCircumstancesInstance?.firstCodCvocab?.cVocabVer}" id="firstCause-cod-cvocabVer" name="firstCauseCodCvocabVer"/>
              <input type="hidden" value="" id="firstCause-cod-srcDef" name="firstCauseCodSrcDef"/>
            </td>
          </g:if> 
          <g:else>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'firstCause', 'errors')}">
            <g:select name="firstCause" from="${nci.obbr.cahub.staticmembers.DeathCause.list(sort:'name')}" optionKey="name" value="${deathCircumstancesInstance?.firstCause}"  noSelection="['': '']" />
            </td>
          </g:else>
          </tr>

          <g:if test="${deathCircumstancesInstance?.firstCause=='Other'}">
            <tr class="prop" id="of" >
              <td class="name">
                <label for="otherFirstCause"><g:message code="deathCircumstances.otherFirstCause.label" default="if other specify" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherFirstCause', 'errors')}">
            <g:textField name="otherFirstCause" value="${deathCircumstancesInstance?.otherFirstCause}" />
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="of" style="display:none">
              <td class="name">
                <label for="otherFirstCause"><g:message code="deathCircumstances.otherFirstCause.label" default="if other specify" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherFirstCause', 'errors')}">
            <g:textField name="otherFirstCause" value="${deathCircumstancesInstance?.otherFirstCause}" />
            </td>
            </tr>
          </g:else>
          <tr class="prop">
            <td class="name">
              <label for="firstCauseInterval"><g:message code="deathCircumstances.firstCauseInterval.label" default="Approximate Interval: Onset to Death" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'firstCauseInterval', 'errors')}">
          <g:textField name="firstCauseInterval" value="${deathCircumstancesInstance?.firstCauseInterval}" /> (hours)
          </td>
          </tr>
          <tr class="prop">
            <td class="name">
              <label for="lastCause"><g:message code="deathCircumstances.lastCause.label" default="Last Underlying Cause of Death" /></label>
            </td>
          <g:if test="${version45 == true}">
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'lastCause', 'errors')}">
              <div id="lastCause" class="select-cod-by-vocab"></div>
              <input type="hidden" value="${deathCircumstancesInstance?.lastCodCvocab?.cVocabId}" id="lastCause-cod-id" name="lastCauseCodId"/>
              <input type="hidden" value="${deathCircumstancesInstance?.lastCodCvocab?.cVocabType}" id="lastCause-cod-typ" name="lastCauseCodTyp"/>
              <input type="hidden" value="${deathCircumstancesInstance?.lastCodCvocab?.cVocabUserSelection}" id="lastCause-cod-cod" name="lastCauseCodCod"/>
              <input type="hidden" value='${deathCircumstancesInstance?.lastCodCvocab?.cuiList?.join(",")}' id="lastCause-cod-cui" name="lastCauseCodCui"/>
              <input type="hidden" value="${deathCircumstancesInstance?.lastCodCvocab?.icdCd}" id="lastCause-cod-ICDcd" name="lastCauseCodIcdCd"/>
              <input type="hidden" value="${deathCircumstancesInstance?.lastCodCvocab?.cVocabVer}" id="lastCause-cod-cvocabVer" name="lastCauseCodCvocabVer"/>
              <input type="hidden" value="" id="lastCause-cod-srcDef" name="lastCauseCodSrcDef"/>
            </td>
          </g:if> 
          <g:else>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'lastCause', 'errors')}">
            <g:select name="lastCause" from="${nci.obbr.cahub.staticmembers.DeathCause.list(sort:'name')}" optionKey="name" value="${deathCircumstancesInstance?.lastCause}"  noSelection="['': '']" />
            </td>
          </g:else>
          </tr>
          <g:if test="${deathCircumstancesInstance?.lastCause=='Other'}">
            <tr class="prop" id="ol"  style="display:display">
              <td class="name">
                <label for="otherLastCause"><g:message code="deathCircumstances.otherLastCause.label" default="if other specify" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherLastCause', 'errors')}">
            <g:textField name="otherLastCause" value="${deathCircumstancesInstance?.otherLastCause}" />
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="ol"  style="display:none">
              <td class="name">
                <label for="otherLastCause"><g:message code="deathCircumstances.otherLastCause.label" default="if other specify" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'otherLastCause', 'errors')}">
            <g:textField name="otherLastCause" value="${deathCircumstancesInstance?.otherLastCause}" />
            </td>
            </tr>

          </g:else>
          <g:if test="${!version45}">
            <tr class="prop">
              <td class="name">
                <label for="lastCauseInterval"><g:message code="deathCircumstances.lastCauseInterval.label" default="Approximate Interval: Onset to Death" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'lastCauseInterval', 'errors')}">
            <g:textField name="lastCauseInterval" value="${deathCircumstancesInstance?.lastCauseInterval}" /> (hours)
            </td>
            </tr>
          </g:if>
          <tr class="prop">
            <td class="name">
              <label for="wasRefrigerated"><g:message code="deathCircumstances.wasRefrigerated.label" default="Was the body refrigerated at any time before procurement?" /></label>
            </td>
            <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'wasRefrigerated', 'errors')}">
              <div>
                <g:radio name="wasRefrigerated" id="re1" value="Yes" checked="${deathCircumstancesInstance?.wasRefrigerated?.toString() =='Yes'}"/>&nbsp;<label for="re1">Yes</label>&nbsp;&nbsp;&nbsp;
                <g:radio name="wasRefrigerated" class="wasRefrigerated" id="re2" value="No" checked="${deathCircumstancesInstance?.wasRefrigerated?.toString() =='No'}"/>&nbsp;<label for="re2">No</label>&nbsp;&nbsp;&nbsp;
                <g:radio name="wasRefrigerated" class="wasRefrigerated" id="re3" value="Unknown" checked="${deathCircumstancesInstance?.wasRefrigerated?.toString() =='Unknown'}"/>&nbsp;<label for="re3">Unknown</label>
              </div>
            </td>
          </tr>
          <g:if test="${deathCircumstancesInstance?.wasRefrigerated?.name()=='Yes'}">
            <tr class="prop" id="eh" style="display:display">
              <td class="name">
                <label for="estimatedHours"><g:message code="deathCircumstances.estimatedHours.label" default="If yes, estimate number of hours in refrigeration" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'estimatedHours', 'errors')}">
            <g:textField name="estimatedHours" value="${deathCircumstancesInstance?.estimatedHours}" /> (hours)
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="eh" style="display:none">
              <td class="name">
                <label for="estimatedHours"><g:message code="deathCircumstances.estimatedHours.label" default="If yes, estimate number of hours in refrigeration" /></label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'estimatedHours', 'errors')}">
            <g:textField name="estimatedHours" value="${deathCircumstancesInstance?.estimatedHours}" /> (hours)
            </td>
          </g:else>
          <g:if test="${parentBssCode == 'NDRI' && caseCollectionTypeCode == 'OPO'}">
            <tr class="prop">
              <td class="name">
                <label for="opoType">Organ Donor (OPO) Type</label>
              </td>
              <td class="value ${hasErrors(bean: deathCircumstancesInstance, field: 'opoType', 'errors')}">
                <div>    
                  <g:radio name="opoType" id="opo1" value="BD" checked="${deathCircumstancesInstance?.opoType =='BD'}"/>&nbsp;<label for="opo1">Brain Death (BD)</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="opoType" id="opo2" value="DCD" checked="${deathCircumstancesInstance?.opoType =='DCD'}"/>&nbsp;<label for="opo2">Donation After Cardiac Death (DCD)</label>
                </div>
              </td>
            </tr> 
          </g:if>
          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="Save" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
