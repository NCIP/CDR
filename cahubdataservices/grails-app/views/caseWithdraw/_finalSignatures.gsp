<h2>Section D: Final Review by ${authority}</h2>
<div class="list">
  <table>
    <tbody>
    

    <g:if test="${authority=='ELR'}">
      <tr class="prop">
        <td valign="top" class="name">
          <label for="elrReviewer"><g:message code="caseWithdrawInstance.elrReviewer.label" default="Reviewer" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'elrReviewer', 'errors')}">

      <g:textField name="elrReviewer"  value="${caseWithdrawInstance?.elrReviewer}" />
      </td>
      </tr>
    </g:if>



    <g:if test="${authority=='QM'}">
      <tr class="prop">
        <td valign="top" class="name">
          <label for="qmReviewer"><g:message code="caseWithdrawInstance.qmReviewer.label" default="Reviewer" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'qmReviewer', 'errors')}">

      <g:textField name="qmReviewer"  value="${caseWithdrawInstance?.qmReviewer}" value="${caseWithdrawInstance?.qmReviewer}"/>
      </td>
      </tr>
    </g:if>

    <g:if test="${authority=='OPS'}">
      <tr class="prop">
        <td valign="top" class="name">
          <label for="opsReviewer"><g:message code="caseWithdrawInstance.opsReviewer.label" default="Reviewer" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'opsReviewer', 'errors')}">

      <g:textField name="opsReviewer"   value="${caseWithdrawInstance?.opsReviewer}"/>
      </td>
      </tr>
    </g:if>



    <g:if test="${authority=='DIRECTOR'}">
      <tr class="prop">
        <td valign="top" class="name">
          <label for="directorReviewer"><g:message code="caseWithdrawInstance.directorReviewer.label" default="Reviewer" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'directorReviewer', 'errors')}">

      <g:textField name="directorReviewer"   value="${caseWithdrawInstance?.directorReviewer}"/>
      </td>
      </tr>
    </g:if>

    </tbody>
  </table>
</div>

