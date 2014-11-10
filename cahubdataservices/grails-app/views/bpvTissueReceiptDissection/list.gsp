<%@ page import="nci.obbr.cahub.forms.bpv.BpvTissueReceiptDissection" %>
<g:set var="bodyclass" value="bpvtissuereceipt create bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName}"/>
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
                            <g:sortableColumn property="id" title="${message(code: 'bpvTissueReceiptDissection.id.label', default: 'Id')}"/>
                            <th><g:message code="bpvTissueReceiptDissection.caseRecord.label" default="Case Record"/></th>
                            <g:sortableColumn property="caseRecord.tissueBankId" title="${message(code: 'bpvTissueReceiptDissection.caseRecord.tissueBankId.label', default: 'Tissue Bank ID')}"/>
                            <g:sortableColumn property="bloodSamplesCollected" title="${message(code: 'bpvTissueReceiptDissection.bloodSamplesCollected.label', default: 'Blood Samples Collected')}"/>
                            <g:sortableColumn property="dateTimeTissueReceived" title="${message(code: 'bpvTissueReceiptDissection.dateTimeTissueReceived.label', default: 'Date Received')}"/>
                            <g:sortableColumn property="tissueRecipient" title="${message(code: 'bpvTissueReceiptDissection.tissueRecipient.label', default: 'Received By')}"/>
                            <g:sortableColumn property="dateSubmitted" title="${message(code: 'bpvTissueReceiptDissection.dateSubmitted.label', default: 'Date Submitted')}"/>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${bpvTissueReceiptDissectionInstanceList}" status="i" var="bpvTissueReceiptDissectionInstance">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td><g:link action="show" id="${bpvTissueReceiptDissectionInstance.id}">${fieldValue(bean: bpvTissueReceiptDissectionInstance, field: "id")}</g:link></td>
                                <td>${fieldValue(bean: bpvTissueReceiptDissectionInstance, field: "caseRecord")}</td>
                                <td>${fieldValue(bean: bpvTissueReceiptDissectionInstance, field: "caseRecord.tissueBankId")}</td>
                                <td>${fieldValue(bean: bpvTissueReceiptDissectionInstance, field: "bloodSamplesCollected")}</td>
                                <td><g:formatDate format="MM/dd/yyyy" date="${bpvTissueReceiptDissectionInstance.dateTimeTissueReceived}"/></td>
                                <td>${fieldValue(bean: bpvTissueReceiptDissectionInstance, field: "tissueRecipient")}</td>
                                <td><g:formatDate format="MM/dd/yyyy" date="${bpvTissueReceiptDissectionInstance.dateSubmitted}"/></td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvTissueReceiptDissectionInstanceTotal}"/>
            </div>
        </div>
    </body>
</html>
