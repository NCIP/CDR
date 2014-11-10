package cahubdataservices


class TextIndexJob {
    static triggers = {
         cron name: 'cronTrigger', cronExpression: "0 40 23 * * ?" 
    } 
    
    def group = "MyGroup"  
    def textSearchService
    
    def execute(){
    
       println " ${(new Date()).toString()},  Job run!"
       textSearchService.index_all()
  }
}
