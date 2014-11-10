
<%@ page import="nci.obbr.cahub.util.HelpFileUpload" %>
<g:set var="bodyclass" value="help fileupload list" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'helpFileUpload.label', default: 'HelpFileUpload')}" />
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

        <g:sortableColumn property="id" title="${message(code: 'helpFileUpload.id.label', default: 'Id')}" />

        <g:sortableColumn property="uploadedBy" title="${message(code: 'helpFileUpload.uploadedBy.label', default: 'Uploaded by')}" />

        <g:sortableColumn property="fileName" title="${message(code: 'helpFileUpload.fileName.label', default: 'File Name')}" />

        <g:sortableColumn property="filePath" title="${message(code: 'helpFileUpload.filePath.label', default: 'File Path')}" />

        <g:sortableColumn property="comments" title="${message(code: 'helpFileUpload.comments.label', default: 'Comments')}" />

        <g:sortableColumn property="dateCreated" title="${message(code: 'helpFileUpload.dateCreated.label', default: 'Date Created')}" />

        <g:sortableColumn property="studyCode" title="${message(code: 'helpFileUpload.studyCode.label', default: 'Study Code')}" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${helpFileUploadInstanceList}" status="i" var="helpFileUploadInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show" id="${helpFileUploadInstance.id}">${fieldValue(bean: helpFileUploadInstance, field: "id")}</g:link></td>

          <td>${fieldValue(bean: helpFileUploadInstance, field: "uploadedBy")}</td>

          <td>${fieldValue(bean: helpFileUploadInstance, field: "fileName")}</td>

          <td>${fieldValue(bean: helpFileUploadInstance, field: "filePath")}</td>

          <td>${fieldValue(bean: helpFileUploadInstance, field: "comments")}</td>

          <td><g:formatDate date="${helpFileUploadInstance.dateCreated}" /></td>

          <td>${fieldValue(bean: helpFileUploadInstance, field: "studyCode")}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${helpFileUploadInstanceTotal}" />
    </div>
  </div>
</body>
</html>
