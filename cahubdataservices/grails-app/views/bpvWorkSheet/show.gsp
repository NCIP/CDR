<%@ page import="nci.obbr.cahub.forms.bpv.worksheet.BpvWorkSheet" %>
<g:set var="bodyclass" value="bpvworksheet show xtrawide bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${bpvWorkSheetInstance?.formMetadata?.cdrFormName}" />
  <g:set var="caseId" value="${bpvWorkSheetInstance?.caseRecord?.caseId}"/>
  <title><g:message code="default.show.label" args="[entityName]" /></title>

</head>
<body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
      <h1><g:message code="default.show.label.with.case.id" args="[entityName,caseId]"/></h1>
      <!--  rendering the case record details include here-->
      <g:render template="/formMetadata/timeConstraint" bean="${bpvWorkSheetInstance.formMetadata}" var="formMetadata"/>
      <g:render template="/caseRecord/caseDetails" bean="${bpvWorkSheetInstance.caseRecord}" var="caseRecord" />

      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${bpvWorkSheetInstance}">
        <div class="errors">
          <g:renderErrors bean="${bpvWorkSheetInstance}" as="list" />
        </div>
      </g:hasErrors>
      <g:warnings warningmap="${warningMap}" />
      <g:queryDesc caserecord="${bpvWorkSheetInstance?.caseRecord}" form="bpvWorksheet" />
     
      <!-- rendering the include to display  the main edit page here in READONLY-->
      <div id="show" class="line">

        <g:render template="formFieldsInc" />
         <br/>

        <!-- END rendering the include from the main edit page here-->




        <g:if test="${bpvWorkSheetInstance.m1}">
          <h2> Module I </h2>
          <g:render template="formFieldsEditm1Inc" />
            <br/>
        </g:if>


          <g:if test="${bpvWorkSheetInstance.m2}">
          <h2> Module II </h2>
          <g:render template="formFieldsEditm2Inc" />
            <br/>
        </g:if>

        
         <g:if test="${bpvWorkSheetInstance.m3}">
          <h2> Module III </h2>
          <g:render template="formFieldsEditm3nInc" />
            <br/>
        </g:if>
        
        
         <g:if test="${bpvWorkSheetInstance.m4}">
          <h2> Module IV </h2>
          <g:render template="formFieldsEditm4nInc" />
            <br/>
        </g:if>
        
        
         <g:if test="${bpvWorkSheetInstance.m5}">
          <h2> Module V </h2>
          <g:render template="formFieldsEditm5Inc" />
            <br/>
        </g:if>

        <!-- logic to render the module 3 view here-->

        <g:if test="${bpvWorkSheetInstance.nat}">
          <h2> Normal Adjacent Tissue </h2>
          <g:render template="formFieldsEditm3Inc" />
          <br/>
        </g:if>

        <!-- logic to render the module 4 view here-->

        <g:if test="${bpvWorkSheetInstance.ett}">
          <h2> Additional Tumor Tissue </h2>
          <g:render template="formFieldsEditm4Inc" />
            <br/>
        </g:if>

         <h2> Comments </h2>
        <table>
          <tr>
            <td>${bpvWorkSheetInstance.comments}</td>
          </tr>
        </table>
      </div>
      
      
       <g:if test="${bpvWorkSheetInstance?.dateSubmitted && bpvWorkSheetInstance?.caseRecord?.candidateRecord?.isEligible && bpvWorkSheetInstance?.caseRecord?.candidateRecord?.isConsented && canResume}">
        <dr></dr>
       <g:form method="post" >
      <g:hiddenField name="id" value="${bpvWorkSheetInstance?.id}" />
       <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="restart" value="Resume Editing" /></span>
       
      </div>
      </g:form>
      </g:if>

     
    </div>

    
   
 
</body>
</html>
