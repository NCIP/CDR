package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.prc.*;
import java.text.SimpleDateFormat;

class PrcReportFznService {
    def activityEventService
    static transactional = true

     def createReport(prcReportFznInstance) { 
       try{
          // println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG")
           def caseRecord = prcReportFznInstance.caseRecord
           def prcReportSubmission = new PrcReportSubmission()
            prcReportSubmission.reportVersion=1
            prcReportSubmission.caseRecord = caseRecord
            prcReportSubmission.forFzn =true
            prcReportFznInstance.currentSubmission = prcReportSubmission
            prcReportFznInstance.status='Editing'
            
           
            def speciments=[]
            speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=?  and s.fixative.code='DICE' and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
           
          
           
           speciments.each(){
               def prcSpecimen = new PrcSpecimen()
               prcSpecimen.specimenRecord = it
                    prcSpecimen.inventoryStatus=InventoryStatus.findByCode('ACCP')
                    prcSpecimen.comments='piece'
                    prcSpecimen.save(failOnError:true)
                    it.prcSpecimen = prcSpecimen
                    it.save(failOnError:true)
             }

            // }
            prcReportSubmission.save(failOnError:true)
            prcReportFznInstance.save(failOnError:true)
            //println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH")
        } catch(Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    
    
    
      def getPrcSpeciemenList4Edit(prcReportFznInstance) { 
       try{
           def result=[]
           def caseRecord = prcReportFznInstance.caseRecord
           
            
            result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=? and s.fixative.code='DICE'  order by s.specimenId", [caseRecord.id])
          // println("case record: " + caseRecord.caseId)
           //def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) order by s.specimenId", [caseRecord.id])
           
            
           //add new prcSpecimen in case vari loaded slide ship more than once
            def speciments= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s not in (select ps.specimenRecord from PrcSpecimen ps) and s.fixative.code='DICE'  order by s.specimenId", [caseRecord.id])
            
            speciments.each(){
                    def prcSpecimen = new PrcSpecimen()
                    prcSpecimen.specimenRecord = it
                    prcSpecimen.inventoryStatus=InventoryStatus.findByCode('ACCP')
                    prcSpecimen.save(failOnError:true)
                    result.add(prcSpecimen)
                    it.prcSpecimen = prcSpecimen
                    it.save(failOnError:true)
            }
                
          
          
           return result
       
       }catch(Exception e){
              e.printStackTrace()
           
               throw new RuntimeException(e.toString())
       }
        

    }
    
    
    
    
     def getPrcSpeciemenList4EditFull(prcReportFznInstance) { 
       try{
            def caseRecord = prcReportFznInstance.caseRecord
          def fznlist = getPrcSpeciemenList4Edit(prcReportFznInstance)
          def tissueList=[]
          fznlist.each(){
              tissueList.add(it.specimenRecord.tissueType)
          }
          def result = PrcSpecimen.executeQuery("select ps from PrcSpecimen ps inner join ps.specimenRecord s where s.caseRecord.id=:id and s.tissueType  in (:list)  order by s.tissueType.name, s.fixative.displayOrder", [id:caseRecord.id, list:tissueList])
          
          def  row='odd'
          def preTissue=''
          result.each(){
              def code = it.specimenRecord.tissueType.code
              if(code!= preTissue){
                  preTissue=code
                  if(row== 'odd')
                    row='even'
                  else
                    row='odd'
              }
             
              it.row=row
          }  
           return result
       
       }catch(Exception e){
              e.printStackTrace()
           
               throw new RuntimeException(e.toString())
       }
        

    }
    
    
    
     def getPrcIssueList(prcReportFznInstance) { 
       try{
           def caseRecord = prcReportFznInstance.caseRecord
         
         //  def result = PrcIssue.executeQuery("select pi from PrcIssue pi inner join pi.specimenRecord s where s.caseRecord.id=?", [caseRecord.id])
          def result = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and (forQc is null or forQc = false) and forFzn =true", [caseRecord.id])
         
           return result
       
       }catch(Exception e){
              e.printStackTrace()
           
               throw new RuntimeException(e.toString())
       }
        

    }
    
   
    def getPrcReportSubList(prcReportFznInstance){
       try{
           def caseRecord = prcReportFznInstance.caseRecord
         
           def result = PrcReportSubmission.executeQuery("select prs from PrcReportSubmission prs inner join prs.caseRecord c where c.id=? and prs.forFzn = true order by prs.id", [caseRecord.id])
         
           return result
       
       }catch(Exception e){
              e.printStackTrace()
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
   
    
    
     def getPrcIssueResolutionDisplayList(prcReportFznInstance) { 
        def result=[]
        try{
           def caseRecord = prcReportFznInstance.caseRecord
         
           def resolution  = PrcIssueResolution.executeQuery("select pir from PrcIssueResolution  pir  inner join pir.prcIssue pi  where pi.caseRecord.id=?  and (pi.forQc is null or pi.forQc = false) and pi.forFzn=true  order by pir.id", [caseRecord.id])
         
            resolution.each(){
              
               def map = [:]
               def tissue = it.prcIssue.specimenRecord?.tissueType?.name
               def specumenId = it.prcIssue.specimenRecord?.specimenId
               map.put("specimenId", specumenId )
               map.put("tissue", tissue)
         
               map.put("issueDescription", it.issueDescription)
               map.put("resolutionComments", it.resolutionComments)
               String pattern = "MM/dd/yyyy";
               SimpleDateFormat format = new SimpleDateFormat(pattern);
               def dateString = format.format(it.prcReportSubmission.dateSubmitted)

               map.put("dateSubmitted", dateString)
               result.add(map)
           }
           def issue = PrcIssue.executeQuery("select pi from PrcIssue pi where caseRecord.id=? and (forQc is null or forQc = false) and (forFzn=true)", [caseRecord.id])
           issue.each(){
                
                  
                   def prcIssueResolutionList = PrcIssueResolution.executeQuery("select pir from PrcIssueResolution pir inner join pir.prcIssue pi where (pi.forQc is null or pi.forQc = false) and pi.forFzn =true and pi.id=? and pir.prcReportSubmission.id = (select max(pir2.prcReportSubmission.id) from PrcIssueResolution pir2 inner join pir2.prcIssue pi2 where pi2.id=?)", [it.id, it.id])
                  
                    if(prcIssueResolutionList){
                        def prcIssueResolution = prcIssueResolutionList.get(0)
                    
                        if(prcIssueResolution.issueDescription != it.issueDescription || prcIssueResolution.resolutionComments != it.resolutionComments){
                          
                              def map = [:]
                              def tissue = it.specimenRecord?.tissueType?.name
                              map.put("tissue", tissue)
                              map.put("issueDescription", it.issueDescription)
                              map.put("resolutionComments", it.resolutionComments)
                       
                              result.add(map)
                        }
                    }else{
                          
                        
                           def map = [:]
                            def tissue = it.specimenRecord?.tissueType?.name
                              map.put("tissue", tissue)
                              map.put("issueDescription", it.issueDescription)
                              map.put("resolutionComments", it.resolutionComments)
                         
                              result.add(map)
                    }
                    
               
           }
           
           return result
       }catch(Exception e){
              e.printStackTrace()
             
               throw new RuntimeException(e.toString())
       }
        

    }
    
    
      def getSpecimenList(prcReportFznInstance){
       try{
           def result=[]
           def caseRecord = prcReportFznInstance.caseRecord
         
          
                 
                result= SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s inner join s.slides sl inner join sl.imageRecord i  where s.caseRecord.id=? and s.fixative.code='DICE'  order by s.specimenId", [caseRecord.id])
         
            
           return result
       
       }catch(Exception e){
              e.printStackTrace()
            
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
      
    
     def saveReport(params, request){
         try{
          
            
            def prcReportFznInstance = PrcReportFzn.get(params.id)
            def caseRecord = prcReportFznInstance.caseRecord
            prcReportFznInstance.properties = params
            
           /** def prcCaseStatusId =params.prcCaseStatusId
            if(prcCaseStatusId){
                def prcCaseStatus = PrcCaseStatus.findById(prcCaseStatusId)
                prcReportInstance.prcCaseStatus = prcCaseStatus
            }**/
            
          
            prcReportFznInstance.save(failOnError:true)
            
          
           
            params.each(){key,value->
         
                def ps_id
                if(key.startsWith('is_ps_id')){
                   
                    def prcSpecimen = PrcSpecimen.get(value)
                    prcSpecimen.autolysis=params["${value}_autolysis"]
                    prcSpecimen.comments=params["${value}_comments"]
                   // prcSpecimen.releaseToInventory=params["${value}_releaseToInventory"]
                  //  prcSpecimen.projectManagerFU=params["${value}_projectManagerFU"]
                    def inventoryStatusName =params["${value}_inventoryStatus"]
                   // println("???????inventoryStatusName: " + inventoryStatusName)
                    def inventoryStatus = InventoryStatus.findByName(inventoryStatusName)
                    prcSpecimen.inventoryStatus = inventoryStatus
                    
                    if(inventoryStatusName=='Unacceptable'){
                         def reasons = prcSpecimen.unaccReasons
                         def reasonMap=[:]
                         reasons.each(){
                            // println("it.reason.code: " + it.reason.code)
                            reasonMap.put(it.reason.id, it)
                                 
                         }
                         def list = PrcUnaccReason.findAll()
                         list.each(){
                             def id = it.id
                            // println("code??? " +code)
                             def unaccReason = reasonMap.get(id)
                             if(!unaccReason){
                                 unaccReason = new UnaccReasonSelection()
                             }
                             unaccReason.reason=it
                             unaccReason.prcSpecimen=prcSpecimen
                             if(params.get(prcSpecimen.id + "_unacc_reason_" + id ) == 'on'){
                                 unaccReason.selected = true
                             }else{
                                unaccReason.selected = false 
                             }
                            unaccReason.save(failOnError:true) 
                         }
                    }else{
                        def reasons = prcSpecimen.unaccReasons
                        reasons.each(){
                            it.selected = false
                            it.save(failOnError:true) 
                        }
                    }
                    
             
                    prcSpecimen.save(failOnError:true)
            
                }
            }
            
            
            
              params.each(){key,value->
               
                 
                if(key.startsWith('is_pi_id')){
               
                    def prcIssue = PrcIssue.get(value)
                    prcIssue.issueDescription =params["${value}_issueDescription"]
                    // SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy")
                   // def issueRequestDate = params["${value}_issueRequestDate_month"]+"/" + params["${value}_issueRequestDate_day"] +"/" + params["${value}_issueRequestDate_year"]
                   // prcIssue.issueRequestDate = df.parse(issueRequestDate)
                   // prcIssue.releaseToInventory=params["${value}_releaseToInventory2"]
                    prcIssue.pendingFurtherFollowUp=params["${value}_pendingFurtherFollowUp"]
                    prcIssue.resolved=params["${value}_resolved"]
                    prcIssue.resolutionComments=params["${value}_resolutionComments"]
                    
                    prcIssue.save(failOnError:true)
            
                }
            }
            
            def new_pi_specimen_id = params.new_pi_specimen_id
            def new_pi_issue_description = params.new_pi_issue_description
            if(new_pi_specimen_id  || new_pi_issue_description ){
                def prcIssue = new PrcIssue()
                prcIssue.specimenRecord = SpecimenRecord.findBySpecimenId(new_pi_specimen_id)
                
               
             
                
                prcIssue.issueDescription = params.new_pi_issue_description
               // prcIssue.issueRequestDate = df.parse(issueRequestDate)
        
        
               // prcIssue.releaseToInventory = params.new_pi_issue_release_to_inventory
                prcIssue.pendingFurtherFollowUp=params.new_pi_issue_pending_further_follow_up
                prcIssue.resolved =params.new_pi_issue_resolved
                prcIssue.resolutionComments = params.new_pi_issue_resolution_comments
                prcIssue.submissionCreated=prcReportFznInstance.currentSubmission
                prcIssue.caseRecord = caseRecord
                prcIssue.forQc = false
                prcIssue.forFzn = true
                prcIssue.save(failOnError:true)
            }
            
            def deletePi = params.delete_pi
            if(deletePi){
                    def prcIssue = PrcIssue.get(deletePi)
                    prcIssue.delete(failOnError:true)
            }
            
            
             params.each(){key,value->
               
               
                if(key.startsWith('is_prs_id')){
                    
                    def prcReportSubmission = PrcReportSubmission.get(value)
                    prcReportSubmission.pathologist = params["${value}_pathologist"]
                    
                    prcReportSubmission.save(failOnError:true)
            
                }
            }
            
            
            
         
            
         }catch(Exception e){
               e.printStackTrace()
           
               throw new RuntimeException(e.toString())
         }
    }
    
    
                                                
                                                
     def submitReport(prcReportFznInstance, username, prcIssueList) {   
        try {
            def submission = prcReportFznInstance.currentSubmission
            submission.dateSubmitted = new Date()
            submission.submittedBy = username
            submission.save(failOnError:true)
            prcReportFznInstance.status = "Submitted"
            prcReportFznInstance.save(failOnError:true)
           
            prcIssueList.each() {
                def prcIssueResolutionList = PrcIssueResolution.executeQuery("select pir from PrcIssueResolution pir inner join pir.prcIssue pi where (pi.forQc is null or pi.forQc = false)  and (pi.forFzn = true) and pi.id=? and pir.prcReportSubmission.id = (select max(pir2.prcReportSubmission.id) from PrcIssueResolution pir2 inner join pir2.prcIssue pi2 where (pi2.forQc is null or pi2.forQc = false)  and (pi2.forFzn= true) and pi2.id=?)", [it.id, it.id])

                if (prcIssueResolutionList) {
                    def prcIssueResolution = prcIssueResolutionList.get(0)
                    if (prcIssueResolution.issueDescription != it.issueDescription || prcIssueResolution.resolutionComments != it.resolutionComments) {
                        def pir = new PrcIssueResolution()
                        pir.issueDescription = it.issueDescription
                        pir.resolutionComments = it.resolutionComments
                        pir.prcReportSubmission = submission
                        pir.prcIssue = it
                        pir.save(failOnError:true)
                    }
                } else {
                    def pir = new PrcIssueResolution()
                    pir.issueDescription = it.issueDescription
                    pir.resolutionComments = it.resolutionComments
                    pir.prcReportSubmission = submission
                    pir.prcIssue = it
                    pir.save(failOnError:true)
                }
            }
            
            
             if( submission.reportVersion == 1){
                
                  def caseRecord = prcReportFznInstance.caseRecord
                  def feedbackFznInstance = new FeedbackFzn()
                  
                  def feedbackSubmission = new FeedbackSubmission()
                  feedbackSubmission.feedbackVersion=1
                  feedbackSubmission.caseRecord = caseRecord
                  feedbackSubmission.forFzn = true
                  feedbackFznInstance.currentSubmission = feedbackSubmission
                  feedbackFznInstance.status='Editing'
                  feedbackFznInstance.caseRecord = caseRecord
                   feedbackFznInstance.comments = prcReportFznInstance.comments
                  feedbackSubmission.save(failOnError:true)
                  feedbackFznInstance.save(failOnError:true)
                  prcIssueList.each() {
                      def feedbackIssue = new FeedbackIssue()
                      feedbackIssue.specimenRecord = it.specimenRecord
                      feedbackIssue.caseRecord = it.caseRecord
                      feedbackIssue.issueDescription=it.issueDescription
                      feedbackIssue.resolutionComments = it.resolutionComments
                      feedbackIssue.forFzn = true
                      feedbackIssue.submissionCreated=feedbackSubmission
                      feedbackIssue.save(failOnError:true)
                  }
                
            }
            
            def activityType = ActivityType.findByCode("PRCFZNCOMP")
            def caseId = prcReportFznInstance.caseRecord.caseId
            def study = prcReportFznInstance.caseRecord.study
            def bssCode = prcReportFznInstance.caseRecord.bss?.parentBss?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
        } catch (Exception e) {
            e.printStackTrace()    
            throw new RuntimeException(e.toString())
        }
    }
    
    
     def qaReview(prcReportInstance, username) {
        try {
            prcReportInstance.reviewedBy = username
            prcReportInstance.reviewDate = new Date()
            def caseRecord = prcReportInstance.caseRecord
         
            prcReportInstance.save(failOnError:true)

            def activityType = ActivityType.findByCode("PRCAVAILABLE")
            def caseId = prcReportInstance.caseRecord.caseId
            def study = prcReportInstance.caseRecord.study
            def bssCode = prcReportInstance.caseRecord.bss?.parentBss?.code

           
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
           
        } catch(Exception e) {
            throw new RuntimeException(e.toString())
        }
    }

    
                                                
                                                
    def startNew(prcReportInstance){
        try{
             def caseRecord = prcReportInstance.caseRecord
            prcReportInstance.status = 'Editing'
            prcReportInstance.reviewedBy = null
            prcReportInstance.reviewDate = null
            def currentSub = prcReportInstance.currentSubmission
            def currentVersion = currentSub.reportVersion
            def nextSubmission= new PrcReportSubmission()
            nextSubmission.reportVersion= currentVersion +1
            nextSubmission.caseRecord = caseRecord
            nextSubmission.forFzn = true
            prcReportInstance.currentSubmission = nextSubmission
            prcReportInstance.save(failOnError:true)
            nextSubmission.save(failOnError:true)
        }catch(Exception e){
             
               throw new RuntimeException(e.toString())
       }
        
    }                                             
                                                
}
