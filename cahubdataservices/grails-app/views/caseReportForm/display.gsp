<%@page defaultCodec="none" %>
<%@ page import="nci.obbr.cahub.forms.gtex.crf.CaseReportForm" %>
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="casereportform display" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseReportForm.label', default: 'Case Report Form')}" />
                <title><g:message code="default.show.label" args="[entityName]" /></title>    
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>    
            <g:queryDesc caserecord="${caseReportFormInstance?.caseRecord}" form="gtexCrf" />
          
          <h1>View Case Report Form for ${caseReportFormInstance?.caseRecord?.encodeAsHTML()}</h1>

          
<table>
        <tbody>
          <tr class="prop">
              <td colspan="5">                                
                <h2>Case Details</h2>
              </td>
          </tr>
    <tr class="prop">
        <td valign="top">
          <div class="clearfix">
            <dl class="formdetails left">
              <dt>Case ID:</dt><dd><g:link controller="caseRecord" action="display" id="${caseReportFormInstance.caseRecord.id}">${caseReportFormInstance.caseRecord.caseId}</g:link></dd>
            </dl>
            <dl class="formdetails left">
              <dt>Collection Type:</dt><dd>${caseReportFormInstance.caseRecord.caseCollectionType}</dd>
            </dl>
            <dl class="formdetails left">
              <dt>BSS:</dt><dd>${caseReportFormInstance.caseRecord.bss.name}</dd>
            </dl>
          </div>
        </td>
    </tr>                    
</table>
      
            <g:render template="de_show" model="${['demographicsInstance':demographicsInstance]}" />

            <g:render template="me_show" model="${['medicalHistoryInstance':medicalHistoryInstance]}" />
            <g:if test="${medicalHistoryInstance?.nonMetastaticCancer?.toString() =='Yes'}">
               <g:render template="ch_list" model="${['cancerHistoryInstanceList':cancerHistoryInstanceList]}" />
            </g:if>

            <g:render template="gm_list" model="${['generalMedicalHistoryInstanceList':generalMedicalHistoryInstanceList]}" />

            <g:render template="cm_list" model="${['concomitantMedicationInstanceList':concomitantMedicationInstanceList]}" />

            <g:if test="${isdeath}">
            <g:render template="dt_show" model="${['deathCircumstancesInstance':deathCircumstancesInstance]}" />
            </g:if>
            <g:else>
              <g:render template="su_show" model="${[surgicalProceduresInstance:surgicalProceduresInstance,list1:list1, list2:list2, list3:list3, list4:list4, list5:list5, list6:list6, list7:list7, list8:list8, list9:list9, list10:list10, list11:list11, list12:list12, list13:list13, list14:list14 ]}" />
            </g:else>

            <g:render template="se_show" model="${['serologyResultInstance':serologyResultInstance]}" />
         
        </div>
    </body>
</html>
