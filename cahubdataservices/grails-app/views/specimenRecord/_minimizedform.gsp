<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>                    
<table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicComments"><g:message code="specimenRecord.publicComments.label" default="Public Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'publicComments', 'errors')}">
                                    <g:textArea name="publicComments" cols="40" rows="5" value="${specimenRecordInstance?.publicComments}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseRecord"><g:message code="specimenRecord.caseRecord.label" default="Case Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'caseRecord', 'errors')}">
                                  <input type="hidden" id="caseRecordID" name="caseRecord.id" value="${specimenRecordInstance?.caseRecord?.id}"  />
                                  <a title="Change Case Record ID" id="changeRecordID" class="uibutton borderless removepadding"><span id="caseid">${CaseRecord.get(specimenRecordInstance?.caseRecord?.id)?.caseId}</span><span class="ui-icon ui-icon-circle-zoomin"></span></a>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specimenId"><g:message code="specimenRecord.specimenId.label" default="Specimen Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'specimenId', 'errors')}">
                                    <g:textField name="specimenId" value="${specimenRecordInstance?.specimenId}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissueType"><g:message code="specimenRecord.tissueType.label" default="Tissue Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'tissueType', 'errors')}">
                                    <g:select name="tissueType.id" from="${nci.obbr.cahub.staticmembers.AcquisitionType.list()}" optionKey="id" value="${specimenRecordInstance?.tissueType?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="provisionalTissueType"><g:message code="specimenRecord.provisionalTissueType.label" default="Provisional Tissue Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'provisionalTissueType', 'errors')}">
                                    <g:select name="provisionalTissueType.id" from="${nci.obbr.cahub.staticmembers.AcquisitionType.list()}" optionKey="id" value="${specimenRecordInstance?.provisionalTissueType?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fixative"><g:message code="specimenRecord.fixative.label" default="Fixative" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'fixative', 'errors')}">
                                    <g:select name="fixative.id" from="${nci.obbr.cahub.staticmembers.Fixative.list()}" optionKey="id" value="${specimenRecordInstance?.fixative?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="inQuarantine"><g:message code="specimenRecord.inQuarantine.label" default="In Quarantine" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'inQuarantine', 'errors')}">
                                    <g:checkBox name="inQuarantine" value="${specimenRecordInstance?.inQuarantine}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="wasConsumed"><g:message code="specimenRecord.wasConsumed.label" default="Was Consumed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'wasConsumed', 'errors')}">
                                    <g:checkBox name="wasConsumed" value="${specimenRecordInstance?.wasConsumed}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="protocol"><g:message code="specimenRecord.protocol.label" default="Protocol" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'protocol', 'errors')}">
                                    <g:select name="protocol.id" from="${nci.obbr.cahub.staticmembers.Protocol.list()}" optionKey="id" value="${specimenRecordInstance?.protocol?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="containerType"><g:message code="specimenRecord.containerType.label" default="Container Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'containerType', 'errors')}">
                                    <g:select name="containerType.id" from="${nci.obbr.cahub.staticmembers.ContainerType.list()}" optionKey="id" value="${specimenRecordInstance?.containerType?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bloodTimeDraw"><g:message code="specimenRecord.bloodTimeDraw.label" default="Blood Time Draw" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'bloodTimeDraw', 'errors')}">
                                    <g:textField name="bloodTimeDraw" value="${specimenRecordInstance?.bloodTimeDraw}" />
                                </td>
                            </tr>
                        
                        
                        </tbody>
                    </table>
