package nci.obbr.cahub.staticmembers

class MedicalCondition extends StaticMemberBaseClass{
    Integer rown
    
     static mapping = {
      table 'st_medical_condition'
      id generator:'sequence', params:[sequence:'st_medical_condition_pk']
    }

    static constraints = {
    }
}
