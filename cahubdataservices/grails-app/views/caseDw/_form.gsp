<%@ page import="nci.obbr.cahub.datawarehouse.CaseDw" %>



<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'caseRecord', 'error')} required">
	<label for="caseRecord">
		<g:message code="caseDw.caseRecord.label" default="Case Record" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="caseRecord.id" from="${nci.obbr.cahub.datarecords.CaseRecord.list()}" optionKey="id" value="${caseDwInstance?.caseRecord?.id}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'caseId', 'error')} required">
	<label for="caseId">
		<g:message code="caseDw.caseId.label" default="Case Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="caseId" value="${caseDwInstance?.caseId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'PMI', 'error')} required">
	<label for="PMI">
		<g:message code="caseDw.PMI.label" default="PMI" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="PMI" value="${fieldValue(bean: caseDwInstance, field: 'PMI')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'avgFixTime', 'error')} required">
	<label for="avgFixTime">
		<g:message code="caseDw.avgFixTime.label" default="Avg Fix Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="avgFixTime" value="${fieldValue(bean: caseDwInstance, field: 'avgFixTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'brain', 'error')} ">
	<label for="brain">
		<g:message code="caseDw.brain.label" default="Brain" />
		
	</label>
	<g:textField name="brain" value="${caseDwInstance?.brain}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'caseCollectionType', 'error')} required">
	<label for="caseCollectionType">
		<g:message code="caseDw.caseCollectionType.label" default="Case Collection Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="caseCollectionType.id" from="${nci.obbr.cahub.staticmembers.CaseCollectionType.list()}" optionKey="id" value="${caseDwInstance?.caseCollectionType?.id}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'maxFixTime', 'error')} required">
	<label for="maxFixTime">
		<g:message code="caseDw.maxFixTime.label" default="Max Fix Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="maxFixTime" value="${fieldValue(bean: caseDwInstance, field: 'maxFixTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'minFixTime', 'error')} required">
	<label for="minFixTime">
		<g:message code="caseDw.minFixTime.label" default="Min Fix Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="minFixTime" value="${fieldValue(bean: caseDwInstance, field: 'minFixTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'procedureDuration', 'error')} required">
	<label for="procedureDuration">
		<g:message code="caseDw.procedureDuration.label" default="Procedure Duration" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="procedureDuration" value="${fieldValue(bean: caseDwInstance, field: 'procedureDuration')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: caseDwInstance, field: 'specimens', 'error')} ">
	<label for="specimens">
		<g:message code="caseDw.specimens.label" default="Specimens" />
		
	</label>
	
<ul>
<g:each in="${caseDwInstance?.specimens?}" var="s">
    <li><g:link controller="specimenDw" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="specimenDw" action="create" params="['caseDw.id': caseDwInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'specimenDw.label', default: 'SpecimenDw')])}</g:link>

</div>

