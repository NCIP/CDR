<%@ page import="nci.obbr.cahub.forms.gtex.crf.MedicalHistory" %>
<g:if test="${env != 'production'}">
  <%-- cache buster--%>
  <g:set var="d" value="${new Date()}" />
  <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="medicalhistory edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'medicalHistory.label', default: 'MedicalHistory')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'medicalhistory.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
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
            <h1>Medical History</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${medicalHistoryInstance}">
            <div class="errors">
                <g:renderErrors bean="${medicalHistoryInstance}" as="list" />
            </div>
            </g:hasErrors>
           
            <g:form method="post" autocomplete="off" >
               <input type='hidden' name="formid" value="${params.formid}"/>
               <input type='hidden' name="mid" value="${medicalHistoryInstance?.id}"/>
                <g:hiddenField name="id" value="${medicalHistoryInstance?.id}" />
                <g:hiddenField name="version" value="${medicalHistoryInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="source"><g:message code="medicalHistory.source.label" default="Primary History Source" /></label>
                                </td>
                                  <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'source', 'errors')}">
                                   <g:radio name="source" id='s2' value="Medical Record" checked="${medicalHistoryInstance?.source =='Medical Record'}"/>&nbsp;<label for="s2">Medical Record</label><br/>
                                   <g:radio name="source" id='s3' value="Family Report" checked="${medicalHistoryInstance?.source =='Family Report'}"/>&nbsp;<label for="s3">Family Report, specify primary individual below</label><br/>
                                </td>
                            </tr>
                        
                          
                            <g:if test="${medicalHistoryInstance.source == 'Family Report'}">
                             
                            <tr class="prop" id="c">
                                <td valign="top" class="name">
                                  <label for="primary"><g:message code="medicalHistory.primary.label" default="If Person, Specify Primary:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'primary', 'errors')}">
                                   <g:select name="primary" from="${nci.obbr.cahub.forms.gtex.crf.MedicalHistory$Primary?.values()}" keys="${nci.obbr.cahub.forms.gtex.crf.MedicalHistory$Primary?.values()*.name()}" value="${medicalHistoryInstance?.primary?.name()}" noSelection="['': '']" />
                                  
                                </td>
                            </tr>
                               <g:if test="${medicalHistoryInstance.primary.toString() == 'Other'}">
                                  <tr class="prop" id="o" >
                                  <td valign="top" class="name">
                                    <label for="otherPrimary"><g:message code="medicalHistory.otherPrimary.label" default="Other Primary" />
                                    <br/><font color='red'>Do not put name, ONLY relationship to donor.</font></label>
                                  </td>
                                   <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'otherPrimary', 'errors')}">
                                    <g:textField name="otherPrimary" value="${medicalHistoryInstance?.otherPrimary}" />
                                   </td>
                                 </tr>
                               </g:if>
                               <g:else >
                                  <tr class="prop" id="o" style="display:none">
                                  <td valign="top" class="name">
                                    <label for="otherPrimary"><g:message code="medicalHistory.otherPrimary.label" default="Other Primary" />
                                    <br/><font color='red'>Do not put name, ONLY relationship to donor.</font></label>
                                  </td>
                                   <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'oterPrimary', 'errors')}">
                                    <g:textField name="otherPrimary" value="${medicalHistoryInstance?.otherPrimary}" />
                                   </td>
                                 </tr>
                               </g:else>
                            </g:if>
                            <g:else >
                            <tr class="prop" id="c" style="display:none">
                                <td valign="top" class="name">
                                  <label for="primary"><g:message code="medicalHistory.primary.label" default="If Person, Specify Primary:" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'primary', 'errors')}">
                                   <g:select name="primary" from="${nci.obbr.cahub.forms.gtex.crf.MedicalHistory$Primary?.values()}" keys="${nci.obbr.cahub.forms.gtex.crf.MedicalHistory$Primary?.values()*.name()}" value="${medicalHistoryInstance?.primary?.name()}" noSelection="['': '']" />
                                   
                                </td>
                            </tr>
                            
                            <tr class="prop" id="o" style="display:none">
                                <td valign="top" class="name">
                                  <label for="oterPrimary"><g:message code="medicalHistory.otherPrimary.label" default="Other Primary" />
                                  <br/><font color='red'>Do not put name, ONLY relationship to donor.</font></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'otherPrimary', 'errors')}">
                                    <g:textField name="otherPrimary" value="${medicalHistoryInstance?.otherPrimary}" />
                                </td>
                            </tr>
                            </g:else>
                           
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nonMetastaticCancer"><g:message code="medicalHistory.nonMetastaticCancer.label" default="Is there a history of non-metastatic cancer?" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'nonMetastaticCancer', 'errors')}">
                                     <g:radio name="nonMetastaticCancer" id="n1"  value="Yes" checked="${medicalHistoryInstance.nonMetastaticCancer?.toString() =='Yes'}"/>&nbsp;<label for="n1">Yes</label>&nbsp;&nbsp;&nbsp;
                                    <g:radio name="nonMetastaticCancer" id ="n2" value="No" checked="${medicalHistoryInstance.nonMetastaticCancer?.toString() =='No'}"/>&nbsp;<label for="n2">No</label>&nbsp;&nbsp;&nbsp;
                                     <g:radio name="nonMetastaticCancer" id="n3" value="Unknown" checked="${medicalHistoryInstance.nonMetastaticCancer?.toString() =='Unknown'}"/>&nbsp;<label for="n3">Unknown</label>
                              
                                </td>
                            </tr>
                        
                            
                               
                            <tr class="prop depends-on" id="ch" data-id="n1">
                               <g:if test="${medicalHistoryInstance.version >0}">
                                <td valign="top" class="name">
                                  <label for="cancerHistories"><g:message code="medicalHistory.cancerHistories.label" default="Cancer History" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: medicalHistoryInstance, field: 'cancerHistories', 'errors')}">
                                  
                                <div class="buttons">   
                                <g:actionSubmit class="show" action="update_ch" value="Cancer History" />
                                </div>
                                   
                                </td>
                                </g:if>
                            </tr>
                            
                                 <tr class="prop depends-on" data-id="s2,s3">
                                    <g:if test="${medicalHistoryInstance.version >0}">
                                <td valign="top" class="name">
                                  <label for="generalMedicalHistories"><g:message code="medicalHistory.generalMedicalHistories.label" default="General Medical History" /></label>
                                </td>
                                <td valign="top" >
                                 
                                  <table>
                                    <tr><td class="value ${hasErrors(bean: medicalHistoryInstance, field: 'generalMedicalHistories', 'errors')}"><g:actionSubmit class="show" action="update_g" value="General Medical History" /></td></tr>
                                    <tr><td class="value ${errorMap.get('s_generalMedicalHistories')}"><g:actionSubmit class="show" action="update_sg" value="General Medical History (not specified in the form)" /></td></tr>
                                  </table>
                                  
                                </td>
                                </g:if>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons"><span class="button"><g:actionSubmit class="save" action="update" value="Save" id="sub"/></span></div>
               
            </g:form>
           
       
        </div>
    </body>
</html>
