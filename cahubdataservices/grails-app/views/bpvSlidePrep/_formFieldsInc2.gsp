<%@ page import="nci.obbr.cahub.staticmembers.Module" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>

<script type="text/javascript">
    $(document).ready(function() {
          $("#microtome").change(function() {
              if($("#microtome").val() == 'Other (specify)') {
                  $("#microtomeOsRow").show()
              } else {
                  $("#microtomeOsRow").hide()
                  $("#microtomeOs").val('')
              }
          });

          $("#microtomeBladeType").change(function() {
              if($("#microtomeBladeType").val() == 'Other (specify)') {
                  $("#microtomeBladeTypeOsRow").show()
              } else {
                  $("#microtomeBladeTypeOsRow").hide()
                  $("#microtomeBladeTypeOs").val('')
              }
          });

          $("#microtomeBladeAge").change(function() {
              if($("#microtomeBladeAge").val() == 'Other (specify)') {
                  $("#microtomeBladeAgeOsRow").show()
              } else {
                  $("#microtomeBladeAgeOsRow").hide()
                  $("#microtomeBladeAgeOs").val('')
              }
          });

          $("#facedBlockPrep").change(function() {
              if($("#facedBlockPrep").val() == 'Other (specify)') {
                  $("#facedBlockPrepOsRow").show()
              } else {
                  $("#facedBlockPrepOsRow").hide()
                  $("#facedBlockPrepOs").val('')
              }
          });

          $("#sectionThickness").change(function() {
              if($("#sectionThickness").val() == 'Other (specify)') {
                  $("#sectionThicknessOsRow").show()
              } else {
                  $("#sectionThicknessOsRow").hide()
                  $("#sectionThicknessOs").val('')
              }
          });

          $("#slideCharge").change(function() {
              if($("#slideCharge").val() == 'Other (specify)') {
                  $("#slideChargeOsRow").show()
              } else {
                  $("#slideChargeOsRow").hide()
                  $("#slideChargeOs").val('')
              }
        });

        $("#waterBathTemp").change(function() {
            if($("#waterBathTemp").val() == 'Other (specify)') {
                $("#waterBathTempOsRow").show()
            } else {
                $("#waterBathTempOsRow").hide()
                $("#waterBathTempOs").val('')
            }
        });

        $("#microtomeDailyMaint").change(function() {
            if($("#microtomeDailyMaint").val() == 'Other (specify)') {
                $("#microtomeDailyMaintOsRow").show()
            } else {
                $("#microtomeDailyMaintOsRow").hide()
                $("#microtomeDailyMaintOs").val('')
            }
        });

        $("#waterbathMaint").change(function() {
            if($("#waterbathMaint").val() == 'Other (specify)') {
                $("#waterbathMaintOsRow").show()
            } else {
                $("#waterbathMaintOsRow").hide()
                $("#waterbathMaintOs").val('')
            }
        });

        $("#heTimeInOven").change(function() {
            if($("#heTimeInOven").val() == 'Other (specify)') {
                $("#heTimeInOvenOsRow").show()
            } else {
                $("#heTimeInOvenOsRow").hide()
                $("#heTimeInOvenOs").val('')
            }
        });

        $("#heOvenTemp").change(function() {
            if($("#heOvenTemp").val() == 'Other (specify)') {
                $("#heOvenTempOsRow").show()
            } else {
                $("#heOvenTempOsRow").hide()
                $("#heOvenTempOs").val('')
            }
        });

        $("#heDeParrafinMethod").change(function() {
            if($("#heDeParrafinMethod").val() == 'Other (specify)') {
                $("#heDeParrafinMethodOsRow").show()
            } else {
                $("#heDeParrafinMethodOsRow").hide()
                $("#heDeParrafinMethodOs").val('')
            }
        });

        $("#heStainMethod").change(function() {
            if($("#heStainMethod").val() == 'Other (specify)') {
                $("#heStainMethodOsRow").show()
            } else {
                $("#heStainMethodOsRow").hide()
                $("#heStainMethodOs").val('')
            }
        });

        $("#heClearingMethod").change(function() {
            if($("#heClearingMethod").val() == 'Other (specify)') {
                $("#heClearingMethodOsRow").show()
            } else {
                $("#heClearingMethodOsRow").hide()
                $("#heClearingMethodOs").val('')
            }
        });

        $("#heCoverSlipping").change(function() {
            if($("#heCoverSlipping").val() == 'Other (specify)') {
                $("#heCoverSlippingOsRow").show()
            } else {
                $("#heCoverSlippingOsRow").hide()
                $("#heCoverSlippingOs").val('')
            }
        });

        $("#heEquipMaint").change(function() {
            if($("#heEquipMaint").val() == 'Other (specify)') {
                $("#heEquipMaintOsRow").show()
            } else {
                $("#heEquipMaintOsRow").hide()
                $("#heEquipMaintOs").val('')
            }
        });
    });
</script>
<g:set var="cid" value="${bpvSlidePrepInstance?.caseRecord?.id ?: params.caseRecord?.id}" />
<g:set var="labelNumber" value="${1}"/>
<g:render template="/formMetadata/timeConstraint" bean="${bpvSlidePrepInstance.formMetadata}" var="formMetadata"/>
<g:render template="/caseRecord/caseDetails" bean="${bpvSlidePrepInstance?.caseRecord}" var="caseRecord" />

<div class="list">
    <table class="tdwrap">
        <tbody>
            <tr><td colspan="2" class="formheader">FFPE Sectioning</td></tr>
                <tr class="prop">
                    <td valign="top" class="name" style="width:300px;">
                        <label for="slidePrepTech"><g:message code="bpvSlidePrep.slidePrepTech.label" default="Slide Prep technician name:" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'slidePrepTech', 'errors')}">
                        <g:textField name="slidePrepTech" value="${bpvSlidePrepInstance?.slidePrepTech}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td colspan="2" class="name formheader">Slides Created</td>
                </tr>

                <tr class="prop subentry clearborder">
                    <td valign="top" colspan="2" class="name" >
                        <div id="slideDialog">
                            <table>
                                <tbody>
                                  <tr>
                                      <th valign="top" class="name">Slide ID(s)<span id="bpvslideprep.slideid" class="vocab-tooltip"></span></th>
                                       <th valign="top" class="name">Module<span id="bpvslideprep.module" class="vocab-tooltip"></span></th>
                                        <th class="editOnly" valign="top" style="text-align: center">Action</th>
                                  </tr>
                                  <g:each in="${slides}" status="i" var="slide">
                                      <tr class="${i%2 == 0 ? 'even' : 'odd'}">
                                          <td> <g:textField name="slideId_${slide.id}"  value="${slide?.slideId}"/></td>
                                           <g:if test="${CaseRecord.findById(cid).bpvWorkSheet && CaseRecord.findById(cid).bpvWorkSheet.formVersion==1}">
                                          <td><g:select name="module_${slide.id}" id="module" from="${[Module.findByCode('MODULE1'),Module.findByCode('MODULE2'), Module.findByCode('MODULE3N'), Module.findByCode('MODULE4N')] }" optionKey="id" value="${slide?.module?.id}"  noSelection="['': '']" /> </td>
                                          </g:if>
                                          <g:else>
                                            <td><g:select name="module_${slide.id}" id="module" from="${[Module.findByCode('MODULE1'),Module.findByCode('MODULE2'), Module.findByCode('MODULE5')] }" optionKey="id" value="${slide?.module?.id}"  noSelection="['': '']" /> </td>
                                          </g:else>
                                           <td style="text-align: center" class="editOnly timeEntry">
                                             <g:if test="${!slide.specimenRecord}">
                                              <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" action="deleteSlide" value="Delete2" update="slideDialog" params="'caseId=${slide.caseId}'" id="${slide.id}">
                                                <span class="ui-icon ui-icon-trash">Delete</span>
                                               </g:remoteLink>
                                          </g:if>
                                           <g:else>
                                             &nbsp;
                                           </g:else>
                                           </td>
                                      </tr>
                                  </g:each>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>

                <tr id="add1Row" class="subentry"><td colspan="2"><button class="Btn" id="addSlideBtn">Add</button></td></tr>

                <tr><td colspan="2" class="formheader">FFPE Sectioning Details</td></tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        ${labelNumber++}. Slide Prep SOP:<span id="bpvslideprep.ffpetissueprepsop" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'siteSOPSlidePrep', 'errors')}">
                        <g:textField name="siteSOPSlidePrep" value="${bpvSlidePrepInstance?.siteSOPSlidePrep}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        ${labelNumber++}. Microtome:<span id="bpvslideprep.microtome" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtome', 'errors')}">
                        <g:select name="microtome" from="${['Microm Ergostar HM200', 'Other (specify)']}" value="${bpvSlidePrepInstance.microtome}" noSelection="['': '']" />
                    </td>
                </tr>

                <tr class="prop" id="microtomeOsRow" style="display:${bpvSlidePrepInstance?.microtome == 'Other (specify)'?'display':'none'}">
                    <td valign="top" class="name">
                        &nbsp;&nbsp;&nbsp;&nbsp;Other Microtome (specify):<span id="bpvslideprep.othermicrotome" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeOs', 'errors')}">
                        <g:textField name="microtomeOs" value="${bpvSlidePrepInstance?.microtomeOs}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                      ${labelNumber++}. Microtome blade type:<span id="bpvslideprep.microtomeblade" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeBladeType', 'errors')}">
                        <g:select name="microtomeBladeType" from="${['Low profile Sakura Accu-Edge', 'Other (specify)']}" value="${bpvSlidePrepInstance.microtomeBladeType}" noSelection="['': '']" />
                    </td>
                </tr>

                <tr class="prop" id="microtomeBladeTypeOsRow" style="display:${bpvSlidePrepInstance?.microtomeBladeType == 'Other (specify)'?'display':'none'}">
                    <td valign="top" class="name">
                        &nbsp;&nbsp;&nbsp;&nbsp;Other Microtome Blade Type (specify):<span id="bpvslideprep.othermicrotomeblade" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeBladeTypeOs', 'errors')}">
                        <g:textField name="microtomeBladeTypeOs" value="${bpvSlidePrepInstance?.microtomeBladeTypeOs}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        ${labelNumber++}. Microtome blade age:<span id="bpvslideprep.microtomebladeage" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeBladeAge', 'errors')}">
                        <g:select name="microtomeBladeAge" from="${['Fresh', 'Other (specify)']}" value="${bpvSlidePrepInstance.microtomeBladeAge}" noSelection="['': '']" />
                    </td>
                </tr>

                <tr class="prop" id="microtomeBladeAgeOsRow" style="display:${bpvSlidePrepInstance?.microtomeBladeAge == 'Other (specify)'?'display':'none'}">
                    <td valign="top" class="name">
                        &nbsp;&nbsp;&nbsp;&nbsp;Other Microtome blade age (specify):<span id="bpvslideprep.othermicrotomebladeage" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeBladeAgeOs', 'errors')}">
                        <g:textField name="microtomeBladeAgeOs" value="${bpvSlidePrepInstance?.microtomeBladeAgeOs}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        ${labelNumber++}. Preparation of block face for sectioning:<span id="bpvslideprep.blockfaceprep" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'facedBlockPrep', 'errors')}">
                        <g:select name="facedBlockPrep" from="${['20 Minutes on ice', 'Other (specify)']}" value="${bpvSlidePrepInstance.facedBlockPrep}" noSelection="['': '']" />
                    </td>
                </tr>

                <tr class="prop" id="facedBlockPrepOsRow" style="display:${bpvSlidePrepInstance?.facedBlockPrep == 'Other (specify)'?'display':'none'}">
                  <td valign="top" class="name">
                      &nbsp;&nbsp;&nbsp;&nbsp;Other Faced Block Prep (specify):<span id="bpvslideprep.otherblockfaceprep" class="vocab-tooltip"></span>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'facedBlockPrepOs', 'errors')}">
                      <g:textField name="facedBlockPrepOs" value="${bpvSlidePrepInstance?.facedBlockPrepOs}" />
                  </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        ${labelNumber++}. Section thickness:<span id="bpvslideprep.sectionthickness" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'sectionThickness', 'errors')}">
                        <g:select name="sectionThickness" from="${['4-5 micrometers', 'Other (specify)']}" value="${bpvSlidePrepInstance.sectionThickness}" noSelection="['': '']" />
                    </td>
                </tr>

                  <tr class="prop" id="sectionThicknessOsRow" style="display:${bpvSlidePrepInstance?.sectionThickness == 'Other (specify)'?'display':'none'}">
                    <td valign="top" class="name">
                        &nbsp;&nbsp;&nbsp;&nbsp;Other section thickness (specify):<span id="bpvslideprep.othersectionthickness" class="vocab-tooltip"></span>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'sectionThicknessOs', 'errors')}">
                        <g:textField name="sectionThicknessOs" value="${bpvSlidePrepInstance?.sectionThicknessOs}" />
                    </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. Slide charge:<span id="bpvslideprep.slidecharge" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'slideCharge', 'errors')}">
                          <g:select name="slideCharge" from="${['Charged','Uncharged','Other (specify)']}" value="${bpvSlidePrepInstance.slideCharge}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop" id="slideChargeOsRow" style="display:${bpvSlidePrepInstance?.slideCharge == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">
                          &nbsp;&nbsp;&nbsp;&nbsp;Other Slide charge (specify):<span id="bpvslideprep.otherslidecharge" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'slideChargeOs', 'errors')}">
                          <g:textField name="slideChargeOs" value="${bpvSlidePrepInstance?.slideChargeOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. Water bath temp:<span id="bpvslideprep.waterbathtemp" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'waterBathTemp', 'errors')}">
                          <g:select name="waterBathTemp" from="${['43 (+/-2)°C', 'Other (specify)']}" value="${bpvSlidePrepInstance.waterBathTemp}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop" id="waterBathTempOsRow" style="display:${bpvSlidePrepInstance?.waterBathTemp == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">
                          &nbsp;&nbsp;&nbsp;&nbsp;Other water bath temp (specify):<span id="bpvslideprep.otherwaterbathtemp" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'waterBathTempOs', 'errors')}">
                          <g:textField name="waterBathTempOs" value="${bpvSlidePrepInstance?.waterBathTempOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. Microtome maintenance:<span id="bpvslideprep.microtomemaint" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeDailyMaint', 'errors')}">
                          <g:select name="microtomeDailyMaint" from="${['Daily', 'Other (specify)']}" value="${bpvSlidePrepInstance.microtomeDailyMaint}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop" id="microtomeDailyMaintOsRow" style="display:${bpvSlidePrepInstance?.microtomeDailyMaint == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">
                          &nbsp;&nbsp;&nbsp;&nbsp;Please record any deviations from Microtome daily maintenance SOP:<span id="bpvslideprep.devmicrotomemaintsop" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'microtomeDailyMaintOs', 'errors')}" >
                          <g:textArea name="microtomeDailyMaintOs" cols="20" rows="3" value="${bpvSlidePrepInstance?.microtomeDailyMaintOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. Waterbath maintenance:<span id="bpvslideprep.waterbathmaint" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'waterbathMaint', 'errors')}">
                          <g:select name="waterbathMaint" from="${['Daily', 'Other (specify)']}" value="${bpvSlidePrepInstance.waterbathMaint}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop" id="waterbathMaintOsRow" style="display:${bpvSlidePrepInstance?.waterbathMaint == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please record any deviations from the Water Bath daily maintenance SOP:<span id="bpvslideprep.devwaterbathmaintsop" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'waterbathMaintOs', 'errors')}">
                          <g:textArea name="waterbathMaintOs" cols="20" rows="3" value="${bpvSlidePrepInstance?.waterbathMaintOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td colspan="2" class="name ${hasErrors(bean: bpvSlidePrepInstance, field: 'FFPEComments', 'errors')}">
                          ${labelNumber++}. Any additional comments related to preparation of FFPE tissue sections:<span id="bpvslideprep.ffpetissueprepcomments" class="vocab-tooltip"></span><br />
                          <g:textArea class="textwide" name="FFPEComments" cols="20" rows="3" value="${bpvSlidePrepInstance?.FFPEComments}" />
                      </td>
                  </tr>

                  <tr><td colspan="2" class="formheader">H&E Staining</td></tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E Stain SOP:<span id="bpvslideprep.hestainmountsop" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'siteSOPHEStain', 'errors')}">
                          <g:textField name="siteSOPHEStain" value="${bpvSlidePrepInstance?.siteSOPHEStain}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E time in oven:<span id="bpvslideprep.heoventime" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heTimeInOven', 'errors')}">
                          <g:select name="heTimeInOven" from="${['20 minutes', 'Other (specify)']}" value="${bpvSlidePrepInstance?.heTimeInOven}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop subentry" id="heTimeInOvenOsRow" style="display:${bpvSlidePrepInstance?.heTimeInOven == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">Other H&E time in oven (specify):<span id="bpvslideprep.otherheoventime" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heTimeInOvenOs', 'errors')}">
                          <g:textField name="heTimeInOvenOs" value="${bpvSlidePrepInstance?.heTimeInOvenOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E oven temp:<span id="bpvslideprep.heoventemp" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heOvenTemp', 'errors')}">
                          <g:select name="heOvenTemp" from="${['60° C', 'Other (specify)']}" value="${bpvSlidePrepInstance?.heOvenTemp}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop subentry" id="heOvenTempOsRow" style="display:${bpvSlidePrepInstance?.heOvenTemp == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">Other H&E oven temp (specify):<span id="bpvslideprep.otherheoventemp" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heOvenTempOs', 'errors')}">
                          <g:textField name="heOvenTempOs" value="${bpvSlidePrepInstance?.heOvenTempOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E de-paraffin method:<span id="bpvslideprep.hedeparaffinmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heDeParrafinMethod', 'errors')}">
                          <g:select name="heDeParrafinMethod" from="${['Manual', 'Automated stainer', 'Other (specify)']}" value="${bpvSlidePrepInstance?.heDeParrafinMethod}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop subentry" id="heDeParrafinMethodOsRow" style="display:${bpvSlidePrepInstance?.heDeParrafinMethod == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">Other H&E de-paraffin method (specify):<span id="bpvslideprep.otherhedeparaffinmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heDeParrafinMethodOs', 'errors')}">
                          <g:textField name="heDeParrafinMethodOs" value="${bpvSlidePrepInstance?.heDeParrafinMethodOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E stain method:<span id="bpvslideprep.hestainmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heStainMethod', 'errors')}">
                          <g:select name="heStainMethod" from="${['Manual', 'Automated stainer', 'Other (specify)']}" value="${bpvSlidePrepInstance?.heStainMethod}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop subentry" id="heStainMethodOsRow" style="display:${bpvSlidePrepInstance?.heStainMethod == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">Other H&E stain method (specify):<span id="bpvslideprep.otherhestainmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heStainMethodOs', 'errors')}">
                          <g:textField name="heStainMethodOs" value="${bpvSlidePrepInstance?.heStainMethodOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E clearing method:<span id="bpvslideprep.heclearingmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heClearingMethod', 'errors')}">
                          <g:select name="heClearingMethod" from="${['Manual', 'Automated stainer','Other (specify)']}" value="${bpvSlidePrepInstance?.heClearingMethod}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop subentry" id="heClearingMethodOsRow" style="display:${bpvSlidePrepInstance?.heClearingMethod == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">Other H&E clearing method (specify):<span id="bpvslideprep.otherheclearingmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heClearingMethodOs', 'errors')}">
                          <g:textField name="heClearingMethodOs" value="${bpvSlidePrepInstance?.heClearingMethodOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E cover slipping:<span id="bpvslideprep.hecovslipmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heCoverSlipping', 'errors')}">
                          <g:select name="heCoverSlipping" from="${['Manual', 'Other (specify)']}" value="${bpvSlidePrepInstance?.heCoverSlipping}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop subentry" id="heCoverSlippingOsRow" style="display:${bpvSlidePrepInstance.heCoverSlipping == 'Other (specify)'?'display':'none'}">
                      <td valign="top" class="name">Other H&E cover slipping (specify):<span id="bpvslideprep.otherhecovslipmethod" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heCoverSlippingOs', 'errors')}">
                          <g:textField name="heCoverSlippingOs" value="${bpvSlidePrepInstance?.heCoverSlippingOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name">
                          ${labelNumber++}. H&E equipment maintenance:<span id="bpvslideprep.hestainequipmaint" class="vocab-tooltip"></span>
                      </td>
                      <td valign="top" class="value ${hasErrors(bean: bpvSlidePrepInstance, field: 'heEquipMaint', 'errors')}">
                          <g:select name="heEquipMaint" from="${['Daily, Weekly, Bi-Monthly, Per SOP', 'Other (specify)']}" value="${bpvSlidePrepInstance.heEquipMaint}" noSelection="['': '']" />
                      </td>
                  </tr>

                  <tr class="prop" id="heEquipMaintOsRow" style="display:${bpvSlidePrepInstance.heEquipMaint == 'Other (specify)'?'display':'none'}">
                      <td colspan="2"class="name">Please record any deviations from the H&E equipment maintenance SOP:<span id="bpvslideprep.otherhestainequipmaint" class="vocab-tooltip"></span><br />
                          <g:textArea class="textwide" name="heEquipMaintOs" cols="20" rows="3" value="${bpvSlidePrepInstance?.heEquipMaintOs}" />
                      </td>
                  </tr>

                  <tr class="prop">
                      <td colspan="2" class="name ${hasErrors(bean: bpvSlidePrepInstance, field: 'heComments', 'errors')}">
                          ${labelNumber++}. Additional comments related to preparation of FFPE Hematoxylin and Eosin stained slides:<span id="bpvslideprep.ffpehestainedslideprepcomments" class="vocab-tooltip"></span><br />
                          <g:textArea class="textwide" name="heComments" cols="20" rows="3" value="${bpvSlidePrepInstance?.heComments}" />
                      </td>
                  </tr>
            </tbody>
        </table>
  </div>
