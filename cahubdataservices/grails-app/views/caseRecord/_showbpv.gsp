<%--THIS IS THE OBBR FACING PAGE--%>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.ldacc.Donor" %>

<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>

<%@ page import="nci.obbr.cahub.util.ActivityEvent" %>
<%@ page import="nci.obbr.cahub.staticmembers.ActivityType" %>
<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>

<script type="text/javascript" src="${resource(dir:'js',file:'caserecord.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
<g:set var="caseStatus" value="${caseRecordInstance.caseStatus.code}"/>
<g:set var="canModify" value="${session.DM && caseRecordInstance?.candidateRecord?.isEligible && caseRecordInstance?.candidateRecord?.isConsented && (caseRecordInstance?.caseStatus?.code == 'INIT' || caseRecordInstance?.caseStatus?.code == 'DATA' || caseRecordInstance?.caseStatus?.code == 'QA' || caseRecordInstance?.caseStatus?.code == 'REMED')}" />
<g:set var="canPrcModify" value="${session.PRC && caseRecordInstance?.candidateRecord?.isEligible && caseRecordInstance?.candidateRecord?.isConsented}" />
<g:set var="canModify6MFUP" value="${session.DM && caseRecordInstance?.candidateRecord?.isEligible && caseRecordInstance?.candidateRecord?.isConsented}" />
<g:set var="fcount" value="${1}" />

<g:if test="${caseRecordInstance?.caseStatus?.code == 'DATA' && caseRecordInstance?.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date())}">
  <g:warnings warningmap="${['':'This case has had the status \'Data Entry Underway\' for more than 21 days post-surgery']}" />
</g:if>

<div class="dialog">
  <table>
    <tbody>
      <tr class="prop toptable">
        <td valign="top" class="name">Case ID:</td>

        <td valign="top" class="value" id="caseRecordID">${caseRecordInstance.caseId}</td>

        <td valign="top" rowspan="25" style="vertical-align:top">
          <table border="0">
            <tr>
              <td valign="top" class="name">Automated CDR-AR Data Checks:</td>
            </tr>
            <tr>
              <td>
            <g:set var="cdrar_hostname" value="${AppSetting.findByCode('CDRAR_HOSTNAME')}" />
            <g:set var="cdrar_iframe_hgt" value="${AppSetting.findByCode('CDR_AR_BPV_OBBR_IFRAME_HGT')}" />
            <iframe src="${cdrar_hostname.value}/cahubanalytics/iframe/bpvcasedatachecks/${caseRecordInstance.caseId}" frameborder="0" scrolling="no" height="${cdrar_iframe_hgt.value}" width="470" id="cdrar_iframe"></iframe>
        </td>
      </tr>
  </table>
</td>
</tr>    

<tr class="prop toptable">
  <td valign="top" class="name">Case Status:</td>
  <td valign="top" class="value" >
    <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>                             
<g:if test="${session.org.code == 'OBBR' && session.DM == true}">
  <g:link controller="caseRecord" action="changeCaseStatus" id="${caseRecordInstance?.id}">(Change)</g:link>
</g:if>                        
</td>
</tr>

<tr class="prop toptable">
  <td valign="top" class="name">BSS:</td>
  <td valign="top" class="value">
<g:link controller="BSS" action="show" id="${caseRecordInstance?.bss?.id}">${caseRecordInstance?.bss.name} (${caseRecordInstance?.bss.code})</g:link>
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
  <td valign="top" class="value">${caseRecordInstance.study} <i>${caseRecordInstance.phase?.name}</i></td>
</tr>

<tr class="prop toptable">
  <td valign="top" class="name"><g:message code="caseRecord.kits.label" default="Kits Used:" /></td>
<td valign="top" style="text-align: left;" class="value">
<g:if test="${caseRecordInstance.kitList}">
${caseRecordInstance.kitList.replace(',',', ')}
</g:if>
<g:else>&nbsp;</g:else>
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

<tr class="prop toptable">
  <td valign="top" class="name"><g:message code="caseRecord.internalComments.label" default="Internal Comments:" /></td>
<td valign="top" class="value">${fieldValue(bean: caseRecordInstance, field: "internalComments")}</td>
</tr>

<tr class="prop toptable">
  <td valign="top" class="name">CDR Version When Created:</td>
  <td valign="top" class="value">${caseRecordInstance.cdrVer}</td>
</tr>  

<tr class="prop toptable">
  <td valign="top" class="name">CBR Shipping Events:</td>
  <td valign="top" class="value">
<g:if test="${ShippingEvent.findByCaseId(caseRecordInstance.caseId)}">
  <span class="yes">Yes</span> <g:link controller="shippingEvent" action="listByCase" params="[caseId:caseRecordInstance.caseId]"> (View)</g:link>
</g:if>
<g:else>
  <span class="no">No</span> (Not available)
</g:else>
</td> 
</tr>


<g:if test="${session.DM == true}">
  <tr class="prop toptable">
    <td valign="top" class="name">QM Signature:</td>
    <g:set var="qmSignatureInstance" value="${ nci.obbr.cahub.datarecords.qm.CaseQMSignature.findByCaseRecord(caseRecordInstance) }"/>
    <g:set var="qmSignatureTag" value="${ qmSignatureInstance?.userId ? 'Yes' : 'No' }"/>
    <td valign="top" class="value"> <span class="${ qmSignatureTag.toLowerCase() }">${ qmSignatureTag }</span> <g:link  controller="caseQMSignature" action="edit" id="${caseRecordInstance.id}" >${ qmSignatureInstance?.userId ? '(View)' : '(Sign)' }</g:link></td>
  </tr>
</g:if>

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


<g:if test="${session.DM == true}">
  <tr class="prop toptable">
    <td valign="top" class="name">Query Tracker:</td>
    <td valign="top" class="value">
      <a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
    </td>
  </tr>

<tr class="prop toptable">
  <td valign="top" class="name">Deviation list:</td>
  <td valign="top" class="value">
    <a href="/cahubdataservices/deviation/listByCase?caseRecord.id=${caseRecordInstance.id}">View Deviation list (<span class="${deviationCount == 0 ? 'yes' : 'no'}">${deviationCount}</span>)</a>
  </td>
</tr>
</g:if>
</tbody>
</table>
<table>
  <tbody>
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
        <g:if test="${canModify}">
          
          <th> </th>
        </g:if>
        </tr>
        </thead>
        <tbody>
        <g:each in="${caseRecordInstance.files}" status="i" var="fileUploadInstance">
          <!--pmh change 08/19/13 -->
          <g:if test="${fileUploadInstance.hideFromBss == true && session.org.code != 'OBBR'}"><!--dont show file for a BSS if 'hide from BSS' is selected --> </g:if>
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
              <td>
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
  <g:if test="${canModify}">
    <a class="uibutton" href="/cahubdataservices/fileUpload/create?caseRecord.id=${caseRecordInstance.id}" title="Upload case documents" />
    <span class="ui-icon ui-icon-circle-arrow-n"></span>Upload
    </a>
  </g:if>
  </td>
  </tr>
  
  <g:if test="${session.DM == true}">
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
  </g:if>
  
  

  <g:render template="/caseRecord/showbpvcrfstatus" bean="${caseRecordInstance}" />
  
  
  <tr class="prop"><td valign="top" class="name formheader" colspan="2">Specimens (${caseRecordInstance.specimens.size()}):</td></tr>
  <tr>
    <td valign="top" colspan="2" class="value">
      <div class="list">
        <table id="specimenstable" class="nowrap">
          <thead>
            <tr>
              <th>1st Gen</th>
              <th>2nd Gen</th>
              <th>3rd Gen</th>
              <th>Tissue Type</th>
              <th>Priority</th> 
              <th>Fixation Protocol</th>                          
              <th>Fixative</th>
              <th>Container</th>
              <th>Consumed?</th>
              <th>Slides</th>
              <th>PRC Path Review</th>
              <th>Local Path Review</th>
              <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
               session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">  
                <th>Inv Status</th>
              </g:if>  
            </tr>
          </thead>
          <tbody>
<%
def parity = 0
%>
          <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
            <g:if test="${!s.parentSpecimen}">
              <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}" id="${s.specimenId.toUpperCase()}">
                <td class="itemid"><g:link action="show" controller="specimenRecord" class="specimenid" id="${s.id}">${s.specimenId}</g:link></td>
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
                <g:link controller="slideRecord" action="show" id="${sl.id}">${sl.slideId}</g:link>
                <br />
              </g:each>
              </td>
              <td></td>
              <td></td>
              <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
               session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">
                <td id="${s.id}"><span class="sp_status"></span></td>
              </g:if>  
              </tr>
              <g:set var="slist" value="${SpecimenRecord.findAllByParentSpecimen(s)}" />
              <g:each in="${slist}" status="j" var="u">
                <g:if test="${!u.parentSpecimen.parentSpecimen}">
                  <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}" id="${u.specimenId.toUpperCase()}">
                    <td></td>
                    <td class="itemid"><g:link action="show" controller="specimenRecord" class="specimenid" id="${u.id}">${u.specimenId}</g:link></td>
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
                      <g:link controller="slideRecord" action="show" id="${sl.id}">${sl.slideId}</g:link>
                      <br />
                    </span>
                  </g:each>
                  </td>
                  <td nowrap="nowrap">
                  <g:each in="${u.slides?}" var="sl" status="l">
                    <g:if test="${sl?.createdBy && sl?.createdBy != 'VARI'}">
                      <span style="line-height:20px;">
                        <g:if test="${!sl.bpvPrcPathReview}">
                          <span class="no">Not Started</span>
                        </g:if>
                        <g:elseif test="${sl.bpvPrcPathReview.status == 'Editing'}">
                          <span class="incomplete">In Progress</span>
                          <g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}">(View)</g:link>
                        </g:elseif>
                        <g:else>
                          <span class="yes">Completed</span>
                          <g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}">(View)</g:link>
                        </g:else>
                        <br />
                      </span>
                    </g:if>
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
                  <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                              session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">                  
                    <td id="${u.id}"><span class="sp_status"></span></td>
                  </g:if>                  
                  </tr>
                  <g:set var="ulist" value="${SpecimenRecord.findAllByParentSpecimen(u)}" />
                  <g:each in="${ulist}" status="k" var="v">
                    <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}" id="${v.specimenId.toUpperCase()}">
                      <td></td>
                      <td></td>
                      <td class="itemid"><g:link action="show" controller="specimenRecord" class="specimenid" id="${v.id}">${v.specimenId}</g:link></td>
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
                    <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY3b'}">
                      <td>IIIB</td>
                    </g:elseif>
                    <g:else><td>&nbsp;</td></g:else>


                    <td>${v.protocol?.name}</td>
                    <td>${v.fixative}</td>
                    <td>${v.containerType}</td>
                    <td><g:if test="${v.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                    <td nowrap="nowrap">
                    <g:each in="${v.slides?}" var="sl" status="l">
                      <g:link controller="slideRecord" action="show" id="${sl.id}">${sl.slideId}</g:link>
                      <br />
                    </g:each>
                    </td>
                    <td></td>
                    <td></td>
                    <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">                    
                      <td id="${v.id}"><span class="sp_status"></span></td>
                    </g:if>                    
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



