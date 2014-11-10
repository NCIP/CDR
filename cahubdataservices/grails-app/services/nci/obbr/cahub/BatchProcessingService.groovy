package nci.obbr.cahub
import groovy.sql.Sql
import nci.obbr.cahub.util.bpv.BatchProcessing
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.util.AppSetting
import grails.util.GrailsUtil

class BatchProcessingService {
    static transactional = false
     def sendMailService
     def dataSource
     
    def trackModule(){
        setNewBatch()
        scanAllBatches()
        
    }
    
    
    def setNewBatch(){
        
       def firstCaseId = AppSetting.findByCode('BPV_MODULE_TRACK_FIRST_CASE')?.value
      
      def firstCase = null
      if(firstCaseId){
          firstCase = CaseRecord.findByCaseId(firstCaseId)
      }
      
       def day_start_case=0
      if(firstCase){
         day_start_case = getDays(firstCase) 
      }
      if(firstCase && firstCase.batchProcessing)
         firstCase = null
         
      def caseMap = getAllCases(day_start_case)
     
        if(!firstCase)
        firstCase = findFirstCase(caseMap)
        
        
      
        if(firstCase){
         
            def firstDay = caseMap.get((BigDecimal)firstCase.id)
         
         
            def group=[]
            group.add(firstCase.id)
            caseMap.each(){ key, value->
             
               
                if( (firstDay - value) <= 60 && (firstDay - value >= 0) && (BigDecimal)firstCase.id != key){
                    group.add(key)
                      
                }
                
            }
            
            createBatch(firstCase, group)
            setNewBatch()
        }
      
     
    }

    def getAllCases(day_start_case) {
        
         def sqlString =   " select id, max(num_days) as num_days from ( " +
                       " select c.id,  trunc(sysdate) - trunc(t.date_sample_frozen) as num_days from dr_case c, " +
                       " bpv_work_sheet w, bpv_module3n_sheet m3, bpv_timepoint t " +
                       " where  c.id = w.case_record_id and w.id = m3.bpv_work_sheet_id and m3.sample_fr_id = t.id " +
                       " and t.date_sample_frozen is not null and w.sm3 = 1  " +
                       " and c.id not in (select id from dr_case where batch_processing_id is  not null or mt_excluded = 1) " +
                       " union " +
                       " select c.id,  trunc(sysdate) - trunc(t.date_sample_frozen) as num_days from dr_case c, " +
                       " bpv_work_sheet w, bpv_module4n_sheet m4, bpv_timepoint t" +
                       "  where c.id = w.case_record_id and w.id = m4.bpv_work_sheet_id and m4.sample_fr_id = t.id " +
                       " and t.date_sample_frozen is not null  and w.sm4 = 1 " +
                       " and c.id not in (select id from dr_case where batch_processing_id is  not null or mt_excluded = 1)) group by id order by num_days desc";
        
          def caseMap=[:]
        
          try{
            groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
            def total = 0;
            sql.eachRow(sqlString, { row ->
                
                   if(day_start_case){
                   if(day_start_case >= row[1]){
                      
                       caseMap.put(row[0], row[1])
                 
                   }
                   }else{
                      caseMap.put(row[0], row[1]) 
                   }
                    
                   
                })
            sql.close()
            
         
            
        }
        catch(Exception e){
            e.printStackTrace()
        }
        
        return caseMap
    }
    
    
    
    def getDays(caseRecord) {
        
         def sqlString =   " select max(num_days) as num_days from ( " +
                       " select  trunc(sysdate) - trunc(t.date_sample_frozen) as num_days from dr_case c, " +
                       " bpv_work_sheet w, bpv_module3n_sheet m3, bpv_timepoint t " +
                       " where  c.id = w.case_record_id and w.id = m3.bpv_work_sheet_id and m3.sample_fr_id = t.id " +
                       " and t.date_sample_frozen is not null and w.sm3 = 1 " +
                       " and c.id = " + caseRecord.id +
                       " union " +
                       " select  trunc(sysdate) - trunc(t.date_sample_frozen) as num_days from dr_case c, " +
                       " bpv_work_sheet w, bpv_module4n_sheet m4, bpv_timepoint t " +
                       "  where c.id = w.case_record_id and w.id = m4.bpv_work_sheet_id and m4.sample_fr_id = t.id " +
                       " and t.date_sample_frozen is not null  and w.sm4 = 1 " +
                       " and c.id = " + caseRecord.id + ")";
        
          def day=0
        
          try{
            groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
            def total = 0;
            sql.eachRow(sqlString, { row ->
               
                    day= row[0]
                    
                   
                })
            sql.close()
            
         
            
        }
        catch(Exception e){
            e.printStackTrace()
        }
        
        return day
    }
    
    
    def getMaxProcessingId() {
        
        def sqlString =   "select max(processing_id) from batch_processing"
        
          def caseMap=[]
          
          def id= 0
        
          try{
            groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
            def total = 0;
            sql.eachRow(sqlString, { row ->
                   
                   id = row[0]
                    
                   
                })
            sql.close()
          } catch(Exception e){
            e.printStackTrace()
        }
        
        return id
        
    }
    def findFirstCase(caseMap){
      
        def firstCase=null
        def maxDays = 0;
        def id=0
        caseMap.each(){key, value->
            if(value > maxDays){
                maxDays = value
                id = key
            }
            
        }
        
        if(maxDays >= 60){
         firstCase = CaseRecord.findById(id)
        
        }
        
        return firstCase
         
    }
    
    
    def getAvgDays(batchProcessing){
        def caseList = CaseRecord.findAllByBatchProcessing(batchProcessing)
       
        def caseStr=""
        caseList.each{
            caseStr += "," + it.id + ""
        }
        caseStr = caseStr.substring(1)
        
        def avgDays=0
        
         def sqlString =  " select avg(num_days) from ( "+
                          " select id, max(num_days) as num_days from ( "+
                          " select c.id,  trunc(sysdate) - trunc(t.date_sample_frozen) as num_days from dr_case c, "+
                          " bpv_work_sheet w, bpv_module3n_sheet m3, bpv_timepoint t "+
                          " where  c.id = w.case_record_id and w.id = m3.bpv_work_sheet_id and m3.sample_fr_id = t.id "+
                          " and t.date_sample_frozen is not null  and w.sm3 = 1 "+
                          " and c.id  in (" + caseStr + ") " +
                          " union " +
                          " select c.id,  trunc(sysdate) - trunc(t.date_sample_frozen) as num_days from dr_case c, " +
                          " bpv_work_sheet w, bpv_module4n_sheet m4, bpv_timepoint t "+
                          " where c.id = w.case_record_id and w.id = m4.bpv_work_sheet_id and m4.sample_fr_id = t.id " + 
                          " and t.date_sample_frozen is not null and w.sm4 = 1 " +
                          " and c.id  in (" + caseStr + ")) group by id ) ";
        
        
         try{
            groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
            def total = 0;
            sql.eachRow(sqlString, { row ->
                   
                   avgDays = row[0]
                  
                   
                })
            sql.close()
          } catch(Exception e){
            e.printStackTrace()
        }
        
        return avgDays
        
        
        
        
        
    }
    
    
     def scanAllBatches(){
         def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"    
         def allBatches = BatchProcessing.findAll()
       
         allBatches.each(){
             def emailDate2M2 = it.emailDate2M2
             def emailDate6M = it.emailDate6M
             def emailDate6M2 = it.emailDate6M2
             def emailDate18M = it.emailDate18M
             def emailDate18M2 = it.emailDate18M2
             
            
             if(!emailDate2M2){
                  def firstCase = it.firstCase
                  def days = getDays(firstCase)
                  if(days >= 74 ){
                      def group =CaseRecord.findAllByBatchProcessing(it)
                      def caseStr = ""
                      group.each(){it2->
                          caseStr += "," +it2.caseId
                          
                      }
              
                      it.emailDate2M2 = new Date()
                      it.save(failOnError:true)
                      def sub="The Second Alert:$env Module III/IV with batch #" + it.processingId + " is ready for 0-60 day processing"
                      def bodytext = caseStr.substring(1)
                      def emailTo = AppSetting.findByCode('BPV_MODULE_TRACK_DISTRO')?.bigValue
         
                       sendMailService.sendAppRequestEmail(sub, bodytext,emailTo)
                      
                  }
             }
             
             if(!emailDate6M){
                  def avgDays = getAvgDays(it)
               
                  if(avgDays >= 152 ){
                      def group =CaseRecord.findAllByBatchProcessing(it)
                      def caseStr = ""
                      group.each(){it2->
                          caseStr += "," +it2.caseId
                          
                      }
              
                      it.emailDate6M = new Date()
                      it.save(failOnError:true)
                      def sub="The First Alert:$env Module III/IV with batch #" + it.processingId + " is ready for 6 month processing"
                      def bodytext = caseStr.substring(1)
                      def emailTo = AppSetting.findByCode('BPV_MODULE_TRACK_DISTRO')?.bigValue
         
                       sendMailService.sendAppRequestEmail(sub, bodytext,emailTo)
                      
                  }
             }
                 
            
            if(!emailDate6M2){
                  def avgDays = getAvgDays(it)
               
                  if(avgDays >= 166 ){
                      def group =CaseRecord.findAllByBatchProcessing(it)
                      def caseStr = ""
                      group.each(){it2->
                          caseStr += "," +it2.caseId
                          
                      }
              
                      it.emailDate6M2 = new Date()
                      it.save(failOnError:true)
                      def sub="The Second Alert:$env Module III/IV with batch #" + it.processingId + " is ready for 6 month processing"
                      def bodytext = caseStr.substring(1)
                      def emailTo = AppSetting.findByCode('BPV_MODULE_TRACK_DISTRO')?.bigValue
         
                       sendMailService.sendAppRequestEmail(sub, bodytext,emailTo)
                      
                  }
             }
            
             if(!emailDate18M){
                 def avgDays = getAvgDays(it)
                  if(avgDays > 512 ){
                      def group =CaseRecord.findAllByBatchProcessing(it)
                      def caseStr = ""
                      group.each(){it2->
                          caseStr += "," +it2.caseId
                          
                      }
                      it.emailDate18M = new Date()
                      it.save(failOnError:true)
                       def sub="The First Alert:$env Module III/IV with batch #" + it.processingId + " is ready for 18 month processing"
                      def bodytext = caseStr.substring(1)
                      def emailTo = AppSetting.findByCode('BPV_MODULE_TRACK_DISTRO')?.bigValue
         
                       sendMailService.sendAppRequestEmail(sub, bodytext,emailTo)
                      
             }
             
         }
         
               if(!emailDate18M2){
                 def avgDays = getAvgDays(it)
                  if(avgDays > 526 ){
                      def group =CaseRecord.findAllByBatchProcessing(it)
                      def caseStr = ""
                      group.each(){it2->
                          caseStr += "," +it2.caseId
                          
                      }
                      it.emailDate18M2 = new Date()
                      it.save(failOnError:true)
                       def sub="The Second Alert:$env Module III/IV with batch #" + it.processingId + " is ready for 18 month processing"
                      def bodytext = caseStr.substring(1)
                      def emailTo = AppSetting.findByCode('BPV_MODULE_TRACK_DISTRO')?.bigValue
         
                       sendMailService.sendAppRequestEmail(sub, bodytext,emailTo)
                      
             }
             
         }
         
     }
     }
    
    def createBatch(firstCase, group){
       
      try{
        def batchProcessing = new BatchProcessing()
        batchProcessing.firstCase = firstCase
        def processingId = 1
        def maxId = getMaxProcessingId()
        if(maxId){
            processingId = maxId + 1
        }
        batchProcessing.processingId = processingId
        batchProcessing.emailDate2M = new Date()
         batchProcessing.save(flush:true, failOnError:true)
        
        String caseIds=""
        group.each(){
            def caseRecord=CaseRecord.findById(it)
            caseRecord.batchProcessing = batchProcessing
            caseRecord.save(flush:true, failOnError:true)
            caseIds += "," + caseRecord.caseId
            
        }
        
         def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"      
        
         def sub="The First Alert:$env Module III/IV with batch #" + processingId + " is ready for 0-60 day processing"
         def bodytext = caseIds.substring(1)
         def emailTo = AppSetting.findByCode('BPV_MODULE_TRACK_DISTRO')?.bigValue
         
           sendMailService.sendAppRequestEmail(sub, bodytext,emailTo)
        }catch(Exception e){
            e.printStackTrace()
             throw new RuntimeException(e.toString())
         
        }
        
        
    }
    
}
