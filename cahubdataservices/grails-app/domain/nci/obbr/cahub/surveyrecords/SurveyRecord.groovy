package nci.obbr.cahub.surveyrecords

import nci.obbr.cahub.surveycomponents.*

class SurveyRecord extends SurveyRecordBaseClass{

    InterviewRecord interviewRecord
    SurveyTemplate surveyTemplate
    
    static hasMany = [answers:SurveyAnswer]
    
    static constraints = {
        interviewRecord(blank:false,nullable:false)
        surveyTemplate(blank:false,nullable:false)
    }
    
    
   static mapping = {
      table 'sv_survey'
      id generator:'sequence', params:[sequence:'sv_survey_pk']
      
      sort id:"desc"  
    }       
    
}
