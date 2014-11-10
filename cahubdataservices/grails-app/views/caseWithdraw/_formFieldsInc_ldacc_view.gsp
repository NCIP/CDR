
<h2>Section C: Specimen/Data recall and recall notification FORM (${caseWithdrawFormsInstance?.authority})</h2>
<div class="list">
  <table>
    <tbody>


      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateProcessingStarted">Date processing started</label>
        </td>
        <td valign="top">

    <g:jqDatePicker name="dateProcessingStarted" value="${caseWithdrawFormsInstance?.dateProcessingStarted}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="ackRcvdYesNO">Acknowledged receipt of message of specimen and data recall:</label>
      </td>
      <td >

        <div>
          <g:radio name="ackRcvdYesNOldacc" id="ackRcvdYesNOldacc_yes" value="YES" checked="${caseWithdrawFormsInstance?.ackRcvdYesNO == 'YES'}"/> <label class="radio" for="ackRcvdYesNO1">YES</label><br>

          <g:radio name="ackRcvdYesNOldacc" id="ackRcvdYesNOldacc_no" value="NO" checked="${caseWithdrawFormsInstance?.ackRcvdYesNO == 'NO'}"/> <label class="radio" for="ackRcvdYesNO2">NO</label>
        </div>


      </td>
    </tr>

    <g:if test="${caseWithdrawFormsInstance?.ackRcvdYesNO}">

      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateAckRcvd"><g:message code="withdrawalOfConsent.dateAckRcvd.label" default="Date acknowledged:" /></label>
        </td>
        <td>
      <g:formatDate date="${caseWithdrawFormsInstance?.dateSubmitted}" />
      </td>
      </tr>
    </g:if>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateSpeciWithdraw"><g:message code="withdrawalOfConsent.dateSpeciWithdraw.label" default="Date of specimen withdrawal:" /></label>
      </td>
      <td valign="top">

    <g:jqDatePicker name="dateSpeciWithdraw" value="${caseWithdrawFormsInstance?.dateSpeciWithdraw}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="outcomeOfSpecimen">Outcome of withdrawn specimen:</label>
      </td>
      <td  class="value ${hasErrors(bean: caseWithdrawFormsInstance, field: 'outcomeOfSpecimen', 'errors')}">
        <div>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_1" value="Destroyed" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Destroyed'}"/> <label class="radio" for="outcomeOfSpeci1">Destroyed</label><br>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_2" value="Sequestered and remove from active biobank" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Sequestered and remove from active biobank'}"/> <label class="radio" for="outcomeOfSpeci2">Sequestered and remove from active biobank</label><br>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_3" value="Returned to BSS" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Returned to BSS'}"/> <label class="radio" for="outcomeOfSpeci3">Returned to BSS</label><br>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_4" value="Returned to donor" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Returned to donor'}"/> <label class="radio" for="outcomeOfSpeci4">Returned to donor</label><br>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_5" value="Returned to Donor family" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Returned to Donor family'}"/> <label class="radio" for="outcomeOfSpeci5">Returned to Donor family</label><br>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_6" value="No residual specimen available" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'No residual specimen available'}"/> <label class="radio" for="outcomeOfSpeci6">No residual specimen available</label><br>
          <g:radio name="outcomeOfSpecimenldacc"  id="outcomeOfSpecimenldacc_7" value="Shipped to other location, specify" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Shipped to other location, specify'}"/> <label class="radio" for="outcomeOfSpeci7">Shipped to other location, specify</label><br>

          <g:textField id="outcomeOfSpeci8"  style="display:${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Shipped to other location, specify'?'display':'none'}" name="specShippedTo" value="${caseWithdrawFormsInstance?.specShippedTo}" />&nbsp;<br>

          <g:radio name="outcomeOfSpecimen" id="outcomeOfSpeci9" value="Other" checked="${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Other'}"/> <label class="radio" for="outcomeOfSpeci9">Other</label><br>

          <g:textField id="outcomeOfSpeci10"  style="display:${caseWithdrawFormsInstance?.outcomeOfSpecimen == 'Other'?'display':'none'}" name="outcomeOfSpecimenOther" value="${caseWithdrawFormsInstance?.outcomeOfSpecimenOther}" />&nbsp;
        </div>
      </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="datedataWdrawn"><g:message code="withdrawalOfConsent.dateVerified.label" default="Date of data withdrawal:" /></label>
      </td>
      <td valign="top">

    <g:jqDatePicker name="datedataWdrawn" value="${caseWithdrawFormsInstance?.datedataWdrawn}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="outcomeOfWDrawnData">Outcome of withdrawn data:</label>
      </td>
      <td>
        <div>
          <g:radio name="outcomeOfWDrawnDataldacc" id="outcomeOfWDrawnDataldacc_1" value="Deleted from active files and sequestered" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Deleted from active files and sequestered'}"/> <label class="radio" for="outcomeOfWDrawData1">Deleted from active files and sequestered</label><br>

          <g:radio name="outcomeOfWDrawnDataldacc" id="outcomeOfWDrawnDataldacc_2" value="Other" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'}"/> <label class="radio" for="outcomeOfWDrawData2">Other</label><br>

          <g:textField  id="outcomeOfWDrawData3" style="display:${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'?'display':'none'}" name="outcomeOfWDrawDataOther" value="${caseWithdrawFormsInstance?.outcomeOfWDrawDataOther}" />&nbsp;
        </div>
      </td>
    </tr>



    <tr class="prop">
      <td valign="top" class="name">
        <label for="comments">Comments: </label>
      </td>
      <td valign="top">
    <g:textArea name="comments" cols="40" rows="5" value="${caseWithdrawFormsInstance?.comments}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="verifiedBy">Verified by name: </label>
      </td>
      <td valign="top" class="value  ${hasErrors(bean: caseWithdrawFormsInstance, field: 'verifiedBy', 'errors')}">

${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}
      </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="verifiedByRole">Verified by role: </label>
      </td>
      <td valign="top">
    <g:textField name="verifiedByRole" value="${caseWithdrawFormsInstance?.verifiedByRole}"  />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateVerified"><g:message code="withdrawalOfConsent.dateVerified.label" default="Date of verification:" /></label>
      </td>
      <td valign="top">
    <g:formatDate date="${caseWithdrawFormsInstance?.dateVerified}" />
    </td>
    </tr>


    </tbody>
  </table>
</div>

