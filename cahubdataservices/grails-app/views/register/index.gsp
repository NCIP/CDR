<html>
  <g:set var="bodyclass" value="loginpage" scope="request"/>
  <head>
    <title><g:message code='spring.security.ui.register.title'/></title>
    <meta name='layout' content='cahubTemplate' />
  </head>
  <body>
    <div id="nav" class="clearfix">
       <div id="navlist"></div>
    </div>
    <div id="container" class="clearfix">
      <div id="homemenu">
          <g:if test='${emailSent}'>
            <div class="message"><g:message code='spring.security.ui.register.sent'/></div>
          </g:if>
            <div class="inner ui-corner-all">
              <h1><g:message code='spring.security.ui.register.description'/></h1>
              <g:form action='register' name='registerForm' class="cssform registerForm" autocomplete='off'>
	<table>
	<tbody>

		<s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${command}"
                         size='40' labelCodeDefault='Username' value="${command.username}"/>

		<s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
		                   size='40' labelCode='user.email.label' labelCodeDefault='E-mail'/>

		<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                             size='40' labelCodeDefault='Password' value="${command.password}"/>

		<s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
                             size='40' labelCodeDefault='Password (again)' value="${command.password2}"/>

	</tbody>
	</table>

                  <fieldset class="submit">
                    <input class="button ui-button ui-widget ui-state-default ui-corner-all" id="create" type="submit" value="<g:message code='spring.security.ui.register.submit'/>">
                  </fieldset>
              </g:form>
              <p><g:message code='spring.security.ui.register.text'/></p>
            </div>
        </div>
    </div>
</body>
</html>


