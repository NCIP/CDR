package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CaseRecord

class BpvCaseQualityReview extends BpvFormBaseClass {
    
    CaseRecord caseRecord
    String consent
    String tubes
    String plasmaAliquots
    String tumorModule
    String additionalModule
    String slideReview
    String reviewConsistent
    String clinicalData
    String projectCriteria
    String requirements
    String wasStopped
    String reasonStopped
    String otherReason
    String reviewComments
    String sldRevConfirmElig
    
    String normalAdjCollected
    String addlTumorCollected
    
    String whatWasShipped // blood, tissue or both?
    
    static belongsTo = CaseRecord
    
    String toString(){"$caseRecord.caseId"}

    static constraints = {
        caseRecord(blank:false, nullable:false)
        consent(blank:true, nullable:true)
        tubes(blank:true, nullable:true)
        plasmaAliquots(blank:true, nullable:true)
        tumorModule(blank:true, nullable:true)
        additionalModule(blank:true, nullable:true)
        slideReview(blank:true, nullable:true)
        reviewConsistent(blank:true, nullable:true)
        clinicalData(blank:true, nullable:true)
        projectCriteria(blank:true, nullable:true)
        requirements(blank:true, nullable:true)
        wasStopped(blank:true, nullable:true)
        reasonStopped(blank:true, nullable:true)
        otherReason(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        reviewComments(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        sldRevConfirmElig(blank:true, nullable:true)
        normalAdjCollected(blank:true, nullable:true)
        addlTumorCollected(blank:true, nullable:true)
        whatWasShipped(blank:true, nullable:true)
    }
    
    static mapping = {
        table 'bpv_case_quality_review'
        id generator:'sequence', params:[sequence:'bpv_case_quality_review_pk']
    }
}
