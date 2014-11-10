<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.staticmembers.CaseStatus" %>
<g:set var="bodyclass" value="caserecord changestatus" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'Case Record')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        
        <script type="text/javascript">
            function confirmChange() {
                if (${session.org.code != 'OBBR' && session.study.code == 'BPV'}) {
                    if ($('#selectedStatus').val() == ${CaseStatus.findByCode('BSSQACOMP').id}) {
                        return confirm('Changing the status to "BSS QA Review Complete" cannot be undone. Are you sure?')
                    }
                    return true
                } else {
                    return confirm('${message(code: 'default.button.submit.confirm.message', default: 'Are you sure?')}')
                }
            }
        </script>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Change Status for ${caseRecordInstance.caseId}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${caseRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${caseRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${caseRecordInstance?.id}" />
                <g:hiddenField name="version" value="${caseRecordInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseRecord">Patient ID:</label>
                                </td>
                                <td valign="top">
                                  <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}" action="accessCtc"/>
                                  %{-- <g:link controller="caseRecord" action="accessCtc" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link> --}%
                                </td>
                            </tr>                          

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseRecord">Current Status:</label>
                                </td>
                                <td valign="top"><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span></td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseRecord">Change Case Status To:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseRecordInstance, field: 'caseRecord', 'errors')}">
                                    <g:select id="selectedStatus" name="caseStatus.id" from="${filteredStatusList}" optionKey="id" value="${caseRecordInstance?.caseStatus?.id}"  />
                          
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bss">Collection Site:</label>
                                </td>
                                <td valign="top">
                                    ${caseRecordInstance?.bss?.name}
                                </td>
                            </tr>
                        
                           
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="ctcUpdate" value="${message(code: 'default.button.update.label', default: 'Update')}" onclick="return confirmChange();" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
