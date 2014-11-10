
<%@ page import="nci.obbr.cahub.util.appaccess.ApplicationListing" %>
<g:set var="bodyclass" value="applicationlisting show" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'applicationListing.label', default: 'ApplicationListing')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
             <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
            session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
              session.authorities?.contains("ROLE_ADMIN")}'>
          <a class="home" href="${createLink(uri: '/home/opshome')}"><g:message code="default.home.label"/></a>
    </g:if>
    
    <g:else>
          <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </g:else>
          <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
          <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
          </div>
        </div>
        <div id="container" class="clearfix">  
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="applicationListing.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: applicationListingInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="applicationListing.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: applicationListingInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="applicationListing.code.label" default="Code" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: applicationListingInstance, field: "code")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="applicationListing.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: applicationListingInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="applicationListing.opEmail.label" default="Op Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: applicationListingInstance, field: "opEmail")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${applicationListingInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
