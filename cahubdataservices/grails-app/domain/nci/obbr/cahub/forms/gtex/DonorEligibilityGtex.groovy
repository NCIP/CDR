package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.CDRBaseClass

class DonorEligibilityGtex extends CDRBaseClass{

    //static searchable = true    
    
    CandidateRecord candidateRecord
    
    String allowedMinOrganType       // Q1
    String age                       // Q2
    String BMI                       // Q3
    String collectIn24hDeath                 // Q4
    // from 5.2 onwards Q4  will be
    String collectIn8afterDeath // new Q4a
    String collectAllIn24afterDeath // new Q4b
    String receiveTransfusionIn48h    // new Q5
    
    String diagnosedMetastatis               //Q6
    String receivedChemoIn2y                 //Q7
    String drugAbuse                  //Q8
    String histOfSexWithHIV           //Q9
    String contactHIV                 //Q10
    String histOfReactiveAssays       //Q11
    
    // Second Section 
    String histOfInfections                 // Q12
    String documentedSepsis
    String pneumonia
    String openWounds
    String highUnexplainedFever
    String positiveBloodCultures
    String abnormalWBC  // (< 2 or > 20)
    String infectedLines
    String fungalInfections
    String ascities
    String cellulites  
    
    String pastBloodDonations         // Q13
    String bloodDonDenialReason
    
    String bloodTransfusion           // Q14
    
    String humAnimTissueTransplant    // Q15
    String       tissueTransplantComments
   
    String recentSmallpoxVac          //Q16
    String contactWithSmallpox        //Q17
    String dialysisTreatment          //Q18
    String currentCancerDiag          //Q19
    String cancerDiagPrec5yrs         //Q20
    String tBHistory                  //Q21
    String activeMeningitis           //Q22
    String activeEncephalitis         //Q23
    String longtermSteroidUse         //Q24
    String osteomyelitis              //Q25
    String unexplSeizures             //Q26
    String unexplWkness               //Q27
    String exposureToToxics              //Q28
    String noPhysicalActivity         //Q29
    String residedOnMilitBase         //Q30
    String biteFromAnimal             //Q31
    String heroinUse                  //Q32
    String cocaineUse                 //Q33
    String menWithMen                 //Q34
    String drugUseForNonMed           //Q35
    String hemophilia                 //Q36
    String sexForMoneyDrugs           //Q37
    
    String sexWithOthers                    //Q38
    String menSexWithMen
    String drugsForNonMed5yrs         //Q39
    String hemophiliaTreat            //Q40
    String sexForDrugsOrMoney         //Q41
        
    String notTestedForHiv            //q42
    
    String medHistory                       //Q43
    String unexplWtLoss               //Q44
    String nightSweats                //Q45
    String spotsOnSkin                //Q46
    String unexplLymphad              //Q47
    String unexplTemp     //Q48
    String unexplCough    //Q49
    String oppInfections  //Q50
    String sexTansDis     //Q51
    String signsOfDrugAbuse   //Q52
    
        
    String diagOfSars        //Q53
    String histOfWestNile     //Q54
    String westNileContact    //Q55
    String unexplWeighttLoss  //Q56
    String timeInDetCenter    //Q57
    String tatttoos           //Q58
    String growthHarmone      //Q59
    String prescDrugAbuse     //Q60
    String intravenDrugAbuse  //Q61
    String syphilisTreat      //Q62
    String gonorrheaTreat     //Q63
        
    String histOfphysicContact      //Q64
    String hepatitisB         //Q65
    String hepatitisC         //Q66
    String hiv                //Q67
    
    String timeInUk           //Q68
    String nonProfpiercing    //Q69
    String nonProfTattoos     //Q70
    String stateRunHome       //Q71
    String timeInEurope       //Q72
    
    String histOfAutoImmDis         //Q73
    String systLupus          //Q74
    String sarcoidosis        //Q75
    String scleroderma        //Q76
    String reyesSynd          //Q77
    String rheumArthritis     //Q78
    String heartDis           //Q79
    String alzheimers         //Q80
    String dementia           //Q81
    String multiSclero        //Q82
    String lateralSclero      //Q83
    String creutzfeldtJakob   //Q84
    
    String endComments // Q85 last


    static constraints = {

        candidateRecord(nullable:true, blank:true)           
        allowedMinOrganType(nullable:true, blank:true)
        age(nullable:true, blank:true)
        BMI(nullable:true, blank:true)        
        collectIn24hDeath(nullable:true, blank:true)
        receiveTransfusionIn48h(nullable:true, blank:true)    
        diagnosedMetastatis(nullable:true, blank:true)    
        receivedChemoIn2y(nullable:true, blank:true)    
        drugAbuse(nullable:true, blank:true)    
        histOfSexWithHIV(nullable:true, blank:true)    
        contactHIV(nullable:true, blank:true)    
        histOfReactiveAssays(nullable:true, blank:true)             
        histOfInfections(nullable:true, blank:true)                     // Q12
        documentedSepsis(nullable:true, blank:true)
        pneumonia(nullable:true, blank:true)
        openWounds(nullable:true, blank:true)
        highUnexplainedFever(nullable:true, blank:true)
        positiveBloodCultures(nullable:true, blank:true)
        abnormalWBC(nullable:true, blank:true)  // (< 2 or > 20)
        infectedLines(nullable:true, blank:true)
        fungalInfections(nullable:true, blank:true)
        ascities(nullable:true, blank:true)
        cellulites(nullable:true, blank:true)          
        pastBloodDonations(nullable:true, blank:true)         // Q13           
        bloodDonDenialReason(nullable:true, blank:true)        
        bloodTransfusion(nullable:true, blank:true)           // Q14
        
        humAnimTissueTransplant(nullable:true, blank:true)    // Q15
        
        tissueTransplantComments(nullable:true, blank:true)
        
        recentSmallpoxVac(nullable:true, blank:true)          //Q16
        contactWithSmallpox(nullable:true, blank:true)        //Q17
        dialysisTreatment(nullable:true, blank:true)          //Q18
        currentCancerDiag(nullable:true, blank:true)          //Q19
        cancerDiagPrec5yrs(nullable:true, blank:true)         //Q20
        tBHistory(nullable:true, blank:true)                  //Q21
        activeMeningitis(nullable:true, blank:true)           //Q22
        activeEncephalitis(nullable:true, blank:true)         //Q23
        longtermSteroidUse(nullable:true, blank:true)         //Q24
        osteomyelitis(nullable:true, blank:true)              //Q25
        unexplSeizures(nullable:true, blank:true)             //Q26
        unexplWkness(nullable:true, blank:true)               //Q27
        exposureToToxics(nullable:true, blank:true)              //Q28
        noPhysicalActivity(nullable:true, blank:true)         //Q29
        residedOnMilitBase(nullable:true, blank:true)         //Q30
        biteFromAnimal(nullable:true, blank:true)             //Q31
        heroinUse(nullable:true, blank:true)                  //Q32
        cocaineUse(nullable:true, blank:true)                 //Q33
        menWithMen(nullable:true, blank:true)                 //Q34
        drugUseForNonMed(nullable:true, blank:true)          //Q35
        hemophilia(nullable:true, blank:true)                 //Q36
        sexForMoneyDrugs(nullable:true, blank:true)           //Q37
        
        sexWithOthers(nullable:true, blank:true)                    //Q38
        menSexWithMen(nullable:true, blank:true)
        drugsForNonMed5yrs(nullable:true, blank:true)         //Q39
        hemophiliaTreat(nullable:true, blank:true)            //Q40
        sexForDrugsOrMoney(nullable:true, blank:true)         //Q41
        
        notTestedForHiv(nullable:true, blank:true)            //q42
        
        medHistory(nullable:true, blank:true)                       //Q43
        unexplWtLoss(nullable:true, blank:true)               //Q44
        nightSweats(nullable:true, blank:true)                //Q45
        spotsOnSkin(nullable:true, blank:true)               //Q46
        unexplLymphad(nullable:true, blank:true)              //Q47
        unexplTemp(nullable:true, blank:true)     //Q48
        unexplCough(nullable:true, blank:true)    //Q49
        oppInfections(nullable:true, blank:true) //Q50
        sexTansDis(nullable:true, blank:true)     //Q51
        signsOfDrugAbuse(nullable:true, blank:true)   //Q52
        
        
        diagOfSars(nullable:true, blank:true)        //Q53
        histOfWestNile(nullable:true, blank:true)     //Q54
        westNileContact(nullable:true, blank:true)    //Q55
        unexplWeighttLoss(nullable:true, blank:true)  //Q56
        timeInDetCenter(nullable:true, blank:true)    //Q57
        tatttoos(nullable:true, blank:true)           //Q58
        growthHarmone(nullable:true, blank:true)      //Q59
        prescDrugAbuse(nullable:true, blank:true)     //Q60
        intravenDrugAbuse(nullable:true, blank:true)  //Q61
        syphilisTreat(nullable:true, blank:true)      //Q62
        gonorrheaTreat(nullable:true, blank:true)     //Q63
        
        histOfphysicContact(nullable:true, blank:true)      //Q64
        hepatitisB(nullable:true, blank:true)         //Q65
        hepatitisC(nullable:true, blank:true)         //Q66
        hiv(nullable:true, blank:true)                //Q67
        
        timeInUk(nullable:true, blank:true)           //Q68
        nonProfpiercing(nullable:true, blank:true)    //Q69
        nonProfTattoos(nullable:true, blank:true)     //Q70
        stateRunHome(nullable:true, blank:true)       //Q71
        timeInEurope(nullable:true, blank:true)       //Q72
        
        histOfAutoImmDis(nullable:true, blank:true)         //Q73
        systLupus(nullable:true, blank:true)          //Q74
        sarcoidosis(nullable:true, blank:true)        //Q75
        scleroderma(nullable:true, blank:true)        //Q76
        reyesSynd(nullable:true, blank:true)          //Q77
        rheumArthritis(nullable:true, blank:true)     //Q78
        heartDis(nullable:true, blank:true)           //Q79
        alzheimers(nullable:true, blank:true)         //Q80
        dementia(nullable:true, blank:true)           //Q81
        multiSclero(nullable:true, blank:true)        //Q82
        lateralSclero(nullable:true, blank:true)      //Q83
        creutzfeldtJakob(nullable:true, blank:true)   //Q84                                
        endComments(nullable:true, blank:true, maxSize:4000)
        
        collectIn8afterDeath(nullable:true, blank:true) // new Q4a from 5.2 onwards
        collectAllIn24afterDeath(nullable:true, blank:true)//new Q4b from 5.2 onwards
    } 
    
    static mapping = {	
        table 'gtex_donor_eligibility'
        id generator:'sequence', params:[sequence:'gtex_donor_eligibility_pk']
    }
    
}


