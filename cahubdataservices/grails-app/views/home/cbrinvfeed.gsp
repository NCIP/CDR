<g:set var="bodyclass" value="cbrinvfeed" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title><g:message code="default.page.title"/></title>
     
    </head>
    <body>       
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
            <g:link controller="textSearch" class="list" action="searchhome">Search</g:link>
            <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.authorities.contains('ROLE_ADMIN')}">
                <g:link controller="backoffice" class="list" action="index">Back Office</g:link>
                <g:link controller="backoffice" class="list" action="restservices">Rest Services</g:link>
            </g:if>    
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>CBR (BRIMS) Inventory Feed</h1>
            <div class="list">
              <div style="margin:10px;">              
                        1. <g:link controller="rest" action="getCaseRecordFromBrims">Case Record from BRIMS</g:link> 
                        <g:formRemote name="brimsInventoryFeed1" url="[controller: 'rest', action: 'getCaseRecordFromBrims']" update="serviceOutput" >
                          Case ID: <g:textField name="caseId" size="10" maxlength="40" value="" /><span class="button"><g:actionSubmit class="button bleft" action="getCaseRecordFromBrims" value="Get Case Record" /></span>
                        </g:formRemote>
                        <br>
                        2. <g:link controller="rest" action="getSpecimenRecordFromBrims">Specimen Record from BRIMS</g:link> 
                        <g:formRemote name="brimsInventoryFeed2" url="[controller: 'rest', action: 'getSpecimenRecordFromBrims']" update="serviceOutput" >
                          Specimen ID : <g:textField name="specimenId" size="10" maxlength="40" value="" /><span class="button"><g:actionSubmit class="button bleft" action="getSpecimenRecordFromBrims" value="Get Specimen Record" /></span>
                        </g:formRemote>
                        <br>
                        3. <g:link controller="rest" action="getSpecimensByIdentifiersFromBrims">Search Specimens by Identifiers</g:link> 
                        <g:formRemote name="brimsInventoryFeed3" url="[controller: 'rest', action: 'getSpecimensByIdentifiersFromBrims']" update="serviceOutput" >
                          Identifiers (separated by commas) : <br><g:textArea id="identifiers" style="resize:none;" name="identifiers" cols="90" rows="3" resize="none" /><br>
                          <g:actionSubmit class="button bleft" action="getSpecimensByIdentifiersFromBrims" value="Get Specimens" />
                        </g:formRemote>
                        <br>
                        Service Output : <br>
                        <textarea class="nolimit" id="serviceOutput" readonly="true" name="serviceOutput" cols="90" rows="25" ></textarea><br><input type="button" onclick="javascript:$('#serviceOutput').html('');" value="Clear Output">
              </div>
        </div>
        </div>
    </body>
</html>

