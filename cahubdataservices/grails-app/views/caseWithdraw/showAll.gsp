
<%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdraw" %>
<%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdrawForms" %>
<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.staticmembers.CaseAttachmentType" %>

<g:set var="bodyclass" value="CaseWithdraw show" scope="request"/>

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

    <div class="dialog" id="show">

      <br/>
      <g:render template="formFieldsInc_bss" />



      <br/>

      <g:render template="formFieldsInc_aprshow" />





      <g:if test="${CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CDR')}">
        <br/>
        <g:render template="formFieldsInc_cdr_view"  model="[caseWithdrawFormsInstance: CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CDR')]"/>

      </g:if>

      <g:if test="${CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CBR') && caseWithdrawInstance.assignToCBR}">
        <br/>
        <g:render template="formFieldsInc_cbr_view"  model="[caseWithdrawFormsInstance: CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CBR')]"/>
        <!-- show file uploads-->
        <g:if test="${fuploadcbr}">
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
              <g:each in="${fuploadcbr}" status="i" var="fileUploadInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
        </g:if>
        <!-- END show file uploads-->

      </g:if>

      <g:if test="${CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'LDACC') && caseWithdrawInstance.assignToLDACC}">
        <br/>
        <g:render template="formFieldsInc_ldacc_view"  model="[caseWithdrawFormsInstance: CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'LDACC')]"/>
        <!-- show file uploads-->
        <g:if test="${fuploadldacc}">
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
              <g:each in="${fuploadldacc}" status="i" var="fileUploadInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
        </g:if>
        <!-- END show file uploads-->



      </g:if>

      <g:if test="${CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'BRAINBANK') && caseWithdrawInstance.assignToBrainbank}">
        <br/>
        <g:render template="formFieldsInc_bb_view"  model="[caseWithdrawFormsInstance: CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'BRAINBANK')]"/>
        <!-- show file uploads-->
        <g:if test="${fuploadbrainbank}">
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
              <g:each in="${fuploadbrainbank}" status="i" var="fileUploadInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
        </g:if>
        <!-- END show file uploads-->

      </g:if>


      <g:if test="${CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'PRC') && caseWithdrawInstance.assignToPRC}">
        <br/>
        <g:render template="formFieldsInc_prc_view"  model="[caseWithdrawFormsInstance: CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'PRC')]"/>
        <!-- show file uploads-->
        <g:if test="${fuploadprc}">
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
              <g:each in="${fuploadprc}" status="i" var="fileUploadInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
                <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
        </g:if>
        <!-- END show file uploads-->


      </g:if>

      <br/>
      <g:render template="showFinalReview" />

      <br/>
      <g:render template="fulfillRecall_view" />

      <!-- show file uploads-->
      <g:if test="${fuploadcomplete}">
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
            <g:each in="${fuploadcomplete}" status="i" var="fileUploadInstance">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
              <td><nobr>${fileUploadInstance.uploadTime}</nobr></td>

              </tr>
            </g:each>
            </tbody>
          </table>
        </div>
      </g:if>
      <!-- END show file uploads-->

    </div>


  </div>
</body>
</html>
