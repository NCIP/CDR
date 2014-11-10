<g:if test="${params.action != 'create' && params.action != 'save'}">
  <table>
    <tbody>
      <tr class="prop">
        <td valign="top">
          <div class="clearfix">
            <dl class="formdetails left"><dt>Date opened:</dt><dd><g:formatDate format="MM/dd/yyyy HH:mm" date="${deviationInstance?.dateCreated}" /></dd></dl>
            <dl class="formdetails left"><dt>Status:</dt><dd>${deviationInstance?.queryStatus}</dd></dl>
          </div>
        </td>
      </tr>      
    </tbody>
  </table>
</g:if>

<div class="list">
  <table class="tdwrap">
    <tbody>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="caseRecord">Case Record:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'caseRecord', 'errors')}">
    <g:if test="${params.action == 'show'}">
      <g:link controller="caseRecord" action="display" id="${deviationInstance?.caseRecord?.id}">${deviationInstance?.caseRecord?.caseId}</g:link>
    </g:if>
    <g:else>
${nci.obbr.cahub.datarecords.CaseRecord.get(deviationInstance?.caseRecord?.id)}
      <g:hiddenField name="caseRecord.id" value="${deviationInstance?.caseRecord?.id}" />
    </g:else>
    </td>
    </tr>



    <tr class="prop">
      <td colspan="2" valign="top" class="name ${hasErrors(bean: deviationInstance, field: 'description', 'errors')}">
        <label for="description">Description:</label>
    <g:textArea class="textwidehigh" name="description" cols="40" rows="5" value="${deviationInstance?.description}" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="sop">SOP:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'sop', 'errors')}">
    <g:textField name="sop" value="${deviationInstance?.sop}" />
    </td>
    </tr>



    <tr class="prop">
      <td valign="top" class="name">
        <label for="type">Type:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'type', 'errors')}">
    <g:select name="type" from="${['Minor', 'Major', 'Critical']}" value="${deviationInstance?.type}" noSelection="['':'']" />
    </td>
    </tr>

    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateDeviation">Date of Deviation:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'dateDeviation', 'errors')}">
    <g:jqDatePicker name="dateDeviation" value="${deviationInstance?.dateDeviation}" />
    </td>
    </tr>
    <tr class="prop">
      <td valign="top" class="name">
        <label for="dateDeviation">Planned:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'planned', 'errors')}">
    <g:bpvYesNoRadioPicker id="planned" checked="${deviationInstance?.planned}"  name="planned" />
    </td>
    </td>
    </tr>   


    <tr  id="memoCiNum" class="prop depends-on" data-id="planned_yes">
      <td valign="top" class="name">
        <label for="dateDeviation">CI Memo Number:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'memoCiNum', 'errors')}">
    <g:textField  name="memoCiNum" value="${deviationInstance?.memoCiNum}" />
    </td>
    </tr>            
    <tr class="prop depends-on" data-id="planned_yes" id="memoCiNumDate">
      <td valign="top" class="name">
        <label for="dateDeviation">Memo Expiration:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'memoExpiration', 'errors')}">
    <g:jqDatePicker  name="memoExpiration" value="${deviationInstance?.memoExpiration}" />
    </td>
    </tr> 

<%-- pmh 05/14/14 added jira id cdrqa 1153--%>
    <tr id="jiraids" class="prop depends-on" data-id="planned_no">
      <td valign="top" class="name">
        <label for="jiraId">JIRA ID:<br><span class="verysmallfont">(For multiple entries please enter comma separated IDs)</span></label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'jiraId', 'errors')}">
    <g:if test="${params.action != 'create' && params.action != 'save' && params.action != 'index' && deviationInstance?.jiraId}">
    <ul>
      
      <g:each in="${deviationInstance?.jiraId?.split(',')}" var="k">
        <li><a target="_blank" href="${jira_home.value}${k.trim()}">${k}</a></li>
      </g:each>
    </ul>
     
    </g:if>
    <g:else>
      <g:textField name="jiraId" value="${deviationInstance?.jiraId}" />
    </g:else>

    </td>
    </tr> 

    <tr  id="jiraidsclosedt" class="prop depends-on" data-id="planned_no">
      <td valign="top" class="name">
        <label for="jiraCloseDate">Date JIRA Issue(s) Closed:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'jiraCloseDate', 'errors')}">
    <g:jqDatePicker name="jiraCloseDate" value="${deviationInstance?.jiraCloseDate}" id="jiraclosedt"/>
    </td>
    </tr>   

    <tr class="prop depends-on" id="ncissuedyesno" data-id="planned_no">
      <td valign="top" class="name">
        <label for="nonConformance">Non Conformance/CAPPA issued?</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'nonConformance', 'errors')}">
    <g:bpvYesNoRadioPicker checked="${deviationInstance?.nonConformance}"  name="nonConformance" />
    </td>
    </tr>

<%-- pmh 05/14/14 added non conformance CAPPA number cdrqa 1153--%>
    <tr class="prop depends-on" data-id="nonConformance_yes" id="nccappanum">
      <td valign="top" class="name" >
        <label for="nonConformCapaNum">Non Conformance CAPPA Number:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'nonConformCapaNum', 'errors')}">
    <g:textField name="nonConformCapaNum" value="${deviationInstance?.nonConformCapaNum}" />
    </td>
    </tr>     

<%-- pmh 05/14/14 added non conformance CAPPA close date & jira close date cdrqa 1153--%>
    <tr class="prop depends-on" data-id="nonConformance_yes" id="nccappanumclosedt">
      <td valign="top" class="name" >
        <label for="capaCloseDate">Date All Non Conformance/CAPPA(s) Closed:</label>
      </td>
      <td valign="top" class="value ${hasErrors(bean: deviationInstance, field: 'capaCloseDate', 'errors')}">
    <g:jqDatePicker name="capaCloseDate" value="${deviationInstance?.capaCloseDate}" id="capaclosedt"/>
    </td>
    </tr>   

    <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_DM') || session.DM}">
      <tr class="prop">
        <td valign="top" class="name">
          <label for="queries">Related Queries:</label>
        </td>
        <td valign="top">
      <g:each in="${queryList}" status="i" var="query">
        <g:checkBox name="query_${query.id}" value="${deviationInstance?.id ? query.deviation?.id == deviationInstance?.id : false}" />
        <g:if test="${query.description?.size() <= 60}"><span><g:link controller="query" action="show" id="${query.id}">${query.id}</g:link>${": " + query.description}</span></g:if>
        <g:else><span class="ca-tooltip-nobg" data-msg="${query.description?.replace('\n', '<br />')?.replace('\"', '\'')}"><g:link controller="query" action="show" id="${query.id}">${query.id}</g:link>${": " + query.description?.substring(0, 60)}&nbsp;&hellip;</span></g:else><br />
      </g:each>
      </td>
      </tr>
    </g:if>


    </tbody>
  </table>
</div>
