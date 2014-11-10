<table>
    <tbody>
        <tr class="prop"><td colspan="4"><h2>Case Details</h2></td></tr>
        <tr class="prop">
            <td valign="top">
              <div class="clearfix">
                <g:if test="${session.org?.code == 'MBB'}">
               <dl class="formdetails left"><dt>Case ID:</dt><dd>${caseRecord.caseId}</dd></dl>
                </g:if>
                <g:else>
                   <dl class="formdetails left"><dt>Case ID:</dt><dd><g:link controller="caseRecord" action="display" id="${caseRecord.id}">${caseRecord.caseId}</g:link></dd></dl>
                </g:else>
                <dl class="formdetails left"><dt>BSS:</dt><dd>${caseRecord.bss.name}</dd></dl>
                
              </div>
            </td>
        </tr>      
    </tbody>
</table>

