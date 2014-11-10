<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.ldacc.Donor" %>

<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>

<%@ page import="nci.obbr.cahub.util.ActivityEvent" %>
<%@ page import="nci.obbr.cahub.staticmembers.ActivityType" %>
<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>


<tr class="prop"><td valign="top" class="name formheader" colspan="2">CRF Status:</td></tr>
<tr>
    <td valign="top" class="value" colspan="2">
      <div class="list">
        <table>
          <thead>
            <tr>
              <th>Form</th>
              <th>Status</th>
              <th>Timeframe for form completion</th>
              <th>Date Submitted</th>
            </tr>
          </thead>
          <tbody>

            <tr class="odd ${session.bpvCaseStatus?.hideBlood ? 'hide' : ''}">
              <td>${FormMetadata.findByCode('BLOOD')?.cdrFormName}</td>
              <td>
          <g:if test="${session.bpvCaseStatus.blood == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvBloodForm/save?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('BLOOD')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.blood == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvBloodForm" action="edit" id="${caseRecordInstance.bpvBloodForm.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvBloodForm" action="show" id="${caseRecordInstance.bpvBloodForm.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.blood == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvBloodForm" action="show" id="${caseRecordInstance.bpvBloodForm.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('BLOOD')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvBloodForm?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvBloodForm?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvBloodForm?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>
          </td>
          </tr>

          <tr class="even ${session.bpvCaseStatus?.hideTissueGrossEvaluation ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('GROSSEVA')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.tissueGrossEvaluation == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvTissueGrossEvaluation/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('GROSSEVA')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.tissueGrossEvaluation == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvTissueGrossEvaluation" action="edit" id="${caseRecordInstance.bpvTissueGrossEvaluation.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvTissueGrossEvaluation" action="show" id="${caseRecordInstance.bpvTissueGrossEvaluation.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.tissueGrossEvaluation == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvTissueGrossEvaluation" action="show" id="${caseRecordInstance.bpvTissueGrossEvaluation.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('GROSSEVA')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvTissueGrossEvaluation?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvTissueGrossEvaluation?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvTissueGrossEvaluation?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>
          </td>
          </tr>

          <tr class="odd ${session.bpvCaseStatus?.hideSurgeryAnesthesia ? 'hide' : ''}">
            <td>
          <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
${FormMetadata.findByCode('SURGERYK')?.cdrFormName}
          </g:if>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
${FormMetadata.findByCode('SURGERYO')?.cdrFormName}
          </g:elseif>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
${FormMetadata.findByCode('SURGERYL')?.cdrFormName}
          </g:elseif>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
${FormMetadata.findByCode('SURGERYC')?.cdrFormName}
          </g:elseif>
          </td>
          <td>
          <g:if test="${session.bpvCaseStatus.surgeryAnesthesia == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
                <a href="/cahubdataservices/bpvSurgeryAnesthesiaForm/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SURGERYK')?.id}">(Start)</a>
              </g:if>
              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
                <a href="/cahubdataservices/bpvSurgeryAnesthesiaForm/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SURGERYO')?.id}">(Start)</a>
              </g:elseif>
              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
                <a href="/cahubdataservices/bpvSurgeryAnesthesiaForm/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SURGERYL')?.id}">(Start)</a>
              </g:elseif>
              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
                <a href="/cahubdataservices/bpvSurgeryAnesthesiaForm/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SURGERYC')?.id}">(Start)</a>
              </g:elseif>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.surgeryAnesthesia == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvSurgeryAnesthesiaForm" action="edit" id="${caseRecordInstance.bpvSurgeryAnesthesiaForm.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvSurgeryAnesthesiaForm" action="show" id="${caseRecordInstance.bpvSurgeryAnesthesiaForm.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.surgeryAnesthesia == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvSurgeryAnesthesiaForm" action="show" id="${caseRecordInstance.bpvSurgeryAnesthesiaForm.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>
          <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
${FormMetadata.findByCode('SURGERYK')?.timeConstraintLabel}
          </g:if>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
${FormMetadata.findByCode('SURGERYO')?.timeConstraintLabel}
          </g:elseif>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
${FormMetadata.findByCode('SURGERYL')?.timeConstraintLabel}
          </g:elseif>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
${FormMetadata.findByCode('SURGERYC')?.timeConstraintLabel}
          </g:elseif>
          </td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvSurgeryAnesthesiaForm?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvSurgeryAnesthesiaForm?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvSurgeryAnesthesiaForm?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span> 
          </td>
          </tr>

          <tr class="even ${session.bpvCaseStatus?.hideTissueReceiptDissection ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('DISSECT')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.tissueReceiptDissection == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvTissueReceiptDissection/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('DISSECT')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.tissueReceiptDissection == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvTissueReceiptDissection" action="edit" id="${caseRecordInstance.bpvTissueReceiptDissection.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvTissueReceiptDissection" action="show" id="${caseRecordInstance.bpvTissueReceiptDissection.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.tissueReceiptDissection == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvTissueReceiptDissection" action="show" id="${caseRecordInstance.bpvTissueReceiptDissection.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('DISSECT')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvTissueReceiptDissection?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvTissueReceiptDissection?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvTissueReceiptDissection?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>
          </td>
          </tr>

          <tr class="odd ${session.bpvCaseStatus?.hideWorkSheet ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('WORKSHEET')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.workSheet == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvWorkSheet/save?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('WORKSHEET')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.workSheet == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvWorkSheet" action="edit" id="${caseRecordInstance.bpvWorkSheet.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvWorkSheet" action="view" id="${caseRecordInstance.bpvWorkSheet.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.workSheet == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvWorkSheet" action="view" id="${caseRecordInstance.bpvWorkSheet.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('WORKSHEET')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvWorkSheet?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvWorkSheet?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvWorkSheet?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>  
          </td>
          </tr>

          <tr class="even ${session.bpvCaseStatus?.hideTissueProcessEmbed ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('PROCESS')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.tissueProcessEmbed == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvTissueProcessEmbed/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('PROCESS')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.tissueProcessEmbed == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvTissueProcessEmbed" action="edit" id="${caseRecordInstance.bpvTissueProcessEmbed.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvTissueProcessEmbed" action="show" id="${caseRecordInstance.bpvTissueProcessEmbed.id}">(View)</g:link> 
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.tissueProcessEmbed == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvTissueProcessEmbed" action="show" id="${caseRecordInstance.bpvTissueProcessEmbed.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('PROCESS')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvTissueProcessEmbed?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvTissueProcessEmbed?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvTissueProcessEmbed?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>
          </td>
          </tr>

          <tr class="odd ${session.bpvCaseStatus?.hideSlidePrep ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('SECTION')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.slidePrep == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvSlidePrep/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SECTION')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.slidePrep == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvSlidePrep" action="edit" id="${caseRecordInstance.bpvSlidePrep?.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvSlidePrep" action="show" id="${caseRecordInstance.bpvSlidePrep?.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.slidePrep == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvSlidePrep" action="show" id="${caseRecordInstance.bpvSlidePrep?.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('SECTION')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance?.bpvSlidePrep?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvSlidePrep?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvSlidePrep?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span> 
          </td>
          </tr>

          <tr class="even ${session.bpvCaseStatus?.hideFinalSurgicalPath ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('FINAL')?.cdrFormName}</td>
            <td>
          <g:if test="${!caseRecordInstance.finalSurgicalPath}">
            <span class="no filelabel">Not Uploaded</span>
            <g:if test="${canModify}">
              <g:link controller="bpvLocalPathReview" action="upload" id="${caseRecordInstance.id}" class="uibutton removepadding" title="Upload"><span class="ui-icon ui-icon-circle-arrow-n">Upload</span></g:link>
            </g:if>
          </g:if>
          <g:else>
            <span class="yes">Uploaded</span>
            &nbsp;<g:link controller="bpvLocalPathReview" action="download" id="${caseRecordInstance.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></g:link>
            <g:if test="${canModify}">
              &nbsp;<g:link controller="bpvLocalPathReview" action="remove" id="${caseRecordInstance.id}" onclick="return confirm('Are you sure to remove the file?')" class="uibutton removepadding" title="Delete"><span class="ui-icon ui-icon-trash">Delete</span></g:link>
            </g:if>
          </g:else>
          </td>
          <td>${FormMetadata.findByCode('FINAL')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.dateFspUploaded}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.fspUploadedBy}</b>"><g:if test="${caseRecordInstance.fspUploadedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>
          </td>
          </tr>

          <tr class="odd ${session.bpvCaseStatus?.hideClinicalDataEntry ? 'hide' : ''}">
            <td>
          <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
${FormMetadata.findByCode('CLINICK')?.cdrFormName}
          </g:if>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
${FormMetadata.findByCode('CLINICO')?.cdrFormName}
          </g:elseif>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
${FormMetadata.findByCode('CLINICL')?.cdrFormName}
          </g:elseif>
          <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
${FormMetadata.findByCode('CLINICC')?.cdrFormName}
          </g:elseif>
          </td>
          <td>
          <g:if test="${session.bpvCaseStatus.clinicalDataEntry == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
                <a href="/cahubdataservices/bpvClinicalDataEntry/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('CLINICK')?.id}">(Start)</a>
              </g:if>
              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
                <a href="/cahubdataservices/bpvClinicalDataEntry/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('CLINICO')?.id}">(Start)</a>
              </g:elseif>
              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
                <a href="/cahubdataservices/bpvClinicalDataEntry/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('CLINICL')?.id}">(Start)</a>
              </g:elseif>
              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
                <a href="/cahubdataservices/bpvClinicalDataEntry/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('CLINICC')?.id}">(Start)</a>
              </g:elseif>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.clinicalDataEntry == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvClinicalDataEntry" action="edit" id="${caseRecordInstance.bpvClinicalDataEntry.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvClinicalDataEntry" action="show" id="${caseRecordInstance.bpvClinicalDataEntry.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.clinicalDataEntry == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvClinicalDataEntry" action="show" id="${caseRecordInstance.bpvClinicalDataEntry.id}">(View)</g:link>
          </g:elseif>
          </td>
          <g:if test="${session.bpvCaseStatus.stopCondition1}">
            <td>Within 7 days of case stopped</td>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.stopCondition2 || session.bpvCaseStatus.stopCondition3}">
            <td>Within 24-48 hours of case stopped</td>
          </g:elseif>
          <g:else>
            <td>
            <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
${FormMetadata.findByCode('CLINICK')?.timeConstraintLabel}
            </g:if>
            <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
${FormMetadata.findByCode('CLINICO')?.timeConstraintLabel}
            </g:elseif>
            <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
${FormMetadata.findByCode('CLINICL')?.timeConstraintLabel}
            </g:elseif>
            <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
${FormMetadata.findByCode('CLINICC')?.timeConstraintLabel}
            </g:elseif>
            </td>
          </g:else>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvClinicalDataEntry?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvClinicalDataEntry?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvClinicalDataEntry?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>  
          </td>
          </tr>

          <tr class="even ${session.bpvCaseStatus?.hideCaseQualityReview ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('QUALITY')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.caseQualityReview == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify}">
              <a href="/cahubdataservices/bpvCaseQualityReview/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('QUALITY')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.caseQualityReview == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify}">
              <g:link controller="bpvCaseQualityReview" action="edit" id="${caseRecordInstance.bpvCaseQualityReview.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvCaseQualityReview" action="show" id="${caseRecordInstance.bpvCaseQualityReview.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.caseQualityReview == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvCaseQualityReview" action="show" id="${caseRecordInstance.bpvCaseQualityReview.id}">(View)</g:link>
          </g:elseif>
          </td>
          <g:if test="${session.bpvCaseStatus.stopCondition1}">
            <td>Within 7 days of case stopped</td>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.stopCondition2 || session.bpvCaseStatus.stopCondition3}">
            <td>Within 24-48 hours of case stopped</td>
          </g:elseif>
          <g:else>
            <td>${FormMetadata.findByCode('QUALITY')?.timeConstraintLabel}</td>
          </g:else>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance.bpvCaseQualityReview?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvCaseQualityReview?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvCaseQualityReview?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span>
          </td>
          </tr>
          
          <!-- pmh 04/02/14 hub req 862 -->
          
            <tr class="odd ${session.bpvCaseStatus?.hideFollowUp ? 'hide' : ''}">
            <td>${FormMetadata.findByCode('6MFUP')?.cdrFormName}</td>
            <td>
          <g:if test="${session.bpvCaseStatus.sixMonthFollowUp == 1}">
            <span class="no">Not Started</span>
            <g:if test="${canModify6MFUP}">
              <a href="/cahubdataservices/bpvSixMonthFollowUp/create?caseRecord.id=${caseRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('6MFUP')?.id}">(Start)</a>
            </g:if>
          </g:if>
          <g:elseif test="${session.bpvCaseStatus.sixMonthFollowUp == 2}">
            <span class="incomplete">In Progress</span>
            <g:if test="${canModify6MFUP}">
              <g:link controller="bpvSixMonthFollowUp" action="edit" id="${caseRecordInstance.bpvSixMonthFollowUp?.id}">(Edit)</g:link>
            </g:if>
            <g:else>
              <g:link controller="bpvSixMonthFollowUp" action="show" id="${caseRecordInstance.bpvSixMonthFollowUp?.id}">(View)</g:link>
            </g:else>
          </g:elseif>
          <g:elseif test="${session.bpvCaseStatus.sixMonthFollowUp == 3}">
            <span class="yes">Completed</span>
            <g:link controller="bpvSixMonthFollowUp" action="show" id="${caseRecordInstance.bpvSixMonthFollowUp?.id}">(View)</g:link>
          </g:elseif>
          </td>
          <td>${FormMetadata.findByCode('6MFUP')?.timeConstraintLabel}</td>
          <td>
          <g:formatDate format="yyyy-MM-dd HH:mm" date="${caseRecordInstance?.bpvSixMonthFollowUp?.dateSubmitted}"/>
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bpvSixMonthFollowUp?.submittedBy}</b>"><g:if test="${caseRecordInstance.bpvSixMonthFollowUp?.submittedBy}"><span class="uibutton removepadding borderless"><span class="ui-icon ui-icon-person"></span></span></g:if></span> 
          </td>
          </tr>
           <!-- END pmh 04/02/14 hub req 862 -->

          </tbody>
        </table>
      </div>
    </td>
  </tr>
