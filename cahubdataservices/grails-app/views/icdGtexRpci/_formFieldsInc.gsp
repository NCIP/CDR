<g:render template="/candidateRecord/candidateDetails" bean="${IcdGtexRpciInstance.candidateRecord}" var="candidateRecord" /> 

<table>
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
                <label for="dateConsented"><g:message code="IcdGtexRpci.dateConsented.label" default="6. Date of consent or Date of approach" /></label><br />
                <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateConsented" value="${IcdGtexRpciInstance?.dateConsented}" />
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
                <label for="dateIRBApproved"><g:message code="IcdGtexRpci.dateIRBApproved.label" default="8. IRB approval date" /></label><br />
                <g:jqDatePicker name="dateIRBApproved" value="${IcdGtexRpciInstance?.dateIRBApproved}" />
            </td>
           
        </tr>
        <tr class="prop">
           <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'dateIRBExpires', 'errors')}">
                <label for="dateIRBExpires"><g:message code="IcdGtexRpci.dateIRBExpires.label" default="9. IRB expiration date" /></label><br />
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
<table>

      <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'gtexAuthAddendum', 'errors')}">
              <label for="gtexAuthAddendum"><g:message code="IcdGtexRpci.gtexAuthAddendum.label" default="11. GTEx Authorization Addendum" /></label><br />
              <g:select name="gtexAuthAddendum" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.samplesAsNeeded?.name()}"  />
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'samplesAsNeeded', 'errors')}">
              <label for="samplesAsNeeded"><g:message code="IcdGtexRpci.samplesAsNeeded.label" default="12. Research samples as needed" /></label><br />
              <g:select name="samplesAsNeeded" from="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexRpci$YesNoNA?.values()*.name()}" value="${IcdGtexRpciInstance?.samplesAsNeeded?.name()}"  />
        </td>
      </tr>

      <tr class="prop">
          <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexRpciInstance, field: 'transplantToPerson', 'errors')}">
              13. I make this anatomical gift, if medically acceptable for the purpose of:<br /><br />
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
                <label for="specifiedLimitations"><g:message code="IcdGtexRpci.specifiedLimitations.label" default="14. Specify additions(such as brain consent), limitations, if any" /></label><br />
                <g:textArea style="width:500px;" cols="80" rows="5" name="specifiedLimitations" value="${IcdGtexRpciInstance?.specifiedLimitations}" />
            </td>
        </tr>                

  </tbody>
</table>
