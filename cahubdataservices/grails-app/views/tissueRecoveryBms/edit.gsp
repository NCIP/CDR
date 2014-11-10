

<%@ page import="nci.obbr.cahub.forms.bms.TissueRecoveryBms" %>
<g:set var="bodyclass" value="tissuerecoverybms edit wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>


<script type="text/javascript">
  function setTimeDelay(hour){
    //alert("in time delay func... hour:" + hour)
    document.getElementById("time_delay").value=hour
   // alert('${needWarning4}')
    var w4 = '${needWarning4}'
    var w6 = '${needWarning6}'
    var w15 = '${needWarning15}'
    if(hour==4 && w4=='true')
        return confirm('The previous timepoint sheet(s) have not been completed yet, are you sure to work on this timepoint sheet ('+hour +' Hours)?')
    if(hour==6 && w6=='true')
        return confirm('The previous timepoint sheet(s) have not been completed yet, are you sure to work on this timepoint sheet ('+hour +' Hours)?')
     if(hour==15 && w15=='true')
        return confirm('The previous timepoint sheet(s) have not been completed yet, are you sure to work on this timepoint sheet ('+hour +' Hours)?')
      
    return true
  }
</script>

    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
          </div>
     </div>
    <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tissueRecoveryBmsInstance}">
            <div class="errors">
                <g:renderErrors bean="${tissueRecoveryBmsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${tissueRecoveryBmsInstance?.caseRecord}" form="bmsTrf" />
            <g:form method="post">
                <g:hiddenField name="id" value="${tissueRecoveryBmsInstance?.id}" />
                <g:hiddenField name="version" value="${tissueRecoveryBmsInstance?.version}" />
                <g:hiddenField name="time_delay" id="time_delay" value="" />
                <div class="list">
                <table >
                  <tr><th colspan="3" >Case Details</th></tr>
                  <tr>
                    <td><b>BMS Case ID:</b>
                        <g:displayCaseRecordLink caseRecord="${tissueRecoveryBmsInstance.caseRecord}" session="${session}" />
                        %{-- <g:link controller="caseRecord" action="display" id="${tissueRecoveryBmsInstance.caseRecord.id}"> ${tissueRecoveryBmsInstance.caseRecord.caseId}</g:link> --}%
                    </td>
                     <td><b>BSS:</b> ${tissueRecoveryBmsInstance.caseRecord.parentCase.bss.name} </td>
                     <td><b>Linked GTEx Case ID:</b>
                         <g:displayCaseRecordLink caseRecord="${tissueRecoveryBmsInstance.caseRecord.parentCase}" session="${session}" />
                         %{-- <g:link controller="caseRecord" action="display" id="${tissueRecoveryBmsInstance.caseRecord.parentCase.id}">${tissueRecoveryBmsInstance.caseRecord.parentCase.caseId}</g:link> --}%
                     </td>
                    
                  </tr>
                </table>
               
                <br></br>
                
                 <table   >
                  <tr><th colspan="2" >Tissue Recovery Collection Data</th></tr>
                   <tr>
                    <td width="50%" ><b>Procedure Start Date:</b>
                    <g:formatDate format="MM/dd/yyyy" date="${tissueRecoveryBmsInstance.caseRecord.parentCase.tissueRecoveryGtex?.collectionDate}"/></td>
                    <td style ="border-bottom: 1px solid #ddd;border-right: 1px solid #ddd;"><b>Procedure Start Time:</b>
                    ${tissueRecoveryBmsInstance.caseRecord.parentCase.tissueRecoveryGtex?.collectionStartTime}</td>
                   <tr>
                    <td colspan="" >
                       <b>Cardiac Cessation  Time:</b><span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.cardiacCessationTimeTip"/>"></span></td>
                  <g:if  test="${ccDateTime}">
          <td style ="border-bottom: 1px solid #ddd;border-right: 1px solid #ddd;" > <g:formatDate format="MM/dd/yyyy HH:mm" date="${ccDateTime}"/></td>
                  </g:if>
                  <g:else>
                    <td  > &nbsp;</td>
                  </g:else>
                  </tr>
                   
                  
                   <tr >
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateSampleLeave', 'errors')}" ><b>Date tissue samples leave collection site:</b>
                      <g:jqDatePicker name="dateSampleLeave" value="${tissueRecoveryBmsInstance?.dateSampleLeave}" /></td>
                   
                    <td valign="top"  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeSampleLeave', 'errors')}"><b>Time tissue samples leave collection site: </b>
                         <g:textField name="timeSampleLeave" value="${tissueRecoveryBmsInstance?.timeSampleLeave}" class="timeEntry"/>
                    </td>
                   
                    
                    
                  </tr>
                  
                  
                     <tr >
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateSampleArrive', 'errors')}"><b>Date tissue samples arrive at processing site:</b>
                      <g:jqDatePicker name="dateSampleArrive" value="${tissueRecoveryBmsInstance?.dateSampleArrive}" /> </td>
                   
                    <td valign="top"  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeSampleArrive', 'errors')}"><b>Time tissue samples arrive at processing site:</b>
                         <g:textField name="timeSampleArrive" value="${tissueRecoveryBmsInstance?.timeSampleArrive}" class="timeEntry"/>
                    </td>
                   
                    
                    
                  </tr>
                  
                    </tr>
                  
                     <tr>
                    <td  ><b>Environment Temperature : Please confirm data logger is actively recording room temperature during procurement:</b>
                    </td>
                   
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'envTemperature', 'errors')}"> <g:select name="envTemperature" from="${['','Yes','No', 'Unknown']}" value="${tissueRecoveryBmsInstance.envTemperature}" />
                   </td>
                   
                    
                    
                  </tr>
                  
                      </tr>
                  
                     <tr>
                    <td  ><b>Is the BMS host tissue collection site contiguous to the GTEx aliquot collection site?</b>
                    </td>
                   
                    <td  > 
                      <table>
                        <tr>
                          <td class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'skinContiguous', 'errors')}">
                            <g:if test="${tissueList.contains('SKIN')}">
                              Skin: <g:select name="skinContiguous" from="['','Yes', 'No']"  value="${tissueRecoveryBmsInstance.skinContiguous}"   />
                           </g:if>
                           <g:else>
                                Skin: <g:select name="skinContiguous" from="['','Not Collected']"  value="${tissueRecoveryBmsInstance.skinContiguous}"   />
                            </g:else>
                          </td>
                          <td class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'aortaContiguous', 'errors')}">
                            <g:if test="${tissueList.contains('AORTA')}">
                                Aorta: <g:select name="aortaContiguous" from="['','Yes', 'No']"  value="${tissueRecoveryBmsInstance.aortaContiguous}"   />
                            </g:if>
                          <g:else>
                               Aorta: <g:select name="aortaContiguous" from="['', 'Not Collected']"  value="${tissueRecoveryBmsInstance.aortaContiguous}"   />
                          </g:else>
                          
                            </td>
                          <td class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'adrenalContiguous', 'errors')}">
                             <g:if test="${tissueList.contains('ADRENA')}">
                            Adrenal Glands: <g:select name="adrenalContiguous" from="['','Yes', 'No']"  value="${tissueRecoveryBmsInstance.adrenalContiguous}"   />
                             </g:if> 
                          <g:else>
                             Adrenal Glands: <g:select name="adrenalContiguous" from="['', 'Not Collected']"  value="${tissueRecoveryBmsInstance.adrenalContiguous}"   />
                          </g:else>
                             </td>
                        </tr>
                        <tr>
                          <td class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'muscleContiguous', 'errors')}">
                            <g:if test="${tissueList.contains('MUSCSK')}">
                            Muscle, Skeletal: <g:select name="muscleContiguous" from="['','Yes', 'No']"  value="${tissueRecoveryBmsInstance.muscleContiguous}"   />
                            </g:if>
                            <g:else>
                                Muscle, Skeletal: <g:select name="muscleContiguous" from="['', 'Not Collected']"  value="${tissueRecoveryBmsInstance.muscleContiguous}"   />
                            </g:else>
                            </td>
                          <td class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'pancreasContiguous', 'errors')}">
                             <g:if test="${tissueList.contains('PANCRE')}">
                            Pancreas: <g:select name="pancreasContiguous" from="['','Yes', 'No']"  value="${tissueRecoveryBmsInstance.pancreasContiguous}"   />
                             </g:if>
                            <g:else>
                               Pancreas: <g:select name="pancreasContiguous" from="['', 'Not Collected']"  value="${tissueRecoveryBmsInstance.pancreasContiguous}"   />
                            </g:else>
                             </td>
                          <td class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'thyroidContiguous', 'errors')}">
                            <g:if test="${tissueList.contains('THYROI')}">
                            Thyroid Gland: <g:select name="thyroidContiguous" from="['','Yes', 'No']"  value="${tissueRecoveryBmsInstance.thyroidContiguous}"   />
                            </g:if>
                          <g:else>
                             Thyroid Gland: <g:select name="thyroidContiguous" from="['', 'Not Collected']"  value="${tissueRecoveryBmsInstance.thyroidContiguous}"   />
                          </g:else>
                          </td>
                        </tr>
                      </table>
                   </td>
                   
                    
                    
                  </tr>
                  
                  
                   <tr>
                       <td ><b>Procurement Site Restriction:</b><span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.restrictionTip"/>"></span></td>
                       <td valign="top" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'restriction', 'errors')}" >
                                     <g:textArea style="height:50px;width:250px;" name="restriction" value="${tissueRecoveryBmsInstance?.restriction}" />
                                </td>
                  </tr>
                </table>
                <br></br>
                
                <table  >
                   <tr><th colspan="2" >Delay Timepoints</th></tr>
                   <tr>
                    <td style ="width:300px; "><b>Delay Timepoint: 1 Hour</b></td>
                    <td > 
                       <div style="width:82px" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'protocol1hStarted', 'errors')}"> <g:actionSubmit class="show" action="edit2" value="Edit" onclick="return setTimeDelay(1)"  /></div>
                                </td>
                   
                  </tr>
                   <tr>
                    <td style ="width:300px;"><b>Delay Timepoint: 4 Hours</b></td>
                    <td > 
                     <div style="width:82px" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'protocol4hStarted', 'errors')}">   <g:actionSubmit class="show" action="edit2" value="Edit" onclick="return setTimeDelay(4)" /></div>
                                </td>
                   
                  </tr>
                  
                  </tr>
                   <tr>
                    <td style ="width:300px;"><b>Delay Timepoint: 6 Hours</b></td>
                    <td >               
                      <div style="width:82px" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'protocol6hStarted', 'errors')}">  <g:actionSubmit class="show" action="edit2" value="Edit" onclick="return setTimeDelay(6)" /></div>                          
                          </td>
                   
                  </tr>
                  
                  <tr>
                    <td style ="width:300px;"><b>Delay Timepoint: 15 Hours</b></td>
                    <td > 
                     <div style="width:82px" class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'protocol15hStarted', 'errors')}"> <g:actionSubmit class="show" action="edit2" value="Edit" onclick="return setTimeDelay(15)" /></div>
                                </td>
                   
                  </tr>
                  
                   
                  
                </table>
                <br></br>
                
                  <table >
                  <tr><th colspan="2" >Additional Tissue Recovery Collection Data</th></tr>
                  <tr>
                    <td width="40%" nowrap="nowrap"  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateStabilized', 'errors')}"><b>Date Placed in Stabilizer (PaxGene):</b>
                      <g:jqDatePicker name="dateStabilized" value="${tissueRecoveryBmsInstance?.dateStabilized}" /></td>
                     
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeStabilized', 'errors')}"><b>Time Placed in Stabilizer (PaxGene):</b>
                     <g:textField name="timeStabilized" value="${tissueRecoveryBmsInstance?.timeStabilized}" class="timeEntry"/> </td>
                   
                    
                  </tr>
                  
                  
                   
                  <tr>
                    <td width="40%" nowrap="nowrap"  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateInDewarLn2', 'errors')}"><b>Date aliquot held in dewar for LN2:</b>
                      <g:jqDatePicker name="dateInDewarLn2" value="${tissueRecoveryBmsInstance?.dateInDewarLn2}" /></td>
                     
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeInDewarLn2', 'errors')}"><b>Time aliquot held in dewar for LN2:</b>
                     <g:textField name="timeInDewarLn2" value="${tissueRecoveryBmsInstance?.timeInDewarLn2}" class="timeEntry"/> </td>
                   
                  </tr>
                  
                  
                   <tr>
                    <td width="40%" nowrap="nowrap"  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'dateInDewarDice', 'errors')}"><b>Date aliquot held in dewar for Dry Ice:</b>
                      <g:jqDatePicker name="dateInDewarDice" value="${tissueRecoveryBmsInstance?.dateInDewarDice}" /></td>
                     
                    <td  class="value ${hasErrors(bean: tissueRecoveryBmsInstance, field: 'timeInDewarDice', 'errors')}"><b>Time aliquot held in dewar for Dry Ice:</b>
                     <g:textField name="timeInDewarDice" value="${tissueRecoveryBmsInstance?.timeInDewarDice}" class="timeEntry"/> </td>
                   
                  </tr>
                  
                  
                  <tr>
                    <td valign="middle"><b>Comments:</b></td>
                    <td>
                        <g:textArea style="height:50px;width:250px;" name="comments" value="${tissueRecoveryBmsInstance?.comments}" />
                   </td>
                  </tr>
                 
                </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" /></span>
                    <g:if test="${canSubmit == 'Yes'}">
                      <span class="button"><g:actionSubmit class="save" action="submit" value="Submit" /></span>
                    </g:if>
                </div>
            </g:form>
        </div>
    </body>
</html>
