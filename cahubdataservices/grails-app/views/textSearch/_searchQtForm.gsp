<%@page defaultCodec="html" %>
<script>
  function openHint(){
    $("#hint").show()
    $("#o").hide()
    $("#c").show()
  }
  
  function closeHint(){
    $("#hint").hide()
    $("#o").show()
    $("#c").hide()
  }
  
</script>


<g:form controller='textSearch' action="searchQt">
<lable><b>Enter Search Criteria:</b></lable>
<input style="width:500px" id="query" type="text" name="query" value="${params.query}"/>
<g:actionSubmit action="searchQt" value="Go" />
</g:form>
<a href="#" onclick="openHint()" id="o" >Show Search Hints</a>
<a href="#" onclick="closeHint()" id="c" style="display:none">Hide Search Hints</a>
<br/>
<div id ="hint" style="display:none">
  <br/>
  <div class="message">    
      <h1>Hints</h1>
      <h3>All Field Search</h3>
        <ul>
                <li>Search by Case ID <i>(GTEX-000351)</i></li>
                <li>Search by Candidate ID <i> (UNM-D076D8E4-C)</i> </li>
                <li>Search by title <i>(CI04546-BRN)</i></li>
                <li>Search by organization <i>(NDRI)</i></li>
                <li>Search by access db id <i>(accessId:60)</i></li>
                <li>Search by issue status <i>(closed)</i></li>
                <li>Search by query type <i>(Action)</i></li>
                <li>Search by description <i>(Gross Evaluation of Tissue)</i></li>
                <li>Search by query tracker id <i>(qtId:100)</i></li>
                <li>Wild card search<i>(g*98)</i></li>
                <li>Search by multiple key words
                  <ul>
                    <li>AND <i>(NDRI active)</i></li>
                    <li>OR <i>(NDRI OR active)</i> </li>
                  </ul>
                </li>
               
        </ul>
      <br>
      <h3>Specific Field Search</h3>
      <ul>
        <li>Syntax: [field]:[value]<i>(caseId:GTEX-000351)</i></li>
        <li>Available fields
          <ul>
            <li>CaseRecord: caseId
            <li>CandidateRecord:candidateId </li>
            <li>Organization: orgName, orgCode </li>
            <li>QueryStatus: qtStatusName, qtStatusCode</li>
            <li>QueryType: qtTypeName, qtTypeCode </li>
            <li>Study: studyName, studyCode</li>           
             <li>Query: qtId, qtDescription, qtTitle,qtDateCreated,accessId</li>
          </ul>
         
        </li>
        <li>Date range search <i>(qtDateCreated:[2013-05-01 TO 2013-06-01])</i> </li>       
        
      </ul>
  </div>
  
</div>

