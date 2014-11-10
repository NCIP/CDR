

<%@ page import="nci.obbr.cahub.forms.gtex.crf.SurgicalProcedures" %>
<g:set var="bodyclass" value="surgicalprocedures create" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'surgicalProcedures.label', default: 'SurgicalProcedures')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
          <g:javascript>
           $(document).ready(function(){
               $("#a1").click(function(){
                     // alert("what???")
                      document.getElementById("d1").style.display = 'block';
                     // alert("still good???")
                      $("#l1").hide()

                    return false;
                      
                });
                
                
                $("#c1").click(function(){
                    document.getElementById("new_medication1").value='';
                    document.getElementById("new_dosage1").value=''
                    document.getElementById("d1").style.display = 'none';
                    $("#l1").show()
                    
                    return false;
                  
                  
               
                });    

                
                  $("#a2").click(function(){
                     // alert("what???")
                      document.getElementById("d2").style.display = 'block';
                     // alert("still good???")
                      $("#l2").hide()

                    return false;
                      
                });
                
                
                $("#c2").click(function(){
                    document.getElementById("new_medication2").value='';
                    document.getElementById("new_dosage2").value=''
                    document.getElementById("d2").style.display = 'none';
                    $("#l2").show()
                    
                    return false;
                  
                  
               
                });    
                
                
                 $("#a3").click(function(){
                     // alert("what???")
                      document.getElementById("d3").style.display = 'block';
                     // alert("still good???")
                      $("#l3").hide()

                    return false;
                      
                });
                
                
                $("#c3").click(function(){
                    document.getElementById("new_medication3").value='';
                    document.getElementById("new_dosage3").value=''
                    document.getElementById("d3").style.display = 'none';
                    $("#l3").show()
                    
                    return false;
                  
                  
               
                });    
              
                
           
             });

             function del(mid){
               document.getElementById("delete").value=mid;
              // alert(mid)
               return confirm("Are you sure to delete the medication record?")
             }
          </g:javascript>
        
        <style type="text/css">
          .blueRow{
            background-color:#B2D1FF;
          }
           .blueRow, td{
             font-weight:bold;
           }
        </style>
        
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
             <g:link class="list" controller="caseReportForm" action="show" id="${params.formid}">Case Report Form Menu</g:link>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Surgical Procedures</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${surgicalProceduresInstance}">
            <div class="errors">
                <g:renderErrors bean="${surgicalProceduresInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${surgicalProceduresInstance?.id}" />
                <g:hiddenField name="version" value="${surgicalProceduresInstance?.version}" />
                <input type="hidden" name="delete" id="delete">
                <input type="hidden" name="formid" value="${params.formid}">
                <div class="list">
                 
                  
                  <g:each in="${list1}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list2}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                    <g:each in="${list3}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                    <g:each in="${list4}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                    <g:each in="${list5}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list6}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list7}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list8}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list9}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list10}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list11}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                  <g:each in="${list12}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <g:each in="${list13}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                    <g:each in="${list14}" status="i" var="med">
                       <input type="hidden" name="${med.id}" value="isid"/>
                  </g:each>
                  
                   <h3 align="center">Pre-Operative Medications</h3>
                  <table>
                    <tr><td>${durationId1}</td> <td colspan="3" class="value ${errorMap.get('durationPreOpeMed')}">Duration of administration of pre-operative medications to surgery  (hr:min)&nbsp;<input name="durationPreOpeMed" value="${surgicalProceduresInstance?.durationPreOpeMed}" /></td>
                    </tr>
                    <tr class="blueRow" ><td>&nbsp;</td><td>Type of intravenous (IV) sedation administered</td><td colspan="2">Dosage / Unit</td></tr>
                    
                       <g:each in="${list1}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify): &nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                       </g:each>
                  
                    <tr class="blueRow" ><td>&nbsp;</td><td>Type of IV Opiate administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list2}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify): &nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>
                     
                     
                    <tr class="blueRow" ><td>&nbsp;</td><td>Type of IV Antiemetic administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list3}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>
                     
                     
                    <tr class="blueRow" ><td>&nbsp;</td><td>Type of IV Antacid administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list4}" status="i" var="med">
                       
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td  colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>
                     
                    <tr class="blueRow" ><td>&nbsp;</td><td>List other IV pre-operative medications administered but not previously listed above</td><td colspan="2">Dosage / Unit</td></tr>
                   
                      <g:each in="${list12}" status="i" var="med">
                       
                       
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}"><input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                           <td><g:actionSubmit class="save" action="update" value="Delete"  onclick="return del('${med.id}')" /></td>
                           
                         </tr>
                      
                     </g:each>
                      <tr id="l1"><td colspan="4"> <button id="a1">add</button> </td></tr>
                        
                   



                    
                  
                  </table>   
                   
                    <div id="d1" style="display:none">
                      <div class="dialog">
                        <br></br>
                        <table style="width:700px">
                           <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Medication">Medication</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                    <input type="text" id="new_medication1" name="new_medication1" />
                                </td>
                            </tr>
                             <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="">Dosage / Unit</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                    <input type="text" id="new_dosage1" name="new_dosage1" />
                                </td>
                            </tr>
                             <tr class="prop" bgcolor="#FFFFCC">
                               <td colspan="2"><g:actionSubmit class="save" action="update" value="Save" /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c1" /></td>
                            </tr>
                        </table>
                      </div>
                    </div>

                   

                  <br></br>
                   <h3 align="center">Pre-Operative Anesthesia</h3>
                  <table>
                    <tr><td>${durationId2}</td> <td colspan="3"  class="value ${errorMap.get('durationAnesthesia')}">Duration of anesthesia induction  (hr:min)&nbsp;<input name="durationAnesthesia" value="${surgicalProceduresInstance?.durationAnesthesia}" /></td>
                    </tr>
                    
                      <tr class="blueRow" >
                        <td>&nbsp;</td> <td>Type of local anesthesia agents administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list5}" status="i" var="med">
                      
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>
                      
                      
                      <tr class="blueRow" ><td>&nbsp;</td><td>Type of regional (spinal/epidural) anesthesia agents  administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list6}" status="i" var="med">
                   
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                           </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>
                      
                      
                      <tr class="blueRow" ><td>&nbsp;</td><td>Type of IV anesthetic administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list7}" status="i" var="med">
                     
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>
                      
                    
                      <tr class="blueRow" ><td>&nbsp;</td><td>Type of IV narcotic / opiate anesthetic administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list8}" status="i" var="med">
                      
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each> 

                      
                      <tr class="blueRow" ><td>&nbsp;</td><td>Type of IV muscle relaxant administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list9}" status="i" var="med">
                      
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td>${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>  
                       
                      <tr class="blueRow" ><td>&nbsp;</td><td>Type of Inhalation anesthetic administered</td><td colspan="2">Dosage / Unit</td></tr>
                     <g:each in="${list10}" status="i" var="med">
                       
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">Other (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.rowid}</td>
                         <td >${med.medicationName}</td>
                         <td colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                       </tr>
                         </g:else>
                     </g:each>  
                         
                      <tr class="blueRow" ><td>&nbsp;</td><td>Type of other anesthetics administered that were not previously listed</td><td colspan="2">Dosage / Unit</td></tr>
                       <g:each in="${list13}" status="i" var="med">
                       
                       
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}"><input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                           <td><g:actionSubmit class="save" action="update" value="Delete"  onclick="return del('${med.id}')" /></td>
                         </tr>
                      
                     </g:each>
                        <tr id="l2"><td colspan="4"> <button id="a2">add</button> </td></tr>
                  </table>
                  
                   
                     <div id="d2" style="display:none">
                    <div class="list">
                        <br></br>
                        <table style="width:700px">
                           <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Medication">Medication</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                    <input type="text" id="new_medication2" name="new_medication2" />
                                </td>
                            </tr>
                             <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="">Dosage / Unit</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                    <input type="text" id="new_dosage2" name="new_dosage2" />
                                </td>
                            </tr>
                             <tr class="prop" bgcolor="#FFFFCC">
                               <td colspan="2"><g:actionSubmit class="save" action="update" value="Save" /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c2" /></td>
                            </tr>
                        </table>
                     </div>
                    </div>
                   
                   
                    <br></br>
                   <h3 align="center">Other medications administered during surgery</h3>
                   
                   <table>
                      <tr class="blueRow" >
                        <td>&nbsp;</td><td>Medication Name</td><td colspan="2">Dosage / Unit</td></tr> 
                     
                       <g:each in="${list11}" status="i" var="med">
                       
                      
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}">${med.subCatgory} (specify):&nbsp;<input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}" colspan="2"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                         </tr>
                      
                     
                     </g:each>  
                      
                      <tr class="blueRow" ><td>&nbsp;</td><td>Any other medications (specify)</td><td colspan="2">Dosage / Unit</td></tr>
                        <g:each in="${list14}" status="i" var="med">
                       
                       
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.rowid}</td>
                           <td class="value ${errorMap.get('medicationName' + med.id)}"><input type="text" name="${med.id}_medication_name" value="${med.medicationName}"/></td>
                           <td class="value ${errorMap.get('dosage' + med.id)}"><input type="text" name="${med.id}_dosage" value="${med.dosage}"/></td>
                           <td><g:actionSubmit class="save" action="update" value="Delete"  onclick="return del('${med.id}')" /></td>
                         </tr>
                      
                     </g:each>
                        <tr id="l3"><td colspan="4"> <button id="a3">add</button> </td></tr>
                      
                   </table>
                   
                   
                    <div id="d3" style="display:none">
                      <div class="dialog">
                        <br></br>
                        <table style="width:700px">
                           <tr class="prop" bgcolor="#FFFFCC" >
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="Medication">Medication</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                    <input type="text" id="new_medication3" name="new_medication3" />
                                </td>
                            </tr>
                             <tr class="prop" bgcolor="#FFFFCC">
                                <td valign="top" class="name" style="border: 0px">
                                    <label for="">Dosage / Unit</label>
                                </td>
                                <td valign="top" style="border: 0px">
                                    <input type="text" id="new_dosage3" name="new_dosage3" />
                                </td>
                            </tr>
                             <tr class="prop" bgcolor="#FFFFCC">
                               <td colspan="2"><g:actionSubmit class="save" action="update" value="Save" /> &nbsp;&nbsp;<g:actionSubmit class="save" action="update" value="Cancel" id="c3" /></td>
                            </tr>
                        </table>
                      </div>
                    </div>
                  
                </div>
              <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" /></span>
               </div>
            </g:form>
        </div>
    </body>
</html>
