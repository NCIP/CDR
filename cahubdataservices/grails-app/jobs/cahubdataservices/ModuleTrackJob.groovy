package cahubdataservices
import nci.obbr.cahub.util.*

class ModuleTrackJob {
    
    static triggers = {
         
        
        cron name: 'moduleTrack',  cronExpression: "0 01 01 * * ?"
        
    } 
    
    def group = "MyGroup"  
    def batchProcessingService
    
    def execute(){
     
      // println " ${(new Date()).toString()},  Job run!"
        log.info((new Date()).toString() + " started module track")
        batchProcessingService.trackModule()
        log.info((new Date()).toString() + " ended module track")
    
  }
}
