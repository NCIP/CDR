<div id="tabs-serum" class="tabbed-content">
      <h3>DESIRABLE: Serum Aliquots - (3) 0.5 ml aliquots are desirable</h3> 
      <table class="tab-table  dec12table">
                            <tr><td colspan="2" class="formheader">Serum Separator Tube (SST) Information</td></tr>  
                            <tr class="prop">
                                <td class="name"><label for="serumParBarcode">${labelNumber++}. Serum Separator Tube barcode:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumParBarcode', 'errors')}">              
                                   <g:set var="srmPBCode" value="" />
                                      <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                        <g:if test="${specimen.parentSpecimen == null}">
                                          <g:if test="${specimen.tissueType.code == 'BLOODSRM'}">
                                            <g:if test="${specimen.containerType.code == 'SST'}">
                                               <g:set var="srmPBCode" value="${specimen.specimenId}" />
                                            </g:if> 
                                          </g:if>
                                      </g:if>
                                    </g:each>

                              <g:hiddenField name="serumParBarCode" value="${srmPBCode}" />
                              <input type="text" class="showonly" id="pageloadserumParBarCode" name="pageloadserumParBarCode"  value="${srmPBCode}" />
                                    <div id="serumParBarcodeDisp" class="barCodeSelect">${srmPBCode}</div>
                                </td>
                            </tr>
                            <tr class="prop clearborder">           
                                <td class="name"><label for="serumProcStart">${labelNumber++}. Time Serum processing began:</td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcStart', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="serumProcStart" precision="day" value="${bpvBloodFormInstance?.serumProcStart}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                            <tr class="prop subentry clearborder serumBarcodeGroup">
                                <td class="name" colspan="2">Aliquot details: Enter information for each aliquot derived from SST collection tube</td>   
                            </tr>
                            <tr class="subentry clearborder serumBarcodeGroup">
                              <td colspan="2">
                                <div id="SerumTubes"><g:render template="serumTable"/></div>
                                </td></tr>           
                            <tr id="add3Row" class="subentry"><td colspan="2"><button class="Btn serumBarcodeGroup" id="addSerumBtn">Add</button></td></tr>
                            <tr class="prop">
                                <td class="name"><label for="serumProcEnd">${labelNumber++}. Time Serum aliquot processing was completed:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcEnd', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="serumProcEnd" value="${bpvBloodFormInstance?.serumProcEnd}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td class="name"><label for="serumProcFrozen">${labelNumber++}. Time Serum aliquots were frozen:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcFrozen', 'errors')} ${warningMap?.get('serumProcFrozen') ? 'warnings' : ''}"> 
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="serumProcFrozen" value="${bpvBloodFormInstance?.serumProcFrozen}" />
                                </td>
                            </tr>
                            <tr class="prop">           
                                <td class="name"><label for="serumProcStorage">${labelNumber++}. Time Serum aliquots were transferred to storage:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcStorage', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="serumProcStorage" value="${bpvBloodFormInstance?.serumProcStorage}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td class="name"><label for="serumProcTech">${labelNumber++}. Serum aliquots were processed by:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcTech', 'errors')}">
                                    <g:textField name="serumProcTech" value="${bpvBloodFormInstance?.serumProcTech}" />
                                </td>
                            </tr>
           
                            
                            <tr><td colspan="2" class="formheader">Note deviations from SOP, processing or storage issues</td></tr>
                        
                            <tr class="prop">
                                <td class="name">
                                  <label for="serumProcSopDev">${labelNumber++}. Serum processing was performed in accordance with the specified SOP:</label>
                               </td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcSopDev', 'errors')}">
                                    <g:bpvYesNoRadioPicker checked="${(bpvBloodFormInstance?.serumProcSopDev)}"  name="serumProcSopDev" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td colspan="2" class="name tdtop ${hasErrors(bean: bpvBloodFormInstance, field: 'serumProcComments', 'errors')}">
                                  <label for="serumProcComments">${labelNumber++}. Serum processing comments:</label><br />
                                    <g:textArea class="textwide" name="serumProcComments" value="${bpvBloodFormInstance?.serumProcComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td colspan="2" class="name tdtop ${hasErrors(bean: bpvBloodFormInstance, field: 'serumStorageIssues', 'errors')}">
                                    <label for="serumStorageIssues">${labelNumber++}. Serum storage issues:</label><br />
                                    <g:textArea class="textwide" name="serumStorageIssues" value="${bpvBloodFormInstance?.serumStorageIssues}" />
                                </td>
                            </tr>
                            
                  </table>
                </div>  
