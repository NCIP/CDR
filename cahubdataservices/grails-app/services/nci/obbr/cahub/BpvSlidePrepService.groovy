package nci.obbr.cahub

import nci.obbr.cahub.forms.bpv.BpvSlidePrep
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SlideRecord
import nci.obbr.cahub.staticmembers.Module

import nci.obbr.cahub.staticmembers.FormMetadata

class BpvSlidePrepService {

    static transactional = true

    def saveForm(params, request){
        try {
            def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        } catch(Exception e){
               e.printStackTrace()
               throw new RuntimeException(e.toString())
        }
    }
    
  
      def submitForm(bpvSlidePrepInstance, username) {
        // println("start save???")
        try{
            def caseRecord = bpvSlidePrepInstance.caseRecord
            //def caseId = caseRecord.caseId
            def caseId=caseRecord.id.toString()
            bpvSlidePrepInstance.dateSubmitted = new Date()
            bpvSlidePrepInstance.submittedBy=username
            bpvSlidePrepInstance.save(failOnError:true)
            if(caseRecord?.bpvWorkSheet?.dateSubmitted){
                //def qcSample = caseRecord?.bpvWorkSheet?.qcAndFrozenSample?.sampleQc?.sample
                def slides = SlideRecord.findAllByCaseId(caseId)
                slides.each{
                    def qcSample=null
                    if(it.module?.code=='MODULE1'){
                        qcSample = caseRecord?.bpvWorkSheet?.module1Sheet?.sampleQc?.sample
                    }else if (it.module?.code=='MODULE2'){
                        qcSample = caseRecord?.bpvWorkSheet?.module2Sheet?.sampleQc?.sample
                    }else if(it.module?.code=='MODULE3N'){
                       qcSample = caseRecord?.bpvWorkSheet?.module3NSheet?.sampleQc?.sample 
                    }else if(it.module?.code=='MODULE4N'){
                        qcSample = caseRecord?.bpvWorkSheet?.module4NSheet?.sampleQc?.sample 
                    }else if (it.module?.code=='MODULE5'){
                        qcSample = caseRecord?.bpvWorkSheet?.module5Sheet?.sampleQc?.sample 
                    }else{
                        
                    }
                    it.specimenRecord = qcSample
                    it.save(failOnError:true)
                }
                
            }
          
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }

    }
    
    
     def updateForm(params){
         def msg=""
         def slideList =[]
        try {
            def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
            bpvSlidePrepInstance.properties = params
            bpvSlidePrepInstance.save(failOnError:true)
            params.each(){key, value->
                if(key.startsWith("slideId_")){
                    def sid = key.substring(8)
                    def slideRecord = SlideRecord.findById(sid)
                   
                    if( !value){
                    msg+=", slide id can't be empty"    
                    
                    } else if((SlideRecord.findBySlideId(value) && value != slideRecord.slideId)|| slideList.contains(value)){
                       msg+=", duplicated slide id " + value 
                    }else{
                        slideList.add(value)
                        slideRecord.slideId=value
                        if(!slideRecord.specimenRecord ){
                            def mid = params.get("module_" + sid)
                            slideRecord.module=Module.findById(mid)

                        }
                        slideRecord.save(failOnError:true)
                    }
                }
              }
                
            
            
        } catch(Exception e){
               e.printStackTrace()
               throw new RuntimeException(e.toString())
        }
        
        return msg
    }
    
}
