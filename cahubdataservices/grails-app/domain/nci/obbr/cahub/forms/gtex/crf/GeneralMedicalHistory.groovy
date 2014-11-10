package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.vocab.*

class GeneralMedicalHistory extends CDRBaseClass{
    String medicalCondition
    CVocabRecord medicalConditionCvocab
    YesNo  chooseOption
    String yearOfOnset
    YesNo  treatment
    YesNo  medicalRecord
    String displayOrder
    Integer rown
    String source
    //pmh vocab test
    String code
    static belongsTo = [medicalHistory:MedicalHistory]
    
    static mapping = {
        table 'gtex_crf_general_medic_his'
        id generator:'sequence', params:[sequence:'gtex_crf_general_medic_his_pk' ]
    }
    
    static constraints = {
        chooseOption(nullable:true, blank:true)
        medicalConditionCvocab(nullable:true, blank:true)
        yearOfOnset(nullable:true, blank:true)
        treatment(nullable:true, blank:true)
        medicalRecord(nullable:true, blank:true)
        displayOrder(nullable:true, blank:true)
        source(nullable:true, blank:true)
        code(nullable:true, blank:true)
        medicalCondition(nullable:true, blank:true, maxSize:4000)
        
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
