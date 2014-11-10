
<%@ page import="nci.obbr.cahub.datarecords.ChpBloodRecord" %>
<g:set var="bodyclass" value="chpblood listcase wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Case Details: Blood Time points</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
<!-- Loop through all the records to get Case level details -- in reality, just the first recordInstance would do -->                        
 <g:each in="${chpBloodRecordInstanceList}" status="i" var="chpBloodRecordInstance">
   <g:set var="caseId" value ="${chpBloodRecordInstance.specimenRecord.caseRecord.caseId}" scope="page" />
   <g:set var="caseCollectionType" value ="${chpBloodRecordInstance.specimenRecord.caseRecord.caseCollectionType}" scope="page" />
   <g:set var="bssName" value ="${chpBloodRecordInstance.specimenRecord.caseRecord.bss.name}" scope="page" />
   <g:set var="surgDate" value ="${chpBloodRecordInstance.surgDate}" scope="page" />
 </g:each>
 
            
            <div class="dialog">
                     <table style="width:100%;">
                          <tbody>
                          <tr class="prop">
                          <td valign="top" class="name">Case ID:</td>
                          <td valign="top" class="value">
                          <g:link controller="caseRecord" action="display" id="${caseId}">${caseId}</g:link>
                          </td>
                          <td valign="top" class="name">Gross Diagnosis:</td>
                          <td valign="top" class="value"> ${grossDiagnosis}</td>       
                          <td valign="top" class="name">BSS:</td>
                          <td valign="top" class="value">${bssName}</td>
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
                        
                            <g:sortableColumn property="specimenRecord" title="${message(code: 'chpBloodRecord.specimenRecord.label', default: 'Specimen Id')}" />
                            
                            <!-- g:sortableColumn property="parentSpecimen" title="${message(code: 'chpBloodRecord.specimenRecord.parentSpecimen.label', default: 'Parent Specimen')}" / -->
                            
                            <g:sortableColumn property="dateTimeBloodDraw" title="${message(code: 'chpBloodRecord.dateTimeBloodDraw.label', default: 'Blood Draw')}" />
                            
                            <g:sortableColumn property="volume" title="${message(code: 'chpBloodRecord.volume.label', default: 'Volume')}" />
                            
                            <g:sortableColumn property="containerType" title="${message(code: 'chpBloodRecord.containerType.label', default: 'Container Type')}" />
                            
                            <!-- g:sortableColumn property="fixative" title="${message(code: 'chpBloodRecord.fixative.label', default: 'Fixative')}" / -->
                            
                            <g:sortableColumn property="acquisType" title="${message(code: 'chpBloodRecord.acquisType.label', default: 'Acquis Type')}" />
                            
                            <g:sortableColumn property="dateTimeBloodReceived" title="${message(code: 'chpBloodRecord.dateTimeBloodReceived.label', default: 'Blood Received')}" />
                            
                            <g:sortableColumn property="bloodProcStart" title="${message(code: 'chpBloodRecord.bloodProcStart.label', default: 'Blood Proc Start')}" />
                            
                            <g:sortableColumn property="bloodProcEnd" title="${message(code: 'chpBloodRecord.bloodProcEnd.label', default: 'Blood Proc End')}" />
                            
                            <g:sortableColumn property="hemolysis" title="${message(code: 'chpBloodRecord.hemolysis.label', default: 'Hemolysis')}" />
                        
                            <g:sortableColumn property="bloodProcComment" title="${message(code: 'chpBloodRecord.bloodProcComment.label', default: 'Blood Proc Comment')}" />
                        
                            <g:sortableColumn property="bloodFrozen" title="${message(code: 'chpBloodRecord.bloodFrozen.label', default: 'Blood Frozen')}" />
                        
                            <g:sortableColumn property="bloodStorage" title="${message(code: 'chpBloodRecord.bloodStorage.label', default: 'Blood Storage')}" />
                        
                            <g:sortableColumn property="bloodStorageComment" title="${message(code: 'chpBloodRecord.bloodStorageComment.label', default: 'Blood Storage Comment')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${chpBloodRecordInstanceList}" status="i" var="chpBloodRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <g:if test="${session.LDS==true}"><td><g:link action="show" id="${chpBloodRecordInstance.id}"><img border="0" src="/cahubdataservices/images/magGlass16x16.png" alt="Specimen Handling Details" title="Specimen Handling Details"/></g:link></td></g:if>
                            
                            <td style="white-space:nowrap"><img src="/cahubdataservices/images/pixel.gif" width="${(chpBloodRecordInstance.generation-1) * 10}" height="1"/>
                                ${chpBloodRecordInstance.specimenRecord}</td>
                            
                            <!-- td><g:link controller="specimenRecord" action="show" id="${chpBloodRecordInstance.specimenRecord.parentSpecimen?.id}">${chpBloodRecordInstance.specimenRecord.parentSpecimen}</g:link></td -->
                        
                            <td style="white-space:nowrap">
                              <g:if test="${session.LDS==true}">
                                <g:formatDate format ="yyyy-MMM-dd HH:mm" date="${chpBloodRecordInstance.dateTimeBloodDraw}" /></g:if>
                              <g:else>
                                <g:formatDate format ="HH:mm" date="${chpBloodRecordInstance.dateTimeBloodDraw}" />
                              </g:else>
                            </td>
                            
                            <td>${chpBloodRecordInstance.volume}&nbsp;${chpBloodRecordInstance.volUnits}</td>
                            
                            
                            
                            <td>${chpBloodRecordInstance.containerType}</td>
                            
                            <!-- td><g:link controller ="fixative" action="show" id="${chpBloodRecordInstance.fixative.id}">${chpBloodRecordInstance.fixative}</g:link></td -->
                    
                            <td>${chpBloodRecordInstance.acquisType}</td>
                            
                            <td><g:formatDate format ="HH:mm" date="${chpBloodRecordInstance.dateTimeBloodReceived}" /></td>
                            
                            <td><g:formatDate format ="HH:mm" date="${chpBloodRecordInstance.bloodProcStart}" /></td>
                            
                            <td><g:formatDate format ="HH:mm" date="${chpBloodRecordInstance.bloodProcEnd}" /></td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "hemolysis")}</td>
                        
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "bloodProcComment")}</td>
                        
                            <td><g:formatDate format ="HH:mm" date="${chpBloodRecordInstance.bloodFrozen}" /></td>
                        
                            <td><g:formatDate format ="HH:mm" date="${chpBloodRecordInstance.bloodStorage}" /></td>
                            
                            <td>${fieldValue(bean: chpBloodRecordInstance, field: "bloodStorageComment")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${chpBloodRecordInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
