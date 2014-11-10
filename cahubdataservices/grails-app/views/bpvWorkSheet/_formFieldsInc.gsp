 <table>
  <tbody>
   
<tr class="prop">
  <td valign="top" class="name" style="width:30%">
    <label for="parentSampleId">Gross sample barcode ID:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: bpvWorkSheetInstance, field: 'parentSampleId', 'errors')}">
<g:textField name="parentSampleId" value="${bpvWorkSheetInstance?.parentSampleId}" />
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="experimentId">Experimental key ID:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: bpvWorkSheetInstance, field: 'experimentId', 'errors')}">
<g:textField class="recorddate" name="experimentId_${bpvWorkSheetInstance?.id}" value="${bpvWorkSheetInstance?.experimentId}" />
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="dateEidRecorded">Date & Time experimental key barcode ID was recorded:</label>
  </td>
  <td valign="top" >
 <g:if test="${session.LDS == true}">
      ${bpvWorkSheetInstance?.dateEidRecordedStr}
    </g:if>
    <g:if test="${session.LDS == false || session.LDS == null}">
      <span class="redactedMsg">REDACTED (No LDS privilege)</span>
    </g:if>
</td>
</tr>


</tbody>
</table>
