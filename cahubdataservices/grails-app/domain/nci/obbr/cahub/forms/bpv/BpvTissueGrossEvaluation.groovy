package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.PhotoRecord
import nci.obbr.cahub.datarecords.SOPRecord

class BpvTissueGrossEvaluation extends BpvFormBaseClass {

    CaseRecord caseRecord
    String tissueReceived
    String tissueNotReceivedReason
    Date dateTimeArrived
    String nameReceived
    SOPRecord transportSOP
    String transportPerformed
    String transportComments
    Double roomTemperature
    String roomTemperatureUnit
    Double roomHumidity
    String nameEvaluated
    Double resectionH
    Double resectionW
    Double resectionD
    Double resectionWeight
    String diseaseObserved
    String diseaseComments
    String diagnosis
    String photoTaken
    String reasonNoPhoto
    String inkUsed
    String inkType
    String excessReleased
    String noReleaseReason
    Double excessH
    Double excessW
    Double excessD
    Double areaPercentage
    Double contentPercentage
    String appearance
    String normalAdjReleased
    Double normalAdjH
    Double normalAdjW
    Double normalAdjD
    String timeTransferred
    //pmh cdrqa 1222 07/01/14
    Integer formVersion=2
    // first tissue access module selections
    String ftm1
    String ftm2
    String ftm3
    String ftm4
    String ftm5
    String ftAddlTumor
     // second tissue access module selections
    String stm1
    String stm2
    String stm3
    String stm4
    String stm5
    String stAddlTumor
    
    String secTissueCollect
    Double secTisH
    Double secTisW
    Double secTisD
    // second tissue fields 23, 24, 25
    Double stAreaPercentage
    Double stContentPercentage
    String stAppearance
    String dimNoMeetCriteriaReas
   String  dimenMeetCriteria
    
    
    static belongsTo = CaseRecord
    
    static hasMany = [photos:PhotoRecord]
    
    static fetchMode = [photos:'eager']
    
    String toString(){"$caseRecord.caseId"}
    
    static constraints = {
        caseRecord(blank:false, nullable:false)
        tissueReceived(blank:true, nullable:true)
        tissueNotReceivedReason(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        dateTimeArrived(blank:true, nullable:true)
        nameReceived(blank:true, nullable:true)
        transportSOP(blank:true, nullable:true)
        transportPerformed(blank:true, nullable:true)
        transportComments(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        roomTemperature(blank:true, nullable:true)
        roomTemperatureUnit(blank:true, nullable:true)
        roomHumidity(blank:true, nullable:true)
        nameEvaluated(blank:true, nullable:true)
        resectionH(blank:true, nullable:true)
        resectionW(blank:true, nullable:true)
        resectionD(blank:true, nullable:true)
        resectionWeight(blank:true, nullable:true)
        diseaseObserved(blank:true, nullable:true)
        diseaseComments(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        diagnosis(blank:true, nullable:true)
        photoTaken(blank:true, nullable:true)
        reasonNoPhoto(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        inkUsed(blank:true, nullable:true)
        inkType(blank:true, nullable:true)
        excessReleased(blank:true, nullable:true)
        noReleaseReason(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        excessH(blank:true, nullable:true)
        excessW(blank:true, nullable:true)
        excessD(blank:true, nullable:true)
        areaPercentage(blank:true, nullable:true)
        contentPercentage(blank:true, nullable:true)
        appearance(blank:true, nullable:true)
        normalAdjReleased(blank:true, nullable:true)
        normalAdjH(blank:true, nullable:true)
        normalAdjW(blank:true, nullable:true)
        normalAdjD(blank:true, nullable:true)
        timeTransferred(blank:true, nullable:true)
        formVersion(blank:true, nullable:true)
        ftm1(blank:true, nullable:true)
        ftm2(blank:true, nullable:true)
        ftm3(blank:true, nullable:true)
        ftm4(blank:true, nullable:true)
        ftm5(blank:true, nullable:true)
        ftAddlTumor(blank:true, nullable:true)
        stm1(blank:true, nullable:true)
        stm2(blank:true, nullable:true)
        stm3(blank:true, nullable:true)
        stm4(blank:true, nullable:true)
        stm5(blank:true, nullable:true)
        stAddlTumor(blank:true, nullable:true)
        secTisH(blank:true, nullable:true)
        secTisW(blank:true, nullable:true)
        secTisD(blank:true, nullable:true)
        secTissueCollect(blank:true, nullable:true)
        stAreaPercentage(blank:true, nullable:true)
        stContentPercentage(blank:true, nullable:true)
        stAppearance(blank:true, nullable:true)
        dimNoMeetCriteriaReas(blank:true, nullable:true)
        dimenMeetCriteria(blank:true, nullable:true)
    }
    
    static mapping = {
        table 'bpv_tissue_gross_evaluation'
        id generator:'sequence', params:[sequence:'bpv_tissue_gross_evaluation_pk']
    }
}
