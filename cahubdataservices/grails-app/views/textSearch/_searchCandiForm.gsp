<%@page defaultCodec="html" %>
<g:if test="${session.study.code == 'GTEX'}">
    <g:form controller='textSearch' action="searchCandi">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchCandi" value="Go" />
      </g:form>
</g:if>
<g:if test="${session.study.code == 'BPV'}">
    <g:form controller='textSearch' action="searchCandiBPV">
     <lable><b>Enter Search Criteria:</b></lable>
     <input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
      <g:actionSubmit action="searchCandiBPV" value="Go" />
      </g:form>
</g:if>
  
