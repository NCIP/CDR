
                <div id="tabs-dna" class="tabbed-content">
                  <g:if test="${bloodFormVersion > 1}">
                    <h3>Optional: (1) DNA PAXgene tube (minimum of 4.0 ml of blood required)</h3>
                  </g:if>
                  <g:else>
                    <h3>DESIRABLE: (1) 8.5 ml DNA PAXgene tube</h3>
                  </g:else>
                  <table class="tab-table  dec12table">
                    <tr><td colspan="2" class="formheader">DNA Information</td></tr>
                    <tr class="prop">
                      <td class="name"><label for="dnaParBarCode">${labelNumber++}. DNA PAXgene tube Specimen barcode ID:</label></td>
                      <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaParBarCode', 'errors')}">
                      <g:hiddenField name="dnaParBarCode"  value="${bpvBloodFormInstance?.dnaParBarCode}" />
                      <input type="text" class="showonly" id="pageloaddnaParBarCode" name="pageloaddnaParBarCode"  value="${bpvBloodFormInstance?.dnaParBarCode}" />
                        <div id="dnaParBarcodeDisp" class="barCodeSelect"></div>
                      </td>
                    </tr><tr class="prop">
                      <td class="name"><label for="dnaPaxFrozen">${labelNumber++}. Time DNA PAXgene tube was frozen at -20˚C ± 2˚C:</label></td>
                      <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaPaxFrozen', 'errors')} ${warningMap?.get('dnaPaxFrozen') ? 'warnings' : ''}"><g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dnaPaxFrozen" value="${bpvBloodFormInstance?.dnaPaxFrozen}" /></td>
                    </tr><tr class="prop">
                       <td class="name"><label for="dnaPaxStorage">${labelNumber++}. Time DNA PAXgene tube was transferred to storage at -75˚C ± 5˚C:</label></td>
                       <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaPaxStorage', 'errors')}" ><g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dnaPaxStorage" value="${bpvBloodFormInstance?.dnaPaxStorage}" /></td>
                    </tr><tr class="prop">
                       <td class="name"><label for="dnaPaxProcTech">${labelNumber++}. DNA PAXgene tube was stored by:</label></td>
                       <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaPaxProcTech', 'errors')}"><g:textField name="dnaPaxProcTech" value="${bpvBloodFormInstance?.dnaPaxProcTech}" /></td>
                    </tr>
                    <tr><td colspan="2" class="formheader">Note deviations from SOP, processing or storage issues</td></tr>
                    <tr class="prop">
                       <td class="name"><label for="dnaPaxProcSopDev">${labelNumber++}. DNA PAXgene tube was collected and stored in accordance with the specified SOP:</label></td>
                       <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaPaxProcSopDev', 'errors')}"><g:bpvYesNoRadioPicker checked="${(bpvBloodFormInstance?.dnaPaxProcSopDev)}"  name="dnaPaxProcSopDev" /></td>
                    </tr>
                    <tr class="prop">
                       <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaPaxProcComments', 'errors')}">
                         <label for="dnaPaxProcComments">${labelNumber++}. DNA PAXgene tube collection comments:</label><br />
                         <g:textArea class="textwide" name="dnaPaxProcComments" value="${bpvBloodFormInstance?.dnaPaxProcComments}" />
                        </td>
                    </tr>
                    <tr class="prop">
                       <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'dnaPaxStorageIssues', 'errors')}">
                          <label for="dnaPaxStorageIssues">${labelNumber++}. DNA PAXgene tube storage comments:</label><br />
                          <g:textArea class="textwide" name="dnaPaxStorageIssues" value="${bpvBloodFormInstance?.dnaPaxStorageIssues}" />
                        </td>
                     </tr>
                  </table>
              </div>
