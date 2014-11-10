package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass
class SurgicalProcedures extends CDRBaseClass {
    String durationPreOpeMed
    String durationAnesthesia
    
    static hasMany = [surgicalMedications:SurgicalMedication]
     static mapping = {
      table 'gtex_crf_surgical_proc'
      id generator:'sequence', params:[sequence:'gtex_crf_surgical_proc_pk' ]
    }

    static constraints = {
         durationPreOpeMed(nullable:true, blank:true)
         durationAnesthesia(nullable:true, blank:true)
    }
}
