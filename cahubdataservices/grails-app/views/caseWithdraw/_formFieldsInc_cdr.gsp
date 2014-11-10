<script type="text/javascript">
 $(document).ready(function() {

      
         
        
         
          $("#outcomeOfWDrawDataCDR2").click(function() {
             if(this.checked) {
                 $("#outcomeOfWDrawDataCDR3").show()
                 $("#outcomeOfWDrawDataCDR3").focus()
             } else {
                 $("#outcomeOfWDrawDataCDR3").hide()
                 $("#outcomeOfWDrawDataCDR3").val('')
             }
         });
         
         $("#outcomeOfWDrawDataCDR1").click(function() {
             if(this.checked) {
                 $("#outcomeOfWDrawDataCDR3").hide()
                 $("#outcomeOfWDrawDataCDR3").val()
             } 
         });
         
         
         });
</script>    


<h2>Section C: Data Recall and Recall Notification (CDR) </h2>

<div class="list">
  <table>
    <tbody>



      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateProcessingStarted">Date processing started:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'dateProcessingStarted', 'errors')}">

    <g:jqDatePicker name="dateProcessingStarted" value="${caseWithdrawFormsInstance?.dateProcessingStarted}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="datedataWdrawn"><g:message code="caseWithdrawInstance.dateVerified.label" default="Date of data recall:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'datedataWdrawn', 'errors')}">

    <g:jqDatePicker name="datedataWdrawn" value="${caseWithdrawFormsInstance?.datedataWdrawn}" />
    </td>
    </tr>





    <tr class="prop">
      <td valign="top" class="name">
        <label for="outcomeOfWDrawnData">Outcome of recall data:</label>
      </td>
      <td  class="value  ${hasErrors(bean: caseWithdrawFormsInstance, field: 'outcomeOfWDrawnData', 'errors')}">
        <div>
          <g:radio name="outcomeOfWDrawnData" id="outcomeOfWDrawDataCDR1" value="Deleted from active files and sequestered" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Deleted from active files and sequestered'}"/> <label class="radio" for="outcomeOfWDrawDataCDR1">Deleted from active files and sequestered</label><br>

          <g:radio name="outcomeOfWDrawnData" id="outcomeOfWDrawDataCDR2" value="Other" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'}"/> <label class="radio" for="outcomeOfWDrawDataCDR2">Other</label><br>

          <g:textField id="outcomeOfWDrawDataCDR3"  style="display:${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'?'display':'none'}" name="outcomeOfWDrawDataOther" value="${caseWithdrawFormsInstance?.outcomeOfWDrawDataOther}" />&nbsp;</td>
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
        <label for="comments">Comments:</label><br />
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
        <label for="dateVerified"><g:message code="caseWithdrawFormsInstance.dateVerified.label" default="Date of verification:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'dateVerified', 'errors')}">

    <g:jqDatePicker name="dateVerified" value="${caseWithdrawFormsInstance?.dateVerified}" />
    </td>
    </tr>



    </tbody>
  </table>
</div>


