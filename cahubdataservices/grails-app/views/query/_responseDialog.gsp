<div id="responseDialog" title="Add response">
    <g:formRemote name="responseForm" url="[controller: 'query', action: 'addResponse']" update="responseTableDiv">
        <g:hiddenField name="id" value="${queryInstance?.id}" />
        <g:textArea class="textwide" name="response" cols="40" rows="5" />
        <div class="button clearfix ui-corner-all">
            <g:submitButton class="left" name="saveResponseBtn" value="Save" />
            <input type="button" class="left" id="cancelResponseBtn" value="Cancel" />
        </div>
    </g:formRemote>
</div>
