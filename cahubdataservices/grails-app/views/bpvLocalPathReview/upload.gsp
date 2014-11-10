<%@ page import="nci.obbr.cahub.forms.bpv.BpvLocalPathReview" %>
<%@ page import="nci.obbr.cahub.staticmembers.FormMetadata" %>
<g:set var="bodyclass" value="bpvlocalpathreview upload bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${FormMetadata.findByCode('FINAL')?.cdrFormName}" />
        <title><g:message code="default.upload.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.upload.label.with.case.id" args="[entityName,caseRecordInstance?.caseId]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${caseRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${caseRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" enctype="multipart/form-data" >
                <g:hiddenField name="id" value="${caseRecordInstance?.id}" />
                <g:hiddenField name="version" value="${caseRecordInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">Case ID:</td>
                                <td valign="top" class="value">
                                    <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">File:</td>
                                <td valign="top" class="value"><input type="file" name="filepath" size="125"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="upload_save" value="Upload" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="window.location.href='${createLink(uri: '/')}caseRecord/display/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
