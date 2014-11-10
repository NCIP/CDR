
%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdraw" %>
<g:set var="bodyclass" value="CaseWithdraw" scope="request"/>
<g:set var="caseId" value="${caseWithdrawInstance?.caseRecord?.caseId}" />
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'CaseWithdraw.label', default: 'CaseWithdraw')}" />

  <title><g:message code="default.show.label" args="[entityName]" /></title>

  <g:javascript>
    function setID(){

    document.getElementById("id").value=document.getElementById("cid").value
    //alert(document.getElementById("id").value)
    }


  </g:javascript>

</head>


<body>
  <div id="nav">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link controller="caseWithdraw" class="list" action="listAllRecallCases" >Case Recall Home</g:link>

    </div>
  </div>
  <div id="container" class="clearfix">




    <h2>Request for Withdrawal</h2>

    <g:form action="save" >

      <table>

        <tr >
          <td valign="top" class="name">
            <label for="caseCollectionType">Select Case ID to withdraw:</label>
          </td>
          <td valign="top" >
        <g:if test="${caseRecordInstanceList.size()== 0}"> Sorry.. there are no cases to display. </g:if>
        <g:else>
          <g:select id="cid" name="cid" from="${caseRecordInstanceList}" optionKey="id"   noSelection="['': '']" />
        </g:else>

        </td>

        </tr>




      </table>
      <div class="buttons">
        <g:hiddenField name="id" id="id" />
        <g:if test="${caseRecordInstanceList.size()> 0}">

          <g:actionSubmit  action="wdcmain" value="Click here to request a case withdrawal"  onclick="setID()"/>
        </g:if>
    </g:form>
  </div>


</body>
</html>




