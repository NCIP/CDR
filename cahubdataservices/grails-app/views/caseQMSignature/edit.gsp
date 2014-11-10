<%@ page import="nci.obbr.cahub.datarecords.qm.CaseQMSignature" %>
<g:set var="bodyclass" value="caseqmsignature edit" scope="request"/>

<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="Case QM Signature" />
                
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        
        <script type="text/javascript">
            function unsign()
            {
                var check = confirm("Are you sure to unsign QM signature on this case?")
                if (check)
                {
                    document.location.href = "/cahubdataservices/caseQMSignature/sign/${caseRecordInstance.caseId}?signingmode=unsign"
                }
            }
            function changeUserId()
            {
                var currentUser = '${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}'
                var originalVal = '${selfInstance?.userId ? selfInstance.userId : 'N/A'}'
                var buttonVal_ori = 'Set back to the original value'
                var buttonVal_curr = 'Change to the current user ID'
                var currButton = document.getElementById('changeButton').value
                
                if (currButton == buttonVal_curr)
                {
                    document.getElementById('signedUserId').value = currentUser
                    document.getElementById('changeButton').value = buttonVal_ori
                }
                if (currButton == buttonVal_ori)
                {
                    document.getElementById('signedUserId').value = originalVal
                    document.getElementById('changeButton').value = buttonVal_curr
                }
            }
            function setToday()
            {
                var signDate = '${ ((selfInstance == null)|| (selfInstance.signedDate == null)) ? '' : (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(selfInstance.signedDate)}'
                var todayDate = '${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date()) }'
                
                if (signDate == '')
                {
                    document.getElementById('signedDate').value = todayDate
                    document.getElementById('changeButton').style.visibility = 'hidden'
                }
                else if (signDate != todayDate)
                {
                    var buttonVal_ori = 'Set back to the original Date'
                    var buttonVal_curr = 'Change to Today Date'
                    var currButton = document.getElementById('changeDateButton').value

                    if (currButton == buttonVal_curr)
                    {
                        document.getElementById('signedDate').value = todayDate
                        document.getElementById('changeDateButton').value = buttonVal_ori
                    }
                    if (currButton == buttonVal_ori)
                    {
                        document.getElementById('signedDate').value = signDate
                        document.getElementById('changeDateButton').value = buttonVal_curr
                    }
                }
            }
            function pressSubmitButton2()
            {
                var dateVal = document.getElementById('signedDate').value
                var commentVal = document.getElementById('comment').value
                
                if ((dateVal == null)||(dateVal == ''))
                {
                    alert('Sign Date is not input yet.')
                    return false
                }
                
                return true
            }

        </script> 
        <script type="text/javascript">
            var todayDate = '${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date()) }'
            $(document).ready(function() {     
                
                $("#signButton").click(function() {
                     
                    return pressSubmitButton2()
//                    if (pressSubmitButton2())
//                    {  
//                        var val = $("#signButton").val()
//                        
//                        if (val == 'Update') var msg = "Are you sure to update QM sign on this case?" 
//                        else var msg = "Are you sure to sign QM signature on this case?"
//                        
//                        return confirm(msg)
//                    }
//                    else return false
                    
                });  
                $("#changeDateButton2").click(function() {
                    $("#signedDate").val(todayDate)
                    $("#changeDateButton2_span").hide()
                });   
                $("#editButton").click(function() {
                       
                    var signDate = '${ selfInstance?.signedDate ? (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(selfInstance.signedDate) : '' }'
                    $("#signedDate_text").hide()
                    $("#signedDate_input").show()
                    if ( signDate != todayDate)
                    {
                        $("#changeDateButton2_span").show()
                        $("#signedDate").val(signDate) 
                    }
                    else $("#changeDateButton2_span").hide()
                    $("#comment_text").hide()
                    $("#comment_input").show()
                    $("#editButton_span").hide()
                    $("#unsignButton_span").hide()
                    $("#summitButton_span").show()
                    $("#cancelButton_span").show()
                    
                });    
                $("#cancelButton").click(function() {
                    var signDate = '${ selfInstance?.signedDate ? (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(selfInstance.signedDate) : '' }'
                    $("#signedDate").val(signDate) 
                    $("#signedDate_text").show()
                    $("#signedDate_input").hide()
                    $("#changeDateButton2_span").hide()
                    $("#comment_text").show()
                    $("#comment_input").hide()
                    $("#editButton_span").show()
                    $("#unsignButton_span").show()
                    $("#summitButton_span").hide()
                    $("#cancelButton_span").hide()
                    
                });   
                $("#addButton").click(function() {
                    
                    $("#new_insert").show()
                    $("#addButton_span").hide()
                    $("#comment").val('')
                });
                $("#cancelButton2").click(function() {
                    $("#new_insert").hide()
                    $("#addButton_span").show()
                });   
            });
        </script> 
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            %{-- <a class="home" href="${createLink(uri: '/caseRecord/show') + '/' + caseRecordInstance.id}">Case Home</a> --}%
        </div>
      </div>
      <div id="container" class="clearfix">
        <g:if test="${message}" >
          <g:if test="${result}" >
            <div class="message">${message}</div>
          </g:if>
          <g:else>
            <div class="errors"><font color="red"><b>${message}</b></font></div>
          </g:else>
        </g:if>
        <g:hasErrors bean="${caseQMSignatureInstances}">
          <div class="errors">
            <g:renderErrors bean="${caseQMSignatureInstances}" as="list" />
          </div>
        </g:hasErrors>
        %{-- <h1>${caseQMSignatureInstance?.userId ? 'Edit' : 'Create'} QM Signature for Case Record ${caseRecordInstance.caseId}</h1><br> --}%  
        <h1>QM Signature for Case Record ${caseRecordInstance.caseId}</h1><br>
        
        <div class="dialog" id="signing">
            <g:render template="/caseRecord/caseDetails" bean="${caseRecordInstance}" var="caseRecord" />
            
            <form action='/cahubdataservices/caseQMSignature/sign/${caseRecordInstance.id}' method='POST' id='caseQMSignatureInput'>
              <input type="hidden" name="caseRecordId" id="caseRecordId" value="${caseRecordInstance.id}" />
              <input type="hidden" name="signedUserId" id="signedUserId" value="${ selfInstance?.userId ? selfInstance.userId : session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername() }" />
              <g:if test="${ caseQMSignatureInstances }">
                
                <table>
                 <%-- <th colspan="2"><h3>Signature</h3></th>--%>
                  <g:each in="${caseQMSignatureInstances}" status="i" var="instance">
                    <tr class="prop">
                      <td class="name">Signer's ID</td>
                      <td class="value">${instance.userId}</td>
                    </tr>
                      
                    <tr class="prop">
                      <td class="name">Date of Signing</td>
                      <td class="value">
                        <span id="signedDate_text" style="display: dispaly">${(new java.text.SimpleDateFormat("yyyy-MM-dd")).format(instance.signedDate)}</span>
                        <span id="signedDate_input" style="display: none">
                          <g:if test="${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(instance.signedDate).equals((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date())) }">
                            <input type="hidden" name="signedDate" id="signedDate" value="${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date())}" />
                            ${(new java.text.SimpleDateFormat("yyyy-MM-dd")).format(instance.signedDate)}
                          </g:if> 
                          <g:else>
                            <g:textField id="signedDate" name="signedDate" value="${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(instance.signedDate)}" readonly="true"/>
                          </g:else>  
                        </span>
                        <span class="button" id="changeDateButton2_span" style="display: none"><input type="button" id="changeDateButton2" value="Set Today Date"></input></span>
                      </td>
                    </tr>
                    <tr class="prop">
                      <td class="name">Comment</td>
                      <td class="value">
                        <span id="comment_text" style="display: dispaly">
                          <%
                           String cr = instance.comments ? instance.comments : ' '
                           String bc = ''
                           for(int j=0;j<cr.length();j++)
                           {
                               String achar = cr.substring(j, j+1)
                               if (achar.equals('\t')) achar = ' '
                               String disp = ''
                               if (achar.equals('\n')) disp = '<br>'
                               else if (achar.equals('\r')) disp = ''
                               else if (achar.equals('<')) disp = '&lt;'
                               else if (achar.equals('>')) disp = '&gt;'
                               else if (achar.equals('&')) disp = '&amp;'
                               else if (achar.equals(' '))
                               {
                                   if (bc.equals(' ')) disp = '&nbsp;'
                                   else disp = ' '
                               }
                               else disp = achar
                               %>${disp}<%
                               bc = achar
                           }
                        %>

                        </span>
                        <span id="comment_input" style="display: none">
                          <g:textArea name="comment" id="comment" cols="80" rows="4" value="${ instance.comments ? instance.comments : '' }" />
                        </span>
                      </td>
                    </tr>
                    
                  </g:each>
                </table>
                <g:if test="${ (selfInstance) }">
                  
                  <div class="buttons">
                    <input type="hidden" name="signingmode" id="signingmode" value="edit" /> 
                    <span id="editButton_span" class="button" style="display: dispaly"><input class="edit" type="button" name="editButton" value="Edit" id="editButton"/></span>
                    <span id="unsignButton_span" class="button" style="display: dispaly"><input class="delete" type="button" value="Unsign" onclick="unsign()"></input></span>
                    <span id="summitButton_span" class="button" style="display: none"><input type="submit" name="signButton" class="save" value="Update" id="signButton"/></span>
                    <span id="cancelButton_span" class="button" style="display: none"><input class="cancel" type="button" value="Cancel" id="cancelButton"></input></span>
                  </div>
                  
                </g:if>
              </g:if>
              <g:else>
                <!--h3>No Signature currently</h3><br-->
                <div id="new_insert" style="display: display">
                  <!--h4>You can put your QM signature on this case via 'Sign' button. </h-->
                  
                  <table>
                    %{--
                    <tr class="prop">
                      <td class="name">Signer</td>
                      <td class="value">${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}</td>
                    </tr>
                    <tr class="prop">
                      <td class="name">Date of Signing</td>
                      <input type="hidden" name="signedDate" id="signedDate" value="${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date())}" />
                      <td class="value">${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date()) }</td>
                    </tr>
                      --}%
                    <input type="hidden" name="signedDate" id="signedDate" value="${ (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date())}" />
                    <tr class="prop">
                      <td class="name">Comment</td>
                      <td class="value"><g:textArea name="comment" id="comment" cols="80" rows="4" value="${ CaseQMSignature.findByCaseRecord(caseRecordInstance) ? CaseQMSignature.findByCaseRecord(caseRecordInstance).comments : '' }" /></td>
                    </tr>
                  </table>
                  <div class="buttons">
                    <input type="hidden" name="signingmode" id="signingmode" value="sign" /> 
                    <span id="summitButton_span" class="button"><input type="submit" name="signButton" class="create" value="Sign" id="signButton"/></span>
                  </div>
                </div>  
                <div class="buttons" id="addButton_span" style="display: ${ selfInstance ? 'dispaly' : 'none'}">
                  <h3>No Signature</h3>
                  <span class="button" ><input type="button" name="addButton" class="add" value="Add Sign" id="addButton"/></span>
                </div>
              </g:else>
            </form>
            
        </div>
      </div>
   </body>
</html>
