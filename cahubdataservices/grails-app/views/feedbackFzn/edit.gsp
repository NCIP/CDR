

<%@ page import="nci.obbr.cahub.prc.PrcReport" %>
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prcreport edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'feedbackFzn.label', default: 'FeedbackFzn')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
       
        <g:javascript>
           $(document).ready(function(){
                 $("#a1").click(function(){
                 
                    $("#d1").show()
                      $("#l1").hide()

                   return false;
                      
                });  
                
               
                 
                
                $("#c1").click(function(){
                
                    
                   document.getElementById("new_fi_specimen_id").value='';
                   document.getElementById("new_fi_issue_description").value=''
                  // document.getElementById("d1").style.display = 'none';
                  $("#d1").hide()
                  $("#l1").show()
                    
                    return false;
                  
                  
               
                });    
                
                
               
             
                
             

                
                
                $(":input").change(function(){
                  document.getElementById("changed").value = "Y"
                  //alert("Changed!")
                });
                
              });
          
          
           function del_fi(fi_id){
            
              document.getElementById("delete_fi").value=fi_id;
              
  
                
              return confirm("Are you sure you want to delete the issue record?")
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
            
            var sid = document.getElementById("new_fi_specimen_id").value
            //alert("sid: " + sid.length)
        
            var desc = document.getElementById("new_fi_issue_description").value
             
            if((sid==null || sid.length==0) && (desc== null || desc.length == 0)){
             alert("Please specify specimen id and/or issue description!")
             return false
             }
           
  
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
            <h1 align="Center" style="font-weight: bold;">Procurement Feedback Fzn For ${feedbackFznInstance.caseRecord.caseId}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${feedbackFznInstance}">
            <div class="errors">
                <g:renderErrors bean="${feedbackFznInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" enctype="multipart/form-data" >
                <g:hiddenField name="id" value="${feedbackFznInstance?.id}" />
                <g:hiddenField name="version" value="${feedbackFznInstance?.version}" />
                <g:hiddenField name="started" value="true" />
                <input type="hidden" id="delete_fi" name="delete_fi" />
               
                <input type="hidden" name="changed" value="N" id="changed"/>
                
                 
                
                <g:each in="${feedbackIssueList}" status="i" var="fi">
                       <input type="hidden" name ="is_fi_id_${fi.id}" value="${fi.id}" />
                </g:each>
                
               
                <div class="list">
                 
                     <h3>Case Details</h3>
            <table>
              <tbody>
             <tr class="prop">
              <td><label>Case ID:</label> <g:link controller="caseRecord" action="display" id="${feedbackFznInstance.caseRecord.id}">${feedbackFznInstance.caseRecord.caseId}</g:link></td>
              <td><label>Collection Type:</label> ${feedbackFznInstance.caseRecord.caseCollectionType}</td>       
             <td><label>BSS:</label> ${feedbackFznInstance.caseRecord.bss.name}</td>
             </tr>  
            </tbody>
             </table>
                 
                  
                   <h3>Issue Resolutions</h3>

                  <table>
                    <tr><th>Tissue</th><th>Tissue ID</th><th>Issue Description</th><th>Resolution Comments</th><th>Date Submitted</th></tr>
                   <g:each in="${feedbackIssueResolutionDisplayList}" status="i" var="fir">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${fir.tissue}</td>
                        <td>${fir.specimenId}</td>
                        <td>${fir.issueDescription}</td>
                        <td>${fir.resolutionComments}</td>
                        <td>${fir.dateSubmitted}</td>
                      </tr>                   
                    </g:each>
                  </table>
                     
                
                  
                   <h3>Issues</h3>
                   <table>
                     <tr><th>Specimen ID</th><th>Issue Description</th> <th>Resolution Comments</th><th style="border-right: 1px solid #ccc">&nbsp;</th>
                    </tr>
                    <g:each in="${feedbackIssueList}" status="i" var="fi">
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td nowrap="nowrap">${fi.specimenRecord?.specimenId}</td>
                        <td class="value ${errorMap.get(fi.id +'_issueDescription')}" >
                          <g:textArea style="height:38px;width:200px;" name="${fi.id}_issueDescription" value="${fi.issueDescription}" />
                        </td>
                      
                        
                       
                       
                        <td class="value ${errorMap.get(fi.id +'_resolutionComments')}">
                           <g:textArea style="height:38px;width:200px;" name="${fi.id}_resolutionComments" value="${fi.resolutionComments}" />
                        </td>
                        <g:if test="${feedbackFznInstance.currentSubmission.feedbackVersion > fi.submissionCreated.feedbackVersion}">
                              <td style="border-right: 1px solid #ccc">&nbsp;</td>  
                           </g:if>
                        <g:else>
                        <td style="border-right: 1px solid #ccc"><g:actionSubmit class="save" action="update" value="Delete"  onclick="return del_fi('${fi.id}')" /></td>
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
                                    <g:select id="new_fi_specimen_id" name="new_fi_specimen_id" from="${specimenList}" optionKey="specimenId"   noSelection="['': '']" />
                                    
                                </td>
                            </tr>
                            
                            </tr>
                               <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="description">Issue Description</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px"  >
                               <g:textArea name="new_fi_issue_description" cols="40" rows="5"/>
                                </td>
                            </tr>
                           
                        
                           

                               
                           
                            
                              
                               <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Resolution Comments">Resolution Comments</label>
                                </td>
                                <td valign="top" class="value" style="border: 0px"  >
                               <g:textArea name="new_fi_issue_resolution_comments" cols="40" rows="5"/>
                                </td>
                            </tr>

                        
                        
                             <tr class="prop" bgcolor="#FFFFCC">
                               <td colspan="2"><g:actionSubmit class="save" action="update" value="Save" onclick="return check_id()"  /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c1" /></td>
                            </tr>
                        </table>
                      </div>
                    </div>

                   <h3>Comments</h3>
                   <table>
                     <tr>
                       <td>   <g:textArea name="comments" cols="120" rows="5" value="${feedbackFznInstance?.comments}" /></td>
                     </tr>
                   </table>
                  
                  
                   <h3>Submissions</h3>
                  <table>
                    <tr>
                      <th>Procurement Feedback Submitted By caHUB Pathologist(s) </th><th>Date Submitted</th><th>Version</th>
                    </tr>
                    
                    <g:each in="${feedbackSubList}" status="i" var="frs">
                      
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${frs.submittedBy}</td>
                      <g:if test="${frs.dateSubmitted}">
                        <td><g:formatDate format="MM/dd/yyyy" date="${frs.dateSubmitted}"/></td>
                      </g:if>
                      <g:else>
                         <td>Not submitted yet.</td>
                      </g:else>
                         <td>V${frs.feedbackVersion}</td>
                         
                      </tr>
                     
                    </g:each>
                  
                  
                
                  </table>
                   
                   <h3>Data Manager QA Review</h3>
                  <table>
                    <tr>
                      <th>Reviewed By </th><th>Review Date</th>
                    </tr>
                    
                    <tr>
                      <td>${feedbackFznInstance.reviewedBy}</td>
                    <g:if test="${feedbackFznInstance.reviewDate}">
                      <td><g:formatDate format="MM/dd/yyyy" date="${feedbackFznInstance.reviewDate}"/></td>
                      
                    </g:if>
                    <g:else>
                      <td>Not reviewed yet.</td>
                    </g:else>
                    </tr>
                  
                  
                
                  </table>
                   
              
                  
                   
                
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" /></span>
                    <g:if test="${canSub}">
                         <span class="button"><g:actionSubmit class="delete" action="submit" value="Submit" onclick="return sub()" /></span>
                    </g:if>            
                </div>
            </g:form>
        </div>
                <g:if test="${AppSetting.findByCode('PF_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PF_DISCLAIMER').bigValue}</p>
                </g:if>
                 
    </body>
</html>
