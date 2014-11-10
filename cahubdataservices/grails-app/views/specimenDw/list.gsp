
<%@ page import="nci.obbr.cahub.datawarehouse.SpecimenDw" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'specimenDw.label', default: 'SpecimenDw')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'specimenDw.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'specimenDw.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'specimenDw.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="specimenId" title="${message(code: 'specimenDw.specimenId.label', default: 'Specimen Id')}" />
                        
                            <th><g:message code="specimenDw.tissueType.label" default="Tissue Type" /></th>
                        
                            <th><g:message code="specimenDw.tissueLocation.label" default="Tissue Location" /></th>
                        
                            <g:sortableColumn property="ischemicTime" title="${message(code: 'specimenDw.ischemicTime.label', default: 'Ischemic Time')}" />
                        
                            <g:sortableColumn property="latestRin" title="${message(code: 'specimenDw.latestRin.label', default: 'Latest Rin')}" />
                        
                            <g:sortableColumn property="minFixTime" title="${message(code: 'specimenDw.minFixTime.label', default: 'Min Fix Time')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${specimenDwInstanceList}" status="i" var="specimenDwInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${specimenDwInstance.id}">${fieldValue(bean: specimenDwInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "specimenId")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "tissueType")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "tissueLocation")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "ischemicTime")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "latestRin")}</td>
                        
                            <td>${fieldValue(bean: specimenDwInstance, field: "minFixTime")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${specimenDwInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
