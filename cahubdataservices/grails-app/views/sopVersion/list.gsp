
<%@ page import="nci.obbr.cahub.staticmembers.SopVersion" %>
<g:set var="bodyclass" value="new_page_enter_lowercase_folder_name_here list" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'sopVersion.label', default: 'SopVersion')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'sopVersion.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="Version" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sopVersionInstanceList}" status="i" var="sopVersionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sopVersionInstance.id}">${fieldValue(bean: sopVersionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sopVersionInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sopVersionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
