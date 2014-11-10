  <div id="HRTTableContent">
    <table class="tabledatecomments">
      <tr>
        <th class="dateentry">Form</th>
        <th class="textcenter">If other, specify here.</th>
        <th class="textcenter">Type</th>
        <th class="textcenter">Duration (Months)</th>
        <th class="textcenter">Time since last usage (Yrs)</th>
        <th class="textcenter editOnly">Delete</th>
      </tr>
      <g:each in="${bpvClinicalDataEntryInstance.therapyRecords}" status="i" var="therapyRecord">
        <g:if test="${therapyRecord.typeOfTherapy == 'HRT'}">
          <g:if test="${i%2 == 0}">
              <tr class="even" id="hrt_${therapyRecord.id}">
          </g:if>
          <g:else>
              <tr class="odd" id="hrt_${therapyRecord.id}">
          </g:else>  
                <td>${therapyRecord.hrtForm}</td>
                <g:if test="${therapyRecord.specOtherHRTForm}">
                   <td class="textcenter">${therapyRecord.specOtherHRTForm}</td>
                </g:if>
                <g:else>
                  
                  <td class="textcenter">N/A</td>
                </g:else>
                
                
                <td>${therapyRecord.hrtType}</td>
                <td class="textcenter">${therapyRecord.durationMonths}</td>
                <td class="textcenter">${therapyRecord.noOfYearsStopped}</td>
                <td class="textcenter editOnly"><g:remoteLink class="button ui-button  ui-state-default ui-corner-all removepadding" action="deleteTherapy" value="Delete" update="HRTTableContent"  params="'therType=HRT'" id="${therapyRecord.id}" >
                  <span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink></td>
              </tr>
              </g:if>
            </g:each>
              <tr id="hrtAddNew" class="hide">
                <td class="textcenter"><g:select name="formHorReplaceTher" from="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTForm?.values()}" keys="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTForm?.values()*.name()}" /></td>
                <td class="textcenter"><g:textField size="30" maxlength="255" name="formHRTOtherSpecification"/></td>
                <td class="textcenter"><g:select name="typeHorReplaceTher" from="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTType?.values()}" keys="${nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord$HRTType?.values()*.name()}" /></td>
                <td class="textcenter"><g:textField size="4" maxlength="4" name="hormRTDur" onkeyup="isNumericValidation(this)" /></td><td class="textcenter"><g:textField size="4" maxlength="4" name="hormRTLast" onkeyup="isNumericValidation(this)"/></td><td></td>
              </tr>
     </table>
  </div>
