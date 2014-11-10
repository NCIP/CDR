package nci.obbr.cahub
import nci.obbr.cahub.prctumor.*;
import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.util.*;

class PrcTumorFormService {

    static transactional = true
    
    def activityEventService
    
     def createForm(prcFormInstance) { 
       try{
           prcFormInstance.status = 'Editing'
           def tissue =prcFormInstance.slideRecord.specimenRecord.tissueType.code
           def hisList
           def hisOther
           def hisOtherS
           if(tissue == 'OVARY'){
               prcFormInstance.category="Ovary"
           }
           
            if(tissue=='KIDNEY'){             
                prcFormInstance.category="Kidney"
            }
            
            prcFormInstance.save(failOnError:true)
          
          // println("hisList size:" + hisList.size())
            
          
          
       
           
       
       }catch(Exception e){
          
               throw new RuntimeException(e.toString())
       }
        

    }
    
    
    def getHisList(prcFormInstance){
         def hisList
         def tissue =prcFormInstance.slideRecord.specimenRecord.tissueType.code
          if(tissue == 'OVARY'){
               def ovary_str = (AppSetting.findByCode("HISTO_OVARY")).bigValue
               def ovary_str2 = ovary_str.replace(",C20", "")
               def ovary_str3 = ovary_str2.replace(",OTHER", "")
               def list="'" + ovary_str3.replace(",", "','") +"'"
               hisList = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list +") order by h.name")
               hisList.add(HistologicType.findByCode("C20"))
               hisList.add(HistologicType.findByCode("OTHER"))
             
           }
           
         if(tissue=='KIDNEY'){
               def ovary_str = (AppSetting.findByCode("HISTO_KIDNEY")).bigValue
                def ovary_str2 = ovary_str.replace(",OTHER", "")
               def list="'" + ovary_str2.replace(",", "','") +"'"
               hisList = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list +") order by h.name")
               hisList.add(HistologicType.findByCode("OTHER"))
              
          }
          return hisList
    }
    
     def saveForm(params){
          def prcFormInstance = PrcForm.get(params.id)
          prcFormInstance.properties = params
          if(params.histologicType?.id){
              def histologicType = HistologicType.findById(params.histologicType.id)
              if(histologicType.code=='C7'){
                prcFormInstance.histologicTypeDetail=params.detail_C7  
              }else if (histologicType.code=='C8'){
                  prcFormInstance.histologicTypeDetail=params.detail_C8   
              } else if (histologicType.code=='C9'){
                  prcFormInstance.histologicTypeDetail=params.detail_C9
              }else if (histologicType.code=='C20'){
                  prcFormInstance.histologicTypeDetail=params.detail_C20
              }else{
                  prcFormInstance.histologicTypeDetail=null
              }
              
            
              if(histologicType.code=='OTHER'){
                  prcFormInstance.otherHistologicType=params.otherHistoloicType
              }else{
                  prcFormInstance.otherHistologicType=null
              }
          }
        
          prcFormInstance.save(failOnError:true)
          
         
          
        /**  def hisList = prcFormInstance.prcHistologicTypes
          hisList.each(){
              def hisId = it.id
              if(params.get("his_" + hisId) == 'on'){
                  it.included = true
                  if(params.get("detail_" + hisId)){
                      it.details= params.get("detail_" + hisId)
                  }
              }else{
                  it.included = false
                  it.details= null
              }
              it.save(failOnError:true)
          }**/
          
     }
     
    def reviewForm(prcFormInstance, username){
        
          try{
          
           prcFormInstance.status="Reviewed"
           prcFormInstance.reviewedBy=username
           prcFormInstance.reviewDate=new Date()
           
           prcFormInstance.save(failOnError:true)
            
           def activityType = ActivityType.findByCode("PRCCOMP")
           def caseId = prcFormInstance.caseRecord.caseId
           def study = prcFormInstance.caseRecord.study
           def bssCode = prcFormInstance.caseRecord.bss?.parentBss?.code
           activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
       
       }catch(Exception e){
              e.printStackTrace()
            
               throw new RuntimeException(e.toString())
       }
        
    }
    
    
     def reEdit(prcFormInstance){
        
          try{
          
           prcFormInstance.status="Editing"
          
           
           prcFormInstance.save(failOnError:true)
            
          
       
       }catch(Exception e){
              e.printStackTrace()
            
               throw new RuntimeException(e.toString())
       }
        
    }
   
   def upload(params, request){
       
     try{
       def cat = params.cat
       def caseRecord
       def slideRecord
       if(cat=='F'){
             caseRecord = CaseRecord.get(params.id)
       }
       if(cat=='L'){
           slideRecord = SlideRecord.get(params.id)
           caseRecord= slideRecord?.specimenRecord?.caseRecord
       }
       
       def caseId = caseRecord.caseId
       
        def uploadedFile = request.getFile("filepath")
          if(uploadedFile){
              def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            
              def strippedFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.'))                    
                            
            
              def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                    
          
                        
                       
             def current_time = (new Date()).getTime()
                            
             def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
              def dir_name=''
              
            def pathUploads
             if(cat=='F'){
             
                 pathUploads = AppSetting.findByCode("FILE_STORAGE").value  + File.separator + 'BRN' + File.separator + caseId + File.separator + 'final_surgical_review'
             }
                
             if(cat=='L'){
               pathUploads = AppSetting.findByCode("PRC_FILE").value +File.separator + caseId + File.separator +  'local_pathology_review'     
             }
           
             File dir = new File(pathUploads)
             if(!dir.exists()){
                  dir.mkdirs()
             }
              uploadedFile.transferTo( new File(pathUploads, newFileName) )
             // println("file uplodaed????")
              if(cat=='F'){
                  caseRecord.finalSurgicalPath=pathUploads + File.separator + newFileName
                  caseRecord.save(failOnError:true)
              }
               
              if(cat=='L'){
                  slideRecord.localPathologyReview = pathUploads + File.separator + newFileName
                  slideRecord.save(failOnError:true)
              }  
             
           }
          }catch(Exception e){
                e.printStackTrace()
            
               throw new RuntimeException(e.toString())
          }
       
   }
    
}
