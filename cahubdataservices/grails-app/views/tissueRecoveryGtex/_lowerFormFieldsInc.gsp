    <g:set var="acquisitionLocList" value="${nci.obbr.cahub.staticmembers.AcquisitionLocation.list(sort:'name')}" scope="request"/>
    <%-- Create acquisitionLoc drop down manually only once to speed up page performance --%>
    <% def acquisitionLocOptionsStr = "" %><g:if test="${bodyclass.indexOf('view') == -1}"><% acquisitionLocOptionsStr = "<option value=\"-1\">Choose Tissue Location</option>" %></g:if><g:else><% acquisitionLocOptionsStr = "<option value=\"-1\"></option>" %></g:else><g:each var="i" in="${ (0..<acquisitionLocList.size) }"><% acquisitionLocOptionsStr += "<option value=\"" + acquisitionLocList.id[i] + "\">" + acquisitionLocList[i] + "</option>"%></g:each>
    <% def  specimensTRF = tissueRecoveryGtexInstance.caseRecord.specimens.sort { it.aliquotTimeRemoved }%>
    <h2>Specimen Tissue Recovery Data Section</h2>
    <g:if test="${brainFlag == true}"><div id="greenKitNav" class="kitNav">Jump to kit: <div class="jump-to" data-goto="yellowKitNav"><span class="yellowLink">&nbsp;</span> Yellow</div> | <div class="jump-to" data-goto="aquaKitNav"><span class="aquaLink">&nbsp;</span> Aqua</div> <g:if test="${frozenCaseNum > 0}">| <div class="jump-to" data-goto="pinkKitNav"><span class="pinkLink">&nbsp;</span> Pink</div></g:if></div>        
    <span class="tablefooter">Instructions: Enter the data below for each specimen in each kit.  Data is automatically saved when you move away from a field.</span>
      <%--begin TRF specimen fields.  These pull from the caseRecordInstance put into context by the controller --%>
        <g:if test="${brainFlag == true}"><table id="greenKit">
            <tr><td colspan="9" class="green-kit-header">Green Kit</td></tr>
            <tr>
              <th class="no-wrap">Specimen Id</th>
              <th class="tissue-type-h">Tissue Type</th>
              <th>Start Brain<br />Removal</th>
              <th class="no-wrap">End Brain<br />Aliquot Prep</th>
              <th class="no-wrap">Time Head<br />Put on Ice</th>
              <th>Comments</th>
            </tr><g:each in="${specimensTRF}" var="a" status="i"><g:if test="${a.gtexSequenceNum() in ['0011','0014'] && (a.tissueType.code=='BRAIN' || a.tissueType.code =='HAIR')}">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td class="itemid">${a}</td>
              <td>${a.tissueType}</td>                          
              <td class="td_brainTimeStartRemoval"><g:if test="${a.gtexSequenceNum() == '0011'}"><g:textField data-currentid="${a.id}" id="brainTimeStartRemoval_${a.id}" name="brainTimeStartRemoval" value="${a?.brainTimeStartRemoval}" class="timeEntry gtexQuickSave" data-param="brainTimeStartRemoval" /></g:if></td>                                                                            
              <td class="td_brainTimeEndAliquot"><g:if test="${a.gtexSequenceNum() == '0011'}"><g:textField data-currentid="${a.id}" id="brainTimeEndAliquot_${a.id}" name="brainTimeEndAliquot" value="${a?.brainTimeEndAliquot}" class="timeEntry gtexQuickSave" data-param="brainTimeEndAliquot" /></g:if></td>
              <td class="td_brainTimeIce"><g:if test="${a.gtexSequenceNum() == '0011'}"><g:textField data-currentid="${a.id}" id="brainTimeIce_${a.id}" name="brainTimeIce" value="${a?.brainTimeIce}" class="timeEntry gtexQuickSave" data-param="brainTimeIce" /></g:if></td>                                                                             
              <td class="td_prosectorComments"><g:textArea data-currentid="${a.id}" id="prosectorComments_${a.id}" name="prosectorComments" value="${a?.prosectorComments}" class="gtexQuickSave" data-param="prosectorComments" /></td>
            </tr></g:if></g:each>
          </table></g:if></g:if>
    <div id="yellowKitNav" class="kitNav">Jump to kit: <g:if test="${brainFlag == true}"><div class="jump-to" data-goto="greenKitNav"><span class="greenLink">&nbsp;</span> Green</div> | </g:if><div class="jump-to" data-goto="aquaKitNav"><span class="aquaLink">&nbsp;</span> Aqua</div> <g:if test="${frozenCaseNum > 0}">| <div class="jump-to" data-goto="pinkKitNav"><span class="pinkLink">&nbsp;</span> Pink</div></g:if></div>    
      <table id="yellowKit">
        <tr><td colspan="9" class="yellow-kit-header">Yellow Kit</td></tr>
        <tr>
          <th>Specimen Id</th>
          <th class="tissue-type-h">Tissue Type</th>
          <th>Fixative/Container</th>
          <th>Draw Time</th>
          <th>Time Inverted</th>
          <th>Time in Medium</th>
          <th>Comments</th>
        </tr><g:each in="${specimensTRF}" var="a" status="i"><g:if test="${a.gtexSequenceNum() in['0001','0002','0003','0004','0005','0006','0008', '0007', '0009', '0010']}"><g:set var="bloodFlag" value="1" /><%--set flag suppress render empty row--%>                                    
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td class="itemid">${a}</td>
          <td>${a.tissueType}</td><td>${a.fixative}</td>                                      
        <g:if test="${a.gtexSequenceNum() != '0008'}"><td class="td_bloodTimeDraw"><g:textField data-currentid="${a.id}" name="bloodTimeDraw" value="${a?.bloodTimeDraw}" class="timeEntry gtexQuickSave" data-param="bloodTimeDraw" /></td>                                                                            
          <td class="td_bloodTimeDrawInverted"><g:textField data-currentid="${a.id}" name="bloodTimeDrawInverted" value="${a?.bloodTimeDrawInverted}" class="timeEntry gtexQuickSave" data-param="bloodTimeDrawInverted" /></td>        
          <td></td></g:if><g:else><td></td><td></td>
          <td class="td_skinTimeIntoMedium"><g:textField data-currentid="${a.id}" name="skinTimeIntoMedium" value="${a?.skinTimeIntoMedium}" class="timeEntry gtexQuickSave" data-param="skinTimeIntoMedium" /></td></g:else>
          <td class="td_prosectorComments"><g:textArea data-currentid="${a.id}" name="prosectorComments" value="${a?.prosectorComments}" class="gtexQuickSave" data-param="prosectorComments" /></td></tr></g:if></g:each>
    <g:if test="${!bloodFlag}"><tr><td colspan="9">Blood not collected.</td></tr></g:if>                                                        
      </table> 
    <div id="aquaKitNav" class="kitNav">Jump to kit: 
      <g:if test="${brainFlag == true}">
        <div class="jump-to" data-goto="greenKitNav">
          <span class="greenLink">&nbsp;</span> Green
        </div> | 
      </g:if>
      <div class="jump-to" data-goto="yellowKitNav">
        <span class="yellowLink">&nbsp;</span> Yellow
      </div> 
      <g:if test="${frozenCaseNum > 0}">| 
        <div class="jump-to" data-goto="pinkKitNav">
          <span class="pinkLink">&nbsp;</span> Pink
        </div>
      </g:if>  

      <%  def countAqua = 0 %>
      <g:each in="${specimensTRF}" var="a" status="i">
        <g:if test="${!(a.gtexSequenceNum() in['0011','0014','0001','0002','0003','0004','0005','0006','0008', '0007', '0009', '0010']) && a.fixative.code != 'DICE'}">
          <%  countAqua++ %>
        </g:if>
      </g:each>

      <g:if test='${(session.DM == false) || (!session.DM) || (countAqua == 0) || (!session.org) || ((session.org.toString() != "OBBR")&&(session.org.toString() != "BBRB"))}'>
      </g:if>
      <g:else>
        | <a class="home" href="${(request.getRequestURL()).indexOf("localhost")>0?"http://localhost:8081":""}/cahubanalytics/gtexTRFAquaReport/reportAqua/${tissueRecoveryGtexInstance.id}">Export Report</a>
      </g:else>

    </div>
    <span class="tablefooter">*Note that entries below are ordered by "Time Removed" (WITHOUT FACTORING IN THE DATE - Take note when dealing with entries for multiple days).<br><span class="editOnly">**Entries where "Time Removed" is blank will be raised to the top when order is refreshed.</span></span>
    <div id="aquaSection"><g:render template="updateAqua" /></div>
    <g:if test="${frozenCaseNum > 0}"><div id="pinkKitNav" class="kitNav">Jump to kit: <g:if test="${brainFlag == true}"><div class="jump-to" data-goto="greenKitNav"><span class="greenLink">&nbsp;</span> Green</div> | </g:if><div class="jump-to" data-goto="yellowKitNav"><span class="yellowLink">&nbsp;</span> Yellow</div> | <div class="jump-to" data-goto="aquaKitNav"><span class="aquaLink">&nbsp;</span> Aqua</div></div>    
      <table id="pinkKit">
        <tr><td colspan="11" class="pink-kit-header">Pink Kit</td></tr>
        <tr>
          <th>Specimen Id</th>
          <th class="tissue-type-h">Tissue Type</th>
          <th>Tissue Location</th>
          <th>Fixative</th>
          <th>Time Removed</th>
          <th>Time Put<br />in Fixative</th>
          <th>Adjacent to<br />Tissue for<br />PAXgene Fixation</th>                                      
          <th>Size if<br />Different<br />than SOP</th>
          <th>Comments</th>
        </tr>
        <g:if test="${bodyclass.indexOf('view') == -1}"><g:each in="${specimensTRF}" var="a" status="i"><g:if test="${a.fixative.code == 'DICE'}">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td class="itemid">${a}</td>
          <td>${a.tissueType}</td>
          <td class="td_tissueLocation"><select name="tissueLocation.id" id="tissueLocation_${a.id}" data-currentid="${a.id}" data-value="${a?.tissueLocation?.id}"><%=acquisitionLocOptionsStr.replace("value=\"" + a?.tissueLocation?.id + "\"","value=\"" + a?.tissueLocation?.id + "\" selected")%></select><div class="depends-on" data-id="tissueLocation_${a.id}" data-select="28"><span id="tl_${a.id}" class="labelstr otherTissueLocation otherTissueLocation${a.id}">Enter other location: </span><g:textField class="otherTissueLocation" data-currentid="${a.id}" name="otherTissueLocation${a.id}" value="${a?.otherTissueLocation}" /></div></td>  
          <td>${a.fixative}</td>
          <td class="td_aliquotTimeRemoved"><g:textField data-currentid="${a.id}" id="aliquotTimeRemoved_${a.id}" name="aliquotTimeRemoved" value="${a?.aliquotTimeRemoved}" class="timeEntry gtexQuickSave" data-param="aliquotTimeRemoved" /></td>
          <td class="td_aliquotTimeFixed"><g:textField data-currentid="${a.id}" id="aliquotTimeFixed_${a.id}" name="aliquotTimeFixed" value="${a?.aliquotTimeFixed}" class="timeEntry gtexQuickSave" data-param="aliquotTimeFixed" /></td>                                                                                   
          <td class="td_adjacentToPaxTissue"><g:select data-currentid="${a.id}" id="adjacentToPaxTissue_${a.id}" name="adjacentToPaxTissue" from="${['','Yes','No']}" class="gtexQuickSave" data-param="adjacentToPaxTissue" value="${a?.adjacentToPaxTissue}" /></td>                                      
          <td class="td_sizeDiffThanSOP"><g:textField data-currentid="${a.id}" id="sizeDiffThanSOP_${a.id}" name="sizeDiffThanSOP" value="${a?.sizeDiffThanSOP}" class="gtexQuickSave" data-param="sizeDiffThanSOP" /></td>                                                                            
          <td class="td_prosectorComments"><g:textArea data-currentid="${a.id}" id="prosectorComments_${a.id}" name="prosectorComments" value="${a?.prosectorComments}" class="gtexQuickSave" data-param="prosectorComments" /></td>
        </tr></g:if></g:each></g:if><g:else><g:each in="${specimensTRF}" var="a" status="i"><g:if test="${a.fixative.code == 'DICE'}">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td class="itemid">${a}</td>
          <td>${a.tissueType}</td>
          <td class="td_tissueLocation"><select name="tissueLocation.id" id="tissueLocation_${a.id}" data-currentid="${a.id}" data-value="${a?.tissueLocation?.id}"><%=acquisitionLocOptionsStr.replace("value=\"" + a?.tissueLocation?.id + "\"","value=\"" + a?.tissueLocation?.id + "\" selected")%></select><div class="depends-on" data-id="tissueLocation_${a.id}" data-select="28"><span id="tl_${a.id}" class="labelstr otherTissueLocation otherTissueLocation${a.id}">Enter other location: </span><g:textField class="otherTissueLocation" data-currentid="${a.id}" name="otherTissueLocation${a.id}" value="${a?.otherTissueLocation}" /></div></td>  
          <td>${a.fixative}</td>
          <td class="td_aliquotTimeRemoved">${a?.aliquotTimeRemoved}</td>
          <td class="td_aliquotTimeFixed">${a?.aliquotTimeFixed}</td>                                                                                   
          <td class="td_adjacentToPaxTissue">${a?.adjacentToPaxTissue}</td>                                      
          <td class="td_sizeDiffThanSOP">${a?.sizeDiffThanSOP}</td>                                                                            
          <td class="td_prosectorComments">${a?.prosectorComments?.replace("\n","<br />")}</td>
        </tr></g:if></g:each></g:else>
      </table></g:if>
