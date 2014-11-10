<%@ page import="nci.obbr.cahub.forms.gtex.crf.Demographics" %>
<%@ page import="java.util.Calendar"%> 
<g:set var="bodyclass" value="demographics edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'demographics.label', default: 'Demographics')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  <script type="text/javascript" src="/cahubdataservices/js/demographic.js"></script>
</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link class="list" controller="caseReportForm" action="show" id="${params.formid}">Case Report Form Menu</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1>Donor Demographics</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${demographicsInstance}">
      <div class="errors">
        <g:renderErrors bean="${demographicsInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" autocomplete="off">
      <input type='hidden' name="formid" value="${params.formid}"/>
      <g:hiddenField name="BMI" value="${demographicsInstance?.BMI}" />
      <g:hiddenField name="id" value="${demographicsInstance?.id}" />
      <g:hiddenField name="version" value="${demographicsInstance?.version}" />
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td class="name">
                <label for="gender"><g:message code="demographics.gender.label" default="Gender" /></label>
              </td>
              <td class="value ${hasErrors(bean: demographicsInstance, field: 'gender', 'errors')}"><div>
                <g:radio name="gender" id='g1' value="Male" checked="${demographicsInstance.gender?.toString() =='Male'}"/>&nbsp;<label for="g1">Male</label>&nbsp;&nbsp;&nbsp;
                <g:radio name="gender" id='g2' value="Female" checked="${demographicsInstance.gender?.toString() =='Female'}"/>&nbsp;<label for="g2">Female</label>&nbsp;&nbsp;&nbsp;
                <g:radio name="gender" id="g4" value="Other" checked="${demographicsInstance.gender?.toString() =='Other'}"/>&nbsp;<label for="g4">Other</label>&nbsp;&nbsp;&nbsp;
                </div></td>
             </tr>
          <g:if test="${demographicsInstance?.gender?.name()=='Other'}">
            <tr class="prop" id="other" >
              <td class="name">
                <label for="gender"><g:message code="demographics.otherGender.lael" default="Specify Other Gender" /></label>
              </td>
              <td class="value ${hasErrors(bean: demographicsInstance, field: 'otherGender', 'errors')}">
            <g:textField name="otherGender" value="${fieldValue(bean: demographicsInstance, field: 'otherGender')}" /> 
            </td>
            </tr>
          </g:if>
          <g:else>
            <tr class="prop" id="other" style="display:none">
              <td class="name">
                <label for="gender"><g:message code="demographics.otherGender.label" default="Specify Other Gender" /></label>
              </td>
              <td class="value ${hasErrors(bean: demographicsInstance, field: 'otherGender', 'errors')}">
            <g:textField name="otherGender" value="${fieldValue(bean: demographicsInstance, field: 'otherGender')}" /> 
            </td>
            </tr>
          </g:else>
          <tr class="prop">
            <td class="name">
              <label for="dateOfBirth"><g:message code="demographics.dateOfBirth.label" default="Date Of Birth" /></label>
            </td>
            <td class="value ${hasErrors(bean: demographicsInstance, field: 'dateOfBirth', 'errors')}">
          <g:datePicker name="dateOfBirth" precision="day" value="${demographicsInstance?.dateOfBirth}" years="${1900..Calendar.instance.get(Calendar.YEAR)}"/> 
          </td>
          </tr>

          <tr class="prop">
            <td class="name">
              <label for="height"><g:message code="demographics.height.label" default="Height" /></label>
            </td>
            <td class="value ${hasErrors(bean: demographicsInstance, field: 'height', 'errors')}">
          <g:textField name="height" value="${fieldValue(bean: demographicsInstance, field: 'height')}" />&nbsp;(inches)
          </td>
          </tr>

          <tr class="prop">
            <td class="name">
              <label for="weight"><g:message code="demographics.weight.label" default="Weight" /></label>
            </td>
            <td class="value ${hasErrors(bean: demographicsInstance, field: 'weight', 'errors')}">
          <g:textField name="weight" value="${fieldValue(bean: demographicsInstance, field: 'weight')}" />&nbsp;(lbs)
          </td>
          </tr>
          <tr class="prop">
            <td class="name">
              <label for="BMI"><g:message code="demographics.BMI.label" default="BMI" /></label>
            </td>
            <td class="value ${hasErrors(bean: demographicsInstance, field: 'BMI', 'errors')}">
               <!-- <g:textField name="BMI" value="${fieldValue(bean: demographicsInstance, field: 'BMI')}" /> -->
              <span id="bmi" >${fieldValue(bean: demographicsInstance, field: 'BMI')}</span>
            </td>
          </tr>
          <tr class="prop">
            <td class="name">
              <label for="race"><g:message code="demographics.race.label" default="Race" /></label>
            </td>
            <td class="value ${hasErrors(bean: demographicsInstance, field: 'race', 'errors')}">
              <div>
                <g:checkBox name="raceIndian" id="r4" value="${demographicsInstance?.raceIndian}" />&nbsp;<label for="r4">American Indian or Alaska Native </label> <br />
                <g:checkBox name="raceAsian" id="r2" value="${demographicsInstance?.raceAsian}" />&nbsp;<label for="r2">Asian</label> <br />
                <g:checkBox name="raceBlackAmerican" id="r3" value="${demographicsInstance?.raceBlackAmerican}" />&nbsp;<label for="r3">Black or African American</label> <br />                                    
                <g:checkBox name="raceHawaiian" id="r5" value="${demographicsInstance?.raceHawaiian}" />&nbsp;<label for="r5">Native Hawaiian or other Pacific Islander</label> <br />
                <g:checkBox name="raceWhite" id="r1" value="${demographicsInstance?.raceWhite}"/>&nbsp;<label for="r1">White</label> <br />                                    
                <g:checkBox name="raceUnknown" id="r6" value="${demographicsInstance?.raceUnknown}" />&nbsp;<label for="r6">Unknown</label> <br />
              </div>
            </td>
          </tr>

          <tr class="prop">
            <td class="name">
              <label for="ethnicity"><g:message code="demographics.ethnicity.label" default="Ethnicity" /></label>
            </td>
            <td class="value ${hasErrors(bean: demographicsInstance, field: 'ethnicity', 'errors')}">
              <div>
                <g:radio name="ethnicity" id="e1" value="Hispanic or Latino" checked="${demographicsInstance?.ethnicity =='Hispanic or Latino'}"/>&nbsp;<label for="e1">Hispanic or Latino</label> <br />
                <g:radio name="ethnicity" id="e2" value="Not-Hispanic or Latino" checked="${demographicsInstance?.ethnicity =='Not-Hispanic or Latino'}"/>&nbsp;<label for="e2">Not-Hispanic or Latino</label><br />
                <g:radio name="ethnicity" id="e3" value="Not reported" checked="${demographicsInstance?.ethnicity =='Not reported'}"/>&nbsp;<label for="e3">Not reported</label> <br />
                <g:radio name="ethnicity" id="e4" value="Unknown" checked="${demographicsInstance?.ethnicity =='Unknown'}"/>&nbsp;<label for="e4">Unknown</label>  <br />
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="Save" id="sub" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
