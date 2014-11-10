<%@ page import="nci.obbr.cahub.surveyrecords.InterviewRecord" %>
<g:set var="bodyclass" value="interviewrecord changeinterviewstatus" scope="request"/>

<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'interviewRecord.label', default: 'Interview Record')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <script type="text/javascript">
            $(document).ready(function() { 
                if( $("#interviewStatus").hasClass("data") ) {
                    $("#interviewStatus option").each( function(){
                        if( !($(this).val() == "DATACOMP")){
                          $(this).remove();
                        }
                    });
                } else if( $("#interviewStatus").hasClass("datacomp") ) {
                    $("#interviewStatus option").each( function(){
                        if( !($(this).val() == "DATA" || $(this).val() == "SITEQACOMP") ) {
                          $(this).remove();
                        }
                    });
                } else if( $("#interviewStatus").hasClass("remed") ) {
                    $("#interviewStatus option").each( function(){ 
                        if( $(this).val() == "DATA" || $(this).val() == "DATACOMP" ){
                          $(this).remove();
                        }
                    });
                } 
            });
            function confirmChange() {
                if (${session.org.code != 'OBBR' && session.study.code == 'BPVELSI'} && document.getElementById("interviewStatus").value == "SITEQACOMP") {
                        return confirm('Changing the status to "Site QA Complete" cannot be undone. Are you sure?');
                } else {
                    return confirm('${message(code: 'default.button.submit.confirm.message', default: 'Are you sure?')}');
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
            <h1>Change Interview Status for ${interviewRecordInstance.interviewId}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${interviewRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${interviewRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${interviewRecordInstance?.id}" />
                <g:hiddenField name="version" value="${interviewRecordInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  Interview ID:
                                </td>
                                <td valign="top">
                                  <g:link controller="interviewRecord" action="show" id="${interviewRecordInstance.id}">${interviewRecordInstance.interviewId}</g:link>
                                </td>
                            </tr>                          

                            <tr class="prop">
                                <td valign="top" class="name">
                                  Current Interview Status:
                                </td>
                                <td valign="top">${interviewRecordInstance.interviewStatus}</td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  Change Interview Status To:
                                </td>
                                <td valign="top" class="value">
                                  <g:if test="${session.org.code == 'OBBR' && session.DM == true}">
                                      <g:select class="${interviewRecordInstance.interviewStatus.key.toLowerCase()}" name="interviewStatus" id="interviewStatus" from="${nci.obbr.cahub.surveyrecords.InterviewRecord$DmInterviewStatus?.values()}" keys="${nci.obbr.cahub.surveyrecords.InterviewRecord$DmInterviewStatus?.values()*.name()}" value="${interviewRecordInstance?.interviewStatus?.name()}"  />
                                  </g:if>
                                  <g:elseif test="${session.org.code != 'OBBR'}">
                                      <g:select class="${interviewRecordInstance.interviewStatus.key.toLowerCase()}" name="interviewStatus" id="interviewStatus" from="${nci.obbr.cahub.surveyrecords.InterviewRecord$SiteInterviewStatus?.values()}" keys="${nci.obbr.cahub.surveyrecords.InterviewRecord$SiteInterviewStatus?.values()*.name()}" value="${interviewRecordInstance?.interviewStatus?.name()}"  />
                                  </g:elseif>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  Organization:
                                </td>
                                <td valign="top">
                                    ${interviewRecordInstance?.orgCode}
                                </td>
                            </tr>

 
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" onclick="return confirmChange();" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
