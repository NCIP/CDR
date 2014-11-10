
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
            <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN')}">
                <g:link controller="backoffice" class="list" action="index">Back Office</g:link>
            </g:if>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>CDR DS Back Office</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
                <h3>Restricted administrative areas. Access is restricted to local 'ROLE_ADMIN' and LDAP 'ROLE_NCI-FREDERICK_CAHUB_SUPER' roles.</h3>
                <p>
                  <ul>
                    <li class="controller"><a href="/cahubdataservices/user/">Non-NIH user administration</a><span style="color:red">* All usernames should be created in lowercase.</span></li>                  
                    <li><b>NIH Outlook DL Groups:</b> NCI-Frederick CAHUB; NCI-Frederick CAHUB DM; NCI-Frederick CAHUB PRC; NCI-Frederick CAHUB LDS; NCI-Frederick CAHUB Super</li>
                    <li class="controller"><a href="/cahubdataservices/appSetting/list?offset=0&max=200">Application settings</a></li>   
                    <li class="controller"><a href="/cahubdataservices/auditLogEvent/">Full audit log</a><span style="color:red">* Must be logged in as <i><b>hubadmin</b></i>.</span></li>
                    <li class="controller"><a href="viewapplog">View application log</a></li>           
                    <li class="controller"><a href="/cahubdataservices/backoffice/controllers">All controllers</a><span style="color:red">* Use with caution.</span></li>
                    <li class="controller"><a href="/cahubdataservices/userLogin/">User login history</a></li>
                    <!--<li class="controller"><a href="/cahubdataservices/monitoring">JavaMelody plugin</a></li>-->
                    <li class="controller"><a href="/cahubdataservices/appinfo/">App Info plugin</a></li>                   
                    <li class="controller"><a href="/cahubdataservices/backoffice/restservices">REST API - Rest Services</a></li>
                  </ul>   
                </p>
                <hr />
                  <h3>Application Status</h3>
                      <ul>
                          <li>App version: <g:meta name="app.version"/></li>
                          <li>Grails version: <g:meta name="app.grails.version"/></li>
                          <li>Groovy version: ${GroovySystem.getVersion()}</li>
                          <li>JVM version: ${System.getProperty('java.version')}</li>
                          <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
                          <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
                          <li>Domains: ${grailsApplication.domainClasses.size()}</li>
                          <li>Services: ${grailsApplication.serviceClasses.size()}</li>
                          <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
                      </ul>
                    <hr />
                      <h3>Installed Plugins</h3>
                      <ul>
                        <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                                        <li>${plugin.name} - ${plugin.version}</li>
                        </g:each>
                      </ul>
                

        <hr />
        <h3>Browser debugging</h3>
	<script language="javascript">
		document.write('APP NAME: ' + navigator.appName + '<br /> <br />USER AGENT: ' + navigator.userAgent)
	</script>      
        
      </div>
    </body>
</html>

