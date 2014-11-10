package nci.obbr.cahub.prctumor
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.CDRBaseClass

class PrcHistologicType extends CDRBaseClass{
  
    HistologicType histologicType
    boolean included
    String details
    
    static belongsTo = [prcForm:PrcForm]
    
    static mapping = {
      table 'prc_histologic_type'
      id generator:'sequence', params:[sequence:'prc_histologic_type_pk']
    }
    
    static constraints = {
        histologicType(unique:false, nullable:false, blank:false)
        details(nullable:true, blank:true)
    }
}
