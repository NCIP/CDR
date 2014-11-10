

<%@ page import="nci.obbr.cahub.prc.PrcReport" %>
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prcreport edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'feedbackFzn.label', default: 'FeedbackFzn')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
       
      
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
                  <g:if test="${feedbackIssueResolutionDisplayList}">
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
                  </g:if> 
                   <g:else>
                     <p>No issues.</p>
                   </g:else>
                
                  
                
                   <h3>Comments</h3>
                   <table>
                     <tr>
                       <td> ${feedbackFznInstance?.comments}</td>
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
                   
              
            
                        <g:if test="${feedbackFznInstance.status == 'Submitted'}">
                   <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')}">
                      <div class="buttons">
                        
                        
                        <g:if test="${!feedbackFznInstance.reviewDate}">
                                <span class="button"><g:actionSubmit class="save" action="qareview" value="DM QA Review Complete" /></span>
                        </g:if>
                        <span class="button"><g:actionSubmit class="save" action="startnew" value="Start Next Version"  onclick="return confirm('Are you sure you want to create a new version of the Procurement Feedback Fzn?')" /></span>
                            

                      </div>
                     
                   </g:if>

                
                </g:if>       
               
            </g:form>
        </div>
                <g:if test="${AppSetting.findByCode('PF_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PF_DISCLAIMER').bigValue}</p>
                </g:if>
                 
    </body>
</html>
