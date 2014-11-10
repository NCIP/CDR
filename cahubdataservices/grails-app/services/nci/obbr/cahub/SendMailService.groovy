package nci.obbr.cahub

import grails.util.GrailsUtil
import nci.obbr.cahub.util.AppSetting
import org.apache.commons.validator.EmailValidator 

class SendMailService {

    def mailService
    static transactional = false
    def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"

    def sendServiceEventEmail(message, xml) {
        try {
            def recipient = AppSetting.findByCode('REST_SERVICE_EMAIL')?.value
            if(recipient) {
                mailService.sendMail {
                    to filterInvalidAddr(recipient).split(',')
                    from 'noreply@cahub.ncifcrf.gov'
                    subject "CDR Alert:$env ${message} web service event was received by the CDR"
                    body "${xml}"
                }
            } else {
                log.warn("Service Mail Not Sent: No recipient found!")
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendFileUploadEventEmail(fileUploadInstance) {
        try {
            def recipient = AppSetting.findByCode('FILE_UPLOAD_ALERT_EMAIL')?.value
            if(recipient) {
                mailService.sendMail {
                    to filterInvalidAddr(recipient).split(',')
                    from 'noreply@cahub.ncifcrf.gov'
                    subject "CDR Alert:$env New file(s) uploaded for ${fileUploadInstance.caseId}"
                    body "Case ID: ${fileUploadInstance.caseId}\n\nComments: ${fileUploadInstance.comments} \n\nLog into CDR DS to view: https://cahub.ncifcrf.gov/cahubdataservices/"
                }
            } else {
                log.warn("Service Mail Not Sent: No recipient found!")
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendLDACCImportEmail(xml) {
        try {
            def recipient = AppSetting.findByCode('LDACC_IMPORT_EMAIL')?.value
            if(recipient) {
                mailService.sendMail {
                    to filterInvalidAddr(recipient).split(',')
                    from 'noreply@cahub.ncifcrf.gov'
                    subject "CDR Alert:$env LDACC Import Report - ${(new Date()).toString()}"
                    body "${xml}"
                }
            } else {
                log.warn("Service Mail Not Sent: No recipient found!")
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendAppRequestCreateEventEmail(AppReqID, bodytext,emailTo) {
        try {
            mailService.sendMail {
                to filterInvalidAddr(emailTo).split(',')
                from 'noreply@cahub.ncifcrf.gov'
                subject "Request for caHUB application access- RequestID: "+AppReqID
                body "${bodytext}"
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendAppRequestEmail(sub, bodytext,emailTo) {
        try {
            mailService.sendMail {
                to filterInvalidAddr(emailTo).split(',')
                from 'noreply@cahub.ncifcrf.gov'
                subject "${sub}"
                body "${bodytext}"
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def mytest(subject, bodytext) {
        println("subject: " + subject)
        println("bodytext: " + bodytext)
    }

    def sendServiceEmailWithAttachment(recipient, emailSubject, content, fileName='tempFile', fileExtension='.xml', contentType='text/xml') {
        try {
            if(recipient) {
                def attachment = File.createTempFile(fileName, fileExtension)
                attachment.write(content)
                mailService.sendMail {
                    multipart true
                    to filterInvalidAddr(recipient).split(',')
                    from 'noreply@cahub.ncifcrf.gov'
                    subject "CDR Alert:$env ${emailSubject}"
                    body "${content}"
                    attachBytes "${fileName}${fileExtension}", contentType, attachment.readBytes()
                }
                attachment.delete();
            } else {
                log.warn("Service Mail Not Sent: No recipient found!")
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendServiceEmail(recipient, emailSubject, emailBody, emailFormat='body') {
        try {
            if(recipient) {
                mailService.sendMail {
                    to filterInvalidAddr(recipient).split(',')
                    from 'noreply@cahub.ncifcrf.gov'
                    subject "CDR Alert:$env $emailSubject"
                    "$emailFormat" "$emailBody"
                }
            } else {
                log.warn("Service Mail Not Sent: No recipient found!")
            }
        } catch(Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendActivityEventEmail(recipient, emailSubject, emailBody) {
        try {
            if(recipient) {
                mailService.sendMail {
                    to "noreply@cahub.ncifcrf.gov"
                    from "noreply@cahub.ncifcrf.gov"
                    bcc filterInvalidAddr(recipient).split(',')
                    subject emailSubject
                    body emailBody
                }
            } else {
                log.warn("Service Mail Not Sent: No recipient found!")
            }
        } catch (Exception e) {
            throw new RuntimeException("Service Mail Not Sent: " +e.toString())
        }
    }

    def sendCaseRecallConsentNotifyEmail(sub, bodytext,emailTo) {
        try {
            mailService.sendMail {
                to filterInvalidAddr(emailTo).split(',')
                from 'noreply@cahub.ncifcrf.gov'
                subject "${sub}"
                body "${bodytext}"
            }
        } catch(Exception e) {
            throw new RuntimeException("Case Recall Consent Email NOT SENT: " +e.toString())
        }
    }
    
    
    
    def filterInvalidAddr(emails){
        EmailValidator validator = EmailValidator.getInstance();
        def list = emails.split(",")
        def goodEmails=""
        list.each(){
            if(validator.isValid(it.trim())){
                goodEmails = goodEmails + "," + it.trim()
            }else{
                log.warn(it + " is an invalid email address")
            }
        }
        
        if(goodEmails){
            goodEmails = goodEmails.substring(1)
        }else{
            //if all the emails are bad, use default one, so the sending method will not crash
            goodEmails = "NCIFCAHUBSuper@mail.nih.gov"
        }
        
        return goodEmails
    }
    
}
