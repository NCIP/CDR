<%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdraw" %>
<g:set var="bodyclass" value="casewithdraw create" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'CaseWithdraw.label', default: 'Recall Request')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
  <div id="nav">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>

      <g:link  class="list" action="wdcmain" id="${caseWithdrawInstance?.caseRecord?.id}">Recall Main Page</g:link>
      <g:if test="${authority=='APR'}">

      </g:if>
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1><g:message code="default.create.label.with.case.id" args="[entityName,caseWithdrawInstance?.caseRecord]" /></h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <g:hasErrors bean="${caseWithdrawInstance}">
      <div class="errors">
        <g:renderErrors bean="${caseWithdrawInstance}" as="list" />
      </div>
    </g:hasErrors>

    <g:hasErrors bean="${caseWithdrawFormsInstance}">
      <div class="errors">
        <g:renderErrors bean="${caseWithdrawFormsInstance}" as="list" />
      </div>
    </g:hasErrors>

    <g:if test="${session.study.code.equals('GTEX')}">
      <g:render template="caseDetails" bean="${caseWithdrawInstance?.caseRecord}" var="caseRecord" />
    </g:if>
    <g:else><g:render template="/caseRecord/caseDetails" bean="${caseWithdrawInstance?.caseRecord}" var="caseRecord" /> </g:else>

    <g:form method="post" class="tdwrap tdtop" enctype="multipart/form-data">
      <g:hiddenField name="caseRecord.id" value="${caseWithdrawInstance?.caseRecord?.id}" />
      <g:hiddenField name="id" value="${caseWithdrawInstance?.caseRecord?.id}" />
      <g:hiddenField name="authority" value="${authority}" />
      <g:hiddenField name="wid" value="${caseWithdrawInstance.id}" />



      <div class="dialog">

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
        <g:elseif test="${authority=='ELR'}">
          <g:render template="finalSignatures" />
        </g:elseif>
        <g:elseif test="${authority=='OPS'}">
          <g:render template="finalSignatures" />
        </g:elseif>
        <g:elseif test="${authority=='QM'}">
          <g:render template="finalSignatures" />
        </g:elseif>
        <g:elseif test="${authority=='DIRECTOR'}">
          <g:render template="finalSignatures" />
        </g:elseif>
        <g:elseif test="${authority=='BSSCOMPLETE'}">
          <g:render template="fulfillRecall" />
        </g:elseif>

        <g:elseif test="${authority.contains('FL')}">
          <g:render template="entFileUpload" />

        </g:elseif>
        <g:else>
          <g:render template="formFieldsInc_bss" />
        </g:else>

      </div>

      <div class="buttons">

        <g:actionSubmit class="save" action="save" value="Save"  />
      <g:if test="${canSubmit=='Yes'}"><g:actionSubmit class="save" action="submitSignature" value="Submit"  /></g:if>
        <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>

     
        <g:if test="${!authority.contains('FL') && !authority.equals('BSS') && !authority.equals('APR')}">
        <g:actionSubmit class="save" action="entitiesFileUpload" value="Upload files"  />
        </g:if>
      </div>
    </g:form>

    <br>


   
<g:if test="${authority=='ELR' || authority=='QM' || authority=='OPS' || authority=='DIRECTOR'|| authority?.contains('CDR')|| authority?.contains('CBR')|| authority?.contains('PRC')|| authority?.contains('LDACC')|| authority?.contains('BRAINBANK')|| authority?.contains('BSSCOMPLETE')}">
        <g:if test="${fuploads}">
          <div class="list">
            <table>
              <thead>
                <tr>

                  <th>File Name</th>
                  <th class="dateentry">Date Uploaded</th>
                  <th> </th> 


                </tr>
              </thead>
              <tbody>
              <g:each in="${fuploads}" status="i" var="fileUploadInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

                <td>
                <g:link class="ui-button ui-state-default ui-corner-all removepadding" controller="caseWithdraw" action="removeFile" id="${fileUploadInstance.id}" params="[wid: caseWithdrawInstance.id,authority:authority]" onclick="return confirm('Are you sure to remove the file?')"><span class="ui-icon ui-icon-trash">Remove</span></g:link>
                </td>

                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
        </g:if>
    </g:if>

  </div>
</body>
</html>
