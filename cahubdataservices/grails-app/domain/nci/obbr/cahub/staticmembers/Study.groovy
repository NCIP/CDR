package nci.obbr.cahub.staticmembers

class Study extends StaticMemberBaseClass{
    static mapping = {
      table 'st_study'
      id generator:'sequence', params:[sequence:'st_study_pk']
    }
    
     static searchable ={
        only= ['name', 'code']
        'name' name:'studyName'
        'code' name:'studyCode'
        root false
    }
}
