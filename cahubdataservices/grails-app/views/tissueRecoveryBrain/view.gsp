

<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryBrain" %>
<g:set var="bodyclass" value="tissuerecoverybrain view wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          
        </div>
      </div>
      <div id="container" class="clearfix">
        <h1>View Tissue Recovery Form <g:if test="${tissueRecoveryBrainInstance.dateSubmitted}">, Submitted on <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBrainInstance.dateSubmitted}"/></g:if></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tissueRecoveryBrainInstance}">
            <div class="errors">
                <g:renderErrors bean="${tissueRecoveryBrainInstance}" as="list" />
            </div>
            </g:hasErrors>
              <g:render template="caseDetails" bean="${tissueRecoveryBrainInstance.caseRecord}" var="caseRecord" /> 
            <g:form method="post" >
                <g:hiddenField id ="id" name="id" value="${tissueRecoveryBrainInstance?.id}" />
                <g:hiddenField name="version" value="${tissueRecoveryBrainInstance?.version}" />
                 <g:hiddenField  name="caseRecord.id" value="${tissueRecoveryBrainInstance?.caseRecord.id}" />
               
                <div class="list">
                  <table>
                    <tr>
                      <th>Collected</th>
                      <th>Position</th>
                      <th class="nowrap">Sample ID</th>
                      <th class="collaborator-sample nowrap">Alias Collaborator<br />Sample ID</th>
                      <th class="collaborator-sample nowrap">Alias Collaborator<br />Sample ID_2</th>
                      <th>Mass<br/>mg</th>
                      <th class="collection-date nowrap">Collection Date <span data-msg="Date specific sample underwent dissection" class="cahub-tooltip"></span><div class="nowrap"><g:jqDatePicker id ="batch_date" name="batch_date" /></div></th>
                      <th class="collection-time nowrap">Collection Time <span data-msg="Time specific sample underwent dissection" class="cahub-tooltip"></span><div class="textcenter"><g:textField id="batch_time" name="batch_time" class="timeEntry"  /></div></th>
                      <th class="nowrap">Tissue Site <span data-msg="Specific brain subregion of tissue sample" class="cahub-tooltip"></span></th>
                      <th class="collection-note">Note</th>
                   <!--   <th class="nowrap">Interval<span data-msg="Time (hh:mm) between Brain removal and tissue sample dissection" class="cahub-tooltip"></span></th> -->
                      <th>RIN</th>
                    </tr> 
                    <g:each in="${brainTissues}" status="i" var="t">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td><g:checkBox name="collected_${t.id}"   value="${t.collected}" DISABLED="true" /></td>
                        <td>${t.position}</td>
                        <td class="nowrap">${t.sampleId}</td>
                        <td class="nowrap">${t.collSampleId}</td>
                         <td class="nowrap">${t.collSampleId2}</td>
                         <td>${t.mass}</td>
                         <td class="nowrap textcenter">${t.collectionDateStr}</td>
                         <td class="nowrap textcenter">${t.collectionTimeStr}</td>
                         <td>${t.tissueType.name}</td>
                         <td class="unlimitedstr"><div class="show-textarea">${t.notes}</div></td>
                       <!--  <td>${t.interval}</td> -->
                         <td>${t.rin}</td>
                      </tr>
                    </g:each>
                  </table>
                <div class="buttons">
                    <g:if test="${tissueRecoveryBrainInstance.dateSubmitted && (session.DM == true || session.org?.code == 'MBB')}">
                    <span class="button"><g:actionSubmit id="sub" class="save" action="resume" value="Resume Editing" /></span>
                    </g:if>
                    <span class="button"><g:actionSubmit  class="save" action="export" value="Export" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
