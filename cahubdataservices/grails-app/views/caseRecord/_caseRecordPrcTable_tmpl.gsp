
<g:javascript>
   function makeCall(div_id, case_id){
       // alert("div_id: " + div_id)
        if(confirm("Are you sure you want to make a request for case " + case_id +"?")){
        $.ajax({
              type:'POST',
              dataType: "text", 
              data: ({case_id:div_id}),
              url:'/cahubdataservices/prcReport/makeReq',
              success:function(data,textStatus){changeField(data,textStatus);},
              error:function(XMLHttpRequest,textStatus,errorThrown){}
            
           });
         }
    }
    
    function changeField(data,textStatus){
        var obj= jQuery.parseJSON(data)
        document.getElementById(obj.div_id).innerHTML = '<img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add1.png" title="Create PRC report Fzn"/>'
    }
   
</g:javascript>

 

<table>
   <thead>
     <tr><th colspan="11">GTEX Case List</th></tr>
        <tr>
          <th>QC Flag</th>
          <th>Case ID</th>                     
          <th>Case Status</th>  
          <th>Gender</th>
          <th>Age</th>
          <th>Cause of Death</th>
          <th class="specimencount">Specimens</th>
          <th>Issue Count Total</th>
          <th>PRC Case Status</th>
          <th>PRC Report</th>
          <th>PRC Report FZN <span class="ca-icicle"></span></th>
        </tr>
   </thead><tbody>
      <g:if test="${caseList}">
         <g:each in="${caseList}" status="i" var="c">
             <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
               <td>
                 <g:if test="${c.qcFlag=='black'}">
                 <img height="15" width="15" border="0" src="/cahubdataservices/images/flag_mark_gray.png" />
                 </g:if>
                 <g:elseif test="${c.qcFlag=='green'}">
                   <img height="15" width="15" border="0" src="/cahubdataservices/images/flag_mark_green.png" />
                 </g:elseif>
             <g:elseif test="${c.qcFlag=='red'}" >
               <img height="15" width="15" border="0" src="/cahubdataservices/images/flag_mark_red.png" />
             </g:elseif>
             <g:else>
               &nbsp;
             </g:else>
        </td>
                <td><g:link controller="caseRecord" action="display" id="${c.id}">${c.caseId}</g:link></td>
                <td><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${c.status}</b>. ${c.statusdesc}">${c.status}</span></td>
                <td>${c.gender}</td>
                <td>${c.age}</td>
                <td>${c.causeOfDeath}</td>
                <td>${c.specimenCount}</td>
                <td>${c.issueTotal}</td>
                <td>${c.unresolvedCount}</td>
            <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC'))}">
              <g:if test ="${c.prcReport!= null && c.prcReport.status=='Editing' && c.feedback== null}">
                 <td><g:link controller="prcReport" action="edit" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report"/></g:link>&nbsp;<g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
              </g:if>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='Editing' && c.feedback != null && c.feedback.status=='Editing'}">
                <td><g:link controller="prcReport" action="edit" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report"/></g:link>&nbsp;<g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="edit" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit2.png" title="Edit procurement feedback"/></g:link>&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td>
              </g:elseif>
               <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='Editing' && c.feedback != null && c.feedback.status=='Submitted'}">
                <td><g:link controller="prcReport" action="edit" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report"/></g:link>&nbsp;<g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td>
              </g:elseif>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='Submitted' && c.feedback== null}">
                  <td><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td> 
              </g:elseif>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='Submitted' && c.feedback!= null && c.feedback.status=='Editing'}">
                  <td><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="edit" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit2.png" title="Edit procurement feedback"/></g:link>&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td> 
              </g:elseif>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='Submitted' && c.feedback!= null && c.feedback.status=='Submitted'}">
                  <td><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td> 
              </g:elseif>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='QCEditing' && c.feedback== null}">
               <td><g:link controller="prcReport" action="qc" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit3.png" title="Pathologist QC Review"/>&nbsp;</g:link><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td> 
              </g:elseif>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='QCEditing' && c.feedback!= null && c.feedback.status=='Editing'}">
                <td><g:link controller="prcReport" action="qc" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit3.png" title="Pathologist QC Review"/>&nbsp;</g:link><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="edit" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit2.png" title="Edit procurement feedback"/></g:link>&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td> 
              </g:elseif>
              <g:elseif test ="${c.prcReport!= null && c.prcReport.status=='QCEditing' && c.feedback!= null && c.feedback.status=='Submitted'}">
                <td><g:link controller="prcReport" action="qc" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit3.png" title="Pathologist QC Review"/>&nbsp;</g:link><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td> 
              </g:elseif>
              <g:elseif test="${c.prcReport== null && c.specimenCount != '--'}">
                 <td><a href="/cahubdataservices/prcReport/save?caseRecord.id=${c.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add2.png" title="Create PRC report" /></a></td>
               </g:elseif>
               <g:elseif test="${c.prcReport== null && c.specimenCount == '--'}">
                 <td><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add1.png" title="Create PRC report" /></td>
               </g:elseif>
              <g:else>
                <td>report</td>
              </g:else>
              
              <g:if test="${c.prcReportFzn== null && c.specimenCountFzn == '--'}">
                 <g:if test="${c.hasFr && c.requestMade}">
                 <td class="pink"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add1.png" title="Create PRC report Fzn"/></td>
                  </g:if>
                 <g:elseif test="${c.hasFr && !c.requestMade && session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC')}">
                     <g:if test="${c.received}">
                     <td class="pink"> <div id ="${c.id}"><a href="#" title="Make a request" onclick="makeCall('${c.id}', '${c.caseId}')"> <img height="13" width="13" border="0" src="/cahubdataservices/images/send_2.png" /></a></div></td>
                     </g:if>
                   <g:else>
                      <td class="pink">  <img height="13" width="13" border="0" src="/cahubdataservices/images/send_1.png" title="Make a request" /></td>
                   </g:else>  
                 </g:elseif>
                  <g:elseif test="${c.hasFr && !c.requestMade && !session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC')}">
                     <td class="pink">&nbsp;</td>
                  </g:elseif>
                  <g:else><td>&nbsp;</td></g:else>
              </g:if>
              <g:elseif test="${c.prcReportFzn== null && c.specimenCountFzn != '--'}">
                 <td class="pink"><a href="/cahubdataservices/prcReportFzn/save?caseRecord.id=${c.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add2.png" title="Create PRC report Fzn"/></a></td>
              </g:elseif>
               <g:elseif test ="${c.prcReportFzn!= null && c.prcReportFzn.status=='Editing' && c.feedbackFzn==null}">
                 <td class="pink"><g:link controller="prcReportFzn" action="edit" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report Fzn"/></g:link>&nbsp;<g:link controller="prcReportFzn" action="view" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link></td>
                </g:elseif>
               <g:elseif test ="${c.prcReportFzn!= null && c.prcReportFzn.status=='Editing' && c.feedbackFzn!=null && c.feedbackFzn.status=='Editing'}">
                 <td class="pink"><g:link controller="prcReportFzn" action="edit" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report Fzn"/></g:link>&nbsp;<g:link controller="prcReportFzn" action="view" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link>&nbsp;&nbsp;<g:link controller="feedbackFzn" action="edit" id="${c.feedbackFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit2.png" title="Edit procurement feedback Fzn"/></g:link>&nbsp;<g:link controller="feedbackFzn" action="view" id="${c.feedbackFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback Fzn" /></g:link></td>
                </g:elseif>
              <g:elseif test ="${c.prcReportFzn!= null && c.prcReportFzn.status=='Editing' && c.feedbackFzn!=null && c.feedbackFzn.status=='Submitted'}">
                 <td class="pink"><g:link controller="prcReportFzn" action="edit" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report Fzn"/></g:link>&nbsp;<g:link controller="prcReportFzn" action="view" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link>&nbsp;&nbsp;<g:link controller="feedbackFzn" action="view" id="${c.feedbackFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback Fzn" /></g:link></td>
                </g:elseif>
              <g:elseif test ="${c.prcReportFzn!= null && c.prcReportFzn.status=='Submitted' && c.feedbackFzn==null}">
                 <td class="pink"><g:link controller="prcReportFzn" action="view" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link></td>
                </g:elseif>
               <g:elseif test ="${c.prcReportFzn!= null && c.prcReportFzn.status=='Submitted' && c.feedbackFzn!=null && c.feedbackFzn.status=='Editing'}">
                 <td class="pink"><g:link controller="prcReportFzn" action="view" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link>&nbsp;&nbsp;<g:link controller="feedbackFzn" action="edit" id="${c.feedbackFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit2.png" title="Edit procurement feedback Fzn"/></g:link>&nbsp;<g:link controller="feedbackFzn" action="view" id="${c.feedbackFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback Fzn" /></g:link></td>
                </g:elseif>
                <g:elseif test ="${c.prcReportFzn!= null && c.prcReportFzn.status=='Submitted' && c.feedbackFzn!=null && c.feedbackFzn.status=='Submitted'}">
                 <td class="pink"><g:link controller="prcReportFzn" action="view" id="${c.prcReportFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link>&nbsp;&nbsp;<g:link controller="feedbackFzn" action="view" id="${c.feedbackFzn.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback Fzn" /></g:link></td>
                </g:elseif>
              <g:else>
              <td>report Fzn</td>
              </g:else>
            </g:if>
             <g:else>
               <g:if test ="${c.prcReport!= null && c.feedback== null}">
                  <td><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
               </g:if>
               <g:elseif test ="${c.prcReport!= null && c.feedbackFzn != null}">
                  <td><g:link controller="prcReport" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link>&nbsp;&nbsp;<g:link controller="feedback" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback" /></g:link></td>
               </g:elseif>
               <g:else>
               <td>&nbsp;</td>
               </g:else>
               
                 <g:if test ="${c.prcReportFzn!= null && c.feedbackFzn== null}">
                  <td><g:link controller="prcReportFzn" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link></td>
               </g:if>
               <g:elseif test ="${c.prcReportFzn!= null && c.feedbackFzn != null}">
                  <td><g:link controller="prcReportFzn" action="view" id="${c.prcReport.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report Fzn" /></g:link>&nbsp;&nbsp;<g:link controller="feedbackFzn" action="view" id="${c.feedback.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view2.png" title="View procurement feedback Fzn" /></g:link></td>
               </g:elseif>
               <g:else>
               <td>&nbsp;</td>
               </g:else>
               
              
            
             </g:else>
            </tr>
        </g:each>
      </g:if>
    </tbody>
</table>

