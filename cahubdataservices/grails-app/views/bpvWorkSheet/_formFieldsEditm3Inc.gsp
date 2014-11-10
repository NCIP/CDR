<table border="0">
  <tbody>
    <tr class="prop">

    <tr class="prop">
      <td valign="top" class="name">
        <label for="ntDisecPerformedBy">Tissue dissection performed by:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntDisecPerformedBy', 'errors')}">
  <g:textField name="ntDisecPerformedBy" value="${module3SheetInstance?.ntDisecPerformedBy}" />
</td>
</tr>
<tr class="prop">
  <td valign="top" class="name">
    <label for="fixativeTimeInRange">Were the normal adjacent tissue FFPE specimens processed with <1 hour delay to fixation and  23 hour time in fixative?</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntfixativeTimeInRange', 'errors')}">
    <div>
      <g:radio name="ntFixativeTimeInRange" id="ntFixativeTimeInRange_yes"  value="Yes" checked="${module3SheetInstance.ntFixativeTimeInRange =='Yes'}"  />&nbsp;<label for="f1">Yes</label><br />
      <g:radio name="ntFixativeTimeInRange" id="ntFixativeTimeInRange_no"  value="No" checked="${module3SheetInstance.ntFixativeTimeInRange =='No'}"  />&nbsp;<label for="f2">No</label>
    </div>
  </td>
</tr>
<g:if test="${module3SheetInstance.ntFixativeTimeInRange =='No'}">
  <tr >
    <td valign="top" class="name">
      <label for="ntFixativeTimeInRange">If No, please specify:</label>
    </td>
    <td valign="top" vlass="value">
  <g:textArea style="height:38px;width:200px;" name="ntReasonNotInRange" value="${module3SheetInstance?.ntReasonNotInRange}" />
  </td>
  </tr>
</g:if>
<tr class="prop">
  <td valign="top" class="name">
    <label for="ntComment">Comments/issues with tissue receipt of deviations from SOP:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntComment', 'errors')}">
<g:textArea style="height:38px;width:200px;" name="ntComment" value="${module3SheetInstance?.ntComment}" />
</td>
</tr>


</tbody>
</table>


<table border="0">
  <th>&nbsp;</th><th>Barcode ID</th><th>Date/Time of Preservation (fixed or frozen)</th><th>Weight(mg)</th>
  <tr class="odd">
    <td>FFPE tissue 1</td>  
    <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeId1', 'errors')} ${warningMap.get("ntFfpeId1")? "warnings" : ""}">
  <g:textField name="ntFfpeId1" value="${module3SheetInstance?.ntFfpeId1}" />
</td>



<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module3SheetInstance?.ntFfpeTimeStr1}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>


<td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeWeight1', 'errors')}">
<g:textField class="w" id="w7" name="ntFfpeWeight1" value="${fieldValue(bean: module3SheetInstance, field: 'ntFfpeWeight1')}" />
</td>
</tr>
<tr class="even">
  <td>FFPE tissue 2</td>  
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeId2', 'errors')} ${warningMap.get("ntFfpeId2")? "warnings" : ""} ">
<g:textField name="ntFfpeId2" value="${module3SheetInstance?.ntFfpeId2}" />
</td>


<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module3SheetInstance?.ntFfpeTimeStr2}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>

<td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeWeight2', 'errors')}">
<g:textField class="w" id="w8" name="ntFfpeWeight2" value="${fieldValue(bean: module3SheetInstance, field: 'ntFfpeWeight2')}" />
</td>
</tr>

<tr class="odd">
  <td>FFPE tissue 3</td>  
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeId3', 'errors')} ${warningMap.get("ntFfpeId3")? "warnings" : ""}">
<g:textField name="ntFfpeId3" value="${module3SheetInstance?.ntFfpeId3}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module3SheetInstance?.ntFfpeTimeStr3}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>

<td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeWeight3', 'errors')}">
<g:textField class="w" id="w9" name="ntFfpeWeight3" value="${fieldValue(bean: module3SheetInstance, field: 'ntFfpeWeight3')}" />
</td>
</tr>
<tr class="even">
  <td>Frozen tissues 1</td>
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenId1', 'errors')} ${warningMap.get("ntFrozenId1")? "warnings" : ""}">
<g:textField name="ntFrozenId1" value="${module3SheetInstance?.ntFrozenId1}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module3SheetInstance?.ntFrozenTimeStr1}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>
<td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenWeight1', 'errors')}">
<g:textField class="w" id="w10" name="ntFrozenWeight1" value="${fieldValue(bean: module3SheetInstance, field: 'ntFrozenWeight1')}" />
</td>
</tr>
<tr class="odd">
  <td>Frozen tissues 2</td>
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenId2', 'errors')} ${warningMap.get("ntFrozenId2")? "warnings" : ""}">
<g:textField name="ntFrozenId2" value="${module3SheetInstance?.ntFrozenId2}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module3SheetInstance?.ntFrozenTimeStr2}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>
<td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenWeight2', 'errors')}">
<g:textField class="w" id="w11" name="ntFrozenWeight2" value="${fieldValue(bean: module3SheetInstance, field: 'ntFrozenWeight2')}" />
</td>
</tr>
<tr class="even">
  <td>Frozen tissues 3</td>
  <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenId3', 'errors')} ${warningMap.get("ntFrozenId3")? "warnings" : ""}">
<g:textField name="ntFrozenId3" value="${module3SheetInstance?.ntFrozenId3}" />
</td>

<td valign="top" class="value">
<g:if test="${session.LDS == true}">
${module3SheetInstance?.ntFrozenTimeStr3}
</g:if>
<g:if test="${session.LDS == false || session.LDS == null}">
  <span class="redactedMsg">REDACTED (No LDS privilege)</span>
</g:if>
</td>

<td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenWeight3', 'errors')}">
<g:textField class="w" id="w12" name="ntFrozenWeight3" value="${fieldValue(bean: module3SheetInstance, field: 'ntFrozenWeight3')}" />
</td>
</tr>

</table>




