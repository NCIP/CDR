package nci.obbr.cahub.ldacc

class ExpressionResult extends Result{
   static belongsTo = [expression:Expression]
    
    static auditable = false
    
    static mapping = {
      table 'ldacc_expression_result'
      id generator:'sequence', params:[sequence:'ldacc_expression_result_pk']
    }
    static constraints = {
    }
   
}
