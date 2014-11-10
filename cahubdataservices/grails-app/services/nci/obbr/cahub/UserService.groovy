package nci.obbr.cahub

import grails.util.GrailsUtil
import groovy.text.SimpleTemplateEngine

import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.authservice.User

import org.codehaus.groovy.grails.web.util.WebUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode


class UserService {
    def userCache
    def sendMailService
    def grailsApplication

    static transactional = true
    boolean isProduction = "production".equalsIgnoreCase(GrailsUtil.environment)

    String url = AppSetting.findByCode('CDR_PROTOCOL')?.value +
        AppSetting.findByCode('CDR_HOST')?.value + "/cahubdataservices/register/resetPassword"

    def initPasswordExpirationWorkflow() {
        
        for (int i=0;i<3;i++)
        {
            int n = 0
            long current = -1
            long distance = 500
            while(true)
            {
                if (current < 0)
                {
                    
                    java.util.Calendar cal = java.util.Calendar.getInstance()
                    current = cal.getTimeInMillis() 
                    //println 'CCCC '+i+'-1 ('+n+') current= ' + current
                    continue
                }
                java.util.Calendar cal2 = java.util.Calendar.getInstance()
                long c = cal2.getTimeInMillis() 
                //println 'CCCC '+i+'-2 ('+n+') = ' + c
                if ((c - current) < distance) continue
                //println 'CCCC '+i+'-3 ('+n+') = ' + c + ', d = ' + (c - current)
                try
                {
                    if (i == 0) expirePassword()
                    else if (i == 1) mailReminderNotice()
                    else if (i == 2) setDefaultPasswordChangeDate()
                    
                    break
                }
                catch(org.hibernate.StaleObjectStateException ee)
                {
                    n++
                    if (n > 3)
                    {
                        println 'CCCC '+i+'-3 ('+n+') Exception = ' + ee.getMessage()
                        ee.printStackTrace()
                        throw ee
                    }
                }
                current = -1
                distance = 300
            }
        }
    }

    def expirePassword() {
        try {
            int maxAge = grailsApplication.config.user.password.expire.max.age
            Date cutoffDate = new Date() - maxAge

            def users = User.executeQuery(
                'from User user where user.passwordChangeDate <= :cutoffDate and user.passwordExpired = :isExpired and user not in '+
                    '(select distinct userrole.user from Role role, UserRole userrole where role.authority = :authority and userrole.role = role)',
                        [cutoffDate: cutoffDate, isExpired: false, authority: 'ROLE_SERVICE'])

            def binding
            def registrationCode
            def engine = new SimpleTemplateEngine()

            String body
            String subject = "${SpringSecurityUtils.securityConfig.ui.expiredPassword.emailSubject}"

            for(user in users) {
                User.withTransaction { status ->
                    registrationCode = RegistrationCode.findByUsername(user.username)
                    if(!registrationCode) {
                        registrationCode = new RegistrationCode(username: user.username)
                        registrationCode.save()
                    }

                    user.passwordExpired = true
                    user.save()
                    userCache.removeUserFromCache user.username

                    if(isSendMail()) {
                        body = "${SpringSecurityUtils.securityConfig.ui.expiredPassword.emailBody}"
                        binding = ["username":user.username, "url":"${url}?t=$registrationCode.token"]

                        body = engine.createTemplate(body).make(binding).toString()
                        sendMailService.sendServiceEmail(user.email, subject, body, 'html')
                    }
                }
            }

            log.info("${users.size()} ${users.size() > 1 ? 'users':'user'} password expired.")
        } catch(Exception e) {
            sendFailureAlert('Expiring User Password', "$e.message\n\n$e.stackTrace")
            log.error("problem expiring password for user $user.username : $e.message", e)
            throw new RuntimeException("problem expiring password for user $user.username : $e.message", e)
        }
    }

    def mailReminderNotice() {
        try {
            def users = []
            if(isSendMail()) {
                int maxAge = grailsApplication.config.user.password.expire.max.age
                int reminderAge = grailsApplication.config.user.password.expire.reminder.notice.age

                Date startCutoffDate = new Date() - maxAge
                Date endCutoffDate = new Date() - (maxAge - reminderAge)

                users = User.executeQuery(
                    'from User user where user.passwordChangeDate > :startCutoffDate and '+
                        'user.passwordChangeDate <= :endCutoffDate and user.passwordExpired = :isExpired and '+
                            'user.enabled = :isEnabled and user.accountLocked = :isAccountLocked and user.accountExpired = :isAccountExpired and user not in '+
                                '(select distinct userrole.user from Role role, UserRole userrole where role.authority = :authority and userrole.role = role)',
                                [startCutoffDate: startCutoffDate, endCutoffDate: endCutoffDate, isExpired: false, isEnabled: true, isAccountLocked: false, isAccountExpired: false, authority: 'ROLE_SERVICE'])

                def binding
                def engine = new SimpleTemplateEngine()

                def daysRemain
                String expireDate
                def registrationCode

                String body
                String subject = "${SpringSecurityUtils.securityConfig.ui.expiredPassword.reminder.emailSubject}"

                for(user in users) {
                    daysRemain = user.passwordChangeDate.minus(startCutoffDate)
                    expireDate = (new Date()).plus(daysRemain).format("EEEEEEEEEE, MMMMMMMMMM d, yyyy")
                    daysRemain = daysRemain > 1 ? "$daysRemain days":"$daysRemain day"

                    RegistrationCode.withTransaction { status ->
                        registrationCode = RegistrationCode.findByUsername(user.username)
                        if(!registrationCode) {
                            registrationCode = new RegistrationCode(username: user.username)
                            registrationCode.save()
                        }

                        body = "${SpringSecurityUtils.securityConfig.ui.expiredPassword.reminder.emailBody}"
                        binding = ["username":user.username, "expireDate":expireDate, "daysRemain":daysRemain, "url":"${url}?t=$registrationCode.token"]

                        body = engine.createTemplate(body).make(binding).toString()
                        sendMailService.sendServiceEmail(user.email, subject, body, 'html')
                    }
                }
            }

            log.info("${users.size()} ${users.size() > 1 ? 'users were':'user was'} sent a password expiration reminder notice.")
        } catch(Exception e) {
            sendFailureAlert('Password Expiration Notice', "$e.message\n\n$e.stackTrace")
            log.error("problem expiration reminder notice to user $user.username : $e.message", e)
            throw new RuntimeException("problem expiration reminder notice to user $user.username : $e.message", e)
        }
    }

    def setDefaultPasswordChangeDate() {
        try {
            def users = User.executeQuery(
                'from User user where user.passwordChangeDate is null and user not in '+
                    '(select distinct userrole.user from Role role, UserRole userrole where role.authority = :authority and userrole.role = role)',
                        [authority: "ROLE_SERVICE"])

            Date today = new Date()
            for(user in users) {
                User.withTransaction { status ->
                    user.passwordChangeDate = today
                    user.save()
                }
            }

            log.info("${users.size()} ${users.size() > 1 ? 'users':'user'} password change date was set to today's date [${today}].")
        } catch(Exception e) {
            sendFailureAlert('Default Password Change Date', "$e.message\n\n$e.stackTrace")
            log.error("problem setting the default password change date for user $user.username : $e.message", e)
            throw new RuntimeException("problem setting the default password change date for user $user.username : $e.message", e)
        }
    }

    protected boolean isSendMail() {
        return isProduction && "1".equals(AppSetting.findByCode('PASSWORD_EXPIRE_EMAIL_FLAG')?.value?.trim())
    }

    protected void sendFailureAlert(title, cause) {
        def subject = "User Service Failure - $title"
        def recipient = AppSetting.findByCode('PASSWORD_EXPIRE_ALERT_RECIPIENT')?.value
        def body = "An error occurred while executing CDR User Service. Please check the application log for detail information.\n\n $cause"
        sendMailService.sendServiceEmail(recipient, subject, body)
    }
}
