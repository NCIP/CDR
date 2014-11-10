
    <%@ page import="nci.obbr.cahub.util.AppSetting" %> 
            <g:form method="post"  >
                <g:hiddenField name="id" value="${prcReportInstance?.id}" />
                <g:hiddenField name="paid"  />
                  <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB')}">  
                <g:if test="${prcReportInstance.hasQcIssue}">
                  
                    <h3>Pathologist Quality Control Review</h3>
                 <table>
                     <tr><td class="name">Were there any QC issues:</td><td class="value"> ${prcReportInstance.hasQcIssue}</td></tr>
                   </table>
                
                <div id="issue" style="display:${prcReportInstance.hasQcIssue =='Yes'?'display':'none'}">
                   <table>
                     <tr><th>Specimen ID</th><th>Issue Description</th>  <th>Pending Further Follow Up</th><th>Resolved</th><th>Resolution Comments</th>
                    </tr>
                    
                   
                      <g:each in="${prcIssueList4Qc}" status="i" var="pi">
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td nowrap="nowrap">${pi.specimenRecord?.specimenId}</td>
                        <td> ${pi.issueDescription}</td>
                      <td>${pi.pendingFurtherFollowUp}</td>
                        <td>${pi.resolved}</td>
                        <td>${pi.resolutionComments}
                        </td>
                        
                      </tr>
                    </g:each>
                    
                   </table>
                </div>
                
                
                
                  <table>
                     <tr><td class="name" witth="30%">Any additional pathologist comments:</td><td class="value"> ${prcReportInstance.qcComments}</td></tr>
                   </table>
                   <table>
                    <tr>
                      <th>Quality Control Performed By caHub Pathologist </th><th>Date Quality Control Completed</th><th>Report Version Reviewed</th>
                    </tr>
                    <tr><td> ${prcReportInstance.qcReviewedBy}</td><td><g:formatDate format="MM/dd/yyyy" date="${prcReportInstance.qcReviewDate}"/></td><td>V${prcReportInstance.qcVersion?.reportVersion}</td></tr>
                </table>
                    
                  
                  
                </g:if>
                
                </g:if>
                
                
                   <h3>Data Manager QA Review</h3>
                  <table>
                    <tr>
                      <th>Reviewed By </th><th>Review Date</th>
                    </tr>
                    
                    <tr>
                      <td>${prcReportInstance.reviewedBy}</td>
                    <g:if test="${prcReportInstance.reviewDate}">
                      <td><g:formatDate format="MM/dd/yyyy" date="${prcReportInstance.reviewDate}"/></td>
                      
                    </g:if>
                    <g:else>
                      <td>Not reviewed yet.</td>
                    </g:else>
                    </tr>
                  
                  
                
                  </table>
                   
                   <h3>Attachments</h3>
                  <table>
                     <tr>
                      <th>Tissue </th><th>Caption</th><th>File</th>
                    </tr>
                     <g:each in="${prcAttachmentList}" status="i" var="pa">
                       <g:if test="${pa.filePath}">
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${pa.specimenRecord.tissueType?.name}</td>
                         <td>${pa.caption}</td>
                         <td><g:actionSubmit class="do" action="download" value="Download"  id="do${pa.id}" /></td>
                       </tr>
                       </g:if>
                     </g:each>
                      
                  </table>
                  
                  
                  
                </div>
                
                <g:if test="${prcReportInstance.status == 'Submitted'}">
                   <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')}">
                      <div class="buttons">
                        <g:if test="${prcReportInstance.matchedSeq && !prcReportInstance.hasQcIssue && prcReportInstance.reviewDate}">
                            <span class="button"><g:actionSubmit class="save" action="qc" value="Start PRC QC Review" /></span>
                        </g:if>
                        <g:if test="${prcReportInstance.matchedSeq && prcReportInstance.hasQcIssue && prcReportInstance.reviewDate}">
                            <span class="button"><g:actionSubmit class="save" action="qc" value="Resume PRC QC Review" onclick="return confirm('Are you sure you want to resume QC review?')" /></span>
                        </g:if>
                        <g:if test="${!prcReportInstance.reviewDate}">
                                <span class="button"><g:actionSubmit class="save" action="qareview" value="DM QA Review Complete" /></span>
                        </g:if>
                        <span class="button"><g:actionSubmit class="save" action="startnew" value="Start Next Version"  onclick="return confirm('Are you sure you want to create a new version of the PRC Report?')" /></span>
                            

                      </div>
                     
                   </g:if>

                
                </g:if>
                
                
            </g:form>
  
            <g:if test="${AppSetting.findByCode('PRC_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PRC_DISCLAIMER').bigValue}</p>
        </g:if>
