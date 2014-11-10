package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.vocab.*

import nci.obbr.cahub.CDRBaseClass

class DeathCircumstances extends CDRBaseClass{
    YesNo deathCertificateAvailable
    Date dateTimePronouncedDead
    Date dateTimeActualDeath
    Date dateTimePresumedDeath
    Date dateTimeLastSeenAlive 
    String placeOfDeath
    String otherPlaceOfDeath
    String personDeterminedDeath
    String otherPersonDeterminedDeath
    String mannerOfDeath
    YesNo autopsyPerformed
    YesNo onVentilator
    String ventilatorDuration
    String deathCertificateCause
    String deathCertificateCauseV
    CVocabRecord deathCertCauseVocab
    String  immediateCause
    CVocabRecord immCodCvocab
    String otherImmediate
    String immediateInterval
    String  firstCause
    CVocabRecord firstCodCvocab
    String otherFirstCause
    String firstCauseInterval
    String  lastCause
    CVocabRecord lastCodCvocab
    String otherLastCause
    String lastCauseInterval
    YesNo wasRefrigerated
    String estimatedHours
    String hardyScale
    String opoType
    
    static mapping = {
      table 'gtex_crf_death_circ'
      id generator:'sequence', params:[sequence:'gtex_crf_death_circ_pk' ]
    }
    
    
    
    static constraints = {
         deathCertificateAvailable(nullable:true, blank:true)
         dateTimePronouncedDead(nullable:true, blank:true)
         dateTimeActualDeath(nullable:true, blank:true)
         dateTimePresumedDeath(nullable:true, blank:true)
         dateTimeLastSeenAlive(nullable:true, blank:true)
         placeOfDeath(nullable:true, blank:true)
         otherPlaceOfDeath(nullable:true, blank:true)
         personDeterminedDeath(nullable:true, blank:true)
         otherPersonDeterminedDeath(nullable:true, blank:true)
         mannerOfDeath(nullable:true, blank:true)
         autopsyPerformed(nullable:true, blank:true)
         onVentilator(nullable:true, blank:true)
         ventilatorDuration(nullable:true, blank:true)
         immediateCause(nullable:true, blank:true)
         otherImmediate(nullable:true, blank:true)
         immediateInterval(nullable:true, blank:true)
         firstCause(nullable:true, blank:true)
         otherFirstCause(nullable:true, blank:true)
         firstCauseInterval(nullable:true, blank:true)
         lastCause(nullable:true, blank:true)
         otherLastCause(nullable:true, blank:true)
         lastCauseInterval(nullable:true, blank:true)
         wasRefrigerated(nullable:true, blank:true)
         estimatedHours(nullable:true, blank:true)
         hardyScale(nullable:true, blank:true)
         opoType(nullable:true, blank:true)
         deathCertCauseVocab(nullable:true, blank:true)
         deathCertificateCause(nullable:true, blank:true)
         deathCertificateCauseV(nullable:true, blank:true)
         immCodCvocab(nullable:true, blank:true)
         firstCodCvocab(nullable:true, blank:true)
         lastCodCvocab(nullable:true, blank:true)
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
    
    
     enum DeathPlace {
        HospitalInpatient("Hospital inpatient"),
        EmergencyRoom("Emergency room"),
        Outpatient("Outpatient"),
        Hospice("Hospice"),
        NursingHome("Nursing home/Long-term care facility"),
        DecedentHome("Decedent’s home"),
        DeadOnArrivalAtHospital("Dead on arrival at hospital"),
        Other("Other")
        


        final String value;

        DeathPlace(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [HospitalInpatient,EmergencyRoom,Outpatient,Hospice,NursingHome,DecedentHome,DeadOnArrivalAtHospital,Other]
        }
    }
    
    
      enum PersonDeterminedDeath {
        P("Physician"),
        C("Coroner/Medical Examiner (ME)"),
        O("Other")


        final String value;

        PersonDeterminedDeath(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [P, C, O]
        }
    }
    
    
     enum MannerOfDeath {
        N("Natural"),
        A("Accident"),
        S("Suicide"),
        H("Homicide"),
        P("Pending"),
        U("Undetermined")


        final String value;

        MannerOfDeath(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [N,A,S,H,P,U]
        }
    }
    
    
     enum CauseOfDeath {
        HeartDisease("Heart Disease"),
        Cancer("Cancer"),
        Stroke("Stroke"),
        ChronicLowerRespiratoryDisease("Chronic Lower Respiratory Disease"),
        Accident("Accident"),
        Alzheimers("Alzheimers"),
        Diabetes("Diabetes"),
        Influenza("Influenza"),
        Pneumonia("Pneumonia"),
        Nephritis("Nephritis"),
        NephroticSyndrome("Nephrotic Syndrome"),
        Nephrosis("Nephrosis"),
        Septicemia("Septicemia"),
        Other("Other")


        final String value;

        CauseOfDeath(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [HeartDisease,Cancer,Stroke,ChronicLowerRespiratoryDisease,Accident,Alzheimers,
            Diabetes,Influenza, Pneumonia,Nephritis,NephroticSyndrome,Nephrosis,Septicemia,Other]
        }
    }
}
