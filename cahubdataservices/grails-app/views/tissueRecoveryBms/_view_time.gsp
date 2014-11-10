
                  <div class="list">
                  <table  >
                  <tr><th colspan="2">Delay Timepoint: ${dhour} Hour(s)</th></tr>
                  
                  <tr><th colspan="2">Tissue Recovery Collection</th></tr>
                   <tr><td colspan="2" class="tablefooter"><span class="cahub-tooltip"></span>Mouseover for instructions</td></tr>
                  
                  <tr>
                    <td colspan="2" style="padding:0px;">
                      <table>
                        <tr>
                          <th>Specimen ID</th>
                          <th>Tissue Type</th>
                          <th>Tissue Location</th>
                          <th>Fixation Method </th>
                          <th>Time Removed 1<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.time1Tip"/>"></span></th>
                          <th>Time Removed 2<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.time2Tip"/>"></span></th>
                          <th>Fixation Start Time<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.timeFixativeTip"/>"></span></th>
                          <th>Delay to Fixation<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.delayTip"/>"></span></th>
                          <th>Size if Different </th>
                      <th>Tissue Consistency</th>
                          <th>Comments</th>
                        </tr>
                        <g:each in="${specimens}" status="i" var="s">
                          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td nowrap="nowrap">${s.specimenId}</td>
                            <td nowrap="nowrap">${s.tissue}</td>
                            <td nowrap="nowrap">${s.tissueLoc}</td>
                            <td nowrap="nowrap">${s.fixative}</td>
                             <g:if test="${session.LDS == true}">
                                       <td> <g:formatDate format="MM/dd/yyyy HH:mm" date="${s.timeRemovFromBody}"/>  </td>
                           </g:if>
                          <g:if test="${session.LDS == false || session.LDS == null}">
                                      <td> <g:formatDate format="HH:mm" date="${s.timeRemovFromBody}"/>  </td>
                            </g:if>
                            
                          
                             
                              
                            <g:if test="${session.LDS == true}">
                                       <td> <g:formatDate format="MM/dd/yyyy HH:mm" date="${s.procTimeRemov}"/>  </td>
                           </g:if>
                           <g:if test="${session.LDS == false || session.LDS == null}">
                                      <td> <g:formatDate format="HH:mm" date="${s.procTimeRemov}"/>  </td>
                            </g:if>
             
                                 
                            <g:if test="${session.LDS == true}">
                               <td><g:formatDate format="MM/dd/yyyy HH:mm" date="${s.timeInFix}"/></td>           
                            </g:if>
                           <g:if test="${session.LDS == false || session.LDS == null}">
                                 <td><g:formatDate format="HH:mm" date="${s.timeInFix}"/></td>      
                            </g:if>
                              
                           
                            <td>${s.delay}</td>
                            <td>${s.size}</td>
                             <td>${s.tissue_cons}</td>
                            <td>${s.comments}</td>
                          </tr>
                        </g:each>
                      </table>
                      
                    </td>
                  </tr>
                  
                 
                  <tr>
                     <td style ="width:300px;"><b>Please report on the ease of use for LN2 fixation at this timepoint:</b>
                   </td>
                    <td>
                     ${report1}
                  </td>      
                    
                  </tr>
                  
                   <tr>
                     <td style ="width:300px;"><b>Please report on the ease of use for Dry Ice fixation at this timepoint:</b>
                   </td>
                    <td>
                     ${report2}
                  </td>      
                    
                  </tr>
                 
                  
                  
                
                 
            </table>
          </div>
               
