<%@ page import="nci.obbr.cahub.util.pogo.AuditLogEventUnitRecord" %>
<g:set var="bodyclass" value="auditlogtrail" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'auditLogEvent.label', default: 'AuditLogTrail')}" />
        <g:set var="seq" value="${message(code: 'auditLogEvent.label', default: 'AuditLogTrail')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <script language="javascript">
            function buttonRefresh(theForm)
            {
                var url = theForm.refreshUrl.value;
                // checkbox value 0=unchecked, 1=checked, 2=checkbox disabled  
                var caseRecordC = '0';
                var caseRecordT = ${ (params.caserecord) };
                if (caseRecordT != 2)
                {
                    if (theForm.caserecordcheck.checked == true) caseRecordC = '1';
                }
                
                // checkbox value 0=unchecked, 1=checked, 2=checkbox disabled 
                var candidateRecordC = '0';
                var candidateRecordT = ${ (params.candidaterecord) };
                if (candidateRecordT != 2)
                {
                    if (theForm.candidaterecordcheck.checked == true) candidateRecordC = '1';
                }
                // checkbox value 0=unchecked, 1=checked, 2=checkbox disabled 
                var specimensC = '0';
                var specimensT = ${ (params.specimens) };
                if (specimensT != 2)
                {
                    if (theForm.specimenscheck.checked == true) specimensC = '1';
                }
                var paramSort = '${ (params.sort) }';
                var paramOrder = '${ (params.order) }';
                
                var childforms2 = ''
                 
                <%
                    def separator = "-";
                    if (params.sort)
                    {
                        params.bsort = params.sort
                        if (params.order) params.bsort = params.sort + separator + params.order
                    }
                    
                    if (childForms)
                    {
                        // checkbox value for child forms X=unchecked, O=checked, D=checkbox disabled 
                        for (int j=0;j<childForms.size();j++)
                        {
                %>
                            if (theForm.childForm${j}.checked == true) childforms2 = childforms2 + 'O'
                            else childforms2 = childforms2 + 'X'
                <%
                        }
                    }
                %>
                
                                   
                var i = 0;
                var seq = 0;
                var form = '';
                var action = '';
                var id = '';
                var buff = '';
                var url0 = url + '/'
                for(i=0;i<url0.length;i++)
                {
                    var achar = url0.substring(i, i+1);
                    if (achar == '/')
                    {
                        if (i > 0) 
                        {
                            if (buff != '')
                            {
                                if (seq == 0) form = buff;
                                else if (seq == 1) action = buff;
                                else if (seq == 2) id = buff;
                                else
                                {
                                    seq = -1;
                                    break;
                                }
                                seq++; 
                            }
                        }
                        buff = '';
                    }
                    else
                    {
                        buff = buff + achar;
                    }
                }
                var separator = '${ (separator) }';
                var url3 = '../../trailAuditLog/trailLog/' + form + separator + action + separator + id;
                var url2 = url3 + '?caserecord=' + caseRecordC + '&candidaterecord=' + candidateRecordC + '&specimens=' + specimensC;
                if (childforms2 != '') url2 = url2 + '&childforms=' + childforms2;
                if (paramSort != '')
                {
                    url2 = url2 + '&sort=' + paramSort;
                    if (paramOrder != '') url2 = url2 + '&order=' + paramOrder;
                }
                
                //alert(url2);
                window.location.href = url2
            }
        </script>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <a class="goback" href="${createLink(uri: goBackUrl)}">Go Back</a>
            <g:if test='${ (auditLogEventInstanceTotal > 0) }'>
                <span class="list cprint">Print</span>
            </g:if>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
            <g:if test='${ (className) }'>
	                    <div class="trailparameters"><b>Searching parameter:</b><br/>Class Name=${ className.substring(0,1).toUpperCase() + className.substring(1) }<br/>Object ID=${objectId}
	                        <div class="trailcount"><b>Total Count:</b> ${auditLogEventInstanceTotal}</div></div>
            </g:if>
            <g:if test='${ (auditLogEventInstanceTotal > 0) }'>
                <div>
                    <form name="searchOptions" id="searchOptions">
                        <div class="clear"></div>
                        <div class="left">
                      		<input type="button" name="refresh" id="refresh" value="Refresh" onClick="buttonRefresh(this.form)" />
                    	</div>
                    	<div class="clear"></div>
                        %{--  checkbox value 0=unchecked, 1=checked, 2=checkbox disabled  --}%
                        <g:if test='${ (params.caserecord < 2)||(params.candidaterecord < 2)||(params.specimens < 2) }'>
                            <div class="left">
                              <h3>Options: </h3> 
                              <g:if test='${ (params.caserecord < 2) }'>
                                  <g:checkBox id ="caserecordcheck" name="caserecordcheck" value="Case Record" checked="${(params.caserecord == 1)}" /><label for="caserecordcheck">Case Record</label>
                              </g:if>
                              <g:if test='${ (params.candidaterecord < 2) }'>
                                  <g:checkBox id ="candidaterecordcheck" name="candidaterecordcheck" value="Candidate Record" checked="${(params.candidaterecord == 1)}" /><label for="candidaterecordcheck">Candidate Record</label>
                              </g:if>
                              <g:if test='${ (params.specimens < 2) }'>
                                  <g:checkBox id ="specimenscheck" name="specimenscheck" value="Specimens" checked="${(params.specimens == 1)}" /><label for="specimenscheck">Specimens</label>
                              </g:if>
                            </div>
                        </g:if>
                    	
                        <g:if test='${ childForms }'>
                            <div class="left">
                                <h3>Child Forms: </h3> 
                                %{--  checkbox value for child forms X=unchecked, O=checked, D=checkbox disabled  --}%
                                <g:each in="${childForms}" status="i" var="childForm">
                                    <g:if test="${(params.childforms.substring(i, i+1).equalsIgnoreCase('O'))}">
                                        <input type="checkbox" id ="childForm${i}" name="childForm${i}" value="${childForm}" checked="checked"><label for="childForm${i}">${childForm}</label>
                                    </g:if>
                                    <g:elseif test="${(params.childforms.substring(i, i+1).equalsIgnoreCase('X'))}">
                                        <input type="checkbox" id ="childForm${i}" name="childForm${i}" value="${childForm}"><label for="childForm${i}">${childForm}</label>
                                    </g:elseif>
                                    <g:elseif test="${(params.childforms.substring(i, i+1).equalsIgnoreCase('D'))}">
                                        <input type="checkbox" id ="childForm${i}" name="childForm${i}" value="${childForm}" disabled="disabled"><label for="childForm${i}">${childForm}</label>
                                    </g:elseif>
                                </g:each>
                            </div>
                        </g:if>
           
                    	<div class="clear"></div>
                        <input type="hidden" name="refreshUrl" id="refreshUrl" value="${goBackUrl}" />
                        
                    </form>
                
            </g:if>
            <g:else>
                <div id="pageErrs" class="errors"><font color="red"><b>Null Result or Error: ${errorMessage}</b></font></div>
            </g:else>

            <div class="list">
                <table>
                    <thead>
                        <tr>
 
                            <th>Seq.#</th>
                            
                            <g:sortableColumn class="dateentry" property="dateCreated" title="${message(code: 'auditLogEvent.dateCreated.label', default: 'Created Date')}" params="${params}" /> 
                            <g:sortableColumn class="dateentry" property="lastUpdated" title="${message(code: 'auditLogEvent.lastUpdated.label', default: 'Last Updated')}" params="${params}" />
                            <g:sortableColumn property="actor" title="${message(code: 'auditLogEvent.actor.label', default: 'Actor')}" params="${params}" />
                            <g:sortableColumn property="eventName" title="${message(code: 'auditLogEvent.eventName.label', default: 'Event Name')}" params="${params}" />
                            
                            <g:sortableColumn property="className" title="${message(code: 'auditLogEvent.className.label', default: 'Class Name')}" params="${params}" />
                            <g:sortableColumn property="persistedObjectId" title="${message(code: 'auditLogEvent.persistedObjectId.label', default: 'ID')}" params="${params}" />
                            <g:sortableColumn property="propertyName" title="${message(code: 'auditLogEvent.propertyName.label', default: 'Property Name')}" params="${params}" />
                            
                            <g:sortableColumn class="oldval" property="oldValue" title="${message(code: 'auditLogEvent.oldValue.label', default: 'Old Value')}" params="${params}" />
                            <g:sortableColumn class="newval" property="newValue" title="${message(code: 'auditLogEvent.newValue.label', default: 'New Value')}" params="${params}" />
                            <g:sortableColumn class="notes" property="note" title="${message(code: 'auditLogEvent.note.label', default: 'Note')}" params="${params}" />
                            
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${auditLogEventInstanceList}" status="i" var="auditLogEventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${i + params.offset + 1}</td>
                            <td><g:formatDate date="${auditLogEventInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${auditLogEventInstance.lastUpdated}" /></td>
                                                    
                            <td>${fieldValue(bean: auditLogEventInstance, field: "actor")}</td>
                        
                            <td>${fieldValue(bean: auditLogEventInstance, field: "eventName")}</td>
                        
                            <td>
                                <g:if test="${(auditLogEventInstance.className.toLowerCase().endsWith('.' +className.toLowerCase()) ) && (objectId?.equals(auditLogEventInstance.persistedObjectId)) }">
                                    <b>${fieldValue(bean: auditLogEventInstance, field: "className")}</b>
                                </g:if>
                                <g:else>
                                    ${fieldValue(bean: auditLogEventInstance, field: "className")}
                                </g:else>
                            </td>
                            <td>
                                <g:if test="${ (auditLogEventInstance.link) }">
                                    <a target="_blank" href="${createLink(uri: auditLogEventInstance.link)}">${auditLogEventInstance.persistedObjectId}</a>
                                </g:if>
                                <g:else>
                                    ${fieldValue(bean: auditLogEventInstance, field: "persistedObjectId")}
                                </g:else>
                            </td>
                            <td>${fieldValue(bean: auditLogEventInstance, field: "propertyName")}</td>
                            <td>${fieldValue(bean: auditLogEventInstance, field: "oldValue")}</td>
                            <td>${fieldValue(bean: auditLogEventInstance, field: "newValue")}</td>
                            <td>${fieldValue(bean: auditLogEventInstance, field: "note")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate params="${params}" total="${auditLogEventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
