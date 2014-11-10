              <div id="tabs-rna" class="tabbed-content">
                  <g:if test="${bloodFormVersion > 1}">
                    <h3>Optional(1) RNA PAXgene tube (minimum of 1.0 ml of blood required)</h3>
                  </g:if>
                  <g:else>
                        <h3>DESIRABLE: (1) 2.5 ml RNA PAXgene tube</h3>
                  </g:else>              
              <table class="tab-table  dec12table">
                <tr><td colspan="2" class="formheader">RNA Information</td></tr>
                <tr class="prop">
                  <td class="name"><label for="rnaParBarCode">${labelNumber++}. RNA PAXgene tube Specimen barcode ID:</label></td>
                  <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaParBarCode', 'errors')}">
                    <g:hiddenField name="rnaParBarCode"  value="${bpvBloodFormInstance?.rnaParBarCode}" />
                    <input type="text" class="showonly" id="pageloadrnaParBarCode" name="pageloadrnaParBarCode"  value="${bpvBloodFormInstance?.rnaParBarCode}" />
                      <div id="rnaParBarcodeDisp" class="barCodeSelect"></div>
                  </td>
                </tr>
                <tr class="prop">
                  <td class="name"><label for="rnaPaxFrozen">${labelNumber++}. Time RNA PAXgene tube was frozen at -20˚C ± 2˚C:</label></td>
                  <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaPaxFrozen', 'errors')} ${warningMap?.get('rnaPaxFrozen') ? 'warnings' : ''}">
                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="rnaPaxFrozen" value="${bpvBloodFormInstance?.rnaPaxFrozen}" class="timeEntry" />
                  </td>
                            </tr>
                            <tr class="prop">
                                <td class="name">
                                    <label for="rnaPaxStorage">${labelNumber++}. Time RNA PAXgene tube was transferred to storage at -75˚C ± 5˚C:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaPaxStorage', 'errors')} ${warningMap?.get('rnaPaxStorage') ? 'warnings' : ''}">
                                    <g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="rnaPaxStorage" value="${bpvBloodFormInstance?.rnaPaxStorage}" class="timeEntry" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td class="name">
                                    <label for="rnaPaxProcTech">${labelNumber++}. RNA PAXgene tube was stored by:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaPaxProcTech', 'errors')}">
                                    <g:textField name="rnaPaxProcTech" value="${bpvBloodFormInstance?.rnaPaxProcTech}" />
                                </td>
                            </tr>
           
                        <tr><td colspan="2" class="formheader">Note deviations from SOP, processing or storage issues</td></tr>
                            <tr class="prop">
                                <td class="name">
                                    <label for="rnaPaxProcSopDev">${labelNumber++}. RNA PAXgene tube was collected and stored in accordance with the specified SOP:</label>
                                </td>
                                <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaPaxProcSopDev', 'errors')}">
                                    <g:bpvYesNoRadioPicker checked="${(bpvBloodFormInstance?.rnaPaxProcSopDev)}"  name="rnaPaxProcSopDev" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaPaxProcComments', 'errors')}">
                                    <label for="rnaPaxProcComments">${labelNumber++}. RNA PAXgene tube collection comments:</label><br />
                                    <g:textArea class="textwide" name="rnaPaxProcComments" value="${bpvBloodFormInstance?.rnaPaxProcComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaPaxStorageIssues', 'errors')}">
                                    <label for="rnaPaxStorageIssues">${labelNumber++}. RNA PAXgene tube storage comments:</label><br />
                                    <g:textArea class="textwide" name="rnaPaxStorageIssues" value="${bpvBloodFormInstance?.rnaPaxStorageIssues}" />
                                </td>
                            </tr>
      </table>
    </div>
