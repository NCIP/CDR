package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass

class Demographics extends CDRBaseClass{
    Date dateOfBirth
    Gender gender
    float height
    float weight
    float BMI
    String race
    String ethnicity
    String otherGender
    
    boolean raceWhite=false
    boolean raceAsian=false
    boolean raceBlackAmerican=false
    boolean raceIndian=false
    boolean raceHawaiian=false
    boolean raceUnknown=false
    
    String age
    
    Integer ageForIndex
    
    static transients = ['age']
    
    static mapping = {
      table 'gtex_crf_demographics'
      id generator:'sequence', params:[sequence:'gtex_crf_demographics_pk']
    }
    
     static searchable ={
        only= ['gender','height', 'weight','BMI', 'race', 'ethnicity', 'otherGender', 'ageForIndex' ]
        'BMI' index:"not_analyzed", format: "000000.00"
        weight index:"not_analyzed", format: "000000.00"
        height index:"not_analyzed", format: "000000.00"
        ageForIndex index:"not_analyzed", format: "000000", name: 'age'
      
       
       
        root false
    }
    static constraints = {
        dateOfBirth(nullable:true, blank:true)
        gender(nullable:true, blank:true)
        height(nullable:true, blank:true)
        weight(nullable:true, blank:true)
        BMI(nullable:true, blank:true)
        race(nullable:true, blank:true)
        ethnicity(nullable:true, blank:true)
        otherGender(nullable:true, blank:true)
        ageForIndex(nullable:true, blank:true)
    }
    
   /* String getRace(){
        String str=""
        if(raceWhite){
            str = str+ ",White"
        }
        if(raceAsian){
            str = str+ ",Asian"
        }
        if(raceBlackAmerican){
            str= str+ ",Black or African American"
        }
        if(raceIndian){
            str= str +",American Indian or Alaska Native"
        }
        if(raceHawaiian){
            str= str +",Native Hawaiian or other Pacific Islander"
        }
        if(raceUnknown){
            str= str +",Unknown"
        }
        
        if(str && str.length() > 0){
            str=str.substring(1)
        }
     
        return str
    }*/
    
     enum Gender {
        Male("Male"),
        Female("Female"),
        Unknown("Unknown"),
        Other("Other")
        
        final String value;

        Gender(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        
       
        static list() {
            [Male,Female,Unknown,Other]
        }
    }
    
    
     enum Race {
        W("White"),
        A("Asian"),
        B("Black or African American"),
        I("American Indian or Alaska Native"),
        H("Native Hawaiian or other Pacific Islander"),
        U("Unknown")
        
        final String value;

        Race(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [W,A,B,I,H,U]
        }
    }
    
    enum Ethnicity {
        H("Hispanic or Latino"),
        NH("Not-Hispanic or Latino"),
        NR("Not reported"),
        U("Unknown")
        
        final String value;

        Ethnicity(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [H, NH, NR, U]
        }
    }
    
}
