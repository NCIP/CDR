
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.forms.ctc.CtcCrf" %>
<%@ page import="nci.obbr.cahub.staticmembers.*" %>
<%@ page import="nci.obbr.cahub.datarecords.ctc.*" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here edit" scope="request"/>
<g:set var="bodyclass" value="ctccrf edit wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'ctcCrf.label', default: 'CtcCrf')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript>
         
        </g:javascript>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <a href="${AppSetting.findByCode('CDRAR_HOSTNAME').value}/cahubanalytics/ctcForm/crf?id=${ctcCrfInstance.id}" class="list">PDF</a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>CTC Case Report Form </h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${ctcCrfInstance}">
            <div class="errors">
                <g:renderErrors bean="${ctcCrfInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:render template="/caseRecord/caseDetails" bean="${ctcCrfInstance.caseRecord}" var="caseRecord" /> 
            <g:form method="post" >
                <g:hiddenField name="id" value="${ctcCrfInstance?.id}" />
                <g:hiddenField name="version" value="${ctcCrfInstance?.version}" />
                <input type="hidden" name="changed" value="N" id="changed"/>
                <div class="list">
                    <table>
                        <tbody>
                        
                           
                            <tr class="prop">
                              <td valign="top" class="name" style="width:400px" >
                                  <label for="whichVisit"><g:message code="ctcCrf.whichVisit.label" default="Which Visit:" /></label>
                                </td>
                                <td>
                                <g:if test="${ctcCrfInstance?.whichVisit==1}">
                                  First
                                </g:if>
                               <g:elseif test="${ctcCrfInstance?.whichVisit==2}">
                                  Second
                               </g:elseif>
                                <g:elseif test="${ctcCrfInstance?.whichVisit==3}">
                                  Third
                               </g:elseif>
                          <g:elseif test="${ctcCrfInstance?.whichVisit==4}">
                                  Fourth
                               </g:elseif>
                                 <g:elseif test="${ctcCrfInstance?.whichVisit==5}">
                                  Fifth
                               </g:elseif>
                               <g:else>
                                 Sixth
                               </g:else>
                                </td>
                               
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phlebotomySite"><g:message code="ctcCrf.phlebotomySite.label" default="Phlebotomy Site:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'phlebotomySite', 'errors')}">
                                   ${ctcCrfInstance?.phlebotomySite?.name}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="needleType"><g:message code="ctcCrf.needleType.label" default="Needle Type:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'needleType', 'errors')}">
                                  <div>
                                    ${ctcCrfInstance?.needleType}
                                  </div>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="needleGauge"><g:message code="ctcCrf.needleGauge.label" default="Needle Gauge:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'needleGauge', 'errors')}">
                                  <div>
                                    ${ctcCrfInstance?.needleGauge}
                                  </div>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="treatmentStatus"><g:message code="ctcCrf.treatmentStatus.label" default="Treatment Status:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'treatmentStatus', 'errors')}">
                                    <g:if test="${ctcCrfInstance?.treatmentStatus}">
                                    ${ctcCrfInstance?.treatmentStatus}
                                    </g:if>
                                   <g:else>
                                     &nbsp;
                                   </g:else>
                                </td>
                            </tr>
                        
                               <g:if test="${PatientRecord.findByCaseRecord(ctcCrfInstance?.caseRecord).cancerStage=='Early Stage'}">
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="stageT">Stage T:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'stageT', 'errors')}">
                                     ${ctcCrfInstance?.stageT} 
                                </td>
                            </tr>
                            
                              <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="stageN">Stage N:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'stageN', 'errors')}">
                                     ${ctcCrfInstance?.stageN}
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="chemo">Pre-operative Chemotherapy:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'chemo', 'errors')}">
                                     ${ctcCrfInstance?.chemo}
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="regimen">Regimen:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'regimen', 'errors')}">
                                     ${ctcCrfInstance?.regimen}
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="regimenDate">Regimen Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'regimenDate', 'errors')}">
                                     ${ctcCrfInstance?.regimenDate}
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgery">Planned Surgery:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'surgery', 'errors')}">
                                     ${ctcCrfInstance?.surgery}
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgeryOther">Planned Surgery Other:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'surgeryOther', 'errors')}">
                                     ${ctcCrfInstance?.surgeryOther}
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgeryDate">Date of Surgery:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'surgeryDate', 'errors')}">
                                     ${ctcCrfInstance?.surgeryDate}
                                </td>
                            </tr>
                            
                               <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lymph">Lymph Node Evaluation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'lymph', 'errors')}">
                                     ${ctcCrfInstance?.lymph}
                                </td>
                            </tr>
                            
                              <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lymphOther">Lymph Node Evaluation Other:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'lymphOther', 'errors')}">
                                     ${ctcCrfInstance?.lymphOther}
                                </td>
                            </tr>
                          </g:if>
                             <g:if test="${PatientRecord.findByCaseRecord(ctcCrfInstance?.caseRecord).cancerStage=='Late Stage'}">
                               
                                <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="therapy">Current Therapy:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'therapy', 'errors')}">
                                     ${ctcCrfInstance?.therapy}
                                </td>
                            </tr>
                            
                               <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="status">Disease Status:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'status', 'errors')}">
                                     ${ctcCrfInstance?.status}
                                </td>
                            </tr>
                            
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="statusDeterBy">Disease Status Determined by:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'statusDeterBy', 'errors')}">
                                     ${ctcCrfInstance?.statusDeterBy}
                                </td>
                            </tr>
                               
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="statusDeterByOther">Disease Status Determined by Other:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'statusDeterByOther', 'errors')}">
                                     ${ctcCrfInstance?.statusDeterByOther}
                                </td>
                            </tr>
                            
                             </g:if>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateSampleCollectedStr">Date and time when patient had first blood drawn:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSampleCollectedStr', 'errors')}">
                                  
                                    <g:if test="${ctcCrfInstance?.dateSampleCollected}">
                                      <g:formatDate date="${ctcCrfInstance?.dateSampleCollected}" type="datetime" style="MEDIUM" timeStyle="SHORT"/>
                                      </g:if>
                                </td>
                            </tr>
                        
                       
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateSampleShippedStr">Date when samples were shipped to Scripps:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSampleShippedStr', 'errors')}">
                                   <g:formatDate date="${ctcCrfInstance?.dateSampleShipped}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                        
                           
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateSampleReceivedStr">Date when samples were received at Scripps:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSampleReceivedStr', 'errors')}">
                                   <g:formatDate date="${ctcCrfInstance?.dateSampleReceived}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                        
                            
                            
                              <tr class="prop" >
                                  <td valign="top" class="name">
                                    <g:if test="${PatientRecord.findByCaseRecord(ctcCrfInstance?.caseRecord).experiment=='VC'}">
                                  <label for="dateSample24hProcessedStr">Date when sample for the 24 hour time point was processed on to slides:</label>
                                    </g:if>
                              <g:else>
                                   <label for="dateSample24hProcessedStr">Date when samples for the 24 hour time point were processed on to slides:</label>
                              </g:else>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSample24hProcessedStr', 'errors')}">
                                  <g:formatDate date="${ctcCrfInstance?.dateSample24hProcessed}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                            
                              <g:if test="${PatientRecord.findByCaseRecord(ctcCrfInstance?.caseRecord).experiment=='VC'}">
                                
                                  <tr class="prop" >
                                  <td valign="top" class="name">
                                  <label for="dateSample48hProcessedStr">Date when sample for the 48 hour time point was processed on to slides:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSample48hProcessedStr', 'errors')}">
                                  <g:formatDate date="${ctcCrfInstance?.dateSample48hProcessed}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                                
                              </g:if>
                          
                            <tr class="prop" >
                                <td valign="top" class="name">
                                  <label for="dateSample72hProcessedStr">Date when sample for the 72 hour time point was processed on to slides:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSample72hProcessedStr', 'errors')}">
                                   <g:formatDate date="${ctcCrfInstance?.dateSample72hProcessed}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                                    
                          <g:if test="${PatientRecord.findByCaseRecord(ctcCrfInstance?.caseRecord).experiment=='VC'}">
                                
                                  <tr class="prop" >
                                  <td valign="top" class="name">
                                  <label for="dateSample96hProcessedStr">Date when sample for the 96 hour time point was processed on to slides:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSample96hProcessedStr', 'errors')}">
                                  <g:formatDate date="${ctcCrfInstance?.dateSample96hProcessed}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                                
                              </g:if>
                            
                        <g:if test="${PatientRecord.findByCaseRecord(ctcCrfInstance?.caseRecord).experiment=='VC'}">
                          
                          <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateSampleCsProcessedStr">Date when CellSafe tube was measured:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSampleCsProcessedStr', 'errors')}">
                                   <g:formatDate date="${ctcCrfInstance?.dateSampleCsProcessed}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
           
                        </g:if>
                        
                           <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comments"><g:message code="ctcCrf.comments.label" default="Comments:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'comments', 'errors')}">
                                   ${ctcCrfInstance?.comments}
                                </td>
                            </tr>
                           <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateSubmitted"><g:message code="ctcCrf.dateSubmitted.label" default="Date CRF Submitted:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ctcCrfInstance, field: 'dateSubmitted', 'errors')}">
                                   <g:formatDate date="${ctcCrfInstance?.dateSubmitted}" type="date" style="MEDIUM" timeStyle="SHORT"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <br></br>
                <div class='list'>
                  <table>
                    <thead>
                         <tr ><th colspan="12">Sample Sheet</th></tr>
                    <tr>
                      <th>Draw Number</th>
                      <th>Tube Type</th>
                      <th>Bench Time (Hours)</th>
                      <th>CTC Measurement Technology</th>
                      <th>Probes Employed Besides Pan CK, CD45 And DAPI</th>
                      <th>Morphological Criteria Employed </th>
                       <th>Date Sample Stained</th>
                      <th>Date Sample Analysed</th>
                      <th>CTC/ml Reported</th>
                      <th>Date Sample Uploaded to DCC</th>
                      <th>Sample Status</th>
                    </tr>
                    </thead>
                    <tbody>
                      <g:each in="${sample_list.sort{it.id}}" status="i" var="sample">
                        <tr id='row_${sample.id}'>
                            <td nowrap="nowrap" class="value ${errorMap.get('tubeId_'+sample.id)}">${sample.tubeId}</td>
                           <td class='value'>${sample.tubeType?.code}</td>
                           <td class='value'>${sample.benchTime}</td>
                           <td class="value ${errorMap.get('measureTech_'+sample.id)}">${sample.measureTech}</td>
                           
                           <td class="value ${errorMap.get('probe_'+sample.id)}" nowrap="nowap">
                              ${sample.probes}
                           </td>
                           <td class="value ${errorMap.get('criteria_'+sample.id)}" nowrap="nowap">
                              ${sample.criteria}
                           </td> 
                           <td nowrap="nowrap" class="value ${errorMap.get('dateSampleStainedStr_'+sample.id)}"> <g:formatDate date="${sample.dateSampleStained}" type="date" style="MEDIUM"/></td>  
                           <td nowrap="nowrap" class="value ${errorMap.get('dateSampleAnalyzedStr_'+sample.id)}"><g:formatDate date="${sample.dateSampleAnalyzed}" type="date" style="MEDIUM"/></td>  
                           <td nowrap="nowrap" class="value ${errorMap.get('ctcValueStr_'+sample.id)}">${sample.ctcValueStr}</td>
                            <td nowrap="nowrap" class="value ${errorMap.get('dateLoadedDccStr_'+sample.id)}"><g:formatDate date="${sample.dateLoadedDcc}" type="date" style="MEDIUM"/></td>
                             <td nowrap="nowrap" class="value ${errorMap.get('status_'+sample.id)}" nowrap="nowap">${sample.status}</td>
                        </tr>
                      </g:each>
                    </tbody>
                  </table>
                  
                 
                </div>
                <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) &&ctcCrfInstance.dateSubmitted}">
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="resume" value="Resume Editing"/></span>
                </div>
                </g:if>
            </g:form>
        </div>
    </body>
</html>
