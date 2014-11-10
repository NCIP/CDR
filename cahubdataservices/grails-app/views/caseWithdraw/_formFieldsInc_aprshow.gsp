<script type="text/javascript">
 $(document).ready(function() {

      
         $("#outcomeOfSpeciCBR8").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeciCBR9").show()
                 $("#outcomeOfSpeciCBR9").focus()
             } else {
                 $("#outcomeOfSpeciCBR9").hide()
                 $("#outcomeOfSpeciCBR9").val('')
             }
         });
         
         $("#ackRcvdYesNOCBR1").click(function() {
             if(this.checked) {
                 $("#dateAckRcvdByCBR").show()
                 $("#dateAckRcvdByCBR").focus()
             } else {
                 $("#dateAckRcvdByCBR").hide()
                 $("#dateAckRcvdByCBR").val('')
             }
         });
         
          $("#outcomeOfWDrawDataCBR2").click(function() {
             if(this.checked) {
                 $("#outcomeOfWDrawDataCBR3").show()
                 $("#outcomeOfWDrawDataCBR3").focus()
             } else {
                 $("#outcomeOfWDrawDataCBR3").hide()
                 $("#outcomeOfWDrawDataCBR3").val('')
             }
         });
         
         $("#cbrVerified1").click(function() {
             if(this.checked) {
                 $("#dateCbrVerified").show()
                 $("#dateCbrVerified").focus()
             } else {
                 $("#dateCbrVerified").hide()
                 $("#dateCbrVerified").val('')
             }
         });
         
         
         });
</script>    


<h2>Section B: Acknowledgment and Approval by CaHUB to proceed </h2>
<div class="list">
  <table>
    <tbody>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="ackRcvdYesNOCDR">Acknowledged receipt of message of Case Recall :</label>
        </td>
        <td valign="top" >

          <table >

            <tbody>
              <tr><td> <g:radio name="ackRcvdYesNOCDR" id="dateAckRcvdByCDR1" value="YES" checked="${caseWithdrawInstance?.ackRcvdYesNOCDR == 'YES'}"/> <label class="radio" for="ackRcvdYesNOCDR1">YES</label></td></tr>

      <tr><td>  <g:radio name="ackRcvdYesNOCDR" id="dateAckRcvdByCDR2" value="NO" checked="${caseWithdrawInstance?.ackRcvdYesNOCDR == 'NO'}"/> <label class="radio" for="ackRcvdYesNOCDR2">NO</label></td></tr>

    </tr>
    </tbody>
  </table>
</td>
</tr>
<g:if test="${caseWithdrawInstance?.dateAckRcvdByCDR}">

  <tr class="prop">
    <td valign="top" class="name">
      <label for="dateAckRcvdByCDR"><g:message code="withdrawalOfConsent.dateAckRcvdByCDR.label" default="Date Acknowledged:" /></label>
    </td>
    <td valign="top" >

  <g:formatDate date="${caseWithdrawInstance?.dateAckRcvdByCDR}" />

  </td>
  </tr>
</g:if>


<tr class="prop ">
  <td valign="top" class="name">
    Assignment of entities for Case Recall process:
  </td>
  <td valign="top" >
    <table  >

      <tbody>


        <tr><td><g:checkBox   id ="assign1" name="assignToCDR" value="${caseWithdrawInstance?.assignToCDR}" checked="${caseWithdrawInstance?.assignToCDR}" /> <label for="assign1">CDR</label></td></tr>
<tr><td><g:checkBox   id ="assign2" name="assignToCBR" value="${caseWithdrawInstance?.assignToCBR}" checked="${caseWithdrawInstance?.assignToCBR}" /> <label for="assign2">CBR</label></td></tr>
<tr><td><g:checkBox   id ="assign3" name="assignToLDACC" value="${caseWithdrawInstance?.assignToLDACC}" checked="${caseWithdrawInstance?.assignToLDACC}" /> <label for="assign3">LDACC</label></td></tr>
<g:if test="${hasBrain.equals('YES')}">
  <tr><td><g:checkBox   id ="assign4" name="assignToBrainbank" value="${caseWithdrawInstance?.assignToBrainbank}" checked="${caseWithdrawInstance?.assignToBrainbank}" /> <label for="assign4">BRAIN BANK</label></td></tr>
</g:if>
<tr><td><g:checkBox   id ="assign5" name="assignToPRC" value="${caseWithdrawInstance?.assignToPRC}" checked="${caseWithdrawInstance?.assignToPRC}" /> <label for="assign5">PRC</label></td></tr>


</tbody>
</table>
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="cahubApproveOrNot">Approval to proceed with Case Recall:</label>
  </td>
  <td valign="top" >
    <table >

      <tbody>
        <tr><td>  <g:radio name="cahubApproveOrNot" id="CahubApproveOrNot1" value="YES" checked="${caseWithdrawInstance?.cahubApproveOrNot == 'YES'}"/> <label class="radio" for="CahubApproveOrNot1">YES</label></td></tr>

<tr><td> <g:radio name="cahubApproveOrNot" id="CahubApproveOrNot2" value="NO" checked="${caseWithdrawInstance?.cahubApproveOrNot == 'NO'}"/> <label class="radio" for="CahubApproveOrNot2">NO</label></td></tr>


</tbody>
</table>
</td>
</tr>






<tr class="prop">
  <td valign="top" class="name">
    <label for="apprComments">Comments: </label>
  </td>
  <td valign="top" >
<g:textArea name="apprComments" cols="40" rows="5" value="${caseWithdrawInstance?.apprComments}" />
</td>
</tr>


<tr class="prop">
  <td valign="top" class="name">
    <label for="approvedBy">Approved by name: </label>
  </td>
  <td valign="top" >
${caseWithdrawInstance?.approvedBy}
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

