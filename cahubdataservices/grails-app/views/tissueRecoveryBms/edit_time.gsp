<%@ page import="nci.obbr.cahub.forms.bms.TissueRecoveryBms" %>
<g:set var="bodyclass" value="tissuerecoverybms edit_time" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'tissueRecoveryBms.label', default: 'TissueRecoveryBms')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <script type="text/javascript"> var specimenSize = ${specimens.size()}; var ccString = "${ccString}";</script>
        <script type="text/javascript" src="/cahubdataservices/js/tissueRecoveryBMS.js"></script>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
            <g:link class="list" action="edit" id="${params.id}">Back to TRF Home</g:link>
          </div>
     </div>
    <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label" args="[entityName]" /> (Delay Timepoint: ${timedelay} Hour(s))</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tissueRecoveryBmsInstance}">
            <div class="errors"><g:renderErrors bean="${tissueRecoveryBmsInstance}" as="list" /></div>
            </g:hasErrors>
            <g:queryDesc caserecord="${tissueRecoveryBmsInstance?.caseRecord}" form="bmsTrf" />
            <g:form method="post" >
                <g:hiddenField name="id" value="${tissueRecoveryBmsInstance?.id}" />
                <g:hiddenField name="version" value="${tissueRecoveryBmsInstance?.version}" />
                <g:hiddenField name="time_delay" value="${timedelay}" />
                <g:each in="${specimens}" status="i" var="s">
                  <g:hiddenField name="${s.ids}" value="isid" />
                </g:each>
                <table>
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
                 <table class="pushdown">
                  <tr><th colspan="3">Tissue Recovery Collection Data</th></tr>
                  <tr>
                    <td>
                      <b>Procedure Start Date:</b>
                      <g:formatDate format="MM/dd/yyyy" class="dateentry" date="${tissueRecoveryBmsInstance.caseRecord.parentCase.tissueRecoveryGtex?.collectionDate}"/>
                    </td><td><b>Procedure Start Time:</b>
                      <span class="dateentry">${tissueRecoveryBmsInstance.caseRecord.parentCase.tissueRecoveryGtex?.collectionStartTime}</span>
                    </td><td><b>Cardiac Cessation Time:</b>
                      <g:if  test="${ccDateTime}">
                        <g:formatDate format="MM/dd/yyyy HH:mm" class="dateentry" date="${ccDateTime}"/>
                      </g:if>
                    </td>
                  </tr>
                </table>
                <table class="pushdown">
                  <tr><th colspan="2" >Delay Timepoint: ${timedelay} Hour(s)</th></tr>
                  <tr><th colspan="2" >Tissue Recovery Collection</th></tr>
                  <tr><td colspan="2" ><span class="tableheader"><span class="cahub-tooltip"></span>Mouseover for instructions</span></td></tr>
                  <tr><td colspan="2">
                      <div class="list"><table>
                          <tr>
                          <thead>
                            <th>Specimen ID</th>
                            <th>Tissue Type</th>
                            <th>Tissue Location</th>
                            <th>Fixation<br />Method</th>
                            <th class="leftblue">Date<br />Removed 1<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.date1Tip"/>"></span><br/><g:select id="batch_date3" from="${dateList}"  noSelection="['': 'Select']"  /></th>
                            <th class="rightblue">Time<br />Removed 1<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.date1Tip"/>"></span></th>
                            <th class="leftblue">Date Removed 2<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.date2Tip"/>"></span><br/><g:select id="batch_date1" from="${dateList}"  noSelection="['': 'Select']"  /></th>
                            <th class="rightblue">Time<br />Removed 2<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.time2Tip"/>"></span></th>
                            <th class="leftblue">Fixation Start Date<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.dateFixativeTip"/>"></span><br/><g:select id="batch_date2" from="${dateList}"  noSelection="['': 'Select']" /></th>
                            <th class="rightblue">Fixation<br />Start Time<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.timeFixativeTip"/>"></span></th>
                            <th class="leftblue">Delay to<br />Fixation<span class="cahub-tooltip" data-msg="<g:message code="tissueRecoveryBms.delayTip"/>"></span></th>
                            <th>Size if<br />Different</th>
                            <th>Tissue<br />Consistency<br/><g:select id="batch_cons" from="${['Firm','Soft','Diffluent']}"  noSelection="['': 'Select']" /></th>
                            <th>Comments</th>
                            </thead>
                          </tr>
                        <g:each in="${specimens}" status="i" var="s">
                          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td class="nowrap">${s.specimenIds}</td>
                            <td>${s.tissue}</td>
                            <td class="${errorMap.get("tissueLocation_" +s.ids)}"> <g:select class="tissueLocationdp" name="tissueLocation_${s.ids}" id="tissueLocation_${s.ids}"  from="${s.locationList}" optionKey="id" value="${s.tissueLoc?s.tissueLoc:s.parentTissueLoc}" noSelection="['': 'Choose Tissue Location']"  onChange="showhide('${s.ids}')" />
                            <br /><g:if test="${s.tissueLoc==28}"><span style="display:block" id="otl_${s.ids}">Enter other location:  <g:textField style="width:150px;"  name="otherTissueLocation_${s.ids}" id="otherTissueLocation_${s.ids}" value="${s.otherTissueLoc}"   /></span>   
                            </g:if><g:else><span style="display:none" id="otl_${s.ids}">Enter other location:  <g:textField style="width:150px;"  name="otherTissueLocation_${s.ids}" id="otherTissueLocation_${s.ids}" value="${s.otherTissueLoc}"  /></span> 
                            </g:else></td>
                            <td>${s.fixative}</td>
                             <td class="leftblue value ${errorMap.get("date3_" +s.ids)}"><div>
                                <g:each in="${dateList}" status="j" var="d">
                                  <g:if test="${j==0}">
                                  <g:radio name="date3_${s.ids}" id="date3_${i}_${j}" value="${d}"  checked="${s.date3?s.date3==d:s.pdate3==d}" /><label for="date3_${i}_${j}" >${d}</label><br/>
                                  </g:if> 
                                  <g:else>
                                     <g:radio name="date3_${s.ids}" id="date3_${i}_${j}" value="${d}"  checked="${s.date3?s.date3==d:s.pdate3==d}"  /><label for="date3_${i}_${j}" >${d}</label>
                                  </g:else>
                                </g:each></div></td>
                            <td class="rightblue ${errorMap.get("time3_" +s.ids)}"><g:textField id="time3_${i}" name="time3_${s.ids}" value="${s.time3?s.time3:s.ptime3}" class="timeEntry" /></td>
                            <td class="leftblue value ${errorMap.get("date1_" +s.ids)}"><div>
                                <g:each in="${dateList}" status="j" var="d">
                              <g:if test="${j==0}">
                              <g:radio name="date1_${s.ids}" id="date1_${i}_${j}" value="${d}" checked="${s.date1==d}"  /><label for="date1_${i}_${j}" >${d}</label><br/>
                              </g:if><g:else>
                                 <g:radio name="date1_${s.ids}" id="date1_${i}_${j}" value="${d}" checked="${s.date1==d}"   /><label for="date1_${i}_${j}" >${d}</label>
                              </g:else>
                            </g:each></div></td>
                            <td  class="${errorMap.get("time1_" +s.ids)} rightblue" > <g:textField id="time1_${i}" name="time1_${s.ids}" value="${s.time1}" class="timeEntry" />   </td>
                            <td class="leftblue value ${errorMap.get("date2_" +s.ids)}"><div>
                             <g:each in="${dateList}" status="j" var="d">
                              <g:if test="${j==0}">
                              <g:radio name="date2_${s.ids}" id="date2_${i}_${j}" value="${d}" checked="${s.date2==d}" onclick="setDate('${i}')"  /><label for="date2_${i}_${j}" >${d}</label><br/>
                              </g:if><g:else>
                                 <g:radio name="date2_${s.ids}" id="date2_${i}_${j}" value="${d}" checked="${s.date2==d}" onclick="setDate('${i}')" /><label for="date2_${i}_${j}" >${d}</label>
                              </g:else></g:each></div></td>
                              <g:if test="${ccTime}">
                               <td class="rightblue ${errorMap.get("time2_" +s.ids)}"><g:textField id="time2_${i}" name="time2_${s.ids}" value="${s.time2}" class="timeEntry" onchange="setDate('${i}')"/></td>
                              </g:if><g:else>
                                <td class="rightblue ${errorMap.get("time2_" +s.ids)}"><g:textField id="time2_${i}" name="time2_${s.ids}" value="${s.time2}" class="timeEntry" /></td>
                              </g:else>
                            <td class="leftblue"><span id="delay_${i}" >${s.delay}</span></td>
                            <td><g:textField id="size_${i}" name="size_${s.ids}" value="${s.size}" style="width:50px" /></td>
                             <td class="${errorMap.get("tissue_cons_" +s.ids)}"><g:select id="tissue_cons_${i}" name="tissue_cons_${s.ids}" from="${['','Firm','Soft','Diffluent']}"  value="${s.tissue_cons}"/></td>
                            <td class="${errorMap.get("comments_" +s.ids)}"><g:textArea id="comments_${i}" name="comments_${s.ids}" value="" style="height:28px;width:150px;" value="${s.comments}" /></td>
                          </tr>
                        </g:each>
                      </table></div>                                            
                    </td></tr>
                  <g:if test="${timedelay == '1'}"><tr>
                    <td class="width300"><b>Please report on the ease of use for LN2 fixation at this timepoint:</b></td>
                    <td><g:textField name="easeOfUseLn21h" value="${tissueRecoveryBmsInstance.easeOfUseLn21h}" size="150" /></td>      
                  </tr><tr>
                    <td class="width300"><b>Please report on the ease of use for Dry Ice fixation at this timepoint:</b></td>
                    <td><g:textField name="easeOfUseDice1h" value="${tissueRecoveryBmsInstance.easeOfUseDice1h}" size="150" /></td>      
                  </tr></g:if>
                  <g:if test="${timedelay == '4'}"><tr>
                    <td class="width300"><b>Please report on the ease of use for LN2 fixation at this timepoint:</b></td>
                    <td><g:textField name="easeOfUseLn24h" value="${tissueRecoveryBmsInstance.easeOfUseLn24h}" size="150" /></td>      
                  </tr><tr>
                     <td class="width300"><b>Please report on the ease of use for Dry Ice fixation:</b></td>
                     <td><g:textField name="easeOfUseDice4h" value="${tissueRecoveryBmsInstance.easeOfUseDice4h}" size="150" /></td>      
                  </tr></g:if>
                  <g:if test="${timedelay == '6'}"><tr>
                    <td><b>Please report on the ease of use for LN2 fixation at this timepoint:</b></td>
                    <td><g:textField name="easeOfUseLn26h" value="${tissueRecoveryBmsInstance.easeOfUseLn26h}" size="150" /></td>      
                  </tr><tr>
                    <td><b>Please report on the ease of use for Dry Ice fixation:</b></td>
                    <td class="width300"><g:textField name="easeOfUseDice6h" value="${tissueRecoveryBmsInstance.easeOfUseDice6h}" size="150" /></td>      
                  </tr></g:if>
                  <g:if test="${timedelay == '15'}"><tr>
                     <td class="width300"><b>Please report on the ease of use for LN2 fixation at this timepoint:</b></td>
                     <td><g:textField name="easeOfUseLn215h" value="${tissueRecoveryBmsInstance.easeOfUseLn215h}" size="150" /></td>      
                  </tr><tr>
                     <td class="width300"><b>Please report on the ease of use for Dry Ice fixation:</b></td>
                     <td><g:textField name="easeOfUseDice15h" value="${tissueRecoveryBmsInstance.easeOfUseDice15h}" size="150" /></td>      
                  </tr></g:if>
                </table>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="edit2" value="Save" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
