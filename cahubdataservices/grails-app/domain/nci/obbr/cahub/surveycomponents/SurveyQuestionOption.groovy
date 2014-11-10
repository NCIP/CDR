package nci.obbr.cahub.surveycomponents

import nci.obbr.cahub.surveyrecords.*

class SurveyQuestionOption {
    SurveyQuestion parentQuestion
    String optionText
    String optionLabel
    String optionType
    
    static constraints = {
        optionText(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        optionLabel(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        parentQuestion(nullable:true, blank:true)
        optionType(nullable:true, blank:true)
    }
    
    
    
    String toString(){"$optionText"}
    
   static mapping = {
      table 'sv_question_option'
      id generator:'sequence', params:[sequence:'sv_question_option_pk']
      
      sort id:"desc"  
    }      
    
}
