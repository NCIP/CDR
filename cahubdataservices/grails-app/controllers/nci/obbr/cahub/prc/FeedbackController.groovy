package nci.obbr.cahub.prc
import grails.plugins.springsecurity.Secured

class FeedbackController {

    def feedbackService
    def accessPrivilegeService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [feedbackInstanceList: Feedback.list(params), feedbackInstanceTotal: Feedback.count()]
    }

    def create = {
        def feedbackInstance = new Feedback()
        feedbackInstance.properties = params
        return [feedbackInstance: feedbackInstance]
    }

    def save = {
        def feedbackInstance = new Feedback(params)
        if (feedbackInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'feedback.label', default: 'Feedback'), feedbackInstance.id])}"
            redirect(action: "show", id: feedbackInstance.id)
        }
        else {
            render(view: "create", model: [feedbackInstance: feedbackInstance])
        }
    }

    def show = {
        def feedbackInstance = Feedback.get(params.id)
        if (!feedbackInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedback.label', default: 'Feedback'), params.id])}"
            redirect(action: "list")
        }
        else {
            [feedbackInstance: feedbackInstance]
        }
    }

    def edit = {
     
        def feedbackInstance  = Feedback.get(params.id)
        def feedbackIssueList
        def feedbackSubList
        def errorMap=[:]
        def canSub=false
        def feedbackIssueResolutionDisplayList
        def specimenList
   
        
        
         try{
            
             feedbackIssueList=feedbackService.getFeedbackIssueList(feedbackInstance)
             feedbackSubList = feedbackService.getFeedbackSubList(feedbackInstance)
             feedbackIssueResolutionDisplayList = feedbackService.getFeedbackIssueResolutionDisplayList(feedbackInstance)
             specimenList= feedbackService.getSpecimenList(feedbackInstance)
            
           
           
                  def result= checkError(feedbackInstance, feedbackIssueList)
                //  println("after check result...")
                  if(result && isStarted(feedbackInstance, feedbackIssueList) ){
                    result.each(){key,value->
                  
                      feedbackInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                  }else if (result && !isStarted(feedbackInstance, feedbackIssueList)){
                  }else{
                    
                      canSub=true
                  }
              
             
             return [feedbackInstance: feedbackInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList]
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
         
            return [feedbackInstance: feedbackInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList]
          
          }
        
        
      
        
    }

    
    
      def view = {
         def feedbackInstance  = Feedback.get(params.id)
        
          if (!accessPrivilegeService.checkAccessPrivilegePrc(feedbackInstance, session, 'view')) {
                redirect(controller: "login", action: "denied")
                return
            }
        
        def feedbackIssueList
        def feedbackSubList
        def feedbackIssueResolutionDisplayList
        
        
        feedbackIssueList=feedbackService.getFeedbackIssueList(feedbackInstance)
        feedbackSubList = feedbackService.getFeedbackSubList(feedbackInstance)
        feedbackIssueResolutionDisplayList = feedbackService.getFeedbackIssueResolutionDisplayList(feedbackInstance)
       
       
         return [feedbackInstance: feedbackInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList]
        
        
    }
    
    
    
    def update = {
       
        
        
         def feedbackInstance = Feedback.get(params.id)
        
        if (feedbackInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (feedbackInstance.version > version) {
                    
                    feedbackInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'feedback.label', default: 'Feedback')] as Object[], "Another user has updated this feedback while you were editing")
                     redirect(action:"edit", params:[id:feedbackInstance.id])
                    return
                }
            }
            
          
            feedbackService.saveFeedback(params)
           
             redirect(action:"edit", params:[id:feedbackInstance.id])
            
            
            
          
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedbak.label', default: 'Feedback'), params.id])}"
            redirect(action: "list")
        }
        
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def feedbackInstance = Feedback.get(params.id)
        if (feedbackInstance) {
            try {
                feedbackInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'feedback.label', default: 'Feedback'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'feedback.label', default: 'Feedback'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedback.label', default: 'Feedback'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    
    
    def submit = {
      
          def feedbackInstance  = Feedback.get(params.id)
          def feedbackIssueList
          def feedbackSubList
          def errorMap=[:]
          def canSub=false
          def feedbackIssueResolutionDisplayList
          def specimenList
        try{
            
            
             feedbackIssueList=feedbackService.getFeedbackIssueList(feedbackInstance)
             feedbackSubList = feedbackService.getFeedbackSubList(feedbackInstance)
             feedbackIssueResolutionDisplayList = feedbackService.getFeedbackIssueResolutionDisplayList(feedbackInstance)
             specimenList= feedbackService.getSpecimenList(feedbackInstance)
            
             
              def result= checkError(feedbackInstance, feedbackIssueList)
               if(result){
                    result.each(){key,value->
                    
                      feedbackInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                     flash.message="failed to submit"
                     
                
                     render(view: "edit", model: [feedbackInstance: feedbackInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList] )
            
                 
               }else{
                    //prcReportService.submitReport(prcReportInstance)
                   def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                
                   feedbackService.submitFeedback(feedbackInstance, username, feedbackIssueList)
                 
                   feedbackIssueResolutionDisplayList = feedbackService.getFeedbackIssueResolutionDisplayList(feedbackInstance)
                   
                   render(view: "view", model: [feedbackInstance: feedbackInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList] )
               }
              
             }catch(Exception e){
            flash.message="Failed: " + e.toString()  
           // redirect(action:"edit", params:[id:prcReportInstance.id])
        
           
               redirect(action:"edit", params:[id:feedbackInstance.id])
         
          }
        
    }
    
    
      def qareview={
          def feedbackInstance  = Feedback.get(params.id)
        
               
         
        try{
            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def submission = feedbackInstance.currentSubmission
            if(username == submission.submittedBy){
                flash.message="QA review cannot be performed by same user who submitted the report"
            }else{
                feedbackService.qaReview(feedbackInstance, username)
            }
            
           
            redirect(action:"view", params:[id:feedbackInstance.id])
         
            
           //redirect(action:"view", params:[id:prcReportInstance.id])
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
              redirect(action:"view", params:[id:feedbackInstance.id])
            
        }
        
    }
    
    
     def startnew ={
        
        def feedbackInstance = Feedback.get(params.id)
       
             
        try{
           
            feedbackService.startNew(feedbackInstance)
          
               redirect(action:"edit", params:[id:feedbackInstance.id])
           
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
            
              redirect(action:"view", params:[id:feedbackInstance.id])
          
        }
        
        
    }
    
    
    
     static boolean isStarted(feedbackInstance, feedbackIssueList){
        def result=false
        if(feedbackInstance.version > 0)
          return true;
          
       /** feedbackIssueList.each{
            if(it.version > 0)
              result = true
        }**/
       
         
        
           
        return result
        
    }
    static Map checkError(feedbackInstance, feedbackIssueList){
         
          def result = [:]
       
        feedbackIssueList.each(){
             def specimenId =it.specimenRecord?.specimenId
      
            def issueDescription = it.issueDescription
            if(!issueDescription){
                result.put("${it.id}_issueDescription".trim(), "The Issue Description for specimen ${specimenId} is a required field.")
            }  
    
        }
        
        return result
    }

    
}
