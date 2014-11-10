package nci.obbr.cahub.prc.bpv

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.forms.bpv.BpvFormBaseClass
import nci.obbr.cahub.staticmembers.HistologicType

class BpvPrcPathReview extends BpvFormBaseClass {
    
    CaseRecord caseRecord
    String reviewedBy
    Date reviewDate
    SlideRecord slideRecord
    String organOrigin
    String otherOrganOrigin
    HistologicType histologicType
    String otherHistologicType
    String sarcomatoid
    String sarcomatoidDesc
    Float tumorDimension
    Float pctTumorArea
    Float pctTumorCellularity
    Float pctNecroticTissue
    Float pctViablTumor
    Float pctNecroticTumor
    Float pctViableNonTumor
    Float pctNonCellular
    Float hisTotal
    String nonCellularDesc
    String gradingSystem
    String grade
    String meetsCriteria
    String reasonNotMeet
    String pathologyComments
    String consistentLocalPrc
    String reasonNotCons
    String status
    String category
    String histoEligible
    
    static hasMany = [bpvPrcExpSampleReviews:BpvPrcExpSampleReview]
    
    static mapping = {
        table 'bpv_prc_path_review'
        id generator:'sequence', params:[sequence:'bpv_prc_path_review_pk']
        bpvPrcExpSampleReviews sort:'slideRecord'
    }
    
    static constraints = {
        caseRecord(unique:false, nullable:false, blank:false)
        reviewedBy(nullable:true, blank:true)
        reviewDate(nullable:true, blank:true)
        slideRecord(nullable:false, blank:false)
        organOrigin(nullable:true, blank:true)
        otherOrganOrigin(nullable:true, blank:true)
        histologicType(nullable:true, blank:true)
        otherHistologicType(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        sarcomatoid(nullable:true, blank:true)
        sarcomatoidDesc(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        tumorDimension(nullable:true, blank:true)
        pctTumorArea(nullable:true, blank:true)
        pctTumorCellularity(nullable:true, blank:true)
        pctNecroticTissue(nullable:true, blank:true)
        pctViablTumor(nullable:true, blank:true)
        pctNecroticTumor(nullable:true, blank:true)
        pctViableNonTumor(nullable:true, blank:true)
        pctNonCellular(nullable:true, blank:true)
        hisTotal(nullable:true, blank:true)
        nonCellularDesc(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        gradingSystem(nullable:true, blank:true)
        grade(nullable:true, blank:true)
        meetsCriteria(nullable:true, blank:true)
        reasonNotMeet(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        pathologyComments(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        consistentLocalPrc(nullable:true, blank:true)
        reasonNotCons(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        status(nullable:true, blank:true)
        category(nullable:true, blank:true)
        histoEligible(nullable:true, blank:true)
    }
}
