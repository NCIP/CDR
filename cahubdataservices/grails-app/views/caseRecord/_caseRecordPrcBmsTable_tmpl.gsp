  <table>
    <thead>
      <tr><th colspan="10">BMS Case List</th></tr>
      <tr>
          <th>Case ID</th> 
          <th>GTEX Case ID</th>
          <th>Case Status</th>  
          <th>Gender</th>
          <th>Age</th>
          <th>Cause of Death</th>
          <th class="specimencount">Specimens</th>
          <th>Issue Count Total</th>
          <th>PRC Case Status</th>
          <th>PRC Report</th>
      </tr>
    </thead><tbody>
       <g:if test="${caseListBms}">
          <g:each in="${caseListBms}" status="i" var="c">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                 <td><g:link controller="caseRecord" action="display" id="${c.id}">${c.caseId}</g:link></td>
                 <td><g:link controller="caseRecord" action="display" id="${c.gId}">${c.gCaseId}</g:link></td>
                 <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${c.status}</b>. ${c.statusdesc}">${c.status}</span></td>
                 <td>${c.gender}</td>
                 <td>${c.age}</td>
                 <td>${c.causeOfDeath}</td>
                 <td>${c.specimenCount}</td>
                 <td>${c.issueTotal}</td>
                 <td>${c.unresolvedCount}</td>
            <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC'))}">
               <g:if test ="${c.prcReport!= null && c.prcReport.status=='Editing'}">
                 <td><g:link controller="prcReport" action="editBms" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report"/></g:link>&nbsp;<g:link controller="prcReport" action="viewBms" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
               </g:if><g:if test ="${c.prcReport!= null && c.prcReport.status!='Editing'}">
                 <td><g:link controller="prcReport" action="viewBms" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
               </g:if><g:if test="${c.prcReport== null}">
                 <td><a href="/cahubdataservices/prcReport/saveBms?caseRecord.id=${c.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add2.png" title="Create PRC report" /></a></td>
               </g:if>
            </g:if><g:else>
               <g:if test="${c.prcReport}">
                 <td><g:link controller="prcReport" action="viewBms" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
               </g:if><g:else><td>&nbsp;</td></g:else></g:else>
              </tr>
          </g:each>
       </g:if>
    </tbody>
  </table>

