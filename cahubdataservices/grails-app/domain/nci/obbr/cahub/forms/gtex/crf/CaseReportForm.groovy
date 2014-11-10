package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.CDRBaseClass

class CaseReportForm extends CDRBaseClass {
    Demographics demographics
    MedicalHistory medicalHistory
    static hasMany=[concomitantMedications:ConcomitantMedication]
    DeathCircumstances deathCircumstances
    SerologyResult serologyResult
    CaseRecord caseRecord
    Status status
    SurgicalProcedures surgicalProcedures

    static mapping = {
      table 'gtex_crf'
      id generator:'sequence', params:[sequence:'gtex_crf_pk']
    }
    
    
     static searchable ={
        only= ['id']
       
        demographics component: true
        
        root false
    }
    
    static constraints = {
         caseRecord(unique:true, nullable:false, blank:false)
         //demographics(nullable:true, blank:true)
        // medicalHistory(nullable:true, blank:true)
         //deathCircumstances(nullable:true, blank:true)
         deathCircumstances(nullable:true, blank:true)
         surgicalProcedures(nullable:true, blank:true)
 
        
        // serologyResult(nullable:true, blank:true)
         
    }
    
    
    enum Status {
        Editing(0),
        Submitted(1)
        


        final int value;

        Status(int value) {
            this.value = value;
        }
        String toString(){
            (new Integer(value)).toString()
        }
        String getKey(){
            name()
        }
        static list() {
            [Editing, Submitted]
        }
       
    }
}
