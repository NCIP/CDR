
<%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdraw" %>
<g:set var="bodyclass" value="show" scope="request"/>

<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'CaseWithdraw.label', default: 'CaseWithdraw')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link  class="list" action="wdcmain" id="${caseWithdrawInstance?.caseRecord?.id}">Recall Main Page</g:link>

    </div>
  </div>
  <div id="container" class="clearfix">

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <g:if test="${session.study.code.equals('GTEX')}">
      <g:render template="caseDetails" bean="${caseWithdrawInstance?.caseRecord}" var="caseRecord" />
    </g:if>
    <g:else><g:render template="/caseRecord/caseDetails" bean="${caseWithdrawInstance?.caseRecord}" var="caseRecord" /> </g:else>

    <g:form action="post" class="tdwrap tdtop">
      <g:hiddenField name="caseRecord.id" value="${params.caseid}" />
      <g:hiddenField name="caseid" value="${params.caseid}" />
      <g:hiddenField name="authorityity" value="${authority}" />


      <div class="dialog" id="show">

        <g:if test="${authority=='BSS'}">
          <g:render template="formFieldsInc_bss" />
        </g:if>
        <g:elseif test="${authority=='CDR'}">
          <g:render template="formFieldsInc_cdr" />
        </g:elseif>
        <g:elseif test="${authority=='APR'}">
          <g:render template="formFieldsInc_apr" />
        </g:elseif>
        <g:elseif test="${authority=='CBR'}">
          <g:render template="formFieldsInc_clpb" />
        </g:elseif>
        <g:elseif test="${authority=='LDACC'}">
          <g:render template="formFieldsInc_clpb" />
        </g:elseif>
        <g:elseif test="${authority=='PRC'}">
          <g:render template="formFieldsInc_clpb" />
        </g:elseif>
        <g:elseif test="${authority=='BRAINBANK'}">
          <g:render template="formFieldsInc_clpb" />
        </g:elseif>
        <g:elseif test="${authority=='ELR' || authority=='QM' || authority=='OPS' || authority=='DIRECTOR' }">
          <h2>Section D: View Case Recall Verifications by ${authority}</h2>
          <g:if test="${authority=='ELR'}">
            <h3> Reviewer: ${caseWithdrawInstance.elrReviewer} &nbsp;&nbsp; Last Review Date: <g:formatDate date="${caseWithdrawInstance.dateELRReviewed}"/></h3>
          </g:if>
          <g:if test="${authority=='QM'}">
            <h3> Reviewer: ${caseWithdrawInstance.qmReviewer} &nbsp;&nbsp; Last Review Date: <g:formatDate date="${caseWithdrawInstance.dateQMReviewed}"/></h3>
          </g:if>
          <g:if test="${authority=='OPS'}">
            <h3> Reviewer: ${caseWithdrawInstance.opsReviewer} &nbsp;&nbsp; Last Review Date: <g:formatDate date="${caseWithdrawInstance.dateOPSReviewed}"/></h3>
          </g:if>
          <g:if test="${authority=='DIRECTOR'}">
            <h3> Reviewer: ${caseWithdrawInstance.directorReviewer} &nbsp;&nbsp; Last Review Date: <g:formatDate date="${caseWithdrawInstance.dateDirectorReviewed}"/></h3>
          </g:if>

        </g:elseif>
        <g:elseif test="${authority=='BSSCOMPLETE'}">
          <g:render template="fulfillRecall_view" />
        </g:elseif>
        <g:else>
          <g:render template="formFieldsInc_bss" />

        </g:else>

      </div>

    </g:form>

<g:if test="${authority=='ELR' || authority=='QM' || authority=='OPS' || authority=='DIRECTOR'||authority?.contains('CDR')|| authority?.contains('CBR')|| authority?.contains('PRC')|| authority?.contains('LDACC')|| authority?.contains('BRAINBANK')||authority?.contains('BSSCOMPLETE')}">
  
 <g:if test="${fuploads}">
    <div class="list">
      <table>
        <thead>
          <tr>
            <th>File Name</th>
            <th class="dateentrywide">Date Uploaded</th>
          </tr>
        </thead>
        <tbody>
        <g:each in="${fuploads}" status="i" var="fileUploadInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td class="name"><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
          <td class="value">${fileUploadInstance.uploadTime}</td>
          </tr>
        </g:each>
        </tbody>
      </table>
</g:if>
 </g:if>
  </div>

</body>
</html>
