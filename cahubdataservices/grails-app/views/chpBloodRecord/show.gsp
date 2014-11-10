
<%@ page import="nci.obbr.cahub.datarecords.ChpBloodRecord" %>
<g:set var="bodyclass" value="chpblood show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'chpBloodRecord.label', default: 'ChpBloodRecord')}" />
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
                            <td valign="top" class="name"><g:message code="chpBloodRecord.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.internalComments.label" default="Internal Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "internalComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.publicComments.label" default="Public Comments" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "publicComments")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.hemolysis.label" default="Hemolysis" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "hemolysis")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.bloodProcComment.label" default="Blood Proc Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "bloodProcComment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.bloodStorageComment.label" default="Blood Storage Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "bloodStorageComment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.bloodFrozen.label" default="Blood Frozen" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpBloodRecordInstance?.bloodFrozen}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.bloodProcEnd.label" default="Blood Proc End" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpBloodRecordInstance?.bloodProcEnd}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.bloodProcStart.label" default="Blood Proc Start" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpBloodRecordInstance?.bloodProcStart}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.bloodStorage.label" default="Blood Storage" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpBloodRecordInstance?.bloodStorage}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpBloodRecordInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.internalGUID.label" default="Internal GUID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "internalGUID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${chpBloodRecordInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.publicVersion.label" default="Public Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "publicVersion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.specimenRecord.label" default="Specimen Record" /></td>
                            
                            <td valign="top" class="value">${chpBloodRecordInstance?.specimenRecord?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.volUnits.label" default="Vol Units" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "volUnits")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="chpBloodRecord.volume.label" default="Volume" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: chpBloodRecordInstance, field: "volume")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
