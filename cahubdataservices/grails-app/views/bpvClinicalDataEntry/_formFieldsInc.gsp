<g:render template="/formMetadata/timeConstraint" bean="${bpvClinicalDataEntryInstance.formMetadata}" var="formMetadata"/>
<g:render template="/caseRecord/caseDetails" bean="${bpvClinicalDataEntryInstance.caseRecord}" var="caseRecord" />


<div class="list">
    <g:set var="caseCdrVersion" value="${bpvClinicalDataEntryInstance.caseRecord?.cdrVer}"/>
    
    <%
       Double versionValue = 0.0
       if (caseCdrVersion)
       {
           try { versionValue = Double.parseDouble(caseCdrVersion.substring(0, (caseCdrVersion.indexOf('.') + 2 ))) }
           catch(Exception ee) { versionValue = 0.0 }
       }
    %>
    <table class="tdwrap">
        <tbody>
            <%
                def labelNumber = 1
            %>

          <tr><td colspan="2" class="formheader">History of Cancer in Participant or blood relatives</td></tr>
          <tr class="prop clearborder">
                <td valign="top" class="name">
                    <label for="prevMalignancy">${labelNumber++}. Does the Participant have a history of prior malignancy?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'prevMalignancy', 'errors')}">
                    <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.prevMalignancy)}"  name="prevMalignancy" />
                </td>
            </tr>
            
            <tr class="prop clearborder subentry" id="PrevCancerTable" style="display:${bpvClinicalDataEntryInstance?.prevMalignancy == 'Yes'?'display':'none'}">

              <td colspan="2">
                <g:render template="PrevCancerTabContent"/>
          </td></tr>
            <tr id="prevCancerSave" class="subentry" style="display:${bpvClinicalDataEntryInstance?.prevMalignancy == 'Yes'?'display':'none'}">
              <td colspan="2"><g:actionSubmit style="display:none" id="prevCancerSaveBtn" action="savePrevCancerDiag" value="Save" /><g:actionSubmit style="display:none" id="prevCancerCancelBtn" value="Cancel" /><g:actionSubmit class="Btn commentsdtentry" id="prevCancerAddBtn" value="Add" /></td>
            </tr>            
            
            <tr class="clearborder prop" >
                <td valign="top" class="name">
                    <label>${labelNumber++}. Participant's blood relatives who have had a history of Cancer:</label>
                    <span id="bpvclinicaldataentry.bloodrelcancer" class="vocab-tooltip"></span>
                </td>
            </tr>
            <tr class="prop subentry">
              <td colspan="2" class="${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'bloodRelCancer1', 'errors')}">
                    <table id="bloodrelative" class="tabledatecomments">
                        <tr><th class="relationship">Blood Relative</th><th class="cancer">Type of Cancer</th></tr>
                        <tbody>
                          <tr><td><g:checkBox id="bloodRelCancer1" name="bloodRelCancer1" value="Aunt" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer1 == 'Aunt'}" onClick="checkRelatives(this.form, 1)"/> <label for="bloodRelCancer1">Aunt</label></td><td id="relCancerType1Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType1', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer1 == 'Aunt'?'display':'none'}" name="relCancerType1" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType1}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer2" name="bloodRelCancer2" value="Brother" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer2 == 'Brother'}" onClick="checkRelatives(this.form, 2)"/> <label for="bloodRelCancer2">Brother</label></td><td id="relCancerType2Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType2', 'errors')}"><g:textField  style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer2 == 'Brother'?'display':'none'}" name="relCancerType2" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType2}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer3" name="bloodRelCancer3" value="Daughter" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer3 == 'Daughter'}" onClick="checkRelatives(this.form, 3)"/> <label for="bloodRelCancer3">Daughter</label></td><td id="relCancerType3Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType3', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer3 == 'Daughter'?'display':'none'}" name="relCancerType3" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType3}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer4" name="bloodRelCancer4" value="Father" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer4 == 'Father'}" onClick="checkRelatives(this.form, 4)"/> <label for="bloodRelCancer4">Father</label></td><td id="relCancerType4Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType4', 'errors')}" ><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer4 == 'Father'?'display':'none'}"  name="relCancerType4" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType4}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer5" name="bloodRelCancer5" value="Mother" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer5 == 'Mother'}" onClick="checkRelatives(this.form, 5)"/> <label for="bloodRelCancer5">Mother</label></td><td id="relCancerType5Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType5', 'errors')}"` ><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer5 == 'Mother'?'display':'none'}" name="relCancerType5" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType5}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer6" name="bloodRelCancer6" value="Sister" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer6 == 'Sister'}" onClick="checkRelatives(this.form, 6)"/> <label for="bloodRelCancer6">Sister</label></td><td id="relCancerType6Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType6', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer6 == 'Sister'?'display':'none'}"  name="relCancerType6" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType6}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer7" name="bloodRelCancer7" value="Son" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer7 == 'Son'}" onClick="checkRelatives(this.form, 7)"/> <label for="bloodRelCancer7">Son</label></td><td id="relCancerType7Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType7', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer7 == 'Son'?'display':'none'}" name="relCancerType7" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType7}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer8" name="bloodRelCancer8" value="Uncle" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer8 == 'Uncle'}" onClick="checkRelatives(this.form, 8)"/> <label for="bloodRelCancer8">Uncle</label></td><td id="relCancerType8Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType8', 'errors')}" ><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer8 == 'Uncle'?'display':'none'}" name="relCancerType8" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType8}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer9" name="bloodRelCancer9" value="Grandmother" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer9 == 'Grandmother'}" onClick="checkRelatives(this.form, 9)"/> <label for="bloodRelCancer9">Grandmother</label></td><td id="relCancerType9Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType9', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer9 == 'Grandmother'?'display':'none'}" name="relCancerType9" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType9}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer10" name="bloodRelCancer10" value="Grandfather" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer10 == 'Grandfather'}" onClick="checkRelatives(this.form, 10)"/> <label for="bloodRelCancer10">Grandfather</label></td><td id="relCancerType10Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType10', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer10 == 'Grandfather'?'display':'none'}" name="relCancerType10" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType10}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer11" name="bloodRelCancer11" value="Nephew" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer11 == 'Nephew'}" onClick="checkRelatives(this.form, 11)"/> <label for="bloodRelCancer11">Nephew</label></td><td id="relCancerType11Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType11', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer11 == 'Nephew'?'display':'none'}"  name="relCancerType11" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType11}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer12" name="bloodRelCancer12" value="Niece" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer12 == 'Niece'}" onClick="checkRelatives(this.form, 12)"/> <label for="bloodRelCancer12">Niece</label></td><td id="relCancerType12Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType12', 'errors')}" ><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer12 == 'Niece'?'display':'none'}"  name="relCancerType12" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType12}" /></td></tr>
                          <tr><td><g:checkBox id="bloodRelCancer13" name="bloodRelCancer13" value="Other" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer13 == 'Other'}" onClick="checkRelatives(this.form, 13)"/> <label for="bloodRelCancer13">Other - Specify</label></td><td></td></tr>
                          <tr><td class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'othBloodRelCancer', 'errors')}"><g:textField id="othbloodRel" style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer13 == 'Other'?'display':'none'}" name="othBloodRelCancer" maxlength="255" value="${bpvClinicalDataEntryInstance?.othBloodRelCancer}" />&nbsp;</td><td id="relCancerType13Cell" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'relCancerType13', 'errors')}"><g:textField style="display:${bpvClinicalDataEntryInstance?.bloodRelCancer13 == 'Other'?'display':'none'}" name="relCancerType13" maxlength="255" value="${bpvClinicalDataEntryInstance?.relCancerType13}" /></td></tr>
                          <g:if test="${versionValue >= 6.0}">
                            <tr><td><g:checkBox id="bloodRelCancer14" name="bloodRelCancer14" value="None" checked="${bpvClinicalDataEntryInstance?.bloodRelCancer14 == 'None'}" onClick="checkRelatives(this.form, 14)"/> <label for="bloodRelCancer14">None</label></td><td></td></tr>
                          </g:if>
                        </tbody>
                    </table>
                </td>
            </tr>

            <tr class="prop clearborder" >
                <td valign="top" class="name">
                    <label>${labelNumber++}. Does the Participant have an immunosuppressive issue (HIV, organ transplant, steroid use, etc.)?</label>
                      <span id="bpvclinicaldataentry.immunosupressiveissue" class="vocab-tooltip"></span>
                </td>
                <td class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'isImmunoSupp', 'errors')}"><g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.isImmunoSupp)}"  name="isImmunoSupp" /></td>
            </tr>
            <tr class="prop subentry" id="immunoSuppRow" style="display:${bpvClinicalDataEntryInstance?.isImmunoSupp == 'Yes'?'display':'none'}">
              <td colspan="2" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'immunoSuppStatus1', 'errors')}"><div>
                    <table class="tabledatecomments">
                        <tr><td valign="top" ><g:checkBox id ="immunoSuppStatus1" name="immunoSuppStatus1" value="HIV" checked="${bpvClinicalDataEntryInstance?.immunoSuppStatus1 == 'HIV'}" />&nbsp;<label for="immunoSuppStatus1">HIV</label></td></tr>
                        <tr><td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'immunoSuppStatus2', 'errors')}"><g:checkBox id ="immunoSuppStatus2" name="immunoSuppStatus2" value="Organ transplant" checked="${bpvClinicalDataEntryInstance?.immunoSuppStatus2 == 'Organ transplant'}" />&nbsp;<label for="immunoSuppStatus2">Organ transplant</label></td></tr>
                        <tr><td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'immunoSuppStatus3', 'errors')}"><g:checkBox id ="immunoSuppStatus3" name="immunoSuppStatus3" value="Chronic systemic steroid use" checked="${bpvClinicalDataEntryInstance?.immunoSuppStatus3 == 'Chronic systemic steroid use'}" />&nbsp;<label for="immunoSuppStatus3">Chronic systemic steroid use</label></td></tr>
                        <tr><td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'immunoSuppStatus4', 'errors')}"><g:checkBox id ="immunoSuppStatus4" name="immunoSuppStatus4" value="Other - specify" checked="${bpvClinicalDataEntryInstance?.immunoSuppStatus4 == 'Other - specify'}" />&nbsp;<label for="immunoSuppStatus4">Other - specify</label></td></tr>
                        <tr class="prop" id="othImmStat" style="display:${bpvClinicalDataEntryInstance?.immunoSuppStatus4 == 'Other - specify'?'display':'none'}"><td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'otherImmunoSuppStatus', 'errors')}"><g:textField name="otherImmunoSuppStatus" maxlength="255" value="${bpvClinicalDataEntryInstance?.otherImmunoSuppStatus}" /></td></tr>
                    </table></div>
                </td>
            </tr>

            
            <tr class="prop clearborder">
                <td valign="top" class="name">
                    <label for="irradTherb4Surg">${labelNumber++}. Has the Participant received radiation therapy prior to surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'irradTherb4Surg', 'errors')}">
                    <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.irradTherb4Surg)}"  name="irradTherb4Surg" />
                </td>
            </tr>
            
            <tr class="prop clearborder subentry" id="IrradTable" style="display:${bpvClinicalDataEntryInstance?.irradTherb4Surg == 'Yes'?'display':'none'}">
              <td colspan="2">
                <g:render template="IrradTabContent"/>
          </td></tr>

            <tr id="irradSaveRow" class="subentry" style="display:${bpvClinicalDataEntryInstance?.irradTherb4Surg == 'Yes'?'display':'none'}">
              <td colspan="2"><g:actionSubmit id="irradSaveBtn" style="display:none" class="Save" action="saveIrradTher" value="Save" /><g:actionSubmit style="display:none" id="irradCancelBtn" value="Cancel" /><g:actionSubmit class="Btn" id="irradAddBtn" value="Add" /></td>
             <%--  <td><g:submitToRemote id="irradSaveBtn" asynchronus="false" url="[controller: 'bpvClinicalDataEntry', action: 'saveIrradTher']" before="validateIrrad()" value="Save" update="IrradTableContent"/><g:actionSubmit style="display:none" id="irradCancelBtn" value="Cancel" /><g:actionSubmit id="irradAddBtn" value="Add" /></td> --%>
            </tr>            
            
            <tr class="prop clearborder">
                <td valign="top" class="name">
                    <label for="chemoTherb4Surg">${labelNumber++}. Has the Participant received chemotherapy prior to surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'chemoTherb4Surg', 'errors')}">
                    <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.chemoTherb4Surg)}"  name="chemoTherb4Surg" />
                </td>
            </tr>
            
            <tr class="prop subentry clearborder" id="ChemoTable" style="display:${bpvClinicalDataEntryInstance?.chemoTherb4Surg == 'Yes'?'display':'none'}">
              <td colspan="2">
                <g:render template="ChemoTabContent"/>
          </td></tr>
            
            <tr id="chemoSaveRow" class="subentry" style="display:${bpvClinicalDataEntryInstance?.chemoTherb4Surg == 'Yes'?'display':'none'}">
              <td colspan="2"><g:actionSubmit id="chemoSaveBtn" style="display:none" action="saveChemo" value="Save" /><g:actionSubmit style="display:none" id="chemoCancelBtn" value="Cancel" /><g:actionSubmit class="Btn" id="chemoAddBtn" value="Add" /></td>
            </tr>



            <tr class="prop clearborder">
                <td valign="top" class="name">
                    <label for="immTherb4Surg">${labelNumber++}. Has the Participant received immunotherapy prior to surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'immTherb4Surg', 'errors')}">
                    <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.immTherb4Surg)}"  name="immTherb4Surg" />
                </td>
            </tr>

            <tr class="prop subentry clearborder" id="ImmunoTable" style="display:${bpvClinicalDataEntryInstance?.immTherb4Surg == 'Yes'?'display':'none'}">
              <td colspan="2">
                <g:render template="ImmunoTabContent"/>
          </td></tr>


            <tr id="immSaveRow" class="subentry" style="display:${bpvClinicalDataEntryInstance?.immTherb4Surg == 'Yes'?'display':'none'}">
              <td colspan="2"><g:actionSubmit id="immSaveBtn" style="display:none" action="saveImmunoTher" value="Save" /><g:actionSubmit style="display:none" id="immCancelBtn" value="Cancel" /><g:actionSubmit class="Btn" id="immAddBtn" value="Add" /></td>
            </tr>            

            <tr class="prop clearborder">
                <td valign="top" class="name">
                    <label for="hormTherb4Surg">${labelNumber++}. Has the Participant received hormonal therapy prior to surgery?</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hormTherb4Surg', 'errors')}">
                    <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.hormTherb4Surg)}"  name="hormTherb4Surg" />
                </td>
            </tr>
            
            <tr class="prop clearborder subentry" id="HormTable" style="display:${bpvClinicalDataEntryInstance?.hormTherb4Surg == 'Yes'?'display':'none'}" >
              <td colspan="2">
                <g:render template="HormTabContent"/>
          </td></tr>
            <tr id="hormSaveRow" class="subentry" style="display:${bpvClinicalDataEntryInstance?.hormTherb4Surg == 'Yes'?'display':'none'}" >
              <td colspan="2"><g:actionSubmit id="hormSaveBtn" style="display:none" action="saveHormonalTher" value="Save" /><g:actionSubmit style="display:none" id="hormCancelBtn" value="Cancel" /><g:actionSubmit class="Btn" id="hormAddBtn" value="Add" /></td>
            </tr>            
            

            <g:if test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString() == 'Colon'}">
              <tr class="prop clearborder">
                  <td valign="top" class="name">
                      <label>${labelNumber++}. Did the Participant have any additional Colorectal Cancer risk factors (as recorded in the medical record)?</label>
                  </td>
                  <td class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'isAddtlColoRisk', 'errors')}"><g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.isAddtlColoRisk)}"  name="isAddtlColoRisk" /></td>
              </tr>
              
              <tr class="prop" style="display:${bpvClinicalDataEntryInstance?.isAddtlColoRisk == 'Yes'?'display':'none'}" id="addtlColoRiskRow">
                  <td colspan="2" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'addtlColoRisk1', 'errors')}">
                    <table class="tabledatecomments">
                        <tr><td valign="top" >
                    <div>
                      <g:checkBox id ="addtlColoRisk1" name="addtlColoRisk1" value="${bpvClinicalDataEntryInstance?.addtlColoRisk1}" />&nbsp;<label for="addtlColoRisk1">A diet that is high in red meats (beef, lamb, or liver) and processed meats (hot dogs and some luncheon meats)</label><br>
                      <g:checkBox id ="addtlColoRisk2" name="addtlColoRisk2" value="${bpvClinicalDataEntryInstance?.addtlColoRisk2}" />&nbsp;<label for="addtlColoRisk2">Obesity – weight > 20% ideal body weight</label><br>
                      <g:checkBox id ="addtlColoRisk3" name="addtlColoRisk3" value="${bpvClinicalDataEntryInstance?.addtlColoRisk3}" />&nbsp;<label for="addtlColoRisk3">Type II diabetes</label><br>
                      <g:checkBox id ="addtlColoRisk4" name="addtlColoRisk4" value="${bpvClinicalDataEntryInstance?.addtlColoRisk4}" />&nbsp;<label for="addtlColoRisk4">Previous colorectal polyps</label><br>
                      <g:checkBox id ="addtlColoRisk5" name="addtlColoRisk5" value="${bpvClinicalDataEntryInstance?.addtlColoRisk5}" />&nbsp;<label for="addtlColoRisk5">Diagnosis of Familial adenomatous polyposis in Participant or family member</label><br>
                      <g:checkBox id ="addtlColoRisk6" name="addtlColoRisk6" value="${bpvClinicalDataEntryInstance?.addtlColoRisk6}" />&nbsp;<label for="addtlColoRisk6">Other risk factors - specify</label>
                    </div></td></tr></table>
                  </td>
              </tr>

              <tr class="prop" id="otherColRiskRow" style="display:${bpvClinicalDataEntryInstance?.addtlColoRisk6 == 'on'?'display':'none'}" >
                  <td colspan="2" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'otherAddColRisk', 'errors')}">
                      <g:textArea class="textwide" name="otherAddColRisk" cols="40" rows="5" maxlength="4000" value="${bpvClinicalDataEntryInstance?.otherAddColRisk}" />
                  </td>
              </tr>
          </g:if>

          <g:if test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString()=='Lung'}">
              <tr class="prop">
                  <td valign="top" class="name">
                      <label for="isEnvCarc">${labelNumber++}. Was the Participant exposed to environmental/workplace carcinogens (arsenic, asbestos, diesel exhaust, chromium, and/or silica)?</label>
                  </td>
                  <td class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'isEnvCarc', 'errors')}"><g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.isEnvCarc)}"  name="isEnvCarc" /></td>
              </tr>
              
              <tr class="prop" style="display:${bpvClinicalDataEntryInstance?.isEnvCarc == 'Yes'?'display':'none'}" id="envCarcRow">
                <td></td>
                  <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'envCarc1', 'errors')}">
                    <div>
                      <g:checkBox id ="envCarc1" name="envCarc1" value="${bpvClinicalDataEntryInstance?.envCarc1}" />&nbsp;<label for="envCarc1">Exposure to arsenic</label><br>
                      <g:checkBox id ="envCarc2" name="envCarc2" value="${bpvClinicalDataEntryInstance?.envCarc2}" />&nbsp;<label for="envCarc2">Exposure to asbestos</label><br>
                      <g:checkBox id ="envCarc3" name="envCarc3" value="${bpvClinicalDataEntryInstance?.envCarc3}" />&nbsp;<label for="envCarc3">Exposure to diesel exhaust</label><br>
                      <g:checkBox id ="envCarc4" name="envCarc4" value="${bpvClinicalDataEntryInstance?.envCarc4}" />&nbsp;<label for="envCarc4">Exposure to chromium</label><br>
                      <g:checkBox id ="envCarc5" name="envCarc5" value="${bpvClinicalDataEntryInstance?.envCarc5}" />&nbsp;<label for="envCarc5">Exposure to silica</label>
                    </div>
                  </td>
              </tr>

              <tr class="prop" style="display:${bpvClinicalDataEntryInstance?.isEnvCarc == 'Yes'?'display':'none'}" id="envCarcDescRow">
                  <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'envCarcExpDesc', 'errors')}">
                      <label for="envCarcExpDesc"  class="subentry">Describe circumstances and duration of exposure to environmental carcinogens if available:</label><br />
                      <g:textArea class="textwide" name="envCarcExpDesc" cols="40" rows="5" maxlength="4000" value="${bpvClinicalDataEntryInstance?.envCarcExpDesc}" />
                  </td>
              </tr>
          </g:if>

                <tr><td colspan="2" class="formheader">Infectious Diseases</td></tr>
<%--                <tr class="prop">                
                    <td valign="top" class="name">
                        <label>${labelNumber++}. Has the Participant been diagnosed with:</label>
                    </td>
                    <td></td>
                </tr>--%>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="hepB" >${labelNumber++}. Has the Participant been diagnosed with Hepatitis B?</label>
                    </td>
                    <td valign="top"  class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hepB', 'errors')}">
                        <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.hepB)}"  name="hepB" />
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="hepC" >${labelNumber++}. Has the Participant been diagnosed with Hepatitis C?</label>
                    </td>
                    <td valign="top"  class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hepC', 'errors')}">
                        <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.hepC)}"  name="hepC" />
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="hiv" >${labelNumber++}. Has the Participant been diagnosed with HIV?</label>
                    </td>
                    <td valign="top"  class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hiv', 'errors')}">
                        <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.hiv)}"  name="hiv" />
                    </td>
                </tr>

                
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="scrAssay">${labelNumber++}. Does the Participant have a history of repeatedly reactive screening assays for HIV-1 or HIV-2 antibodies regardless of the results of supplemental assays?</label>
                    </td>
                    <td valign="top"  class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'scrAssay', 'errors')}">
                        <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.scrAssay)}"  name="scrAssay" />
                    </td>
                </tr>            
                <tr class="prop" id="othInfectRow" >
                    <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'otherInfect', 'errors')}">
                        <label for="otherInfect" >${labelNumber++}.  Other infectious diseases:</label><br />
                        <g:textArea name="otherInfect" class="textwide" cols="40" rows="5" maxlength="255" value="${bpvClinicalDataEntryInstance?.otherInfect}" />
                    </td>
                </tr>                
            
            <g:if test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString() == 'Ovary'}">
              <tr><td colspan="2"  class="formheader">Reproductive History</td></tr>              
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="wasPregnant">${labelNumber++}. Has the Participant ever been pregnant?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'wasPregnant', 'errors')}">
                        <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.wasPregnant)}"  name="wasPregnant" />
                    </td>
                </tr>

                <tr class="prop" id="totPregRow" style="display:${bpvClinicalDataEntryInstance?.wasPregnant == 'Yes'?'display':'none'}" >
                    <td valign="top" class="name">
                        <label for="totPregnancies" class="subentry">What is the total number of pregnancies?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'totPregnancies', 'errors')}">
                        <g:textField size="2" maxlength="2" name="totPregnancies" value="${bpvClinicalDataEntryInstance?.totPregnancies}" onkeyup="isNumericValidation(this)" />
                    </td>
                </tr>

                <tr class="prop" id="totLBRow" style="display:${bpvClinicalDataEntryInstance?.wasPregnant == 'Yes'?'display':'none'}" >
                    <td valign="top" class="name">
                        <label for="totLiveBirths" class="subentry">What is the total number of live births?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'totLiveBirths', 'errors')}">
                        <g:textField size="2" maxlength="2" name="totLiveBirths" value="${bpvClinicalDataEntryInstance?.totLiveBirths}" onkeyup="isNumericValidation(this)" />
                    </td>
                </tr>

                <tr class="prop" id="firstChildRow" style="display:${bpvClinicalDataEntryInstance?.wasPregnant == 'Yes'?'display':'none'}" >
                    <td valign="top" class="name">
                        <label for="ageAt1stChild" class="subentry">What was the Participant's age when her first biological child was born?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'ageAt1stChild', 'errors')}">
                        <g:textField size="2" maxlength="2" name="ageAt1stChild" value="${bpvClinicalDataEntryInstance?.ageAt1stChild}" onkeyup="isNumericValidation(this)" />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="gynSurg">${labelNumber++}. Has the Participant had any of these gynecological surgeries in the past?</label>
                    </td>
                    <td valign="top"  class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'gynSurg', 'errors')}">
                      <div>
                        <g:radio name="gynSurg" id="gynSurg1" value="Hysterectomy" checked="${bpvClinicalDataEntryInstance?.gynSurg == 'Hysterectomy'}" />&nbsp;<label for="gynSurg1">Hysterectomy</label><br/>
                        <g:radio name="gynSurg" id="gynSurg2" value="Unilateral oophorectomy" checked="${bpvClinicalDataEntryInstance?.gynSurg == 'Unilateral oophorectomy'}" />&nbsp;<label for="gynSurg2">Unilateral oophorectomy</label><br/>
                        <g:radio name="gynSurg" id="gynSurg3" value="Neither hysterectomy nor oophorectomy" checked="${bpvClinicalDataEntryInstance?.gynSurg == 'Neither hysterectomy nor oophorectomy'}" />&nbsp;<label for="gynSurg3">Neither hysterectomy nor oophorectomy</label><br/>
                        <g:radio name="gynSurg" id="gynSurg4" value="Unknown" checked="${bpvClinicalDataEntryInstance?.gynSurg == 'Unknown'}" />&nbsp;<label for="gynSurg4">Unknown</label>
                      </div>  
                    </td>
                </tr>
                
                <tr><td colspan="2" class="formheader">Hormonal Birth Control Use</td></tr>
                <tr class="prop clearborder">
                    <td valign="top" class="name">
                        <label for="hormBirthControl">${labelNumber++}. Has the Participant ever used hormonally based birth control?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'hormBirthControl', 'errors')}" >
                      <div>
                        <g:radio name="hormBirthControl" id="hbcStat1" value="Current user" checked="${bpvClinicalDataEntryInstance?.hormBirthControl == 'Current user'}" />&nbsp;<label for="hbcStat1">Current user</label><br/>
                        <g:radio name="hormBirthControl" id="hbcStat2" value="Former user" checked="${bpvClinicalDataEntryInstance?.hormBirthControl == 'Former user'}" />&nbsp;<label for="hbcStat2">Former user</label><br/>
                        <g:radio name="hormBirthControl" id="hbcStat3" value="Never used" checked="${bpvClinicalDataEntryInstance?.hormBirthControl == 'Never used'}" />&nbsp;<label for="hbcStat3">Never used</label><br/>
                        <g:radio name="hormBirthControl" id="hbcStat4" value="Unknown" checked="${bpvClinicalDataEntryInstance?.hormBirthControl == 'Unknown'}" />&nbsp;<label for="hbcStat4">Unknown</label>
                      </div>  
                    </td>
                </tr>

                <tr class="prop clearborder subentry" id="HBCTable" style="display:${bpvClinicalDataEntryInstance?.hormBirthControl == 'Current user' || bpvClinicalDataEntryInstance?.hormBirthControl == 'Former user' ?'display':'none'}" >
                  <td colspan="2">
                    <g:render template="HBCTabContent"/>
                  </td>
                </tr>

            <tr id="hbcSaveRow" class="subentry clearborder" style="display:${bpvClinicalDataEntryInstance?.hormBirthControl == 'Current user' || bpvClinicalDataEntryInstance?.hormBirthControl == 'Former user' ?'display':'none'}" >
              <td  colspan="2"><g:actionSubmit id="hbcSaveBtn" style="display:none" action="saveHormonalBC" value="Save" /><g:actionSubmit style="display:none" id="hbcCancelBtn" value="Cancel" /><g:actionSubmit class="Btn" id="hbcAddBtn" value="Add" /></td>
            </tr>                
                <tr class="prop" id="othFormHBCDescRow" style="display:${bpvClinicalDataEntryInstance?.hormBirthControl == 'Current user' || bpvClinicalDataEntryInstance?.hormBirthControl == 'Former user' ?'display':'none'}" >
                    <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'othHorBC', 'errors')}">
                        <label for="othHorBC" class="subentry">General comment:</label><br />
                        <g:textArea name="othHorBC" class="textwide" cols="40" rows="5" maxlength="4000" value="${bpvClinicalDataEntryInstance?.othHorBC}" />
                    </td>
                </tr>
                

                <tr><td colspan="2" class="formheader">Hormone Replacement Therapy Use</td></tr>
                <tr class="prop clearborder">
                    <td valign="top" class="name">
                        <label for="usedHorReplaceTher">${labelNumber++}. Has the Participant ever used Hormone replacement therapy?</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'usedHorReplaceTher', 'errors')}">
                        <g:yesNoUnknownRadioPicker checked="${(bpvClinicalDataEntryInstance?.usedHorReplaceTher)}"  name="usedHorReplaceTher" />
                    </td>
                </tr>

                <tr class="prop clearborder subentry" id="HRTTable" style="display:${bpvClinicalDataEntryInstance?.usedHorReplaceTher == 'Yes'?'display':'none'}" >
                  <td colspan="2">
                    <g:render template="HRTTabContent"/>
                </td>
                </tr>

                
            <tr id="hrtSaveRow" class="subentry clearborder" style="display:${bpvClinicalDataEntryInstance?.usedHorReplaceTher == 'Yes'?'display':'none'}" >
              <td colspan="2"><g:actionSubmit id="hrtSaveBtn" style="display:none" action="saveHormonalRT" value="Save" /><g:actionSubmit style="display:none" id="hrtCancelBtn" value="Cancel" /><g:actionSubmit class="Btn" id="hrtAddBtn" value="Add" /></td>
            </tr>  
            
                <tr class="prop" id="othFormHRTDescRow" style="display:${bpvClinicalDataEntryInstance?.usedHorReplaceTher == 'Yes'?'display':'none'}" >
                    <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'othHorRT', 'errors')}">
                        <label for="othHorRT" class="subentry">General Comment:</label><br />
                        <g:textArea class="textwide" name="othHorRT" cols="40" rows="5" maxlength="4000" value="${bpvClinicalDataEntryInstance?.othHorRT}" />
                    </td>
                </tr>
                
                <tr class="prop">
                    <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'menoStatus', 'errors')}">
                      <label for="menoStatus">${labelNumber++}. Indicate Participant's menopausal status:</label>
                      <div class="subentry value menoStatus">
                        <g:radio name="menoStatus" id="ms1" value="Less than 6 months since LMP AND no prior bilateral oophorectomy AND not on estrogen replacement" checked="${bpvClinicalDataEntryInstance?.menoStatus == 'Less than 6 months since LMP AND no prior bilateral oophorectomy AND not on estrogen replacement'}" />&nbsp;<label for="ms1">Premenopausal: Less than 6 months since LMP AND no prior bilateral oophorectomy AND not on estrogen replacement</label><br>
                        <g:radio name="menoStatus" id="ms2" value="6-12 months since last menstrual period" checked="${bpvClinicalDataEntryInstance?.menoStatus == '6-12 months since last menstrual period'}" />&nbsp;<label for="ms2">Perimenopausal: 6-12 months since last menstrual period</label><br>
                        <g:radio name="menoStatus" id="ms3" value="prior bilateral oophorectomy OR more than 12 months since LMP with no prior hysterectomy" checked="${bpvClinicalDataEntryInstance?.menoStatus == 'prior bilateral oophorectomy OR more than 12 months since LMP with no prior hysterectomy'}" />&nbsp;<label for="ms3">Postmenopausal: prior bilateral oophorectomy OR more than 12 months since LMP with no prior hysterectomy</label><br>
                        <g:radio name="menoStatus" id="ms4" value="neither pre- nor post-menopausal" checked="${bpvClinicalDataEntryInstance?.menoStatus == 'neither pre- nor post-menopausal'}" />&nbsp;<label for="ms4">Indeterminate: neither pre- nor post-menopausal</label>
                      </div>  
                    </td>
                </tr>
            </g:if>

                <tr><td colspan="2" class="formheader">Alcohol History</td></tr>
                <tr class="prop">
                    <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'alcoholConsum', 'errors')}">
                      <label for="alcoholConsum">${labelNumber++}. Alcohol consumption:</label>
                      <div class="subentry value alcoholConsum">
                        <g:radio name="alcoholConsum" id="consum5" value="Lifelong non-drinker" checked="${bpvClinicalDataEntryInstance?.alcoholConsum == 'Lifelong non-drinker'}" />&nbsp;<label for="consum5">Lifelong non-drinker</label><br>
                        <g:radio name="alcoholConsum" id="consum4" value="Alcohol consumption equal to or less than 2 drinks per day for men and 1 drink or less  per day for women" checked="${bpvClinicalDataEntryInstance?.alcoholConsum == 'Alcohol consumption equal to or less than 2 drinks per day for men and 1 drink or less  per day for women'}" />&nbsp;<label for="consum4">Alcohol consumption equal to or less than 2 drinks per day for men and 1 drink or less  per day for women</label><br>
                        <g:radio name="alcoholConsum" id="consum3" value="Alcohol consumption more than 2 drinks per day for men and more than 1 drink per day for women" checked="${bpvClinicalDataEntryInstance?.alcoholConsum == 'Alcohol consumption more than 2 drinks per day for men and more than 1 drink per day for women'}" />&nbsp;<label for="consum3">Alcohol consumption more than 2 drinks per day for men and more than 1 drink per day for women</label><br>
                        <g:radio name="alcoholConsum" id="consum2" value="Consumed alcohol in the past, but currently a non-drinker" checked="${bpvClinicalDataEntryInstance?.alcoholConsum == 'Consumed alcohol in the past, but currently a non-drinker'}" />&nbsp;<label for="consum2">Consumed alcohol in the past, but currently a non-drinker</label><br>
                        <g:radio name="alcoholConsum" id="consum1" value="Alcohol consumption history not available" checked="${bpvClinicalDataEntryInstance?.alcoholConsum == 'Alcohol consumption history not available'}" />&nbsp;<label for="consum1">Alcohol consumption history not available</label>
                      </div>  
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="numYrsAlcCon">${labelNumber++}. Number of years Participant has consumed more than 2 drinks per day for men and more than 1 drink per day for women:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'numYrsAlcCon', 'errors')}">
                        <g:textField name="numYrsAlcCon" size="2" maxlength="2" value="${bpvClinicalDataEntryInstance?.numYrsAlcCon}" onkeyup="isNumericValidation(this)"/>
                    </td>
                </tr>



          <tr><td colspan="2" class="formheader">Tobacco smoking history</td></tr>
          <tr class="prop">
              <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'tobaccoSmHist', 'errors')}">
                <label for="tobaccoSmHist">${labelNumber++}. Tobacco smoking history:</label>
                <div class="subentry value tobaccoSmHist">
                  <g:radio name="tobaccoSmHist" id="sm5" value="Less than 100 cigarettes smoked in lifetime" checked="${bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Less than 100 cigarettes smoked in lifetime'}" />&nbsp;<label for="sm5">Lifelong non-smoker: Less than 100 cigarettes smoked in lifetime</label><br>
                  <g:radio name="tobaccoSmHist" id="sm4" value="Includes daily and non-daily smokers" checked="${bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Includes daily and non-daily smokers'}" />&nbsp;<label for="sm4">Current smoker: Includes daily and non-daily smokers</label><br>
                  <g:radio name="tobaccoSmHist" id="sm3" value="Current reformed smoker for more than 15 years" checked="${bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for more than 15 years'}" />&nbsp;<label for="sm3">Current reformed smoker for more than 15 years</label><br>
                  <g:radio name="tobaccoSmHist" id="sm2" value="Current reformed smoker for less than 15 years" checked="${bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for less than 15 years'}" />&nbsp;<label for="sm2">Current reformed smoker for less than 15 years</label><br>
                  <g:radio name="tobaccoSmHist" id="sm1" value="Smoking history not available" checked="${bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Smoking history not available'}" />&nbsp;<label for="sm1">Smoking history not available</label>
                </div> 
              </td>
          </tr>
          <tr class="prop subentry" id="smkStartRow" style="display:${(bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for more than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for less than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Includes daily and non-daily smokers')?'display':'none'}" >
              <td class="name">
                  <label for="smokeAgeStart">Enter age at which the Participant started smoking:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'smokeAgeStart', 'errors')}">
                  <g:textField name="smokeAgeStart" size="2" maxlength="3" value="${bpvClinicalDataEntryInstance?.smokeAgeStart}"  />
               </td>
          </tr>

          <tr class="prop subentry" id="smkStopRow" style="display:${(bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for more than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for less than 15 years' )?'display':'none'}" >
              <td valign="top" class="name">
                  <label for="smokeAgeStop">The age at which the Participant stopped smoking:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'smokeAgeStop', 'errors')}">
                  <g:textField name="smokeAgeStop" size="2" maxlength="3" value="${bpvClinicalDataEntryInstance?.smokeAgeStop}" />
            <%
                def ageAsOfConsentedDate = bpvClinicalDataEntryInstance?.caseRecord?.candidateRecord?.bpvConsentEnrollment?.age
                
               if (ageAsOfConsentedDate != null && ageAsOfConsentedDate.indexOf(" ")!=-1) {
                  ageAsOfConsentedDate = ageAsOfConsentedDate.substring(0,ageAsOfConsentedDate.indexOf(" "))
                }
                
            %>                  
                  <g:hiddenField name="ageAsOfConsentedDate" value="${ageAsOfConsentedDate}" />
              </td>
          </tr>

          <tr class="prop subentry" id="cigsPDRow" style="display:${(bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for more than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for less than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Includes daily and non-daily smokers')?'display':'none'}" >
              <td valign="top" class="name">
                  <label for="cigsPerDay">On the days that the Participant smoked, how many cigarettes <br>did s/he usually smoke?</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'cigsPerDay', 'errors')}">
                  <g:textField name="cigsPerDay" size="2" maxlength="4" value="${bpvClinicalDataEntryInstance?.cigsPerDay}" />
              </td>
          </tr>

          <tr class="prop subentry" id="numPkYrsRow" style="display:${(bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for more than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Current reformed smoker for less than 15 years' || bpvClinicalDataEntryInstance?.tobaccoSmHist == 'Includes daily and non-daily smokers')?'display':'none'}" >
            <td valign="top" class="name"> 
                  <label for="numPackYearsSm">Number of pack years smoked:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'numPackYearsSm', 'errors')}">
                <label id="numPackYearsSmLbl">${bpvClinicalDataEntryInstance?.numPackYearsSm}</label>
                  <g:hiddenField name="numPackYearsSm" size="3" maxlength="3" value="${bpvClinicalDataEntryInstance?.numPackYearsSm}" readonly="true" />
              </td>
          </tr>

          <tr class="prop">
              <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'secHandSmHist1', 'errors')}">
                <label>${labelNumber++}. Was the Participant exposed to secondhand smoke?</label>
                <span id="bpvclinicaldataentry.2ndhandsmokeexpose" class="vocab-tooltip"></span>
                <div class="subentry value secHandSmHist">
                  <g:radio id ="secHandSmHist1" name="secHandSmHist1" value="No or minimal exposure to secondhand smoke" checked="${bpvClinicalDataEntryInstance?.secHandSmHist1 == 'No or minimal exposure to secondhand smoke'}"/>&nbsp;<label for="secHandSmHist1">No or minimal exposure to secondhand smoke</label><br>
                  <g:radio id ="secHandSmHist2" name="secHandSmHist1" value="Yes" checked="${bpvClinicalDataEntryInstance?.secHandSmHist1 == 'Yes'}"/>&nbsp;<label for="secHandSmHist2">Yes</label><br>
                  <div id="secHandSmokeHistYesDiv" style="display:${bpvClinicalDataEntryInstance?.secHandSmHist1 == 'Yes'?'display':'none'}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox id ="secHandSmHist3" name="secHandSmHist2" value="Exposure to secondhand smoke in household during Participant’s childhood" checked="${bpvClinicalDataEntryInstance?.secHandSmHist2 == 'Exposure to secondhand smoke in household during Participant’s childhood'}" />&nbsp;<label for="secHandSmHist3">Exposure to secondhand smoke in household during<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Participant’s childhood</label><br>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox id ="secHandSmHist4" name="secHandSmHist3" value="Exposure to secondhand smoke in Participant’s current household" checked="${bpvClinicalDataEntryInstance?.secHandSmHist3 == 'Exposure to secondhand smoke in Participant’s current household'}" />&nbsp;<label for="secHandSmHist4">Exposure to secondhand smoke in Participant’s current household</label><br></div>
                  <g:radio id ="secHandSmHist5" name="secHandSmHist1" value="Exposure to secondhand smoke history not available" checked="${bpvClinicalDataEntryInstance?.secHandSmHist1 == 'Exposure to secondhand smoke history not available'}" />&nbsp;<label for="secHandSmHist5">Exposure to secondhand smoke history not available</label>
                </div>
              </td>
          </tr>

          <g:if test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString() == 'Ovary'}">
              <tr><td colspan="2" class="formheader">Clinical FIGO Stage</td></tr>
              <tr class="prop">
                  <td valign="top" class="name">
                      <label for="clinicalFIGOStg">${labelNumber++}. Clinical FIGO stage:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'clinicalFIGOStg', 'errors')}">
                      <g:select name="clinicalFIGOStg" from="${['Stage IA','Stage IA1','Stage IA2','Stage IB','Stage IB1','Stage IB2','Stage IC','Stage IIA','Stage IIA1','Stage IIA2', 'Stage IIB','Stage IIC','Stage IIIA','Stage IIIB','Stage IIIC','Stage IVA','Stage IVB','Not Available']}" value="${bpvClinicalDataEntryInstance?.clinicalFIGOStg}" noSelection="['': '']" />
                  </td>
              </tr>
          </g:if>
          <g:else>
            <tr><td colspan="2" class="formheader">Clinical tumor stage group (AJCC 7th Edition)</td></tr>
              <tr class="prop">
                  <td valign="top" class="name">
                      <label for="clinicalTumStgGrp">${labelNumber++}. Clinical tumor stage group (AJCC 7th Edition):</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'clinicalTumStgGrp', 'errors')}">
                      <g:if test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString() == 'Kidney'}">
                          <g:select name="clinicalTumStgGrp" from="${['Stage I','Stage II','Stage III','Stage IV','Not Available']}" value="${bpvClinicalDataEntryInstance?.clinicalTumStgGrp}" noSelection="['': '']" />
                      </g:if>
                      <g:elseif test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString() == 'Colon'}">
                          <g:select name="clinicalTumStgGrp" from="${['Occult carcinoma','Stage 0','Stage I','Stage IIA', 'Stage IIB','Stage IIC','Stage IIIA','Stage IIIB','Stage IIIC','Stage IVA','Stage IVB','Not Available']}" value="${bpvClinicalDataEntryInstance?.clinicalTumStgGrp}" noSelection="['': '']" />
                      </g:elseif>
                      <g:elseif test="${bpvClinicalDataEntryInstance.caseRecord.primaryTissueType.toString() == 'Lung'}">
                          <g:select name="clinicalTumStgGrp" from="${['Occult carcinoma','Stage 0','Stage IA','Stage IB', 'Stage IIA','Stage IIB','Stage IIIA','Stage IIIB','Stage IV','Not Available']}" value="${bpvClinicalDataEntryInstance?.clinicalTumStgGrp}" noSelection="['': '']" />
                      </g:elseif>
                  </td>
              </tr>
          </g:else>

          <tr><td colspan="2" class="formheader">Record Karnofsky Score OR Eastern Cancer Oncology (ECOG) Score</td></tr>
          <tr class="prop clearborder">
              <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'perfStatusScale', 'errors')}">
                <label for="perfStatusScale">${labelNumber++}. Performance Status Scale recorded:</label>
                <div class="subentry value perfStatusScale">
                  <g:radio name="perfStatusScale" id="${(versionValue >= 6.0) ? 'karnofsky60' : 'karnofsky'}" value="Karnofsky Score" checked="${bpvClinicalDataEntryInstance?.perfStatusScale == 'Karnofsky Score'}" />&nbsp;<label for="karnofsky">Karnofsky Score</label><br>
                  <g:radio name="perfStatusScale" id="${(versionValue >= 6.0) ? 'ecog60' : 'ecog'}" value="Eastern Cancer Oncology Group" checked="${bpvClinicalDataEntryInstance?.perfStatusScale == 'Eastern Cancer Oncology Group'}" />&nbsp;<label for="ecog">Eastern Cancer Oncology Group</label><br>
                  <g:radio name="perfStatusScale" id="${(versionValue >= 6.0) ? 'notRecorded60' : 'notRecorded'}" value="Not Recorded" checked="${bpvClinicalDataEntryInstance?.perfStatusScale == 'Not Recorded'}"/>&nbsp;<label for="notRecorded">Not recorded</label><br>
                </div>   
              </td>
          </tr>

          <tr class="prop" id="karnofskyRow" style="display:${bpvClinicalDataEntryInstance?.perfStatusScale == 'Karnofsky Score'?'display':'none'}" >
              <td class="name subentry ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'karnofskyScore', 'errors')}" colspan="2">
                <label for="karnofskyScore" class="subentry">Karnofsky Score:</label>
                <div class="subentry value karnofskyScore">
                  <g:radio name="karnofskyScore" id="asym100" value="asymptomatic" checked="${bpvClinicalDataEntryInstance?.karnofskyScore == 'asymptomatic'}" />&nbsp;<label for="asym100">100: asymptomatic</label><br>
                  <g:radio name="karnofskyScore" id="symp8090" value="symptomatic but fully ambulatory" checked="${bpvClinicalDataEntryInstance?.karnofskyScore == 'symptomatic but fully ambulatory'}" />&nbsp;<label for="symp8090">80-90: symptomatic but fully ambulatory</label><br>
                  <g:radio name="karnofskyScore" id="symp6070" value="symptomatic but in bed less than 50% of the day" checked="${bpvClinicalDataEntryInstance?.karnofskyScore == 'symptomatic but in bed less than 50% of the day'}" />&nbsp;<label for="symp6070">60-70: symptomatic but in bed less than 50% of the day</label><br>
                 <g:radio name="karnofskyScore" id="symp4050" value="symptomatic, in bed more than 50% of the day, but not bed ridden" checked="${bpvClinicalDataEntryInstance?.karnofskyScore == 'symptomatic, in bed more than 50% of the day, but not bed ridden'}" />&nbsp;<label for="symp4050">40-50: symptomatic, in bed more than 50% of the day, but not bed ridden</label><br>
                  <g:radio name="karnofskyScore" id="bedridden2030" value="bed ridden" checked="${bpvClinicalDataEntryInstance?.karnofskyScore == 'bed ridden'}" />&nbsp;<label for="bedridden2030">20-30: bed ridden</label>
                </div>   
              </td>
          </tr>

          <tr class="prop" id="ecogRow" style="display:${bpvClinicalDataEntryInstance?.perfStatusScale == 'Eastern Cancer Oncology Group'?'display':'none'}" >
              <td class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'ecogStatus', 'errors')}" colspan="2">
                <label for="ecogStatus" class="subentry">ECOG Functional Performance Status</label>
                <div class="subentry value ecogStatus">
                  <g:radio name="ecogStatus" id="asym" value="asymptomatic" checked="${bpvClinicalDataEntryInstance?.ecogStatus == 'asymptomatic'}" />&nbsp;<label for="asym">0: asymptomatic</label><br>
                  <g:radio name="ecogStatus" id="symp1" value="symptomatic but fully ambulatory" checked="${bpvClinicalDataEntryInstance?.ecogStatus == 'symptomatic but fully ambulatory'}" />&nbsp;<label for="symp1">1: symptomatic but fully ambulatory</label><br>
                  <g:radio name="ecogStatus" id="symp2" value="symptomatic but in bed less than 50% of the day" checked="${bpvClinicalDataEntryInstance?.ecogStatus == 'symptomatic but in bed less than 50% of the day'}" />&nbsp;<label for="symp2">2: symptomatic but in bed less than 50% of the day</label><br>
                  <g:radio name="ecogStatus" id="symp3" value="symptomatic, in bed more than 50% of the day, but not bed ridden" checked="${bpvClinicalDataEntryInstance?.ecogStatus == 'symptomatic, in bed more than 50% of the day, but not bed ridden'}" />&nbsp;<label for="symp3">3: symptomatic, in bed more than 50% of the day, but not bed ridden</label><br>
                  <g:radio name="ecogStatus" id="bedridden4" value="bed ridden" checked="${bpvClinicalDataEntryInstance?.ecogStatus == 'bed ridden'}" />&nbsp;<label for="bedridden4">4: bed ridden</label>
                </div>  
              </td>
          </tr>

          <tr class="prop ${(versionValue >= 6.0) ? '' :'clearborder'}" id="timingOfScoreTR" style="display:${ (versionValue >= 6.0) ? ((!bpvClinicalDataEntryInstance?.perfStatusScale)||(bpvClinicalDataEntryInstance?.perfStatusScale.trim().equals(''))||(bpvClinicalDataEntryInstance?.perfStatusScale == 'Not Recorded') ? 'none' : 'display' ) :'display'}" >
              <td class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'timingOfScore', 'errors')}" colspan="2">
                <label for="timingOfScore">${labelNumber++}. Timing of score:</label>
                <div class="subentry value timingOfScore">
                  <g:radio name="timingOfScore" id="Preoperative" value="Preoperative" checked="${bpvClinicalDataEntryInstance?.timingOfScore == 'Preoperative'}" />&nbsp;<label for="Preoperative">Preoperative</label><br>
                  <g:radio name="timingOfScore" id="Preadjuvant" value="Pre-adjuvant therapy" checked="${bpvClinicalDataEntryInstance?.timingOfScore == 'Pre-adjuvant therapy'}" />&nbsp;<label for="Preadjuvant">Pre-adjuvant therapy</label><br>
                  <g:radio name="timingOfScore" id="Postadjuvant" value="Post adjuvant therapy" checked="${bpvClinicalDataEntryInstance?.timingOfScore == 'Post adjuvant therapy'}" />&nbsp;<label for="Postadjuvant">Post adjuvant therapy</label><br>
                  <g:radio name="timingOfScore" id="tosUnknown" value="Unknown" checked="${bpvClinicalDataEntryInstance?.timingOfScore == 'Unknown'}" />&nbsp;<label for="tosUnknown">Unknown</label><br>
                  <g:radio name="timingOfScore" id="othTimScore" value="Other, Specify" checked="${bpvClinicalDataEntryInstance?.timingOfScore == 'Other, Specify'}" />&nbsp;<label for="othTimScore">Other, Specify</label>
                  </div>  
              </td>
          </tr>

            <tr class="subentry prop" id="timingOfScoreOsRow" style="display:${bpvClinicalDataEntryInstance?.timingOfScore == 'Other, Specify'?((versionValue >= 6.0)&&((!bpvClinicalDataEntryInstance?.perfStatusScale)||(bpvClinicalDataEntryInstance?.perfStatusScale == 'Not Recorded')||(bpvClinicalDataEntryInstance?.perfStatusScale.trim().equals('')))?'none':'display'):'none'}">
                  <td colspan="2" class="value ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'timingOfScoreOs', 'errors')}">
                      <g:textField name="timingOfScoreOs" id="timingOfScoreOs" maxlength="255" value="${bpvClinicalDataEntryInstance?.timingOfScoreOs}" />
                  </td>
              </tr>               

                <tr class="prop">
                    <td colspan="2" class="name ${hasErrors(bean: bpvClinicalDataEntryInstance, field: 'comments', 'errors')}">
                        <label for="comments">${labelNumber++}. Comments:</label><br />
                        <g:textArea class="textwide" name="comments" cols="40" rows="5" maxlength="4000" value="${bpvClinicalDataEntryInstance?.comments}" />
                    </td>
                </tr>
              
      </tbody>
  </table>
</div>
