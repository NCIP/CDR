

<%@ page import="nci.obbr.cahub.prctumor.PrcForm" %>
<g:set var="bodyclass" value="prcform edit" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${prcFormInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.edit.label.brnprcreport" args="[entityName]" /></title>
        <style type="text/css">
        span.errors select {
          border: 1px solid red;
        }
        span.errors input {
               border: 1px solid red;
        }
        span.errors textarea {
             border: 1px solid red;
        }
        span.errors div{
              border: 1px solid red;
        }
          
      </style>

         <g:javascript>
           $(document).ready(function(){
              $("#o1").change(function(){
                    document.getElementById("otherOrganOrigin").value=''
                     $("#oo").hide()
               });  
               
                $("#o2").change(function(){
                
                   $("#oo").show()
               });  
               
                $(":radio").change(function(){
                
                //alert("checked....")
                
                 if(this.id == 'his_C7'){
                       $("#SC7").show()
                       $("#SC8").hide();
                       $("#SC9").hide();
                       $("#SC20").hide();
                       $("#SOTHER").hide()
                     
                     
                     
                     
                     
                  }
                  
                  if(this.id == 'his_C8'){
                  
                       $("#SC8").show()
                       $("#SC7").hide()
                       $("#SC9").hide();
                       $("#SC20").hide();
                       $("#SOTHER").hide()
                    
                     
                    
                  }
                  
                  
                   if(this.id == 'his_C9'){
                      $("#SC7").hide();
                       $("#SC8").hide();
                       $("#SC9").show();
                       $("#SC20").hide();
                       $("#SOTHER").hide()
                  
                  }
                  
                  
                    if(this.id == 'his_C20'){
                  
                         $("#SC7").hide();
                       $("#SC8").hide();
                       $("#SC9").hide();
                       $("#SC20").show();
                       $("#SOTHER").hide()
                  }
                  
                     if(this.id == 'his_OTHER'){
                       $("#SC7").hide();
                       $("#SC8").hide();
                       $("#SC9").hide();
                       $("#SC20").hide();
                       $("#SOTHER").show()
                 
                     
                  }
                  
                  
                  if(this.id !='his_C7' && this.id !='his_C8' && this.id != 'his_C9' && this.id != 'his_C20' && this.id != 'his_OTHER'){
                      $("#SC7").hide();
                       $("#SC8").hide();
                       $("#SC9").hide();
                       $("#SC20").hide();
                       $("#SOTHER").hide()
                  }
                  
                  
                });
                
                
                
                 $("#p1").change(function(){
                     //alert("p1....")
                      calculateTotal()
                });
                
                 $("#p2").change(function(){
                  
                      calculateTotal()
                });
                
                  $("#p3").change(function(){
                  
                      calculateTotal()
                });
                
                
                
                 $("#p4").change(function(){
                     calculateTotal()
                   var pct= document.getElementById("p4").value
                  
                   
                   
                  /* if(!isNaN(pct) && pct > 0){
                       $("#ncc").show()
                   }else{
                       $("#ncc").hide()
                   }*/
                

                });
                
          
                 $("#g1").change(function(){
                    //document.getElementById("otherOrganOrigin").value=''
                    document.getElementById("tt1").value=''
                    document.getElementById("tt2").value=''
                    
                     $("#who").show()
                     $("#t2t").hide()
               });  
               
                 $("#g2").change(function(){
                // alert("g2....")
                    document.getElementById("w1").value=''
                    document.getElementById("w2").value=''
                    document.getElementById("w3").value=''
                    document.getElementById("w4").value=''
                    document.getElementById("w5").value=''
                      $("#who").hide()
                     $("#t2t").show()
                    
               });  
               
               
                 $("#m1").change(function(){
                    document.getElementById("re").value=''
                     $("#f").hide()
                    
               });  
                   
               $("#m2").change(function(){
               
                     $("#f").show()
                    
               });  
               
                  $("#c1").change(function(){
                    document.getElementById("re2").value=''
                     $("#f2").hide()
                    
               });  
                   
               $("#c2").change(function(){
               
                     $("#f2").show()
                    
               });  
               
               
                $(":input").change(function(){
                  document.getElementById("changed").value = "Y"
                  //alert("Changed!")
                });
                
                
                 $("#sa1").change(function(){
                    document.getElementById("sarcomatoidDesc").value=''
                     $("#sad").hide()
                });  
                
                 $("#sa2").change(function(){
                 
                     $("#sad").show()
                });  
                
                
                  $("#sub").click(function(){
                    //return false;
                    var val= document.getElementById("tumorDimension").value
                    if(val != null && isNaN(val)){
                        alert("The greatest tumor dimension on slidet must be a number")
                        document.getElementById("tumorDimension").focus()
                        return false;
                     }
                     
                     val = document.getElementById("pctTumorArea").value
                      if(val != null && isNaN(val)){
                        alert("The percent of cross-sectional surface area composed of tumor focus must be a number")
                        document.getElementById("pctTumorArea").focus()
                        return false;
                     }
                  
                      val = document.getElementById("pctTumorCellularity").value
                      if(val != null && isNaN(val)){
                        alert("The percent of tumor cellularity by cell count of the entire slide must be a number")
                        document.getElementById("pctTumorCellularity").focus()
                        return false;
                     }
                     
                      val = document.getElementById("p1").value
                      if(val != null && isNaN(val)){
                        alert("The percent viable tumor by surface area must be a number")
                        document.getElementById("p1").focus()
                        return false;
                     }
                     
                       val = document.getElementById("p2").value
                      if(val != null && isNaN(val)){
                        alert("The percent necrotic tumor by surface area must be a number")
                        document.getElementById("p2").focus()
                        return false;
                     }
                     
                       val = document.getElementById("p3").value
                      if(val != null && isNaN(val)){
                        alert("The percent viable non-tumor tissue by surface area be a number")
                        document.getElementById("p3").focus()
                        return false;
                     }
               
                       val = document.getElementById("p4").value
                      if(val != null && isNaN(val)){
                        alert("The percent viable non-tumor tissue by surface area be a number")
                        document.getElementById("p4").focus()
                        return false;
                     }
               
                    
                });    
           
           
           });
             
           function calculateTotal(){
              // alert("change p1")
                   var pct1= document.getElementById("p1").value
                   var pct2= document.getElementById("p2").value
                   var pct3= document.getElementById("p3").value
                   var pct4= document.getElementById("p4").value
                   
                   if(pct1 == '')
                      pct1=0
                   
                   if(pct2 == '')
                      pct2 = 0
                      
                    if(pct3 == '')
                      pct3 = 0
                     
                    if(pct4 == '')
                      pct4 = 0

                      
                   
                  
                   
                   var hisTotal
                   
                   if(!isNaN(pct1) && !isNaN(pct2) && !isNaN(pct3) && !isNaN(pct4)){
                      hisTotal = pct1*1 + pct2*1 + pct3*1 + pct4*1
                      document.getElementById("t").value = hisTotal    
                  
                   }
                
           }
           
           
           function rev(){
            var changed = document.getElementById("changed").value
            if(changed == "Y"){
               alert("Please save the change!")
               return false
               }
            
          }
          </g:javascript>
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
            <g:hasErrors bean="${prcFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${prcFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prcFormInstance?.id}" />
                <g:hiddenField name="version" value="${prcFormInstance?.version}" />
                 <g:if test="${prcFormInstance?.category=='Kidney'}">
                        <g:hiddenField name="gradingSystem" value="Fuhrman Nuclear Grading System" />
                 </g:if>
                
                <input type="hidden" name="changed" value="N" id="changed"/>
                <div class="dialog">
                  
                   <table style="height:55px" >
                     <tbody>
                       <tr ><td style="width:50%; height:50px; font:bold 40px Arial; color:#336699;" >BRN</td>
                         <g:if test="${prcFormInstance?.category=='Ovary'}">
                         <td style="font:bold 20px Arial; ">Biospecimen Research Network<br/>Ovarian Slide Pathology Review for <br/>${prcFormInstance?.caseRecord?.caseId}</td>
                         </g:if>
                     
                         <g:if test="${prcFormInstance?.category=='Kidney'}">
                         <td style="font:bold 20px Arial; ">Biospecimen Research Network<br/>Kidney Slide Pathology Review for <br/>${prcFormInstance?.caseRecord?.caseId}</td>
                         </g:if>
                   </tr>
                     </tbody>
                    
                   </table>
                     
                     <table >
                       <tbody>
                          <tr class="prop">
                                <td valign="top" >
                                  <label for="caseRecord"><b>caHUB/BRN Case ID:</b> </label>
                                
                                 <!-- <g:link controller="caseRecord" action="display" id="${prcFormInstance?.caseRecord?.id}">${prcFormInstance?.caseRecord?.caseId}</g:link>-->
                                  ${prcFormInstance?.caseRecord?.caseId}
                                </td>
                                <td>&nbsp;</td> 
                            </tr>
                            
                              <tr class="prop">
                                <td valign="top" >
                                  <label for="reviewedBy"><b>Name of Pathologist Who Reviewed Slide: </b> </label>
                              
                                    ${prcFormInstance?.reviewedBy}
                                </td>
                                 <td valign="top">
                                  <label for="reviewDate"><b>Date of Slide Review:</b></label>
                                   <g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${prcFormInstance?.reviewDate}"/>
                               
                                </td>
                            </tr>
                         
                       </tbody>
                       
                     </table>
                     </div>
                
                <div class="list">
                     
                    <table>
                      <thead>
                        <tr >
                          <g:if test="${prcFormInstance?.category=='Ovary'}">
                          <th colspan="2">Ovarian Slide Pathology Review</th>
                          </g:if>
                           <g:if test="${prcFormInstance?.category=='Kidney'}">
                          <th colspan="2">Kidney Slide Pathology Review</th>
                          </g:if>
                          <th >Notes</th>
                        </tr>
                      </thead>
                        <tbody>
                          <tr >
                            <td><b>Slide ID examined by pathologist:</b></td>
                            <td style="border-bottom: 1px solid #ccc; width:53%"> 
                        <!--<g:link controller="slideRecord" action="show" id="${prcFormInstance?.slideRecord?.id}">${prcFormInstance?.slideRecord?.slideId}</g:link>-->
                        ${prcFormInstance?.slideRecord?.slideId}
                        </td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td><b>Organ of origin:</b></td>
                            <td class="value ${errorMap.get('organOrigin')}" >
                              <div>
                              Select One:<br/>
                                <g:if test="${prcFormInstance?.category=='Ovary'}">
                               <g:radio name="organOrigin" id='o1' value="Ovary" checked="${prcFormInstance?.organOrigin =='Ovary'}"/>&nbsp;<label for="o1">Ovary</label><br/>
                                </g:if>
                                <g:if test="${prcFormInstance?.category=='Kidney'}">
                               <g:radio name="organOrigin" id='o1' value="Kidney" checked="${prcFormInstance?.organOrigin =='Kidney'}"/>&nbsp;<label for="o1">Kidney</label><br/>
                                </g:if>
                               <g:radio name="organOrigin" id='o2' value="Other" checked="${prcFormInstance?.organOrigin =='Other'}"/>&nbsp;<label for="o2">Other</label>
                               <g:if test="${prcFormInstance?.organOrigin == 'Other'}">
                               <span id="oo" style="display: block" class="value ${errorMap.get('otherOrganOrigin')}"><br/>Specify other organ of origin: <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${prcFormInstance?.otherOrganOrigin}" /></span>  
                               </g:if>
                               <g:else>
                                 <span id="oo" style="display: none"  class="value ${errorMap.get('otherOrganOrigin')}"><br/>Specify other organ of origin: <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${prcFormInstance?.otherOrganOrigin}" /></span>  
                               </g:else>
                              </div>
                            </td>
                            <td>If Other was selected, record other organ of origin</td>
                          </tr>
                          <tr>
                            <td><b>Histologic type:</b></td>
                            <td  class="value ${errorMap.get('histologicType')}">
                              <div>
                              Select one:<br/>
                             <g:each in="${hisList}" status="i" var="his">
                               <g:radio  name="histologicType.id" value="${his.id}" id="his_${his.code}" checked="${prcFormInstance.histologicType?.id ==his.id}"/><label for="his_${his.code}">  ${his.name}
                                 <g:if test="${his.who_code}"> (WHO code: ${his.who_code}) </g:if>
                               </label><br/>
                              <g:if test="${his.code=='C7' && prcFormInstance.histologicType?.id ==his.id}">
                                <span class="value ${errorMap.get('detail_C7')}"  id="SC7" style="display:bolck">Specify types and % <g:textField name="detail_C7" value="${prcFormInstance.histologicTypeDetail}" /><br/></span>
                              </g:if>
                               <g:if test="${his.code=='C7' && prcFormInstance.histologicType?.id !=his.id}">
                                 <span  id="SC7" style="display:none">Specify types and % <g:textField name="detail_C7" value="" /><br/></span>
                               </g:if>
                               
                                <g:if test="${his.code=='C8' && prcFormInstance.histologicType?.id ==his.id}">
                                <span class="value ${errorMap.get('detail_C8')}" id="SC8" style="display:bolck">Specify types and % <g:textField name="detail_C8" value="${prcFormInstance.histologicTypeDetail}" /><br/></span>
                              </g:if>
                               <g:if test="${his.code=='C8' && prcFormInstance.histologicType?.id !=his.id}">
                                 <span  id="SC8" style="display:none">Specify types and % <g:textField name="detail_C8" value="" /><br/></span>
                               </g:if>
                               
                                <g:if test="${his.code=='C9' && prcFormInstance.histologicType?.id ==his.id}">
                                <span class="value ${errorMap.get('detail_C9')}" id="SC9" style="display:bolck">Specify types and % <g:textField name="detail_C9" value="${prcFormInstance.histologicTypeDetail}" /><br/></span>
                              </g:if>
                                <g:if test="${his.code=='C9' && prcFormInstance.histologicType?.id !=his.id}">
                                 <span  id="SC9" style="display:none">Specify types and % <g:textField name="detail_C9" value="" /><br/></span>
                               </g:if>
                               
                                <g:if test="${his.code=='C20' && prcFormInstance.histologicType?.id ==his.id}">
                                <span class="value ${errorMap.get('detail_C20')}"  id="SC20" style="display:bolck">Specify types<g:textField name="detail_C20" value="${prcFormInstance.histologicTypeDetail}" /><br/></span>
                              </g:if>
                                <g:if test="${his.code=='C20' && prcFormInstance.histologicType?.id !=his.id}">
                                 <span  id="SC20" style="display:none">Specify types <g:textField name="detail_C20" value="" /><br/></span>
                               </g:if>
                              
                                <g:if test="${his.code=='OTHER' && prcFormInstance.histologicType?.id ==his.id}">
                                <span class="value ${errorMap.get('other_type')}" id="SOTHER" style="display:bolck">Specify other histologic type: <g:textField name="otherHistoloicType" value="${prcFormInstance.otherHistologicType}" /><br/></span>
                              </g:if>
                                <g:if test="${his.code=='OTHER' && prcFormInstance.histologicType?.id !=his.id}">
                                <span  id="SOTHER" style="display:none">Specify other histologic type: <g:textField name="otherHistoloicType" value="" /><br/></span>
                              </g:if>
                               
                             
                               
                               
                            </g:each>
                              </div>
                            </td>
                            <g:if test="${prcFormInstance?.category=='Ovary'}">
                            <td>Specify histologic type details if the field pops up</td>
                            </g:if>
                             <g:if test="${prcFormInstance?.category=='Kidney'}">
                            <td>If Other was selected, record other histologic type</td>
                            </g:if>
                          </tr>
                          
                          <g:if test="${prcFormInstance?.category=='Kidney'}">
                            <tr>
                              <td><b>Presence of sarcomatoid features:</b></td>
                              <td class="value ${errorMap.get('sarcomatoid')}">
                                <div>
                                Select one:<br/>
                               <g:radio name="sarcomatoid" id='sa1' value="Not identified" checked="${prcFormInstance?.sarcomatoid =='Not identified'}"/>&nbsp;<label for="sa1">Not identified</label><br/>
                               <g:radio name="sarcomatoid" id='sa2' value="Present" checked="${prcFormInstance?.sarcomatoid =='Present'}"/>&nbsp;<label for="sa2">Present</label>
                                <g:if test="${prcFormInstance?.sarcomatoid == 'Present'}">
                               <span id="sad" style="display: block" class="value ${errorMap.get('sarcomatoidDesc')}"><br/>Describe sarcomatoid features: <g:textField id="sarcomatoidDesc" name="sarcomatoidDesc" value="${prcFormInstance?.sarcomatoidDesc}" /></span>  
                               </g:if>
                               <g:else>
                                 <span id="sad" style="display: none"  class="value ${errorMap.get('sarcomatoidDesc')}"><br/>Describe sarcomatoid features: <g:textField id="sarcomatoidDesc" name="sarcomatoidDesc" value="${prcFormInstance?.sarcomatoidDesc}" /></span>  
                               </g:else>
                                </div>
                               </td>
                               <td>If Present, describe sarcomatoid features</td>
                            </tr>
                          
                          </g:if>
                          
                          
                          
                          <tr>
                            <td><b>Greatest tumor dimension on slide:</b></td>
                            <td class="value ${errorMap.get('tumorDimension')}"><g:textField id="tumorDimension" name="tumorDimension" value="${fieldValue(bean: prcFormInstance, field: 'tumorDimension')}" /> (mm)</td>
                            <td>&nbsp;</td>
                          </tr>
                          
                           <tr>
                            <td><b>Percent of cross-sectional surface area composed of tumor focus:</b></td>
                            <td class="value ${errorMap.get('pctTumorArea')}"><g:textField id="pctTumorArea" name="pctTumorArea" value="${fieldValue(bean: prcFormInstance, field: 'pctTumorArea')}" /> %</td>
                            <td>&nbsp;</td>
                          </tr>
                          
                            <tr>
                            <td><b>Percent of tumor cellularity by cell count of the entire slide:</b></td>
                            <td class="value ${errorMap.get('pctTumorCellularity')}"><g:textField id="pctTumorCellularity" name="pctTumorCellularity" value="${fieldValue(bean: prcFormInstance, field: 'pctTumorCellularity')}" /> %</td>
                            <td>&nbsp;</td>
                          </tr>
                           <tr >
                          <th colspan="3">Histologic Profile Quantitative Assessment should total 100%. BRN Case Acceptance Criteria require Necrosis Percentage of <20% AND Tumor Content of &ge;50% Tumor Cells by surface area.</th>
                           </tr>
                           <tr>
                             <td><b>Histologic profile quantitative assessment:</b></td>
                             <td>
                               <table>
                                 <tr>
                                   <td>Percent Viable Tumor by Surface Area</td>
                                   <td class="value ${errorMap.get('pctViablTumor')}" style="border-bottom: 1px solid #ccc; WIDTH:25%"><g:textField id="p1" name="pctViablTumor" value="${fieldValue(bean: prcFormInstance, field: 'pctViablTumor')}" SIZE="10" /> %</td>
                                 </tr>
                                  <tr>
                                   <td>Percent Necrotic Tumor by Surface Area</td>
                                   <td class="value ${errorMap.get('pctNecroticTumor')}"><g:textField id="p2" name="pctNecroticTumor" value="${fieldValue(bean: prcFormInstance, field: 'pctNecroticTumor')}" SIZE="10" /> %</td>
                                 </tr>
                                 
                                  <tr>
                                   <td>Percent Viable Non-Tumor Tissue by Surface Area</td>
                                   <td class="value ${errorMap.get('pctViableNonTumor')}"><g:textField id="p3" name="pctViableNonTumor" value="${fieldValue(bean: prcFormInstance, field: 'pctViableNonTumor')}" SIZE="10" /> %</td>
                                 </tr>
                                 
                                  <tr>
                                   <td>Percent Non-Cellular Component by Surface Area (i.e., mucin, hemorrhage, blood clot, etc.)</td>
                                   <td class="value ${errorMap.get('pctNonCellular')}"><g:textField id="p4" name="pctNonCellular" value="${fieldValue(bean: prcFormInstance, field: 'pctNonCellular')}"  SIZE="10" /> %</td>
                                 </tr>
                                
                                      <tr >
                                        <td style="border-bottom: 1px solid">Non-Cellular Component:</td>
                                         <td style="border-bottom: 1px solid" class="value ${errorMap.get('nonCellularDesc')}"><g:textField name="nonCellularDesc" value="${prcFormInstance?.nonCellularDesc}" /></td>
                                      </tr>
                                
                                  <tr>
                                   <td>Histologic Profile Total % (Should Equal 100%)</td>
                                   <td ><g:textField id="t" name="hisTotal" value="${fieldValue(bean: prcFormInstance, field: 'hisTotal')}" SIZE="10" /> %</td>
                                 </tr>
                                 
                                 
                               </table>
                             </td>
                             <td>If present, describe Non-Cellular Component</td>
                             
                           </tr>
                           
                           <g:if test="${prcFormInstance?.category=='Ovary'}">
                           <tr>
                             <td ><b>What histologic grading system was applied?</b></td>
                             <td class="value ${errorMap.get('gradingSystem')}" >
                               <div>
                               Select one:<br/>
                               <g:radio name="gradingSystem" id='g1' value="WHO Grading System" checked="${prcFormInstance?.gradingSystem =='WHO Grading System'}"/>&nbsp;<label for="g1">WHO Grading System</label><br/>
                               <g:radio name="gradingSystem" id='g2' value="Two-Tier Grading System" checked="${prcFormInstance?.gradingSystem =='Two-Tier Grading System'}"/>&nbsp;<label for="g2">Two-Tier Grading System</label><br/>
                               </div>
                               </td>
                             <td >&nbsp;</td>
                           </tr>
                           
                           
                           <g:if test="${prcFormInstance?.gradingSystem =='WHO Grading System'}" >
                             <tr id="who" style="display:block">
                               <td><b>Histologic Grade (WHO Grading System)</b></td>
                               <td class="value ${errorMap.get('grade')}">
                                 <div>
                                 Select one:<br/>
                                 <g:radio name="grade" id='w1' value="G1: Well Differentiated" checked="${prcFormInstance?.grade =='G1: Well Differentiated'}"/>&nbsp;<label for="w1">G1: Well Differentiated</label><br/>
                                 <g:radio name="grade" id='w2' value="G2: Moderately differentiated" checked="${prcFormInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="w2">G2: Moderately differentiated</label><br/>
                                 <g:radio name="grade" id='w3' value="G3: Poorly differentiated" checked="${prcFormInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="w3">G3: Poorly differentiated</label><br/>
                                 <g:radio name="grade" id='w4' value="G4: Undifferentiated" checked="${prcFormInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="w4">G4: Undifferentiated</label><br/>
                                 <g:radio name="grade" id='w5' value="GX: Cannot be assessed" checked="${prcFormInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="w5">GX: Cannot be assessed</label>
                                 </div>
                                 </td>
                               <td>WHO grading applies to all carcinomas, including serous carcinomas</td>
                             </tr>
                              <tr id="t2t" style="display:none">
                               <td><b>Histologic Grade (Two-Tier Grading System)</b></td>
                               <td class="value">
                                 Select one:<br/>
                                 <g:radio name="grade" id='tt1' value="Low grade" checked="${prcFormInstance?.grade =='Low grade'}"/>&nbsp;<label for="tt1">Low grade</label><br/>
                                 <g:radio name="grade" id='tt2' value="High grade" checked="${prcFormInstance?.grade =='High grade'}"/>&nbsp;<label for="tt2">High grade</label><br/>
                               </td>
                               <td>Two-Teir grading may be applied to serous carcinomas and immature teratomas only</td>
                             </tr>
                           </g:if>
                            <g:if test="${prcFormInstance?.gradingSystem =='Two-Tier Grading System'}" >
                             <tr id="who" style="display:none">
                               <td ><b>Histologic Grade (WHO Grading System)</b></td>
                               <td class="value">
                                 Select one:<br/>
                                 <g:radio name="grade" id='w1' value="G1: Well Differentiated" checked="${prcFormInstance?.grade =='G1: Well Differentiated'}"/>&nbsp;<label for="w1">G1: Well Differentiated</label><br/>
                                 <g:radio name="grade" id='w2' value="G2: Moderately differentiated" checked="${prcFormInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="w2">G2: Moderately differentiated</label><br/>
                                 <g:radio name="grade" id='w3' value="G3: Poorly differentiated" checked="${prcFormInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="w3">G3: Poorly differentiated</label><br/>
                                 <g:radio name="grade" id='w4' value="G4: Undifferentiated" checked="${prcFormInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="w4">G4: Undifferentiated</label><br/>
                                 <g:radio name="grade" id='w5' value="GX: Cannot be assessed" checked="${prcFormInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="w5">GX: Cannot be assessed</label>
                               </td>
                               <td>WHO grading applies to all carcinomas, including serous carcinomas</td>
                             </tr>
                              <tr id="t2t" style="display:block">
                               <td><b>Histologic Grade (Two-Tier Grading System)</b></td>
                               <td  class="value ${errorMap.get('grade')}" >
                                 <div>
                                 Select one:<br/>
                                 <g:radio name="grade" id='tt1' value="Low grade" checked="${prcFormInstance?.grade =='Low grade'}"/>&nbsp;<label for="tt1">Low grade</label><br/>
                                 <g:radio name="grade" id='tt2' value="High grade" checked="${prcFormInstance?.grade =='High grade'}"/>&nbsp;<label for="tt2">High grade</label><br/>
                                 </div>
                               </td>
                               <td>Two-Teir grading may be applied to serous carcinomas and immature teratomas only</td>
                             </tr>
                           </g:if>
                            <g:if test="${prcFormInstance?.gradingSystem == null}" >
                               <tr id="who" style="display:none">
                               <td ><b>Histologic Grade (WHO Grading System)</b></td>
                               <td class="value">
                                 Select one:<br/>
                                 <g:radio name="grade" id='w1' value="G1: Well Differentiated" checked="${prcFormInstance?.grade =='G1: Well Differentiated'}"/>&nbsp;<label for="w1">G1: Well Differentiated</label><br/>
                                 <g:radio name="grade" id='w2' value="G2: Moderately differentiated" checked="${prcFormInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="w2">G2: Moderately differentiated</label><br/>
                                 <g:radio name="grade" id='w3' value="G3: Poorly differentiated" checked="${prcFormInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="w3">G3: Poorly differentiated</label><br/>
                                 <g:radio name="grade" id='w4' value="G4: Undifferentiated" checked="${prcFormInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="w4">G4: Undifferentiated</label><br/>
                                 <g:radio name="grade" id='w5' value="GX: Cannot be assessed" checked="${prcFormInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="w5">GX: Cannot be assessed</label>
                               </td>
                               <td >WHO grading applies to all carcinomas, including serous carcinomas</td>
                             </tr>
                             <tr id="t2t" style="display:none">
                               <td><b>Histologic Grade (Two-Tier Grading System)</b></td>
                               <td class="value">                               
                                 Select one:<br/>
                                 <g:radio name="grade" id='tt1' value="Low grade" checked="${prcFormInstance?.grade =='Low grade'}"/>&nbsp;<label for="tt1">Low grade</label><br/>
                                 <g:radio name="grade" id='tt2' value="High grade" checked="${prcFormInstance?.grade =='High grade'}"/>&nbsp;<label for="tt2">High grade</label>
                               </td>
                               <td>Two-Tier grading may be applied to serous carcinomas and immature teratomas only</td>
                             </tr>
                             
                            
                           </g:if>
                            </g:if>
                           
                           <g:if test="${prcFormInstance?.category=='Kidney'}">
                             <tr>
                               <td><b>Histologic Grade (Fuhrman Nuclear Grading System)</b></td>
                                <td class="value ${errorMap.get('grade')}" >
                                 Select one:<br/>
                                 <g:radio name="grade" id='fn1' value="G1: Nuclei  round, uniform, approximately 10µm; nucleoli inconspicuous or absent" checked="${prcFormInstance?.grade =='G1: Nuclei  round, uniform, approximately 10µm; nucleoli inconspicuous or absent'}"/>&nbsp;<label for="fn1">G1: Nuclei  round, uniform, approximately 10µm; nucleoli inconspicuous or absent</label><br/>
                                 <g:radio name="grade" id='fn2' value="G2: Nuclei  slightly irregular, approximately 15µm; nucleoli evident" checked="${prcFormInstance?.grade =='G2: Nuclei  slightly irregular, approximately 15µm; nucleoli evident'}"/>&nbsp;<label for="fn2">G2: Nuclei  slightly irregular, approximately 15µm; nucleoli evident</label><br/>
                                 <g:radio name="grade" id='fn3' value="G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent" checked="${prcFormInstance?.grade =='G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent'}"/>&nbsp;<label for="fn3">G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent</label><br/>
                                 <g:radio name="grade" id='fn4' value="G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped" checked="${prcFormInstance?.grade =='G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped'}"/>&nbsp;<label for="fn4">G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped</label><br/>
                                 <g:radio name="grade" id='fn5' value="GX: Cannot be assessed" checked="${prcFormInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="fn5">GX: Cannot be assessed</label>
                               </td>
                               <td >&nbsp;</td>
                             </tr>
                           </g:if>
                           
                           <tr>
                             <td>
                               <b> This slide meets the Microscopic Analysis Criteria of the BRN Project of Necrosis Percentage of <20% AND Tumor Content of &ge;50% Tumor Cells by surface area. </b>
                             </td>
                             <td class="value ${errorMap.get('meetsCriteria')}">
                               <div>
                               Select one:<br/>
                               <g:radio name="meetsCriteria" id='m1' value="Yes" checked="${prcFormInstance?.meetsCriteria =='Yes'}"/>&nbsp;<label for="m1">Yes</label><br/>
                               <g:radio name="meetsCriteria" id='m2' value="No" checked="${prcFormInstance?.meetsCriteria =='No'}"/>&nbsp;<label for="m2">No</label><br/>
                               </div>
                               <g:if test="${prcFormInstance?.meetsCriteria == 'No'}">
                                 <span class="value ${errorMap.get('reasonNotMeet')}" id="f" style="display:display">Specify findings:  <g:textArea style="height:38px;width:200px;" name="reasonNotMeet" value="${prcFormInstance?.reasonNotMeet}" id="re" /></span>
                               </g:if>
                               <g:else>
                               <span id="f" style="display:none">Specify findings:  <g:textArea style="height:38px;width:200px;" name="reasonNotMeet" value="${prcFormInstance?.reasonNotMeet}" id="re" /> </span>
                               </g:else>
                             </td>
                             <td>If No is selected, specify what findings do not meet the microscopic analysis criteria of the BRN project</td>
                           </tr>
                           <tr>
                             <td><b>Pathology review comments:</b></td>
                             <td> 
                                 <g:textArea style="height:38px;width:200px;" name="pathologyComments" value="${prcFormInstance?.pathologyComments}" />
                             </td>
                             <td>&nbsp;</td>
                           </tr>
                           <tr>
                             <th colspan="3"><b>Concordance With Findings of the Local BSS Pathologist</b></th>
                           </tr>
                            <tr>
                             <td><b>This slide is consistent with the findings of the local BSS pathologist.</b></td>
                              <td class="value ${errorMap.get('consistentLocalPrc')}">
                                <div >
                               Select one:<br/>
                               <g:radio name="consistentLocalPrc" id='c1' value="Yes" checked="${prcFormInstance?.consistentLocalPrc =='Yes'}"/>&nbsp;<label for="c1">Yes</label><br/>
                               <g:radio name="consistentLocalPrc" id='c2' value="No" checked="${prcFormInstance?.consistentLocalPrc =='No'}"/>&nbsp;<label for="c2">No</label><br/>
                                </div>
                               <g:if test="${prcFormInstance?.consistentLocalPrc == 'No'}">
                                 <span  class="value ${errorMap.get('reasonNotCons')}" id="f2" style="display:display">Specify findings:  <g:textArea style="height:38px;width:200px;" name="reasonNotCons" value="${prcFormInstance?.reasonNotCons}" id="re2" /></span>
                               </g:if>
                               <g:else>
                               <span id="f2" style="display:none">Specify findings:  <g:textArea style="height:38px;width:200px;" name="reasonNotCons" value="${prcFormInstance?.reasonNotCons}" id="re2" /> </span>
                               </g:else>
                             </td>
                             <td>If No is selected, specify what findings are not consistent with those of the local BSS pathologist</td>
                                 </tr>
                        </tbody>
                    </table>
                 
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" id="sub" /></span>
                      <g:if test="${canReview}">
                         <span class="button"><g:actionSubmit class="delete" action="review" value="Submit" onclick="return rev()" /></span>
                    </g:if>  
                </div>
            </g:form>
        </div>
    </body>
</html>
