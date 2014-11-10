<g:set var="bodyclass" value="bpvelsihome" scope="request"/>
<html>
    <head>
        <title><g:message code="default.page.title"/> - BPV ELSI Home</title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>
       <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN')}">
                <span class="menuButton"><g:link controller="backoffice" class="list" action="index">Back Office</g:link></span> 
            </g:if>
            <g:if test='${session.authorities?.contains("ROLE_BPV_ELSI")}'>
                   <a href="/cahubdataservices/interviewRecord/create" class="create">Start New Interview</a>    
            </g:if>
            <g:if test='${session.authorities?.contains("ROLE_BPV_MAIN")}'>  
                  <g:link controller="home" class="list" action="bpvbsshome" >BPV Main Home</g:link>
            </g:if>
            
            <g:link controller="bpvElsiCrf" class="list" action="searchHome">Search</g:link> 
            
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>BPV ELSI Home</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${interviewRecordList}">
            <div class="errors">
                <g:renderErrors bean="${interviewRecordList}" as="list" />
            </div>
            </g:hasErrors>
            <div class="list">

                <g:render template="/interviewRecord/interviewRecordTable_tmpl" bean="${interviewRecordList}" />

                <div class="clearfix tablefooter"><div class="left">Most recently created on top</div>
                    <div class="right"><a href="/cahubdataservices/interviewRecord/list">View all Interviews >></a></div> %{-- took out a dangling </td> here -- Tabor --}%
                    <br />
                    <div class="left querytracker">QT = Number of active queries</div></div>
              
        </div>
        </div>
    </body>
</html>

