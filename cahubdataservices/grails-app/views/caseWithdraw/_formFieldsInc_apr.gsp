<script type="text/javascript">
 $(document).ready(function() {
    $("#CahubApproveOrNot2").click(function(){
                     $("#entityList").hide()
                     
          
   
         });
         
   $("#CahubApproveOrNot1").click(function(){
                     $("#entityList").show()
                     
          
   
         });

         });
</script>

<table>
  <tr>
    <td>
      <h2>Section B: Acknowledgment and Approval by caHUB to proceed </h2>

      <div class="list">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name" >
                <label for="ackRcvdYesNOCDR">Acknowledge receipt of message of Case Recall :</label>

              </td>
              <td  class="value  ${hasErrors(bean: caseWithdrawInstance, field: 'ackRcvdYesNOCDR', 'errors')}">

                <div>
                  <g:radio name="ackRcvdYesNOCDR" id="ackRcvdYesNOCDR1" value="YES" checked="${caseWithdrawInstance?.ackRcvdYesNOCDR == 'YES'}"/> <label class="radio" for="ackRcvdYesNOCDR1">YES</label><br>

                  <g:radio name="ackRcvdYesNOCDR" id="ackRcvdYesNOCDR2" value="NO" checked="${caseWithdrawInstance?.ackRcvdYesNOCDR == 'NO'}"/> <label class="radio" for="ackRcvdYesNOCDR2">NO</label>
                </div>
              </td>
            </tr>

          <g:if test="${caseWithdrawInstance?.dateAckRcvdByCDR}">

            <tr class="prop">
              <td valign="top" class="name">
                <label for="dateAckRcvdByCDR"><g:message code="withdrawalOfConsent.dateAckRcvdByCDR.label" default="Date Acknowledged:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'dateAckRcvdByCDR', 'errors')}">

            <g:formatDate date="${caseWithdrawInstance?.dateAckRcvdByCDR}" />

            </td>
            </tr>
          </g:if>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="cahubApproveOrNot">Approval to proceed with Case Recall:</label>
            </td>
            <td class="value  ${hasErrors(bean: caseWithdrawInstance, field: 'cahubApproveOrNot', 'errors')}">
              <div>
                <g:radio name="cahubApproveOrNot" id="CahubApproveOrNot1" value="YES" checked="${caseWithdrawInstance?.cahubApproveOrNot == 'YES'}"/> <label class="radio" for="CahubApproveOrNot1">YES</label><br>

                <g:radio name="cahubApproveOrNot" id="CahubApproveOrNot2" value="NO" checked="${caseWithdrawInstance?.cahubApproveOrNot == 'NO'}"/> <label class="radio" for="CahubApproveOrNot2">NO</label>

              </div>

            </td>
          </tr>


          <g:if test="${caseWithdrawInstance.cahubApproveOrNot?.equals('NO')|| !caseWithdrawInstance.cahubApproveOrNot}">
            <tr class="prop hide" id="entityList">
          </g:if>
          <g:else>
            <tr class="prop" id="entityList">
          </g:else>

          <td valign="top" class="name">
            Assignment of entities for Case Recall process:
          </td>
          <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'assignToBrainbank', 'errors')}">

            <div>

              <g:checkBox   id ="assign1" name="assignToCDR" value="${caseWithdrawInstance?.assignToCDR}" checked="${caseWithdrawInstance?.assignToCDR}" /> <label for="assign1">CDR</label><br>
              <g:checkBox   id ="assign2" name="assignToCBR" value="${caseWithdrawInstance?.assignToCBR}" checked="${caseWithdrawInstance?.assignToCBR}" /> <label for="assign2">CBR</label><br>
              <g:checkBox   id ="assign3" name="assignToLDACC" value="${caseWithdrawInstance?.assignToLDACC}" checked="${caseWithdrawInstance?.assignToLDACC}" /> <label for="assign3">LDACC</label><br>
              <g:if test="${hasBrain.equals('YES')}">
                <g:checkBox   id ="assign4" name="assignToBrainbank" value="${caseWithdrawInstance?.assignToBrainbank}" checked="${caseWithdrawInstance?.assignToBrainbank}" /> <label for="assign4">BRAIN BANK</label> <br>
              </g:if>
              <g:checkBox   id ="assign5" name="assignToPRC" value="${caseWithdrawInstance?.assignToPRC}" checked="${caseWithdrawInstance?.assignToPRC}" /> <label for="assign5">PRC</label><br>
            </div>

          </td>
          </tr>




          <tr class="prop">
            <td valign="top" class="name">
              <label for="apprComments">Comments: </label>
            </td>
            <td valign="top" class="value  ${hasErrors(bean: caseWithdrawInstance, field: 'apprComments', 'errors')}">
          <g:textArea name="apprComments" cols="40" rows="5" value="${caseWithdrawInstance?.apprComments}" />
          </td>
          </tr>


          <tr class="prop">
            <td valign="top" class="name">
              <label for="approvedBy">Approved by name: </label>
            </td>
            <td valign="top" class="value  ${hasErrors(bean: caseWithdrawInstance, field: 'approvedBy', 'errors')}">

          <g:hiddenField name="approvedBy" value="${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}" />
          <g:if test="${caseWithdrawInstance.approvedBy}">
${caseWithdrawInstance.approvedBy}
          </g:if>
          <g:else>
${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}
          </g:else>
          </td>
          </tr>


          <g:if test="${caseWithdrawInstance?.dateApproved}">

            <tr class="prop">
              <td valign="top" class="name">
                <label for="dateApproved"><g:message code="withdrawalOfConsent.dateAckRcvdByCDR.label" default="Date approved:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'dateApproved', 'errors')}">

            <g:formatDate date="${caseWithdrawInstance?.dateApproved}" />

            </td>
            </tr>
          </g:if>

          </tbody>
        </table>


      </div>
    </td><!-- end of the actual APR form. The next td tag contains display of the BSS recall request form -->
    <td>
      <h3>View Case Recall Request Information</h3>

      <div >
        <table >
          <tbody>



            <tr class="prop">
              <td valign="top" class="name">
                <label for="dateWithdrawalRcvd"><g:message code="withdrawalOfConsent.dateWithdrawalRcvd.label" default="Date Recall request made:" /></label>
              </td>
              <td><g:formatDate format="MM-dd-yyyy" date="${caseWithdrawInstance?.dateWithdrawalRcvd}" /></td>
          </tr>


          <tr class="prop">
            <td valign="top" class="name">
              Request initiated by:
            </td>
            <td > ${caseWithdrawInstance?.requestInitiator} </td>
          </tr>


          <tr class="prop" >
            <td valign="top" class="name">
              Reason for Case Recall:
            </td>
            <td >
              <ul>  
                <g:if test="${caseWithdrawInstance.reasonMedical}"><li>Medical</li> </g:if>
                <g:if test="${caseWithdrawInstance.reasonPersonal}"> <li>Personal </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonCultural}"> <li>Cultural </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonReligious}"><li> Religious </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonUndocumConsent}"><li> Undocumented Consent </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonProcError}"><li> Processing Error </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonInclExcl}"><li> Inclusion/Exclusion criteria </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonUnknown}"> <li>Unknown/not given </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonOther}"><li> Other - Specify </li></g:if>
                <g:if test="${caseWithdrawInstance.reasonOther && caseWithdrawInstance.reasonOtherDescrip}">(${caseWithdrawInstance.reasonOtherDescrip}) </g:if>
              </ul>

            </td>
          </tr>





          <tr class="prop">
            <td valign="top" class="name">
              <label for="reasonComments">Comments: (for above choices)</label>
            </td>
            <td >
${caseWithdrawInstance?.reasonComments}
            </td>
          </tr>


          <tr class="prop " >
            <td valign="top" class="name ">
              <label for="donReqforWithdraw"><g:message code="withdrawalOfConsent.donReqforWithdrawOther.label" default="Type of withdrawal request:" /></label>
            </td>
            <td>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'All specimens and  data'}">All specimens and  data </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Specimen only'}"> Specimen only </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Data only'}"> Data only </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Follow Up'}"> Follow Up </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Slides only'}"> Slides only </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Partial withdrawal of specimen and associated data'}"> Partial withdrawal of specimen and associated data </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Other'}"> other </g:if>
          <g:if test="${caseWithdrawInstance?.withdrawReqType == 'Other' && caseWithdrawInstance?.withdrawReqTypeOther}">(${caseWithdrawInstance?.withdrawReqTypeOther}) </g:if>


          </td>

          </tr>


          <!-- display specimen IDs -->



          <tr class="prop">
            <td valign="top" class="name">
              <label for="bssNameCompletingForm">Name of person completing form: </label>
            </td>
            <td>
${caseWithdrawInstance?.bssNameCompletingForm}
            </td>
          </tr>

          <g:if test="${caseWithdrawInstance?.bssNameCompletingForm}">

            <tr class="prop">
              <td valign="top" class="name">
                <label for="dateBSSEntrySubmitted"><g:message code="withdrawalOfConsent.dateBSSEntrySubmitted.label" default="Date form completed:" /></label>
              </td>
              <td >

            <g:formatDate date="${caseWithdrawInstance?.dateBSSEntrySubmitted}" />

            </td>
            </tr>
          </g:if>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="bssSiteComments">Site comments: </label>
            </td>
            <td >
${caseWithdrawInstance?.bssSiteComments}
            </td>
          </tr>

          </tbody>
        </table>
      </div>


    </td>
  </tr>
</table>

