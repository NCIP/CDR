

   
      <table>
         <tr><td class="name">Select Priority<span id="bpvworksheet.priority" class="vocab-tooltip"></span></td><td class="value ${hasErrors(bean: module5SheetInstance, field: 'priority', 'errors')}"><div><g:radio id ="p1" name="priority4"  value="1" checked="${module5SheetInstance.priority ==1}"  />&nbsp;<label for="p1">Priority I</label><br /><g:radio id ="p2" name="priority4"  value="2" checked="${module5SheetInstance.priority ==2}"  />&nbsp;<label for="p2">Priority II</label></div></td></tr>
       </table>
      <br></br>
      
      <g:if test ="${bpvWorkSheetInstance.caseRecord.primaryTissueType.code=='OVARY'}">
       <table>
         <tr><td class="name">Module V tissue was collected from:<span id="bpvworksheet.moduletissuefrom" class="vocab-tooltip"></span></td><td class="value ${hasErrors(bean: module5SheetInstance, field: 'whichOvary', 'errors')}"><div><g:radio id ="l" name="whichOvary4"  value="L" checked="${module5SheetInstance.whichOvary =='L'}"  />&nbsp;<label for="l">Left Ovary</label><br /><g:radio id ="r" name="whichOvary4"  value="R" checked="${module5SheetInstance.whichOvary =='R'}"  />&nbsp;<label for="r">Right Ovary</label></div></td></tr>
       </table>
      </g:if>
     
      <div class="line">
        
        
         <table class="bpvws">
          <tbody>
            <tr><th colspan="2" > Frozen Control Tissue Sample Information, Module V</th></tr>
            <tr class="prop" >
              <td  valign="top" class="name" style="width:30%" >Barcode ID of frozen control tissue cryosette:<span id="bpvworksheet.barcodeidcryosette" class="vocab-tooltip"></span></td>
              <td class="${errorMap.get("sampleId4Frozen_" +module5SheetInstance?.sampleFr?.id)} ${warningMap.get("sampleId_" +module5SheetInstance?.sampleFr?.id)? "warnings" : ""}"><g:textField class="recorddate" name="sampleId4Frozen_${module5SheetInstance?.sampleFr?.id}" value="${module5SheetInstance?.sampleFr?.sampleId4Frozen}" /></td>
          </tr>
          <tr class="prop">
            <td  valign="top" class="name" >Date/Time the tissue sample was frozen in liquid nitrogen:<span id="bpvworksheet.datefrozenliquidnitrogen" class="vocab-tooltip"></span></td>
            <td class="${errorMap.get("dateSampleFrozenStr_" +module5SheetInstance?.sampleFr?.id)}">
               <g:if test="${session.LDS == true}">
          <g:textField  name="dateSampleFrozenStr_${module5SheetInstance?.sampleFr?.id}" value="${module5SheetInstance?.sampleFr?.dateSampleFrozenStr}" />
           </g:if>
           <g:if test="${session.LDS == false || session.LDS == null}">
             <span class="redactedMsg">REDACTED (No LDS privilege)</span>
          </g:if>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="sampleFrWeight">Weight of frozen control block:</label>
            </td>
            <td valign="top" class="value ${errorMap.get("weight_" +module5SheetInstance?.sampleFr?.id)}">
          <g:textField class="weight" id="sampleFrWeight" name="weight_${module5SheetInstance?.sampleFr?.id}" value="${module5SheetInstance?.sampleFr?.weight}" /> mg
          </td>
          </tr>

          </tbody>
        </table>
        <br></br>
      <table class="bpvws">
        <tr><th colspan="7" class="bpvws">Module V: QC FFPE Section</th></tr>
        <tr>
          <th>Protocol</th>
          <th>Planned Delay to Fixation Time</th>
          <th>Scanned ID of cassette: Record first scan</th>
          <th>Date/Time That Cassette Was First Scanned Or Recorded</th>
          <th>Scanned ID of cassette: Record when placed in fixative</th>
          <th>Date/Time That Cassette Was Placed In Fixative</th>
          <th>Delay to Fixation Time (HH:MM)</th>
        </tr>
        <g:each in="[module5SheetInstance.sampleQc]" status="i" var="s">
          <tr id="${s.protocolId}">
            <td>${s.protocol}</td>
            <td>${s.plannedDelay}</td>
            <td class='${errorMap.get("sampleId4Record_" +s.id)} ${warningMap.get("sampleId_" +s.id)? "warnings" : ""}'><g:textField class="recorddate" name="sampleId4Record_${s.id}" value="${s.sampleId4Record}" size="15" /></td>
          <td class="${errorMap.get("dateSampleRecordedStr_" +s.id)}" nowrap="nowrap">
            <g:if test="${session.LDS == true}">
          <g:textField  name="dateSampleRecordedStr_${s.id}" value="${s.dateSampleRecordedStr}" size="16" />
           </g:if>
        <g:if test="${session.LDS == false || session.LDS == null}">
          <span class="redactedMsg">REDACTED (No LDS privilege)</span>
        </g:if>

          </td>
          <td class="${errorMap.get("sampleId4Fixative_" +s.id)}"><g:textField class="recorddate"  name="sampleId4Fixative_${s.id}" value="${s.sampleId4Fixative}" size="16" /></td>
          <td class="${errorMap.get("dateSampleInFixativeStr_" +s.id)}" nowrap="nowrap">
            <g:if test="${session.LDS == true}">
          <g:textField class="recordinterval" id="dateSampleInFixativeStr_${s.id}" name="dateSampleInFixativeStr_${s.id}" value="${s.dateSampleInFixativeStr}" size="16" />
           </g:if>
           <g:if test="${session.LDS == false || session.LDS == null}">
             <span class="redactedMsg">REDACTED (No LDS privilege)</span>
           </g:if>

          </td>
          <td class="${warningMap?.get('interval1_' + s.id) ? 'warnings' : ''}"><span id="in1_${s.id}">${intervalMap5.get('interval1_' + s.id)}</span></td>
          </tr>
        </g:each>
      </table>
      <br></br>

      <table class="bpvws">
        <tr><th colspan="10" class="bpvws">Module V: QC FFPE Section<br />Continue entering data for QC FFPE Section</th></tr>
        <tr>
          <th>Protocol</th>
          <th>Scanned ID of cassette: Record when placed in tissue processor</th>
          <th>Date/Time Cassette Was Placed in Processor</th>                      
          <th>Date/Time Tissue Processor Cycle Ended</th>
          <th>Actual Time in Fixative (HH:MM)</th>
          <th>Scanned ID of cassette: Record when removed from tissue processor</th>
          <th>Date/Time Tissue Cassettes Were Removed From Tissue Processor</th>
          <th>Scanned ID of cassette: Record when tissue embedding was started</th>
          <th>Date/Time Tissue Embedding Was Started</th>
          <th>Time between Tissue Processor Cycle Completion and Embedding (HH:MM)</th>
        </tr>
        <g:each in="[module5SheetInstance.sampleQc]" status="i" var="s">
          <tr id="${s.protocolId}">
            <td>${s.protocol}</td>
            <td class="${errorMap.get("sampleId4Proc_" +s.id)}"><g:textField class="recorddate" name="sampleId4Proc_${s.id}" value="${s.sampleId4Proc}" size="15" /></td>
          <td class="${errorMap.get("dateSampleInProcStr_" +s.id)}" nowrap="nowrap">
            <g:if test="${session.LDS == true}">
          <g:textField class="recordinterval" name="dateSampleInProcStr_${s.id}" value="${s.dateSampleInProcStr}" size="16" />
           </g:if>
           <g:if test="${session.LDS == false || session.LDS == null}">
             <span class="redactedMsg">REDACTED (No LDS privilege)</span>
          </g:if>
          </td>
          <td class="${errorMap.get("dateSampleProcEndStr_" +s.id)}" nowrap="nowrap">
            <g:if test="${session.LDS == true}"> 
          <g:textField class="recordinterval" id="dateSampleProcEndStr_${s.id}" name="dateSampleProcEndStr_${s.id}" value="${s.dateSampleProcEndStr}" size="16" />
           </g:if>
          <g:if test="${session.LDS == false || session.LDS == null}">
            <span class="redactedMsg">REDACTED (No LDS privilege)</span>
          </g:if>
          </td>
          <td class="${warningMap?.get('interval2_' + s.id) ? 'warnings' : ''}"><span id="in2_${s.id}">${intervalMap5.get('interval2_' + s.id)}</span></td>
          <td class='${errorMap.get("sampleId4Removal_" +s.id)}'><g:textField class="recorddate" name="sampleId4Removal_${s.id}" value="${s.sampleId4Removal}" size="16" /></td>
          <td class="${errorMap.get("dateSampleRemovedStr_" +s.id)}" nowrap="nowrap">
             <g:if test="${session.LDS == true}">
          <g:textField  name="dateSampleRemovedStr_${s.id}" value="${s.dateSampleRemovedStr}" size="16" />
            </g:if>
         <g:if test="${session.LDS == false || session.LDS == null}">
          <span class="redactedMsg">REDACTED (No LDS privilege)</span>
        </g:if>

          </td>
          <td class='${errorMap.get("sampleId4Embedding_" +s.id)}'><g:textField class="recorddate" name="sampleId4Embedding_${s.id}" value="${s.sampleId4Embedding}" size="16" /></td>
          <td class="${errorMap.get("dateSampleEmbStartedStr_" +s.id)}" nowrap="nowrap">
            <g:if test="${session.LDS == true}">
          <g:textField class="recordinterval" id="dateSampleEmbStartedStr_${s.id}" name="dateSampleEmbStartedStr_${s.id}" value="${s.dateSampleEmbStartedStr}" size="16" />
          </g:if>
          <g:if test="${session.LDS == false || session.LDS == null}">
           <span class="redactedMsg">REDACTED (No LDS privilege)</span>
           </g:if>
          </td>
          <td><span id="in3_${s.id}">${intervalMap5.get('interval3_' + s.id)}</span></td>


          </tr>
        </g:each>

      </table>
       </div>
    
      <br></br>
      <table class="bpvws">
        <tr><th colspan="8" class="bpvws">Module V:</th></tr>
        <tr>
          <th>Protocol</th><th>Scanned ID of Cryosette: <br/> Record when frozen</th><th>Time Frozen</th> <th>Method of Freezing</th><th>Scanned ID of Cryosette: <br/> Record when transferred </th><th>Time Transferred</th><th>Transferred to</th><th>Weight (mg)</th>
        </tr>
         <g:each in="[module5SheetInstance.sampleU, module5SheetInstance.sampleV, module5SheetInstance.sampleW, module5SheetInstance.sampleX]" status="i" var="s">
           <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${s.protocol}</td>
              <td class="${errorMap.get("sampleId4Frozen_" +s.id)} ${warningMap.get("sampleId_" +s.id)? "warnings" : ""} "><g:textField class="recorddate" name="sampleId4Frozen_${s.id}" value="${s.sampleId4Frozen}" size="15" /></td>
              <td class="${errorMap.get("dateSampleFrozenStr_" +s.id)}">
                 <g:if test="${session.LDS == true}">
              <g:textField  name="dateSampleFrozenStr_${s.id}" value="${s.dateSampleFrozenStr}" />
               </g:if>
            <g:if test="${session.LDS == false || session.LDS == null}">
            <span class="redactedMsg">REDACTED (No LDS privilege)</span>
            </g:if>

              </td>
              <td>${s.freezeMethod}</td>
              <td class="${errorMap.get("sampleId4Trans_" +s.id)}"><g:textField class="recorddate" name="sampleId4Trans_${s.id}" value="${s.sampleId4Trans}" size="15" /></td>
              <td class="${errorMap.get("dateSampleTransStr_" +s.id)}">
               <g:if test="${session.LDS == true}"> 
              <g:textField  name="dateSampleTransStr_${s.id}" value="${s.dateSampleTransStr}" />
              </g:if>
             <g:if test="${session.LDS == false || session.LDS == null}">
             <span class="redactedMsg">REDACTED (No LDS privilege)</span>
             </g:if>

              </td>
              <td>${s.transTo}</td>
              <td class="${errorMap.get("weight_" +s.id)}"><g:textField class="weight" name="weight_${s.id}" value="${s.weight}" size="15" /></td>
           </tr>
         </g:each>
      </table>
    
