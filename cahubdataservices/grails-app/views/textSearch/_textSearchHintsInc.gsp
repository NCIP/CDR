<div class="message">    
      <h1>Hints</h1>
      <h3>All Field Search</h3>
        <ul>
                <li>Search by Case ID <i>(GTEX-000200)</i></li>
                 <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_VARI') && !session.authorities?.contains('ROLE_ORG_MBB') }">
                <li>Search by a <i>linked</i> Candidate ID <i> (SC-A5YF7B79-C)</i> </li>
                 </g:if>
                 <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_MBB') }">
                <li>Search by BSS <i>(NDRI)</i></li>
                 </g:if>
                <li>Search by Case Collection Type <i>(Postmortem)</i></li>
                <li>Search by Case Status <i>(Collected)</i></li>
                <li>Search by Study Phase <i>(BP, PP, IP, SU1)</i></li>
                <li>Search by Specimen ID <i>(GTEX-000200-0001)</i></li>
                 <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_MBB') }">
                <li>Search by Slide ID <i>(2230939)</i></li>
                 </g:if>
                <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_MBB') }">
                <li>Search by Upload File Type <i>(TOX)</i></li>
                </g:if>
                <li>Wild card Search<i>(g*200)</i></li>
                <li>Search by multiple key words
                  <ul>
                    <li>AND <i>(Postmortem Released)</i></li>
                    <li>OR <i>(Postmortem OR Released)</i> </li>
                  </ul>
                </li>
               
        </ul>
      <br>
      <h3>Specific Field Search</h3>
      <ul>
        <li>Syntax: [field]:[value]<i>(caseId:GTEX-000200)</i></li>
        <li>Available fields
          <ul>
            <li>CaseRecord: caseId, caseDateCreated<g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')}">, prcSignOff(BPV only)</g:if> </li>
            <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_VARI') }">  
          <li>CandidateRecord:candidateId, candidateDateCreated </li>
            </g:if>
             <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_MBB')}">
            <li>BSS: BSSName, BSSCode </li>
             </g:if>
            <li>CaseStatus: statusName, statusCode, statusDescription</li>
            <li>CaseCollectionType: collectionTypeName, collectionTypeCode </li>
            <li>SpecimenRecord: specimenId, specimenComments </li>
             <g:if test="${!session.authorities?.contains('ROLE_ORG_MBB') }">
            <li>SlideRecord: slideId </li>
             </g:if>
            <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_VARI') && !session.authorities?.contains('ROLE_ORG_MBB')}">
            <li>Demographics (GTEX/BMS only): age, gender, height, weight, BMI, race, ethnicity, otherGender</li>
            </g:if>
             <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') || session.authorities?.contains('ROLE_ORG_BROAD') || session.authorities?.contains('ROLE_ORG_VARI')}">
            <li>PrcSpecimen(GTEX/BMS only): prcComments</li>
             </g:if>
            <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_MBB') }">
            <li>AcquisitionType(BPV only): tissueName, tissueCode</li>
            </g:if>
             <li>StudyPhase: phaseName, phaseCode</li>
             <g:if test="${!session.authorities?.contains('ROLE_ORG_MBB') }">
             <li>CaseAttachmentType: fileTypeName, fileTypeCode</li>
             </g:if>
               <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')}">
              <li>PatientRecord(CTC only):experiment,visitCount,cancerStage,patientDateCreated</li>
               </g:if>
          </ul>
         
        </li>
        <li>Date range search <i>(caseDateCreated:[2011-09-01 TO 2011-10-01])</i> </li>
         <g:if test="${!session.authorities?.contains('ROLE_ORG_BROAD') && !session.authorities?.contains('ROLE_ORG_VARI') && !session.authorities?.contains('ROLE_ORG_MBB') }">
        <li>Number range search <i>(BMI:[30 TO *])</i> </li>
          </g:if>
      </ul>
</div>
