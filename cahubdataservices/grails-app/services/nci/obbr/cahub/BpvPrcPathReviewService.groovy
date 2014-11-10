package nci.obbr.cahub

import nci.obbr.cahub.forms.bpv.*;
import nci.obbr.cahub.prc.bpv.*;
import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.util.*;

class BpvPrcPathReviewService {

    static transactional = true
    
    def activityEventService
    
    def createForm(bpvPrcPathReviewInstance) { 
        try {
            bpvPrcPathReviewInstance.status = 'Editing'
            def tissue = bpvPrcPathReviewInstance.slideRecord.specimenRecord.tissueType.code
            
            if (tissue == 'OVARY') {
                bpvPrcPathReviewInstance.category = "Ovary"
            }
           
            if (tissue == 'KIDNEY') {
                bpvPrcPathReviewInstance.category = "Kidney"
            }
            
            if (tissue == 'COLON') {
                bpvPrcPathReviewInstance.category = "Colon"
            }
            
            if (tissue == 'LUNG') {
                bpvPrcPathReviewInstance.category = "Lung"
            }
            
            // Create new bpvPrcExpSampleReviews
           // def expSampleProtocolList = [Protocol.findByCode('BPV_PROTOCOL_A'),Protocol.findByCode('BPV_PROTOCOL_B'),Protocol.findByCode('BPV_PROTOCOL_C'),Protocol.findByCode('BPV_PROTOCOL_D'),Protocol.findByCode('BPV_PROTOCOL_E'),Protocol.findByCode('BPV_PROTOCOL_F'),Protocol.findByCode('BPV_PROTOCOL_G'),Protocol.findByCode('BPV_PROTOCOL_H')]
           def expSampleProtocolList=getProtocolList(bpvPrcPathReviewInstance)
           if(bpvPrcPathReviewInstance.slideRecord?.module?.code=='MODULE1' || bpvPrcPathReviewInstance.slideRecord?.module?.code=='MODULE2'){
            def slideList = SlideRecord.createCriteria().list {
                createAlias("specimenRecord", "s")
                eq("s.caseRecord", bpvPrcPathReviewInstance.caseRecord)
                'in'("s.protocol", expSampleProtocolList)
            }
            for (i in slideList) {
                 if(!excluded(i) && ImageRecord.findAllBySlideRecord(i)){
                        bpvPrcPathReviewInstance.addToBpvPrcExpSampleReviews(slideRecord:i)
                 }
            }
           }
            bpvPrcPathReviewInstance.save(failOnError:true)
        } catch (Exception e) {
            throw new RuntimeException(e.toString())
        }
    }
    
    def retrieveHisList(bpvPrcPathReviewInstance) {
        try {
            def tissue = bpvPrcPathReviewInstance.slideRecord.specimenRecord.tissueType.code
            def hisList0
            def hisList1 = []
            def c20
            def other
            
            if (tissue == 'OVARY') {
                def ovary_str = (AppSetting.findByCode("HISTO_OVARY")).bigValue
                def list = "'" + ovary_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
           
            if (tissue == 'KIDNEY') {
                def kidney_str = (AppSetting.findByCode("HISTO_KIDNEY")).bigValue
                def list = "'" + kidney_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
            
            if (tissue == 'COLON') {
                def colon_str = (AppSetting.findByCode("HISTO_COLON")).bigValue
                def list = "'" + colon_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
            
            if (tissue == 'LUNG') {
                def lung_str = (AppSetting.findByCode("HISTO_LUNG")).bigValue
                def list = "'" + lung_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }

            // Put "other" options at the end
            hisList0.each() {
                if (it.code == 'C20') {
                    c20 = it
                } else if (it.code == 'OTHER') {
                    other = it
                } else {
                    hisList1.add(it)
                }
            }
            if (c20 != null) {
                hisList1.add(c20)
            }
            if (other != null) {
                hisList1.add(other)
            }
            
            return hisList1
        } catch (Exception e) {
            throw new RuntimeException(e.toString())
        }
    }
    
    def saveForm(params) {
        def bpvPrcPathReviewInstance = BpvPrcPathReview.get(params.id)
        bpvPrcPathReviewInstance.properties = params
        
        if (params.histologicType?.id) {
            def histologicType = HistologicType.findById(params.histologicType.id)
            if (histologicType.code == 'C7') {
                bpvPrcPathReviewInstance.otherHistologicType = params.detail_C7  
            } else if (histologicType.code == 'C8') {
                bpvPrcPathReviewInstance.otherHistologicType = params.detail_C8   
            } else if (histologicType.code == 'C9') {
                bpvPrcPathReviewInstance.otherHistologicType = params.detail_C9
            } else if (histologicType.code == 'C20') {
                bpvPrcPathReviewInstance.otherHistologicType = params.detail_C20
            } else if (histologicType.code == 'OTHER') {
                bpvPrcPathReviewInstance.otherHistologicType = params.detail_other
            } else if (histologicType.code == 'C78') {
                bpvPrcPathReviewInstance.otherHistologicType = params.detail_C78
            } else {
                bpvPrcPathReviewInstance.otherHistologicType = null
            }
        }
        
        // Saving experiment sample reviews
        for (i in bpvPrcPathReviewInstance.bpvPrcExpSampleReviews) {
            i.sameOriginType = params.get("sameOriginType_" + i.id?.toString())
            i.meetsCriteria = params.get("meetsCriteria_" + i.id?.toString())
            i.reasonNotMeet = params.get("reasonNotMeet_" + i.id?.toString())
            i.percTumor=params.get("percTumor_" + i.id?.toString())
        }
        
        bpvPrcPathReviewInstance.save(failOnError:true)  
    }
     
    def reviewForm(bpvPrcPathReviewInstance, username) {
        try {
            bpvPrcPathReviewInstance.status = "Reviewed"
            bpvPrcPathReviewInstance.reviewedBy = username
            bpvPrcPathReviewInstance.reviewDate = new Date()
            bpvPrcPathReviewInstance.save(failOnError: true)
            
            def activityType = ActivityType.findByCode("PRCCOMP")
            def caseId = bpvPrcPathReviewInstance.caseRecord.caseId
            def study = bpvPrcPathReviewInstance.caseRecord.study
            def bssCode = bpvPrcPathReviewInstance.caseRecord.bss?.parentBss?.code
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def reEdit(bpvPrcPathReviewInstance) {
        try {
            bpvPrcPathReviewInstance.status = "Editing"
            bpvPrcPathReviewInstance.reviewedBy = null
            bpvPrcPathReviewInstance.reviewDate = null
            bpvPrcPathReviewInstance.save(failOnError: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    // Add new experimental sample review if it does not exist
    def addNewReview(bpvPrcPathReviewInstance) {
        def slideRecord = bpvPrcPathReviewInstance.slideRecord
        if(slideRecord.module?.code=='MODULE3N' || slideRecord.module?.code=='MODULE4N' || slideRecord.module?.code=='MODULE5'){
            
        }else{
            //def expSampleProtocolList = [Protocol.findByCode('BPV_PROTOCOL_A'),Protocol.findByCode('BPV_PROTOCOL_B'),Protocol.findByCode('BPV_PROTOCOL_C'),Protocol.findByCode('BPV_PROTOCOL_D'),Protocol.findByCode('BPV_PROTOCOL_E'),Protocol.findByCode('BPV_PROTOCOL_F'),Protocol.findByCode('BPV_PROTOCOL_G'),Protocol.findByCode('BPV_PROTOCOL_H')]
            def expSampleProtocolList =getProtocolList(bpvPrcPathReviewInstance)
            def slideList = SlideRecord.createCriteria().list {
                createAlias("specimenRecord", "s")
                eq("s.caseRecord", bpvPrcPathReviewInstance.caseRecord)
                'in'("s.protocol", expSampleProtocolList)
            }
            def found
            for (i in slideList) {
                found = false
                for (j in i.bpvPrcExpSampleReviews) {
                    if (j.bpvPrcPathReview == bpvPrcPathReviewInstance) {
                        found = true
                    }
                }
                if (!found) {
                    if(!excluded(i) && ImageRecord.findAllBySlideRecord(i)){
                        bpvPrcPathReviewInstance.addToBpvPrcExpSampleReviews(slideRecord:i).save()
                    }
                }
            }
        }
    }

    def excluded(slideRecord){
        def result = false
        def events =slideRecord.processEvents
        events.each(){
            if(it.processName){
                if(it.processName.toLowerCase().indexOf("clarient") > -1 || it.processName.toLowerCase().indexOf("caprion") > -1){
                    result = true
                }
            }
            
        }
        
        return result
    }

     def getProtocolList(bpvPrcPathReviewInstance) {
         def result=[]
         def caseRecord=bpvPrcPathReviewInstance.caseRecord
         def slideRecord = bpvPrcPathReviewInstance.slideRecord
         def bpvWorkSheet=caseRecord.bpvWorkSheet
         if(slideRecord.module?.code=='MODULE1'){
             result.add(Protocol.findByCode('BPV_PROTOCOL_A'))
             result.add(Protocol.findByCode('BPV_PROTOCOL_B'))
             result.add(Protocol.findByCode('BPV_PROTOCOL_C'))
             result.add(Protocol.findByCode('BPV_PROTOCOL_D'))
             if(bpvWorkSheet.sm2 && !bpvWorkSheet.module2Sheet.sampleQc.sample){
                result.add(Protocol.findByCode('BPV_PROTOCOL_E'))
                result.add(Protocol.findByCode('BPV_PROTOCOL_F'))
                result.add(Protocol.findByCode('BPV_PROTOCOL_G'))
                result.add(Protocol.findByCode('BPV_PROTOCOL_H')) 
             }
         }
         
          if(slideRecord.module?.code=='MODULE2'){
              result.add(Protocol.findByCode('BPV_PROTOCOL_E'))
              result.add(Protocol.findByCode('BPV_PROTOCOL_F'))
              result.add(Protocol.findByCode('BPV_PROTOCOL_G'))
              result.add(Protocol.findByCode('BPV_PROTOCOL_H')) 
              
                if(bpvWorkSheet.sm1 && !bpvWorkSheet.module1Sheet.sampleQc.sample){
                result.add(Protocol.findByCode('BPV_PROTOCOL_A'))
                result.add(Protocol.findByCode('BPV_PROTOCOL_B'))
                result.add(Protocol.findByCode('BPV_PROTOCOL_C'))
                result.add(Protocol.findByCode('BPV_PROTOCOL_D')) 
             }
              
          }
          
        
         
         return result
     }
    
}


