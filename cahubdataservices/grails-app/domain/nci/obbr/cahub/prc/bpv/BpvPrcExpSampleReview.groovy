package nci.obbr.cahub.prc.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.SlideRecord

class BpvPrcExpSampleReview extends CDRBaseClass {

    String sameOriginType
    String meetsCriteria
    String reasonNotMeet
    String percTumor
    String no_tumor
    
    static belongsTo = [slideRecord:SlideRecord, bpvPrcPathReview:BpvPrcPathReview]
    
    static mapping = {
        table 'bpv_prc_exp_sample_review'
        id generator:'sequence', params:[sequence:'bpv_prc_exp_sample_review_pk']
    }
    
    static constraints = {
        sameOriginType(nullable:true, blank:true)
        meetsCriteria(nullable:true, blank:true)
        reasonNotMeet(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        percTumor(nullable:true, blank:true)
        no_tumor(nullable:true, blank:true)
    }
}
