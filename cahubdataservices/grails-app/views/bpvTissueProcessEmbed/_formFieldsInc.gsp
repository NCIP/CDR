<%@ page import="nci.obbr.cahub.staticmembers.SOP" %>

<script type="text/javascript">
    $(document).ready(function() {
        $("#tissProcessorMdl_LPRTP").click(function() {
            if(this.checked) {
                $("#othTissProcessorMdl").val('')
                $("#othTissProcessorMdlRow").hide()
            }
        });

        $("#tissProcessorMdl_Other").click(function() {
            if(this.checked) {
                $("#othTissProcessorMdlRow").show()
            }
        });

        $("#procMaintenance_Yes").click(function() {
            if(this.checked) {
                $("#othProcMaintenance").val('')
                $("#othProcMaintenanceRow").hide()
            }
        });

        $("#procMaintenance_No").click(function() {
            if(this.checked) {
                $("#othProcMaintenanceRow").show()
            }
        });

        $("#alcoholType_AE").click(function() {
            if(this.checked) {
                $("#othAlcoholType").val('')
                $("#othAlcoholTypeRow").hide()
            }
        });

        $("#alcoholType_Other").click(function() {
            if(this.checked) {
                $("#othAlcoholTypeRow").show()
            }
        });

        $("#clearingAgt_Xylene").click(function() {
            if(this.checked) {
                $("#othClearingAgt").val('')
                $("#othClearingAgtRow").hide()
            }
        })

        $("#clearingAgt_Other").click(function() {
            if(this.checked) {
                $("#othClearingAgtRow").show()
            }
        });

        $("#alcoholStgDur_Yes").click(function() {
            if(this.checked) {
                $("#othAlcoholStgDur").val('')
                $("#othAlcoholStgDurRow").hide()
            }
        })

        $("#alcoholStgDur_No").click(function() {
            if(this.checked) {
                $("#othAlcoholStgDurRow").show()
            }
        });

        $("#dehydrationProcDur_Yes").click(function() {
            if(this.checked) {
                $("#othDehydrationProcDur").val('')
                $("#othDehydrationProcDurRow").hide()
            }
        })

        $("#dehydrationProcDur_No").click(function() {
            if(this.checked) {
                $("#othDehydrationProcDurRow").show()
            }
        });

        $("#temperatureDehyd_Yes").click(function() {
            if(this.checked) {
                $("#othTemperatureDehyd").val('')
                $("#othTemperatureDehydRow").hide()
            }
        })

        $("#temperatureDehyd_No").click(function() {
            if(this.checked) {
                $("#othTemperatureDehydRow").show()
            }
        });

        $("#numStages_Yes").click(function() {
            if(this.checked) {
                $("#othNumStages").val('')
                $("#othNumStagesRow").hide()
            }
        })

        $("#numStages_No").click(function() {
            if(this.checked) {
                $("#othNumStagesRow").show()
            }
        });

        $("#clearingAgtDur_Yes").click(function() {
            if(this.checked) {
                $("#othClearingAgtDur").val('')
                $("#othClearingAgtDurRow").hide()
            }
        })

        $("#clearingAgtDur_No").click(function() {
            if(this.checked) {
                $("#othClearingAgtDurRow").show()
            }
        });

        $("#temperatureClearingAgt_Yes").click(function() {
            if(this.checked) {
                $("#othTemperatureClearingAgt").val('')
                $("#othTemperatureClearingAgtRow").hide()
            }
        })

        $("#temperatureClearingAgt_No").click(function() {
            if(this.checked) {
                $("#othTemperatureClearingAgtRow").show()
            }
        });

        $("#paraffImpreg_Yes").click(function() {
            if(this.checked) {
                $("#othParaffImpreg").val('')
                $("#othParaffImpregRow").hide()
            }
        })

        $("#paraffImpreg_No").click(function() {
            if(this.checked) {
                $("#othParaffImpregRow").show()
            }
        });

        $("#tempParaffinProc_Yes").click(function() {
            if(this.checked) {
                $("#othTempParaffinProc").val('')
                $("#othTempParaffinProcRow").hide()
            }
        })

        $("#tempParaffinProc_No").click(function() {
            if(this.checked) {
                $("#othTempParaffinProcRow").show()
            }
        });

        $("#paraffinManufacturer_Fisher").click(function() {
            if(this.checked) {
                $("#otherParaffinManufacturer").val('')
                $("#otherParaffinManufacturerRow").hide()
            }
        })

        $("#paraffinManufacturer_Other").click(function() {
            if(this.checked) {
                $("#otherParaffinManufacturerRow").show()
            }
        });

        $("#paraffinUsage_FW").click(function() {
            if(this.checked) {
                $("#otherParaffinUsage").val('')
                $("#otherParaffinUsageRow").hide()
            }
        })

        $("#paraffinUsage_Other").click(function() {
            if(this.checked) {
                $("#otherParaffinUsageRow").show()
            }
        });

        $("#storedFfpeBlocksPerSop_Yes").click(function() {
            if(this.checked) {
                $("#othStoredFfpeBlocksPerSop").val('')
                $("#othStoredFfpeBlocksPerSopRow").hide()
            }
        })

        $("#storedFfpeBlocksPerSop_No").click(function() {
            if(this.checked) {
                $("#othStoredFfpeBlocksPerSopRow").show()
            }
        });
    });
</script>

<g:render template="/formMetadata/timeConstraint" bean="${bpvTissueProcessEmbedInstance.formMetadata}" var="formMetadata"/>
<g:render template="/caseRecord/caseDetails" bean="${bpvTissueProcessEmbedInstance.caseRecord}" var="caseRecord"/>
<g:set var="labelNumber" value="${1}"/>

<div class="list">
    <table>
        <tbody>
          <tr class="prop">
                <td valign="top" class="name">
                    <label for="expKeyBarcodeId">${labelNumber++}. Experimental Key Barcode ID:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'expKeyBarcodeId', 'errors')}">
                    <g:textField name="expKeyBarcodeId" value="${bpvTissueProcessEmbedInstance?.expKeyBarcodeId}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="parentBarcodeId">${labelNumber++}. Parent Tissue Specimen ID:</label>
                </td>
                <td valign="top" class="value}">
                  
                  <%-- Auto populated manaully or read.  This is to match the gross/module specimen created later on the FFPE worksheet main.--%>                  
                  
                  <g:if test="${bpvTissueProcessEmbedInstance.caseRecord.bpvWorkSheet?.parentSampleId}">
                      ${bpvTissueProcessEmbedInstance.caseRecord.bpvWorkSheet.parentSampleId}
                  </g:if>
                  <g:else>
                    ${bpvTissueProcessEmbedInstance.caseRecord.caseId}-00
                  </g:else>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="processingSop">${labelNumber++}. BPV SOP governing processing of FFPE Tissue:</label>
                </td>
                <td valign="top" class="value">
                    <g:if test="${params.action == 'create'}">
                        ${bpvTissueProcessEmbedInstance?.formMetadata?.sops?.get(0)?.sopNumber}
                        ${bpvTissueProcessEmbedInstance?.formMetadata?.sops?.get(0)?.sopName}  
                        ${bpvTissueProcessEmbedInstance?.formMetadata?.sops?.get(0)?.activeSopVer}
                    </g:if>
                    <g:else>
                        ${bpvTissueProcessEmbedInstance?.processingSOP?.sopNumber}
                        ${SOP.get(bpvTissueProcessEmbedInstance?.processingSOP?.sopId)?.sopName}  
                        ${bpvTissueProcessEmbedInstance?.processingSOP?.sopVersion}
                    </g:else>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="tissProcessorMdl">${labelNumber++}. Make and model of Tissue Processor:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'tissProcessorMdl', 'errors')}">
                    <div>
                        <g:radio name="tissProcessorMdl" id="tissProcessorMdl_LPRTP" value="Leica Peloris Rapid Tissue Processor" checked="${bpvTissueProcessEmbedInstance?.tissProcessorMdl == 'Leica Peloris Rapid Tissue Processor'}"/>
                        &nbsp;<label for="tissProcessorMdl_LPRTP">Leica Peloris Rapid Tissue Processor</label>
                        <br/>
                        <g:radio name="tissProcessorMdl" id="tissProcessorMdl_Other" value="Other, Specify" checked="${bpvTissueProcessEmbedInstance?.tissProcessorMdl == 'Other, Specify'}"/>
                        &nbsp;<label for="tissProcessorMdl_Other">Other, Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othTissProcessorMdlRow" style="display:${bpvTissueProcessEmbedInstance?.tissProcessorMdl == 'Other, Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othTissProcessorMdl', 'errors')}">
                    <g:textField name="othTissProcessorMdl" value="${bpvTissueProcessEmbedInstance?.othTissProcessorMdl}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="procMaintenance">${labelNumber++}. Was processor maintenance provided as per the manufacturer recommendation:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'procMaintenance', 'errors')}">
                    <div>
                        <g:radio name="procMaintenance" id="procMaintenance_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.procMaintenance == 'Yes'}"/>
                        &nbsp;<label for="procMaintenance_Yes">Yes</label>
                        <br/>
                        <g:radio name="procMaintenance" id="procMaintenance_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.procMaintenance == 'No - Specify'}"/>
                        &nbsp;<label for="procMaintenance_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othProcMaintenanceRow" style="display:${bpvTissueProcessEmbedInstance?.procMaintenance == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othProcMaintenance', 'errors')}">
                    <g:textField name="othProcMaintenance" value="${bpvTissueProcessEmbedInstance?.othProcMaintenance}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="alcoholType">${labelNumber++}. Type of alcohol:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'alcoholType', 'errors')}">
                    <div>
                        <g:radio name="alcoholType" id="alcoholType_AE" value="Absolute Ethanol" checked="${bpvTissueProcessEmbedInstance?.alcoholType == 'Absolute Ethanol'}"/>
                        &nbsp;<label for="alcoholType_AE">Absolute Ethanol (100%)</label>
                        <br/>
                        <g:radio name="alcoholType" id="alcoholType_Other" value="Other, Specify" checked="${bpvTissueProcessEmbedInstance?.alcoholType == 'Other, Specify'}"/>
                        &nbsp;<label for="alcoholType_Other">Other, Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othAlcoholTypeRow" style="display:${bpvTissueProcessEmbedInstance?.alcoholType == 'Other, Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othAlcoholType', 'errors')}">
                    <g:textField name="othAlcoholType" value="${bpvTissueProcessEmbedInstance?.othAlcoholType}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="clearingAgt">${labelNumber++}. Type of clearing agent:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'clearingAgt', 'errors')}">
                    <div>
                        <g:radio name="clearingAgt" id="clearingAgt_Xylene" value="Xylene" checked="${bpvTissueProcessEmbedInstance?.clearingAgt == 'Xylene'}"/>
                        &nbsp;<label for="clearingAgt_Xylene">Xylene</label>
                        <br/>
                        <g:radio name="clearingAgt" id="clearingAgt_Other" value="Other, Specify" checked="${bpvTissueProcessEmbedInstance?.clearingAgt == 'Other, Specify'}"/>
                        &nbsp;<label for="clearingAgt_Other">Other, Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othClearingAgtRow" style="display:${bpvTissueProcessEmbedInstance?.clearingAgt == 'Other, Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othClearingAgt', 'errors')}">
                    <g:textField name="othClearingAgt" value="${bpvTissueProcessEmbedInstance?.othClearingAgt}"/>
                </td>
            </tr>
            <tr><td colspan="2" class="formheader">Were the following done as per the BPV FFPE Tissue Processing SOP?</td></tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="alcoholStgDur">${labelNumber++}. Alcohol stage duration:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'alcoholStgDur', 'errors')}">
                    <div>
                        <g:radio name="alcoholStgDur" id="alcoholStgDur_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.alcoholStgDur == 'Yes'}"/>
                        &nbsp;<label for="alcoholStgDur_Yes">Yes</label>
                        <br/>
                        <g:radio name="alcoholStgDur" id="alcoholStgDur_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.alcoholStgDur == 'No - Specify'}"/>
                        &nbsp;<label for="alcoholStgDur_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othAlcoholStgDurRow" style="display:${bpvTissueProcessEmbedInstance?.alcoholStgDur == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othAlcoholStgDur', 'errors')}">
                    <g:textField name="othAlcoholStgDur" value="${bpvTissueProcessEmbedInstance?.othAlcoholStgDur}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="dehydrationProcDur">${labelNumber++}. Duration of dehydration process:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'dehydrationProcDur', 'errors')}">
                    <div>
                        <g:radio name="dehydrationProcDur" id="dehydrationProcDur_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.dehydrationProcDur == 'Yes'}"/>
                        &nbsp;<label for="dehydrationProcDur_Yes">Yes</label>
                        <br/>
                        <g:radio name="dehydrationProcDur" id="dehydrationProcDur_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.dehydrationProcDur == 'No - Specify'}"/>
                        &nbsp;<label for="dehydrationProcDur_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othDehydrationProcDurRow" style="display:${bpvTissueProcessEmbedInstance?.dehydrationProcDur == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othDehydrationProcDur', 'errors')}">
                    <g:textField name="othDehydrationProcDur" value="${bpvTissueProcessEmbedInstance?.othDehydrationProcDur}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="temperatureDehyd">${labelNumber++}. Temperature of dehydration:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'temperatureDehyd', 'errors')}">
                    <div>
                        <g:radio name="temperatureDehyd" id="temperatureDehyd_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.temperatureDehyd == 'Yes'}"/>
                        &nbsp;<label for="temperatureDehyd_Yes">Yes</label>
                        <br/>
                        <g:radio name="temperatureDehyd" id="temperatureDehyd_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.temperatureDehyd == 'No - Specify'}"/>
                        &nbsp;<label for="temperatureDehyd_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othTemperatureDehydRow" style="display:${bpvTissueProcessEmbedInstance?.temperatureDehyd == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othTemperatureDehyd', 'errors')}">
                    <g:textField name="othTemperatureDehyd" value="${bpvTissueProcessEmbedInstance?.othTemperatureDehyd}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="numStages">${labelNumber++}. Number of stages/replicates:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'numStages', 'errors')}">
                    <div>
                        <g:radio name="numStages" id="numStages_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.numStages == 'Yes'}"/>
                        &nbsp;<label for="numStages_Yes">Yes</label>
                        <br/>
                        <g:radio name="numStages" id="numStages_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.numStages == 'No - Specify'}"/>
                        &nbsp;<label for="numStages_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othNumStagesRow" style="display:${bpvTissueProcessEmbedInstance?.numStages == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othNumStages', 'errors')}">
                    <g:textField name="othNumStages" value="${bpvTissueProcessEmbedInstance?.othNumStages}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="clearingAgtDur">${labelNumber++}. Duration in clearing agent:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'clearingAgtDur', 'errors')}">
                    <div>
                        <g:radio name="clearingAgtDur" id="clearingAgtDur_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.clearingAgtDur == 'Yes'}"/>
                        &nbsp;<label for="clearingAgtDur_Yes">Yes</label>
                        <br/>
                        <g:radio name="clearingAgtDur" id="clearingAgtDur_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.clearingAgtDur == 'No - Specify'}"/>
                        &nbsp;<label for="clearingAgtDur_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othClearingAgtDurRow" style="display:${bpvTissueProcessEmbedInstance?.clearingAgtDur == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othClearingAgtDur', 'errors')}">
                    <g:textField name="othClearingAgtDur" value="${bpvTissueProcessEmbedInstance?.othClearingAgtDur}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="temperatureClearingAgt">${labelNumber++}. Temperature of clearing agent:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'temperatureClearingAgt', 'errors')}">
                    <div>
                        <g:radio name="temperatureClearingAgt" id="temperatureClearingAgt_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.temperatureClearingAgt == 'Yes'}"/>
                        &nbsp;<label for="temperatureClearingAgt_Yes">Yes</label>
                        <br/>
                        <g:radio name="temperatureClearingAgt" id="temperatureClearingAgt_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.temperatureClearingAgt == 'No - Specify'}"/>
                        &nbsp;<label for="temperatureClearingAgt_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othTemperatureClearingAgtRow" style="display:${bpvTissueProcessEmbedInstance?.temperatureClearingAgt == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othTemperatureClearingAgt', 'errors')}">
                    <g:textField name="othTemperatureClearingAgt" value="${bpvTissueProcessEmbedInstance?.othTemperatureClearingAgt}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="paraffImpreg">${labelNumber++}. Paraffin impregnation method:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'paraffImpreg', 'errors')}">
                    <div>
                        <g:radio name="paraffImpreg" id="paraffImpreg_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.paraffImpreg == 'Yes'}"/>
                        &nbsp;<label for="paraffImpreg_Yes">Yes</label>
                        <br/>
                        <g:radio name="paraffImpreg" id="paraffImpreg_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.paraffImpreg == 'No - Specify'}"/>
                        &nbsp;<label for="paraffImpreg_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othParaffImpregRow" style="display:${bpvTissueProcessEmbedInstance?.paraffImpreg == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othParaffImpreg', 'errors')}">
                    <g:textField name="othParaffImpreg" value="${bpvTissueProcessEmbedInstance?.othParaffImpreg}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="tempParaffinProc">${labelNumber++}. Temperature of paraffin:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'tempParaffinProc', 'errors')}">
                    <div>
                        <g:radio name="tempParaffinProc" id="tempParaffinProc_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.tempParaffinProc == 'Yes'}"/>
                        &nbsp;<label for="tempParaffinProc_Yes">Yes</label>
                        <br/>
                        <g:radio name="tempParaffinProc" id="tempParaffinProc_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.tempParaffinProc == 'No - Specify'}"/>
                        &nbsp;<label for="tempParaffinProc_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othTempParaffinProcRow" style="display:${bpvTissueProcessEmbedInstance?.tempParaffinProc == 'No - Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othTempParaffinProc', 'errors')}">
                    <g:textField name="othTempParaffinProc" value="${bpvTissueProcessEmbedInstance?.othTempParaffinProc}"/>
                </td>
            </tr>
            <tr class="prop">
                <td colspan="2" class="name ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'addtlCommtsProc', 'errors')}">
                    <label for="addtlCommtsProc">${labelNumber++}. Provide any comments related to FFPE processing:</label><br />
                    <g:textArea class="textwide" name="addtlCommtsProc" cols="40" rows="5" value="${bpvTissueProcessEmbedInstance?.addtlCommtsProc}"/>
                </td>
            </tr>
            <tr><td colspan="2" class="formheader">Embedding</td></tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="embeddingSop">${labelNumber++}. BPV SOP governing embedding of FFPE Tissue:</label>
                </td>
                <td valign="top" class="value">
                    <g:if test="${params.action == 'create'}">
                        ${bpvTissueProcessEmbedInstance?.formMetadata?.sops?.get(1)?.sopNumber}
                        ${bpvTissueProcessEmbedInstance?.formMetadata?.sops?.get(1)?.sopName}  
                        ${bpvTissueProcessEmbedInstance?.formMetadata?.sops?.get(1)?.activeSopVer}
                    </g:if>
                    <g:else>
                        ${bpvTissueProcessEmbedInstance?.embeddingSOP?.sopNumber}
                        ${SOP.get(bpvTissueProcessEmbedInstance?.embeddingSOP?.sopId)?.sopName}  
                        ${bpvTissueProcessEmbedInstance?.embeddingSOP?.sopVersion}
                    </g:else>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="typeParaffin">${labelNumber++}. Type of paraffin:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'typeParaffin', 'errors')}">
                    <g:textField name="typeParaffin" value="${bpvTissueProcessEmbedInstance?.typeParaffin}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="paraffinManufacturer">${labelNumber++}. Manufacturer of paraffin:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'paraffinManufacturer', 'errors')}">
                    <div>
                        <g:radio name="paraffinManufacturer" id="paraffinManufacturer_Fisher" value="Fisher" checked="${bpvTissueProcessEmbedInstance?.paraffinManufacturer == 'Fisher'}"/>
                        &nbsp;<label for="paraffinManufacturer_Fisher">Fisher</label>
                        <br/>
                        <g:radio name="paraffinManufacturer" id="paraffinManufacturer_Other" value="Other, Specify" checked="${bpvTissueProcessEmbedInstance?.paraffinManufacturer == 'Other, Specify'}"/>
                        &nbsp;<label for="paraffinManufacturer_Other">Other, Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="otherParaffinManufacturerRow" style="display:${bpvTissueProcessEmbedInstance?.paraffinManufacturer == 'Other, Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'otherParaffinManufacturer', 'errors')}">
                    <g:textField name="otherParaffinManufacturer" value="${bpvTissueProcessEmbedInstance?.otherParaffinManufacturer}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="paraffinProductNum">${labelNumber++}. Paraffin product #:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'paraffinProductNum', 'errors')}">
                    <g:textField name="paraffinProductNum" value="${bpvTissueProcessEmbedInstance?.paraffinProductNum}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="paraffinLotNum">${labelNumber++}. Paraffin lot #:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'paraffinLotNum', 'errors')}">
                    <g:textField name="paraffinLotNum" value="${bpvTissueProcessEmbedInstance?.paraffinLotNum}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="tempParaffinEmbed">${labelNumber++}. Temperature of paraffin processing for Embedding:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'tempParaffinEmbed', 'errors')}">
                    <g:textField size="4" name="tempParaffinEmbed" value="${fieldValue(bean: bpvTissueProcessEmbedInstance, field: 'tempParaffinEmbed')}" onkeyup="isNumericValidation(this)"/>&nbsp;&nbsp;
                    <g:select name="tempParaffinEmbedUnit" value="${bpvTissueProcessEmbedInstance?.tempParaffinEmbedUnit}" keys="${['Fahrenheit']}" from="${['°F']}" noSelection="['Celsius':'°C']"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="paraffinUsage">${labelNumber++}. Type of paraffin used in embedding:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'paraffinUsage', 'errors')}">
                    <div>
                        <g:radio name="paraffinUsage" id="paraffinUsage_FW" value="Fresh Paraffin" checked="${bpvTissueProcessEmbedInstance?.paraffinUsage == 'Fresh Paraffin'}"/>
                        &nbsp;<label for="paraffinUsage_FW">Fresh Paraffin</label>
                        <br/>
                        <g:radio name="paraffinUsage" id="paraffinUsage_Other" value="Other, Specify" checked="${bpvTissueProcessEmbedInstance?.paraffinUsage == 'Other, Specify'}"/>
                        &nbsp;<label for="paraffinUsage_Other">Other, Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="otherParaffinUsageRow" style="display:${bpvTissueProcessEmbedInstance?.paraffinUsage == 'Other, Specify'?'display':'none'}">
                <td valign="top" class="name"></td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'otherParaffinUsage', 'errors')}">
                    <g:textField name="otherParaffinUsage" value="${bpvTissueProcessEmbedInstance?.otherParaffinUsage}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="waxAge">${labelNumber++}. Age of paraffin:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'waxAge', 'errors')}">
                    <g:textField name="waxAge" value="${bpvTissueProcessEmbedInstance?.waxAge}"/>&nbsp;day(s)
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="totalTimeBlocksCooled">${labelNumber++}. Total time the freshly poured blocks were cooled:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'totalTimeBlocksCooled', 'errors')}">
                    <g:textField name="totalTimeBlocksCooled" value="${bpvTissueProcessEmbedInstance?.totalTimeBlocksCooled}"/>&nbsp;minute(s)
                </td>
            </tr>
            <tr><td colspan="2" class="formheader">FFPE Block Storage</td></tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="handleTrackStoreSop">${labelNumber++}. SOP governing handling, tracking and storage of FFPE tissue block:</label>                   
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'siteSOPProcEmbed', 'errors')}">
                  <g:textField name="siteSOPProcEmbed" value="${bpvTissueProcessEmbedInstance?.siteSOPProcEmbed}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="storedFfpeBlocksPerSop">${labelNumber++}. Were the FFPE blocks stored as per SOP:</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'storedFfpeBlocksPerSop', 'errors')}">
                    <div>
                        <g:radio name="storedFfpeBlocksPerSop" id="storedFfpeBlocksPerSop_Yes" value="Yes" checked="${bpvTissueProcessEmbedInstance?.storedFfpeBlocksPerSop == 'Yes'}"/>
                        &nbsp;<label for="storedFfpeBlocksPerSop_Yes">Yes</label>
                        <br/>
                        <g:radio name="storedFfpeBlocksPerSop" id="storedFfpeBlocksPerSop_No" value="No - Specify" checked="${bpvTissueProcessEmbedInstance?.storedFfpeBlocksPerSop == 'No - Specify'}"/>
                        &nbsp;<label for="storedFfpeBlocksPerSop_No">No - Specify</label>
                    </div>
                </td>
            </tr>
            <tr class="prop" id="othStoredFfpeBlocksPerSopRow" style="display:${bpvTissueProcessEmbedInstance?.storedFfpeBlocksPerSop == 'No - Specify'?'display':'none'}">
                <td colspan="2" class="name ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'othStoredFfpeBlocksPerSop', 'errors')}">
                    <g:textArea class="textwide" name="othStoredFfpeBlocksPerSop" cols="40" rows="5" value="${bpvTissueProcessEmbedInstance?.othStoredFfpeBlocksPerSop}"/>
                </td>
            </tr>
            <tr class="prop">
                <td colspan="2" class="name ${hasErrors(bean: bpvTissueProcessEmbedInstance, field: 'addtlCommentsEmbed', 'errors')}">
                    <label for="addtlCommentsEmbed">${labelNumber++}. Provide any additional comments related to paraffin embedding of FFPE tissue:</label><br />
                    <g:textArea class="textwide" name="addtlCommentsEmbed" cols="40" rows="5" value="${bpvTissueProcessEmbedInstance?.addtlCommentsEmbed}"/>
                </td>
            </tr>
        </tbody>
    </table>
</div>
