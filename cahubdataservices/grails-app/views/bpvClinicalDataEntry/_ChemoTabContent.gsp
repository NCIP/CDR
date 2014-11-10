  <div id="ChemoTableContent">
    <table class="tabledatecomments">
      <tr>
        <th class="descentrywide">Description</th>
        <th class="dateentrywide">Date of Chemotherapy <br />or Time since receiving date of chemotherapy (Yrs)</th>
        <th class="editOnly textcenter">Delete</th>
      </tr>
      <g:each in="${bpvClinicalDataEntryInstance.therapyRecords}" status="i" var="therapyRecord">
        <g:if test="${therapyRecord.typeOfTherapy == 'chemo'}">
          <g:if test="${i%2 == 0}">
              <tr class="even" id="chemo_${therapyRecord.id}">
          </g:if>
          <g:else>
              <tr class="odd" id="chemo_${therapyRecord.id}">
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
                            
                <td class="editOnly textcenter"><g:remoteLink class="button ui-button  ui-state-default ui-corner-all removepadding" action="deleteTherapy" onSuccess="activateCahubDatePickers('chemoTherb4SurgDt')" value="Delete" update="ChemoTableContent"  params="'therType=Chemo'" id="${therapyRecord.id}" >
                  <span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
              </tr>
              </g:if>
      </g:each>
              <tr class="prop hide" id="chemoTherb4SurgDescRow">
                <td colspan="3" class="subform"> 
                  <div class="subformquestion clearfix ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'chemoTherb4SurgDesc', 'errors')}">
                    <div class="subformlbl">
                      <label for="chemoTherb4SurgDesc">Describe chemotherapy the Participant received prior to surgery:</label>
                    </div>
                    <div class="subformentry">
                      <g:textArea name="chemoTherb4SurgDesc" maxlength="255"/>
                    </div>
                  </div>
                  <div class="subformquestion clearfix ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'chemoTherb4SurgDt', 'errors')}">
                    <div class="subformlbldte">
                      <label for="chemoTherb4SurgDt">When chemotherapy was received:</label>
                      <span data-msg="Pick the date if known or Enter how long ago, in the text box" class="cahub-tooltip"></span> 
                    </div>
                    <div class="subformentrydte">    
                       <g:jqDatePicker name="chemoTherb4SurgDt"/> <b>Or</b> Time since when chemotherapy was received (in Years): <g:textField size="3" maxlength="3" name="chemoTherb4SurgEst" onkeyup="isNumericValidation(this)" />
                    </div>
                  </div> 
                </td>
              </tr>
    </table>
  </div>
