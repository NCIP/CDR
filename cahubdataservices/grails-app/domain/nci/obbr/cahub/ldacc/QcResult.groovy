package nci.obbr.cahub.ldacc



class QcResult extends Result{
     static belongsTo = [qc:Qc]
   
     static auditable = false
     
      static mapping = {
      table 'ldacc_qc_result'
      id generator:'sequence', params:[sequence:'ldacc_qc_result_pk']
    }

    
    static constraints = {
      

    }
}
