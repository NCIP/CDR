package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.staticmembers.BSS

public class BaseGTEXCRFController{
    
    //set isConsented boolean on candidateRecord
    def setConsentOnCandidateRecord(icdobj){

        if(icdobj.consentObtained == 'Yes'){
            
            icdobj.candidateRecord.isConsented = true
        }
        else{
       
            icdobj.candidateRecord.isConsented = false
        }    
    }
    
    //calculate eligibility and set isEligible on candidateRecord
    def calculateAndSetEligibility(donorobj,ver52Updates){
        def chkResponseFromQ4=false
        
        if(ver52Updates){            
            if(donorobj.collectIn8afterDeath == 'Yes' || donorobj.collectAllIn24afterDeath == 'Yes'){
                chkResponseFromQ4=true
            }
        }
        else{
            if(donorobj.collectIn24hDeath == 'Yes'){
                chkResponseFromQ4=true
            }
            
        }
       
      
        if(donorobj.allowedMinOrganType == 'No' || 
            donorobj.age == 'No' ||
            donorobj.BMI == 'No' ||
            donorobj.receiveTransfusionIn48h == 'Yes' || donorobj.receiveTransfusionIn48h == 'Unknown' ||
            donorobj.diagnosedMetastatis == 'Yes' ||
            donorobj.receivedChemoIn2y == 'Yes' ||
            donorobj.drugAbuse == 'Yes' ||
            donorobj.histOfSexWithHIV == 'Yes' ||
            donorobj.contactHIV == 'Yes' ||
            donorobj.histOfReactiveAssays == 'Yes' ||
            !chkResponseFromQ4
        ){

            donorobj.candidateRecord.isEligible = false
        }
       
        else{
            /**
            println  "allowedMinOrganType = "+donorobj.allowedMinOrganType
            println  "age = "+donorobj.age
            println  "BMI = "+donorobj.BMI
            println  "receiveTransfusionIn48h = "+donorobj.receiveTransfusionIn48h
            println  "diagnosedMetastatis = "+donorobj.diagnosedMetastatis
            println  "receivedChemoIn2y = "+donorobj.receivedChemoIn2y
            println  "drugAbuse = "+donorobj.drugAbuse
            println  "histOfSexWithHIV = "+donorobj.histOfSexWithHIV
            println  "contactHIV = "+donorobj.contactHIV
            println  "histOfReactiveAssays = "+donorobj.histOfReactiveAssays
            println  "chkResponseFromQ4 = "+chkResponseFromQ4
             **/
            
            donorobj.candidateRecord.isEligible = true            
        }

    }
    
    //get a list of subs to a parent BSS
    def bssSubList(passedBss){

        def bssSubList = []
        
        if(!passedBss){
            bssSubList = BSS.list()
        }
        else{
            def bss = BSS.findByCode(passedBss?.parentBss.code)
            //get all bss, parent and subs
            if(bss){
                def tmpList = BSS.findAllByParentBss(bss)
     
                tmpList.each{
                    //strip BSSs that don't have a protocolSiteNum
                    if(it.protocolSiteNum){
                        bssSubList.add(it)
                    }
                }
            }
        }
        return bssSubList
    }
    
}
