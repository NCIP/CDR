
<h2>Section D: Final Review by ${authority}</h2>
<div class="list">
  <table>
    <tbody>
    <g:hiddenField name="wid" value="${caseWithdrawInstance.id}" />

    <g:if test="${authority=='ELR'}">
      <tr class="prop">
        <td valign="top" class="name">
          <label for="elrReviewer"><g:message code="caseWithdrawInstance.elrReviewer.label" default="Reviewer" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'elrReviewer', 'errors')}">

      <g:textField name="elrReviewer"  value="${caseWithdrawInstance?.elrReviewer}" />
      </td>
      </tr>

    

      <g:if test="${caseWithdrawInstance.elrfile?.id}">
        <tr class="prop">
          <td valign="top" class="name" >
            <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Download signature file (${caseWithdrawInstance.elrfile?.fileName})" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
            <a href="/cahubdataservices/caseWithdraw/download/${caseWithdrawInstance.elrfile.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></a>&nbsp;&nbsp;&nbsp;

          </td>
        </tr>
        
      </g:if>
      <g:else>
          <tr class="prop">
        <td valign="top" class="name" >
          <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Choose File:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
          <input type="file" name="fileName" id="fileName" size="25"  value="${caseWithdrawInstance.elrfile?.fileName}"/>   
        </td>
      </tr>
        
      </g:else>

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
    
      <g:if test="${caseWithdrawInstance.qmfile?.id}">
        <tr class="prop">
          <td valign="top" class="name" >
            <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Download ${caseWithdrawInstance.qmfile?.fileName}" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
            <a href="/cahubdataservices/caseWithdraw/download/${caseWithdrawInstance.qmfile.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></a>&nbsp;&nbsp;&nbsp;

          </td>
        </tr>
      
      </g:if>
      <g:else>
         <tr class="prop">
        <td valign="top" class="name" >
          <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Choose File:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
          <input type="file" name="fileName" id="fileName" size="25"  value="${caseWithdrawInstance.qmfile?.fileName}"/>   
        </td>
      </tr>
      </g:else>
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

     
      <g:if test="${caseWithdrawInstance.opsfile?.id}">
        <tr class="prop">
          <td valign="top" class="name" >
            <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Download ${caseWithdrawInstance.opsfile?.fileName}" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
            <a href="/cahubdataservices/caseWithdraw/download/${caseWithdrawInstance.opsfile.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></a>&nbsp;&nbsp;&nbsp;

          </td>
        </tr>
       
      </g:if>
      <g:else>
         <tr class="prop">
        <td valign="top" class="name" >
          <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Choose File:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
          <input type="file" name="fileName" id="fileName" size="25" value="${caseWithdrawInstance.opsfile?.fileName}"/>   
        </td>
      </tr>
      </g:else>
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
     
      <g:if test="${caseWithdrawInstance.directorfile?.id}">
        <tr class="prop">
          <td valign="top" class="name" >
            <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Download ${caseWithdrawInstance.directorfile?.fileName}" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
            <a href="/cahubdataservices/caseWithdraw/download/${caseWithdrawInstance.directorfile.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></a>&nbsp;&nbsp;&nbsp;

          </td>
        </tr>
       
      </g:if>
      <g:else>
         <tr class="prop">
        <td valign="top" class="name" >
          <label for="fileName"><g:message code="caseWithdrawInstance.myFile.label" default="Choose File:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'fileName', 'errors')}">
          <input type="file" name="fileName" id="fileName" size="25" value="${caseWithdrawInstance.directorfile?.fileName}"/>   
        </td>
      </tr>
      </g:else>
    </g:if>




    </tbody>
  </table>
</div>

