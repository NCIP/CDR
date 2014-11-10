package nci.obbr.cahub.prctumor

import nci.obbr.cahub.datarecords.*
import grails.plugins.springsecurity.Secured

class PrcFormController {
    def prcTumorFormService
    static allowedMethods = [ update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [prcFormInstanceList: PrcForm.list(params), prcFormInstanceTotal: PrcForm.count()]
    }

    def create = {
        def prcFormInstance = new PrcForm()
        prcFormInstance.properties = params
        return [prcFormInstance: prcFormInstance]
    }

    def save = {
        def prcFormInstance = new PrcForm(params)
        
         try{
            
           
             prcTumorFormService.createForm(prcFormInstance)
             
             flash.message = "${message(code: 'default.created.message', args: [message(code: 'PrcTumorForm.label', default: 'PRC Tumor Form For Slide'), prcFormInstance?.slideRecord?.slideId])}"
         
             redirect(action: "edit", id:prcFormInstance.id)
        }catch(Exception e){
            flash.message="Failed: " + e.toString()  
        
          redirect(controller:"caseRecord", action:"show", params:[id:prcFormInstance.caseRecord.id])
         
          }
        
    
    }
    
    
    
    def uploadF= {
        def caseRecordInstance = CaseRecord.get(params.id)
        render(view: "upload", model:[caseRecordInstance:caseRecordInstance, cat:'F'])
    }
    
     def uploadL= {
        def slideRecordInstance = SlideRecord.get(params.id)
        render(view: "upload", model:[slideRecordInstance:slideRecordInstance, cat:'L'])
    }
    
   def upload_save={
       def cat = params.cat
       def id
       if(cat == 'F'){
           id = params.id
       }
       if(cat == 'L'){
        def slideRecordInstance = SlideRecord.get(params.id)
        id = slideRecordInstance.specimenRecord.caseRecord.id
       }
        
           
        
           prcTumorFormService.upload(params, request)
           redirect(controller:'caseRecord', action:"show", params:[id:id])
   
   }
   
    
     def downloadF = {
       
        def caseRecord = CaseRecord.get(params.id)
    
        def convertedFilePath = caseRecord.finalSurgicalPath
        def file = new File(convertedFilePath)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
        response.outputStream << file.newInputStream() // Performing a binary stream copy
       
    }
    
    
     def downloadL = {
       
        def slideRecordInstance = SlideRecord.get(params.id)
    
        def convertedFilePath = slideRecordInstance.localPathologyReview
        def file = new File(convertedFilePath)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
        response.outputStream << file.newInputStream() // Performing a binary stream copy
       
    }
    
      def removeF = {
      // println("call remove F")
        def caseRecord = CaseRecord.get(params.id)
    
        def convertedFilePath = caseRecord.finalSurgicalPath
        def file = new File(convertedFilePath)
        caseRecord.finalSurgicalPath=null
        caseRecord.save(failOnError:true)
         
        def ok= file.delete()
        
        /*if(ok)
           println("remove ok")
        else
           println("remove not ok")*/
        redirect(controller:'caseRecord', action:"show", params:[id:caseRecord.id])
       
    }
    
    
      def removeL = {
      // println("call remove L")
        def slideRecordInstance = SlideRecord.get(params.id)
    
        def convertedFilePath = slideRecordInstance.localPathologyReview
        def file = new File(convertedFilePath)
        slideRecordInstance.localPathologyReview=null
        slideRecordInstance.save(failOnError:true)
         
        file.delete()
        redirect(controller:'caseRecord', action:"show", params:[id:slideRecordInstance.specimenRecord.caseRecord.id])
       
    }
    
    
    def show = {
        def prcFormInstance = PrcForm.get(params.id)
        if (!prcFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), params.id])}"
            redirect(action: "list")
        }
        else {
            [prcFormInstance: prcFormInstance]
        }
    }

    def edit = {
        def prcFormInstance = PrcForm.get(params.id)
        def hisList=prcTumorFormService.getHisList(prcFormInstance)
        def errorMap=[:]
        def canReview = false
        if (!prcFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), params.id])}"
            redirect(action: "list")
        }
        else {
            if(isStarted(prcFormInstance)){
                 def result= checkError(prcFormInstance)
                  if(result){
                     // println("result is not null")
                    result.each(){key,value->
                   //   println("in edit method, key: " + key + " value: " + value)
                      prcFormInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                  }else{
                    //  println("result is null")
                      canReview=true
                  }
            }
         
            return [prcFormInstance: prcFormInstance, hisList:hisList, errorMap:errorMap, canReview:canReview]
        }
    }

    def update = {
        def prcFormInstance = PrcForm.get(params.id)
        if (prcFormInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prcFormInstance.version > version) {
                    
                    prcFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prcForm.label', default: 'PrcForm')] as Object[], "Another user has updated this PrcForm while you were editing")
                    render(view: "edit", model: [prcFormInstance: prcFormInstance])
                    return
                }
            }
            
            prcTumorFormService.saveForm(params)
           //  params.each(){key,value->
               //  println("key: " + key + " value: " + value)
           //  }
            
            
           /* prcFormInstance.properties = params
            if (!prcFormInstance.hasErrors() && prcFormInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), prcFormInstance.id])}"
                redirect(action: "show", id: prcFormInstance.id)
            }
            else {
                render(view: "edit", model: [prcFormInstance: prcFormInstance])
            }*/
            
           //render(view: "edit", model: [prcFormInstance: prcFormInstance])
            redirect(action:"edit", params:[id:prcFormInstance.id])
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), params.id])}"
           // redirect(action: "list")
           //render(view: "edit", model: [prcFormInstance: prcFormInstance])
           redirect(action:"edit", params:[id:prcFormInstance.id])
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def prcFormInstance = PrcForm.get(params.id)
        if (prcFormInstance) {
            try {
                prcFormInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prcForm.label', default: 'PrcForm'), params.id])}"
            redirect(action: "list")
        }
    }
    
                                        
                                        
     static Map checkError(prcFormInstance){
          def result = [:]
          def detailsList = []
          detailsList.add('C7')
          detailsList.add('C8')
          detailsList.add('C9')
          detailsList.add('C20')
          //detailsList.add('C21')
          detailsList.add('OTHER')
          
        if(!prcFormInstance?.organOrigin){
            result.put("organOrigin", "The Organ of origin is a required field.")
        }
         
         if(prcFormInstance?.organOrigin =='Other' && !prcFormInstance.otherOrganOrigin){
           result.put("otherOrganOrigin", "The Other organ of origin is a required field.")
          
         }
           
     /**   def hasHis=false
         def hisList = prcFormInstance.prcHistologicTypes
          hisList.each(){
             if(detailsList.contains(it.histologicType.code) && it.included && !it.details ){
                result.put("detail_" + it.id, "Specify histologic type details for " + it.histologicType.name) 
             }
            if(it.included){
                hasHis = true
            }       
          }  
          
         if(!hasHis){
             result.put("needHis", "Please select histologic type.")
         }**/
         
        if(!prcFormInstance?.histologicType)
           result.put("histologicType", "Please specify histologic type.")
           
        if(prcFormInstance?.histologicType?.code=='C7' && !prcFormInstance?.histologicTypeDetail ){
            result.put("detail_C7", "Specify histologic types and %.")
        }
        
         if(prcFormInstance?.histologicType?.code=='C8' && !prcFormInstance?.histologicTypeDetail ){
            result.put("detail_C8", "Specify histologic types and %.")
        }
        
         if(prcFormInstance?.histologicType?.code=='C9' && !prcFormInstance?.histologicTypeDetail ){
            result.put("detail_C9", "Specify histologic types and %.")
        }
        
         if(prcFormInstance?.histologicType?.code=='C20' && !prcFormInstance?.histologicTypeDetail ){
            result.put("detail_C20", "Specify histologic types and %.")
        }
        
         if(prcFormInstance?.histologicType?.code=='OTHER' && !prcFormInstance?.otherHistologicType ){
            result.put("other_type", "Specify other histologic type.")
        }
        
         
        if(prcFormInstance?.tumorDimension==null)
             result.put("tumorDimension", "The greatest tumor dimension on slide is a required field." )
             
        if(prcFormInstance?.pctTumorArea==null)
             result.put("pctTumorArea", "The percent of cross-sectional surface area composed of tumor focus is a required field." )
         
         if(prcFormInstance?.pctTumorCellularity==null)
             result.put("pctTumorCellularity", "The percent of tumor cellularity by cell count of the entire slide is a required field." )
         
        
         if(prcFormInstance?.pctViablTumor==null)
             result.put("pctViablTumor", "The percent viable tumor by surface area is a required field." )
          
         if(prcFormInstance?.pctNecroticTumor==null)
             result.put("pctNecroticTumor", "The percent necrotic tumor by surface area is a required field." )
             
         if(prcFormInstance?.pctViableNonTumor==null)
             result.put("pctViableNonTumor", "The percent viable non-tumor tissue by surface area is a required field." )
         
         if(prcFormInstance?.pctNonCellular==null)
             result.put("pctNonCellular", "The percent non-cellular component by surface area is a required field." )
         
         if(prcFormInstance?.pctNonCellular >0 && !prcFormInstance.nonCellularDesc)
           result.put("nonCellularDesc", "The non-cellular component is a required field.") 
            
          if(prcFormInstance?.category=='Kidney'){
              if(!prcFormInstance?.sarcomatoid){
                 result.put("sarcomatoid", "The presence of sarcomatoid features is a required field.") 
              }
          }
          if(prcFormInstance?.sarcomatoid == 'Present' && !prcFormInstance?.sarcomatoidDesc)
            result.put("sarcomatoidDesc", "Describe sarcomatoid features")
        
    
         if(!prcFormInstance?.gradingSystem)
             result.put("gradingSystem", "The histologic grading system is a required field." )
         
          if(prcFormInstance?.gradingSystem){
              if(!prcFormInstance?.grade)
                  result.put("grade", "The is histologic grade a required field." )
          }
            
           if(!prcFormInstance?.meetsCriteria)
           result.put("meetsCriteria", "Please specify if this slide meets the microscopic analysis criteria.") 
            
        
           
          if(prcFormInstance?.meetsCriteria == 'No' && !prcFormInstance?.reasonNotMeet)
            result.put("reasonNotMeet", "Specify what findings do not meet the microscopic analysis criteria of the BRN project")
           
           if(!prcFormInstance?.consistentLocalPrc)
           result.put("consistentLocalPrc", "Please specify if this slide is consistent with the findings of the local BSS pathologist") 
        
           if(prcFormInstance?.consistentLocalPrc == 'No' && !prcFormInstance?.reasonNotCons)
            result.put("reasonNotCons", "Specify what findings are not consistent with those of the local BSS pathologist")
            
        
         
          return result
     }
     
      static boolean isStarted(prcFormInstance){
       def result=false;
        if(prcFormInstance.version > 0)
          return true;
          
           
        /*  def hisList = prcFormInstance.prcHistologicTypes
          hisList.each(){
             if(it.version > 0){
                // println(" version > 0?????")
                 result=true
               // return true
             }
          }  */
          
          return result                                  
      }
      
    
       def view = {
       
        // params.each(){key,value->
        //      println("in view,   key: " + key + " value: " + value)
        //  }
        
         def errorMap=[:]
        def prcFormInstance = PrcForm.get(params.id)
        
       
        return [prcFormInstance: prcFormInstance, errorMap:errorMap]
  
    }
    
    
     def review = {
        def prcFormInstance = PrcForm.get(params.id)
        def hisList=prcTumorFormService.getHisList(prcFormInstance)
       
        def errorMap=[:]
        def canReview=false
       
        try{
            
           
             
             def result= checkError(prcFormInstance)
               if(result){
                    result.each(){key,value->
                    
                      prcFormInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                     flash.message="failed to submit"
                    render(view: "edit", model: [prcFormInstance: prcFormInstance, hisList:hisList,  errorMap:errorMap, canReview:canReview] )
               }else{
                    //prcReportService.submitReport(prcReportInstance)
                   def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                
                   prcTumorFormService.reviewForm(prcFormInstance, username)
                 
               
                    render(view: "view", model: [prcFormInstance: prcFormInstance] )
               }
              
             }catch(Exception e){
            flash.message="Failed: " + e.toString()  
            redirect(action:"edit", params:[id:prcFormInstance.id])
        
          }
        
    }
    
    
     def reedit = {
        def prcFormInstance = PrcForm.get(params.id)
         def hisList=prcTumorFormService.getHisList(prcFormInstance)
         //println("hisList size: "  + hisList.size())
       
        def errorMap=[:]
        def canReview=false
       
        try{
            
           
             
             def result= checkError(prcFormInstance)
               if(result){
                    result.each(){key,value->
                    
                      prcFormInstance.errors.reject(value, value)
                      errorMap.put(key, "errors")
                    }//each
                     flash.message="failed to submit"
                    render(view: "edit", model: [prcFormInstance: prcFormInstance, hisList:hisList,  errorMap:errorMap, canReview:canReview] )
               }else{
                    //prcReportService.submitReport(prcReportInstance)
                   def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                
                   prcTumorFormService.reEdit(prcFormInstance)
                 
               
                    render(view: "edit", model: [prcFormInstance: prcFormInstance, hisList:hisList,  errorMap:errorMap, canReview:canReview] )
               }
              
             }catch(Exception e){
            flash.message="Failed: " + e.toString()  
            redirect(action:"edit", params:[id:prcFormInstance.id])
        
          }
        
    }
    
}
