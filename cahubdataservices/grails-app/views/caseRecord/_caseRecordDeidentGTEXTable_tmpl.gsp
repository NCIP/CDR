<div id="ErrorDiv"></div>
<table>
  <thead>
    <tr><th colspan="10">GTEX Case List</th></tr>
    <tr>
      <g:if test="${params.controller != 'textSearch'}">
          <th>QT</th>
      </g:if>
      <g:if test="${session.org?.code == 'BROAD' || (session.org?.code == 'OBBR' && session.DM == true)}"><g:sortableColumn property="dmFastTrack" title="FT" /></g:if>                          
      <g:sortableColumn property="caseId" title="Case ID" />                          
      <g:sortableColumn property="bss" title="BSS" />
      <g:sortableColumn property="caseCollectionType" title="Case Type" />
      <th class="specimencount">Specimens</th>                            
      <g:sortableColumn property="caseStatus" title="Case Status" />                            
      <th>PRC Report</th>                            
      <th>Donor Export</th>
      <g:sortableColumn property="dateCreated" class="dateentry" title="Date Created" />                
    </tr>
  </thead>
  <tbody>
    <g:if test="${caseRecordInstanceList}">
      <g:each in="${caseRecordInstanceList}" status="i" var="caseRecordInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <g:if test="${params.controller != 'textSearch'}">
              <td><g:if test="${queryCount.get(caseRecordInstance.id)}"><a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}"><span class="no">${queryCount.get(caseRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
          </g:if>
          <g:if test="${session.org?.code == 'BROAD' || (session.org?.code == 'OBBR' && session.DM == true)}">
            <td><g:if test ="${caseRecordInstance.caseStatus?.code=='COLL' || caseRecordInstance.caseStatus?.code=='DATA' || caseRecordInstance.caseStatus?.code=='DATACOMP' || caseRecordInstance.caseStatus?.code=='QA' || caseRecordInstance.caseStatus?.code=='PRC' || caseRecordInstance.caseStatus?.code=='BSSQACOMP'}"> 
                 <g:if test="${session.org?.code == 'BROAD' || (session.org?.code == 'OBBR' && session.DM == true)}">
                    <g:remoteLink controller="caseRecord" action="changeCaseFastTrackStatus" update='[success:"imgFT_${caseRecordInstance.id}",failure:"ErrorDiv"]' id="${caseRecordInstance.id}">
                        <div id='imgFT_${caseRecordInstance.id}' onclick="clearErrorDiv()">
                            <g:if test ="${caseRecordInstance.dmFastTrack==0 || caseRecordInstance.dmFastTrack==null}"><img src="/cahubdataservices/images/normalStatus.gif" onmouseover="tooltip.show('Click to add this case into the FastTrack queue');" onmouseout="tooltip.hide();"></g:if>
                            <g:else><img src="/cahubdataservices/images/fastTrack.gif" onmouseover="tooltip.show('Click to remove this case from the FastTrack queue');" onmouseout="tooltip.hide();"></g:else>
                        </div>
                    </g:remoteLink>
                  </g:if><g:else>
                     <g:if test ="${caseRecordInstance.dmFastTrack==0 || caseRecordInstance.dmFastTrack==null}">
                         <img src="/cahubdataservices/images/normalStatus.gif" onmouseover="tooltip.show('Click to add this case into the FastTrack queue');" onmouseout="tooltip.hide();"></g:if>
                     <g:else><img src="/cahubdataservices/images/fastTrack.gif" onmouseover="tooltip.show('Click to remove this case from the FastTrack queue');" onmouseout="tooltip.hide();"></g:else>                            
                  </g:else>
              </g:if>
              <g:else><img src="/cahubdataservices/images/nonMod.gif" onmouseover="tooltip.show('Case passed QA review.');" onmouseout="tooltip.hide();"></g:else></td></g:if>      
            <td class="nowrap"><g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link></td>
            <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.bss.name}</b>">${caseRecordInstance.bss}</span></td>
            <td>${caseRecordInstance.caseCollectionType}</td>
            <td><g:if test="${specimenCount.get(caseRecordInstance.id)}">${specimenCount.get(caseRecordInstance.id)}</g:if><g:else>&nbsp;</g:else></td>                                         
            <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span></td>
            <td><g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate != null && caseRecordInstance.caseStatus.code  != 'WITHDR'}">
                  <span class="yes">Yes</span> <g:link controller="prcReport" action="view" id="${caseRecordInstance.prcReport.id}">(View)</g:link> | <a target="_blank" href="${createLink(uri: '/')}rest/gtexprcreport/${caseRecordInstance.caseId}">(XML)</a>
                </g:if><g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate == null}">
                  <span class="incomplete">In Progress</span>
                </g:if><g:if test="${!caseRecordInstance.prcReport ||  caseRecordInstance.caseStatus.code  == 'WITHDR'}"><span class="no">No</span></g:if></td>
            <td><g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'}">
                  <g:if test="${session.org?.code == 'BROAD'}"><a target="_blank" href="${createLink(uri: '/')}rest/gtexdonorvars/${caseRecordInstance.caseId}">(View)</a></g:if>
                  <g:else><a target="_blank" href="${createLink(uri: '/')}rest/gtexdonorvars/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (View)</a></g:else>
                </g:if><g:else>
                  <g:if test="${session.org?.code == 'BROAD'}"><a target="_blank" href="${createLink(uri: '/')}rest/gtexpartialdonorvars/${caseRecordInstance.caseId}">(Preview)</a></g:if><g:else><span class="no">No</span></g:else>
                </g:else></td>
            <td><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.phase?.code}</b> (${caseRecordInstance.phase?.name})"><g:formatDate date="${caseRecordInstance.dateCreated}" /></span></td>
       </tr>
     </g:each></g:if><g:else><tr><td colspan="10">No cases exist</td></tr></g:else>
     </tbody>
   </table>
