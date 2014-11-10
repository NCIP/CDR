%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdraw" %>
%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdrawForms" %>
<g:set var="bodyclass" value="CaseWithdraw show" scope="request"/>
<g:set var="caseId" value="${caseWithdrawInstance?.caseRecord?.caseId}" />
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
      <!--  <g:link controller="caseWithdraw" class="list" action="requestRecallForm" >Request Recall Form</g:link>-->

    </div>
  </div>
  <div id="container" class="clearfix">

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <g:hasErrors bean="${caseWithdrawInstance}">
      <div class="errors">
        <g:renderErrors bean="${caseWithdrawInstance}" as="list" />
      </div>
    </g:hasErrors>
 
    <g:if test="${caseRecordInstanceList.size() ==0}">
      <h3>No Recalled Cases</h3>
    </g:if>
    <g:else>
     <h2>Show all Recall cases for ${session.study.code}</h2>
      <div class="list" id="show">
        <table>
          <tbody>

          <div class="dialog" id="show">
            <div>
              <table>

                <tr>

                <g:sortableColumn property="caseId" title="${message(code: 'caseRecord.caseId.label', default: 'Case ID')}" /> 
                <g:sortableColumn property="status" title="${message(code: 'caseRecord.caseId.label', default: 'Entities Forms Started')}" />  
                <g:sortableColumn property="status" title="${message(code: 'caseRecord.caseId.label', default: 'Recall Progress')}" />


                </tr>

                <tbody>



                <g:if test="${caseRecordInstanceList}">
                  <g:each in="${caseRecordInstanceList}" status="i" var="caseInstance">

                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      <td>
                    <g:displayCaseRecordLink caseRecord="${caseInstance}" session="${session}" action="wdcmain" controller="caseWithdraw" />
                    %{-- <g:link  action="wdcmain" id="${caseInstance.id}"> ${caseInstance.caseId}</g:link> --}%

                    </td>  


                    <td>

                    <g:if test="${fmap}">
                      <g:each in="${fmap.get(caseInstance.id)}" status="j" var="entity">
  ${entity.authority} form <g:checkBox name="myCheckbox" value="${caseInstance.id}" />
                      </g:each>
                    </g:if>

                    </td>


                    <td>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('BSS')}"> BSS Recall form submitted: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:else>BSS Recall form submitted: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.cahubApproveOrNot.equals('YES')}"> BSS Recall form approved: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:else> BSS Recall form approved: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('CDR')}"> CDR verified: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:elseif test="${caseInstance.caseWithdraw.assignToCDR}"> CDR verified: <g:checkBox name="myCheckbox" /> </g:elseif>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('CBR')}"> CBR verified: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:elseif test="${caseInstance.caseWithdraw.assignToCBR}"> CBR verified: <g:checkBox name="myCheckbox" /> </g:elseif>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('LDACC')}"> LDACC verified: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:elseif test="${caseInstance.caseWithdraw.assignToLDACC}">LDACC verified: <g:checkBox name="myCheckbox" /> </g:elseif>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('PRC')}"> PRC verified: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:elseif test="${caseInstance.caseWithdraw.assignToPRC}">PRC verified: <g:checkBox name="myCheckbox" /> </g:elseif>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('BRAINBANK')}">BRAIN BANK verified: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:elseif test="${caseInstance.caseWithdraw.assignToBrainbank}">BRAIN BANK verified: <g:checkBox name="myCheckbox" /> </g:elseif>
                    <br>
                    <i>Final review and completion:</i><br>
                    <g:if test="${caseInstance.caseWithdraw.dateDirectorReviewed}"> DIRECTOR signature: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.dateDirectorReviewed}" /> </g:if>
                    <g:else>DIRECTOR signature: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.dateELRReviewed}">  ELR signature: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.dateELRReviewed}" /> </g:if>
                    <g:else>ELR signature: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.dateOPSReviewed}"> OPS signature: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.dateOPSReviewed}" /> </g:if>
                    <g:else>OPS signature: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.dateQMReviewed}"> QM signature: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.dateQMReviewed}" /> </g:if>
                    <g:else>QM signature: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('COMPLETE')}"> Recall Completed: <g:checkBox name="myCheckbox" value="${caseInstance.caseWithdraw.caseStatus}" /> </g:if>
                    <g:else>Recall Completed: <g:checkBox name="myCheckbox" /> </g:else>
                    <br>
                    <g:if test="${caseInstance.caseWithdraw.caseStatus?.contains('CDR')|| caseInstance.caseWithdraw.caseStatus?.contains('CBR')|| caseInstance.caseWithdraw.caseStatus?.contains('PRC')|| caseInstance.caseWithdraw.caseStatus?.contains('LDACC')||caseInstance.caseWithdraw.caseStatus?.contains('BRAINBANK')}">

                      <g:link  class="list" action="showAll" id="${caseInstance.caseWithdraw?.id}">    ( View All forms)</g:link>
                    </g:if>
                    </td>    



                  </g:each>
                </g:if>

                </tbody>

              </table>


              </g:else>
            </div>

          </div>


          </body>
          </html>
