package nci.obbr.cahub.prc

import grails.plugins.springsecurity.Secured

class FeedbackFznController {
    def feedbackFznService
    def accessPrivilegeService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [feedbackFznInstanceList: FeedbackFzn.list(params), feedbackFznInstanceTotal: FeedbackFzn.count()]
    }

    def create = {
        def feedbackFznInstance = new FeedbackFzn()
        feedbackFznInstance.properties = params
        return [feedbackFznInstance: feedbackFznInstance]
    }

    def save = {
        def feedbackFznInstance = new FeedbackFzn(params)
        if (feedbackFznInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'feedbackFzn.label', default: 'FeedbackFzn'), feedbackFznInstance.id])}"
            redirect(action: "show", id: feedbackFznInstance.id)
        }
        else {
            render(view: "create", model: [feedbackFznInstance: feedbackFznInstance])
        }
    }

    def show = {
        def feedbackFznInstance = FeedbackFzn.get(params.id)
        if (!feedbackFznInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedbackFzn.label', default: 'FeedbackFzn'), params.id])}"
            redirect(action: "list")
        }
        else {
            [feedbackFznInstance: feedbackFznInstance]
        }
    }

    def edit = {
         def feedbackFznInstance  = FeedbackFzn.get(params.id)
        def feedbackIssueList
        def feedbackSubList
        def errorMap=[:]
        def canSub=false
        def feedbackIssueResolutionDisplayList
        def specimenList
   
        
        
         try{
            
             feedbackIssueList=feedbackFznService.getFeedbackIssueList(feedbackFznInstance)
             feedbackSubList = feedbackFznService.getFeedbackSubList(feedbackFznInstance)
             feedbackIssueResolutionDisplayList = feedbackFznService.getFeedbackIssueResolutionDisplayList(feedbackFznInstance)
             specimenList= feedbackFznService.getSpecimenList(feedbackFznInstance)
            
           
                def result= checkError(feedbackFznInstance, feedbackIssueList)
                //  println("after check result...")
                  if(result && isStarted(feedbackFznInstance, feedbackIssueList) ){
                    result.each(){key,value->
                  
                      feedbackFznInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                  }else if (result && !isStarted(feedbackFznInstance, feedbackIssueList)){
                  }else{
                    
                      canSub=true
                  }
            
          
             return [feedbackFznInstance: feedbackFznInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList]
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
         
            return [feedbackFznInstance: feedbackFznInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList]
          
          }
        
        
      
    }

    def update = {
     
        
        
         def feedbackFznInstance = FeedbackFzn.get(params.id)
        
        if (feedbackFznInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (feedbackFznInstance.version > version) {
                    
                    feedbackFznInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'feedbackFzn.label', default: 'FeedbackFzn')] as Object[], "Another user has updated this feedback Fzn while you were editing")
                     redirect(action:"edit", params:[id:feedbackFznInstance.id])
                    return
                }
            }
            
          
            feedbackFznService.saveFeedbackFzn(params)
           
             redirect(action:"edit", params:[id:feedbackFznInstance.id])
            
            
            
          
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedbakFzn.label', default: 'FeedbackFzn'), params.id])}"
            redirect(action: "list")
        }
        
   
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def feedbackFznInstance = FeedbackFzn.get(params.id)
        if (feedbackFznInstance) {
            try {
                feedbackFznInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'feedbackFzn.label', default: 'FeedbackFzn'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'feedbackFzn.label', default: 'FeedbackFzn'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedbackFzn.label', default: 'FeedbackFzn'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    
    def submit = {
      
          def feedbackFznInstance  = FeedbackFzn.get(params.id)
          def feedbackIssueList
          def feedbackSubList
          def errorMap=[:]
          def canSub=false
          def feedbackIssueResolutionDisplayList
          def specimenList
        try{
            
            
             feedbackIssueList=feedbackFznService.getFeedbackIssueList(feedbackFznInstance)
             feedbackSubList = feedbackFznService.getFeedbackSubList(feedbackFznInstance)
             feedbackIssueResolutionDisplayList = feedbackFznService.getFeedbackIssueResolutionDisplayList(feedbackFznInstance)
             specimenList= feedbackFznService.getSpecimenList(feedbackFznInstance)
            
             
              def result= checkError(feedbackFznInstance, feedbackIssueList)
               if(result){
                    result.each(){key,value->
                    
                      feedbackFznInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                     flash.message="failed to submit"
                     
                
                     render(view: "edit", model: [feedbackFznInstance: feedbackFznInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList] )
            
                 
               }else{
                    //prcReportService.submitReport(prcReportInstance)
                   def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                
                   feedbackFznService.submitFeedbackFzn(feedbackFznInstance, username, feedbackIssueList)
                 
                   feedbackIssueResolutionDisplayList = feedbackFznService.getFeedbackIssueResolutionDisplayList(feedbackFznInstance)
                   
                   render(view: "view", model: [feedbackFznInstance: feedbackFznInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList, errorMap:errorMap, canSub:canSub,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList, specimenList:specimenList] )
               }
              
             }catch(Exception e){
            flash.message="Failed: " + e.toString()  
           // redirect(action:"edit", params:[id:prcReportInstance.id])
        
           
               redirect(action:"edit", params:[id:feedbackFznInstance.id])
         
          }
        
    }
    
    
    
    
    def view = {
         def feedbackFznInstance  = FeedbackFzn.get(params.id)
        
          if (!accessPrivilegeService.checkAccessPrivilegePrc(feedbackFznInstance, session, 'view')) {
                redirect(controller: "login", action: "denied")
                return
            }
        
        def feedbackIssueList
        def feedbackSubList
        def feedbackIssueResolutionDisplayList
        
        
        feedbackIssueList=feedbackFznService.getFeedbackIssueList(feedbackFznInstance)
        feedbackSubList = feedbackFznService.getFeedbackSubList(feedbackFznInstance)
        feedbackIssueResolutionDisplayList = feedbackFznService.getFeedbackIssueResolutionDisplayList(feedbackFznInstance)
       
       
         return [feedbackFznInstance: feedbackFznInstance,  feedbackIssueList:feedbackIssueList, feedbackSubList:feedbackSubList,  feedbackIssueResolutionDisplayList:feedbackIssueResolutionDisplayList]
        
        
    }
    
    
    
    
     def qareview={
          def feedbackFznInstance  = FeedbackFzn.get(params.id)
        
               
         
        try{
            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def submission = feedbackFznInstance.currentSubmission
            if(username == submission.submittedBy){
                flash.message="QA review cannot be performed by same user who submitted the report"
            }else{
                feedbackFznService.qaReview(feedbackFznInstance, username)
            }
            
           
            redirect(action:"view", params:[id:feedbackFznInstance.id])
         
            
           //redirect(action:"view", params:[id:prcReportInstance.id])
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
              redirect(action:"view", params:[id:feedbackFznInstance.id])
            
        }
        
    }
    
    
    
     def startnew ={
        
        def feedbackFznInstance = FeedbackFzn.get(params.id)
       
             
        try{
           
            feedbackFznService.startNew(feedbackFznInstance)
          
               redirect(action:"edit", params:[id:feedbackFznInstance.id])
           
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
            
              redirect(action:"view", params:[id:feedbackFznInstance.id])
          
        }
        
        
    }
    
    
    static boolean isStarted(feedbackFznInstance, feedbackIssueList){
        def result=false
        if(feedbackFznInstance.version > 0)
          return true;
          
       /** feedbackIssueList.each{
            if(it.version > 0)
              result = true
        }**/
       
         
        
           
        return result
        
    }
    static Map checkError(feedbackFznInstance, feedbackIssueList){
         
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
