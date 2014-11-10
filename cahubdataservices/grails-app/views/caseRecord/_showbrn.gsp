<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.ChpTissueRecord" %>

<%@ page import="nci.obbr.cahub.util.ActivityEvent" %>
<%@ page import="nci.obbr.cahub.staticmembers.ActivityType" %>


<g:set var="caseStatus" value="${caseRecordInstance.caseStatus.code}"/>            

<div class="dialog">
  <table>
    <tbody>

      <tr class="prop">
        <td valign="top" class="name">Case ID:</td>
        <td valign="top" class="value">
    <g:link controller="caseRecord" action="display" id="${caseRecordInstance.caseId}">${caseRecordInstance.caseId}</g:link>
    </td>
    <td valign="top" class="name">Collection Type:</td>
    <td valign="top" class="value">${caseRecordInstance.caseCollectionType}</td>       
    <td valign="top" class="name">BSS:</td>
    <td valign="top" class="value">${caseRecordInstance.bss.name}</td>
    </tr>                    
  </table>
</div>
<div class="dialog">
  <table border="0">
    <tbody>
      <tr class="prop toptable">
        <td valign="top" class="name">Study:</td>
        <td valign="top" class="value">${caseRecordInstance.study}</td>
      </tr>       
      <tr class="prop toptable">
        <td valign="top" class="name">Case Status:</td>
        <td valign="top" class="value" >
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>                             
    <g:if test="${session.org.code == 'OBBR' && session.DM == true}">
      <g:link controller="caseRecord" action="changeCaseStatus" id="${caseRecordInstance?.id}">(Change)</g:link>                           
    </g:if>
    </td>
    </tr>         
    <tr class="prop toptable">
      <td valign="top" class="name">Primary Organ:</td>
      <td valign="top" class="value">${caseRecordInstance?.primaryTissueType}</td>
    </tr>
    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.lastUpdated.label" default="Last Updated:" /></td>
    <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.lastUpdated}" /></td>
    </tr>
    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.dateCreated.label" default="Date Created:" /></td>
    <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.dateCreated}" /></td>
    </tr>

    <tr class="prop toptable">
        <td valign="top" class="name"><g:message code="caseRecord.caseEmail.label" default="Specimen Data to CBR:" /></td>
        <td valign="top" class="value">
            <div id="emailCaseBtn">
                <g:render template="sendCaseEmail"
                    model="[id:caseRecordInstance.id, caseStatus:caseStatus, emailSent:ActivityEvent.findByCaseIdAndActivityType(caseRecordInstance.caseId,ActivityType.findByCode('EMAIL'))]"/>
            </div>
        </td>
    </tr>

    <tr class="prop toptable">
      <td valign="top" class="name">CDR Version When Created:</td>
      <td valign="top" class="value">${caseRecordInstance.cdrVer}</td>
    </tr>

    
      <tr class="prop">
        <td valign="top" class="name">Blood Timepoints:</td>
      <g:set var="chpBloodRecordFound" value="false" />
      <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
        <g:if test="${s.chpBloodRecord}">
          <g:set var="chpBloodRecordFound" value="true" />
        </g:if>
      </g:each>
      <g:if test="${chpBloodRecordFound == 'true'}">
        <td valign="top" class="value"><g:link controller="chpBloodRecord" action="listcase" id="${caseRecordInstance.id}">Available</g:link></td>
      </g:if>
      <g:else>
        <td valign="top" class="value">Not Available</td>
      </g:else>
      </tr>
      
      
      <g:if test="${session.DM == true}">
        <tr class="prop toptable">
          <td valign="top" class="name">QM Signature:</td>
          <g:set var="qmSignatureInstance" value="${ nci.obbr.cahub.datarecords.qm.CaseQMSignature.findByCaseRecord(caseRecordInstance) }"/>
          <g:set var="qmSignatureTag" value="${ qmSignatureInstance?.userId ? 'Yes' : 'No' }"/>
          <td valign="top" class="value"> <span class="${ qmSignatureTag.toLowerCase() }">${ qmSignatureTag }</span> <g:link  controller="caseQMSignature" action="edit" id="${caseRecordInstance.id}" >${ qmSignatureInstance?.userId ? '(View)' : '(Sign)' }</g:link></td>
        </tr>
      </g:if>

      <tr class="prop">
        <td valign="top" class="name">Tissue Timepoints:</td>
        <td valign="top" class="value">
      <g:set var="chpTissueRecordFound" value="false" />
      <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
        <g:if test="${s.containerType?.code == 'TRAY' && s.bpvTissueForm}">
          <g:set var="chpTissueRecordFound" value="true" />
          <g:link controller="chpTissueRecord" action="list" id="${s.specimenId}">${s.specimenId}</g:link> <br/>
        </g:if>
      </g:each>
      <g:if test="${chpTissueRecordFound == 'false'}">
        Not Available
      </g:if>
      </td>
      </tr>
    
    <tr class="prop">
      <td valign="top" class="name">Specimen Count:</td>
      <td valign="top" class="value">${caseRecordInstance.specimens.size()}</td>
    </tr>  
    <tr class="prop">
      <td valign="top" class="name">Final Surgical Review File:</td>
      <td valign="top" class="value">
    <g:if test="${caseRecordInstance.finalSurgicalPath}">
      <g:link controller="prcForm" action="downloadF" id="${caseRecordInstance.id}" ><img height="13" width="13" border="0" src="/cahubdataservices/images/download.png" alt="Download Final Surgical Review File" title="Download Final Surgical Review File" /></g:link> 
      <g:if test="${session.DM == true}">
        <g:link controller="prcForm" action="removeF" id="${caseRecordInstance.id}" onclick="return confirm('Are you sure you want to remove the file?')"><img height="13" width="13" border="0" src="/cahubdataservices/images/remove.png" alt="Remove Final Surgical Review File" title="Remove Final Surgical Review File" /></g:link>  
      </g:if>
    </g:if>
    <g:else>
      <g:if test="${session.DM == true}">
        <g:link controller="prcForm" action="uploadF" id="${caseRecordInstance.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/upload.png" alt="Upload Final Surgical Review File" title="Upload Final Surgical Review File"  /></g:link> 
      </g:if>
      <g:else>
        &nbsp;
      </g:else>
    </g:else>
    </td>
    </tr>    



    <!--  pmh 01/15/13 hold the case recall logic-->
    <g:if test="${false}">
      <g:if test="${session.DM == true}">
      <tr class="prop toptable">
        <td valign="top" class="name">Recall Case:</td>
      <g:if test="${!caseRecordInstance.caseWithdraw?.id}">
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Start Recall process </g:link></td>
      </g:if>
      <g:elseif test="${caseRecordInstance.caseWithdraw?.finalCompleteDate}">
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall completed</g:link></td>
      </g:elseif>
      <g:else>
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall in progress</g:link></td>
      </g:else>

      </tr>
      </g:if>
    </g:if>
    <!--end  pmh 01/15/13 -->
    
    <g:if test="${session.DM == true}">
        <tr class="prop toptable">
            <td valign="top" class="name">Query Tracker:</td>
            <td valign="top" class="value">
                <a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
            </td>
        </tr>
        
        <tr class="prop toptable">
            <td valign="top" class="name">Deviation list:</td>
            <td valign="top" class="value">
                <a href="/cahubdataservices/deviation/listByCase?caseRecord.id=${caseRecordInstance.id}">View Deviation list (<span class="${deviationCount == 0 ? 'yes' : 'no'}">${deviationCount}</span>)</a>
            </td>
        </tr>
    </g:if>

    <tr class="prop"><td valign="top" class="name formheader" colspan="2">Specimens:</td></tr>
    <tr>
      <td valign="top" class="value" colspan="2">
        <div class="list">

          <table>
            <thead>
              <tr>

                <th>1st Gen Specimen</th>

                <th>2nd Gen Specimen</th>

                <th>3rd Gen Specimen</th>

                <th>Organ</th>

                <th>Organ Location</th>

                <th>Fixative</th>

                <th>Container</th>

                <th>Consumed?</th>

                <th>Tumor Status</th>

                <th>Slides<g:if test="${session.PRC == true}">/PRC</g:if></th>

            </tr>
            </thead>
            <tbody>

            <g:each in="${caseRecordInstance.specimens.sort{a,b-> a.id.compareTo(b.id)}}" status="i" var="s">
              <g:if test="${!s.parentSpecimen}">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <td>
${s.specimenId}

                <g:if test="${s.internalComments && session.org.code != 'VARI'}">
                  <span class="ca-bubble" data-msg="<b>${s.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>                           
                </g:if>

                </td>                          
                <td></td>
                <td></td>
                <td>${s.tissueType}</td>

                <td>${s.tissueLocation}</td>

                <td>${s.fixative?.name}</td>

                <td>${s.containerType?.name}</td>

                <td><g:if test="${s.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            

                <td>${s.tumorStatus}</td>
                <td nowrap="nowrap">
                <g:each in="${s.slides?}" var="sl" status="l">
<%--begin prc slide--%>             
                  <span style="line-height:20px;">
                    <g:link controller="slideRecord" action="show" id="${sl.id}">${sl.slideId}</g:link>

                    <g:if test="${sl?.createdBy  && sl?.createdBy!= 'VARI'}">
                      <g:if test="${sl.localPathologyReview}">
                        <g:link controller="prcForm" action="downloadL" id="${sl.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/download.png" alt="Download Local Pathology Review File" title="Download Local Pathology Review File" /></g:link> 
                        <g:if test="${session.DM == true}">
                          <g:link controller="prcForm" action="removeL" id="${sl.id}" onclick="return confirm('Are you sure you want to remove the file?')"><img height="13" width="13" border="0" src="/cahubdataservices/images/remove.png" alt="Remove Local Pathology Review File" title="Remove Local Pathology Review File" /></g:link> 
                        </g:if>
                      </g:if>
                      <g:else>
                        <g:if test="${session.DM == true}">
                          <g:link controller="prcForm" action="uploadL" id="${sl.id}" ><img height="13" width="13" border="0" src="/cahubdataservices/images/upload.png" alt="Upload Local Pathology Review File" title="Upload Local Pathology Review File"  /></g:link> 
                        </g:if>
                      </g:else>
                      &nbsp;&nbsp;

                      <g:if test="${sl.prcForm}">
                        <g:link controller="prcForm" action="view" id="${sl.prcForm.id}" abc="a"><img height="13" width="13" border="0" src="/cahubdataservices/images/view.png" alt="View PRC Form" title="View PRC Form"   /></g:link>
                      </g:if>
                    </g:if>
                    <br />
                  </span>
<%--end prc slide--%>                               
                </g:each>

                </td>
              </g:if>                      
              </tr>
              <g:set var="slist" value="${SpecimenRecord.findAllByParentSpecimen(s)}" />
              <g:each in="${slist}" status="j" var="u">
                <g:if test="${!u.parentSpecimen.parentSpecimen}">
                  <tr>
                    <td></td>
                    <td>
${u.specimenId}

                  <g:if test="${u.internalComments  && session.org.code != 'VARI'}">
                    <span class="ca-bubble" data-msg="<b>${u.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>
                  </g:if>                      

                  </td>
                  <td></td>  
                  <td>${u.tissueType}</td>

                  <td>${u.tissueLocation}</td>                            

                  <td>${u.fixative?.name}</td>

                  <td>${u.containerType?.name}</td>

                  <td><g:if test="${u.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            

                  <td>${u.tumorStatus}</td>
                  <td nowrap="nowrap">
                  <g:each in="${u.slides?}" var="sl" status="k">
<%--begin prc slide--%>               
                    <span style="line-height:20px;">
${sl.slideId}




                      <g:if test="${sl?.createdBy  && sl?.createdBy!= 'VARI'}">

                        <g:if test="${sl.localPathologyReview}">
                          <g:link controller="prcForm" action="downloadL" id="${sl.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/download.png" alt="Download Local Pathology Review File" title="Download Local Pathology Review File" /></g:link> 
                          <g:if test="${session.DM == true}">
                            <g:link controller="prcForm" action="removeL" id="${sl.id}" onclick="return confirm('Are you sure you want to remove the file?')"><img height="13" width="13" border="0" src="/cahubdataservices/images/remove.png" alt="Remove Local Pathology Review File" title="Remove Local Pathology Review File" /></g:link> 
                          </g:if>
                        </g:if>
                        <g:else>
                          <g:if test="${session.DM == true}">
                            <g:link controller="prcForm" action="uploadL" id="${sl.id}" ><img height="13" width="13" border="0" src="/cahubdataservices/images/upload.png" alt="Upload Local Pathology Review File" title="Upload Local Pathology Review File"  /></g:link> 
                          </g:if>
                        </g:else>
                        &nbsp;&nbsp;

                        <g:if test="${sl.prcForm}">

                          <g:link controller="prcForm" action="view" id="${sl.prcForm.id}" abc="a"><img height="13" width="13" border="0" src="/cahubdataservices/images/view.png" alt="View PRC Form" title="View PRC Form"   /></g:link>
                        </g:if>
                      </g:if>

                      <br />
                    </span>
<%--end prc slide--%>                               
                  </g:each>
                  </td>                                               
                  </tr>
                </g:if>
                <g:set var="ulist" value="${SpecimenRecord.findAllByParentSpecimen(u)}" />                    
                <g:each in="${ulist}" status="k" var="v"> 

                  <tr>
                    <td></td>
                    <td></td>
                    <td>
${v.specimenId}
                  <g:if test="${v.internalComments && session.org.code != 'VARI'}">
                    <span class="ca-bubble" data-msg="<b>${v.internalComments?.replaceAll('"','&quot;')?.replaceAll('\r\n','<br />')}</b>"></span>                           
                  </g:if>                        

                  </td> 
                  <td>${v.tissueType}</td>

                  <td>${v.tissueLocation}</td>                            

                  <td>${v.fixative?.name}</td>

                  <td>${v.containerType?.name}</td>

                  <td><g:if test="${v.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            

                  <td>${v.tumorStatus}</td>
                  <td nowrap="nowrap">
                  <g:each in="${v.slides?}" var="sl2" status="m">
<%--begin prc slide--%>                               
                    <span style="line-height:20px;">
                      <g:link controller="slideRecord" action="show" id="${sl.id}">${sl.slideId}</g:link>

                      <g:if test="${sl?.createdBy  && sl?.createdBy!= 'VARI'}">
                        <g:if test="${sl.localPathologyReview}">
                          <g:link controller="prcForm" action="downloadL" id="${sl.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/download.png" alt="Download Local Pathology Review File" title="Download Local Pathology Review File" /></g:link> 
                          <g:if test="${session.DM == true}">
                            <g:link controller="prcForm" action="removeL" id="${sl.id}" onclick="return confirm('Are you sure you want to remove the file?')"><img height="13" width="13" border="0" src="/cahubdataservices/images/remove.png" alt="Remove Local Pathology Review File" title="Remove Local Pathology Review File" /></g:link> 
                          </g:if>

                        </g:if>
                        <g:else>
                          <g:if test="${session.DM == true}">
                            <g:link controller="prcForm" action="uploadL" id="${sl.id}" ><img height="13" width="13" border="0" src="/cahubdataservices/images/upload.png" alt="Upload Local Pathology Review File" title="Upload Local Pathology Review File"  /></g:link> 
                          </g:if>
                        </g:else>
                        &nbsp;&nbsp;

                        <g:if test="${sl.prcForm}">
                          <g:link controller="prcForm" action="view" id="${sl.prcForm.id}" abc="a"><img height="13" width="13" border="0" src="/cahubdataservices/images/view.png" alt="View PRC Form" title="View PRC Form"   /></g:link>
                        </g:if>
                      </g:if>
                      <br />
                    </span>
<%--end prc slide--%>                               
                  </g:each>
                  </td>                                               
                  </tr>
                </g:each>
              </g:each>       
            </g:each>
            </tbody></table>
        </div>
      </td>

    </tr>




    </tbody>
  </table>

</div>
<div class="buttons">


</div>

