<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueProcessEmbed" %>
<g:set var="bodyclass" value="bpvtissueprocess list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName}"/>
        <title><g:message code="default.list.label" args="[entityName]"/></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]"/></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'bpvTissueProcessEmbed.id.label', default: 'Id')}"/>
                            <th><g:message code="bpvTissueProcessEmbedInstance.caseRecord.label" default="Case Record"/></th>
                            <g:sortableColumn property="caseRecord.tissueBankId" title="Tissue Bank ID"/>
                            <g:sortableColumn property="expKeyBarcodeId" title="Experimental Key Barcode ID"/>
                            <g:sortableColumn property="parentBarcodeId" title="Parent Sample Barcode ID"/>
                            <g:sortableColumn property="processingSop" title="Processing SOP"/>
                            <g:sortableColumn property="tissProcessorMdl" title="Make and Model of Tissue Processor"/>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${bpvTissueProcessEmbedInstanceList}" status="i" var="bpvTissueProcessEmbedInstance">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td><g:link action="show" id="${bpvTissueProcessEmbedInstance.id}">${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "id")}</g:link></td>
                                <td>${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "caseRecord")}</td>
                                <td>${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "caseRecord.tissueBankId")}</td>
                                <td>${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "expKeyBarcodeId")}</td>
                                <td>${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "parentBarcodeId")}</td>
                                <td>${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "processingSop")}</td>
                                <td>${fieldValue(bean: bpvTissueProcessEmbedInstance, field: "tissProcessorMdl")}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvTissueProcessEmbedInstanceTotal}"/>
            </div>
        </div>
    </body>
</html>
