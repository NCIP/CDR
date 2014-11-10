
<h2>Section D: Final Reviews </h2>
<div class="list">
  <table>

    <thead>

      <tr>

        <th>Reviewer</th>
        <th>Category</th>
        <th>File Name</th>
        <th>Date Uploaded</th>



      </tr>
    </thead>

    <tbody>
      <!-- ELR REVIEWER FILE UPLOAD DETAILS -->

    <g:if test="${caseWithdrawInstance?.elrReviewer && fuploadelr }">





      <g:each in="${fuploadelr}" status="i" var="fileUploadInstance">
        <tr class="prop">
          <td>
${caseWithdrawInstance?.elrReviewer}

          </td>

          <td> ELR</td>
          <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
        <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>


      </g:each>


      </tr>

    </g:if>
    <g:else>
      <tr class="prop">
        <td>
${caseWithdrawInstance?.elrReviewer}

        </td>

        <td> ELR</td>
        <td>NA</td>
        <td>NA</td>

    </g:else>

    <!-- OPS REVIEWER FILE UPLOAD DETAILS -->
    <g:if test="${caseWithdrawInstance?.opsReviewer && fuploadops}">




      <g:each in="${fuploadops}" status="i" var="fileUploadInstance">
        <tr class="prop">
          <td>
${caseWithdrawInstance?.opsReviewer}

          </td>

          <td> OPS</td>

          <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
        <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>


      </g:each>


      </tr>

    </g:if>
    <g:else>
      <tr class="prop">
        <td>
${caseWithdrawInstance?.opsReviewer}

        </td>

        <td> OPS</td>
        <td>NA</td>
        <td>NA</td>

    </g:else>
    <!-- QM REVIEWER FILE UPLOAD DETAILS -->
    <g:if test="${caseWithdrawInstance?.qmReviewer && fuploadqm}">

      <g:each in="${fuploadqm}" status="i" var="fileUploadInstance">
        <tr class="prop">
          <td>
${caseWithdrawInstance?.qmReviewer}

          </td>

          <td> QM</td>
          <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
        <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>


      </g:each>


      </tr>

    </g:if>
    <g:else>
      <tr class="prop">
        <td>
${caseWithdrawInstance?.qmReviewer}

        </td>

        <td> QM</td>
        <td>NA</td>
        <td>NA</td>

    </g:else>

    <!-- DIRECTOR REVIEWER FILE UPLOAD DETAILS -->
    <g:if test="${caseWithdrawInstance?.directorReviewer && fuploaddirector}">

      <g:each in="${fuploaddirector}" status="i" var="fileUploadInstance">
        <tr class="prop">
          <td>
${caseWithdrawInstance?.directorReviewer}

          </td>

          <td> DIRECTOR</td>
          <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
        <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>


      </g:each>


      </tr>

    </g:if>
    <g:else>
      <tr class="prop">
        <td>
${caseWithdrawInstance?.directorReviewer}

        </td>

        <td> DIRECTOR</td>
        <td>NA</td>
        <td>NA</td>

    </g:else>



    </tbody>

  </table>
</div>


