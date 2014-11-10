package nci.obbr.cahub.surveycomponents

import nci.obbr.cahub.surveyrecords.*

class SurveySection {

    SurveyTemplate surveyTemplate
    SurveySection parentSection
    String sectionHeader
    String sectionDescription
    
    static hasMany = [questions:SurveyQuestion]
    
    static constraints = {
        surveyTemplate(nullable:true, blank:true)
        sectionHeader(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        sectionDescription(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        parentSection(nullable:true, blank:true)
    }
    
   static mapping = {
      table 'sv_section'
      id generator:'sequence', params:[sequence:'sv_section_pk']
      
      sort id:"desc"  
      questions sort:"id"
    }      
    
}
