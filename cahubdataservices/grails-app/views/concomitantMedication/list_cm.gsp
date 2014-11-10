<%@page defaultCodec="none" %>
<%@ page import="nci.obbr.cahub.forms.gtex.crf.ConcomitantMedication" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="concomitantmed list_cm create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'concomitantMedication.label', default: 'ConcomitantMedication')}" />
  
  <script type="text/javascript" src="${resource(dir:'js',file:'concomitantmedication.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
  <title><g:message code="default.list.label" args="[entityName]" /></title>
  <g:javascript>
    var immediateCause_cod_srcDef_str =""
    var firstCause_cod_srcDef_str = ""
    var lastCause_cod_srcDef_str=""
    var deathCertificateCause_cod_srcDef_str=""
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
    <h1>Current Medications</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:each in="${concomitantMedicationInstanceList}" status="i" var="concomitantMedicationInstance">

      <g:hasErrors bean="${concomitantMedicationInstance}">
        <div class="errors">
          <g:renderErrors bean="${concomitantMedicationInstance}" as="list" />
        </div>
      </g:hasErrors>
    </g:each>
    <div class="list">
      <table>
        <thead>
          <tr>
            <th>Medication Name/Vitamins/Supplements</th>
        <g:if test="${!show45VersionUpdates}">
          <th>Amount</th>
          <th>Dosage Form/Unit</th>
          <th>Frequency</th>
          <th>Route</th>
        </g:if>
        <th>Date of Last Administration</th>
        <g:if test="${show45VersionUpdates}">
          <th>History Source</th>
        </g:if>


        </tr>
        </thead>
        <tbody>
        <g:each in="${concomitantMedicationInstanceList}" status="i" var="concomitantMedicationInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">


          <g:if test="${show45VersionUpdates}">
              <td class="value ${errorMap.get('medicationName'+ concomitantMedicationInstance.id)}"><div><g:link action="edit" id="${concomitantMedicationInstance.id}" params="['id': concomitantMedicationInstance.id, 'formid':params.formid]">${concomitantMedicationInstance?.medicationNameCvocab?.cVocabUserSelection}</g:link></div></td>
          </g:if>
          <g:else>
              <td class="value ${errorMap.get('medicationName'+ concomitantMedicationInstance.id)}"><div><g:link action="edit" id="${concomitantMedicationInstance.id}" params="['id': concomitantMedicationInstance.id, 'formid':params.formid]">${fieldValue(bean: concomitantMedicationInstance, field: "medicationName")}</g:link></div></td>            
          </g:else>
            

          <g:if test="${!show45VersionUpdates}">

            <td>${fieldValue(bean: concomitantMedicationInstance, field: "amount")}</td>
            <g:if test="${concomitantMedicationInstance.dosageUnit && (nci.obbr.cahub.staticmembers.DosageUnit.findByName(concomitantMedicationInstance.dosageUnit))?.description}">
              <td><span class="ca-tooltip-nobg" data-msg="${(nci.obbr.cahub.staticmembers.DosageUnit.findByName(concomitantMedicationInstance.dosageUnit))?.description}">${fieldValue(bean: concomitantMedicationInstance, field: "dosageUnit")}</span></td>
            </g:if>
            <g:else>
              <td>${fieldValue(bean: concomitantMedicationInstance, field: "dosageUnit")}</td>
            </g:else>
            <g:if test="${concomitantMedicationInstance.frequency && (nci.obbr.cahub.staticmembers.Frequency.findByName(concomitantMedicationInstance.frequency))?.description}">
              <td>
                <span class="ca-tooltip-nobg" data-msg="${(nci.obbr.cahub.staticmembers.Frequency.findByName(concomitantMedicationInstance.frequency))?.description}">${fieldValue(bean: concomitantMedicationInstance, field: "frequency")}</span>
              </td>
            </g:if>
            <g:else>
              <td>${fieldValue(bean: concomitantMedicationInstance, field: "frequency")}</td>
            </g:else>
            <g:if test="${concomitantMedicationInstance.route && (nci.obbr.cahub.staticmembers.Route.findByName(concomitantMedicationInstance.route))?.description}">
              <td>
                <span class="ca-tooltip-nobg" data-msg="${(nci.obbr.cahub.staticmembers.Route.findByName(concomitantMedicationInstance.route))?.description}">${fieldValue(bean: concomitantMedicationInstance, field: "route")}</span>
              </td>
            </g:if>
            <g:else>
              <td>${fieldValue(bean: concomitantMedicationInstance, field: "route")}</td>
            </g:else>



          </g:if>


          <td><g:formatDate format ="MM/dd/yyyy" date="${concomitantMedicationInstance.dateofLastAdministration}" /></td>

          <g:if test="${show45VersionUpdates}"><td>${fieldValue(bean: concomitantMedicationInstance, field: "source")}</td> </g:if>

          </tr>
        </g:each>
        </tbody>
      </table>


    </div>
    <!-- <div class="paginateButtons">
         <g:paginate total="${concomitantMedicationInstanceTotal}" />
     </div>-->
    <div class="buttons">
      <span class="button"><input type="button" value="Add" id ="a" /></span>
    </div>
    <div id="f" style="display:none">
      <g:if test="${show25VersionUpdates && !show45VersionUpdates}">
        <div class="message">Instruction: 
          <ul>
            <li>If "Amount" is unknown, enter -1</li>
            <li>If "Date of Last Administration" is unknown, choose "Unknown"</li>
          </ul>
        </div>
      </g:if> 
      <g:else>
        <div class="message">Instruction: 
          <ul>
            <li>If "Date of Last Administration" is unknown, choose "Unknown"</li>
          </ul>
        </div>
      </g:else> 
      <g:form action="save_cm" autocomplete="off" >
        <input type="hidden" name="caseReportForm.id" value="${params.formid}"/>
        <input type="hidden" name="formid" value="${params.formid}"/>
        <div class="dialog">
          <table>
            <tbody>

              <tr class="prop">
                <td valign="top" class="name">
                  <label for="medicationName"><g:message code="concomitantMedication.medicationName.label" default="Medication Name" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'medicationName', 'errors')}">
              <g:if test="${show45VersionUpdates}">
                  <div id="medicationName" class="select-rx-by-vocab"></div>
                  <input type="hidden" value="${concomitantMedicationInstance?.medicationNameCvocab?.cVocabId}" id="medicationName-rx-id" name="medicationNameRxId"/>
                  <input type="hidden" value="${concomitantMedicationInstance?.medicationNameCvocab?.cVocabType}" id="medicationName-rx-typ" name="medicationNameRxTyp"/>
                  <input type="hidden" value="${concomitantMedicationInstance?.medicationNameCvocab?.cVocabUserSelection}" id="medicationName-rx-rx" name="medicationNameRxRx"/>
                  <input type="hidden" value="${concomitantMedicationInstance?.medicationNameCvocab?.cVocabVer}" id="medicationName-rx-cvocabVer" name="medicationNameRxCvocabVer"/>
                  <input type="hidden" value="${concomitantMedicationInstance?.medicationNameCvocab?.genName}" id="medicationName-rx-genName" name="medicationNameRxGenName"/>
              </g:if>
              <g:else>
                  <g:textField name="medicationName" value="${concomitantMedicationInstance?.medicationName}" />
              </g:else>
            </td>
            </tr>

            <!-- show old form for 4.5 version and below only -->
            <g:if test="${!show45VersionUpdates}">

              <tr class="prop">
                <td valign="top" class="name">
                  <label for="amount"><g:message code="concomitantMedication.amount.label" default="Amount" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'amount', 'errors')}">
              <g:textField name="amount" value="${concomitantMedicationInstance?.amount}" />
              </td>
              </tr>

              <tr class="prop">
                <td valign="top" class="name">
                  <label for="dosageUnit"><g:message code="concomitantMedication.dosageUnit.label" default="Dosage Form/Unit" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'dosageUnit', 'errors')}">
              <g:select name="dosageUnit" from="${nci.obbr.cahub.staticmembers.DosageUnit.list(sort:'name')}" optionKey="name" value="${concomitantMedicationInstance?.dosageUnit}"  noSelection="['': '']" />
              </td>
              </tr>

              <tr class="prop">
                <td valign="top" class="name">
                  <label for="frequency"><g:message code="concomitantMedication.frequency.label" default="Frequency" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'frequency', 'errors')}">
              <g:select name="frequency" from="${nci.obbr.cahub.staticmembers.Frequency.list(sort:'name')}" optionKey="name" value="${concomitantMedicationInstance?.frequency}"  noSelection="['': '']" />
              </td>
              </tr>

              <tr class="prop">
                <td valign="top" class="name">
                  <label for="route"><g:message code="concomitantMedication.route.label" default="Route" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'route', 'errors')}">
              <g:select name="route" from="${nci.obbr.cahub.staticmembers.Route.list(sort:'name')}" optionKey="name" value="${concomitantMedicationInstance?.route}"  noSelection="['': '']" />
              </td>
              </tr>


            </g:if>


            <!-- END show old form for 4.5 version and below only -->

           


            <g:if test="${show25VersionUpdates || show45VersionUpdates}">
              <tr class="prop">
                <td valign="top" class="name">
                  <label for="dateofLastAdministration"><g:message code="concomitantMedication.dateofLastAdministration.label" default="Dateof Last Administration" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'dateofLastAdministration', 'errors')}">
                  <input type="radio" id="d1" name="dateofLastAdministration" value="Known" onclick="getCurrentDate()" checked />&nbsp;<label for="d1">Known</label>&nbsp;&nbsp;&nbsp;
                  <input type="radio" id="d2" name="dateofLastAdministration" value="Unknown" onclick="getDefaultDate()" />&nbsp;<label for="d2">Unknown</label><br/><br/>
              <g:datePicker name="dateofLastAdministration" precision="day" value="${concomitantMedicationInstance?.dateofLastAdministration}" years="${1900..2099}" /> 
              </td>
              </tr>
            </g:if>
            <g:else>
              <tr class="prop">
                <td valign="top" class="name">
                  <label for="dateofLastAdministration"><g:message code="concomitantMedication.dateofLastAdministration.label" default="Dateof Last Administration" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'dateofLastAdministration', 'errors')}">
                  <input type="radio" id="d1" name="dateofLastAdministration" value="Known" onclick="getCurrentDate2()" checked />&nbsp;<label for="d1">Known</label>&nbsp;&nbsp;&nbsp;
                  <input type="radio" id="d2" name="dateofLastAdministration" value="No Date Selected" onclick="getDefaultDate()" />&nbsp;<label for="d2">No Date Selected</label><br/><br/>
              <g:datePicker name="dateofLastAdministration" precision="day" value="${concomitantMedicationInstance?.dateofLastAdministration}" noSelection="['':'']" years="${1900..2099}" />
              </td>
              </tr>
            </g:else>
            
             <g:if test="${show45VersionUpdates}">
              <tr class="prop">
                <td valign="top" class="name">
                  <label for="source"><g:message code="concomitantMedication.source.label" default="History Source" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: concomitantMedicationInstance, field: 'source', 'errors')}">
              <g:select name="source" from="${['Medical Record', 'Family Report', 'Medical Record and Family Report']}" value="${concomitantMedicationInstance?.source?:defaultSource}" noSelection="['': 'Select One']"/>
              </td>
              </tr>
            </g:if>
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
