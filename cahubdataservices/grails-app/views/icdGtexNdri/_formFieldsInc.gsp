<g:render template="/candidateRecord/candidateDetails" bean="${IcdGtexNdriInstance.candidateRecord}" var="candidateRecord" /> 

<table>
  <tbody>
    <tbody>
        <tr class="prop">
            <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'protocolSiteNum', 'errors')}">
                <label for="protocolSiteNum"><g:message code="IcdGtexNdri.protocolSiteNum.label" default="1. Site Protocol Number" /></label><br />
                <b>${IcdGtexNdriInstance?.candidateRecord.bss.protocolSiteNum}</b>
                <g:hiddenField name="protocolSiteNum" value="${IcdGtexNdriInstance?.candidateRecord.bss.protocolSiteNum}" />
            </td>
            <td valign="top" style="width:33%">
                <label for="candidateRecord.candidateId"><g:message code="IcdGtexNdri.candidateRecord.candidateId.label" default="2. Candidate ID" /></label><br />
                <span style="font-weight:bold;">${IcdGtexNdriInstance?.candidateRecord.candidateId}</span><br />(${IcdGtexNdriInstance?.candidateRecord.caseCollectionType})
            </td>
            
             <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'consentor', 'errors')}">
                <label for="consentor"><g:message code="IcdGtexNdri.consentor.label" default="3. Person obtaining consent / approaching candidate" /></label><br />
                <g:textField name="consentor" value="${IcdGtexNdriInstance?.consentor}" />
            </td>
        </tr>

        <tr class="prop">          
            <td valign="top" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'consentorRelationship', 'errors')}">
              <label for="consentorRelationship"><g:message code="IcdGtexNdri.consentorRelationship.label" default="4. Relationship of consent signer to donor" /></label><br />
              
              <g:if test="${IcdGtexNdriInstance.candidateRecord.caseCollectionType.code == 'SURGI'}">
                <g:select name="consentorRelationship" from="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_SURGI?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_SURGI?.values()*.name()}" value="${IcdGtexNdriInstance?.consentorRelationship}" noSelection="['': '']" /><br/>              
              </g:if>
              <g:else>
                <g:select name="consentorRelationship" from="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_POSTM_OPO?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexBaseClass$ConsentorRelationship_POSTM_OPO?.values()*.name()}" value="${IcdGtexNdriInstance?.consentorRelationship}" noSelection="['': '']" /><br/>                              
              </g:else>
              <g:textField style="width:120px;" name="otherConsentRelation" value="${IcdGtexNdriInstance?.otherConsentRelation}" /> 
              
           </td>         

           <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'consentObtained', 'errors')}">
                <label for="consentObtained"><g:message code="IcdGtexNdri.consentObtained.label" default="5. Was consent obtained?" /></label><br />
                <g:icdYesNoRadioPicker checked="${(IcdGtexNdriInstance?.consentObtained?.toString())}"  name="consentObtained"/>
            </td>
            
           
           
           <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'dateConsented', 'errors')}">
                <label for="dateConsented"><g:message code="IcdGtexNdri.dateConsented.label" default="6. Date of consent or Date of approach" /></label><br />
                <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateConsented" value="${IcdGtexNdriInstance?.dateConsented}" />
            </td>
           
           <script>   
                   function showhide()
                   {
                         var selectedValue = $("#consentorRelationship").val();
                          if (selectedValue == 'OTH') {
//                              $('#otherConsentRelation').val('')
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
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'institutionICDVersion', 'errors')}">
              <label for="institutionICDVersion"><g:message code="IcdGtexNdri.institutionICDVersion.label" default="7. Institutional version number of ICD" /></label><br />
              <g:textField name="institutionICDVersion" value="${IcdGtexNdriInstance?.institutionICDVersion}" />
          </td>
          
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'dateIRBApproved', 'errors')}">
              <label for="dateIRBApproved"><g:message code="IcdGtexNdri.dateIRBApproved.label" default="8. IRB approval date" /></label><br />
              <g:jqDatePicker name="dateIRBApproved" value="${IcdGtexNdriInstance?.dateIRBApproved}" />
          </td>
        </tr>
        
        <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'dateIRBExpires', 'errors')}">
              <label for="dateIRBExpires"><g:message code="IcdGtexNdri.dateIRBExpires.label" default="9. IRB expiration date" /></label><br />
              <g:jqDatePicker name="dateIRBExpires" value="${IcdGtexNdriInstance?.dateIRBExpires}" />
          </td>

          <td colspan="3" valign="top" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'willingELSISubStudy', 'errors')}">
              <label for="willingELSISubStudy"><g:message code="IcdGtexNdri.willingELSISubStudy.label" default="10. Is there a willingness to be contacted at a later date for the ELSI sub-study?" /></label><br />
              <g:icdYesNoRadioPicker checked="${(IcdGtexNdriInstance?.willingELSISubStudy?.toString())}"  name="willingELSISubStudy"/>
          </td>
        </tr>     

        <tr class="prop">
          <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'comments', 'errors')}">
              <label for="comments"><g:message code="IcdGtexNdri.comments.label" default="11. Comments to Consent Section" /></label><br />
              <g:textArea style="width:300px" name="comments" value="${IcdGtexNdriInstance?.comments}" />
          </td>
        </tr>               
        
    </tbody>
</table>

<div class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'consentObtained', 'errors')}">
  <h1>To Be Completed for Consented Participants</h1>
</div>

<g:if test="${IcdGtexNdriInstance.candidateRecord.caseCollectionType.code != 'SURGI'}">

  <table>
    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'adipose', 'errors')}">
            <label for="adipose"><g:message code="IcdGtexNdri.adipose.label" default="12. Adipose" /></label></td>
        <td><g:select name="adipose" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.adipose?.name()}"  /></td>
    </tr>
    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'bladder', 'errors')}">
            <label for="bladder"><g:message code="IcdGtexNdri.bladder.label" default="13. Bladder" /></label></td>
        <td><g:select name="bladder" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.bladder?.name()}"  /></td>
    </tr>        

    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'bloodUrineSaliva', 'errors')}">
            <label for="bloodUrineSaliva"><g:message code="IcdGtexNdri.bloodUrineSaliva.label" default="14. Blood, urine, saliva" /></label></td>
        <td><g:select name="bloodUrineSaliva" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.bloodUrineSaliva?.name()}"  /></td>
    </tr>

    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'bloodVessel', 'errors')}">
            <label for="bloodVessel"><g:message code="IcdGtexNdri.bloodVessel.label" default="15. Blood vessel" /></label></td>
        <td><g:select name="bloodVessel" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.bloodVessel?.name()}"   /></td>
    </tr>

    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'brain', 'errors')}">
            <label for="brain"><g:message code="IcdGtexNdri.brain.label" default="16. Brain" /></label></td>
        <td><g:select name="brain" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.brain?.name()}"  /></td>
    </tr>
    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'breast', 'errors')}">
            <label for="breast"><g:message code="IcdGtexNdri.breast.label" default="17. Mammary tissue (breast)" /></label></td>
        <td><g:select name="breast" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.breast?.name()}"  /></td>
    </tr>      
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'endocrine', 'errors')}">
            <label for="endocrine"><g:message code="IcdGtexNdri.endocrine.label" default="18. Endocrine" /></label></td>
        <td><g:select name="endocrine" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.endocrine?.name()}"  /></td>
    </tr>        

    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'esophagus', 'errors')}">
            <label for="esophagus"><g:message code="IcdGtexNdri.esophagus.label" default="19. Esophagus" /></label></td>
        <td><g:select name="esophagus" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.esophagus?.name()}"  /></td>
    </tr>        
    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'heartTissue', 'errors')}">
            <label for="heartTissue"><g:message code="IcdGtexNdri.heartTissue.label" default="20. Heart tissue" /></label></td>
        <td><g:select name="heartTissue" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.heartTissue?.name()}"   /></td>
    </tr>        
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'kidney', 'errors')}">
            <label for="kidney"><g:message code="IcdGtexNdri.kidney.label" default="21. Kidney" /></label></td>
        <td><g:select name="kidney" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.kidney?.name()}"  /></td>
    </tr>
    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'largeIntestine', 'errors')}">
            <label for="largeIntestine"><g:message code="IcdGtexNdri.largeIntestine.label" default="22. Large intestine" /></label></td>
        <td><g:select name="largeIntestine" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.largeIntestine?.name()}"  /></td>
    </tr>
    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'liver', 'errors')}">
            <label for="liver"><g:message code="IcdGtexNdri.liver.label" default="23. Liver" /></label></td>
        <td><g:select name="liver" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.liver?.name()}"  /></td>
    </tr>        
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'lung', 'errors')}">
            <label for="lung"><g:message code="IcdGtexNdri.lung.label" default="24. Lung" /></label></td>
        <td><g:select name="lung" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.lung?.name()}"  /></td>
    </tr>
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'lymphNode', 'errors')}">
            <label for="lymphNode"><g:message code="IcdGtexNdri.lymphNode.label" default="25. Lymph node" /></label></td>
        <td><g:select name="lymphNode" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.lymphNode?.name()}"  /></td>
    </tr>

    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'muscle', 'errors')}">
            <label for="muscle"><g:message code="IcdGtexNdri.muscle.label" default="26. Muscle" /></label></td>
        <td><g:select name="muscle" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.muscle?.name()}"  /></td>
    </tr>
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'nerveTissue', 'errors')}">
             <label for="nerveTissue"><g:message code="IcdGtexNdri.nerveTissue.label" default="27. Neurological tissue" /></label></td>
        <td><g:select name="nerveTissue" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.nerveTissue?.name()}"   /></td>
    </tr>

    <tr class="prop">
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'pancreas', 'errors')}">
            <label for="pancreas"><g:message code="IcdGtexNdri.pancreas.label" default="28. Pancreas" /></label></td>
        <td><g:select name="pancreas" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.pancreas?.name()}"  /></td>
    </tr>

    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'reproductive', 'errors')}">
            <label for="reproductive"><g:message code="IcdGtexNdri.reproductive.label" default="29. Reproductive" /></label></td>
        <td><g:select name="reproductive" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.reproductive?.name()}"  /></td>
    </tr>        

    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'smallIntestine', 'errors')}">
            <label for="smallIntestine"><g:message code="IcdGtexNdri.smallIntestine.label" default="30. Small intestine" /></label></td>
        <td><g:select name="smallIntestine" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.smallIntestine?.name()}"  /></td>
    </tr>        

    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'skin', 'errors')}">
            <label for="skin"><g:message code="IcdGtexNdri.skin.label" default="31. Skin" /></label></td>
        <td><g:select name="skin" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.skin?.name()}"  /></td>
    </tr>

    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'spleen', 'errors')}">
            <label for="spleen"><g:message code="IcdGtexNdri.spleen.label" default="32. Spleen" /></label></td>
        <td><g:select name="spleen" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.spleen?.name()}"  /></td>
    </tr>
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'stomach', 'errors')}">
            <label for="stomach"><g:message code="IcdGtexNdri.stomach.label" default="33. Stomach" /></label></td>
        <td><g:select name="stomach" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.stomach?.name()}"  /></td>
    </tr>
    <tr class="prop">                        
        <td valign="top" style="width:33%" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'minorSalivary', 'errors')}">
            <label for="minorSalivary"><g:message code="IcdGtexNdri.minorSalivary.label" default="34. Minor Salivary" /></label></td>
        <td><g:select name="minorSalivary" from="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()}" keys="${nci.obbr.cahub.forms.gtex.IcdGtexNdri$YesNo?.values()*.name()}" value="${IcdGtexNdriInstance?.minorSalivary?.name()}"  /></td>
    </tr>
  </table>
</g:if>
<table>
    <tr class="prop">                        
        <td colspan="2" valign="top" class="value ${hasErrors(bean: IcdGtexNdriInstance, field: 'specifiedLimitations', 'errors')}">
            <label for="specifiedLimitations">
              <g:if test="${IcdGtexNdriInstance?.candidateRecord.caseCollectionType.code != 'SURGI'}">35.</g:if>
              <g:else>12.</g:else>
              Specify limitations / additions, if any</label><br />
            <g:textArea style="width:500px;" cols="80" rows="5" name="specifiedLimitations" value="${IcdGtexNdriInstance?.specifiedLimitations}" />
        </td>
    </tr>
</table>
