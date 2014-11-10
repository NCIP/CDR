
    <div id="tabs-plasma" class="tabbed-content">
                  <g:if test="${bloodFormVersion == 2}">
                    <h3>Mandatory: Plasma Aliquots - (6) 0.5 ml aliquots</h3>
                  </g:if>
                  <g:elseif test="${bloodFormVersion > 2}">
                    <h3>Mandatory: Plasma Aliquots - (12) 0.25 ml aliquots</h3>
                  </g:elseif>
                  <g:else>
                        <h3>DESIRABLE: Plasma aliquots - (6) 0.5 ml aliquots are desirable</h3>
                  </g:else>
      <table class="tab-table  dec12table">
        <tr><td colspan="2" class="formheader">EDTA Tube Centrifugation</td></tr>
                            <tr class="prop">
                                <td class="name">
                                    <label for="plasmaParBarcode">${labelNumber++}. EDTA collection tube Specimen barcode ID :</label>
                                </td>
                            <g:if test="${bloodFormVersion > 1}">
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaParBarcode', 'errors')}">
                                    <g:hiddenField id ="plasmaParBarCode" name="plasmaParBarcode" value="${bpvBloodFormInstance?.plasmaParBarcode}" /> 
                                    <input type="text" class="showonly" id="pageloadplasmaParBarCode" name="pageloadplasmaParBarCode"  value="${bpvBloodFormInstance?.plasmaParBarcode}" />
                                    <div id="plasmaParBarcodeDisp" class="barCodeSelect"></div>
                                </td>                              
                            </g:if>
                            <g:else>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaParBarcode', 'errors')}">
                                  <g:set var="plasmaPBCode" value="" />
                                  <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                    <g:if test="${specimen.parentSpecimen == null}">
                                    <g:if test="${specimen.tissueType.code == 'BLOODPLAS'}">
                                      <g:if test="${specimen.containerType.code == 'LAVEDTA'}">
                                        <g:set var="plasmaPBCode" value="${specimen.specimenId}" />
                                      </g:if> 
                                    </g:if>
                                   </g:if>
                                  </g:each>                                  
                                    <g:hiddenField name="plasmaParBarCode" value="${plasmaPBCode}" /> 
                                    <input type="text" class="showonly" id="pageloadplasmaParBarCode" name="pageloadplasmaParBarCode"  value="${plasmaPBCode}" />
                                    <div id="plasmaParBarcodeDisp" class="barCodeSelect">${plasmaPBCode}</div>
                                </td>
                              </g:else>                                
                            </tr>
                            <tr class="prop">
                                <td class="name">
                                    <label for="plasmaProcStart">${labelNumber++}. Time Plasma processing began:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcStart', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="plasmaProcStart" value="${bpvBloodFormInstance?.plasmaProcStart}" class="timeEntry "/>
                                </td>
                            </tr> 
                            <tr class="prop">
                                <td class="name">
                                    <label for="plasmaCTBarcode">${labelNumber++}. Conical centrifuge tube code:</label>
                                </td>
                            <g:if test="${bloodFormVersion > 1}">
                                      <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                        <g:if test="${specimen.parentSpecimen != null && specimen.parentSpecimen.specimenId == bpvBloodFormInstance?.plasmaParBarcode}">
                                        <g:if test="${specimen.tissueType.code == 'BLOODPLAS'}">
                                          <g:if test="${specimen.containerType.code == 'CONICT'}">
                                            <g:if test="${specimen.fixative.code == 'FRESH'}">
                                                    <g:set var="plasmaConicalTube" value="${specimen.specimenId}" />
                                                    <g:set var="plasmaConicalTubeId" value="${specimen.id}" />
                                                    <g:set var="plasmaConicalTubeVolume" value="${specimen.chpBloodRecord?.volume}" />
                                            </g:if>
                                          </g:if> 
                                        </g:if>
                                        </g:if>
                                      </g:each>                              
                            </g:if>
                            <g:else>
                                      <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                        <g:if test="${specimen.parentSpecimen != null}">
                                        <g:if test="${specimen.tissueType.code == 'BLOODPLAS'}">
                                          <g:if test="${specimen.containerType.code == 'CONICT'}">
                                            <g:if test="${specimen.fixative.code == 'FRESH'}">
                                                    <g:set var="plasmaConicalTube" value="${specimen.specimenId}" />
                                                    <g:set var="plasmaConicalTubeId" value="${specimen.id}" />
                                                    <g:set var="plasmaConicalTubeVolume" value="${specimen.chpBloodRecord?.volume}" />
                                            </g:if>
                                          </g:if> 
                                        </g:if>
                                        </g:if>
                                      </g:each>
                              </g:else>
                                
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaCTBarcode', 'errors')}">
                                  
                                  <g:if test="${plasmaConicalTube == null}">
                                          <g:hiddenField name="plasmaCTBarcode" type="hidden" />
                                          <g:hiddenField name="origPlasmaCTBarcode" value="" /><button class="Btn subentry ${bpvBloodFormInstance?.plasmaParBarcode==null?'unselectedbc':''}" id="addPlasmaCTBtn">Add</button>
<span id="selectSpecIDFirst" class="${bpvBloodFormInstance?.plasmaParBarcode==null?'unselectedbc':''}" >To add Conical centrifuge tube code, please first select Specimen Barcode ID in question #26</span>
                                  </g:if>
                                <g:else>
                                              <g:hiddenField name="origPlasmaCTBarcode" value="Yes" />
                                              <g:hiddenField name="plasmaCTBarcode" id="plasmaCTBarcode" value="Yes" /><span id="conTbId">${plasmaConicalTube}</span><a class="ui-button ui-state-default ui-corner-all removepadding plasmaBarcodeGroup" title="Delete" id="plasmaCTBarcodeDel" ><span class="ui-icon ui-icon-trash">Delete</span></a>
                                              <g:remoteLink class="plasmaCTBarcodeDel hide ui-button ui-state-default ui-corner-all removepadding" title="Delete" action="deleteSpecimen" value="Delete" update='[success:"ParentTubes",failure:"imgPlCTDel"]' params="'tube=PlasCT'" onSuccess="clearCT()" id="${plasmaConicalTubeId}" before="showWait('Deleting conical centrifuge tube code')" onComplete="plasmaCTBarcodeDel()"><span id='imgPlCTDel' class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                </g:else>   
                                </td>
                            </tr>
                            <tr class="prop plasmaBarcodeGroup clearborder">
                            <g:if test="${bloodFormVersion > 1}">
                                <td class="name"><label for="plasmaCTVol">${labelNumber}a. Conical tube volume:</label></td>                              
                            </g:if>
                            <g:else>
                                <td class="name"><label for="plasmaCTVol">${labelNumber++}. Conical tube volume:</label></td>
                            </g:else>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaCTVol', 'errors')}">
                                  <g:if test="${plasmaConicalTubeVolume == null}">
                                      <g:textField size="4" name="plasmaCTVol" disabled="true" /> ml
                                      <g:hiddenField name="origPlasmaCTVol" value="${fieldValue(bean: bpvBloodFormInstance, field: 'plasmaCTVol')}" />
                                  </g:if>
                                <g:else>
                                      <g:hiddenField name="plasmaCTVol" value="${bpvBloodFormInstance?.plasmaCTVol}" /><span id="conTbVol">${plasmaConicalTubeVolume} ml</span>
                                      <a title="Edit" href="" onClick="editPlasmaCT('${plasmaConicalTubeId}','${plasmaConicalTube.replace("'","\\'")}','${plasmaConicalTubeVolume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                </g:else>
                                </td>
                            </tr>
                            <tr class="saveNContinue prop clearborder">
                              <td class="name" colspan="2">
                                <div class="buttons">
                                   <span class="button"><input type="submit" name="_action_update" value="Save and Continue" class="save" /></span>
                                </div>
                              </td>
                            </tr>
                            <tr class="subentry clearborder plasmaBarcodeGroup plasma-tubes-section">
                                <td colspan="2" class="ui-state-default ${warningMap?.get('plasmaTubes_warn1') ? 'warnings' : ''} ${warningMap?.get('plasmaTubes_warn2') ? 'warnings' : ''}">
                            <g:if test="${bloodFormVersion == 2}">
                                  <h4>${labelNumber++}b. Plasma Aliquot Details: Enter information for each aliquot derived from Conical Centrifuge tube.
                                    <br>(Minimum of 6 aliquots containing 0.5 ml of plasma, 1.2 ml cryovials)</h4>                           
                            </g:if>
                            <g:elseif test="${bloodFormVersion > 2}">
                                  <h4>${labelNumber++}b. Plasma Aliquot Details: Enter information for each aliquot derived from Conical Centrifuge tube.
                                    <br>(Minimum of 12 aliquots containing 0.25 ml of plasma, 1.2 ml cryovials)</h4>
                            </g:elseif>
                            <g:else>
                                  <h4>Plasma Aliquot Details: Enter information for each aliquot derived from Conical Centrifuge tube.</h4>
                            </g:else>
                                  <div id="PlasmaTubes"><g:render template="plasmaTable"/></div>
                                </td></tr>
                            <tr id="add2Row" class="subentry plasmaBarcodeGroup"><td colspan="2">
                                <button class="Btn" id="addPlasmaBtn">Add</button>
                              </td></tr>
                            
                            <g:if test="${bloodFormVersion == 1}">
                            <tr class="prop plasmaBarcodeGroup">
                                <td class="name"><label for="plasmaProcEnd">${labelNumber++}. Time Plasma aliquot processing was completed:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcEnd', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="plasmaProcEnd" value="${bpvBloodFormInstance?.plasmaProcEnd}" class="timeEntry"  />
                                </td>
                            </tr>
                            <tr class="prop plasmaBarcodeGroup">           
                                <td class="name"><label for="plasmaProcFrozen">${labelNumber++}. Time Plasma aliquots were frozen:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcFrozen', 'errors')} ${warningMap?.get('plasmaProcFrozen') ? 'warnings' : ''}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="plasmaProcFrozen" value="${bpvBloodFormInstance?.plasmaProcFrozen}" class="timeEntry"  />
                                </td>
                            </tr>
                        
                            <tr class="prop plasmaBarcodeGroup">
                                <td class="name"><label for="plasmaProcStorage">${labelNumber++}. Time Plasma aliquots were transferred to storage:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcStorage', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="plasmaProcStorage" value="${bpvBloodFormInstance?.plasmaProcStorage}" class="timeEntry"  />
                                </td>
                            </tr>
                            </g:if>
                            <tr class="prop plasmaBarcodeGroup">
                                <td class="name"><label for="plasmaProcTech">${labelNumber++}. Plasma aliquots were processed by:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcTech', 'errors')}">
                                    <g:textField name="plasmaProcTech" value="${bpvBloodFormInstance?.plasmaProcTech}" />
                                </td>
                            </tr>
                            <g:if test="${bloodFormVersion > 1}">
                            <tr class="prop plasmaBarcodeGroup">
                                <td class="name"><label for="plasmaTransferTech">${labelNumber++}. Frozen Plasma transfer completed by:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaTransferTech', 'errors')}">
                                    <g:textField name="plasmaTransferTech" value="${bpvBloodFormInstance?.plasmaTransferTech}" />
                                </td>
                            </tr>                              
                            </g:if>
                          <tr class="plasmaBarcodeGroup"><td colspan="2" class="formheader">Note deviations from SOP, processing or storage issues</td></tr>
                            <tr class="prop plasmaBarcodeGroup">           
                                <td class="name"><label for="plasmaProcSopDev">${labelNumber++}. Plasma processing was performed in accordance with specified SOP:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcSopDev', 'errors')}">
                                    <g:bpvYesNoRadioPicker checked="${(bpvBloodFormInstance?.plasmaProcSopDev)}"  name="plasmaProcSopDev" />
                                </td>
                            </tr>
                        
                            <tr class="prop plasmaBarcodeGroup">
                                <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaProcComments', 'errors')}">
                                    <label for="plasmaProcComments">${labelNumber++}. Plasma processing comments:</label><br />
                                    <g:textArea class="textwide" name="plasmaProcComments" value="${bpvBloodFormInstance?.plasmaProcComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop plasmaBarcodeGroup">
                                <td class="name"><label for="plasmaHemolysis">${labelNumber++}. Was presence of Gross Hemolysis of Plasma observed?</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaHemolysis', 'errors')}">
                                    <g:bpvYesNoRadioPicker checked="${(bpvBloodFormInstance?.plasmaHemolysis)}"  name="plasmaHemolysis" />
                                </td>
                            </tr>
                        
                            <tr class="prop plasmaBarcodeGroup">
                                <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaStorageIssues', 'errors')}">
                                    <label for="plasmaStorageIssues">${labelNumber++}. Plasma storage issues:</label><br />
                                    <g:textArea class="textwide" name="plasmaStorageIssues" value="${bpvBloodFormInstance?.plasmaStorageIssues}" />
                                </td>
                            </tr>
                  
      </table>
    </div>
