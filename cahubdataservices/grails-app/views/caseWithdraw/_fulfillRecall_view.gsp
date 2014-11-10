


<h2>Section E: Notification of Case Recall Request Fulfilled</h2>

<div class="list">
  <table>
    <tbody>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="finalCanNotifyDate"><g:message code="caseWithdrawInstance.bssNotifyDonNokDt.label" default="Date BSS can notify Donor or NOK:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'finalCanNotifyDate', 'errors')}">

    <g:jqDatePicker name="finalCanNotifyDate" value="${caseWithdrawInstance?.finalCanNotifyDate}" />
    </td>
    </tr>




    <tr class="prop fnotify" >
      <td valign="top" class="name">
        <label for="finalNotifyviaEmail"><g:message code="caseWithdrawInstance.finalNotifyviaEmail.label" default="Method of notification:" /></label>
      </td>

      <td  class="value ${hasErrors(bean: caseWithdrawInstance, field: 'finalNotifyviaEmail', 'errors')}">
        <div>
          <g:checkBox  id ="fnot1" name="finalNotifyviaEmail" value="${caseWithdrawInstance?.finalNotifyviaEmail}" checked="${caseWithdrawInstance?.finalNotifyviaEmail}" /> <label for="fnot1">Email</label><br>
          <g:checkBox  id ="fnot2" name="finalNotifyviaMail" value="${caseWithdrawInstance?.finalNotifyviaMail}" checked="${caseWithdrawInstance?.finalNotifyviaMail}" /> <label for="fnot2">Mail</label><br>
          <g:checkBox  id ="fnot3" name="finalNotifyviaPhone" value="${caseWithdrawInstance?.finalNotifyviaPhone}" checked="${caseWithdrawInstance?.finalNotifyviaPhone}" /> <label for="fnot3">Phone</label><br>

          <g:checkBox  id ="fnot4" name="finalNotifyviaOth" value="${caseWithdrawInstance?.finalNotifyviaOth}" checked="${caseWithdrawInstance?.finalNotifyviaOth}"/> <label for="fnot4">Other - Specify</label><br>

          <g:textField  id="fnot5"  style="display:${caseWithdrawInstance?.finalNotifyviaOth ?'display':'none'}" name="finalNotifyOthType" value="${caseWithdrawInstance?.finalNotifyOthType}" />&nbsp;
        </div>
      </td>
    </tr>



    <tr class="prop">
      <td valign="top" class="name">
        <label for="finalNotifiedDate"><g:message code="caseWithdrawInstance.finalNotifiedDate.label" default="Date BSS notifies Donor or NOK:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'finalNotifiedDate', 'errors')}">

    <g:jqDatePicker name="finalNotifiedDate" value="${caseWithdrawInstance?.finalNotifiedDate}" />
    </td>
    </tr>



    <tr class="prop">
      <td valign="top" class="name">
        <label for="finalprovideProofDate"><g:message code="caseWithdrawInstance.finalprovideProofDate.label" default="Date BSS provides proof of recall to Donor or NOK:" /></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'finalprovideProofDate', 'errors')}">

    <g:jqDatePicker name="finalprovideProofDate" value="${caseWithdrawInstance?.finalprovideProofDate}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="finalAnyOtherNotified">Other entities notified: </label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'finalAnyOtherNotified', 'errors')}">
    <g:textArea name="finalAnyOtherNotified" cols="40" rows="5" value="${caseWithdrawInstance?.finalAnyOtherNotified}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="finalAnyOtherNotified">BSS who completed this form: </label>
      </td>
      <td valign="top" >
${caseWithdrawInstance?.finalCompleteBy}
      </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="finalAnyOtherNotified">Date of completion: </label>
      </td>
      <td valign="top" >
    <g:formatDate date="${caseWithdrawInstance?.finalCompleteDate}" />
    </td>
    </tr>
    </tbody>
  </table>
</div>
