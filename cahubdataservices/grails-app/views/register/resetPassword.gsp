<html>
  <g:set var="bodyclass" value="loginpage" scope="request"/>
  <head>
     <title><g:message code='${title}'/></title>
     <meta name='layout' content='cahubTemplate' />
  </head>
  <body>
    <div id="nav" class="clearfix">
       <div id="navlist"></div>
    </div>
    <div id="container" class="clearfix">
      <div id="homemenu">
          <div class="inner ui-corner-all">
            <h1>${header}</h1>
            <g:form action="${action?:'resetPassword'}" name='resetPasswordForm' autocomplete='off' class="cssform registerForm">
                <div class="sign-in">
                    <g:if test='${passwordChanged}'>
                        <div class="message1"><g:message code='spring.security.ui.resetPassword.success'/></div>
                        <div class="message2">Please click <a href="${loginUrl}">here</a> to log into caHUB CDR Data Services.</div>
                    </g:if><g:else>
                        <g:hiddenField name='t' value='${token}'/>
                        <table>
                          <tbody>
                            <g:if test='${checkOldPassword}'>
                                <s2ui:passwordFieldRow name='oldpassword' labelCode='resetPasswordCommand.oldpassword.label' bean="${command}" labelCodeDefault='Old Password' value="${command?.oldpassword}"/>
                            </g:if><g:else>
                                <g:hiddenField name='oldpassword' value='[PROTECTED]'/>
                            </g:else>
                            <s2ui:passwordFieldRow name='password' labelCode='resetPasswordCommand.password.label' bean="${command}" labelCodeDefault='New Password' value="${command?.password}"/>
                            <s2ui:passwordFieldRow name='password2' labelCode='resetPasswordCommand.password2.label' bean="${command}" labelCodeDefault='Confirm' value="${command?.password2}"/>
                           </tbody>  
                         </table>
                         <fieldset class="submit">
                            <input class="button ui-button ui-widget ui-state-default ui-corner-all" id="reset" type="submit" value="<g:message code='spring.security.ui.resetPassword.submit'/>">
                         </fieldset>
                    </g:else>
                </div>
              </g:form>
              <p>Password must be at least eight (8) characters in length and have at least one letter, number, and special character: !@#$%^&</p>
              <p>${desc}</p>
            </div>
        </div>
      </div>
    </body>
</html>
