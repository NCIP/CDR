<script type="text/javascript"> 
    $(document).ready(function() {
        $('#meetCriteria_no').change(function() {
            $('#reasonNotMeetRow').show()
            $("#consentObtained_none").attr('checked', true)
            $('#consentObtainedRow').hide()
            $("#reasonNotConsented12").attr('checked', true)
            $("#reasonNotConsentedRow").hide()
            $("#otherReason").val('')
            $("#nameConsentCandidate").val('')
            $("#surgeryDate_cleardate").trigger("click")
            //$("#surgeryDate").val(null)
            $("#otherReasonRow").hide()
            $('#nameConsentCandidateRow').hide()
            $('#surgeryDateRow').hide()
        });

        $('#meetCriteria_yes').change(function() {
            $('#reasonNotMeetRow').hide()
            $("#reasonNotMeet6").attr('checked', true) // This radio button's value is blank. By checking it, the reasonNotMeet field will be set to blank.
            $('#otherReasonNotMeetRow').hide()
            $('#otherReasonNotMeet').val('')
            $('#consentObtainedRow').show()
        });
        
        $("input:radio[name=reasonNotMeet]").change(function() {
            if ($("input:radio[name=reasonNotMeet]")[4].checked) {
                $("#otherReasonNotMeetRow").show()
            } else {
                $("#otherReasonNotMeetRow").hide()
                $("#otherReasonNotMeet").val('')
            }
        });
        
        $("#consentObtained_yes").change(function() {
            $("#reasonNotConsentedRow").hide()
            $("#reasonNotConsented12").attr('checked', true) // This radio button's value is blank. By checking it, the reasonNotConsented field will be set to blank.
            $("#otherReasonRow").hide()
            $("#otherReason").val('')
            $('#nameConsentCandidateRow').show()
            $('#surgeryDateRow').show()
        });

        $("#consentObtained_no").change(function() {
            $("#reasonNotConsentedRow").show()
            $('#nameConsentCandidateRow').show()
            $("#surgeryDate_cleardate").trigger("click")
            $('#surgeryDateRow').hide()
        });
        
        $("input:radio[name=reasonNotConsented]").change(function() {
            if ($("input:radio[name=reasonNotConsented]")[10].checked) {
                $("#otherReasonRow").show()
            } else {
                $("#otherReasonRow").hide()
                $("#otherReason").val('')
            }
        });
    });
</script>

<g:render template="/formMetadata/timeConstraint" bean="${bpvScreeningEnrollmentInstance.formMetadata}" var="formMetadata"/>
<g:render template="/candidateRecord/candidateDetails" bean="${bpvScreeningEnrollmentInstance.candidateRecord}" var="candidateRecord" /> 

<div class="list">
  <table class="tdwrap">
    <tbody>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="protocolSiteNum">1. Site Protocol Number:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'protocolSiteNum', 'errors')}">
${bpvScreeningEnrollmentInstance?.candidateRecord.bss.protocolSiteNum}
    <g:hiddenField name="protocolSiteNum" value="${bpvScreeningEnrollmentInstance?.candidateRecord.bss.protocolSiteNum}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="nameCreatCandidate">2. Name of person who performed Screening:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'nameCreatCandidate', 'errors')}">
    <g:textField name="nameCreatCandidate" value="${bpvScreeningEnrollmentInstance?.nameCreatCandidate}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="meetCriteria">3. Does the Candidate meet all eligibility criteria defined within the Study Protocol?</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'meetCriteria', 'errors')}">
        <g:radio name="meetCriteria" id="meetCriteria_yes" value="Yes" checked="${bpvScreeningEnrollmentInstance?.meetCriteria == 'Yes'}"/>&nbsp;<label for="meetCriteria_yes">Yes</label>
        <g:radio name="meetCriteria" id="meetCriteria_no" value="No" checked="${bpvScreeningEnrollmentInstance?.meetCriteria == 'No'}"/>&nbsp;<label for="meetCriteria_no">No</label>
        <g:radio name="meetCriteria" id="meetCriteria_none" class="hide" value="" checked="${((bpvScreeningEnrollmentInstance?.meetCriteria != 'Yes')&&(bpvScreeningEnrollmentInstance?.meetCriteria != 'No'))}"/>
    <!--g:bpvYesNoRadioPicker checked="#{(bpvScreeningEnrollmentInstance?.meetCriteria)}"  name="meetCriteria" /-->
    </td>
    </tr>

    <tr class="prop" id="reasonNotMeetRow" style="display:${bpvScreeningEnrollmentInstance?.meetCriteria == 'No' ? 'display' : 'none'}">
      <td colspan="2" class="name ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'reasonNotMeet', 'errors')}">
        <label for="reasonNotMeet" class="subentry">Select reason:</label>
        <div class="subentry value reasonNotMeet">
          <g:radio name="reasonNotMeet" id="reasonNotMeet1" value="Age of majority for institution/state" checked="${bpvScreeningEnrollmentInstance?.reasonNotMeet == 'Age of majority for institution/state'}"/>&nbsp;<label for="reasonNotMeet1">Age of majority for institution/state</label><br/>
          <g:radio name="reasonNotMeet" id="reasonNotMeet2" value="Tumor is metastasis from another tissue or organ" checked="${bpvScreeningEnrollmentInstance?.reasonNotMeet == 'Tumor is metastasis from another tissue or organ'}"/>&nbsp;<label for="reasonNotMeet2">Tumor is metastasis from another tissue or organ</label><br/>
          <g:radio name="reasonNotMeet" id="reasonNotMeet3" value="Size of the tumor (based on pre-operative assessments)" checked="${bpvScreeningEnrollmentInstance?.reasonNotMeet == 'Size of the tumor (based on pre-operative assessments)'}"/>&nbsp;<label for="reasonNotMeet3">Size of the tumor (based on pre-operative assessments)</label><br/>
          <g:radio name="reasonNotMeet" id="reasonNotMeet4" value="Candidate received or is undergoing chemotherapy, radiation therapy or immunotherapy for any previous or current cancer diagnosis" checked="${bpvScreeningEnrollmentInstance?.reasonNotMeet == 'Candidate received or is undergoing chemotherapy, radiation therapy or immunotherapy for any previous or current cancer diagnosis'}"/>&nbsp;<label for="reasonNotMeet4">Candidate received or is undergoing chemotherapy, radiation therapy or immunotherapy for any previous or current cancer diagnosis</label><br/>
          <g:radio name="reasonNotMeet" id="reasonNotMeet5" value="Other, specify" checked="${bpvScreeningEnrollmentInstance?.reasonNotMeet == 'Other, specify'}"/>&nbsp;<label for="reasonNotMeet5">Other, specify</label><br/>
          <g:radio name="reasonNotMeet" id="reasonNotMeet6" class="hide" value="" checked="${bpvScreeningEnrollmentInstance?.reasonNotMeet == ''}"/>
        </div>
      </td>
    </tr>

    <tr class="prop subentry" id="otherReasonNotMeetRow" style="display:${((bpvScreeningEnrollmentInstance?.meetCriteria == 'No') && (bpvScreeningEnrollmentInstance?.reasonNotMeet == 'Other, specify')) ? 'display' : 'none'}">
      <td colspan="2" class="name ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'otherReasonNotMeet', 'errors')}">
        <label for="otherReasonNotMeet">Specify other reason:</label><br />
    <g:textArea class="textwide" name="otherReasonNotMeet" cols="40" rows="5" value="${bpvScreeningEnrollmentInstance?.otherReasonNotMeet}" />
    </td>
    </tr>

    <tr class="prop" id="consentObtainedRow" style="display:${(bpvScreeningEnrollmentInstance?.meetCriteria == 'Yes') ? 'display' : 'none'}"> 
      <td valign="top" class="name">
        <label for="consentObtained">4. Was Consent obtained?</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'consentObtained', 'errors')}">
        <g:radio name="consentObtained" id="consentObtained_yes" value="Yes" checked="${bpvScreeningEnrollmentInstance?.consentObtained == 'Yes'}"/>&nbsp;<label for="consentObtained_yes">Yes</label>
        <g:radio name="consentObtained" id="consentObtained_no" value="No" checked="${bpvScreeningEnrollmentInstance?.consentObtained == 'No'}"/>&nbsp;<label for="consentObtained_no">No</label>
        <g:radio name="consentObtained" id="consentObtained_none" class="hide" value="" checked="${((bpvScreeningEnrollmentInstance?.consentObtained != 'Yes')&&(bpvScreeningEnrollmentInstance?.consentObtained != 'No'))}"/>
    <!--g:bpvYesNoRadioPicker name="consentObtained" checked="#{bpvScreeningEnrollmentInstance?.consentObtained}" /-->
    </td>
    </tr>

    <tr class="prop" id="reasonNotConsentedRow" style="display:${((bpvScreeningEnrollmentInstance?.consentObtained == 'No') && (bpvScreeningEnrollmentInstance?.meetCriteria == 'Yes')) ? 'display' : 'none'}">
      <td colspan="2" class="name ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'reasonNotConsented', 'errors')}">
        <label for="reasonNotConsented" class="subentry">Choose option for reason given:</label>
        <div class="subentry value reasonNotConsented">
          <g:radio name="reasonNotConsented" id="reasonNotConsented1" value="Religious reasons" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Religious reasons'}"/>&nbsp;<label for="reasonNotConsented1">Religious reasons</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented2" value="Not recommended by surgeon" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Not recommended by surgeon'}"/>&nbsp;<label for="reasonNotConsented2">Not recommended by surgeon</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented3" value="Felt Pressured" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Felt Pressured'}"/>&nbsp;<label for="reasonNotConsented3">Felt Pressured</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented4" value="Too distraught at the time" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Too distraught at the time'}"/>&nbsp;<label for="reasonNotConsented4">Too distraught at the time</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented5" value="Needed more time to make decision" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Needed more time to make decision'}"/>&nbsp;<label for="reasonNotConsented5">Needed more time to make decision</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented6" value="Uncomfortable with risks" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Uncomfortable with risks'}"/>&nbsp;<label for="reasonNotConsented6">Uncomfortable with risks</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented7" value="Afraid it may affect surgery" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Afraid it may affect surgery'}"/>&nbsp;<label for="reasonNotConsented7">Afraid it may affect surgery</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented8" value="Disagree with biobanking" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Disagree with biobanking'}"/>&nbsp;<label for="reasonNotConsented8">Disagree with biobanking</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented9" value="Candidate did not show up as scheduled" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Candidate did not show up as scheduled'}"/>&nbsp;<label for="reasonNotConsented9">Candidate did not show up as scheduled</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented10" value="Patient changed their mind after ELSI survey" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Patient changed their mind after ELSI survey'}"/>&nbsp;<label for="reasonNotConsented10">Patient changed their mind after ELSI survey</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented11" value="Other, specify" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Other, specify'}"/>&nbsp;<label for="reasonNotConsented11">Other, specify</label><br/>
          <g:radio name="reasonNotConsented" id="reasonNotConsented12" class="hide" value="" checked="${bpvScreeningEnrollmentInstance?.reasonNotConsented == ''}"/>
        </div>
      </td>
    </tr>

    <tr class="prop subentry" id="otherReasonRow" style="display:${ ((bpvScreeningEnrollmentInstance?.consentObtained == 'No') && (bpvScreeningEnrollmentInstance?.meetCriteria == 'Yes') && (bpvScreeningEnrollmentInstance?.reasonNotConsented == 'Other, specify')) ? 'display' : 'none'}">
      <td colspan="2" class="name ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'otherReason', 'errors')}">
        <label for="otherReason">Specify other reason:</label><br />
    <g:textArea class="textwide" name="otherReason" cols="40" rows="5" value="${bpvScreeningEnrollmentInstance?.otherReason}" />
    </td>
    </tr>

    <tr class="prop" id="nameConsentCandidateRow" style="display:${((bpvScreeningEnrollmentInstance?.consentObtained == 'Yes')||(bpvScreeningEnrollmentInstance?.consentObtained == 'No')) ? 'display' : 'none'}">
      <td valign="top" class="name">
        <label for="nameConsentCandidate">5. Name of person consenting Candidate:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'nameConsentCandidate', 'errors')}">
    <g:textField name="nameConsentCandidate" value="${bpvScreeningEnrollmentInstance?.nameConsentCandidate}" />
    </td>
    </tr>

    <tr class="prop" id="surgeryDateRow" style="display:${((bpvScreeningEnrollmentInstance?.meetCriteria == 'Yes') && (bpvScreeningEnrollmentInstance?.consentObtained == 'Yes')) ? 'display' : 'none'}">
      <td valign="top" class="name">
        <label for="surgeryDate">6. Scheduled date for surgery:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'surgeryDate', 'errors')}">
    <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="surgeryDate" value="${bpvScreeningEnrollmentInstance?.surgeryDate}"/>
    </td>
    </tr>

    <tr class="prop">
      <td colspan="2" class="name ${hasErrors(bean: bpvScreeningEnrollmentInstance, field: 'comments', 'errors')}">
        <label for="comments">7. Comments:</label><br />
    <g:textArea class="textwide" name="comments" cols="40" rows="5" value="${bpvScreeningEnrollmentInstance?.comments}" /><br />
    <span class="subentry no-phi-note">*No PHI allowed in this field</span>
    </td>
    </tr>

    </tbody>
  </table>
</div>
