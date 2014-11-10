

<%@ page import="nci.obbr.cahub.util.HelpFileUpload" %>
<g:set var="bodyclass" value="help fileupload create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'helpFileUpload.label', default: 'HelpFileUpload')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>  
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${helpFileUploadInstance}">
      <div class="errors">
        <g:renderErrors bean="${helpFileUploadInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form action="save" method="post" enctype="multipart/form-data" >

      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name" >
                <label for="fileName"><g:message code="helpFileUpload.myFile.label" default="Choose File:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'fileName', 'errors')}">
                <input type="file" name="fileName" id="fileName" size="125" value="${helpFileUploadInstance?.fileName}"/>   
              </td>
            </tr>


            <tr class="prop">
              <td valign="top" class="name">
                <label for="studyCode"><g:message code="helpFileUpload.studyCode.label" default="Study Code:" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'studyCode', 'errors')}">

              <g:select name="studyCode" from="${['BPV', 'GTEX', 'OBBR']}" value="${helpFileUploadInstance?.studyCode}" noSelection="['ALL': 'ALL STUDIES']" />
          </td>
          </tr>



          <tr class="prop">
            <td valign="top" class="name">
              <label for="comments"><g:message code="helpFileUpload.comments.label" default="Comments:" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: helpFileUploadInstance, field: 'comments', 'errors')}">
           <g:textArea class="textwide" cols="40" rows="5" name="comments" value="${helpFileUploadInstance?.comments}" />
          </td>
          </tr>





          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
