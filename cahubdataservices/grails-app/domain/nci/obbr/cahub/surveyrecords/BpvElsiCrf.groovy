package nci.obbr.cahub.surveyrecords

import nci.obbr.cahub.forms.bpv.*
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.DataRecordBaseClass

class BpvElsiCrf extends SurveyRecordBaseClass {
    InterviewRecord interviewRecord
    Date  initialReferralDate 
    String bpvStudyConsent    
    Double daysConsentToInterview
    Double daysInterviewToSurgery
    String tumorLocation
    Date initialContactDate
    String contactResultA
    String contactResultB
    String contactResultB1
    String contactResultB2
    String contactResultC
    String contactResultC1
    String contactResultC2
    String contactResultC2A
    String contactResultC2B
    String contactResultC2C
    Date interviewStartDate
    Date interviewEndDate
    String interviewStartTime
    String interviewEndTime
    String interviewInterruptReason
    Date interviewRestartDate
    Date interviewRestartEndDate
    String interviewRestartTime
    String interviewRestartEndTime
    String surveyAdministrationMode
    String dataCollectionMethod
    Date CIFShredDate
    String CIFShredTime
    Date responsesTranscriptionDate
    Date transcriptionVerifiedDate
    Date cdrEntryDate
    Date cdrEntryVerifiedDate
    Date audioRecordEraseDate
    Date audioEraseVerifiedDate
    String protocolDeviations
    String comments
    String referringStudy
    String externalStudySpecify
    
    Integer formVersion=2
    
    
    static constraints = {
    
    interviewRecord(blank:false,nullable:false) 
    initialReferralDate(blank:true,nullable:true) 
    bpvStudyConsent(blank:true,nullable:true)
    daysConsentToInterview(blank:true,nullable:true)
    daysInterviewToSurgery(blank:true,nullable:true)
    tumorLocation(blank:true,nullable:true)
    initialContactDate(blank:true,nullable:true)
    contactResultA(blank:true,nullable:true)
    contactResultB(blank:true,nullable:true)
    contactResultB1(blank:true,nullable:true)
    contactResultB2(blank:true,nullable:true)
    contactResultC(blank:true,nullable:true)
    contactResultC1(blank:true,nullable:true)
    contactResultC2(blank:true,nullable:true)
    contactResultC2A(blank:true,nullable:true)
    contactResultC2B(blank:true,nullable:true)
    contactResultC2C(blank:true,nullable:true)
    interviewStartDate(blank:true,nullable:true)
    interviewEndDate(blank:true,nullable:true)
    interviewStartTime(blank:true,nullable:true)
    interviewEndTime(blank:true,nullable:true)
    interviewInterruptReason(blank:true,nullable:true,widget:'textarea',maxSize:4000)
    interviewRestartDate(blank:true,nullable:true)
    interviewRestartEndDate(blank:true,nullable:true)
    interviewRestartTime(blank:true,nullable:true)
    interviewRestartEndTime(blank:true,nullable:true)
    surveyAdministrationMode(blank:true,nullable:true)
    dataCollectionMethod(blank:true,nullable:true)
    CIFShredDate(blank:true,nullable:true)
    CIFShredTime(blank:true,nullable:true)
    responsesTranscriptionDate(blank:true,nullable:true)
    transcriptionVerifiedDate(blank:true,nullable:true)
    cdrEntryDate(blank:true,nullable:true)
    cdrEntryVerifiedDate(blank:true,nullable:true)
    audioRecordEraseDate(blank:true,nullable:true)
    audioEraseVerifiedDate(blank:true,nullable:true)
    protocolDeviations(blank:true,nullable:true,widget:'textarea',maxSize:4000)
    comments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
    referringStudy(blank:true,nullable:true)
    externalStudySpecify(blank:true,nullable:true)
    formVersion(blank:true, nullable:true)
        
    }

    static mapping = {
         table 'bpv_elsi_crf'
         id generator:'sequence', params:[sequence:'bpv_elsi_crf_pk']
    }
}
