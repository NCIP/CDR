  <div id="IrradTableContent">
    <table class="tabledatecomments">
      <tr>
        <th class="descentrywide">Description</th>
        <th class="dateentrywide">Date of radiation therapy <br />or Time since receiving radiation therapy (Yrs)</th>
        <th class="textcenter editOnly">Delete</th>
      </tr>
        <g:each in="${bpvClinicalDataEntryInstance.therapyRecords}" status="i" var="therapyRecord">
          <g:if test="${therapyRecord.typeOfTherapy == 'radiation'}">
            <g:if test="${i%2 == 0}">
             <tr class="even" id="irrad_${therapyRecord.id}">
            </g:if>
            <g:else>
              <tr class="odd" id="irrad_${therapyRecord.id}">
            </g:else>
                <td>${therapyRecord.descTherapy}</td>
                <td class="textcenter">
		    <g:formatDate date="${therapyRecord.therapyDate}" format="MM/dd/yyyy" />${therapyRecord.howLongAgo}
                </td>
                <td class="textcenter editOnly"><g:remoteLink class="button ui-button  ui-state-default ui-corner-all removepadding" action="deleteTherapy" onSuccess="activateCahubDatePickers('irradTherb4SurgDt')" value="Delete" update="IrradTableContent"  params="'therType=Irrad'" id="${therapyRecord.id}" > <%--params="'therapyId='+${therapyRecord.id}" --%>
                  <span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
              </tr>
            </g:if>
         </g:each>
                <tr class="prop clearborder hide" id="irradTherb4SurgDescRow">
                  <td colspan="3" class="subform">
                    <div class="subformquestion clearfix">
                      <div class="subformlbl">
                        <label for="irradTherb4SurgDesc">Describe radiation therapy the Participant received prior to surgery:</label>
                      </div>
                      <div class="subformentry">
                        <g:textArea name="irradTherb4SurgDesc" maxlength="255" />
                      </div>
                    </div>
                    <div class="subformquestion clearfix">
                      <div class="subformlbldte">
                        <label for="irradTherb4SurgDt">When radiation therapy was received:</label>
                      <span data-msg="Pick the date if known or Enter how long ago, in the text box" class="cahub-tooltip"></span> 
                      </div>
                      <div class="subformentrydte">    
                        <g:jqDatePicker name="irradTherb4SurgDt" /> <b>Or</b> Time since when radiation therapy was received (in Years): <g:textField size="3" maxlength="3" name="irradTherb4SurgEst" onkeyup="isNumericValidation(this)" />
                      </div>
                    </div>          
                  </td>
                </tr>
    </table>
  </div>
