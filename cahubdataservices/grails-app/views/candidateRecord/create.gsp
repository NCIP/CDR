<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<g:set var="bodyclass" value="candidaterecord create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'candidateRecord.label', default: 'CandidateRecord')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Create New Candidate Record</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${candidateRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${candidateRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bss">BSS:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'bss', 'errors')}">
                                    <g:select name="bss.id" from="${bssSubList}" optionValue="name" optionKey="id" value="${candidateRecordInstance?.bss?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="caseCollectionType">Case Collection Type:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'caseCollectionType', 'errors')}">
                                    <g:select name="caseCollectionType.id" from="${filteredCaseCollectionTypeList}" optionKey="id" value="${candidateRecordInstance?.caseCollectionType?.id}"  />
                                </td>
                            </tr>
                     
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="internalComments">Comments:*</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: candidateRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${candidateRecordInstance?.internalComments}" />
                                    <br /><span class="no-phi-note">*No PHI allowed in this field</span>
                                </td>
                            </tr>                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="study">Study:</label>
                                </td>
                                <td valign="top">${session?.study?.name}
                                    <g:hiddenField name="study.id" value="${session?.study?.id}"  />
                                </td>
                            </tr>                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
