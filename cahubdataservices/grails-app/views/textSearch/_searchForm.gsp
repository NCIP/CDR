<%@page defaultCodec="html" %>
<g:if test="${session.getAttribute('chosenHome') == 'BRN' || session.getAttribute('chosenHome') == 'PRC' || session.getAttribute('chosenHome') == 'BMS' ||session.getAttribute('chosenHome') == 'BPV'  ||session.getAttribute('chosenHome') == 'CTC' ||session.getAttribute('chosenHome') == 'MBB'}">
  <g:if test="${ session.getAttribute('chosenHome') == 'BRN'}">
    <g:form controller="textSearch" action="searchBRN">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchBRN" value="Go" />
    </g:form>
  </g:if>  
   <g:if test="${session.getAttribute('chosenHome') == 'PRC'}">
    <g:form controller="textSearch" action="searchPRC">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchPRC" value="GTEX" /> <g:actionSubmit action="searchPRCBpv" value="BPV" />
          </g:form>
  </g:if> 
  <g:if test="${session.getAttribute('chosenHome') == 'BMS'}">
    <g:form controller="textSearch" action="searchBMS">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchBMS" value="Go" />
          </g:form>
  </g:if> 
  <g:if test="${session.getAttribute('chosenHome') == 'BPV'}">
    <g:form controller="textSearch" action="searchBPV">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchBPV" value="Go" />
          </g:form>
  </g:if> 
 <g:if test="${session.getAttribute('chosenHome') == 'CTC'}">
    <g:form controller="textSearch" action="searchCTC">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchCTC" value="Go" />
          </g:form>
  </g:if>  
</g:if>
<g:elseif test="${session.org?.code == 'VARI'}">
   <g:if test="${session.study.code=='GTEX'}">
      <g:form controller='textSearch' action="searchVari">
         <lable><b>Enter Search Criteria:</b></lable>
         <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
          <g:actionSubmit action="searchVari" value="Go" />
          </g:form>
   </g:if>
  <g:if test="${session.study.code=='BPV'}">
      <g:form controller='textSearch' action="searchVariBpv">
         <lable><b>Enter Search Criteria:</b></lable>
         <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
          <g:actionSubmit action="searchVariBpv" value="Go" />
          </g:form>
   </g:if>
  
   <g:if test="${session.study.code=='BRN'}">
      <g:form controller='textSearch' action="searchVariBrn">
         <lable><b>Enter Search Criteria:</b></lable>
         <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
          <g:actionSubmit action="searchVariBrn" value="Go" />
          </g:form>
   </g:if>
  
   <g:if test="${session.study.code=='BMS'}">
      <g:form controller='textSearch' action="searchVariBms">
         <lable><b>Enter Search Criteria:</b></lable>
         <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
          <g:actionSubmit action="searchVariBms" value="Go" />
          </g:form>
   </g:if>
  
</g:elseif>

<g:elseif test="${session.org?.code == 'BROAD'}">
   <g:if test="${session.study.code=='GTEX'}">
      <g:form controller='textSearch' action="searchVari">
         <lable><b>Enter Search Criteria:</b></lable>
         <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
          <g:actionSubmit action="searchVari" value="Go" />
          </g:form>
   </g:if>
   <g:if test="${session.study.code=='BMS'}">
      <g:form controller='textSearch' action="searchVariBms">
         <lable><b>Enter Search Criteria:</b></lable>
         <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
          <g:actionSubmit action="searchVariBms" value="Go" />
          </g:form>
   </g:if>

</g:elseif>
<g:elseif test="${session.org?.code == 'NDRI'}">
   <g:form controller="textSearch" action="search" >
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="search" value="GTEX" /> <g:actionSubmit action="searchBMS" value="BMS" />
    </g:form>
</g:elseif>
<g:elseif test="${session.org?.code == 'MBB'}">
   <g:form controller="textSearch" action="searchMBB">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchMBB" value="Go" />
    </g:form>
</g:elseif>
<g:else>
    <g:form controller='textSearch' action="search">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="search" value="Go" />
      </g:form>
</g:else>

  
