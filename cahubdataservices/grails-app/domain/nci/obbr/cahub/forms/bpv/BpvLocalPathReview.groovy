package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.HistologicType

class BpvLocalPathReview extends BpvFormBaseClass {
    
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
    String pathTumor
    String pathNodes
    String pathMetastases
    String pathStage
    String meetsCriteria
    String reasonNotMeet
    String pathologyComments
    String consistentLocalPrc
    String reasonNotCons
    String pathologistName
    String dataEnteredBy
    Date dateSlideReview
    String status
    String category
    SOPRecord formSOP
    String histoEligible
    
    static mapping = {
        table 'bpv_local_path_review'
        id generator:'sequence', params:[sequence:'bpv_local_path_review_pk']
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
        pathTumor(nullable:true, blank:true)
        pathNodes(nullable:true, blank:true)
        pathMetastases(nullable:true, blank:true)
        pathStage(nullable:true, blank:true)
        meetsCriteria(nullable:true, blank:true)
        reasonNotMeet(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        pathologyComments(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        consistentLocalPrc(nullable:true, blank:true)
        reasonNotCons(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        pathologistName(nullable:true, blank:true)
        dataEnteredBy(nullable:true, blank:true)
        dateSlideReview(nullable:true, blank:true)
        status(nullable:true, blank:true)
        category(nullable:true, blank:true)
        formSOP(nullable:true, blank:true)
        histoEligible(nullable:true, blank:true)
    }
}
