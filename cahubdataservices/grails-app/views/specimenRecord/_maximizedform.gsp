                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="specimenRecord.internalComments.label" default="Internal Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${specimenRecordInstance?.internalComments}" />
                                </td>
                            </tr>
                        
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
                                    <g:select name="caseRecord.id" from="${nci.obbr.cahub.datarecords.CaseRecord.list()}" optionKey="id" value="${specimenRecordInstance?.caseRecord?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="parentSpecimen"><g:message code="specimenRecord.parentSpecimen.label" default="Parent Specimen" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'parentSpecimen', 'errors')}">
                                    <g:select name="parentSpecimen.id" from="${nci.obbr.cahub.datarecords.SpecimenRecord.list()}" optionKey="id" value="${specimenRecordInstance?.parentSpecimen?.id}" noSelection="['null': '']" />
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
                                  <label for="publicId"><g:message code="specimenRecord.publicId.label" default="Public Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'publicId', 'errors')}">
                                    <g:textField name="publicId" value="${specimenRecordInstance?.publicId}" />
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
                                  <label for="tissueLocation"><g:message code="specimenRecord.tissueLocation.label" default="Tissue Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'tissueLocation', 'errors')}">
                                    <g:select name="tissueLocation.id" from="${nci.obbr.cahub.staticmembers.AcquisitionLocation.list()}" optionKey="id" value="${specimenRecordInstance?.tissueLocation?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="otherTissueLocation"><g:message code="specimenRecord.otherTissueLocation.label" default="Other Tissue Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'otherTissueLocation', 'errors')}">
                                    <g:textField name="otherTissueLocation" value="${specimenRecordInstance?.otherTissueLocation}" />
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
                                  <label for="shipEvents"><g:message code="specimenRecord.shipEvents.label" default="Ship Events" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'shipEvents', 'errors')}">
                                    <g:select name="shipEvents" from="${nci.obbr.cahub.datarecords.ShippingEvent.list()}" multiple="yes" optionKey="id" size="5" value="${specimenRecordInstance?.shipEvents*.id}" />
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
                                  <label for="isDepleted"><g:message code="specimenRecord.isDepleted.label" default="Is Depleted" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'isDepleted', 'errors')}">
                                    <g:checkBox name="isDepleted" value="${specimenRecordInstance?.isDepleted}" />
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
                                  <label for="chpBloodRecord"><g:message code="specimenRecord.chpBloodRecord.label" default="Chp Blood Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'chpBloodRecord', 'errors')}">
                                    <g:select name="chpBloodRecord.id" from="${nci.obbr.cahub.datarecords.ChpBloodRecord.list()}" optionKey="id" value="${specimenRecordInstance?.chpBloodRecord?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="chpTissueRecord"><g:message code="specimenRecord.chpTissueRecord.label" default="Chp Tissue Record" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'chpTissueRecord', 'errors')}">
                                    <g:select name="chpTissueRecord.id" from="${nci.obbr.cahub.datarecords.ChpTissueRecord.list()}" optionKey="id" value="${specimenRecordInstance?.chpTissueRecord?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosectorComments"><g:message code="specimenRecord.prosectorComments.label" default="Prosector Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'prosectorComments', 'errors')}">
                                    <g:textArea name="prosectorComments" cols="40" rows="5" value="${specimenRecordInstance?.prosectorComments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="aliquotTimeRemoved"><g:message code="specimenRecord.aliquotTimeRemoved.label" default="Aliquot Time Removed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'aliquotTimeRemoved', 'errors')}">
                                    <g:textField name="aliquotTimeRemoved" value="${specimenRecordInstance?.aliquotTimeRemoved}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="aliquotTimeFixed"><g:message code="specimenRecord.aliquotTimeFixed.label" default="Aliquot Time Fixed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'aliquotTimeFixed', 'errors')}">
                                    <g:textField name="aliquotTimeFixed" value="${specimenRecordInstance?.aliquotTimeFixed}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="aliquotTimeStabilized"><g:message code="specimenRecord.aliquotTimeStabilized.label" default="Aliquot Time Stabilized" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'aliquotTimeStabilized', 'errors')}">
                                    <g:textField name="aliquotTimeStabilized" value="${specimenRecordInstance?.aliquotTimeStabilized}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sizeDiffThanSOP"><g:message code="specimenRecord.sizeDiffThanSOP.label" default="Size Diff Than SOP" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'sizeDiffThanSOP', 'errors')}">
                                    <g:textField name="sizeDiffThanSOP" value="${specimenRecordInstance?.sizeDiffThanSOP}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="brainTimeStartRemoval"><g:message code="specimenRecord.brainTimeStartRemoval.label" default="Brain Time Start Removal" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'brainTimeStartRemoval', 'errors')}">
                                    <g:textField name="brainTimeStartRemoval" value="${specimenRecordInstance?.brainTimeStartRemoval}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="brainTimeEndAliquot"><g:message code="specimenRecord.brainTimeEndAliquot.label" default="Brain Time End Aliquot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'brainTimeEndAliquot', 'errors')}">
                                    <g:textField name="brainTimeEndAliquot" value="${specimenRecordInstance?.brainTimeEndAliquot}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="brainTimeIce"><g:message code="specimenRecord.brainTimeIce.label" default="Brain Time Ice" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'brainTimeIce', 'errors')}">
                                    <g:textField name="brainTimeIce" value="${specimenRecordInstance?.brainTimeIce}" />
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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bloodTimeDrawInverted"><g:message code="specimenRecord.bloodTimeDrawInverted.label" default="Blood Time Draw Inverted" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'bloodTimeDrawInverted', 'errors')}">
                                    <g:textField name="bloodTimeDrawInverted" value="${specimenRecordInstance?.bloodTimeDrawInverted}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="skinTimeIntoMedium"><g:message code="specimenRecord.skinTimeIntoMedium.label" default="Skin Time Into Medium" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'skinTimeIntoMedium', 'errors')}">
                                    <g:textField name="skinTimeIntoMedium" value="${specimenRecordInstance?.skinTimeIntoMedium}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tumorStatus"><g:message code="specimenRecord.tumorStatus.label" default="Tumor Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'tumorStatus', 'errors')}">
                                    <g:select name="tumorStatus.id" from="${nci.obbr.cahub.staticmembers.TumorStatus.list()}" optionKey="id" value="${specimenRecordInstance?.tumorStatus?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prcSpecimen"><g:message code="specimenRecord.prcSpecimen.label" default="Prc Specimen" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'prcSpecimen', 'errors')}">
                                    <g:select name="prcSpecimen.id" from="${nci.obbr.cahub.prc.PrcSpecimen.list()}" optionKey="id" value="${specimenRecordInstance?.prcSpecimen?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalGUID"><g:message code="specimenRecord.internalGUID.label" default="Internal GUID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'internalGUID', 'errors')}">
                                    <g:textField name="internalGUID" value="${specimenRecordInstance?.internalGUID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pathologyReview"><g:message code="specimenRecord.pathologyReview.label" default="Pathology Review" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'pathologyReview', 'errors')}">
                                    
<ul>
<g:each in="${specimenRecordInstance?.pathologyReview?}" var="p">
    <li><g:link controller="prcSpecimenReport" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="prcSpecimenReport" action="create" params="['specimenRecord.id': specimenRecordInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prcSpecimenReport.label', default: 'PrcSpecimenReport')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publicVersion"><g:message code="specimenRecord.publicVersion.label" default="Public Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'publicVersion', 'errors')}">
                                    <g:textField name="publicVersion" value="${fieldValue(bean: specimenRecordInstance, field: 'publicVersion')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="slides"><g:message code="specimenRecord.slides.label" default="Slides" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specimenRecordInstance, field: 'slides', 'errors')}">
                                    
<ul>
<g:each in="${specimenRecordInstance?.slides?}" var="s">
    <li><g:link controller="slideRecord" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="slideRecord" action="create" params="['specimenRecord.id': specimenRecordInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'slideRecord.label', default: 'SlideRecord')])}</g:link>

                                </td>
                            </tr>

                        </tbody>
                    </table>
