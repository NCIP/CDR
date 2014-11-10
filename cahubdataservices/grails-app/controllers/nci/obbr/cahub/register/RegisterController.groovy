package nci.obbr.cahub.register

import nci.obbr.cahub.authservice.User
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


class RegisterController extends grails.plugins.springsecurity.ui.RegisterController {

    def resetPassword = { ResetPasswordCommand command ->
        String token = params.t
        def registrationCode = !token ?: RegistrationCode.findByToken(token)

        if(!registrationCode) {
            flash.error = message(code: 'spring.security.ui.resetPassword.badCode')
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            return
        }

        if(User.findByUsername(registrationCode.username)?.passwordExpired) {
            redirect uri: "/register/expiredPassword?t=$token"
            return
        }

        def title = message(code: 'spring.security.ui.resetPassword.title')
        def header = message(code: 'spring.security.ui.resetPassword.header')

        if(!request.post) {
            return [title:title, header: header, token: token]
        }

        command.username = registrationCode.username
        command.validate()

        if(command.hasErrors()) {
            return [title:title, header: header, token: token, command: command]
        }

        updateUserRegistration(User.findByUsername(command.username), command.password)
        return [title:title, header:header, passwordChanged:true, loginUrl:getRedirectUrl()]
    }

    def expiredPassword = { ResetPasswordCommand command ->
        String token = params.t
        if(!token) {
            token = session['EXPIRED_PASSWORD_TOKEN']
        }

        def registrationCode = !token ?: RegistrationCode.findByToken(token)
        if(!registrationCode) {
            flash.error = message(code: 'spring.security.ui.resetPassword.badCode')
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            return
        }

        def title = message(code: 'spring.security.ui.expiredPassword.title')
        def header = message(code: 'spring.security.ui.expiredPassword.header')
        def desc = message(code: 'spring.security.ui.expiredPassword.description')

        def username = registrationCode?.username
        def user = User.findByUsername(username)
        boolean checkOldPassword = registrationCode.token.equals(session['EXPIRED_PASSWORD_TOKEN']) ? false : true

        command.username = username
        if(!request.post) {
            render view: "resetPassword", model: [action:'expiredPassword', title:title, header:header, token:token, desc:desc, checkOldPassword:checkOldPassword]
            return
        }

        command.username = username
        command.validate()

        if(checkOldPassword) {
            validateOldPassword(user, command)
        }

        if(command.hasErrors()) {
            render view: "resetPassword", model: [action:'expiredPassword', title:title, header:header, token:token, desc:desc, checkOldPassword:checkOldPassword, command:command]
            return
        }

        updateUserRegistration(user, command.password)
        render view: "resetPassword", model: [title:title, header:header, passwordChanged:true, loginUrl:getRedirectUrl()]
    }

    def validateOldPassword(user, command) {
        if(command.oldpassword &&
            !user.password.equals(springSecurityService.encodePassword(command.oldpassword))) {
            command.errors.rejectValue('oldpassword', 'Old password is invalid')
        }
    }

    def getRedirectUrl() {
        def loginUrl
        if(SpringSecurityUtils.securityConfig.cas.active) {
            loginUrl = "${SpringSecurityUtils.securityConfig.logout.afterLogoutUrl}"
        } else {
            loginUrl = "${resource(dir:'/')}"
        }

        return loginUrl
    }

    def updateUserRegistration(user, password) {
        RegistrationCode.withTransaction { status ->
            user.password = password
            user.passwordExpired = false
            user.passwordChangeDate = new Date()
            user.save()

            RegistrationCode.executeUpdate(
                "delete RegistrationCode rc where rc.username = :username",
                    [username: user.username])
        }
    }
}

class ResetPasswordCommand {
    String username
    String password
    String password2
    String oldpassword

    static constraints = {
        password blank: false, nullable: false, validator: RegisterController.passwordValidator
        password2 validator: RegisterController.password2Validator
        oldpassword blank: false, nullable: false//, validator: RegisterController.oldPasswordValidator
    }
}
