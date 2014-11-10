
<%@ page import="nci.obbr.cahub.forms.gtex.crf.CaseReportForm" %>
<g:set var="bodyclass" value="casereportform show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'caseReportForm.label', default: 'Case Report Form')}" />
        <g:set var="caseId" value="${(caseReportFormInstance?.caseRecord?.caseId)}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
      <div id="container" class="clearfix">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:queryDesc caserecord="${caseReportFormInstance?.caseRecord}" form="gtexCrf" />
             <h1><g:message code="default.edit.label.with.case.id" args="[entityName,caseId]" /></h1>
                    <div class="dialog">
                     <table>
                          <tbody>
                            <tr class="prop">
                                <td colspan="5">                                
                                  <h2>Case Details</h2>
                                </td>
                            </tr>
                      <tr class="prop">
                          <td valign="top">
                            <div class="clearfix">
                              <dl class="formdetails left">
                                  <dt>Case ID:</dt><dd><g:link controller="caseRecord" action="display" id="${caseReportFormInstance.caseRecord.id}">${caseReportFormInstance.caseRecord.caseId}</g:link></dd>
                              </dl>
                              <dl class="formdetails left">
                                  <dt>Collection Type:</dt><dd>${caseReportFormInstance?.caseRecord.caseCollectionType}</dd>
                              </dl>
                              <dl class="formdetails left">
                                  <dt>BSS:</dt><dd>${caseReportFormInstance?.caseRecord.bss.name}</dd>
                              </dl>
                            </div>
                          </td>
                      </tr> 
                          </tbody>
                     </table>
                    </div>
            
            <br/>
            <g:if test="${caseReportFormInstance?.status.value==0}">
            <div class="dialog">
                <table>
                  <tr>
                    <th width="400">Sections</th>
                    <th>Editing Status</th>
                  </tr>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name" ><g:link controller="demographics" action="edit" id="${caseReportFormInstance?.demographics?.id}" params="['id': caseReportFormInstance?.demographics?.id, formid:caseReportFormInstance?.id]" ><font color="${sectionA}">Section A.  Donor Demographics</font></g:link></td>
                           <td valign="top" class="value">${sectionAStr}</td>
                        </tr>
                        
                         <tr class="prop">
                           <td valign="top" class="name" ><g:link controller="medicalHistory" action="edit"  params="['id': caseReportFormInstance?.medicalHistory?.id, 'formid':caseReportFormInstance?.id]"><font color="${sectionB}">Section B.  Medical History</fon></g:link></td>
                           <td valign="top" class="value">${sectionBStr}</td> 
                        </tr>
                        
                          <tr class="prop">
                            <td valign="top" class="name" ><g:link controller="concomitantMedication" action="list_cm"  params="['formid': caseReportFormInstance?.id]" ><font color="${sectionC}">Section C. Current Medications (if available)</font></g:link></td>
                             <td valign="top" class="value">${sectionCStr}</td> 
                        </tr>
                    
                        
                        <g:if test="${caseReportFormInstance?.caseRecord.caseCollectionType?.code=='SURGI'}">
                        <tr class="prop">
                            <td valign="top" class="name" ><g:link controller="surgicalProcedures" action="edit"  params="['id': caseReportFormInstance?.surgicalProcedures?.id, 'formid':caseReportFormInstance?.id]" ><font color="${sectionD}">Section D.  Surgical Procedures</font></g:link></td>
                             <td valign="top" class="value">${sectionDStr}</td> 
                        </tr>
                        </g:if>
                        <g:else>
                           <tr class="prop">
                            <td valign="top" class="name" ><g:link controller="deathCircumstances" action="edit"  params="['id': caseReportFormInstance?.deathCircumstances?.id, 'formid':caseReportFormInstance?.id]" ><font color="${sectionD}">Section D.  Death Circumstances</font></g:link></td>
                             <td valign="top" class="value">${sectionDStr}</td> 
                        </tr>
                        </g:else>

                        
                        
                       
                        
                         <tr class="prop">
                            <td valign="top" class="name" ><g:link controller="serologyResult" action="edit"  params="['id': caseReportFormInstance?.serologyResult?.id, 'formid':caseReportFormInstance?.id]" ><font color="${sectionE}">Section E. Serology Results</g:link></font></td>
                             <td valign="top" class="value">${sectionEStr}</td> 
                        </tr>
                        
                     
                    </tbody>
                </table>
            </div>

            <table>
              <tr>
                <td>
                <g:form>
                    <g:hiddenField name="id" value="${caseReportFormInstance?.id}" />
                   
                     <span class="button"><g:actionSubmit class="show"  action="display2" value="Display Full Report" /></span>
                    
                </g:form>
                </td>
                <td>
                    <g:if test="${canSubmit}">
                <g:form >
                    <g:hiddenField name="id" value="${caseReportFormInstance?.id}" />
                       <span class="button"><g:actionSubmit class="edit"  action="submit" value="Submit" /></span>
                     
                </g:form>
                      </g:if>
                </td>
            </tr>
            </table>
            </g:if>
            <g:else>
              <div class="message">Case Report Form submitted.  To go back and edit, click Resume Editing below.</div>
                <table>
              <tr>
                <td>
                <g:form>
                    <g:hiddenField name="id" value="${caseReportFormInstance?.id}" />
                   
                     <span class="button"><g:actionSubmit class="show"  action="display2" value="Display Full Report" /></span>
                    
                </g:form>
                </td>
                <td>
        <g:if test="${session.DM == true}">     
                <g:form >
                    <g:hiddenField name="id" value="${caseReportFormInstance?.id}" />
                  
                       <span class="button"><g:actionSubmit class="edit"  action="editing" value="Resume Editing" /></span>
                     
                </g:form>
        </g:if>                      
                </td>
            </tr>
            </table>
              
            </g:else>
        </div>
    </body>
</html>
