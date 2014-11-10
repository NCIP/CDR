

<%@ page import="nci.obbr.cahub.prctumor.PrcForm" %>
<g:set var="bodyclass" value="prcform view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${prcFormInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.edit.label.brnprcreport" args="[entityName]" /></title>
        <style type="text/css">
        span.errors select {
          border: 1px solid red;
        }
        span.errors input {
               border: 1px solid red;
        }
        span.errors textarea {
             border: 1px solid red;
        }
        span.errors div{
              border: 1px solid red;
        }
          
      </style>

      
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prcFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prcFormInstance?.id}" />
                <g:hiddenField name="version" value="${prcFormInstance?.version}" />
                
                
              
                <div class="dialog">
                  
                   <table style="height:55px" >
                     <tbody>
                       <tr ><td style="width:50%; height:50px; font:bold 40px Arial; color:#336699;" >BRN</td>
                         <g:if test="${prcFormInstance?.category=='Ovary'}">
                         <td style="font:bold 20px Arial; ">Biospecimen Research Network<br/>Ovarian Slide Pathology Review for <br/>${prcFormInstance?.caseRecord?.caseId}</td>
                         </g:if>
                     
                         <g:if test="${prcFormInstance?.category=='Kidney'}">
                         <td style="font:bold 20px Arial; ">Biospecimen Research Network<br/>Kidney Slide Pathology Review for <br/>${prcFormInstance?.caseRecord?.caseId}</td>
                         </g:if>
                   </tr>
                     </tbody>
                    
                   </table>
                     <table >
                       <tbody>
                          <tr class="prop">
                                <td valign="top" >
                                  <label for="caseRecord"><b>caHUB/BRN Case ID:</b> </label>
                                
                                 <!-- <g:link controller="caseRecord" action="display" id="${prcFormInstance?.caseRecord?.id}">${prcFormInstance?.caseRecord?.caseId}</g:link>-->
                                  ${prcFormInstance?.caseRecord?.caseId}
                                </td>
                                <td>&nbsp;</td> 
                            </tr>
                            
                              <tr class="prop">
                                <td valign="top" >
                                  <label for="reviewedBy"><b>Name of Pathologist Who Reviewed Slide: </b> </label>
                              
                                    ${prcFormInstance?.reviewedBy}
                                </td>
                                 <td valign="top">
                                  <label for="reviewDate"><b>Date of Slide Review:</b></label>
                                   <g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${prcFormInstance?.reviewDate}"/>
                               
                                </td>
                            </tr>
                         
                       </tbody>
                       
                     </table>
                     </div>
                
                <div class="list">
                    <table>
                      <thead>
                        <tr >
                          <g:if test="${prcFormInstance?.category=='Ovary'}">
                          <th colspan="2">Ovarian Slide Pathology Review</th>
                          </g:if>
                           <g:if test="${prcFormInstance?.category=='Kidney'}">
                          <th colspan="2">Kidney Slide Pathology Review</th>
                          </g:if>
                         
                        </tr>
                      </thead>
                        <tbody>
                          <tr >
                            <td><b>Slide ID examined by pathologist:</b></td>
                            <td style="border-bottom: 1px solid #ccc; width:50%">
                       <!-- <g:link controller="slideRecord" action="show" id="${prcFormInstance?.slideRecord?.id}">${prcFormInstance?.slideRecord?.slideId}</g:link>-->
                       ${prcFormInstance?.slideRecord?.slideId}
                     </td>
                          </tr>
                          <tr>
                            <td><b>Organ of origin:</b></td>
                            <td >
                               ${prcFormInstance?.organOrigin}
                             <g:if test="${prcFormInstance?.organOrigin == 'Other'}">
                             <br/><u>Other organ of origin:</u> ${prcFormInstance?.otherOrganOrigin} </g:if>
                            </td>
                          </tr>
                          <tr>
                            <td><b>Histologic type:</b></td>
                            <td  >
                                <g:if test="${prcFormInstance?.histologicType?.code=='OTHER'}">
                                    ${prcFormInstance?.otherHistologicType}
                                  </g:if>
                                 <g:else>
                                   ${prcFormInstance?.histologicType?.name}
                                    <g:if test="${prcFormInstance?.histologicType?.who_code}"> (WHO code: ${prcFormInstance?.histologicType?.who_code}) </g:if>
                                    <g:if test="${prcFormInstance?.histologicType?.code=='C7' ||prcFormInstance?.histologicType?.code=='C8' || prcFormInstance?.histologicType?.code=='C9'}"><br/><u>Types and %:</u> ${prcFormInstance?.histologicTypeDetail}</g:if>
                                     <g:if test="${prcFormInstance?.histologicType?.code=='C20'}"><br/><u>Types:</u> ${prcFormInstance?.histologicTypeDetail}</g:if>
                                    
                                </g:else>


                            </td>
                           
                          </tr>
                          
                          <g:if test="${prcFormInstance?.category=='Kidney'}">
                            <tr>
                              <td><b>Presence of sarcomatoid features:</b></td>
                               
                             
                              <td >
                                  ${prcFormInstance?.sarcomatoid}
                                <g:if test="${prcFormInstance?.sarcomatoid == 'Present'}"><br/><u>sarcomatoid features:</u> ${prcFormInstance?.sarcomatoidDesc}</g:if>
                                
                               </td>
                              
                            </tr>
                          
                          </g:if>
                          
                          
                          
                          <tr>
                            <td><b>Greatest tumor dimension on slide:</b></td>
                            <td >${prcFormInstance?.tumorDimension}(mm)</td>
                           
                          </tr>
                          
                           <tr>
                            <td><b>Percent of cross-sectional surface area composed of tumor focus:</b></td>
                            <td>${prcFormInstance?.pctTumorArea} %</td>
                           
                          </tr>
                          
                            <tr>
                            <td><b>Percent of tumor cellularity by cell count of the entire slide:</b></td>
                            <td>${prcFormInstance?.pctTumorCellularity} %</td>
                            
                          </tr>
                           <tr >
                          <th colspan="2">Histologic Profile Quantitative Assessment should total 100%. BRN Case Acceptance Criteria require Necrosis Percentage of <20% AND Tumor Content of &ge;50% Tumor Cells by surface area.</th>
                           </tr>
                           <tr>
                             <td><b>Histologic profile quantitative assessment:</b></td>
                             <td>
                               <table>
                                 <tr>
                                   <td>Percent Viable Tumor by Surface Area</td>
                                   <td style="border-bottom: 1px solid #ccc; WIDTH:25%">${prcFormInstance?.pctViablTumor} %</td>
                                   <td>%</td>
                                 </tr>
                                  <tr>
                                   <td>Percent Necrotic Tumor by Surface Area</td>
                                   <td>${prcFormInstance?.pctNecroticTumor}</td>
                                    <td>%</td>
                                 </tr>
                                 
                                  <tr>
                                   <td>Percent Viable Non-Tumor Tissue by Surface Area</td>
                                   <td>${prcFormInstance?.pctViableNonTumor}</td>
                                   <td>%</td>
                                 </tr>
                                 
                                   <g:if test="${prcFormInstance?.pctNonCellular > 0}">
                                      <tr>
                                     <td>Percent Non-Cellular Component by Surface Area (i.e., mucin, hemorrhage, blood clot, etc.)</td>
                                     <td>${prcFormInstance?.pctNonCellular}</td>
                                      <td >%</td>
                                    </tr>
                                      <tr >
                                        <td style="border-bottom: 1px solid">Non-Cellular Component:</td>
                                        <td   style="border-bottom: 1px solid">${prcFormInstance?.nonCellularDesc}</td>
                                         <td style="border-bottom: 1px solid">&nbsp;</td>
                                      </tr>
                                 </g:if>
                                 <g:else>
                                       <tr>
                                     <td style="border-bottom: 1px solid">Percent Non-Cellular Component by Surface Area (i.e., mucin, hemorrhage, blood clot, etc.)</td>
                                     <td style="border-bottom: 1px solid">${prcFormInstance?.pctNonCellular}</td>
                                     <td style="border-bottom: 1px solid">%</td>
                                    </tr>
                                 </g:else>
                                 
                                  <tr>
                                   <td>Histologic Profile Total % (Should Equal 100%)</td>
                                   <td> ${prcFormInstance?.hisTotal}</td>
                                   <td>%</td>
                                 </tr>
                                 
                                 
                               </table>
                             </td>
                         
                             
                           </tr>
                           
                          
                           <tr>
                             <td ><b>What histologic grading system was applied?</b></td>
                               <td >
                               ${prcFormInstance?.gradingSystem}
                             </td>
                             
                             
                           </tr>
                           
                           <tr>
                             <td ><b>Histologic Grade: </b> </td>
                             <td >
                               ${prcFormInstance?.grade}
                             </td>
                          
                           </tr>
                           
                            
                           
                           
                           
                           <tr>
                             <td>
                               <b> This slide meets the Microscopic Analysis Criteria of the BRN Project of Necrosis Percentage of <20% AND Tumor Content of &ge;50% Tumor Cells by surface area. </b>
                             </td>
                             <td>
                               ${prcFormInstance?.meetsCriteria}
                              <g:if test="${prcFormInstance?.meetsCriteria == 'No'}">
                                <br/> <u>Findings:</u>  ${prcFormInstance?.reasonNotMeet}
                               </g:if>
                             </td>
                             
                            
                           </tr>
                           
                           
                           <tr>
                             <td><b>Pathology review comments:</b></td>
                             <td> 
                                 ${prcFormInstance?.pathologyComments}
                             </td>
                         
                             
                           </tr>
                           <tr>
                             <th colspan="2"><b>Concordance With Findings of the Local BSS Pathologist</b></th>
                           </tr>
                            <tr>
                             <td><b>This slide is consistent with the findings of the local BSS pathologist.</b></td>
                              <td>
                                ${prcFormInstance?.consistentLocalPrc}
                            <g:if test="${prcFormInstance?.consistentLocalPrc == 'No'}"><br/><u>Findings:</u> ${prcFormInstance?.reasonNotCons}
                              </g:if>
                              
                             </td>
                           
                    </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                  <g:if test="${prcFormInstance?.status=='Reviewed'}">
                    <span class="button"><g:actionSubmit class="save" action="reedit" value="Resume Editing"  onclick="return confirm('Are you sure to resume editing?')" /></span>
                  </g:if> 
                  <g:if test="${prcFormInstance?.status=='Editing'}">
                    <span class="button"><g:actionSubmit class="save" action="edit" value="Edit" /></span>
                  </g:if>
                </div>
                
            </g:form>
        </div>
    </body>
</html>
