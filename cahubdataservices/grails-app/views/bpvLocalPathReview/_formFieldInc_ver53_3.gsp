                      
                                <tr class="prop">
                                    <td class="name" >
                                      <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">${seq++}. Pathologic Tumor Stage Grouping (FIGO):</g:if>
                                      <g:else>${seq++}. Pathologic Tumor Stage Group  (AJCC 7th Edition):</g:else>
                                    </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('pathStage')}" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                            <div>
                                                <g:radio name="pathStage" id='pathStage1' value="Stage 1" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 1'}"/>&nbsp;<label for="pathStage1">Stage 1</label><br/>
                                                <g:radio name="pathStage" id='pathStage2' value="Stage 1A" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 1A'}"/>&nbsp;<label for="pathStage2">Stage 1A</label><br/>
                                                <g:radio name="pathStage" id='pathStage3' value="Stage 1B" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 1B'}"/>&nbsp;<label for="pathStage3">Stage 1B</label><br/>
                                                <g:radio name="pathStage" id='pathStage4' value="Stage 1C" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 1C'}"/>&nbsp;<label for="pathStage4">Stage 1C</label><br/>
                                                <g:radio name="pathStage" id='pathStage5' value="Stage 2" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 2'}"/>&nbsp;<label for="pathStage5">Stage 2</label><br/>
                                                <g:radio name="pathStage" id='pathStage6' value="Stage 2A" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 2A'}"/>&nbsp;<label for="pathStage6">Stage 2A</label><br/>
                                                <g:radio name="pathStage" id='pathStage7' value="Stage 2B" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 2B'}"/>&nbsp;<label for="pathStage7">Stage 2B</label><br/>
                                                <g:radio name="pathStage" id='pathStage8' value="Stage 2C" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 2C'}"/>&nbsp;<label for="pathStage8">Stage 2C</label><br/>
                                                <g:radio name="pathStage" id='pathStage9' value="Stage 3" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 3'}"/>&nbsp;<label for="pathStage9">Stage 3</label><br/>
                                                <g:radio name="pathStage" id='pathStage10' value="Stage 3A" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 3A'}"/>&nbsp;<label for="pathStage10">Stage 3A</label><br/>
                                                <g:radio name="pathStage" id='pathStage11' value="Stage 3B" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 3B'}"/>&nbsp;<label for="pathStage11">Stage 3B</label><br/>
                                                <g:radio name="pathStage" id='pathStage12' value="Stage 3C" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 3C'}"/>&nbsp;<label for="pathStage12">Stage 3C</label><br/>
                                                <g:radio name="pathStage" id='pathStage13' value="Stage 4" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 4'}"/>&nbsp;<label for="pathStage13">Stage 4</label><br/>
                                                <g:radio name="pathStage" id='pathStage14' value="Stage Unknown" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage Unknown'}"/>&nbsp;<label for="pathStage14">Stage Unknown</label>
                                            </div>
                                        </g:if>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            <div>
                                                <g:radio name="pathStage" id='pathStage1' value="Stage I" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage I'}"/>&nbsp;<label for="pathStage1">Stage I</label><br/>
                                                <g:radio name="pathStage" id='pathStage2' value="Stage II" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage II'}"/>&nbsp;<label for="pathStage2">Stage II</label><br/>
                                                <g:radio name="pathStage" id='pathStage3' value="Stage III" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage III'}"/>&nbsp;<label for="pathStage3">Stage III</label><br/>
                                                <g:radio name="pathStage" id='pathStage4' value="Stage IV" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IV'}"/>&nbsp;<label for="pathStage4">Stage IV</label>
                                            </div>
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                            <div>
                                                <g:radio name="pathStage" id='pathStage1' value="Stage 0" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 0'}"/>&nbsp;<label for="pathStage1">Stage 0</label><br/>
                                                <g:radio name="pathStage" id='pathStage2' value="Stage I" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage I'}"/>&nbsp;<label for="pathStage2">Stage I</label><br/>
                                                <g:radio name="pathStage" id='pathStage3' value="Stage IIA" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIA'}"/>&nbsp;<label for="pathStage3">Stage IIA</label><br/>
                                                <g:radio name="pathStage" id='pathStage4' value="Stage IIB" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIB'}"/>&nbsp;<label for="pathStage4">Stage IIB</label><br/>
                                                <g:radio name="pathStage" id='pathStage5' value="Stage IIC" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIC'}"/>&nbsp;<label for="pathStage5">Stage IIC</label><br/>
                                                <g:radio name="pathStage" id='pathStage6' value="Stage IIIA" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIIA'}"/>&nbsp;<label for="pathStage6">Stage IIIA</label><br/>
                                                <g:radio name="pathStage" id='pathStage7' value="Stage IIIB" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIIB'}"/>&nbsp;<label for="pathStage7">Stage IIIB</label><br/>
                                                <g:radio name="pathStage" id='pathStage8' value="Stage IIIC" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIIC'}"/>&nbsp;<label for="pathStage8">Stage IIIC</label><br/>
                                                <g:radio name="pathStage" id='pathStage9' value="Stage IVA" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IVA'}"/>&nbsp;<label for="pathStage9">Stage IVA</label><br/>
                                                <g:radio name="pathStage" id='pathStage10' value="Stage IVB" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IVB'}"/>&nbsp;<label for="pathStage10">Stage IVB</label>
                                            </div>
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                            <div>
                                                <g:radio name="pathStage" id='pathStage1' value="Occult carcinoma" checked="${bpvLocalPathReviewInstance?.pathStage =='Occult carcinoma'}"/>&nbsp;<label for="pathStage1">Occult carcinoma</label><br/>
                                                <g:radio name="pathStage" id='pathStage2' value="Stage 0" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage 0'}"/>&nbsp;<label for="pathStage2">Stage 0</label><br/>
                                                <g:radio name="pathStage" id='pathStage3' value="Stage IA" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IA'}"/>&nbsp;<label for="pathStage3">Stage IA</label><br/>
                                                <g:radio name="pathStage" id='pathStage4' value="Stage IB" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IB'}"/>&nbsp;<label for="pathStage4">Stage IB</label><br/>
                                                <g:radio name="pathStage" id='pathStage5' value="Stage IIA" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIA'}"/>&nbsp;<label for="pathStage5">Stage IIA</label><br/>
                                                <g:radio name="pathStage" id='pathStage6' value="Stage IIB" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIB'}"/>&nbsp;<label for="pathStage6">Stage IIB</label><br/>
                                                <g:radio name="pathStage" id='pathStage7' value="Stage IIIA" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIIA'}"/>&nbsp;<label for="pathStage7">Stage IIIA</label><br/>
                                                <g:radio name="pathStage" id='pathStage8' value="Stage IIIB" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IIIB'}"/>&nbsp;<label for="pathStage8">Stage IIIB</label><br/>
                                                <g:radio name="pathStage" id='pathStage9' value="Stage IV" checked="${bpvLocalPathReviewInstance?.pathStage =='Stage IV'}"/>&nbsp;<label for="pathStage9">Stage IV</label>
                                            </div>
                                        </g:elseif>
                                    </td>
                                </tr>
                      
                                <tr class="prop clearborder">
                                    <td class="name" >
                                        ${seq++}. Did pathology review of the H&E slide derived from QC FFPE tumor tissue confirm the histological type to be eligible for BPV study?  
                                    </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('histoEligible')}">
                                        <div>
                                            <g:radio name="histoEligible" id='histo1' value="Yes" checked="${bpvLocalPathReviewInstance?.histoEligible =='Yes'}"/>&nbsp;<label for="histo1">Yes</label><br/>
                                            <g:radio name="histoEligible" id='histo2' value="No" checked="${bpvLocalPathReviewInstance?.histoEligible =='No'}"/>&nbsp;<label for="histo2">No</label><br/>
                                        </div>
                                    </td>
                                </tr>

                                
                                <tr class="prop clearborder">
                                    <td class="name" >
                                        ${seq++}. This slide meets the microscopic analysis criteria of the BPV project of necrosis percentage of <20% and tumor content of &ge;50% tumor nuclei:
                                        <span data-msg="If <b>No</b> is selected, specify what findings do not meet the microscopic analysis criteria of the BPV project" class="cahub-tooltip"></span>
                                    </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('meetsCriteria')}">
                                        <div>
                                            <g:radio name="meetsCriteria" id='m1' value="Yes" checked="${bpvLocalPathReviewInstance?.meetsCriteria =='Yes'}"/>&nbsp;<label for="m1">Yes</label><br/>
                                            <g:radio name="meetsCriteria" id='m2' value="No" checked="${bpvLocalPathReviewInstance?.meetsCriteria =='No'}"/>&nbsp;<label for="m2">No</label><br/>
                                        </div>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td class="name" colspan="2">
                                        <g:if test="${bpvLocalPathReviewInstance?.meetsCriteria == 'No'}">
                                            <span class="subentry value ${ (errorMap == null) ? '' : errorMap.get('reasonNotMeet')}" id="f" style="display:display">Specify findings: <br/><g:textArea class="textwide" name="reasonNotMeet" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.reasonNotMeet}" id="re" /></span>
                                        </g:if>
                                        <g:else>
                                            <span id="f" style="display:none" class="subentry">Specify findings: <br/><g:textArea class="textwide" name="reasonNotMeet" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.reasonNotMeet}" id="re" /></span>
                                        </g:else>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td class="name" colspan="2">
                                        ${seq++}. Pathology review comments:<br />
                                        <g:textArea class="textwide" name="pathologyComments" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.pathologyComments}" />
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td colspan="2" class="formheader">Concordance with Diagnostic Pathology Report</td>
                                </tr>

                                <tr class="prop clearborder">
                                    <td class="name" >
                                        ${seq++}. This slide is consistent with the findings of the Diagnostic Pathology Report for this case:
                                        <span data-msg="If <b>No</b> is selected, specify what findings are not consistent with the Diagnostic Pathology Report" class="cahub-tooltip"></span>
                                    </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('consistentLocalPrc')}" >
                                        <div>
                                            <g:radio name="consistentLocalPrc" id='c1' value="Yes" checked="${bpvLocalPathReviewInstance?.consistentLocalPrc =='Yes'}"/>&nbsp;<label for="c1">Yes</label><br/>
                                            <g:radio name="consistentLocalPrc" id='c2' value="No" checked="${bpvLocalPathReviewInstance?.consistentLocalPrc =='No'}"/>&nbsp;<label for="c2">No</label><br/>
                                        </div>
                                    </td>
                                </tr>
                                
                                <tr class="prop">
                                    <td class="name" colspan="2">
                                        <g:if test="${bpvLocalPathReviewInstance?.consistentLocalPrc == 'No'}">
                                            <span class="subentry value ${ (errorMap == null) ? '' : errorMap.get('reasonNotCons')}" id="f2" style="display:display">Specify findings: <br/><g:textArea class="textwide" name="reasonNotCons" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.reasonNotCons}" id="re2" /></span>
                                        </g:if>
                                        <g:else>
                                            <span id="f2" style="display:none" class="subentry">Specify findings: <br/><g:textArea class="textwide" name="reasonNotCons" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.reasonNotCons}" id="re2" /></span>
                                        </g:else>
                                    </td>
                                </tr>
                                
                                <tr class="prop">
                                    <td class="name" >
                                        ${seq++}. Name of local BSS reviewing pathologist:
                                        <span data-msg="In the format of <b>First Initial, Last Name</b>, please indicate the <b>Name</b> of local BSS reviewing pathologist" class="cahub-tooltip"></span>
                                    </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('pathologistName')}" > 
                                        <g:textField name="pathologistName" value="${bpvLocalPathReviewInstance?.pathologistName}" />
                                    </td>
                                </tr>
                                
                                <tr class="prop">
                                    <td class="name" >
                                        ${seq++}. Date of Slide review by the pathologist:
                                    </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('dateSlideReview')}" > 
                                        <g:jqDatePicker name="dateSlideReview" value="${bpvLocalPathReviewInstance?.dateSlideReview}" />
                                    </td>
                                </tr>
                                
                                <tr class="prop">
                                    <td class="name" >
                                        ${seq++}. Data entry in the Local Pathology Review form was performed by:
                                          </td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('dataEnteredBy')}" > 
                                        <g:textField name="dataEnteredBy" value="${bpvLocalPathReviewInstance?.dataEnteredBy}" />
                                    </td>
                                </tr>
