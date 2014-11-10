package nci.obbr.cahub.datarecords

import nci.obbr.cahub.CDRBaseClass

class CbrApiEvent extends CDRBaseClass {

    String bio4dEventId
    String eventType
    String rawData        
    String eventStatus
  
    static constraints = {
        bio4dEventId(blank:true,nullable:true)
        eventType(blank:true,nullable:true)
        rawData(blank:true,nullable:true)
        eventStatus(blank:true,nullable:true)
    }
    
    static mapping = {
      table 'dr_cbr_api_event'
      id generator:'sequence', params:[sequence:'dr_cbr_api_event_pk']
      sort id:"desc"  
      rawData sqlType: 'clob'
    }
}
