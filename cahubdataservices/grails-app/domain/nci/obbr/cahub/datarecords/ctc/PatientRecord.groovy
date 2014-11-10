package nci.obbr.cahub.datarecords.ctc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.ctc.CtcCrf

class PatientRecord extends CDRBaseClass{

    
    CaseRecord caseRecord
    String cancerStage 
    BSS collectionSite 
    Date consentDate
    String disease
    String experiment
    String gender="Female"
    String patientId
    String visit
    String comments
    
    
    Date dateSubmitted
    String submittedBy
    
   // static hasMany = [ctccrf:CtcCrf]
    
     def static searchable = {
         only= ['id', 'experiment','visit', 'cancerStage','dateCreated', 'patientId' ]
         collectionSite component: true
         caseRecord component: true
         'visit'  name:'visitCount'
          'dateCreated'  name:'patientDateCreated', format: "yyyy-MM-dd HH:mm"
        // root false
         
     }
    
       
    static constraints = {
        caseRecord(nullable:true,blank:true)
        cancerStage(blank:true, nullable:true)
        consentDate(blank:true, nullable:true)
        collectionSite(blank:true, nullable:true)
        //caseId(blank:true, nullable:true)
        dateSubmitted(blank:true, nullable:true)
        disease (blank:true, nullable:true)
        experiment(blank:true, nullable:true)
        gender(blank:true, nullable:true)
        patientId(blank:false, nullable:false,unique:true)
        submittedBy(blank:true, nullable:true)
        visit(blank:true, nullable:true)
        comments(blank:true, nullable:true, widget:'textarea', maxSize:4000)
                
    }

    static mapping = {
        table 'dr_patient'
        id generator:'sequence', params:[sequence:'dr_patient_pk']
    } 


}
