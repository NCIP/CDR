<g:if test="${deviationInstance.queryAttachments}">
    <div id="attachmentTableDiv">
        <table>
            <thead>
                <th>File name</th>
                <th>Category</th>
                <th><nobr>Uploaded by</nobr></th>
                <th>Upload date</th>
                <th>Comments</th>
            </thead>
            <tbody>
                <g:each in="${deviationInstance.queryAttachments}" status="i" var="attachment">
                    <tr class="${i % 2 == 0 ? 'even' : 'odd'}">
                        <td>
                            <g:link controller="deviation" action="download" id="${deviationInstance.id}" params="[attachmentId:attachment.id]">${attachment.fileUpload?.fileName}</g:link>
                            <g:if test="${(session.org?.code == 'OBBR' && deviationInstance?.queryStatus?.code != 'CLOSED' && deviationInstance?.queryStatus?.code != 'INVALID') || (session.org?.code != 'OBBR' && (deviationInstance?.queryStatus?.code == 'OPEN' || deviationInstance?.queryStatus?.code == 'PROGRESS'))}">
                                <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" controller="deviation" update="attachmentTableDiv" action="remove" id="${deviationInstance.id}" params="[attachmentId:attachment.id]" before="if(!confirm('Are you sure to remove the file?')) return false"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                            </g:if>
                        </td>
                        <td>${attachment.fileUpload?.category}</td>
                        <td>${attachment.uploadedBy}</td>
                        <td><nobr><g:formatDate format="MM/dd/yyyy HH:mm" date="${attachment.dateCreated}" /></nobr></td>
                        <td>${attachment.fileUpload?.comments}</td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </div>
</g:if>
