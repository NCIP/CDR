<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%  def labelNumber = 1%>
<div id="bbf-feedback-rec" class="div-t">
  <div class="div-t-h">Section A: Assessment</div>
  <div class="div-t-r">
    <div class="left width-60">
      <label for="contentTempOnArr">${labelNumber++}. Temperature of contents upon arrival: </label>
      <span class="bbfsmalltext">(For 'Other', please describe in comment box below)</span>
      <div class="value ${hasErrors(bean: brainBankFeedbackInstance, field: 'contentTempOnArr', 'errors')}">
        <g:radio  id="tarr1" name="contentTempOnArr"  value="cold" checked="${brainBankFeedbackInstance.contentTempOnArr =='cold'}" /><label for="tarr1">Cold</label> &nbsp;&nbsp;&nbsp;
        <g:radio id="tarr2" name="contentTempOnArr"  value="roomtemp" checked="${brainBankFeedbackInstance.contentTempOnArr =='roomtemp'}"  /><label for="tarr2">Room Temp</label>&nbsp;&nbsp;&nbsp;
        <g:radio id="tarr3" name="contentTempOnArr"  value="other" checked="${brainBankFeedbackInstance.contentTempOnArr =='other'}"  /><label for="tarr3">Other </label>
      </div>
    </div>
    <div class="right ${hasErrors(bean: brainBankFeedbackInstance, field: 'wholeBrainWt', 'errors')}">
      <label for="wholeBrainWt">${labelNumber++}. Whole Brain weight: </label>
      <g:textField size="5" maxlength="10" name="wholeBrainWt" class="numFloat" value="${brainBankFeedbackInstance?.wholeBrainWt}" /> Grams
    </div>
    <div class="clear"></div>
  </div>
  <div class="div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'comments', 'errors')}">
    <label for="comments">${labelNumber++}. Comments:</label><br>
    <textarea id="comments" name="comments" class="textwide">${brainBankFeedbackInstance.comments}</textarea>
  </div>
  <div class="div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'missingStructExists', 'errors')}">
    <label for="missingStructExists">${labelNumber++}. Are there any missing structures?</label>
    <div class="value">
      <g:radio  id="msyes" name="missingStructExists"  value="Yes" checked="${brainBankFeedbackInstance.missingStructExists =='Yes'}" /><label for="msyes">Yes</label> &nbsp;&nbsp;&nbsp;
      <g:radio id="msno" name="missingStructExists"  value="No" checked="${brainBankFeedbackInstance.missingStructExists =='No'}" /><label for="msno">No</label>
    </div>
  </div>
  <div id="addstructure" class="div-t-r depends-on ${hasErrors(bean: brainBankFeedbackInstance, field: 'missingStructExists', 'errors')}" data-id="msyes">   
    <label>${labelNumber-1} a) If yes to above, please list missing structures:</label>
    <div class="checkboxlist">
      <g:each in="${pslist}" status="j" var="st">
        <g:checkBox name="stchkbox_${st.id}" id="stchkbox_${st.id}" value="${st.preSelected}" /><label for="stchkbox_${st.id}">${st.structName}</label>
      </g:each>
    </div>
    <input type="hidden" name="structName" value="" id="structName" />
    <a id="add1" title="Add other missing structures" class="uibutton"><span class="ui-icon ui-icon-plusthick"></span>Add</a>
        <ul class="nobullet" id ="editstructlist">
      <g:if test="${showmslist == true && brainBankFeedbackInstance.missingStructExists =='Yes'}">
        <g:each in="${mslist}" status="i" var="ms">
          <g:if test="${ms.userInput == true}">
            <li id="row${ms.id}">
              <g:textField size="30" maxlength="255" name="editstructName_${ms.id}"  value="${ms.structName}"/>
              <a class="delete-struct ui-button ui-state-default ui-corner-all removepadding" data-deleteid="${ms.id}" title="delete" action="removeStructure"  ><span class="ui-icon ui-icon-trash">Remove</span></a>
            </li>
          </g:if>
        </g:each>
      </g:if>
        </ul>
      <div class="clear"></div>
  </div>
  <div class="div-t-r list">
    <label for="damComments">${labelNumber++}. Please note any observations of damage to whole brain and brain structures upon gross inspection: </label><br />
    <a id="add2" title="Add a damage type" class="uibutton"><span class="ui-icon ui-icon-plusthick"></span>Add</a>
    <input type="hidden" id="damageRegion" name="damageRegion" value=""/>
    <input type="hidden" id="damageObservation" name="damageObservation" value=""/>
      <table id="damageRegionTable" name="damComments">
     <g:if test="${showgi}">
       <thead><tr class="prop" ><th class="label">Region of Brain</th><th>Observations</th><th class="action"></th></tr></thead><tbody>
        <g:each in="${damlist}" status="k" var="dam">
          <g:if test="${dam.studyType.equals('gross inspection') && dam.userInput == true }">
            <tr class="prop" id="row${dam.id}" >
              <td><g:textField size="30" maxlength="255"  name="editDamRegion_${dam.id}"  value="${dam.region}"/></td>
              <td><g:textArea name="editDamObservation_${dam.id}" value="${dam.observation}" cols="30" rows="2"/> </td>
              <td><a class="delete-damage ui-button ui-state-default ui-corner-all removepadding" data-deleteid="${dam.id}" title="delete" action="removeDamage"  ><span class="ui-icon ui-icon-trash">Remove</span></a></td>
            </tr>
          </g:if>
        </g:each>
       </tbody>
      </g:if>
       </table>
  </div>
  <div class="div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'acceptForFurtherProc', 'errors')}">
    <label for="acceptForFurtherProc">${labelNumber++}. Upon gross evaluation, were the partial/whole brain and brain structures acceptable for further processing for the GTEx project? </label><br />
    <div class="value">
       <g:radio  id="procyes" name="acceptForFurtherProc"  value="Yes" checked="${brainBankFeedbackInstance.acceptForFurtherProc =='Yes'}" /><label for="procyes">Yes</label> &nbsp;&nbsp;&nbsp;
       <g:radio id="procno" name="acceptForFurtherProc"  value="No" checked="${brainBankFeedbackInstance.acceptForFurtherProc =='No'}" /><label for="procno">No</label>
    </div>
  </div>
  <div class="div-t-r depends-on ${hasErrors(bean: brainBankFeedbackInstance, field: 'noLeftHemisProc', 'errors')}" data-id="procno">
    <label for="noLeftHemisProcReason"> If No for left hemisphere, please explain:</label><br>
    <textarea name="noLeftHemisProcReason" class="textwide">${brainBankFeedbackInstance?.noLeftHemisProcReason}</textarea>
  </div>
  <div class="div-t-r depends-on ${hasErrors(bean: brainBankFeedbackInstance, field: 'noRightHemisProc', 'errors')}" data-id="procno">
    <label for="noRightHemisProcReason">If No for right hemisphere, please explain:</label><br>
    <textarea  name="noRightHemisProcReason" class="textwide">${brainBankFeedbackInstance?.noRightHemisProcReason}</textarea>
  </div>
  <div class="div-t-r depends-on ${hasErrors(bean: brainBankFeedbackInstance, field: 'wholeBrainNoProc', 'errors')}" data-id="procno">
    <label for="wholeBrainNoProcReason">If No for whole brain, please explain:</label><br>
    <textarea  name="wholeBrainNoProcReason" class="textwide">${brainBankFeedbackInstance?.wholeBrainNoProcReason}</textarea>
  </div>
  <div class="div-t-h">Section B: Processing and Storage</div>  
  <div class="div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'wasBrainProcImmed', 'errors')}">
    <label for="wasBrainProcImmed">${labelNumber++}. Was the Brain processed immediately? </label> 
    <div class="value">
       <g:radio  id="procYesImmedYes" name="wasBrainProcImmed"  value="Yes" checked="${brainBankFeedbackInstance.wasBrainProcImmed =='Yes'}" /><label for="procYesImmedYes">Yes</label> &nbsp;&nbsp;&nbsp;
       <g:radio id="procYesImmedNo" name="wasBrainProcImmed"  value="No" checked="${brainBankFeedbackInstance.wasBrainProcImmed =='No'}" /><label for="procYesImmedNo">No</label>
    </div>
  </div>
  <div class="depends-on" data-id="procYesImmedNo" id="procImmedNo">
      <label for="justtext" class="subentry">If Brain not processed immediately, please explain how brain & brain contents were stored: </label>
      <div class="subentry div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'storedImmed', 'errors')}">
        <label for="storedImmed">Stored Immediately:</label>
        <div class="value subentry">
           <g:radio  id="siYes" name="storedImmed"  value="Yes" checked="${brainBankFeedbackInstance.storedImmed =='Yes'}" /><label for="siYes">Yes </label>&nbsp;&nbsp;&nbsp;
           <g:radio id="siNo" name="storedImmed"  value="No" checked="${brainBankFeedbackInstance.storedImmed =='No'}" /><label for="siNo">No </label>
        </div>
      </div>
      <div class="subentry div-t-r depends-on ${hasErrors(bean: brainBankFeedbackInstance, field: 'storedImmedType', 'errors')}"  data-id="siYes">
          <label for="storedImmedType"> Storage type:</label>
          <span>(For 'Other' or 'In Formalin at RT', please describe in comment box below)</span>
          <div class="value subentry">
              <g:radio id="store2" name="storedImmedType"  value="-80" checked="${brainBankFeedbackInstance.storedImmedType =='-80'}" /><label for="store2">At -80˚C </label>&nbsp;&nbsp;&nbsp;
              <g:radio id="store3" name="storedImmedType"  value="Formalin at RT" checked="${brainBankFeedbackInstance.storedImmedType =='Formalin at RT'}" /> <label for="store3">In Formalin at RT  </label>&nbsp;&nbsp;&nbsp;
              <g:radio id="store4" name="storedImmedType"  value="other" checked="${brainBankFeedbackInstance.storedImmedType =='other'}"  /><label for="store4">Other  </label>
          </div>
      </div>
      <div class="div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'storedInComments', 'errors')}" >
        <label for="storedInComments" class="subentry">Comments:</label>
        <g:textArea class="textwide" name="storedInComments" cols="40" rows="5" value="${brainBankFeedbackInstance.storedInComments}"/><br />
        <span class="subentry editonly">*Please note if whole brain was found to be unacceptable for further processing please go to SECTION C.</span>
      </div>
  </div>
  <div class="div-t-r">
    <div class="left">
      <label for="processDate">${labelNumber++}. Date of processing:</label>
      <div class="value ${hasErrors(bean: brainBankFeedbackInstance, field: 'wasBrainProcImmed', 'errors')}">
          <g:jqDatePicker LDSOverlay="false" name="processDate" value="${brainBankFeedbackInstance?.processDate}" />
      </div>
    </div>
    <div class="right ${hasErrors(bean: brainBankFeedbackInstance, field: 'wasBrainProcImmed', 'errors')}">
      <label for="processedBy">${labelNumber++}. Name of staff processing:</label><br />
      <g:textField size="25" name="processedBy" maxlength="255"  value="${brainBankFeedbackInstance?.processedBy}" /> 
    </div>
    <div class="clear"></div>
  </div>
  <div class="div-t-r list">
    <label for="damComments">${labelNumber++}. Please note any abnormalities observed upon histopathologic evaluation of the brain for processing: </label><br />
    <a id="add3" title="Add new Abnormality" class="uibutton"><span class="ui-icon ui-icon-plusthick"></span>Add</a>
    <input type="hidden" name="hpRegion" id="hpRegion" />
    <input type="hidden" name="hpObservation" id="hpObservation"/>
    <table id="hpRegionTable"> 
    <g:if test="${showhp}">
      <thead><tr class="prop" ><th class="label">Region of Brain</th><th> Observations</th><th class="action"></th></tr></thead>
      <tbody><g:each in="${damlist}" status="k" var="dam">
        <g:if test="${dam.studyType.equals('histopathological evaluation') && dam.userInput == true }">
          <tr class="prop " id="row${dam.id}">
            <td><g:textField size="30" maxlength="255"  name="editHpRegion_${dam.id}"  value="${dam.region}"/></td>
            <td><g:textArea name="editHpObservation_${dam.id}" value="${dam.observation}" cols="30" rows="2"/></td>
            <td><a class="delete-damage ui-button ui-state-default ui-corner-all removepadding" data-deleteid="${dam.id}" title="delete" action="removeDamage"  ><span class="ui-icon ui-icon-trash">Remove</span></a></td>
          </tr>
        </g:if>
      </g:each></tbody>
    </g:if>
    </table>
  </div>
  <div class="div-t-h">Section C: Feedback Form QA Verification</div> 
  <div class="div-t-r">
    <div class="left width-35">
      <label for="qaPerson1">Name of person completing information:</label>
    </div>
    <div class="left width-30 ${hasErrors(bean: brainBankFeedbackInstance, field: 'qaPerson1', 'errors')}">
      <g:textField name="qaPerson1" maxlength="255" size="25" value="${brainBankFeedbackInstance?.qaPerson1}" />
    </div>
    <div class="right width-30 ${hasErrors(bean: brainBankFeedbackInstance, field: 'qaPerson1', 'errors')}"> <label for="qaDate1"> Date: </label> 
      <g:jqDatePicker LDSOverlay="false" name="qaDate1" value="${brainBankFeedbackInstance?.qaDate1}" /> 
    </div><div class="clear"></div>
  </div>
  <div class="div-t-r">
    <div class="left width-35">
      <label for="qaPerson2">Name of another person verifying information: </label>
    </div>
    <div class="left width-30 ${hasErrors(bean: brainBankFeedbackInstance, field: 'qaPerson2', 'errors')}">
      <g:textField name="qaPerson2" maxlength="255" size="25" value="${brainBankFeedbackInstance?.qaPerson2}" />
    </div>
     <div class="right width-30 ${hasErrors(bean: brainBankFeedbackInstance, field: 'qaPerson2', 'errors')}"><label for="qaDate2"> Date: </label> 
      <g:jqDatePicker LDSOverlay="false" name="qaDate2" value="${brainBankFeedbackInstance?.qaDate2}" /> 
    </div><div class="clear"></div>
  </div>
  <div class="div-t-r ${hasErrors(bean: brainBankFeedbackInstance, field: 'qaComments', 'errors')}">
    <label for="qaComments">Additional Comments:</label><br />
    <g:textArea class="textwide" name="qaComments" cols="40" rows="5" value="${brainBankFeedbackInstance?.qaComments}" />
  </div>
  <div class="div-t-h">Section D: Neuropathology Report</div>
  <div class="div-t-r">
    <label for="uploadedFile">Neuropathology Report:</label>
    <g:if test="${brainBankFeedbackInstance.uploadedFile?.isInteger()}">
      <g:each in="${FileUpload?.findAllById(brainBankFeedbackInstance.uploadedFile)}" status="i" var="fileUploadInstance">
        <div class="value ${hasErrors(bean: brainBankFeedbackInstance, field: 'uploadedFile', 'errors')}">
          <g:link title="Download ${fileUploadInstance.fileName}" controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link>
          <a title="Delete ${fileUploadInstance.fileName}" data-deleteid="${brainBankFeedbackInstance.id}" class="delete-file ui-button ui-state-default ui-corner-all removepadding" title="delete"><span class="ui-icon ui-icon-trash">Remove</span></a>
        </div>
      </g:each>
    </g:if>
    <g:else>
      <div class="value ${hasErrors(bean: brainBankFeedbackInstance, field: 'uploadedFile', 'errors')}">
        <input type ="file" name="fileName" id="fileName" size="25"  value=""/>
        <g:if test="${brainBankFeedbackInstance.uploadedFile && !brainBankFeedbackInstance.uploadedFile?.isInteger()}"> <span class="bbfsmalltext">(${brainBankFeedbackInstance.uploadedFile} initially entered is not a valid file type)</span></g:if>
      </div>
    </g:else>
  </div>
</div>

