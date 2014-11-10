package nci.obbr.cahub.util.querytracker

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.staticmembers.BSS

class Deviation extends CDRBaseClass {

    CaseRecord caseRecord
    BSS bss
    String description
    String sop
    String nonConformance
    String type
    Date dateDeviation
    QueryStatus queryStatus
    String planned
    String memoCiNum
    Date memoExpiration
    String jiraId // comma separated jira ids 
    String nonConformCapaNum // comma separated 
    Date capaCloseDate
    Date jiraCloseDate
    
    static hasMany = [queries:Query, queryAttachments:QueryAttachment, queryResponses:QueryResponse]
    
    static constraints = {
        caseRecord(nullable:true, blank:true)
        bss(nullable:true, blank:true)
        description(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        sop(nullable:true, blank:true)
        nonConformance(nullable:true, blank:true)
        type(nullable:true, blank:true)
        dateDeviation(nullable:true, blank:true)
        queryStatus(nullable:true, blank:true)
        planned(nullable:true, blank:true)
        memoCiNum(nullable:true, blank:true)
        memoExpiration(nullable:true, blank:true)
        jiraId(nullable:true, blank:true)
        nonConformCapaNum(nullable:true, blank:true)
        capaCloseDate(nullable:true, blank:true)
        jiraCloseDate(nullable:true, blank:true)
    }
    
    static mapping = {
        table 'deviation'
        id generator:'sequence', params:[sequence:'deviation_pk']
        sort dateCreated:'desc'
        queryAttachments sort:'dateCreated'
        queryResponses sort:'dateCreated'
    } 
}
