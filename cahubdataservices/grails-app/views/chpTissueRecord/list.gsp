
<%@ 
  page import="nci.obbr.cahub.datarecords.ChpTissueRecord"
  page import="nci.obbr.cahub.ChpService"
%>
<g:set var="bodyclass" value="chptissue listcase wide" scope="request"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord')}" />
        <r:require module="export"/>
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <!--
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            -->
        </div>
        <div class="body">
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1>Case Details: Specimen Time points</h1>            
            <div class="dialog">
                     <table style="width:100%;">
                          <tbody>
                          <tr class="prop">
                          <td valign="top" class="name">Case ID:</td>
                          <td valign="top" class="value">
                          <g:link controller="caseRecord" action="display" id="${caseRecord.caseId}">${caseRecord.caseId}</g:link>
                          </td>
                          <td valign="top" class="name">Gross Diagnosis:</td>
                          <td valign="top" class="value"> ${grossDiagnosis}</td>       
                          <td valign="top" class="name">BSS:</td>
                          <td valign="top" class="value">${caseRecord.bss.name}</td>
                          <td valign="top" class="name">Surgery Date: </td>
                          <g:if test="${session.LDS==true}">
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${surgDate}" /></td>
                          </g:if>
                          <g:else>
                            <g:if test="${session.LDS == false || session.LDS == null}">
                              <td valign="top" class="value"><span class="redactedMsg">REDACTED (No LDS privilege)</span></td>
                            </g:if>
                          </g:else>
                          
                      </tr>                    
                     </table>
                    </div>
            <br/>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:if test="${session.LDS==true}"><g:sortableColumn property="id" title="Details" /></g:if>
                            <g:sortableColumn property="specimenId" title="${message(code: 'chpTissueRecord.specimenRecord.specimenId.label', default: 'Specimen Id')}" />
                            <g:sortableColumn property="firstIncis" title="${message(code: 'chpTissueRecord.firstIncis.label', default: 'First Incis')}" />
                            <g:sortableColumn property="clamp1Time" title="${message(code: 'chpTissueRecord.clamp1Time.label', default: 'Clamp1 Time')}" />
                            <g:sortableColumn property="clamp2Time" title="${message(code: 'chpTissueRecord.clamp2Time.label', default: 'Clamp2 Time')}" />
                            <g:sortableColumn property="resectTime" title="${message(code: 'chpTissueRecord.resectTime.label', default: 'Resect Time')}" />
                            <g:sortableColumn property="grossTimeIn" title="${message(code: 'chpTissueRecord.grossTimeIn.label', default: 'Gross Time In')}" />
                            <g:sortableColumn property="tissDissecTime" title="${message(code: 'chpTissueRecord.tissDissecTime.label', default: 'TissBank TimeIn')}" />
                            <g:sortableColumn property="dissecStartTime" title="${message(code: 'chpTissueRecord.dissecStartTime.label', default: 'Dissect StartTime')}" />
                            <g:sortableColumn property="timeInCass" title="${message(code: 'chpTissueRecord.timeInCass.label', default: 'Time in Cassette')}" />
                            <g:sortableColumn property="timeInFix" title="${message(code: 'chpTissueRecord.timeInFix.label', default: 'Time into Fixative')}" />
                            <g:sortableColumn property="protocol" title="${message(code: 'chpTissueRecord.protocol.label', default: 'Protocol')}" />
                            <g:sortableColumn property="protoDelayToFix" title="${message(code: 'chpTissueRecord.protoDelayToFix.label', default: 'Protocol: DTF')}" />
                            <th>Delay to Fixation</th>
                            <g:sortableColumn property="timeInProcessor" title="${message(code: 'chpTissueRecord.timeInProcessor.label', default: 'Time into Processor')}" />
                            <g:sortableColumn property="protoTimeInFix" title="${message(code: 'chpTissueRecord.protoTimeInFix.label', default: 'Protocol: TIF')}" />
                            <th>Time in Fixative</th>
                            <g:sortableColumn property="procTimeEnd" title="${message(code: 'chpTissueRecord.procTimeEnd.label', default: 'Proc Cycle End')}" />
                            <g:sortableColumn property="procTimeRemov" title="${message(code: 'chpTissueRecord.procTimeRemov.label', default: 'Proc Time Removed')}" />
                            <g:sortableColumn property="timeEmbedded" title="${message(code: 'chpTissueRecord.timeEmbedded.label', default: 'Time Embedded')}" />
                        </tr>
                    </thead>
                    <tbody>
                      
                    <g:each in="${chpTissueRecordInstanceList}" status="i" var="chpTissueRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                          <g:if test="${session.LDS==true}"><td><g:link action="show" id="${chpTissueRecordInstance.id}"><img border="0" src="/cahubdataservices/images/magGlass16x16.png" alt="Specimen Handling Details" title="Specimen Handling Details"/></g:link></td></g:if>
                            <td>${chpTissueRecordInstance.specimenRecord.specimenId}</td>
                            <td>
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.firstIncis}" />"></g:if>
                                <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.firstIncis}" /></span>
                            </td>
                            <td>
                              <span class="${chpService.flagOrder(chpTissueRecordInstance.firstIncis?.getTime(), chpTissueRecordInstance.clamp1Time?.getTime())}">
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.clamp1Time}" />"></g:if>
                                <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.clamp1Time}" /></span>
                              </span>
                            </td>
                            <td><g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.clamp2Time}" />"></g:if>
                                <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.clamp2Time}" /></span>
                            </td>
                            <td><g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.resectTime}" />"></g:if>
                                <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.resectTime}" /></span>
                            </td>
                            <td><g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.grossTimeIn}" />"></g:if>
                              <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.grossTimeIn}" /></span>
                            </td>
                            
                            <td><g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.tissDissecTime}" />"></g:if>
                                <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.tissDissecTime}" /></span>
                            </td>
                            <td><g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.dissecStartTime}" />"></g:if>
                                <g:formatDate format="HH:mm" date="${chpTissueRecordInstance.dissecStartTime}" /></span>
                            </td>
                            <td>
                              <span class="${chpService.flagOrder(chpTissueRecordInstance.dissecStartTime?.getTime(), chpTissueRecordInstance.timeInCass?.getTime())}">
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.timeInCass}" />"></g:if>
                              <g:formatDate format="HH:mm:ss" date="${chpTissueRecordInstance.timeInCass}" /><g:if test="${session.LDS==true}"></span></g:if>
                              </span>
                            </td>
                            <td style="white-space:nowrap">
                              <span class="${chpService.flagOrder(chpTissueRecordInstance.timeInCass?.getTime(), chpTissueRecordInstance.timeInFix?.getTime())}">
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.timeInFix}" />"></g:if>
                                    ${ chpService.daysDiff(surgDate, chpTissueRecordInstance.timeInFix ) }
                              <g:formatDate format="HH:mm:ss" date="${chpTissueRecordInstance.timeInFix}" />
                              <g:if test="${session.LDS==true}"></span></g:if>
                              </span>
                            </td>
                            <td style="white-space:nowrap">${chpTissueRecordInstance.protocol}</td>
                            <td style="white-space:nowrap">
                              ${chpTissueRecordInstance.protoDelayToFix}</td>
                            <td style="white-space:nowrap">
                              <span class="${chpService.flagDiff(chpTissueRecordInstance, "DTF", caseRecord.bss)}">
                              <g:if test="${caseRecord.bss.code == 'VUMC'}">
                              <span onmouseover="tooltip.show('VUMC: Time Into Fixative - TissBank Time In');" onmouseout="tooltip.hide();">${chpService.timeDiff(chpTissueRecordInstance, "DTF", caseRecord.bss)}</span>
                              </g:if>
                              <g:if test="${caseRecord.bss.code == 'UNM'}">
                              <span onmouseover="tooltip.show('UNM: Time Into Fixative - Resect Time');" onmouseout="tooltip.hide();">${chpService.timeDiff(chpTissueRecordInstance, "DTF", caseRecord.bss)}</span>
                              </g:if>
                              </span>
                            </td>
                            <td>
                              <span class="${chpService.flagOrder(chpTissueRecordInstance.timeInFix?.getTime(), chpTissueRecordInstance.timeInProcessor?.getTime())}">
                                <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.timeInProcessor}" />"></g:if>
                                ${ chpService.daysDiff(surgDate, chpTissueRecordInstance.timeInProcessor ) }
                                <g:formatDate format="HH:mm:ss" date="${chpTissueRecordInstance.timeInProcessor}" />
                                <g:if test="${session.LDS==true}"></span></g:if>
                              </span>
                            </td>
                            <td style="white-space:nowrap">${chpTissueRecordInstance.protoTimeInFix}</td>
                            <td style="white-space:nowrap">
                              <span class="${chpService.flagDiff(chpTissueRecordInstance, "TIF", caseRecord.bss)}">
                              <span onmouseover="tooltip.show('(ProcCycleEnd-7h) - Time Into Fixative');" onmouseout="tooltip.hide();">${chpService.timeDiff(chpTissueRecordInstance, "TIF", caseRecord.bss)}</span>
                              </span>
                            </td>
                            
                            <td style="white-space:nowrap">
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.procTimeEnd}" />"></g:if>
                                    ${ chpService.daysDiff(surgDate, chpTissueRecordInstance.procTimeEnd ) }
                              <g:formatDate format="HH:mm:ss" date="${chpTissueRecordInstance.procTimeEnd}" /><g:if test="${session.LDS==true}"></span></g:if>
                            </td>
                            <td style="white-space:nowrap">
                              <span class="${chpService.flagDiff(chpTissueRecordInstance, "TimeRemov", caseRecord.bss)}">
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.procTimeRemov}" />"></g:if>
                                    ${ chpService.daysDiff(surgDate, chpTissueRecordInstance.procTimeRemov ) }
                              <g:formatDate format="HH:mm:ss" date="${chpTissueRecordInstance.procTimeRemov}" /><g:if test="${session.LDS==true}"></span></g:if>
                              </span>
                            </td>
                            <td style="white-space:nowrap">
                              <span class="${chpService.flagOrder(chpTissueRecordInstance.procTimeRemov?.getTime(), chpTissueRecordInstance.timeEmbedded?.getTime())}">
                              <g:if test="${session.LDS==true}"><span class="ca-tooltip-nobg" data-msg="<g:formatDate format="yyyy-MM-dd" date="${chpTissueRecordInstance.timeEmbedded}" />"></g:if>
                                    ${ chpService.daysDiff(surgDate, chpTissueRecordInstance.timeEmbedded ) }
                              <g:formatDate format="HH:mm:ss" date="${chpTissueRecordInstance.timeEmbedded}" /><g:if test="${session.LDS==true}"></span></g:if>
                              </span>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${chpTissueRecordInstanceTotal}" />
                <g:if test="${session.LDS==true}"><export:formats formats="['csv', 'excel', 'ods', 'pdf', 'rtf', 'xml']" params="[id: params?.id]"/></g:if>
            </div>
        </div>
    </body>
</html>
