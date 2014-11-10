<g:set var="bodyclass" value="backoffice" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title><g:message code="default.page.title"/></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a> 
            <g:link controller="fileUpload" class="list" action="list">Uploaded File List</g:link> 
            <g:link controller="textSearch" class="list" action="searchhome">Search</g:link>
            <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.authorities.contains('ROLE_ADMIN')}">
                <g:link controller="backoffice" class="list" action="index">Back Office</g:link>
            </g:if>    
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Rest API List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
              <div style="margin:10px;">              
                <h2>All Rest Services</h2>
                  <ul>
                    <h3>POST</h3>
                        <li class="controller"><g:link controller="rest" action="shippingEventRestActions">Shipping Event</g:link></li>                  
                        <li class="controller"><g:link controller="rest" action="processingEventRestActions">Processing Event</g:link></li>
                        <li class="controller"><g:link controller="rest" action="collectionEventRestActions">Collection Event</g:link></li>
                        <li class="controller"><g:link controller="rest" action="importLDACCDonor">Import LDACC Donor</g:link></li>      
                        <li class="controller"><g:link controller="rest" action="chpEventRestActions">CHP Event</g:link></li>      
                        <li class="controller"><g:link controller="rest" action="searchId">Search ID</g:link></li>                  
                    <h3>GET</h3>
                        <li class="controller"><g:link controller="rest" action="caseRecordRestActions">Case Record</g:link></li>                    
                        <li class="controller"><g:link controller="rest" action="getValidBSS">Get Valid BSS</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getValidAcquisitionLocation">Get Valid Acquisition Location</g:link></li>                  
                        <li class="controller"><g:link controller="rest" action="getValidAcquisitionType">Get Valid Acquisition Type</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getValidFixative">Get Valid Fixative</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getValidContainerType">Get Valid Container Type</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getValidKit">Get Valid Kit</g:link></li>                  
                        <li class="controller"><g:link controller="rest" action="getValidShippingContentType">Get Valid Shipping Content Type</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getValidOrganization">Get Valid Organization</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getPrcReport">Get PRC Report</g:link></li>
                        <li class="controller"><g:link controller="rest" action="getPrcSpecimenReport">Get PRC Specimen Report</g:link></li>                  
                    <h3>CBR (BRIMS)</h3>
                        <li class="controller"><g:link controller="home" action="cbrinvfeed">Inventory Feed from BRIMS</g:link></li>
                </ul>                
              </div>
        </div>
        </div>
    </body>
</html>

