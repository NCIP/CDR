<%@ page import="nci.obbr.cahub.forms.bpv.BpvCaseQualityReview" %>
<g:set var="bodyclass" value="bpvcasequality list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvCaseQualityReviewInstance?.formMetadata?.cdrFormName}" />
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
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvCaseQualityReview.id.label', default: 'Id')}" />
                            
                            <th><g:message code="bpvCaseQualityReview.caseRecord.label" default="Case Record" /></th>
                        
                            <g:sortableColumn property="consent" title="${message(code: 'bpvCaseQualityReview.consent.label', default: 'Consent')}" />
                            
                            <g:sortableColumn property="tubes" title="${message(code: 'bpvCaseQualityReview.tubes.label', default: 'Tubes')}" />
                        
                            <g:sortableColumn property="plasmaAliquots" title="${message(code: 'bpvCaseQualityReview.plasmaAliquots.label', default: 'Plasma Aliquots')}" />
                        
                            <g:sortableColumn property="tumorModule" title="${message(code: 'bpvCaseQualityReview.tumorModule.label', default: 'Tumor Module')}" />
                        
                            <g:sortableColumn property="additionalModule" title="${message(code: 'bpvCaseQualityReview.additionalModule.label', default: 'Additional Module')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvCaseQualityReviewInstanceList}" status="i" var="bpvCaseQualityReviewInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvCaseQualityReviewInstance.id}">${fieldValue(bean: bpvCaseQualityReviewInstance, field: "id")}</g:link></td>
                            
                            <td>${fieldValue(bean: bpvCaseQualityReviewInstance, field: "caseRecord")}</td>
                        
                            <td>${fieldValue(bean: bpvCaseQualityReviewInstance, field: "consent")}</td>
                            
                            <td>${fieldValue(bean: bpvCaseQualityReviewInstance, field: "tubes")}</td>
                        
                            <td>${fieldValue(bean: bpvCaseQualityReviewInstance, field: "plasmaAliquots")}</td>
                        
                            <td>${fieldValue(bean: bpvCaseQualityReviewInstance, field: "tumorModule")}</td>
                        
                            <td>${fieldValue(bean: bpvCaseQualityReviewInstance, field: "additionalModule")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvCaseQualityReviewInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
