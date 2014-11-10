<!-- Jquery Dialog box content: simple format> -->
<%@page defaultCodec="none" %>
<html lang="en">
  <head></head>
  <body> 
      <h3>Selected Result:</h3>
      <ul>
        <li><b>${vocabRec.cVocabUserSelection}</b></li>
        <li><b>CUI:</b> ${vocabRec.cuiList?.join(",")} </li>
        <li><b>ICDcd:</b> ${vocabRec.icdCd} </li>
      </ul>
      <g:if test="${vocabRec.srcDef}">
        <h3>Definition:</h3>
       ${vocabRec.srcDef}  
      </g:if>  
   </body>
</html>
