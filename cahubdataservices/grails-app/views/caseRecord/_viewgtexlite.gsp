<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.ShippingEvent" %>

<script type="text/javascript" src="${resource(dir:'js',file:'caserecord.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>

<div class="dialog">
  <table border="0">
    <tbody>

      <tr class="prop toptable">
        <td valign="top" class="name">Case ID:</td>

        <td valign="top" class="value" id="caseRecordID">${caseRecordInstance.caseId}</td>

      </tr>     
      <tr class="prop toptable">    
        <td valign="top" class="name">Collection Type:</td>

        <td valign="top" class="value">${caseRecordInstance.caseCollectionType}</td>
      </tr>    
      <tr class="prop toptable">
        <td valign="top" class="name">Case Status:</td>

        <td valign="top" class="value" >
          <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>

        </td>

      </tr>
      <tr class="prop toptable">
        <td valign="top" class="name">BSS:</td>

        <td valign="top" class="value">${caseRecordInstance?.bss.code}</td>
      </tr>

<%-- hide for Broad --%>

    <g:if test="${session.org.code != 'BROAD'}">

      <tr class="prop toptable">
        <td valign="top" class="name">Study:</td>

        <td valign="top" class="value">${caseRecordInstance.study}</td>
      </tr>

      <tr class="prop toptable">
        <td valign="top" class="name"><g:message code="caseRecord.kits.label" default="Kits Used:" /></td>

      <td valign="top" class="value unlimitedstr">
        <div>
          <g:if test="${caseRecordInstance.kitList}">
${caseRecordInstance.kitList.replace(',',', ')}
          </g:if>
          <g:else>&nbsp;</g:else>
        </div>
      </td>

      </tr>

    </g:if>


    <tr class="prop toptable">
      <td valign="top" class="name"><g:message code="caseRecord.dateCreated.label" default="Date Created:" /></td>

    <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.dateCreated}" /></td>

    </tr>

    <tr  class="prop toptable">
      <td class="name">PRC Summary Report</td>
    <g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate != null}">
      <td> <g:link controller="prcReport" action="view" id="${caseRecordInstance.prcReport.id}"><span class="yes">Yes</span> (View)</g:link>
      </td>
    </g:if>
    <g:if test="${caseRecordInstance.prcReport && caseRecordInstance.prcReport?.reviewDate == null}">
      <td><span class="incomplete">In Progress</span></td>
    </g:if>
    <g:if test="${!caseRecordInstance.prcReport}">
      <td><span class="no">No</span> (Not Available)</td>
    </g:if>
    </tr>


    <tr class="prop toptable">
      <td valign="top" class="name">De-identified Data Export:</td>
    <g:if test ="${caseRecordInstance.caseStatus?.code=='RELE'}">
      <td valign="top" class="value"><a target="_blank" href="${createLink(uri: '/')}rest/gtexdonorvars/${caseRecordInstance.caseId}"><span class="yes">Yes</span> (View)</a></td>
    </g:if>
    <g:else>
      <td valign="top" class="value"><span class="no">No</span> (Not Available – case not released)</td>
    </g:else>

    </tr>      

<%-- hide for Broad --%>

    <g:if test="${session.org.code != 'BROAD'}">    

      <tr class="prop toptable">
        <td valign="top" class="name">CBR Shipping Events:</td>
        <td valign="top" class="value">
      <g:if test="${ShippingEvent.findByCaseId(caseRecordInstance.caseId)}">
        <span class="yes">Yes</span> <g:link controller="shippingEvent" action="listByCase" params="[caseId:caseRecordInstance.caseId]"> (View)</g:link>
      </g:if>
      <g:else>
        <span class="no">No</span> (Not available)
      </g:else>
      </td> 
      </tr>    
    </g:if>
    <g:if test="${session.DM == true}">

      <tr class="prop toptable">
        <td valign="top" class="name">Recall Case:</td>
      <g:if test="${!caseRecordInstance.caseWithdraw?.id}">
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}" onclick="return confirm(' Are you sure you want to recall this case?')">Start Recall process </g:link></td>
      </g:if>
      <g:elseif test="${caseRecordInstance.caseWithdraw?.finalCompleteDate}">
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall completed</g:link></td>
      </g:elseif>
      <g:else>
        <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall in progress</g:link></td>
      </g:else>

      </tr>
    </g:if>
    
   <g:if test="${caseRecordInstance.caseWithdraw?.id && caseRecordInstance.caseWithdraw.assignToCBR && session.org.code == 'VARI'}">  
     <tr class="prop toptable">
        <td valign="top" class="name">Recall Case:</td>
    <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall in progress</g:link></td>
    </tr>
    </g:if>
    
    
    <g:if test="${caseRecordInstance.caseWithdraw?.id && caseRecordInstance.caseWithdraw.assignToLDACC && session.org.code == 'BROAD'}">  
     <tr class="prop toptable">
        <td valign="top" class="name">Recall Case:</td>
    <td> <g:link  controller="caseWithdraw" action="wdcmain" id="${caseRecordInstance.id}">Recall in progress</g:link></td>
    </tr>
    </g:if>
    
    <tr class="prop toptable">
      <td valign="top" class="name">Query Tracker:</td>
      <td valign="top" class="value">
        <a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
      </td>
    </tr>

    <tr class="prop"><td valign="top" class="name formheader" colspan="3">Specimen Summary (${caseRecordInstance.specimens.size()}):</td></tr>
    <tr>
      <td valign="top" class="value" colspan="3">
        <div class="list">
          <table class="nowrap">
            <thead>
              <tr>

                <th>Specimen ID</th>

                <th>Tissue Type</th>

                <th>Fixative</th>

                <th>Container</th>
<%-- hide for Broad --%>

            <g:if test="${session.org.code != 'BROAD'}">                
              <th>Slide ID(s)</th>

              <th>Image ID(s)</th>
                 <th>Inv Status</th> 
            </g:if>
            </tr>
            </thead>
            <tbody>
            <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" id="${s.specimenId.toUpperCase()}">

                <td class="itemid">${s.specimenId}</td>

                <td>${s.tissueType}</td>

                <td>${s.fixative}</td>
                <td>${s.containerType}</td>
<%-- hide for Broad --%>

              <g:if test="${session.org.code != 'BROAD'}">                
                <td>
                <g:each in="${s.slides?}" var="sl" status="l">
${sl.slideId}
                  <br />
                </g:each>
                </td>

                <td>
                <g:each in="${s.slides?}" var="sl" status="l">
${sl.imageRecord?.imageId}
                  <br />
                </g:each>                
                </td>  
                  <td id="${s.id}"><span class="sp_status"></span></td>                
              </g:if>
              </tr>
            </g:each>
            </tbody>
          </table>
        </div>
      </td>

    </tr>

    </tbody>
  </table>
</div>
