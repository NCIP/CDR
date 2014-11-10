<%@ page import="nci.obbr.cahub.surveyrecords.BpvElsiCrf" %>
<g:set var="bodyclass" value="bpvElsiCrf search" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="BPV ELSI Search Form" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
        <h1>BPV ELSI Search Form</h1>
        <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
        %{-- g:hasErrors bean="${bpvElsiCrfInstance}">
        <div class="errors">
            <g:renderErrors bean="${bpvElsiCrfInstance}" as="list" />
        </div>
        </g:hasErrors --}%
        <g:form action="search">
          <div class="dialog">
            <table class="tdwrap">
              <tbody>
                <% def i = 0 %>
                <tr class="${((i++ % 2) == 1) ? 'odd':'even' }">
                  <td class="name">
                    <label for="searchById">Search by Interview ID</label>
                  </td>
                  <td>
                    <g:textField  name="interviewId" size="20" maxlength="14" value="${interviewId}" />  
                  </td>
                  <td> 
                    <span class="button">
                    <g:actionSubmit name="byId" value="Go!" action="search"  />
                    </span>
                  </td>
                </tr>
                <g:if test="${session.org.code == 'OBBR'}">
                <tr class="${((i++ % 2) == 1) ? 'odd':'even' }">
                  <td class="name">
                    <label for="searchByBss">Search by Organization</label>
                  </td>
                  <td>
                    <g:select name="bssId" from="${nci.obbr.cahub.staticmembers.BSS.findAllByStudy(nci.obbr.cahub.staticmembers.Study.findByCode("BPV"))}" optionKey="code" optionValue="name" value="${bssId}"  noSelection="${['':'Select One...']}"/>
                  </td>
                  <td> 
                    <span class="button">
                    <g:actionSubmit name="byBss" value="Go!" action="search"  />
                    </span>
                  </td>
                </tr>
                </g:if>
                <tr class="${((i++ % 2) == 1) ? 'odd':'even' }">
                  <td class="name">
                    <label for="searchByStatus">Search by Interview Status</label>
                  </td>
                  <td> %{-- optionKey="InterviewStatus" --}%
                    <g:select name="intrStatus" from="${nci.obbr.cahub.surveyrecords.InterviewRecord.InterviewStatus.list()}" optionKey="key" value="${intrStatus}"  noSelection="${['':'Select One...']}"/>
                  </td>
                  <td> 
                    <span class="button">
                    <g:actionSubmit name="byIntStatus" value="Go!" action="search"  />
                    </span>
                  </td>
                </tr>
                <tr class="${((i++ % 2) == 1) ? 'odd':'even' }">
                  <td class="name">
                    <label for="bySurvStatus">Search by Survey Status</label>
                  </td>
                  <td> %{-- optionKey="InterviewStatus" --}%
                    <g:select name="survStatus" from="${["INPROG" : "In Progress", "COMP": "Complete"]}" optionKey="key" optionValue="value" value="${survStatus}"  noSelection="${['':'Select One...']}"/>
                  </td>
                  <td> 
                    <span class="button">
                    <g:actionSubmit name="bySurvStatus" value="Go!" action="search"  />
                    </span>
                  </td>
                </tr>
                <tr class="${((i++ % 2) == 1) ? 'odd':'even' }">
                  <td class="name">
                    <label for="byCRFStatus">Search by CRF Status</label>
                  </td>
                  <td> %{-- optionKey="InterviewStatus" --}%
                    <g:select name="crfStatus" from="${["INPROG" : "In Progress", "COMP": "Complete"]}" optionKey="key" optionValue="value" value="${crfStatus}"  noSelection="${['':'Select One...']}"/>
                  </td>
                  <td> 
                    <span class="button">
                    <g:actionSubmit name="byCRFStatus" value="Go!" action="search"  />
                    </span>
                  </td>
                </tr>
                <tr class="${((i++ % 2) == 1) ? 'odd':'even' }">
                  <td class="name">
                    <label for="byDateRange">Search by Date Range</label>
                  </td>
                  <td class="value ${hasErrors(field: 'searchFromDate', 'errors')}">
                      <b>From:</b> <g:jqDatePicker name="searchFromDate" value="${searchFromDate}" /> &nbsp; <b>To:</b> <g:jqDatePicker name="searchToDate" value="${searchToDate}" />
                  </td>
                  <td> 
                    <span class="button">
                    <g:actionSubmit name="byCRFStatus" value="Go!" action="search"  />
                    </span>
                  </td>
                </tr>
                      %{--
                      <tr class="prop subentry">
                        <td class="name">
                          <label for="bpvStudyConsent">Study consent: </label>
                        </td>
                        <td class="name">
                          <g:if test="${bpvElsiCrfInstance?.interviewRecord.studyConsent.equals('Consented')}" >
                            ${bpvElsiCrfInstance?.bpvStudyConsent}
                            <g:hiddenField name="bpvStudyConsent" id="bpvStudyConsent" value="${bpvElsiCrfInstance?.bpvStudyConsent}" />
                          </g:if>
                          <g:else>
                            <div>
                              <g:radio name="bpvStudyConsent" id="bpvStudyConsent1" value="Consented" checked="${bpvElsiCrfInstance?.bpvStudyConsent == 'Consented'}" />&nbsp;<label for="bpvStudyConsent1">Consented</label><br/>
                              <g:radio name="bpvStudyConsent" id="bpvStudyConsent2" value="Declined" checked="${bpvElsiCrfInstance?.bpvStudyConsent == 'Declined'}" />&nbsp;<label for="bpvStudyConsent2">Declined</label><br/>
                              <g:radio name="bpvStudyConsent" id="bpvStudyConsent4" value="None" checked="${bpvElsiCrfInstance?.bpvStudyConsent == 'None'}" />&nbsp;<label for="bpvStudyConsent4">None</label>
                            </div>                              
                          </g:else>
                        </td>
                      </tr>
                      <tr class="prop subentry">
                        <td class="name">
                          <label for="daysConsentToInterview">${labelNumber}a. DATE study informed consent completed:</label>
                        </td>
                        <td >Not entered into CDR
                        </td>
                      </tr>
                      <tr class="prop subentry">
                        <td class="name">
                          <label for="daysConsentToInterview">DAYS from informed consent to ELSI completed interview<br />(4a vs. 1a):</label>
                        </td>
                        <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'daysConsentToInterview', 'errors')}">
                          <g:textField class="numInt" name="daysConsentToInterview" size="4" maxlength="2" value="${fieldValue(bean: bpvElsiCrfInstance, field: 'daysConsentToInterview')}" />
                        </td>
                      </tr>
                      <tr class="prop subentry">
                        <td class="name">
                          <label for="daysConsentToInterview">${labelNumber}b. DATE surgery is scheduled to occur:</label>
                        </td>
                                <td >Not entered into CDR
                                </td>
                            </tr>
                    
                            <tr class="prop subentry">
                                <td class="name">
                              <label for="daysInterviewToSurgery">DAYS between ELSI completed interview and surgery (4a vs. 1b):</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'daysInterviewToSurgery', 'errors')}">
                                    <g:textField class="numNegInt" name="daysInterviewToSurgery" size="4" maxlength="4" value="${fieldValue(bean: bpvElsiCrfInstance, field: 'daysInterviewToSurgery')}" />
                                </td>
                            </tr>
                            
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="tumorLocation">${labelNumber}c. Patient tumor location:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'tumorLocation', 'errors')}">
                                    <g:textField name="tumorLocation" value="${fieldValue(bean: bpvElsiCrfInstance, field: 'tumorLocation')}" />
                                </td>
                            </tr>                            
                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="initialContactDate">${++labelNumber}. Date of initial contact with potential interview respondent:</label>
                                </td>
                                <td>Not entered into CDR</td>
                            </tr>
                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="contactResultA">${++labelNumber}. Result of contact with potential interview respondent:<br />(check all that apply)</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'contactResultA', 'errors')}">
                                  <div>
                                  <ul class="checkboxtreecrf">
                                    <li><g:checkBox id ="contactResultA" name="contactResultA" value="${bpvElsiCrfInstance?.contactResultA}" />&nbsp;<label for="contactResultA"><b>a.</b> Patient declines to undergo informed consent procedures for ELSI survey interview<b>*</b></label></li>
                                    <li><g:checkBox id ="contactResultB" name="contactResultB" value="${bpvElsiCrfInstance?.contactResultB}" />&nbsp;<label for="contactResultB"><b>b.</b> Interviewer completes full informed consent procedures with patient:</label>
                                        <ul>
                                            <li><g:checkBox id ="contactResultB1" class="contactResultB" name="contactResultB1" value="${bpvElsiCrfInstance?.contactResultB1}" />&nbsp;<label for="contactResultB1"><b>(1)</b>  Patient provides informed consent for survey interview</label></li>
                                            <li><g:checkBox id ="contactResultB2" class="contactResultB" name="contactResultB2" value="${bpvElsiCrfInstance?.contactResultB2}" />&nbsp;<label for="contactResultB2"><b>(2)</b>  Patient declines informed consent for survey interview<b>*</b></label></li>
                                        </ul>
                                    </li>
                                    <li><g:checkBox id ="contactResultC" name="contactResultC" value="${bpvElsiCrfInstance?.contactResultC}" />&nbsp;<label for="contactResultC"><b>c.</b> Interviewer fails to complete full informed consent procedures with patient:</label>
                                        <ul>
                                            <li><g:checkBox id ="contactResultC1"  class="contactResultC" name="contactResultC1" value="${bpvElsiCrfInstance?.contactResultC1}" />&nbsp;<label for="contactResultC1"><b>(1)</b>  Patient truncates informed consent process and declines informed consent<b>*</b></label></li>
                                            <li><g:checkBox id ="contactResultC2"  class="contactResultC" name="contactResultC2" value="${bpvElsiCrfInstance?.contactResultC2}" />&nbsp;<label for="contactResultC2"><b>(2)</b>  Patient truncates informed consent process and </label>
                                                <ul>
                                                    <li><g:checkBox id ="contactResultC2A"  class="contactResultC2" name="contactResultC2A" value="${bpvElsiCrfInstance?.contactResultC2A}" />&nbsp;<label for="contactResultC2A"><b>(a)</b>  provides future contact information (see CIF) to enable scheduling an interview later</label></li>
                                                    <li><g:checkBox id ="contactResultC2B"  class="contactResultC2" name="contactResultC2B" value="${bpvElsiCrfInstance?.contactResultC2B}" />&nbsp;<label for="contactResultC2B"><b>(b)</b>  refuses to provide future contact information but agrees to contact interviewer in near future</label></li>
                                                    <li><g:checkBox id ="contactResultC2C"  class="contactResultC2" name="contactResultC2C" value="${bpvElsiCrfInstance?.contactResultC2C}" />&nbsp;<label for="contactResultC2C"><b>(c)</b>  refuses to provide future contact information or to accept interviewer phone number<b>*</b></label></li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                  </ul>
                                </div>
                                  <label>* Indicates patient was not enrolled in the ELSI survey interview study</label>
                                </td>
                            </tr>

              <g:if test="${bpvElsiCrfInstance?.interviewRecord.studyConsent.equals('Consented') || bpvElsiCrfInstance?.interviewRecord.studyConsent.equals('Partial')}">   
                            <tr class="prop">
                                <td class="name">
                                    <label for="interviewStartDate">${++labelNumber}. Date patient commenced  interview:</label>
                                </td>
                                <td>Not entered into CDR</td>
                            </tr>

                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="interviewEndDate">${labelNumber}a. Date patient completed interview:</label>
                                </td>
                                <td>Not entered into CDR</td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="interviewStartTime">${labelNumber}b.  Time survey interview commenced:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'interviewStartTime', 'errors')}">
                                    <g:textField class="timeEntry" name="interviewStartTime" size="5" maxlength="5" value="${bpvElsiCrfInstance?.interviewStartTime}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="interviewEndTime">${labelNumber}c. Time survey interview completed:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'interviewEndTime', 'errors')}">
                                    <g:textField class="timeEntry" name="interviewEndTime" size="5" maxlength="5" value="${bpvElsiCrfInstance?.interviewEndTime}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="interviewInterruptReason">${labelNumber}d. Survey interview interrupted because:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'interviewInterruptReason', 'errors')}">
                                    <g:textArea name="interviewInterruptReason" cols="40" rows="5" value="${bpvElsiCrfInstance?.interviewInterruptReason}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="interviewRestartDate">${labelNumber}e.  Date/Time survey recommenced:</label>
                                </td>
                                <td>
                                    <g:textField class="timeEntry" name="interviewRestartTime" size="5" maxlength="5" value="${bpvElsiCrfInstance?.interviewRestartTime}" /> (Date not entered into CDR)
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="interviewRestartEndTime">${labelNumber}f. Time recommenced survey completed:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'interviewRestartEndTime', 'errors')}">
                                    <g:textField class="timeEntry" name="interviewRestartEndTime" size="5" maxlength="5" value="${bpvElsiCrfInstance?.interviewRestartEndTime}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="surveyAdministrationMode">${labelNumber}g. Mode of survey administration:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'surveyAdministrationMode', 'errors')}">
                                  <div>
                                    <g:radio name="surveyAdministrationMode" id="surveyAdministrationMode1" value="Face-to-face" checked="${bpvElsiCrfInstance?.surveyAdministrationMode == 'Face-to-face'}" />&nbsp;<label for="surveyAdministrationMode1">Face-to-face</label><br/>
                                    <g:radio name="surveyAdministrationMode" id="surveyAdministrationMode2" value="Phone" checked="${bpvElsiCrfInstance?.surveyAdministrationMode == 'Phone'}" />&nbsp;<label for="surveyAdministrationMode2">Phone</label><br>
                                    <g:radio name="surveyAdministrationMode" id="surveyAdministrationMode3" value="Both face-to-face and phone" checked="${bpvElsiCrfInstance?.surveyAdministrationMode == 'Both face-to-face and phone'}" />&nbsp;<label for="surveyAdministrationMode3">Both face-to-face and phone</label>
                                  </div>  
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="dataCollectionMethod">${labelNumber}h. Data collection method:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'dataCollectionMethod', 'errors')}">
                                  <div>
                                    <g:radio name="dataCollectionMethod" id="dataCollectionMethod1" value="Direct entry to computer" checked="${bpvElsiCrfInstance?.dataCollectionMethod == 'Direct entry to computer'}" />&nbsp;<label for="dataCollectionMethod1">Direct entry to computer</label><br/>
                                    <g:radio name="dataCollectionMethod" id="dataCollectionMethod2" value="Recorded on written survey" checked="${bpvElsiCrfInstance?.dataCollectionMethod == 'Recorded on written survey'}" />&nbsp;<label for="dataCollectionMethod2">Recorded on written survey</label>
                                  </div>                                     
                                </td>
                            </tr>

                            <tr class="prop">
                                <td class="name">
                                    <label>${++labelNumber}. Date patient re-contacted to schedule an interview:</label>
                                </td>
                                <td>Not entered into CDR</td>
                            </tr>
                            
                            <tr class="prop subentry">
                                <td class="name">
                                    <label>${labelNumber}a. Date & time ELSI survey interview is scheduled to occur:</label>
                                </td>
                                <td>Not entered into CDR</td>
                            </tr>
                            
                            <tr class="prop subentry">
                                <td class="name">
                                    <label>${labelNumber}b. Dates of subsequent re-contact calls:</label>
                                </td>
                                <td>Not entered into CDR</td>
                            </tr>
                            
                            <tr class="prop">
                                <td class="name">
                                    <label for="CIFShredDate">${++labelNumber}. Date & time patient Contact Information Form (CIF) is  shredded:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'CIFShredDate', 'errors')}">
                                    <g:jqDatePicker name="CIFShredDate" value="${bpvElsiCrfInstance?.CIFShredDate}" /><g:textField class="timeEntry" name="CIFShredTime" size="5" maxlength="5" value="${bpvElsiCrfInstance?.CIFShredTime}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="responsesTranscriptionDate">${++labelNumber}. Date patient open-ended interview responses transcription completed:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'responsesTranscriptionDate', 'errors')}">
                                    <g:jqDatePicker name="responsesTranscriptionDate" value="${bpvElsiCrfInstance?.responsesTranscriptionDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="transcriptionVerifiedDate">${labelNumber}a. Date transcription is verified for accuracy:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'transcriptionVerifiedDate', 'errors')}">
                                    <g:jqDatePicker name="transcriptionVerifiedDate" value="${bpvElsiCrfInstance?.transcriptionVerifiedDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="cdrEntryDate">${++labelNumber}. Date patient survey interview data entered into CDR:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'cdrEntryDate', 'errors')}">
                                    <g:jqDatePicker name="cdrEntryDate" value="${bpvElsiCrfInstance?.cdrEntryDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="cdrEntryVerifiedDate">${labelNumber}a. Date data entry in CDR is verified for accuracy:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'cdrEntryVerifiedDate', 'errors')}">
                                    <g:jqDatePicker name="cdrEntryVerifiedDate" value="${bpvElsiCrfInstance?.cdrEntryVerifiedDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="audioRecordEraseDate">${++labelNumber}. Date audio-recording of interview is erased:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'audioRecordEraseDate', 'errors')}">
                                    <g:jqDatePicker name="audioRecordEraseDate" value="${bpvElsiCrfInstance?.audioRecordEraseDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop subentry">
                                <td class="name">
                                    <label for="audioEraseVerifiedDate">${labelNumber}a. Date recording erasure is verified:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'audioEraseVerifiedDate', 'errors')}">
                                    <g:jqDatePicker name="audioEraseVerifiedDate" value="${bpvElsiCrfInstance?.audioEraseVerifiedDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="protocolDeviations">${++labelNumber}. Possible adverse events or protocol deviations reported or noted (include source and description)(notify PI):</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'protocolDeviations', 'errors')}">
                                    <g:textArea name="protocolDeviations" cols="40" rows="5" value="${bpvElsiCrfInstance?.protocolDeviations}" />
                                </td>
                            </tr>
                    </g:if>                        
                            <tr class="prop">
                                <td class="name">
                                    <label for="comments">${++labelNumber}. Comments and notes (do not include any patient direct or indirect identifying information):</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'comments', 'errors')}">
                                    <g:textArea name="comments" cols="40" rows="5" value="${bpvElsiCrfInstance?.comments}" />
                                </td>
                            </tr>
                               <g:if test="${bpvElsiCrfInstance?.formVersion >=2}">                           
                               <tr class="prop">
                                <td class="name">
                                   <label for="referringStudy">${++labelNumber}. Referring Study:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'externalStudySpecify', 'errors')}">
                                  <div>
                                    <g:radio name="referringStudy" id="referringStudy1" value="BPV" checked="${bpvElsiCrfInstance?.referringStudy == 'BPV'}" />&nbsp;<label for="referringStudy1">BPV</label><br/>
                                    <g:radio name="referringStudy" id="referringStudy2" value="External Study" checked="${bpvElsiCrfInstance?.referringStudy == 'External Study'}" />&nbsp;<label for="referringStudy2">External Study</label> &nbsp;&nbsp; &nbsp;&nbsp;Specify:<g:textField  name="externalStudySpecify" size="15"  value="${bpvElsiCrfInstance?.externalStudySpecify}" /><br>
                                    <g:radio name="referringStudy" id="referringStudy3" value="Clinic Referral (Non-Study or Study Unknown)" checked="${bpvElsiCrfInstance?.referringStudy == 'Clinic Referral (Non-Study or Study Unknown)'}" />&nbsp;<label for="referringStudy3">Clinic Referral (Non-Study or Study Unknown)</label>
                                  </div>  
                                </td>
                            </tr>
                               </g:if>
                            <g:else>
                              <tr class="prop">
                                <td class="name">
                                    <label for="referringStudy">${++labelNumber}. Referring Study:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvElsiCrfInstance, field: 'referringStudy', 'errors')}">
                                  <g:textField name="referringStudy" value="${fieldValue(bean: bpvElsiCrfInstance, field: 'referringStudy')}" />
                                </td>
                            </tr>     
                            </g:else>
                           --}%
                        </tbody>
                    </table>
                  </div>
                  <script type="text/javascript" src="/cahubdataservices/js/bpv/bpvelsicrf.js"></script>
                </div>
                %{--
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
                --}%
            </g:form>
        </div>
    </body>
</html>
