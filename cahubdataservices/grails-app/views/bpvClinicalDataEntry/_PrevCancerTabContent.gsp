  <div id="PrevCancerTableContent">
    <table class="tabledatecomments">
      <tr>
        <th class="descentrywide">Description</th>
        <th class="dateentrywide">Date of diagnosis <br />or Time since diagnosis (Yrs)</th>
        <th  class="textcenter editOnly">Delete</th>
      </tr>
      <g:each in="${bpvClinicalDataEntryInstance.therapyRecords}" status="i" var="therapyRecord">
        <g:if test="${therapyRecord.typeOfTherapy == 'prevcancer'}">
          <g:if test="${i%2 == 0}">
              <tr class="even" id="prevcancer_${therapyRecord.id}">
          </g:if>
          <g:else>
              <tr class="odd" id="prevcancer_${therapyRecord.id}">
          </g:else>      
                <td>${therapyRecord.descTherapy}</td>
                <td class="textcenter">
		    <g:if test="${(session.LDS != null && session.LDS == true) || params.action != 'show'}">
			<g:formatDate date="${therapyRecord.therapyDate}" format="MM/dd/yyyy" />${therapyRecord.howLongAgo}
		    </g:if>
		    <g:else>
			<span class="redactedMsg">REDACTED (No LDS privilege)</span>
		    </g:else>
                </td>
                
                <td class="textcenter editOnly"><g:remoteLink class="button ui-button  ui-state-default ui-corner-all removepadding" action="deleteTherapy" onSuccess="activateCahubDatePickers('prevCancerDiagDt')" value="Delete" update="PrevCancerTableContent"  params="'therType=PrevCancer'" id="${therapyRecord.id}" > <%--params="'therapyId='+${therapyRecord.id}" --%>
                  <span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
              </tr>
        </g:if>
      </g:each>
              <tr id="prevCancerSave2" class="hide">
                <td colspan="3" class="subform"> 
                  <div class="subformquestion clearfix">
                    <div class="subformlbl">
                      <label for="prevCancerDiagDesc">Describe history of diagnosis:</label>
                    </div>
                    <div class="subformentry">
                      <g:textArea name="prevCancerDiagDesc" maxlength="255" />
                    </div>
                  </div>
                  <div class="subformquestion clearfix">
                    <div class="subformlbldte">
                      <label for="prevCancerDiagDt">When diagnosis was received:</label>
                      <span data-msg="Pick the date if known or Enter how long ago, in the text box" class="cahub-tooltip"></span>  
                    </div>
                    <div class="subformentrydte">    
                      <g:jqDatePicker name="prevCancerDiagDt" /> <b>Or</b> Time since diagnosis (in Years): <g:textField size="3" maxlength="3" name="prevCancerDiagEst" onkeyup="isNumericValidation(this)" />
                    </div>
                  </div>  
                </td>
              </tr>                            
    </table>
  </div>
