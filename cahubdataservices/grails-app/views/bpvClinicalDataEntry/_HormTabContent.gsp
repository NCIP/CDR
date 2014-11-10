  <div id="HormTableContent">
    <table class="tabledatecomments">
      <tr>
        <th class="descentrywide">Description</th>
        <th class="dateentrywide">Date of hormonal therapy <br />or Time since receiving date of hormonal therapy (Yrs)</th>
        <th class="textcenter editOnly">Delete</th>
      </tr>
      <g:each in="${bpvClinicalDataEntryInstance.therapyRecords}" status="i" var="therapyRecord">
        <g:if test="${therapyRecord.typeOfTherapy == 'hormonal'}">
          <g:if test="${i%2 == 0}">
              <tr class="even" id="horm_${therapyRecord.id}">
          </g:if>
          <g:else>
              <tr class="odd" id="horm_${therapyRecord.id}">
          </g:else>                
                <td>${therapyRecord.descTherapy}</td>
                <td class="textcenter">
		    <g:formatDate date="${therapyRecord.therapyDate}" format="MM/dd/yyyy" />${therapyRecord.howLongAgo}
                </td>
                <td class="textcenter editOnly"><g:remoteLink class="button ui-button  ui-state-default ui-corner-all removepadding" action="deleteTherapy" onSuccess="activateCahubDatePickers('hormTherb4SurgDt')" value="Delete" update="HormTableContent"  params="'therType=Hormonal'" id="${therapyRecord.id}" > <%--params="'therapyId='+${therapyRecord.id}" --%>
                  <span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
              </tr>
          </g:if>
      </g:each>
              <tr class="prop hide" id="hormTherb4SurgDescRow">
                <td colspan="3" class="subform"> 
                  <div class="subformquestion clearfix ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hormTherb4SurgDesc', 'errors')}">
                    <div class="subformlbl">
                      <label for="hormTherb4SurgDesc">Describe hormonal therapy the Participant received prior to surgery:</label>
                    </div>
                    <div class="subformentry">
                      <g:textArea name="hormTherb4SurgDesc" maxlength="255" />
                    </div>
                  </div>
                  <div class="subformquestion clearfix ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hormTherb4SurgDt', 'errors')}">
                    <div class="subformlbldte">
                      <label for="hormTherb4SurgDt">When hormonal therapy was received:</label>
                      <span data-msg="Pick the date if known or Enter how long ago, in the text box" class="cahub-tooltip"></span>  
                    </div>
                    <div class="subformentrydte">    
                      <g:jqDatePicker name="hormTherb4SurgDt"/> <b>Or</b> Time since when hormonal therapy was received (in Years): <g:textField size="3" maxlength="3" name="hormTherb4SurgEst" onkeyup="isNumericValidation(this)" />
                    </div>
                  </div> 
                </td>
              </tr>
    </table>
  </div>
