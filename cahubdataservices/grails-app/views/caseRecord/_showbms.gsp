<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.ldacc.Donor" %>
<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>

            <div class="dialog">
                <table class="nowrap">
                    <tbody>
                        <tr class="prop toptable">
                          <td valign="top" class="name">Case ID:</td>
                          <td valign="top" class="value">${caseRecordInstance.caseId}</td>
                        </tr>
                        <tr class="prop toptable">
                          <td valign="top" class="name">Parent Case ID:</td>
                          <td valign="top" class="value"><g:link controller="caseRecord" action="display" id="${caseRecordInstance.parentCase?.id}">${caseRecordInstance.parentCase?.caseId}</g:link></td>
                        </tr>    
                        <tr class="prop toptable">    
                          <td valign="top" class="name">Collection Type:</td>
                          <td valign="top" class="value">${caseRecordInstance.caseCollectionType}</td>
                        </tr>    
                        <tr class="prop toptable">
                          <td valign="top" class="name">Case Status:</td>
                          <td valign="top" class="value" ><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>                             
                              <g:if test="${session.org.code == 'OBBR' && session.DM == true}"><g:link controller="caseRecord" action="changeCaseStatus" id="${caseRecordInstance?.id}">(Change)</g:link></g:if></td>
                        </tr>
                        <tr class="prop toptable">
                          <td valign="top" class="name">BSS:</td>
                          <td valign="top" class="value"><g:link controller="BSS" action="show" id="${caseRecordInstance?.bss?.id}">${caseRecordInstance?.bss.name} (${caseRecordInstance?.bss.code})</g:link></td>
                       </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name">Tissue Recovery Form (TRF):</td>
                            <td valign="top" class="value"><g:if test="${caseRecordInstance.tissueRecoveryBms}">
                                <g:if test="${caseRecordInstance.tissueRecoveryBms.dateSubmitted}"><span class="yes">Yes</span></g:if><g:else><span class="incomplete">In Progress</span></g:else>
                                  <g:if test="${session.DM == true}">
                                    <g:if test="${caseRecordInstance.caseStatus.code == 'DATA' || caseRecordInstance.caseStatus.code == 'REMED' || caseRecordInstance.caseStatus.code == 'DATACOMP'}">
                                      <g:if test="${caseRecordInstance.tissueRecoveryBms.dateSubmitted}"><g:link controller="tissueRecoveryBms" action="restart" id="${caseRecordInstance.tissueRecoveryBms.id}" onclick="return confirm('The form has beem submitted, are you sure to resume editing?')" >(Edit)</g:link>|<g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link></g:if>
                                      <g:else><g:link controller="tissueRecoveryBms" action="edit" id="${caseRecordInstance.tissueRecoveryBms.id}" >(Edit)</g:link>|<g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link></g:else>
                                    </g:if><g:else><g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link></g:else>
                                  </g:if><g:else><g:link controller="tissueRecoveryBms" action="view" id="${caseRecordInstance.tissueRecoveryBms.id}">(View)</g:link>
                                 </g:else>
                               </g:if>
                            <g:else><span class="no">No</span><g:if test="${session.DM == true && caseRecordInstance?.parentCase?.candidateRecord && caseRecordInstance?.parentCase?.tissueRecoveryGtex}"><a href="/cahubdataservices/tissueRecoveryBms/save?caseRecord.id=${caseRecordInstance.id}">(Add)</a></g:if></g:else></td>
                        </tr>                   
                        <tr class="prop toptable">
                            <td valign="top" class="name">Study:</td>
                            <td valign="top" class="value">${caseRecordInstance.study}</td>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name"><g:message code="caseRecord.kits.label" default="Kits Used:" /></td>
                            <td valign="top" style="text-align: left;" class="value"><g:if test="${caseRecordInstance.kitList}">${caseRecordInstance.kitList.replace(',',', ')}</g:if><g:else>&nbsp;</g:else></td>  
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
                            <td valign="top" class="name"><g:message code="caseRecord.internalGUID.label" default="Internal GUID:" /></td>
                            <td valign="top" class="value">${fieldValue(bean: caseRecordInstance, field: "internalGUID")}</td>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name"><g:message code="caseRecord.internalComments.label" default="Internal Comments:" /></td>
                            <td valign="top" class="value">${fieldValue(bean: caseRecordInstance, field: "internalComments")}</td>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name"><g:message code="caseRecord.publicComments.label" default="Public Comments:" /></td>
                            <td valign="top" class="value">${fieldValue(bean: caseRecordInstance, field: "publicComments")}</td>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name">CDR Version When Created:</td>
                            <td valign="top" class="value">${caseRecordInstance.cdrVer}</td>
                        </tr>
                        <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')}">
                        <tr class="prop toptable">
                          <td class="name">PRC Summary Report:</td>
                             <g:if test ="${caseRecordInstance.prcReport!= null && caseRecordInstance.prcReport?.status=='Editing'}">
                                 <td><span class="incomplete">In Progress</span> <g:link controller="prcReport" action="editBms" id="${caseRecordInstance.prcReport.id}">(Edit)</g:link> | <g:link controller="prcReport" action="viewBms" id="${caseRecordInstance.prcReport.id}">(View)</g:link></td>
                           </g:if>
                           <g:if test="${caseRecordInstance.prcReport!= null && caseRecordInstance.prcReport?.status!='Editing'}">
                                 <td><g:link controller="prcReport" action="viewBms" id="${caseRecordInstance.prcReport.id}"><span class="yes">Yes</span> (View)</g:link></td>
                           </g:if>
                           <g:if test="${caseRecordInstance.prcReport== null}">
                                 <td><a href="/cahubdataservices/prcReport/saveBms?caseRecord.id=${caseRecordInstance.id}"><span class="no">No</span> (Add)</a></span></td>
                           </g:if>
                        </tr>
                        </g:if><g:else>
                          <tr class="prop toptable">
                            <td class="name">PRC Summary Report:</td>
                            <td><g:if test="${caseRecordInstance.prcReport != null && caseRecordInstance.prcReport?.reviewDate == null}"><span class="incomplete">In Progress</span></g:if>
                              <g:if test="${caseRecordInstance.prcReport != null && caseRecordInstance.prcReport?.reviewDate != null}"><span class="yes">Yes</span> <g:link controller="prcReport" action="viewBms" id="${caseRecordInstance.prcReport.id}"> (View)</g:link></g:if>
                              <g:if test="${!caseRecordInstance.prcReport}"><span class="no">No</span> (Not available)</g:if></td>             
                        </g:else>
                          <tr class="prop">
                            <td valign="top" class="name">De-identified Data Export:</td>
                            <g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'  || caseRecordInstance.caseStatus?.code=='COMP'}">
                              <g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'}">
                               <td valign="top" class="value"><a target="_blank" href="${createLink(uri: '/')}rest/gtexdonorvars/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (View XML)</a></td>
                              </g:if><g:else>
                                <td valign="top" class="value"><a target="_blank" href="${createLink(uri: '/')}rest/previewgtexdonorvars/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (Preview XML)</a></td>
                              </g:else>
                            </g:if>
                         <g:else><td valign="top" class="value"><span class="no">No</span> (Not Available – case not released)</td></g:else> 
                        </tr>
                        
                        <g:if test="${session.DM == true}">
                          <tr class="prop toptable">
                            <td valign="top" class="name">QM Signature:</td>
                            <g:set var="qmSignatureInstance" value="${ nci.obbr.cahub.datarecords.qm.CaseQMSignature.findByCaseRecord(caseRecordInstance) }"/>
                            <g:set var="qmSignatureTag" value="${ qmSignatureInstance?.userId ? 'Yes' : 'No' }"/>
                            <td valign="top" class="value"> <span class="${ qmSignatureTag.toLowerCase() }">${ qmSignatureTag }</span> <g:link  controller="caseQMSignature" action="edit" id="${caseRecordInstance.id}" >${ qmSignatureInstance?.userId ? '(View)' : '(Sign)' }</g:link></td>
                          </tr>
                        </g:if>
                        
                         <tr class="prop">
                            <td valign="top" class="name">LDACC Molecular Data Import:</td>
                           <g:if test ="${Donor.findByCaseRecord(caseRecordInstance)}"><td valign="top" class="value"><a target="_blank" href="${createLink(uri: '/')}rpc/ldaccGtexMolecularData/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (View XML)</a></td></g:if><g:else><td valign="top" class="value"><span class="no">No</span> (Not Available)</td></g:else>
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

                        
                       <g:if test="${false}">
                         <g:if test="${session.DM == true}">
                           <tr class="prop toptable">
                             <td valign="top" class="name">Recall Case:</td>
                           <g:if test="${!caseRecordInstance.caseWithdraw?.id}">
                             <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Start Recall process </g:link></td>
                           </g:if>
                           <g:elseif test="${caseRecordInstance.caseWithdraw?.finalCompleteDate}">
                             <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall completed</g:link></td>
                           </g:elseif>
                           <g:else>
                             <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall in progress</g:link></td>
                           </g:else>

                           </tr>
                        </g:if>
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
                       
                        <tr class="prop"><td valign="top" class="name formheader" colspan="3">Uploaded Files:</td></tr>
                        <tr>
                          <td valign="top" class="value" colspan="3"><g:if test="${FileUpload.findAll('from FileUpload as f where f.caseId=?', [caseRecordInstance?.caseId])}">
                                    <div class="list">
                                        <table>
                                            <thead>
                                                <tr>
                                                  <th>File Name</th>
                                                  <th class="dateentry">Date Uploaded</th>
                                                  <th>Comments</th>
                                                  <g:if test="${session.DM}"><th></th></g:if>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <g:each in="${FileUpload.findAll('from FileUpload as f where f.caseId=?', [caseRecordInstance?.caseId])}" status="i" var="fileUploadInstance">
                                                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                                        <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                                                        <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>
                                                        <td class="unlimitedstr"><div>${fieldValue(bean: fileUploadInstance, field: "comments")}</div></td>
                                                        <g:if test="${session.DM}"><td><g:link controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')">Remove</g:link></td></g:if>
                                                    </tr>
                                                </g:each>
                                            </tbody>
                                        </table>
                                    </div>
                                </g:if>
                                <g:if test="${session.DM}">
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
                                                      <th>Comments</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <g:each in="${qtAttachments}" status="i" var="qtAttachment">
                                                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                                            <td><g:link controller="fileUpload" action="download" id="${qtAttachment.id}">${qtAttachment.fileName}</g:link></td>
                                                            <td><nobr>${qtAttachment.uploadTime}</nobr></td>
                                                            <td><nobr>${qtAttachment.category}</nobr></td>
                                                            <td class="unlimitedstr"><div>${fieldValue(bean: qtAttachment, field: "comments")}</div></td>
                                                        </tr>
                                                    </g:each>
                                                </tbody>
                                            </table>
                                        </div>
                                    </g:if>
                                </td>
                            </tr>
                        </g:if>
                        
            <tr class="prop"><td valign="top" class="name formheader" colspan="2">Specimens (${caseRecordInstance.specimens.size()}):</td></tr>
            <tr>
                <td valign="top" class="value" colspan="2"><div class="list">
                    <table class="nowrap">
                        <thead>
                            <tr>
                              <th>Specimen Id</th>
                              <th>Tissue Type</th>
                              <th>Treatment Group</th>  
                              <th>Fixative</th>
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td class="itemid"><g:link action="show" controller="specimenRecord" id="${s.id}">${s.specimenId}</g:link></td>
                                <td>${s.tissueType}</td>
                                <td>${s.protocol?.name}</td>
                                <td>${s.fixative}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table></div>
                </td>
           </tr>
          </tbody>
        </table>
    </div>

