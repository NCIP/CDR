package nci.obbr.cahub.util

class BpvCaseStatus {
    int blood = 0
    int tissueGrossEvaluation = 0
    int surgeryAnesthesia = 0
    int tissueReceiptDissection = 0
    int workSheet = 0
    int tissueProcessEmbed = 0
    int slidePrep = 0
    int clinicalDataEntry = 0
    int caseQualityReview = 0
    int  sixMonthFollowUp = 0
    
    boolean stopCondition1 = false
    boolean stopCondition2 = false
    boolean stopCondition3 = false
    
    long qcSpecimenId
    
    boolean hideBlood = false
    boolean hideTissueGrossEvaluation = false
    boolean hideSurgeryAnesthesia = false
    boolean hideTissueReceiptDissection = false
    boolean hideWorkSheet = false
    boolean hideTissueProcessEmbed = false
    boolean hideSlidePrep = false
    boolean hideFinalSurgicalPath = false
    boolean hideClinicalDataEntry = false
    boolean hideCaseQualityReview = false
    boolean hideFollowUp = false
   
}
