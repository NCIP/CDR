package nci.obbr.cahub.staticmembers

class StudyPhase extends StaticMemberBaseClass{
    
    Study study
    
    static mapping = {
      table 'st_studyphase'
      id generator:'sequence', params:[sequence:'st_studyphase_pk']
    }
    
     static searchable ={
        only= ['name', 'code']
        'name' name:'phaseName'
        'code' name:'phaseCode'
        root false
    }
    
    static constraints = {
        study(blank:false, nullable:false)        
    }
    
}
