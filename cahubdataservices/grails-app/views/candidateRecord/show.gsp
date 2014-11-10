
<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<g:set var="bodyclass" value="candidaterecord show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'candidateRecord.label', default: 'CandidateRecord')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
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
                            <td valign="top" class="name"><g:message code="candidateRecord.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: candidateRecordInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: candidateRecordInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: candidateRecordInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.candidateId.label" default="Candidate Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: candidateRecordInstance, field: "candidateId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.caseRecord.label" default="Case Record" /></td>
                            
                            <td valign="top" class="value"><g:link controller="caseRecord" action="show" id="${candidateRecordInstance?.caseRecord?.id}">${candidateRecordInstance?.caseRecord?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.bss.label" default="Bss" /></td>
                            
                            <td valign="top" class="value"><g:link controller="BSS" action="show" id="${candidateRecordInstance?.bss?.id}">${candidateRecordInstance?.bss?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.study.label" default="Study" /></td>
                            
                            <td valign="top" class="value"><g:link controller="study" action="show" id="${candidateRecordInstance?.study?.id}">${candidateRecordInstance?.study?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.caseCollectionType.label" default="Case Collection Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="caseCollectionType" action="show" id="${candidateRecordInstance?.caseCollectionType?.id}">${candidateRecordInstance?.caseCollectionType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.isConsented.label" default="Is Consented" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${candidateRecordInstance?.isConsented}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.isEligible.label" default="Is Eligible" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${candidateRecordInstance?.isEligible}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.icdGtexNdri.label" default="Icd Gtex Ndri" /></td>
                            
                            <td valign="top" class="value"><g:link controller="icdGtexNdri" action="show" id="${candidateRecordInstance?.icdGtexNdri?.id}">${candidateRecordInstance?.icdGtexNdri?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.icdGtexRpci.label" default="Icd Gtex Rpci" /></td>
                            
                            <td valign="top" class="value"><g:link controller="icdGtexRpci" action="show" id="${candidateRecordInstance?.icdGtexRpci?.id}">${candidateRecordInstance?.icdGtexRpci?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.icdGtexSc.label" default="Icd Gtex Sc" /></td>
                            
                            <td valign="top" class="value"><g:link controller="icdGtexSc" action="show" id="${candidateRecordInstance?.icdGtexSc?.id}">${candidateRecordInstance?.icdGtexSc?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.donorEligibilityGtex.label" default="Donor Eligibility Gtex" /></td>
                            
                            <td valign="top" class="value"><g:link controller="donorEligibilityGtex" action="show" id="${candidateRecordInstance?.donorEligibilityGtex?.id}">${candidateRecordInstance?.donorEligibilityGtex?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${candidateRecordInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: candidateRecordInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${candidateRecordInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="candidateRecord.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: candidateRecordInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${candidateRecordInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
