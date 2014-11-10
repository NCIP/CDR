<g:render template="/candidateRecord/candidateDetails" bean="${IcdGtexScInstance.candidateRecord}" var="candidateRecord" /> 

<table>
    <tbody>
        <tr class="prop">

            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'protocolSiteNum', 'errors')}">
                <label for="protocolSiteNum"><g:message code="IcdGtexSc.protocolSiteNum.label" default="1. Site Protocol Number" /></label><br />
                <b>${IcdGtexScInstance?.candidateRecord.bss.protocolSiteNum}</b>
                <g:hiddenField name="protocolSiteNum" value="${IcdGtexScInstance?.candidateRecord.bss.protocolSiteNum}" />
            </td>
            <td valign="top" style="width:33%">
                <label for="candidateRecord.candidateId"><g:message code="IcdGtexSc.candidateRecord.candidateId.label" default="2. BSS Candidate ID" /></label><br />
                <span style="font-weight:bold;">${IcdGtexScInstance?.candidateRecord.candidateId}</span><br />(${IcdGtexScInstance?.candidateRecord.caseCollectionType})
            </td>
            
             <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'consentor', 'errors')}">
                <label for="consentor"><g:message code="IcdGtexSc.consentor.label" default="3. Person obtaining consent / approaching candidate" /></label><br />
                <g:textField name="consentor" value="${IcdGtexScInstance?.consentor}" />
            </td>
            
            
        </tr>

        <tr class="prop">  
          
          <td valign="top" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'consentorRelationship', 'errors')}">
              <label for="consentorRelationship"><g:message code="IcdGtexSc.consentorRelationship.label" default="4. Relationship of consent signer to donor" /></label><br />
              <g:select name="consentorRelationship" from="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_POSTM_OPO?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_POSTM_OPO?.values()*.name()}" value="${IcdGtexScInstance?.consentorRelationship}" noSelection="['': '']" /><br/>                              
              <g:textField style="width:120px;" name="otherConsentRelation" value="${IcdGtexScInstance?.otherConsentRelation}" />              
              </td>  
              
              <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'consentObtained', 'errors')}">
                <g:message code="IcdGtexSc.consentObtained.label" default="5. Was consent obtained?" /><br />
                <g:icdYesNoRadioPicker checked="${(IcdGtexScInstance?.consentObtained?.toString())}"  name="consentObtained"/>                
            </td>
          
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'dateConsented', 'errors')}">
                <label for="dateConsented"><g:message code="IcdGtexSc.dateConsented.label" default="6. Date of consent or Date of approach" /></label><br /> 
                <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateConsented" value="${IcdGtexScInstance?.dateConsented}" />
            </td>
           
                
                
             <script>   
                   function showhide()
                   {
                         var selectedValue = $("#consentorRelationship").val();
                          if (selectedValue == 'OTH') {
                              //$('#otherConsentRelation').val('')
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

           
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'institutionICDVersion', 'errors')}">
                <label for="institutionICDVersion"><g:message code="IcdGtexSc.institutionICDVersion.label" default="7. Institutional version number of ICD" /></label><br />
                <g:textField name="institutionICDVersion" value="${IcdGtexScInstance?.institutionICDVersion}" />
            </td>
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'dateIRBApproved', 'errors')}">
                <label for="dateIRBApproved"><g:message code="IcdGtexSc.dateIRBApproved.label" default="8. IRB approval date" /></label><br />
                <g:jqDatePicker name="dateIRBApproved" value="${IcdGtexScInstance?.dateIRBApproved}" />
            </td>
           
        </tr>

        <tr class="prop">
           <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'dateIRBExpires', 'errors')}">
                <label for="dateIRBExpires"><g:message code="IcdGtexSc.dateIRBExpires.label" default="9. IRB expiration date" /></label><br />
                <g:jqDatePicker name="dateIRBExpires" value="${IcdGtexScInstance?.dateIRBExpires}" />
            </td>
          
            <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'specifiedLimitations', 'errors')}">
                <label for="specifiedLimitations"><g:message code="IcdGtexSc.specifiedLimitations.label" default="10. For consented participants, specify donation limitations / exclusions, if any" /></label><br />
                <g:textArea style="width:300px;" cols="80" rows="5" name="specifiedLimitations" value="${IcdGtexScInstance?.specifiedLimitations}" />
            </td>
        </tr>
        
        <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexScInstance, field: 'comments', 'errors')}">
                <label for="comments"><g:message code="IcdGtexSc.comments.label" default="11. Comments to Consent Section" /></label><br />
                <g:textArea style="width:300px" name="comments" value="${IcdGtexScInstance?.comments}" />
          </td>
        </tr>

     
    </tbody>
</table>
