package nci.obbr.cahub.surveycomponents

import nci.obbr.cahub.surveyrecords.*

class SurveyQuestion {

    SurveyTemplate surveyTemplate
    SurveyQuestion parentQuestion
    String questionText
    String section
    SurveySection surveySection
    String answerType
    String uiClass
    
    static hasMany = [answers:SurveyAnswer, options:SurveyQuestionOption]
    
    static constraints = {
        
        questionText(nullable:true, blank:true, widget:'textarea',maxSize:4000)        
        parentQuestion(nullable:true, blank:true)
        section(nullable:true, blank:true)
        answers(nullable:true, blank:true)
        options(nullable:true, blank:true)
        surveySection(nullable:true, blank:true)
        answerType(nullable:true, blank:true)
        uiClass(nullable:true, blank:true)
    }
    
    String toString(){"$questionText"}
    
   static mapping = {
      table 'sv_question'
      id generator:'sequence', params:[sequence:'sv_question_pk']
      
      sort id:"desc"  
    }      
    
}
