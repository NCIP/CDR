

<%@ page import="nci.obbr.cahub.prc.PrcReport" %>
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prcreport edit wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'prcReport.label', default: 'PrcReport')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
       
        <g:javascript>
           $(document).ready(function(){
                 $("#a1").click(function(){
                 
                    $("#d1").show()
                      $("#l1").hide()

                   return false;
                      
                });  
                
               
                 $("#a2").click(function(){
                   
                     $("#d2").show()
                      $("#l2").hide()

                   return false;
                      
                });  
           
                
                $("#c1").click(function(){
                
                    
                   document.getElementById("new_pi_specimen_id").value='';
                   document.getElementById("new_pi_issue_description").value=''
                  // document.getElementById("d1").style.display = 'none';
                  $("#d1").hide()
                  $("#l1").show()
                    
                    return false;
                  
                  
               
                });    
                
                
                 $("#c2").click(function(){
                
                   document.getElementById("new_pi_specimen_id_att").value=''
                  // document.getElementById("d2").style.display = 'none';
                    $("#d2").hide()
                    $("#l2").show()
                    
                    return false;
                  
                  
               
                });    
                
             
                
                
               
                
               
                 
                  $(".do").click(function(){
                     var paid =(jQuery(this).get(0).id).substring(2)
                 
                     document.getElementById("paid").value = paid
                   
                      
                });

                
               
                 $(".rm").click(function(){
                     var paid =(jQuery(this).get(0).id).substring(2)
                     //alert("psid: " + psid)
                     document.getElementById("paid_remove").value = paid
                     
                     document.getElementById("filepath_remove").value=document.getElementById("filePath_" +paid).value
                    return confirm("Are you sure to remove the tissue attached file?")
                      
                });
                
                $(":input").change(function(){
                  document.getElementById("changed").value = "Y"
                  //alert("Changed!")
                });
              
            
               $(".inv_status").change(function(){
                 // alert(this.id + "  val: " + $(this).attr('value'))
                    var thisid = this.id
                    var id = thisid.substring(11);
                //    alert("id: " + id)
                  if($(this).attr('value') == 'Unacceptable'){
                    $("#div_" + id).show()
                  }else{
                     $("#div_" + id).hide()
                  }
                });
                
              });
          
          
           function del_pi(pi_id){
            
              document.getElementById("delete_pi").value=pi_id;
              
  
                
              return confirm("Are you sure to delete the issue record?")
           }
             
          function sub(){
            var changed = document.getElementById("changed").value
            if(changed == "Y"){
               alert("Please save the change!")
               return false
               }
            
          }
           
           function check_id(){
          // alert("start checking...")
            
            var sid = document.getElementById("new_pi_specimen_id").value
            //alert("sid: " + sid.length)
        
            var desc = document.getElementById("new_pi_issue_description").value
             
            if((sid==null || sid.length==0) && (desc== null || desc.length == 0)){
             alert("Please specify specimen id and/or issue description!")
             return false
             }
           
  
           }
           
        
            function check_id2(){
              
               var sid = document.getElementById("new_pi_specimen_id_att").value
            //alert("sid: " + sid.length)
        
            
             
            if(sid==null || sid.length==0){
             alert("Please specify specimen id!")
             return false
             }
           
            var file_path = document.getElementById("new_file").value
            
            if(file_path == null || file_path.length==0){
              alert("Please specify file path!")
              return false
            }
          
            
         
           
  
           }

        
          function check_id3(){
              
               var sid = document.getElementById("new_pi_specimen_id_att").value
            //alert("sid: " + sid.length)
        
            
            
           
            var file_path = document.getElementById("new_file").value
            
            if(sid!=null && sid.length!=0 && (file_path == null || file_path.length==0)){
              alert("Please specify file path!")
              return false
            }
          
            
         
           
  
           }
        
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
            <h1 align="Center" style="font-weight: bold;">PRC Case Summary Report</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prcReportInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcReportInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" enctype="multipart/form-data" >
                <g:hiddenField name="id" value="${prcReportInstance?.id}" />
                <g:hiddenField name="version" value="${prcReportInstance?.version}" />
                <input type="hidden" id="delete_pi" name="delete_pi" />
                <input type="hidden" name="paid" id="paid" />
                <input type="hidden" name="paid_remove" id="paid_remove" />
                <input type="hidden" name="filepath_remove" id="filepath_remove" />
                <input type="hidden" name="changed" value="N" id="changed"/>
                
                  <g:each in="${prcSpecimenList}" status="i" var="ps">
                    <input type="hidden" name ="is_ps_id_${ps.id}" value="${ps.id}" />
                  </g:each>
                
                <g:each in="${prcIssueList}" status="i" var="pi">
                       <input type="hidden" name ="is_pi_id_${pi.id}" value="${pi.id}" />
                </g:each>
                
                 <g:each in="${prcAttachmentList}" status="i" var="pa">
                       <input type="hidden" name ="is_pa_id_${pa.id}" value="${pa.id}" />
                </g:each>
                
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
                   <table align="Center" style="width:400px;" >
                     <g:each in="${serologyList}" status="i" var="se">
                       <g:if test="${i==0}">
                             <tr><td><font color ="red"><b>Serology Alert:</b></font> </td><td><font color ="red"><b>${se}</b></font></td></tr>
                       </g:if>
                       <g:else>
                           <tr><td>&nbsp; </td><td ><font color ="red"><b>${se}</b></font></td></tr>
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
                        <td>${pir.dateSubmitted}</td>
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
                          <th style="border-bottom: 1px solid #ccc" colspan="2" >Case #</th>
                          <td style="border-bottom: 1px solid #ccc" colspan="1">
                            <g:displayCaseRecordLink caseRecord="${prcReportInstance.caseRecord}" session="${session}"/>
                            %{-- <g:link controller="caseRecord" action="display" id="${prcReportInstance.caseRecord.id}">${prcReportInstance.caseRecord.caseId}</g:link> --}%
                          </td>
                       
                         <th style="text-align: center; border-bottom: 1px solid #ccc" colspan="3" >Amputation Type</th>
                         <td style="border-bottom: 1px solid #ccc" colspan="2">${TissueRecoveryGtex.findByCaseRecord(prcReportInstance.caseRecord)?.amputationType} 
                            </td>
                       </tr>
                    </g:else>
                    <tr><th>Specimen ID</th> <th>Tissue</th><th>RNA Integrity Number (RIN)</th>
                      <th>Image ID</th>
                      <th>Slide ID</th>
                      <th>Autolysis</th><th>Comments</th><th style="border-right: 1px solid #ccc">Inventory Status</th>
                    </tr>
                  <g:each in="${prcSpecimenList}" status="i" var="ps">
                  
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                     
                       
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}"  nowrap="nowrap">${ps.specimenRecord.specimenId} 
                          <g:if test="${ps.specimenRecord.prosectorComments}">
                            <span class="ca-bubble" data-msg="<b>${ps.specimenRecord.prosectorComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>
                          </g:if>
                          </td>
                        <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.specimenRecord?.tissueType?.name}</td>
                         <td rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" >${ps.rin}</td>
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
                        <td class="value ${errorMap.get(ps.id +'_autolysis')}" rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}" nowrap="nowrap">
                          <div>
                            <g:radio name="${ps.id}_autolysis" id="${ps.id}_a0"  value="0" checked="${ps.autolysis =='0'}"/><label for="${ps.id}_a0">0</label>&nbsp;&nbsp;&nbsp;
                            <g:radio name="${ps.id}_autolysis" id="${ps.id}_a1"  value="1" checked="${ps.autolysis =='1'}"/><label for="${ps.id}_a1">1</label>&nbsp;&nbsp;&nbsp;
                            <g:radio name="${ps.id}_autolysis" id="${ps.id}_a2"  value="2" checked="${ps.autolysis =='2'}"/><label for="${ps.id}_a2">2</label>&nbsp;&nbsp;&nbsp;
                            <g:radio name="${ps.id}_autolysis" id="${ps.id}_a3"  value="3" checked="${ps.autolysis =='3'}"/><label for="${ps.id}_a3">3</label>
                          </div>         
                                    
                        </td>
                        <td class="value ${errorMap.get(ps.id +'_comments')}" rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                          <g:textArea style="height:38px;width:250px;" name="${ps.id}_comments" value="${ps.comments}" />
                         </td>
                        <td style="border-right: 1px solid #ccc" class="value ${errorMap.get(ps.id +'_inventoryStatus')}" rowspan="${ps.specimenRecord.slides?.size() == 0 ? 1 : ps.specimenRecord.slides?.size()}">
                          
                            <g:select name="${ps.id}_inventoryStatus" from="${nci.obbr.cahub.staticmembers.InventoryStatus.list(sort:'id')}" optionKey="name" value="${ps?.inventoryStatus?.name}"   class="inv_status" id="inv_status_${ps.id}" />
                           <div id="div_${ps.id}" style="display:${ps?.inventoryStatus?.name == 'Unacceptable' ? 'display' : 'none'}">
                           Reasons:<br/>
                            <g:each in="${unaccReasons.get(ps.id)}" var="reason" status="k">
                               <g:checkBox name="${ps.id}_unacc_reason_${reason.get('id')}" id="${ps.id}_unacc_reason_${reason.get('id')}" value="${reason.selected}" />&nbsp;<label for="${ps.id}_unacc_reason_${reason.get('id')}">${reason.name}</label> <br/>
                             </g:each>
                           </div>
                          </td>
                        
                       
                     
                      </tr>
                      
                        <g:each in="${ps.specimenRecord.slides}" var="sl" status="j">
                        <g:if test="${j > 0}">
                          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
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
                     <tr><th>Specimen ID</th><th>Issue Description</th>  <th>Pending Further Follow Up</th><th>Resolved</th><th>Resolution Comments</th><th style="border-right: 1px solid #ccc">&nbsp;</th>
                    </tr>
                    <g:each in="${prcIssueList}" status="i" var="pi">
                      
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
                        <g:if test="${prcReportInstance.currentSubmission.reportVersion > pi.submissionCreated.reportVersion}">
                              <td style="border-right: 1px solid #ccc">&nbsp;</td>  
                           </g:if>
                        <g:else>
                        <td style="border-right: 1px solid #ccc"><g:actionSubmit class="save" action="update" value="Delete"  onclick="return del_pi('${pi.id}')" /></td>
                           </g:else>
                      </tr>
                    </g:each>
                     <tr id="l1"><td style="border-top: 1px solid #ccc" colspan="8"><g:actionSubmit action="update" value="Add"  id="a1" /></td></tr>
                   </table>
                  
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
                               <td colspan="2"><g:actionSubmit class="save" action="update" value="Save" onclick="return check_id()"  /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c1" /></td>
                            </tr>
                        </table>
                      </div>
                    </div>

                   <h3>General Information</h3>
                  <table>
                    <tr>
                      <th>Overall Staining of Slides</th><th>Overall Processing/Embedding</th>
                    </tr>
                    <tr>
                      <td class="value ${errorMap['stainingOfSlides']}"><input type="text" name="stainingOfSlides" value="${prcReportInstance.stainingOfSlides}"/></td>
                     
                      <td class="value ${errorMap['processing']}"><input type="text" name="processing" value="${prcReportInstance.processing}"/></td>
                    </tr>
                    <tr>
                    
                      <th colspan="2">Any additional comments from PRC</th>
                    </tr>
                     <tr>
                     
                      <td colspan="2"><g:textArea class="textwide" name="comments" value="${prcReportInstance.comments}" /></td>
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
                      <th>Tissue </th><th>Caption</th><th>File</th><th>&nbsp;</th>
                    </tr>
                     <g:each in="${prcAttachmentList}" status="i" var="pa">
                       <g:if test="${pa.filePath}">
                         <input type="hidden" name="filePath_${pa.id}" value="${pa.filePath}" id="filePath_${pa.id}" />
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${pa.specimenRecord?.tissueType?.name}</td>
                         <td>${pa.caption}</td>
                         <td><g:actionSubmit class="do" action="download" value="Download"  id="do${pa.id}" /></td>
                         <td><g:actionSubmit class="rm" action="update" value="Remove"  id="rm${pa.id}" /></td>
                       </tr>
                       </g:if>
                     </g:each>
                      <tr id="l2"><td style="border-top: 1px solid #ccc" colspan="4"><g:actionSubmit action="update" value="Add"  id="a2" /></td></tr>
                  </table>
                  
                   
                 <div id="d2" style="display:none">
                      <div class="dialog">
                        <table style="width:700px" bgcolor="#FFFFCC">
                           <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Specimen ID">Specimen ID</label>
                                </td>
                                <td valign="top" style="border: 0px" >
                                    <g:select id="new_pi_specimen_id_att" name="new_pi_specimen_id_att" from="${specimenList}" optionKey="specimenId"   noSelection="['': '']" />
                                    
                                </td>
                            </tr>
                            
                            </tr>
                               <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="caption">Caption</label>
                                </td>
                                <td valign="top" style="border: 0px"  >
                                   <input name="new_caption" value="" id="new_caption" />
                                </td>
                            </tr>
                           
                        
                           

                               
                             <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="new_file">File</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                  <input type="file" name="new_file"  id ="new_file" />
                                   
                                </td>
                            </tr>
                            
                             
                          

                        
                        
                             <tr class="prop" bgcolor="#FFFFCC">
                               <td colspan="2"><g:actionSubmit class="save" action="update" value="Save" onclick="return check_id2()" /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c2" /></td>
                            </tr>
                        </table>
                      </div>
                 </div>
                </div>
                
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" onclick="return check_id3()" /></span>
                    <g:if test="${canSub}">
                         <span class="button"><g:actionSubmit class="delete" action="submit" value="Submit" onclick="return sub()" /></span>
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
