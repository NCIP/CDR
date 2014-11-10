package nci.obbr.cahub.surveyrecords

import nci.obbr.cahub.surveycomponents.*

class SurveyAnswer {

    SurveyRecord surveyRecord
    SurveyQuestion question //id of the question asked
    String response
    //Map response = [:]
    
    static constraints = {
        surveyRecord(nullable:true, blank:true)
        response(nullable:true, blank:true, widget:'textarea',maxSize:4000)
    }
    
   static auditable = true
    
   static mapping = {
      table 'sv_answer'
      id generator:'sequence', params:[sequence:'sv_answer_pk']
      
      sort id:"desc"  
    }        
    
}
