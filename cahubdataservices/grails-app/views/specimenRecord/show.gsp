<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<g:set var="bodyclass" value="specimenrecord show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'specimenRecord.label', default: 'SpecimenRecord')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.caseRecord.label" default="Case Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="caseRecord" action="show" id="${specimenRecordInstance?.caseRecord?.id}">${specimenRecordInstance?.caseRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.parentSpecimen.label" default="Parent Specimen" /></td>
                            
                            <td valign="top" class="value"><g:link controller="specimenRecord" action="show" id="${specimenRecordInstance?.parentSpecimen?.id}">${specimenRecordInstance?.parentSpecimen?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.specimenId.label" default="Specimen Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "specimenId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.publicId.label" default="Public Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "publicId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.tissueType.label" default="Tissue Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="acquisitionType" action="show" id="${specimenRecordInstance?.tissueType?.id}">${specimenRecordInstance?.tissueType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.provisionalTissueType.label" default="Provisional Tissue Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="acquisitionType" action="show" id="${specimenRecordInstance?.provisionalTissueType?.id}">${specimenRecordInstance?.provisionalTissueType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.tissueLocation.label" default="Tissue Location" /></td>
                            
                            <td valign="top" class="value"><g:link controller="acquisitionLocation" action="show" id="${specimenRecordInstance?.tissueLocation?.id}">${specimenRecordInstance?.tissueLocation?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.otherTissueLocation.label" default="Other Tissue Location" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "otherTissueLocation")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.fixative.label" default="Fixative" /></td>
                            
                            <td valign="top" class="value"><g:link controller="fixative" action="show" id="${specimenRecordInstance?.fixative?.id}">${specimenRecordInstance?.fixative?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.inQuarantine.label" default="In Quarantine" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${specimenRecordInstance?.inQuarantine}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.shipEvents.label" default="Ship Events" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${specimenRecordInstance.shipEvents}" var="s">
                                    <li><g:link controller="shippingEvent" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.processEvents.label" default="Processing Events" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${specimenRecordInstance.processEvents}" var="s">
                                    <li><g:link controller="processingEvent" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>                                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.wasConsumed.label" default="Was Consumed" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${specimenRecordInstance?.wasConsumed}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.isDepleted.label" default="Is Depleted" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${specimenRecordInstance?.isDepleted}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.protocol.label" default="Protocol" /></td>
                            
                            <td valign="top" class="value"><g:link controller="protocol" action="show" id="${specimenRecordInstance?.protocol?.id}">${specimenRecordInstance?.protocol?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.containerType.label" default="Container Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="containerType" action="show" id="${specimenRecordInstance?.containerType?.id}">${specimenRecordInstance?.containerType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.chpBloodRecord.label" default="Chp Blood Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="chpBloodRecord" action="show" id="${specimenRecordInstance?.chpBloodRecord?.id}">${specimenRecordInstance?.chpBloodRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.chpTissueRecord.label" default="Chp Tissue Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="chpTissueRecord" action="show" id="${specimenRecordInstance?.chpTissueRecord?.id}">${specimenRecordInstance?.chpTissueRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.prosectorComments.label" default="Prosector Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "prosectorComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.aliquotTimeRemoved.label" default="Aliquot Time Removed" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "aliquotTimeRemoved")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.aliquotTimeFixed.label" default="Aliquot Time Fixed" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "aliquotTimeFixed")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.aliquotTimeStabilized.label" default="Aliquot Time Stabilized" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "aliquotTimeStabilized")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.sizeDiffThanSOP.label" default="Size Diff Than SOP" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "sizeDiffThanSOP")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.brainTimeStartRemoval.label" default="Brain Time Start Removal" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "brainTimeStartRemoval")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.brainTimeEndAliquot.label" default="Brain Time End Aliquot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "brainTimeEndAliquot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.brainTimeIce.label" default="Brain Time Ice" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "brainTimeIce")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.bloodTimeDraw.label" default="Blood Time Draw" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "bloodTimeDraw")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.bloodTimeDrawInverted.label" default="Blood Time Draw Inverted" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "bloodTimeDrawInverted")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.skinTimeIntoMedium.label" default="Skin Time Into Medium" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "skinTimeIntoMedium")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.tumorStatus.label" default="Tumor Status" /></td>
                            
                            <td valign="top" class="value"><g:link controller="tumorStatus" action="show" id="${specimenRecordInstance?.tumorStatus?.id}">${specimenRecordInstance?.tumorStatus?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.prcSpecimen.label" default="Prc Specimen" /></td>
                            
                            <td valign="top" class="value"><g:link controller="prcSpecimen" action="show" id="${specimenRecordInstance?.prcSpecimen?.id}">${specimenRecordInstance?.prcSpecimen?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${specimenRecordInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${specimenRecordInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.pathologyReview.label" default="Pathology Review" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${specimenRecordInstance.pathologyReview}" var="p">
                                    <li><g:link controller="prcSpecimenReport" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: specimenRecordInstance, field: "publicVersion")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="specimenRecord.slides.label" default="Slides" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${specimenRecordInstance.slides}" var="s">
                                    <li><g:link controller="slideRecord" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${specimenRecordInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Edit All" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
