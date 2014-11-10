<%@ page import="nci.obbr.cahub.staticmembers.SOP" %>

<table>
  <thead><tr class="prop"><th colspan="3"><h2>Case Details</h2></th></tr></thead>
  <tbody>
    <tr class="prop">
      <td>
          <label>Case ID:</label> 
          <g:displayCaseRecordLink caseRecord="${tissueRecoveryGtexInstance.caseRecord}" session="${session}" />
          %{-- <g:link controller="caseRecord" action="display" id="${tissueRecoveryGtexInstance.caseRecord.id}">${tissueRecoveryGtexInstance.caseRecord.caseId}</g:link> --}%
      </td>
      <td><label>Collection Type:</label> ${tissueRecoveryGtexInstance.caseRecord.caseCollectionType}</td>       
      <td><label>BSS:</label> ${tissueRecoveryGtexInstance.caseRecord.bss.name}</td>
    </tr>  
  </tbody>
</table>

<div id="sect-tissue-rec" class="div-t">
    <div class="div-t-r div-t-h">Tissue Recovery Collection Data</div>
    <div class="div-t-r clearfix">
          <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'collectionDate', 'errors')}"><span class="label-maintain"><label for="collectionDate">GTEX procedure start date:</label></span>
            <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="collectionDate" value="${tissueRecoveryGtexInstance?.collectionDate}" /></div>                              
          <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'collectionStartTime', 'errors')}"><label for="collectionStartTime">GTEX procedure start time:</label>
            <g:textField name="collectionStartTime" value="${tissueRecoveryGtexInstance?.collectionStartTime}" class="timeEntry" /></div>
    </div>
    <div class="div-t-r clearfix">
          <g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code != 'SURGI'}">
            <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'chestIncisionTime', 'errors')}">
              <span class="label-maintain"><label for="chestIncisionTime">Chest incision time:</label></span> <g:textField name="chestIncisionTime" value="${tissueRecoveryGtexInstance?.chestIncisionTime}" class="timeEntry" /></div>
          </g:if>
          <g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code != 'POSTM'}">
            <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'crossClampTime', 'errors')}"><label for="crossClampTime"><!--note just the label changes below.  The same DB field is used for both labels-->
                <g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'SURGI'}">Earliest cross-clamp time:</g:if>
                <g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'OPO'}">Clamp/ligature time:</g:if></label> 
              <g:textField name="crossClampTime" value="${tissueRecoveryGtexInstance?.crossClampTime}" class="timeEntry" /></div>
          </g:if>
      </div>
  <g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'OPO'}">
    <div id="org-tis-donated" class="div-t-r clearfix">
      <div class="left width-55">
        <label for="donatedOrganOther">Organs/Tissues Donated From GTEx Subject:</label><br />
        <g:checkBox class="checkBxLabelLeft" name="donatedLiver" id="d1" value="${tissueRecoveryGtexInstance?.donatedLiver}" /><label class="no-bold" for="d1">Liver</label>
        <g:checkBox class="checkBxLabelLeft" name="donatedKidney" id="d2" value="${tissueRecoveryGtexInstance?.donatedKidney}" /><label class="no-bold" for="d2">Kidney</label> 
        <g:checkBox class="checkBxLabelLeft" name="donatedHeart" id="d3" value="${tissueRecoveryGtexInstance?.donatedHeart}" /><label class="no-bold" for="d3">Heart</label>                                 
        <g:checkBox class="checkBxLabelLeft" name="donatedLung" id="d4" value="${tissueRecoveryGtexInstance?.donatedLung}" /><label class="no-bold" for="d4">Lung</label>
        <g:checkBox class="checkBxLabelLeft" name="donatedPancreas" id="d5" value="${tissueRecoveryGtexInstance?.donatedPancreas}"/><label class="no-bold" for="d5">Pancreas</label>                               
        <g:checkBox class="checkBxLabelLeft" name="donatedOther" id="d6" value="${tissueRecoveryGtexInstance?.donatedOther}" /><label class="no-bold" for="d6">Other, please specify</label>
      </div>
      <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'donatedOrganOther', 'errors')}">
        <span class="depends-on" data-id="d6">
          <label for="d7">Other:</label><g:textField  id="d7" name="donatedOrganOther" class="dependent-focus dependent-clear" value="${tissueRecoveryGtexInstance?.donatedOrganOther}" />
        </span>
      </div>
    </div>
  </g:if>            
  <g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'SURGI'}">   
    <div class="div-t-r clearfix">
        <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'amputationType', 'errors')}"><span class="label-maintain"><label for="amputationType">Amputation type:</label></span>
        <g:select name="amputationType" from="${['','Above the knee amputation','Below the knee amputation']}" value="${tissueRecoveryGtexInstance.amputationType}" /></div> 
        <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'surgeryStartTime', 'errors')}"><label for="surgeryStartTime">Surgery start time:</label>
        <g:textField name="surgeryStartTime" value="${tissueRecoveryGtexInstance?.surgeryStartTime}" class="timeEntry" /></div>            
    </div>           
  </g:if> 
  <g:if test="${tissueRecoveryGtexInstance.caseRecord.bss.code.contains('RPCI') && tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code.equals('POSTM')}">
    <!-- do nothing here -->
  </g:if>
  <g:else>
    <div class="div-t-r clearfix"><div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'coreBodyTemp', 'errors')}"><span class="label-maintain"><label for="coreBodyTemp">Core body temperature:</label></span>
          <g:hiddenField maxlength="4" name="coreBodyTemp" value="${tissueRecoveryGtexInstance?.coreBodyTemp}" /> 
          <input type="text" id="coreBodyTempStr" class="numFloat" name="coreBodyTempStr" value="${tissueRecoveryGtexInstance?.coreBodyTemp == -1000?'':tissueRecoveryGtexInstance?.coreBodyTemp}" />
        </div>
        <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'coreBodyTempScale', 'errors')}"><label for="coreBodyTempScale">Temperature scale (Fahrenheit or Celsius):</label> 
          <g:select name="coreBodyTempScale" from="${['F','C']}" value="${tissueRecoveryGtexInstance.coreBodyTempScale}" /></div>
    </div>
    <div class="div-t-r clearfix">
        <div id="coreBodyTempLocHelperParent" class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'coreBodyTempLoc', 'errors')}"><span class="label-maintain"><label for="coreBodyTempLoc">Temperature obtained via:</label></span> 
          <g:select id="coreBodyTempLocHelper" name="coreBodyTempLocHelper" from="${['Select','Thermometer - rectal','Thermometer - organ', 'Anesthesia probe','Other']}" value="${tissueRecoveryGtexInstance?.coreBodyTempLoc}"/></div>
        <div id="coreBodyTempLocParent" data-id="coreBodyTempLocHelper" data-select="Other" class="depends-on left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'coreBodyTempLoc', 'errors')}">
          <label for="coreBodyTempLocOther">Other:</label><g:textField class="dependent-focus" name="coreBodyTempLoc" value="${tissueRecoveryGtexInstance?.coreBodyTempLoc}" /></div>
    </div>
  </g:else>
  <div class="div-t-r clearfix"><div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'dataEnteredBy', 'errors')}"><span class="label-maintain"><label for="dataEnteredBy">Data entered by:</label></span>
      <g:textField name="dataEnteredBy" value="${tissueRecoveryGtexInstance?.dataEnteredBy}" /></div>
    <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'teamLeader', 'errors')}"><label for="teamLeader">Team leader:</label>
      <g:textField name="teamLeader" value="${tissueRecoveryGtexInstance?.teamLeader}" /></div>
  </div>
  <div class="div-t-r clearfix">
    <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'teamLeadVeriDate', 'errors')}"><span class="label-maintain"><label for="teamLeadVeriDate">Team lead verification date:</label></span>
    <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="teamLeadVeriDate" value="${tissueRecoveryGtexInstance?.teamLeadVeriDate}"  /></div>
  </div>
  <div class="div-t-r clearfix prosector">
      <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector1', 'errors')}">
        <span class="label-maintain"><label for="prosector1">1st prosector initials:</label></span>
        <g:textField name="prosector1" value="${tissueRecoveryGtexInstance?.prosector1}" />
      </div>
      <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector2', 'errors')}">
        <span class="label-maintain"><label for="prosector2">2nd prosector initials:</label></span>
        <g:textField name="prosector2" value="${tissueRecoveryGtexInstance?.prosector2}" />
      </div>
   </div>
   <div class="div-t-r clearfix prosector">
      <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector3', 'errors')}">
        <span class="label-maintain"><label for="prosector3">3rd prosector initials:</label></span>
        <g:textField name="prosector3" value="${tissueRecoveryGtexInstance?.prosector3}" />
      </div>
      <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector4', 'errors')}">
        <span class="label-maintain"><label for="prosector4">4th prosector initials:</label></span>
        <g:textField name="prosector4" value="${tissueRecoveryGtexInstance?.prosector4}" />
      </div>   
  </div>
  <div class="div-t-r clearfix prosector">
      <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector5', 'errors')}">
        <span class="label-maintain"><label for="prosector5">5th prosector initials:</label></span>
        <g:textField name="prosector5" value="${tissueRecoveryGtexInstance?.prosector5}" />
      </div>
      <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector6', 'errors')}">
        <span class="label-maintain"><label for="prosector6">6th prosector initials:</label></span>
        <g:textField name="prosector6" value="${tissueRecoveryGtexInstance?.prosector6}" />
      </div>
  </div>
  <div class="div-t-r clearfix prosector">
      <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector7', 'errors')}">
        <span class="label-maintain"><label for="prosector7">7th prosector initials:</label></span>
        <g:textField name="prosector7" value="${tissueRecoveryGtexInstance?.prosector7}" />
      </div>
      <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'prosector8', 'errors')}">
        <span class="label-maintain"><label for="prosector8">8th prosector initials:</label></span>
        <g:textField name="prosector8" value="${tissueRecoveryGtexInstance?.prosector8}" />
      </div>
  </div>
  <div class="clear"></div>
  <div class="div-t-r clearfix">
      <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'restriction', 'errors')}">
        <label for="restriction">Procurement Site Restriction:</label><br />
        <g:textArea class="textwide" name="restriction" value="${tissueRecoveryGtexInstance?.restriction}" />
      </div>
  </div>
  <%-- frozens not collected at RPCI --%>
  <g:if test="${frozenCaseNum < 5 && tissueRecoveryGtexInstance.caseRecord.bss.parentBss.code != 'RPCI'}">
    <div class="div-t-r clearfix ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'reasonReqMinFrznNotCol', 'errors')}"><div class="left"><label for="reasonReqMinFrznNotCol">Reason for not collecting required minimum frozen specimens:</label><br />
      <g:textArea class="textwide" name="reasonReqMinFrznNotCol" value="${tissueRecoveryGtexInstance?.reasonReqMinFrznNotCol}" /></div>
    </div>
  </g:if>
  <div class="clear"></div>
</div>

<div id="sect-sop" class="div-t">
    <div class="div-t-r div-t-h">Relevant SOP Versions</div>
    <div class="div-t-r clearfix">
        <div class="left width-55"><label for="op0001Ver">OP-0001 GTEx Kit Receipt, Supplies, and Shipping Procedure:</label></div>
        <div class="left"><g:select name="op0001Ver" from="${sopVerLists?.get('op0001')}" value="${tissueRecoveryGtexInstance.op0001?.sopVersion}" noSelection="['':'']" /></div>
        <div class="left width-55">&nbsp;&nbsp;&nbsp;&nbsp;<label for="deviationOp0001">OP-0001 Memo or Approved Deviation #*:</label></div>
        <div class="left"><g:textField class="trfdevi" name="memoCiNumOp0001" value="${tissueRecoveryGtexInstance?.deviationOp0001?.memoCiNum}" /></div>        
    </div>
    <div class="div-t-r clearfix">
        <div class="left width-55"><label for="pr0004Ver">PR-0004 GTEx Tissue Procurement Procedure:</label></div>
        <div class="left"><g:select name="pr0004Ver" from="${sopVerLists?.get('pr0004')}" value="${tissueRecoveryGtexInstance.pr0004?.sopVersion}" noSelection="['':'']" /></div>
        <div class="left width-55">&nbsp;&nbsp;&nbsp;&nbsp;<label for="deviationPr0004">PR-0004 Memo or Approved Deviation #*:</label></div>  
        <div class="left"><g:textField class="trfdevi" name="memoCiNumPr0004" value="${tissueRecoveryGtexInstance?.deviationPr0004?.memoCiNum}" /></div>             
    </div>
    <div class="div-t-r clearfix">
        <div class="left width-55"><label for="pm0003Ver">PM-0003 GTEx CRF Completion:</label></div>
        <div class="left"><g:select name="pm0003Ver" from="${sopVerLists?.get('pm0003')}" value="${tissueRecoveryGtexInstance.pm0003?.sopVersion}" noSelection="['':'']" /></div>
        <div class="left width-55">&nbsp;&nbsp;&nbsp;&nbsp;<label for="deviationPm0003">PM-0003 Memo or Approved Deviation #*:</label></div>  
        <div class="left"><g:textField class="trfdevi" name="memoCiNumPm0003" value="${tissueRecoveryGtexInstance?.deviationPm0003?.memoCiNum}" /></div>           
    </div>
    <div class="clear"></div>
    <div class="foot-notes">*If Memo or Approved Deviation from SOP, please enter Memo # or AD # here.</div>
    <div class="clear"></div>
</div>

<div id="sect-add-aqua" class="div-t">
  <div class="div-t-r div-t-h">Additional Tissue Recovery Data*</div>
    <div class="div-t-r clearfix">
        <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'firstBloodDrawDate', 'errors')}">
          <span class="label-maintain"><label for="firstBloodDrawDate">First blood draw date:</label></span>
          <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="firstBloodDrawDate" value="${tissueRecoveryGtexInstance?.firstBloodDrawDate}" />
        </div>
        <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'firstBloodDrawTime', 'errors')}">
          <span class="label-maintain"><label for="firstBloodDrawTime">First blood draw time:</label></span>
          <g:textField name="firstBloodDrawTime" value="${tissueRecoveryGtexInstance?.firstBloodDrawTime}" class="timeEntry" />
        </div>
    </div>
    <div class="div-t-r clearfix">
        <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'firstTissueRemovedDate', 'errors')}">
            <span class="label-maintain"><label for="firstTissueRemovedDate">First tissue removed date:</label></span>
            <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="firstTissueRemovedDate" value="${tissueRecoveryGtexInstance?.firstTissueRemovedDate}" />
        </div>
        <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'firstTissueRemovedTime', 'errors')}">
          <span class="label-maintain"><label for="firstTissueRemovedTime">First tissue removed time:</label></span>
            <g:textField name="firstTissueRemovedTime" value="${tissueRecoveryGtexInstance?.firstTissueRemovedTime}" class="timeEntry" />
        </div>
    </div>
    <div class="div-t-r clearfix">
        <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'firstTissueRemoved', 'errors')}">
            <span class="label-maintain"><label for="firstTissueRemoved">First tissue removed:</label></span>
            <g:select id="firstTissueRemoved" name="firstTissueRemoved.id" from="${filteredTissueList}" optionKey="id" value="${tissueRecoveryGtexInstance.firstTissueRemoved?.id}" noSelection="['0': 'Choose Tissue Type']"  />
        </div>
        <div class="left"></div>
    </div>
    <div class="clear"></div>
    <div class="foot-notes">*Please enter this additional data to help ensure data accuracy.</div>
    <div class="clear"></div>
</div>

<g:if test="${tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code != 'SURGI' && brainFlag == true}">
<div id="sect-add-green" class="div-t">
    <div class="div-t-r div-t-h green-kit-header">Additional Green Kit Data</div>
    <div class="div-t-r clearfix">
      <label for="ventLess24hrs">Did site receive verbal confirmation of ventilator status <24 hours prior to collection start?</label>
      <g:select id="${tissueRecoveryGtexInstance.id}" name="ventLess24hrs" from="${['','Yes','No']}" value="${tissueRecoveryGtexInstance.ventLess24hrs}" />
    </div>
    <div class="clear"></div>
</div>
</g:if> 


<g:if test="${frozenCaseNum > 0}">
<div id="sect-add-pink" class="div-t">
    <div class="div-t-r div-t-h pink-kit-header">Additional Pink Kit Data</div>
    <div class="div-t-r clearfix">
        <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'dateInDryIce', 'errors')}">
          <span class="label-maintain"><label for="dateInDryIce">Date placed in dry ice for transport:</label></span>
          <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateInDryIce" value="${tissueRecoveryGtexInstance?.dateInDryIce}"  />
        </div>
        <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'timeInDryIce', 'errors')}">
          <span class="label-maintain"><label for="timeInDryIce">Time placed in dry ice for transport:</label></span>
          <g:textField name="timeInDryIce" value="${tissueRecoveryGtexInstance?.timeInDryIce}" class="timeEntry" />
        </div>
    </div>
    <div class="div-t-r clearfix">
        <div class="left width-55 ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'dateInMinus80', 'errors')}">
          <span class="label-maintain"><label for="dateInMinus80">Date placed in -80&deg;C storage:</label></span>
          <g:jqDatePicker LDSOverlay="${bodyclass ?: ''}" name="dateInMinus80" value="${tissueRecoveryGtexInstance?.dateInMinus80}"  />
        </div>
        <div class="left ${hasErrors(bean: tissueRecoveryGtexInstance, field: 'timeInMinus80', 'errors')}">
          <span class="label-maintain"><label for="timeInMinus80">Time placed in -80&deg;C storage:</label></span>
          <g:textField name="timeInMinus80" value="${tissueRecoveryGtexInstance?.timeInMinus80}" class="timeEntry" />
        </div>
    </div>
    <div class="clear"></div>
</div>
</g:if> 
