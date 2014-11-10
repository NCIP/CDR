

<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<g:set var="bodyclass" value="candidaterecord casetocase" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'candidateRecord.label', default: 'CandidateRecord')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Link Candidate to Case</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${candidateRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${candidateRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${candidateRecordInstance?.id}" />
                <g:hiddenField name="version" value="${candidateRecordInstance?.version}" />
                <g:hiddenField name="caseRecord.caseStatus.id" value="${nci.obbr.cahub.staticmembers.CaseStatus.findByCode('DATA').id}" />
                <input type='hidden' name="pre_case_id" value="${candidateRecordInstance.caseRecord?.caseId}" />
                
                <div class="dialog" >
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="candidateId">Candidate ID:</label>
                                </td>
                                <td valign="top">
                                  <g:link controller="candidateRecord" action="view" id="${candidateRecordInstance?.id}">${candidateRecordInstance?.candidateId}</g:link>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseRecord">Case ID:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'caseRecord', 'errors')}">
                                <g:if test="${!candidateRecordInstance.caseRecord}">
                                  <g:select name="caseRecord.id" from="${filteredCaseList}" optionKey="id" value="${candidateRecordInstance?.caseRecord?.id}" noSelection="['null': 'Choose case']" />
                                </g:if>                                      
                                <g:else>
                                  <g:select name="caseRecord.id" from="" optionKey="id" value="null" noSelection="['null': 'Unlink case...']" />
                                </g:else>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bss">BSS:</label>
                                </td>
                                <td valign="top">
                                    ${candidateRecordInstance?.bss?.name}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="caseCollectionType">Collection Type:</label>
                                </td>
                                <td valign="top">
                                    ${candidateRecordInstance?.caseCollectionType?.name}
                                </td>
                            </tr>
                        

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
