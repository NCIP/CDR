package nci.obbr.cahub


import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.prc.*;
import java.text.SimpleDateFormat
import java.text.DecimalFormat

class FeedbackService {

    def activityEventService
    
   def getFeedbackIssueList(feedbackInstance) { 
       try{
           def caseRecord = feedbackInstance.caseRecord
         
          def result = FeedbackIssue.executeQuery("select fi from FeedbackIssue fi where caseRecord.id=?  and (forFzn is null or forFzn = false)", [caseRecord.id])
         
           return result
       
       }catch(Exception e){
              e.printStackTrace()
           
               throw new RuntimeException(e.toString())
       }
        

    }
    

     def  getFeedbackIssueResolutionDisplayList(feedbackInstance) { 
        def result=[]
        try{
           def caseRecord = feedbackInstance.caseRecord
         
           def resolution  = FeedbackIssueResolution.executeQuery("select fir from FeedbackIssueResolution  fir  inner join fir.feedbackIssue fi  where fi.caseRecord.id=?   and (fi.forFzn is null or fi.forFzn = false) order by fir.id", [caseRecord.id])
         
            resolution.each(){
              
               def map = [:]
               def tissue = it.feedbackIssue.specimenRecord?.tissueType?.name
               def specumenId = it.feedbackIssue.specimenRecord?.specimenId
               map.put("specimenId", specumenId )
               map.put("tissue", tissue)
         
               map.put("issueDescription", it.issueDescription)
               map.put("resolutionComments", it.resolutionComments)
               String pattern = "MM/dd/yyyy";
               SimpleDateFormat format = new SimpleDateFormat(pattern);
               def dateString = format.format(it.feedbackSubmission.dateSubmitted)

               map.put("dateSubmitted", dateString)
               result.add(map)
           }
           def issue = FeedbackIssue.executeQuery("select fi from FeedbackIssue fi where caseRecord.id=?  and (forFzn is null or forFzn = false)", [caseRecord.id])
           issue.each(){
                
                  
                   def feedbackIssueResolutionList = FeedbackIssueResolution.executeQuery("select fir from FeedbackIssueResolution fir inner join fir.feedbackIssue fi where (fi.forFzn is null or fi.forFzn = false) and fi.id=? and fir.feedbackSubmission.id = (select max(fir2.feedbackSubmission.id) from FeedbackIssueResolution fir2 inner join fir2.feedbackIssue fi2 where fi2.id=?)", [it.id, it.id])
                  
                    if(feedbackIssueResolutionList){
                        def feedbackIssueResolution = feedbackIssueResolutionList.get(0)
                    
                        if(feedbackIssueResolution.issueDescription != it.issueDescription || feedbackIssueResolution.resolutionComments != it.resolutionComments){
                          
                              def map = [:]
                              def tissue = it.specimenRecord?.tissueType?.name
                              map.put("tissue", tissue)
                              map.put("issueDescription", it.issueDescription)
                              map.put("resolutionComments", it.resolutionComments)
                              map.put("specimenId", it.specimenRecord?.specimenId)
                              result.add(map)
                        }
                    }else{
                          
                        
                           def map = [:]
                            def tissue = it.specimenRecord?.tissueType?.name
                              map.put("tissue", tissue)
                              map.put("issueDescription", it.issueDescription)
                              map.put("resolutionComments", it.resolutionComments)
                              map.put("specimenId", it.specimenRecord?.specimenId)
                              result.add(map)
                    }
                    
               
           }
           
           return result
       }catch(Exception e){
              e.printStackTrace()
             
               throw new RuntimeException(e.toString())
       }
        

    }
    
    
   
     
    def getFeedbackSubList(feedbackInstance){
       try{
           def caseRecord = feedbackInstance.caseRecord
         
           def result = FeedbackSubmission.executeQuery("select frs from FeedbackSubmission frs inner join frs.caseRecord c where c.id=? and (frs.forFzn=null or frs.forFzn=false) order by frs.id", [caseRecord.id])
         
           return result
       
       }catch(Exception e){
              e.printStackTrace()
               throw new RuntimeException(e.toString())
       }
        
    }
    
  
    
    
     def getSpecimenList(feedbackInstance){
       try{
           def result=[]
           def caseRecord = feedbackInstance.caseRecord
         
         
              
             result= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s.fixative.code='XG'  order by s.specimenId", [caseRecord.id])
               
            
           return result
       
       }catch(Exception e){
              e.printStackTrace()
            
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
     def saveFeedback(params){
         try{
          
            
            def feedbackInstance = Feedback.get(params.id)
            def caseRecord = feedbackInstance.caseRecord
            feedbackInstance.properties = params
          
            feedbackInstance.save(failOnError:true)
            
          
            
              params.each(){key,value->
               
                 
                if(key.startsWith('is_fi_id')){
               
                    def feedbackIssue = FeedbackIssue.get(value)
                    feedbackIssue.issueDescription =params["${value}_issueDescription"]
                   
                    feedbackIssue.resolutionComments=params["${value}_resolutionComments"]
                    
                    feedbackIssue.save(failOnError:true)
            
                }
            }
            
            def new_fi_specimen_id = params.new_fi_specimen_id
            def new_fi_issue_description = params.new_fi_issue_description
            if(new_fi_specimen_id  || new_fi_issue_description ){
                def feedbackIssue = new FeedbackIssue()
                feedbackIssue.specimenRecord = SpecimenRecord.findBySpecimenId(new_fi_specimen_id)
                feedbackIssue.issueDescription = params.new_fi_issue_description
                feedbackIssue.resolutionComments = params.new_fi_issue_resolution_comments
                feedbackIssue.submissionCreated=feedbackInstance.currentSubmission
                feedbackIssue.caseRecord = caseRecord
                feedbackIssue.forFzn = false
                feedbackIssue.save(failOnError:true)
            }
            
            def deleteFi = params.delete_fi
            if(deleteFi){
                    def feedbackIssue = FeedbackIssue.get(deleteFi)
                    feedbackIssue.delete(failOnError:true)
            }
            
         
            
         }catch(Exception e){
               e.printStackTrace()
           
               throw new RuntimeException(e.toString())
         }
    }
    
    
    
    
     def submitFeedback(feedbackInstance, username, feedbackIssueList) {   
        try {
            def submission = feedbackInstance.currentSubmission
            submission.dateSubmitted = new Date()
            submission.submittedBy = username
            submission.save(failOnError:true)
            feedbackInstance.started=true
            feedbackInstance.status = "Submitted"
            feedbackInstance.save(failOnError:true)
           
            feedbackIssueList.each() {
                def feedbackIssueResolutionList = FeedbackIssueResolution.executeQuery("select fir from FeedbackIssueResolution fir inner join fir.feedbackIssue fi where  (fi.forFzn is null or fi.forFzn = false) and fi.id=? and fir.feedbackSubmission.id = (select max(fir2.feedbackSubmission.id) from FeedbackIssueResolution fir2 inner join fir2.feedbackIssue fi2 where  (fi2.forFzn is null or fi2.forFzn = false) and fi2.id=?)", [it.id, it.id])

                if (feedbackIssueResolutionList) {
                    def feedbackIssueResolution = feedbackIssueResolutionList.get(0)
                   if (feedbackIssueResolution.issueDescription != it.issueDescription || feedbackIssueResolution.resolutionComments != it.resolutionComments) {
                        def fir = new FeedbackIssueResolution()
                        fir.issueDescription = it.issueDescription
                        fir.resolutionComments = it.resolutionComments
                        fir.feedbackSubmission = submission
                        fir.feedbackIssue = it
                        fir.save(failOnError:true)
                    }
                } else {
                    def fir = new FeedbackIssueResolution()
                    fir.issueDescription = it.issueDescription
                    fir.resolutionComments = it.resolutionComments
                    fir.feedbackSubmission = submission
                    fir.feedbackIssue = it
                    fir.save(failOnError:true)
                }
            }
            
            def activityType = ActivityType.findByCode("PFFCOMP")
            def caseId = feedbackInstance.caseRecord.caseId
            def study = feedbackInstance.caseRecord.study
             def bssCode = feedbackInstance.caseRecord.bss?.parentBss?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
           
        } catch (Exception e) {
            e.printStackTrace()    
            throw new RuntimeException(e.toString())
        }
    }
    
    
    
     def qaReview(feedbackInstance, username) {
        try {
            feedbackInstance.reviewedBy = username
            feedbackInstance.reviewDate = new Date()
            def caseRecord = feedbackInstance.caseRecord
           
            feedbackInstance.save(failOnError:true)
           
            def activityType = ActivityType.findByCode("PROCFEEDAVAI")
            def caseId = feedbackInstance.caseRecord.caseId
            def study = feedbackInstance.caseRecord.study
            def bssCode = feedbackInstance.caseRecord.bss?.parentBss?.code

            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
            
         
        } catch(Exception e) {
            throw new RuntimeException(e.toString())
        }
    }
    
    
     def startNew(feedbackInstance){
        try{
             def caseRecord = feedbackInstance.caseRecord
            feedbackInstance.status = 'Editing'
            feedbackInstance.reviewedBy = null
            feedbackInstance.reviewDate = null
            def currentSub = feedbackInstance.currentSubmission
            def currentVersion = currentSub.feedbackVersion
            def nextSubmission= new FeedbackSubmission()
            nextSubmission.feedbackVersion= currentVersion +1
            nextSubmission.caseRecord = caseRecord
            nextSubmission.forFzn = false
            feedbackInstance.currentSubmission = nextSubmission
            feedbackInstance.save(failOnError:true)
            nextSubmission.save(failOnError:true)
        }catch(Exception e){
             
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
}
