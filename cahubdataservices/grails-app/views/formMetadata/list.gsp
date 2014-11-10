
<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>
<g:set var="bodyclass" value="formmetadata list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'formMetadata.label', default: 'FormMetadata')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'formMetadata.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'formMetadata.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="code" title="${message(code: 'formMetadata.code.label', default: 'Code')}" />
                        
                            <%--<g:sortableColumn property="description" title="${message(code: 'formMetadata.description.label', default: 'Description')}" />--%>
                        
                            <th><g:message code="formMetadata.study.label" default="Study" /></th>
                        
                            <g:sortableColumn property="paperFormName" title="${message(code: 'formMetadata.paperFormName.label', default: 'Paper Form Name')}" />
                        
                            <g:sortableColumn property="formNumber" title="${message(code: 'formMetadata.formNumber.label', default: 'Form Number')}" />
                        
                            <g:sortableColumn property="cdrFormName" title="${message(code: 'formMetadata.cdrFormName.label', default: 'CDR Form Name')}" />
                        
                            <g:sortableColumn property="timeConstraintLabel" title="${message(code: 'formMetadata.timeConstraintLabel.label', default: 'Time Constraint Label')}" />
                        
                            <%--<g:sortableColumn property="timeConstraintVal" title="${message(code: 'formMetadata.timeConstraintVal.label', default: 'Time Constraint Val')}" />--%>
                            
                            <th><g:message code="formMetadata.sops.label" default="SOP (Active Version)" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${formMetadataInstanceList}" status="i" var="formMetadataInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${formMetadataInstance.id}">${fieldValue(bean: formMetadataInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "code")}</td>
                        
                            <%--<td>${fieldValue(bean: formMetadataInstance, field: "description")}</td>--%>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "study")}</td>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "paperFormName")}</td>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "formNumber")}</td>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "cdrFormName")}</td>
                        
                            <td>${fieldValue(bean: formMetadataInstance, field: "timeConstraintLabel")}</td>
                        
                            <%--<td>${fieldValue(bean: formMetadataInstance, field: "timeConstraintVal")}</td>--%>
                            
                            <td class="nowrap">
                                <g:each in="${formMetadataInstance.sops}" status="j" var="sop">
                                    <g:link controller="SOP" action="show" id="${sop.id}">${sop.code}</g:link> (${sop.activeSopVer}) <br />
                                </g:each>
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${formMetadataInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
