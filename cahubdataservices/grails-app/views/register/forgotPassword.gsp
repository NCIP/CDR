<html>
  <g:set var="bodyclass" value="loginpage" scope="request"/>
  <head>
    <title><g:message code='spring.security.ui.forgotPassword.title'/></title>
    <meta name='layout' content='cahubTemplate' />
  </head>
  <body>
    <div id="nav" class="clearfix">
       <div id="navlist"></div>
    </div>
    <div id="container" class="clearfix">
      <div id="homemenu">
          <g:if test='${emailSent}'>
            <div class="message"><g:message code='spring.security.ui.forgotPassword.sent'/></div>
          </g:if>
         
            <div class="inner ui-corner-all">
              <g:if test="${flash.error}">
                   <div class="errors">${flash.error}</div>
             </g:if>
              <h1><g:message code='spring.security.ui.forgotPassword.header'/></h1>
              
              <g:form action='forgotPassword' class="cssform" name="forgotPasswordForm" autocomplete='off'>
                  <fieldset class="username">
                    <label for="username"><g:message code='spring.security.ui.forgotPassword.username'/></label>
                    <g:textField name="username" size="25" />
                  </fieldset>
                  <fieldset class="submit">
                    <input class="button ui-button ui-widget ui-state-default ui-corner-all" id="reset" type="submit" value="<g:message code='spring.security.ui.forgotPassword.submit'/>">
                    <input class="button ui-button ui-widget ui-state-default ui-corner-all" id="cancel" value="<g:message code='spring.security.ui.forgotPassword.cancel'/>" type="button">
                  </fieldset>
              </g:form>
              <p><g:message code='spring.security.ui.forgotPassword.description'/></p>
            </div>
        </div>
    </div>
</body>
</html>
