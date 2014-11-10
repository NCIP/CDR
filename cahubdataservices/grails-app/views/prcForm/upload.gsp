

<%@ page import="nci.obbr.cahub.prctumor.PrcForm" %>
<g:set var="bodyclass" value="prcform upload" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'prcForm.label', default: 'PrcForm')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>  
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
          <g:if test="${cat == 'F'}">
            <h1>Upload Final Surgical Review File</h1>
          </g:if>
           <g:if test="${cat == 'L'}">
            <h1>Upload Local Pathology Review File </h1>
          </g:if>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
           
            <g:form method="post" enctype="multipart/form-data" >
              <g:if test="${cat=='F'}">
                 <g:hiddenField name="id" value="${caseRecordInstance?.id}" />
                 <g:hiddenField name="version" value="${caseRecordInstance?.version}" />
              </g:if>
              <g:else>
                <g:hiddenField name="id" value="${slideRecordInstance?.id}" />
                <g:hiddenField name="version" value="${slideRecordInstance?.version}" />
              </g:else>
              <g:hiddenField name="cat" value="${cat}" />
              
              <div class="dialog">
                  <table>
                    <tbody>
                    <g:if test="${cat=='F'}">
                       <tr class="prop">
                            <td valign="top" class="name">Case Id:</td>
                            
                            <td valign="top" class="value">${caseRecordInstance?.caseId}</td>
                            
                        </tr>
                    </g:if>
                    <g:else>
                        <tr class="prop">
                            <td valign="top" class="name">Slide:</td>
                            
                            <td valign="top" class="value">${slideRecordInstance?.slideId}</td>
                            
                        </tr>
                    </g:else>
                        <tr class="prop">
                            <td valign="top" class="name">File:</td>
                            
                            <td valign="top" class="value"><input type="file" name="filepath" /></td>
                            
                        </tr>
                    </tbody>
                  </table>
              </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="upload_save" value="upload" /></span>
                      
                </div>
            </g:form>
        </div>
    </div>
    </body>
</html>
