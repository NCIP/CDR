package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass

class MedicalHistory extends CDRBaseClass {
    String source
    Primary primary
    String otherPrimary
    YesNo nonMetastaticCancer
    static hasMany = [cancerHistories:CancerHistory, generalMedicalHistories:GeneralMedicalHistory]
    
    
   static mapping = {
      table 'gtex_crf_medic_his'
          primary column: 'primary_kin'
      id generator:'sequence', params:[sequence:'gtex_crf_medic_his_pk']
    }
    static constraints = {
         source(nullable:true, blank:true)
         primary(nullable:true, blank:true)
         otherPrimary(nullable:true, blank:true)
         nonMetastaticCancer(nullable:true, blank:true)
    }
    
     enum Source {
        E("Electronic Health Record"),
        M("Medical Record"),
        P("Person")
     
        
        final String value

        Source(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [E,M,P]
        }
    }
    
     enum Primary {
        Self("Self"),
        Spouse("Spouse"),
        Parent("Parent"),
        Child("Child"),
        Sibling("Sibling"),
        Other("Other")
     
        
        final String value

        Primary(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Self,Spouse,Parent, Child, Sibling, Other]
        }
    }
    
    
     enum YesNo {
        No("No"),
        Yes("Yes"),
        Unknown("Unknown")


        final String value;

        YesNo(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Yes, No, Unknown]
        }
    }
    
}
