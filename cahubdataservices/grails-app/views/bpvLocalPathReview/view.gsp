
<%@ page import="nci.obbr.cahub.forms.bpv.BpvLocalPathReview" %>
<g:set var="bodyclass" value="bpvlocalpathreview view bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvLocalPathReviewInstance?.formMetadata?.cdrFormName}" />
        <g:set var="slideId" value="${bpvLocalPathReviewInstance?.slideRecord?.slideId}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="Home"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.view.label.with.slide.id" args="[entityName,slideId]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvLocalPathReviewInstance}">
                <div class="errors">
                    <g:renderErrors bean="${bpvLocalPathReviewInstance}" as="list" />
                </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${bpvLocalPathReviewInstance?.caseRecord}" form="bpvLocalPath" />
            <g:form method="post" >
                <g:hiddenField name="id" value="${bpvLocalPathReviewInstance?.id}" />
                <g:hiddenField name="version" value="${bpvLocalPathReviewInstance?.version}" />
                <div id="view">
                    <div class="dialog">
                        <g:render template="/formMetadata/timeConstraint" bean="${bpvLocalPathReviewInstance.formMetadata}" var="formMetadata"/>
                        <g:render template="/caseRecord/caseDetails" bean="${bpvLocalPathReviewInstance.caseRecord}" var="caseRecord" />
                        
                        <div class="list">
                            <table class="tdwrap">
                                <tbody>
                                    <g:set var="isLater5_3" value="${ nci.obbr.cahub.util.ComputeMethods.compareCDRVersion(bpvLocalPathReviewInstance.caseRecord.cdrVer, '5.3') }" />
                                    
                                    <g:if test="${ (isLater5_3 < 0)}">
                                        <g:render template="view1" bean="${bpvLocalPathReviewInstance}" />
                                        <g:render template="view2" bean="${bpvLocalPathReviewInstance}" />
                                    </g:if>
                                    <g:else>
                                        <g:set var="seq" value="${1}" />
                                        <g:render template="formFieldInc_ver53_1" bean="${bpvLocalPathReviewInstance}" />
                                        <g:render template="formFieldInc_ver53_2" bean="${bpvLocalPathReviewInstance}" />
                                        <g:render template="formFieldInc_ver53_3" bean="${bpvLocalPathReviewInstance}" />
                                    </g:else>
                                    
                                </tbody>
                            </table>     
                        </div>
                    </div>
                </div>
                <g:if test="${canResume
                              && bpvLocalPathReviewInstance?.status == 'Reviewed'
                              && bpvLocalPathReviewInstance?.slideRecord?.specimenRecord?.caseRecord?.candidateRecord?.isEligible 
                              && bpvLocalPathReviewInstance?.slideRecord?.specimenRecord?.caseRecord?.candidateRecord?.isConsented}">
                    <div class="buttons">
                        <span class="button"><g:actionSubmit class="edit" action="reedit" value="Resume Editing" /></span>
                    </div>
                </g:if>
            </g:form>
        </div>
    </body>
</html>
