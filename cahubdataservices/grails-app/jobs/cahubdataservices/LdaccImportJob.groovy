package cahubdataservices
import nci.obbr.cahub.util.*

class LdaccImportJob {
    static triggers = {
         cron name: 'ldaccTrigger', cronExpression: "0 19 1 * * ?" 
        
    } 
    
    def group = "MyGroup"  
    def rpcService
    
    def execute(){
      def ldaccImport = AppSetting.findByCode('LDACC_IMPORT_ON_OFF')?.value
       if(ldaccImport?.equalsIgnoreCase("1")){
      // println " ${(new Date()).toString()},  Job run!"
        log.info((new Date()).toString() + " started LDACC Import")
       rpcService.importLdaccData()
        log.info((new Date()).toString() + " ended LDACC Import")
       }else{
            log.info((new Date()).toString() + " not import from LDACC")
       }
  }
}
