<script type="text/javascript">
 $(document).ready(function() {

         
          $("#outcomeOfWDrawData2").click(function() {
             if(this.checked) {
                 $("#outcomeOfWDrawData3").show()
                 $("#outcomeOfWDrawData3").focus()
             } else {
                 $("#outcomeOfWDrawData3").hide()
                 $("#outcomeOfWDrawData3").val('')
             }
         });
         
        $("#outcomeOfWDrawData1").click(function() {
             if(this.checked) {
                 $("#outcomeOfWDrawData3").hide()
                 $("#outcomeOfWDrawData3").val('')
             } 
         });
         
         
         $("#outcomeOfSpeci7").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").show()
                 $("#outcomeOfSpeci8").focus()
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } else {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 
             }
         });
         
        $("#outcomeOfSpeci1").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } 
         });
         $("#outcomeOfSpeci2").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } 
         });
         $("#outcomeOfSpeci3").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } 
         });
         $("#outcomeOfSpeci4").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } 
         });
         $("#outcomeOfSpeci5").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } 
         });
          $("#outcomeOfSpeci6").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             } 
         });
          $("#outcomeOfSpeci9").click(function() {
             if(this.checked) {
                 $("#outcomeOfSpeci8").hide()
                 $("#outcomeOfSpeci8").val('')
                 $("#outcomeOfSpeci10").show()
                 $("#outcomeOfSpeci10").focus()
             } 
             else {
                 $("#outcomeOfSpeci10").hide()
                 $("#outcomeOfSpeci10").val('')
             }
         });
         
         
         
         });
</script>    


<h2>Section C: Specimen/Data recall and recall notification FORM (${caseWithdrawFormsInstance?.authority})</h2>

<div class="list">
  <table>
    <tbody>


      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateProcessingStarted">Date processing started</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'dateProcessingStarted', 'errors')}">

    <g:jqDatePicker name="dateProcessingStarted" value="${caseWithdrawFormsInstance?.dateProcessingStarted}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="ackRcvdYesNO">Acknowledge receipt of message of specimen and data recall:</label>
      </td>
      <td  class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'ackRcvdYesNO', 'errors')}">

        <div>
          <g:radio name="ackRcvdYesNO" id="ackRcvdYesNO1" value="YES" checked="${caseWithdrawFormsInstance?.ackRcvdYesNO == 'YES'}"/> <label class="radio" for="ackRcvdYesNO1">YES</label><br>

          <g:radio name="ackRcvdYesNO" id="ackRcvdYesNO2" value="NO" checked="${caseWithdrawFormsInstance?.ackRcvdYesNO == 'NO'}"/> <label class="radio" for="ackRcvdYesNO2">NO</label>
        </div>


      </td>
    </tr>

    <g:if test="${caseWithdrawFormsInstance?.ackRcvdYesNO}">

      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateAckRcvd"><g:message code="withdrawalOfConsent.dateAckRcvd.label" default="Date acknowledged:" /></label>
        </td>
        <td  class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'dateSubmitted', 'errors')}">

          <g:formatDate date="${caseWithdrawFormsInstance?.dateSubmitted}" />

        </td>
      </tr>
    </g:if>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateSpeciWithdraw"><g:message code="withdrawalOfConsent.dateSpeciWithdraw.label" default="Date of specimen recall:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'dateSpeciWithdraw', 'errors')}">

    <g:jqDatePicker name="dateSpeciWithdraw" value="${caseWithdrawFormsInstance?.dateSpeciWithdraw}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="outcomeOfSpecimen">Outcome of recalled specimen:</label>
      </td>
      <td  class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'outcomeOfSpecimen', 'errors')}">
        <div>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci1" value="Destroyed" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Destroyed'}"/> <label class="radio" for="outcomeOfSpeci1">Destroyed</label><br>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci2" value="Sequestered and remove from active biobank" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Sequestered and remove from active biobank'}"/> <label class="radio" for="outcomeOfSpeci2">Sequestered and remove from active biobank</label><br>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci3" value="Returned to BSS" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Returned to BSS'}"/> <label class="radio" for="outcomeOfSpeci3">Returned to BSS</label><br>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci4" value="Returned to donor" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Returned to donor'}"/> <label class="radio" for="outcomeOfSpeci4">Returned to donor</label><br>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci5" value="Returned to Donor family" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Returned to Donor family'}"/> <label class="radio" for="outcomeOfSpeci5">Returned to Donor family</label><br>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci6" value="No residual specimen available" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'No residual specimen available'}"/> <label class="radio" for="outcomeOfSpeci6">No residual specimen available</label><br>
          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci7" value="Shipped to other location, specify" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Shipped to other location, specify'}"/> <label class="radio" for="outcomeOfSpeci7">Shipped to other location, specify</label><br>

          <g:textField id="outcomeOfSpeci8"  style="display:${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Shipped to other location, specify'?'display':'none'}" name="specShippedTo" value="${caseWithdrawFormsInstance?.specShippedTo}" />&nbsp;<br>

          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci9" value="Other" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Other'}"/> <label class="radio" for="outcomeOfSpeci9">Other</label><br>

          <g:textField id="outcomeOfSpeci10"  style="display:${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Other'?'display':'none'}" name="outcomeOfSpecimenOther" value="${caseWithdrawFormsInstance?.outcomeOfSpecimenOther}" />&nbsp;
        </div>
      </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="datedataWdrawn"><g:message code="withdrawalOfConsent.dateVerified.label" default="Date of data recall:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'datedataWdrawn', 'errors')}">

    <g:jqDatePicker name="datedataWdrawn" value="${caseWithdrawFormsInstance?.datedataWdrawn}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="outcomeOfWDrawnData">Outcome of recalled data:</label>
      </td>
      <td  class="value  ${hasErrors(bean: caseWithdrawFormsInstance, field: 'outcomeOfWDrawnData', 'errors')}">
        <div>
          <g:radio name="outcomeOfWDrawnData" id="outcomeOfWDrawData1" value="Deleted from active files and sequestered" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Deleted from active files and sequestered'}"/> <label class="radio" for="outcomeOfWDrawData1">Deleted from active files and sequestered</label><br>

          <g:radio name="outcomeOfWDrawnData" id="outcomeOfWDrawData2" value="Other" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'}"/> <label class="radio" for="outcomeOfWDrawData2">Other</label><br>

          <g:textField  id="outcomeOfWDrawData3" style="display:${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'?'display':'none'}" name="outcomeOfWDrawDataOther" value="${caseWithdrawFormsInstance?.outcomeOfWDrawDataOther}" />&nbsp;
        </div>
      </td>
    </tr>


<!--
    <tr class="prop">
      <td valign="top" class="name">
        <label for="comments">Comments: </label>
      </td>
      <td valign="top" class="value  ${hasErrors(bean: caseWithdrawFormsInstance, field: 'comments', 'errors')}">
    <g:textArea name="comments" cols="40" rows="5" value="${caseWithdrawFormsInstance?.comments}" />
    </td>
    </tr>

-->

 <tr class="prop">
      <td colspan="2" class="name ${hasErrors(bean: caseWithdrawFormsInstance, field: 'comments', 'errors')}">
        <label for="comments">Comments: (for above choices)</label><br />
    <g:textArea class="textwide" name="comments" cols="40" rows="5" value="${caseWithdrawFormsInstance?.comments}" />
    </td>
    </tr>
    
    
    <tr class="prop">
      <td valign="top" class="name">
        <label for="verifiedBy">Verified by name: </label>
      </td>
      <td valign="top" class="value  ${hasErrors(bean: caseWithdrawFormsInstance, field: 'verifiedBy', 'errors')}">
      <g:hiddenField name="verifiedBy" value="${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}" />
   
        ${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="verifiedByRole">Verified by role: </label>
      </td>
      <td valign="top" class="value  ${hasErrors(bean: caseWithdrawFormsInstance, field: 'verifiedByRole', 'errors')}">
    <g:textField name="verifiedByRole" value="${caseWithdrawFormsInstance?.verifiedByRole}"  />
    </td>
    </tr>





    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateVerified"><g:message code="withdrawalOfConsent.dateVerified.label" default="Date of verification:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'dateVerified', 'errors')}">

    <g:jqDatePicker name="dateVerified" value="${caseWithdrawFormsInstance?.dateVerified}" />
    </td>
    </tr>

  


    </tbody>
  </table>
</div>

