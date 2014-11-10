
<%@ page import="nci.obbr.cahub.datarecords.ShipDiscrepancy" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'shipDiscrepancy.label', default: 'ShipDiscrepancy')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'shipDiscrepancy.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'shipDiscrepancy.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'shipDiscrepancy.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="discrepancyId" title="${message(code: 'shipDiscrepancy.discrepancyId.label', default: 'Discrepancy Id')}" />
                        
                            <g:sortableColumn property="shippingContainerId" title="${message(code: 'shipDiscrepancy.shippingContainerId.label', default: 'Shipping Container Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'shipDiscrepancy.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="value" title="${message(code: 'shipDiscrepancy.value.label', default: 'Value')}" />
                        
                            <th><g:message code="shipDiscrepancy.shipEvent.label" default="Ship Event" /></th>
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'shipDiscrepancy.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="internalGUID" title="${message(code: 'shipDiscrepancy.internalGUID.label', default: 'Internal GUID')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${shipDiscrepancyInstanceList}" status="i" var="shipDiscrepancyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${shipDiscrepancyInstance.id}">${fieldValue(bean: shipDiscrepancyInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "discrepancyId")}</td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "shippingContainerId")}</td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "value")}</td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "shipEvent")}</td>
                        
                            <td><g:formatDate date="${shipDiscrepancyInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: shipDiscrepancyInstance, field: "internalGUID")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${shipDiscrepancyInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
