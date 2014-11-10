<table style="width:100%;">
  <tbody>
    <tr class="prop">
      <td colspan="8">                                
        <h2>Case Details</h2>
      </td>
    </tr>
    <tr class="prop">
      <td valign="top">
        <div class="clearfix">
          <dl class="formdetails left">

           <dt>Case ID:</dt>

              <g:if test="${!caseRecord.caseWithdraw?.finalCompleteDate}">
                <dd>  
                  <g:displayCaseRecordLink caseRecord="${caseRecord}" session="${session}" />
                  %{-- <g:link controller="caseRecord" action="display" id="${caseRecord.id}">${caseRecord.caseId}</g:link> --}%
                </dd>
              </g:if>
              <g:else>
                <dd>${caseRecord.caseId}</dd>
              </g:else>
           
            </dl>
          

          <dl class="formdetails left">
            <dt>BSS:</dt><dd>${caseRecord?.bss?.name}</dd>
          </dl>
          <dl class="formdetails left">
            <dt>BSS Protocol ID:</dt><dd>${caseRecord?.bss?.protocolSiteNum}</dd>
          </dl>


          <g:if test="${hasBrain}">
            <dl class="formdetails left">
              <dt>Brain Specimen Collected:</dt><dd>${hasBrain?.equals('YES')? 'YES' :'NO'}</dd>
            </dl>
          </g:if>

        </div>
      </td>
    </tr>   


  </tbody>
</table>

