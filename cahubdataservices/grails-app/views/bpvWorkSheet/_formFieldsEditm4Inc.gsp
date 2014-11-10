
<table border="0">
  <tbody>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="ttDisecPerformedBy">Tissue dissection performed by:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttDisecPerformedBy', 'errors')}">
  <g:textField name="ttDisecPerformedBy" value="${module4SheetInstance?.ttDisecPerformedBy}" />
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="fixativeTimeInRange">Were the additional tumor Tissue FFPE specimens processed with <1 hour delay to fixation and  23 hour time in fixative?</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFixativeTimeInRange', 'errors')}">
    <div>
      <g:radio name="ttFixativeTimeInRange" id="ttFixativeTimeInRange_yes" value="Yes" checked="${module4SheetInstance.ttFixativeTimeInRange =='Yes'}"  />&nbsp;<label for="f1">Yes</label><br />
      <g:radio name="ttFixativeTimeInRange" id="ttFixativeTimeInRange_no" value="No" checked="${module4SheetInstance.ttFixativeTimeInRange =='No'}"  />&nbsp;<label for="f2">No</label>
    </div>
  </td>
</tr>
<g:if test="${module4SheetInstance.ttFixativeTimeInRange =='No'}">
  <tr >
    <td valign="top" class="name">
      <label for="ttFixativeTimeInRange">If No, please specify:</label>
    </td>
    <td valign="top" vlass="value">
  <g:textArea style="height:38px;width:200px;" name="ttReasonNotInRange" value="${module4SheetInstance?.ttReasonNotInRange}" />
  </td>
  </tr>
</g:if>   


<tr class="prop">
  <td valign="top" class="name">
    <label for="ttComment">Comments/issues with tissue receipt of deviations from SOP:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttComment', 'errors')}">
<g:textArea style="height:38px;width:200px;" name="ttComment" value="${module4SheetInstance?.ttComment}" />
</td>
</tr>


</tbody>
</table>

<table border="0">
  <th>&nbsp;</th><th>Barcode ID</th><th>Date/Time of Preservation (fixed or frozen)</th><th>Weight(mg)</th>
  <tr class="odd">
    <td>FFPE tissue 1</td>  
    <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeId1', 'errors')} ${warningMap.get("ttFfpeId1")? "warnings" : ""}">
  <g:textField name="ttFfpeId1" value="${module4SheetInstance?.ttFfpeId1}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module4SheetInstance?.ttFfpeTimeStr1}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>
<td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeWeight1', 'errors')}">
<g:textField class="w" id="w1"  name="ttFfpeWeight1" value="${fieldValue(bean: module4SheetInstance, field: 'ttFfpeWeight1')}" />
</td>
</tr>
<tr class="even">
  <td>FFPE tissue 2</td>  
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeId2', 'errors')} ${warningMap.get("ttFfpeId2")? "warnings" : ""}">
<g:textField name="ttFfpeId2" value="${module4SheetInstance?.ttFfpeId2}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module4SheetInstance?.ttFfpeTimeStr2}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>
<td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeWeight2', 'errors')}">
<g:textField class="w" id="w2" name="ttFfpeWeight2" value="${fieldValue(bean: module4SheetInstance, field: 'ttFfpeWeight2')}" />
</td>
</tr>

<tr class="odd">
  <td>FFPE tissue 3</td>  
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeId3', 'errors')} ${warningMap.get("ttFfpeId3")? "warnings" : ""}">
<g:textField  name="ttFfpeId3" value="${module4SheetInstance?.ttFfpeId3}" />
</td>


<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module4SheetInstance?.ttFfpeTimeStr3}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>



<td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeWeight3', 'errors')}">
<g:textField class="w" id="w3" name="ttFfpeWeight3" value="${fieldValue(bean: module4SheetInstance, field: 'ttFfpeWeight3')}" />
</td>
</tr>
<tr class="even">
  <td>Frozen tissues 1</td>
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenId1', 'errors')} ${warningMap.get("ttFrozenId1")? "warnings" : ""}">
<g:textField  name="ttFrozenId1" value="${module4SheetInstance?.ttFrozenId1}" />
</td>


<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module4SheetInstance?.ttFrozenTimeStr1}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>


<td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenWeight1', 'errors')}">
<g:textField class="w" id="w4" name="ttFrozenWeight1" value="${fieldValue(bean: module4SheetInstance, field: 'ttFrozenWeight1')}" />
</td>

</tr>
<tr class="odd">
  <td>Frozen tissues 2</td>
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenId2', 'errors')} ${warningMap.get("ttFrozenId2")? "warnings" : ""}">
<g:textField name="ttFrozenId2" value="${module4SheetInstance?.ttFrozenId2}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module4SheetInstance?.ttFrozenTimeStr2}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>
<td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenWeight2', 'errors')}">
<g:textField class="w" id="w5" name="ttFrozenWeight2" value="${fieldValue(bean: module4SheetInstance, field: 'ttFrozenWeight2')}" />
</td>
</tr>
<tr class="even">
  <td>Frozen tissues 3</td>
  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenId3', 'errors')} ${warningMap.get("ttFrozenId3")? "warnings" : ""}">
<g:textField name="ttFrozenId3" value="${module4SheetInstance?.ttFrozenId3}" />
</td>


<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module4SheetInstance?.ttFrozenTimeStr3}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>
<td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenWeight3', 'errors')}">
<g:textField class="w" id="w6" name="ttFrozenWeight3" value="${fieldValue(bean: module4SheetInstance, field: 'ttFrozenWeight3')}" />
</td>
</tr>

</table>
