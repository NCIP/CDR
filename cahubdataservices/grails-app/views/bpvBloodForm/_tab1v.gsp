<%@ page import="nci.obbr.cahub.util.FileUpload" %>
                <div id="tabs-main" class="tabbed-content">
                  <h3>Blood Collection</h3>           
                  <table class="tab-table dec12table ${bpvBloodFormInstance.caseRecord?.primaryTissueType?.code=="LUNG" && version53 == true ?"islung":""}">
                    <tbody>
                      <tr class="prop topsection">
                        <td class="name"><label for="bloodSop">${labelNumber++}. <g:message code="bpvBloodForm.bloodSop.label" default="Blood Collection and Processing SOP:" /></label></td>
                        <td class="value">${sopNumber} ${sopName} ${sopVersion}</td>
                      </tr> 
                      <tr class="prop topsection">
                        <td class="name">
                          <g:if test="${bloodFormVersion > 1}">
                            ${labelNumber++}. The minimum requirement was met for pre-operative blood collection <br>as per the SOP (EDTA Tube):
                          </g:if>
                          <g:else>
                            ${labelNumber++}. The minimum requirement for blood collection was met as per the SOP:
                          </g:else>
                          <div>
                            <div class="message left">
                              <strong>PLEASE NOTE:</strong>
                              <g:if test="${bloodFormVersion == 2}">
                                <p>Collection of blood in the plasma EDTA tube is mandatory. The volume of blood to be collected in the EDTA tube is 10 ml such that at least 6 plasma aliquots (0.5 ml plasma per aliquot) are collected. A minimum of 8.0 ml of blood must be collected to meet plasma requirement. Collection of blood in the DNA PAXgene/RNA PAXgene tubes is optional. If blood is collected in the optional tubes, the minimum requirement is as follows: (1) DNA PAXgene blood tube with 4.0 ml blood and (1) RNA PAXgene blood tube with 1.0 ml blood. If the minimum requirement for pre-operative blood collection as specified in the SOP is not met, this participant is <strong>NOT ELIGIBLE</strong> to continue in the study. Do not collect tissue from this participant.</p>
                              </g:if>
                              <g:elseif test="${bloodFormVersion == 3}">
                                <p>Collection of blood in the plasma EDTA tube is mandatory. The volume of blood to be collected in the EDTA tube is 10 ml such that at least 12 plasma aliquots (0.25 ml plasma per aliquot) and 3 whole cell pellet aliquots are collected. Collection of blood in the DNA PAXgene/RNA PAXgene tubes is optional. If blood is collected in the optional tubes, the minimum requirement is as follows: (1) DNA PAXgene blood tube with 4.0 ml blood and (1) RNA PAXgene blood tube with 1.0 ml blood. If the minimum requirement for pre-operative blood collection as specified in the SOP is not met, this participant is <strong>NOT ELIGIBLE</strong> to continue in the study. Do not collect tissue from this participant.</p>
                              </g:elseif>
                              <g:else>
                                <p>The minimum requirement is (1)  DNA PAXgene blood tube with 7 ml blood and (1) RNA PAXgene blood tube with 2.0 ml blood, however the desired volume of blood to be collected in PAXgene Blood DNA  tube is 8.5 ml and PAXgene Blood RNA tube is 2.5 ml.  If the minimum requirement for pre-operative blood collection as specified in the SOP was not met, this participant is <strong>NOT ELIGIBLE</strong> to continue in the study. Do not collect tissue from this participant.</p>
                              </g:else>                              
                            </div><div class="clear"></div>
                          </div>
                         </td>
                         <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodMinimum', 'errors')}">
                            <g:bpvYesNoRadioPicker checked="${(bpvBloodFormInstance?.bloodMinimum)}"  name="bloodMinimum" />
                            <g:if test="${bloodFormVersion == 3}">
                              <div class="depends-on" data-id="bloodMinimum_no" id="proceedByLeidosSection">
                                <label for="proceedByLeidos" class="redtext">Did you get permission from Leidos Biomed<br> to proceed with collection?</label><br>
                                <g:bpvYesNoRadioPicker    name="proceedByLeidos" />

                                
                                
                                
                                <div class="depends-on" data-id="proceedByLeidos_yes">FILE UPLOAD AREA</div>                         
                                
                                
                                
                                
                              </div>
                            </g:if>
                         </td>
                      </tr><tr class="prop bottomsection">
                        <td class="name"><label for="bloodDrawType">${labelNumber++}. Blood draw type:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawType', 'errors')}"><g:select name="bloodDrawType" from="${['Pre-operative (Pre Anesthesia)', 'Other (specify)']}" onchange="showhideOthDT()" value="${bpvBloodFormInstance?.bloodDrawType}" noSelection="['': '']" /></td>
                      </tr><tr id="bloodDrawTypeOsRow" class="bottomsection subentry">
                        <td class="name"><g:if test="${bloodFormVersion > 1}">Specify Other Blood Draw Type:</g:if></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawTypeOs', 'errors')}"><g:textField name="bloodDrawTypeOs" value="${bpvBloodFormInstance?.bloodDrawTypeOs}" /></td>
                      </tr><tr id="dateTimeBloodDrawRow" class="prop bottomsection">
                        <td class="name"><label for="dateTimeBloodDraw">${labelNumber++}. Date and time blood was drawn:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dateTimeBloodDraw', 'errors')}"><g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dateTimeBloodDraw" precision="day" value="${bpvBloodFormInstance?.dateTimeBloodDraw}" default="none" noSelection="['': '']" /></td>
                      </tr>
                      <tr class="prop bottomsection">
                        <td class="name"><label for="bloodDrawNurse">${labelNumber++}. Blood draw was performed by:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawNurse', 'errors')}"><g:select name="bloodDrawNurse" from="${nci.obbr.cahub.forms.bpv.BpvBloodForm$BloodDrawTech?.values()}" onchange="showhideOthNurse();showhideBloodNurseName()" keys="${nci.obbr.cahub.forms.bpv.BpvBloodForm$BloodDrawTech?.values()*.name()}" value="${bpvBloodFormInstance?.bloodDrawNurse?.name()}" noSelection="['': '']" /></td>
                      </tr><tr id="bloodDrawNurseOsRow" class="bottomsection subentry">
                        <td class="name"><g:if test="${bloodFormVersion > 1}">Specify Role of Other Blood Drawer:</g:if></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawNurseOs', 'errors')}"><g:textField name="bloodDrawNurseOs" value="${bpvBloodFormInstance?.bloodDrawNurseOs}" /></td>
                      </tr><tr id="bloodDrawNurseNameRow" class="bottomsection">
                        <td class="name"><label for="bloodDrawNurseName">Name:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawNurseName', 'errors')}"><g:textField name="bloodDrawNurseName" value="${bpvBloodFormInstance?.bloodDrawNurseName}" /></td>
                      </tr>
                      <tr id ="second-blood-draw" class="prop D2-draw bottomsection"><td colspan="2"><g:checkBox name="bloodDraw2" id="bloodDraw2" value="${(bpvBloodFormInstance?.dateTimeBloodDraw2 == null || bpvBloodFormInstance?.dateTimeBloodDraw2 == '') && (bpvBloodFormInstance?.bloodDrawNurse2 == null || bpvBloodFormInstance?.bloodDrawNurse2 == '') && (bpvBloodFormInstance?.bloodDrawNurseOs2 == null || bpvBloodFormInstance?.bloodDrawNurseOs2 == '') && (bpvBloodFormInstance?.bloodDrawNurseName2 == null || bpvBloodFormInstance?.bloodDrawNurseName2 == '')? '': 'checked'}" class="checkBxLabelRight" /><label class="namelabel" for="bloodDraw2">Check here for Second Blood Draw if any.</label><span id="checkboxinstr"></td></tr>
                      <tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                        <td class="name"><label for="dateTimeBloodDraw2">Second date and time blood was drawn:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dateTimeBloodDraw2', 'errors')}"><g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dateTimeBloodDraw2" precision="day" value="${bpvBloodFormInstance?.dateTimeBloodDraw2}" default="none" noSelection="['': '']" /></td>
                      </tr><tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                        <td class="name"><label for="bloodDrawNurse2">Second blood draw was performed by:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawNurse2', 'errors')}"><g:select name="bloodDrawNurse2" id="bloodDrawNurse2" from="${nci.obbr.cahub.forms.bpv.BpvBloodForm$BloodDrawTech?.values()}" keys="${nci.obbr.cahub.forms.bpv.BpvBloodForm$BloodDrawTech?.values()*.name()}" value="${bpvBloodFormInstance?.bloodDrawNurse2?.name()}" noSelection="['': '']" /></td>
                      </tr><tr id="bloodDrawNurseOsRow2" class="subentry D2-draw depends-on"  data-id="bloodDrawNurse2" data-select="OtherSpecify">
                        <td class="name"><g:if test="${bloodFormVersion > 1}">Specify Role of Other Blood Drawer:</g:if></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawNurseOs2', 'errors')}"><g:textField name="bloodDrawNurseOs2" value="${bpvBloodFormInstance?.bloodDrawNurseOs2}" class="dependent-clear"/></td>
                      </tr><tr id="bloodDrawNurseNameRow2" class="subentry D2-draw depends-on bottomsection" data-id="bloodDraw2">
                        <td class="name"><label for="bloodDrawNurseName2">Name:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawNurseName2', 'errors')}"><g:textField name="bloodDrawNurseName2" value="${bpvBloodFormInstance?.bloodDrawNurseName2}" /></td>
                      </tr><tr class="prop bottomsection">
                        <td class="name"><label for="bloodSource">${labelNumber++}. Blood source:</label></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodSource', 'errors')}"><g:select name="bloodSource" from="${['Fresh Venous Needle Stick', 'Other (specify)']}" onchange="showhideOthSource();" value="${bpvBloodFormInstance?.bloodSource}" noSelection="['': '']" /></td>
                      </tr><tr id="bloodSourceOsRow" class="bottomsection subentry"><td class="name"><g:if test="${bloodFormVersion > 1}">Specify Other Blood Source:</g:if></td>
                        <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodSourceOs', 'errors')}"><g:textField name="bloodSourceOs"  value="${bpvBloodFormInstance?.bloodSourceOs}" /></td>
                      </tr><tr class="prop bottomsection" id="blooddrawcomments">
                        <td colspan="2" class="name ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodDrawComments', 'errors')}"><label for="bloodDrawComments">${labelNumber++}. Blood collection comments:</label><br />
                        <g:textArea class="textwide" name="bloodDrawComments"  value="${bpvBloodFormInstance?.bloodDrawComments}" /></td>
                      </tr>

                      <tr class="bottomsection"><td colspan="2" class="formheader">Blood Processing Overview</td></tr>
                      <tr class="prop bottomsection">
                          <td class="name"><label for="dateTimeBloodReceived">${labelNumber++}. Date and time blood received in lab:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dateTimeBloodReceived', 'errors')}"><g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dateTimeBloodReceived" precision="day" value="${bpvBloodFormInstance?.dateTimeBloodReceived}" default="none" noSelection="['': '']" /></td>
                      </tr>
                      <tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                          <td class="name"><label for="dateTimeBloodReceived2"> Date and time second blood draw was received in lab:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'dateTimeBloodReceived2', 'errors')}"><g:jqDateTimePicker LDSOverlay="${bodyclass ?: ''}" name="dateTimeBloodReceived2" precision="day" value="${bpvBloodFormInstance?.dateTimeBloodReceived2}" default="none" noSelection="['': '']" /></td>
                      </tr>
                      <tr class="prop bottomsection">
                          <td class="name"><label for="bloodReceiptTech">${labelNumber++}. Blood tube(s) received in lab by:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptTech', 'errors')}"><g:textField name="bloodReceiptTech" value="${bpvBloodFormInstance?.bloodReceiptTech}" /></td>
                      </tr>
                      <tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                          <td class="name"><label for="bloodReceiptTech2"> Blood tube(s) from second blood draw received in lab by:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptTech2', 'errors')}"><g:textField name="bloodReceiptTech2" value="${bpvBloodFormInstance?.bloodReceiptTech2}" /></td>
                      </tr>
                      <g:if test="${bloodFormVersion > 1}">
                      <tr class="prop bottomsection">
                          <td class="name"><label for="bloodReceiptTemp">${labelNumber++}. Temperature in lab when blood was received:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp', 'errors')}"><g:textField size="4" name="bloodReceiptTemp" value="${fieldValue(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp')}" onkeyup="isNumericValidation(this)"/> ˚C</td>
                      </tr>
                      <tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                          <td class="name"><label for="bloodReceiptTemp2"> Temperature in lab when blood from second draw was received:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp2', 'errors')}"><g:textField size="4" name="bloodReceiptTemp2" value="${fieldValue(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp2')}" onkeyup="isNumericValidation(this)"/> ˚C</td>
                      </tr>                        
                      </g:if>
                      <g:else>
                      <tr class="prop bottomsection">
                          <td class="name"><label for="bloodReceiptTemp">${labelNumber++}. Temperature in lab when tubes received:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp', 'errors')}"><g:textField size="4" name="bloodReceiptTemp" value="${fieldValue(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp')}" onkeyup="isNumericValidation(this)"/> ˚C</td>
                      </tr>
                      <tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                          <td class="name"><label for="bloodReceiptTemp2"> Temperature in lab when tubes from second blood draw received:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp2', 'errors')}"><g:textField size="4" name="bloodReceiptTemp2" value="${fieldValue(bean: bpvBloodFormInstance, field: 'bloodReceiptTemp2')}" onkeyup="isNumericValidation(this)"/> ˚C</td>
                      </tr>                        
                      </g:else>

                      <tr class="prop bottomsection">
                          <td class="name"><label for="bloodReceiptHumidity">${labelNumber++}. Humidity in lab when tube(s) received:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptHumidity', 'errors')}"><g:textField size="4" name="bloodReceiptHumidity" value="${fieldValue(bean: bpvBloodFormInstance, field: 'bloodReceiptHumidity')}" onkeyup="isNumericValidation(this)"/> %</td>
                      </tr>
                      <tr class="subentry prop D2-draw depends-on bottomsection" data-id="bloodDraw2">
                          <td class="name"><label for="bloodReceiptHumidity2"> Humidity in lab when tube(s) from second blood draw received:</label></td>
                          <td class="value ${hasErrors(bean: bpvBloodFormInstance, field: 'bloodReceiptHumidity2', 'errors')}"><g:textField size="4" name="bloodReceiptHumidity2" value="${fieldValue(bean: bpvBloodFormInstance, field: 'bloodReceiptHumidity2')}" onkeyup="isNumericValidation(this)"/> %</td>
                      </tr>
  
