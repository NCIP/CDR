<!-- common dialog box div holder -->
<%@page defaultCodec="none" %>
<div id="vocDefDialog1" ></div>

<div class="dialog4">
  <h3>Section D.  Death Circumstances</h3>
  <div class="list">
    <table>
      <tbody>
        <tr class="prop">
          <td style="width:300px;" class="name"><g:message code="deathCircumstances.deathCertificateAvailable.label" default="Death Certificate Available" /><span id="deathcircumstances.deathcertificateavailable" class="vocab-tooltip"></span></td>
      <td class="value">${deathCircumstancesInstance?.deathCertificateAvailable?.encodeAsHTML()}</td>  
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.dateTimePronouncedDead.label" default="Date Time Pronounced Dead" /><span id="deathcircumstances.datetimepronounceddead" class="vocab-tooltip"></span></td>
      <td class="value">
      <g:if test="${session.LDS == true}"><g:formatDate date="${deathCircumstancesInstance?.dateTimePronouncedDead}" /></g:if>
      <g:if test="${session.LDS == false || session.LDS == null}"><span class="redactedMsg">REDACTED (No LDS privilege)</span></g:if>            
      </td>
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.dateTimeActualDeath.label" default="Date Time Actual Death" /><span id="deathcircumstances.datetimeactualdeath" class="vocab-tooltip"></span></td>
      <td class="value">
      <g:if test="${session.LDS == true}"><g:formatDate date="${deathCircumstancesInstance?.dateTimeActualDeath}" /></g:if>
      <g:if test="${session.LDS == false || session.LDS == null}"><span class="redactedMsg">REDACTED (No LDS privilege)</span></g:if>            
      </td>
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.dateTimePresumedDeath.label" default="Date Time Presumed Death" /><span id="deathcircumstances.datetimepresumeddeath" class="vocab-tooltip"></span></td>
      <td class="value">
      <g:if test="${session.LDS == true}"><g:formatDate date="${deathCircumstancesInstance?.dateTimePresumedDeath}" /></g:if>
      <g:if test="${session.LDS == false || session.LDS == null}"><span class="redactedMsg">REDACTED (No LDS privilege)</span></g:if>            
      </td>    
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.dateTimeLastSeenAlive.label" default="Date Time Last Seen Alive" /><span id="deathcircumstances.datetimelastseenalive" class="vocab-tooltip"></span></td>
      <td class="value">
      <g:if test="${session.LDS == true}"><g:formatDate date="${deathCircumstancesInstance?.dateTimeLastSeenAlive}" /></g:if>
      <g:if test="${session.LDS == false || session.LDS == null}"><span class="redactedMsg">REDACTED (No LDS privilege)</span></g:if>            
      </td>  
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.placeOfDeath.label" default="Place Of Death" /><span id="deathcircumstances.placeofdeath" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "placeOfDeath")}</td>
      </tr>
      <g:if test="${deathCircumstancesInstance.otherPlaceOfDeath}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.otherPlaceOfDeath.label" default="Other Place Of Death" /><span id="deathcircumstances.otherplaceofdeath" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "otherPlaceOfDeath")}</td>
        </tr>
      </g:if>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.personDeterminedDeath.label" default="Person Determined Death" /><span id="deathcircumstances.persondetermineddeath" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "personDeterminedDeath")}</td>
      </tr>
      <g:if  test="${deathCircumstancesInstance.otherPersonDeterminedDeath}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.otherPersonDeterminedDeath.label" default="Other Person Determined Death" /><span id="deathcircumstances.otherpersondetermineddeath" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "otherPersonDeterminedDeath")}</td>                          </tr>
      </g:if>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.mannerOfDeath.label" default="Manner Of Death" /><span id="deathcircumstances.mannerofdeath" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "mannerOfDeath")}</td>
      </tr>
      <tr class="prop">
        <td class="name">
      <g:message code="deathCircumstances.hardyScale.label" default="Death Classification: 4-point Hardy Scale" /><span id="deathcircumstances.hardyscale" class="vocab-tooltip"></span>                         
      </td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "hardyScale")}</td> 
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.autopsyPerformed.label" default="Autopsy Performed" /><span id="deathcircumstances.autopsyperformed" class="vocab-tooltip"></span></td>
      <td class="value">${deathCircumstancesInstance?.autopsyPerformed?.encodeAsHTML()}</td>     
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.onVentilator.label" default="On Ventilator" /><span id="deathcircumstances.onventilator" class="vocab-tooltip"></span></td>
      <td class="value">${deathCircumstancesInstance?.onVentilator?.encodeAsHTML()}</td>                
      </tr>
      <g:if test="${deathCircumstancesInstance.ventilatorDuration}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.ventilatorDuration.label" default="Ventilator Duration" /> (hours)<span id="deathcircumstances.ventilatorduration" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "ventilatorDuration")}</td>
        </tr>
      </g:if>

      <!--new Question for 4.5-->
      <g:if test="${show45VersionUpdates}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.deathCertificateCause.label" default="Death Certificate Cause" /><span id="deathcircumstances.deathcertificatecause" class="vocab-tooltip"></span></td>
        <g:if test="${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabUserSelection}">
          <td class="value">
            <div class="vocDefDC" data-title="Death Circumstance" data-vocid="${deathCircumstancesInstance.deathCertCauseVocab?.id}" title="${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabUserSelection}" >
              <span class="asLink">${deathCircumstancesInstance?.deathCertCauseVocab?.cVocabUserSelection}</span>
            </div>
          </td>
        </g:if>
        <g:elseif test="${deathCircumstancesInstance?.deathCertificateCause}">
          <td>${deathCircumstancesInstance?.deathCertificateCause}</td>
        </g:elseif>
        <g:else><td>&nbsp;</td></g:else>
        </tr>
      </g:if>
      <!-- end new question for 4.5-->
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.immediateCause.label" default="Immediate Cause" /><span id="deathcircumstances.immediatecause" class="vocab-tooltip"></span></td>
      <g:if test="${show45VersionUpdates}">

        <td class="value">
          <div class="vocDefDC" data-title="Death Circumstance" data-vocid="${deathCircumstancesInstance.immCodCvocab?.id}" title="${deathCircumstancesInstance?.immCodCvocab?.cVocabUserSelection}" >
            <span class="asLink">${deathCircumstancesInstance?.immCodCvocab?.cVocabUserSelection}</span>
          </div>

        </td>


      </g:if>
      <g:else>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "immediateCause")}</td> 

      </g:else>
      </tr>
      <g:if test="${deathCircumstancesInstance.otherImmediate}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.otherImmediate.label" default="Other Immediate" /><span id="deathcircumstances.otherimmediate" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "otherImmediate")}</td>
        </tr>
      </g:if>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.immediateInterval.label" default="Immediate Interval" />(hours)<span id="deathcircumstances.immediateinterval" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "immediateInterval")}</td>                     
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.firstCause.label" default="First Cause" /><span id="deathcircumstances.firstcause" class="vocab-tooltip"></span></td>
      <g:if test="${show45VersionUpdates}">

        <td class="value">
          <div class="vocDefDC" data-title="Death Circumstance" data-vocid="${deathCircumstancesInstance.firstCodCvocab?.id}" title="${deathCircumstancesInstance?.firstCodCvocab?.cVocabUserSelection}" >
            <span class="asLink">${deathCircumstancesInstance?.firstCodCvocab?.cVocabUserSelection}</span>
          </div>

        </td>
      </g:if>
      <g:else>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "firstCause")}</td>

      </g:else>
      </tr>
      <g:if test="${deathCircumstancesInstance.otherFirstCause}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.otherFirstCause.label" default="Other First Cause" /><span id="deathcircumstances.otherfirstcause" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "otherFirstCause")}</td>
        </tr>
      </g:if>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.firstCauseInterval.label" default="First Cause Interval" />(hours)<span id="deathcircumstances.firstcauseinterval" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "firstCauseInterval")}</td>     
      </tr>

      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.lastCause.label" default="Last Cause" /><span id="deathcircumstances.lastcause" class="vocab-tooltip"></span></td>

      <g:if test="${show45VersionUpdates}">

        <td class="value">
          <div class="vocDefDC" data-title="Death Circumstance" data-vocid="${deathCircumstancesInstance.lastCodCvocab?.id}" title="${deathCircumstancesInstance?.lastCodCvocab?.cVocabUserSelection}" >
            <span class="asLink">${deathCircumstancesInstance?.lastCodCvocab?.cVocabUserSelection}</span>
          </div>

        </td>
      </g:if>
      <g:else>

        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "lastCause")}</td>  
      </g:else>
      </tr>

      <g:if test="${deathCircumstancesInstance.otherLastCause}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.otherLastCause.label" default="Other Last Cause" /><span id="deathcircumstances.otherlastcause" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "otherLastCause")}</td>
        </tr>
      </g:if>
      <g:if test="${showDeathInterval}">
        <tr class="prop">
          <td class="name"><g:message code="deathCircumstances.lastCauseInterval.label" default="Last Cause Interval" />(hours)<span id="deathcircumstances.lastcauseinterval" class="vocab-tooltip"></span></td>
        <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "lastCauseInterval")} </td>
        </tr>
      </g:if>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.wasRefrigerated.label" default="Was the body refrigerated at any time before procurement?" /><span id="deathcircumstances.wasrefrigerated" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "wasRefrigerated")}</td>              
      </tr>
      <tr class="prop">
        <td class="name"><g:message code="deathCircumstances.estimatedHours.label" default="if yes, estimate number of hours in refrigeration" /> (hours)<span id="deathcircumstances.estimatedhours" class="vocab-tooltip"></span></td>
      <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "estimatedHours")}</td>          
      </tr>
      <g:if test="${!caseReportFormInstance.caseRecord.caseCollectionType.name.equals('Postmortem') && !caseReportFormInstance.caseRecord.caseCollectionType.name.equals('Surgical')}">
        <tr class="prop">
          <td class="name">Organ Donor (OPO) Type<span id="deathcircumstances.opotype" class="vocab-tooltip"></span></td>
          <td class="value">${fieldValue(bean: deathCircumstancesInstance, field: "opoType")}</td>                        
        </tr>
      </g:if>
      </tbody>
    </table>
  </div>
</div>
