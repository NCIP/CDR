package nci.obbr.cahub.util.querytracker

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.surveyrecords.InterviewRecord
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.Organization
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.staticmembers.QueryType

class Query extends CDRBaseClass {

    CaseRecord caseRecord
    CandidateRecord candidateRecord
    InterviewRecord interviewRecord
    String other
    Study study
    Organization organization
    String openedBy
    QueryStatus queryStatus
    String isDcf
    String dcfId
    String jira
    String isPr2
    String pr2Id
    String pr2Jira
    String pr2Dcf
    QueryType queryType
    String description
    Date dueDate
    String closedBy
    Date dateClosed
    Boolean gtexCrf
    Boolean gtexTrf
    Boolean gtexConsent
    Boolean gtexDonorEligi
    Boolean bmsTrf
    Boolean bpvScreening
    Boolean bpvConsent
    Boolean bpvBlood
    Boolean bpvGross
    Boolean bpvSurgery
    Boolean bpvDissection
    Boolean bpvWorksheet
    Boolean bpvProcessing
    Boolean bpvStain
    Boolean bpvFinalSurgi
    Boolean bpvClinical
    Boolean bpvQuality
    Boolean bpvLocalPath
    Boolean elsiSurvey
    Boolean elsiCrf
    Integer accessId
    String task
    
    static hasMany = [queryAttachments:QueryAttachment, queryResponses:QueryResponse]
    
    static belongsTo = [deviation:Deviation]
    
    static searchable = {
        only=['description', 'id', 'accessId', 'dateCreated', 'other']
        organization component: true
        study component: true
        caseRecord component: true
        candidateRecord component: true
        'dateCreated'  name:'qtDateCreated', format: "yyyy-MM-dd HH:mm"
        'id' name: 'qtId'
        'description' name: 'qtDescription'
        'other' name: 'qtTitle'
        queryStatus component: true
        queryType component: true
    }
    
    static constraints = {
        caseRecord(nullable:true, blank:true)
        candidateRecord(nullable:true, blank:true)
        interviewRecord(nullable:true, blank:true)
        other(nullable:true, blank:true)
        study(nullable:true, blank:true)
        organization(nullable:true, blank:true)
        openedBy(nullable:true, blank:true)
        queryStatus(nullable:true, blank:true)
        isDcf(nullable:true, blank:true)
        dcfId(nullable:true, blank:true)
        jira(nullable:true, blank:true)
        isPr2(nullable:true, blank:true)
        pr2Id(nullable:true, blank:true)
        pr2Jira(nullable:true, blank:true)
        pr2Dcf(nullable:true, blank:true)
        queryType(nullable:true, blank:true)
        description(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        dueDate(nullable:true, blank:true)
        closedBy(nullable:true, blank:true)
        dateClosed(nullable:true, blank:true)
        gtexCrf(nullable:true, blank:true)
        gtexTrf(nullable:true, blank:true)
        gtexConsent(nullable:true, blank:true)
        gtexDonorEligi(nullable:true, blank:true)
        bmsTrf(nullable:true, blank:true)
        bpvScreening(nullable:true, blank:true)
        bpvConsent(nullable:true, blank:true)
        bpvBlood(nullable:true, blank:true)
        bpvGross(nullable:true, blank:true)
        bpvSurgery(nullable:true, blank:true)
        bpvDissection(nullable:true, blank:true)
        bpvWorksheet(nullable:true, blank:true)
        bpvProcessing(nullable:true, blank:true)
        bpvStain(nullable:true, blank:true)
        bpvFinalSurgi(nullable:true, blank:true)
        bpvClinical(nullable:true, blank:true)
        bpvQuality(nullable:true, blank:true)
        bpvLocalPath(nullable:true, blank:true)
        elsiSurvey(nullable:true, blank:true)
        elsiCrf(nullable:true, blank:true)
        accessId(unique:true, nullable:true, blank:true)
        task(nullable:true, blank:true)
        deviation(nullable:true, blank:true)
    }
    
    static mapping = {
        table 'query'
        id generator:'sequence', params:[sequence:'query_pk']
        sort dateCreated:'desc'
        queryAttachments sort:'dateCreated'
        queryResponses sort:'dateCreated'
    } 
}
