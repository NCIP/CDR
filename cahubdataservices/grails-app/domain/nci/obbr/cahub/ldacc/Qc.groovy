package nci.obbr.cahub.ldacc

import nci.obbr.cahub.CDRBaseClass

class Qc extends CDRBaseClass {
    
    static belongsTo = [specimen:Specimen]
    String lsid
    String protocolName
    Stock stock
    
    Date dateRun
    
    static auditable = false
          
    static hasMany = [qcResults:QcResult]

    static mapping = {
      table 'ldacc_qc'
      id generator:'sequence', params:[sequence:'ldacc_qc_pk']
    }

    
    static constraints = {
         lsid(nullable:false, blank:false, maxSize:50)
         protocolName(nullable:true, blank:true)
         dateRun(nullable:true, blank:true)
         stock(nullable:true, blank:true)

    }

  
}
