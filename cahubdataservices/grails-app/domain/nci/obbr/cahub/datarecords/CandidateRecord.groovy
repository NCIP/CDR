package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.*
import nci.obbr.cahub.forms.gtex.*
import nci.obbr.cahub.forms.bpv.*

class CandidateRecord extends DataRecordBaseClass{
    
    String candidateId
    CaseRecord caseRecord
    BSS bss
    Study study
    CaseCollectionType caseCollectionType
    boolean isConsented = false
    boolean isEligible = false
    
   //pmh 08/15/13 new for ver 5.2
   String cdrVer
    
     static searchable = {
         only=['candidateId', 'id', 'dateCreated']
         bss component: true
         study component: true
         caseRecord component: true
          'dateCreated'  name:'candidateDateCreated', format: "yyyy-MM-dd HH:mm"
       // icdGtexNdri component: true
       // icdGtexRpci component: true
       // icdGtexSc component: true
       // donorEligibilityGtex component: true
       
    }

    
    static hasOne = [icdGtexNdri:IcdGtexNdri, icdGtexRpci:IcdGtexRpci, icdGtexSc:IcdGtexSc, donorEligibilityGtex:DonorEligibilityGtex, bpvConsentEnrollment:BpvConsentEnrollment, bpvScreeningEnrollment:BpvScreeningEnrollment]

    String toString(){"$candidateId"}  
    
    static constraints = {
        
        candidateId(nullable:false,blank:false)
        caseRecord(nullable:true,blank:true)
        bss(nullable:false,blank:false)
        study(nullable:false,blank:false)
        caseCollectionType(nullable:false,blank:false)
        isConsented(nullable:false,blank:false)
        isEligible(nullable:false,blank:false)
        icdGtexNdri(nullable:true,blank:true)
        icdGtexRpci(nullable:true,blank:true)
        icdGtexSc(nullable:true,blank:true)
        donorEligibilityGtex(nullable:true,blank:true)
        bpvConsentEnrollment(nullable:true,blank:true)
        bpvScreeningEnrollment(nullable:true,blank:true)
        //pmh 08/15/13 new for ver 5.2
        cdrVer(nullable:true,blank:true)
    }
    
     static mapping = {
      table 'dr_candidate'
      id generator:'sequence', params:[sequence:'dr_candidate_pk']
      
      sort dateCreated:"desc"  
        
    }
}
