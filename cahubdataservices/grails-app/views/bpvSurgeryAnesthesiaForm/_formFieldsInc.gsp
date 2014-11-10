<g:render template="/formMetadata/timeConstraint" bean="${bpvSurgeryAnesthesiaFormInstance.formMetadata}" var="formMetadata"/>
<g:render template="/caseRecord/caseDetails" bean="${bpvSurgeryAnesthesiaFormInstance.caseRecord}" var="caseRecord"/>

<g:set var="labelNumber" value="${1}"/>
<g:set var="primaryTissueType" value="${bpvSurgeryAnesthesiaFormInstance.caseRecord?.primaryTissueType?.toString()}"/>
<g:set var="isPrimaryTissueTypeLung" value="${'Lung'.equalsIgnoreCase(primaryTissueType)}"/>
<g:set var="isPrimaryTissueTypeOvary" value="${'Ovary'.equalsIgnoreCase(primaryTissueType)}"/>
<g:set var="isPrimaryTissueTypeColon" value="${'Colon'.equalsIgnoreCase(primaryTissueType)}"/>
<g:set var="isPrimaryTissueTypeKidney" value="${'Kidney'.equalsIgnoreCase(primaryTissueType)}"/>

<div class="list">
    <table id="bpvsurgeryanest" class="tdwrap tdtop">
        <tbody>
            <tr class="prop">
                <td valign="top" colspan="2" class="formheader">Pre-operative medications administration: Record medications administered in the holding area prior to participant entering the operating room.<br />If additional space is required record any additional pre-operative medications administered in #6 below.
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="surgeryDate">${labelNumber++}. Date of surgery:</label>   
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'surgeryDate', 'errors')}">
		    <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="surgeryDate" value="${bpvSurgeryAnesthesiaFormInstance?.surgeryDate}"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Pre-operative IV Sedation administered:</label>
                    <span id="bpvsurgeryanesthesiaform.preopivsedation" class="vocab-tooltip"></span>    
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSedDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.poSedDiv)}"  name="poSedDiv"/>
                </td>
            </tr>
            <tr class="prop" id="poSedDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.poSedDiv == 'Yes'?'display':'none'}">
              <td></td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="poSed1" dlist="Diazepam" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poSed1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed1Time', 'errors')}"/>
                            <g:medAdmin name="poSed2" dlist="Lorazepam" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poSed2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed2Time', 'errors')}"/>
                            <g:medAdmin name="poSed3" dlist="Midazolam" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed3Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poSed3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed3Time', 'errors')}"/>
                            <g:medAdmin name="poSed4" id="otherpreopivsedation" dlist="Other IV Sedation (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed4Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed4Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed4Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed4Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poSed4Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed4Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poSed4Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poSed4Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Pre-operative IV Opiates administered:</label>
                    <span id="bpvsurgeryanesthesiaform.preopivopiates" class="vocab-tooltip"></span>       
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOpDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.poOpDiv)}"  name="poOpDiv"/>
                </td>
            </tr>
            <tr class="prop" id="poOpDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.poOpDiv == 'Yes'?'display':'none'}">
              <td></td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="poOp1" dlist="Fentanyl" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poOp1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp1Time', 'errors')}"/>
                            <g:medAdmin name="poOp2" dlist="Hydromorphone" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poOp2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp2Time', 'errors')}"/>
                            <g:medAdmin name="poOp3" dlist="Meperidine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp3Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poOp3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp3Time', 'errors')}"/>
                            <g:medAdmin name="poOp4" dlist="Morphine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp4Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp4Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp4Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poOp4Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp4Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp4Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp4Time', 'errors')}"/>
                            <g:medAdmin name="poOp5" id="otherpreopivopiates" dlist="Other IV Opiates (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp5Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp5Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp5Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp5Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poOp5Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp5Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poOp5Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poOp5Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Pre-operative IV Antiemetics administered:</label>
                    <span id="bpvsurgeryanesthesiaform.preopivantiemetics" class="vocab-tooltip"></span>                       
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiemDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.poAntiemDiv)}"  name="poAntiemDiv"/>
                </td>
            </tr>
            <tr class="prop" id="poAntiemDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.poAntiemDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="poAntiem1" dlist="Droperidol" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem1Time', 'errors')}"/>
                            <g:medAdmin name="poAntiem2" dlist="Ondansetron" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem2Time', 'errors')}"/>
                            <g:medAdmin name="poAntiem3" id="otherpreopivantiemetics" dlist="Other IV Antiemetic (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem3Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem3Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiem3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiem3Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Pre-operative IV Anti-acids administered:</label>
                    <span id="bpvsurgeryanesthesiaform.preopivantacids" class="vocab-tooltip"></span>              
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAcDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.poAntiAcDiv)}"  name="poAntiAcDiv"/>
                </td>
            </tr>
            <tr class="prop" id="poAntiAcDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.poAntiAcDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="poAntiAc1" dlist="Ranitidine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc1Time', 'errors')}"/>
                            <g:medAdmin name="poAntiAc2" id="otherpreopivantacids" dlist="Other IV Anti-acids (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poAntiAc2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poAntiAc2Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop"> 
                <td valign="top" class="name">
                    <label>${labelNumber++}. Other Pre-operative IV Medications administered:</label>
                    <span id="bpvsurgeryanesthesiaform.otherpreopivmeds" class="vocab-tooltip"></span>          
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMedDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.poMedDiv)}"  name="poMedDiv"/>
                </td>
            </tr>
            <tr class="prop" id="poMedDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.poMedDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value">
                    <div>
                        <table>
                            <g:medAdmin name="poMed1" dlist="Other Pre-operative IV medications (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed1Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed1Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poMed1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed1Time', 'errors')}"/>
                            <g:medAdmin name="poMed2" dlist="Other Pre-operative IV medications (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poMed2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed2Time', 'errors')}"/>
                            <g:medAdmin name="poMed3" dlist="Other Pre-operative IV medications (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed3Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed3Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.poMed3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.poMed3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'poMed3Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" colspan="2" class="formheader">
                    Type of anesthesia administered: Please record ONLY ANESTHESIA agents administered PRIOR TO REMOVAL OF ORGAN.<br />If additional space is required record any additional anesthesia agents administered in #13 below.
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Local Anesthesia agents administered:</label>
                    <span id="bpvsurgeryanesthesiaform.localanesthesiapriororganremove" class="vocab-tooltip"></span> 
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnesDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.localAnesDiv)}"  name="localAnesDiv"/>
                </td>
            </tr>
            <tr class="prop" id="localAnesDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.localAnesDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="localAnes1" dlist="Lidocaine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes1Time', 'errors')}"/>
                            <g:medAdmin name="localAnes2" dlist="Procaine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes2Time', 'errors')}"/>
                            <g:medAdmin name="localAnes3" id="otherlocalanesthesiapriororganremove" dlist="Other IV Local Anesthesia agents (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes3Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes3Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.localAnes3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'localAnes3Time', 'errors')}"/>
                        </table>
                    </div> 
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Regional (Spinal/Epidural) Anesthesia agents administered:</label>
                    <span id="bpvsurgeryanesthesiaform.regionalanesthesiapriororganremove" class="vocab-tooltip"></span> 
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnesDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.regAnesDiv)}"  name="regAnesDiv"/>
                </td>
            </tr>
            <tr class="prop" id="regAnesDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.regAnesDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="regAnes1" dlist="Bupivacaine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes1Time', 'errors')}"/>
                            <g:medAdmin name="regAnes2" dlist="Lidocaine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes2Time', 'errors')}"/>
                            <g:medAdmin name="regAnes3" id="otherregionalanesthesiapriororganremove" dlist="Other Spinal/Regional Anesthetic (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes3Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes3Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.regAnes3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'regAnes3Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. IV Anesthesia agents administered:</label>
                    <span id="bpvsurgeryanesthesiaform.ivanesthesiapriororganremove" class="vocab-tooltip"></span> 
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anesDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.anesDiv)}"  name="anesDiv"/>
                </td>
            </tr>
            <tr class="prop" id="anesDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.anesDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="anes1" dlist="Brevital" namevalue="${bpvSurgeryAnesthesiaFormInstance?.anes1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.anes1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.anes1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.anes1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes1Time', 'errors')}"/>
                            <g:medAdmin name="anes2" dlist="Etomidate" namevalue="${bpvSurgeryAnesthesiaFormInstance?.anes2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.anes2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.anes2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.anes2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes2Time', 'errors')}"/>
                            <g:medAdmin name="anes3" dlist="Ketamine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.anes3Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.anes3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.anes3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.anes3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes3Time', 'errors')}"/>
                            <g:medAdmin name="anes4" dlist="Propofol" namevalue="${bpvSurgeryAnesthesiaFormInstance?.anes4Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.anes4Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes4Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.anes4Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes4Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.anes4Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes4Time', 'errors')}"/>
                            <g:medAdmin name="anes5" dlist="Sodium Thiopental" namevalue="${bpvSurgeryAnesthesiaFormInstance?.anes5Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.anes5Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes5Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.anes5Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes5Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.anes5Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes5Time', 'errors')}"/>
                            <g:medAdmin name="anes6" id="otherivanesthesiapriororganremove" dlist="Other IV Anesthesia Agents (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.anes6Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes6Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.anes6Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes6Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.anes6Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes6Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.anes6Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'anes6Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. IV Narcotic/Opiate agents administered:</label>
                    <span id="bpvsurgeryanesthesiaform.ivnarcoticpriororganremove" class="vocab-tooltip"></span> 
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOpDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.narcOpDiv)}"  name="narcOpDiv"/>
                </td>
            </tr>
            <tr class="prop" id="narcOpDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.narcOpDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td  class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="narcOp1" dlist="Fentanyl" namevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp1Time', 'errors')}"/>
                            <g:medAdmin name="narcOp2" dlist="Hydromorphone" namevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp2Time', 'errors')}"/>
                            <g:medAdmin name="narcOp3" dlist="Meperidine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp3Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp3Time', 'errors')}"/>
                            <g:medAdmin name="narcOp4" dlist="Morphine" namevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp4Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp4Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp4Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp4Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp4Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp4Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp4Time', 'errors')}"/>
                            <g:medAdmin name="narcOp5" id="otherivnarcoticpriororganremove" dlist="Other Narcotics/Opiates (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp5Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp5Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp5Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp5Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp5Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp5Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.narcOp5Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'narcOp5Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. IV Muscle Relaxants administered:</label>
                    <span id="bpvsurgeryanesthesiaform.ivmusclerelaxpriororganremove" class="vocab-tooltip"></span> 
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelaxDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.musRelaxDiv)}"  name="musRelaxDiv"/>
                </td>
            </tr>
            <tr class="prop" id="musRelaxDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.musRelaxDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="musRelax1" dlist="Pancuronium" namevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax1Time', 'errors')}"/>
                            <g:medAdmin name="musRelax2" dlist="Suxamethonium Chloride" namevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax2Time', 'errors')}"/>
                            <g:medAdmin name="musRelax3" dlist="Vercuronium" namevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax3Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax3Time', 'errors')}"/>
                            <g:medAdmin name="musRelax4" id="otherivmusclerelaxpriororganremove" dlist="Other Muscle Relaxants (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax4Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax4Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax4Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax4Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax4Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax4Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.musRelax4Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'musRelax4Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Inhalation Anesthesia agents administered:</label>
                    <span id="bpvsurgeryanesthesiaform.inhalationanestheticpriororganremove" class="vocab-tooltip"></span>           
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnesDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.inhalAnesDiv)}"  name="inhalAnesDiv"/>
                </td>
            </tr>
            <tr class="prop" id="inhalAnesDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.inhalAnesDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes1Name', 'errors')}">
                    <div>
                        <table>
                            <g:medAdmin name="inhalAnes1" dlist="Isoflurane" namevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes1Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes1Time', 'errors')}"/>
                            <g:medAdmin name="inhalAnes2" dlist="Nitrous Oxide" namevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes2Name}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes2Time', 'errors')}"/>
                            <g:medAdmin name="inhalAnes3" id="otherinhalationanestheticpriororganremove" dlist="Other Inhalation Anesthesia Agents (specify)" namevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes3Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes3Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.inhalAnes3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inhalAnes3Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Additional Anesthesia agents used:</label>
                    <span id="bpvsurgeryanesthesiaform.anestheticpriororganremove" class="vocab-tooltip"></span>                       
                </td>
                <td class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnesDiv', 'errors')}">
                  <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.addtlAnesDiv)}"  name="addtlAnesDiv"/>
                </td>
            </tr>
            <tr class="prop" id="addtlAnesDivRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.addtlAnesDiv == 'Yes'?'display':'none'}">
              <td></td>                
                <td class="value">
                    <div>
                        <table>
                            <g:medAdmin name="addtlAnes1" dlist="" namevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes1Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes1Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes1Time', 'errors')}"/>
                            <g:medAdmin name="addtlAnes2" dlist="" namevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes2Time', 'errors')}"/>
                            <g:medAdmin name="addtlAnes3" id="otheranestheticpriororganremove" dlist="" namevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes3Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes3Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes3Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes3Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes3Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes3Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.addtlAnes3Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'addtlAnes3Time', 'errors')}"/>
                        </table>
                    </div>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" colspan="2" class="formheader">
                    Surgery information: Indicate whether any of the following medications were administered during surgery.
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name" colspan="2">
                    <label>${labelNumber++}. Other medications administered during surgery prior to removal of the organ:</label>
                    <span id="bpvsurgeryanesthesiaform.othermedication" class="vocab-tooltip"></span>                     
                </td>
            </tr>
            <tr class="prop subentry">
                <td valign="top" class="name"><label for="insulinAdmin">Was Insulin administered during surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insulinAdmin', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.insulinAdmin)}"  name="insulinAdmin"/>
                </td>
            </tr>

            <tr id="InsulinRow1" style="display:${bpvSurgeryAnesthesiaFormInstance?.insulinAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="insul1" dlist="Insulin" namevalue="${bpvSurgeryAnesthesiaFormInstance?.insul1Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul1Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.insul1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.insul1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.insul1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul1Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr id="InsulinRow2" style="display:${bpvSurgeryAnesthesiaFormInstance?.insulinAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="insul2" dlist="Insulin" namevalue="${bpvSurgeryAnesthesiaFormInstance?.insul2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.insul2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.insul2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.insul2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'insul2Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name"><label for="steroidAdmin">Were Steroids administered during surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroidAdmin', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.steroidAdmin)}"  name="steroidAdmin"/>
                </td>
            </tr>

            <tr id="SteroidRow1" style="display:${bpvSurgeryAnesthesiaFormInstance?.steroidAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="steroid1" dlist="Steroid" namevalue="${bpvSurgeryAnesthesiaFormInstance?.steroid1Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid1Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.steroid1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.steroid1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.steroid1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid1Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr id="SteroidRow2" style="display:${bpvSurgeryAnesthesiaFormInstance?.steroidAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="steroid2" dlist="Steroid" namevalue="${bpvSurgeryAnesthesiaFormInstance?.steroid2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.steroid2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.steroid2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.steroid2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'steroid2Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name"><label for="antibioAdmin">Were Antibiotics administered during surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibioAdmin', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.antibioAdmin)}"  name="antibioAdmin"/>
                </td>
            </tr>

            <tr id="AntibioRow1" style="display:${bpvSurgeryAnesthesiaFormInstance?.antibioAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="antibio1" dlist="Antibiotic" namevalue="${bpvSurgeryAnesthesiaFormInstance?.antibio1Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio1Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.antibio1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.antibio1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.antibio1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio1Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr id="AntibioRow2" style="display:${bpvSurgeryAnesthesiaFormInstance?.antibioAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="antibio2" dlist="Antibiotic" namevalue="${bpvSurgeryAnesthesiaFormInstance?.antibio2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.antibio2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.antibio2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.antibio2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'antibio2Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name"><label for="othMedAdmin">Were other medications administered during surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'othMedAdmin', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.othMedAdmin)}"  name="othMedAdmin"/>
                </td>
            </tr>

            <tr id="OthMedRow1" style="display:${bpvSurgeryAnesthesiaFormInstance?.othMedAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="med1" dlist="Other Medication" namevalue="${bpvSurgeryAnesthesiaFormInstance?.med1Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med1Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.med1Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med1Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.med1Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med1Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.med1Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med1Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr id="OthMedRow2" style="display:${bpvSurgeryAnesthesiaFormInstance?.othMedAdmin == 'Yes'?'display':'none'}">
                <td></td>
                <td>
                    <table>
                        <g:medAdmin name="med2" dlist="Other Medication" namevalue="${bpvSurgeryAnesthesiaFormInstance?.med2Name}" nameerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med2Name', 'errors')}" dosevalue="${bpvSurgeryAnesthesiaFormInstance?.med2Dose}" doseerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med2Dose', 'errors')}" unitvalue="${bpvSurgeryAnesthesiaFormInstance?.med2Unit}" uniterror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med2Unit', 'errors')}" timevalue="${bpvSurgeryAnesthesiaFormInstance?.med2Time}" timeerror="${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'med2Time', 'errors')}"/>
                    </table>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" colspan="2" class="formheader">
                    <label>Surgical procedure details</label>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="firstIncisTime">${labelNumber++}. Time of first incision:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'firstIncisTime', 'errors')}">
                    <g:textField name="firstIncisTime" value="${bpvSurgeryAnesthesiaFormInstance?.firstIncisTime}" class="timeEntry"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="surgicalProc">${labelNumber++}. Surgical Procedure:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'surgicalProc', 'errors')}">
                    <g:if test="${isPrimaryTissueTypeColon}">
                        <g:select name="surgicalProc" from="${['Abdominoperineal resection','Colectomy','Colectomy, left','Colectomy, right','Colectomy, sigmoid','Colectomy, subtotal','Colectomy, total','Colectomy, transverse','Low anterior resection','Proctectomy','Proctocolectomy','Rectosigmoidectomy','Other-specify']}" value="${bpvSurgeryAnesthesiaFormInstance?.surgicalProc}" noSelection="['': '']"/>
                    </g:if>
                    <g:elseif test="${isPrimaryTissueTypeLung}">
                        <g:select name="surgicalProc" from="${['Lung biopsy, left','Lung biopsy, right','Lung lobectory, left','Lung lobectomy, right','Lung mass excision, left','Lung mass excision, right','Pneumonectomy, left','Pneumonectomy, right','Wedge resection, left','Wedge resection, right','Other-specify']}" value="${bpvSurgeryAnesthesiaFormInstance?.surgicalProc}" noSelection="['': '']"/>
                    </g:elseif>
                    <g:elseif test="${isPrimaryTissueTypeKidney}">
                        <g:select name="surgicalProc" from="${['Kidney biopsy (left)','Kidney biopsy (right)','Kidney mass excision (left)','Kidney mass excision (right)','Nephrectomy (partial left)','Nephrectomy (partial right)','Nephrectomy (radical left)','Nephrectomy (radical right)','Nephroureterectomy (left)','Nephroureterectomy (right)','Other-specify']}" value="${bpvSurgeryAnesthesiaFormInstance?.surgicalProc}" noSelection="['': '']"/>
                    </g:elseif>
                    <g:elseif test="${isPrimaryTissueTypeOvary}">
                        <g:select name="surgicalProc" from="${['Hysterectomy with bilateral salpingo-oophorectomy','Hysterectomy with left salpingo-oophorectomy','Hysterectomy with right salpingo-oophorectomy','Oophorectomy, bilateral','Oophorectomy, left','Oophorectomy, right','Pelvic exenteration','Pelvic mass excision','Salpingo-oophorectomy, bilateral','Salpingo-oophorectomy, left','Salpingo-oophorectomy, right','Other-specify']}" value="${bpvSurgeryAnesthesiaFormInstance?.surgicalProc}" noSelection="['': '']"/>
                    </g:elseif>
                </td>
            </tr>

            <tr class="prop" id="otherSurgicalProcRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.surgicalProc == 'Other-specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'otherSurgicalProc', 'errors')}">
                    <g:textField name="otherSurgicalProc" value="${bpvSurgeryAnesthesiaFormInstance?.otherSurgicalProc}"/>
                </td>
            </tr>

            <g:if test="${isPrimaryTissueTypeKidney || isPrimaryTissueTypeOvary}">
                <tr class="prop subentry">
                    <td valign="top" class="name">
                        <label for="surgicalMethod">Surgical method:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'surgicalMethod', 'errors')}">
                        <g:if test="${isPrimaryTissueTypeKidney}">
                            <g:select name="surgicalMethod" from="${['Laparoscopic','Open','Robotic','Other-specify']}" value="${bpvSurgeryAnesthesiaFormInstance?.surgicalMethod}" noSelection="['': '']"/>
                        </g:if>
                        <g:elseif test="${isPrimaryTissueTypeOvary}">
                            <g:select name="surgicalMethod" from="${['Abdominal','Vaginal','Laparoscopic','Laparoscopically Assisted Vaginal Hysterectomy (LAVH)','Supracervical','Robotic','Other-specify']}" value="${bpvSurgeryAnesthesiaFormInstance?.surgicalMethod}" noSelection="['': '']"/>
                        </g:elseif>
                    </td>
                </tr>
                <tr class="prop" id="otherSurgicalMethodRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.surgicalMethod == 'Other-specify'?'display':'none'}">
                    <td valign="top" class="name"></td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'otherSurgicalMethod', 'errors')}">
                        <g:textField name="otherSurgicalMethod" value="${bpvSurgeryAnesthesiaFormInstance?.otherSurgicalMethod}"/>
                    </td>
                </tr>
            </g:if>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Time of first clamp:</label>
                    <span id="bpvsurgeryanesthesiaform.firstclamptime" class="vocab-tooltip"></span>
                </td>
                <td valign="top" >
                  <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'firstClampTimeLeft', 'errors')}">
                    <span class="labelFieldCombo">
                      <g:if test="${isPrimaryTissueTypeOvary}">
                        <label for="firstClampTimeLeft">Left:</label>
                      </g:if>
                      <g:textField name="firstClampTimeLeft" value="${bpvSurgeryAnesthesiaFormInstance?.firstClampTimeLeft}" class="timeEntry ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'firstClampTimeLeft', 'errors')}" onBlur="getTimeDifference('Left', '')"/>
                    </span>
                  </span>
                  <g:if test="${isPrimaryTissueTypeOvary}">
                    <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'firstClampTimeRight', 'errors')}">
                      <span class="labelFieldCombo" >
                        <label for="firstClampTimeRight">Right:</label>
                          <g:textField name="firstClampTimeRight" value="${bpvSurgeryAnesthesiaFormInstance?.firstClampTimeRight}" class="timeEntry ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'firstClampTimeRight', 'errors')}" onBlur="getTimeDifference('Right', '')"/>
                      </span>
                    </span>
                  </g:if> 
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Time of second clamp:</label>
                    <span id="bpvsurgeryanesthesiaform.secondclamptime" class="vocab-tooltip"></span>
                </td>
                <td valign="top" >
                  <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'secondClampTimeLeft', 'errors')}">
                    <span class="labelFieldCombo">
                      <g:if test="${isPrimaryTissueTypeOvary}">
                        <label for="secondClampTimeLeft">Left:</label>
                      </g:if>
                      <g:textField name="secondClampTimeLeft" value="${bpvSurgeryAnesthesiaFormInstance?.secondClampTimeLeft}" size="4" maxlength="5" onBlur="getSecondClampTimeEntry('Left')"/>
                    </span>
                  </span>
                  <g:if test="${isPrimaryTissueTypeOvary}">
                    <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'secondClampTimeRight', 'errors')}">
                      <span class="labelFieldCombo">
                        <label for="secondClampTimeRight">Right:</label>
                        <g:textField name="secondClampTimeRight" value="${bpvSurgeryAnesthesiaFormInstance?.secondClampTimeRight}" size="4" maxlength="5" onBlur="getSecondClampTimeEntry('Right')"/>
                      </span>
                    </span>
                  </g:if>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Time of organ resection:</label>
                    <span id="bpvsurgeryanesthesiaform.organresectiontime" class="vocab-tooltip"></span>
                </td>
                <td valign="top" >
                  <span class="labelFieldCombo" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'organResecTimeLeft', 'errors')}">
                    <g:if test="${isPrimaryTissueTypeOvary}">
                        <label for="organResecTimeLeft">Left:</label>
                    </g:if>
                    <g:textField name="organResecTimeLeft" value="${bpvSurgeryAnesthesiaFormInstance?.organResecTimeLeft}" class="timeEntry ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'organResecTimeLeft', 'errors')}" onBlur="getTimeDifference('Left', '')"/>
                  </span>
                  <g:if test="${isPrimaryTissueTypeOvary}">
                    <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'organResecTimeRight', 'errors')}">
                      <span class="labelFieldCombo">
                        <label for="organResecTimeRight">Right:</label>
                          <g:textField name="organResecTimeRight" value="${bpvSurgeryAnesthesiaFormInstance?.organResecTimeRight}" class="timeEntry ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'organResecTimeRight', 'errors')}" onBlur="getTimeDifference('Right', '')"/>
                      </span>
                    </span>
                  </g:if>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>${labelNumber++}. In Vivo Intra-operative Ischemic Period (minutes):</label>
                    <span id="bpvsurgeryanesthesiaform.intraopischemicperiodmin" class="vocab-tooltip"></span>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inVivoIntOpIschemPdLeft', 'errors')}">
                  <span class="labelFieldCombo">
                    <g:if test="${isPrimaryTissueTypeOvary}">
                        <label for="inVivoIntOpIschemPdLeft">Left:</label>
                    </g:if>
                    <g:textField size="4" name="inVivoIntOpIschemPdLeft" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inVivoIntOpIschemPdLeft')}" onBlur="getTimeDifference('Left', 'InVivo')" />
                  </span>
                  <span class="labelFieldCombo">
                    <g:if test="${isPrimaryTissueTypeOvary}">
                      <label for="inVivoIntOpIschemPdRight">Right:</label>
                        <g:textField size="4" name="inVivoIntOpIschemPdRight" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'inVivoIntOpIschemPdRight')}" onBlur="getTimeDifference('Right', 'InVivo')" />
                    </g:if>
                  </span>
                </td>
            </tr>

            <tr class="prop">
                <td colspan="2" class="name ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'excFirst15PostAnesInd', 'errors')}">
                    <label for="excFirst15PostAnesInd">${labelNumber++}. Describe BP excursions from time of anesthesia induction to 15 minutes post:</label><br />
                    <g:textArea class="textwide" name="excFirst15PostAnesInd" cols="40" rows="5" value="${bpvSurgeryAnesthesiaFormInstance?.excFirst15PostAnesInd}"/>
                </td>
            </tr>

            <tr class="prop">
                <td colspan="2" class="name ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'excAnesInd2OrganExc', 'errors')}">
                    <label for="excAnesInd2OrganExc">${labelNumber++}. Describe BP excursions from 15 minutes post anesthesia induction to organ excision:</label><br />
                    <g:textArea class="textwide" name="excAnesInd2OrganExc" cols="40" rows="5" value="${bpvSurgeryAnesthesiaFormInstance?.excAnesInd2OrganExc}"/>
                </td>
            </tr>

            <tr class="prop clearborder">
                <td valign="top" class="name">
                    <label>${labelNumber++}. Temperature:</label>
                </td>
                <td></td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name">
                    <label for="temperature1">First Participant temperature recorded in OR:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'temperature1', 'errors')}">
                    <g:textField size="4" name="temperature1" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'temperature1')}" onkeyup="isNumericValidation(this)"/>&nbsp;&nbsp;
                    <g:select name="temperature1Unit" value="${bpvSurgeryAnesthesiaFormInstance?.temperature1Unit}" keys="${['Fahrenheit']}" from="${['°F']}" noSelection="['Celsius':'°C']"/>
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name">
                    <label for="timeTemp1">Time of first temperature:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'timeTemp1', 'errors')}">
                    <g:textField name="timeTemp1" value="${bpvSurgeryAnesthesiaFormInstance?.timeTemp1}" class="timeEntry"/>
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name">
                    <label for="temperature2">Second Participant temperature recorded in OR:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'temperature2', 'errors')}">
                    <g:textField size="4" name="temperature2" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'temperature2')}" onkeyup="isNumericValidation(this)"/>&nbsp;&nbsp;
                    <g:select name="temperature2Unit" value="${bpvSurgeryAnesthesiaFormInstance?.temperature2Unit}" keys="${['Fahrenheit']}" from="${['°F']}" noSelection="['Celsius':'°C']"/>
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name">
                    <label for="timeTemp2">Time of second temperature:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'timeTemp2', 'errors')}">
                    <g:textField name="timeTemp2" value="${bpvSurgeryAnesthesiaFormInstance?.timeTemp2}" class="timeEntry"/>
                </td>
            </tr>

            <tr class="prop">
                <td colspan="2" class="name">
                    <label for="epochsO2">${labelNumber++}. Describe Epochs of Oxygen (O2) desaturation of <92% for >5 minutes prior to organ excision:</label><br />
                    <g:textArea class="textwide" name="epochsO2" cols="40" rows="5" value="${bpvSurgeryAnesthesiaFormInstance?.epochsO2}"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="co2Level">${labelNumber++}. Carbon Dioxide level (CO2) recorded at time closest to organ excision:</label>
                </td>
                <td valign="top">
                    <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'co2LevelValue', 'errors')}">
                        <g:textField size="6" name="co2LevelValue" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'co2LevelValue')}" onkeyup="isNumericValidation(this)"/>&nbsp;
                    </span>
                    <span class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'co2LevelUnit', 'errors')}">
                        <g:select name="co2LevelUnit" from="${nci.obbr.cahub.forms.bpv.BpvSurgeryAnesthesiaForm$CO2Unit?.values()}" keys="${nci.obbr.cahub.forms.bpv.BpvSurgeryAnesthesiaForm$CO2Unit?.values()*.name()}" value="${bpvSurgeryAnesthesiaFormInstance?.co2LevelUnit?.name()}"/> 
                    </span>
                    <g:if test="${(bpvSurgeryAnesthesiaFormInstance?.co2LevelUnit.toString() == 'Other, Specify') || (bpvSurgeryAnesthesiaFormInstance?.co2LevelUnit.toString() == 'OTH')}">
                        <span class="prop" id="co2LevelUnitOtherRow" style="display:display" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'co2LevelUnitOther', 'errors')}">
                            &nbsp;&nbsp;<g:textField size="10" name="co2LevelUnitOther" value="${bpvSurgeryAnesthesiaFormInstance?.co2LevelUnitOther}" />
                        </span>
                    </g:if>
                    <g:else>
                        <span class="prop" id="co2LevelUnitOtherRow" style="display:none" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'co2LevelUnitOther', 'errors')}">
                            &nbsp;&nbsp;<g:textField size="10" name="co2LevelUnitOther" value="${bpvSurgeryAnesthesiaFormInstance?.co2LevelUnitOther}" />
                        </span>
                    </g:else>
                    
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" colspan="2" class="formheader">Intraoperative blood product administration.</td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="albuminCount">${labelNumber}a. Albumin:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'albuminCount', 'errors')}">
                    <g:textField size="4" name="albuminCount" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'albuminCount')}"/> ml
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="rbcCount">${labelNumber}b. Packed Red Blood Cells:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'rbcCount', 'errors')}">
                    <g:textField size="4" name="rbcCount" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'rbcCount')}"/> #units
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="plateletCount">${labelNumber}c. Platelets:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'plateletCount', 'errors')}">
                    <g:textField size="4" name="plateletCount" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'plateletCount')}"/> ml
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="frozPlasma">${labelNumber++}d. Fresh Frozen Plasma:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'frozPlasma', 'errors')}">
                    <g:textField size="4" name="frozPlasma" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'frozPlasma')}"/> #units
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" colspan="2" class="formheader">Participant Fluid output.</td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="bloodLossb4OrganExc">${labelNumber++}. Blood loss:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'bloodLossb4OrganExc', 'errors')}">
                    <g:textField size="4" name="bloodLossb4OrganExc" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'bloodLossb4OrganExc')}"/> ml
                </td>
            </tr>

            <tr class="prop subentry">
                <td valign="top" class="name"><label for="bloodLossRecPt">At what point was blood loss recorded? Select one:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'bloodLossRecPt', 'errors')}">
                    <g:select name="bloodLossRecPt" value="${bpvSurgeryAnesthesiaFormInstance?.bloodLossRecPt}" from="${['Prior to organ excision', 'End of surgery']}" noSelection="['': '']"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="urineVolb4Exc">${labelNumber++}. Urine volume excreted:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'urineVolb4Exc', 'errors')}">
                    <g:textField size="4" name="urineVolb4Exc" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'urineVolb4Exc')}"/> ml
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name"><label for="urineVolRecPt">At what point was urine output recorded? Select one:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'urineVolRecPt', 'errors')}">
                    <g:select name="urineVolRecPt" value="${bpvSurgeryAnesthesiaFormInstance?.urineVolRecPt}" from="${['Prior to organ excision', 'End of surgery']}" noSelection="['': '']"/>
                </td>
            </tr>

            <g:if test="${isPrimaryTissueTypeOvary}">
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="isPelvicWashColl">${labelNumber++}. Record Pelvic washing collection?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'isPelvicWashColl', 'errors')}">
                        <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.isPelvicWashColl)}"  name="isPelvicWashColl"/>
                    </td>
                </tr>

                <tr class="prop" id="collPelvicWashRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.isPelvicWashColl == 'Yes'?'display':'none'}">
                    <td valign="top" class="name">
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'collPelvicWash', 'errors')}">
                        <g:textField size="4" name="collPelvicWash" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'collPelvicWash')}"/> ml
                    </td>
                </tr>
          </g:if>

          <g:if test="${isPrimaryTissueTypeOvary || isPrimaryTissueTypeLung || isPrimaryTissueTypeColon}">
              <tr class="prop">
                  <td valign="top" class="name">
                      <label for="isAscitesFldColl">${labelNumber++}. Was ascites fluid collected?</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'isAscitesFldColl', 'errors')}">
                      <g:bpvYesNoRadioPicker checked="${(bpvSurgeryAnesthesiaFormInstance?.isAscitesFldColl)}"  name="isAscitesFldColl"/>
                  </td>
              </tr>

              <tr class="prop" id="collAscFluidRow" style="display:${bpvSurgeryAnesthesiaFormInstance?.isAscitesFldColl == 'Yes'?'display':'none'}">
                  <td valign="top" class="name">
                  </td>
                  <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'collAscFluid', 'errors')}">
                      <g:textField size="4" name="collAscFluid" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'collAscFluid')}"/> ml
                  </td>
              </tr>
          </g:if>

          <tr class="prop">
              <td valign="top" colspan="2" class="formheader">Additional information</td>
          </tr>

          <tr class="prop">
              <td valign="top" class="name">
                  <label for="durFastingb4Surg">${labelNumber++}. Duration of fasting prior to surgery:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'durFastingb4Surg', 'errors')}">
                  <g:textField size="4" name="durFastingb4Surg" value="${fieldValue(bean: bpvSurgeryAnesthesiaFormInstance, field: 'durFastingb4Surg')}"/> hours
              </td>
          </tr>

          <tr class="prop">
              <td colspan="2" class="name ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'bowelPrepb4Surg', 'errors')}">
                  <label for="bowelPrepb4Surg">${labelNumber++}. Describe pre-operative bowel preparation prior to surgery:</label><br />
                  <g:textArea class="textwide" name="bowelPrepb4Surg" cols="40" rows="5" value="${bpvSurgeryAnesthesiaFormInstance?.bowelPrepb4Surg}"/>
              </td>
          </tr>

          <tr class="prop">
              <td colspan="2" class="name ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'notableEvtsSurg', 'errors')}">
                  <label for="notableEvtsSurg">${labelNumber++}. Other notable events during surgery:</label><br />
                  <g:textArea class="textwide" name="notableEvtsSurg" cols="40" rows="5" value="${bpvSurgeryAnesthesiaFormInstance?.notableEvtsSurg}"/>
              </td>
          </tr>

          <tr class="prop">
              <td valign="top" class="name">
                  <label for="specOrLeavingTime">${labelNumber++}. Time Specimen left OR:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: bpvSurgeryAnesthesiaFormInstance, field: 'specOrLeavingTime', 'errors')}">
                  <g:textField name="specOrLeavingTime" value="${bpvSurgeryAnesthesiaFormInstance?.specOrLeavingTime}" class="timeEntry"/>
              </td>
          </tr>
        </tbody>
    </table>
</div>
