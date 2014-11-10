package cahubdataservices
import nci.obbr.cahub.util.*
import nci.obbr.cahub.datawarehouse.SpecimenDw
import nci.obbr.cahub.datawarehouse.SpecimenDwController
import nci.obbr.cahub.datawarehouse.CaseDw

class SpecimenDwJob {
    static triggers = {
//         cron name: 'SpecimenDwTrigger', cronExpression: "0 45 13 * * ?" 
         cron name: 'SpecimenDwTrigger', cronExpression: "0 00 22 * * ?" 
    } 
    
    def group = "MyGroup"  
    def specimenDwService
    def caseDwService
    
    def execute(){
        def isRunning = AppSetting.findByCode('SPECIMEN_DW_RUN_STATUS')
        if (isRunning) {
            if(isRunning.value.equals('NOTRUNNING')) {
                try {
                    isRunning.value='RUNNING'
                    isRunning.save(flush: true)
                    def rebuildSpecDw = AppSetting.findByCode('SPECIMEN_DW_ON_OFF')?.value
                    def caseDwInstance = new CaseDw()
                    if(rebuildSpecDw?.equalsIgnoreCase("On")){
                        log.info("Started Specimen DW Rebuild")
                        caseDwService.populateCaseDw(caseDwInstance)
                        log.info("Ended Specimen DW Rebuild")
                    } else {
                        log.info("Specimen DW Rebuild is turned off by configuration (SPECIMEN_DW_ON_OFF)")
                    }
                    isRunning.value='NOTRUNNING'
                    isRunning.save(flush: true)
                } 
                catch (Exception e) {
                    log.error("Ended Specimen DW Rebuild!")
                    def fixUp = AppSetting.findByCode('SPECIMEN_DW_RUN_STATUS')  // The variable isRunning is out of scope in a catch block
                    fixUp.value='NOTRUNNING'
                    fixUp.save(flush: true)
                    e.printStackTrace()
                }
            } else {
                log.info("Specimen DW Rebuild not running because of a previous instance (SPECIMEN_DW_RUN_STATUS=RUNNING)")
            }
        } else {
            log.error("Application ERROR -- SPECIMEN_DW_RUN_STATUS flag is not set in appSettings!")
        }
    }
}
