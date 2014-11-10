package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.datarecords.SOPRecord

class BpvScreeningEnrollment extends BpvFormBaseClass {
    
    String protocolSiteNum
    CandidateRecord candidateRecord
    String nameCreatCandidate
    String meetCriteria
    String reasonNotMeet
    String otherReasonNotMeet
    String consentObtained
    String reasonNotConsented
    String otherReason
    String nameConsentCandidate
    String comments
    Date surgeryDate
    SOPRecord formSOP

    static constraints = {
        protocolSiteNum(blank:true, nullable:true)
        candidateRecord(blank:false, nullable:false)
        nameCreatCandidate(blank:true, nullable:true)
        meetCriteria(blank:true, nullable:true)
        reasonNotMeet(blank:true, nullable:true)
        otherReasonNotMeet(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        consentObtained(blank:true, nullable:true)
        reasonNotConsented(blank:true, nullable:true)
        otherReason(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        nameConsentCandidate(blank:true, nullable:true)
        comments(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        surgeryDate(blank:true, nullable:true)
        formSOP(blank:true, nullable:true)
    }
    
    static mapping = {
        table 'bpv_screening_enrollment'
        id generator:'sequence', params:[sequence:'bpv_screening_enrollment_pk']
    }
}
