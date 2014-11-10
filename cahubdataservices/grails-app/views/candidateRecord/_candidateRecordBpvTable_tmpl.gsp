<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>
<table>
    <thead>
        <tr><th colspan="10">Candidate List</th></tr>
        <tr>
            <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
              <g:if test="${params.controller != 'textSearch'}">
                <th>QT</th>
              </g:if>
            </g:if>
           <g:if test="${params.controller != 'textSearch'}">
            <g:sortableColumn property="candidateId" title="${message(code: 'candidateRecord.candidateId.label', default: 'Candidate ID')}"/>
            <g:sortableColumn property="bss" title="${message(code: 'candidateRecord.BSS.label', default: 'BSS')}"/>
            <g:sortableColumn property="study.name" title="${message(code: 'candidateRecord.Study.label', default: 'Study')}"/>
           </g:if>
          <g:else>
            <th>Candidate ID</th>
            <th>BSS</th>
            <th>Study</th>
          </g:else>
            <th>Case ID</th>
            <th>Subject Screening</th>
            <th>Subject Consent</th>
            <th>Eligible?</th>
            <th>Consented?</th>
              <g:if test="${params.controller != 'textSearch'}">
                 <g:sortableColumn property="dateCreated" class="dateentry" title="${message(code: 'candidateRecord.lastUpdated.label', default: 'Date Created')}"/>
              </g:if>
            <g:else>
              <th>Date Created</th>
            </g:else>
        </tr>
    </thead>
    <tbody>
      <g:if test="${candidateRecordInstanceList}">
        <g:each in="${candidateRecordInstanceList}" status="i" var="candidateRecordInstance">
          
            <g:set var="canModify" value="${session.DM && (!candidateRecordInstance?.caseRecord || candidateRecordInstance?.caseRecord?.caseStatus?.code == 'INIT' || candidateRecordInstance?.caseRecord?.caseStatus?.code == 'DATA' || candidateRecordInstance?.caseRecord?.caseStatus?.code == 'REMED')}" />
          
            <g:if test="${candidateRecordInstance.caseRecord}"><tr class="${(i % 2) == 0 ? 'odd' : 'even'} linkedRow"></g:if>
            <g:else><tr class="${(i % 2) == 0 ? 'odd' : 'even'}"></g:else>
                <g:if test="${(session.org?.code == 'OBBR' && session.DM == true) || session.org?.code != 'OBBR'}">
                  <g:if test="${params.controller != 'textSearch'}">
                    <td><g:if test="${queryCountCandidate.get(candidateRecordInstance.id)}"><a href="/cahubdataservices/query/listByCandidate?candidateRecord.id=${candidateRecordInstance.id}"><span class="no">${queryCountCandidate.get(candidateRecordInstance.id)}</span></a></g:if><g:else><span class="yes">0</span></g:else></td>
                  </g:if>
                </g:if>
                <td class="candidate-id">
                    <g:if test="${candidateRecordInstance.caseRecord || session.org.code == 'OBBR'}">
                      <a href="/cahubdataservices/candidateRecord/view/${candidateRecordInstance.id}">${candidateRecordInstance.candidateId}</a> <g:if test="${candidateRecordInstance?.internalComments?.length() > 0}"><span class="ca-bubble" data-msg="${candidateRecordInstance?.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}"></span></g:if>
                    </g:if>
                    <g:elseif test="${!candidateRecordInstance.caseRecord && session.DM == true}">
                       <a href="/cahubdataservices/candidateRecord/modify/${candidateRecordInstance.id}">${candidateRecordInstance.candidateId}</a> <g:if test="${candidateRecordInstance?.internalComments?.length() > 0}"><span class="ca-bubble" data-msg="${candidateRecordInstance?.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}');"></span></g:if>
                    </g:elseif>
                </td>
                <td><span class="ca-tooltip-nobg" data-msg="<b>${candidateRecordInstance.bss.name}</b>">${candidateRecordInstance.bss}</span></td>
                <td>${fieldValue(bean: candidateRecordInstance, field: "study.name")}</td>
                <td>
                    <g:if test="${candidateRecordInstance.caseRecord}">
                        <g:displayCaseRecordLink caseRecord="${candidateRecordInstance.caseRecord}" session="${session}"/>
                        %{--
                        <g:if test="${candidateRecordInstance.caseRecord.caseStatus.code == 'COMP' || candidateRecordInstance.caseRecord.caseStatus.code == 'RELE' || session.org.code == candidateRecordInstance.caseRecord.bss.code}">
                          <span class="${candidateRecordInstance.caseRecord.caseStatus?.code == 'DATA' && candidateRecordInstance.caseRecord.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date()) ? 'redlink' : ''}">
                            <g:link controller="caseRecord" action="display" id="${candidateRecordInstance.caseRecord.id}">${candidateRecordInstance.caseRecord.caseId}</g:link>
                          </span>
                        </g:if>
                        <g:elseif test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                          session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                                          session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") ||
                                          session.authorities?.contains("ROLE_ADMIN")}'>         
                          <span class="${candidateRecordInstance.caseRecord.caseStatus?.code == 'DATA' && candidateRecordInstance.caseRecord.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date()) ? 'redlink' : ''}">
                            <g:link controller="caseRecord" action="display" id="${candidateRecordInstance.caseRecord.id}">${candidateRecordInstance.caseRecord.caseId}</g:link>
                          </span>
                        </g:elseif>
                        <g:else>
                            ${candidateRecordInstance.caseRecord.caseId}                        
                        </g:else>    
                        --}%
                    </g:if>
                    <g:else>
                        <span class="no">Not Linked</span>
                    </g:else>
                </td>
                <td>
                    <g:if test="${!candidateRecordInstance.bpvScreeningEnrollment}">
                        <span class="no">Not Started</span>
                        <g:if test="${canModify}">
                            <a href="/cahubdataservices/bpvScreeningEnrollment/create?candidateRecord.id=${candidateRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('SCREEN')?.id}">(Start)</a>
                        </g:if>
                    </g:if>
                    <g:elseif test="${!candidateRecordInstance.bpvScreeningEnrollment.dateSubmitted}">
                        <span class="incomplete">In Progress</span>
                        <g:if test="${canModify}">
                            <g:link controller="bpvScreeningEnrollment" action="edit" id="${candidateRecordInstance.bpvScreeningEnrollment.id}">(Edit)</g:link>
                        </g:if>
                        <g:else>
                            <g:link controller="bpvScreeningEnrollment" action="show" id="${candidateRecordInstance.bpvScreeningEnrollment.id}">(View)</g:link>
                        </g:else>
                    </g:elseif>
                    <g:else>
                        <span class="yes">Completed</span>
                        <g:link controller="bpvScreeningEnrollment" action="show" id="${candidateRecordInstance.bpvScreeningEnrollment.id}">(View)</g:link>
                    </g:else>
                </td>
                <td>
                    <g:if test="${!candidateRecordInstance.bpvConsentEnrollment}">
                        <span class="no">Not Started</span>
                        <g:if test="${canModify && candidateRecordInstance?.isEligible}">
                            <a href="/cahubdataservices/bpvConsentEnrollment/create?candidateRecord.id=${candidateRecordInstance.id}&formMetadata.id=${FormMetadata.findByCode('CONSENT')?.id}">(Start)</a>
                        </g:if>
                    </g:if>
                    <g:elseif test="${!candidateRecordInstance.bpvConsentEnrollment.dateSubmitted}">
                        <span class="incomplete">In Progress</span>
                        <g:if test="${canModify}">
                            <g:link controller="bpvConsentEnrollment" action="edit" id="${candidateRecordInstance.bpvConsentEnrollment.id}">(Edit)</g:link>
                        </g:if>
                        <g:else>
                            <g:link controller="bpvConsentEnrollment" action="show" id="${candidateRecordInstance.bpvConsentEnrollment.id}">(View)</g:link>
                        </g:else>
                    </g:elseif>
                    <g:else>
                        <span class="yes">Completed</span>
                        <g:link controller="bpvConsentEnrollment" action="show" id="${candidateRecordInstance.bpvConsentEnrollment.id}">(View)</g:link>
                    </g:else>
                </td>
                <td>
                    <g:if test="${candidateRecordInstance.isEligible}"><span class="yes">Yes</span></g:if>
                    <g:else><span class="no">No</span></g:else>
                </td>
                <td>
                    <g:if test="${candidateRecordInstance.isConsented}"><span class="yes">Yes</span></g:if>
                    <g:else><span class="no">No</span></g:else>
                </td>
                <td><g:formatDate date="${candidateRecordInstance.dateCreated}" /></td>
            </tr>
        </g:each>
      </g:if>
      <g:else>
            <tr><td colspan="10">No candidates exist</td></tr>
      </g:else>
    </tbody>
</table>
