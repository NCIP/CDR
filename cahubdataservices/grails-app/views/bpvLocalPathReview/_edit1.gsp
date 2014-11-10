                                <tr class="prop">
                                    <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                        <td colspan="2" class="formheader">Ovarian Slide Pathology Review</td>
                                    </g:if>
                                    <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                        <td colspan="2" class="formheader">Kidney Slide Pathology Review</td>
                                    </g:elseif>
                                    <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                        <td colspan="2" class="formheader">Colon Slide Pathology Review</td>
                                    </g:elseif>
                                    <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                        <td colspan="2" class="formheader">Lung Slide Pathology Review</td>
                                    </g:elseif>
                                </tr>

                                <tr class="prop">
                                    <td class="name" >1. Slide ID examined by pathologist:</td>
                                    <td class="value" >
                                        <g:if test="${session.org.code == 'OBBR'}">
                                            <g:link controller="slideRecord" action="show" id="${bpvLocalPathReviewInstance?.slideRecord?.id}">${bpvLocalPathReviewInstance?.slideRecord?.slideId},</g:link>&nbsp;${bpvLocalPathReviewInstance?.slideRecord?.module}
                                        </g:if>
                                        <g:else>
                                            ${bpvLocalPathReviewInstance?.slideRecord?.slideId},&nbsp;${bpvLocalPathReviewInstance?.slideRecord?.module}
                                        </g:else>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td class="name" >2. Parent Specimen ID of the sample from which this slide was derived:</td>
                                    <td class="value" >
                                        ${bpvLocalPathReviewInstance?.slideRecord?.specimenRecord?.specimenId}
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td class="name ${errorMap.get('organOrigin')}" colspan="2">
                                        3. Organ of origin:
                                        <span data-msg="If <b>Other</b> was selected, record other organ of origin" class="cahub-tooltip"></span>
                                        <div class="subentry value formvaluediv">
                                            <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                                <g:radio name="organOrigin" id='o1' value="Ovary" checked="${bpvLocalPathReviewInstance?.organOrigin =='Ovary'}"/>&nbsp;<label for="o1">Ovary</label><br/>
                                                <g:radio name="organOrigin" id='o2' value="Peritoneum" checked="${bpvLocalPathReviewInstance?.organOrigin =='Peritoneum'}"/>&nbsp;<label for="o2">Peritoneum</label><br/>
                                                <g:radio name="organOrigin" id='o4' value="Fallopian tube" checked="${bpvLocalPathReviewInstance?.organOrigin =='Fallopian tube'}"/>&nbsp;<label for="o4">Fallopian tube</label><br/>
                                                <g:radio name="organOrigin" id='o3' value="Indeterminate" checked="${bpvLocalPathReviewInstance?.organOrigin =='Indeterminate'}"/>&nbsp;<label for="o3">Indeterminate</label><br/>
                                            </g:if>
                                            <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                                <g:radio name="organOrigin" id='o1' value="Kidney" checked="${bpvLocalPathReviewInstance?.organOrigin =='Kidney'}"/>&nbsp;<label for="o1">Kidney</label><br/>
                                            </g:elseif>
                                            <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                                <g:radio name="organOrigin" id='o1' value="Cecum" checked="${bpvLocalPathReviewInstance?.organOrigin =='Cecum'}"/>&nbsp;<label for="o1">Cecum</label><br/>
                                                <g:radio name="organOrigin" id='o2' value="Colon, ascending" checked="${bpvLocalPathReviewInstance?.organOrigin =='Colon, ascending'}"/>&nbsp;<label for="o2">Colon, ascending</label><br/>
                                                <g:radio name="organOrigin" id='o3' value="Colon, descending" checked="${bpvLocalPathReviewInstance?.organOrigin =='Colon, descending'}"/>&nbsp;<label for="o3">Colon, descending</label><br/>
                                                <g:radio name="organOrigin" id='o4' value="Colon, sigmoid" checked="${bpvLocalPathReviewInstance?.organOrigin =='Colon, sigmoid'}"/>&nbsp;<label for="o4">Colon, sigmoid</label><br/>
                                                <g:radio name="organOrigin" id='o5' value="Colon, transverse" checked="${bpvLocalPathReviewInstance?.organOrigin =='Colon, transverse'}"/>&nbsp;<label for="o5">Colon, transverse</label><br/>
                                                <g:radio name="organOrigin" id='o6' value="Hepatic flexure" checked="${bpvLocalPathReviewInstance?.organOrigin =='Hepatic flexure'}"/>&nbsp;<label for="o6">Hepatic flexure</label><br/>
                                                <g:radio name="organOrigin" id='o7' value="Rectosigmoid junction" checked="${bpvLocalPathReviewInstance?.organOrigin =='Rectosigmoid junction'}"/>&nbsp;<label for="o7">Rectosigmoid junction</label><br/>
                                                <g:radio name="organOrigin" id='o8' value="Rectum" checked="${bpvLocalPathReviewInstance?.organOrigin =='Rectum'}"/>&nbsp;<label for="o8">Rectum</label><br/>
                                                <g:radio name="organOrigin" id='o9' value="Splenic flexure" checked="${bpvLocalPathReviewInstance?.organOrigin =='Splenic flexure'}"/>&nbsp;<label for="o9">Splenic flexure</label><br/>
                                            </g:elseif>
                                            <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                                <g:radio name="organOrigin" id='o1' value="Lung" checked="${bpvLocalPathReviewInstance?.organOrigin =='Lung'}"/>&nbsp;<label for="o1">Lung</label><br/>
                                            </g:elseif>
                                            <g:radio name="organOrigin" id='o10' value="Other" checked="${bpvLocalPathReviewInstance?.organOrigin =='Other'}"/>&nbsp;<label for="o10">Other</label>
                                        </div>
                                        <g:if test="${bpvLocalPathReviewInstance?.organOrigin == 'Other'}">
                                            <span id="oo" style="display: block" class="value ${errorMap.get('otherOrganOrigin')}"><br/>Specify other organ of origin: <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${bpvLocalPathReviewInstance?.otherOrganOrigin}" /></span>  
                                        </g:if>
                                        <g:else>
                                            <span id="oo" style="display: none"  class="value ${errorMap.get('otherOrganOrigin')}"><br/>Specify other organ of origin: <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${bpvLocalPathReviewInstance?.otherOrganOrigin}" /></span>  
                                        </g:else>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td class="name ${errorMap.get('needHis')}" colspan="2">
                                        4. Histologic type:
                                        <span data-msg="If <b>Other</b> was selected, record other histologic type" class="cahub-tooltip"></span>
                                        <g:if test="${bpvLocalPathReviewInstance?.category == 'Ovary' || bpvLocalPathReviewInstance?.category == 'Lung'}">
                                        <span data-msg="Specify histologic type details if the field pops up" class="cahub-tooltip"></span>
                                        </g:if>
                                        <div class="subentry value histologictype">
                                            <g:each in="${hisList}" status="i" var="his">
                                                <g:radio class="hisRadio" name="histologicType.id" value="${his.id}" id="his_${his.code}" checked="${bpvLocalPathReviewInstance?.histologicType?.id == his.id}"/>
                                                <label for="his_${his.code}">  ${his.name}
                                                    <g:if test="${his.who_code}"> (WHO code: ${his.who_code}) </g:if>
                                                </label><br/>
                                                
                                                <g:if test="${his.code == 'C7' && bpvLocalPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C7')} hisDetail"  id="SC7"><br/>Specify types and %:<br/><g:textArea name="detail_C7" cols="40" rows="5" value="${bpvLocalPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code =='C7' && bpvLocalPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC7" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C7" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'C8' && bpvLocalPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C8')} hisDetail" id="SC8"><br/>Specify types and %:<br/><g:textArea name="detail_C8" cols="40" rows="5" value="${bpvLocalPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C8' && bpvLocalPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC8" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C8" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'C9' && bpvLocalPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C9')} hisDetail" id="SC9"><br/>Specify types and %:<br/><g:textArea name="detail_C9" cols="40" rows="5" value="${bpvLocalPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C9' && bpvLocalPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC9" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C9" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'C20' && bpvLocalPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C20')} hisDetail"  id="SC20"><br/>Specify type:<br/><g:textArea name="detail_C20" cols="40" rows="5" value="${bpvLocalPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C20' && bpvLocalPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC20" style="display:none"><br/>Specify type:<br/><g:textArea name="detail_C20" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'OTHER' && bpvLocalPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_other')} hisDetail" id="SOTHER"><br/>Specify type:<br/><g:textArea name="detail_other" cols="40" rows="5" value="${bpvLocalPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'OTHER' && bpvLocalPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SOTHER" style="display:none"><br/>Specify type:<br/><g:textArea name="detail_other" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>
                                                
                                                <g:if test="${his.code == 'C78' && bpvLocalPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C78')} hisDetail"  id="SC78"><br/>Specify types and %:<br/><g:textArea name="detail_C78" cols="40" rows="5" value="${bpvLocalPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C78' && bpvLocalPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC78" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C78" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>
                                            </g:each>
                                        </div>
                                    </td>
                                </tr>

                                <g:if test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                    <tr class="prop clearborder">
                                        <td class="name" >
                                            5. Presence of Sarcomatoid features:
                                            <span data-msg="If <b>Present</b>, describe sarcomatoid features" class="cahub-tooltip"></span>
                                        </td>
                                        <td class="value ${errorMap.get('sarcomatoid')}" >
                                            <div>
                                                <g:radio name="sarcomatoid" id='sa1' value="Not identified" checked="${bpvLocalPathReviewInstance?.sarcomatoid =='Not identified'}"/>&nbsp;<label for="sa1">Not identified</label><br/>
                                                <g:radio name="sarcomatoid" id='sa2' value="Present" checked="${bpvLocalPathReviewInstance?.sarcomatoid =='Present'}"/>&nbsp;<label for="sa2">Present</label>
                                            </div>
                                        </td>
                                    </tr>
                                    
                                    <tr class="prop">
                                        <td colspan="2" class="name" >
                                            <g:if test="${bpvLocalPathReviewInstance?.sarcomatoid == 'Present'}">
                                                <span id="sad" style="display: block" class="value ${errorMap.get('sarcomatoidDesc')}"><br/><span class="subentry">Describe Sarcomatoid features:</span><br/><g:textArea class="textwide" id="sarcomatoidDesc" name="sarcomatoidDesc" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.sarcomatoidDesc}" /></span>  
                                            </g:if>
                                            <g:else>
                                                <span id="sad" style="display: none"  class="value ${errorMap.get('sarcomatoidDesc')}"><br/><span class="subentry">Describe Sarcomatoid features:</span><br/><g:textArea class="textwide" id="sarcomatoidDesc" name="sarcomatoidDesc" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.sarcomatoidDesc}" /></span>  
                                            </g:else>
                                        </td>
                                    </tr>
                                </g:if>

                                <tr class="prop">
                                    <td class="name" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            6. Greatest tumor dimension on slide:
                                        </g:if>
                                        <g:else>
                                            5. Greatest tumor dimension on slide:
                                        </g:else>
                                    </td>
                                    <td class="value ${errorMap.get('tumorDimension')}" ><g:textField id="tumorDimension" name="tumorDimension" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'tumorDimension')}" /> (mm)</td>
                                </tr>  

                                <tr class="prop">
                                    <td class="name" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            7. Percent of cross-sectional surface area composed of tumor focus:
                                        </g:if>
                                        <g:else>
                                            6. Percent of cross-sectional surface area composed of tumor focus:
                                        </g:else>
                                    </td>
                                    <td class="value ${errorMap.get('pctTumorArea')}" ><g:textField id="pctTumorArea" name="pctTumorArea" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'pctTumorArea')}" /> %</td>
                                </tr>

                                <tr class="prop">
                                    <td class="name" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            8. Percent of tumor cellularity by cell count of the entire slide:
                                        </g:if>
                                        <g:else>
                                            7. Percent of tumor cellularity by cell count of the entire slide:
                                        </g:else>
                                    </td>
                                    <td class="value ${errorMap.get('pctTumorCellularity')}" ><g:textField id="pctTumorCellularity" name="pctTumorCellularity" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'pctTumorCellularity')}" /> %</td>
                                </tr>

                                <tr class="prop">
                                    <td colspan="2" class="formheader name">Histologic Profile Quantitative Assessment Should Total 100%. BPV Case Acceptance Criteria Require Necrosis Percentage of <20% AND Tumor Content of &ge;50% Tumor Cells by Surface Area.</td>
                                </tr>

                                <tr class="prop clearborder">
                                    <td class="name" colspan="2">
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            9. Histologic Profile Quantitative Assessment:
                                        </g:if>
                                        <g:else>
                                            8. Histologic Profile Quantitative Assessment:
                                        </g:else>
                                        <span data-msg="If <b>Present</b>, describe Non-Cellular component" class="cahub-tooltip"></span>
                                    </td>                                
                                </tr>

                                <tr class="prop">
                                    <td class="name" colspan="2">
                                        <table class="tabledatecomments">
                                            <tr>
                                                <td >Percent Viable Tumor by surface area</td>
                                                <td class="value ${errorMap.get('pctViablTumor')}"><g:textField id="p1" name="pctViablTumor" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'pctViablTumor')}" SIZE="10" /> %</td>
                                            </tr>
                                            <tr>
                                                <td >Percent Necrotic Tumor by surface area</td>
                                                <td class="value ${errorMap.get('pctNecroticTumor')}" ><g:textField id="p2" name="pctNecroticTumor" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'pctNecroticTumor')}" SIZE="10" /> %</td>
                                            </tr> 
                                            <tr>
                                                <td >Percent Viable Non-Tumor Tissue by surface area</td>
                                                <td class="value ${errorMap.get('pctViableNonTumor')}" ><g:textField id="p3" name="pctViableNonTumor" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'pctViableNonTumor')}" SIZE="10" /> %</td>
                                            </tr>
                                            <tr>
                                                <td>Percent Non-Cellular Component by surface area (i.e., mucin, hemorrhage, blood clot, etc.)</td>
                                                <td class="value ${errorMap.get('pctNonCellular')}"><g:textField id="p4" name="pctNonCellular" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'pctNonCellular')}"  SIZE="10" /> %</td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" class="value ${errorMap.get('nonCellularDesc')}">
                                                    If present, describe Non-Cellular component:<br/>
                                                    <g:textArea class="textwide" name="nonCellularDesc" cols="40" rows="5" value="${bpvLocalPathReviewInstance?.nonCellularDesc}" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="formheader">Histologic Profile Total % (should equal 100%)</td>
                                                <td class="formheader ${errorMap.get('hisTotal')}"><g:textField readonly="readonly" id="t" name="hisTotal" value="${fieldValue(bean: bpvLocalPathReviewInstance, field: 'hisTotal')}" SIZE="10" /> %</td>
                                            </tr> 
                                        </table>
                                    </td>
                                </tr>
                                  
                                <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                    <tr class="prop">
                                        <td class="name" >9. What histologic grading system was applied?</td>
                                        <td class="value ${errorMap.get('gradingSystem')}" >
                                            <div>
                                                <g:radio name="gradingSystem" id='g1' value="WHO Grading System" checked="${bpvLocalPathReviewInstance?.gradingSystem =='WHO Grading System'}"/>&nbsp;<label for="g1">WHO Grading System</label><br/>
                                                <g:radio name="gradingSystem" id='g2' value="Two-Tier Grading System" checked="${bpvLocalPathReviewInstance?.gradingSystem =='Two-Tier Grading System'}"/>&nbsp;<label for="g2">Two-Tier Grading System</label><br/>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="prop" id="who" style="display:${bpvLocalPathReviewInstance?.gradingSystem == 'WHO Grading System' ? 'display' : 'none'}">
                                        <td class="name" >
                                            &nbsp;&nbsp;&nbsp;&nbsp;Histologic Grade (WHO Grading System):
                                            <span data-msg="WHO Grading applies to all carcinomas, including serous carcinomas" class="cahub-tooltip"></span>
                                        </td>
                                        <td class="value ${errorMap.get('grade')}" >
                                            <div>
                                                <g:radio name="grade" id='w1' value="G1: Well Differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G1: Well Differentiated'}"/>&nbsp;<label for="w1">G1: Well Differentiated</label><br/>
                                                <g:radio name="grade" id='w2' value="G2: Moderately differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="w2">G2: Moderately differentiated</label><br/>
                                                <g:radio name="grade" id='w3' value="G3: Poorly differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="w3">G3: Poorly differentiated</label><br/>
                                                <g:radio name="grade" id='w4' value="G4: Undifferentiated" checked="${bpvLocalPathReviewInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="w4">G4: Undifferentiated</label><br/>
                                                <g:radio name="grade" id='w5' value="GX: Cannot be assessed" checked="${bpvLocalPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="w5">GX: Cannot be assessed</label><br/>
                                                <g:radio name="grade" id='w6' value="" checked="${bpvLocalPathReviewInstance?.grade ==''}"/>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="prop" id="t2t" style="display:${bpvLocalPathReviewInstance?.gradingSystem == 'Two-Tier Grading System' ? 'display' : 'none'}">
                                        <td class="name" >
                                            &nbsp;&nbsp;&nbsp;&nbsp;Two-Tier Grading System Grade:
                                            <span data-msg="Two-Teir grading may be applied to serous carcinomas and immature teratomas only" class="cahub-tooltip"></span>
                                        </td>
                                        <td class="value ${errorMap.get('grade')}" >
                                            <div>
                                                <g:radio name="grade" id='tt1' value="Low grade" checked="${bpvLocalPathReviewInstance?.grade =='Low grade'}"/>&nbsp;<label for="tt1">Low grade</label><br/>
                                                <g:radio name="grade" id='tt2' value="High grade" checked="${bpvLocalPathReviewInstance?.grade =='High grade'}"/>&nbsp;<label for="tt2">High grade</label><br/>
                                            </div>
                                        </td >
                                    </tr>
                                </g:if>   
                                <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                    <tr class="prop">
                                        <td class="name" >10. Histologic Grade (Fuhrman Nuclear Grading System):</td>
                                        <td class="value ${errorMap.get('grade')}" >
                                            <div>
                                                <g:radio name="grade" id='fn1' value="G1: Nuclei round, uniform, approximately 10µm; nucleoli inconspicuous or absent" checked="${bpvLocalPathReviewInstance?.grade =='G1: Nuclei round, uniform, approximately 10µm; nucleoli inconspicuous or absent'}"/>&nbsp;<label for="fn1">G1: Nuclei  round, uniform, approximately 10µm; nucleoli inconspicuous or absent</label><br/>
                                                <g:radio name="grade" id='fn2' value="G2: Nuclei slightly irregular, approximately 15µm; nucleoli evident" checked="${bpvLocalPathReviewInstance?.grade =='G2: Nuclei slightly irregular, approximately 15µm; nucleoli evident'}"/>&nbsp;<label for="fn2">G2: Nuclei  slightly irregular, approximately 15µm; nucleoli evident</label><br/>
                                                <g:radio name="grade" id='fn3' value="G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent" checked="${bpvLocalPathReviewInstance?.grade =='G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent'}"/>&nbsp;<label for="fn3">G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent</label><br/>
                                                <g:radio name="grade" id='fn4' value="G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped" checked="${bpvLocalPathReviewInstance?.grade =='G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped'}"/>&nbsp;<label for="fn4">G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped</label><br/>
                                                <g:radio name="grade" id='fn5' value="GX: Cannot be assessed" checked="${bpvLocalPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="fn5">GX: Cannot be assessed</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:elseif>
                                <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                    <tr class="prop">
                                        <td class="name" >9. AJCC 7th Edition Histologic Grade:</td>
                                        <td class="value ${errorMap.get('grade')}" >
                                            <div>
                                                <g:radio name="grade" id='ajccColon1' value="G1: Well differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G1: Well differentiated'}"/>&nbsp;<label for="ajccColon1">G1: Well differentiated</label><br/>
                                                <g:radio name="grade" id='ajccColon2' value="G2: Moderately differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="ajccColon2">G2: Moderately differentiated</label><br/>
                                                <g:radio name="grade" id='ajccColon3' value="G3: Poorly differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="ajccColon3">G3: Poorly differentiated</label><br/>
                                                <g:radio name="grade" id='ajccColon4' value="G4: Undifferentiated" checked="${bpvLocalPathReviewInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="ajccColon4">G4: Undifferentiated</label><br/>
                                                <g:radio name="grade" id='ajccColon5' value="GX: Cannot be assessed" checked="${bpvLocalPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="ajccColon5">GX: Cannot be assessed</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:elseif>
                                <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                    <tr class="prop">
                                        <td class="name" >9. AJCC 7th Edition Histologic Grade:</td>
                                        <td class="value ${errorMap.get('grade')}" >
                                            <div>
                                                <g:radio name="grade" id='ajccLung1' value="G1: Well differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G1: Well differentiated'}"/>&nbsp;<label for="ajccLung1">G1: Well differentiated</label><br/>
                                                <g:radio name="grade" id='ajccLung2' value="G2: Moderately differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="ajccLung2">G2: Moderately differentiated</label><br/>
                                                <g:radio name="grade" id='ajccLung3' value="G3: Poorly differentiated" checked="${bpvLocalPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="ajccLung3">G3: Poorly differentiated</label><br/>
                                                <g:radio name="grade" id='ajccLung4' value="GX: Cannot be assessed" checked="${bpvLocalPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="ajccLung4">GX: Cannot be assessed</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:elseif>

                                <tr class="prop">
                                    <td colspan="2" class="formheader">Tumor Staging per AJCC 7th Edition</td>
                                </tr>

                                <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                    <tr class="prop">
                                        <td class="name" >10. pT: Pathologic Spread Primary Tumor (AJCC 7th Edition):</td>
                                        <td class="value ${errorMap.get('pathTumor')}" >
                                            <div>
                                                <g:radio name="pathTumor" id='pathTumor1' value="pTX" checked="${bpvLocalPathReviewInstance?.pathTumor =='pTX'}"/>&nbsp;<label for="pathTumor1">pTX</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor2' value="pT0" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT0'}"/>&nbsp;<label for="pathTumor2">pT0</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor3' value="pT1" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1'}"/>&nbsp;<label for="pathTumor3">pT1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor4' value="pT1a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1a'}"/>&nbsp;<label for="pathTumor4">pT1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor5' value="pT1b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1b'}"/>&nbsp;<label for="pathTumor5">pT1b</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor6' value="pT1c" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1c'}"/>&nbsp;<label for="pathTumor6">pT1c</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor7' value="pT2" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2'}"/>&nbsp;<label for="pathTumor7">pT2</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor8' value="pT2a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2a'}"/>&nbsp;<label for="pathTumor8">pT2a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor9' value="pT2b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2b'}"/>&nbsp;<label for="pathTumor9">pT2b</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor10' value="pT2c" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2c'}"/>&nbsp;<label for="pathTumor10">pT2c</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor11' value="pT3" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3'}"/>&nbsp;<label for="pathTumor11">pT3</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor12' value="pT3a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3a'}"/>&nbsp;<label for="pathTumor12">pT3a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor13' value="pT3b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3b'}"/>&nbsp;<label for="pathTumor13">pT3b</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor14' value="pT3c" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3c'}"/>&nbsp;<label for="pathTumor14">pT3c</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:if>
                                <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                    <tr class="prop">
                                        <td class="name" >11. pT: Pathologic Spread Primary Tumor (AJCC 7th Edition):</td>
                                        <td class="value ${errorMap.get('pathTumor')}" >
                                            <div>
                                                <g:radio name="pathTumor" id='pathTumor1' value="pTX" checked="${bpvLocalPathReviewInstance?.pathTumor =='pTX'}"/>&nbsp;<label for="pathTumor1">pTX</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor2' value="pT0" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT0'}"/>&nbsp;<label for="pathTumor2">pT0</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor3' value="pT1" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1'}"/>&nbsp;<label for="pathTumor3">pT1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor4' value="pT1a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1a'}"/>&nbsp;<label for="pathTumor4">pT1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor5' value="pT1b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1b'}"/>&nbsp;<label for="pathTumor5">pT1b</label><br/>                               
                                                <g:radio name="pathTumor" id='pathTumor6' value="pT2" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2'}"/>&nbsp;<label for="pathTumor6">pT2</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor7' value="pT2a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2a'}"/>&nbsp;<label for="pathTumor7">pT2a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor8' value="pT2b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2b'}"/>&nbsp;<label for="pathTumor8">pT2b</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor9' value="pT3" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3'}"/>&nbsp;<label for="pathTumor9">pT3</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor10' value="pT3a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3a'}"/>&nbsp;<label for="pathTumor10">pT3a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor11' value="pT3b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3b'}"/>&nbsp;<label for="pathTumor11">pT3b</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor12' value="pT3c" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3c'}"/>&nbsp;<label for="pathTumor12">pT3c</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor13' value="pT4" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT4'}"/>&nbsp;<label for="pathTumor13">pT4</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:elseif>
                                <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                    <tr class="prop">
                                        <td class="name" >10. pT: Pathologic Spread Primary Tumor (AJCC 7th Edition):</td>
                                        <td class="value ${errorMap.get('pathTumor')}" >
                                            <div>
                                                <g:radio name="pathTumor" id='pathTumor1' value="pTX" checked="${bpvLocalPathReviewInstance?.pathTumor =='pTX'}"/>&nbsp;<label for="pathTumor1">pTX</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor2' value="pT0" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT0'}"/>&nbsp;<label for="pathTumor2">pT0</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor3' value="pTis" checked="${bpvLocalPathReviewInstance?.pathTumor =='pTis'}"/>&nbsp;<label for="pathTumor3">pTis</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor4' value="pT1" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1'}"/>&nbsp;<label for="pathTumor4">pT1</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor5' value="pT2" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2'}"/>&nbsp;<label for="pathTumor5">pT2</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor6' value="pT3" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3'}"/>&nbsp;<label for="pathTumor6">pT3</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor7' value="pT4a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT4a'}"/>&nbsp;<label for="pathTumor7">pT4a</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor8' value="pT4b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT4b'}"/>&nbsp;<label for="pathTumor8">pT4b</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:elseif>
                                <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                    <tr class="prop">
                                        <td class="name" >10. pT: Pathologic Spread Primary Tumor (AJCC 7th Edition):</td>
                                        <td class="value ${errorMap.get('pathTumor')}" >
                                            <div>
                                                <g:radio name="pathTumor" id='pathTumor1' value="pTX" checked="${bpvLocalPathReviewInstance?.pathTumor =='pTX'}"/>&nbsp;<label for="pathTumor1">pTX</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor2' value="pT0" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT0'}"/>&nbsp;<label for="pathTumor2">pT0</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor3' value="pTis" checked="${bpvLocalPathReviewInstance?.pathTumor =='pTis'}"/>&nbsp;<label for="pathTumor3">pTis</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor4' value="pT1" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1'}"/>&nbsp;<label for="pathTumor4">pT1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor5' value="pT1a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1a'}"/>&nbsp;<label for="pathTumor5">pT1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor6' value="pT1b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT1b'}"/>&nbsp;<label for="pathTumor6">pT1b</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor7' value="pT2" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2'}"/>&nbsp;<label for="pathTumor7">pT2</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor8' value="pT2a" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2a'}"/>&nbsp;<label for="pathTumor8">pT2a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathTumor" id='pathTumor9' value="pT2b" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT2b'}"/>&nbsp;<label for="pathTumor9">pT2b</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor10' value="pT3" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT3'}"/>&nbsp;<label for="pathTumor10">pT3</label><br/>
                                                <g:radio name="pathTumor" id='pathTumor11' value="pT4" checked="${bpvLocalPathReviewInstance?.pathTumor =='pT4'}"/>&nbsp;<label for="pathTumor11">pT4</label>
                                            </div>
                                        </td>
                                    </tr>
                                </g:elseif>
