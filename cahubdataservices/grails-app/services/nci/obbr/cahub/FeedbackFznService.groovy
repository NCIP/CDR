package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.prc.*;
import java.text.SimpleDateFormat
import java.text.DecimalFormat

class FeedbackFznService {

    def activityEventService
    
    static transactional = true

     def getFeedbackIssueList(feedbackFznInstance) { 
       try{
           def caseRecord = feedbackFznInstance.caseRecord
         
          def result = FeedbackIssue.executeQuery("select fi from FeedbackIssue fi where caseRecord.id=?  and  forFzn = true)", [caseRecord.id])
         
           return result
       
       }catch(Exception e){
              e.printStackTrace()
           
               throw new RuntimeException(e.toString())
       }
        

    }
    
    
    def  getFeedbackIssueResolutionDisplayList(feedbackFznInstance) { 
        def result=[]
        try{
           def caseRecord = feedbackFznInstance.caseRecord
         
           def resolution  = FeedbackIssueResolution.executeQuery("select fir from FeedbackIssueResolution  fir  inner join fir.feedbackIssue fi  where fi.caseRecord.id=?   and fi.forFzn = true order by fir.id", [caseRecord.id])
         
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
           def issue = FeedbackIssue.executeQuery("select fi from FeedbackIssue fi where caseRecord.id=?  and  forFzn = true", [caseRecord.id])
           issue.each(){
                
                  
                   def feedbackIssueResolutionList = FeedbackIssueResolution.executeQuery("select fir from FeedbackIssueResolution fir inner join fir.feedbackIssue fi where fi.forFzn = true and fi.id=? and fir.feedbackSubmission.id = (select max(fir2.feedbackSubmission.id) from FeedbackIssueResolution fir2 inner join fir2.feedbackIssue fi2 where fi2.id=?)", [it.id, it.id])
                  
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
    
    
    
    
    def getFeedbackSubList(feedbackFznInstance){
       try{
           def caseRecord = feedbackFznInstance.caseRecord
         
           def result = FeedbackSubmission.executeQuery("select frs from FeedbackSubmission frs inner join frs.caseRecord c where c.id=? and  frs.forFzn=true order by frs.id", [caseRecord.id])
         
           return result
       
       }catch(Exception e){
              e.printStackTrace()
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
     def getSpecimenList(feedbackFznInstance){
       try{
           def result=[]
           def caseRecord = feedbackFznInstance.caseRecord
         
         
              
             result= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s.fixative.code='DICE'  order by s.specimenId", [caseRecord.id])
               
            
           return result
       
       }catch(Exception e){
              e.printStackTrace()
            
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
     def saveFeedbackFzn(params){
         try{
          
            
            def feedbackFznInstance = FeedbackFzn.get(params.id)
            def caseRecord = feedbackFznInstance.caseRecord
            feedbackFznInstance.properties = params
            feedbackFznInstance.save(failOnError:true)
            
          
            
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
                feedbackIssue.submissionCreated=feedbackFznInstance.currentSubmission
                feedbackIssue.caseRecord = caseRecord
                feedbackIssue.forFzn = true
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
    
    
    
     def submitFeedbackFzn(feedbackFznInstance, username, feedbackIssueList) {   
        try {
            def submission = feedbackFznInstance.currentSubmission
            submission.dateSubmitted = new Date()
            submission.submittedBy = username
            submission.save(failOnError:true)
            feedbackFznInstance.started=true
            feedbackFznInstance.status = "Submitted"
            feedbackFznInstance.save(failOnError:true)
           
            feedbackIssueList.each() {
                def feedbackIssueResolutionList = FeedbackIssueResolution.executeQuery("select fir from FeedbackIssueResolution fir inner join fir.feedbackIssue fi where  fi.forFzn = true and fi.id=? and fir.feedbackSubmission.id = (select max(fir2.feedbackSubmission.id) from FeedbackIssueResolution fir2 inner join fir2.feedbackIssue fi2 where  (fi2.forFzn is null or fi2.forFzn = false) and fi2.id=?)", [it.id, it.id])

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
            
            def activityType = ActivityType.findByCode("PFFFZNCOMP")
            def caseId = feedbackFznInstance.caseRecord.caseId
            def study = feedbackFznInstance.caseRecord.study
             def bssCode = feedbackFznInstance.caseRecord.bss?.parentBss?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
           
        } catch (Exception e) {
            e.printStackTrace()    
            throw new RuntimeException(e.toString())
        }
    }
    
    
    
     def qaReview(feedbackFznInstance, username) {
        try {
            feedbackFznInstance.reviewedBy = username
            feedbackFznInstance.reviewDate = new Date()
            def caseRecord = feedbackFznInstance.caseRecord
           
            feedbackFznInstance.save(failOnError:true)
           
            def activityType = ActivityType.findByCode("PROCFEEDFZNAVAI")
            def caseId = feedbackFznInstance.caseRecord.caseId
            def study = feedbackFznInstance.caseRecord.study
            def bssCode = feedbackFznInstance.caseRecord.bss?.parentBss?.code

            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
            
         
        } catch(Exception e) {
            throw new RuntimeException(e.toString())
        }
    }
    
    
    
    def startNew(feedbackFznInstance){
        try{
             def caseRecord = feedbackFznInstance.caseRecord
            feedbackFznInstance.status = 'Editing'
            feedbackFznInstance.reviewedBy = null
            feedbackFznInstance.reviewDate = null
            def currentSub = feedbackFznInstance.currentSubmission
            def currentVersion = currentSub.feedbackVersion
            def nextSubmission= new FeedbackSubmission()
            nextSubmission.feedbackVersion= currentVersion +1
            nextSubmission.caseRecord = caseRecord
            nextSubmission.forFzn = true
            feedbackFznInstance.currentSubmission = nextSubmission
            feedbackFznInstance.save(failOnError:true)
            nextSubmission.save(failOnError:true)
        }catch(Exception e){
             
               throw new RuntimeException(e.toString())
       }
        
    }
    
}
