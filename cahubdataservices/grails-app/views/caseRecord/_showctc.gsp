<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.ldacc.Donor" %>
<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>
<%@ page import="nci.obbr.cahub.forms.ctc.CtcCrf" %>
<%@ page import="nci.obbr.cahub.forms.ctc.CtcSample" %>
<%@ page import="nci.obbr.cahub.datarecords.ctc.PatientRecord" %>


            <div class="dialog">
                <table class="nowrap">
                    <tbody>
                        <tr class="prop toptable">
                          <td valign="top" class="name">Patient ID:</td>
                          <td valign="top" class="value">${caseRecordInstance.caseId}</td>
                        </tr>
                        <tr class="prop toptable">
                          <td valign="top" class="name">Status:</td>
                          <td valign="top" class="value" ><span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>                             
                               <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)}"><g:link controller="caseRecord" action="changeCtcStatus" id="${caseRecordInstance?.id}">(Change)</g:link></g:if></td>
                        </tr>
                        <tr class="prop toptable">
                          <td valign="top" class="name">Collection Site:</td>
                          <td valign="top" class="value">${caseRecordInstance?.bss.name} (${caseRecordInstance?.bss.code})</td>
                       </tr>
                       
                       <tr class="prop toptable">
                          <td valign="top" class="name">Experiment:</td>
                           <g:if test="${PatientRecord.findByCaseRecord(caseRecordInstance).experiment=='VC'}">
                             <td valign="top" class="value">Time Course Veridex Comparison</td>
                           </g:if>
                       <g:else>
                              <td valign="top" class="value">Best Tube</td>
                       </g:else>
                       </tr>
                       
                       
                       
                        <tr class="prop toptable">
                            <td valign="top" class="name">Study:</td>
                            <td valign="top" class="value">${caseRecordInstance.study}</td>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name">Patient Profile:</td>
                             <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)}">
                               <g:if test="${PatientRecord.findByCaseRecord(caseRecordInstance)?.dateSubmitted}" >
                            <td valign="top" class="value"> <g:link controller="PatientRecord" action="show" id="${PatientRecord.findByCaseRecord(caseRecordInstance)?.id}">View</g:link></td>
                               </g:if>
                               <g:else>
                                   <td valign="top" class="value"> 
                                   <g:link controller="PatientRecord" action="edit" id="${PatientRecord.findByCaseRecord(caseRecordInstance)?.id}">Edit</g:link> | <g:link controller="PatientRecord" action="show" id="${PatientRecord.findByCaseRecord(caseRecordInstance)?.id}">View</g:link>
                               </td>
                               </g:else>
                             </g:if>
                        <g:else>
                           <td valign="top" class="value"> <g:link controller="PatientRecord" action="show" id="${PatientRecord.findByCaseRecord(caseRecordInstance)?.id}">View</g:link></td>
                        </g:else>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name"><g:message code="caseRecord.lastUpdated.label" default="Last Updated:" /></td>
                            <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.lastUpdated}" /></td>
                        </tr>
                        <tr class="prop toptable">
                            <td valign="top" class="name"><g:message code="caseRecord.dateCreated.label" default="Date Created:" /></td>
                            <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.dateCreated}" /></td>
                        </tr>
                     
                        
                       
                       
                        <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN') ||  AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)}">
                        <tr class="prop toptable">
                             <g:if test="${ctc_patient_visits.equals('One')}">
                            <td valign="top" class="name">Blood Sample CRF:</td>
                           </g:if>
                           <g:else>
                              <td valign="top" class="name">Blood Sample CRF for The First Visit:</td>
                           </g:else>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1) && CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).dateSubmitted }">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).id}">View </a></td>
                        </g:if>
                         <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).dateSubmitted && !caseRecordInstance.patientRecord.dateSubmitted }">
                             <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).id}">View </a></td>
                         </g:elseif>
                         <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).dateSubmitted && caseRecordInstance.patientRecord.dateSubmitted }">
                          <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/edit/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).id}">Edit </a> |
                            <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).id}">View </a>
                          </td>
                            </g:elseif>
                        <g:else>
                          <g:if test="${caseRecordInstance.patientRecord.dateSubmitted}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/create?caseRecord.id=${caseRecordInstance?.id}&whichVisit=1">Add</a></td>
                          </g:if>
                          <g:else>
                             <td valign="top" class="value"> &nbsp;</td>
                          </g:else>
                        </g:else>
                        </tr>
                        
                       <g:if test="${ctc_patient_visits.equals('Two') || ctc_patient_visits.equals('Three') ||  ctc_patient_visits.equals('Four') ||  ctc_patient_visits.equals('Five') ||  ctc_patient_visits.equals('Six')}">
                          <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Second Visit:</td>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2) && CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).dateSubmitted  }">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).id}">View</a></td>
                        </g:if>
                        <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).dateSubmitted && !caseRecordInstance.patientRecord.dateSubmitted }">
                             <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).id}">View</a></td>
                         </g:elseif>
                          <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).dateSubmitted  && caseRecordInstance.patientRecord.dateSubmitted }">
                          <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/edit/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).id}">Edit </a> |
                            <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).id}">View </a>
                          </td>
                            </g:elseif>
                          
                        <g:else>
                           <g:if test="${caseRecordInstance.patientRecord.dateSubmitted}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/create?caseRecord.id=${caseRecordInstance?.id}&whichVisit=2">Add </a></td>
                           </g:if>
                          <g:else>
                            <td valign="top" class="value"> &nbsp;</td>
                          </g:else>
                        </g:else>
                        </tr>
                         
                       </g:if>
                        
                        <g:if test="${ctc_patient_visits.equals('Three') ||  ctc_patient_visits.equals('Four') ||  ctc_patient_visits.equals('Five') ||  ctc_patient_visits.equals('Six')}">
                          <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Third Visit:</td>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3) && CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).dateSubmitted  }">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).id}">View</a></td>
                        </g:if>
                        <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).dateSubmitted && !caseRecordInstance.patientRecord.dateSubmitted }">
                             <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).id}">View</a></td>
                         </g:elseif>
                          <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).dateSubmitted  && caseRecordInstance.patientRecord.dateSubmitted }">
                          <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/edit/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).id}">Edit </a> |
                            <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).id}">View </a>
                          </td>
                            </g:elseif>
                          
                        <g:else>
                           <g:if test="${caseRecordInstance.patientRecord.dateSubmitted}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/create?caseRecord.id=${caseRecordInstance?.id}&whichVisit=3">Add </a></td>
                           </g:if>
                          <g:else>
                            <td valign="top" class="value"> &nbsp;</td>
                          </g:else>
                        </g:else>
                        </tr>
                         
                       </g:if>
                    
                        
                         
                  
                        
                          <g:if test="${ctc_patient_visits.equals('Four') ||  ctc_patient_visits.equals('Five') ||  ctc_patient_visits.equals('Six')}">
                          <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Fourth Visit:</td>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4) && CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).dateSubmitted  }">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).id}">View</a></td>
                        </g:if>
                        <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).dateSubmitted && !caseRecordInstance.patientRecord.dateSubmitted }">
                             <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).id}">View</a></td>
                         </g:elseif>
                          <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).dateSubmitted  && caseRecordInstance.patientRecord.dateSubmitted }">
                          <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/edit/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).id}">Edit </a> |
                            <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).id}">View </a>
                          </td>
                            </g:elseif>
                          
                        <g:else>
                           <g:if test="${caseRecordInstance.patientRecord.dateSubmitted}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/create?caseRecord.id=${caseRecordInstance?.id}&whichVisit=4">Add </a></td>
                           </g:if>
                          <g:else>
                            <td valign="top" class="value"> &nbsp;</td>
                          </g:else>
                        </g:else>
                        </tr>
                         
                       </g:if>
                  
                         <g:if test="${ctc_patient_visits.equals('Five') || ctc_patient_visits.equals('Six')}">
                          <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Fifth Visit:</td>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5) && CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).dateSubmitted  }">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).id}">View</a></td>
                        </g:if>
                        <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).dateSubmitted && !caseRecordInstance.patientRecord.dateSubmitted }">
                             <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).id}">View</a></td>
                         </g:elseif>
                          <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).dateSubmitted  && caseRecordInstance.patientRecord.dateSubmitted }">
                          <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/edit/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).id}">Edit </a> |
                            <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).id}">View </a>
                          </td>
                            </g:elseif>
                          
                        <g:else>
                           <g:if test="${caseRecordInstance.patientRecord.dateSubmitted}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/create?caseRecord.id=${caseRecordInstance?.id}&whichVisit=5">Add </a></td>
                           </g:if>
                          <g:else>
                            <td valign="top" class="value"> &nbsp;</td>
                          </g:else>
                        </g:else>
                        </tr>
                         
                       </g:if>
                        
                        
                          <g:if test="${ctc_patient_visits.equals('Six')}">
                          <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Sixth Visit:</td>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6) && CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6).dateSubmitted  }">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6).id}">View</a></td>
                        </g:if>
                        <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6).dateSubmitted && !caseRecordInstance.patientRecord.dateSubmitted }">
                             <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).id}">View</a></td>
                         </g:elseif>
                          <g:elseif test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6) && !CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6).dateSubmitted  && caseRecordInstance.patientRecord.dateSubmitted }">
                          <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/edit/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6).id}">Edit </a> |
                            <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 6).id}">View </a>
                          </td>
                            </g:elseif>
                          
                        <g:else>
                           <g:if test="${caseRecordInstance.patientRecord.dateSubmitted}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/create?caseRecord.id=${caseRecordInstance?.id}&whichVisit=6">Add </a></td>
                           </g:if>
                          <g:else>
                            <td valign="top" class="value"> &nbsp;</td>
                          </g:else>
                        </g:else>
                        </tr>
                         
                       </g:if>
                        
                     </g:if>    
                        
                     <g:else>
                       
                       <tr class="prop toptable">
                             <g:if test="${ctc_patient_visits.equals('One')}">
                            <td valign="top" class="name">Blood Sample CRF:</td>
                           </g:if>
                           <g:else>
                              <td valign="top" class="name">Blood Sample CRF for The First Visit:</td>
                           </g:else>
                        <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1)}">
                            <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 1).id}">View </a></td>
                        </g:if>
                          <g:else>
                             <td valign="top" class="value"> No</td>
                          </g:else>
                        </tr>
                        <g:if test="${ctc_patient_visits.equals('Two') || ctc_patient_visits.equals('Three') ||  ctc_patient_visits.equals('Four') ||   ctc_patient_visits.equals('Five')}">
                          
                           <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Second Visit:</td>
                              <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2)}">
                                  <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 2).id}">View</a></td>
                              </g:if>

                          <g:else>
                            <td valign="top" class="value"> No</td>
                          </g:else>
                      
                        </g:if>
                        
                        <g:if test="${ctc_patient_visits.equals('Three') ||  ctc_patient_visits.equals('Four') ||   ctc_patient_visits.equals('Five')}">
                          
                           <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Third Visit:</td>
                              <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3)}">
                                  <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 3).id}">View</a></td>
                              </g:if>

                          <g:else>
                            <td valign="top" class="value"> No</td>
                          </g:else>
                      
                        </g:if>
                        
                        
                        <g:if test="${  ctc_patient_visits.equals('Four') ||   ctc_patient_visits.equals('Five')}">
                          
                           <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Fourth Visit:</td>
                              <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4)}">
                                  <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 4).id}">View</a></td>
                              </g:if>

                          <g:else>
                            <td valign="top" class="value"> No</td>
                          </g:else>
                      
                        </g:if>
                        
                         <g:if test="${ ctc_patient_visits.equals('Five')}">
                          
                           <tr class="prop toptable">
                              <td valign="top" class="name">Blood Sample CRF for The Fifth Visit:</td>
                              <g:if test="${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5)}">
                                  <td valign="top" class="value"> <a href="/cahubdataservices/ctcCrf/view/${CtcCrf.findByCaseRecordAndWhichVisit(caseRecordInstance, 5).id}">View</a></td>
                              </g:if>

                          <g:else>
                            <td valign="top" class="value"> No</td>
                          </g:else>
                      
                        </g:if>
                       
                     </g:else>
                        
                      
              
                        
            <tr class="prop"><td valign="top" class="name formheader" colspan="2">Specimens (${caseRecordInstance.specimens.size()}):</td></tr>
            <tr>
                <td valign="top" class="value" colspan="2"><div class="list">
                    <table class="nowrap">
                        <thead>
                            <tr>
                               <th>Visit Number</th>
                              <th>Draw Number</th>
                              <th>Tissue Type</th>
                              <th>Time on Bench (Hours)</th>  
                              <th>Tube Type</th>
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td class="itemid">${CtcSample.findBySample(s)?.ctcCrf.whichVisit}</td>
                                <td class="itemid">${CtcSample.findBySample(s)?.tubeId}</td>
                                <td>${s.tissueType}</td>
                                <td>${CtcSample.findBySample(s)?.benchTime}</td>
                                <td>${s.fixative.code}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table></div>
                </td>
           </tr>
          </tbody>
        </table>
    </div>

