
<%@ page import="nci.obbr.cahub.surveyrecords.InterviewRecord" %>
<g:set var="bodyclass" value="interviewrecord show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'interviewRecord.label', default: 'InterviewRecord')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link controller="bpvElsiCrf" class="list" action="searchHome">Search</g:link> 
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Interview Details</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name" style="width:200px;">Interview ID:</td>
                            <td valign="top" class="value">${interviewRecordInstance.interviewId}</td>
                            <td class="message" rowspan="20">
                              <g:render template="elsitips" />
                            </td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Organization:</td>
                            
                            <td valign="top" class="value">${interviewRecordInstance.orgCode}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Interview Status:</td>
                            
                            <td valign="top" class="value">${interviewRecordInstance.interviewStatus} <g:bpvelsistatuslink ir="${interviewRecordInstance}"/></td>
                            
                        </tr>                        
                        

                        
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value"><g:formatDate date="${interviewRecordInstance.dateCreated}" /></td>
                            
                        </tr>
                        
                        
                        <tr class="prop">
                            <td valign="top" class="name">BPV ELSI sub-study Consent:</td>
                            
                            <td valign="top" class="value">${interviewRecordInstance.studyConsent}</td>
                            
                        </tr>
                                                
                        
                        <tr class="prop">
                            <td valign="top" class="name">Survey:</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <g:if test="${interviewRecordInstance.surveys}">
                                    <g:each in="${interviewRecordInstance.surveys}" var="sr">
                                        <g:bpvelsisurveylink sr="${sr}"/> (${sr.surveyTemplate.surveyCode})
                                    </g:each>
                                </g:if>
                                <g:else>
                                  Not Administered
                                </g:else>
                            </td>
                            <td>
                            </tr>
                            <tr>
                            <td valign="top" class="name">CRF:</td>
                            
                            <td valign="top" style="text-align: left;" class="value">                              
                                <g:bpvelsicrflink crf="${interviewRecordInstance.bpvElsiCrf}" ir="${interviewRecordInstance}"/> 
                            </td>
                            
                        </tr>
                        
                      <tr class="prop">
                        <td valign="top" class="name">Comments:</td>
                        <td valign="top" class="value">
                            ${interviewRecordInstance?.comments}
                            <br />
                            <span class="no-phi-note">*No PHI allowed in this field</span>
                        </td>                        
                      </tr>
                    
                      <g:if test="${session.DM == true}">
                        <tr class="prop">
                          <td valign="top" class="name">Query Tracker:</td>
                          <td valign="top" class="value">
                            <a href="/cahubdataservices/query/listByInterview?interviewRecord.id=${interviewRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
                          </td>
                        </tr>
                      </g:if>
                    
                    </tbody>
                </table>
            </div>

        </div>
    </body>
</html>
