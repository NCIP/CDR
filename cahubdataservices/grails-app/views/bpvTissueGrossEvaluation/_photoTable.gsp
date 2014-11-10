<div id="photoTableDiv">
    <g:if test="${bpvTissueGrossEvaluationInstance.photos}">
        <table>
            <g:each in="${bpvTissueGrossEvaluationInstance.photos}" status="i" var="photo">
                <tr>
                    <td><g:link controller="bpvTissueGrossEvaluation" action="download" id="${bpvTissueGrossEvaluationInstance.id}" params="[photoId:photo.id]">${photo.fileName}</g:link></td>
                    <td class="photoLink"><g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" controller="bpvTissueGrossEvaluation" update="photoTableDiv" action="remove" id="${bpvTissueGrossEvaluationInstance.id}" params="[photoId:photo.id]" before="if(!confirm('Are you sure to remove the file?')) return false"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
                </tr>
            </g:each>
        </table>
    </g:if>
</div>
