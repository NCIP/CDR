<div id="ErrorDiv"></div>
<table>
  <thead>
    <tr><th colspan="12">Interview List</th></tr>
      <th>Interview ID</th>
      <th>Org</th>
      <th>Consent</th>
      <th>Survey Type</th>
      <th>Survey</th>
      <th>CRF</th>
      <th>Interview Status</th>
      <th>Date Created</th>
      
    </tr>
  </thead><tbody>
    <g:if test="${interviewRecordInstanceList}">
       <g:each in="${interviewRecordInstanceList}" status="i" var="ir">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
      

      <td><a href="/cahubdataservices/interviewRecord/show/${ir.id}">${ir.interviewId}</a> <g:if test="${ir?.comments?.length() > 0}"><span class="ca-bubble" data-msg="${ir?.comments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}"></span></g:if></td>
      <td>${ir.orgCode}</td>
      <td>${ir.studyConsent}</td>
        <g:if test="${ir.surveys}">
            <g:each in="${ir.surveys}" var="sr">
              <td>
                  ${sr.surveyTemplate.surveyCode}
              </td>              
              <td>              

               <g:bpvelsisurveylink sr="${sr}"/>
              
              </td>
            </g:each> 
        </g:if>
      <g:else>
        <td>
        N/A
        </td>
        <td>
        Not Administered
        </td>        
      </g:else>
      <td>
        
        <g:bpvelsicrflink crf="${ir.bpvElsiCrf}" ir="${ir}"/>
      
            
      </td>
      <td>${ir.interviewStatus} <g:bpvelsistatuslink ir="${ir}"/></td>
      <td><g:formatDate date="${ir.dateCreated}" /></td>      
    
    </tr>
    
     </g:each>
   </g:if><g:else><tr><td colspan="10">No interview records exist</td></tr></g:else>
  </tbody>
</table>

