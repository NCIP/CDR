<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<g:set var="bodyclass" value="fileupload" scope="request"/>

<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'fileUpload.label', default: 'File Upload')}" />
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
          <!--  <th>Seq.#</th> -->
        <g:sortableColumn property="fileName" title="${message(code: 'fileUpload.fileName.label', default: 'File Name')}" />
        <g:if test="${isTest}">
          <th>CaseId</th>
          <th>BSS Code</th>
        </g:if>
        <g:sortableColumn property="uploadTime" class="dateentrymed" title="${message(code: 'fileUpload.uploadTime.label', default: 'Upload Time')}" />
        <g:sortableColumn property="bssCode" title="${message(code: 'fileUpload.organization.label', default: 'Organization/BSS')}" />

        <g:sortableColumn property="category" title="${message(code: 'fileUpload.category.label', default: 'Category')}" /> 
        <g:if test="${session.org.code == 'OBBR'}">
          <g:sortableColumn property="category" title="${message(code: 'fileUpload.hideFromBss.label', default: 'Hide From BSS')}" /> 
        </g:if>

        <th> Action </th>
         
        </tr>
        </thead>
        <tbody>
        <g:each in="${fileUploadInstanceList}" status="i" var="fileUploadInstance">

          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            
            <td><g:link class="createspace" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link>
          <p><%=fieldValue(bean: fileUploadInstance, field: "comments").replace("\n","<br />")%></p>
          </td>
          <g:if test="${isTest}">
            <td>${fieldValue(bean: fileUploadInstance, field: "caseId")}</td>
            <td>${fieldValue(bean: fileUploadInstance, field: "bssCode")}</td>
          </g:if>
          
          <td>${fieldValue(bean: fileUploadInstance, field: "uploadTime")}</td>

          <td>${fieldValue(bean: fileUploadInstance, field: "bssCode")}</td>
      <!--    <td>${fieldValue(bean: fileUploadInstance, field: "study")}</td> -->
          <td>${fieldValue(bean: fileUploadInstance, field: "category")}</td> 
          <g:if test="${session.org.code == 'OBBR'}">
            <td>${fileUploadInstance.hideFromBss ?'Yes':'No'}</td>
          </g:if>

          
          <td nowrap>
           
          <g:if test="${fileUploadInstance?.bssCode?.equals(session.org.code) || session.org.code == 'OBBR'}">
              <g:link class="ui-button ui-state-default ui-corner-all removepadding" title="edit" controller="fileUpload" action="show" id="${fileUploadInstance.id}" ><span class="ui-icon ui-icon-pencil">Edit</span></g:link>
              <g:link class="ui-button ui-state-default ui-corner-all removepadding" title="delete" controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')"><span class="ui-icon ui-icon-trash">Remove</span></g:link>
            </g:if>
            </td>
          </tr>

        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate action="list" params="${params}" total="${fileUploadInstanceTotal}" /> 
    </div>
  </div>
</body>
</html>
