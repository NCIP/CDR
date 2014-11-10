package cahubdataservices

import nci.obbr.cahub.util.AppSetting

class ExpirePasswordsJob {
    static triggers = {
        // midnight daily
        cron name: 'myTrigger', cronExpression: '0 0 0 * * ?'
    }

    def userService
    def group = "MyGroup"

    def execute() {
        if("1".equals(AppSetting.findByCode('PASSWORD_EXPIRE_CRONJOB_FLAG')?.value?.trim())) {
            log.info("  [start]: passwordExpirationJob")
            userService.initPasswordExpirationWorkflow()
            log.info(" [finish]: passwordExpirationJob")
        }
    }
}
