

            <div class="dialog3">
              <h3>Section B.  Medical History</h3>
              <div class="list">                 
                <table>
                    <tbody>
                    
                     
                    
                        <tr class="prop">
                            <td valign="top" style="width:300px;" class="name"><g:message code="medicalHistory.source.label" default="Primary History Source" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: medicalHistoryInstance, field: "source")}</td>
                            
                        </tr>
                    
                        <g:if test="${medicalHistoryInstance?.primary}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="medicalHistory.primary.label" default="Primary" /></td>
                            
                            <td valign="top" class="value">${medicalHistoryInstance?.primary?.encodeAsHTML()}</td>
                            
                        </tr>
                        </g:if>
                    
                        <g:if test="${medicalHistoryInstance?.otherPrimary}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="medicalHistory.otherPrimary.label" default="Other Primary" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: medicalHistoryInstance, field: "otherPrimary")}</td>
                            
                        </tr>
                        </g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="medicalHistory.nonMetastaticCancer.label" default="Is there a history of non-metastatic cancer" /></td>
                            
                            <td valign="top" class="value">${medicalHistoryInstance?.nonMetastaticCancer?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                      
                    
                       
                    
                    </tbody>
                </table>
            </div>
            </div>
