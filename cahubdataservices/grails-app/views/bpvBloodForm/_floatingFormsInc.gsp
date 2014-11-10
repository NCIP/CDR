
                    <div id="add-parent" class="bloodpopups">
                          <g:formRemote name="parent" url="[controller: 'bpvBloodForm', action: 'updateParent']" update="ParentTubes" onComplete="updateParentSpecimens(${bpvBloodFormInstance?.caseRecord?.id})" >
                          <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                          <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                                  <table>
                                    <tbody>
                                      <tr>  
                                        <th class="name">Select Draw</th> 
                                        <th class="name">Collection Tube Specimen Barcode ID</th>  
                                        <th class="name">Specimen Tube Type</th>  
                                        <th class="name">Processed for</th>  
                                        <th class="name">Volume Collected</th> 
                                      </tr>
                                      <tr class="prop">
                                        <td class="value ${hasErrors(bean: specimenRecord, field: 'bloodTimeDraw', 'errors')}">
                                            <label for="drawTime" id="drawTimeLbl"></label>
                                        </td> 
                                        <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                                            <g:textField name="specimenId" id="specimenId" value="${specimenRecordInstance?.specimenId}" />
                                        </td>
                                        <td class="value ${hasErrors(bean: specimenRecord, field: 'containerType', 'errors')}"><div id="ctIdHTML">
                                            <g:select id="ctId" name="specimenRecordInstance.containerType.code" from="${bpvContainerList}" optionKey="code" value="${specimenRecordInstance?.containerType?.code}" noSelection="['null': '']" />
                                          </div></td>
                                        <td class="value ${hasErrors(bean: specimenRecord, field: 'acquisitionType', 'errors')}">
                                            <g:select id="code" name="specimenRecordInstance.tissueType.code" from="${bpvBloodList}" optionKey="code" value="${specimenRecordInstance?.tissueType?.code}" noSelection="['null': '']" />
                                        </td>
                                        <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                                            <g:textField id="volume" size="4" name="volume" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                                        </td>                                     
                                      </tr>
                                      <tr class="prop">
                                        <td colspan="5" class="popupbuttons ui-corner-all">
                                            <g:actionSubmit class="button bleft" action="updateParent" value="Save" id="saveBtn1" /> &nbsp;&nbsp;
                                            <g:actionSubmit class="button bright" action="update" value="Cancel" id="cancelButton1" />
                                        </td>
                                      </tr>
                                    </tbody>
                                  </table>
                          </g:formRemote>
                    </div>

                    <div id="edit-parent" class="bloodpopups">
                          <g:formRemote name="parent" url="[controller: 'bpvBloodForm', action: 'editParent']" update="ParentTubes" onComplete="buildTabsOnTable()" >
                          <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                          <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                          <g:hiddenField name="specParentId" />
                          <table>
                            <tbody>
                              <tr>
                                <th class="name">Blood Draw Time</th> 
                                <th class="name">Collection Tube Specimen Barcode ID</th>  
                                <th class="name">Specimen Tube Type</th>  
                                <th class="name">Processed for</th>  
                                <th class="name">Volume Collected</th>                                   
                              </tr>
                              <tr class="prop">
                                <td class="value ${hasErrors(bean: specimenRecord, field: 'bloodTimeDraw', 'errors')}">
                                    <label for="drawTime" id="drawTimeEdLbl"></label>
                                    <input id="drawTimeEd" name="drawtime" type="hidden" value="" />
                                </td> 
                                <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                                  <g:textField id="specId" name="specId" disabled="true" />
                                  <g:hiddenField name="specimenId" id="specimenIdEd" value="${specimenRecordInstance?.specimenId}" />
                                </td>
                                <td class="value ${hasErrors(bean: specimenRecord, field: 'containerType', 'errors')}">
                                  <g:select id="ctIdEd" disabled="true" name="specimenRecordInstance.containerType.code" from="${bpvContainerList}" optionKey="code" value="${specimenRecordInstance?.containerType?.code}" noSelection="['null': '']" />
                                </td>
                                <td class="value ${hasErrors(bean: specimenRecord, field: 'acquisitionType', 'errors')}">
                                  <g:select id="codeEd" disabled="true" name="specimenRecordInstance.tissueType.code" from="${bpvBloodList}" optionKey="code" value="${specimenRecordInstance?.tissueType?.code}" noSelection="['null': '']" />
                                </td>
                                <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                                  <g:textField id="volumeEd" size="4" name="volume" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                                </td>                               
                              </tr>
                              <tr class="prop">
                                <td colspan="5" class="popupbuttons ui-corner-all">
                                  <g:actionSubmit class="button bleft" action="editParent" value="Update" id="saveBtn4"/> &nbsp;&nbsp;
                                  <g:actionSubmit class="button bright" action="update" value="Cancel" id="cancelButton4" />
                                </td>
                              </tr>
                           </tbody>
                        </table>
                        </g:formRemote>
                  </div>

                  <div id="add-plasma" class="bloodpopups" title="Add Plasma Aliquot">
                    <g:formRemote name="plasmaFrm1" url="[controller: 'bpvBloodForm', action: 'updatePlasmaCh']" update="PlasmaTubes" onComplete="addParentTableWithAliquot('plasma',3);" >                      
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parPlBarCd" value="" />
                      <g:hiddenField name="coniTbCd" value="" />
                      <g:hiddenField name="coniTbVol" value="" />                      
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Plasma Aliquot Specimen barcode ID</th> 
                            <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                              <g:textField name="specimenId" id="specimenIdPl" value="${specimenRecordInstance?.specimenId}" />
                              <g:hiddenField name="specimenRecordInstance.containerType.id" value="3" />
                              <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODPLAS" />
                            </td>
                              </tr><tr>
                            <th class="name">Plasma Aliquot volume</th>  
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                              <g:textField name="volume" size="4" id="volumePl" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                            </td>
                              </tr>
                        <g:if test="${bloodFormVersion > 1}">
                          <tr>
                          <th class="name">Time Placed on Dry Ice</th>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'bloodFrozen', 'errors')}">
                              <g:jqDateTimePicker name="bloodFrozen" LDSOverlay="${bodyclass ?: ''}" value="${chpBloodRecordInstance?.bloodFrozen}" class="timeEntry "/>
                            </td>
                              </tr><tr>
                          <th class="name">Scanned ID of Cryovial when transferred</th>
                            <td class="value" >
                              <g:textField name="scannedId" id="scannedIdPl" value="" />
                            </td>
                              </tr><tr>
                          <th class="name">Time Transferred to Freezer</th>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'bloodStorage', 'errors')}">
                              <g:jqDateTimePicker name="bloodStorage" LDSOverlay="${bodyclass ?: ''}" value="${chpBloodRecordInstance?.bloodStorage}" class="timeEntry "/>
                            </td>
                              </tr><tr>
                          <th class="name">Freezer Type Transferred to</th>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'freezerType', 'errors')}">
                                <g:select id="freezerType" name="freezerType" from="${['-80°C Freezer', 'LN Freezer']}" optionKey="" value="${chpBloodRecordInstance?.freezerType}" noSelection="['': 'Please Select']" />
                            </td>
                              </tr>
                        </g:if>    
                          <tr class="prop">
                             <td colspan="4" class="popupbuttons ui-corner-all">
                               <g:actionSubmit class="button bleft" action="updatePlasmaCh" value="Add New Specimen Barcode ID" id="saveBtn2"/>&nbsp;&nbsp;
                               <g:actionSubmit class="button bright" action="update" value="Exit" id="cancelButton2" />
                             </td>
                           </tr>
                         </tbody>
                       </table>
                    </g:formRemote>
                 </div>      
                 <div id="edit-plasma" class="bloodpopups" title="Edit Plasma Aliquot">
                    <g:formRemote name="plasmaFrm2" url="[controller: 'bpvBloodForm', action: 'editPlasma']" update="PlasmaTubes" onSuccess="uiEditAliquot(3)">
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parPlBarCdEd" value="" />
                      <g:hiddenField name="specPlasmaId" value="" />
                      <g:hiddenField name="coniTbCdEd" value="" />
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Plasma Aliquot Specimen Barcode ID</th> 
                             <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                                <g:textField name="specPlId" id="specPlId" disabled="true" />
                                <g:hiddenField name="specimenId" id="specimenIdPlEd" value="${specimenRecordInstance?.specimenId}" />
                                <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODPLAS" />
                             </td> 
                              </tr><tr>
                            <th class="name">Plasma Aliquot Volume</th>
                             <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                                <g:textField name="volume" size="4" id="volumePlEd" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                             </td>  
                          </tr>
                          <g:if test="${bloodFormVersion > 1}">
                          <tr>
                          <th class="name">Time Placed on Dry Ice</th>
                            <td class="value scanned-2 ${hasErrors(bean: chpBloodRecord, field: 'bloodFrozen', 'errors')}">
                              <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}"  id="bloodFrozenEd" name="bloodFrozen" value="${chpBloodRecordInstance?.bloodFrozen?.format("MM/dd/yyyy HH:mm")}"/>
                            </td>
                              </tr><tr>
                          <th class="name">Scanned ID of Cryovial when transferred</th>
                            <td class="value" >
                              <g:textField name="scannedId" id="scannedIdPlEd" value="" />
                            </td>
                              </tr><tr>
                          <th class="name">Time Transferred to Freezer</th>
                            <td class="value scanned-2 ${hasErrors(bean: chpBloodRecord, field: 'bloodStorage', 'errors')}">
                              <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" id="bloodStorageEd" name="bloodStorage" value="${chpBloodRecordInstance?.bloodStorage?.format("MM/dd/yyyy HH:mm")}"/>
                            </td>
                              </tr><tr>
                          <th class="name">Freezer Type Transferred to</th>
                            <td class="value scanned-2 ${hasErrors(bean: chpBloodRecord, field: 'freezerType', 'errors')}">
                                <g:select id="freezerTypeEd" name="freezerType" from="${['-80°C Freezer', 'LN Freezer']}" optionKey="" value="${chpBloodRecordInstance?.freezerType}" noSelection="['': 'Please Select']" />
                            </td>
                              </tr>
                        </g:if>   
                           <tr class="prop">
                              <td colspan="4" class="popupbuttons ui-corner-all">
                                 <g:actionSubmit class="button bleft" action="editPlasma" value="Update" id="saveBtn5"/>&nbsp;&nbsp;
                                 <g:actionSubmit class="button bright" action="update" value="Cancel" id="cancelButton5" />
                              </td>
                           </tr>
                         </tbody>
                    </table>
                    </g:formRemote>
            </div>      


                  <div id="add-plasmaCT" class="bloodpopups">
                    <g:formRemote name="plasmaFrm3" url="[controller: 'bpvBloodForm', action: 'updatePlasmaConTb']" update="" onComplete="callSaveNContinue();" >
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parPlBarCd" value="" />
                      <g:hiddenField name="coniTbCd" value="" />
                      <g:hiddenField name="coniTbVol" value="" />                      
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Conical Centrifuge tube code</th>  
                            <th class="name">Conical tube volume</th>
                          </tr>
                          <tr class="prop">
                            <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                              <g:textField name="specimenId" id="specimenIdCT" value="${specimenRecordInstance?.specimenId}" />
                              <g:hiddenField name="specimenRecordInstance.containerType.id" value="61" />
                              <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODPLAS" />
                            </td>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                              <g:textField name="volume" size="4" id="volumeCT" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                            </td>
                          </tr>
                          <tr class="prop">
                             <td colspan="4" class="popupbuttons ui-corner-all">
                               <input type="submit" class="button bleft" value="Save" id="saveBtn7" />&nbsp;&nbsp;
                               <input type="button" class="button bright" value="Cancel" id="cancelButton7" />
                             </td>
                           </tr>
                         </tbody>
                       </table>
                    </g:formRemote>
                 </div>      
                 <div id="edit-plasmaCT" class="bloodpopups">
                    <g:formRemote name="plasmaFrm4" url="[controller: 'bpvBloodForm', action: 'editPlasmaCT']" update="" onComplete="callSaveNContinue();">
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parPlBarCdEd" value="" />
                      <g:hiddenField name="specPlasmaCTId" value="" />
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Conical Centrifuge tube code</th>  
                            <th class="name">Conical tube volume</th>
                          </tr>
                          <tr class="prop">
                             <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                                <g:textField name="specCTId" id="specCTId" disabled="true" />
                                <g:hiddenField name="specimenId" id="specimenIdCTEd" value="${specimenRecordInstance?.specimenId}" />
                                <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODPLAS" />
                             </td>
                             <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                                <g:textField name="volume" size="4" id="volumeCTEd" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                             </td>
                           </tr>
                           <tr class="prop">
                              <td colspan="4" class="popupbuttons ui-corner-all">
                                 <g:actionSubmit class="button bleft" action="editPlasmaCT" value="Update" id="saveBtn8"/>&nbsp;&nbsp;
                                 <g:actionSubmit class="button bright" action="update" value="Cancel" id="cancelButton8" />
                              </td>
                           </tr>
                         </tbody>
                    </table>
                    </g:formRemote>
            </div>
        
              <div id="add-serum" class="bloodpopups">
                      <g:formRemote name="serumFrm" url="[controller: 'bpvBloodForm', action: 'updateSerumCh']" update="SerumTubes"  onComplete="addParentTableWithAliquot('serum',2);">                      
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parSrmBarCd" value="" />
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Serum aliquot Specimen barcode ID</th>  
                            <th class="name">Serum aliquot volume</th>  
                          </tr>
                          <tr class="prop">
                            <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                              <g:textField name="specimenId" id="specimenIdSrm" value="${specimenRecordInstance?.specimenId}" />
                              <g:hiddenField name="specimenRecordInstance.containerType.id" value="3" />
                              <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODSRM" />
                            </td>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                              <g:textField name="volume" size="4" id="volumeSrm" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                            </td>
                           </tr>
                           <tr class="prop">
                             <td colspan="4" class="popupbuttons ui-corner-all">
                               <g:actionSubmit class="button bleft" action="updateSerumCh" value="Save" id="saveBtn3"/>&nbsp;&nbsp;
                               <g:actionSubmit class="button bright" action="update" value="Exit" id="cancelButton3" />
                             </td>
                           </tr>
                         </tbody>
                      </table>
                      </g:formRemote>
            </div>           

            <div id="edit-serum" class="bloodpopups">
                      <g:formRemote name="serumFrm" url="[controller: 'bpvBloodForm', action: 'editSerum']" update="SerumTubes" onSuccess="uiEditAliquot(2)">                      
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance.caseRecord?.caseId}" />
                      <g:hiddenField name="parSrmBarCdEd" value="" />
                      <g:hiddenField name="specSerumId" value="" />
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Serum aliquot Specimen barcode ID</th>  
                            <th class="name">Serum aliquot volume</th>  
                          </tr>
                          <tr class="prop">
                            <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                              <g:textField name="specSrmId" id="specSrmId" disabled="true" />
                              <g:hiddenField name="specimenId" id="specimenIdSrmEd" value="${specimenRecordInstance?.specimenId}" />
                              <g:hiddenField name="specimenRecordInstance.containerType.id" value="3" />
                              <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODSRM" />
                            </td>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                              <g:textField name="volume" size="4" id="volumeSrmEd" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                            </td>
                          </tr>
                          <tr class="prop">
                            <td colspan="4" class="popupbuttons ui-corner-all">
                              <g:actionSubmit class="button bleft" action="editSerum" value="Update" id="saveBtn6"/>&nbsp;&nbsp;
                              <g:actionSubmit class="button bright" action="update" value="Cancel" id="cancelButton6" />
                            </td>
                          </tr>
                        </tbody>
                     </table>
                     </g:formRemote>
            </div>

                  <div id="add-wcp" class="bloodpopups">
                    <g:formRemote name="wcpFrm1" url="[controller: 'bpvBloodForm', action: 'updateWholeCellPelletCh']" update="WcpTubes" onComplete="addParentTableWithAliquot('wcp',2);" >
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parPlBarCd" value="" />
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Whole Cell Pellet aliquot Specimen barcode ID</th>  
                            <th class="name">Whole Cell Pellet aliquot volume</th>  
                          </tr>
                          <tr class="prop">
                            <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                              <g:textField name="specimenId" id="specimenIdWcp" value="${specimenRecordInstance?.specimenId}" />
                              <g:hiddenField name="specimenRecordInstance.containerType.id" value="3" />
                              <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODCP" />
                            </td>
                            <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                              <g:textField name="volume" size="4" id="volumeWcp" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                            </td>
                          </tr>
                          <tr class="prop">
                             <td colspan="4" class="popupbuttons ui-corner-all">
                               <g:actionSubmit class="button bleft" action="updateWholeCellPelletCh" value="Save" id="saveBtn9"/>&nbsp;&nbsp;
                               <g:actionSubmit class="button bright" action="update" value="Exit" id="cancelButton9" />
                             </td>
                           </tr>
                         </tbody>
                       </table>
                    </g:formRemote>
                 </div>      
                 <div id="edit-wcp" class="bloodpopups">
                    <g:formRemote name="wcpFrm2" url="[controller: 'bpvBloodForm', action: 'editWholeCellPellet']" update="WcpTubes" onSuccess="uiEditAliquot(2)">
                      <g:hiddenField name="caseId" value="${bpvBloodFormInstance?.caseRecord?.caseId}" />
                      <g:hiddenField name="parPlBarCdEd" value="" />
                      <g:hiddenField name="specWCId" value="" />
                      <g:hiddenField name="id" value="${bpvBloodFormInstance?.id}" />
                      <table>
                        <tbody>
                          <tr>
                            <th class="name">Whole Cell Pellet Aliquot Specimen Barcode ID</th>  
                            <th class="name">Whole Cell Pellet Aliquot Volume</th>
                          </tr>
                          <tr class="prop">
                             <td class="value ${hasErrors(bean: specimenRecord, field: 'specimenId', 'errors')}">
                                <g:textField name="specWcpId" id="specWcpId" disabled="true" />
                                <g:hiddenField name="specimenId" id="specimenIdWcpEd" value="${specimenRecordInstance?.specimenId}" />
                                <g:hiddenField name="specimenRecordInstance.tissueType.code" value="BLOODCP" />
                             </td>
                             <td class="value ${hasErrors(bean: chpBloodRecord, field: 'volume', 'errors')}">
                                <g:textField name="volume" size="4" id="volumeWcpEd" value="${chpBloodRecordInstance?.volume}" onkeyup="isVolumeValid(this)"/> ml
                             </td>
                           </tr>
                           <tr class="prop">
                              <td colspan="4" class="popupbuttons ui-corner-all">
                                 <g:actionSubmit class="button bleft" action="editWholeCellPellet" value="Update" id="saveBtn10"/>&nbsp;&nbsp;
                                 <g:actionSubmit class="button bright" action="update" value="Cancel" id="cancelButton10" />
                              </td>
                           </tr>
                         </tbody>
                    </table>
                    </g:formRemote>
            </div>      

            <div id="dialog-confirm" title="Clear Previously Related Entries?">
              <p><span class="ui-icon ui-icon-alert"></span>Data entries related to processing with the previous bar code will be cleared and cannot be recovered once the values are saved.</p>
            </div>

