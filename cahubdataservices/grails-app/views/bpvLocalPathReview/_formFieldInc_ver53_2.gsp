

                                <tr class="prop">
                                    <td colspan="2" class="formheader">Tumor Staging per AJCC 7th Edition</td>
                                </tr>
                                
                                <tr class="prop">
                                    <td class="name" >${seq++}. pT: Pathologic Spread Primary Tumor (AJCC 7th Edition):</td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('pathTumor')}" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
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
                                        </g:if>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
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
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
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
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
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
                                        </g:elseif>
                                    </td>
                                </tr>
                                                                
                                <tr class="prop">
                                    <td class="name" >${seq++}. pN: Pathologic Spread Lymph Nodes (AJCC 7th Edition):</td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('pathNodes')}" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                            <div>
                                                <g:radio name="pathNodes" id='pathNodes1' value="pNX" checked="${bpvLocalPathReviewInstance?.pathNodes =='pNX'}"/>&nbsp;<label for="pathNodes1">pNX</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes2' value="pN0" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN0'}"/>&nbsp;<label for="pathNodes2">pN0</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes3' value="pN1" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1'}"/>&nbsp;<label for="pathNodes3">pN1</label>
                                            </div>
                                        </g:if>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            <div>
                                                <g:radio name="pathNodes" id='pathNodes1' value="pNX" checked="${bpvLocalPathReviewInstance?.pathNodes =='pNX'}"/>&nbsp;<label for="pathNodes1">pNX</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes2' value="pN0" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN0'}"/>&nbsp;<label for="pathNodes2">pN0</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes3' value="pN1" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1'}"/>&nbsp;<label for="pathNodes3">pN1</label>
                                            </div>
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                            <div>
                                                <g:radio name="pathNodes" id='pathNodes1' value="pNX" checked="${bpvLocalPathReviewInstance?.pathNodes =='pNX'}"/>&nbsp;<label for="pathNodes1">pNX</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes2' value="pN0" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN0'}"/>&nbsp;<label for="pathNodes2">pN0</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes3' value="pN1" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1'}"/>&nbsp;<label for="pathNodes3">pN1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathNodes" id='pathNodes4' value="pN1a" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1a'}"/>&nbsp;<label for="pathNodes4">pN1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathNodes" id='pathNodes5' value="pN1b" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1b'}"/>&nbsp;<label for="pathNodes5">pN1b</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathNodes" id='pathNodes6' value="pN1c" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1c'}"/>&nbsp;<label for="pathNodes6">pN1c</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes7' value="pN2" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN2'}"/>&nbsp;<label for="pathNodes7">pN2</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathNodes" id='pathNodes8' value="pN2a" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN2a'}"/>&nbsp;<label for="pathNodes8">pN2a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathNodes" id='pathNodes9' value="pN2b" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN2b'}"/>&nbsp;<label for="pathNodes9">pN2b</label>
                                            </div>
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                            <div>
                                                <g:radio name="pathNodes" id='pathNodes1' value="pNX" checked="${bpvLocalPathReviewInstance?.pathNodes =='pNX'}"/>&nbsp;<label for="pathNodes1">pNX</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes2' value="pN0" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN0'}"/>&nbsp;<label for="pathNodes2">pN0</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes3' value="pN1" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN1'}"/>&nbsp;<label for="pathNodes3">pN1</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes4' value="pN2" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN2'}"/>&nbsp;<label for="pathNodes4">pN2</label><br/>
                                                <g:radio name="pathNodes" id='pathNodes5' value="pN3" checked="${bpvLocalPathReviewInstance?.pathNodes =='pN3'}"/>&nbsp;<label for="pathNodes5">pN3</label>
                                            </div>
                                        </g:elseif>
                                    </td>
                                </tr>
                                                  
                                <tr class="prop">
                                    <td class="name" >${seq++}. M: Distant Metastases (AJCC 7th Edition):</td>
                                    <td class="value ${ (errorMap == null) ? '' : errorMap.get('pathMetastases')}" >
                                        <g:if test="${bpvLocalPathReviewInstance?.category=='Ovary'}">
                                            <div>
                                                <g:radio name="pathMetastases" id='pathMetastases1' value="cM0" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM0'}"/>&nbsp;<label for="pathMetastases1">cM0</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases2' value="cM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1'}"/>&nbsp;<label for="pathMetastases2">cM1</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases3' value="pM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1'}"/>&nbsp;<label for="pathMetastases3">pM1</label>
                                            </div>
                                        </g:if>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                                            <div>
                                                <g:radio name="pathMetastases" id='pathMetastases1' value="cM0" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM0'}"/>&nbsp;<label for="pathMetastases1">cM0</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases2' value="cM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1'}"/>&nbsp;<label for="pathMetastases2">cM1</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases3' value="pM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1'}"/>&nbsp;<label for="pathMetastases3">pM1</label>
                                            </div>
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Colon'}">
                                            <div>
                                                <g:radio name="pathMetastases" id='pathMetastases1' value="cM0" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM0'}"/>&nbsp;<label for="pathMetastases1">cM0</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases2' value="cM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1'}"/>&nbsp;<label for="pathMetastases2">cM1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases3' value="cM1a" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1a'}"/>&nbsp;<label for="pathMetastases3">cM1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases4' value="cM1b" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1b'}"/>&nbsp;<label for="pathMetastases4">cM1b</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases5' value="pM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1'}"/>&nbsp;<label for="pathMetastases5">pM1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases6' value="pM1a" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1a'}"/>&nbsp;<label for="pathMetastases6">pM1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases7' value="pM1b" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1b'}"/>&nbsp;<label for="pathMetastases7">pM1b</label>
                                            </div>
                                        </g:elseif>
                                        <g:elseif test="${bpvLocalPathReviewInstance?.category=='Lung'}">
                                            <div>
                                                <g:radio name="pathMetastases" id='pathMetastases1' value="cM0" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM0'}"/>&nbsp;<label for="pathMetastases1">cM0</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases2' value="cM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1'}"/>&nbsp;<label for="pathMetastases2">cM1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases3' value="cM1a" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1a'}"/>&nbsp;<label for="pathMetastases3">cM1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases4' value="cM1b" checked="${bpvLocalPathReviewInstance?.pathMetastases =='cM1b'}"/>&nbsp;<label for="pathMetastases4">cM1b</label><br/>
                                                <g:radio name="pathMetastases" id='pathMetastases5' value="pM1" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1'}"/>&nbsp;<label for="pathMetastases5">pM1</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases6' value="pM1a" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1a'}"/>&nbsp;<label for="pathMetastases6">pM1a</label><br/>
                                                &nbsp;&nbsp;&nbsp;&nbsp;<g:radio name="pathMetastases" id='pathMetastases7' value="pM1b" checked="${bpvLocalPathReviewInstance?.pathMetastases =='pM1b'}"/>&nbsp;<label for="pathMetastases7">pM1b</label>
                                            </div>
                                        </g:elseif>
                                    </td>
                                </tr>
          
