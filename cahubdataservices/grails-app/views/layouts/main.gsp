<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<g:set var="appMilestone" value="${nci.obbr.cahub.util.AppSetting.findByCode('APP_RELEASE_MILESTONE')?.bigValue}"/>

<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <!-- appending app version to CSS file name to force refresh upon new deployment -->       
        <link href="${resource(dir:'css/theme',file:'jquery-ui-1.8.23.custom.css')}?v<g:meta name='app.version'/>x" type="text/css" rel="stylesheet" media="screen, projection" id="jquery-ui-theme" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}?v<g:meta name='app.version'/>x" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="no-cache" content="no-cache" />
	<meta http-equiv="expires" content="-1" />
	<meta http-equiv="cache-control" content="no-cache" />  
        <g:javascript library="jquery" plugin="jquery"/>  
        <script type="text/javascript" src="${resource(dir:'js',file:'jquery-ui-1.8.23.custom.js')}?v<g:meta name='app.version'/>x" ></script>
        <g:javascript library="jquery-ui-timepicker-addon"/>
        <g:javascript library="timeentry/jquery.timeentry.min"/>
        <g:javascript library="countdown/jquery.countdown.min"/>
        <g:javascript library="application" /> 
        <script type="text/javascript" src="${resource(dir:'js',file:'cahub.js')}?v<g:meta name='app.version'/>x" ></script>
        <g:javascript library="cdrcustom"/>
        <g:javascript library="ddSpecimens"/>
        <tooltip:resources/>
        <tooltip:resources stylesheet="tooltip"/>
        <g:layoutHead />        
    </head>
    <body class="${bodyclass ?: ''}">
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="header">
          <g:if test="${controllerName != 'login' && controllerName != 'logout'}">
            <g:set var="env" value="${grails.util.GrailsUtil.environment}" />             
            <g:if test="${env != 'production' && session.DM == true && session.org?.code == 'OBBR'}">
              <div id="testBannerOK">Note: You are logged into a test system.  Data will not be saved to production.</div>
            </g:if>
            <g:if test="${env == 'production' && session.DM == true && session.org?.code == 'OBBR'}">
              <div id="testBannerWarn">Warning: You are logged into production!  Data changes will be saved to the production database.</div>
            </g:if>            
          </g:if>          
          <div id="grailsLogo">
            <g:if test="${session.org?.code == 'OBBR'}">
                <a href="/cahubdataservices/home/choosehome"><img src="${resource(dir:'images',file:'logo_caHUB.jpg')}" alt="caHUB" border="0" /></a>
            </g:if>
            <g:else>
              <a href="/cahubdataservices/home"><img src="${resource(dir:'images',file:'logo_caHUB.jpg')}" alt="caHUB" border="0" /></a>
            </g:else>
          </div>
          <g:if test="${controllerName != 'login' && controllerName != 'logout'}">
          <div id="userInfo">
                  Welcome, ${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()} | <a href="/cahubdataservices/logout">Logout</a><br />
                  Organization: ${session.org?.name}<br />
                  <g:if test="${session.org?.code == 'OBBR'}">
                  Privileges: 
                  <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                                session.authorities?.contains("ROLE_ADMIN")}'>
                      <g:if test="${session.DM == true}">
                        <b><g:link controller="privilege" action="toggleDM" params="[returnPage: (request.requestURI - request.contextPath)]">DM</g:link></b> |
                      </g:if>
                      <g:else>
                        <g:link style="color:#999999;font-weight:normal" controller="privilege" action="toggleDM" params="[returnPage: (request.requestURI - request.contextPath)]">DM</g:link> |
                      </g:else>
                  </g:if>
                  <g:else>
                    <span style="color:#999999">DM | </span>
                  </g:else>
                  <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                                session.authorities?.contains("ROLE_ADMIN")}'>
                      <g:if test="${session.PRC == true}">
                        <b><g:link controller="privilege" action="togglePRC" params="[returnPage: (request.requestURI - request.contextPath)]">PRC</g:link></b> |
                      </g:if>
                      <g:else>
                        <g:link style="color:#999999;font-weight:normal" controller="privilege" action="togglePRC" params="[returnPage: (request.requestURI - request.contextPath)]">PRC</g:link> |
                      </g:else>
                  </g:if>
                  <g:else>
                    <g:if test="${session.PRC == true}"><b>PRC</b> |</g:if>
                    <g:else><span style="color:#999999">PRC</span> |</g:else>
                  </g:else>

                  <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") ||
                                session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                                session.authorities?.contains("ROLE_ADMIN")}'>
                      <g:if test="${session.LDS == true}">
                        <b><g:link controller="privilege" action="toggleLDS" params="[returnPage: (request.requestURI - request.contextPath)]">LDS</g:link></b>
                      </g:if>
                      <g:else>
                        <g:link style="color:#999999;font-weight:normal" controller="privilege" action="toggleLDS" params="[returnPage: (request.requestURI - request.contextPath)]">LDS</g:link>
                      </g:else>
                  </g:if>
                  <g:else>
                    <g:if test="${session.LDS == true}"><b>LDS</b></g:if>
                    <g:else><span style="color:#999999">LDS</span></g:else>
                  </g:else>
                  <br />
                  </g:if>
                  <g:if test="${session.org?.code != 'VARI' && session.org?.code != 'BROAD'}">
                  <g:link controller="textSearch" action="searchhome">Search</g:link>
                  |
                  <g:link controller="help" action="helphome">Help</g:link>
                  </g:if>
                  <div id="countdown" class="countdown"></div>
                  <script>
                    <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC")}'>
                        var timeoutDuration = +3600
                        var layout = 'Session expires in: <b>{hnn}{sep}{mnn}{sep}{snn}</b> {desc}'
                    </g:if>
                    <g:else>
                        var timeoutDuration = +1800
                        var layout = 'Session expires in: <b>{mnn}{sep}{snn}</b> {desc}'                        
                    </g:else>

                    //var warningTripped = false
                    //var alertDisplayed = false

                    $('#countdown').countdown({until: timeoutDuration, compact: true, onTick: highlightLast300, onExpiry: redirectToLogin,
                      layout: layout});
                      
                      function highlightLast300(periods) { 
                          if ($.countdown.periodsToSeconds(periods) == 300) { 
                              $(this).addClass('countdownHighlight'); 
                              //warningTripped = true
                          } 
                          //if(warningTripped == true && alertDisplayed == false){
                            //alertDisplayed = true
                            //alert('Your session will expire in 5 minutes. Please save your work or refresh the page too keep your session active.')
                          //}
                      } 

                      $('#highlightButton').click(function() { 
                          $('#highlightCountdown').removeClass('countdownHighlight'). 
                              countdown('change', {until: +10}); 
                      });                  
                  
                      function redirectToLogin(){
                        window.location = '/cahubdataservices/logout';
                      }
                  
                  </script>
          </div>
          </g:if>
        </div>
        <g:layoutBody />
        <p /><br />
        <div class="nav" style="clear:both;text-align:center;">
          <span style="color:#666;font-size:10px">caHUB CDR DS v<g:meta name="app.version"/>${appMilestone?".$appMilestone":""}</span><br />
          <g:if test="${controllerName != 'login'}"></g:if>
        </div>
        <div class="branding">
            <a target="_blank" alt="caHUB" href="http://cahub.cancer.gov"><img height="50" width="110" border="0" src="/cahubdataservices/images/cahublogogrey.jpg" /></a> 
            <a target="_blank" alt="NCI" href="http://www.cancer.gov"><img height="50" width="57" border="0" src="/cahubdataservices/images/ncilogo.gif" /></a> 
            <a target="_blank" alt="NIH" href="http://www.nih.gov"><img height="50" width="56" border="0" src="/cahubdataservices/images/nihlogo.gif" /></a> 
            <a target="_blank" alt="HHS" href="http://www.hhs.gov"><img height="50" width="54" border="0" src="/cahubdataservices/images/hhslogo.gif" /></a> 
            <a target="_blank" alt="USA.GOV" href="http://www.usa.gov"><img height="50" width="91" border="0" src="/cahubdataservices/images/usagovlogo.gif" /></a> 
          <br /><br />
        </div>
    </body>
</html>
