    <g:set var="acquisitionLocList" value="${nci.obbr.cahub.staticmembers.AcquisitionLocation.list(sort:'name')}" />
    <%-- Create acquisitionLoc drop down manually only once to speed up page performance --%>
    <% def acquisitionLocOptionsStr = "" %><g:if test="${bodyclass.indexOf('view') == -1}"><% acquisitionLocOptionsStr = "<option value=\"-1\">Choose Tissue Location</option>" %></g:if><g:else><% acquisitionLocOptionsStr = "<option value=\"-1\"></option>" %></g:else><g:each var="i" in="${ (0..<acquisitionLocList.size) }"><% acquisitionLocOptionsStr += "<option value=\"" + acquisitionLocList.id[i] + "\">" + acquisitionLocList[i] + "</option>"%></g:each>
    <% def  specimensTRF = tissueRecoveryGtexInstance.caseRecord.specimens.sort { it.aliquotTimeRemoved } %>

      <table id="aquaKit">
        <tr><td colspan="9" class="aqua-kit-header">Aqua Kit<g:remoteLink update="[success: 'aquaSection']" onSuccess="aquaRefreshJs()" id="${tissueRecoveryGtexInstance.id}" action="updateAqua" title="Refresh Aqua Kit & Validate order by Time Removed" class="uibutton right aquarefresh"><span class="ui-icon ui-icon-refresh"></span>Refresh & Validate Order</g:remoteLink></td></tr>
        <%def aquaCnt = 0%>
        <g:if test="${bodyclass.indexOf('view') == -1}">
        <tr>
          <th>Specimen Id</th>
          <th class="tissue-type-h">Tissue Type</th>
          <th>Tissue Location</th>
          <th>Fixative</th>
          <th class="dateentrynarr">Time Removed</th>
          <th class="dateentrynarr">Time Put in Fixative</th>
          <th class="dateentrynarr">Time Placed in Stabilizer</th>                                      
          <th class="dateentrynarr">Size if Different than SOP</th>
          <th>Comments</th>
        </tr>
          <g:each in="${specimensTRF}" var="a" status="i">
            <g:if test="${!(a.gtexSequenceNum() in['0011','0014','0001','0002','0003','0004','0005','0006','0008', '0007', '0009', '0010']) && a.fixative.code != 'DICE'}">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'} aqua-row" id="aqua-${aquaCnt++}">
                <td class="itemid">${a}</td>
                <td>${a.tissueType}</td>
                <td class="td_tissueLocation">
                  <select name="tissueLocation.id" id="tissueLocation_${a.id}" data-currentid="${a.id}" data-value="${a?.tissueLocation?.id}">
                     <%=acquisitionLocOptionsStr.replace("value=\"" + a?.tissueLocation?.id + "\"","value=\"" + a?.tissueLocation?.id + "\" selected")%>
                  </select>
                  <div class="depends-on" data-id="tissueLocation_${a.id}" data-select="28">
                    <span id="tl_${a.id}" class="labelstr otherTissueLocation otherTissueLocation${a.id}">Enter other location: </span>
                    <g:textField  id="otherTissueLocation_${a.id}" class="otherTissueLocation" data-currentid="${a.id}" name="otherTissueLocation${a.id}" value="${a?.otherTissueLocation}" />
                  </div>
                </td>  
                <td>${a.fixative}</td>
                <td class="td_aliquotTimeRemoved clearfix">
                  <g:textField data-currentid="${a.id}" id="aliquotTimeRemoved_${a.id}" name="aliquotTimeRemoved" value="${a?.aliquotTimeRemoved}" class="timeEntry gtexQuickSave" data-param="aliquotTimeRemoved" />
                  <br><a class="uibutton removepadding borderless left ca-tooltip-nobg" data-msg="If Time Removed exceeded 20 minutes before last collection, please explain in comments." ><span class="ui-icon ui-icon-info"></span></a>
                </td>
                <td class="td_aliquotTimeFixed clearfix">
                  <g:textField data-currentid="${a.id}" id="aliquotTimeFixed_${a.id}" name="aliquotTimeFixed" value="${a?.aliquotTimeFixed}" class="timeEntry gtexQuickSave" data-param="aliquotTimeFixed" />
                  <br><a class="uibutton removepadding borderless left ca-tooltip-nobg" data-msg="If Time Put in Fixative exceeded 20 minutes after Time Removed, please explain in comments." ><span class="ui-icon ui-icon-info"></span></a>
                </td>                                                                                   
                <td class="td_aliquotTimeStabilized clearfix">
                  <g:textField data-currentid="${a.id}" id="aliquotTimeStabilized_${a.id}" name="aliquotTimeStabilized" value="${a?.aliquotTimeStabilized}" class="timeEntry gtexQuickSave" data-param="aliquotTimeStabilized" />
                  <br><a class="uibutton removepadding borderless left ca-tooltip-nobg" data-msg="If Time Placed in Stabilizer was under 6 hours after Time Put in Fixative, please explain in comments." ><span class="ui-icon ui-icon-info"></span></a>
                </td>                                      
                <td class="td_sizeDiffThanSOP">
                  <g:textField data-currentid="${a.id}" id="sizeDiffThanSOP_${a.id}" name="sizeDiffThanSOP" value="${a?.sizeDiffThanSOP}" class="gtexQuickSave" data-param="sizeDiffThanSOP" />
                </td>                                                                            
                <td class="td_prosectorComments">
                  <g:textArea data-currentid="${a.id}" id="prosectorComments_${a.id}" name="prosectorComments" value="${a?.prosectorComments}" class="gtexQuickSave" data-param="prosectorComments" />
                </td>
              </tr>
            </g:if>
          </g:each>
        </g:if>
        <g:else>
        <tr>
          <th>Specimen Id</th>
          <th class="tissue-type-h">Tissue Type</th>
          <th>Tissue Location</th>
          <th>Fixative</th>
          <th class="dateentrynarr">Time Removed<br/><i class="ca-tooltip-nobg" data-msg="<b>Time Removed:</b> An asterix next to the time in this column indicates Time Removed exceeded 20 minutes before last collection.">(*)</i></th>
          <th class="dateentrynarr">Time Put in Fixative<br/><i class="ca-tooltip-nobg" data-msg="<b>Time Put in Fixative:</b> An asterix next to the time in this column indicates Time Put in Fixative exceeded 20 minutes after Time Removed.">(*)</i></span></th>
          <th>Time Placed in Stabilizer<br/><i class="ca-tooltip-nobg" data-msg="<b>Time Placed in Stabilizer:</b> An asterix next to the time in this column indicates Time Placed in Stabilizer was under 6 hours, after Time Put in Fixative">(*)</i></span></th>                                      
          <th class="dateentrynarr">Size if Different than SOP</th>
          <th>Comments</th>
        </tr>
          <g:each in="${specimensTRF}" var="a" status="i">
            <g:if test="${!(a.gtexSequenceNum() in['0011','0014','0001','0002','0003','0004','0005','0006','0008', '0007', '0009', '0010']) && a.fixative.code != 'DICE'}">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'} aqua-row" id="aqua-${aquaCnt++}">
                <td class="itemid">${a}</td>
                <td>${a.tissueType}</td>
                <td class="td_tissueLocation">
                  <select name="tissueLocation.id" id="tissueLocation_${a.id}" data-currentid="${a.id}" data-value="${a?.tissueLocation?.id}">
                    <%=acquisitionLocOptionsStr.replace("value=\"" + a?.tissueLocation?.id + "\"","value=\"" + a?.tissueLocation?.id + "\" selected")%>
                  </select>
                  <div class="depends-on" data-id="tissueLocation_${a.id}" data-select="28">
                    <span id="tl_${a.id}" class="labelstr otherTissueLocation otherTissueLocation${a.id}">Enter other location: </span>
                    <g:textField class="otherTissueLocation" data-currentid="${a.id}" name="otherTissueLocation${a.id}" value="${a?.otherTissueLocation}" />
                  </div>
                </td>  
                <td>${a.fixative}</td>
                <td class="td_aliquotTimeRemoved"><span class="timeEntry">${a?.aliquotTimeRemoved}</span><span class="indicator">*</span></td>
                <td class="td_aliquotTimeFixed"><span class="timeEntry">${a?.aliquotTimeFixed}</span><span class="indicator">*</span></td>                                                                                   
                <td class="td_aliquotTimeStabilized"><span class="timeEntry">${a?.aliquotTimeStabilized}</span><span class="indicator">*</span></td>                                      
                <td class="td_sizeDiffThanSOP"><span class="timeEntry">${a?.sizeDiffThanSOP}</span></td>                                                                            
                <td class="td_prosectorComments"><span class="timeEntry">${a?.prosectorComments?.replace("\n","<br />")}</span></td>
              </tr>
            </g:if>
          </g:each>
        </g:else>
      </table> 
      <p><g:remoteLink update="[success: 'aquaSection']" onSuccess="aquaRefreshJs()" id="${tissueRecoveryGtexInstance.id}" action="updateAqua" title="Refresh Aqua Kit & Validate order by Time Removed" class="uibutton aquarefresh"><span class="ui-icon ui-icon-refresh"></span>Refresh & Validate Order</g:remoteLink></p>
    
