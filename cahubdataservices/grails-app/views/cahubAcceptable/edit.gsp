<%@ page import="nci.obbr.cahub.datarecords.qm.CahubAcceptable" %>
<g:set var="bodyclass" value="cahubAcceptable edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'cahubAcceptable.label', default: 'CahubAcceptable')}" />
        <title>Update if Case is Acceptable for caHUB Analysis</title>
    </head>
    <body>
      
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" >
            <h1>Acceptable for caHUB Analysis</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cahubAcceptableInstance}">
              <g:message> has errors </g:message>
            <div class="errors">
                <g:renderErrors bean="${cahubAcceptableInstance}" as="list" />
            </div>
            </g:hasErrors>
            
           
            
            <g:form  method="post" >
                <g:hiddenField name="id" value="${cahubAcceptableInstance?.id}" />
                <g:hiddenField name="version" value="${cahubAcceptableInstance?.version}" />
               <g:render template="/caseRecord/caseDetails" bean="${cahubAcceptableInstance.caseRecord}" var="caseRecord" /> 
                 <g:render template="formFieldsInc"/>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    
                    %{-- JIRA CDRQA-1326 09/04/2014 <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span> --}%
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))history.go(-1);"></input></span>
                                                                                          
                </div>
            </g:form>
        </div>
    </body>
</html>
