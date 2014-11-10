<%@ page import="nci.obbr.cahub.staticmembers.AcquisitionType" %>

<g:set var="labelNumber" value="${1}"/>
<g:set var="caseCreated" value="${bpvConsentEnrollmentInstance?.candidateRecord?.caseRecord != null}" />

<script type="text/javascript">
    $(document).ready(function() {     
        $("input:radio[name=gender]").change(function() {
            if ($("input:radio[name=gender]")[2].checked) {
                $("#otherGenderRow").show()
            } else {
                $("#otherGenderRow").hide()
                $("#otherGender").val('')
            }
        });
        
        $(".race").click(function() {
            if (this.checked) {
                $("#raceNotReported").attr('checked', false)
                $("#raceUnknown").attr('checked', false)
            }
        });
        
        $("#raceNotReported").click(function() {
            if (this.checked) {
                $(".race").attr('checked', false)
                $("#raceUnknown").attr('checked', false)
            }
        });
        
        $("#raceUnknown").click(function() {
            if (this.checked) {
                $(".race").attr('checked', false)
                $("#raceNotReported").attr('checked', false)
            }
        });
        
        $("#africa").click(function() {
            if (!this.checked) {
                $(".africa").attr('checked', false)
            }
        });
        
        $(".africa").click(function() {
            if (this.checked) {
                $("#africa").attr('checked', true)
            } else if (!$("#centralAfrican").attr('checked') && !$("#northAfrican").attr('checked') && !$("#southAfrican").attr('checked')) {
                $("#africa").attr('checked', false)
            }
        });
        
        $("#americas").click(function() {
            if (!this.checked) {
                $(".americas").attr('checked', false)
            }
        });
        
        $(".americas").click(function() {
            if (this.checked) {
                $("#americas").attr('checked', true)
            } else if (!$("#africanAmerican").attr('checked') && !$("#northAmerican").attr('checked') && !$("#latinAmerican").attr('checked') && !$("#caribbean").attr('checked') && !$("#southAmerican").attr('checked')) {
                $("#americas").attr('checked', false)
            }
        });
        
        $("#asia").click(function() {
            if (!this.checked) {
                $(".asia").attr('checked', false)
            }
        });
        
        $(".asia").click(function() {
            if (this.checked) {
                $("#asia").attr('checked', true)
            } else if (!$("#southeastAsian").attr('checked') && !$("#southAsian").attr('checked') && !$("#centralAsian").attr('checked') && !$("#chinese").attr('checked') && !$("#korean").attr('checked') && !$("#japanese").attr('checked') && !$("#indian").attr('checked')) {
                $("#asia").attr('checked', false)
            }
        });
        
        $("#australiaNewZealand").click(function() {
            if (!this.checked) {
                $(".australiaNewZealand").attr('checked', false)
            }
        });
        
        $(".australiaNewZealand").click(function() {
            if (this.checked) {
                $("#australiaNewZealand").attr('checked', true)
            } else if (!$("#australian").attr('checked') && !$("#newZealander").attr('checked')) {
                $("#australiaNewZealand").attr('checked', false)
            }
        });
        
        $("#europe").click(function() {
            if (!this.checked) {
                $(".europe").attr('checked', false)
            }
        });
        
        $(".europe").click(function() {
            if (this.checked) {
                $("#europe").attr('checked', true)
            } else if (!$("#easternEuropean").attr('checked') && !$("#icelandic").attr('checked') && !$("#mediterranean").attr('checked') && !$("#scandinavian").attr('checked') && !$("#westernEuropean").attr('checked')) {
                $("#europe").attr('checked', false)
            }
        });
        
        $("#intercontinental").click(function() {
            if (!this.checked) {
                $(".intercontinental").attr('checked', false)
            }
        });
        
        $(".intercontinental").click(function() {
            if (this.checked) {
                $("#intercontinental").attr('checked', true)
            } else if (!$("#middleEastern").attr('checked') && !$("#hispanic").attr('checked')) {
                $("#intercontinental").attr('checked', false)
            }
        });
        
        $("#otherEthnic").click(function() {
            if (this.checked) {
                $("#otherEthnicStrRow").show()
            } else {
                $("#otherEthnicStrRow").hide()
                $("#otherEthnicStr").val('')
            }
        });
        
        $("#signedDated_yes").change(function() {
            $("#dateConsentedRow").show()
            $("#ageRow").show()
            $("#meetAgeRow").show()
            $("#dateWitnessedRow").show()
            $("#dateVerifiedRow").show()
            $("#consentFormVerRow").show()
            $("#nameObtainedConsentRow").show()
            $("#consentorRelationshipRow").show()
            if ($("#consentorRelationship").val() == 'Other, specify') {
                $("#otherConsentRelationRow").show()
            } else {
                $("#otherConsentRelationRow").hide()
            }
        })

        $("#signedDated_no").change(function() {
            $("#dateConsentedRow").hide()
            $("#dateConsented_cleardate").trigger("click")
            $("#ageRow").hide()
            $("#age").val('')
            $("#meetAgeRow").hide()
            $("#meetAgeBlank").attr('checked', true) // This radio button's value is blank. By checking it, the meetAge field will be set to blank.
            $("#dateWitnessedRow").hide()
            $("#dateWitnessed_cleardate").trigger("click")
            $("#dateVerifiedRow").hide()
            $("#dateVerified_cleardate").trigger("click")
            $("#consentFormVerRow").hide()
            $("#consentFormVer").val('')
            $("#nameObtainedConsentRow").hide()
            $("#nameObtainedConsent").val('')
            $("#consentorRelationshipRow").hide()
            $("#consentorRelationship").val('')
            $("#otherConsentRelationRow").hide()
            $("#otherConsentRelation").val('')
        });
        
        $("#consentorRelationship").change(function() {
            if ($("#consentorRelationship").val() == 'Other, specify') {
                $("#otherConsentRelationRow").show()
            } else {
                $("#otherConsentRelationRow").hide()
                $("#otherConsentRelation").val('')
            }
        });
        
        
         $(":input").change(function(){
                  document.getElementById("changed").value = "Y"
                  //alert("Changed!")
          });
    });
</script>

<g:render template="/formMetadata/timeConstraint" bean="${bpvConsentEnrollmentInstance.formMetadata}" var="formMetadata"/>
<g:render template="/candidateRecord/candidateDetails" bean="${bpvConsentEnrollmentInstance.candidateRecord}" var="candidateRecord" /> 

<div class="list">
    <table class="tdwrap">
        <tbody>
          
            <tr class="prop">
                <td class="name">
                    <label for="inputtedCaseId">${labelNumber++}. BPV Case ID:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'inputtedCaseId', 'errors')} ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'caseId', 'errors')}">
                    <g:if test="${caseCreated}">
                      <g:if test ="${session.DM == true && (bpvConsentEnrollmentInstance.candidateRecord?.caseRecord?.caseStatus?.code == 'DATA' || bpvConsentEnrollmentInstance.candidateRecord?.caseRecord?.caseStatus?.code == 'REMED' || bpvConsentEnrollmentInstance.candidateRecord?.caseRecord?.caseStatus?.code == 'INIT')}">
                          <g:textField name="inputtedCaseId" value="${bpvConsentEnrollmentInstance?.inputtedCaseId}" />
                        </g:if>
                      <g:else>
                          ${bpvConsentEnrollmentInstance?.inputtedCaseId}
                        <g:hiddenField name="inputtedCaseId" value="${bpvConsentEnrollmentInstance?.inputtedCaseId}" />
                    </g:else>
                    </g:if>
                    <g:else>
                        <g:textField name="inputtedCaseId" value="${bpvConsentEnrollmentInstance?.inputtedCaseId}" />
                    </g:else>
                </td>
            </tr>
          
           <g:if test="${bpvConsentEnrollmentInstance.candidateRecord.bss.code == 'UPMC'}">
                                  
            <tr class="prop">
                <td class="name">
                    <label for="localFacility">&nbsp;&nbsp;&nbsp;&nbsp;Please select the local hospital where the surgery occurred (UPMC only):</label>           
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'localFacility', 'errors')} ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'localFacility', 'errors')}">
                    <g:select name="localFacility" from="${['':'Select hospital','shadyside':'Shadyside','presby':'Presbyterian','passavant':'Passavant','magee':'Magee-Women’s']}" value="${bpvConsentEnrollmentInstance?.localFacility}" optionKey="key" optionValue="value"/>
                </td>
            </tr>            

           </g:if>            
            
            <tr class="prop">
                <td class="name">
                    <label for="inputtedKitIds">${labelNumber++}. Kits IDs used:</label>           
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'inputtedKitList', 'errors')} ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'inputtedKitList', 'errors')}">
                    N/A
                    <g:hiddenField name="inputtedKitList" value="N/A" />
                </td>
            </tr>            
            
            <tr class="prop">
                <td class="name">
                    <label for="bssIrbProtocol">${labelNumber++}. Site Protocol Number:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'bssIrbProtocol', 'errors')}">
                    ${bpvConsentEnrollmentInstance?.candidateRecord.bss.protocolSiteNum}
                    <g:hiddenField name="bssIrbProtocol" value="${bpvConsentEnrollmentInstance?.candidateRecord.bss.protocolSiteNum}" />
                </td>
            </tr>

            <tr class="prop">
                <td class="name">
                    <label for="tissueBankId">${labelNumber++}. Tissue Bank ID:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'tissueBankId', 'errors')}">
                  <g:if test="${caseCreated}">
                        <g:if test ="${session.DM == true && ( bpvConsentEnrollmentInstance.candidateRecord?.caseRecord?.caseStatus?.code == 'DATA' || bpvConsentEnrollmentInstance.candidateRecord?.caseRecord?.caseStatus?.code == 'REMED' || bpvConsentEnrollmentInstance.candidateRecord?.caseRecord?.caseStatus?.code == 'INIT')}">
                          <g:textField name="tissueBankId" value="${bpvConsentEnrollmentInstance?.tissueBankId}" />
                        </g:if>
                        <g:else> 
                            ${bpvConsentEnrollmentInstance?.tissueBankId}
                            <g:hiddenField name="tissueBankId" value="${bpvConsentEnrollmentInstance?.tissueBankId}" />
                        </g:else>
                    </g:if>
                    <g:else>
                        <g:textField name="tissueBankId" value="${bpvConsentEnrollmentInstance?.tissueBankId}" />
                    </g:else>
                </td>
            </tr>
            
            <tr class="prop">
                <td class="name">
                    <label for="primaryTissueType">${labelNumber++}. Primary Tissue Type:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'primaryTissueType', 'errors')}">
                    <g:if test="${caseCreated || params.action == 'show'}">
                        ${bpvConsentEnrollmentInstance?.primaryTissueType}
                        <g:hiddenField name="primaryTissueType.id" value="${bpvConsentEnrollmentInstance?.primaryTissueType?.id}" />
                    </g:if>
                    <g:else>
                        <g:select name="primaryTissueType.id" from="${[AcquisitionType.findByCode('COLON'), AcquisitionType.findByCode('KIDNEY'), AcquisitionType.findByCode('LUNG'), AcquisitionType.findByCode('OVARY')]}" optionKey="id" value="${bpvConsentEnrollmentInstance?.primaryTissueType?.id}" noSelection="['':'']" />
                    </g:else>
                </td>
            </tr>

            <tr class="prop">
                <td class="name">
                    <label for="dob">${labelNumber++}. Candidate's date of birth:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'dob', 'errors')}">
                    <g:if test="${(session.LDS != null && session.LDS == true) || params.action != 'show'}">
                        <g:datePicker id="dob" name="dob" value="${bpvConsentEnrollmentInstance?.dob}" precision="month" noSelection="['':'']" years="${1900..2050}" default="none" />
                    </g:if>
                    <g:else>
                        <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                    </g:else>
                </td>
                
            </tr>
            
            <tr><td colspan="2" class="formheader">Gender, Race and Ethnicity</td></tr>

            <tr class="prop">
                <td class="name tdtop">
                    <label for="gender">${labelNumber++}. Candidate's gender:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'gender', 'errors')}">
                    <div>
                        <g:radio name="gender" id="gender1" value="Male" checked="${bpvConsentEnrollmentInstance?.gender == 'Male'}"/>&nbsp;<label for="gender1">Male</label><br /><br />
                        <g:radio name="gender" id="gender2" value="Female" checked="${bpvConsentEnrollmentInstance?.gender == 'Female'}"/>&nbsp;<label for="gender2">Female</label><br /><br />
                        <g:radio name="gender" id="gender3" value="Other, specify" checked="${bpvConsentEnrollmentInstance?.gender == 'Other, specify'}"/>&nbsp;<label for="gender3">Other, specify</label>
                    </div>
                </td>
            </tr>
            
            <tr class="prop" id="otherGenderRow" style="display:${bpvConsentEnrollmentInstance?.gender == 'Other, specify' ? 'display' : 'none'}">
                <td class="name">
                    <label for="otherGender">&nbsp;&nbsp;&nbsp;&nbsp;Specify other gender:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'otherGender', 'errors')}">
                    <g:textField name="otherGender" value="${bpvConsentEnrollmentInstance?.otherGender}" />
                </td>
            </tr>

            <tr class="prop">
                <td class="name tdtop">
                    <label for="race">${labelNumber++}. Candidate's race:</label>
                    <div>
                      <div class="message left">
                        <h5>Candidate's race guide</h5>
                        <p><b>American Indian or Alaska Native</b>: A person having origins in any of the original peoples of North/South America (including Central America) and maintains tribal affiliation or community attachment.</p>
                        <p><b>Asian</b>: A person having origins in any of the original peoples of the Far East, Southeast Asia, or Indian subcontinent, for example, Cambodia, China, India, Japan, Korea, Malaysia, Pakistan, the Philippine Islands, Thailand, and Vietnam.</p>
                        <p><b>White</b>: A person having origins in any of the original peoples of Europe, the Middle East, or North Africa.</p>
                        <p><b>Black or African American</b>: A person having origins in any of the black racial groups of Africa.  Terms such as "Haitian" or "Negro" can be used in addition to "Black or African American".</p>
                        <p><b>Native Hawaiian or other Pacific Islander</b>: A person having origins in any of the original peoples of Hawaii, Guam, Samoa, or other Pacific Islands.</p>
                        <p><b>Not Reported</b>: Not provided or available.</p>
                        <p><b>Unknown</b>: Could not be determined or unsure.</p>
                      </div>
                      <div class="clear"></div>
                    </div>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'race', 'errors')} racevalue">
                    <div>
                        <g:radio name="race" id="race1" value="American Indian or Alaska Native" checked="${bpvConsentEnrollmentInstance?.race == 'American Indian or Alaska Native'}"/> <label for="race1">American Indian or Alaska Native</label><br /><br />
                        <g:radio name="race" id="race2" value="Asian" checked="${bpvConsentEnrollmentInstance?.race == 'Asian'}"/> <label for="race2">Asian</label><br /><br />
                        <g:radio name="race" id="race3" value="White" checked="${bpvConsentEnrollmentInstance?.race == 'White'}"/> <label for="race3">White</label><br /><br />
                        <g:radio name="race" id="race4" value="Black or African American" checked="${bpvConsentEnrollmentInstance?.race == 'Black or African American'}"/> <label for="race4">Black or African American</label><br /><br />
                        <g:radio name="race" id="race5" value="Native Hawaiian or other Pacific Islander" checked="${bpvConsentEnrollmentInstance?.race == 'Native Hawaiian or other Pacific Islander'}"/> <label for="race5">Native Hawaiian or other Pacific Islander</label><br /><br />
                        <g:radio name="race" id="race6" value="Not Reported" checked="${bpvConsentEnrollmentInstance?.race == 'Not Reported'}"/> <label for="race6">Not Reported</label><br /><br />
                        <g:radio name="race" id="race7" value="Unknown" checked="${bpvConsentEnrollmentInstance?.race == 'Unknown'}"/> <label for="race7">Unknown</label>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td class="name tdtop">
                    <label for="ethnicity">${labelNumber++}. Candidate's ethnicity:</label>
                    <div>
                      <div class="message left">
                        <h5>Candidate's ethnicity guide</h5>
                        <p><b>Not Hispanic or Latino</b>: A person not meeting the definition for Hispanic or Latino.</p>
                        <p><b>Hispanic or Latino</b>: A person of Mexican, Puerto Rican, Cuban, Central or South American or other Spanish cultures or origin, regardless of race.</p>
                        <p><b>Not Reported</b>: Not provided or unavailable.</p>
                        <p><b>Unknown</b>: Could not be determined or unsure.</p>
                      </div>
                      <div class="clear"></div>
                    </div>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'ethnicity', 'errors')} ethnicityvalue">
                    <div>
                        <g:radio name="ethnicity" id="ethnicity1" value="Not Hispanic or Latino" checked="${bpvConsentEnrollmentInstance?.ethnicity == 'Not Hispanic or Latino'}"/> <label for="ethnicity1">Not Hispanic or Latino</label><br /><br />
                        <g:radio name="ethnicity" id="ethnicity2" value="Hispanic or Latino" checked="${bpvConsentEnrollmentInstance?.ethnicity == 'Hispanic or Latino'}"/> <label for="ethnicity2">Hispanic or Latino</label><br /><br />
                        <g:radio name="ethnicity" id="ethnicity3" value="Not Reported" checked="${bpvConsentEnrollmentInstance?.ethnicity == 'Not Reported'}"/> <label for="ethnicity3">Not Reported</label><br /><br />
                        <g:radio name="ethnicity" id="ethnicity4" value="Unknown" checked="${bpvConsentEnrollmentInstance?.ethnicity == 'Unknown'}"/> <label for="ethnicity4">Unknown</label>        
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td class="name tdtop">
                    <label for="jewish">${labelNumber++}. Is Candidate of Sephardic or Ashkenazi Jewish heritage?</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'jewish', 'errors')}">
                    <div>
                        <g:radio name="jewish" id="jewish1" value="No" checked="${bpvConsentEnrollmentInstance?.jewish == 'No'}"/>&nbsp;<label for="jewish1">No</label><br /><br />
                        <g:radio name="jewish" id="jewish2" value="Ashkenazi Jewish" checked="${bpvConsentEnrollmentInstance?.jewish == 'Ashkenazi Jewish'}"/>&nbsp;<label for="jewish2">Ashkenazi Jewish</label><br /><br />
                        <g:radio name="jewish" id="jewish3" value="Sephardic Jewish" checked="${bpvConsentEnrollmentInstance?.jewish == 'Sephardic Jewish'}"/>&nbsp;<label for="jewish3">Sephardic Jewish</label><br /><br />
                        <g:radio name="jewish" id="jewish4" value="Unknown" checked="${bpvConsentEnrollmentInstance?.jewish == 'Unknown'}"/>&nbsp;<label for="jewish4">Unknown</label>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td class="name tdtop">
                    <label>${labelNumber++}. Candidate ethnic background (geographic):</label>
                  <span id="bpvconsentenrollment.geoethnicbackground" class="vocab-tooltip"></span>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'africa', 'errors')}">
                    <div>
                        <g:checkBox name="africa" value="${bpvConsentEnrollmentInstance?.africa}" />&nbsp;<label for="africa">Africa</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="africa" name="centralAfrican" value="${bpvConsentEnrollmentInstance?.centralAfrican}" />&nbsp;<label for="centralAfrican">Central African</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="africa" name="northAfrican" value="${bpvConsentEnrollmentInstance?.northAfrican}" />&nbsp;<label for="northAfrican">North African</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="africa" name="southAfrican" value="${bpvConsentEnrollmentInstance?.southAfrican}" />&nbsp;<label for="southAfrican">South African</label><br/><br/>
                        <g:checkBox name="americas" value="${bpvConsentEnrollmentInstance?.americas}" />&nbsp;<label for="americas">Americas</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="americas" name="africanAmerican" value="${bpvConsentEnrollmentInstance?.africanAmerican}" />&nbsp;<label for="africanAmerican">African American</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="americas" name="northAmerican" value="${bpvConsentEnrollmentInstance?.northAmerican}" />&nbsp;<label for="northAmerican">North American</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="americas" name="latinAmerican" value="${bpvConsentEnrollmentInstance?.latinAmerican}" />&nbsp;<label for="latinAmerican">Latin American</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="americas" name="caribbean" value="${bpvConsentEnrollmentInstance?.caribbean}" />&nbsp;<label for="caribbean">Caribbean</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="americas" name="southAmerican" value="${bpvConsentEnrollmentInstance?.southAmerican}" />&nbsp;<label for="southAmerican">South American</label><br/><br/>
                        <g:checkBox name="asia" value="${bpvConsentEnrollmentInstance?.asia}" />&nbsp;<label for="asia">Asia</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="southeastAsian" value="${bpvConsentEnrollmentInstance?.southeastAsian}" />&nbsp;<label for="southeastAsian">Southeast Asian</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="southAsian" value="${bpvConsentEnrollmentInstance?.southAsian}" />&nbsp;<label for="southAsian">South Asian</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="centralAsian" value="${bpvConsentEnrollmentInstance?.centralAsian}" />&nbsp;<label for="centralAsian">Central Asian</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="chinese" value="${bpvConsentEnrollmentInstance?.chinese}" />&nbsp;<label for="chinese">Chinese</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="korean" value="${bpvConsentEnrollmentInstance?.korean}" />&nbsp;<label for="korean">Korean</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="japanese" value="${bpvConsentEnrollmentInstance?.japanese}" />&nbsp;<label for="japanese">Japanese</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="asia" name="indian" value="${bpvConsentEnrollmentInstance?.indian}" />&nbsp;<label for="indian">Indian</label><br/><br/>
                        <g:checkBox name="australiaNewZealand" value="${bpvConsentEnrollmentInstance?.australiaNewZealand}" />&nbsp;<label for="australiaNewZealand">Australia/New Zealand</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="australiaNewZealand" name="australian" value="${bpvConsentEnrollmentInstance?.australian}" />&nbsp;<label for="australian">Australian</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="australiaNewZealand" name="newZealander" value="${bpvConsentEnrollmentInstance?.newZealander}" />&nbsp;<label for="newZealander">New Zealander</label><br/><br/>
                        <g:checkBox name="europe" value="${bpvConsentEnrollmentInstance?.europe}" />&nbsp;<label for="europe">Europe</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="europe" name="easternEuropean" value="${bpvConsentEnrollmentInstance?.easternEuropean}" />&nbsp;<label for="easternEuropean">Eastern European</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="europe" name="icelandic" value="${bpvConsentEnrollmentInstance?.icelandic}" />&nbsp;<label for="icelandic">Icelandic</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="europe" name="mediterranean" value="${bpvConsentEnrollmentInstance?.mediterranean}" />&nbsp;<label for="mediterranean">Mediterranean</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="europe" name="scandinavian" value="${bpvConsentEnrollmentInstance?.scandinavian}" />&nbsp;<label for="scandinavian">Scandinavian</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="europe" name="westernEuropean" value="${bpvConsentEnrollmentInstance?.westernEuropean}" />&nbsp;<label for="westernEuropean">Western European</label><br/><br/>
                        <g:checkBox name="intercontinental" value="${bpvConsentEnrollmentInstance?.intercontinental}" />&nbsp;<label for="intercontinental">Intercontinental</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="intercontinental" name="middleEastern" value="${bpvConsentEnrollmentInstance?.middleEastern}" />&nbsp;<label for="middleEastern">Middle Eastern</label><br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox class="intercontinental" name="hispanic" value="${bpvConsentEnrollmentInstance?.hispanic}" />&nbsp;<label for="hispanic">Hispanic</label><br/><br/>
                        <g:checkBox name="otherEthnic" value="${bpvConsentEnrollmentInstance?.otherEthnic}" />&nbsp;<label for="otherEthnic">Other Ethnic Background</label>
                    </div>
                </td>
            </tr>

            <tr class="prop" id="otherEthnicStrRow" style="display:${bpvConsentEnrollmentInstance?.otherEthnic == true ? 'display' : 'none'}">
                <td class="name">
                    <label for="otherEthnicStr">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other ethnic background:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'otherEthnicStr', 'errors')}">
                    <g:textField name="otherEthnicStr" value="${bpvConsentEnrollmentInstance?.otherEthnicStr}" />
                </td>
            </tr>
            
            <tr><td colspan="2" class="formheader">Consent Information</td></tr>
            
            <tr class="prop">
                <td class="name">
                    <label for="signedDated">${labelNumber++}. Consent form was signed and dated:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'signedDated', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvConsentEnrollmentInstance?.signedDated)}"  name="signedDated" />
                </td>
            </tr>
            
            <tr class="prop" id="dateConsentedRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="dateConsented">${labelNumber++}. Date of Candidate's Consent:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'dateConsented', 'errors')}">
		    <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateConsented" value="${bpvConsentEnrollmentInstance?.dateConsented}" />
                </td>
                
            </tr>
            
            <tr class="prop" id="ageRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="age">${labelNumber++}. Age of Candidate as of Consent date:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'age', 'errors')}">
                    <g:if test="${(session.LDS != null && session.LDS == true) || params.action != 'show'}">
                        <g:textField name="age" value="${bpvConsentEnrollmentInstance?.age}" onblur="${remoteFunction(action: 'updatePackYearsSmoked', options: '[asynchronous: true]', params: '\'age=\' + this.value', id : bpvConsentEnrollmentInstance?.id)} " />
                    </g:if>
                    <g:else>
                        <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                    </g:else>
                </td>
            </tr>
            
            <tr class="prop" id="meetAgeRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="meetAge">${labelNumber++}. Does the Candidate meet the Age of Majority for your Institution/State:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'meetAge', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvConsentEnrollmentInstance?.meetAge)}"  name="meetAge" />
                    <g:radio name="meetAge" id="meetAgeBlank" class="hide" value="" checked="${bpvConsentEnrollmentInstance?.meetAge == ''}"/>
                </td>
            </tr>
            
            <tr class="prop" id="dateWitnessedRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="dateWitnessed">${labelNumber++}. Date of witness of Consent:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'dateWitnessed', 'errors')}">
		    <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateWitnessed" value="${bpvConsentEnrollmentInstance?.dateWitnessed}" />
                </td>
            </tr>
            
            <tr class="prop" id="dateVerifiedRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="dateVerified">${labelNumber++}. Date of Consent verification:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'dateVerified', 'errors')}">
		    <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateVerified" value="${bpvConsentEnrollmentInstance?.dateVerified}" />
                </td>
            </tr>
            
            <tr class="prop" id="consentFormVerRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="consentFormVer">${labelNumber++}. Version of Consent form signed by Candidate:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'consentFormVer', 'errors')}">
                    <g:textField name="consentFormVer" value="${bpvConsentEnrollmentInstance?.consentFormVer}" />
                </td>
            </tr>
            
            <tr class="prop" id="nameObtainedConsentRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="nameObtainedConsent">${labelNumber++}. Consent obtained by:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'nameObtainedConsent', 'errors')}">
                    <g:textField name="nameObtainedConsent" value="${bpvConsentEnrollmentInstance?.nameObtainedConsent}" />
                </td>
            </tr>
            
            <tr class="prop" id="consentorRelationshipRow" style="display:${bpvConsentEnrollmentInstance?.signedDated == 'Yes' ? 'display' : 'none'}">
                <td class="name">
                    <label for="consentorRelationship">${labelNumber++}. Relationship of Consent signer to donor:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'consentorRelationship', 'errors')}">
                    <g:select name="consentorRelationship" from="${['Self', 'Other, specify']}" value="${bpvConsentEnrollmentInstance?.consentorRelationship}" noSelection="['':'']" />
                </td>
            </tr>
            
            <tr class="prop" id="otherConsentRelationRow" style="display:${(bpvConsentEnrollmentInstance?.consentorRelationship == 'Other, specify' && bpvConsentEnrollmentInstance?.signedDated == 'Yes') ? 'display' : 'none'}">
                <td class="name">
                    <label for="otherConsentRelation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other relationship of Consent signer to donor:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'otherConsentRelation', 'errors')}">
                    <g:textField name="otherConsentRelation" value="${bpvConsentEnrollmentInstance?.otherConsentRelation}" />
                </td>
            </tr>
            
            <tr><td colspan="2" class="formheader">IRB Approval</td></tr>

            <tr class="prop">
                <td class="name">
                    <label for="dateIRBApproved">${labelNumber++}. IRB approval date:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'dateIRBApproved', 'errors')}">
                    <g:jqDatePicker name="dateIRBApproved" value="${bpvConsentEnrollmentInstance?.dateIRBApproved}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td class="name">
                    <label for="dateIRBExpires">${labelNumber++}. IRB expiration date:</label>
                </td>
                <td class="value ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'dateIRBExpires', 'errors')}">
                    <g:jqDatePicker name="dateIRBExpires" value="${bpvConsentEnrollmentInstance?.dateIRBExpires}" />
                </td>
            </tr>
            
            <tr><td colspan="2" class="formheader">General Comment </td></tr>
            
            <tr class="prop">
                <td colspan="2" class="name ${hasErrors(bean: bpvConsentEnrollmentInstance, field: 'specifiedLimitations', 'errors')}">
                    <label for="specifiedLimitations">${labelNumber++}. Specify limitations/additions, if any:</label><br />
                    <g:textArea class="textwide" name="specifiedLimitations" cols="40" rows="5" value="${bpvConsentEnrollmentInstance?.specifiedLimitations}" />
                </td>
            </tr>
            
            <g:hiddenField name="willingELSISubStudy" value="n/a" />

        </tbody>
    </table>
</div>
