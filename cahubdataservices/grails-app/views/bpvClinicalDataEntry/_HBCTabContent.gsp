  <div id="HBCTableContent">
    <table class="tabledatecomments">
      <tr>
        <th>Form</th>
        <th class="textcenter">If other, specify here.</th>
        <th class="textcenter">Duration (Months)</th>
        <th class="textcenter">Time since last usage (Yrs)</th>
        <th class="textcenter editOnly">Delete</th>
      </tr>
      <g:each in="${bpvClinicalDataEntryInstance.therapyRecords}" status="i" var="therapyRecord">
        <g:if test="${therapyRecord.typeOfTherapy == 'HBC'}">
          <g:if test="${i%2 == 0}">
              <tr class="even" id="hbc_${therapyRecord.id}">
          </g:if>
          <g:else>
              <tr class="odd" id="hbc_${therapyRecord.id}">
          </g:else>                
                <td>${therapyRecord.hbcForm}</td>
                <g:if test="${therapyRecord.specOtherHBCForm}">
                   <td class="textcenter">${therapyRecord.specOtherHBCForm}</td>
                </g:if>
                <g:else>
                   <td class="textcenter">N/A</td>
                </g:else>
                
                <td class="textcenter">${therapyRecord.durationMonths}</td>
                <td class="textcenter">${therapyRecord.noOfYearsStopped}</td>
                <td class="editOnly textcenter"><g:remoteLink class="button ui-button  ui-state-default ui-corner-all removepadding" action="deleteTherapy" value="Delete" update="HBCTableContent"  params="'therType=HBC'" id="${therapyRecord.id}" >
                  <span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
              </tr>
        </g:if>
      </g:each>
              <tr id="hbcAddNew" class="hide" >
                 <td><g:select name="formHorBirthControl" from="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HBCForm?.values()}" keys="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HBCForm?.values()*.name()}" /></td>
                 <td class="textcenter"><g:textField size="30" maxlength="255" name="formHBCOtherSpecification"/></td>
                 <td class="textcenter"><g:textField size="4" maxlength="4" name="hormBCDur" onkeyup="isNumericValidation(this)" /></td><td class="textcenter"><g:textField size="4" maxlength="4" name="hormBCLast" onkeyup="isNumericValidation(this)" /></td>
                 <td></td>
              </tr>                
    </table>
  </div>
