
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:javascript>
  
           
    $(document).ready(function(){
        
       $(".signOff").change(function(){
         
           var name=this.name
           var id = this.id
           var checked = this.checked
           //alert("name: " + name + " checked: " + checked + " id: " +id)
           
           $.ajax({
              type:'POST',
              dataType: "text", 
              data: ({id:this.id, checked:this.checked}),
              url:'/cahubdataservices/prcReport/sign_off',
            
           });
           
        });
        
    });
    
     function openImageWin(image_id){
             //var w2=window.open('https://microscopy.vai.org/imageserver/@@/@'+image_id + '/view.apml', 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
              var w2=window.open('${AppSetting.findByCode("APERIO_URL").value}'+image_id, 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
              w2.focus();
        }
</g:javascript>
<table>
   <thead>
      <tr><th colspan="10">BPV Case List</th></tr>
      <tr>
          <th>Sign Off</th>
          <th>Case ID</th> 
          <th>Primary Organ</th>  
          <th>Case Status</th>
          <th>Slide Id</th>
          <th>Module</th>
          <th>Image Id</th>
          <th>PRC Report</th>
          <th>Local Path Report</th>
          <th>Surgical Pathology Report</th>
      </tr>
  </thead><tbody>
   <g:if test="${caseListBpv}">
     <g:each in="${caseListBpv}" status="i" var="c">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" >
        <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC'))}"><td rowspan="${c.slides?.size() == 0 ? 1 : c.slides?.size()}"> <g:checkBox name="prcSignOff${c.id}" class="signOff" id="${c.id}" value="${c.prcSignOff}" /></td></g:if>
        <g:else><td rowspan="${c.slides?.size() == 0 ? 1 : c.slides?.size()}"> <g:checkBox name="prcSignOff${c.id}" class="signOff" id="${c.id}" value="${c.prcSignOff}" DISABLED="true" /></td></g:else>
        <td rowspan="${c.slides?.size() == 0 ? 1 : c.slides?.size()}">${c.caseId}</td>
        <td rowspan="${c.slides?.size() == 0 ? 1 : c.slides?.size()}">${c.primaryOrgan}</td>
        <td rowspan="${c.slides?.size() == 0 ? 1 : c.slides?.size()}"><span class="ca-tooltip-nobg home-case-status" data-msg="<b>${c.status}</b>. ${c.statusdesc}">${c.status}</span></td>
    <g:if test="${c.slides?.size() > 0}">
     <g:each in="${c.slides}" var="sl" status="j">
       <g:if test="${j==0}">
        <td>${sl.slideId}</td>
        <td>${sl.module?.name}</td>
        <td><a href="javascript:openImageWin('${sl.imageRecord?.imageId}')">${sl.imageRecord?.imageId}</a></td>
        <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC'))}">
           <g:if test ="${sl.bpvPrcPathReview && sl.bpvPrcPathReview.status=='Editing'}">
              <td><g:link controller="bpvPrcPathReview" action="edit" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report"/></g:link>&nbsp;<g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
           </g:if>
           <g:if test ="${sl.bpvPrcPathReview && sl.bpvPrcPathReview.status!='Editing'}">
              <td><g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
           </g:if><g:if test="${sl.bpvPrcPathReview== null && sl.imageRecord}">
              <td><a href="/cahubdataservices/bpvPrcPathReview/save?caseRecord.id=${c.id}&slideRecord.id=${sl.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add2.png" title="Create PRC report" /></a></td>
           </g:if><g:if test="${sl.bpvPrcPathReview== null && !sl.imageRecord}">
              <td><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add1.png" title="Create PRC report" /></td>
           </g:if>
        </g:if><g:else><g:if test="${sl.bpvPrcPathReview}"><td><g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td></g:if><g:else><td>&nbsp;</td></g:else></g:else>
        <g:if test="${sl.bpvLocalPathReview}"><td><g:link controller="bpvLocalPathReview" action="view" id="${sl.bpvLocalPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td></g:if><g:else><td>--</td></g:else>
      </g:if>
    </g:each>
   </g:if><g:else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></g:else>
      <td rowspan="${c.slides?.size() == 0 ? 1 : c.slides?.size()}"><g:if test="${c.finalSurgicalPath}"><g:link controller="bpvLocalPathReview" action="download" id="${c.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/download.png" title="Download" /></g:link></g:if><g:else>&nbsp;</g:else></td>
   </tr>
   <g:each in="${c.slides}" var="sl" status="j">
      <g:if test="${j > 0}">
         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
           <td>${sl.slideId}</td>
           <td>${sl.module?.name}</td>
           <td><a href="javascript:openImageWin('${sl.imageRecord?.imageId}')">${sl.imageRecord?.imageId}</a></td>
           <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC'))}">
               <g:if test ="${sl.bpvPrcPathReview && sl.bpvPrcPathReview.status=='Editing'}">
                    <td><g:link controller="bpvPrcPathReview" action="edit" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_edit1.png" title="Edit PRC report"/></g:link>&nbsp;<g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
               </g:if><g:if test ="${sl.bpvPrcPathReview && sl.bpvPrcPathReview.status!='Editing'}">
                    <td><g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td>
               </g:if><g:if test="${sl.bpvPrcPathReview == null && sl.imageRecord}">
                    <td><a href="/cahubdataservices/bpvPrcPathReview/save?caseRecord.id=${c.id}&slideRecord.id=${sl.id}" ><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add2.png" title="Create PRC report" /></a></td>
               </g:if><g:if test="${sl.bpvPrcPathReview== null && !sl.imageRecord}">
                    <td><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_add1.png" title="Create PRC report" /></td>
               </g:if>
          </g:if><g:else>
               <g:if test="${sl.bpvPrcPathReview}"><td><g:link controller="bpvPrcPathReview" action="view" id="${sl.bpvPrcPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td></g:if><g:else><td>&nbsp;</td></g:else>
          </g:else>
               <g:if test="${sl.bpvLocalPathReview}"><td ><g:link controller="bpvLocalPathReview" action="view" id="${sl.bpvLocalPathReview.id}"><img height="13" width="13" border="0" src="/cahubdataservices/images/prc_view1.png" title="View PRC report" /></g:link></td></g:if><g:else><td>--</td></g:else>
         </tr>
       </g:if>
     </g:each>
    </g:each>
   </g:if>
  </tbody>
</table>

