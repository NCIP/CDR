package nci.obbr.cahub.forms.gtex
import nci.obbr.cahub.CDRBaseClass

class BrainDamage extends CDRBaseClass{

    
    BrainStructures bstruct
    String region
    String studyType
    String observation
   // boolean preSelected=false
    boolean userInput=false
    
    static belongsTo = [brainbankfeedback:BrainBankFeedback]
    static constraints = {
            bstruct(blank:true,nullable:true)
            region(blank:true,nullable:true)
            studyType(blank:true,nullable:true)
            observation(blank:true, nullable:true,widget:'textarea',maxSize:4000)
      }
    
     static mapping = {
      table 'brain_damage'
      id generator:'sequence', params:[sequence:'brain_damage_pk']
    }
}
