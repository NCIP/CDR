<div id="tabs-wholeblood" class="tabbed-content">
  <h3>Mandatory: Whole Cell Pellet - (3) 1.0 ml Aliquots</h3>
      <table class="tab-table  dec12table">
                            <tr><td colspan="2" class="formheader">Whole Cell Pellet Information</td></tr>
                            <tr class="prop">
                                <td class="name"><label for="plasmaParBarcode">EDTA collection tube Specimen barcode ID:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaParBarcode', 'errors')}">
                                  <g:set var="plasmaPBCode" value="" />
                                    <div id="wcpParBarcodeDisp">${bpvBloodFormInstance?.plasmaParBarcode}</div>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td class="name"><label for="plasmaRemainingVol">${labelNumber}a. Volume of the Whole Cell Pellet remaining after plasma removed:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'plasmaRemainingVol', 'errors')}">
                                    <g:textField class="numFloat" size="4" name="plasmaRemainingVol" value="${bpvBloodFormInstance?.plasmaRemainingVol}" /> ml
                                </td>
                            </tr>                            
                            <tr class="prop subentry clearborder wcpBarcodeGroup">
                                <td class="name ui-state-default ${warningMap?.get('wcpTubes') ? 'warnings' : ''}" colspan="2"><h4>${labelNumber++}b. Aliquot details: Enter information for each Aliquot derived from EDTA collection tube</h4>   
                                <div id="WcpTubes"><g:render template="wcpTable"/></div>
                                </td></tr>           
                            <tr id="add4Row" class="subentry"><td colspan="2"><button class="Btn wcpBarcodeGroup" id="addWcpBtn">Add</button></td></tr>
                            
                            <tr class="prop">
                                <td class="name"><label for="wholeBloodProcEnd">${labelNumber++}. Time Whole Cell Pellet Aliquots processing was completed:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodProcEnd', 'errors')} ${warningMap?.get('wholeBloodProcEnd') ? 'warnings' : ''}"> 
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="wholeBloodProcEnd" value="${bpvBloodFormInstance?.wholeBloodProcEnd}" />
                                </td>
                            </tr>                            
                            <tr class="prop">
                                <td class="name"><label for="wholeBloodProcFrozen">${labelNumber++}. Time Whole Cell Pellet Aliquots were frozen (-80°C):</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodProcFrozen', 'errors')} ${warningMap?.get('wholeBloodProcFrozen') ? 'warnings' : ''}"> 
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="wholeBloodProcFrozen" value="${bpvBloodFormInstance?.wholeBloodProcFrozen}" />
                                </td>
                            </tr>
                            <tr class="prop">           
                                <td class="name"><label for="wholeBloodProcStorage">${labelNumber++}. Time Whole Cell Pellet Aliquots were transferred to storage:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodProcStorage', 'errors')}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="wholeBloodProcStorage" value="${bpvBloodFormInstance?.wholeBloodProcStorage}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td class="name"><label for="wholeBloodProcTech">${labelNumber++}. Whole Cell Pellet Aliquots were processed by:</label></td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodProcTech', 'errors')}">
                                    <g:textField name="wholeBloodProcTech" value="${bpvBloodFormInstance?.wholeBloodProcTech}" />
                                </td>
                            </tr>
           
                            
                            <tr><td colspan="2" class="formheader">Note deviations from SOP, processing or storage issues</td></tr>
                        
                            <tr class="prop">
                                <td class="name">
                                  <label for="wholeBloodProcSopDev">${labelNumber++}. Whole Cell Pellet processing was performed in accordance with the specified SOP:</label>
                               </td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodProcSopDev', 'errors')}">
                                    <g:bpvYesNoRadioPicker checked="${bpvBloodFormInstance?.wholeBloodProcSopDev}"  name="wholeBloodProcSopDev" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td colspan="2" class="name tdtop ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodProcComments', 'errors')}">
                                  <label for="wholeBloodProcComments">${labelNumber++}. Whole Cell Pellet processing comments:</label><br />
                                    <g:textArea class="textwide" name="wholeBloodProcComments" value="${bpvBloodFormInstance?.wholeBloodProcComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td colspan="2" class="name tdtop ${hasErrors(bean: bpvBloodFormInstance, field: 'wholeBloodStorageIssues', 'errors')}">
                                    <label for="wholeBloodStorageIssues">${labelNumber++}. Whole Cell Pellet storage issues:</label><br />
                                    <g:textArea class="textwide" name="wholeBloodStorageIssues" value="${bpvBloodFormInstance?.wholeBloodStorageIssues}" />
                                </td>
                            </tr>
                            
                  </table>
                </div>  
