<g:set var="env" value="${grails.util.GrailsUtil.environment}"/>
<g:set var="appMilestone" value="${nci.obbr.cahub.util.AppSetting.findByCode('APP_RELEASE_MILESTONE')?.bigValue}"/>

<g:if test="${homeLink == null || homeLink == ''}" ><g:if test="${session.org?.code == 'OBBR'}" ><g:set var="homeLink" value="/cahubdataservices/home/choosehome" /><g:set var="homeTitle" value="caHUB" /></g:if> <g:else><g:set var="homeLink" value="/cahubdataservices/home" /><g:set var="homeTitle" value="caHUB" /></g:else></g:if>
<g:if test="${env != 'production'}"><%-- cache buster--%><g:set var="d" value="${new Date()}" /><g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" /></g:if>
<!DOCTYPE html>
<html>
  <head>
     <title><g:message code="default.page.title"/></title>
      <g:if test="${bodyclass?.indexOf('tissuerecoverygtex') > -1}"><meta http-equiv="X-UA-Compatible" content="IE=8" /></g:if>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <g:if test="${extjs ?: false == true}"><script type="text/javascript" src="${resource(dir:'js',file:'ext/ext-all.js')}"></script>
      <link rel="stylesheet" href="${resource(dir:'css',file:'ext/resources/css/ext-all.css')}" /></g:if>
      <script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.10.2.min.js')}" ></script>
      <script type="text/javascript" src="${resource(dir:'js',file:'jquery-migrate-1.2.1.min.js')}" ></script>      
      <script type="text/javascript" src="${resource(dir:'js',file:'jquery-ui-1.8.23.custom.js')}" ></script>
      <g:javascript library="jquery-ui-timepicker-addon"/><g:javascript library="timeentry/jquery.timeentry.min"/><g:javascript library="countdown/jquery.countdown.min"/><g:javascript library="application" /><g:javascript library="cdrcustom"/>
      <link href="${resource(dir:'css',file:'theme/jquery-ui-1.8.23.custom.css')}?v<g:meta name='app.version'/>-${ts ?: ''}" type="text/css" rel="stylesheet" media="screen, projection" id="jquery-ui-theme" /> 
      <link rel="stylesheet" href="${resource(dir:'css',file:'cahub.css')}?v<g:meta name='app.version'/>-${ts ?: ''}" />
      <link rel="shortcut icon" type="image/ico" href="/cahubdataservices/images/favicon.ico"/>
      <script type="text/javascript" src="${resource(dir:'js',file:'cahub.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>  
      <script type="text/javascript">
        var vocabJsnLoc = "${nci.obbr.cahub.util.AppSetting.findByCode('VOCAB_SOLR_LOC')?.bigValue}";
        var sessionStudy = "${session.study?.name}";
        var sessionAuthDM = ${session.authorities ? session.authorities.contains("ROLE_NCI-FREDERICK_CAHUB_DM") : false };
        var sessionAuthSuper = ${session.authorities ? session.authorities.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") : false };
        var sessionAuthLDS = ${session.authorities ? session.authorities.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") : false };
        var sessionAuthAdmin = ${session.authorities ? session.authorities.contains("ROLE_ADMIN") : false };
        var sessionDM = ${(session.DM == null)? false : session.DM};
        var sessionOrg = "${session.org?.code}";
        <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC")}'>var timeOutMilliSecs = 3601000;</g:if>
        <g:else>var timeOutMilliSecs = 1801000;</g:else> 
        <%-- Below variable only applies to Activity Center  --%>
        <g:if test="${session.org?.code in ['BROAD', 'NDRI', 'RPCI', 'UPMC', 'UNM', 'BMC', 'EU']}">deidentified = true;</g:if>
      </script>
      <g:layoutHead />  
  </head>
  <body class="${bodyclass ?: ''}">
    <div id="body_wrapper">    
      <div id="sitemessage">
          <g:if test="${controllerName != 'login' && controllerName != 'logout'}">
            <g:if test="${env != 'production' && session.DM == true && session.org?.code == 'OBBR'}"><div id="testBannerOK">Note: You are logged into a test system. Data will not be saved to production.</div></g:if>
            
            <g:if test="${env == 'production' && session.DM == true && session.org?.code == 'OBBR'}"><div id="testBannerWarn">Warning: You are logged into production! Data changes will be saved to the production database.</div></g:if>            
            
            <g:if env="production" test="${(controllerName == 'backoffice' || controllerName == 'appSetting')}"><div id="testBannerWarn">Warning: You are logged into production! Data changes will be saved to the production database!</div></g:if>
            
          </g:if>
      </div>
      <div id="spinner" class="spinner">Loading...</div>
      <div id="header">
        <div id="grailsLogoLink">   
          <a href="${homeLink}" title="${homeTitle}">${homeTitle}</a> 
        </div>
        <g:if test="${controllerName != 'login' && controllerName != 'logout' && controllerName != 'register'}">
	<div id="userInfo" class="clearfix">
                  <div class="small"><span class="welcome">Welcome, ${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}</span> <a href="javascript:redirectToLogin();">Logout</a></div>
                  <div class="small">Org: ${session.org?.name}</div>   
                  <div class="small">
                  <g:if test="${session.org?.code == 'OBBR'}">
                  <h6>Privileges:</h6> 
                  <ul class="gray">
                    <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") || session.authorities?.contains("ROLE_ADMIN")}'>
                        <g:if test="${session.DM == true}">
                          <li class="activeSession"><g:link controller="privilege" action="toggleDM" params="[returnPage: (request.requestURI - request.contextPath)]">DM</g:link></li>
                        </g:if><g:else>
                          <li><g:link controller="privilege" action="toggleDM" params="[returnPage: (request.requestURI - request.contextPath)]">DM</g:link></li>
                        </g:else>
                    </g:if><g:else>
                      <li>DM</li>
                    </g:else>
                    <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") || session.authorities?.contains("ROLE_ADMIN")}'>
                        <g:if test="${session.PRC == true}">
                          <li class="activeSession"><g:link controller="privilege" action="togglePRC" params="[returnPage: (request.requestURI - request.contextPath)]">PRC</g:link></li>
                        </g:if><g:else>
                          <li><g:link controller="privilege" action="togglePRC" params="[returnPage: (request.requestURI - request.contextPath)]">PRC</g:link></li>
                        </g:else>
                    </g:if><g:else>
                      <g:if test="${session.PRC == true}"><li class="activeSession">PRC</li></g:if><g:else><li>PRC</li></g:else>
                    </g:else>       
                    <g:if test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") || session.authorities?.contains("ROLE_ADMIN")}'>
                        <g:if test="${session.LDS == true}">
                          <li class="activeSession last"><g:link controller="privilege" action="toggleLDS" params="[returnPage: (request.requestURI - request.contextPath)]">LDS</g:link></li>
                        </g:if><g:else>
                          <li class="last"><g:link controller="privilege" action="toggleLDS" params="[returnPage: (request.requestURI - request.contextPath)]">LDS</g:link></li>
                        </g:else>
                    </g:if><g:else>
                      <g:if test="${session.LDS == true}"><li class="activeSession last">LDS</li></g:if><g:else><li class="last">LDS</li></g:else>
                    </g:else>
                  </ul>
                  </g:if> 
               </div>
               <div class="clear"></div>
               <div id="searchhelp" class="small"> 
                  <g:if test="${session.org?.code != 'MBB' && session.study?.code != 'BPVELSI' && session.study?.code != 'CTC'}">
		  <ul>
                    <li class="first"><g:link controller="textSearch" action="searchhome">Search</g:link></li>
                    <li class="last"><g:link controller="help" action="helphome">Help</g:link></li>
		  </ul>
                  </g:if>
              </div>
               <div class="clear"></div>
               <g:if test="${!params.ncd}"> <%-- no countdown timer when ncd is in the requst --%>
                  <div id="countdown" class="countdown hasCountdown">Session expires in: <b><span class="minutesleft"></span>:<span class="secondsleft"></span></b></div>
               </g:if>
         </div>
        <g:if test="${session.org?.code in ['OBBR','NDRI','SC','RPCI','VUMC','UNM','BMC','UPMC','EU','VARI']}"><div id="activityEvent"></div></g:if></g:if>
      </div><!--end header-->
      <div class="clear"></div>
      <g:layoutBody />
    </div><!--end body-wrapper-->     
    <div id="footer">
      <div id="vers"><div id="verstext">caHUB CDR DS v<g:meta name="app.version"/>${appMilestone?".$appMilestone":""}</div></div>
      <ul class="footerlogos clearfix">
        <li class="footer_cahub"><a target="_blank" href="http://cahub.cancer.gov" title="caHUB" >caHUB</a></li>
        <li class="footer_nci"><a target="_blank" href="http://www.cancer.gov/" title="NCI - National Cancer Institute">National Cancer Institute</a></li>
        <li class="footer_leidos"><a target="_blank" href="http://www.leidos.com/about/companies/leidos-biomedical-research" title="Leidos Biomedical Research Inc.">Leidos Biomedical Research Inc.</a></li>        
        <li class="footer_nih"><a target="_blank" href="http://www.nih.gov/" title="NIH - National Institutes of Health">National Institutes of Health</a></li>
        <li class="footer_dop"><a target="_blank" href="http://www.hhs.gov/" title="HHS - U.S. Department of Health &amp; Human Services">U.S. Department of Health &amp; Human Services</a></li>
        <li class="footer_usagov"><a target="_blank" href="http://www.usa.gov/" title="USA.gov">USA.gov</a></li>
      </ul>
    </div><!--end footer-->
    <!-- global pop-ups -->
    <div id="ca-dialog"></div>
    <div id="login-dialog"></div>
  </body>
</html>
