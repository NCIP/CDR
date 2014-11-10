

<table>
  <tbody>
    <tr class="prop"><td colspan="8"><h2>Case Details</h2></td></tr>
    <tr class="prop">
      <td valign="top">
        <div class="clearfix">
          <dl class="formdetails left"><dt>BSS Name:</dt><dd>${caseRecord.bss.name}</dd></dl>
          <dl class="formdetails left"> <dt>Case ID:</dt> <dd>
            <g:displayCaseRecordLink caseRecord="${caseRecord}" session="${session}" />
            </dd>
          </dl>

          <dl class="formdetails left"><dt>Eligibility Status:</dt>
            <dd>
<%--
      <g:radio name="eligibleYes" checked="${isElig==true}"  />&nbsp;Eligible</label>&nbsp;&nbsp;
      <g:radio name="eligibleNot" checked="${isElig==false}"  />&nbsp;InEligible</label>
      --%>
    <g:if test="${isElig==true}"> <span class="yes"> Eligible </span></g:if>
    <g:if test="${isElig==false}"> <span class="no">Ineligible</span></g:if>
            </dd></dl>

        </div>
      </td>
    </tr>      
  </tbody>
</table>
