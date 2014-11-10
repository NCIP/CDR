<table>
    <tbody>
        <tr class="prop"><td colspan="4"><h2>Case Details</h2></td></tr>
        <tr class="prop">
            <td valign="top">
              <div class="clearfix">
                <g:if test="${session.org?.code == 'OBBR'}">
                <dl class="formdetails left">
                    <dt>Case ID:</dt>
                    <dd>
                        <g:displayCaseRecordLink caseRecord="${caseRecord}" session="${session}" />
                        %{-- <g:link controller="caseRecord" action="display" id="${caseRecord.id}">${caseRecord.caseId}</g:link> --}%
                    </dd>
                </dl>
                </g:if>
                <g:else>
                 <dl class="formdetails left"><dt>Case ID:</dt><dd>${caseRecord.caseId}</dd></dl>
                </g:else>
                <dl class="formdetails left"><dt>BSS:</dt><dd>${caseRecord.bss.name}</dd></dl>
                
                <dl class="formdetails left"><dt>Uploaded File Name:</dt><dd>${caseRecord.tissueRecoveryBrain?.uploadedFilename_bt}</dd></dl>
                
              </div>
            </td>
        </tr>      
    </tbody>
</table>
