

<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:set var="bodyclass" value="caserecord create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'interviewRecord.label', default: 'InterviewRecord')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'interviewrecord.js')}?bv<g:meta name='app.version'/>-${ts ?: ''}"></script>        
        <title><g:message code="default.create.label" args="[entityName]" /></title>

    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Create New CTC Case</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${caseRecord}">
            <div class="errors">
                <g:renderErrors bean="${caseRecord}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="saveCtcCase">
              <g:hiddenField name="caseStatus.id" value="${caseRecord?.caseStatus.id}" />
              <g:hiddenField name="caseCollectionType.id" value="${caseRecord?.caseCollectionType.id}" />
              <g:hiddenField name="study.id" value="${caseRecord?.study.id}" />
              
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="caseId">Case ID:</label>
                                </td>
                                <td valign="top" class="value">
                                  <g:textField style="width:120px;" name="caseId" value="${caseRecord?.caseId}" />                                    
                                </td>
                            </tr>                 
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bss">BSS:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseRecord, field: 'bss', 'errors')}">
                                    <g:select name="bss.id" from="${bssList}" optionValue="name" optionKey="id" value="${caseRecord?.bss?.id}"  />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="study">Study:</label>
                                </td>
                                <td valign="top">${caseRecord?.study?.name}</td>
                            </tr>   
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="orgCode">Comments*:</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${caseRecord?.internalComments}" />
                                    <br />
                                    <span class="no-phi-note">*No PHI allowed in this field</span>
                                </td>
                            </tr>                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton id="ctcCaseCreate" name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><input class="delete" type="button" id="ctcCaseCancel" value="Cancel"></input></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
