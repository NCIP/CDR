package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass

class SurgicalMedication extends CDRBaseClass{
    String medicationName
    String dosage
    String catgory
    String subCatgory
    boolean isOther1
    boolean isOther2
    static belongsTo = [surgicalProcedures:SurgicalProcedures]
    Integer rowid
    
    static transients = ['rowid']
    
    static mapping = {
      table 'gtex_crf_surgical_med'
      id generator:'sequence', params:[sequence:'gtex_crf_surgical_med_pk' ]
    }

   
    
    static constraints = {
        
         medicationName(nullable:true, blank:true)
         dosage(nullable:true, blank:true)
         catgory(nullable:true, blank:true)
         subCatgory(nullable:true, blank:true)
        
    }
}
