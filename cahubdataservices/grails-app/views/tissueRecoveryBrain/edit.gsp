<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryBrain" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="tissuerecoverybrain edit wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'tissuerecoverybrain.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
         <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
         </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Edit Tissue Recovery Form</h1>
            <g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
            <g:hasErrors bean="${tissueRecoveryBrainInstance}">
            <div class="errors"><g:renderErrors bean="${tissueRecoveryBrainInstance}" as="list" /></div>
            </g:hasErrors>
            <g:render template="caseDetails" bean="${tissueRecoveryBrainInstance.caseRecord}" var="caseRecord" /> 
            <g:form method="post">
                <g:hiddenField id ="id" name="id" value="${tissueRecoveryBrainInstance?.id}" />
                <g:hiddenField name="version" value="${tissueRecoveryBrainInstance?.version}" />
                <g:hiddenField  name="caseRecord.id" value="${tissueRecoveryBrainInstance?.caseRecord.id}" />
                <g:each in="${brainTissues}" status="i" var="t">
                  <g:hiddenField name="${t.id}" value="is_id" /></g:each>
                <input type="hidden" name="changed" value="N" id="changed"/>
                <div class="list">
                  <table>
                    <tr>
                      <th>Collected</th>
                      <th>Position</th>
                      <th class="nowrap">Sample ID</th>
                      <th class="collaborator-sample nowrap">Alias Collaborator<br />Sample ID</th>
                      <th class="collaborator-sample nowrap">Alias Collaborator<br />Sample ID_2</th>
                      <th>Mass<br/>mg</th>
                      <th class="collection-date nowrap">Collection Date <span data-msg="Date specific sample underwent dissection" class="cahub-tooltip"></span><div class="textcenter"><g:jqDatePicker id ="batch_date" name="batch_date" /></div></th>
                      <th class="collection-time nowrap">Collection Time <span data-msg="Time specific sample underwent dissection" class="cahub-tooltip"></span><div class="textcenter"><g:textField id="batch_time" name="batch_time" class="timeEntry"  /></div></th>
                      <th class="nowrap">Tissue Site <span data-msg="Specific brain subregion of tissue sample" class="cahub-tooltip"></span></th>
                      <th class="collection-note">Note</th>
                  <!--    <th class="nowrap">Interval<span data-msg="Time (hh:mm) between Brain removal and tissue sample dissection" class="cahub-tooltip"></span></th> -->
                      <th>RIN</th>
                    </tr> 
                    <g:each in="${brainTissues}" status="i" var="t">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>
                          <g:if test ="${t.specimenRecord}">
                      <g:checkBox name="collected_${t.id}"  id="collected_${t.id}" value="${t.collected}" disabled="TRUE" />
                          </g:if>
                      <g:else>
                        <g:checkBox name="collected_${t.id}"  id="collected_${t.id}" value="${t.collected}"  />
                      </g:else>
                      </td>
                        <td>${t.position}</td>
                        <td class="nowrap">${t.sampleId}</td>
                        <td class="nowrap">${t.collSampleId}</td>
                        <td class="nowrap">${t.collSampleId2}</td>
                        <td><g:textField class="mass ${errorMap.get('mass_' + t.id)}" id="mass_${i}" name="mass_${t.id}" value="${t.mass}"  size="5" /></td>
                        <td><g:textField class="${errorMap.get('date_' + t.id)} recordinterval collectiondate" id="date_${i}" name="date_${t.id}" value="${t.collectionDateStr}"  /></td>
                        <td class="textcenter"><g:textField id="time_${i}" name="time_${t.id}" value="${t.collectionTimeStr}" class="timeEntry ${errorMap.get('time_' + t.id)} recordinterval"  /><span class="button"><g:actionSubmit id="clear_${i}"  class="date-time-clear"  value="Clear" /></span></td>
                        <td>${t.tissueType.name}</td>
                        <td><g:textArea id="comments_${t.id}" class="${errorMap.get('notes_' + t.id)}" name="notes_${t.id}" value="" style="height:28px;width:150px;" value="${t.notes}" /></td>
                      <!--  <td><span id="in_${i}">${t.interval}</span></td> -->
                        <td>${t.rin}</td>
                      </tr>
                    </g:each>
                  </table>
                  <div class="buttons">
                      <span class="button"><g:actionSubmit id="sub" class="save" action="update" value="Save" /></span>
                      <span class="button"><g:actionSubmit  class="save" action="upload" value="Reload" /></span>
                      <g:if test="${canSubmit=='Y'}"><span class="button"><g:actionSubmit  class="save" action="submit" value="Submit" onclick="return check()"  /></span></g:if>
                  </div>
                </div>
            </g:form>
      </div>
    </body>
</html>
