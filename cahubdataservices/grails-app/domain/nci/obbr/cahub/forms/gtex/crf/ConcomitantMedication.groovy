package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.vocab.*

class ConcomitantMedication extends CDRBaseClass{

    String medicationName
    CVocabRecord medicationNameCvocab
    String dosage
    Float  amount
    String frequency
    String dosageUnit
    String route
    Date dateofLastAdministration
    // pmh: new for v 4.5 onwards
    String source
    
    static belongsTo = [caseReportForm:CaseReportForm]
    
    
    static mapping = {
        table 'gtex_crf_concomitant_med'
        id generator:'sequence', params:[sequence:'gtex_crf_concomitant_med_pk' ]
    }
    
    
    static constraints = {
        medicationName(nullable:true, blank:true, maxSize:4000)
        medicationNameCvocab(nullable:true, blank:true)
        dosage(nullable:true, blank:true)
        route(nullable:true, blank:true)
        dateofLastAdministration(nullable:true, blank:true)
        amount(nullable:true, blank:true)
        dosageUnit(nullable:true, blank:true)
        frequency(nullable:true, blank:true)
        source(nullable:true, blank:true)
    }
}
