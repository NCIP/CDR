<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
    
            <h1 align="Center" style="font-weight: bold;">PRC Case Summary Report For ${prcReportInstance.caseRecord.caseId}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prcReportInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcReportInstance}" as="list" />
            </div>
            </g:hasErrors>
          
               
                <div class="list">
                 
                  <g:if test="${eligQ15Response && eligQ15Response[0] == 'yes'}">
                     <div align="center"> 
                   <table style="width:400px" align="center">
                     
                             <tr><td><font color ="red"><b>Transplant Alert:</b></font> </td><td><font color ="red"><b>${eligQ15Response[1]}</b></font></td></tr>
                                            
                  </table>
                     </div>
                  </g:if>
                  
                  <g:if test="${serologyList}">
                     <div align="center"> 
                   <table style="width:400px" align="center">
                     <g:each in="${serologyList}" status="i" var="se">
                       <g:if test="${i==0}">
                             <tr><td><font color ="red"><b>Serology Alert:</b></font> </td><td><font color ="red"><b>${se}</b></font></td></tr>
                       </g:if>
                       <g:else>
                           <tr><td>&nbsp; </td><td><font color ="red"><b>${se}</b></font></td></tr>
                       </g:else>
                     </g:each>
                  </table>
                     </div>
                  </g:if>
                  
                   <h3>Issue Resolutions</h3>
                  <table>
                    <tr><th>Tissue</th><th>Issue Description</th><th>Resolution Comments</th><th>Date Submitted</th></tr>
                    <g:each in="${prcIssueResolutionDisplayList}" status="i" var="pir">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${pir.tissue}</td>
                        <td>${pir.issueDescription}</td>
                        <td>${pir.resolutionComments}</td>
                      <g:if test="${pir.dateSubmitted ==null}">
                        <td>Not Submitted yet.</td>
                      </g:if>
                      <g:else>
                        <td>${pir.dateSubmitted}</td>
                      </g:else>
                      </tr>                   
                    </g:each>
                  </table>
                  
                   <h3>Specimens</h3>
                  <table>
                   <g:if test="${prcReportInstance.caseRecord.caseCollectionType.code !='SURGI'}">
                    
                       <tr>
                         <th  style="border-bottom: 1px solid #ccc" colspan="3">Case #</th>
                         <td style="border-bottom: 1px solid #ccc; border-right: 1px solid #ccc" colspan="5">
                           <g:displayCaseRecordLink caseRecord="${prcReportInstance.caseRecord}" session="${session}"/>
                           %{-- <g:link controller="caseRecord" action="display" id="${prcReportInstance.caseRecord.id}">${prcReportInstance.caseRecord.caseId}</g:link> --}%
                         </td>
                       </tr>
                     </g:if>
                    <g:else>
                        
                        <tr>
                         <th style="border-bottom: 1px solid #ccc" colspan="2">Case #</th>
                         <td style="border-bottom: 1px solid #ccc" colspan="1">
                           <g:displayCaseRecordLink caseRecord="${prcReportInstance.caseRecord}" session="${session}"/>
                           %{-- <g:link controller="caseRecord" action="display" id="${prcReportInstance.caseRecord.id}">${prcReportInstance.caseRecord.caseId}</g:link> --}%
                         </td>
                       
                         <th style="text-align: center; border-bottom: 1px solid #ccc" colspan="2" >Amputation Type</th>
                         <td style="border-bottom: 1px solid #ccc; border-right: 1px solid #ccc" colspan="3"> ${TissueRecoveryGtex.findByCaseRecord(prcReportInstance.caseRecord)?.amputationType}
                            </td>
                       </tr>
                    </g:else>
                    <g:if test="${session.org?.code && session.org?.code != 'BROAD'}">
                    <tr><th>Specimen ID</th> <th>Tissue</th><th>RNA Integrity Number (RIN)</th>
                      <th>Image ID</th>
                      <th>Slide ID</th>
                      <th>Autolysis</th><th>Comments</th><th style="border-right: 1px solid #ccc">Inventory Status</th>
                    </tr>
                   <g:each in="${prcSpecimenList}" status="i" var="ps">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      
                       
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" nowrap="nowrap" >${ps.specimenRecord.specimenId} 
                          <g:if test="${ps.specimenRecord.prosectorComments}">
                             <span class="ca-bubble" data-msg="<b>${ps.specimenRecord.prosectorComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>
                          </g:if>
                          </td>
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.specimenRecord.tissueType?.name}</td>
                         <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.rin}</td>
                        <g:if test="${ps.specimenRecord.slides?.size() > 0}">
                        <g:each in="${ps.specimenRecord.slides}" var="sl" status="j">
                            <g:if test="${j==0}">
                            <td> <a href="javascript:openImageWin('${sl.imageRecord?.imageId}')">${sl.imageRecord?.imageId}</a></td>                      
                        <td>${sl.slideId}</td>
                        </g:if>
                        </g:each>
                        </g:if>
                        <g:else>
                         <td >&nbsp;</td>
                         <td >&nbsp;</td>
                         </g:else>
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                          <div>
                            ${ps.autolysis}
                          </div>         
                                    
                        </td>
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                          ${ps.comments}
                         </td>
                        <td style="border-right: 1px solid #ccc" rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                          <div>
                            ${ps.inventoryStatus}
                            <g:if test="${ps.inventoryStatus.name=='Unacceptable' && session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') }">
                              <br/>Reasons: ${reasons.get(ps.id)}
                          </g:if>
                          </div>
                          </td>
                       
                        
                      
                      </tr>
                      
                        <g:each in="${ps.specimenRecord.slides}" var="sl" status="j">
                        <g:if test="${j > 0}">
                          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                             <td> <a href="javascript:openImageWin('${sl.imageRecord?.imageId}')">${sl.imageRecord?.imageId}</a></td> 
                           <td >${sl.slideId}</td>
                          </tr>
                        </g:if>
                        </g:each>
                    </g:each>
                    <tr>
                      <td colspan="9" style="border-top: 1px solid #ccc"> *Autolysis key: 0 = none &nbsp;&nbsp;  1 = mild &nbsp;&nbsp;  2 = moderate  &nbsp;&nbsp; 3 = severe</td>
                    </tr>
                    <tr>
                      <td colspan="9">*Mouse over <span class="ca-bubble"></span> to view prosector's comments</td>
                    </tr>
                    </g:if>
                          <g:else>
    
                            <tr><th>Specimen ID</th> <th>Tissue</th><th>RNA Integrity Number (RIN)</th>
                      
                            <th>Autolysis</th><th>Comments</th><th style="border-right: 1px solid #ccc">Inventory Status</th>
                    </tr>
                   <g:each in="${prcSpecimenList}" status="i" var="ps">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      
                       
                        <td rowspan="1" nowrap="nowrap" >${ps.specimenRecord.specimenId} 
                          <g:if test="${ps.specimenRecord.prosectorComments}">
                             <span class="ca-bubble" data-msg="<b>${ps.specimenRecord.prosectorComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>
                             
                          </g:if>
                          </td>
                        <td rowspan="1" >${ps.specimenRecord.tissueType?.name}</td>
                        <td rowspan="1" >${ps.rin}</td>
                       
                       
                        <td rowspan="1">
                          <div>
                            ${ps.autolysis}
                          </div>         
                                    
                        </td>
                        <td rowspan="1">
                          ${ps.comments}
                         </td>
                        <td style="border-right: 1px solid #ccc" rowspan="1">
                          <div>
                            ${ps.inventoryStatus}
                           
                          </div>
                          </td>
                      </tr>
                      
                    </g:each>
                    <tr>
                      <td colspan="9" style="border-top: 1px solid #ccc"> *Autolysis key: 0 = none &nbsp;&nbsp;  1 = mild &nbsp;&nbsp;  2 = moderate  &nbsp;&nbsp; 3 = severe</td>
                    </tr>
                    <tr>
                      <td colspan="9">*Mouse over <span class="ca-bubble"></span> to view prosector's comments</td>
                    </tr>
                   
    
    
                          </g:else>
                   </table>
                  
                   <h3>Issues</h3>
                   <table>
                     <tr><th>Specimen ID</th><th>Issue Description</th><th>Pending Further Follow Up</th><th>Resolved</th><th>Resolution Comments</th>
                    </tr>
                    <g:each in="${prcIssueList}" status="i" var="pi">
                       <input type="hidden" name ="is_pi_id_${pi.id}" value="${pi.id}" />
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td nowrap="nowrap">${pi.specimenRecord?.specimenId}</td>
                        <td>
                          ${pi.issueDescription}
                        </td>
                       
                        
                       
                        <td>
                          ${pi.pendingFurtherFollowUp}
                         
                        </td>
                        <td >
                          <div>
                            ${pi.resolved}
                          </div>
                         </td>
                        <td >
                           ${pi.resolutionComments}
                        </td>
                       
                      </tr>
                    </g:each>
                    
                    
                   </table>
                  
                    
                   <h3>General Information</h3>

                  <table>
                    <tr>
                      <th>Overall Staining of Slides</th><th>Overall Processing/Embedding</th>
                    </tr>
                    <tr>
                      <td >${prcReportInstance.stainingOfSlides}</td>
                      <td>${prcReportInstance.processing}</td>
                    </tr>
                    <tr>
                       
                      <th colspan="2">Any additional comments from PRC</th>
                    </tr>
                     <tr>
                     
                      <td colspan="2">${prcReportInstance.comments}</td>
                    </tr>
                  </table>
                  
                   <h3>Submissions</h3>
                  <table>
                    <tr>
                      <th>Report Submitted By caHUB Pathologist(s) </th><th>Date Summary Report Submitted</th><th>Version</th>
                    </tr>
                    
                    <g:each in="${prcReportSubList}" status="i" var="prs">
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${prs.submittedBy}</td>
                      <g:if test="${prs.dateSubmitted}">
                        
                        <td><g:formatDate format="MM/dd/yyyy" date="${prs.dateSubmitted}"/></td>
                      </g:if>
                      <g:else>
                         <td>Not submitted yet.</td>
                      </g:else>
                         <td>V${prs.reportVersion}</td>
                        
                      </tr>
                     
                    </g:each>
                  
                  
                
                  </table>
                   
                 
                   
                  
                  
                  
                </div>
               
   
