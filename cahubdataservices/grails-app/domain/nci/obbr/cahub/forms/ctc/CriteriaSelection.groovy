package nci.obbr.cahub.forms.ctc
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.CDRBaseClass

class CriteriaSelection extends CDRBaseClass {
     CtcSample ctcSample
     MorphologicalCriteria morphCrireria
     boolean selected = false
     
    static constraints = {
        ctcSample(nullable:false,blank:false)
        morphCrireria(nullable:true,blank:true)
    }
    
    static mapping = {
        table 'criteria_selection'
        id generator:'sequence', params:[sequence:'criteria_selection_pk']
     } 
}
