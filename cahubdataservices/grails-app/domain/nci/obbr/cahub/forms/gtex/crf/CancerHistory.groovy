package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.vocab.*

class CancerHistory extends CDRBaseClass{
    String  primaryTumorSite
    // String MonthOfFirstDiagnosis
    // String YearOfFirstDiagnosis
    Date monthYearOfFirstDiagnosis
    String treatments
    //  String MonthOfLastTreatment
    //  String YearOfLastTreatment
    Date monthYearOfLastTreatment
    YesNo medicalRecordExist
    
    boolean treatmentSurgery=false
    boolean treatmentRadiation=false
    boolean treatmentChemotherapy=false
    boolean treatmentOther=false
    boolean  treatmentNo =false
    boolean treatmentUnknown=false
    
    String otherTreatment
    
    // pmh: new for v 4.5 onwards
    String source
    CVocabRecord primaryTumorSiteCvocab
    
    
    static belongsTo = [medicalHistory:MedicalHistory]
    
    static mapping = {
        table 'gtex_crf_cancer_history'
        id generator:'sequence', params:[sequence:'gtex_crf_cancer_history_pk' ]
    }
    
    
    static constraints = {
        primaryTumorSite(nullable:true, blank:true)
        monthYearOfFirstDiagnosis(nullable:true, blank:true)
        treatments(nullable:true, blank:true)
        monthYearOfLastTreatment(nullable:true, blank:true)
        medicalRecordExist(nullable:true, blank:true)
        otherTreatment (nullable:true, blank:true) 
        source(nullable:true, blank:true)
        primaryTumorSiteCvocab(nullable:true, blank:true)
    }
    
    String getTreatments(){
        
        String str = '';
        if(treatmentSurgery){
            str = str +",Surgery"
        }
        if(treatmentRadiation){
            str = str+",Radiation"
        }
        if(treatmentChemotherapy){
            str = str+",Chemotherapy"
        }
        
        if(treatmentOther){
            if(otherTreatment){
                str = str +"," +otherTreatment
            }
        }
        if(treatmentNo){
            str = str+ ",None"
        }
        if(treatmentUnknown){
            str = str+ ",Unknown"
        }
        
        if(str && str.length() > 0){
            str=str.substring(1)
        }
        
        if(!str)
        str = 'null'
        return str
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
