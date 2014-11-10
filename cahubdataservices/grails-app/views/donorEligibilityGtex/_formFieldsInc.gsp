<g:render template="/candidateRecord/candidateDetails" bean="${donorEligibilityGtexInstance.candidateRecord}" var="candidateRecord" /> 

<h3> Note: Complete all questions for documentation of screening and eligibility of donor. Please submit for ALL screened AND enrolled subjects.</h3>
<div align="center">Note: <i> Donor is ineligible if any box is checked in the ineligible column </i> </div>          
<div class="list">
<table>
      <tbody>
        <tr class="prop">
          <th>            
          </th>
          <th style="font-weight:bold;width:170px;"><nobr>Eligible &nbsp; | &nbsp; Ineligible</nobr>
          </th>
        </tr>
    <tr class="prop">
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'allowedMinOrganType', 'errors')}">
        <g:message code="DonorEligibilityGtex.allowedMinOrganType.question.label" default="allowedMinOrganType" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'allowedMinOrganType', 'errors')}">
        <g:yesNoRadioPicker checked="${(donorEligibilityGtexInstance?.allowedMinOrganType?.toString())}"  name="allowedMinOrganType" />
    </td>
    </tr>
    <tr class="prop">
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'age', 'errors')}">
    <g:message code="DonorEligibilityGtex.age.label" default="2. Donor is &gt;= 21 and &lt;= 70 years of age" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'age', 'errors')}">
      <g:yesNoRadioPicker checked="${(donorEligibilityGtexInstance?.age?.toString())}"  name="age"/>
    </td>
    </tr>
    <tr class="prop">
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'BMI', 'errors')}">
    <g:message code="DonorEligibilityGtex.BMI.label" default="3. Donor BMI is &gt;= 18.50 and &lt;= 35.00 (BMI = 703 * weight in # /height in inches squared)" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'BMI', 'errors')}">
    <g:yesNoRadioPicker checked="${(donorEligibilityGtexInstance?.BMI?.toString())}"  name="BMI" />
    </td>
    </tr>
    <!--pmh 5.2 08/27/13-->
    <g:if test="${ver52Updates}">
      <tr class="prop odd" >
        <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'collectIn8afterDeath', 'errors')}">
      <g:message code="DonorEligibilityGtex.collectIn8afterDeath.label" default="4a. Is it likely that tissue collection can be started AND the first tissue can be placed in fixative within 8.0 hours of cardiac cessation or recorded time of death (observed or presumed) for a non-brain donor?" />
      </td>
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'collectIn8afterDeath', 'errors')}">
        <div>
          <g:radio name="collectIn8afterDeath" id="collectIn8afterDeath_yes"  value="Yes" checked="${donorEligibilityGtexInstance?.collectIn8afterDeath == 'Yes'}"/> <label class="createradiospace" for="collectIn8afterDeath">Yes</label>
          <g:radio name="collectIn8afterDeath" id="collectIn8afterDeath_no"  value="No" checked="${donorEligibilityGtexInstance?.collectIn8afterDeath == 'No'}"/> <label class="createradiospace" for="collectIn8afterDeath">No</label>
        </div>
      </td>
      </tr>
     <tr class="prop"> <td class="name" colspan='2'>Or</td>
          </tr>
      <tr class="prop odd" >
        <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'collectAllIn24afterDeath', 'errors')}">
      <g:message code="DonorEligibilityGtex.collectAllIn24afterDeath.label" default="4b. Is it likely that all tissues can be collected and placed into fixative within 24.0 hours of cardiac cessation (observed or presumed) for a brain donor?" />
      </td>
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'collectAllIn24afterDeath', 'errors')}">
        <div>
          <g:radio name="collectAllIn24afterDeath" id="collectAllIn24afterDeath_yes" value="Yes" checked="${donorEligibilityGtexInstance?.collectAllIn24afterDeath == 'Yes'}"/> <label class="createradiospace" for="collectAllIn24afterDeath">Yes</label>
          <g:radio name="collectAllIn24afterDeath" id="collectAllIn24afterDeath_no" value="No" checked="${donorEligibilityGtexInstance?.collectAllIn24afterDeath == 'No'}"/> <label class="createradiospace" for="collectAllIn24afterDeath">No</label>
        </div>
      </td>
      </tr>
    </g:if>

    <g:else>  

      <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'collectIn24hDeath', 'errors')}">
    <g:message code="DonorEligibilityGtex.collectIn24hDeath.label" default="4. Is it likely that tissue collection can be completed within 24 hours of cardiac cessation (observed or presumed) or within 4 hours of clamp time for surgical collections?" />
    </td>
    <div>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'collectIn24hDeath', 'errors')}">
    <g:yesNoRadioPicker checked="${(donorEligibilityGtexInstance?.collectIn24hDeath?.toString())}"  name="collectIn24hDeath" />                                
    </td>
    </div>
    </tr>
    </g:else>
    <!--pmh END 5.2 08/27/13-->
    
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'receiveTransfusionIn48h', 'errors')}">
    <g:message code="DonorEligibilityGtex.receiveTransfusionIn48h.label" default="5. Did donor receive a whole blood transfusion within the previous 48 hours?" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'receiveTransfusionIn48h', 'errors')}">
    <g:noYesUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.receiveTransfusionIn48h?.toString())}" name="receiveTransfusionIn48h" />
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'diagnosedMetastatis', 'errors')}">
    <g:message code="DonorEligibilityGtex.diagnosedMetastatis.label" default="6. Has the donor ever been diagnosed with metastatic cancer (cancer that spread beyond the initial site, such as to other organs like brain, bone, or liver)" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'diagnosedMetastatis', 'errors')}">
    <g:noYesRadioPicker checked="${(donorEligibilityGtexInstance?.diagnosedMetastatis?.toString())}"  name="diagnosedMetastatis" />
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'receivedChemoIn2y', 'errors')}">
    <g:message code="DonorEligibilityGtex.receivedChemoIn2y.label" default="7. Has the donor received chemotherapy or radiation therapy for cancer or any other condition within the past 2 years?" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'receivedChemoIn2y', 'errors')}">
    <g:noYesRadioPicker checked="${(donorEligibilityGtexInstance?.receivedChemoIn2y?.toString())}"  name="receivedChemoIn2y" />
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'drugAbuse', 'errors')}">
    <g:message code="DonorEligibilityGtex.drugAbuse.label" default="8. Does the donor have a history of intravenous drug abuse in the last 5 years?" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'drugAbuse', 'errors')}">
    <g:noUnknownYesRadioPicker checked="${(donorEligibilityGtexInstance?.drugAbuse?.toString())}"  name="drugAbuse" />
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfSexWithHIV', 'errors')}">
    <g:message code="DonorEligibilityGtex.histOfSexWithHIV.question.label" default="9. Does the donor have a history of sex with someone who has been diagnosed or at risk for HIV/AIDS, and/or HCV, and/or HBV or someone who has used intravenous drugs in the last 5 years?" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfSexWithHIV', 'errors')}">
    <g:noUnknownYesRadioPicker checked="${(donorEligibilityGtexInstance?.histOfSexWithHIV?.toString())}"  name="histOfSexWithHIV" />
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'contactHIV', 'errors')}">
    <g:message code="DonorEligibilityGtex.contactHIV.question.label" default="10. Has the donor been exposed to HIV/AIDS, and/or HCV, and/or HBV through needle sticks, and/or contact with non-intact skin and/or contact with open wounds, and/or contact with mucous membranes? " />                                  
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'contactHIV', 'errors')}">
    <g:noUnknownYesRadioPicker checked="${(donorEligibilityGtexInstance?.contactHIV?.toString())}"  name="contactHIV" />                             
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfReactiveAssays', 'errors')}">
    <g:message code="DonorEligibilityGtex.histOfReactiveAssays.label" default="11. Does the donor have a history of repeatedly reactive screening assays for HIV-1 or HIV-2 antibody regardless of the results of supplemental assays?" />
    </td>
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfReactiveAssays', 'errors')}">
    <g:noUnknownYesRadioPicker checked="${(donorEligibilityGtexInstance?.histOfReactiveAssays?.toString())}"  name="histOfReactiveAssays" />                                
    </td>
    </tr>
 </tbody>
</table>
  <h2>FOR INFORMATIONAL PURPOSES ONLY</h2>
  <h3>The items below are not exclusionary criteria. Please complete for all screened subjects.</h3>              
<table>
  <tbody>
    <tr class="prop">
        <td valign="top" colspan ="2" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfInfections', 'errors')}">
          <g:if test="${!changeText}">
        <label for="histOfInfections"><g:message code="DonorEligibilityGtex.histOfInfections.label" default="12. History of any of the following at the time of death " /></label>
          </g:if>
  <g:else>
    <label for="histOfInfections">12. History of any of the following </label>
  </g:else>
          </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'documentedSepsis', 'errors')}">
        <label for="documentedSepsis"><g:message code="DonorEligibilityGtex.documentedSepsis.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Documented Sepsis" /></label>
      </td>
      <td class="value" style="font-weight:bold;width:170px;"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.documentedSepsis?.toString())}" name="documentedSepsis" /></td>
    </tr>
     <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'pneumonia', 'errors')}">
        <label for="pneumonia"><g:message code="DonorEligibilityGtex.pneumonia.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pneumonia" /></label>
      </td>
        <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.pneumonia?.toString())}" name="pneumonia" /></td>
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'openWounds', 'errors')}">
      <label for="openWounds"><g:message code="DonorEligibilityGtex.openWounds.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Open Wounds" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.openWounds?.toString())}" name="openWounds" /></td>      
      </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'highUnexplainedFever', 'errors')}">
        <label for="highUnexplainedFever"><g:message code="DonorEligibilityGtex.highUnexplainedFever.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;High Unexplained Fever" /></label>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.highUnexplainedFever?.toString())}" name="highUnexplainedFever" /></td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'positiveBloodCultures', 'errors')}">
        <label for="positiveBloodCultures"><g:message code="DonorEligibilityGtex.positiveBloodCultures.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Positive Blood Cultures" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.positiveBloodCultures?.toString())}" name="positiveBloodCultures" /></td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'abnormalWBC', 'errors')}">
        <label for="abnormalWBC"><g:message code="DonorEligibilityGtex.abnormalWBC.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Abnormal WBC" /></label>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.abnormalWBC?.toString())}" name="abnormalWBC" /></td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'infectedLines', 'errors')}">
       <label for="infectedLines"><g:message code="DonorEligibilityGtex.infectedLines.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Infected Lines" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.infectedLines?.toString())}" name="infectedLines" /></td></td>
    </tr>
    <tr class="prop">
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'fungalInfections', 'errors')}">
      <label for="fungalInfections"><g:message code="DonorEligibilityGtex.fungalInfections.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fungal Infections" /></label>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.fungalInfections?.toString())}" name="fungalInfections" /></td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'ascities', 'errors')}">
    <label for="ascities"><g:message code="DonorEligibilityGtex.ascities.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ascities" /></label>
      </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.ascities?.toString())}" name="ascities" /></td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'cellulites', 'errors')}">
    <label for="cellulites"><g:message code="DonorEligibilityGtex.cellulites.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cellulites" /></label>
      </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.cellulites?.toString())}" name="cellulites" /></td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'pastBloodDonations', 'errors')}">
      <label for="pastBloodDonations"><g:message code="DonorEligibilityGtex.pastBloodDonations.label" default="13. Has blood donation been denied in the past, specify below:" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.pastBloodDonations?.toString())}" name="pastBloodDonations" /></td>
      </td>  
    </tr>
    <tr class="prop">
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'pastBloodDonations', 'errors')}">
    <label for="bloodDonDenialReason"><g:message code="DonorEligibilityGtex.bloodDonDenialReason.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If Yes, Reason:" /></label>      
        <g:textField name="bloodDonDenialReason" value="${donorEligibilityGtexInstance?.bloodDonDenialReason}" />    
    </td>
    <td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'bloodTransfusion', 'errors')}">
        <label for="bloodTransfusion"><g:message code="DonorEligibilityGtex.bloodTransfusion.label" default="14. Blood transfusion received in another country" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.bloodTransfusion?.toString())}" name="bloodTransfusion" /></td>
    </td>
    </tr>    
    <tr class="prop">
    <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'humAnimTissueTransplant', 'errors')}">
    <label for="humAnimTissueTransplant"><g:message code="DonorEligibilityGtex.humAnimTissueTransplant.label" default="15. Received a human and/or animal tissue and/or organ transplant or xenotransplant.  If yes, specify in comments below:" /></label>
    </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.humAnimTissueTransplant?.toString())}" name="humAnimTissueTransplant" /></td>
      </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'humAnimTissueTransplant', 'errors')}">
        <label for="tissueTransplantComments"><g:message code="DonorEligibilityGtex.tissueTransplantComments.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Comments:" /></label>
        <g:textField name="tissueTransplantComments" value="${donorEligibilityGtexInstance?.tissueTransplantComments}" />
    </td>
    <td></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'recentSmallpoxVac', 'errors')}">
      <label for="recentSmallpoxVac"><g:message code="DonorEligibilityGtex.recentSmallpoxVac.label" default="16. Recent smallpox vaccination" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.recentSmallpoxVac?.toString())}" name="recentSmallpoxVac" /></td>
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'contactWithSmallpox', 'errors')}">
      <label for="contactWithSmallpox"><g:message code="DonorEligibilityGtex.contactWithSmallpox.label" default="17. Contact with someone who has recently had smallpox" /></label>
      </td>
      <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.contactWithSmallpox?.toString())}" name="contactWithSmallpox" /></td>
      </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'dialysisTreatment', 'errors')}">
        <label for="dialysisTreatment"><g:message code="DonorEligibilityGtex.dialysisTreatment.label" default="18. Dialysis treatment (long term greater than 1 month @ 3 times per week)" /></label>
      </td>
        <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.dialysisTreatment?.toString())}" name="dialysisTreatment" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'currentCancerDiag', 'errors')}">
        <label for="currentCancerDiag"><g:message code="DonorEligibilityGtex.currentCancerDiag.label" default="19. Current diagnosis of cancer (regardless of treatment and location)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.currentCancerDiag?.toString())}" name="currentCancerDiag" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'cancerDiagPrec5yrs', 'errors')}">
        <label for="cancerDiagPrec5yrs"><g:message code="DonorEligibilityGtex.cancerDiagPrec5yrs.label" default="20. Cancer diagnosis within the preceding 5 years (regardless of treatment and location)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.cancerDiagPrec5yrs?.toString())}" name="cancerDiagPrec5yrs" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'tBHistory', 'errors')}">
        <label for="tBHistory"><g:message code="DonorEligibilityGtex.tBHistory.label" default="21. TB History" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.tBHistory?.toString())}" name="tBHistory" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'activeMeningitis', 'errors')}">
        <label for="activeMeningitis"><g:message code="DonorEligibilityGtex.activeMeningitis.label" default="22. Active meningitis" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.activeMeningitis?.toString())}" name="activeMeningitis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'activeEncephalitis', 'errors')}">
        <label for="activeEncephalitis"><g:message code="DonorEligibilityGtex.activeEncephalitis.label" default="23. Active encephalitis" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.activeEncephalitis?.toString())}" name="activeEncephalitis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'longtermSteroidUse', 'errors')}">
        <label for="longtermSteroidUse"><g:message code="DonorEligibilityGtex.longtermSteroidUse.label" default="24. Long term steroid use" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.longtermSteroidUse?.toString())}" name="longtermSteroidUse" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'osteomyelitis', 'errors')}">
        <label for="osteomyelitis"><g:message code="DonorEligibilityGtex.osteomyelitis.label" default="25. Osteomyelitis" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.osteomyelitis?.toString())}" name="osteomyelitis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplSeizures', 'errors')}">
        <label for="unexplSeizures"><g:message code="DonorEligibilityGtex.unexplSeizures.label" default="26. Unexplained seizures" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplSeizures?.toString())}" name="unexplSeizures" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplWkness', 'errors')}">
        <label for="unexplWkness"><g:message code="DonorEligibilityGtex.unexplWkness.label" default="27. Unexplained weakness and fatigue described as flu-like symptoms" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplWkness?.toString())}" name="unexplWkness" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'exposureToToxics', 'errors')}">
        <label for="exposureToToxics"><g:message code="DonorEligibilityGtex.exposureToToxics.label" default="28. Exposure to toxic substances that may have led to chronic conditions" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.exposureToToxics?.toString())}" name="exposureToToxics" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'noPhysicalActivity', 'errors')}">
        <label for="noPhysicalActivity"><g:message code="DonorEligibilityGtex.noPhysicalActivity.label" default="29. No physical activity defined as bed bound for greater than 4 weeks" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.noPhysicalActivity?.toString())}" name="noPhysicalActivity" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'residedOnMilitBase', 'errors')}">
        <label for="residedOnMilitBase"><g:message code="DonorEligibilityGtex.residedOnMilitBase.label" default="30. Resided on a Northern European military base for 6 months from 1980-1990 or elsewhere in Europe from 1980-1996" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.residedOnMilitBase?.toString())}" name="residedOnMilitBase" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'biteFromAnimal', 'errors')}">
        <label for="biteFromAnimal"><g:message code="DonorEligibilityGtex.biteFromAnimal.label" default="31. Bite from an animal suspected to have rabies in the last 12 months" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.biteFromAnimal?.toString())}" name="biteFromAnimal" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'heroinUse', 'errors')}">
        <label for="heroinUse"><g:message code="DonorEligibilityGtex.heroinUse.label" default="32. Heroin use – EVER – by any route" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.heroinUse?.toString())}" name="heroinUse" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'cocaineUse', 'errors')}">
        <label for="cocaineUse"><g:message code="DonorEligibilityGtex.cocaineUse.label" default="33. Cocaine use in the past 5 years" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.cocaineUse?.toString())}" name="cocaineUse" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'menWithMen', 'errors')}">
        <label for="menWithMen"><g:message code="DonorEligibilityGtex.menWithMen.label" default="34. Men who have sex with men" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.menWithMen?.toString())}" name="menWithMen" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'drugUseForNonMed', 'errors')}">

        <label for="drugUseForNonMed"><g:message code="DonorEligibilityGtex.drugUseForNonMed.label" default="35. Drug injection(s) (intravenous, intramuscular, and subcutaneous) for non-medical use in the last 5 years" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.drugUseForNonMed?.toString())}" name="drugUseForNonMed" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'hemophilia', 'errors')}">
        <label for="hemophilia"><g:message code="DonorEligibilityGtex.hemophilia.label" default="36. Hemophilia and/or clotting disorders requiring treatment with human-derived clotting factors" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.hemophilia?.toString())}" name="hemophilia" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'sexForMoneyDrugs', 'errors')}">
        <label for="sexForMoneyDrugs"><g:message code="DonorEligibilityGtex.sexForMoneyDrugs.label" default="37. Performed sexual acts in exchange for money or drugs" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.sexForMoneyDrugs?.toString())}" name="sexForMoneyDrugs" /></td>
    </tr>
    <tr class="prop">
     <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'sexWithOthers', 'errors')}">
        <label for="sexWithOthers"><g:message code="DonorEligibilityGtex.sexWithOthers.label" default="38. Sexual activity with another person who has a history of:  " /></label>
    </td>
    <td></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'menSexWithMen', 'errors')}">

        <label for="menSexWithMen"><g:message code="DonorEligibilityGtex.menSexWithMen.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Men who have sex with men" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.menSexWithMen?.toString())}" name="menSexWithMen" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'drugsForNonMed5yrs', 'errors')}">
        <label for="drugsForNonMed5yrs"><g:message code="DonorEligibilityGtex.drugsForNonMed5yrs.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Utilized drug injections (intravenous and/or intramuscular and/or subcutaneous) for non-medical use in the last 5 years" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.drugsForNonMed5yrs?.toString())}" name="drugsForNonMed5yrs" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'hemophiliaTreat', 'errors')}">
        <label for="hemophiliaTreat"><g:message code="DonorEligibilityGtex.hemophiliaTreat.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hemophilia and/or clotting disorders requiring treatment with human-derived clotting factors" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.hemophiliaTreat?.toString())}" name="hemophiliaTreat" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'sexForDrugsOrMoney', 'errors')}">
        <label for="sexForDrugsOrMoney"><g:message code="DonorEligibilityGtex.sexForDrugsOrMoney.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Performed sexual acts in exchange for money or drugs" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.sexForDrugsOrMoney?.toString())}" name="sexForDrugsOrMoney" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'notTestedForHiv', 'errors')}">
        <label for="notTestedForHiv"><g:message code="DonorEligibilityGtex.notTestedForHiv.label" default="39. Was not able to be tested for HIV infection because of hemodilution where no pre-transfused specimen is available" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.notTestedForHiv?.toString())}" name="notTestedForHiv" /></td>
    </tr>
    <tr class="prop">
      <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'medHistory', 'errors')}">
        <label for="medHistory"><g:message code="DonorEligibilityGtex.medHistory.label" default="40. History, physical examination, medical records, or autopsy reports reveal other evidence of HIV infection or high-risk behavior such as:  " /></label>
      </td>
      <td></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplWtLoss', 'errors')}">
        <label for="unexplWtLoss"><g:message code="DonorEligibilityGtex.unexplWtLoss.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unexplained weight loss" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplWtLoss?.toString())}" name="unexplWtLoss" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'nightSweats', 'errors')}">
        <label for="nightSweats"><g:message code="DonorEligibilityGtex.nightSweats.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Night sweats" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.nightSweats?.toString())}" name="nightSweats" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'spotsOnSkin', 'errors')}">
        <label for="spotsOnSkin"><g:message code="DonorEligibilityGtex.spotsOnSkin.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Blue or purple spots on the skin or mucus membranes (typical of Kaposi’s sarcoma)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.spotsOnSkin?.toString())}" name="spotsOnSkin" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplLymphad', 'errors')}">
        <label for="unexplLymphad"><g:message code="DonorEligibilityGtex.unexplLymphad.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unexplained lymphadenopathy lasting more than one month" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplLymphad?.toString())}" name="unexplLymphad" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplTemp', 'errors')}">
        <label for="unexplTemp"><g:message code="DonorEligibilityGtex.unexplTemp.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unexplained temperature &gt;=100.5 F (38.6 C) for more than 10 days" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplTemp?.toString())}" name="unexplTemp" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplCough', 'errors')}">
        <label for="unexplCough"><g:message code="DonorEligibilityGtex.unexplCough.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unexplained persistent cough and/or shortness of breath" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplCough?.toString())}" name="unexplCough" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'oppInfections', 'errors')}">
        <label for="oppInfections"><g:message code="DonorEligibilityGtex.oppInfections.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Opportunistic infections" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.oppInfections?.toString())}" name="oppInfections" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'sexTansDis', 'errors')}">
        <label for="sexTansDis"><g:message code="DonorEligibilityGtex.sexTansDis.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sexually transmitted diseases" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.sexTansDis?.toString())}" name="sexTansDis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'signsOfDrugAbuse', 'errors')}">
        <label for="signsOfDrugAbuse"><g:message code="DonorEligibilityGtex.signsOfDrugAbuse.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Needle tracks and/or other signs of drug abuse" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.signsOfDrugAbuse?.toString())}" name="signsOfDrugAbuse" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'diagOfSars', 'errors')}">
        <label for="diagOfSars"><g:message code="DonorEligibilityGtex.diagOfSars.label" default="41. Diagnosis of SARS or recent contact with someone who has it" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.diagOfSars?.toString())}" name="diagOfSars" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfWestNile', 'errors')}">
        <label for="histOfWestNile"><g:message code="DonorEligibilityGtex.histOfWestNile.label" default="42. History of West Nile Virus (WNV)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.histOfWestNile?.toString())}" name="histOfWestNile" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'westNileContact', 'errors')}">
        <label for="westNileContact"><g:message code="DonorEligibilityGtex.westNileContact.label" default="43. History of contact with someone who has West Nile Virus (WNV) (including animals)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.westNileContact?.toString())}" name="westNileContact" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'unexplWeighttLoss', 'errors')}">
        <label for="unexplWeighttLoss"><g:message code="DonorEligibilityGtex.unexplWeighttLoss.label" default="44. Unexplained weight loss" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.unexplWeighttLoss?.toString())}" name="unexplWeighttLoss" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'timeInDetCenter', 'errors')}">
        <label for="timeInDetCenter"><g:message code="DonorEligibilityGtex.timeInDetCenter.label" default="45. Spending &gt;72 hours in a correction/detention center in the last 12 months" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.timeInDetCenter?.toString())}" name="timeInDetCenter" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'tatttoos', 'errors')}">
        <label for="tatttoos"><g:message code="DonorEligibilityGtex.tatttoos.label" default="46. Tattoos done in the last 12 months (professionally) if done in a state that does not regulate tattoo parlors" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.tatttoos?.toString())}" name="tatttoos" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'growthHarmone', 'errors')}">
        <label for="growthHarmone"><g:message code="DonorEligibilityGtex.growthHarmone.label" default="47. Received human growth hormone" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.growthHarmone?.toString())}" name="growthHarmone" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'prescDrugAbuse', 'errors')}">
        <label for="prescDrugAbuse"><g:message code="DonorEligibilityGtex.prescDrugAbuse.label" default="48. Prescription pill use that are not prescribed to the donor" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.prescDrugAbuse?.toString())}" name="prescDrugAbuse" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'intravenDrugAbuse', 'errors')}">
        <label for="intravenDrugAbuse"><g:message code="DonorEligibilityGtex.intravenDrugAbuse.label" default="49. Intravenous Drug Abuse (IVDA) in the past 5 years" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.intravenDrugAbuse?.toString())}" name="intravenDrugAbuse" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'syphilisTreat', 'errors')}">
        <label for="syphilisTreat"><g:message code="DonorEligibilityGtex.syphilisTreat.label" default="50. Current infection or treatment in the last 12 months for syphilis" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.syphilisTreat?.toString())}" name="syphilisTreat" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'gonorrheaTreat', 'errors')}">
        <label for="gonorrheaTreat"><g:message code="DonorEligibilityGtex.gonorrheaTreat.label" default="51. Current infection or treatment in the last 12 months for gonorrhea" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.gonorrheaTreat?.toString())}" name="gonorrheaTreat" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfphysicContact', 'errors')}">
        <label for="histOfphysicContact"><g:message code="DonorEligibilityGtex.histOfphysicContact.label" default="52. Living or close physical contact with someone in the last 12 months who has been diagnosed with:" /></label>
            </td><td></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'hepatitisB', 'errors')}">
        <label for="hepatitisB"><g:message code="DonorEligibilityGtex.hepatitisB.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hepatitis B" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.hepatitisB?.toString())}" name="hepatitisB" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'hepatitisC', 'errors')}">
        <label for="hepatitisC"><g:message code="DonorEligibilityGtex.hepatitisC.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hepatitis C" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.hepatitisC?.toString())}" name="hepatitisC" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'hiv', 'errors')}">
        <label for="hiv"><g:message code="DonorEligibilityGtex.hiv.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HIV" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.hiv?.toString())}" name="hiv" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'timeInUk', 'errors')}">
        <label for="timeInUk"><g:message code="DonorEligibilityGtex.timeInUk.label" default="53. Three or more months cumulatively spent in the UK any time from 1980-1996" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.timeInUk?.toString())}" name="timeInUk" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'nonProfpiercing', 'errors')}">
        <label for="nonProfpiercing"><g:message code="DonorEligibilityGtex.nonProfpiercing.label" default="54. Non-professional piercing " /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.nonProfpiercing?.toString())}" name="nonProfpiercing" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'nonProfTattoos', 'errors')}">
        <label for="nonProfTattoos"><g:message code="DonorEligibilityGtex.nonProfTattoos.label" default="55. Non-professional tattoos" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.nonProfTattoos?.toString())}" name="nonProfTattoos" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'stateRunHome', 'errors')}">
        <label for="stateRunHome"><g:message code="DonorEligibilityGtex.stateRunHome.label" default="56. A resident of a state run group home at time of death" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.stateRunHome?.toString())}" name="stateRunHome" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'timeInEurope', 'errors')}">
        <label for="timeInEurope"><g:message code="DonorEligibilityGtex.timeInEurope.label" default="57. Living in Europe for 5 or more years cumulatively since 1980" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.timeInEurope?.toString())}" name="timeInEurope" /></td>
    </tr>
    <tr class="prop">
     <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'histOfAutoImmDis', 'errors')}">
        <label for="histOfAutoImmDis"><g:message code="DonorEligibilityGtex.histOfAutoImmDis.label" default="58. History of any of the following autoimmune or degenerative neurological disease:" /></label>
    </td>
    <td></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'systLupus', 'errors')}">
        <label for="systLupus"><g:message code="DonorEligibilityGtex.systLupus.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Systemic Lupus" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.systLupus?.toString())}" name="systLupus" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'sarcoidosis', 'errors')}">
        <label for="sarcoidosis"><g:message code="DonorEligibilityGtex.sarcoidosis.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sarcoidosis" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.sarcoidosis?.toString())}" name="sarcoidosis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'scleroderma', 'errors')}">
        <label for="scleroderma"><g:message code="DonorEligibilityGtex.scleroderma.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Scleroderma" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.scleroderma?.toString())}" name="scleroderma" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'reyesSynd', 'errors')}">
        <label for="reyesSynd"><g:message code="DonorEligibilityGtex.reyesSynd.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reyes Syndrome" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.reyesSynd?.toString())}" name="reyesSynd" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'rheumArthritis', 'errors')}">
        <label for="rheumArthritis"><g:message code="DonorEligibilityGtex.rheumArthritis.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rheumatoid Arthritis " /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.rheumArthritis?.toString())}" name="rheumArthritis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'heartDis', 'errors')}">
        <label for="heartDis"><g:message code="DonorEligibilityGtex.heartDis.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Heart Disease (Idiopathic)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.heartDis?.toString())}" name="heartDis" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'alzheimers', 'errors')}">
        <label for="alzheimers"><g:message code="DonorEligibilityGtex.alzheimers.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alzheimer’s Disease" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.alzheimers?.toString())}" name="alzheimers" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'dementia', 'errors')}">
        <label for="dementia"><g:message code="DonorEligibilityGtex.dementia.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dementia with unknown cause (not from a previous CVA, infection, head trauma, or brain tumor)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.dementia?.toString())}" name="dementia" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'multiSclero', 'errors')}">
        <label for="multiSclero"><g:message code="DonorEligibilityGtex.multiSclero.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MS Multiple Sclerosis" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.multiSclero?.toString())}" name="multiSclero" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'lateralSclero', 'errors')}">
        <label for="lateralSclero"><g:message code="DonorEligibilityGtex.lateralSclero.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ALS Amyotropic Lateral Sclerosis (Lou Gehrig’s Disease)" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.lateralSclero?.toString())}" name="lateralSclero" /></td>
    </tr>
    <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'creutzfeldtJakob', 'errors')}">
        <label for="creutzfeldtJakob"><g:message code="DonorEligibilityGtex.creutzfeldtJakob.label" default="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creutzfeldt-Jakob or risk factors/blood relatives being diagnosed" /></label>
            </td>
    <td class="value"><g:yesNoUnknownRadioPicker checked="${(donorEligibilityGtexInstance?.creutzfeldtJakob?.toString())}" name="creutzfeldtJakob" /></td>
    </tr>
  </tbody>
</table>
  <table>
    <tbody>
    <tr class="prop">
     <td valign="top" class="value ${hasErrors(bean: donorEligibilityGtexInstance, field: 'endComments', 'errors')}">
        <label for="endComments"><g:message code="DonorEligibilityGtex.endComments.label" default="59. General Comments" /></label><br> <br>   
    <g:textArea style="width:800px;" cols="80" rows="5" name="endComments" value="${donorEligibilityGtexInstance?.endComments}" />
    </td>
    </tr>
  </tbody>
</table>
</div>


  
  
