<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<g:set var="bodyclass" value="specimenrecord list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'specimenRecord.label', default: 'SpecimenRecord')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'specimenRecord.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'specimenRecord.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'specimenRecord.publicComments.label', default: 'Public Comments')}" />
                        
                            <th><g:message code="specimenRecord.caseRecord.label" default="Case Record" /></th>
                        
                            <th><g:message code="specimenRecord.parentSpecimen.label" default="Parent Specimen" /></th>
                        
                            <g:sortableColumn property="specimenId" title="${message(code: 'specimenRecord.specimenId.label', default: 'Specimen Id')}" />
                        
                            <g:sortableColumn property="publicId" title="${message(code: 'specimenRecord.publicId.label', default: 'Public Id')}" />
                        
                            <th><g:message code="specimenRecord.tissueType.label" default="Tissue Type" /></th>
                        
                            <th><g:message code="specimenRecord.provisionalTissueType.label" default="Provisional Tissue Type" /></th>
                        
                            <th><g:message code="specimenRecord.tissueLocation.label" default="Tissue Location" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${specimenRecordInstanceList}" status="i" var="specimenRecordInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${specimenRecordInstance.id}">${fieldValue(bean: specimenRecordInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "caseRecord")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "parentSpecimen")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "specimenId")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "publicId")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "tissueType")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "provisionalTissueType")}</td>
                        
                            <td>${fieldValue(bean: specimenRecordInstance, field: "tissueLocation")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${specimenRecordInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
