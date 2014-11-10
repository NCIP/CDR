<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>

<div class="dialog">
  <table border="0">
    <tbody>

      <tr class="prop toptable">
        <td valign="top" class="name">Case ID:</td>

        <td valign="top" class="value">${caseRecordInstance.caseId}</td>

        <td valign="top" rowspan="20" style="vertical-align:top">
          <table border="0">
            <tr>
              <td valign="top" class="name">Automated CDR-AR Data Checks:</td>
            </tr>
            <tr>
              <td>
            <g:set var="cdrar_hostname" value="${AppSetting.findByCode('CDRAR_HOSTNAME')}" />
            <g:set var="cdrar_iframe_hgt" value="${AppSetting.findByCode('CDR_AR_GTEX_BSS_IFRAME_HGT')}" />
            <iframe src="${cdrar_hostname.value}/cahubanalytics/iframe/casedatachecks/${caseRecordInstance.caseId}?bss=1" frameborder="0" scrolling="no" height="${cdrar_iframe_hgt.value}" width="470" id="cdrar_iframe"></iframe>
        </td>
      </tr>
  </table>
</td>
</tr>     
<tr class="prop toptable">    
  <td valign="top" class="name">Collection Type:</td>

  <td valign="top" class="value">${caseRecordInstance.caseCollectionType}</td>
</tr>    
<tr class="prop toptable">
  <td valign="top" class="name">Case Status:</td>

  <td valign="top" class="value" >
    <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>

<g:if test="${caseRecordInstance.candidateRecord?.isConsented == true &&
caseRecordInstance.candidateRecord?.isEligible == true &&
caseRecordInstance.tissueRecoveryGtex &&
caseRecordInstance.caseReportForm?.status?.value == 1 &&
(caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP')}">
  <a href="/cahubdataservices/caseRecord/changeCaseStatus/${caseRecordInstance.id}">(Change)</a>
</g:if>
</td>

</tr>
<tr class="prop toptable">
  <td valign="top" class="name">BSS:</td>

  <td valign="top" class="value">${caseRecordInstance?.bss.name} (${caseRecordInstance?.bss.code})</td>
</tr>
<tr class="prop toptable">
  <td valign="top" class="name">Candidate ID:</td>

  <td valign="top" class="value">
<g:link controller="candidateRecord" action="view" id="${caseRecordInstance.candidateRecord?.id}">${caseRecordInstance.candidateRecord?.candidateId}</g:link>
<g:if test="${!caseRecordInstance.candidateRecord}"><span class="no">Not linked</span></g:if>
</td>
</tr>                                         
<tr class="prop toptable">
  <td valign="top" class="name">Donor Consented:</td>

  <td valign="top" class="value">
<g:if test="${caseRecordInstance?.candidateRecord?.isConsented == true}">
  <span class="yes">Yes</span> 
</g:if>
<g:else><span class="no">No</span></g:else>                            

</td>
</tr>                         
<tr class="prop toptable">
  <td valign="top" class="name">Donor Eligible:</td>

  <td valign="top" class="value">
<g:if test="${caseRecordInstance?.candidateRecord?.isEligible == true}">
  <span class="yes">Yes</span> 
</g:if>
<g:else><span class="no">No</span></g:else>                            

</td>
</tr>                              

<tr class="prop toptable">
  <td valign="top" class="name">Consent Verification Form (ICD):</td>

<g:if test="${caseRecordInstance.bss.parentBss.code == 'SC'}">
  <td>
  <g:if test="${caseRecordInstance.candidateRecord?.icdGtexSc}"><span class="yes">Yes</span>
    <g:if test="${caseRecordInstance.caseStatus?.code == 'DATA' || caseRecordInstance.caseStatus?.code == 'REMED' || caseRecordInstance.caseStatus?.code == 'DATACOMP' || !caseRecordInstance.caseStatus?.code}">
      <g:link controller="icdGtexSc" action="edit" id="${caseRecordInstance.candidateRecord?.icdGtexSc.id}">(Edit)</g:link>
    </g:if>                                  
    <g:else>
      <g:link controller="icdGtexSc" action="view" id="${caseRecordInstance.candidateRecord?.icdGtexSc.id}">(View)</g:link>
    </g:else>
  </g:if>
  <g:else><span class="no">No</g:else>
  </td>               
</g:if>                            
<g:if test="${caseRecordInstance.bss.parentBss.code == 'NDRI'}">
  <td>
  <g:if test="${caseRecordInstance.candidateRecord?.icdGtexNdri}"><span class="yes">Yes</span>
    <g:if test="${caseRecordInstance.caseStatus?.code == 'DATA' || caseRecordInstance.caseStatus?.code == 'REMED' || caseRecordInstance.caseStatus?.code == 'DATACOMP' || !caseRecordInstance.caseStatus?.code}">
      <g:link controller="icdGtexNdri" action="edit" id="${caseRecordInstance.candidateRecord?.icdGtexNdri.id}">(Edit)</g:link>
    </g:if>                                  
    <g:else>
      <g:link controller="icdGtexNdri" action="view" id="${caseRecordInstance.candidateRecord?.icdGtexNdri.id}">(View)</g:link>
    </g:else>
  </g:if>
  <g:else><span class="no">No</g:else>
  </td>               
</g:if>  
<g:if test="${caseRecordInstance.bss.parentBss.code == 'RPCI'}">
  <td>
  <g:if test="${caseRecordInstance.candidateRecord?.icdGtexRpci}"><span class="yes">Yes</span>
    <g:if test="${caseRecordInstance.caseStatus?.code == 'DATA' || caseRecordInstance.caseStatus?.code == 'REMED' || caseRecordInstance.caseStatus?.code == 'DATACOMP' || !caseRecordInstance.caseStatus?.code}">
      <g:link controller="icdGtexRpci" action="edit" id="${caseRecordInstance.candidateRecord?.icdGtexRpci.id}">(Edit)</g:link>
    </g:if>                                  
    <g:else>
      <g:link controller="icdGtexRpci" action="view" id="${caseRecordInstance.candidateRecord?.icdGtexRpci.id}">(View)</g:link>
    </g:else>
  </g:if>
  <g:else><span class="no">No</g:else>
  </td>               
</g:if>                         
</tr>  

<tr class="prop toptable">
  <td valign="top" class="name">Donor Eligibility Form:</td>

  <td>
<g:if test="${caseRecordInstance.candidateRecord?.donorEligibilityGtex}"><span class="yes">Yes</span>
  <g:if test="${caseRecordInstance.caseStatus?.code == 'DATA' || caseRecordInstance.caseStatus?.code == 'REMED' || caseRecordInstance.caseStatus?.code == 'DATACOMP' || !caseRecordInstance.caseStatus?.code}">
    <g:link controller="donorEligibilityGtex" action="edit" id="${caseRecordInstance.candidateRecord?.donorEligibilityGtex.id}">(Edit)</g:link>
  </g:if>                                  
  <g:else>
    <g:link controller="donorEligibilityGtex" action="view" id="${caseRecordInstance.candidateRecord?.donorEligibilityGtex.id}">(View)</g:link>
  </g:else>
</g:if>
<g:else><span class="no">No</g:else>
</td> 
</tr>                            

<tr class="prop toptable">
  <td valign="top" class="name">Tissue Recovery Form (TRF):</td>

  <td valign="top" class="value">
<g:if test="${caseRecordInstance.tissueRecoveryGtex}"><span class="yes">Yes</span> 
  <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}">
    <g:link controller="tissueRecoveryGtex" action="edit" id="${caseRecordInstance.tissueRecoveryGtex.id}">(Edit)</g:link>
  </g:if>
  <g:else>
    <g:link controller="tissueRecoveryGtex" action="view" id="${caseRecordInstance.tissueRecoveryGtex.id}">(View)</g:link>
  </g:else>
</g:if>
<g:else><span class="no">No</span>
  <g:if test="${caseRecordInstance.candidateRecord}">
    <a href="/cahubdataservices/tissueRecoveryGtex/create?caseRecord.id=${caseRecordInstance.id}">(Add)</a>
  </g:if>
</g:else>
</td>

</tr>                   
<tr class="prop toptable">
  <td valign="top" class="name">Case Report Form (CRF):</td>

  <td valign="top" class="value">
<g:if test="${caseRecordInstance.caseReportForm?.status?.value == 1}"><span class="yes">Yes</span> 
  <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}">
    <g:link controller="caseReportForm" action="show" id="${caseRecordInstance.caseReportForm.id}">(Edit)</g:link>
  </g:if>
  <g:else>
    <g:link controller="caseReportForm" action="display" id="${caseRecordInstance.caseReportForm.id}">(View)</g:link>
  </g:else>                                    
</g:if>
<g:if test="${caseRecordInstance.caseReportForm?.status?.value == 0}"><span class="incomplete">In Progress</span> 
  <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}">
    <g:link controller="caseReportForm" action="show" id="${caseRecordInstance.caseReportForm.id}">(Edit)</g:link>
  </g:if>
  <g:else>
    <g:link controller="caseReportForm" action="display" id="${caseRecordInstance.caseReportForm.id}">(View)</g:link>
  </g:else>
</g:if>                
<g:if test="${!caseRecordInstance.caseReportForm}"><span class="no">No 
    <g:if test="${caseRecordInstance.candidateRecord}">
      <a href="/cahubdataservices/caseReportForm/save?caseRecord.id=${caseRecordInstance.id}">(Add)</a></span></g:if>
</g:if>
</td>
</tr>


<%-- pmh 08/11/14 feedback form cdrqa 1300--%>
<g:if test="${hasBrain}">
  <tr valign="top" class="prop toptable">
    <td valign="top" class="name">Brain Bank Feedback:</td>
    <td>
  <g:if test="${caseRecordInstance.brainBankFeedback}">


    <g:if test="${caseRecordInstance.brainBankFeedback?.dateSubmitted}"><span class="yes">Yes </span><g:link controller="brainBankFeedback" action="show" id="${caseRecordInstance.brainBankFeedback?.id}">(View)</g:link></g:if>
    <g:else>
      <span class="incomplete">In Progress </span>
      <g:link controller="brainBankFeedback" action="show" id="${caseRecordInstance.brainBankFeedback?.id}">(View)</g:link> </g:else>
  </g:if>
  <g:else><span class="no">No</span></g:else>
  </td>
  </tr>

</g:if>
<%-- end pmh 08/11/14 feedback form--%>


<tr class="prop toptable">
  <td valign="top" class="name">Study/Phase:</td>

  <td valign="top" class="value">${caseRecordInstance.study}/${caseRecordInstance.phase?.name} (${caseRecordInstance.phase?.code})</td>
</tr>


<tr class="prop toptable">
  <td valign="top" class="name"><g:message code="caseRecord.kits.label" default="Kits Used:" /></td>

<td valign="top" class="value unlimitedstr">
  <div>
    <g:if test="${caseRecordInstance.kitList}">
${caseRecordInstance.kitList.replace(',',', ')}
    </g:if>
    <g:else>&nbsp;</g:else>
  </div>
<%--                              <ul>
<g:each in="${caseRecordInstance.kitList.split(',')}" var="k">
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
  <td valign="top" class="name"><g:message code="caseRecord.internalComments.label" default="Comments:" /></td>

<td valign="top" class="value">${fieldValue(bean: caseRecordInstance, field: "internalComments")}</td>

</tr>




<tr class="prop toptable">
  <td class="name">Procurement Feedback:</td>
  <td>
<g:if test="${caseRecordInstance.feedback != null && caseRecordInstance.feedback?.reviewDate == null}">
  <span class="incomplete">In Progress</span>
</g:if>
<g:if test="${caseRecordInstance.feedback != null && caseRecordInstance.feedback?.reviewDate != null}">
  <span class="yes">Yes</span> <g:link controller="feedback" action="view" id="${caseRecordInstance.feedback.id}"> (View)</g:link>
</g:if>


<g:if test="${!caseRecordInstance.feedback}">
  <span class="no">No</span> (Not available)
</g:if>
</td>
</tr>


<g:if test="${caseRecordInstance.feedbackFzn}">
  <tr class="prop toptable">
    <td class="name">Procurement Feedback Fzn:</td>
    <td>
  <g:if test="${caseRecordInstance.feedbackFzn?.reviewDate == null}">
    <span class="incomplete">In Progress</span>
  </g:if>
  <g:if test="${caseRecordInstance.feedbackFzn?.reviewDate != null}">
    <span class="yes">Yes</span> <g:link controller="feedbackFzn" action="view" id="${caseRecordInstance.feedbackFzn.id}"> (View)</g:link>
  </g:if>

  </td>
  </tr>

</g:if>

<%--
<tr class="prop toptable">
<td valign="top" class="name">De-identified Data Export:</td>
<g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'}">
<td valign="top" class="value"><a target="_blank" href="${createLink(uri: '/')}rest/gtexdonorvars/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (View)</a></td>
</g:if>
<g:else>
<td valign="top" class="value"><span class="no">No</span> (Not Available – case not released)</td>
</g:else>

</tr>      
--%>
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

<tr class="prop"><td valign="top" class="name formheader" colspan="3">Uploaded Files:</td></tr>
<tr>
  <td valign="top" class="value" colspan="3">
<g:if test="${FileUpload.findAll('from FileUpload as f where f.caseId=?', [caseRecordInstance?.caseId])}">

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
      <g:each in="${FileUpload.findAll('from FileUpload as f where (f.hideFromBss is null or f.hideFromBss = false) and f.caseId=?', [caseRecordInstance?.caseId])}" status="i" var="fileUploadInstance">
        <!--pmh change 08/19/13 -->
        <g:if test="${fileUploadInstance.hideFromBss == true && session.org.code != 'OBBR'}"> <!--dont show file for a BSS if 'hide from BSS' is selected --></g:if>
        <g:else>

          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
          <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>
          <td><nobr>${fileUploadInstance.category}</nobr></td>
          <g:if test="${session.org.code == 'OBBR'}">
            <td><nobr>${fileUploadInstance.hideFromBss ?'Yes':'No'}</nobr></td>
          </g:if>
          <td class="unlimitedstr"><div>${fieldValue(bean: fileUploadInstance, field: "comments")}</div></td>
          <td>
         <!-- <g:link controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')">Remove</g:link> -->
          <g:if test="${(fileUploadInstance?.bssCode?.equals(session.org.code) || session.org.code == 'OBBR' ) &&(caseRecordInstance.caseStatus?.code == 'DATA' || caseRecordInstance.caseStatus?.code == 'DATACOMP' || caseRecordInstance.caseStatus?.code == 'REMED')}">
            <g:link class="ui-button ui-state-default ui-corner-all removepadding" title="edit" controller="fileUpload" action="show" id="${fileUploadInstance.id}" ><span class="ui-icon ui-icon-pencil">Edit</span></g:link>
            <g:link class="ui-button ui-state-default ui-corner-all removepadding" title="delete" controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')"><span class="ui-icon ui-icon-trash">Remove</span></g:link>
          </g:if>
          </td>
          </tr>
        </g:else>
        <!--END pmh change 08/19/13 -->
      </g:each>
      </tbody>
    </table>
  </div>

</g:if>
<a class="uibutton" href="/cahubdataservices/fileUpload/create?caseRecord.id=${caseRecordInstance.id}" title="Upload case documents" />
<span class="ui-icon ui-icon-circle-arrow-n"></span>Upload
</a>
</td>
</tr>

<tr class="prop"><td valign="top" class="name formheader" colspan="3">Query Tracker Attachments:</td></tr>
<tr>
  <td valign="top" class="value" colspan="3">
<g:if test="${qtAttachments}">
  <div class="list">
    <table>
      <thead>
        <tr>
          <th>File Name</th>
          <th class="dateentry">Date Uploaded</th>
          <th>Category</th>
          <th>Comments</th>
        </tr>
      </thead>
      <tbody>
      <g:each in="${qtAttachments}" status="i" var="qtAttachment">
        <!--pmh change 08/19/13 -->
        <g:if test="${qtAttachment.hideFromBss == true && session.org.code != 'OBBR'}"><!--dont show file for a BSS if 'hide from BSS' is selected --> </g:if>
        <g:else>
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link controller="fileUpload" action="download" id="${qtAttachment.id}">${qtAttachment.fileName}</g:link></td>
          <td><nobr>${qtAttachment.uploadTime}</nobr></td>
          <td><nobr>${qtAttachment.category}</nobr></td>
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

<tr class="prop">
  <td valign="top" class="name formheader" colspan="3">Specimen Summary (${caseRecordInstance.specimens.size()}):
<g:if test="${frozenList?.contains(caseRecordInstance.id)}"><span class="ca-icicle ui-ca-tooltip" data-msg="Case has frozen specimens."></span></g:if>
<g:if test="${brainList?.contains(caseRecordInstance.id)}"><span class="ca-brain ui-ca-tooltip" data-msg="Case has brain specimens."></span></g:if>            
</td>
</tr>
<tr>
  <td valign="top" class="value" colspan="3">
    <div class="list">
      <table class="nowrap">
        <thead>
          <tr>

            <th>Specimen Id</th>

            <th>Tissue Type</th>

            <th>Tissue Location</th>                          

            <th>Fixative</th>

          </tr>
        </thead>
        <tbody>
        <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td class="itemid">${s.specimenId}</td>

            <td>${s.tissueType}</td>

            <td>${s.tissueLocation}</td>

            <td>${s.fixative}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
  </td>

</tr>

</tbody>
</table>
</div>
