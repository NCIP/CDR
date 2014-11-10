package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class PrcAttachment {
    SpecimenRecord specimenRecord
    String filePath
    String caption
    
    //did not extend the class to CDRBaseClass when the class implemented, try to add those fields now...
    String internalGUID = java.util.UUID.randomUUID().toString()
    String internalComments
    String publicComments
    Date dateCreated
    Date lastUpdated
    
      static auditable = true
      
     static mapping = {
      table 'prc_attachment'
      id generator:'sequence', params:[sequence:'prc_attachment_pk']
    }
    static constraints = {
        specimenRecord(unique:false, nullable:false, blank:false)
        filePath(nullable:true, blank:true)
        caption(nullable:true, blank:true)
        
          internalComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        publicComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        
        dateCreated(nullable:true, blank:true)
        lastUpdated(nullable:true, blank:true)
        
        internalGUID(nullable:true, blank:true)
        //publicVersion(nullable:true, blank:true)
    }
}
