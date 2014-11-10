

<%@ page import="nci.obbr.cahub.util.querytracker.Deviation" %>
<g:set var="bodyclass" value="deviation upload" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'deviation.label', default: 'Deviation')}" />
        <title><g:message code="default.upload.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Upload documentation of deviation</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviationInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" enctype="multipart/form-data" >
                <g:hiddenField name="id" value="${deviationInstance?.id}" />
                <g:hiddenField name="version" value="${deviationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                          
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="filepath">File:</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="file" name="filepath" size="125" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="category.id">Category:</label>
                                </td>
                                <td valign="top" >
                                    <g:select name="category.id" from="${gencatList}" optionKey="id" noSelection="['null': 'Select one']" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comments">Comments:</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:textArea class="textwide" name="comments" cols="40" rows="5" />
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="upload_save" value="Upload" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="window.location.href='${createLink(uri: '/')}${params.controller}/show/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

