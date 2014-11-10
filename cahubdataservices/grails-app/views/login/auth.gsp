<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="loginpage" scope="request"/>
<g:set var="cbrIMSHost" value="${AppSetting.findByCode('CBR_IMS_HOST')?.value}" />
<g:set var="cbrIMSName" value="${AppSetting.findByCode('CBR_IMS_NAME')?.value}" />
<g:set var="helpemail" value="${AppSetting.findByCode('HELP_EMAIL')}" />
<g:set var="homeTitle" value="caHUB" scope="request"/>
<g:if test="${session.org?.code == 'OBBR'}">
   <g:set var="homeLink" value="/cahubdataservices/home/choosehome" scope="request"/>
</g:if>
<g:else>
   <g:set var="homeLink" value="/cahubdataservices/home" scope="request"/>
</g:else>
<!-- PH testing for bigValue display at the end of the login box -->
<g:set var="loginbulletin" value="${AppSetting.findByCode('LOGIN_BULLETIN')}" />
<head>
  <meta name='layout' content='cahubTemplate' />
</head>
<body>
 <div id="nav" class="clearfix">
    <div id="navlist"></div>
 </div>
 <div id="container" class="clearfix">
  <div id="homemenu">
    <g:if test ="${loginbulletin.bigValue}">
        <div class="message"><%=loginbulletin.bigValue%></div>
    </g:if>
    <g:if test='${flash.message}'><div id="message" class="redtext">${flash.message}</div></g:if>
    <div class="inner ui-corner-all">
      <h1>CDR Data Services Login</h1>
      <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='on'>
        <fieldset class="username">
          <label for="username">Login ID</label>
          <input type="text" class="text_" name="j_username" id="username" />
        </fieldset>
        <fieldset class="password">
          <label for="password">Password</label>
          <input type="password" class="text_" name="j_password" id="password" />
        </fieldset>
        <fieldset class="submit">
          <input type="submit" class="button" value="Login" /> 
        </fieldset>
      </form>
      <h3>Important links</h3>
      <ul>
        <li><a target="_blank" href="${cbrIMSHost}">Go to ${cbrIMSName} website</a> <i> (GTEx only)</i></li>
        <li><g:link controller="register" action="forgotPassword">Reset or Forgot Password?</g:link> <i>(for non-NIH logins only)</i></li>
        <li><a href="mailto:${helpemail.value}">Request technical assistance via email</a><br /><i>(A support response will be provided by the end of the next business day, 8am-5pm Eastern)</i></li>
      </ul>
    </div>
  </div>
 </div><!-- end container --> 
</body>
