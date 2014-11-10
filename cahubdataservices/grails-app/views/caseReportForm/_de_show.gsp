
<%@ page import="nci.obbr.cahub.forms.gtex.crf.Demographics" %>

            <div class="dialog2">
               
              <h3>Section A.  Donor Demographics</h3>
               <div class="list">
               <table>
                    <tbody>
                    
                      <tr class="prop">
                            <td style="width:300px;" valign="top" class="name"><g:message code="demographics.gender.label" default="Gender" /><span id="demographics.gender" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${demographicsInstance?.gender?.encodeAsHTML()}</td>
                            
                        </tr>
                        
                        <g:if test="${demographicsInstance.otherGender}">
                             <tr class="prop">
                                 <td valign="top" class="name"><g:message code="demographics.otherGender.label" default="Other Gender" /></td>
                            
                                <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "otherGender")}</td>
                            
                            </tr>
                        </g:if>
                      
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.dateOfBirth.label" default="Date Of Birth" /><span id="demographics.dateofbirth" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">
                            
                            <g:if test="${session.LDS == true}">
                              <g:formatDate format="MM/dd/yyyy" date="${demographicsInstance?.dateOfBirth}" />
                            </g:if>
                            <g:if test="${session.LDS == false || session.LDS == null}">
                              <span class="redactedMsg">REDACTED (No LDS privilege)</span>
                            </g:if>
                            </td>
                        </tr>
                    
                      
                           <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.age.label" default="Age (calculated)" /><span id="demographics.age" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "age")}</td>
                            
                        </tr>
                    
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.height.label" default="Height" /> (inches)<span id="demographics.height" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "height")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.weight.label" default="Weight" /> (lbs)<span id="demographics.weight" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "weight")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.BMI.label" default="BMI" /><span id="demographics.bmi" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "BMI")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.race.label" default="Race" /><span id="demographics.race" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "race")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="demographics.ethnicity.label" default="Ethnicity" /><span id="demographics.ethnicity" class="vocab-tooltip"></span></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: demographicsInstance, field: "ethnicity")}</td>
                            
                        </tr>
                    
                       
                    
                    </tbody>
                </table>
            </div>
            </div>
