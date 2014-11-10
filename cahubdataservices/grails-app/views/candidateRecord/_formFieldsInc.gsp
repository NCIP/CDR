<table>
  <tbody>
    <tr class="prop">
      <td valign="top" class="name" style="width:250px;">
        <label for="candidateId">Candidate ID:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'candidateId', 'errors')}">
${candidateRecordInstance?.candidateId}
      </td>
    </tr>                          

    <tr class="prop">
      <td valign="top" class="name">
        <label for="bss">BSS:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'bss', 'errors')}">
${candidateRecordInstance?.bss.name} (${candidateRecordInstance?.bss?.code})
<%--
<g:select name="bss.id" from="${bssSubList}" optionValue="name" optionKey="id" value="${candidateRecordInstance?.bss?.id}"  />
--%>
      </td>
    </tr>                        
    <tr class="prop">
      <td valign="top" class="name">
        <label for="caseCollectionType">Case Collection Type:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'caseCollectionType', 'errors')}">
  <g:select name="caseCollectionType.id" from="${filteredCaseCollectionTypeList}" optionKey="id" value="${candidateRecordInstance?.caseCollectionType?.id}"  />
</td>
</tr>                        
<tr class="prop">
  <td valign="top" class="name">
    <label for="candidateId">Date Created:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'dateCreated', 'errors')}">
<g:formatDate date="${candidateRecordInstance?.dateCreated}" />
</td>
</tr>                           
<tr class="prop">
  <td valign="top" class="name">
    <label for="caseRecord">Linked Case ID:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'caseRecord', 'errors')}">
<g:if test="${!candidateRecordInstance?.caseRecord}"><span class="no">Not Linked</span>

  <g:if test="${candidateRecordInstance.isConsented == true && candidateRecordInstance.donorEligibilityGtex && session.DM == true}">
    <g:link controller="candidateRecord" action="linkCandidateToCase" id="${candidateRecordInstance.id}">(Link)</g:link></span>
  </g:if> 

</g:if>
<g:else>

  %{-- <g:link controller="caseRecord" action="display" id="${candidateRecordInstance?.caseRecord?.id}">${candidateRecordInstance?.caseRecord?.caseId}</g:link> --}%
  <g:displayCaseRecordLink caseRecord="${candidateRecordInstance?.caseRecord}" session="${session}"/>
  <g:if test="${!candidateRecordInstance.caseRecord?.tissueRecoveryGtex && !candidateRecordInstance.caseRecord?.caseReportForm}">
    <a href="/cahubdataservices/candidateRecord/linkCandidateToCase/${candidateRecordInstance.id}">(Unlink)</a>
  </g:if>     


</g:else>
</td>
</tr>
<tr class="prop">
  <td valign="top" class="name">
    Consent Verification Form (ICD):
  </td>
<g:if test="${candidateRecordInstance.bss.parentBss.code == 'NDRI'}">
  <td>
  <g:if test="${candidateRecordInstance.icdGtexNdri}"><span class="yes">Yes</span>
    <g:if test="${session.DM == true}">
      <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP'  || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
        <g:link controller="icdGtexNdri" action="edit" id="${candidateRecordInstance.icdGtexNdri.id}">(Edit)</g:link>
      </g:if>
      <g:else>
        <g:link controller="icdGtexNdri" action="view" id="${candidateRecordInstance.icdGtexNdri.id}">(View)</g:link>                                    
      </g:else>           
    </g:if>
    <g:else>
      <g:link controller="icdGtexNdri" action="view" id="${candidateRecordInstance.icdGtexNdri.id}">(View)</g:link>                                    
    </g:else>   
  </g:if>                                  
  <g:else><span class="no">No 
      <g:if test="${session.DM == true}">
        <a href="/cahubdataservices/icdGtexNdri/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a>
      </g:if>
    </span></g:else>
  </td>               
</g:if>                                                        

<g:if test="${candidateRecordInstance.bss.parentBss.code == 'SC'}">
  <td>
  <g:if test="${candidateRecordInstance.icdGtexSc}"><span class="yes">Yes</span>
    <g:if test="${session.DM == true}">          
      <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP'  || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
        <g:link controller="icdGtexSc" action="edit" id="${candidateRecordInstance.icdGtexSc.id}">(Edit)</g:link>
      </g:if>
      <g:else>
        <g:link controller="icdGtexSc" action="view" id="${candidateRecordInstance.icdGtexSc.id}">(View)</g:link>                                    
      </g:else>
    </g:if>
    <g:else>
      <g:link controller="icdGtexSc" action="view" id="${candidateRecordInstance.icdGtexSc.id}">(View)</g:link>                                    
    </g:else>          
  </g:if>
  <g:else><span class="no">No 
      <g:if test="${session.DM == true}">
        <a href="/cahubdataservices/icdGtexSc/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a>
      </g:if>
    </span></g:else>
  </td>               
</g:if>                            


<g:if test="${candidateRecordInstance.bss.parentBss.code == 'RPCI'}">
  <td>
  <g:if test="${candidateRecordInstance.icdGtexRpci}"><span class="yes">Yes</span>
    <g:if test="${session.DM == true}">     
      <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP'  || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
        <g:link controller="icdGtexRpci" action="edit" id="${candidateRecordInstance.icdGtexRpci.id}">(Edit)</g:link>
        <g:if test="${candidateRecordInstance.caseRecord && !isCaseCurrentCDRVer}">
          <!--link for v1 data correction-->
          <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}" params="[v1: 'true']"><i>(View original 1.x version)</i></g:link>
        </g:if>
      </g:if>
      <g:else>
        <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}">(View)</g:link>                                    
        <g:if test="${candidateRecordInstance.caseRecord && !isCaseCurrentCDRVer}">
          <!--link for v1 data correction-->
          <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}" params="[v1: 'true']"><i>(View original 1.x version)</i></g:link>
        </g:if>                        
      </g:else>              
    </g:if>
    <g:else>
      <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}">(View)</g:link>                                    
      <g:if test="${candidateRecordInstance.caseRecord && !isCaseCurrentCDRVer}">
        <!--link for v1 data correction-->
        <g:link controller="icdGtexRpci" action="view" id="${candidateRecordInstance.icdGtexRpci.id}" params="[v1: 'true']"><i>(View original 1.x version)</i></g:link>
      </g:if>             
    </g:else>           
  </g:if>
  <g:else><span class="no">No 
      <g:if test="${session.DM == true}">
        <a href="/cahubdataservices/icdGtexRpci/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a>
      </g:if>
    </span></g:else>
  </td>               
</g:if>  
</tr>
<tr class="prop">
  <td valign="top" class="name">
    Donor Eligibility Form:
  </td>
  <td>
<g:if test="${candidateRecordInstance.donorEligibilityGtex}"><span class="yes">Yes</span> 
  <g:if test="${session.DM == true}">     
    <g:if test="${candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'REMED' || candidateRecordInstance.caseRecord?.caseStatus?.code == 'DATACOMP'  || !candidateRecordInstance.caseRecord?.caseStatus?.code}">
      <g:link controller="donorEligibilityGtex" action="edit" id="${candidateRecordInstance.donorEligibilityGtex.id}">(Edit)</g:link>
    </g:if>
    <g:else>
      <g:link controller="donorEligibilityGtex" action="view" id="${candidateRecordInstance.donorEligibilityGtex.id}">(View)</g:link>                                    
    </g:else>            
  </g:if>  
  <g:else>
    <g:link controller="donorEligibilityGtex" action="view" id="${candidateRecordInstance.donorEligibilityGtex.id}">(View)</g:link>                                    
  </g:else>          
</g:if>                                                                    
<g:else><span class="no">No 
    <g:if test="${session.DM == true}">
      <a href="/cahubdataservices/donorEligibilityGtex/create?candidateRecord.id=${candidateRecordInstance.id}">(Add)</a>
    </g:if>
  </span>
</g:else>
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="internalComments">Comments:*</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'internalComments', 'errors')}">
<g:textArea name="internalComments" cols="40" rows="5" value="${candidateRecordInstance?.internalComments}" /><br />
<span class="no-phi-note">*No PHI allowed in this field</span>
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="study">Study:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'study', 'errors')}">
${candidateRecordInstance?.study.code}
  </td>
</tr>

<g:if test="${session.DM == true}">
  <tr class="prop">
    <td valign="top" class="name">Query Tracker:</td>
    <td valign="top" class="value">
      <a href="/cahubdataservices/query/listByCandidate?candidateRecord.id=${candidateRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
    </td>
  </tr>
</g:if>

<g:if test="${session.org?.code == 'OBBR'}">
  <tr class="prop">
    <td valign="top" class="name">
      <label for="study">CDR Version When Created:</label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'cdrVer', 'errors')}">
${candidateRecordInstance?.cdrVer}
    </td>
  </tr>
</g:if>
</tbody>
</table>
