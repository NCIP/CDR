<div id="responseTableDiv">
    <g:hasErrors bean="${queryInstance}">
        <div class="errors">
            <g:renderErrors bean="${queryInstance}" as="list" />
        </div>
    </g:hasErrors>
    <g:if test="${queryInstance.queryResponses}">    
        <table>
            <tbody>
                <g:each in="${queryInstance.queryResponses}" status="i" var="queryResponse">
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
