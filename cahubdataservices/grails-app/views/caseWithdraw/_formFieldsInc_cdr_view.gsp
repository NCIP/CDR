

<h2>Section C: Data Withdrawal and Withdrawal Notification (CDR) </h2>
<div class="list">
  <table>
    <tbody>



      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateProcessingStarted">Date processing started</label>
        </td>
        <td valign="top" >

    <g:jqDatePicker name="dateProcessingStarted" value="${caseWithdrawFormsInstance?.dateProcessingStarted}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="datedataWdrawn"><g:message code="caseWithdrawInstance.dateVerified.label" default="Date of data recall:" /></label>
      </td>
      <td valign="top" >

    <g:jqDatePicker name="datedataWdrawn" value="${caseWithdrawFormsInstance?.datedataWdrawn}" />
    </td>
    </tr>





    <tr class="prop">
      <td valign="top" class="name">
        <label for="outcomeOfWDrawnData">Outcome of recall data:</label>
      </td>
      <td >
        <div>
          <g:radio name="outcomeOfWDrawnDatacdr" id="outcomeOfWDrawnDatacdr_1" value="Deleted from active files and sequestered" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Deleted from active files and sequestered'}"/> <label class="radio" for="outcomeOfWDrawDataCDR1">Deleted from active files and sequestered</label><br>

          <g:radio name="outcomeOfWDrawnDatacdr" id="outcomeOfWDrawnDatacdr_2" value="Other" checked="${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'}"/> <label class="radio" for="outcomeOfWDrawDataCDR2">Other</label><br>

          <g:textField id="outcomeOfWDrawDataCDR3"  style="display:${caseWithdrawFormsInstance?.outcomeOfWDrawnData == 'Other'?'display':'none'}" name="outcomeOfWDrawDataOther" value="${caseWithdrawFormsInstance?.outcomeOfWDrawDataOther}" />&nbsp;</td>
      </div>

      </td>
    </tr>



    <tr class="prop">
      <td colspan="2" >
        <label for="comments">Comments:</label><br />
    <g:textArea class="textwide" name="comments" cols="40" rows="5" value="${caseWithdrawFormsInstance?.comments}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="verifiedBy">Verified by name: </label>
      </td>
      <td valign="top" >

${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}
      </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="verifiedByRole">Verified by role: </label>
      </td>
      <td valign="top" >
    <g:textField name="verifiedByRole" value="${caseWithdrawFormsInstance?.verifiedByRole}"  />
    </td>
    </tr>




    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateVerified"><g:message code="caseWithdrawFormsInstance.dateVerified.label" default="Date of verification:" /></label>
      </td>
      <td valign="top" >
    <g:formatDate date="${caseWithdrawFormsInstance?.dateVerified}" />
    </td>
    </tr>


    </tbody>
  </table>
</div>


<g:if test="${fuploadcdr}">

  <div class="list">
    <table>
      <thead>
        <tr>

          <th>File Name</th>
          <th class="dateentry">Date Uploaded</th>
          <th> </th> 
        </tr>
      </thead>
      <tbody>
      <g:each in="${fuploadcdr}" status="i" var="fileUploadInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
        <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

        </tr>
      </g:each>
      </tbody>
    </table>
  </div>

</g:if>
