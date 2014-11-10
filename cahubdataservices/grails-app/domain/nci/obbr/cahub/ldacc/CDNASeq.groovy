package nci.obbr.cahub.ldacc
import nci.obbr.cahub.CDRBaseClass

class CDNASeq extends CDRBaseClass {

    static belongsTo = [stock:Stock]
    String barcode
    Date   dateRun
    String protocolName
    String protocolVersion
    String inputAmountUnits
    String inputAmount
    static hasMany = [cDNASeqResults:CDNASeqResult]
   
   
    static mapping = {
      table 'ldacc_cdnaseq'
      id generator:'sequence', params:[sequence:'ldacc_cdaseq_pk']
    }
    
     static constraints = {
         barcode(unique:true, nullable:false, blank:false, maxSize:50)
         dateRun(nullable:true, blank:true)
         protocolName(nullable:true, blank:true, maxSize:50)
         protocolVersion(nullable:true, blank:true, maxSize:20)
         inputAmountUnits(nullable:true, blank:true, maxSize:20)
         inputAmount(nullable:true, blank:true, maxSize:50)
         stock(nullable:false, blank:false)
     }
}
