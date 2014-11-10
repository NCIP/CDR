<g:set var="fcount" value="${1}" />
<g:if test="${queryInstance.queryAttachments}">
  <div id="attachmentTableDiv">
    <table>
      <thead>
      <th>File name</th>
      <th>Category</th>
      <g:if test="${session.org.code == 'OBBR'}">
        <th><nobr> Hide From BSS</nobr></th>
      </g:if>
      <th><nobr>Uploaded by</nobr></th>
      <th>Upload date</th>
      <th>Comments</th>
      </thead>
      <tbody>
      <g:each in="${queryInstance.queryAttachments}" status="i" var="attachment">
        <!--pmh change 08/19/13 -->
        <g:if test="${attachment.fileUpload?.hideFromBss == true && session.org.code != 'OBBR'}"><!--dont show file for a BSS if 'hide from BSS' is selected --> </g:if>
        <g:else>
          <g:set var="fcount" value="${fcount + 1}" />
            <tr class="${(fcount % 2) == 0 ? 'odd' : 'even'}">
            <td>
          <g:link controller="query" action="download" id="${queryInstance.id}" params="[attachmentId:attachment.id]">${attachment.fileUpload?.fileName}</g:link>
          <g:if test="${(session.org?.code == 'OBBR' && session.DM && queryInstance?.queryStatus?.code != 'CLOSED') || (session.org?.code != 'OBBR' && queryInstance?.queryStatus?.code == 'ACTIVE')}">
            <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" controller="query" update="attachmentTableDiv" action="remove" id="${queryInstance.id}" params="[attachmentId:attachment.id]" before="if(!confirm('Are you sure to remove the file?')) return false"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
          </g:if>
          </td>
          <td>${attachment.fileUpload?.category}</td>
           <g:if test="${session.org.code == 'OBBR'}">
           <td><nobr>${attachment.fileUpload?.hideFromBss ?'Yes':'No'}</nobr></td>
          </g:if>
          <td>${attachment.uploadedBy}</td>
          <td><nobr><g:formatDate format="MM/dd/yyyy HH:mm" date="${attachment.dateCreated}" /></nobr></td>
          <td>${attachment.fileUpload?.comments}</td>
          </tr>
        </g:else>
        <!--END pmh change 08/19/13 -->
      </g:each>
      </tbody>
    </table>
  </div>
</g:if>
