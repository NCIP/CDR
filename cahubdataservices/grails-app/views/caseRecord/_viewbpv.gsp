<%--THIS IS THE BSS FACING PAGE--%>
<%@ page import="nci.obbr.cahub.ldacc.Donor" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>

<%@ page import="nci.obbr.cahub.util.ActivityEvent" %>
<%@ page import="nci.obbr.cahub.staticmembers.ActivityType" %>
<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>


<g:set var="caseStatus" value="${caseRecordInstance.caseStatus.code}"/>
<g:set var="canModify" value="${caseRecordInstance?.candidateRecord?.isEligible && caseRecordInstance?.candidateRecord?.isConsented && (caseRecordInstance?.caseStatus?.code == 'INIT' || caseRecordInstance?.caseStatus?.code == 'DATA' || caseRecordInstance?.caseStatus?.code == 'REMED')}" />
<g:set var="canUpload" value="${caseRecordInstance?.candidateRecord?.isEligible && caseRecordInstance?.candidateRecord?.isConsented}" />
<g:set var="canModify6MFUP" value="${caseRecordInstance?.candidateRecord?.isEligible && caseRecordInstance?.candidateRecord?.isConsented}" />
<g:if test="${caseRecordInstance?.caseStatus?.code == 'DATA' && caseRecordInstance?.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date())}">
  <g:warnings warningmap="${['':'This case has had the status \'Data Entry Underway\' for more than 21 days post-surgery']}" />
</g:if>
<g:set var="fcount" value="${1}" />
<div class="dialog">
  <table border="0">
    <tbody>

      <tr class="prop toptable">
        <td valign="top" class="name">Case ID:</td>
        <td valign="top" class="value" id="caseRecordID">${caseRecordInstance.caseId}</td>
      </tr>

      <tr class="prop toptable">
        <td valign="top" class="name">Case Status:</td>
        <td valign="top" class="value" >
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>
    <g:if test="${caseRecordInstance.candidateRecord?.isConsented == true && caseRecordInstance.candidateRecord?.isEligible == true &&
(caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP') &&
caseRecordInstance?.bpvCaseQualityReview?.dateSubmitted != null}">
      <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
    </g:if>
    </td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name">BSS:</td>
      <td valign="top" class="value">
${caseRecordInstance?.bss.name} (${caseRecordInstance?.bss.code})
      </td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name">Primary Organ:</td>
      <td valign="top" class="value">
${caseRecordInstance.primaryTissueType}  
      </td>
    </tr>            

    <tr class="prop toptable">
      <td valign="top" class="name">Tissue Bank ID:</td>
      <td valign="top" class="value">${caseRecordInstance?.tissueBankId}</td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name">Candidate ID:</td>
      <td valign="top" class="value">
    <g:link controller="candidateRecord" action="view" id="${caseRecordInstance.candidateRecord?.id}">${caseRecordInstance.candidateRecord?.candidateId}</g:link>
    </td>
    </tr> 

    <tr class="prop toptable">
      <td valign="top" class="name">Study:</td>
      <td valign="top" class="value">${caseRecordInstance.study}</td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.kits.label" default="Kits Used:" /></td>
    <td valign="top" style="text-align: left;" class="value">
    <g:if test="${caseRecordInstance.kitList}">
${caseRecordInstance.kitList.replace(',',', ')}
    </g:if>
    <g:else>&nbsp;</g:else>
<%--                
<ul>
<g:each in="${caseRecordInstance.kitList?.split(',')}" var="k">
<li><g:link controller="kitRecord" action="show" id="${k}">${k}</g:link></li>
</g:each>
</ul>
--%>                                
    </td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.lastUpdated.label" default="Last Updated:" /></td>
    <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.lastUpdated}" /></td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.dateCreated.label" default="Date Created:" /></td>
    <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.dateCreated}" /></td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.caseEmail.label" default="Specimen Data to CBR:" /></td>
    <td valign="top" class="value">
      <div id="emailCaseBtn">
        <g:render template="sendCaseEmail" 
                  model="[id:caseRecordInstance.id, caseStatus:caseStatus, emailSent:ActivityEvent.findByCaseIdAndActivityType(caseRecordInstance.caseId,ActivityType.findByCode('EMAIL'))]"/>
      </div>
    </td>
    </tr>


    <g:if test="${session.DM == true}">
      <tr class="prop toptable">

        <td valign="top" class="name">Recall Case:</td>
      <g:if test="${!caseRecordInstance.caseWithdraw?.id}">
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}" onclick="return confirm(' Are you sure you want to recall this case?')">Start Recall process </g:link></td>
      </g:if>
      <g:elseif test="${caseRecordInstance.caseWithdraw?.finalCompleteDate}">
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall completed</g:link></td>
      </g:elseif>
      <g:else>
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall in progress</g:link></td>
      </g:else>

      </tr>

    </g:if>

    <tr class="prop toptable">
      <td valign="top" class="name">Query Tracker:</td>
      <td valign="top" class="value">
        <a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
      </td>
    </tr>

    <tr class="prop"><td valign="top" class="name formheader" colspan="2">Uploaded Files:</td></tr>
    <tr>
      <td valign="top" class="value" colspan="2">
    <g:if test="${caseRecordInstance.files}">
      <div class="list">
        <table>
          <thead>
            <tr>
              <th>File Name</th>
              <th class="dateentry">Date Uploaded</th>
              <th>Category</th>
          <g:if test="${session.org.code == 'OBBR'}">
            <th><nobr> Hide From BSS</nobr></th>
          </g:if>
          <th>Comments</th>
          <th>Action</th>

          </tr>
          </thead>
          <tbody>
          <g:each in="${caseRecordInstance.files}" status="i" var="fileUploadInstance">
            <!--pmh change 08/19/13 -->
            <g:if test="${fileUploadInstance.hideFromBss == true && session.org.code != 'OBBR'}"> <!--dont show file for a BSS if 'hide from BSS' is selected --></g:if>
            <g:else>
              <g:set var="fcount" value="${fcount + 1}" />
              <tr class="${(fcount % 2) == 0 ? 'odd' : 'even'}">
                <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
              <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>
              <td><nobr>${fileUploadInstance.category}</nobr></td>
              <g:if test="${session.org.code == 'OBBR'}">
                <td><nobr>${fileUploadInstance.hideFromBss ?'Yes':'No'}</nobr></td>
              </g:if>
              <td class="unlimitedstr"><div>${fieldValue(bean: fileUploadInstance, field: "comments")}</div></td>
              <g:if test="${canModify}">
                <td nowrap>
              <!--  <g:link controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')">Remove</g:link> -->
                <g:if test="${(fileUploadInstance?.bssCode?.equals(session.org.code) || session.org.code == 'OBBR' ) &&(caseRecordInstance.caseStatus?.code == 'DATA' || caseRecordInstance.caseStatus?.code == 'DATACOMP' || caseRecordInstance.caseStatus?.code == 'REMED')}">
                  <g:link class="ui-button ui-state-default ui-corner-all removepadding" title="edit" controller="fileUpload" action="show" id="${fileUploadInstance.id}" ><span class="ui-icon ui-icon-pencil">Edit</span></g:link>
                  <g:link class="ui-button ui-state-default ui-corner-all removepadding" title="delete" controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')"><span class="ui-icon ui-icon-trash">Remove</span></g:link>
                </g:if>
                </td>
              </g:if>
              </tr>
            </g:else>
            <!--END pmh change 08/19/13 -->
          </g:each>
          </tbody>                                
        </table>
      </div>
    </g:if>
    <g:if test="${canUpload}">
      <a class="uibutton" href="/cahubdataservices/fileUpload/create?caseRecord.id=${caseRecordInstance.id}" title="Upload case documents" />
      <span class="ui-icon ui-icon-circle-arrow-n"></span>Upload
      </a>
    </g:if>
    </td>
    </tr>

    <tr class="prop"><td valign="top" class="name formheader" colspan="2">Query Tracker Attachments:</td></tr>
    <tr>
      <td valign="top" class="value" colspan="2">
    <g:if test="${qtAttachments}">
      <div class="list">
        <table>
          <thead>
            <tr>
              <th>File Name</th>
              <th class="dateentry">Date Uploaded</th>
              <th>Category</th>
          <g:if test="${session.org.code == 'OBBR'}">
            <th><nobr> Hide From BSS</nobr></th>
          </g:if>
          <th>Comments</th>
          </tr>
          </thead>
          <tbody>
          <g:each in="${qtAttachments}" status="i" var="qtAttachment">
            <!--pmh change 08/19/13 -->
            <g:if test="${qtAttachment.hideFromBss == true && session.org.code != 'OBBR'}"><!--dont show file for a BSS if 'hide from BSS' is selected --> </g:if>
            <g:else>
              <g:set var="fcount" value="${fcount + 1}" />
              <tr class="${(fcount % 2) == 0 ? 'odd' : 'even'}">
                <td><g:link controller="fileUpload" action="download" id="${qtAttachment.id}">${qtAttachment.fileName}</g:link></td>
              <td><nobr>${qtAttachment.uploadTime}</nobr></td>
              <td><nobr>${qtAttachment.category}</nobr></td>
              <g:if test="${session.org.code == 'OBBR'}">
                <td><nobr>${qtAttachment.hideFromBss ?'Yes':'No'}</nobr></td>
              </g:if>
              <td class="unlimitedstr"><div>${fieldValue(bean: qtAttachment, field: "comments")}</div></td>
              </tr>
            </g:else>
            <!--END pmh change 08/19/13 -->
          </g:each>
          </tbody>
        </table>
      </div>
    </g:if>
    </td>
    </tr>

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
                <g:link controller="bpvSlidePrep" action="edit" id="${caseRecordInstance?.bpvSlidePrep?.id}">(Edit)</g:link>
              </g:if>
              <g:else>
                <g:link controller="bpvSlidePrep" action="show" id="${caseRecordInstance?.bpvSlidePrep?.id}">(View)</g:link>
              </g:else>
            </g:elseif>
            <g:elseif test="${session.bpvCaseStatus.slidePrep == 3}">
              <span class="yes">Completed</span>
              <g:link controller="bpvSlidePrep" action="show" id="${caseRecordInstance?.bpvSlidePrep?.id}">(View)</g:link>
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
                &nbsp;<g:link class="removeLink" controller="bpvLocalPathReview" action="remove" id="${caseRecordInstance.id}" onclick="return confirm('Are you sure to remove the file?')" class="uibutton removepadding" title="Delete"><span class="ui-icon ui-icon-trash">Delete</span></g:link>
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

    <tr class="prop"><td valign="top" class="name formheader" colspan="2">Specimens (${caseRecordInstance.specimens.size()}):</td></tr>
    <tr>
      <td valign="top" colspan="2" class="value">
        <div class="list">
          <table class="nowrap">
            <thead>
              <tr>
                <th>1st Gen</th>
                <th>2nd Gen</th>
                <th>3rd Gen</th>
                <th>Tissue Type</th>
                <th>Priority</th> 
                <th>Fixation<br />Protocol</th>                          
                <th>Fixative</th>
                <th>Container</th>
                <th>Consumed?</th>
                <th>Slides</th>
                <th>Local Path Review</th>                        
              </tr>
            </thead>
            <tbody>
<%
def parity = 0
%>
            <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
              <g:if test="${!s.parentSpecimen}">
                <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}">
                  <td class="specimenid">${s.specimenId}</td>
                  <td></td>
                  <td></td>
                  <td>${s.tissueType}</td>
                <g:if test="${altMap.get(s.specimenId)=='PRIORITY1'}">
                  <td>I</td>
                </g:if>
                <g:elseif test="${altMap.get(s.specimenId)=='PRIORITY2'}">
                  <td>II</td>
                </g:elseif>
                <g:elseif test="${altMap.get(s.specimenId)=='PRIORITY3a'}">
                  <td>IIIA</td>
                </g:elseif>
                <g:elseif test="${altMap.get(s.specimenId)=='PRIORITY3b'}">
                  <td>IIIB</td>
                </g:elseif>
                <g:else><td>&nbsp;</td></g:else>
                <td>${s.protocol?.name}</td>
                <td>${s.fixative}</td>
                <td>${s.containerType}</td>
                <td><g:if test="${s.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                <td nowrap="nowrap">
                <g:each in="${s.slides?}" var="sl" status="l">
${sl.slideId}
                  <br />
                </g:each>
                </td>
                <td></td>
                </tr>
                <g:set var="slist" value="${SpecimenRecord.findAllByParentSpecimen(s)}" />
                <g:each in="${slist}" status="j" var="u">
                  <g:if test="${!u.parentSpecimen.parentSpecimen}">
                    <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}">
                      <td></td>
                      <td class="specimenid">${u.specimenId}</td>
                      <td></td>
                      <td>${u.tissueType}</td>
                    <g:if test="${altMap.get(u.specimenId)=='PRIORITY1'}">
                      <td>I</td>
                    </g:if>
                    <g:elseif test="${altMap.get(u.specimenId)=='PRIORITY2'}">
                      <td>II</td>
                    </g:elseif>
                    <g:elseif test="${altMap.get(u.specimenId)=='PRIORITY3a'}">
                      <td>IIIA</td>
                    </g:elseif>
                    <g:elseif test="${altMap.get(u.specimenId)=='PRIORITY3b'}">
                      <td>IIIB</td>
                    </g:elseif>
                    <g:else><td>&nbsp;</td></g:else>
                    <td>${u.protocol?.name}</td>
                    <td>${u.fixative}</td>
                    <td>${u.containerType}</td>
                    <td><g:if test="${u.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                    <td nowrap="nowrap">
                    <g:each in="${u.slides?}" var="sl" status="l">
                      <span style="line-height:20px;">
${sl.slideId}
                        <br />
                      </span>
                    </g:each>
                    </td>
                    <td nowrap="nowrap">
                    <g:each in="${u.slides?}" var="sl" status="l">
                      <g:if test="${sl?.createdBy && sl?.createdBy != 'VARI'}">
                        <span style="line-height:20px;">
                          <g:if test="${!sl.bpvLocalPathReview}">
                            <span class="no">Not Started</span>
                            <g:if test="${canModify}">
                              <g:if test="${caseRecordInstance.primaryTissueType.code == 'KIDNEY'}" >
                                <a href="/cahubdataservices/bpvLocalPathReview/save?caseRecord.id=${caseRecordInstance.id}&slideRecord.id=${sl.id}&formMetadata.id=${FormMetadata.findByCode('PATHREVK')?.id}">(Start)</a>
                              </g:if>
                              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'OVARY'}" >
                                <a href="/cahubdataservices/bpvLocalPathReview/save?caseRecord.id=${caseRecordInstance.id}&slideRecord.id=${sl.id}&formMetadata.id=${FormMetadata.findByCode('PATHREVO')?.id}">(Start)</a>
                              </g:elseif>
                              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'LUNG'}" >
                                <a href="/cahubdataservices/bpvLocalPathReview/save?caseRecord.id=${caseRecordInstance.id}&slideRecord.id=${sl.id}&formMetadata.id=${FormMetadata.findByCode('PATHREVL')?.id}">(Start)</a>
                              </g:elseif>
                              <g:elseif test="${caseRecordInstance.primaryTissueType.code == 'COLON'}" >
                                <a href="/cahubdataservices/bpvLocalPathReview/save?caseRecord.id=${caseRecordInstance.id}&slideRecord.id=${sl.id}&formMetadata.id=${FormMetadata.findByCode('PATHREVC')?.id}">(Start)</a>
                              </g:elseif>
                            </g:if>
                          </g:if>
                          <g:elseif test="${sl.bpvLocalPathReview.status == 'Editing'}">
                            <span class="incomplete">In Progress</span>
                            <g:if test="${canModify}">
                              <g:link controller="bpvLocalPathReview" action="edit" id="${sl.bpvLocalPathReview.id}">(Edit)</g:link>
                            </g:if>
                            <g:else>
                              <g:link controller="bpvLocalPathReview" action="view" id="${sl.bpvLocalPathReview.id}">(View)</g:link>
                            </g:else>
                          </g:elseif>
                          <g:else>
                            <span class="yes">Completed</span>
                            <g:link controller="bpvLocalPathReview" action="view" id="${sl.bpvLocalPathReview.id}">(View)</g:link>
                          </g:else>
                          <br />
                        </span>
                      </g:if>
                    </g:each>
                    </td>
                    </tr>
                    <g:set var="ulist" value="${SpecimenRecord.findAllByParentSpecimen(u)}" />
                    <g:each in="${ulist}" status="k" var="v">
                      <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}">
                        <td></td>
                        <td></td>
                        <td class="specimenid">${v.specimenId}</td>
                        <td>${v.tissueType}</td>
                      <g:if test="${altMap.get(v.specimenId)=='PRIORITY1'}">
                        <td>I</td>
                      </g:if>
                      <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY2'}">
                        <td>II</td>
                      </g:elseif>
                      <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY3a'}">
                        <td>IIIA</td>
                      </g:elseif>
                      <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY4b'}">
                        <td>IIIB</td>
                      </g:elseif>
                      <g:else><td>&nbsp;</td></g:else>
                      <td>${v.protocol?.name}</td>
                      <td>${v.fixative}</td>
                      <td>${v.containerType}</td>
                      <td><g:if test="${v.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                      <td nowrap="nowrap">
                      <g:each in="${v.slides?}" var="sl" status="l">
${sl.slideId}
                        <br />
                      </g:each>
                      </td>
                      <td></td>
                      </tr>
                    </g:each>
                  </g:if>      <%-- parentSpecimen.parentSpecimen --%>
                </g:each>      <%-- slist --%>
              </g:if>          <%-- parentSpecimen --%>
            </g:each>
            </tbody>
          </table>
        </div>
      </td>
    </tr>

    </tbody>
  </table>
</div>
