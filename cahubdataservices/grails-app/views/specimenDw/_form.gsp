<%@ page import="nci.obbr.cahub.datawarehouse.SpecimenDw" %>



<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'internalComments', 'error')} ">
	<label for="internalComments">
		<g:message code="specimenDw.internalComments.label" default="Internal Comments" />
		
	</label>
	<g:textArea name="internalComments" cols="40" rows="5" value="${specimenDwInstance?.internalComments}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'publicComments', 'error')} ">
	<label for="publicComments">
		<g:message code="specimenDw.publicComments.label" default="Public Comments" />
		
	</label>
	<g:textArea name="publicComments" cols="40" rows="5" value="${specimenDwInstance?.publicComments}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'specimenRecord', 'error')} required">
	<label for="specimenRecord">
		<g:message code="specimenDw.specimenRecord.label" default="Specimen Record" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="specimenRecord.id" from="${nci.obbr.cahub.datarecords.SpecimenRecord.list()}" optionKey="id" value="${specimenDwInstance?.specimenRecord?.id}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'specimenId', 'error')} required">
	<label for="specimenId">
		<g:message code="specimenDw.specimenId.label" default="Specimen Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="specimenId" value="${specimenDwInstance?.specimenId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'tissueType', 'error')} required">
	<label for="tissueType">
		<g:message code="specimenDw.tissueType.label" default="Tissue Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="tissueType.id" from="${nci.obbr.cahub.staticmembers.AcquisitionType.list()}" optionKey="id" value="${specimenDwInstance?.tissueType?.id}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'tissueLocation', 'error')} ">
	<label for="tissueLocation">
		<g:message code="specimenDw.tissueLocation.label" default="Tissue Location" />
		
	</label>
	<g:select name="tissueLocation.id" from="${nci.obbr.cahub.staticmembers.AcquisitionLocation.list()}" optionKey="id" value="${specimenDwInstance?.tissueLocation?.id}" noSelection="['null': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'ischemicTime', 'error')} ">
	<label for="ischemicTime">
		<g:message code="specimenDw.ischemicTime.label" default="Ischemic Time" />
		
	</label>
	<g:textField name="ischemicTime" value="${fieldValue(bean: specimenDwInstance, field: 'ischemicTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'latestRin', 'error')} ">
	<label for="latestRin">
		<g:message code="specimenDw.latestRin.label" default="Latest Rin" />
		
	</label>
	<g:textField name="latestRin" value="${fieldValue(bean: specimenDwInstance, field: 'latestRin')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'minFixTime', 'error')} ">
	<label for="minFixTime">
		<g:message code="specimenDw.minFixTime.label" default="Min Fix Time" />
		
	</label>
	<g:textField name="minFixTime" value="${fieldValue(bean: specimenDwInstance, field: 'minFixTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'avgFixTime', 'error')} ">
	<label for="avgFixTime">
		<g:message code="specimenDw.avgFixTime.label" default="Avg Fix Time" />
		
	</label>
	<g:textField name="avgFixTime" value="${fieldValue(bean: specimenDwInstance, field: 'avgFixTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'maxFixTime', 'error')} ">
	<label for="maxFixTime">
		<g:message code="specimenDw.maxFixTime.label" default="Max Fix Time" />
		
	</label>
	<g:textField name="maxFixTime" value="${fieldValue(bean: specimenDwInstance, field: 'maxFixTime')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'procedureDuration', 'error')} ">
	<label for="procedureDuration">
		<g:message code="specimenDw.procedureDuration.label" default="Procedure Duration" />
		
	</label>
	<g:textField name="procedureDuration" value="${fieldValue(bean: specimenDwInstance, field: 'procedureDuration')}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'procedureType', 'error')} ">
	<label for="procedureType">
		<g:message code="specimenDw.procedureType.label" default="Procedure Type" />
		
	</label>
	<g:select name="procedureType.id" from="${nci.obbr.cahub.staticmembers.CaseCollectionType.list()}" optionKey="id" value="${specimenDwInstance?.procedureType?.id}" noSelection="['null': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'brain', 'error')} ">
	<label for="brain">
		<g:message code="specimenDw.brain.label" default="Brain" />
		
	</label>
	<g:textField name="brain" value="${specimenDwInstance?.brain}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'caseDw', 'error')} required">
	<label for="caseDw">
		<g:message code="specimenDw.caseDw.label" default="Case Dw" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="caseDw.id" from="${nci.obbr.cahub.datawarehouse.CaseDw.list()}" optionKey="id" value="${specimenDwInstance?.caseDw?.id}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'internalGUID', 'error')} ">
	<label for="internalGUID">
		<g:message code="specimenDw.internalGUID.label" default="Internal GUID" />
		
	</label>
	<g:textField name="internalGUID" value="${specimenDwInstance?.internalGUID}" />
</div>

<div class="fieldcontain ${hasErrors(bean: specimenDwInstance, field: 'publicVersion', 'error')} required">
	<label for="publicVersion">
		<g:message code="specimenDw.publicVersion.label" default="Public Version" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="publicVersion" value="${fieldValue(bean: specimenDwInstance, field: 'publicVersion')}" />
</div>

