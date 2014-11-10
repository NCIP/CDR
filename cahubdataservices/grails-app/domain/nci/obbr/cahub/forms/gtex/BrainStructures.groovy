package nci.obbr.cahub.forms.gtex
import nci.obbr.cahub.CDRBaseClass

class BrainStructures extends CDRBaseClass{
    
    String structName
    String structCode
    String description
    boolean preSelected=false
    boolean userInput=false
    static belongsTo = [brainbankfeedback:BrainBankFeedback]
    
      static constraints = {
            structName(blank:true,nullable:true)
            structCode(blank:true,nullable:true)
            description(blank:true, nullable:true,widget:'textarea',maxSize:4000)
      }

     static mapping = {
      table 'brain_structures'
      id generator:'sequence', params:[sequence:'brain_structures_pk']
    }
}
