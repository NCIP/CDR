package nci.obbr.cahub.ldacc

class CDNASeqResult extends Result {
    static belongsTo = [cDNASeq:CDNASeq]
 
    static mapping = {
      table 'ldacc_cdnaseq_result'
      id generator:'sequence', params:[sequence:'ldacc_cdnaseq_result_pk']
    }
    static constraints = {
    }
  
}
