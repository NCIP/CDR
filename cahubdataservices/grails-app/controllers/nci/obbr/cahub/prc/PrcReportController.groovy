package nci.obbr.cahub.prc

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import grails.plugins.springsecurity.Secured
import grails.converters.*

class PrcReportController {
    def prcReportService
    def accessPrivilegeService
    def queryService
    
    static allowedMethods = [ update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [prcReportInstanceList: PrcReport.list(params), prcReportInstanceTotal: PrcReport.count()]
    }
    
    def caselist = {
        def studyCode = params.study
        def study = Study.findByCode(studyCode)
           
        def caseList=[]
        //def count = CaseRecord.findAllByStudy(Study.findByCode(studyCode)).size()
        def count
        if(studyCode != 'BPV')
        count = CaseRecord.countByStudyAndCaseStatusNotEqual(Study.findByCode(studyCode), CaseStatus.findByCode('WITHDR'))
         
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        //def all = CaseRecord.list(params)
        def offset=0
        if(params.offset)
        offset = params.offset
        def all
        if(studyCode != 'BPV'){
            all=CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode(studyCode), CaseStatus.findByCode('WITHDR'), params)
        }else{
            def count_arr = CaseRecord.executeQuery("select count(distinct c) from CaseRecord c where c.study.code='BPV' and c.caseStatus.code  != 'WITHDR' and c.hasLocalPathReview = 1 order by c.dateCreated desc" )
            count=count_arr.get(0)
            all = CaseRecord.executeQuery("from CaseRecord c where c.study.code='BPV' and c.caseStatus.code  != 'WITHDR' and c.hasLocalPathReview = 1 order by c.dateCreated desc",[max:params.max, offset:offset] )
        }
        all.each(){
            def map
            if(studyCode =='GTEX'){
                // map=prcReportService.getPrcCaseMap(it)
            }else if (studyCode =='BMS'){
                map=prcReportService.getPrcCaseMapBms(it)
            }else if(studyCode =='BRN'){
                map=prcReportService.getPrcCaseMapBrn(it)
            }else {
                // map=prcReportService.getPrcCaseMapBpv(it)
            }
            /*map.each(){key,value->
            println("key" + key + " value:" + value)
                  
            }*/
            caseList.add(map)
        }
        if(studyCode =='GTEX')
        caseList=prcReportService.getPrcCaseMaps(all)
        if (studyCode == 'GTEX')
        return [caseList:caseList, caseRecordInstanceTotal: count, studyCode:studyCode]
            
        if(studyCode =='BMS'){
            //println("return here????")
            return [caseListBms:caseList, caseRecordInstanceTotal: count, studyCode:studyCode]
        }
           
        if(studyCode =='BPV')
        caseList =prcReportService.getPrcCaseMapsBpv(all)
           
        if(studyCode =='BPV'){
            //println("return here????")
            return [caseListBpv:caseList, caseRecordInstanceTotal: count, studyCode:studyCode]
        }
           
        if(studyCode =='BRN'){
            //println("return here????")
            return [caseListBrn:caseList, caseRecordInstanceTotal: count, studyCode:studyCode]
        }
    }


    def create = {
        def prcReportInstance = new PrcReport()
        prcReportInstance.properties = params
        return [prcReportInstance: prcReportInstance]
    }

    def save = {
       
        def prcReportInstance = new PrcReport(params)
      
        try{
            
           
            prcReportService.createReport(prcReportInstance)
             
            
             
          
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseReportForm.label', default: 'PRC Report For'), prcReportInstance?.caseRecord?.caseId])}"
         
            redirect(action: "edit", id:prcReportInstance.id)
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
        
            redirect(action:"edit", params:[id:prcReportInstance.id])
         
        }
        
      
    }
    
    
    def saveBms = {
       
        def prcReportInstance = new PrcReport(params)
      
        try{
            
           
            prcReportService.createReport(prcReportInstance)
             
            
             
          
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseReportForm.label', default: 'PRC Report For'), prcReportInstance?.caseRecord?.caseId])}"
         
            redirect(action: "editBms", id:prcReportInstance.id)
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
        
            redirect(action:"editBms", params:[id:prcReportInstance.id])
         
        }
        
      
    }
     
    def show = {
        def prcReportInstance = PrcReport.get(params.id)
        if (!prcReportInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcReport.label', default: 'PrcReport'), params.id])}"
            redirect(action: "list")
        }
        else {
            [prcReportInstance: prcReportInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_PRC'])
    def submit = {
        def prcReportInstance = PrcReport.get(params.id)
        def prcSpecimenList
        def prcIssueList
        def prcReportSubList
        def specimenList
        def errorMap=[:]
        def canSub=false
        def serologyList
        // def prcIssueResolutionList
        def prcIssueResolutionDisplayList
        def prcAttachmentList
        def eligQ15Response =[]
        def reasons=[:]
        
        try{
            prcSpecimenList=prcReportService.getPrcSpeciemenList(prcReportInstance)
            prcIssueList=prcReportService.getPrcIssueList(prcReportInstance)
            prcReportSubList = prcReportService.getPrcReportSubList(prcReportInstance)
            specimenList= prcReportService.getSpecimenList(prcReportInstance)
            serologyList = prcReportService.getSerologyList(prcReportInstance)
            // prcIssueResolutionList = prcReportService.getPrcIssueResolutionList(prcReportInstance)
            prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
            prcAttachmentList = prcReportService.getPrcAttachmentList(prcReportInstance)
            eligQ15Response = prcReportService.getEligQ15Response(prcReportInstance)
            //flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseReportForm.label', default: 'PRC Report For'), prcReportInstance?.caseRecord?.caseId])}"
            //redirect(action: "show", id: prcReportInstance.id)
           
            prcSpecimenList.each(){
            def str=''
            if(it.inventoryStatus.name=='Unacceptable'){
              def list=it.unaccReasons
              list.each(){it2->
                  if(it2.selected){
                      str += ", " + it2.reason.name
                  }
                
              }
            }
            if(str){
                str = str.substring(2)
            }
            reasons.put(it.id, str)
        }
             
            def result= checkError(prcReportInstance, prcSpecimenList, prcIssueList)
            if(result){
                result.each(){key,value->
                    
                    prcReportInstance.errors.reject(value, value)
                    errorMap.put(key, "errors")
                }//each
                flash.message="failed to submit"
                     
                def studyCode = prcReportInstance.caseRecord.study.code
                if(studyCode =='GTEX'){                
                    render(view: "edit", model: [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList,eligQ15Response:eligQ15Response,reasons:reasons ] )
                }else
                render(view: "ediBms", model: [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList ] )
                
                
                   
                
                // render(view: "edit", model: [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList ] )
            }else{
                //prcReportService.submitReport(prcReportInstance)
                def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                
                prcReportService.submitReport(prcReportInstance, username, prcIssueList)
                 
                prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
                   
                def studyCode = prcReportInstance.caseRecord.study.code
                if(studyCode =='GTEX')
                render(view: "view", model: [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList,eligQ15Response:eligQ15Response, reasons:reasons ] )
                else
                render(view: "viewBms", model: [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList ] )
                
                //  render(view: "view", model: [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList ] )
            }
              
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
            // redirect(action:"edit", params:[id:prcReportInstance.id])        
            def studyCode = prcReportInstance.caseRecord.study.code
            if(studyCode =='GTEX')
            redirect(action:"edit", params:[id:prcReportInstance.id])
            else
            redirect(action:"editBms", params:[id:prcReportInstance.id])
            
        }
        
    }
    
    def qareview={
        def prcReportInstance = PrcReport.get(params.id)
        
        def studyCode = prcReportInstance.caseRecord.study.code
         
            
        
         
        try{
            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def submission = prcReportInstance.currentSubmission
            if(username == submission.submittedBy){
                flash.message="QA review cannot be performed by same user who submitted the report"
            }else{
                prcReportService.qaReview(prcReportInstance, username)
            }
            
            if(studyCode =='GTEX')
            redirect(action:"view", params:[id:prcReportInstance.id])
            else
            redirect(action:"viewBms", params:[id:prcReportInstance.id])
            
            
            //redirect(action:"view", params:[id:prcReportInstance.id])
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
            
            if(studyCode =='GTEX')
            redirect(action:"view", params:[id:prcReportInstance.id])
            else
            redirect(action:"viewBms", params:[id:prcReportInstance.id])
            
            // redirect(action:"view", params:[id:prcReportInstance.id])
        }
        
    }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_PRC'])
    def prcqareview = {
        //println("somebody call me....")
        def prcReportInstance = PrcReport.get(params.id)
        def studyCode = prcReportInstance.caseRecord.study.code
         
        try{
            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def submission = prcReportInstance.currentSubmission
            if(username == submission.submittedBy){
                flash.message="QA review cannot be performed by same user who submitted the report"
            }else{
                prcReportService.prcQaReview(prcReportInstance, username)
            }
            
            if(studyCode =='GTEX')
            redirect(action:"view", params:[id:prcReportInstance.id])
            else
            redirect(action:"viewBms", params:[id:prcReportInstance.id])
            
            //redirect(action:"view", params:[id:prcReportInstance.id])
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
            
            if(studyCode =='GTEX')
            redirect(action:"view", params:[id:prcReportInstance.id])
            else
            redirect(action:"viewBms", params:[id:prcReportInstance.id])
            
            
            // redirect(action:"view", params:[id:prcReportInstance.id])
        }
    }
    def startnew ={
        
        def prcReportInstance = PrcReport.get(params.id)
        def studyCode = prcReportInstance.caseRecord.study.code
             
        try{
           
            prcReportService.startNew(prcReportInstance)
          
            
            if(studyCode =='GTEX')
            redirect(action:"edit", params:[id:prcReportInstance.id])
            else
            redirect(action:"editBms", params:[id:prcReportInstance.id])
            // redirect(action:"edit", params:[id:prcReportInstance.id])
        }catch(Exception e){
            flash.message="Failed: " + e.toString() 
            if(studyCode =='GTEX')
            redirect(action:"view", params:[id:prcReportInstance.id])
            else
            redirect(action:"viewBms", params:[id:prcReportInstance.id])
        }
        
        
    }
    
    
    
    def edit = {
       // println("???????")
        def prcReportInstance = PrcReport.get(params.id)
        def prcSpecimenList
        def prcIssueList
        def prcReportSubList
        def specimenList
        def errorMap=[:]
        def canSub=false
        def serologyList
        def prcIssueResolutionDisplayList
        def prcAttachmentList
        def eligQ15Response =[]
        def unaccReasons = [:]
        
        try{
            prcSpecimenList=prcReportService.getPrcSpeciemenList4Edit(prcReportInstance)
            prcIssueList=prcReportService.getPrcIssueList(prcReportInstance)
            prcReportSubList = prcReportService.getPrcReportSubList(prcReportInstance)
            specimenList= prcReportService.getSpecimenList(prcReportInstance)
            serologyList = prcReportService.getSerologyList(prcReportInstance)
            prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
            prcAttachmentList = prcReportService.getPrcAttachmentList(prcReportInstance)
            eligQ15Response = prcReportService.getEligQ15Response(prcReportInstance)
             
             
            if(isStarted(prcReportInstance, prcSpecimenList, prcIssueList)){
                //println("after is started ")
                def result= checkError(prcReportInstance, prcSpecimenList, prcIssueList)
                //  println("after check result...")
                if(result){
                    result.each(){key,value->
                  
                        prcReportInstance.errors.reject(value, value)
                        errorMap.put(key, "errors")
                    }//each
                }else{
                    
                    canSub=true
                }
              
            }
            
           
            
            prcSpecimenList.each(){it2->
                def list = PrcUnaccReason.findAll()
                def displayList =[]
                def reasons = it2.unaccReasons
                def reasonMap=[:]
                reasons.each(){it5->
                    reasonMap.put(it5.reason.id, it5.selected)
                    
                }
                list.each(){ it3->
                    def contents = [:]
                    contents.put("id", it3.id)
                    contents.put("name", it3.name)
                    
                    boolean selected = false
                    if(reasonMap.get(it3.id)){
                        
                                selected = true
                            
                     }
                        
               
                   contents.put("selected", selected)
                   displayList.add(contents)
                   
                }
               // println("it2: " + it2)
                unaccReasons.put(it2.id, displayList)
            }
        
            
           // println("size: " + unaccReasons.size())
            
            return [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, specimenList:specimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList,eligQ15Response:eligQ15Response, unaccReasons:unaccReasons ]
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
         
        
            return [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, specimenList:specimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList,eligQ15Response:eligQ15Response, unaccReasons:unaccReasons ]
        }
        
        
      
    }
    
    
    
    def editBms = {
     
        def prcReportInstance = PrcReport.get(params.id)
        def prcSpecimenMap
        def prcIssueList
        def prcReportSubList
        def specimenList
        def errorMap=[:]
        def canSub=false
        def serologyList
        def prcIssueResolutionDisplayList
        def prcAttachmentList
        def prcSpecimenList=[]
        
        try{
            prcSpecimenMap=prcReportService.getPrcSpeciemenMapBms(prcReportInstance)
            
            prcSpecimenMap.each(){key, value->
                prcSpecimenList.addAll(value)
            }
            //println("prcSpecimenList size: " + prcSpecimenList.size())
            prcIssueList=prcReportService.getPrcIssueList(prcReportInstance)
            prcReportSubList = prcReportService.getPrcReportSubList(prcReportInstance)
            //specimenList= prcReportService.getSpecimenList26(prcReportInstance)
            specimenList= prcReportService.getSpecimenList(prcReportInstance)
            serologyList = prcReportService.getSerologyList(prcReportInstance)
            //  println("before prcIssueResolutionDisplayList ")
            prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
            //  println("after prcIssueResolutionDisplayList ")
            prcAttachmentList = prcReportService.getPrcAttachmentList(prcReportInstance)
            // println("after att ")
            //flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseReportForm.label', default: 'PRC Report For'), prcReportInstance?.caseRecord?.caseId])}"
            //redirect(action: "show", id: prcReportInstance.id)
            if(isStarted(prcReportInstance, prcSpecimenList, prcIssueList)){
                //println("after is started ")
                def result= checkError(prcReportInstance, prcSpecimenList, prcIssueList)
                //  println("after check result...")
                if(result){
                    result.each(){key,value->
                  
                        prcReportInstance.errors.reject(value, value)
                        errorMap.put(key, "errors")
                    }//each
                }else{
                    
                    canSub=true
                }
              
            }
            return [prcReportInstance: prcReportInstance, prcSpecimenMap:prcSpecimenMap, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList ]
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
         
        
            return [prcReportInstance: prcReportInstance, prcSpecimenMap:prcSpecimenMap, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, errorMap:errorMap, canSub:canSub, serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList ]
        }
        
        
      
    }
    
    static boolean isStarted(prcReportInstance, prcSpecimenList, prcIssueList){
        def result=false
        if(prcReportInstance.version > 0)
        return true;
          
        prcSpecimenList.each() {
            if(it.version > 0){
                result=true
            }
               
        }
       
         
        if(prcIssueList)
        return true;
           
        return result
        
    }
    static Map checkError(prcReportInstance, prcSpecimenList, prcIssueList){
        def studyCode = prcReportInstance.caseRecord.study.code
        def result = [:]
        def releaseMap=[:]
        def stainingOfSlides = prcReportInstance.stainingOfSlides
        if(!stainingOfSlides)
        result.put("stainingOfSlides", "The Overall Staining of Slides is a required field.")
        
        // def stainingOfImages = prcReportInstance.stainingOfImages
        // if(!stainingOfImages){
        //     result.put("stainingOfImages", "The Overall Staining of Images is a required field.")
        //}
        
        def processing = prcReportInstance.processing
        if(!processing){
            result.put("processing", "The Overall Processing/Embedding is a required field.")
        }
        prcSpecimenList.each() {
            def specimenId =it.specimenRecord.specimenId
            def autolysis = it.autolysis
            def fixativeCode= it.specimenRecord.fixative.code
            if(studyCode=='GTEX' || (studyCode=='BMS' && fixativeCode=='XG')){
                if(!autolysis){
                    result.put("${it.id}_autolysis".trim(), "The Autolysis for specimen ${specimenId} is a required field.")
                }
            }
             
            // def releaseToInventory=it.releaseToInventory
            //  if(!releaseToInventory){
            //   result.put("${it.id}_releaseToInventory".trim(), "The Rlease To Inventory for specimen ${specimenId} is a required field.")
            // }
           
            def inventoryStatus=it.inventoryStatus
            if(inventoryStatus?.name=='Unacceptable'){
                def reasons = it.unaccReasons
                boolean selected = false
                reasons.each(){it2->
                    if(it2.selected){
                        selected = true
                    }
                    
                }
                if(!selected)
                  result.put("${it.id}_inventoryStatus".trim(), "Please specify at least one unacceptable reason for specimen ${specimenId}.")
            }
            
            // if(releaseToInventory){
            //  releaseMap.put(specimenId, releaseToInventory)
            //  }
            
            //  def projectManagerFU=it.projectManagerFU
            // if(!projectManagerFU){
            //   result.put("${it.id}_projectManagerFU".trim(), "The Project Manager F/U for specimen ${specimenId} is a required field.")
            //}
            
            def comments=it.comments
            if(!comments){
                result.put("${it.id}_comments".trim(), "The comments for specimen ${specimenId} is a required field.")
            }
            
        }
        
        prcIssueList.each(){
            def specimenId =it.specimenRecord?.specimenId
            // def releaseToInventory = it.releaseToInventory
            //  if(!releaseToInventory){
            // result.put("${it.id}_releaseToInventory2".trim(), "The Rlease To Inventory for specimen ${specimenId} is a required field.")
            //   }
            def resolved = it.resolved
            if(!resolved){
                result.put("${it.id}_resolved".trim(), "The Resolved for specimen ${specimenId} is a required field.")
            }
             
            if(resolved == 'Y'){
                def resolutionComments = it.resolutionComments
                if(!resolutionComments){
                    result.put("${it.id}_resolutionComments".trim(), "Please specify Resolution Comments for specimen ${specimenId}.")
                }
                 
            }
             
            //  if(releaseToInventory){
            //    def releaseFromMap = releaseMap.get(specimenId)
            //   if(releaseFromMap){
            //     if(releaseToInventory != releaseFromMap ){
            // result.put("${it.id}_releaseToInventory2".trim(), "Release To Inventory value for specimen ${specimenId} is different in specimen table than in issue table.")
            // }
            //}
            // }
            
            def issueDescription = it.issueDescription
            if(!issueDescription){
                result.put("${it.id}_issueDescription".trim(), "The Issue Description for specimen ${specimenId} is a required field.")
            }  
            
            def pendingFurtherFollowUp=it.pendingFurtherFollowUp
            if(!pendingFurtherFollowUp){
                result.put("${it.id}_pendingFurtherFollowUp".trim(), "The Pending Further Follow Up for specimen ${specimenId} is a required field.")
            }

        }
        
        return result
    }

    
    
    static Map checkError4Qc(prcReportInstance, prcIssueList4Qc){
        def studyCode = prcReportInstance.caseRecord.study.code
        def result = [:]
        def releaseMap=[:]
        def hasQcIssue = prcReportInstance.hasQcIssue
        if(!hasQcIssue)
        result.put("hasQcIssue", "Were there any QC issues is a required field.")
        
       
        if(hasQcIssue == 'Yes' && !prcIssueList4Qc){
            result.put("noIssue", "Please specify at least one issue.")
            
        }
        
        prcIssueList4Qc.each(){
            def specimenId =it.specimenRecord?.specimenId
            // def releaseToInventory = it.releaseToInventory
            //  if(!releaseToInventory){
            // result.put("${it.id}_releaseToInventory2".trim(), "The Rlease To Inventory for specimen ${specimenId} is a required field.")
            //   }
            def resolved = it.resolved
            if(!resolved){
                result.put("${it.id}_resolved".trim(), "The Resolved for specimen ${specimenId} is a required field.")
            }
             
            if(resolved == 'Y'){
                def resolutionComments = it.resolutionComments
                if(!resolutionComments){
                    result.put("${it.id}_resolutionComments".trim(), "Please specify Resolution Comments for specimen ${specimenId}.")
                }
                 
            }
             
            //  if(releaseToInventory){
            //    def releaseFromMap = releaseMap.get(specimenId)
            //   if(releaseFromMap){
            //     if(releaseToInventory != releaseFromMap ){
            // result.put("${it.id}_releaseToInventory2".trim(), "Release To Inventory value for specimen ${specimenId} is different in specimen table than in issue table.")
            // }
            //}
            // }
            
            def issueDescription = it.issueDescription
            if(!issueDescription){
                result.put("${it.id}_issueDescription".trim(), "The Issue Description for specimen ${specimenId} is a required field.")
            }  
            
            def pendingFurtherFollowUp=it.pendingFurtherFollowUp
            if(!pendingFurtherFollowUp){
                result.put("${it.id}_pendingFurtherFollowUp".trim(), "The Pending Further Follow Up for specimen ${specimenId} is a required field.")
            }

        }
        
        return result
    }

    
    
    def update = {
        
        def prcReportInstance = PrcReport.get(params.id)
        if (prcReportInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prcReportInstance.version > version) {
                    
                    prcReportInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prcReport.label', default: 'PrcReport')] as Object[], "Another user has updated this PrcReport while you were editing")
                    render(view: "edit", model: [prcReportInstance: prcReportInstance])
                    return
                }
            }
            
          
            prcReportService.saveReport(params, request)
            def filepath_remove = params.filepath_remove
            // println("filepath to remove: " + filepath_remove )
          
            if(filepath_remove){
              
                File file = new File(filepath_remove)
                def ok =file.delete()
                /*  if(ok){
                println("remove file ok")
                }else{
                println("remove file not ok")
                }*/
            }
            
            def studyCode = prcReportInstance.caseRecord.study.code
            if(studyCode =='GTEX')
            redirect(action:"edit", params:[id:prcReportInstance.id])
            else
            redirect(action:"editBms", params:[id:prcReportInstance.id])
            
          
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcReport.label', default: 'PrcReport'), params.id])}"
            redirect(action: "list")
        }
    }

    
    def updateQc = {
        def prcReportInstance = PrcReport.get(params.id)
        if (prcReportInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prcReportInstance.version > version) {
                    
                    prcReportInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prcReport.label', default: 'PrcReport')] as Object[], "Another user has updated this PrcReport while you were editing")
                    render(view: "edit", model: [prcReportInstance: prcReportInstance])
                    return
                }
            }
            
          
            prcReportService.saveReport4Qc(params, request)
            
            redirect(action:"qc", params:[id:prcReportInstance.id])
            
          
          
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcReport.label', default: 'PrcReport'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    def completeQc = {
        def prcReportInstance = PrcReport.get(params.id)
       
        
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
           
        def submission = prcReportInstance.currentSubmission
        if(username == submission.submittedBy){
            flash.message="QC review can't be performed by same user who submitted the report"
        }else{
            prcReportService.completeQc(prcReportInstance, username)
        }
           
            
        redirect(action:"view", params:[id:prcReportInstance.id])
            
          
     
        
    }
    

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN', 'ROLE_NCI-FREDERICK_CAHUB_PRC'])     
    def delete = {
        def prcReportInstance = PrcReport.get(params.id)
        if (prcReportInstance) {
            try {
                prcReportInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'prcReport.label', default: 'PrcReport'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'prcReport.label', default: 'PrcReport'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcReport.label', default: 'PrcReport'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    /* def download = {
       
    def prcSpecimen = PrcSpecimen.get(params.psid)
    
        
    if (!prcSpecimen) {
    flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), params.id])}"
    redirect(action: "list")
    }
    else {            
            
    def convertedFilePath = prcSpecimen.filePath
    def file = new File(convertedFilePath)
    response.setContentType("application/octet-stream")
    response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
    response.outputStream << file.newInputStream() // Performing a binary stream copy
    }
    }*/
    
    
    def download = {
       
        def prcAttachment = PrcAttachment.get(params.paid)
    
        
        if (!prcAttachment) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), params.id])}"
            redirect(action: "list")
        }
        else {            
            
            def convertedFilePath = prcAttachment.filePath
            def file = new File(convertedFilePath)
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
            response.outputStream << file.newInputStream() // Performing a binary stream copy
        }
    }
    
    
    
    def view = {
       
        def prcReportInstance = PrcReport.get(params.id)
        
        if (!accessPrivilegeService.checkAccessPrivilegePrc(prcReportInstance, session, 'view')) {
            redirect(controller: "login", action: "denied")
            return
        }
        
        def prcSpecimenList
        def prcIssueList
        def prcReportSubList
        def specimenList
       
        def serologyList
        def prcIssueResolutionDisplayList
        def prcAttachmentList
        def prcIssueList4Qc
        def eligQ15Response=[]
        def reasons=[:]
        
        // try{
        prcSpecimenList=prcReportService.getPrcSpeciemenList(prcReportInstance)
        prcIssueList=prcReportService.getPrcIssueList(prcReportInstance)
        prcReportSubList = prcReportService.getPrcReportSubList(prcReportInstance)
        specimenList= prcReportService.getSpecimenList(prcReportInstance)
        serologyList = prcReportService.getSerologyList(prcReportInstance)
        prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
        prcAttachmentList = prcReportService.getPrcAttachmentList(prcReportInstance)
        prcIssueList4Qc=prcReportService.getPrcIssueList4Qc(prcReportInstance)
        eligQ15Response = prcReportService.getEligQ15Response(prcReportInstance)
        
        prcSpecimenList.each(){
            def str=''
            if(it.inventoryStatus.name=='Unacceptable'){
              def list=it.unaccReasons
              list.each(){it2->
                  if(it2.selected){
                      str += ", " + it2.reason.name
                  }
                
              }
            }
            if(str){
                str = str.substring(2)
            }
            reasons.put(it.id, str)
        }
        
        return [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList,  serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList, prcIssueList4Qc:prcIssueList4Qc,eligQ15Response:eligQ15Response, reasons:reasons ]
        //  }catch(Exception e){
        //  flash.message="Failed: " + e.toString()  
           
        //   render("failed")
        //  }
        
        
        
    }
    
    
    
    def viewBms = {
       
        def prcReportInstance = PrcReport.get(params.id)
        
        if (!accessPrivilegeService.checkAccessPrivilegePrc(prcReportInstance, session, 'view')) {
            redirect(controller: "login", action: "denied")
            return
        }
        
        def prcSpecimenList
        def prcIssueList
        def prcReportSubList
        def specimenList
       
      
        def prcIssueResolutionDisplayList
        def prcAttachmentList
        
        // try{
        prcSpecimenList=prcReportService.getPrcSpeciemenListBms(prcReportInstance, params.orderby)
        prcIssueList=prcReportService.getPrcIssueList(prcReportInstance)
        prcReportSubList = prcReportService.getPrcReportSubList(prcReportInstance)
        specimenList= prcReportService.getSpecimenList(prcReportInstance)
        prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
        prcAttachmentList = prcReportService.getPrcAttachmentList(prcReportInstance)
        def orderby=''
        if(!params.orderby)
        orderby='tissue,delay hour,fixative'
        else
        orderby = params.orderby
                   
        return [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList, orderby:orderby ]
        //  }catch(Exception e){
        //  flash.message="Failed: " + e.toString()  
           
        //   render("failed")
        //  }
        
        
        
    }
    
    
    def qc = {
       
        def prcReportInstance = PrcReport.get(params.id)
        
        if (!accessPrivilegeService.checkAccessPrivilegePrc(prcReportInstance, session, 'view')) {
            redirect(controller: "login", action: "denied")
            return
        }
        
        def prcSpecimenList
        def prcIssueList
        def prcReportSubList
        def specimenList
       
        def serologyList
        def prcIssueResolutionDisplayList
        def prcAttachmentList
          
        def prcIssueList4Qc
        
        def errorMap=[:]
        def canSub=false
        def reasons=[:]
        // try{
        prcSpecimenList=prcReportService.getPrcSpeciemenList(prcReportInstance)
        prcIssueList=prcReportService.getPrcIssueList(prcReportInstance)
        prcReportSubList = prcReportService.getPrcReportSubList(prcReportInstance)
        specimenList= prcReportService.getSpecimenList(prcReportInstance)
        serologyList = prcReportService.getSerologyList(prcReportInstance)
        prcIssueResolutionDisplayList = prcReportService.getPrcIssueResolutionDisplayList(prcReportInstance)
        prcAttachmentList = prcReportService.getPrcAttachmentList(prcReportInstance)
        prcIssueList4Qc=prcReportService.getPrcIssueList4Qc(prcReportInstance)
        
        prcSpecimenList.each(){
            def str=''
            if(it.inventoryStatus.name=='Unacceptable'){
              def list=it.unaccReasons
              list.each(){it2->
                  if(it2.selected){
                      str += ", " + it2.reason.name
                  }
                
              }
            }
            if(str){
                str = str.substring(2)
            }
            reasons.put(it.id, str)
        }
               
        
        if(prcReportInstance.status == 'QCEditing'){
            //println("after is started ")
            def result= checkError4Qc(prcReportInstance, prcIssueList4Qc)
            //  println("after check result...")
            if(result){
                result.each(){key,value->
                  
                    prcReportInstance.errors.reject(value, value)
                    errorMap.put(key, "errors")
                }//each
            }else{
                    
                canSub=true
            }
              
        }
        
        prcReportInstance.status = 'QCEditing'
        return [prcReportInstance: prcReportInstance, prcSpecimenList:prcSpecimenList, prcIssueList:prcIssueList, prcReportSubList:prcReportSubList, specimenList:specimenList,  serologyList:serologyList, prcIssueResolutionDisplayList:prcIssueResolutionDisplayList, prcAttachmentList:prcAttachmentList, prcIssueList4Qc:prcIssueList4Qc, errorMap:errorMap, canSub:canSub, reasons:reasons ]
        //  }catch(Exception e){
        //  flash.message="Failed: " + e.toString()  
           
        //   render("failed")
        //  }
        
        
        
    }
    
    
    
    
    def sign_off={
        
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        prcReportService.signOff(params, username)
         
        render("signoff")
    }
    
    def getSeqNum ={
        def caseRecord = CaseRecord.findByCaseId('GTEX-000285')
        def number = prcReportService.getCaseSeqNumber(caseRecord)
        render ("seq num: " + number)
    }
    
    def matchSeqNum ={
        def caseRecord = CaseRecord.findByCaseId('GTEX-000062')
        def randomlyPicked = prcReportService.randomlyPicked(caseRecord)
        if(randomlyPicked){
            render (caseRecord.caseId + " match")
        }else{
            render(caseRecord.caseId + " not match")            
        }
    }
    
    def makeReq={
        
        def result=[:]
        def div_id = params.case_id
        
        //println("in test: " + params.case_id)
        def caseRecord = CaseRecord.findById(params.case_id)
        def org = Organization.findByCode('VARI')
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        def queryType = QueryType.findByCode('ACTION')
        def desc = 'Create slides for frozen tissues.'
        def today = new Date()
        def dueDate = today.plus(14)
        queryService.createFznRequest(org, caseRecord, queryType, desc, dueDate, username)
        result.put("div_id", div_id)
        render result as JSON
    }
}
