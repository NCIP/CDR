<%@ page import="nci.obbr.cahub.forms.bpv.BpvSlidePrep" %>
<g:set var="bodyclass" value="bpvslideprep list bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvSlidePrepInstance?.formMetadata?.cdrFormName}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'bpvSlidePrep.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="internalComments" title="${message(code: 'bpvSlidePrep.internalComments.label', default: 'Internal Comments')}" />
                        
                            <g:sortableColumn property="publicComments" title="${message(code: 'bpvSlidePrep.publicComments.label', default: 'Public Comments')}" />
                        
                            <g:sortableColumn property="slidePrepTech" title="${message(code: 'bpvSlidePrep.slidePrepTech.label', default: 'Slide Prep Tech')}" />
                        
                            <g:sortableColumn property="microtome" title="${message(code: 'bpvSlidePrep.microtome.label', default: 'Microtome')}" />
                        
                            <g:sortableColumn property="microtomeBladeType" title="${message(code: 'bpvSlidePrep.microtomeBladeType.label', default: 'Microtome Blade Type')}" />
                        
                            <g:sortableColumn property="microtomeBladeTypeOs" title="${message(code: 'bpvSlidePrep.microtomeBladeTypeOs.label', default: 'Microtome Blade Type Os')}" />
                        
                            <g:sortableColumn property="microtomeBladeAge" title="${message(code: 'bpvSlidePrep.microtomeBladeAge.label', default: 'Microtome Blade Age')}" />
                        
                            <g:sortableColumn property="microtomeBladeAgeOs" title="${message(code: 'bpvSlidePrep.microtomeBladeAgeOs.label', default: 'Microtome Blade Age Os')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bpvSlidePrepInstanceList}" status="i" var="bpvSlidePrepInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bpvSlidePrepInstance.id}">${fieldValue(bean: bpvSlidePrepInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "internalComments")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "publicComments")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "slidePrepTech")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "microtome")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "microtomeBladeType")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "microtomeBladeTypeOs")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "microtomeBladeAge")}</td>
                        
                            <td>${fieldValue(bean: bpvSlidePrepInstance, field: "microtomeBladeAgeOs")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bpvSlidePrepInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
