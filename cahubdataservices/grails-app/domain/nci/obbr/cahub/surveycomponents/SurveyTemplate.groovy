package nci.obbr.cahub.surveycomponents

class SurveyTemplate {

    String surveyName
    String surveyCode
    String studyCode
    String surveyDesc
    
    //static hasMany = [questions:SurveyQuestion]=]]
    static hasMany = [sections:SurveySection]
    
    static constraints = {
        surveyName(nullable:true, blank:true)
        surveyDesc(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        studyCode(nullable:true, blank:true)
        surveyCode(nullable:true, blank:true)
    }
    
    String toString(){"$surveyName"}
    
    static mapping = {
       table 'sv_template'
       id generator:'sequence', params:[sequence:'sv_template_pk']
      
       sort id:"desc"  
      sections sort:"id"       
    }       
    
}
