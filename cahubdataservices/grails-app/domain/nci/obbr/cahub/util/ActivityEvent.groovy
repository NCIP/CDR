package nci.obbr.cahub.util

import nci.obbr.cahub.staticmembers.ActivityType
import nci.obbr.cahub.staticmembers.Study

class ActivityEvent {

    ActivityType activityType
    String caseId
    Study study
    String bssCode
    String restEventId
    String initiator
    String additionalInfo1
    String additionalInfo2
    Boolean wasEmailed = false
    Date dateCreated
    Date lastUpdated
    
    static auditable = true
    
    static constraints = {
        activityType(nullable:true, blank:true)
        caseId(nullable:true, blank:true)
        study(nullable:true, blank:true)
        bssCode(nullable:true, blank:true)
        restEventId(nullable:true, blank:true)
        initiator(nullable:true, blank:true)
        additionalInfo1(nullable:true, blank:true)
        additionalInfo2(nullable:true, blank:true)
        wasEmailed(nullable:true, blank:true)
    }
    
    static mapping = {
        table 'activity_event'
        id generator:'sequence', params:[sequence:'activity_event_pk']
    } 
}
