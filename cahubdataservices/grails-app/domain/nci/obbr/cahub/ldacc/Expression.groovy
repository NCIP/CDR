package nci.obbr.cahub.ldacc
import nci.obbr.cahub.CDRBaseClass

class Expression extends CDRBaseClass{
    static belongsTo = [specimen:Specimen]
    String lsid
    String stockLsid
    String chipTypeName
    Stock stock
    
    static auditable = false
    
    static hasMany = [expressionResults:ExpressionResult]
   
   
    static mapping = {
      table 'ldacc_expression'
      id generator:'sequence', params:[sequence:'ldacc_expression_pk']
    }
    
     static constraints = {
         lsid(unique:true, nullable:false, blank:false)
         stockLsid(nullable:true, blank:true)
         stock(nullable:true, blank:true)
         chipTypeName(nullable:true, blank:true)
         
     }
   
}
