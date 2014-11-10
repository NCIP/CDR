<%@ page import="nci.obbr.cahub.staticmembers.SOP" %>

<script type="text/javascript">
    $(document).ready(function() {
        if($("#bloodSamplesCollected_no").attr("checked") == "checked" && $("#bpvtissuereceipt").hasClass("islung") != true){
          hideRestOfForm();
        } else if($("#bloodSamplesCollected_no").attr("checked") == "checked" && $("#bpvtissuereceipt").hasClass("islung") == true){
          showRestOfForm();
        }
        
        $("#bloodSamplesCollected_yes").click(function() {
          $("#bloodNotBankedReasonRow").hide()
          document.getElementById("otherReason").checked = false
          document.getElementById("bloodNotDrawn").checked = false
          document.getElementById("bloodNotReceived").checked = false
          document.getElementById("aliquotsNotBanked").checked = false
          document.getElementById("hemolyzedUnusableBlood").checked = false
          $("#specifiedOtherReason").val('')
          $("#specifiedOtherReasonRow").hide()
          showRestOfForm();
        });
        $("#bloodSamplesCollected_no").click(function() {
              $("#bloodNotBankedReasonRow").show()

              if($("#bpvtissuereceipt").hasClass("islung") != true){
                hideRestOfForm();
              } else {
                showRestOfForm();
              }
        });

        $("#otherReason").click(function() {
            if(this.checked) {
                $("#specifiedOtherReasonRow").show()
            } else {
                $("#specifiedOtherReason").val('')
                $("#specifiedOtherReasonRow").hide()
            }
        });

        $("#grossAppearance").change(function() {
            if($("#grossAppearance").val() == 'Other-specify') {
                $("#otherGrossAppearanceRow").show()
            } else {
                $("#otherGrossAppearance").val('')
                $("#otherGrossAppearanceRow").hide()
            }
        });

        $("#collectionProcedure").change(function() {
            if($("#collectionProcedure").val() == 'Other-specify') {
                $("#otherCollectionProcedureRow").show()
            } else {
                $("#otherCollectionProcedure").val('')
                $("#otherCollectionProcedureRow").hide()
            }
        });

        $("#fixativeType").change(function() {
            if($("#fixativeType").val() == 'Other-specify') {
                $("#otherFixativeTypeRow").show()
            } else {
                $("#otherFixativeType").val('');
                $("#otherFixativeTypeRow").hide()
            }
        });

        $("#fixativeProductType").change(function() {
            if($("#fixativeProductType").val() == 'Other-specify') {
                $("#otherFixativeProductTypeRow").show()
            } else {
                $("#otherFixativeProductType").val('')
                $("#otherFixativeProductTypeRow").hide()
            }
        });
});


function showRestOfForm(){


            $("#sopGuidanceSectionTitle").show()
            $("#tissueBrnSopRow").show()
            $("#dateTimeTissueReceivedRow").show()
            $("#tissueRecipientRow").show()
            $("#tissueReceiptCommentRow").show()
            $("#moduleOneSectionTitle").show()
            $("#barcodeIdRow").show()
            $("#experimentalKeyIdRow").show()
            $("#parentTissueDissectedByRow").show()
            $("#parentTissueDissectBeganRow").show()
            $("#parentTissueDissectEndedRow").show()
            $("#grossAppearanceRow").show()
            $("#tumorSourceRow").show()
            $("#collectionProcedureRow").show()
            $("#fixativeSectionTitle").show()
            $("#fixativeTypeRow").show()
            $("#fixativeFormulaRow").show()
            $("#fixativePHRow").show()
            $("#fixativeManufacturerRow").show()
            $("#fixativeLotNumRow").show()
            $("#dateFixativeLotNumExpiredRow").show()
            $("#fixativeProductNumRow").show()
            $("#fixativeProductTypeRow").show()
            $("#formalinFreshRecycledRow").show()
            $("#parentTissueSopCommentRow").show()
            $("#dateTimeTissueReceivedRow").show()
            $("#dateTimeTissueReceivedRow").show()
}
function hideRestOfForm(){
                $("#sopGuidanceSectionTitle").hide()
                $("#tissueBrnSop").val('')
                $("#tissueBrnSopRow").hide()

                $("#dateTimeTissueReceived").val('')
                $("#dateTimeTissueReceivedRow").hide()

                $("#tissueRecipient").val('')
                $("#tissueRecipientRow").hide()

                $("#tissueReceiptComment").val('')
                $("#tissueReceiptCommentRow").hide()

                $("#moduleOneSectionTitle").hide()
                $("#barcodeId").val('')
                $("#barcodeIdRow").hide()

                $("#experimentalKeyId").val('')
                $("#experimentalKeyIdRow").hide()

                $("#parentTissueDissectedBy").val('')
                $("#parentTissueDissectedByRow").hide()

                $("#parentTissueDissectBegan").val('')
                $("#parentTissueDissectBeganRow").hide()

                $("#parentTissueDissectEnded").val('')
                $("#parentTissueDissectEndedRow").hide()

                $("#grossAppearance").val('')
                $("#grossAppearanceRow").hide()

                $("#otherGrossAppearance").val('')
                $("#otherGrossAppearanceRow").hide()

                $("#tumorSource").val('')
                $("#tumorSourceRow").hide()

                $("#collectionProcedure").val('')
                $("#collectionProcedureRow").hide()

                $("#otherCollectionProcedure").val('')
                $("#otherCollectionProcedureRow").hide()

                $("#fixativeSectionTitle").hide()
                $("#fixativeType").val('')
                $("#fixativeTypeRow").hide()

                $("#otherFixativeType").val('')
                $("#otherFixativeTypeRow").hide()

                $("#fixativeFormula").val('')
                $("#fixativeFormulaRow").hide()

                $("#fixativePH").val('')
                $("#fixativePHRow").hide()

                $("#fixativeManufacturer").val('')
                $("#fixativeManufacturerRow").hide()

                $("#fixativeLotNum").val('')
                $("#fixativeLotNumRow").hide()

                $("#dateFixativeLotNumExpired").val('')
                $("#dateFixativeLotNumExpiredRow").hide()

                $("#fixativeProductNum").val('')
                $("#fixativeProductNumRow").hide()

                $("#fixativeProductType").val('')
                $("#fixativeProductTypeRow").hide()

                $("#otherFixativeProductType").val('')
                $("#otherFixativeProductTypeRow").hide()

                $("#formalinFreshRecycled").val('')
                $("#formalinFreshRecycledRow").hide()

                $("#parentTissueSopComment").val('')
                $("#parentTissueSopCommentRow").hide()

                $("#dateTimeTissueReceived").val('')
                $("#dateTimeTissueReceivedRow").hide()

                $("#dateTimeTissueReceived").val('')
              $("#dateTimeTissueReceivedRow").hide()
}
</script>

<g:render template="/formMetadata/timeConstraint" bean="${bpvTissueReceiptDissectionInstance.formMetadata}" var="formMetadata"/>
<g:render template="/caseRecord/caseDetails" bean="${bpvTissueReceiptDissectionInstance.caseRecord}" var="caseRecord"/>
<g:set var="labelNumber" value="${2}"/>

<div class="list">
    <table id="bpvtissuereceipt" class="tdwrap tdtop ${bpvTissueReceiptDissectionInstance.caseRecord.primaryTissueType?.code.toLowerCase() == "lung" && version53 == true ? "islung":""}">
        <tbody>
            <tr><td colspan="2" class="formheader">Specimen Receipt Overview</td></tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="bloodSamplesCollected">1. Confirm that required pre-operative/preanesthesia blood samples specified in BPV Blood Collection and Processing SOP were collected from the participant and were successfully banked in the Tissue Bank:</label>
                    <br /><br />
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'bloodSamplesCollected', 'errors')}">
                    <g:bpvYesNoRadioPicker checked="${(bpvTissueReceiptDissectionInstance?.bloodSamplesCollected)}" name="bloodSamplesCollected"/>
                </td>
            </tr>
            <tr class="prop" id="bloodNotBankedReasonRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'No'?'display':'none'}">
                <td valign="top" class="name">
                  <label for="bloodNotBankedReason">1a. Reason(s) pre-operative blood was not banked:</label>
                </td>
                <td nowrap="true" valign="top" class="value ${errorMap.get('bloodNotBankedReason')}">
                    <div>
                      <g:checkBox name="bloodNotReceived" value="${bpvTissueReceiptDissectionInstance?.bloodNotReceived}" />&nbsp;<label for="bloodNotReceived">Blood was not received in Tissue Bank</label><br/>
                      <g:checkBox name="bloodNotDrawn" value="${bpvTissueReceiptDissectionInstance?.bloodNotDrawn}" />&nbsp;<label for="bloodNotDrawn">Minimum amount of required pre-op blood<br>&nbsp;&nbsp;&nbsp;&nbsp;was not drawn</label><br/>
                      <g:checkBox name="aliquotsNotBanked" value="${bpvTissueReceiptDissectionInstance?.aliquotsNotBanked}" />&nbsp;<label for="aliquotsNotBanked">Minimum number of aliquots were not banked</label><br/>
                      <g:checkBox name="hemolyzedUnusableBlood" value="${bpvTissueReceiptDissectionInstance?.hemolyzedUnusableBlood}" />&nbsp;<label for="hemolyzedUnusableBlood">Blood was hemolyzed or not usable</label><br/>
                      <g:checkBox name="otherReason" value="${bpvTissueReceiptDissectionInstance?.otherReason}" />&nbsp;<label for="otherReason">Other-specify</label><br/><br/>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="specifiedOtherReasonRow" style="display:${bpvTissueReceiptDissectionInstance?.otherReason?'display':'none'}">
                <td valign="top" class="name">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label for="specifiedOtherReason">Specify other reason(s) that pre-operative blood was not banked:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'specifiedOtherReason', 'errors')}">
                    <g:textArea name="specifiedOtherReason" cols="40" rows="5" value="${bpvTissueReceiptDissectionInstance?.specifiedOtherReason}" />
                </td>
            </tr>
            <tr id="sopGuidanceSectionTitle" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td colspan="2" class="formheader">Receipt and dissection of surgical tissue are expected to conform to the BPV Surgical Tissue Collection and Processing SOP.<br />
                  Please specify any deviation(s) from the SOP in the Comments fields at the bottom of each section.
                </td>
            </tr>
            <tr class="prop" id="tissueBrnSopRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="tissueBrnSop">${labelNumber++}. BPV SOP governing receipt and dissection of surgical tissue in the Tissue Bank:</label>
                    <br /><br />
                </td>
                <td valign="top" class="value">
                    <g:if test="${params.action == 'create'}">
                        ${bpvTissueReceiptDissectionInstance?.formMetadata?.sops?.get(0)?.sopNumber}
                        ${bpvTissueReceiptDissectionInstance?.formMetadata?.sops?.get(0)?.sopName}  
                        ${bpvTissueReceiptDissectionInstance?.formMetadata?.sops?.get(0)?.activeSopVer}
                    </g:if>
                    <g:else>
                        ${bpvTissueReceiptDissectionInstance?.receiptDissectSOP?.sopNumber}
                        ${SOP.get(bpvTissueReceiptDissectionInstance?.receiptDissectSOP?.sopId)?.sopName}  
                        ${bpvTissueReceiptDissectionInstance?.receiptDissectSOP?.sopVersion}
                    </g:else>
                </td>
            </tr>
            <tr class="prop" id="dateTimeTissueReceivedRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="dateTimeTissueReceived">${labelNumber++}. Date and time tissue specimens were received in Tissue Bank from the Pathology Gross Room:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'dateTimeTissueReceived', 'errors')}">
		    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dateTimeTissueReceived" value="${bpvTissueReceiptDissectionInstance?.dateTimeTissueReceived}" />
                </td>
            </tr>
            <tr class="prop" id="tissueRecipientRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="tissueRecipient">${labelNumber++}. Tissue specimens were received in Tissue Bank from the Pathology Gross Room by:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'tissueRecipient', 'errors')}">
                    <g:textField name="tissueRecipient" value="${bpvTissueReceiptDissectionInstance?.tissueRecipient}" />
                </td>
            </tr>
            <tr class="prop" id="tissueReceiptCommentRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td colspan="2" class="name ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'tissueReceiptComment', 'errors')}">
                    <label for="tissueReceiptComment">${labelNumber++}. Comments/issues with tissue receipt or deviation(s) from SOP:</label><br />
                    <g:textArea class="textwide" name="tissueReceiptComment" cols="40" rows="5" value="${bpvTissueReceiptDissectionInstance?.tissueReceiptComment}" />
                </td>
            </tr>
            <tr id="moduleOneSectionTitle" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td colspan="2" class="formheader">Tumor Tissue Specimen Dissection Information. Details of 
                    processing of required tumor tissue are recorded on the BPV Tissue Processing Worksheet for this BPV Case ID and Experimental Key ID pair. Note any deviation(s)
                    from BPV Surgical Tissue Collection and <g:if test="${bpvTissueReceiptDissectionInstance?.formVersion >=2}">Preservation</g:if><g:else>Fixation</g:else> SOP in the Comments field at the bottom of this section.
                </td>
            </tr>
            <tr class="prop" id="barcodeIdRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="barcodeId">${labelNumber++}. Parent Tissue Specimen ID of required study tumor tissue specimen received in Tissue Bank from the Pathology Gross Room:</label>
                </td>
                <td valign="top" class="value">
                    <%-- Auto populated manaully or read.  This is to match the gross/module specimen created later on the FFPE worksheet main.--%>
                  
                  <g:if test="${bpvTissueReceiptDissectionInstance.caseRecord.bpvWorkSheet?.parentSampleId}">
                      ${bpvTissueReceiptDissectionInstance.caseRecord.bpvWorkSheet.parentSampleId}
                  </g:if>
                  <g:else>
                    ${bpvTissueReceiptDissectionInstance.caseRecord.caseId}-00
                  </g:else>                  

                </td>
            </tr>
            <tr class="prop" id="experimentalKeyIdRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="experimentalKeyId">${labelNumber++}. Experimental Key ID:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'experimentalKeyId', 'errors')}">
                    <g:textField name="experimentalKeyId" value="${bpvTissueReceiptDissectionInstance?.experimentalKeyId}" />
                </td>
            </tr>
            <tr class="prop" id="parentTissueDissectedByRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="parentTissueDissectedBy">${labelNumber++}. Dissection of parent tissue specimen was performed by:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'parentTissueDissectedBy', 'errors')}">
                    <g:textField name="parentTissueDissectedBy" value="${bpvTissueReceiptDissectionInstance?.parentTissueDissectedBy}" />
                </td>
            </tr>
            <tr class="prop" id="parentTissueDissectBeganRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="parentTissueDissectBegan">${labelNumber++}. Time dissection of parent tissue specimen began:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'parentTissueDissectBegan', 'errors')}">
                    <g:textField name="parentTissueDissectBegan" value="${bpvTissueReceiptDissectionInstance?.parentTissueDissectBegan}" class="timeEntry" />
                </td>
            </tr>
            <tr class="prop" id="parentTissueDissectEndedRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="parentTissueDissectEnded">${labelNumber++}. Time dissection of parent tissue specimen ended:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'parentTissueDissectEnded', 'errors')}">
                    <g:textField class="timeEntry" name="parentTissueDissectEnded" value="${bpvTissueReceiptDissectionInstance?.parentTissueDissectEnded }"/>
                </td>
            </tr>
            <tr class="prop" id="grossAppearanceRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="grossAppearance">${labelNumber++}. Gross appearance of parent tissue specimen as determined in Pathology Gross Room:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'grossAppearance', 'errors')}">
                    <g:select name="grossAppearance" from="${['Tumor','Other-specify']}" value="${bpvTissueReceiptDissectionInstance?.grossAppearance}" noSelection="['':'']" />
                </td>
            </tr>
            <tr class="prop" id="otherGrossAppearanceRow" style="display:${bpvTissueReceiptDissectionInstance?.grossAppearance == 'Other-specify'?'display':'none'}">
              <td valign="top" class="name">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label for="otherGrossAppearance">Other gross appearance of parent tissue specimen:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'otherGrossAppearance', 'errors')}">
                    <g:textField name="otherGrossAppearance" value="${bpvTissueReceiptDissectionInstance?.otherGrossAppearance}" />
                </td>
            </tr>
            <tr class="prop" id="tumorSourceRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="tumorSource">${labelNumber++}. Source of tumor tissue:</label>
                </td>
                <td valign="top" class="${warningMap?.get('tumorSource') ? 'warnings' : ''}">
                  <span class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'tumorSource', 'errors')}">
                    <g:textField name="tumorSource" value="${bpvTissueReceiptDissectionInstance?.tumorSource}"/>
                  </span>
                </td>
            </tr>
            <tr class="prop" id="collectionProcedureRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="collectionProcedure">${labelNumber++}. Tissue collection procedure:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'collectionProcedure', 'errors')}">
                    <g:select name="collectionProcedure" from="${['Surgical','Core-biopsy','Needle-biopsy','Other-specify']}" value="${bpvTissueReceiptDissectionInstance?.collectionProcedure}" noSelection="['':'']" />
                </td>
            </tr>
            <tr class="prop" id="otherCollectionProcedureRow" style="display:${bpvTissueReceiptDissectionInstance?.collectionProcedure == 'Other-specify'?'display':'none'}">
                <td valign="top" class="name">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label for="otherCollectionProcedure">Other tissue collection procedure:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'otherCollectionProcedure', 'errors')}">
                    <g:textField name="otherCollectionProcedure" value="${bpvTissueReceiptDissectionInstance?.otherCollectionProcedure}" />
                </td>
            </tr>
            <tr id="fixativeSectionTitle" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td colspan="2" class="formheader">Required Study Tissue - Fixative Information<g:if test="${bpvTissueReceiptDissectionInstance?.formVersion >=2}"> (for QC FFPE control and experimental blocks, as applicable)</g:if></td>
            </tr>
            <tr class="prop" id="fixativeTypeRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativeType">${labelNumber++}. Fixative type:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativeType', 'errors')}">
                    <g:select name="fixativeType" from="${['Buffered-formalin','Ethanol','PAXgene tissues','Other-specify']}" value="${bpvTissueReceiptDissectionInstance?.fixativeType}" noSelection="['': '']" />
                </td>
            </tr>
            <tr class="prop" id="otherFixativeTypeRow" style="display:${bpvTissueReceiptDissectionInstance?.fixativeType == 'Other-specify'?'display':'none'}">
                <td valign="top" class="name">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label for="otherFixativeType">Other fixative type:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'otherFixativeType', 'errors')}">
                    <g:textField name="otherFixativeType" value="${bpvTissueReceiptDissectionInstance?.otherFixativeType}" />
                </td>
            </tr>
            <tr class="prop" id="fixativeFormulaRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativeFormula">${labelNumber++}. Fixative formula (buffer):</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativeFormula', 'errors')}">
                    <g:textField name="fixativeFormula" value="${bpvTissueReceiptDissectionInstance?.fixativeFormula}" />
                </td>
            </tr>
            <tr class="prop" id="fixativePHRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativePH">${labelNumber++}. Fixative pH:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativePH', 'errors')}">
                    <g:textField name="fixativePH" value="${bpvTissueReceiptDissectionInstance?.fixativePH}" />
                </td>
            </tr>
            <tr class="prop" id="fixativeManufacturerRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativeManufacturer">${labelNumber++}. Manufacturer of fixative:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativeManufacturer', 'errors')}">
                    <g:textField name="fixativeManufacturer" value="${bpvTissueReceiptDissectionInstance?.fixativeManufacturer}" />
                </td>
            </tr>
            <tr class="prop" id="fixativeLotNumRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativeLotNum">${labelNumber++}. Fixative lot#:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativeLotNum', 'errors')}">
                    <g:textField name="fixativeLotNum" value="${bpvTissueReceiptDissectionInstance?.fixativeLotNum}" />
                </td>
            </tr>
            <tr class="prop" id="dateFixativeLotNumExpiredRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="dateFixativeLotNumExpired">${labelNumber++}. Fixative lot# expiration date:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'dateFixativeLotNumExpired', 'errors')}">
                    <g:jqDatePicker name="dateFixativeLotNumExpired" value="${bpvTissueReceiptDissectionInstance?.dateFixativeLotNumExpired}" />
                </td>
            </tr>
            <tr class="prop" id="fixativeProductNumRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativeProductNum">${labelNumber++}. Fixative product#:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativeProductNum', 'errors')}">
                    <g:textField name="fixativeProductNum" value="${bpvTissueReceiptDissectionInstance?.fixativeProductNum}" />
                </td>
            </tr>
            <tr class="prop" id="fixativeProductTypeRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="fixativeProductType">${labelNumber++}. Is fixative a commercial product or prepared in-house:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'fixativeProductType', 'errors')}">
                    <g:select name="fixativeProductType" from="${['Commercial','In-house','Other-specify']}" value="${bpvTissueReceiptDissectionInstance?.fixativeProductType}" noSelection="['': '']" />
                </td>
            </tr>
            <tr class="prop" id="otherFixativeProductTypeRow" style="display:${bpvTissueReceiptDissectionInstance?.fixativeProductType == 'Other-specify'?'display':'none'}">
                <td valign="top" class="name">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label for="otherFixativeProductType">Other fixative product type:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'otherFixativeProductType', 'errors')}">
                    <g:textField name="otherFixativeProductType" value="${bpvTissueReceiptDissectionInstance?.otherFixativeProductType}" />
                </td>
            </tr>
            <tr class="prop" id="formalinFreshRecycledRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td valign="top" class="name">
                    <label for="formalinFreshRecycled">${labelNumber++}. Is formalin fresh or recycled:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'formalinFreshRecycled', 'errors')}">
                    <g:select name="formalinFreshRecycled" from="${['Fresh','Recycled']}" value="${bpvTissueReceiptDissectionInstance?.formalinFreshRecycled}" noSelection="['': '']" />
                </td>
            </tr>
            <tr class="prop" id="parentTissueSopCommentRow" style="display:${bpvTissueReceiptDissectionInstance?.bloodSamplesCollected == 'Yes'?'display':'none'}">
                <td colspan="2" class="name ${hasErrors(bean: bpvTissueReceiptDissectionInstance, field: 'parentTissueSopComment', 'errors')}">
                    <label for="parentTissueSopComment">${labelNumber++}. Comments/issues with tissue receipt or deviation(s) from SOP:</label><br />
                    <g:textArea class="textwide" name="parentTissueSopComment" cols="40" rows="5" value="${bpvTissueReceiptDissectionInstance?.parentTissueSopComment}" />
                </td>
            </tr>
        </tbody>
    </table>
</div>
