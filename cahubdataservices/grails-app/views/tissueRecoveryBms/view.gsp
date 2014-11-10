
<%@ page import="nci.obbr.cahub.forms.bms.TissueRecoveryBms" %>
<g:set var="bodyclass" value="tissuerecoverybms view wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">          
            <h1>View Tissue Recovery Form for ${tissueRecoveryBmsInstance.caseRecord.caseId}
              <g:if test="${tissueRecoveryBmsInstance.dateSubmitted}">
                , Submitted on <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance.dateSubmitted}"/>
              </g:if>
            </h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tissueRecoveryBmsInstance}">
            <div class="errors">
                <g:renderErrors bean="${tissueRecoveryBmsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${tissueRecoveryBmsInstance?.caseRecord}" form="bmsTrf" />
                <table>
                  <tr><th colspan="3" >Case Details</th></tr>
                  <tr>
                    <td><b>BMS Case ID:</b>
                        <g:displayCaseRecordLink caseRecord="${tissueRecoveryBmsInstance.caseRecord}" session="${session}" />
                        %{-- <g:link controller="caseRecord" action="display" id="${tissueRecoveryBmsInstance.caseRecord.id}"> ${tissueRecoveryBmsInstance.caseRecord.caseId}</g:link> --}%
                    </td>
                    <td><b>BSS:</b>   ${tissueRecoveryBmsInstance.caseRecord.parentCase.bss.name} </td>
                     <td><b>Linked GTEx Case ID:</b>
                         <g:displayCaseRecordLink caseRecord="${tissueRecoveryBmsInstance.caseRecord.parentCase}" session="${session}" />
                         %{-- <g:link controller="caseRecord" action="display" id="${tissueRecoveryBmsInstance.caseRecord.parentCase.id}">${tissueRecoveryBmsInstance.caseRecord.parentCase.caseId}</g:link> --}%
                     </td>
                  </tr>
                </table>
                 <table >
                  <tr><th colspan="4">Tissue Recovery Collection Data</th></tr>
                   <tr>
                    <td style="width:35%"><b>Procedure Start Date:</b>
                      <g:if test="${session.LDS == true}">
                          <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance.caseRecord.parentCase.tissueRecoveryGtex?.collectionDate}"/></td>
                      </g:if>
                        <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                      </g:if>
                    <td><b>Procedure Start Time:</b> ${tissueRecoveryBmsInstance.caseRecord.parentCase.tissueRecoveryGtex?.collectionStartTime}</td>
                  </tr>
                  <tr><td><b>Cardiac Cessation  Time:</b><span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.cardiacCessationTimeTip"/>"></span></td>
          <td  >  
              <g:if  test="${ccDateTime}">
                   <g:if test="${session.LDS == true}">
                               <g:formatDate format="MM/dd/yyyy HH:mm" date="${ccDateTime}"/>
                            </g:if>
                            <g:if test="${session.LDS == false || session.LDS == null}">
                               <g:formatDate format="HH:mm" date="${ccDateTime}"/>
                            </g:if>
                  </td>
           
                  </g:if>
                  <g:else>&nbsp;</g:else>
                    </td>
                  </tr>
                   
                  
                   <tr >
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateSampleLeave', 'errors')}"><b>Date tissue samples leave collection site:</b>
                      
                         <g:if test="${session.LDS == true}">
                              <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance?.dateSampleLeave}"/>
                          </g:if>
                            <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                            </g:if>
                      
                      </td>
                   
                    <td valign="top" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeSampleLeave', 'errors')}"><b>Time tissue samples leave collection site: </b>
                         ${tissueRecoveryBmsInstance?.timeSampleLeave}
                    </td>
                   
                    
                    
                  </tr>
                  
                  
                     <tr >
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateSampleArrive', 'errors')}"><b>Date tissue samples arrive at processing site:</b>
                   
                    
                        <g:if test="${session.LDS == true}">
                            <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance?.dateSampleArrive}"/>   
                        </g:if>
                            <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                            </g:if>
                    
                    </td>
                    
                    
                   
                    <td valign="top"  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeSampleArrive', 'errors')}"><b>Time tissue samples arrive at processing site:</b>
                         ${tissueRecoveryBmsInstance?.timeSampleArrive}
                    </td>
                   
                    
                    
                  </tr>
                  
                   </tr>
                  
                     <tr>
                    <td><b>Environment Temperature: Please confirm data logger is actively<br />recording room temperature during procurement:</b>
                    </td>
                   <td> 
                    ${tissueRecoveryBmsInstance.envTemperature}
                   </td>
                   
                    
                    
                  </tr>
                  
                    <tr>
                    <td  ><b>Is the BMS host tissue collection site contiguous to the GTEx aliquot collection site?</b>
                    </td>
                   
                    <td  > 
                      <table>
                        <tr>
                          <td >Skin: ${tissueRecoveryBmsInstance.skinContiguous}</td>
                          <td >Aorta: ${tissueRecoveryBmsInstance.aortaContiguous}</td>
                          <td >Adrenal Glands: ${tissueRecoveryBmsInstance.adrenalContiguous}</td>
                        </tr>
                        <tr>
                          <td >Muscle, Skeletal: ${tissueRecoveryBmsInstance.muscleContiguous}</td>
                          <td >Pancreas: ${tissueRecoveryBmsInstance.pancreasContiguous}</td>
                          <td >Thyroid Gland: ${tissueRecoveryBmsInstance.thyroidContiguous}</td>
                        </tr>
                      </table>
                   </td>
                   
                  
                  
                   <tr>
                       <td><b>Procurement Site Restriction:</b><span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.restrictionTip"/>"></span></td>
                       <td valign="top" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'restriction', 'errors')}" >${tissueRecoveryBmsInstance?.restriction}</td>
                  </tr>
                </table>
              <g:render template="/tissueRecoveryBms/view_time" model="['dhour':'1', 'specimens': specimens2, 'report1': tissueRecoveryBmsInstance.easeOfUseLn21h, 'report2':  tissueRecoveryBmsInstance.easeOfUseDice1h]" /> 
              <g:render template="/tissueRecoveryBms/view_time" model="['dhour':'4', 'specimens': specimens4, 'report1': tissueRecoveryBmsInstance.easeOfUseLn24h, 'report2':  tissueRecoveryBmsInstance.easeOfUseDice4h]" />  
              <g:render template="/tissueRecoveryBms/view_time" model="['dhour':'6', 'specimens': specimens6, 'report1': tissueRecoveryBmsInstance.easeOfUseLn26h, 'report2':  tissueRecoveryBmsInstance.easeOfUseDice6h]" />   
              <g:render template="/tissueRecoveryBms/view_time" model="['dhour':'15', 'specimens': specimens15, 'report1': tissueRecoveryBmsInstance.easeOfUseLn215h, 'report2':  tissueRecoveryBmsInstance.easeOfUseDice15h]" />    
               <table  >
                  <tr><th colspan="2" >Additional Tissue Recovery Collection Data</th></tr>
                  <tr><td colspan="2" >&nbsp;</td></tr>
                  <tr>
                    <td nowrap="nowrap" ><b>Date Placed in Stabilizer (PaxGene):</b>
                        <g:if test="${session.LDS == true}">
                              <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance?.dateStabilized}"/> 
                         </g:if>
                         <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                       </g:if>
                     </td>
                    <td ><b>Time Placed in Stabilizer (PaxGene):</b> ${tissueRecoveryBmsInstance?.timeStabilized}</td>
                  </tr>
                  <tr>
                    <td nowrap="nowrap" ><b>Date aliquot held in dewar for LN2:</b>
                        <g:if test="${session.LDS == true}">
                              <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance?.dateInDewarLn2}"/> 
                         </g:if>
                         <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                       </g:if>
                     </td>
                    <td ><b>Time aliquot held in dewar for LN2:</b> ${tissueRecoveryBmsInstance?.timeInDewarLn2}</td>
                  </tr>
                  <tr>
                    <td nowrap="nowrap" ><b>Date aliquot held in dewar for Dry Ice:</b>
                        <g:if test="${session.LDS == true}">
                              <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance?.dateInDewarDice}"/> 
                         </g:if>
                         <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                       </g:if>
                    </td>
                    <td ><b>Time aliquot held in dewar for Dry Ice:</b> ${tissueRecoveryBmsInstance?.timeInDewarDice}</td>
                  </tr>
                  <tr><td valign="middle" colspan="2" ><b>Comments:</b> ${tissueRecoveryBmsInstance?.comments}</tr>
                </table>
        </div>
    </body>
</html>
