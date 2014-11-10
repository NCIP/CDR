                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="orgCode">Organization Code:</label>
                                </td>
                                <td valign="top" class="value">${session?.org.code}
                                    <g:hiddenField name="orgCode" value="${session?.org.code}" />
                                </td>
                            </tr>
                    
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bpvStudyConsent">BPV ELSI sub-study Consent:</label>
                                    <span data-msg="Choose the appropriate <b>Consent</b> option for the BPV ELSI sub-study. Note: An option of <b>None</b> or <b>Declined</b> will auto-select <b>No Survey Administered</b> option below." class="cahub-tooltip"></span>
                                </td>
                                <td valign="top" class="value">
                                  <div>
                                    <g:radio name="studyConsent" id="consented" value="Consented"/>&nbsp;<label for="consented">Consented</label><br/>
                                    <g:radio name="studyConsent" id="declined" value="Declined" />&nbsp;<label for="declined">Declined</label><br/>
                                    <g:radio name="studyConsent" id="noconsent" value="None" />&nbsp;<label for="noconsent">None</label>
                                  </div>                                     
                                </td>
                            </tr>                    
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="surveyChoice">Survey Type:</label>
                                    <span data-msg="Choose <b>Donor</b> or <b>Non-Donor</b> if <b>Consent</b> was given." class="cahub-tooltip"></span>
                                </td>
                                <td valign="top" class="value">
                                    <g:radio name="surveyChoice" id="stDonor" value="Donor" checked="" /> <label for="stDonor">Donor</label><br />
                                    <g:radio name="surveyChoice" id="stNonDonor" value="Non-Donor" /> <label for="stNonDonor">Non-Donor</label><br />
                                    <g:radio name="surveyChoice" id="stNone" value="No-Survey" /> <label for="stNone">No Survey Administered</label>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="orgCode">Comments*:</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:textArea name="comments" cols="40" rows="5" value="${interviewRecordInstance?.comments}" />
                                    <br />
                                    <span class="no-phi-note">*No PHI allowed in this field</span>
                                </td>
                            </tr>                            
                        </tbody>
                    </table>
