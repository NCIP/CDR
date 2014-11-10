<%@ page import="nci.obbr.cahub.datarecords.SlideRecord" %>
<g:set var="bodyclass" value="sliderecord edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'slideRecord.label', default: 'SlideRecord')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>

      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${slideRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${slideRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${slideRecordInstance?.id}" />
                <g:hiddenField name="version" value="${slideRecordInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specimenRecord"><g:message code="slideRecord.specimenRecord.label" default="Specimen ID:" /></label>
                                </td>
                                <td valign="top" class="value">${slideRecordInstance?.specimenRecord}</td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="slideId"><g:message code="slideRecord.slideId.label" default="Slide Label:" /></label>
                                </td>
                                <td valign="top" class="value">${slideRecordInstance?.slideId}</td>
                            </tr>                            
                       
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="requestReorder"><g:message code="slideRecord.reorderRequested.label" default="Request Reorder" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'requestReorder', 'errors')}">
                                    <g:checkBox name="requestReorder" value="${slideRecordInstance?.requestReorder}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="reorderType"><g:message code="slideRecord.reorderType.label" default="Reorder Type:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderType', 'errors')}">
                                    <g:select name="reorderType" from="${nci.obbr.cahub.datarecords.SlideRecord$ReorderType?.values()}" keys="${nci.obbr.cahub.datarecords.SlideRecord$ReorderType?.values()*.name()}" value="${slideRecordInstance?.reorderType?.name()}" noSelection="['': 'Choose reorder type']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="reorderReason"><g:message code="slideRecord.reorderReason.label" default="Reorder Reason:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderReason', 'errors')}">
                                    <g:select name="reorderReason" from="${nci.obbr.cahub.datarecords.SlideRecord$ReorderReason?.values()}" keys="${nci.obbr.cahub.datarecords.SlideRecord$ReorderReason?.values()*.name()}" value="${slideRecordInstance?.reorderReason?.name()}" noSelection="['': 'Choose reorder reason']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="reorderOtherComment"><g:message code="slideRecord.reorderOtherComment.label" default="Other Reason:" /></label>
                                </td>                              
                              <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'reorderOtherComment', 'errors')}">
                                  <g:textArea name="reorderOtherComment" cols="40" rows="5" value="${slideRecordInstance?.reorderOtherComment}" />
                              </td>                              
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="reorderRequestDate"><g:message code="slideRecord.reorderRequestDate.label" default="Reorder Request Date:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'reorderRequestDate', 'errors')}">
                                    <g:formatDate format="MM/dd/yyyy hh:mm:ss" date="${slideRecordInstance?.reorderRequestDate}" /><br />
                                </td>
                            </tr>
                      
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="slideLocation"><g:message code="slideRecord.slideLocation.label" default="Current Slide Location:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'slideLocation', 'errors')}">
                                    ${slideRecordInstance.slideLocation}
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="internalComments"><g:message code="slideRecord.internalComments.label" default="Comments:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: slideRecordInstance, field: 'internalComments', 'errors')}">
                                    <g:textArea name="internalComments" cols="40" rows="5" value="${slideRecordInstance?.internalComments}" />
                                </td>
                            </tr>                            
                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><input type="button" class="save" onclick="javascript:window.close()" value="Close" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
