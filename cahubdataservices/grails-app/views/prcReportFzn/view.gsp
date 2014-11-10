

<%@ page import="nci.obbr.cahub.prc.PrcReportFzn" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'prcReportFzn.label', default: 'PrcReportFzn')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript>
           $(document).ready(function(){
                
                
          
                
              });
          
          
          

             function openImageWin(image_id){
              //var w2=window.open('https://microscopy.vai.org/imageserver/@@/@'+image_id + '/view.apml', 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
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
            <h1>PRC Case Summary Report Fzn</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prcReportFznInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcReportFznInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prcReportFznInstance?.id}" />
                <g:hiddenField name="version" value="${prcReportFznInstance?.version}" />
                
               
                 <h3>Issue Resolutions</h3>
                  <div class="list">
                  <table>
                    <tr><th>Tissue</th><th>Issue Description</th><th>Resolution Comments</th><th>Date Submitted</th></tr>
                   <g:each in="${prcIssueResolutionDisplayList}" status="i" var="pir">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${pir.tissue}</td>
                        <td>${pir.issueDescription}</td>
                        <td>${pir.resolutionComments}</td>
                        <td>${pir.dateSubmitted}</td>
                      </tr>                   
                    </g:each>
                  </table>
                    
                     <h3>Specimens</h3>
                  <table>
                  
                    
                       <tr>
                           <th  style="border-bottom: 1px solid #ccc" colspan="3">Case #</th>
                           <td style="border-bottom: 1px solid #ccc; border-right: 1px solid #ccc" colspan="5">
                             <g:displayCaseRecordLink caseRecord="${prcReportFznInstance.caseRecord}" session="${session}"/>
                             %{-- <g:link controller="caseRecord" action="display" id="${prcReportFznInstance.caseRecord.id}">${prcReportFznInstance.caseRecord.caseId}</g:link> --}%
                           </td>
                       </tr>
                    
                  
                    <tr><th>Specimen ID</th> <th>Tissue</th><th>Fixative</th>
                      <th>Image ID</th>
                      <th>Slide ID</th>
                      <th>Autolysis</th><th>Comments</th><th style="border-right: 1px solid #ccc">Inventory Status</th>
                    </tr>
                  <g:each in="${prcSpecimenList}" status="i" var="ps">
                  
                      
                      <tr class="${ps.row}">
                     
                       
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}"  nowrap="nowrap">${ps.specimenRecord.specimenId} 
                          <g:if test="${ps.specimenRecord.prosectorComments}">
                            <span class="ca-bubble" data-msg="<b>${ps.specimenRecord.prosectorComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>
                          </g:if>
                          </td>
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.specimenRecord?.tissueType?.name}</td>
                         <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.specimenRecord?.fixative?.name}</td>
                        <g:if test="${ps.specimenRecord.slides?.size() > 0}">
                        <g:each in="${ps.specimenRecord.slides}" var="sl" status="j">
                        <g:if test="${j==0}">
                        <td ><a href="javascript:openImageWin('${sl.imageRecord?.imageId}')">${sl.imageRecord?.imageId}</a></td>
                        <td>${sl.slideId}</td>
                        </g:if>
                        </g:each>
                        </g:if>
                        <g:else>
                         <td >&nbsp;</td>
                         <td >&nbsp;</td>
                         </g:else>
                        <g:if test ="${ps.specimenRecord.fixative.code=='XG'}">
                        <td>${ps.autolysis}  </td>
                        </g:if>
                        <g:else>
                          <td>&nbsp;</td>
                        </g:else>
                        <g:if test ="${ps.specimenRecord.fixative.code=='XG'}">
                          <td>${ps.comments}</td>
                        </g:if>
                        <g:else>
                        <td class="value" rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                         ${ps.comments}
                         </td>
                        </g:else>
                        <g:if test ="${ps.specimenRecord.fixative.code=='XG'}">
                          <td> ${ps?.inventoryStatus?.name}
                            <g:if test="${ps.inventoryStatus.name=='Unacceptable'}">
                              <br/>Reasons: ${reasons.get(ps.id)}
                             </g:if>
                          </td>
                        </g:if>
                        <g:else>
                        <td style="border-right: 1px solid #ccc" class="value " rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                           ${ps?.inventoryStatus?.name}
                            <g:if test="${ps.inventoryStatus.name=='Unacceptable'}">
                              <br/>Reasons: ${reasons.get(ps.id)}
                             </g:if>
                          </td>
                        </g:else> 
                       
                     
                      </tr>
                      
                        <g:each in="${ps.specimenRecord.slides}" var="sl" status="j">
                        <g:if test="${j > 0}">
                          <tr class="${ps.row}">
                           <td ><a href="javascript:openImageWin('${sl.imageRecord?.imageId}')">${sl.imageRecord?.imageId}</a></td>
                           <td >${sl.slideId}</td>
                          </tr>
                        </g:if>
                        </g:each>
                    </g:each>
                     <tr>
                      <td colspan="9" style="border-top: 1px solid #ccc"> *Autolysis key: 0 = none &nbsp;&nbsp;  1 = mild &nbsp;&nbsp;  2 = moderate  &nbsp;&nbsp; 3 = severe</td>
                    </tr>
                    <tr>
                      <td colspan="9" class="tablefooter">*Mouse over <span class="ca-bubble"></span> to view prosector's comments</td>
                    </tr>
                   </table>
                  
                    
                     <h3>Issues</h3>
                   <table>
                     <tr><th>Specimen ID</th><th>Issue Description</th>  <th>Pending Further Follow Up</th><th>Resolved</th><th>Resolution Comments</th>
                    </tr>
                    <g:each in="${prcIssueList}" status="i" var="pi">
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td nowrap="nowrap">${pi.specimenRecord?.specimenId}</td>
                        <td class="value " >
                          ${pi.issueDescription}
                        </td>
                      
                        
                       
                        <td class="value">
                          ${pi.pendingFurtherFollowUp}
                          
                        </td>
                        <td class="value ">
                          ${pi.resolved}
                         
                         </td>
                        <td class="value">
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
                      <td class="value">${prcReportFznInstance.stainingOfSlides}</td>
                      <td class="value">${prcReportFznInstance.processing}</td>
                    </tr>
                    <tr>
                    
                      <th colspan="2">Any additional comments from PRC</th>
                    </tr>
                     <tr>
                     
                      <td colspan="3">${prcReportFznInstance.comments}</td>
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
                        
                        
                   
                   <h3>Data Manager QA Review</h3>
                  <table>
                    <tr>
                      <th>Reviewed By </th><th>Review Date</th>
                    </tr>
                    
                    <tr>
                      <td>${prcReportFznInstance.reviewedBy}</td>
                    <g:if test="${prcReportFznInstance.reviewDate}">
                      <td><g:formatDate format="MM/dd/yyyy" date="${prcReportFznInstance.reviewDate}"/></td>
                      
                    </g:if>
                    <g:else>
                      <td>Not reviewed yet.</td>
                    </g:else>
                    </tr>
                  
                  
                
                  </table>
                     
                  </div>
                <div class="buttons">
                   <g:if test="${prcReportFznInstance.status == 'Submitted'}">
                   <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')}">
                      <div class="buttons">
                        
                        <g:if test="${!prcReportFznInstance.reviewDate}">
                                <span class="button"><g:actionSubmit class="save" action="qareview" value="DM QA Review Complete" /></span>
                        </g:if>
                        <span class="button"><g:actionSubmit class="save" action="startnew" value="Start Next Version"  onclick="return confirm('Are you sure you want to create a new version of the PRC Report?')" /></span>
                            

                      </div>
                     
                   </g:if>

                
                </g:if>
                </div>
            </g:form>
        </div>
      <g:if test="${AppSetting.findByCode('PRC_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PRC_DISCLAIMER').bigValue}</p>
                </g:if>
    </body>
</html>
