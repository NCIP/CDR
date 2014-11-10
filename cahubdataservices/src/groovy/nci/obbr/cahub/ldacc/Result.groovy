package nci.obbr.cahub.ldacc
import nci.obbr.cahub.CDRBaseClass

abstract class Result extends CDRBaseClass {
    String attribute
    String value
    
    static auditable = false
  
     static constraints = {
         attribute(nullable:true, blank:true)
         value(nullable:true, blank:true)
        
     }
}
