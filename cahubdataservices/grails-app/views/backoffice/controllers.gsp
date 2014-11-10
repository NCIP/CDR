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
            <h1>Controller List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
              <div style="margin:10px;">              
                <h2>All Controllers</h2>
                  <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                    </g:each>
                </ul>                
              </div>
        </div>
        </div>
    </body>
</html>

