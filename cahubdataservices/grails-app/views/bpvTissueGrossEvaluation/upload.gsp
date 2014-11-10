<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueGrossEvaluation" %>
<g:set var="bodyclass" value="bpvtissuegrosseval upload bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'bpvTissueGrossEvaluation.label', default: 'BpvTissueGrossEvaluation')}" />
        <title><g:message code="default.upload.label" args="[entityName]" /></title>
    </head>
    <body>
     <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Upload tissue photograph</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvTissueGrossEvaluationInstance}">
            <div class="errors">
                <g:renderErrors bean="${bpvTissueGrossEvaluationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:warnings warningmap="${warningMap}" />
            <g:form method="post" enctype="multipart/form-data" >
                <g:hiddenField name="id" value="${bpvTissueGrossEvaluationInstance?.id}" />
                <g:hiddenField name="version" value="${bpvTissueGrossEvaluationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">Case ID:</td>
                                <td valign="top" class="value">
                                    <g:link controller="caseRecord" action="display" id="${bpvTissueGrossEvaluationInstance.caseRecord.id}">${bpvTissueGrossEvaluationInstance.caseRecord.caseId}</g:link>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">File:</td>
                                <td valign="top" class="value"><input type="file" name="filepath" size="125" /></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="upload_save" value="Upload" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
