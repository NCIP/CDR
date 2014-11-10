package nci.obbr.cahub.ldacc

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.CDRBaseClass

class Specimen extends CDRBaseClass {
    static belongsTo = [donor:Donor]
    SpecimenRecord specimenRecord
    String publicId
    String privateId
    String lsid
    String materialType
    Date receiptDate
    String receiptNote
    String tissueSite
    String tissueSiteDetail
   
   // String massRemainingUnits
    //String massRemaining
    
    static auditable = false
    
    static hasMany = [qcs:Qc, extractions:Extraction, expressions:Expression]
    
     static mapping = {
      table 'ldacc_specimen'
      id generator:'sequence', params:[sequence:'ldacc_specimen_pk']
     }

    static constraints = {
        publicId(nullable:true, blank:true, maxSize:50)
        privateId(nullable:true, blank:true, maxSize:50)
        materialType(nullable:true, blank:true)
        receiptDate(nullable:true, blank:true)
        receiptNote(nullable:true, blank:true, maxSize:500)
        tissueSite(nullable:true, blank:true)
        tissueSiteDetail(nullable:true, blank:true)
        lsid(nullable:true, blank:true)
       
        
       // massRemainingUnits(nullable:true, blank:true, maxSize:20)
       // massRemaining(nullable:true, blank:true, maxSize:50)
    }
}
