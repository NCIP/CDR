package cahubdataservices


class AgeUpdateJob {
    static triggers = {
         cron name: 'updateTrigger', cronExpression: "0 30 22 * * ?" 
    } 
    
    def group = "MyGroup"  
    def caseReportFormService
    
    def execute(){
    
       println " ${(new Date()).toString()},  Job run!"
       caseReportFormService.updateAge()
  }
}
