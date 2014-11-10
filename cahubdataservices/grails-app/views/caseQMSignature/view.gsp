<%@ page import="nci.obbr.cahub.datarecords.qm.CaseQMSignature" %>
<g:set var="bodyclass" value="caseqmsignature view" scope="request"/>
<g:if test="${session.study.code == 'BPV' || session.study.code == 'BRN'}">
  <g:set var="bodyclass" value="caseqmsignature view wide" scope="request"/>
</g:if>

<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="Case QM Signature" />
        
        <title><g:message code="default.view.label" args="[entityName]" /></title>
        
        <script type="text/javascript">
            
            function startEdit()
            {
                var check = confirm("Are you sure to edit this QM signature?")
                if (check)
                {
                    document.location.href = "/cahubdataservices/caseQMSignature/edit/${caseQMSignatureInstance.caseRecord.id}"
                }
            }
        </script> 
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <a class="home" href="${createLink(uri: '/caseRecord/show') + '/' + caseQMSignatureInstance.caseRecord.id}">Case Home</a>
        </div>
      </div>
      <div id="container" class="clearfix">
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <h1>View QA Signature for Case Record ${caseQMSignatureInstance.caseRecord.caseId}</h1><br>
        <div class="dialog" id="signing">

              <table>
                <tr height="20" class="prop">
                  <td valign="top" class="name"><label for="caseRecordId">Case Record ID:</label><br></td>
                  <td valign="top" class="value">${caseQMSignatureInstance.caseRecord.caseId}</td>
                </tr>
                <tr height="20" class="prop">
                  <td valign="top" class="name"><label for="signedDate">QM Sign Date:</label><br></td>
                  <td valign="top" class="value">${ caseQMSignatureInstance?.signedDate ? (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(caseQMSignatureInstance.signedDate) : '' }</td>
                </tr>
                <tr height="20" class="prop">
                  <td valign="top" class="name"><label for="signedUserId">Signer's ID:</label><br></td>
                  <td valign="top" class="value">${ caseQMSignatureInstance?.userId ? caseQMSignatureInstance.userId : '' }</td> 
                </tr>
                <tr height="20" class="prop">
                  <td valign="top" class="name"><label for="comment">Comment:</label><br></td>
                  <td valign="top" class="value">${ caseQMSignatureInstance?.comments ? caseQMSignatureInstance.comments : '' }</td>  
                </tr>

              </table> 
              <g:if test="${ withEditButton }">
                <span class="button"><input type="button" class="edit" value="Edit" onclick="startEdit()"/></span>
              </g:if>

        </div>
      </div>
        
    </body>
</html>
