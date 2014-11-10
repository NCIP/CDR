<%@ page import="nci.obbr.cahub.prc.PrcReport" %>
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prcreport viewbms view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${prcReportInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.edit.label.prcreport" args="[entityName]" /></title>
       <g:javascript>
           $(document).ready(function(){
           
               $(".do").click(function(){
                     var paid =(jQuery(this).get(0).id).substring(2)
                     //alert("psid: " + psid)
                     document.getElementById("paid").value = paid
                   
                      
                });
              
                $("#orderby").change(function(){
                     this.form.action='/cahubdataservices/prcReport/viewBms'
                     this.form.submit()
                     
                });
           });
           
          function openImageWin(image_id){
            // var w2=window.open('https://microscopy.vai.org/imageserver/@@/@'+image_id + '/view.apml', 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
             var w2=window.open('${AppSetting.findByCode("APERIO_URL").value}'+image_id, 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
              w2.focus();
           }
        </g:javascript>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1 align="Center" style="font-weight: bold;">PRC Case Summary Report For ${prcReportInstance.caseRecord.caseId}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prcReportInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcReportInstance}" as="list" />
            </div>
            </g:hasErrors>
             
            <g:form method="post"  >
                <g:hiddenField name="id" value="${prcReportInstance?.id}" />
                <g:hiddenField name="paid"  />
               
              
                  <div class="list">
                
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
                  
                   <g:if test="${session.org?.code && session.org?.code != 'BROAD'}">
                   <h3>Specimens (order by  <g:select name="orderby" id="orderby" from="${['tissue, delay hour, fixative','tissue, fixative, delay hour','delay hour, tissue, fixative','delay hour, fixative, tissue','fixative, tissue, delay hour','fixative, delay hour, tissue']}" value="${orderby}" />)</h3>
                   </g:if>
                  <g:else>
                    <h3>Specimens</h3>
                  </g:else>
       
                  <table>
             
                    
                    
                       <tr>
                         <td  style="border-bottom: 1px solid #ccc" colspan="3">Case# 
                           <g:displayCaseRecordLink caseRecord="${prcReportInstance.caseRecord}" session="${session}"/>
                           %{-- <g:link controller="caseRecord" action="display" id="${prcReportInstance.caseRecord.id}">${prcReportInstance.caseRecord.caseId}</g:link> --}%
                         </td>
                          <g:if test="${(prcReportInstance.caseRecord.parentCase.prcReport && session.org?.code && session.org?.code != 'BROAD' && session.org?.code !='VARI') || prcReportInstance.caseRecord.parentCase.prcReport?.reviewDate}">
                           <td colspan="7" style="border-bottom: 1px solid #ccc; border-right: 1px solid #ccc"><g:link controller="prcReport" action="view" target="_blank" id="${prcReportInstance.caseRecord.parentCase.prcReport.id}">${prcReportInstance.caseRecord.parentCase.caseId} PRC Summary Report</g:link></td>
                         </g:if>
                        <g:else>
                           <td colspan="7" style="border-bottom: 1px solid #ccc; border-right: 1px solid #ccc">&nbsp;</td>
                       </g:else>
                       </tr>
                   
                   
                    <g:if test="${session.org?.code && session.org?.code != 'BROAD'}">
                      <tr><th>Specimen ID</th><th>Fixative</th><th>Delay Hour</th> <th>Tissue</th><th>RNA Integrity Number (RIN)</th>
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
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.specimenRecord.fixative?.name}</td>
                         <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.specimenRecord.protocol?.delayHour}</td>
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
                        <td  rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" style="border-right: 1px solid #ccc">
                          <div>
                            ${ps.inventoryStatus}
                           
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
                      <td colspan="11" style="border-top: 1px solid #ccc"> *Autolysis key: 0 = none &nbsp;&nbsp;  1 = mild &nbsp;&nbsp;  2 = moderate  &nbsp;&nbsp; 3 = severe</td>
                    </tr>
                    <tr>
                      <td colspan="11" class="tablefooter">*Mouse over <span class="ca-bubble"></span> to view prosector's comments</td>
                    </tr>
                    </g:if>
                          <g:else>
    
                            <tr><th>Specimen ID</th> <th>Fixative</th><th>Delay Hour</th><th>Tissue</th><th>RIN Number</th>
                      
                            <!--<th>Autolysis</th>-->
                            <th>Comments</th><th>Inventory Status</th>
                    </tr>
                   <g:each in="${prcSpecimenList}" status="i" var="ps">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      
                       
                        <td rowspan="1" nowrap="nowrap" >${ps.specimenRecord.specimenId} 
                          <g:if test="${ps.specimenRecord.prosectorComments}">
                              <span class="ca-bubble" data-msg="<b>${ps.specimenRecord.prosectorComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>
                          </g:if>
                          </td>
                        <td rowspan="1">${ps.specimenRecord.fixative?.name}</td>
                        <td rowspan="1">${ps.specimenRecord.protocol?.delayHour}</td>
                        <td rowspan="1">${ps.specimenRecord.tissueType?.name}</td>
                        <td rowspan="1">${ps.rin}</td>
                        <td rowspan="1">${ps.comments}</td>
                        <td rowspan="1"><div>${ps.inventoryStatus}</div></td>
                      </tr>
                    </g:each>
                    <tr>
                      <td colspan="11" style="border-top: 1px solid #ccc"> *Autolysis key: 0 = none &nbsp;&nbsp;  1 = mild &nbsp;&nbsp;  2 = moderate  &nbsp;&nbsp; 3 = severe</td>
                    </tr>
                    <tr>
                      <td colspan="11" class="tablefooter">*Mouse over <span class="ca-bubble"></span> to view prosector's comments</td>
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
                      <th>Report Submitted By caHUB Pathologist(s) </th><th>Date Summary Report Submitted</th><th>Version</th><th>Reviewed By Pathologist</th><th>Date Reviewed</th>
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
                         <td>${prs.reviewedBy}</td>
                         <g:if test="${prs.dateReviewed}">
                             <td><g:formatDate format="MM/dd/yyyy" date="${prs.dateReviewed}"/></td>
                          </g:if>
                          <g:else>
                             <td>&nbsp;</td>
                           </g:else>
                      </tr>
                     
                    </g:each>
                  
                  
                
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
                <g:if test="${prcReportInstance.status == 'Submitted'}">
                    <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')}">
                <div class="buttons">
                    <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC')}">
                    <span class="button"><g:actionSubmit class="save" action="prcqareview" value="PRC QA Review Complete" /></span>
                    </g:if>
                    <span class="button"><g:actionSubmit class="save" action="qareview" value="DM QA Review Complete" /></span>
                    
                    <span class="button"><g:actionSubmit class="save" action="startnew" value="Start Next Version"  onclick="return confirm('Are you sure to create a new version of the PRC Report?')" /></span>
                            
                </div>
                    </g:if>
                </g:if>
            </g:form>
        </div>
    </body>
</html>
