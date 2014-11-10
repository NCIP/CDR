

<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryBrain" %>
<g:set var="bodyclass" value="tissuerecoverybrain" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'tissueRecoveryBrain.label', default: 'TissueRecoveryBrain')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
         
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Upload Broad Spreadsheet</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tissueRecoveryBrainInstance}">
            <div class="errors">
                <g:renderErrors bean="${tissueRecoveryBrainInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:render template="caseDetails" bean="${tissueRecoveryBrainInstance.caseRecord}" var="caseRecord" />
            <g:form action="save"  method="post" enctype="multipart/form-data">
               <input type="hidden" name="caseRecord.id" value="${tissueRecoveryBrainInstance?.caseRecord?.id}"  />
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
                        
                          
                        
                          
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Upload" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
