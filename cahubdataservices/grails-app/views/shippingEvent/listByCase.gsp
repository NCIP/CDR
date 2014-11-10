<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>
<g:set var="bodyclass" value="shippingevent list wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'shippingEvent.label', default: 'Shipping Event')}" />
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
            
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td colspan="6"><h2>Case Details</h2></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top">
                                <div class="clearfix">
                                    <dl class="formdetails left">
                                        <dt>Case ID:</dt>
                                        <dd>
                                            <g:displayCaseRecordLink caseRecord="${caseRecord}" session="${session}" />
                                            %{-- <g:link controller="caseRecord" action="display" id="${caseRecord.id}">${caseRecord.caseId}</g:link> --}%
                                        </dd>
                                    </dl>
                                    <dl class="formdetails left">
                                        <dt>Collection Type:</dt><dd>${caseRecord.caseCollectionType}</dd>
                                    </dl>
                                    <dl class="formdetails left">
                                        <dt>BSS:</dt><dd>${caseRecord.bss.name}</dd>
                                    </dl>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <div class="list">
                <g:render template="/shippingEvent/shippingEventTableByCase_tmpl" bean="${shippingEventList}" />
            </div>
        </div>
    </body>
</html>
