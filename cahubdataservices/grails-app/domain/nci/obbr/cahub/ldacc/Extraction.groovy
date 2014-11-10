package nci.obbr.cahub.ldacc

import nci.obbr.cahub.CDRBaseClass

class Extraction extends CDRBaseClass {
    static belongsTo = [specimen:Specimen]
    Date dateRun
   // String inputAmountUnits
   // String inputAmount
    String protocolName
    //String protocolVersion
    
    
    static auditable = false
    
    static hasMany=[stocks:Stock]
    
    static mapping = {
      table 'ldacc_extraction'
      id generator:'sequence', params:[sequence:'ldacc_extraction_pk']
    }
    
     static constraints = {
         dateRun(nullable:true, blank:true)
        // inputAmountUnits(nullable:true, blank:true, maxSize:20)
       //  inputAmount(nullable:true, blank:true, maxSize:50)
         protocolName(nullable:true, blank:true)
        // protocolVersion(nullable:true, blank:true, maxSize:20)
     }
    
}
