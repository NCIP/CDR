<table style="width:800px;">
  <tbody>
    <tbody>
        <tr class="prop">

            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'protocolSiteNum', 'errors')}">
                <label for="protocolSiteNum"><g:message code="IcdGtexRpci.protocolSiteNum.label" default="1. Site Protocol Number" /></label><br />
                <b>${IcdGtexRpciInstance?.candidateRecord.bss.protocolSiteNum}</b>
                <g:hiddenField name="protocolSiteNum" value="${IcdGtexRpciInstance?.candidateRecord.bss.protocolSiteNum}" />
            </td>
            <td valign="top" style="width:33%">
                <label for="candidateRecord.candidateId"><g:message code="IcdGtexRpci.candidateRecord.candidateId.label" default="2. BSS Candidate ID" /></label><br />
                <span style="font-weight:bold;">${IcdGtexRpciInstance?.candidateRecord.candidateId}</span><br />(${IcdGtexRpciInstance?.candidateRecord.caseCollectionType})
            </td>
            
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'consentor', 'errors')}">
                <label for="consentor"><g:message code="IcdGtexRpci.consentor.label" default="3. Person obtaining consent / approaching candidate" /></label><br />
                <g:textField name="consentor" value="${IcdGtexRpciInstance?.consentor}" />
            </td>
           
        </tr>

        <tr class="prop">            
            
            
            <td valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'consentorRelationship', 'errors')}">
              <label for="consentorRelationship"><g:message code="IcdGtexRpci.consentorRelationship.label" default="4. Relationship of consent signer to donor" /></label><br />
                <g:select name="consentorRelationship" from="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_POSTM_OPO?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_POSTM_OPO?.values()*.name()}" value="${IcdGtexRpciInstance?.consentorRelationship}" noSelection="['': '']" /><br/>                              
              <g:textField style="width:120px;" name="otherConsentRelation" value="${IcdGtexRpciInstance?.otherConsentRelation}" /> 
            </td>  
            
             <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'consentObtained', 'errors')}">
                <g:message code="IcdGtexRpci.consentObtained.label" default="5. Was consent obtained?" /><br />
                <g:icdYesNoRadioPicker checked="${(IcdGtexRpciInstance?.consentObtained?.toString())}"  name="consentObtained"/>
            </td>
            
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'dateConsented', 'errors')}">
                <label for="dateConsented"><g:message code="IcdGtexRpci.dateConsented.label" default="6. Date of consent or Date of approach (mm/dd/yyyy)" /></label><br />
                <g:jqDatePicker name="dateConsented" value="${IcdGtexRpciInstance?.dateConsented}" />
            </td>
            
                                             
           
           <script>   
                   function showhide()
                   {
                         var selectedValue = $("#consentorRelationship").val();
                          if (selectedValue == 'OTH') {
                              //$('#otherConsentRelation').val('')
                              //window.alert($('#otherConsentRelation').val())
                              $('#otherConsentRelation').show();
                          }
                          else {
                              $('#otherConsentRelation').val('')
                              $('#otherConsentRelation').hide();
                          }
                   }
                  
                  showhide();
       
                  $('#consentorRelationship').change(function() {
                      showhide();
                  });                                                                          
           </script>                                                                        
            
        </tr>

        <tr class="prop">  
            
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'institutionICDVersion', 'errors')}">
                <label for="institutionICDVersion"><g:message code="IcdGtexRpci.institutionICDVersion.label" default="7. Institutional version number of ICD" /></label><br />
                <g:textField name="institutionICDVersion" value="${IcdGtexRpciInstance?.institutionICDVersion}" />
            </td>
          
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'dateIRBApproved', 'errors')}">
                <label for="dateIRBApproved"><g:message code="IcdGtexRpci.dateIRBApproved.label" default="8. IRB approval date (mm/dd/yyyy)" /></label><br />
                <g:jqDatePicker name="dateIRBApproved" value="${IcdGtexRpciInstance?.dateIRBApproved}" />
            </td>
           
        </tr>
        <tr class="prop">
           <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'dateIRBExpires', 'errors')}">
                <label for="dateIRBExpires"><g:message code="IcdGtexRpci.dateIRBExpires.label" default="9. IRB expiration date (mm/dd/yyyy)" /></label><br />
                <g:jqDatePicker name="dateIRBExpires" value="${IcdGtexRpciInstance?.dateIRBExpires}" />
            </td>
          <td valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'comments', 'errors')}">
            <label for="comments"><g:message code="IcdGtexRpci.comments.label" default="10.Comments" /></label>
            <g:textArea style="width:250px" name="comments" value="${IcdGtexRpciInstance?.comments}" />
          </td>
        </tr>
        
        
    </tbody>
</table>

<div class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'consentObtained', 'errors')}">
<h2>To Be Completed for Consented Participants</h2>
</div>
<table style="width:800px;">
      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'heart', 'errors')}">
              <label for="heart"><g:message code="IcdGtexRpci.heart.label" default="11. Heart" /></label><br />
              <g:select name="heart" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.heart?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'corneasOnly', 'errors')}">
              <label for="corneasOnly"><g:message code="IcdGtexRpci.corneasOnly.label" default="12. Corneas only" /></label><br />
              <g:select name="corneasOnly" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.corneasOnly?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'pericardium', 'errors')}">
              <label for="pericardium"><g:message code="IcdGtexRpci.pericardium.label" default="13. Pericardium" /></label><br />
              <g:select name="pericardium" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.pericardium?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'lungs', 'errors')}">
              <label for="lungs"><g:message code="IcdGtexRpci.lungs.label" default="14. Lungs" /></label><br />
              <g:select name="lungs" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.lungs?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'boneConnective', 'errors')}">
              <label for="boneConnective"><g:message code="IcdGtexRpci.boneConnective.label" default="15. Bone and connective tissue - upper extremities" /></label><br />
              <g:select name="boneConnective" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.boneConnective?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'boneConnectiveUpper', 'errors')}">
               <label for="boneConnectiveUpper"><g:message code="IcdGtexRpci.boneConnectiveUpper.label" default="16. Bone Connective Upper" /></label><br />
              <g:select name="boneConnectiveUpper" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.boneConnectiveUpper?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'boneConnectiveLower', 'errors')}">
              <label for="boneConnectiveLower"><g:message code="IcdGtexRpci.boneConnectiveLower.label" default="17. Bone and connective tissue - lower extremities" /></label><br />
              <g:select name="boneConnectiveLower" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.boneConnectiveLower?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'boneConnectiveVertebral', 'errors')}">
              <label for="boneConnectiveVertebral"><g:message code="IcdGtexRpci.boneConnectiveVertebral.label" default="18. Bone and connective tissue - vertebral bodies" /></label><br />
              <g:select name="boneConnectiveVertebral" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.boneConnectiveVertebral?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'SaphenousVeins', 'errors')}">
              <label for="SaphenousVeins"><g:message code="IcdGtexRpci.saphenousVeins.label" default="19. Saphenous veins" /></label><br />
              <g:select name="SaphenousVeins" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.saphenousVeins?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'liver', 'errors')}">
              <label for="liver"><g:message code="IcdGtexRpci.liver.label" default="20. Liver" /></label><br />
              <g:select name="liver" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.liver?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'femoralVeinAtery', 'errors')}">
              <label for="femoralVeinAtery"><g:message code="IcdGtexRpci.femoralVeinAtery.label" default="21. Femoral veins and arteries" /></label><br />
              <g:select name="femoralVeinAtery" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.femoralVeinAtery?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'kidney', 'errors')}">
              <label for="kidney"><g:message code="IcdGtexRpci.kidney.label" default="22. Kidney" /></label><br />
              <g:select name="kidney" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.kidney?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'nerves', 'errors')}">
              <label for="nerves"><g:message code="IcdGtexRpci.nerves.label" default="23. Nerves" /></label><br />
              <g:select name="nerves" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.nerves?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'pancreasIslet', 'errors')}">
              <label for="pancreasIslet"><g:message code="IcdGtexRpci.pancreasIslet.label" default="24. Pancreas / Islet cells" /></label><br />
              <g:select name="pancreasIslet" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.pancreasIslet?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'heartValves', 'errors')}">
              <label for="heartValves"><g:message code="IcdGtexRpci.heartValves.label" default="25. Heart for valves" /></label><br />
              <g:select name="heartValves" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.heartValves?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'skin', 'errors')}">
              <label for="skin"><g:message code="IcdGtexRpci.skin.label" default="26. Skin" /></label><br />
              <g:select name="skin" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.skin?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'intestines', 'errors')}">
              <label for="intestines"><g:message code="IcdGtexRpci.intestines.label" default="27. Intestines" /></label><br />
              <g:select name="intestines" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.intestines?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'aortoIliacAtery', 'errors')}">
              <label for="aortoIliacAtery"><g:message code="IcdGtexRpci.aortoIliacAtery.label" default="28. Aorto-iliac atery" /></label><br />
              <g:select name="aortoIliacAtery" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.aortoIliacAtery?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'eyes', 'errors')}">
              <label for="eyes"><g:message code="IcdGtexRpci.eyes.label" default="29. Eyes" /></label><br />
              <g:select name="eyes" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.eyes?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'adipose', 'errors')}">
              <label for="adipose"><g:message code="IcdGtexRpci.adipose.label" default="30. Adipose" /></label><br />
              <g:select name="adipose" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.adipose?.name()}"  />
          </td>
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'samplesAsNeeded', 'errors')}">
              <label for="samplesAsNeeded"><g:message code="IcdGtexRpci.samplesAsNeeded.label" default="31. Research samples as needed" /></label><br />
              <g:select name="samplesAsNeeded" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.samplesAsNeeded?.name()}"  />
          </td>
      </tr>

      <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'specifyOtherOrganTissue', 'errors')}">
              <label for="otherOrganTissue"><g:message code="IcdGtexRpci.otherOrganTissue.label" default="32. Other organ/tissue" /></label><br />
              <g:select name="otherOrganTissue" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.otherOrganTissue?.name()}"  />
          </td>
          <td colspan="2" valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'otherOrganTissue', 'errors')}">
              <label for="specifyOtherOrganTissue"><g:message code="IcdGtexRpci.specifyOtherOrganTissue.label" default="33. Specify other organ/tissue" /></label><br />
              <g:textArea style="width:500px;" name="specifyOtherOrganTissue" cols="40" rows="5" value="${IcdGtexRpciInstance?.specifyOtherOrganTissue}" />
          </td>
      </tr>

      <tr class="prop">
          <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'transplantToPerson', 'errors')}">
              34. I make this anatomical gift, if medically acceptable for the purpose of:<br /><br />
              <g:checkBox name="transplantToPerson" value="${IcdGtexRpciInstance?.transplantToPerson}" />
              <label for="transplantToPerson"><g:message code="IcdGtexRpci.transplantToPerson.label" default="Transplantation to another person or persons only." /></label>
          </td>
      </tr>

      <tr class="prop">
          <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'transplantToResearch', 'errors')}">
            <g:checkBox name="transplantToResearch" value="${IcdGtexRpciInstance?.transplantToResearch}" />
            <label for="transplantToResearch"><g:message code="IcdGtexRpci.transplantToResearch.label" default="Transplantation, research, education of the advancement of science." /></label>
          </td>
      </tr>

      <tr class="prop">
          <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'additionalRecovery', 'errors')}">
              <g:checkBox name="additionalRecovery" value="${IcdGtexRpciInstance?.additionalRecovery}" />
              <label for="additionalRecovery"><g:message code="IcdGtexRpci.additionalRecovery.label" default="Additional organs, tissues, and sample may be recovered for research only purposes." /></label>
          </td>
      </tr>

        <tr class="prop">
            <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'specifiedLimitations', 'errors')}">
              <br />
                <label for="specifiedLimitations"><g:message code="IcdGtexRpci.specifiedLimitations.label" default="35. Specify limitations / additions, if any" /></label><br />
                <g:textArea style="width:500px;" cols="80" rows="5" name="specifiedLimitations" value="${IcdGtexRpciInstance?.specifiedLimitations}" />
            </td>
        </tr>                

  </tbody>
</table>
