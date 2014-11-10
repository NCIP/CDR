<div id="responseTableDiv">
    <g:hasErrors bean="${deviationInstance}">
        <div class="errors">
            <g:renderErrors bean="${deviationInstance}" as="list" />
        </div>
    </g:hasErrors>
    <g:if test="${deviationInstance.queryResponses}">    
        <table>
            <tbody>
                <g:each in="${deviationInstance.queryResponses}" status="i" var="queryResponse">
                    <tr class="${i % 2 == 0 ? 'even' : 'odd'}">
                        <td>
                            <b><i>${queryResponse.responder}</i></b> added response <span class="responseTime"><g:formatDate format="MM/dd/yyyy HH:mm" date="${queryResponse?.dateCreated}" /></span><br /><br />
                            ${queryResponse.response?.replace('\n', '<br />')}
                        </td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </g:if>
</div>
