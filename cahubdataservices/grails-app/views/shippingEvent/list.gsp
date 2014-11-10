
<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>
<g:set var="bodyclass" value="shippingevent list wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'shippingEvent.label', default: 'ShippingEvent')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <g:render template="/shippingEvent/shippingEventTable_tmpl" bean="${shippingEventList}" />            </div>
            <div class="paginateButtons">
                <g:paginate total="${shippingEventInstanceTotal}" /> | Total: ${shippingEventInstanceTotal}
            </div>
        </div>
    </body>
</html>
