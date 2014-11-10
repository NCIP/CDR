
<%@ page import="nci.obbr.cahub.forms.gtex.crf.SerologyResult" %>
<g:if test="${env != 'production'}">
<%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if> 
<g:set var="bodyclass" value="serology edit" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'serologyResult.label', default: 'SerologyResult')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>

</head>
<body>
  <div id="nav" class="clearfix">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link class="list" controller="caseReportForm" action="show" id="${params.formid}">Case Report Form Menu</g:link>
    </div>
  </div>
  <div id="container" class="clearfix">
    <h1>Serology Results</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${serologyResultInstance}">
      <div class="errors">
        <g:renderErrors bean="${serologyResultInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" autocomplete="off">
      <input type="hidden" name="formid" value="${params.formid}"/>
      <g:hiddenField name="id" value="${serologyResultInstance?.id}" />
      <g:hiddenField name="version" value="${serologyResultInstance?.version}" />
      <div class="list">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">

                <label for="HIV_I_II_Ab"><g:message code="serologyResult.HIV_I_II_Ab.label" default="HIV I/II Ab" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HIV_I_II_Ab', 'errors')}  ${hasErrors(bean: serologyResultInstance, field: 'HIV_I_II_Ab_Verified', 'errors')} clearfix ">
                <span class="left">
                  <g:radio name="HIV_I_II_Ab"  id="l11" value="Not Performed" checked="${serologyResultInstance?.HIV_I_II_Ab =='Not Performed'}"/>&nbsp;<label for="l11">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Ab" id="l12"  value="Positive" checked="${serologyResultInstance?.HIV_I_II_Ab?.contains('Positive')}" />&nbsp;<label for="l12">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Ab" id="l13"  value="Negative" checked="${serologyResultInstance?.HIV_I_II_Ab =='Negative'}"/>&nbsp;<label for="l13">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Ab"  id="l14" value="Indeterminate" checked="${serologyResultInstance?.HIV_I_II_Ab?.contains('Indeterminate')}" />&nbsp;<label for="l14">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l12,l14">Verified:
                  <g:radio name="HIV_I_II_Ab_Verified" id="11v1"  value="Yes" checked="${serologyResultInstance.HIV_I_II_Ab_Verified?.toString() =='Yes'}"/>&nbsp;<label for="11v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Ab_Verified" id ="l1v2" value="No" checked="${serologyResultInstance.HIV_I_II_Ab_Verified?.toString() =='No'}"/>&nbsp;<label for="l1v2">No</label>&nbsp;&nbsp;&nbsp;
                </div>

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HIV_I_II_Plus_O_antibody"><g:message code="serologyResult.HIV_I_II_Plus_O_antibody.label" default="HIV I/II Plus O Antibody" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HIV_I_II_Plus_O_antibody', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HIVI_IIPlusOAb_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="HIV_I_II_Plus_O_antibody" id="l21"  value="Not Performed" checked="${serologyResultInstance?.HIV_I_II_Plus_O_antibody =='Not Performed'}"/>&nbsp;<label for="l21">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Plus_O_antibody" id="l22"  value="Positive" checked="${serologyResultInstance?.HIV_I_II_Plus_O_antibody?.contains('Positive')}" />&nbsp;<label for="l22">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Plus_O_antibody"  id="l23" value="Negative" checked="${serologyResultInstance?.HIV_I_II_Plus_O_antibody =='Negative'}"/>&nbsp;<label for="l23">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_I_II_Plus_O_antibody" id="l24" value="Indeterminate" checked="${serologyResultInstance?.HIV_I_II_Plus_O_antibody?.contains('Indeterminate')}" />&nbsp;<label for="l24">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l22,l24">Verified:
                  <g:radio name="HIVI_IIPlusOAb_Verified" id="12v1"  value="Yes" checked="${serologyResultInstance.HIVI_IIPlusOAb_Verified?.toString() =='Yes'}"/>&nbsp;<label for="12v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIVI_IIPlusOAb_Verified" id ="l2v2" value="No" checked="${serologyResultInstance.HIVI_IIPlusOAb_Verified?.toString() =='No'}"/>&nbsp;<label for="l2v2">No</label>&nbsp;&nbsp;&nbsp;
                </div>

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HBsAg"><g:message code="serologyResult.HBsAg.label" default="HBsAg" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HBsAg', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HBsAg_Verified', 'errors')}">

                <span class="left">
                  <g:radio name="HBsAg" id="l31"  value="Not Performed" checked="${serologyResultInstance?.HBsAg =='Not Performed'}"/>&nbsp;<label for="l31">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAg" id="l32"  value="Positive" checked="${serologyResultInstance?.HBsAg?.contains('Positive')}" />&nbsp;<label for="l32">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAg"  id="l33" value="Negative" checked="${serologyResultInstance?.HBsAg =='Negative'}"/>&nbsp;<label for="l33">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAg" id="l34"  value="Indeterminate" checked="${serologyResultInstance?.HBsAg?.contains('Indeterminate')}" />&nbsp;<label for="l34">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l32,l34">Verified:
                  <g:radio name="HBsAg_Verified" id="13v1"  value="Yes" checked="${serologyResultInstance.HBsAg_Verified?.toString() =='Yes'}"/>&nbsp;<label for="13v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAg_Verified" id ="l3v2" value="No" checked="${serologyResultInstance.HBsAg_Verified?.toString() =='No'}"/>&nbsp;<label for="l3v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HBsAb"><g:message code="serologyResult.HBsAb.label" default="HBsAb" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HBsAb', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HBsAb_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="HBsAb" id="l41"   value="Not Performed" checked="${serologyResultInstance?.HBsAb =='Not Performed'}"/>&nbsp;<label for="l41">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAb" id="l42"  value="Positive" checked="${serologyResultInstance?.HBsAb?.contains('Positive')}" />&nbsp;<label for="l42">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAb"  id="l43" value="Negative" checked="${serologyResultInstance?.HBsAb =='Negative'}"/>&nbsp;<label for="l43">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAb"  id="l44" value="Indeterminate" checked="${serologyResultInstance?.HBsAb?.contains('Indeterminate')}" />&nbsp;<label for="l44">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l42,l44">Verified:
                  <g:radio name="HBsAb_Verified" id="14v1"  value="Yes" checked="${serologyResultInstance.HBsAb_Verified?.toString() =='Yes'}"/>&nbsp;<label for="14v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBsAb_Verified" id ="l4v2" value="No" checked="${serologyResultInstance.HBsAb_Verified?.toString() =='No'}"/>&nbsp;<label for="l4v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 

                </div>
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HBcAb"><g:message code="serologyResult.HBcAb.label" default="HBcAb (Total; IgG+IgM)" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HBcAb', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HBcAb_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="HBcAb" id="l51"  value="Not Performed" checked="${serologyResultInstance?.HBcAb =='Not Performed'}"/>&nbsp;<label for="l51">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb"  id="l52" value="Positive" checked="${serologyResultInstance?.HBcAb?.contains('Positive')}" />&nbsp;<label for="l52">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb"  id="l53" value="Negative" checked="${serologyResultInstance?.HBcAb =='Negative'}"/>&nbsp;<label for="l53">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb"  id="l54" value="Indeterminate" checked="${serologyResultInstance?.HBcAb?.contains('Indeterminate')}"/>&nbsp;<label for="l54">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l52,l54">Verified:
                  <g:radio name="HBcAb_Verified" id="15v1"  value="Yes" checked="${serologyResultInstance.HBcAb_Verified?.toString() =='Yes'}"/>&nbsp;<label for="15v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb_Verified" id ="15v2" value="No" checked="${serologyResultInstance.HBcAb_Verified?.toString() =='No'}"/>&nbsp;<label for="15v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HBcAb_IgM"><g:message code="serologyResult.HBcAb_IgM.label" default="HBcAb-IgM" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HBcAb_IgM', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HBcAb_IgM_Verified', 'errors')}">
                <span class="left">

                  <g:radio name="HBcAb_IgM" id="l61"  value="Not Performed" checked="${serologyResultInstance?.HBcAb_IgM =='Not Performed'}"/>&nbsp;<label for="l61">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb_IgM"  id="l62" value="Positive" checked="${serologyResultInstance?.HBcAb_IgM?.contains('Positive')}" />&nbsp;<label for="l62">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb_IgM" id="l63"  value="Negative" checked="${serologyResultInstance?.HBcAb_IgM =='Negative'}"/>&nbsp;<label for="l63">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb_IgM" id="l64"  value="Indeterminate" checked="${serologyResultInstance?.HBcAb_IgM?.contains('Indeterminate')}" />&nbsp;<label for="l64">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l62,l64">Verified:
                  <g:radio name="HBcAb_IgM_Verified" id="16v1"  value="Yes" checked="${serologyResultInstance.HBcAb_IgM_Verified?.toString() =='Yes'}"/>&nbsp;<label for="16v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HBcAb_IgM_Verified" id ="16v2" value="No" checked="${serologyResultInstance.HBcAb_IgM_Verified?.toString() =='No'}"/>&nbsp;<label for="16v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HCV_Ab"><g:message code="serologyResult.HCV_Ab.label" default="HCV Ab" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HCV_Ab', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HCV_Ab_Verified', 'errors')}">

                <span class="left">
                  <g:radio name="HCV_Ab" id="l71"  value="Not Performed" checked="${serologyResultInstance?.HCV_Ab =='Not Performed'}"/>&nbsp;<label for="l71">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_Ab" id="l72"  value="Positive" checked="${serologyResultInstance?.HCV_Ab?.contains('Positive')}" />&nbsp;<label for="l72">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_Ab" id="l73"  value="Negative" checked="${serologyResultInstance?.HCV_Ab =='Negative'}"/>&nbsp;<label for="l73">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_Ab" id="l74"  value="Indeterminate" checked="${serologyResultInstance?.HCV_Ab?.contains('Indeterminate')}" />&nbsp;<label for="l74">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l72,l74">Verified:
                  <g:radio name="HCV_Ab_Verified" id="17v1"  value="Yes" checked="${serologyResultInstance.HCV_Ab_Verified?.toString() =='Yes'}"/>&nbsp;<label for="17v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_Ab_Verified" id ="17v2" value="No" checked="${serologyResultInstance.HCV_Ab_Verified?.toString() =='No'}"/>&nbsp;<label for="17v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 


              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="EBV_IgG_Ab"><g:message code="serologyResult.EBV_IgG_Ab.label" default="EBV IgG Ab" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'EBV_IgG_Ab', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'EBV_IgG_Ab_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="EBV_IgG_Ab" id="l81"  value="Not Performed" checked="${serologyResultInstance?.EBV_IgG_Ab =='Not Performed'}"/>&nbsp;<label for="l81">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgG_Ab" id="l82"  value="Positive" checked="${serologyResultInstance?.EBV_IgG_Ab?.contains('Positive')}" />&nbsp;<label for="l82">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgG_Ab" id="l83"  value="Negative" checked="${serologyResultInstance?.EBV_IgG_Ab =='Negative'}"/>&nbsp;<label for="l83">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgG_Ab" id="l84"  value="Indeterminate" checked="${serologyResultInstance?.EBV_IgG_Ab?.contains('Indeterminate')}" />&nbsp;<label for="l84">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l82,l84">Verified:
                  <g:radio name="EBV_IgG_Ab_Verified" id="18v1"  value="Yes" checked="${serologyResultInstance.EBV_IgG_Ab_Verified?.toString() =='Yes'}"/>&nbsp;<label for="18v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgG_Ab_Verified" id ="18v2" value="No" checked="${serologyResultInstance.EBV_IgG_Ab_Verified?.toString() =='No'}"/>&nbsp;<label for="18v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 


              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="EBV_IgM_Ab"><g:message code="serologyResult.EBV_IgM_Ab.label" default="EBV IgM Ab" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'EBV_IgM_Ab', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'EBV_IgM_Ab_Verified', 'errors')}">

                <span class="left">
                  <g:radio name="EBV_IgM_Ab" id="l91"  value="Not Performed" checked="${serologyResultInstance?.EBV_IgM_Ab =='Not Performed'}"/>&nbsp;<label for="l91">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgM_Ab" id="l92"  value="Positive" checked="${serologyResultInstance?.EBV_IgM_Ab?.contains('Positive')}" />&nbsp;<label for="l92">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgM_Ab" id="l93"  value="Negative" checked="${serologyResultInstance?.EBV_IgM_Ab =='Negative'}"/>&nbsp;<label for="l93">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgM_Ab" id="l94"  value="Indeterminate" checked="${serologyResultInstance?.EBV_IgM_Ab?.contains('Indeterminate')}" />&nbsp;<label for="l94">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l92,l94">Verified:
                  <g:radio name="EBV_IgM_Ab_Verified" id="19v1"  value="Yes" checked="${serologyResultInstance.EBV_IgM_Ab_Verified?.toString() =='Yes'}"/>&nbsp;<label for="19v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="EBV_IgM_Ab_Verified" id ="19v2" value="No" checked="${serologyResultInstance.EBV_IgM_Ab_Verified?.toString() =='No'}"/>&nbsp;<label for="19v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 


              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="RPR"><g:message code="serologyResult.RPR.label" default="RPR" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'RPR', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'RPR_Verified', 'errors')}">

                <span class="left">

                  <g:radio name="RPR" id="l101"  value="Not Performed" checked="${serologyResultInstance?.RPR =='Not Performed'}"/>&nbsp;<label for="l101">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="RPR" id="l102"  value="Positive" checked="${serologyResultInstance?.RPR?.contains('Positive')}" />&nbsp;<label for="l102">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="RPR" id="l103"  value="Negative" checked="${serologyResultInstance?.RPR =='Negative'}"/>&nbsp;<label for="l103">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="RPR"  id="l104" value="Indeterminate" checked="${serologyResultInstance?.RPR?.contains('Indeterminate')}" />&nbsp;<label for="l104">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l102,l104">Verified:
                  <g:radio name="RPR_Verified" id="110v1"  value="Yes" checked="${serologyResultInstance.RPR_Verified?.toString() =='Yes'}"/>&nbsp;<label for="110v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="RPR_Verified" id ="110v2" value="No" checked="${serologyResultInstance.RPR_Verified?.toString() =='No'}"/>&nbsp;<label for="110v2">No</label>&nbsp;&nbsp;&nbsp;
                </div>


              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="CMV_Total_Ab"><g:message code="serologyResult.CMV_Total_Ab.label" default="CMV Total Ab" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'CMV_Total_Ab', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'CMV_Total_Ab_Verified', 'errors')}">


                <span class="left">
                  <g:radio name="CMV_Total_Ab" id="l111"  value="Not Performed" checked="${serologyResultInstance?.CMV_Total_Ab =='Not Performed'}"/>&nbsp;<label for="l111">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="CMV_Total_Ab" id="l112"  value="Positive" checked="${serologyResultInstance?.CMV_Total_Ab?.contains('Positive')}" />&nbsp;<label for="l112">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="CMV_Total_Ab" id="l113"  value="Negative" checked="${serologyResultInstance?.CMV_Total_Ab =='Negative'}"/>&nbsp;<label for="l113">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="CMV_Total_Ab"  id="l114" value="Indeterminate" checked="${serologyResultInstance?.CMV_Total_Ab?.contains('Indeterminate')}" />&nbsp;<label for="l114">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l112,l114">Verified:
                  <g:radio name="CMV_Total_Ab_Verified" id="111v1"  value="Yes" checked="${serologyResultInstance.CMV_Total_Ab_Verified?.toString() =='Yes'}"/>&nbsp;<label for="111v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="CMV_Total_Ab_Verified" id ="111v2" value="No" checked="${serologyResultInstance.CMV_Total_Ab_Verified?.toString() =='No'}"/>&nbsp;<label for="111v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HIV_1_NAT"><g:message code="serologyResult.HIV_1_NAT.label" default="HIV-1 NAT" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HIV_1_NAT', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HIV_1_NAT_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="HIV_1_NAT" id="l121"  value="Not Performed" checked="${serologyResultInstance?.HIV_1_NAT =='Not Performed'}"/>&nbsp;<label for="l121">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_1_NAT"  id="l122" value="Positive" checked="${serologyResultInstance?.HIV_1_NAT?.contains('Positive')}" />&nbsp;<label for="l122">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_1_NAT" id="l123"  value="Negative" checked="${serologyResultInstance?.HIV_1_NAT =='Negative'}"/>&nbsp;<label for="l123">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_1_NAT" id="l124"  value="Indeterminate" checked="${serologyResultInstance?.HIV_1_NAT?.contains('Indeterminate')}" />&nbsp;<label for="l124">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value "  data-id="l122,l124">Verified:
                  <g:radio name="HIV_1_NAT_Verified" id="112v1"  value="Yes" checked="${serologyResultInstance.HIV_1_NAT_Verified?.toString() =='Yes'}"/>&nbsp;<label for="112v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HIV_1_NAT_Verified" id ="112v2" value="No" checked="${serologyResultInstance.HIV_1_NAT_Verified?.toString() =='No'}"/>&nbsp;<label for="112v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="HCV_1_NAT"><g:message code="serologyResult.HCV_1_NAT.label" default="HCV-1 NAT" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'HCV_1_NAT', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'HCV_1_NAT_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="HCV_1_NAT" id="l131"  value="Not Performed" checked="${serologyResultInstance?.HCV_1_NAT =='Not Performed'}"/>&nbsp;<label for="l131">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_1_NAT" id="l132"  value="Positive" checked="${serologyResultInstance?.HCV_1_NAT?.contains('Positive')}" />&nbsp;<label for="l132">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_1_NAT"  id="l133" value="Negative" checked="${serologyResultInstance?.HCV_1_NAT =='Negative'}"/>&nbsp;<label for="l133">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_1_NAT" id="l134"  value="Indeterminate" checked="${serologyResultInstance?.HCV_1_NAT?.contains('Indeterminate')}" />&nbsp;<label for="l134">Indeterminate</label><br />
                </span>

                <div class="depends-on ser_verified left value"  data-id="l132,l134">Verified:
                  <g:radio name="HCV_1_NAT_Verified" id="113v1"  value="Yes" checked="${serologyResultInstance.HCV_1_NAT_Verified?.toString() =='Yes'}"/>&nbsp;<label for="113v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="HCV_1_NAT_Verified" id ="113v2" value="No" checked="${serologyResultInstance.HCV_1_NAT_Verified?.toString() =='No'}"/>&nbsp;<label for="113v2">No</label>&nbsp;&nbsp;&nbsp;
                </div>


              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="PRR_VDRL"><g:message code="serologyResult.PRR_VDRL.label" default="PRR/VDRL" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: serologyResultInstance, field: 'PRR_VDRL', 'errors')} ${hasErrors(bean: serologyResultInstance, field: 'PRR_VDRL_Verified', 'errors')}">
                <span class="left">
                  <g:radio name="PRR_VDRL" id="l141"  value="Not Performed" checked="${serologyResultInstance?.PRR_VDRL =='Not Performed'}"/>&nbsp;<label for="l141">Not Performed</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="PRR_VDRL" id="l142"  value="Positive" checked="${serologyResultInstance?.PRR_VDRL?.contains('Positive')}" />&nbsp;<label for="l142">Positive</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="PRR_VDRL" id="l143"  value="Negative" checked="${serologyResultInstance?.PRR_VDRL =='Negative'}"/>&nbsp;<label for="l143">Negative</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="PRR_VDRL" id="l144"  value="Indeterminate" checked="${serologyResultInstance?.PRR_VDRL?.contains('Indeterminate')}" />&nbsp;<label for="l144">Indeterminate</label><br />
                </span>


                <div class="depends-on ser_verified left value"  data-id="l142,l144">Verified: 
                  <g:radio name="PRR_VDRL_Verified" id="114v1"  value="Yes" checked="${serologyResultInstance.PRR_VDRL_Verified?.toString() =='Yes'}"/>&nbsp;<label for="114v1">Yes</label>&nbsp;&nbsp;&nbsp;
                  <g:radio name="PRR_VDRL_Verified" id ="114v2" value="No" checked="${serologyResultInstance.PRR_VDRL_Verified?.toString() =='No'}"/>&nbsp;<label for="114v2">No</label>&nbsp;&nbsp;&nbsp;
                </div> 


              </td>
            </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="Save" /></span>
      </div>
    </g:form>

  </div>
</body>
</html>
