<%@ page import="nci.obbr.cahub.util.AppSetting" %> 
     
            <g:form method="post"  >
                <g:hiddenField name="id" value="${prcReportInstance?.id}" />
                <g:hiddenField name="paid"  />
                 <g:each in="${prcIssueList4Qc}" status="i" var="pi">
                       <input type="hidden" name ="is_pi_id_${pi.id}" value="${pi.id}" />
                </g:each>
                  <input type="hidden" id="delete_pi" name="delete_pi" />
                   <input type="hidden" name="changed" value="N" id="changed"/>
                   
                <h3>Pathologist Quality Control Review</h3>
                 <table>
                     <tr><td class="name">Were there any QC issues:</td><td class="value ${errorMap['hasQcIssue']}"> <div> <g:radio name="hasQcIssue" id="qq1" value="Yes" checked="${prcReportInstance.hasQcIssue =='Yes'}"/>&nbsp;<label for="qq1">Yes</label>&nbsp;&nbsp;&nbsp;
                           <g:if test="${prcIssueList4Qc}">
                           <g:radio name="hasQcIssue" id='qq2' value="No" checked="${prcReportInstance.hasQcIssue =='No'}"  disabled="true"/>&nbsp;<label for="qq2">No</label></div></td>
                          </g:if>
                           <g:else>
                              <g:radio name="hasQcIssue" id='qq2' value="No" checked="${prcReportInstance.hasQcIssue =='No'}"/>&nbsp;<label for="qq2">No</label></div></td>
                           </g:else>
                     </tr>
                   </table>
                
                <div id="issue" style="display:${prcReportInstance.hasQcIssue =='Yes'?'display':'none'}">
                   <table>
                     <tr><th>Specimen ID</th><th>Issue Description</th>  <th>Pending Further Follow Up</th><th>Resolved</th><th>Resolution Comments</th><th style="border-right: 1px solid #ccc">&nbsp;</th>
                    </tr>
                    
                   
                      <g:each in="${prcIssueList4Qc}" status="i" var="pi">
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td nowrap="nowrap">${pi.specimenRecord?.specimenId}</td>
                        <td class="value ${errorMap.get(pi.id +'_issueDescription')}" >
                          <g:textArea style="height:38px;width:200px;" name="${pi.id}_issueDescription" value="${pi.issueDescription}" />
                        </td>
                      
                        
                       
                        <td class="value ${errorMap.get(pi.id +'_pendingFurtherFollowUp')}">
                          <div>
                           <g:radio name="${pi.id}_pendingFurtherFollowUp" id="${pi.id}_pff1"  value="Y" checked="${pi.pendingFurtherFollowUp =='Y'}"/>&nbsp;<label for="${pi.id}_pff1">Y</label>&nbsp;&nbsp;&nbsp;
                           <g:radio name="${pi.id}_pendingFurtherFollowUp" id="${pi.id}_pff2"  value="N" checked="${pi.pendingFurtherFollowUp =='N'}"/>&nbsp;<label for="${pi.id}_pff2">N</label>&nbsp;&nbsp;&nbsp;
                          </div>
                        </td>
                        <td class="value ${errorMap.get(pi.id +'_resolved')}">
                          <div>
                            <g:radio name="${pi.id}_resolved" id="${pi.id}_re1"  value="Y" checked="${pi.resolved =='Y'}"/>&nbsp;<label for="${pi.id}_re1">Y</label>&nbsp;&nbsp;&nbsp;
                            <g:radio name="${pi.id}_resolved" id="${pi.id}_re2"  value="N" checked="${pi.resolved =='N'}"/>&nbsp;<label for="${pi.id}_re2">N</label>&nbsp;&nbsp;&nbsp;
                            <g:radio name="${pi.id}_resolved" id="${pi.id}_re3"  value="P" checked="${pi.resolved =='P'}"/>&nbsp;<label for="${pi.id}_re3">P</label>
                          </div>
                         </td>
                        <td class="value ${errorMap.get(pi.id +'_resolutionComments')}">
                           <g:textArea style="height:38px;width:200px;" name="${pi.id}_resolutionComments" value="${pi.resolutionComments}" />
                        </td>
                       
                        <td style="border-right: 1px solid #ccc"><g:actionSubmit class="save" action="updateQc" value="Delete"  onclick="return del_pi('${pi.id}')" /></td>
                          
                      </tr>
                    </g:each>
                      <tr id="l1"><td style="border-top: 1px solid #ccc" colspan="8"><g:actionSubmit action="update" value="Add"  id="a1" /></td></tr>
                   </table>
                </div>
                
                 <div id="d1" style="display:none">
                      <div class="dialog">
                        <br></br>
                       
                        <table style="width:700px">
                           <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Specimen ID">Specimen ID</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px" >
                                    <g:select id="new_pi_specimen_id" name="new_pi_specimen_id" from="${specimenList}" optionKey="specimenId"   noSelection="['': '']" />
                                    
                                </td>
                            </tr>
                            
                            </tr>
                               <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="description">Issue Description</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px"  >
                               <g:textArea name="new_pi_issue_description" cols="40" rows="5"/>
                                </td>
                            </tr>
                           
                        
                           

                               
                             <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Pending Further Follow Up">Pending Further Follow Up</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px">
                                     <g:radio name="new_pi_issue_pending_further_follow_up" id="new_pi_issue_pending_further_follow_up1"  value="Y" />&nbsp;<label for="new_pi_issue_pending_further_follow_up1">Y</label>&nbsp;&nbsp;&nbsp;
                                      <g:radio name="new_pi_issue_pending_further_follow_up" id="new_pi_issue_pending_further_follow_up2"  value="N" />&nbsp;<label for="new_pi_issue_pending_further_follow_up2">N</label>
                             
                                </td>
                            </tr>
                            
                              <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Resolved">Resolved</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px">
                                   <g:radio name="new_pi_issue_resolved" id="new_pi_issue_resolved1"  value="Y" />&nbsp;<label for="new_pi_issue_resolved1">Y</label>&nbsp;&nbsp;&nbsp;
                                   <g:radio name="new_pi_issue_resolved" id="new_pi_issue_resolved2"  value="N" />&nbsp;<label for="new_pi_issue_resolved2">N</label>&nbsp;&nbsp;&nbsp;
                                   <g:radio name="new_pi_issue_resolved" id="new_pi_issue_resolved3"  value="P" />&nbsp;<label for="new_pi_issue_resolved3">P</label>
                                   
                                </td>
                            </tr>
                               <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Resolution Comments">Resolution Comments</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px"  >
                               <g:textArea name="new_pi_issue_resolution_comments" cols="40" rows="5"/>
                                </td>
                            </tr>

                        
                        
                             <tr class="prop" bgcolor="#FFFFCC">
                               <td colspan="2"><g:actionSubmit class="save" action="updateQc" value="Save" onclick="return check_id()"  /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c1" /></td>
                            </tr>
                        </table>
                      </div>
                    </div>

                
                  <table>
                     <tr>
                    
                      <th>Any additional pathologist comments:</th>
                    </tr>
                     <tr>
                     
                      <td><g:textArea class="textwide" name="qcComments" value="${prcReportInstance.qcComments}" /></td>
                    </tr>
                   
                   </table>
                   <table>
                    <tr>
                      <th>Quality Control Performed By caHub Pathologist </th><th>Date Quality Control Completed</th><th>Report Version Reviewed</th>
                    </tr>
                    <tr><td> ${prcReportInstance.qcReviewedBy}</td><td><g:formatDate format="MM/dd/yyyy" date="${prcReportInstance.qcReviewDate}"/></td></tr>
                </table>
                    
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
               
                <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')}">
                <div class="buttons">
                     <span class="button"><g:actionSubmit class="save" action="updateQc" value="Save" /></span>
                    <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') && canSub}">
                    <span class="button"><g:actionSubmit class="save" action="completeQc" value="PRC QC Review Complete"  onclick="return sub()" /></span>
                    </g:if>
                   
                            
                </div>
                </g:if>
               
            </g:form>
  
             <g:if test="${AppSetting.findByCode('PRC_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PRC_DISCLAIMER').bigValue}</p>
        </g:if>
