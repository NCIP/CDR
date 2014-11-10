
<%@ page import="nci.obbr.cahub.datarecords.ctc.*" %>
<table>
    <tbody>
        <g:if test="${session.study?.code != 'CTC'}">
        <tr class="prop"><td colspan="8"><h2>Case Details</h2></td></tr>
        </g:if>
    <g:else>
        <tr class="prop"><td colspan="8"><h2>Patient Details</h2></td></tr>
    </g:else>
        <tr class="prop">
            <td valign="top">
              <div class="clearfix">
                 <g:if test="${session.study?.code != 'CTC'}">
                   <dl class="formdetails left">
                     <dt>Case ID:</dt>
                     <dd>
                       <g:displayCaseRecordLink caseRecord="${caseRecord}" session="${session}" />
                       %{--<g:link controller="caseRecord" action="display" id="${caseRecord.id}">${caseRecord.caseId}</g:link> --}%
                     </dd>
                   </dl>
                 </g:if>
                <g:else>
                   <dl class="formdetails left">
                     <dt>Patient ID:</dt>
                     <dd>
                       <g:displayCaseRecordLink caseRecord="${caseRecord}" session="${session}" action="accessCtc" />
                       %{-- <g:link controller="caseRecord" action="accessCtc" id="${caseRecord.id}">${caseRecord.caseId}</g:link> --}%
                     </dd>
                   </dl>
                </g:else>
                <g:if test="${session.study?.code == 'BPV' || session.study?.code == 'BRN'}">
                    <dl class="formdetails left"><dt>Primary Organ:</dt><dd>${caseRecord.primaryTissueType}</dd></dl>
                </g:if>
                <g:elseif test="${session.study?.code == 'GTEX' || session.study?.code == 'BMS'}">
                    <dl class="formdetails left"><dt>Collection Type:</dt><dd>${caseRecord.caseCollectionType}</dd></dl>
                </g:elseif>
                 <g:if test="${session.study?.code == 'CTC'}">
                    <dl class="formdetails left"><dt>Collection Site:</dt><dd>${caseRecord.bss.name}</dd></dl>
                 </g:if>
                <g:else>
                   <dl class="formdetails left"><dt>BSS:</dt><dd>${caseRecord.bss.name}</dd></dl>
                </g:else>
               
                <g:if test="${session.study?.code == 'BPV' || session.study?.code == 'BRN'}">
                    <dl class="formdetails left"><dt>Tissue Bank ID:</dt><dd>${caseRecord.tissueBankId}</dd></dl>
                </g:if>
                <g:if test="${session.study?.code == 'CTC' && PatientRecord.findByCaseRecord(caseRecord).experiment=='VC'}">
                  <dl class="formdetails left"><dt>Experiment:</dt><dd>Time Course Veridex Comparison</dd></dl>
                </g:if>
                <g:if test="${session.study?.code == 'CTC' && PatientRecord.findByCaseRecord(caseRecord).experiment=='BT'}">
                  <dl class="formdetails left"><dt>Experiment:</dt><dd>Best Tube</dd></dl>
                </g:if>
                <g:if test="${session.study?.code == 'CTC'}">
                  <dl class="formdetails left"><dt>Cancer Stage:</dt><dd>${PatientRecord.findByCaseRecord(caseRecord).cancerStage}</dd></dl>
                </g:if>
              </div>
            </td>
        </tr>      
    </tbody>
</table>
