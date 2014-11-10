
<%@ page import="nci.obbr.cahub.forms.bpv.clinicaldataentry.BpvClinicalDataEntry" %>
<g:set var="bodyclass" value="bpvclinicaldata list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvClinicalDataEntryInstance?.formMetadata?.cdrFormName}"/>
        <title><g:message code="default.list.label" args="[entityName]"/></title>
        <g:if test="${env != 'production'}">
          <%-- cache buster--%>
          <g:set var="d" value="${new Date()}" />
          <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
        </g:if>      
        <script type="text/javascript" src="${resource(dir:'js/bpv',file:'clinical-data-entry.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script> 
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
                            <g:sortableColumn property="id" title="${message(code: 'bpvClinicalDataEntry.id.label', default: 'Id')}"/>
                            <th><g:message code="bpvClinicalDataEntryInstance.caseRecord.label" default="Case Record"/></th>
                            <g:sortableColumn property="caseRecord.tissueBankId" title="Tissue Bank ID"/>
                            <g:sortableColumn property="prevMalignancy" title="Prior Malignancy"/>
                            <g:sortableColumn property="prevCancerDiagDt" title="Previous Cancer Diagnosis Date"/>
                            <g:sortableColumn property="bloodRelCancer1" title="Blood relatives with Cancer"/>
                            <g:sortableColumn property="bloodRelCancer2" title="Immuno Suppressive Status"/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvClinicalDataEntryInstanceList}" status="i" var="bpvClinicalDataEntryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${bpvClinicalDataEntryInstance.id}">${fieldValue(bean: bpvClinicalDataEntryInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: bpvClinicalDataEntryInstance, field: "caseRecord")}</td>
                            <td>${fieldValue(bean: bpvClinicalDataEntryInstance, field: "caseRecord.tissueBankId")}</td>
                            <td>${fieldValue(bean: bpvClinicalDataEntryInstance, field: "prevMalignancy")}</td>
                            <td><g:formatDate date="${bpvClinicalDataEntryInstance.prevCancerDiagDt}"/></td>
                            <td>${fieldValue(bean: bpvClinicalDataEntryInstance, field: "bloodRelCancer1")}</td>
                            <td>${fieldValue(bean: bpvClinicalDataEntryInstance, field: "immunoSuppStatus1")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvClinicalDataEntryInstanceTotal}"/>
            </div>
        </div>
    </body>
</html>
